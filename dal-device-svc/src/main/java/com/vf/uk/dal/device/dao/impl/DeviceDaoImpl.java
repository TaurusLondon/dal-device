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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
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
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.OfferPacks;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.utils.CoherenceConnectionProvider;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.MediaConstants;
import com.vf.uk.dal.device.utils.SolrConnectionProvider;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.CoupleRelation;
import com.vf.uk.dal.utility.entity.PriceForAccessory;
import com.vf.uk.dal.utility.entity.PriceForProduct;
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
import com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.product.pojo.ProductGroups;
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

	/**
	 * Returns List of Device Tile based on groupType and groupName.
	 * 
	 * @param groupType
	 * @param groupName
	 * @return List<DeviceTile>
	 */
	@Override
	public List<DeviceTile> getListOfDeviceTile(String make, String model, String groupType, String deviceId,
			String journeyType, Double creditLimit, String offerCode, String bundleId) {
		boolean isConditionalAcceptJourney = (null != creditLimit) ? true : false;

		// Performance Improvement changes in this Method.
		List<DeviceTile> listOfDeviceTile = new ArrayList<>();
		// Create connection for CommercialBundle Repository for Products.
		if (commercialBundleRepository == null) {
			commercialBundleRepository = CoherenceConnectionProvider.getCommercialBundleRepoConnection();
		}
		// Create connection for CommercialProduct Repository for Products.
		if (commercialProductRepository == null) {
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}

		// Create connection for CommercialGroup Repository for Products.
		if (productGroupRepository == null) {
			productGroupRepository = CoherenceConnectionProvider.getProductGroupRepoRepository();
		}
		DeviceTile deviceTile = new DeviceTile();
		String groupName = null;
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();

		List<CommercialProduct> listOfCommercialProducts = null;
		com.vf.uk.dal.device.entity.Member entityMember;
		if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				|| groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)
				|| groupType.equalsIgnoreCase(Constants.STRING_DEVICE_NEARLY_NEW)) {
			listOfCommercialProducts = commercialProductRepository.getByMakeANDModel(make, model);
			
		} else {
			LogHelper.error(this, Constants.NO_DATA_FOUND_FOR_GROUP_TYPE + groupType);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		List<Group> listOfProductGroup = productGroupRepository.getProductGroupsByType(groupType);
		List<CommercialProduct> commercialProductsMatchedMemList = new ArrayList<>();
		Map<String, CommercialProduct> commerProdMemMap = new HashMap<>();
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
		Map<String, Boolean> bundleIdMap = new HashMap<>();
		Map<String, Boolean> fromPricingMap = new HashMap<>();
		if (null != listOfCommercialProducts) {
			listOfCommercialProducts.forEach(commercialProduct -> {
				if ((Constants.STRING_HANDSET.equalsIgnoreCase(commercialProduct.getProductClass())
						|| Constants.STRING_DATA_DEVICE.equalsIgnoreCase(commercialProduct.getProductClass()))
						&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
						&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)) {
					// Begin User Story 9116
					if (StringUtils.isNotBlank(journeyType)
							&& Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
							&& commercialProduct.getProductControl() != null
							&& commercialProduct.getProductControl().isIsSellableRet()
							&& commercialProduct.getProductControl().isIsDisplayableRet()) {
						commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
					} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
							&& commercialProduct.getProductControl() != null
							&& commercialProduct.getProductControl().isIsDisplayableAcq()
							&& commercialProduct.getProductControl().isIsSellableAcq()) {
						commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
					}
					// End User Story 9116
				}
			});
			if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
				for (Group productGroup : listOfProductGroup) {
					//productGroup=listOfProductGroup.get(26);
					// productGroup.getGroupType()
					if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
						for (Member member : productGroup.getMembers()) {
							if (commerProdMemMap.containsKey(member.getId())) {
								groupName = productGroup.getName();
								entityMember = new com.vf.uk.dal.device.entity.Member();
								entityMember.setId(member.getId());
								entityMember.setPriority(String.valueOf(member.getPriority()));
								listOfDeviceGroupMember.add(entityMember);
								CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
								commercialProductsMatchedMemList.add(commercialProduct);
								if (StringUtils.isNotBlank(bundleId)
										&& commercialProduct.getListOfCompatiblePlanIds().contains(bundleId)) {
									fromPricingMap.put(commercialProduct.getId(), commercialProduct.getLeadPlanId() != null ? false : true); 
									bundleIdMap.put(commercialProduct.getId(), true);
									BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
									bundleAndHardwareTuple.setBundleId(bundleId);
									bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
									bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
								} else {
									fromPricingMap.put(commercialProduct.getId(), commercialProduct.getLeadPlanId() != null ? false : true); 
									bundleIdMap.put(commercialProduct.getId(), false);
									bundleAndHardwareTupleList
											.addAll(getListOfPriceForBundleAndHardware(commercialProduct));
								}
							}
						}
					}
				}
			}
		}

		if (commercialProductsMatchedMemList != null && !commercialProductsMatchedMemList.isEmpty()) {

			if (listOfDeviceGroupMember != null && !listOfDeviceGroupMember.isEmpty()) {

				/****
				 * Identify the member based on rules
				 */

				String leadMemberId = getMemeberBasedOnRules(listOfDeviceGroupMember);
				if (leadMemberId != null) {
					deviceTile.setDeviceId(leadMemberId);
					String avarageOverallRating = getDeviceReviewRating(new ArrayList<>(Arrays.asList(leadMemberId)))
							.get(CommonUtility.appendPrefixString(leadMemberId));
					LogHelper.info(this,
							"AvarageOverallRating for deviceId: " + leadMemberId + " Rating: " + avarageOverallRating);
					deviceTile.setRating(avarageOverallRating);
				}

				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
				if (!groupType.equals(Constants.STRING_DEVICE_PAYG)) {
					// Calling Pricing Api
					if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
						listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList,
								offerCode, registryclnt, journeyType);
					}
				}
				deviceTile.setGroupName(groupName);
				deviceTile.setGroupType(groupType);
				DeviceSummary deviceSummary;
				List<DeviceSummary> listOfDeviceSummary = new ArrayList<>();
				for (com.vf.uk.dal.device.entity.Member member : listOfDeviceGroupMember) {
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					CommercialBundle comBundle = null;
					if (isConditionalAcceptJourney && commercialProduct != null) {
						// Check if lead plan is within credit limit.
						if (isLeadPlanWithinCreditLimit(commercialProduct, creditLimit, listOfPriceForBundleAndHardware,
								journeyType)) {
							comBundle = commercialBundleRepository.get(commercialProduct.getLeadPlanId());
						} else {
							comBundle = getLeadBundleBasedOnAllPlans(creditLimit, commercialProduct,
									commercialBundleRepository, listOfPriceForBundleAndHardware, journeyType);
						}

					} else if (StringUtils.isNotBlank(bundleId) && commercialProduct != null
							&& bundleIdMap.get(member.getId())) {
						comBundle = commercialBundleRepository.get(bundleId);
					} else {
						comBundle = commercialBundleRepository
								.get(getListOfPriceForBundleAndHardware(commercialProduct).get(0).getBundleId());
					}
					List<OfferPacks> listOfOfferPacks = new ArrayList<>();
					if (comBundle != null) {
						listOfOfferPacks.addAll(offerPacksMediaListForBundleDetails(comBundle));
					}
					listOfOfferPacks.addAll(offerPacksMediaListForDeviceDetails(commercialProduct));
					deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct,
							comBundle, listOfPriceForBundleAndHardware, listOfOfferPacks, groupType,
							isConditionalAcceptJourney,fromPricingMap);
					
					if (null != deviceSummary && commercialProduct != null) {
						isPlanAffordable(deviceSummary, comBundle, creditLimit, isConditionalAcceptJourney);
						if (StringUtils.isNotBlank(bundleId))
							if (bundleIdMap.get(member.getId()))
								deviceSummary.setIsCompatible(true);
							else
								deviceSummary.setIsCompatible(false);
						listOfDeviceSummary.add(deviceSummary);
					}

				}

				// Reset Device Id if journey is conditional accept and
				// lead device is not affordable.
				resetDeviceId(isConditionalAcceptJourney, deviceTile, listOfDeviceSummary, deviceId);
				if (isConditionalAcceptJourney) {
					if (null != deviceTile.getDeviceId()) {
						deviceTile.setDeviceSummary(listOfDeviceSummary);
						listOfDeviceTile.add(deviceTile);
					}
				} else {
					deviceTile.setDeviceSummary(listOfDeviceSummary);
					listOfDeviceTile.add(deviceTile);
				}
			} else {
				LogHelper.error(this, "Requested Make and Model Not found in given group type:" + groupType);
				throw new ApplicationException(ExceptionMessages.MAKE_AND_MODEL_NOT_FOUND_IN_GROUPTYPE);
			}
		} else {
			LogHelper.error(this, "No data found for given make and mmodel :" + make + " and " + model);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FOR_MAKE_AND_MODEL);
		}

		return listOfDeviceTile;

	}

	/**
	 * If journey is ConditionAccept and then in list of device summary the
	 * first plan which is affordable is lead device plan.
	 * 
	 * @param isConditionalAcceptJourney
	 * @param deviceTile
	 * @param listOfDeviceSummary
	 */
	private void resetDeviceId(boolean isConditionalAcceptJourney, DeviceTile deviceTile,
			List<DeviceSummary> listOfDeviceSummary, String selectedDeviceId) {

		boolean resetDeviceId = false;
		if (isConditionalAcceptJourney) {
			if (StringUtils.isNotBlank(selectedDeviceId)) {
				for (DeviceSummary deviceSummary : listOfDeviceSummary) {
					if (deviceSummary.getIsAffordable() && deviceSummary.getDeviceId().equals(selectedDeviceId)) {
						deviceTile.setDeviceId(deviceSummary.getDeviceId());
						resetDeviceId = true;
						break;
					}
				}
			}

			if (!resetDeviceId) {
				for (DeviceSummary deviceSummary : listOfDeviceSummary) {
					if (deviceSummary.getIsAffordable()) {
						deviceTile.setDeviceId(deviceSummary.getDeviceId());
						resetDeviceId = true;
						break;
					}
				}
			}

			if (!resetDeviceId) {
				deviceTile.setDeviceId(null);
			}

		}

	}

	/**
	 * Check if plan is affordable as per credit limit and plan monthly price,
	 * and set flag.
	 * 
	 * @param deviceSummary
	 * @param comBundle
	 */
	public void isPlanAffordable(DeviceSummary deviceSummary, CommercialBundle comBundle, Double creditLimit,
			boolean isConditionalAcceptJourney) {
		if (null == comBundle) {
			deviceSummary.setIsAffordable(false);
		} else if (isConditionalAcceptJourney) {
			if (null != deviceSummary.getPriceInfo() && null != deviceSummary.getPriceInfo().getBundlePrice()) {
				String discountType = DaoUtils
						.isPartialOrFullTenureDiscount(deviceSummary.getPriceInfo().getBundlePrice());
				Double monthlyPrice = getBundlePriceBasedOnDiscountDuration(deviceSummary, discountType);

				if (null != monthlyPrice && monthlyPrice > creditLimit) {
					deviceSummary.setIsAffordable(false);
				} else {
					deviceSummary.setIsAffordable(true);
					deviceSummary.setLeadPlanId(deviceSummary.getPriceInfo().getBundlePrice().getBundleId());
					deviceSummary.setBundleType(comBundle.getDisplayGroup());
					deviceSummary.setLeadPlanDisplayName(comBundle.getDisplayName());
				}
			}
		}

	}

	public Double getBundlePriceBasedOnDiscountDuration(DeviceSummary deviceSummary, String discountType) {
		Double monthlyPrice = null;
		if (null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT)) {
			if (null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
					&& null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross()) {
				monthlyPrice = Double.parseDouble(
						deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross());
			}
		} else if (null == discountType || discountType.equals(Constants.LIMITED_TIME_DISCOUNT)) {
			if (null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyPrice() && StringUtils
					.isNotBlank(deviceSummary.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross())) {
				monthlyPrice = Double
						.parseDouble(deviceSummary.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross());
			}
		}
		return monthlyPrice;
	}

	/**
	 * Check if lead plan associated with commercial product is within credit
	 * limit.
	 * 
	 * @param product
	 * @param creditDetails
	 * @return
	 */
	private boolean isLeadPlanWithinCreditLimit(CommercialProduct product, Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String journeyType) {
		List<BundleAndHardwareTuple> bundles = new ArrayList<>();

		BundleAndHardwareTuple tuple = new BundleAndHardwareTuple();
		tuple.setBundleId(product.getLeadPlanId());
		tuple.setHardwareId(product.getId());

		bundles.add(tuple);

		List<PriceForBundleAndHardware> priceForBundleAndHardwares = CommonUtility.getPriceDetails(bundles, null,
				registryclnt, journeyType);

		if (isPlanPriceWithinCreditLimit(creditLimit, priceForBundleAndHardwares, product.getLeadPlanId())) {
			listOfPriceForBundleAndHardware.clear();
			listOfPriceForBundleAndHardware.addAll(priceForBundleAndHardwares);

			return true;
		} else {
			return false;
		}

	}

	/**
	 * @param creditDetails
	 * @param listOfPriceForBundleAndHardware
	 */
	private boolean isPlanPriceWithinCreditLimit(Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String bundleId) {
		if (CollectionUtils.isNotEmpty(listOfPriceForBundleAndHardware)) {
			for (PriceForBundleAndHardware priceForBundleAndHardware : listOfPriceForBundleAndHardware) {
				if (null != priceForBundleAndHardware.getBundlePrice()
						&& getDiscountTypeAndComparePrice(creditLimit, priceForBundleAndHardware.getBundlePrice())
						&& bundleId.equals(priceForBundleAndHardware.getBundlePrice().getBundleId())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Check if there is full or partial discount, depending on discount type
	 * get price and check if it is within credit limit.
	 * 
	 * @param creditLimit
	 * @param priceForBundleAndHardware
	 * @return
	 */
	private boolean getDiscountTypeAndComparePrice(Double creditLimit, BundlePrice bundlePrice) {
		String discountType = DaoUtils.isPartialOrFullTenureDiscount(bundlePrice);
		Double grossPrice = null;
		if (null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyDiscountPrice()
					&& null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
				grossPrice = Double.parseDouble(bundlePrice.getMonthlyDiscountPrice().getGross());
			}
		} else if ((null == discountType || (discountType.equals(Constants.LIMITED_TIME_DISCOUNT)))
				&& null != bundlePrice.getMonthlyPrice() && null != bundlePrice.getMonthlyPrice().getGross()) {
			grossPrice = new Double(bundlePrice.getMonthlyPrice().getGross());

		}

		return (null != grossPrice && grossPrice <= creditLimit);

	}

	/**
	 * Get lead bundle based on all plans excluding lead plan.
	 * 
	 * @param creditDetails
	 * @param commercialProduct
	 * @param commercialBundleRepository
	 * @return
	 */
	private CommercialBundle getLeadBundleBasedOnAllPlans(Double creditLimit, CommercialProduct commercialProduct,
			CommercialBundleRepository commercialBundleRepository,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String journeyType) {

		if (CollectionUtils.isNotEmpty(commercialProduct.getListOfCompatiblePlanIds())) {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			List<String> compatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds();

			for (String planId : compatiblePlanIds) {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				// Pass all plan id's except lead plan Id as its not available
				// within credit limit.
				if (!commercialProduct.getLeadPlanId().equalsIgnoreCase(planId)) {
					bundleAndHardwareTuple.setBundleId(planId);
					bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
					bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
				}
			}

			List<PriceForBundleAndHardware> priceForBundleAndHardwares = CommonUtility
					.getPriceDetails(bundleAndHardwareTupleList, null, registryclnt, journeyType);

			if (CollectionUtils.isNotEmpty(priceForBundleAndHardwares)) {
				Iterator<PriceForBundleAndHardware> iterator = priceForBundleAndHardwares.iterator();
				while (iterator.hasNext()) {

					PriceForBundleAndHardware priceForBundleAndHardware = iterator.next();
					if (null != priceForBundleAndHardware.getBundlePrice()) {
						String discountType = DaoUtils
								.isPartialOrFullTenureDiscount(priceForBundleAndHardware.getBundlePrice());

						if (null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT)) {
							if (null != priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice()
									&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice()
											.getGross()) {
								Double grossPrice = Double.parseDouble(priceForBundleAndHardware.getBundlePrice()
										.getMonthlyDiscountPrice().getGross());
								if (grossPrice > creditLimit) {
									iterator.remove();
								}
							}
						} else if (null == discountType
								|| (null != discountType && discountType.equals(Constants.LIMITED_TIME_DISCOUNT))) {
							if (null != priceForBundleAndHardware.getBundlePrice().getMonthlyPrice()
									&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyPrice()
											.getGross()) {
								Double grossPrice = new Double(
										priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross());
								if (grossPrice > creditLimit) {
									iterator.remove();
								}
							}
						}

					}

				}
				if (CollectionUtils.isNotEmpty(priceForBundleAndHardwares)) {
					listOfPriceForBundleAndHardware.clear();
					listOfPriceForBundleAndHardware.addAll(priceForBundleAndHardwares);
					List<PriceForBundleAndHardware> sortedPlanList = DaoUtils
							.sortPlansBasedOnMonthlyPrice(priceForBundleAndHardwares);
					PriceForBundleAndHardware leadBundle = sortedPlanList.get(0);

					return commercialBundleRepository.get(leadBundle.getBundlePrice().getBundleId());
				}

			}
		}

		return null;

	}

	/**
	 * Returns device details based on the deviceId.
	 * 
	 * @param id
	 * @return DeviceDetails
	 */
	@Override
	public DeviceDetails getDeviceDetails(String deviceId, String journeyType, String offerCode) {

		CommercialProductRepository commercialProductRepository = new CommercialProductRepository();
		CommercialProduct commercialProduct = commercialProductRepository.get(deviceId);
		DeviceDetails deviceDetails = new DeviceDetails();
		CommercialBundleRepository commercialBundleRepository = new CommercialBundleRepository();
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						|| commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))) {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList;

			bundleAndHardwareTupleList = getListOfPriceForBundleAndHardware(commercialProduct);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;

			// Calling Pricing Api
			if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
						registryclnt, journeyType);
			}

			// Media Link from merchandising Promotion
			String leadPlanId = null;
			if (commercialProduct.getLeadPlanId() != null) {
				leadPlanId = commercialProduct.getLeadPlanId();
				LogHelper.info(this, "::::: LeadPlanId " + leadPlanId + " :::::");	
			} else if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
				LogHelper.info(this, "::::: LeadPlanId " + leadPlanId + " :::::");	
			}
			CommercialBundle commercialBundle = commercialBundleRepository.get(leadPlanId);
			List<OfferPacks> listOfOfferPacks = new ArrayList<>();
			if (commercialBundle != null) {
				listOfOfferPacks.addAll(offerPacksMediaListForBundleDetails(commercialBundle));
			}
			listOfOfferPacks.addAll(offerPacksMediaListForDeviceDetails(commercialProduct));
			if (StringUtils.isNotBlank(journeyType) && Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsSellableRet()
					&& commercialProduct.getProductControl().isIsDisplayableRet()) {
				deviceDetails = DaoUtils.convertCoherenceDeviceToDeviceDetails(commercialProduct,
						listOfPriceForBundleAndHardware, listOfOfferPacks);
			} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsDisplayableAcq()
					&& commercialProduct.getProductControl().isIsSellableAcq()) {
				deviceDetails = DaoUtils.convertCoherenceDeviceToDeviceDetails(commercialProduct,
						listOfPriceForBundleAndHardware, listOfOfferPacks);
			}
			else{
				LogHelper.error(this, "No data found for given journeyType :" + deviceId);
				throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
			}
			if (StringUtils.isNotEmpty(offerCode) && StringUtils.isNotEmpty(journeyType)) {
				deviceDetails.setValidOffer(validateOfferValidForDevice(commercialProduct, journeyType, offerCode));
			}

		} else {
			LogHelper.error(this, "No data found for given Device Id :" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}
		return deviceDetails;
	}

	public boolean validateOfferValidForDevice(CommercialProduct commercialProduct, String journeyType,
			String offerCode) {
		List<String> offerCodes = new ArrayList<>();
		boolean validOffer = false;
		if (merchandisingPromotionRepository == null) {
			merchandisingPromotionRepository = CoherenceConnectionProvider.getMerchandisingRepoConnection();
		}
		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion merchandisingPromotion = merchandisingPromotionRepository
						.get(promotionName);
				if (merchandisingPromotion != null) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String promotionPackageType = merchandisingPromotion.getCondition().getPackageType();
					List<String> promotionPackagesList = new ArrayList<String>();
					if (StringUtils.isNotEmpty(promotionPackageType)) {
						promotionPackagesList = Arrays.asList(promotionPackageType.toLowerCase().split(","));
					}
					
					LogHelper.info(this, ":::::::: MERCHE_PROMOTION_TAG :::: " +merchandisingPromotion.getTag() +"::::: START DATE :: " + startDateTime + ":::: END DATE ::: " + endDateTime + " :::: ");
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag())
							&& dateValidationForOffers(startDateTime, endDateTime, Constants.DATE_FORMAT_COHERENCE)
							&& promotionPackagesList.contains(journeyType.toLowerCase())) {
						offerCodes.add(promotionName);
					}
				}
			}
		}
		validOffer = offerCodes.contains(offerCode) ? true : false;
		return validOffer;
	}

	/**
	 * Return list of DeviceTile based on the deviceId.
	 * 
	 * @param id
	 * @return List<DeviceTile>
	 */
	@Override
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType) {
		String strGroupType = null;
		CommercialProductRepository commercialProductRepository = new CommercialProductRepository();
		CommercialProduct commercialProduct = commercialProductRepository.get(id);
		ProductGroupRepository productGroupRepository = new ProductGroupRepository();
		List<DeviceTile> listOfDeviceTile;
		Long memberPriority = null;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						|| commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))) {
			listOfDeviceTile = new ArrayList<>();
			DeviceTile deviceTile = new DeviceTile();
			List<DeviceSummary> listOfDeviceSummary = new ArrayList<>();
			DeviceSummary deviceSummary = new DeviceSummary();
			deviceTile.setDeviceId(id);
			String avarageOverallRating = getDeviceReviewRating(new ArrayList<>(Arrays.asList(id)))
					.get(CommonUtility.appendPrefixString(id));
			deviceTile.setRating(avarageOverallRating);
			if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
				strGroupType = Constants.STRING_DEVICE_PAYM;// Constants.STRING_DEVICE;
			} else if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE)) {
				strGroupType = Constants.STRING_DATADEVICE_PAYM;
			}
			// ******** start ofUserStory No 6860 ****//

			List<Group> listOfProductGroup = productGroupRepository.getProductGroupsByType(strGroupType);
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

			CommercialBundleRepository commercialBundleRepository = new CommercialBundleRepository();
			String leadPlanId = null;
			if (commercialProduct.getLeadPlanId() != null) {
				leadPlanId = commercialProduct.getLeadPlanId();
			} else if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
			}
			CommercialBundle comBundle = commercialBundleRepository.get(leadPlanId);
			// Media Link from merchandising Promotion
			List<OfferPacks> listOfOfferPacks = new ArrayList<>();

			if (comBundle != null) {

				listOfOfferPacks.addAll(offerPacksMediaListForBundleDetails(comBundle));
			}
			listOfOfferPacks.addAll(offerPacksMediaListForDeviceDetails(commercialProduct));
			if (StringUtils.isNotBlank(journeyType) && Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsSellableRet()
					&& commercialProduct.getProductControl().isIsDisplayableRet()) {
				deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct,
						comBundle, listOfPriceForBundleAndHardware, listOfOfferPacks, null, false,null);
			} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsDisplayableAcq()
					&& commercialProduct.getProductControl().isIsSellableAcq()) {
				deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct,
						comBundle, listOfPriceForBundleAndHardware, listOfOfferPacks, null, false,null);
			}
			else{
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
				listOfProductGroupModel = requestManager.getProductGroups(Filters.HANDSET);
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
			LogHelper.error(this, "SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}
		return productGroupList;
	}

	@Override
	public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId, String journeyType,String offerCode) 
	{
		ProductGroupRepository productGroupRepository = new ProductGroupRepository();
		CommercialProductRepository commercialProductRepository = new CommercialProductRepository();
		List<AccessoryTileGroup> listOfAccessoryTile = new ArrayList<>();	
		
		LogHelper.info(this, "Start -->  calling  CommercialProduct.get");
		CommercialProduct commercialProduct = commercialProductRepository.get(deviceId);
		LogHelper.info(this, "End -->  After calling  CommercialProduct.get");
		
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
			
			LogHelper.info(this, "Start -->  calling  CommercialProduct.getProductGroups");
			ProductGroups productGroups = commercialProduct.getProductGroups();
			LogHelper.info(this, "End -->  After calling  CommercialProduct.getProductGroups");
			
			List<String> listOfDeviceGroupName = new ArrayList<>();
			List<String> finalAccessoryList = new ArrayList<>();
			if (productGroups != null && productGroups.getProductGroup() != null
					&& !productGroups.getProductGroup().isEmpty()) {
				for (com.vodafone.product.pojo.ProductGroup productGroup : productGroups.getProductGroup()) {
					if (productGroup.getProductGroupRole().equalsIgnoreCase(Constants.STRING_COMPATIBLE_ACCESSORIES)) {
						listOfDeviceGroupName.add(productGroup.getProductGroupName()
								+ Constants.STRING_PRODUCTGROUP_OPERATOR + productGroup.getProductGroupRole());
					}
				}

				if (listOfDeviceGroupName.isEmpty()) {
					LogHelper.error(this, "No Compatible Accessories found for given device Id:" + deviceId);
					throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
				}

				// HashMap for groupName and list of accessories ID
				Map<String, List<String>> mapForGroupName = new LinkedHashMap<>();
				
				LogHelper.info(this, "Start -->  calling  productGroupRepository.getAll");
				List<Group> listOfProductGroup = new ArrayList<Group>(
						productGroupRepository.getAll(listOfDeviceGroupName));
				LogHelper.info(this, "End -->  After calling  productGroupRepository.getAll");
				
				listOfProductGroup = getGroupBasedOnPriority(listOfProductGroup);

				for (Group productGroup : listOfProductGroup) {
					List<Member> listOfAccesoriesMembers = new ArrayList<>();
					if (productGroup != null && StringUtils.containsIgnoreCase(Constants.STRING_ACCESSORY,
							productGroup.getGroupType())) {
						listOfAccesoriesMembers.addAll(productGroup.getMembers());
						if (!listOfAccesoriesMembers.isEmpty()) {
							listOfAccesoriesMembers = getAccessoryMembersBasedOnPriority(listOfAccesoriesMembers);
						}
					}

					List<String> accessoryList = new ArrayList<>();
					if (listOfAccesoriesMembers != null && !listOfAccesoriesMembers.isEmpty()) {
						for (com.vodafone.productGroups.pojo.Member member : listOfAccesoriesMembers) {
							if (member.getId() != null) {
								accessoryList.add(member.getId().trim());
							}
						}
						mapForGroupName.put(productGroup.getName(), accessoryList);
						finalAccessoryList.addAll(accessoryList);
					}
				}
				LogHelper.info(this, "Start -->  calling  CommercialProduct.getAll");
				// Getting all commercial products for all accessories
				Collection<CommercialProduct> comercialProductList = commercialProductRepository
						.getAll(finalAccessoryList);
				LogHelper.info(this, "End -->  After calling  CommercialProduct.getAll");
				
				List<CommercialProduct> listOfFilteredAccessories = comercialProductList.stream()                
                        .filter(commercialProductAccessories -> CommonUtility.isProductNotExpired(commercialProductAccessories) && CommonUtility.isProductJourneySpecific(commercialProductAccessories, journeyType))     
                        .collect(Collectors.toList());
				
				List<String> listOfValidAccesoryIds = listOfFilteredAccessories.stream().filter(Objects::nonNull).map(CommercialProduct::getId)
						.filter(Objects::nonNull).collect(Collectors.toList());


				// Preparing bundleDeviceAndAccessoryList and fetching price for
				// accessories from Pricing API
				BundleDeviceAndProductsList bundleDeviceAndProductsList = new BundleDeviceAndProductsList();
				bundleDeviceAndProductsList.setAccessoryList(listOfValidAccesoryIds);
				bundleDeviceAndProductsList.setDeviceId(deviceId);
				bundleDeviceAndProductsList.setExtraList(new ArrayList<>());
				bundleDeviceAndProductsList.setOfferCode(offerCode);
				bundleDeviceAndProductsList.setPackageType(journeyType);
				PriceForProduct priceForProduct = null;
				if (bundleDeviceAndProductsList != null) {
					priceForProduct = CommonUtility.getAccessoryPriceDetails(bundleDeviceAndProductsList, registryclnt);
				}

				// HashMap for deviceId and PriceForAccessory
				Map<String, PriceForAccessory> mapforPrice = new HashMap<>();
				if (priceForProduct != null && priceForProduct.getPriceForAccessoryes() != null) {
					for (PriceForAccessory priceForAccessory : priceForProduct.getPriceForAccessoryes()) {
						String hardwareId = priceForAccessory.getHardwarePrice().getHardwareId();
						if (listOfValidAccesoryIds.contains(hardwareId))
							mapforPrice.put(hardwareId, priceForAccessory);
					}
				} else {
					LogHelper.info(this, "Null values received from Price API");
					throw new ApplicationException(ExceptionMessages.NULL_VALUES_FROM_PRICING_API);
				}

				

				// HashMap for deviceId and Commercial Product
				Map<String, CommercialProduct> mapforCommercialProduct = new HashMap<>();
				for (CommercialProduct product : listOfFilteredAccessories) {
					String id = product.getId();
					if (listOfValidAccesoryIds.contains(id))
						mapforCommercialProduct.put(id, product);
				}

				for (Map.Entry<String, List<String>> entry : mapForGroupName.entrySet()) {
					AccessoryTileGroup accessoryTileGroup = new AccessoryTileGroup();
					List<Accessory> listOfAccessory = new ArrayList<>();
					
					for (String hardwareId : entry.getValue()) {
							// US-6717 start
						Accessory accessory = null;
						if(mapforCommercialProduct.containsKey(hardwareId) && mapforPrice.containsKey(hardwareId)){
							accessory = DaoUtils.convertCoherenceAccesoryToAccessory(mapforCommercialProduct.get(hardwareId),
											mapforPrice.get(hardwareId));
						// Us-6717 end
						}
						if (accessory != null){
							listOfAccessory.add(accessory);
						}
					}
					if (listOfAccessory != null && !listOfAccessory.isEmpty()) {
						accessoryTileGroup.setGroupName(entry.getKey());
						accessoryTileGroup.setAccessories(listOfAccessory);
						listOfAccessoryTile.add(accessoryTileGroup);
					} else {
						LogHelper.error(this, "Accessories not found for the given :" + entry.getKey());
					}
				}

			} else {
				LogHelper.error(this, "No Compatible Accessories found for given device Id:" + deviceId);
				throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
			}
		} else {
			LogHelper.error(this, "No data found for given device Id:" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}
		if (listOfAccessoryTile.isEmpty()) {
			LogHelper.error(this, "No Compatible Accessories found for given device Id:" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
		}
		return listOfAccessoryTile;
	}

	/*
	 * @Override public Insurances getInsuranceById(String deviceId) {
	 * 
	 * Insurances insurance = null; CommercialProductRepository
	 * commercialProductRepository = new CommercialProductRepository();
	 * CommercialProduct cohProduct = commercialProductRepository.get(deviceId);
	 * String insuranceProductLine = null; if (cohProduct != null &&
	 * cohProduct.getIsDeviceProduct() &&
	 * cohProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET))
	 * { List<String> listOfProductLines = cohProduct.getProductLines();
	 * 
	 * if (listOfProductLines != null && !listOfProductLines.isEmpty()) { for
	 * (String s : listOfProductLines) { if
	 * (s.contains(Constants.STRING_INSURANCE)) { insuranceProductLine = s;
	 * break; } }
	 * 
	 * ProductCompatibilityRepository compatibilityRepository = new
	 * ProductCompatibilityRepository(); List<ProductCompatibility>
	 * listProductCompatibility;
	 * 
	 * listProductCompatibility = compatibilityRepository
	 * .getProductLineFromCompatibility(insuranceProductLine); if
	 * (listProductCompatibility != null && !listProductCompatibility.isEmpty())
	 * { String insuranceProdLine = null;
	 * 
	 * for (ProductCompatibility compatibility : listProductCompatibility) { if
	 * (compatibility.getType().equalsIgnoreCase(Constants.STRING_REQUIRES)) {
	 * if (compatibility.getProductLine() != null) { insuranceProdLine =
	 * compatibility.getProductLine(); } } } if (insuranceProdLine != null) {
	 * List<CommercialProduct> cohProductForRelative =
	 * commercialProductRepository .getProductByProductLine(insuranceProdLine);
	 * if (cohProductForRelative != null && !cohProductForRelative.isEmpty()) {
	 * insurance =
	 * DaoUtils.convertCommercialProductToInsurance(cohProductForRelative); } }
	 * else { LogHelper.error(this,
	 * "No Insurance Product Line found for given compatibility :"); throw new
	 * ApplicationException(ExceptionMessages.
	 * NO_INSURANCE_PRODUCT_FOR_GIVEN_COMPATABILITY); } } else {
	 * LogHelper.error(this,
	 * "No Product compatability found for given InsuranceProductLine :" +
	 * insuranceProductLine); throw new ApplicationException(
	 * ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_INSURANCE_PRODUCTLINE_ID)
	 * ; } } else { LogHelper.error(this,
	 * "No Product Line found for given Device Id :" + deviceId); throw new
	 * ApplicationException(ExceptionMessages.
	 * NO_PRODUCT_LINE_FOUND_FOR_GIVEN_DEVICE_ID); } } else {
	 * LogHelper.error(this, "No data found for given Device Id :" + deviceId);
	 * throw new ApplicationException(ExceptionMessages.
	 * NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID); } if (insurance != null) {
	 * getFormattedPriceForGetCompatibleInsurances(insurance);
	 * insurance.setMinCost(FormatPrice(insurance.getMinCost())); } return
	 * insurance; }
	 */
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
	 * 
	 * @param listOfDeviceGroupMembers
	 * @return leadDeviceSkuId
	 */
	public String getMemeberBasedOnRules(List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember) {
		String leadDeviceSkuId = null;
		DaoUtils daoUtils = new DaoUtils();
		List<com.vf.uk.dal.device.entity.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.entity.Member member : listOfSortedMember) {
			if (validateMemeber(member.getId())) {
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
	 * @return memberFlag
	 */
	public Boolean validateMemeber(String memberId) {
		Boolean memberFlag = false;
		CommercialProductRepository commercialProductRepository = new CommercialProductRepository();
		CommercialProduct comProduct = commercialProductRepository.get(memberId);
		Date startDateTime = comProduct.getProductAvailability().getStart();
		Date endDateTime = comProduct.getProductAvailability().getEnd();
		boolean preOrderableFlag = comProduct.getProductControl().isPreOrderable();

		if ((comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
				|| comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))
				&& DaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& (comProduct.getProductControl().isIsDisplayableAcq()
						&& comProduct.getProductControl().isIsSellableAcq())) {
			memberFlag = true;
		}

		return memberFlag;

	}

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
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param preOrderableFlag
	 * @return flag
	 *//*
		 * public Boolean dateValidation(Date startDateTime, Date endDateTime,
		 * boolean preOrderableFlag) { Date currentDate = new Date(); boolean
		 * flag = false;
		 * 
		 * if (startDateTime != null && endDateTime != null) { Boolean x =
		 * currentDate.before(startDateTime); Boolean y = preOrderableFlag;
		 * boolean z = x && y;
		 * 
		 * Boolean a = currentDate.after(startDateTime); Boolean b =
		 * currentDate.before(endDateTime); Boolean c = a && b; if (z || c) {
		 * flag = true; } } if (startDateTime == null && endDateTime != null &&
		 * currentDate.before(endDateTime)) { flag = true; } if (startDateTime
		 * != null && endDateTime == null && currentDate.after(startDateTime)) {
		 * flag = true; } if (startDateTime == null && endDateTime == null) {
		 * flag = true; } return flag; }
		 */

	/**
	 * calculation of price
	 * 
	 * @param grossPrice
	 * @return Price
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

	public List<OfferPacks> offerPacksMediaListForBundleDetails(CommercialBundle commercialBundle) {
		List<OfferPacks> listOfOfferPacks = new ArrayList<>();
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		OfferPacks offerPacks;

		if (merchandisingPromotionRepository == null) {
			merchandisingPromotionRepository = CoherenceConnectionProvider.getMerchandisingRepoConnection();
		}

		if (commercialBundle.getPromoteAs() != null && commercialBundle.getPromoteAs().getPromotionName() != null
				&& !commercialBundle.getPromoteAs().getPromotionName().isEmpty()) {
			offerPacks = new OfferPacks();
			for (String promotionName : commercialBundle.getPromoteAs().getPromotionName()) {
				com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion merchandisingPromotion = merchandisingPromotionRepository
						.get(promotionName);
				if (merchandisingPromotion != null) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					LogHelper.info(this, ":::::::: MERCHE_PROMOTION_TAG :::: " +merchandisingPromotion.getTag() +"::::: START DATE :: " + startDateTime + ":::: END DATE ::: " + endDateTime + " :::: ");
					
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag())
							&& dateValidationForOffers(startDateTime, endDateTime, Constants.DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
			offerPacks.setBundleId(commercialBundle.getId());
			offerPacks.setMediaLinkList(listOfMediaLink);
			listOfOfferPacks.add(offerPacks);
		}
		return listOfOfferPacks;

	}

	public List<OfferPacks> offerPacksMediaListForDeviceDetails(CommercialProduct commercialProduct) {
		List<OfferPacks> listOfOfferPacks = new ArrayList<>();
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		OfferPacks offerPacks;

		if (merchandisingPromotionRepository == null) {
			merchandisingPromotionRepository = CoherenceConnectionProvider.getMerchandisingRepoConnection();
		}

		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			offerPacks = new OfferPacks();
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion merchandisingPromotion = merchandisingPromotionRepository
						.get(promotionName);
				if (merchandisingPromotion != null) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					LogHelper.info(this, ":::::::: MERCHE_PROMOTION_TAG :::: " +merchandisingPromotion.getTag() +"::::: START DATE :: " + startDateTime + ":::: END DATE ::: " + endDateTime + " :::: ");
					
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag())
							&& dateValidationForOffers(startDateTime, endDateTime, Constants.DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
			offerPacks.setBundleId(commercialProduct.getId());
			offerPacks.setMediaLinkList(listOfMediaLink);
			listOfOfferPacks.add(offerPacks);
		}
		return listOfOfferPacks;

	}

	public List<MediaLink> listOfMediaLinkBasedOnMerchandising(MerchandisingPromotion merchandisingPromotion) {
		MediaLink mediaLinkForDescription;
		MediaLink mediaLinkForLabel;
		MediaLink mediaLinkForUrlGrid;
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		if (!merchandisingPromotion.getType().equalsIgnoreCase("full_duration")
				&& !merchandisingPromotion.getType().equalsIgnoreCase("limited_time")
				&& !merchandisingPromotion.getType().equalsIgnoreCase("hardware_discount")
				&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_full_discount")
				&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_limited_discount`")) {
			mediaLinkForLabel = new MediaLink();
			mediaLinkForLabel.setId(merchandisingPromotion.getType() + "." + Constants.STRING_OFFERS_LABEL);
			mediaLinkForLabel.setType(Constants.STRING_TEXT_ALLOWANCE);
			mediaLinkForLabel.setValue(merchandisingPromotion.getLabel());
			if(merchandisingPromotion.getPriority()!=null){
			mediaLinkForLabel.setPriority(merchandisingPromotion.getPriority().intValue());
			}
			listOfMediaLink.add(mediaLinkForLabel);

			mediaLinkForDescription = new MediaLink();
			mediaLinkForDescription.setId(merchandisingPromotion.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
			mediaLinkForDescription.setType(Constants.STRING_TEXT_ALLOWANCE);
			mediaLinkForDescription.setValue(merchandisingPromotion.getDescription());
			if(merchandisingPromotion.getPriority()!=null){
				mediaLinkForDescription.setPriority(merchandisingPromotion.getPriority().intValue());
				}
			listOfMediaLink.add(mediaLinkForDescription);
			if (merchandisingPromotion.getType() != null && StringUtils
					.containsIgnoreCase(merchandisingPromotion.getType(), Constants.STRING_FOR_ENTERTAINMENT)) {
				mediaLinkForUrlGrid = new MediaLink();
				mediaLinkForUrlGrid.setId(merchandisingPromotion.getType() + "." + Constants.STRING_PROMOTION_MEDIA);
				mediaLinkForUrlGrid.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
				mediaLinkForUrlGrid.setValue(merchandisingPromotion.getPromotionMedia());
				if(merchandisingPromotion.getPriority()!=null){
					mediaLinkForUrlGrid.setPriority(merchandisingPromotion.getPriority().intValue());
					}
				listOfMediaLink.add(mediaLinkForUrlGrid);
			}
		}
		return listOfMediaLink;
	}

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return flag
	 */
	public Boolean dateValidationForOffers(String startDateTime, String endDateTime, String strDateFormat) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();
		
		String currentDateStr = dateFormat.format(currentDate);		
		
		try {
			currentDate = dateFormat.parse(currentDateStr);
			
		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(this, "ParseException: " + e);
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
		
		try{
			if (endDateTime != null) {
				endDate = dateFormat.parse(endDateTime);
				LogHelper.info(this, "::::: EndDate " + endDate + " :::::");
			}
		}catch (ParseException | DateTimeParseException e) {
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
				/*
				 * List<String> listOfCompatiblePlan=
				 * bundleDetailsForDevice=CommonUtility.
				 * getPriceDetailsUsingBundleHarwareTrouple()
				 */
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
	 * @param listOfDeviceGroupMember
	 * @return
	 */
	public List<Member> getAccessoryMembersBasedOnPriority(List<Member> listOfDeviceGroupMember) {
		Collections.sort(listOfDeviceGroupMember, new SortedAccessoryPriorityList());

		return listOfDeviceGroupMember;
	}

	class SortedAccessoryPriorityList implements Comparator<Member> {

		@Override
		public int compare(Member member1, Member member2) {

			if (member1.getPriority() != null && member2.getPriority() != null) {
				if (member1.getPriority() < member2.getPriority()) {
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
	 * @param collectionOfGroup
	 * @return collectionOfGroup(sorted based on priority)
	 */

	public static List<Group> getGroupBasedOnPriority(List<Group> listOfGroup) {
		Collections.sort(listOfGroup, new SortedExtrasGroupPriorityList());

		return listOfGroup;
	}

	static class SortedExtrasGroupPriorityList implements Comparator<Group> {

		@Override
		public int compare(Group member1, Group member2) {
			if (member1.getGroupPriority() != null && member2.getGroupPriority() != null) {
				if (member1.getGroupPriority() < member2.getGroupPriority()) {
					return -1;
				} else
					return 1;
			}

			else
				return -1;
		}

	}

	/*
	 * Identifies the status of the stock from coherence cache.
	 * 
	 * @param memberId
	 * 
	 * @return status
	 */
	/*
	 * @Override public boolean getStockInfo(String memberId) { boolean status =
	 * false; if (memberId != null) {
	 * 
	 * String memberId1 = Constants.STRING_ZERO_STOCK_DEVICE_ID + memberId;
	 * 
	 * StockAvailabilityRepository stockrepository = new
	 * StockAvailabilityRepository(); StockAvailability stockAvailability =
	 * stockrepository.get(memberId); if (stockAvailability != null &&
	 * stockAvailability.getStatus() != null) { if
	 * (Constants.STRING_STOCK_STATUS.equalsIgnoreCase(stockAvailability.
	 * getStatus()) && stockAvailability.getQuantity() > 0) {
	 * LogHelper.info(this, "MemberId With Status: " + memberId + " Status : " +
	 * stockAvailability.getStatus() + " Quantity :" +
	 * stockAvailability.getQuantity()); status = true; } } }
	 * 
	 * return status; }
	 */
	/**
	 * Retrieves the stock details
	 * 
	 * @param groupType
	 * @return List<StockInfo>
	 */
	/*
	 * @Override public List<StockInfo> getStockAvailability(String groupType) {
	 * ProductGroupRepository productGroupRepository = new
	 * ProductGroupRepository(); List<String> listOfDeviceGroupMember = new
	 * ArrayList<>(); String commaSeperatedDeviceId; List<StockInfo>
	 * stockAvailabilityForGroupType; List<Group> listOfProductGroup =
	 * productGroupRepository.getProductGroupsByType(groupType); if
	 * (listOfProductGroup != null && !listOfProductGroup.isEmpty()) { for
	 * (Group productGroup : listOfProductGroup) { if (productGroup.getMembers()
	 * != null && !productGroup.getMembers().isEmpty()) { for (Member member :
	 * productGroup.getMembers()) { listOfDeviceGroupMember.add(member.getId());
	 * } } } } commaSeperatedDeviceId =
	 * DaoUtils.convertStringListToString(listOfDeviceGroupMember); try {
	 * stockAvailabilityForGroupType =
	 * CommonUtility.getStockAvailabilityForDevice(commaSeperatedDeviceId,
	 * registryclnt); if (stockAvailabilityForGroupType == null ||
	 * stockAvailabilityForGroupType.isEmpty()) { throw new
	 * ApplicationException(ExceptionMessages.NULL_VALUES_FOR_STOCK_AVAILABILITY
	 * ); } } catch (Exception e) { LogHelper.info(this,
	 * "Recieved Null values From Stock Availability Api : " + e); throw new
	 * ApplicationException(ExceptionMessages.NULL_VALUES_FOR_STOCK_AVAILABILITY
	 * ); }
	 * 
	 * return stockAvailabilityForGroupType; }
	 */

	/**
	 * Saves the stock information into coherence cache
	 * 
	 * @param listOfStockAvailability
	 */

	/*
	 * @Override public void cacheStockInfo(List<StockAvailability>
	 * listOfStockAvailability) { StockAvailabilityRepository stockrepository =
	 * new StockAvailabilityRepository();
	 * stockrepository.saveAll(listOfStockAvailability); }
	 */

	public Insurances getFormattedPriceForGetCompatibleInsurances(Insurances insurances) {

		if (insurances.getInsuranceList() != null && !insurances.getInsuranceList().isEmpty()) {
			List<Insurance> insuranceList = insurances.getInsuranceList();
			for (Insurance insurance : insuranceList) {
				if (insurance.getPrice() != null) {
					Price price = insurance.getPrice();
					if (price.getNet() != null && !price.getNet().equals("")) {
						price.setNet(FormatPrice(price.getNet()));
					}
					if (price.getVat() != null && !price.getVat().equals("")) {
						price.setVat(FormatPrice(price.getVat()));
					}
					if (price.getGross() != null && !price.getGross().equals("")) {
						price.setGross(FormatPrice(price.getGross()));
					}
					insurance.setPrice(price);
				}
			}
		}
		return insurances;
	}

	public String FormatPrice(String price) {
		if (price.contains(".")) {
			String[] decimalSplit = price.split("\\.");
			String beforeDecimal = decimalSplit[0];
			String afterDecimal = decimalSplit[1];

			if (afterDecimal.length() == 1 && afterDecimal.equals("0")) {
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
		BazaarReviewRepository repo = new BazaarReviewRepository();
		BazaarVoice response = repo.get(deviceId);
		if (response != null) {
			jsonObject = response.getJsonsource();
		}
		return jsonObject;
	}

	/**
	 * 
	 */
	@Override
	public com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotionByPromotionName(
			String promotionName) {
		MerchandisingPromotionRepository merchandisingPromotionRepository = new MerchandisingPromotionRepository();
		return merchandisingPromotionRepository.get(promotionName);
	}

	/**
	 * 
	 */
	@Override
	public List<Group> getProductGroupsByType(String groupType) {
		try {
			ProductGroupRepository productGroupRepository = new ProductGroupRepository();
			return productGroupRepository.getProductGroupsByType(groupType);
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
			CommercialProductRepository commercialProductRepository = new CommercialProductRepository();
			return commercialProductRepository.get(leadMemberId);
		} catch (NullPointerException np) {
			LogHelper.error(this, "Invalid Data Coming From Coherence " + np);
			LogHelper.info(this, "Invalid MemberId " + leadMemberId);
			throw new ApplicationException(ExceptionMessages.INVALID_COHERENCE_DATA);
		}
	}

	public List<OfferAppliedPriceModel> getBundleAndHardwarePriceFromSolr(List<String> deviceIds, String offerCode) {

		List<OfferAppliedPriceModel> list = requestManager.getOfferAppliedPrices(deviceIds, offerCode);
		return list;
	}

	@Override
	public Collection<CommercialProduct> getListCommercialProductRepositoryByLeadMemberId(List<String> leadMemberId) {
		Collection<CommercialProduct> commercialProductList = null;
		try {
			CommercialProductRepository commercialProductRepository = new CommercialProductRepository();
			commercialProductList = commercialProductRepository.getAll(leadMemberId);
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
			/*if (StringUtils.isNotBlank(journeyType) && journeyType.equalsIgnoreCase("upgrade")) {
				productGroupFacetModel = requestManager.getProductGroupsWithFacetsByJourneyType(filterKey,
						filterCriteria, sortBy, sortOption, pageNumber, pageSize,
						Arrays.asList(VodafoneConstants.UPGRADE));
			} else {*/
				productGroupFacetModel = requestManager.getProductGroupsWithFacets(filterKey, filterCriteria, sortBy,
						sortOption, pageNumber, pageSize);
			//}
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}
		return productGroupFacetModel;
	}

	/**
	 * 
	 */
	@Override
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey) {
		ProductGroupFacetModel productGroupFacetModel = null;
		try {
			if (requestManager == null) {
				requestManager = SolrConnectionProvider.getSolrConnection();
			}
			productGroupFacetModel = requestManager.getProductGroupsWithFacets(filterKey);
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
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
			productModel = requestManager.getProductModel(listOfProducts);
		} catch (org.apache.solr.common.SolrException solrExcp) {
			SolrConnectionProvider.closeSolrConnection();
			LogHelper.error(this, "SolrException: " + solrExcp);
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
			LogHelper.error(this, "SolrException: " + solrExcp);
			throw new ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION);
		}
		return bundleModel;
	}

	@Override
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds) {

		try {
			BazaarReviewRepository repo = new BazaarReviewRepository();
			List<BazaarVoice> response = new ArrayList<>();
			for (String skuId : listMemberIds) {
				response.add(repo.get(CommonUtility.appendPrefixString(skuId)));
			}
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
		}
		return bvReviewAndRateMap;
	}

	@Override
	public CommercialProduct getCommercialProductByProductId(String productId) {		
		LogHelper.info(this, "Start -->  calling  CommercialProduct.get");
		if(commercialProductRepository == null){
			commercialProductRepository = new CommercialProductRepository();
		}
		CommercialProduct commercialProduct= commercialProductRepository.get(productId);
		LogHelper.info(this, "End -->  After calling  CommercialProduct.get");
		return commercialProduct;
	}

	@Override
	public CommercialBundle getCommercialBundleByBundleId(String bundleId) {
		CommercialBundleRepository commercialBundleRepository = new CommercialBundleRepository();
		return commercialBundleRepository.get(bundleId);
	}

	@Override
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware;
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
		}

	}

	@Override
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) throws ApplicationException {

		CacheDeviceTileResponse response = new CacheDeviceTileResponse();
		String jobStatus = null;
		try {
			jdbcTemplate.setDataSource(datasource);
			jobStatus = jdbcTemplate.queryForObject(
					"SELECT JOB_STATUS FROM DALMS_CACHE_SERVICES WHERE JOB_ID = '" + jobId + "'", String.class);
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
			CommercialBundleRepository commercialBundleRepository = new CommercialBundleRepository();
			commercialBundleList = commercialBundleRepository.getAll(planIdList);
		} catch (Exception e) {
			LogHelper.error(this, "==>" + e);
			return commercialBundleList;
		}
		return commercialBundleList;
	}

	@Override
	public Group getGroupByProdGroupName(String groupName,String groupType) {
		
		Group  group;
		try {
		LogHelper.info(this, "Start -->  calling  getCommercialProductByDeviceId.get");
		if(productGroupRepository == null){
			productGroupRepository = new ProductGroupRepository();
		}
		 group= productGroupRepository.getProductGroup(groupName,groupType);
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
		if(commercialProductRepository == null){
			commercialProductRepository = CoherenceConnectionProvider.getCommercialProductRepoConnection();
		}
		List<CommercialProduct> commercialProducts= new ArrayList<>(commercialProductRepository.getAll(productIdsList));
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
			listOfMerchandisingPromotions = requestManager.getMerchandisingPromotionsByProductLineAndPackageType("PAYM",
					journeyType);
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

}
