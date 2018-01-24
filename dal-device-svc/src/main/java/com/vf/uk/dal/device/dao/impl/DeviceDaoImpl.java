package com.vf.uk.dal.device.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
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
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.utils.CoherenceConnectionProvider;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.SolrConnectionProvider;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.CoupleRelation;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vodafone.business.service.RequestManager;
import com.vodafone.common.Filters;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.dal.domain.bazaarvoice.BazaarVoice;
import com.vodafone.dal.domain.repository.BazaarReviewRepository;
import com.vodafone.dal.domain.repository.CommercialBundleRepository;
import com.vodafone.dal.domain.repository.CommercialProductRepository;
import com.vodafone.dal.domain.repository.MerchandisingPromotionRepository;
import com.vodafone.dal.domain.repository.ProductGroupRepository;
import com.vodafone.dal.domain.repository.StockAvailabilityRepository;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.productGroups.pojo.Group;
import com.vodafone.productGroups.pojo.Member;
import com.vodafone.solrmodels.BundleModel;
import com.vodafone.solrmodels.MerchandisingPromotionModel;
import com.vodafone.solrmodels.OfferAppliedPriceModel;
import com.vodafone.solrmodels.ProductGroupFacetModel;
import com.vodafone.solrmodels.ProductGroupModel;
import com.vodafone.solrmodels.ProductModel;
import com.vodafone.stockAvailability.pojo.StockAvailability;

import uk.co.vodafone.business.IncrementalIndexManager;
import uk.co.vodafone.customexceptions.SolrDeviceHotFixException;

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

	private RequestManager requestManager = null;
	private CommercialProductRepository commercialProductRepository = null;
	private CommercialBundleRepository commercialBundleRepository = null;
	private ProductGroupRepository productGroupRepository = null;
	private MerchandisingPromotionRepository merchandisingPromotionRepository = null;
	private BazaarReviewRepository bazaarReviewRepository = null;

		
	
	@Override
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType) {
		String strGroupType = null;

		LogHelper.info(this, "Start  -->  calling  CommercialProductRepository.get");
		if (null==commercialProductRepository) {
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}
		CommercialProduct commercialProduct = commercialProductRepository.get(id);
		LogHelper.info(this, "End  -->  After calling  CommercialProductRepository.get");

		List<DeviceTile> listOfDeviceTile;
		Long memberPriority = null;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct() && (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						|| commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))) {
			listOfDeviceTile = new ArrayList<>();
			DeviceTile deviceTile = new DeviceTile();
			List<DeviceSummary> listOfDeviceSummary = new ArrayList<>();
			DeviceSummary deviceSummary = null;
			deviceTile.setDeviceId(id);
			String avarageOverallRating = getDeviceReviewRating(new ArrayList<>(Arrays.asList(id)))
					.get(CommonUtility.appendPrefixString(id));
			deviceTile.setRating(avarageOverallRating);
			if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
				strGroupType = Constants.STRING_DEVICE_PAYM;// Constants.STRING_DEVICE;
			} else if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE)) {
				strGroupType = Constants.STRING_DATADEVICE_PAYM;
			}

			LogHelper.info(this, "Start -->  calling  productGroupRepository.getProductGroupsByType");
			if (productGroupRepository == null) {
				productGroupRepository = CoherenceConnectionProvider.getProductGroupRepoRepository();
			}
			List<Group> listOfProductGroup = productGroupRepository.getProductGroupsByType(strGroupType);
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

			bundleAndHardwareTupleList = getListOfPriceForBundleAndHardware(commercialProduct);
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
			if (commercialBundleRepository == null) {
				commercialBundleRepository = CoherenceConnectionProvider.getCommercialBundleRepoConnection();
			}
			CommercialBundle comBundle = commercialBundleRepository.get(leadPlanId);
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
	@Override
	public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType, String groupName) {
		List<ProductGroup> productGroupList = null;
		List<ProductGroupModel> listOfProductGroupModel = null;
		List<ProductGroupModel> listOfProductGroupModelForGroupName;
		listOfProductGroupModelForGroupName = new ArrayList<>();
		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			if (groupType != null && groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {

				LogHelper.info(this, "Start -->  calling  getProductGroups_Solr");
				listOfProductGroupModel = requestManager.getProductGroups(Filters.HANDSET);
				LogHelper.info(this, "End -->  After calling  getProductGroups_Solr");

			} else {
				LogHelper.error(this, Constants.NO_DATA_FOUND_FOR_GROUP_TYPE + groupType);
				throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
			}

			if (listOfProductGroupModel != null && !listOfProductGroupModel.isEmpty()) {
				productGroupList = new ArrayList<>();
				if (groupName == null) {
					productGroupList = DaoUtils.convertGroupProductToProductGroupDetails(listOfProductGroupModel);
				} else {
					for (ProductGroupModel productGroupModel : listOfProductGroupModel) {
						if (productGroupModel.getName().equals(groupName)) {
							listOfProductGroupModelForGroupName.add(productGroupModel);
							productGroupList = DaoUtils
									.convertGroupProductToProductGroupDetails(listOfProductGroupModelForGroupName);
						}
					}
				}
				if (productGroupList.isEmpty()) {
					LogHelper.error(this, "No data found for given group name:" + groupName);
					throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_NAME);
				}
			} else {
				LogHelper.error(this, "No data found for given group name:" + groupType);
				throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
			}

		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, " SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}
		return productGroupList;
	}
	

	/**
	 * Returns leadSkuId based on the priority
	 * 
	 * @param deviceGroupMember
	 * @return leadSkuId
	 */
	public String findLeadSkuBasedOnPriority(List<com.vodafone.productGroups.pojo.Member> deviceGroupMember) {
		String leadSkuId = null;
		Long maxPriority;
		List<Long> listOfPriority = new ArrayList<>();
		if (deviceGroupMember != null && !deviceGroupMember.isEmpty()) {
			for (com.vodafone.productGroups.pojo.Member member : deviceGroupMember) {
				listOfPriority.add(member.getPriority());
			}
			maxPriority = java.util.Collections.max(listOfPriority);

			for (com.vodafone.productGroups.pojo.Member member : deviceGroupMember) {
				if (maxPriority == member.getPriority()) {
					leadSkuId = member.getId();
				}
			}
		}

		return leadSkuId;
	}

	/**
	 * Identifies members based on the validation rules.
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
	 * @param memberId
	 * @param journeyType
	 * @return
	 */
	public Boolean validateMemeber(String memberId, String journeyType) {
		Boolean memberFlag = false;

		LogHelper.info(this, "Start --->  calling  CommercialProductRepository.get");
		if (commercialProductRepository == null) {
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}
		CommercialProduct comProduct = commercialProductRepository.get(memberId);
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
	public Boolean validateMemeber1(String memberId) {
		Boolean memberFlag = false;
		List<String> listOfProduct = new ArrayList<>();
		listOfProduct.add(memberId);
		Date startDateTime = null;
		Date endDateTime = null;
		List<ProductModel> productModel = requestManager.getProductModel(listOfProduct);
		if (productModel != null && !productModel.isEmpty()) {
			for (ProductModel productModel2 : productModel) {
				if (productModel2.getProductStartDate() != null) {
					try {
						startDateTime = new SimpleDateFormat("dd/MM/yyyy").parse(productModel2.getProductStartDate());
					} catch (ParseException e) {
						LogHelper.error(this, "Parse Exception: " + e);
					}
				}
				if (productModel2.getProductEndDate() != null) {
					try {
						endDateTime = new SimpleDateFormat("dd/MM/yyyy").parse(productModel2.getProductEndDate());
					} catch (ParseException ex) {
						LogHelper.error(this, "Parse Exception: " + ex);
					}
				}
				boolean preOrderableFlag = Boolean.parseBoolean(productModel2.getPreOrderable());

				if (productModel2.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						&& DaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
						&& (Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableAcq())
								&& Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableAcq()))) {
					memberFlag = true;
				}
			}
		}

		return memberFlag;

	}

	/**
	 * calculation of price
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
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardware(CommercialProduct commercialProduct) {

		BundleAndHardwareTuple bundleAndHardwareTuple;
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundles;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		List<CoupleRelation> listOfCoupleRelationForMcs;

		if (commercialProduct.getLeadPlanId() != null) {
			bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
			bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
			bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		} else {
			String gross = null;

			try {

				bundleDetailsForDevice = CommonUtility.getPriceDetailsForCompatibaleBundle(commercialProduct.getId(),
						registryclnt);
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
 * 
 * @param prioritySorted
 * @return
 */
	public List<com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion> getAscendingOrderForMerchndisingPriority(
			List<com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion> prioritySorted) {
		Collections.sort(prioritySorted, new SortedPriorityList());

		return prioritySorted;
	}

	class SortedPriorityList implements Comparator<com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion> {

		@Override
		public int compare(com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion listOfMerchandising,
				com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion listOfMerchandising2) {

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
		String jsonObject = null;
		LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
		BazaarReviewRepository repo = new BazaarReviewRepository();
		BazaarVoice response = repo.get(deviceId);
		LogHelper.info(this, "End -->  calling  BazaarReviewRepository.get");
		if (response != null) {
			jsonObject = response.getJsonsource();
		}
		return jsonObject;
	}

	
	@Override
	public com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotionByPromotionName(
			String promotionName) {

		LogHelper.info(this, "Start -->  calling  merchandisingPromotionRepository.get");
		if (merchandisingPromotionRepository == null) {
			merchandisingPromotionRepository = CoherenceConnectionProvider.getMerchandisingRepoConnection();
		}
		com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion merchandisingPromotion = merchandisingPromotionRepository
				.get(promotionName);
		LogHelper.info(this, "End -->  After calling  merchandisingPromotionRepository.get");
		return merchandisingPromotion;
	}

	@Override
	public List<Group> getProductGroupsByType(String groupType) {
		try {
			if (productGroupRepository == null) {
				productGroupRepository = CoherenceConnectionProvider.getProductGroupRepoRepository();
			}
			LogHelper.info(this, "Start --> Calling  productRepository.getByName");
			List<Group> groupList = productGroupRepository.getProductGroupsByType(groupType);
			LogHelper.info(this, "End --> End -->  After calling  productRepository.getProductByClass");
			return groupList;

		} catch (Exception e) {
			LogHelper.error(this, "Coherence Issue " + e);
			throw new ApplicationException(ExceptionMessages.INVALID_COHERENCE_DATA);
		}
	}

	/**
	 * 
	 */
	@Override
	public CommercialProduct getCommercialProductRepositoryByLeadMemberId(String leadMemberId) {
		try {
			LogHelper.info(this, "Start -->  calling  CommercialProductRepository.get");
			if (commercialProductRepository == null) {
				commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
			}
			CommercialProduct commercialProduct = commercialProductRepository.get(leadMemberId);
			LogHelper.info(this, "End -->  After calling  CommercialProductRepository.get");

			return commercialProduct;

		} catch (NullPointerException np) {
			LogHelper.error(this, " Invalid Data Coming From Coherence " + np);
			LogHelper.info(this, "Invalid MemberId " + leadMemberId);
			throw new ApplicationException(ExceptionMessages.INVALID_COHERENCE_DATA);
		}
	}

	@Override
	public List<OfferAppliedPriceModel> getBundleAndHardwarePriceFromSolr(List<String> deviceIds, String offerCode,String journeyType) {

		LogHelper.info(this, "Start --> Calling  getOfferAppliedPrices_Solr");
		List<OfferAppliedPriceModel> list = requestManager.getOfferAppliedPrices(deviceIds, offerCode,journeyType);
		LogHelper.info(this, "End --> End -->  After calling  getOfferAppliedPrices_Solr");

		return list;
	}

	@Override
	public Collection<CommercialProduct> getListCommercialProductRepositoryByLeadMemberId(List<String> leadMemberId) {
		Collection<CommercialProduct> commercialProductList = null;
		try {

			LogHelper.info(this, "Start -->  calling  productRepository.getAll");
			if (commercialProductRepository == null) {
				commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
			}
			commercialProductList = new ArrayList<>(commercialProductRepository.getAll(leadMemberId));
			LogHelper.info(this, "End -->  After calling  productRepository.getAll");

		} catch (Exception np) {
			LogHelper.error(this, "Invalid Data Coming From Coherence " + np);
			LogHelper.info(this, "Invalid MemberId " + leadMemberId);
			return commercialProductList;
		}
		return commercialProductList;
	}

	/**
	 * 
	 */
	@Override
	public StockAvailability getStockAvailabilityByMemberId(String memberId) {
		StockAvailabilityRepository stockrepository = new StockAvailabilityRepository();
		return stockrepository.get(memberId);
	}

	/**
	 * @author manoj.bera
	 */
	@Override
	public void movePreCalcDataToSolr(List<DevicePreCalculatedData> preCalcPlanList) {
		try {
			String zookeeperHost = ConfigHelper.getString(Constants.ZOOKEEPER_HOST_STRING,
					Constants.DEFAULT_ZOOKEEPER_HOST_STRING);
			String aliasName = ConfigHelper.getString(Constants.ALIAS_NAME, Constants.DEFAULT_ALIAS_NAME);
			String zookeeperUserName = ConfigHelper.getString(Constants.ZOOKEEPER_USERNAME,
					Constants.DEFAULT_ZOOKEEPER_USERNAME);
			String zookeeperpass = ConfigHelper.getString(Constants.ZOOKEEPER_PASS, Constants.DEFAULT_ZOOKEEPER_PASS);
			List<com.vodafone.pojos.fromjson.device.DevicePreCalculatedData> deviceListObjectList = DaoUtils
					.convertDevicePreCalDataToSolrData(preCalcPlanList);
			IncrementalIndexManager manager = new IncrementalIndexManager();
			manager.deviceHotfix(zookeeperHost, aliasName, zookeeperUserName, zookeeperpass, deviceListObjectList);
		} catch (SolrDeviceHotFixException sdhfe) {
			LogHelper.info(this, "Not Able To Load Data In Solr : " + sdhfe);
			throw new ApplicationException(ExceptionMessages.SOLR_INDEXING_ERROR);
		} catch (Exception ex) {
			LogHelper.info(this, "Not Able To Load Data In Solr : " + ex);
			throw new ApplicationException(ExceptionMessages.SOLR_INDEXING_ERROR);
		}
	}

	/**
	 * 
	 */
	@Override
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey, String filterCriteria, String sortBy,
			String sortOption, Integer pageNumber, Integer pageSize, String journeyType) {
		ProductGroupFacetModel productGroupFacetModel = null;
		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			
			LogHelper.info(this, "Start --> Calling  getProductGroupsWithFacets_Solr");
			productGroupFacetModel = requestManager.getProductGroupsWithFacetsByJourneyType
					(filterKey, filterCriteria, sortBy,sortOption, pageNumber, pageSize,Arrays.asList(journeyType));
			LogHelper.info(this, "End --> After calling  getProductGroupsWithFacets_Solr");
			// }
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "  SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}catch (Exception exp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "Exception: " + exp);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_SOLR);
		}
		return productGroupFacetModel;
	}

	/**
	 * 
	 */
	@Override
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey,String journeyType) {
		ProductGroupFacetModel productGroupFacetModel = null;
		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			LogHelper.info(this, "Start --> Calling  getProductGroupsWithFacets_Solr");
			productGroupFacetModel = requestManager.getProductGroupFacetModelByJourneyType(filterKey, Arrays.asList(journeyType));
			LogHelper.info(this, "End --> After Calling  getProductGroupsWithFacets_Solr");
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "SolrException : " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}catch (Exception exp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "Exception: " + exp);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_SOLR);
		}
		return productGroupFacetModel;
	}

	/**
	 * 
	 */
	@Override
	public List<ProductModel> getProductModel(List<String> listOfProducts) {
		List<ProductModel> productModel = null;
		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			LogHelper.info(this, "Start --> Calling  getProductModel_Solr");
			productModel = requestManager.getProductModel(listOfProducts);
			LogHelper.info(this, "End --> Calling  getProductModel_Solr");
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, " SolrException : " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}
		return productModel;
	}

	/**
	 * 
	 * @param listOfLeadPlanId
	 * @return
	 */
	@Override
	public List<BundleModel> getBundleDetails(List<String> listOfLeadPlanId) {
		List<BundleModel> bundleModel = null;
		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			bundleModel = requestManager.getBundleDetails(listOfLeadPlanId);
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, " SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}
		return bundleModel;
	}

	@Override
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds) {

		try {
			LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
			BazaarReviewRepository repo = new BazaarReviewRepository();
			List<BazaarVoice> response = new ArrayList<>();
			for (String skuId : listMemberIds) {
				response.add(repo.get(CommonUtility.appendPrefixString(skuId)));
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

		List<BazaarVoice> response = getReviewRatingList(listMemberIds);
		HashMap<String, String> bvReviewAndRateMap = new HashMap<>();
		try {
			for (BazaarVoice bazaarVoice : response) {
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
	public CommercialProduct getCommercialProductByProductId(String productId) {

		LogHelper.info(this, "Start -->  calling  CommercialProductRepository.get");
		if (commercialProductRepository == null) {
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}
		CommercialProduct commercialProduct = commercialProductRepository.get(productId);
		LogHelper.info(this, "End -->  After calling  CommercialProductRepository.get");
		return commercialProduct;
	}

	@Override
	public CommercialBundle getCommercialBundleByBundleId(String bundleId) {

		LogHelper.info(this, "Start -->  calling  bundleRepository.get");
		if (commercialBundleRepository == null) {
			commercialBundleRepository = CoherenceConnectionProvider.getCommercialBundleRepoConnection();
		}
		CommercialBundle commercialBundle = commercialBundleRepository.get(bundleId);
		LogHelper.info(this, "End -->  After calling  bundleRepository.get");
		return commercialBundle;
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
			String query="SELECT JOB_STATUS FROM DALMS_CACHE_SERVICES WHERE JOB_ID = '" + jobId + "'";
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

	@Override
	public Collection<CommercialBundle> getListCommercialBundleRepositoryByCompatiblePlanList(List<String> planIdList) {
		Collection<CommercialBundle> commercialBundleList = null;
		try {
			if (commercialBundleRepository == null) {
				commercialBundleRepository = CoherenceConnectionProvider.getCommercialBundleRepoConnection();
			}
			LogHelper.info(this, "Start --> Calling BundleRepository.getAll");
			commercialBundleList = commercialBundleRepository.getAll(planIdList);
			LogHelper.info(this, "End --> End -->  After calling  BundleRepository.getAll");

		} catch (Exception e) {
			LogHelper.error(this, "==>" + e);
			return commercialBundleList;
		}
		return commercialBundleList;
	}

	@Override
	public Group getGroupByProdGroupName(String groupName, String groupType) {

		Group group;
		try {
			LogHelper.info(this, "Start -->  calling  getCommercialProductByDeviceId.get");
			if (productGroupRepository == null) {
				productGroupRepository = new ProductGroupRepository();
			}
			group = productGroupRepository.getProductGroup(groupName, groupType);
			LogHelper.info(this, "End -->  After calling  getCommercialProductByDeviceId.get");
		} catch (NullPointerException np) {
			LogHelper.error(this, "Invalid Data Coming From Coherence " + np);
			throw new ApplicationException(ExceptionMessages.INVALID_COHERENCE_DATA);
		}
		return group;
	}

	@Override
	public List<CommercialProduct> getCommercialProductsList(List<String> productIdsList) {

		LogHelper.info(this, "Start -->  calling  productRepository.getAll");
		if (commercialProductRepository == null) {
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}
		List<CommercialProduct> commercialProducts = new ArrayList<>(
				commercialProductRepository.getAll(productIdsList));
		LogHelper.info(this, "End -->  After calling  productRepository.getAll");
		return commercialProducts;
	}

	@Override
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String journeyType) {
		List<MerchandisingPromotionModel> listOfMerchandisingPromotions = null;

		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			LogHelper.info(this, "Start -->  calling  getMerchandisingPromotionsByProductLineAndPackageType_Solr");
			listOfMerchandisingPromotions = requestManager.getMerchandisingPromotionsByProductLineAndPackageType("PAYM",
					journeyType);
			LogHelper.info(this, "End -->  After calling  getMerchandisingPromotionsByProductLineAndPackageType_solr");
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}

		return listOfMerchandisingPromotions;
	}

	@Override
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String grouptype, String journeyType) {
		List<MerchandisingPromotionModel> listOfMerchandisingPromotions = null;

		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			if (grouptype.equals(Constants.OFFERCODE_PAYM)) {
				if (Constants.JOURNEY_TYPE_SECONDLINE_UPGRADE.equalsIgnoreCase(journeyType)) {
					listOfMerchandisingPromotions = requestManager
							.getMerchandisingPromotionsByProductLineAndPackageType(grouptype,
									Constants.JOURNEY_TYPE_SECONDLINE_UPGRADE);
				}
				if (Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyType)) {
					listOfMerchandisingPromotions = requestManager
							.getMerchandisingPromotionsByProductLineAndPackageType(grouptype,
									Constants.JOURNEY_TYPE_UPGRADE);
				}
				if (Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyType)) {
					listOfMerchandisingPromotions = requestManager
							.getMerchandisingPromotionsByProductLineAndPackageType(grouptype,
									Constants.JOURNEY_TYPE_SECONDLINE);
				}
			}

		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}

		return listOfMerchandisingPromotions;
	}
	
	/**
	 * @author aditya.oli
	 * Method to initialize requestManager if it is null. Else, send it as it was initialized. This 
	 * depicts an implementation of the Singleton design pattern where we use a single object/connection 
	 * for the entire session.
	 * @return RequestManager
	 */
	@Override
	public RequestManager getRequestManager()
	{
		if (requestManager == null) 
		{
			requestManager = SolrConnectionProvider.getSolrConnection();
		}
		return requestManager;
	}

	/**
	 * @author aditya.oli
	 * Method to initialize CommercialProductRepository if it is null. Else, send it as it was initialized. This 
	 * depicts an implementation of the Singleton design pattern where we use a single object/connection 
	 * for the entire session.
	 * @return CommercialProductRepository
	 */
	@Override
	public CommercialProductRepository getCommercialProductRepository()
	{
		if (commercialProductRepository == null) 
		{
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}
		return commercialProductRepository;
	}
	
	/**
	 * @author aditya.oli
	 * Method to initialize CommercialBundleRepository if it is null. Else, send it as it was initialized. This 
	 * depicts an implementation of the Singleton design pattern where we use a single object/connection 
	 * for the entire session.
	 * @return CommercialBundleRepository
	 */
	@Override
	public CommercialBundleRepository getCommercialBundleRepository()
	{
		if (commercialBundleRepository == null) 
		{
			commercialBundleRepository = CoherenceConnectionProvider.getCommercialBundleRepoConnection();
		}
		return commercialBundleRepository;
	}
	
	/**
	 * @author aditya.oli
	 * Method to initialize ProductGroupRepository if it is null. Else, send it as it was initialized. This 
	 * depicts an implementation of the Singleton design pattern where we use a single object/connection 
	 * for the entire session.
	 * @return ProductGroupRepository
	 */
	@Override
	public ProductGroupRepository getProductGroupRepository()
	{
		if (productGroupRepository == null) 
		{
			productGroupRepository = CoherenceConnectionProvider.getProductGroupRepoRepository();
		}
		return productGroupRepository;
	}
	
	/**
	 * @author aditya.oli
	 * Method to initialize MerchandisingPromotionRepository if it is null. Else, send it as it was initialized. This 
	 * depicts an implementation of the Singleton design pattern where we use a single object/connection 
	 * for the entire session.
	 * @return MerchandisingPromotionRepository
	 */
	@Override
	public MerchandisingPromotionRepository getMerchandisingPromotionRepository()
	{
		if (merchandisingPromotionRepository == null) 
		{
			merchandisingPromotionRepository = CoherenceConnectionProvider.getMerchandisingRepoConnection();
		}
		return merchandisingPromotionRepository;
	}
	
	/**
	 * @author aditya.oli
	 * This method is used to initialize the Bazaar Review Repository which happens to be a third party repository used for reviews.
	 * @return BazaarReviewRepository
	 */
	@Override
	public BazaarReviewRepository getBazaarReviewRepository()
	{
		if (bazaarReviewRepository == null) 
		{
			bazaarReviewRepository = new BazaarReviewRepository();
		}
		return bazaarReviewRepository;
	}
	
	/**
	 * @author aditya.oli
	 * This method checks whether Bazaar Review Repository has been initialized or not. If yes, it gets the Bazaar Voice from the 
	 * repository. Else it will initialize the Repository and then fetch the Bazaar Voice for the service.
	 * @param String skuId
	 * @return BazaarVoice  
	 */
	@Override
	public BazaarVoice getBazaarVoice(String skuId)
	{
		getBazaarReviewRepository();
		return bazaarReviewRepository.get(CommonUtility.appendPrefixString(skuId));
	}
	
	/**
	 * @author aditya.oli
	 * This method returns the List of CommercialProducts from coherence's CommercialProductRepository for 
	 * the respective parameters.
	 * @param String make
	 * @param String model
	 * @return List<CommercialProduct>
	 */
	@Override
	public List<CommercialProduct> getListOfCommercialProductsFromCommercialProductRepository(String make, String model)
	{
		getCommercialProductRepository();
		return commercialProductRepository.getByMakeANDModel(make, model);
	}
	
	/**
	 * @author aditya.oli
	 * This method returns the List of groups from coherence's ProductGroupRepository for a particular 
	 * groupType passed in the parameter.
	 * @param String groupType
	 * @return List<Group>
	 */
	@Override
	public List<Group> getListOfProductGroupFromProductGroupRepository(String groupType)
	{
		getProductGroupRepository();
		return productGroupRepository.getProductGroupsByType(groupType);
	}

	/**
	 * @author aditya.oli
	 * This method gets all the Commercial Bundles from coherence's CommercialBundleRepository,
	 * for the list of planIds passed in the parameter as returns all the Commercial Bundles in the 
	 * form of a list.
	 * @param List<String> listOfLeadPlan
	 * @return Collection<CommercialBundle>
	 * 
	 */
	@Override 
	public Collection<CommercialBundle> getAllCommercialBundlesFromCommercialBundleRepository(List<String> listofLeadPlan)
	{
		getCommercialBundleRepository();
		return commercialBundleRepository.getAll(listofLeadPlan);
	}
	
	/**
	 * @author aditya.oli
	 * This method fetches and returns the Commercial Bundle from coherence's CommercialBundleRepository
	 * by taking the bundle's Id as a parameter.
	 * @param String bundleId
	 * @return CommercialBundle
	 */
	@Override
	public CommercialBundle getCommercialBundleFromCommercialBundleRepository(String bundleId)
	{
		getCommercialBundleRepository();
		return commercialBundleRepository.get(bundleId);
	}
	
	/**
	 * @author aditya.oli
	 * This method fetches and returns the Commercial Product from coherence's CommercialProductRepository
	 * by taking the product's Id as a parameter.
	 * @param String deviceId
	 * @return CommercialProduct
	 */
	@Override
	public CommercialProduct getCommercialProductFromCommercialProductRepository(String deviceId)
	{
		getCommercialProductRepository();
		return commercialProductRepository.get(deviceId);
	}
	
	/**
	 * @author aditya.oli
	 * This method fetches and returns the Merchandising Promotion from coherence's MerchandisingPromotionRepository
	 * by taking the promotion's name as a parameter.
	 * @param String promotionName
	 * @return MerchandisingPromotion
	 */
	@Override
	public com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotionBasedOnPromotionName(String promotionName)
	{
		getMerchandisingPromotionRepository();
		return merchandisingPromotionRepository.get(promotionName);
	}
	
	/**
	 * @author aditya.oli
	 * This method returns the List of Product Group Models by sending the Filters to Solr and
	 * retrieving the respective Product Group Models.
	 * @return List<ProductGroupModel>
	 */
	@Override
	public List<ProductGroupModel> getListOfProductGroupsFromSolr()
	{
		getRequestManager();
		return requestManager.getProductGroups(Filters.HANDSET);
	}
	
	/**
	 * @author aditya.oli
	 * This method returns the List of Group from Solr by sending the list of
	 * Device Group names and retrieving the respective List of Groups.
	 * @param List<String> listOfDeviceGroupName
	 * @return List<Group>
	 */
	@Override
	public List<Group> getListOfGroupsFromProductGroupRepository(List<String> listOfDeviceGroupName) 
	{
		getProductGroupRepository();
		return new ArrayList<Group>(productGroupRepository.getAll(listOfDeviceGroupName));
	}
	
	/**
	 * @author aditya.oli
	 * This method takes the List of String as a parameter and returns the List of
	 * Commercial Products from coherence, each corresponding to the data sent in as a parameter.
	 * @param List<String> productList
	 * @return Collection<CommercialProduct>
	 */
	@Override
	public Collection<CommercialProduct> getCommercialProductListFromCommercialProductRepository(List<String> productList)
	{
		getCommercialProductRepository();
		return commercialProductRepository.getAll(productList);
	}
	
	/**
	 * @author aditya.oli
	 * This method takes the List of String as a parameter and sends it to Solr to fetch
	 * the List of Product Models corresponding to the each of the data values sent in the
	 * request.
	 * @param List<String> listOfProduct
	 * @return List<ProductModel>
	 */
	@Override
	public List<ProductModel> getListOfProductModelFromSolr(List<String> listOfProduct)
	{
		getRequestManager();
		return requestManager.getProductModel(listOfProduct);
	}
	
	/**
	 * @author aditya.oli
	 * This method is used to fetch the list of Offer Applied Price Models from Solr, which
	 * are retrieved by passing the List of device Ids, and the offerCode applicable.
	 * @param List<String> deviceIds
	 * @param String offerCode
	 * @return List<OfferAppliedPriceModel>
	 */
	@Override
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModelFromSolr(List<String> deviceIds, String offerCode)
	{
		getRequestManager();
		return requestManager.getOfferAppliedPrices(deviceIds, offerCode);
	}
	
	/**
	 * @author aditya.oli
	 * This method takes the filter key, filter criteria, sort by, sort option, page number and the page size 
	 * as parameters and send them the Solr to fetch the Product Group Facet Model depending on the values
	 * sent in the request, that match with Solr data.
	 * @param Filters filterKey
	 * @param String filterCriteria
	 * @param String sortBy
	 * @param String sortOption
	 * @param Integer pageNumber
	 * @param Integer pageSize
	 * @return ProductGroupFacetModel
	 */
	@Override
	public ProductGroupFacetModel getProductGroupFacetModelfromSolr(Filters filterKey, String filterCriteria, String sortBy,
			String sortOption, Integer pageNumber, Integer pageSize)
	{
		getRequestManager();
		return requestManager.getProductGroupsWithFacets(filterKey, filterCriteria, sortBy,
				sortOption, pageNumber, pageSize);
	}
	
	/**
	 * @author aditya.oli
	 * This method takes the Filter key as parameter, sends it to Solr, and fetches the 
	 * Product Group Facet Model which is retrieved by sending the filter key as the only
	 * parameter.
	 * @param Filters filterKey
	 * @return ProductGroupFacetModel
	 */
	@Override
	public ProductGroupFacetModel getProductGroupFacetModelForFilterKeyfromSolr(Filters filterKey) 
	{
		getRequestManager();
		return requestManager.getProductGroupsWithFacets(filterKey);
	}
	
	/**
	 * @author aditya.oli
	 * This method takes a list of Strings as a request parameter, and fetches the 
	 * respective list of Bundle Models from Solr for the data respective to each 
	 * entry in the list that was sent as input.
	 * @param List<String> listOfLeadPlanId
	 * @return List<BundleModel>
	 */
	@Override
	public List<BundleModel> getBundleModelListFromSolr(List<String> listOfLeadPlanId)
	{
		getRequestManager();
		return requestManager.getBundleDetails(listOfLeadPlanId);
	}
	
	/**
	 * @author aditya.oli
	 * This method retrieves the Group object from Coherence's Product Group Repository
	 * by taking the groupName and groupType as parameter.
	 * @param String groupName
	 * @param String groupType
	 * @return Group
	 */
	@Override
	public Group getGroupFromProductGroupRepository(String groupName, String groupType)
	{
		getProductGroupRepository();
		return productGroupRepository.getProductGroup(groupName, groupType);
	}
	
	/**
	 * @author aditya.oli
	 * This method takes a string journeyType as a parameter, and then sends it to Solr along with
	 * String PAYM as another parameter, and returns the list of Merchandising Promotion Models from Solr
	 * back to the service layer.
	 * @param String journeyType
	 * @return List<MerchandisingPromotionModel>
	 */
	@Override
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModelFromSolr(String groupType,String journeyType)
	{
		getRequestManager();
		return requestManager.getMerchandisingPromotionsByProductLineAndPackageType(groupType,
					journeyType);
	}
	
	@Override
    public List<CommercialBundle> fetchCommericalBundlesbyList(List<String> listOfBundleIds) {
		LogHelper.info(this, "Start -->  calling  bundleRepository.getAll");
		if (commercialBundleRepository == null) {
			commercialBundleRepository = CoherenceConnectionProvider.getCommercialBundleRepoConnection();
		}
		Collection<CommercialBundle> commercialBundles = commercialBundleRepository.getAll(listOfBundleIds);
		LogHelper.info(this, "End -->  After calling  bundleRepository.get");
		return new ArrayList<CommercialBundle>(commercialBundles);
    }
}
