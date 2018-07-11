package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupFacetModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.GroupDetails;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.helper.DeviceConditionallHelper;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleModelAndPrice;

/**
 * This class should implement all the methods of DeviceService and should
 * contains the actual business logic and DTO methods.
 * 
 * @author
 */

@Component("deviceService")
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	ResponseMappingHelper response;

	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	RegistryClient registryclnt;

	@Autowired
	DeviceRecommendationService deviceRecommendationService;
	
	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;
	
	@Autowired
	DeviceConditionallHelper deviceHelper;

	DeviceUtils deviceUtils = new DeviceUtils();

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param id
	 * @return List<DeviceTile>
	 */
	@Override
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType) {
		List<DeviceTile> deviceTileList;
		deviceTileList = getDeviceTileByIdForVariant(id, offerCode, journeyType);
		if (deviceTileList == null || deviceTileList.isEmpty()) {
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
		} else
			return deviceTileList;
	}

	/**
	 * 
	 * @param id
	 * @param offerCode
	 * @param journeyTypeInput
	 * @return
	 */
	public List<DeviceTile> getDeviceTileByIdForVariant(String id, String offerCode, String journeyTypeInput) {
		String journeyType;
		journeyType = DeviceServiceImplUtility.getJourneyTypeForVariantAndList(journeyTypeInput);
		LogHelper.info(this, "Start  -->  calling  CommercialProductRepository.get");
		CommercialProduct commercialProduct = deviceEs.getCommercialProduct(id);
		LogHelper.info(this, "End  -->  After calling  CommercialProductRepository.get");

		List<DeviceTile> listOfDeviceTile;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& DeviceServiceImplUtility.getProductclassValidation(commercialProduct)) {
			listOfDeviceTile = getDeviceTileListOfVariant(id, offerCode, journeyType, commercialProduct);
		} else {
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + id);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}

		return listOfDeviceTile;
	}

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param productClass
	 * @param make
	 * @param model
	 * @param groupType
	 * @param sortCriteria
	 * @param pageNumber
	 * @param pageSize
	 * 
	 * @return FacetedDevice
	 */

	@Override
	public FacetedDevice getDeviceList(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, String journeyType, Float creditLimit, String offerCode, String msisdn,
			boolean includeRecommendations) {

		FacetedDevice facetedDevice;
		String sortCriteriaLocal = null;
		sortCriteriaLocal = DeviceServiceImplUtility.getSortCriteria(sortCriteria);
		Validator.validateForDeviceList(sortCriteria, sortCriteriaLocal, groupType, productClass);
		String journeytype = DeviceServiceImplUtility.getJourney(journeyType);
		if (StringUtils.isNotBlank(groupType) && groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			Validator.validateForPAYG(journeytype, offerCode);
			journeytype = Constants.JOURNEY_TYPE_ACQUISITION;
		}
		LogHelper.info(this, "Start -->  calling  getDeviceList in ServiceImpl");
		if (includeRecommendations && StringUtils.isBlank(msisdn)) {
			LogHelper.error(this, "Invalid MSISDN provided. MSISDN is required for retrieving recommendations.");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MSISDN);
		} else {
			if (creditLimit != null) {
				LogHelper.info(this, "Getting devices for conditional Accept, with credit limit :" + creditLimit);
				facetedDevice = getDeviceListForConditionalAccept(productClass, make, model, groupType,
						sortCriteria, pageNumber, pageSize, capacity, colour, operatingSystem, mustHaveFeatures,
						creditLimit, journeytype);
			} else {
				facetedDevice = getDeviceListofFacetedDevice(productClass, make, model, groupType,
						sortCriteria, pageNumber, pageSize, capacity, colour, operatingSystem, mustHaveFeatures,
						journeytype, offerCode);
			}
			if (facetedDevice != null) {
				getFacetDeviceForPromotion(facetedDevice);
			}
			if (facetedDevice != null && includeRecommendations) {
				facetedDevice = getConditionalForDeviceList(msisdn, facetedDevice);

			}
		}
		LogHelper.info(this, "End -->  calling  GetDeviceList in ServiceImpl");
		return facetedDevice;
	}

	/**
	 * 
	 * @param msisdn
	 * @param facetedDevice
	 * @return
	 */
	@Override
	public FacetedDevice getConditionalForDeviceList(String msisdn, FacetedDevice facetedDevice) {
		String message;
		FacetedDevice facetedDeviceResult;
		String deviceId = CommonUtility.getSubscriptionBundleId(msisdn, Constants.SUBSCRIPTION_TYPE_MSISDN,
				registryclnt);
		LogHelper.info(this, "Getting subscription asset for msisdn " + msisdn + "  deviceID " + deviceId);

		if (StringUtils.isNotBlank(deviceId)) {
			LogHelper.info(this, "Getting recommendationed devices for msisdn " + msisdn + " deviceID " + deviceId);
			FacetedDevice sortedFacetedDevice = deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId,
					facetedDevice);
			if (null != sortedFacetedDevice && null != Long.valueOf(sortedFacetedDevice.getNoOfRecordsFound())
					&& sortedFacetedDevice.getNoOfRecordsFound() > 0
					&& StringUtils.isBlank(sortedFacetedDevice.getMessage())) {
				facetedDeviceResult = sortedFacetedDevice;
			} else {
				message = "RECOMMENDATIONS_NOT_AVAILABLE_GRPL_FAILURE";
				facetedDevice.setMessage(message);
				LogHelper.info(this, "Failed to sort based on recommendations. Returning original device list. msisdn "
						+ msisdn + " deviceID " + deviceId);
				facetedDeviceResult = facetedDevice;
				return facetedDeviceResult;
			}
		} else {
			LogHelper.info(this, "Failed to get subscription asset for msisdn " + msisdn);
			message = "RECOMMENDATIONS_NOT_AVAILABLE_SUBSCRIPTION_FAILURE";
			facetedDevice.setMessage(message);
			facetedDeviceResult = facetedDevice;
		}
		return facetedDeviceResult;
	}

	/**
	 * 
	 * @param facetedDevice
	 */
	public void getFacetDeviceForPromotion(FacetedDevice facetedDevice) {
		if (facetedDevice.getDevice() != null && !facetedDevice.getDevice().isEmpty()) {
			List<Device> deviceList = facetedDevice.getDevice();
			List<String> promoteAsTags = new ArrayList<>();
			DeviceServiceImplUtility.getPromoteAsForDevice(deviceList, promoteAsTags);
			if (CollectionUtils.isNotEmpty(promoteAsTags)) {
				Map<String, com.vf.uk.dal.device.entity.MerchandisingPromotion> promotionMap = deviceDao
						.getMerchandisingPromotionsEntityFromRepo(promoteAsTags);
				deviceList.forEach(device -> {
					if (device.getPromotionsPackage() != null) {
						DeviceServiceImplUtility.getPromotionForDeviceList(promotionMap, device);
					}
				});
			}
		}
	}
	/**
	 * KeepDeviceChangetoSimilarPlan
	 * 
	 * @param deviceId
	 * @param bundleId
	 * @param allowedRecurringPriceLimit
	 * @return
	 */
	@Override
	public BundleDetails getBundlesOfDeviceId(String deviceId, String bundleId, String allowedRecurringPriceLimit,
			String plansLimit) {
		BundleDetails bundleDetails = null;
		BundleDetails similarPlan = null;
		BundleDetails bundleDetailsWithFullDuration = null;
		BundleDetails bundleDetailsWithoutFullDuration = new BundleDetails();
		bundleDetails = deviceDao.getBundleDetailsFromComplansListingAPI(deviceId, null);

		if (bundleDetails != null) {
			LogHelper.info(DeviceUtils.class, "bundle Details Without Full Duration " + bundleDetails);
			bundleDetailsWithoutFullDuration.setPlanList(bundleDetails.getPlanList());
			bundleDetailsWithFullDuration = DeviceUtils.removeWithoutFullDurtnPlans(bundleDetails);
			LogHelper.info(DeviceUtils.class, "bundle Details Full Duration " + bundleDetails);
		} else {
			LogHelper.error(DeviceUtils.class, ExceptionMessages.NULL_COMPATIBLE_PLANS_FOR_DEVICE_ID);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_PLANS_FOR_DEVICE_ID);
		}
		if (!bundleDetailsWithFullDuration.getPlanList().isEmpty()) {
			similarPlan = DeviceUtils.getSimilarPlanList(bundleDetailsWithFullDuration, allowedRecurringPriceLimit,
					bundleId, plansLimit, bundleDetailsWithoutFullDuration);
		} else {
			LogHelper.error(DeviceUtils.class, ExceptionMessages.NULL_COMPATIBLE_PLANS_FOR_DEVICE_ID);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_PLANS_FOR_DEVICE_ID);
		}

		return similarPlan;
	}
	/**
	 * @param id
	 * @param offerCode
	 * @param journeyType
	 * @param strGroupType
	 * @param commercialProduct
	 * @param memberPriority
	 * @return
	 */
	public List<DeviceTile> getDeviceTileListOfVariant(String id, String offerCode, String journeyType,
			CommercialProduct commercialProduct) {
		String strGroupType=null;
		 Long memberPriority=null;
		List<DeviceTile> listOfDeviceTile;
		listOfDeviceTile = new ArrayList<>();
		DeviceTile deviceTile = new DeviceTile();
		List<DeviceSummary> listOfDeviceSummary = new ArrayList<>();
		DeviceSummary deviceSummary = null;
		deviceTile.setDeviceId(id);
		deviceTile.setRating(deviceServiceCommonUtility.getDeviceTileRating(id));
		if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
			strGroupType = Constants.STRING_DEVICE_PAYM;
		} else if (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE)) {
			strGroupType = Constants.STRING_DATADEVICE_PAYM;
		}

		LogHelper.info(this, "Start -->  calling  productGroupRepository.getProductGroupsByType");
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(strGroupType);
		LogHelper.info(this, "End -->  After calling  productGroupRepository.getProductGroupsByType");

		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			memberPriority = DeviceServiceImplUtility.getDevicevariantMemberPriority(id, deviceTile, listOfProductGroup);
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
				: deviceEs.getCommercialBundle(leadPlanId);
		LogHelper.info(this, "End -->  After calling  bundleRepository.get");

		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceSummary = getFinalDeviceSummary(id, journeyType, commercialProduct, memberPriority, 
				listOfPriceForBundleAndHardware, comBundle,
				bundleHardwareTupleList);
		if (deviceSummary != null) {
			listOfDeviceSummary.add(deviceSummary);
			deviceTile.setDeviceSummary(listOfDeviceSummary);
		}

		if (CollectionUtils.isNotEmpty(deviceTile.getDeviceSummary()) || deviceTile.getDeviceSummary() != null)
			listOfDeviceTile.add(deviceTile);
		return listOfDeviceTile;
	}

	/**
	 * @param id
	 * @param journeyType
	 * @param commercialProduct
	 * @param memberPriority
	 * @param deviceSummary
	 * @param listOfPriceForBundleAndHardware
	 * @param comBundle
	 * @param promotions
	 * @param priceForBundleAndHardware
	 * @param bundleHardwareTupleList
	 * @return
	 */
	public DeviceSummary getFinalDeviceSummary(String id, String journeyType, CommercialProduct commercialProduct,
			Long memberPriority,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, CommercialBundle comBundle,
			List<BundleAndHardwareTuple> bundleHardwareTupleList) {
		List<BundleAndHardwarePromotions> promotions=null;
		DeviceSummary deviceSummary;
		PriceForBundleAndHardware priceForBundleAndHardware=null;
		if (comBundle != null) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(comBundle.getId());
			bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		}
		if (!bundleHardwareTupleList.isEmpty()) {
			promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList, journeyType,
					registryclnt);
		}
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			priceForBundleAndHardware = listOfPriceForBundleAndHardware.get(0);
		}
		if ((DeviceServiceImplUtility.isUpgrade(journeyType) && DeviceServiceImplUtility.isUpgradeFromCommercialProduct(commercialProduct))
				|| (DeviceServiceImplUtility.isNonUpgrade(journeyType) && DeviceServiceImplUtility.isNonUpgradeCommercialProduct(commercialProduct)) ) {
			deviceSummary = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct, comBundle,
					priceForBundleAndHardware, promotions, null, false, null);
		} else {
			LogHelper.error(this, "No data found for given criteria :" + id);
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
		}
		return deviceSummary;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @return
	 */
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardware(CommercialProduct commercialProduct,
			String journeyType) {

		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		CommercialBundle commercialBundle = null;
		if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())) {
			commercialBundle = deviceEs.getCommercialBundle(commercialProduct.getLeadPlanId());
		}
		boolean sellableCheck = DeviceServiceImplUtility.isSellable(journeyType, commercialBundle);
		List<String> compatiblePlans = commercialProduct.getListOfCompatiblePlanIds() == null
				|| commercialProduct.getListOfCompatiblePlanIds().isEmpty() ? Collections.emptyList()
						: commercialProduct.getListOfCompatiblePlanIds();
		if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
				&& compatiblePlans.contains(commercialProduct.getLeadPlanId()) && sellableCheck) {
			DeviceServiceImplUtility.getBundleHardwareTrupleList(commercialProduct, bundleAndHardwareTupleList);
		} else {

			try {

				deviceServiceCommonUtility.getTupleList(commercialProduct, journeyType, bundleAndHardwareTupleList, listOfBundleHeaderForDevice);
			} catch (Exception e) {
				LogHelper.error(this, "Exception occured when call happen to compatible bundles api: " + e);
			}
			listOfBundleHeaderForDevice.clear();
		}

		return bundleAndHardwareTupleList;
	}
	/**
	 * 
	 * @param productClass
	 * @param make
	 * @param model
	 * @param groupType
	 * @param sortCriteria
	 * @param pageNumber
	 * @param pageSize
	 * @param capacity
	 * @param colour
	 * @param operatingSystem
	 * @param mustHaveFeatures
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public FacetedDevice getDeviceListofFacetedDevice(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, String journeyType, String offerCode) {
		FacetedDevice facetedDevice;
		Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		List<String> criteriaOfSort = DeviceServiceImplUtility.getSortCriteriaForList(sortCriteria);
		String sortOption = criteriaOfSort.get(0);
		String sortBy = criteriaOfSort.get(1);
		Map<String, Object> productGroupFacetMap = getProductGroupFacetMap(groupType, make, capacity, colour,
				operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
		ProductGroupFacetModel productGroupFacetModel = (ProductGroupFacetModel) productGroupFacetMap
				.get("productGroupFacetModel");
		ProductGroupFacetModel productGroupFacetModelForFacets = (ProductGroupFacetModel) productGroupFacetMap
				.get("productGroupFacetModelForFacets");
		LogHelper.info(this, "Facets :"
				+ (null != productGroupFacetModelForFacets ? productGroupFacetModelForFacets.getNumFound() : null));
		List<String> listOfProducts = new ArrayList<>();
		Map<String, String> groupNameWithProdId = new HashMap<>();
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", false);
		if (productGroupFacetModel != null && productGroupFacetModel.getListOfProductGroups() != null
				&& !productGroupFacetModel.getListOfProductGroups().isEmpty()) {
			List<ProductGroupModel> productGroupModelList = productGroupFacetModel.getListOfProductGroups();
			if (productGroupModelList != null && !productGroupModelList.isEmpty()) {
				productGroupModelList.forEach(
						productGroupModel -> getProductGropDetailsForDeviceList(journeyType, productGroupdetailsMap,
								listOfProducts, groupNameWithProdId, isLeadMemberFromSolr, productGroupModel));
			}
			if (listOfProducts.isEmpty()) {
				LogHelper.error(this, "Empty Lead DeviceId List Coming From Solr :  " + listOfProducts);
				throw new ApplicationException(ExceptionMessages.NO_LEAD_MEMBER_ID_COMING_FROM_SOLR);
			}
			LogHelper.error(this, "Lead DeviceId List Coming From Solr------------:  " + listOfProducts);
			List<ProductModel> listOfProductModel = deviceEs.getListOfProductModel(listOfProducts);
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();

			Map<String, List<OfferAppliedPriceModel>> offerPriceMap = new HashMap<>();
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap = new HashMap<>();
			Map<String, BundleAndHardwarePromotions> promotionmap = new HashMap<>();

			if (listOfProductModel != null && !listOfProductModel.isEmpty()) {
				listOfProductModel = getListOfProductModelForDeviceList(groupType, journeyType, offerCode,
						listOfProducts, listOfProductModel, bundleHardwareTupleList, offerPriceMap,
						withoutOfferPriceMap, promotionmap);
			} else {
				LogHelper.error(this, "No Data Found for the given list of Products : " + listOfProductModel);
				throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_PRODUCT_LIST);
			}

			List<FacetField> facetFields = (null != productGroupFacetModelForFacets)
					? productGroupFacetModelForFacets.getListOfFacetsFields() : null;
			facetedDevice = DeviceTilesDaoUtils.convertProductModelListToDeviceList(listOfProductModel, listOfProducts,
					facetFields, groupType, null, null, offerPriceMap, offerCode, groupNameWithProdId, null,
					promotionmap, isLeadMemberFromSolr, withoutOfferPriceMap, journeyType, productGroupdetailsMap);

		} else {
			LogHelper.error(this, "No ProductGroups Found for the given search criteria: ");
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}
		return facetedDevice;
	}
	
	/**
	 * 
	 * @param productClass
	 * @param make
	 * @param model
	 * @param groupType
	 * @param sortCriteria
	 * @param pageNumber
	 * @param pageSize
	 * @param capacity
	 * @param colour
	 * @param operatingSystem
	 * @param mustHaveFeatures
	 * @param creditLimit
	 * @return
	 */
	public FacetedDevice getDeviceListForConditionalAccept(String productClass, String make, String model,
			String groupType, String sortCriteria, int pageNumber, int pageSize, String capacity, String colour,
			String operatingSystem, String mustHaveFeatures, Float creditLimit, String journeyType) {
		LogHelper.info(DeviceTilesDaoUtils.class, "Entering getDeviceListForConditionalAccept ");

		FacetedDevice facetedDevice;
		List<CommercialProduct> ls = null;

		Map<String, BundleModel> bundleModelMap = new HashMap<>();
		Map<String,com.vf.uk.dal.device.entity.BundlePrice> bundleModelAndPriceMap = new HashMap<>();
		List<ProductModel> listOfProductModel = new ArrayList<>();
		List<String> listOfProducts = new ArrayList<>();
		List<String> criteriaOfSort = DeviceServiceImplUtility.getSortCriteriaForList(sortCriteria);
		String sortOption = criteriaOfSort.get(0);
		String sortBy = criteriaOfSort.get(1);
		Map<String, Object> productGroupFacetMap = getProductGroupFacetMap(groupType, make, capacity, colour,
				operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
		ProductGroupFacetModel productGroupFacetModel = (ProductGroupFacetModel) productGroupFacetMap
				.get("productGroupFacetModel");
		ProductGroupFacetModel productGroupFacetModelForFacets = (ProductGroupFacetModel) productGroupFacetMap
				.get("productGroupFacetModelForFacets");
		LogHelper.info(this, "Facets :"
				+ (null != productGroupFacetModelForFacets ? productGroupFacetModelForFacets.getNumFound() : null));

		List<String> listOfProductVariants;
		if (productGroupFacetModel != null && productGroupFacetModel.getListOfProductGroups() != null
				&& !productGroupFacetModel.getListOfProductGroups().isEmpty()) {

			Map<String, String> groupNameWithProdId = new HashMap<>();
			List<ProductGroupModel> productGroupModelList = productGroupFacetModel.getListOfProductGroups();
			if (productGroupModelList != null && !productGroupModelList.isEmpty()) {

				for (ProductGroupModel productGroupModel : productGroupModelList) {
					List<String> listOfProductsNew = new ArrayList<>();
					listOfProductVariants = productGroupModel.getListOfVariants();
					if (productGroupModel.getLeadDeviceId() != null) {
						listOfProductsNew.add(productGroupModel.getLeadDeviceId());
					} else {
						getGroupNameWithListOfProduct(journeyType, listOfProductVariants, groupNameWithProdId,
								productGroupModel, listOfProductsNew);
					}
					List<ProductModel> listOfProductModelNew = new ArrayList<>();
					// get the bundle model
					BundleModelAndPrice bundleModelAndPrice = deviceHelper.calculatePlan(creditLimit, listOfProductsNew,
							listOfProductModelNew);
					if (null != bundleModelAndPrice && null != bundleModelAndPrice.getBundleModel()) {

						listOfProducts.addAll(listOfProductsNew);
						listOfProductModel.addAll(listOfProductModelNew);
						bundleModelMap.put(bundleModelAndPrice.getBundleModel().getBundleId(),
								bundleModelAndPrice.getBundleModel());
						bundleModelAndPriceMap.put(bundleModelAndPrice.getBundleModel().getBundleId(),
								bundleModelAndPrice.getBundlePrice());

					} else {
						getConditionalCriteriaForDeviceList(creditLimit, bundleModelMap, bundleModelAndPriceMap,
								listOfProductModel, listOfProducts, listOfProductVariants);

					}

				}

			}

			Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
			isLeadMemberFromSolr.put("leadMember", false);
			LogHelper.info(DeviceTilesDaoUtils.class, "Entering convertProductModelListToDeviceList ");
			List<FacetField> listOfFacetField=productGroupFacetModelForFacets.getListOfFacetsFields()==null?Collections.emptyList()
					:productGroupFacetModelForFacets.getListOfFacetsFields();
			facetedDevice = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
					listOfProductModel,
					listOfProducts,
					listOfFacetField,
					groupType, 
					ls, 
					bundleModelMap,
					null,
					null,
					groupNameWithProdId,
					bundleModelAndPriceMap,
					null,
					isLeadMemberFromSolr, 
					null, 
					journeyType,
					Collections.emptyMap());
			LogHelper.info(DeviceTilesDaoUtils.class, "exiting convertProductModelListToDeviceList ");
			facetedDevice.setNoOfRecordsFound(productGroupFacetModel.getNumFound());

		} else {
			LogHelper.error(this, "No ProductGroups Found for the given search criteria: ");
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}

		LogHelper.info(DeviceTilesDaoUtils.class, "exiting getDeviceListForConditionalAccept ");
		return facetedDevice;
	}
	/**
	 * 
	 * @param groupType
	 * @param journeyType
	 * @param offerCode
	 * @param listOfProducts
	 * @param listOfProductModel
	 * @param bundleHardwareTupleList
	 * @param offerPriceMap
	 * @param withoutOfferPriceMap
	 * @param promotionmap
	 * @return
	 */
	public List<ProductModel> getListOfProductModelForDeviceList(String groupType, String journeyType, String offerCode,
			List<String> listOfProducts, List<ProductModel> listOfProductModel,
			List<BundleAndHardwareTuple> bundleHardwareTupleList,
			Map<String, List<OfferAppliedPriceModel>> offerPriceMap,
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap,
			Map<String, BundleAndHardwarePromotions> promotionmap) {
		List<ProductModel> listOfProductModelLocal = deviceUtils.sortListForProductModel(listOfProductModel,
				listOfProducts);

		listOfProductModelLocal.forEach(productModel -> DeviceServiceImplUtility.getBundleAndHardwareList(groupType,
				journeyType, bundleHardwareTupleList, productModel));
		getPromotionMapForDeviceList(journeyType, bundleHardwareTupleList, promotionmap);
		if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
			boolean offerCodeJourney = (StringUtils.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType));
			if (offerCodeJourney || (StringUtils.isBlank(offerCode) && (StringUtils.isNotBlank(journeyType)))
					&& !StringUtils.equals(Constants.JOURNEY_TYPE_ACQUISITION, journeyType)) {
				if (StringUtils.isNotBlank(offerCode)) {
					getOfferAppliedPriceWithOfferCode(journeyType, offerCode, listOfProducts, offerPriceMap);
				}
				getOfferAppliedPriceWithoutOfferCode(journeyType, listOfProducts, withoutOfferPriceMap);
			}

		}
		return listOfProductModelLocal;
	}

	/**
	 * 
	 * @param journeyType
	 * @param listOfProducts
	 * @param withoutOfferPriceMap
	 */
	public void getOfferAppliedPriceWithoutOfferCode(String journeyType, List<String> listOfProducts,
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap) {
		List<OfferAppliedPriceModel> listOfWithoutOfferAppliedPrice = deviceEs
				.getListOfOfferAppliedPriceModel(listOfProducts, journeyType, Constants.DATA_NOT_FOUND);
		listOfWithoutOfferAppliedPrice.forEach(offers -> {
			List<OfferAppliedPriceModel> offeredPrice;
			if (withoutOfferPriceMap.containsKey(offers.getHardwareId())) {
				withoutOfferPriceMap.get(offers.getHardwareId()).add(offers);
			} else {
				offeredPrice = new ArrayList<>();
				offeredPrice.add(offers);
				withoutOfferPriceMap.put(offers.getHardwareId(), offeredPrice);
			}
		});
	}

	/**
	 * 
	 * @param journeyType
	 * @param offerCode
	 * @param listOfProducts
	 * @param offerPriceMap
	 */
	public void getOfferAppliedPriceWithOfferCode(String journeyType, String offerCode, List<String> listOfProducts,
			Map<String, List<OfferAppliedPriceModel>> offerPriceMap) {
		List<MerchandisingPromotionModel> listOfMerchandisingPromotions = null;
		listOfMerchandisingPromotions = deviceEs.getListOfMerchandisingPromotionModel(Constants.OFFERCODE_PAYM,
				journeyType);
		MerchandisingPromotionModel merchandisingPromotionModel = listOfMerchandisingPromotions.stream()
				.filter(promotionModel -> offerCode.equals(promotionModel.getTag())).findAny().orElse(null);
		if (merchandisingPromotionModel != null) {
			List<OfferAppliedPriceModel> listOfOfferAppliedPrice = deviceEs
					.getListOfOfferAppliedPriceModel(listOfProducts, journeyType, offerCode);
			listOfOfferAppliedPrice.forEach(offers -> {
				List<OfferAppliedPriceModel> offeredPrice;
				if (offerPriceMap.containsKey(offers.getHardwareId())) {
					offerPriceMap.get(offers.getHardwareId()).add(offers);
				} else {
					offeredPrice = new ArrayList<>();
					offeredPrice.add(offers);
					offerPriceMap.put(offers.getHardwareId(), offeredPrice);
				}
			});
		}
	}

	/**
	 * 
	 * @param journeyType
	 * @param bundleHardwareTupleList
	 * @param promotionmap
	 */
	public void getPromotionMapForDeviceList(String journeyType, List<BundleAndHardwareTuple> bundleHardwareTupleList,
			Map<String, BundleAndHardwarePromotions> promotionmap) {
		List<BundleAndHardwarePromotions> promotions = null;

		if (!bundleHardwareTupleList.isEmpty()) {
			promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList, journeyType,
					registryclnt);
		}
		if (promotions != null) {
			promotions.forEach(promotion ->
				promotionmap.put(promotion.getHardwareId(), promotion)
			);
		}
	}

	/**
	 * 
	 * @param journeyType
	 * @param productGroupdetailsMap
	 * @param listOfProducts
	 * @param groupNameWithProdId
	 * @param isLeadMemberFromSolr
	 * @param productGroupModel
	 */
	public void getProductGropDetailsForDeviceList(String journeyType, Map<String, GroupDetails> productGroupdetailsMap,
			List<String> listOfProducts, Map<String, String> groupNameWithProdId,
			Map<String, Boolean> isLeadMemberFromSolr, ProductGroupModel productGroupModel) {
		if (StringUtils.isNotBlank(productGroupModel.getNonUpgradeLeadDeviceId())
				&& (StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
						&& !StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)))) {
			listOfProducts.add(productGroupModel.getNonUpgradeLeadDeviceId());
			isLeadMemberFromSolr.put("leadMember", true);
			DeviceServiceImplUtility.getProductGroupdetailsMap(productGroupModel, productGroupdetailsMap,
					productGroupModel.getNonUpgradeLeadDeviceId());
		} else if (StringUtils.isNotBlank(productGroupModel.getUpgradeLeadDeviceId())
				&& StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)) {
			listOfProducts.add(productGroupModel.getUpgradeLeadDeviceId());
			isLeadMemberFromSolr.put("leadMember", true);
			DeviceServiceImplUtility.getProductGroupdetailsMap(productGroupModel, productGroupdetailsMap,
					productGroupModel.getUpgradeLeadDeviceId());
		} else {
			List<String> variantsList = productGroupModel.getListOfVariants();
			if (variantsList != null && !variantsList.isEmpty()) {
				List<com.vf.uk.dal.device.entity.Member> listOfMember = DeviceServiceImplUtility
						.getListOfMembers(variantsList);
				String leadMember = getMemeberBasedOnRules1(listOfMember, journeyType);
				if (StringUtils.isNotBlank(leadMember)) {
					groupNameWithProdId.put(leadMember, productGroupModel.getName());
					listOfProducts.add(leadMember);
					DeviceServiceImplUtility.getProductGroupdetailsMap(productGroupModel, productGroupdetailsMap,
							leadMember);
				}
			}
		}
	}

	/**
	 * 
	 * @param groupType
	 * @param make
	 * @param capacity
	 * @param colour
	 * @param operatingSystem
	 * @param mustHaveFeatures
	 * @param sortBy
	 * @param sortOption
	 * @param pageNumber
	 * @param pageSize
	 * @param journeyType
	 * @return
	 */
	public Map<String, Object> getProductGroupFacetMap(String groupType, String make, String capacity, String colour,
			String operatingSystem, String mustHaveFeatures, String sortBy, String sortOption, Integer pageNumber,
			Integer pageSize, String journeyType) {
		Map<String, Object> productGroupFacetMap = new HashMap<>();
		ProductGroupFacetModel productGroupFacetModel = null;
		ProductGroupFacetModel productGroupFacetModelForFacets = null;
		if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			productGroupFacetModel = deviceEs.getProductGroupFacetModel(Constants.STRING_DEVICE_PAYG, make, capacity,
					colour, operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = deviceEs.getProductGroupFacetModel(Constants.STRING_DEVICE_PAYG, journeyType);
			productGroupFacetModelForFacets = null;
			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
			productGroupFacetModel = deviceEs.getProductGroupFacetModel(Constants.STRING_DEVICE_PAYM, make, capacity,
					colour, operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = deviceEs.getProductGroupFacetModel(Constants.STRING_DEVICE_PAYM, journeyType);

			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		}
		productGroupFacetMap.put("productGroupFacetModel", productGroupFacetModel);
		productGroupFacetMap.put("productGroupFacetModelForFacets", productGroupFacetModelForFacets);
		return productGroupFacetMap;
	}
	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return
	 */
	public String getMemeberBasedOnRules1(List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			String journeyType) {
		String leadDeviceSkuId = null;
		DeviceTilesDaoUtils daoUtils = new DeviceTilesDaoUtils();
		List<com.vf.uk.dal.device.entity.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.entity.Member member : listOfSortedMember) {
			if (validateMemeber1(member.getId(), journeyType)) {
				leadDeviceSkuId = member.getId();
				break;
			}
		}
		return leadDeviceSkuId;
	}

	/**
	 * 
	 * @param memberId
	 * @param journeyType
	 * @return
	 */
	public Boolean validateMemeber1(String memberId, String journeyType) {
		Boolean memberFlag = false;
		List<String> listOfProduct = new ArrayList<>();
		listOfProduct.add(memberId);
		Date startDateTime = null;
		Date endDateTime = null;
		List<ProductModel> productModel = deviceEs.getListOfProductModel(listOfProduct);
		if (productModel != null && !productModel.isEmpty()) {
			for (ProductModel productModel2 : productModel) {
				startDateTime = DeviceServiceImplUtility.getStartdateFromProductModel(productModel2);
				endDateTime = DeviceServiceImplUtility.getEndDateFromProductModel(productModel2);
				boolean preOrderableFlag = Boolean.parseBoolean(productModel2.getPreOrderable());

				memberFlag = DeviceServiceImplUtility.isMember(journeyType, startDateTime, endDateTime, productModel2,
						preOrderableFlag);
			}
		}

		return memberFlag;
	}
	/**
	 * 
	 * @param creditLimit
	 * @param bundleModelMap
	 * @param bundleModelAndPriceMap
	 * @param listOfProductModel
	 * @param listOfProducts
	 * @param listOfProductVariants
	 */
	@Override
	public void getConditionalCriteriaForDeviceList(Float creditLimit, Map<String, BundleModel> bundleModelMap,
			Map<String, BundlePrice> bundleModelAndPriceMap, List<ProductModel> listOfProductModel,
			List<String> listOfProducts, List<String> listOfProductVariants) {
		List<String> listOfProductsNew;
		List<ProductModel> listOfProductModelNew;
		BundleModelAndPrice bundleModelAndPrice;
		if (listOfProductVariants != null && !(listOfProductVariants.isEmpty())) {
			int nextId = 2;
			Map<String, String> deviceMap = deviceHelper.getLeadDeviceMap(listOfProductVariants);
			for (String deviceId : listOfProductVariants) {
				LogHelper.error(this, "Device Id :" + deviceId);
				String nextdeviceId = deviceMap.get(nextId + "");

				nextId++;
				listOfProductsNew = new ArrayList<>();
				listOfProductsNew.add(nextdeviceId);
				listOfProductModelNew = new ArrayList<>();
				bundleModelAndPrice = deviceHelper.calculatePlan(creditLimit, listOfProductsNew, listOfProductModelNew);
				if (null != bundleModelAndPrice && null != bundleModelAndPrice.getBundleModel()) {
					listOfProducts.addAll(listOfProductsNew);
					listOfProductModel.addAll(listOfProductModelNew);
					bundleModelMap.put(bundleModelAndPrice.getBundlePrice().getBundleId(),
							bundleModelAndPrice.getBundleModel());
					bundleModelAndPriceMap.put(bundleModelAndPrice.getBundleModel().getBundleId(),
							bundleModelAndPrice.getBundlePrice());
					break;
				}

			}
		}
	}

	/**
	 * 
	 * @param journeyType
	 * @param listOfProductVariants
	 * @param groupNameWithProdId
	 * @param productGroupModel
	 * @param listOfProductsNew
	 */
	@Override
	public void getGroupNameWithListOfProduct(String journeyType, List<String> listOfProductVariants,
			Map<String, String> groupNameWithProdId, ProductGroupModel productGroupModel,
			List<String> listOfProductsNew) {
		if (listOfProductVariants != null && !listOfProductVariants.isEmpty()) {
			List<com.vf.uk.dal.device.entity.Member> listOfMember = DeviceServiceImplUtility
					.getListOfMembers(listOfProductVariants);

			if (listOfMember != null && listOfMember.size() > 1) {
				String leadMember = getMemeberBasedOnRules1(listOfMember, journeyType);
				groupNameWithProdId.put(leadMember, productGroupModel.getName());
				if (leadMember != null) {
					listOfProductsNew.add(leadMember);
				}
			} else if (listOfMember != null) {
				groupNameWithProdId.put(listOfMember.get(0).getId(), productGroupModel.getName());
				listOfProductsNew.add(listOfMember.get(0).getId());
			}
		}
	}
}