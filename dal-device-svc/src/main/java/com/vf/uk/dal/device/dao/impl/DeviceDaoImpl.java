package com.vf.uk.dal.device.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.BazaarVoice;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.querybuilder.DeviceQueryBuilderHelper;
import com.vf.uk.dal.device.utils.BazaarVoiceCache;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.CoupleRelation;

/**
 * 1.Implementation of DeviceDAO Interface 2.DeviceDaoImpl should make call to
 * the exact methods of Solr and Coherence and should retrieve all the details.
 * 
 * 
 **/

@Component("deviceDao")
public class DeviceDaoImpl implements DeviceDao {

	@Autowired
	RegistryClient registryclnt;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource datasource;

	@Autowired
	RestHighLevelClient restClient;

	@Autowired
	BazaarVoiceCache bzrVoiceCache;

	@Autowired
	ResponseMappingHelper response;

	@Override
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyTypeInput) {
		String journeyType;
		if (StringUtils.isBlank(journeyTypeInput)
				|| (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput))) {
			journeyType = Constants.JOURNEY_TYPE_ACQUISITION;
		} else {
			journeyType = journeyTypeInput;
		}
		String strGroupType = null;
		LogHelper.info(this, "Start  -->  calling  CommercialProductRepository.get");
		CommercialProduct commercialProduct = getCommercialProduct(id);
		LogHelper.info(this, "End  -->  After calling  CommercialProductRepository.get");

		List<DeviceTile> listOfDeviceTile;
		Long memberPriority = null;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						|| commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))) {
			listOfDeviceTile = new ArrayList<>();
			DeviceTile deviceTile = new DeviceTile();
			List<DeviceSummary> listOfDeviceSummary = new ArrayList<>();
			DeviceSummary deviceSummary = null;
			deviceTile.setDeviceId(id);
			Map<String, String> rating = getDeviceReviewRating(new ArrayList<>(Arrays.asList(id)));
			String avarageOverallRating = rating.containsKey(CommonUtility.appendPrefixString(id))
					? rating.get(CommonUtility.appendPrefixString(id)) : "na";
			deviceTile.setRating(avarageOverallRating);
			if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
				strGroupType = Constants.STRING_DEVICE_PAYM;// Constants.STRING_DEVICE;
			} else if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE)) {
				strGroupType = Constants.STRING_DATADEVICE_PAYM;
			}

			LogHelper.info(this, "Start -->  calling  productGroupRepository.getProductGroupsByType");
			List<Group> listOfProductGroup = getProductGroupByType(strGroupType);
			LogHelper.info(this, "End -->  After calling  productGroupRepository.getProductGroupsByType");

			if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
				for (Group productGroup : listOfProductGroup) {
					if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
						for (Member member : productGroup.getMembers()) {
							if (member.getId().equalsIgnoreCase(id)) {
								deviceTile.setGroupName(productGroup.getName());
								deviceTile.setGroupType(productGroup.getGroupType());
								memberPriority = Long.valueOf(member.getPriority());
							}
						}
					}

				}
			}
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList;

			bundleAndHardwareTupleList = getListOfPriceForBundleAndHardware(commercialProduct, journeyType);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;

			// Calling Pricing Api
			if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
						registryclnt, journeyType);
			}

			String leadPlanId = null;
			if (commercialProduct.getLeadPlanId() != null) {
				leadPlanId = commercialProduct.getLeadPlanId();
				LogHelper.info(this, "::::: LeadPlanId " + leadPlanId + ":::::");
			} else if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
				LogHelper.info(this, "::::: LeadPlanId " + leadPlanId + " ::::: ");
			}

			LogHelper.info(this, "Start -->  calling  bundleRepository.get");
			CommercialBundle comBundle = (leadPlanId == null || StringUtils.isEmpty(leadPlanId)) ? null
					: getCommercialBundle(leadPlanId);
			LogHelper.info(this, "End -->  After calling  bundleRepository.get");

			List<BundleAndHardwarePromotions> promotions = null;
			PriceForBundleAndHardware priceForBundleAndHardware = null;
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			if (comBundle != null) {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(comBundle.getId());
				bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
				bundleHardwareTupleList.add(bundleAndHardwareTuple);
			}
			if (!bundleHardwareTupleList.isEmpty()) {
				promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
						journeyType, registryclnt);
			}
			if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
				priceForBundleAndHardware = listOfPriceForBundleAndHardware.get(0);
			}
			if (StringUtils.isNotBlank(journeyType) && Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsSellableRet()
					&& commercialProduct.getProductControl().isIsDisplayableRet()) {
				deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct,
						comBundle, priceForBundleAndHardware, promotions, null, false, null);
			} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsDisplayableAcq()
					&& commercialProduct.getProductControl().isIsSellableAcq()) {
				deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct,
						comBundle, priceForBundleAndHardware, promotions, null, false, null);
			} else {
				LogHelper.error(this, "No data found for given criteria :" + id);
				throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
			}

			listOfDeviceSummary.add(deviceSummary);
			deviceTile.setDeviceSummary(listOfDeviceSummary);
			listOfDeviceTile.add(deviceTile);
		} else {
			LogHelper.error(this, "No data found for given Device Id :" + id);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}

		return listOfDeviceTile;
	}

	/**
	 * Returns list of ProductGroup based on groupType and groupName.
	 * 
	 * @param groupType
	 * @param groupName
	 * @return List<ProductGroup>
	 */
	/*
	 * @Override public List<ProductGroup>
	 * getProductGroupByGroupTypeGroupName(String groupType, String groupName) {
	 * List<ProductGroup> productGroupList = null; List<ProductGroupModel>
	 * listOfProductGroupModel = null; List<ProductGroupModel>
	 * listOfProductGroupModelForGroupName; listOfProductGroupModelForGroupName
	 * = new ArrayList<>(); try { if (requestManager == null) { requestManager =
	 * SolrConnectionProvider.getSolrConnection(); } if (groupType != null &&
	 * groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
	 * 
	 * LogHelper.info(this, "Start -->  calling  getProductGroups_Solr");
	 * listOfProductGroupModel =
	 * requestManager.getProductGroups(Filters.HANDSET); LogHelper.info(this,
	 * "End -->  After calling  getProductGroups_Solr");
	 * 
	 * } else { LogHelper.error(this, Constants.NO_DATA_FOUND_FOR_GROUP_TYPE +
	 * groupType); throw new
	 * ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE); }
	 * 
	 * if (listOfProductGroupModel != null &&
	 * !listOfProductGroupModel.isEmpty()) { productGroupList = new
	 * ArrayList<>(); if (groupName == null) { productGroupList =
	 * DaoUtils.convertGroupProductToProductGroupDetails(listOfProductGroupModel
	 * ); } else { for (ProductGroupModel productGroupModel :
	 * listOfProductGroupModel) { if
	 * (productGroupModel.getName().equals(groupName)) {
	 * listOfProductGroupModelForGroupName.add(productGroupModel);
	 * productGroupList = DaoUtils .convertGroupProductToProductGroupDetails(
	 * listOfProductGroupModelForGroupName); } } } if
	 * (productGroupList.isEmpty()) { LogHelper.error(this,
	 * "No data found for given group name:" + groupName); throw new
	 * ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_NAME); } } else {
	 * LogHelper.error(this, "No data found for given group name:" + groupType);
	 * throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
	 * }
	 * 
	 * } catch (org.apache.solr.common.SolrException solrExcp) {
	 * SolrConnectionProvider.closeSolrConnection(); LogHelper.error(this,
	 * " SolrException: " + solrExcp); throw new
	 * ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION); }
	 * return productGroupList; }
	 */
	/**
	 * Returns leadSkuId based on the priority
	 * 
	 * @param<com.vf.uk.dal.device.datamodel.productgroups.Member> deviceGroupMember
	 * @return leadSkuId
	 */
	public String findLeadSkuBasedOnPriority(
			List<com.vf.uk.dal.device.datamodel.productgroups.Member> deviceGroupMember) {
		String leadSkuId = null;
		Long maxPriority;
		List<Long> listOfPriority = new ArrayList<>();
		if (deviceGroupMember != null && !deviceGroupMember.isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.productgroups.Member member : deviceGroupMember) {
				listOfPriority.add(member.getPriority());
			}
			maxPriority = java.util.Collections.max(listOfPriority);

			for (com.vf.uk.dal.device.datamodel.productgroups.Member member : deviceGroupMember) {
				if (maxPriority.equals(member.getPriority())) {
					leadSkuId = member.getId();
				}
			}
		}

		return leadSkuId;
	}

	/**
	 * Identifies members based on the validation rules.
	 * 
	 * @param listOfDeviceGroupMember
	 * @param journeyType
	 * @return
	 */
	public String getMemeberBasedOnRules(List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			String journeyType) {
		String leadDeviceSkuId = null;
		DaoUtils daoUtils = new DaoUtils();
		List<com.vf.uk.dal.device.entity.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.entity.Member member : listOfSortedMember) {
			if (validateMemeber(member.getId(), journeyType)) {
				leadDeviceSkuId = member.getId();
				break;
			}
		}
		return leadDeviceSkuId;
	}

	/**
	 * validates the member based on the memberId.
	 * 
	 * @param memberId
	 * @param journeyType
	 * @return
	 */
	public Boolean validateMemeber(String memberId, String journeyType) {
		Boolean memberFlag = false;

		LogHelper.info(this, "Start --->  calling  CommercialProductRepository.get");
		CommercialProduct comProduct = getCommercialProduct(memberId);
		LogHelper.info(this, "End --->  After calling  CommercialProductRepository.get");

		Date startDateTime = comProduct.getProductAvailability().getStart();
		Date endDateTime = comProduct.getProductAvailability().getEnd();
		boolean preOrderableFlag = comProduct.getProductControl().isPreOrderable();

		if (StringUtils.isNotBlank(journeyType) && Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
				&& (comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						|| comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))
				&& DaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& (comProduct.getProductControl().isIsSellableRet()
						&& comProduct.getProductControl().isIsDisplayableRet())) {
			memberFlag = true;
		} else if ((comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
				|| comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))
				&& DaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& (comProduct.getProductControl().isIsDisplayableAcq()
						&& comProduct.getProductControl().isIsSellableAcq())) {
			memberFlag = true;
		}

		return memberFlag;

	}

	/**
	 * 
	 * @param memberId
	 * @return
	 */
	/*
	 * public Boolean validateMemeber1(String memberId) { Boolean memberFlag =
	 * false; List<String> listOfProduct = new ArrayList<>();
	 * listOfProduct.add(memberId); Date startDateTime = null; Date endDateTime
	 * = null; List<ProductModel> productModel =
	 * requestManager.getProductModel(listOfProduct); if (productModel != null
	 * && !productModel.isEmpty()) { for (ProductModel productModel2 :
	 * productModel) { if (productModel2.getProductStartDate() != null) { try {
	 * startDateTime = new
	 * SimpleDateFormat("dd/MM/yyyy").parse(productModel2.getProductStartDate())
	 * ; } catch (ParseException e) { LogHelper.error(this, "Parse Exception: "
	 * + e); } } if (productModel2.getProductEndDate() != null) { try {
	 * endDateTime = new
	 * SimpleDateFormat("dd/MM/yyyy").parse(productModel2.getProductEndDate());
	 * } catch (ParseException ex) { LogHelper.error(this, "Parse Exception: " +
	 * ex); } } boolean preOrderableFlag =
	 * Boolean.parseBoolean(productModel2.getPreOrderable());
	 * 
	 * if (productModel2.getProductClass().equalsIgnoreCase(Constants.
	 * STRING_HANDSET) && DaoUtils.dateValidation(startDateTime, endDateTime,
	 * preOrderableFlag) &&
	 * (Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableAcq
	 * ()) &&
	 * Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableAcq()))
	 * ) { memberFlag = true; } } }
	 * 
	 * return memberFlag;
	 * 
	 * }
	 */
	/**
	 * calculation of price
	 * 
	 * @param grossPrice
	 * @return
	 */
	public static Price getPriceFromGross(double grossPrice) {
		float vatPercentage = Float
				.parseFloat(ConfigHelper.getString(Constants.CONFIG_VAT_PERCENTAGE, Constants.DEFAULT_VAT_PERCENTAGE));
		Price price = new Price();
		double vat = (grossPrice / (100 + vatPercentage)) * vatPercentage;
		double net = grossPrice - vat;
		price.setGross(CommonUtility.getDecimalFormat().format(grossPrice));
		price.setNet(CommonUtility.getDecimalFormat().format(net));
		price.setVat(CommonUtility.getDecimalFormat().format(vat));
		return price;
	}

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param strDateFormat
	 * @return
	 */
	public Boolean dateValidationForOffers(String startDateTime, String endDateTime, String strDateFormat) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();

		String currentDateStr = dateFormat.format(currentDate);

		try {
			currentDate = dateFormat.parse(currentDateStr);

		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(this, " ParseException: " + e);
		}

		Date startDate = null;
		Date endDate = null;

		try {
			if (startDateTime != null) {
				startDate = dateFormat.parse(startDateTime);
				LogHelper.info(this, "::::: startDate " + startDate + " :::::");
			}

		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(this, "ParseException: " + e);
		}

		try {
			if (endDateTime != null) {
				endDate = dateFormat.parse(endDateTime);
				LogHelper.info(this, "::::: EndDate " + endDate + " :::::");
			}
		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(this, "ParseException: " + e);
		}

		if (startDate != null && endDate != null && ((currentDate.after(startDate) || currentDate.equals(startDate))
				&& (currentDate.before(endDate) || currentDate.equals(endDate)))) {
			flag = true;
		}
		if (startDate == null && endDate != null && currentDate.before(endDate)) {
			flag = true;
		}
		if (startDate != null && endDate == null && currentDate.after(startDate)) {
			flag = true;
		}
		if (startDate == null && endDate == null) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @return
	 */
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardware(CommercialProduct commercialProduct,
			String journeyType) {

		BundleAndHardwareTuple bundleAndHardwareTuple;
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundles;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		List<CoupleRelation> listOfCoupleRelationForMcs;
		CommercialBundle commercialBundle = null;
		if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())) {
			commercialBundle = getCommercialBundleFromCommercialBundleRepository(commercialProduct.getLeadPlanId());
		}
		boolean sellableCheck = false;
		if (commercialBundle != null) {
			if (Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialBundle.getBundleControl() != null
					&& commercialBundle.getBundleControl().getIsSellableRet()
					&& commercialBundle.getBundleControl().getIsDisplayableRet()
					&& !commercialBundle.getAvailability().getSalesExpired()) {
				sellableCheck = true;
			}

			if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialBundle.getBundleControl() != null
					&& commercialBundle.getBundleControl().getIsSellableAcq()
					&& commercialBundle.getBundleControl().getIsDisplayableAcq()
					&& !commercialBundle.getAvailability().getSalesExpired()) {
				sellableCheck = true;
			}
		}

		if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
				&& commercialProduct.getListOfCompatiblePlanIds().contains(commercialProduct.getLeadPlanId())
				&& sellableCheck) {
			bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
			bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
			bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		} else {
			String gross = null;

			try {

				bundleDetailsForDevice = CommonUtility.getPriceDetailsForCompatibaleBundle(commercialProduct.getId(),
						journeyType, registryclnt);
				listOfBundles = bundleDetailsForDevice.getStandalonePlansList();
				listOfCoupleRelationForMcs = bundleDetailsForDevice.getCouplePlansList();
				listOfBundleHeaderForDevice.addAll(listOfBundles);
				listOfCoupleRelationForMcs.forEach(coupleRelationMcs -> {
					listOfBundleHeaderForDevice.addAll(coupleRelationMcs.getPlanList());

				});
				Iterator<com.vf.uk.dal.utility.entity.BundleHeader> it = listOfBundleHeaderForDevice.iterator();
				while (it.hasNext()) {
					com.vf.uk.dal.utility.entity.BundleHeader bundleheaderForDevice = it.next();
					if (bundleheaderForDevice.getPriceInfo() == null
							|| bundleheaderForDevice.getPriceInfo().getHardwarePrice() == null
							|| (bundleheaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
									.getGross() == null
									&& bundleheaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice()
											.getGross() == null)) {
						it.remove();
					}
				}
				if (listOfBundleHeaderForDevice != null && !listOfBundleHeaderForDevice.isEmpty()) {
					List<com.vf.uk.dal.utility.entity.BundleHeader> listOfOneOffPriceForBundleHeader = getAscendingOrderForOneoffPrice(
							listOfBundleHeaderForDevice);
					if (listOfOneOffPriceForBundleHeader != null && !listOfOneOffPriceForBundleHeader.isEmpty()) {
						if (listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
								.getOneOffDiscountPrice().getGross() != null) {
							gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
									.getOneOffDiscountPrice().getGross();
						} else {
							gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
									.getOneOffPrice().getGross();
						}

						List<com.vf.uk.dal.utility.entity.BundleHeader> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
						for (com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice : listOfOneOffPriceForBundleHeader) {
							if (bundleHeaderForDevice.getPriceInfo() != null
									&& bundleHeaderForDevice.getPriceInfo().getHardwarePrice() != null
									&& (bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
											.getOneOffDiscountPrice() != null
											|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
													.getOneOffPrice() != null)
									&& gross != null) {
								if ((bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
										.getGross() != null
										|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice()
												.getGross() != null)
										&& (gross
												.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo()
														.getHardwarePrice().getOneOffDiscountPrice().getGross())
												|| gross.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo()
														.getHardwarePrice().getOneOffPrice().getGross()))) {
									listOfEqualOneOffPriceForBundleHeader.add(bundleHeaderForDevice);
								}
							}
						}
						List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundelMonthlyPriceForBundleHeader;
						String bundleId = null;
						if (listOfEqualOneOffPriceForBundleHeader != null
								&& !listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
							listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePrice(
									listOfEqualOneOffPriceForBundleHeader);
							if (listOfBundelMonthlyPriceForBundleHeader != null
									&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
								bundleId = listOfBundelMonthlyPriceForBundleHeader.get(0).getSkuId();
							}
						}
						LogHelper.info(this, "Compatible Id:" + bundleId);
						if (bundleId != null && !bundleId.isEmpty()) {
							bundleAndHardwareTuple = new BundleAndHardwareTuple();
							bundleAndHardwareTuple.setBundleId(bundleId);
							bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
							bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
						}
						LogHelper.info(this,
								"List Of Bundle and Hardware Tuple:Inside compatible " + bundleAndHardwareTupleList);
					}
				}
			} catch (Exception e) {
				LogHelper.error(this, "Exception occured when call happen to compatible bundles api: " + e);
			}
			listOfBundleHeaderForDevice.clear();
		}

		return bundleAndHardwareTupleList;

	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return
	 */
	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForOneoffPrice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceList());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceList implements Comparator<com.vf.uk.dal.utility.entity.BundleHeader> {

		@Override
		public int compare(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList,
				com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
					&& bundleHeaderList.getPriceInfo().getHardwarePrice() != null
					&& bundleHeaderList1.getPriceInfo().getHardwarePrice() != null) {
				if (bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() != null) {
					gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
				}
				if (bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() != null) {
					gross1 = bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
				}

				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else if (Double.compare(Double.parseDouble(gross), Double.parseDouble(gross1)) == 0) {
					return 0;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return
	 */
	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundlePrice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedBundlePriceList());

		return bundleHeaderForDeviceSorted;
	}

	class SortedBundlePriceList implements Comparator<com.vf.uk.dal.utility.entity.BundleHeader> {

		@Override
		public int compare(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList,
				com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
					&& bundleHeaderList.getPriceInfo().getBundlePrice() != null
					&& bundleHeaderList1.getPriceInfo().getBundlePrice() != null) {
				if (bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
								.getGross() != null) {
					gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
				}
				if (bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
								.getGross() != null) {
					gross1 = bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
				}
				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * need to be checked
	 * 
	 * @param prioritySorted
	 * @return
	 */
	public List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> getAscendingOrderForMerchndisingPriority(
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> prioritySorted) {
		Collections.sort(prioritySorted, new SortedPriorityList());

		return prioritySorted;
	}

	class SortedPriorityList
			implements Comparator<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> {

		@Override
		public int compare(
				com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion listOfMerchandising,
				com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion listOfMerchandising2) {

			if (listOfMerchandising.getPriority() != null && listOfMerchandising2.getPriority() != null) {
				if (listOfMerchandising.getPriority() < listOfMerchandising2.getPriority()) {
					return -1;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * 
	 * @param variantsList
	 * @return
	 */
	public List<com.vf.uk.dal.device.entity.Member> getListOfMembers(List<String> variantsList) {
		com.vf.uk.dal.device.entity.Member member;
		List<com.vf.uk.dal.device.entity.Member> listOfMember = new ArrayList<>();
		for (String variants : variantsList) {
			member = new com.vf.uk.dal.device.entity.Member();
			String[] variantIdPriority = variants.split("\\|");
			if (variantIdPriority != null && variantIdPriority.length == 2) {
				member.setId(variantIdPriority[0]);
				member.setPriority(variantIdPriority[1]);
				listOfMember.add(member);
			}
		}
		return listOfMember;
	}

	/**
	 * 
	 * @param insurances
	 * @return
	 */
	public Insurances getFormattedPriceForGetCompatibleInsurances(Insurances insurances) {

		if (insurances.getInsuranceList() != null && !insurances.getInsuranceList().isEmpty()) {
			List<Insurance> insuranceList = insurances.getInsuranceList();
			for (Insurance insurance : insuranceList) {
				if (insurance.getPrice() != null) {
					Price price = insurance.getPrice();
					if (price.getNet() != null && StringUtils.isNotBlank(price.getNet())) {
						price.setNet(FormatPrice(price.getNet()));
					}
					if (price.getVat() != null && StringUtils.isNotBlank(price.getVat())) {
						price.setVat(FormatPrice(price.getVat()));
					}
					if (price.getGross() != null && StringUtils.isNotBlank(price.getGross())) {
						price.setGross(FormatPrice(price.getGross()));
					}
					insurance.setPrice(price);
				}
			}
		}
		return insurances;
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	public String FormatPrice(String price) {
		if (price.contains(".")) {
			String[] decimalSplit = price.split("\\.");
			String beforeDecimal = decimalSplit[0];
			String afterDecimal = decimalSplit[1];

			if (afterDecimal.length() == 1 && "0".equals(afterDecimal)) {
				return beforeDecimal;
			} else if (afterDecimal.length() == 1) {
				afterDecimal += "0";
				return beforeDecimal + "." + afterDecimal;
			}
		}
		return price;
	}

	/**
	 * 
	 * @param deviceId
	 * @param sortCriteria
	 * @return
	 */
	@Override
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria) {
		BundleDetails bundleDetails = null;
		try {
			LogHelper.info(this, "Getting Compatible bundle details by calling Compatible Plan List API");
			bundleDetails = CommonUtility.getBundleDetailsFromComplansListingAPI(deviceId, sortCriteria, registryclnt);
		} catch (ApplicationException e) {
			LogHelper.error(this, "No Compatible bundle Found By Given Bundle Id " + e);
			bundleDetails = null;
		}
		return bundleDetails;
	}

	/**
	 * Returns Device review details
	 * 
	 * @param deviceId
	 * @return
	 * @throws org.json.simple.parser.ParseException
	 */
	@Override
	public String getDeviceReviewDetails(String deviceId) {
		String jsonObject;
		LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
		jsonObject = bzrVoiceCache.getBazaarVoiceReviews(deviceId);
		return jsonObject;
	}

	@Override
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds) {

		try {
			LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
			List<BazaarVoice> response = new ArrayList<>();
			for (String skuId : listMemberIds) {
				response.add(getBazaarVoice(skuId));
			}
			LogHelper.info(this, "End --> After calling  BazaarReviewRepository.get");
			return response;
		} catch (Exception e) {
			LogHelper.error(this, "Bazar Voice Exception: " + e);
			throw new ApplicationException(ExceptionMessages.BAZARVOICE_SERVICE_EXCEPTION);
		}
	}

	/**
	 * Returns Reviews and Rating for devices
	 * 
	 * @param listMemberIds
	 * @return
	 */
	public Map<String, String> getDeviceReviewRating(List<String> listMemberIds) {

		List<BazaarVoice> bazarVoiceResponse = getReviewRatingList(listMemberIds);
		HashMap<String, String> bvReviewAndRateMap = new HashMap<>();
		try {
			for (BazaarVoice bazaarVoice : bazarVoiceResponse) {
				if (bazaarVoice != null) {
					if (!bazaarVoice.getJsonsource().isEmpty()) {
						org.json.JSONObject jSONObject = new org.json.JSONObject(bazaarVoice.getJsonsource());
						if (!jSONObject.get("Includes").toString().equals("{}")) {
							org.json.JSONObject includes = jSONObject.getJSONObject("Includes");
							org.json.JSONObject products = includes.getJSONObject("Products");
							Iterator level = products.keys();
							while (level.hasNext()) {
								String key = (String) level.next();
								org.json.JSONObject skuId = products.getJSONObject(key);
								org.json.JSONObject reviewStatistics = (null != skuId)
										? skuId.getJSONObject("ReviewStatistics") : null;
								Double averageOverallRating = (null != reviewStatistics)
										? (Double) reviewStatistics.get("AverageOverallRating") : null;
								if (!bvReviewAndRateMap.keySet().contains(key)) {
									String overallRating = (null != averageOverallRating)
											? averageOverallRating.toString() : "na";
									bvReviewAndRateMap.put(key, overallRating);
								}
							}
						} else {
							bvReviewAndRateMap.put(bazaarVoice.getId(), "na");
						}
					} else {
						bvReviewAndRateMap.put(bazaarVoice.getId(), "na");
					}
				}
			}
		} catch (Exception e) {
			LogHelper.error(this, "Failed to get device review ratings, Exception: " + e);
			throw new ApplicationException(ExceptionMessages.BAZAARVOICE_RESPONSE_EXCEPTION);
		}
		return bvReviewAndRateMap;
	}

	@Override
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware;
		LogHelper.info(this, "Get the price details for Bundle and Hardware list from Pricing API");
		listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
				registryclnt, journeyType);
		return listOfPriceForBundleAndHardware;
	}

	@Override
	public CacheDeviceTileResponse insertCacheDeviceToDb() {
		jdbcTemplate.setDataSource(datasource);
		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		String jobId = UUID.randomUUID().toString();
		CacheDeviceTileResponse cacheDeviceTileResponse = new CacheDeviceTileResponse();
		try {

			String jobStatus = "INPROGRESS";

			cacheDeviceTileResponse.setJobId(jobId);
			cacheDeviceTileResponse.setJobStatus(jobStatus);

			conn.setAutoCommit(false);
			String query = "INSERT INTO DALMS_CACHE_SERVICES (JOB_ID, JOB_NAME, JOB_USER_ID, JOB_START, JOB_STATUS, JOB_LAST_UPDATED) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			Object[] params = new Object[] { jobId, "CacheDevice", null, new Timestamp(new Date().getTime()), jobStatus,
					new Timestamp(new Date().getTime()) };
			jdbcTemplate.update(query, params);
			LogHelper.info(this, jobId + " inserted in DALMS_CACHE_SERVICES_TABLE");
			conn.commit();

		} catch (DataAccessException | SQLException e) {
			LogHelper.error(this, jobId + "==> " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LogHelper.error(this, jobId + "Exception occurred while closing connection ==> " + e);
			}
		}

		return cacheDeviceTileResponse;
	}

	@Override
	public void updateCacheDeviceToDb(String jobId, String jobStatus) {
		jdbcTemplate.setDataSource(datasource);
		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());

		try {
			conn.setAutoCommit(false);
			String query = "UPDATE DALMS_CACHE_SERVICES SET JOB_STATUS = ?, JOB_END = ?, JOB_LAST_UPDATED = ? WHERE JOB_ID = ?";
			Object[] params = new Object[] { jobStatus, new Timestamp(new Date().getTime()),
					new Timestamp(new Date().getTime()), jobId };
			jdbcTemplate.update(query, params);

			LogHelper.info(this, jobId + " updated with status " + jobStatus + " in DALMS_CACHE_SERVICES_TABLE");
			conn.commit();
		} catch (DataAccessException | SQLException e) {
			LogHelper.error(this, jobId + "==> " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LogHelper.error(this, jobId + "Exception occurred while closing connection ==> " + e);
			}
		}

	}

	@Override
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) throws ApplicationException {

		CacheDeviceTileResponse response = new CacheDeviceTileResponse();
		String jobStatus = null;
		try {
			jdbcTemplate.setDataSource(datasource);
			String query = "SELECT JOB_STATUS FROM DALMS_CACHE_SERVICES WHERE JOB_ID = '" + jobId + "'";
			jobStatus = jdbcTemplate.queryForObject(query, String.class);
			if (StringUtils.isEmpty(jobStatus) || StringUtils.isBlank(jobStatus)) {
				throw new ApplicationException(ExceptionMessages.INVALID_JOB_ID);
			} else {
				response.setJobId(jobId);
				response.setJobStatus(jobStatus);
			}

		} catch (Exception exception) {
			LogHelper.error(this, jobId + "==>" + exception);
			throw new ApplicationException(ExceptionMessages.INVALID_JOB_ID);
		}
		return response;
	}
	/**
	 * @author aditya.oli This method checks whether Bazaar Review Repository
	 *         has been initialized or not. If yes, it gets the Bazaar Voice
	 *         from the repository. Else it will initialize the Repository and
	 *         then fetch the Bazaar Voice for the service.
	 * @param String
	 *            skuId
	 * @return BazaarVoice
	 */
	@Override
	public BazaarVoice getBazaarVoice(String skuId) {
		BazaarVoice bazaarVoice = new BazaarVoice();
		bazaarVoice.setSkuId(skuId);
		bazaarVoice.setJsonsource(getDeviceReviewDetails(CommonUtility.appendPrefixString(skuId)));
		return bazaarVoice;
	}


	/**
	 * @author aditya.oli This method fetches and returns the Commercial Bundle
	 *         from coherence's CommercialBundleRepository by taking the
	 *         bundle's Id as a parameter.
	 * @param bundleId
	 * @return CommercialBundle
	 */
	public CommercialBundle getCommercialBundleFromCommercialBundleRepository(String bundleId) {
		return getCommercialBundle(bundleId);
	}

	@Override
	public Map<String, MerchandisingPromotion> getMerchandisingPromotionsEntityFromRepo(List<String> promotionAsTags) {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> listOfMerchandisingPromotions;
		Map<String, MerchandisingPromotion> promotions = new HashMap<>();
		listOfMerchandisingPromotions = getMerchandising(promotionAsTags);
		if (listOfMerchandisingPromotions != null && !listOfMerchandisingPromotions.isEmpty()) {
			listOfMerchandisingPromotions.forEach(solrModel -> {
				MerchandisingPromotion promotion = new MerchandisingPromotion();
				promotion.setTag(solrModel.getTag());
				promotion.setDescription(solrModel.getDescription());
				String footNoteKey = solrModel.getFootNoteKey();
				if (StringUtils.isNotBlank(footNoteKey)) {
					promotion.setFootNotes(Arrays.asList(footNoteKey.split(",")));
				}

				promotion.setLabel(solrModel.getLabel());
				promotion.setPriceEstablishedLabel(solrModel.getPriceEstablishedLabel());
				String packagesList = solrModel.getCondition().getPackageType();
				if (StringUtils.isNotBlank(packagesList)) {
					promotion.setPackageType(Arrays.asList(packagesList.split(",")));
				}
				if (solrModel.getPriority() != null) {
					promotion.setPriority(solrModel.getPriority().intValue());
				}
				promotion.setMpType(solrModel.getType());
				promotion.setPromotionMedia(solrModel.getPromotionMedia());
				promotions.put(solrModel.getTag(), promotion);
			});
		}
		return promotions;
	}

	@Override
	public CompletableFuture<List<PriceForBundleAndHardware>> getPriceForBundleAndHardwareListFromTupleListAsync(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType,
			String version) {
		LogHelper.info(this, "Start -->  calling  getPriceForBundleAndHardwareListFromTupleList_PriceAPI");

		return CompletableFuture.supplyAsync(new Supplier<List<PriceForBundleAndHardware>>() {
			@Override
			public List<PriceForBundleAndHardware> get() {
				Constants.CATALOG_VERSION.set(version);
				return CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode, registryclnt, journeyType);
			}
		});

	}

	@Override
	public CompletableFuture<List<com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType, String version) {
		LogHelper.info(this, "Start -->  calling  getBundleAndHardwarePromotionsListFromBundleListAsync");

		return CompletableFuture
				.supplyAsync(new Supplier<List<com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions>>() {
					@Override
					public List<com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions> get() {
						Constants.CATALOG_VERSION.set(version);
						return CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
								journeyType, registryclnt);
					}
				});

	}

	@Override
	public SearchResponse getResponseFromDataSource(SearchRequest searchRequest) {
		LogHelper.info(this, "Start call time Elasticsearch" + System.currentTimeMillis());
		SearchResponse response = null;
		try {
			response = restClient.search(searchRequest,
					new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
		} catch (Exception e) {
			if(StringUtils.containsIgnoreCase(e.getMessage(), ExceptionMessages.Index_NOT_FOUND_EXCEPTION)){
				LogHelper.error(this, ExceptionMessages.Index_NOT_FOUND_EXCEPTION);
				throw new ApplicationException(ExceptionMessages.Index_NOT_FOUND_EXCEPTION);
			}
			LogHelper.error(this, "::::::Exception occured while querieng bundle models from ES " + e);
		}
		LogHelper.info(this, "End call time Elasticsearch" + System.currentTimeMillis());
		return response;
	}

	/**
	 * 
	 * @param promotionAsTags
	 * @return
	 */
	public List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> getMerchandising(
			List<String> promotionAsTags) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMerchandisingByTagName(promotionAsTags);
		SearchResponse bundleResponse = getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfMerchandisingPromotionFromJson(bundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param deviceId
	 * @return
	 */
	public CommercialProduct getCommercialProduct(String deviceId) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForCommercialProductAndCommercialBundle(deviceId, Constants.STRING_PRODUCT);
		SearchResponse commercialProduct = getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Product object response");
		return response.getCommercialProduct(commercialProduct);
	}

	/**
	 * @author manoj.bera
	 * @param groupType
	 * @return
	 */
	public List<Group> getProductGroupByType(String groupType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroup(groupType);
		SearchResponse groupResponse = getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfGroupFromJson(groupResponse);
	}

	/**
	 * @author manoj.bera
	 * @param bundleId
	 * @return
	 */
	public CommercialBundle getCommercialBundle(String bundleId) {
		SearchRequest queryContextMapForLeadPlanId = DeviceQueryBuilderHelper
				.searchQueryForCommercialProductAndCommercialBundle(bundleId, Constants.STRING_BUNDLE);
		SearchResponse commercialBundleResponse = getResponseFromDataSource(queryContextMapForLeadPlanId);
		LogHelper.info(this, "converting elasticsearch response into Commercial Bundle object response");
		return response.getCommercialBundle(commercialBundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param id
	 * @param data
	 */
	@Override
	public void getUpdateElasticSearch(String id, String data) {
		try {
			UpdateRequest updateRequest = new UpdateRequest(Constants.CATALOG_VERSION.get(), "models", id);
			updateRequest.doc(data, XContentType.JSON);
			restClient.update(updateRequest,
					new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));

			UpdateRequest updateRequestForNull = new UpdateRequest(Constants.CATALOG_VERSION.get(), "models", id)
					.doc(org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder().startObject()
							.endObject());
			restClient.update(updateRequestForNull);
		} catch (IOException e) {
			LogHelper.error(this, "::::::Exception From es ::::::" + e);
		}
	}

	/**
	 * @author manoj.bera
	 * @param id
	 * @param data
	 */
	@Override
	public void getIndexElasticSearch(String id, String data) {
		try {
			IndexRequest updateRequestForILSPromo = Requests.indexRequest(Constants.CATALOG_VERSION.get());
			updateRequestForILSPromo.type("models");
			updateRequestForILSPromo.id(id);
			updateRequestForILSPromo.source(data, XContentType.JSON);
			restClient.index(updateRequestForILSPromo,
					new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
		} catch (IOException e) {
			LogHelper.error(this, "::::::Exception From es ::::::" + e);
		}
	}
}
