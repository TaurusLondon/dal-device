package com.vf.uk.dal.device.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.CatalogueServiceClient;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.client.entity.bundle.BundleModel;
import com.vf.uk.dal.device.client.entity.bundle.BundleModelAndPrice;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.catalogue.DeviceEntityModel;
import com.vf.uk.dal.device.client.entity.catalogue.DeviceOnlineModel;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.model.PricePromotionHandsetPlanModel;
import com.vf.uk.dal.device.model.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.productgroups.FacetField;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.ProductGroupFacetModel;
import com.vf.uk.dal.device.model.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceConditionallHelper;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * This class should implement all the methods of DeviceService and should
 * contains the actual business logic and DTO methods.
 * 
 * @author
 */

@Slf4j
@Component("deviceService")
public class DeviceServiceImpl implements DeviceService {

	private static final String ERROR_CODE_DEVICE_LIST = "error_device_list_failed";
	private static final String ERROR_CODE_DEVICETILE_BY_ID = "error_device_entity_failed";
	private static final String LEAD_MEMBER = "leadMember";
	private static final String PRODUCT_GROUP_FACET_MODEL_FOR_FACETS = "productGroupFacetModelForFacets";
	private static final String PRODUCT_GROUP_FACET_MODEL = "productGroupFacetModel";
	public static final String SUBSCRIPTION_TYPE_MSISDN = "msisdn";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String STRING_DATA_DEVICE = "Data Device";
	public static final String STRING_HANDSET = "Handset";
	public static final String DATA_NOT_FOUND = "NA";
	public static final String STRING_DATADEVICE_PAYM = "DATA_DEVICE_PAYM";
	public static final String OFFERCODE_PAYM = "PAYM";
	private static final String JOURNEY_TYPE_SECONDLINE = "Secondline";
	List<String> paymList = Arrays.asList("Mobile phone service Sellable", "Postpay Mobile Phones", "Postpay Handsets",
			"MBB Sellable");

	@Value("${cacheDevice.handsetOnlineModel.enabled}")
	private boolean handsetOnlineModelEnabled;

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	CatalogueServiceClient catalogueServiceClient;

	@Autowired
	ResponseMappingHelper response;

	@Autowired
	DeviceDetailsMakeAndModelVaiantDaoUtils deviceDetailsMakeAndModelVaiantDaoUtils;

	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	DeviceRecommendationService deviceRecommendationService;

	@Autowired
	Validator validator;

	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;

	@Autowired
	DeviceConditionallHelper deviceHelper;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;

	@Autowired
	CommonUtility commonUtility;

	@Value("${cdn.domain.host}")
	private String cdnDomain;

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
			throw new DeviceCustomException(ERROR_CODE_DEVICETILE_BY_ID,
					ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA, "404");
		} else {
			return deviceTileList;
		}
	}

	/**
	 * 
	 * @param id
	 * @param offerCode
	 * @param journeyTypeInput
	 * @return List<DeviceTile>
	 */
	public List<DeviceTile> getDeviceTileByIdForVariant(String id, String offerCode, String journeyTypeInput) {
		String journeyType;
		journeyType = deviceServiceImplUtility.getJourneyTypeForVariantAndList(journeyTypeInput);
		log.info("Start  -->  calling  CommercialProductRepository.get");
		CommercialProduct commercialProduct = deviceEs.getCommercialProduct(id);
		log.info("End  -->  After calling  CommercialProductRepository.get");

		List<DeviceTile> listOfDeviceTile;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& deviceServiceImplUtility.getProductclassValidation(commercialProduct)) {
			listOfDeviceTile = getDeviceTileListOfVariant(id, offerCode, journeyType, commercialProduct);
		} else {
			log.error(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + id);
			throw new DeviceCustomException(ERROR_CODE_DEVICETILE_BY_ID,
					ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID, "404");
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
		sortCriteriaLocal = deviceServiceImplUtility.getSortCriteria(sortCriteria);
		validator.validateForDeviceList(sortCriteria, sortCriteriaLocal, groupType, productClass);
		String journeytype = deviceServiceImplUtility.getJourney(journeyType);
		if (StringUtils.isNotBlank(groupType) && groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)) {
			validator.validateForPAYG(journeytype, offerCode);
			journeytype = JOURNEY_TYPE_ACQUISITION;
		}
		log.info("Start -->  calling  getDeviceList in ServiceImpl");
		if (includeRecommendations && StringUtils.isBlank(msisdn)) {
			log.error("Invalid MSISDN provided. MSISDN is required for retrieving recommendations.");
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST, ExceptionMessages.INVALID_INPUT_MSISDN, "404");
		} else {
			if (creditLimit != null) {
				log.info("Getting devices for conditional Accept, with credit limit :" + creditLimit);
				facetedDevice = getDeviceListForConditionalAccept(make, groupType, sortCriteria, pageNumber, pageSize,
						capacity, colour, operatingSystem, mustHaveFeatures, creditLimit, journeytype);
			} else {
				facetedDevice = getFacetedDevice(make, groupType, sortCriteria, pageNumber, pageSize, capacity, colour,
						operatingSystem, mustHaveFeatures, offerCode, journeytype);

			}
			if (facetedDevice != null) {
				getFacetDeviceForPromotion(facetedDevice);
			}
			if (facetedDevice != null && includeRecommendations) {
				facetedDevice = getConditionalForDeviceList(msisdn, facetedDevice);

			}
		}
		log.info("End -->  calling  GetDeviceList in ServiceImpl");
		return facetedDevice;
	}

	private FacetedDevice getFacetedDevice(String make, String groupType, String sortCriteria, int pageNumber,
			int pageSize, String capacity, String colour, String operatingSystem, String mustHaveFeatures,
			String offerCode, String journeytype) {
		FacetedDevice facetedDevice;
		if (handsetOnlineModelEnabled) {
			facetedDevice = getDeviceListofFacetedDeviceFromHandsetOnlineModel(make, groupType, sortCriteria,
					pageNumber, pageSize, capacity, colour, operatingSystem, mustHaveFeatures, journeytype, offerCode);
		} else {
			facetedDevice = getDeviceListofFacetedDevice(make, groupType, sortCriteria, pageNumber, pageSize, capacity,
					colour, operatingSystem, mustHaveFeatures, journeytype, offerCode);
		}
		return facetedDevice;
	}

	private FacetedDevice getDeviceListofFacetedDeviceFromHandsetOnlineModel(String make, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, String journeytype, String offerCode) {
		FacetedDevice facetedDevice;
		List<FacetField> facetList = null;
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		DeviceEntityModel deviceEntityModel = null;
		deviceEntityModel = groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)
				? catalogueServiceClient.getDeviceEntityService(make, null, STRING_DEVICE_PAYG, null, journeytype,
						sortCriteria, pageNumber, pageSize, colour, operatingSystem, capacity, mustHaveFeatures)
				: catalogueServiceClient.getDeviceEntityService(make, null, STRING_DEVICE_PAYM, null, journeytype,
						sortCriteria, pageNumber, pageSize, colour, operatingSystem, capacity, mustHaveFeatures);
		List<String> listOfProducts = new ArrayList<>();
		if (deviceEntityModel != null && deviceEntityModel.getDeviceList() != null
				&& !deviceEntityModel.getDeviceList().isEmpty()) {
			List<DeviceOnlineModel> productGroupModelList = deviceEntityModel.getDeviceList();
			List<com.vf.uk.dal.device.client.entity.catalogue.Device> listOfProductModel = new ArrayList<>();
			if (productGroupModelList != null && !productGroupModelList.isEmpty()) {
				getProductGroupDetailsForDeviceListHandsetOnlineModel(journeytype, productGroupdetailsMap,
						listOfProducts, productGroupModelList, listOfProductModel);
			}
			throwExceptionListOfProductEmpty(listOfProducts);
			log.error("Lead DeviceId List Coming From Solr------------:  " + listOfProducts);

			if (!listOfProductModel.isEmpty()) {
				listOfProductModel = deviceUtils.sortListForProductModelForHandsetOnlineModel(listOfProductModel,
						listOfProducts);
			} else {
				log.error("No Data Found for the given list of Products :  " + listOfProductModel);
				throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST,
						ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_PRODUCT_LIST, "404");
			}
			List<String> pricePromoIdsWithOfferCode = new ArrayList<>();
			List<String> pricePromoIdsWithoutOfferCode = new ArrayList<>();
			getPricePromoIds(listOfProductModel, pricePromoIdsWithOfferCode, pricePromoIdsWithoutOfferCode, groupType,
					journeytype, offerCode);
			List<PricePromotionHandsetPlanModel> priceForBundleAndHardwareWithOffer = null;
			List<PricePromotionHandsetPlanModel> priceForBundleAndHardwareWithoutOffer = null;
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthOffer = null;
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthoutOffer = null;
			if (CollectionUtils.isNotEmpty(pricePromoIdsWithOfferCode)) {
				priceForBundleAndHardwareWithOffer = getPrices(pricePromoIdsWithOfferCode);
				mapForDeviceAndPriceForBAndWWIthOffer = priceForBundleAndHardwareWithOffer.stream()
						.collect(Collectors.toMap(priceForBAndH -> priceForBAndH.getHardwarePrice().getHardwareId(),
								priceForBAndH -> priceForBAndH, (priceForBAndH1, priceForBAndH2) -> priceForBAndH1));
			}
			if (CollectionUtils.isNotEmpty(pricePromoIdsWithoutOfferCode)) {
				priceForBundleAndHardwareWithoutOffer = getPrices(pricePromoIdsWithoutOfferCode);
				mapForDeviceAndPriceForBAndWWIthoutOffer = priceForBundleAndHardwareWithoutOffer.stream()
						.collect(Collectors.toMap(priceForBAndH -> priceForBAndH.getHardwarePrice().getHardwareId(),
								priceForBAndH -> priceForBAndH, (priceForBAndH1, priceForBAndH2) -> priceForBAndH1));
			}
			throwExceptionIfPriceNull(listOfProductModel, priceForBundleAndHardwareWithOffer,
					priceForBundleAndHardwareWithoutOffer);
			facetList = deviceEntityModel.getFacetField();
			facetedDevice = deviceTilesDaoUtils.convertProductModelListToDeviceListForHandsetOnlineModel(
					listOfProductModel, listOfProducts, facetList, groupType, journeytype, productGroupdetailsMap,
					cdnDomain, mapForDeviceAndPriceForBAndWWIthOffer, mapForDeviceAndPriceForBAndWWIthoutOffer,
					productGroupModelList, offerCode);

		} else {
			log.error("No ProductGroups Found for the given search criteria : ");
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST,
					ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST, "404");
		}
		return facetedDevice;
	}

	private void throwExceptionIfPriceNull(List<com.vf.uk.dal.device.client.entity.catalogue.Device> listOfProductModel,
			List<PricePromotionHandsetPlanModel> priceForBundleAndHardwareWithOffer,
			List<PricePromotionHandsetPlanModel> priceForBundleAndHardwareWithoutOffer) {
		if (priceForBundleAndHardwareWithOffer == null && priceForBundleAndHardwareWithoutOffer == null) {
			log.error("No Data Found for the given list of Products : " + listOfProductModel);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST, "No response from price", "404");
		}
	}

	/**
	 * 
	 * @param pricePromoIds
	 * @return
	 */
	public List<PricePromotionHandsetPlanModel> getPrices(List<String> pricePromoIds) {

		return deviceEs.getPriceForBundleAndHardwareJourneySpecificMap(pricePromoIds);

	}

	private void throwExceptionListOfProductEmpty(List<String> listOfProducts) {
		if (listOfProducts.isEmpty()) {
			log.error("Empty Lead DeviceId List Coming From Solr :  " + listOfProducts);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST,
					ExceptionMessages.NO_LEAD_MEMBER_ID_COMING_FROM_SOLR, "404");
		}
	}

	private void getProductGroupDetailsForDeviceListHandsetOnlineModel(String journeyType,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, List<String> listOfProducts,
			List<DeviceOnlineModel> productGroupModel,
			List<com.vf.uk.dal.device.client.entity.catalogue.Device> listOfProductModel) {
		for (DeviceOnlineModel productGroupModelList : productGroupModel) {
			if (StringUtils.isNotBlank(productGroupModelList.getLeadNonUpgradeDeviceId())
					&& (StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
							&& !StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)))) {
				listOfProducts.add(productGroupModelList.getLeadNonUpgradeDeviceId());
				deviceServiceImplUtility.getProductGroupdetailsMapForHandsetOnlineModel(productGroupModelList,
						productGroupdetailsMap, productGroupModelList.getLeadNonUpgradeDeviceId());
			} else if (StringUtils.isNotBlank(productGroupModelList.getLeadUpgradeDeviceId())
					&& StringUtils.isNotBlank(journeyType)
					&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
				listOfProducts.add(productGroupModelList.getLeadUpgradeDeviceId());
				deviceServiceImplUtility.getProductGroupdetailsMapForHandsetOnlineModel(productGroupModelList,
						productGroupdetailsMap, productGroupModelList.getLeadUpgradeDeviceId());
			}
		}
		for (DeviceOnlineModel productGroupModelList : productGroupModel) {
			for (com.vf.uk.dal.device.client.entity.catalogue.Device device : productGroupModelList.getDevices()) {
				if (listOfProducts.contains(device.getDeviceId())) {
					listOfProductModel.add(device);
				}
			}
		}
		/*
		 * else { List<String> variantsList =
		 * productGroupModel.getListOfVariants(); if (variantsList != null &&
		 * !variantsList.isEmpty()) { List<com.vf.uk.dal.device.model.Member>
		 * listOfMember = deviceServiceImplUtility
		 * .getListOfMembers(variantsList); String leadMember =
		 * getMemeberBasedOnRules1(listOfMember, journeyType); if
		 * (StringUtils.isNotBlank(leadMember)) {
		 * groupNameWithProdId.put(leadMember,
		 * productGroupModel.getProductGroupName());
		 * listOfProducts.add(leadMember); deviceServiceImplUtility.
		 * getProductGroupdetailsMapForHandsetOnlineModel(productGroupModel,
		 * productGroupdetailsMap, leadMember); } } }
		 */
	}

	/**
	 * 
	 * @param msisdn
	 * @param facetedDevice
	 * @return FacetedDevice
	 */
	@Override
	public FacetedDevice getConditionalForDeviceList(String msisdn, FacetedDevice facetedDevice) {
		String message;
		FacetedDevice facetedDeviceResult;
		String deviceId = commonUtility.getSubscriptionBundleId(msisdn, SUBSCRIPTION_TYPE_MSISDN);
		log.info("Getting subscription asset for msisdn " + msisdn + "  deviceID " + deviceId);

		if (StringUtils.isNotBlank(deviceId)) {
			log.info("Getting recommendationed devices for msisdn " + msisdn + " deviceID " + deviceId);
			FacetedDevice sortedFacetedDevice = deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId,
					facetedDevice);
			if (null != sortedFacetedDevice && null != Long.valueOf(sortedFacetedDevice.getNoOfRecordsFound())
					&& sortedFacetedDevice.getNoOfRecordsFound() > 0
					&& StringUtils.isBlank(sortedFacetedDevice.getMessage())) {
				facetedDeviceResult = sortedFacetedDevice;
			} else {
				message = "RECOMMENDATIONS_NOT_AVAILABLE_GRPL_FAILURE";
				facetedDevice.setMessage(message);
				log.info("Failed to sort based on recommendations. Returning original device list. msisdn " + msisdn
						+ " deviceID " + deviceId);
				facetedDeviceResult = facetedDevice;
				return facetedDeviceResult;
			}
		} else {
			log.info("Failed to get subscription asset for msisdn " + msisdn);
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
			deviceServiceImplUtility.getPromoteAsForDevice(deviceList, promoteAsTags);
			if (CollectionUtils.isNotEmpty(promoteAsTags)) {
				Map<String, com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion> promotionMap = getMerchandisingPromotionsEntityFromRepo(
						promoteAsTags);
				deviceList.forEach(device -> {
					if (device.getPromotionsPackage() != null) {
						deviceServiceImplUtility.getPromotionForDeviceList(promotionMap, device);
					}
				});
			}
		}
	}

	private Map<String, MerchandisingPromotion> getMerchandisingPromotionsEntityFromRepo(List<String> promotionAsTags) {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion> listOfMerchandisingPromotions;
		Map<String, MerchandisingPromotion> promotions = new HashMap<>();
		listOfMerchandisingPromotions = deviceDao.getMerchandisingPromotionsEntityFromRepo(promotionAsTags);
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

	/**
	 * 
	 * @param deviceOnlineModel
	 * @param mapForDeviceAndColor
	 * @param pricePromoIds
	 * @param pricePromoIdsWithoutOfferCode
	 * @param groupType
	 * @param offerCode
	 */
	private void getPricePromoIds(List<com.vf.uk.dal.device.client.entity.catalogue.Device> listOfProductModel,
			List<String> pricePromoIdsWithOfferCode, List<String> pricePromoIdsWithoutOfferCode, String groupType,
			String journeyContextLocal, String offerCode) {
		listOfProductModel.stream().forEach(device -> {
			if (isCompatibleProductLine(device.getProductLines(), groupType)) {
				getPricePromoIdsForJourneys(pricePromoIdsWithOfferCode, pricePromoIdsWithoutOfferCode, device,
						journeyContextLocal, offerCode, groupType);
			}
		});
	}

	/**
	 * 
	 * @param pricePromoIds
	 * @param pricePromoIdsWithoutOfferCode
	 * @param device
	 * @param journeyContextLocal
	 * @param offerCode
	 * @param groupType
	 */
	public void getPricePromoIdsForJourneys(List<String> pricePromoIdsWithOfferCode,
			List<String> pricePromoIdsWithoutOfferCode, com.vf.uk.dal.device.client.entity.catalogue.Device device,
			String journeyContextLocal, String offerCode, String groupType) {
		if (device.getNonUpgradeLeadPlanDetails() != null || device.getUpgradeLeadPlanDetails() != null) {
			if (StringUtils.equalsIgnoreCase(journeyContextLocal, JOURNEY_TYPE_ACQUISITION)) {
				pricePromoIdsWithoutOfferCode.add(
						getPricePromoIds(device.getDeviceId(), device.getNonUpgradeLeadPlanDetails().getLeadPlanId(),
								JOURNEY_TYPE_ACQUISITION, "NA", groupType));
			} else if (StringUtils.equalsIgnoreCase(journeyContextLocal, JOURNEY_TYPE_UPGRADE)) {
				if (StringUtils.isNotBlank(offerCode)) {
					pricePromoIdsWithOfferCode.add(
							getPricePromoIds(device.getDeviceId(), device.getUpgradeLeadPlanDetails().getLeadPlanId(),
									JOURNEY_TYPE_UPGRADE, offerCode, groupType));
				} else {
					pricePromoIdsWithoutOfferCode.add(getPricePromoIds(device.getDeviceId(),
							device.getUpgradeLeadPlanDetails().getLeadPlanId(), JOURNEY_TYPE_UPGRADE, "NA", groupType));
				}
			} else if (StringUtils.equalsIgnoreCase(journeyContextLocal, JOURNEY_TYPE_SECONDLINE)) {
				if (StringUtils.isNotBlank(offerCode)) {
					pricePromoIdsWithOfferCode.add(getPricePromoIds(device.getDeviceId(),
							device.getNonUpgradeLeadPlanDetails().getLeadPlanId(), JOURNEY_TYPE_SECONDLINE, offerCode,
							groupType));
				} else {
					pricePromoIdsWithoutOfferCode.add(getPricePromoIds(device.getDeviceId(),
							device.getNonUpgradeLeadPlanDetails().getLeadPlanId(), JOURNEY_TYPE_SECONDLINE, "NA",
							groupType));
				}
			}
		}
	}

	/**
	 * 
	 * @param deviceId
	 * @param leadPlanId
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public String getPricePromoIds(String deviceId, String leadPlanId, String journeyType, String offerCode,
			String groupType) {
		if (StringUtils.equalsIgnoreCase(STRING_DEVICE_PAYG, groupType)) {
			return "PricePromotionHandset_" + deviceId + "_" + "NA" + "_" + journeyType + "_" + offerCode;
		} else {
			return "PricePromotionHandset_" + deviceId + "_" + leadPlanId + "_" + journeyType + "_" + offerCode;
		}

	}

	/**
	 * 
	 * @param device
	 * @return
	 */
	public boolean isCompatibleProductLine(List<String> productLines, String groupType) {
		boolean result = false;
		if (productLines != null && CollectionUtils.isNotEmpty(productLines)) {
			if (StringUtils.equalsIgnoreCase(STRING_DEVICE_PAYM, groupType)) {
				boolean productLine = productLines.stream().anyMatch(paymList.get(0)::equalsIgnoreCase);
				boolean productLinePAYM = productLines.stream().anyMatch(paymList.get(1)::equalsIgnoreCase);
				boolean productLinePAYM1 = productLines.stream().anyMatch(paymList.get(2)::equalsIgnoreCase);
				boolean productLinePAYM2 = productLines.stream().anyMatch(paymList.get(3)::equalsIgnoreCase);
				result = productLine || productLinePAYM || productLinePAYM1 || productLinePAYM2;
			} else if (StringUtils.equalsIgnoreCase(STRING_DEVICE_PAYG, groupType)) {
				result = productLines.stream().anyMatch("Mobile Phones"::equalsIgnoreCase);
			}
		}
		return result;

	}

	/**
	 * @param id
	 * @param offerCode
	 * @param journeyType
	 * @param strGroupType
	 * @param commercialProduct
	 * @param memberPriority
	 * @return List<DeviceTile>
	 */
	public List<DeviceTile> getDeviceTileListOfVariant(String id, String offerCode, String journeyType,
			CommercialProduct commercialProduct) {
		String strGroupType = null;
		Long memberPriority = null;
		List<DeviceTile> listOfDeviceTile;
		listOfDeviceTile = new ArrayList<>();
		DeviceTile deviceTile = new DeviceTile();
		List<DeviceSummary> listOfDeviceSummary = new ArrayList<>();
		DeviceSummary deviceSummary = null;
		deviceTile.setDeviceId(id);
		deviceTile.setRating(deviceServiceCommonUtility.getDeviceTileRating(id));
		if (commercialProduct.getProductClass().equalsIgnoreCase(STRING_HANDSET)) {
			strGroupType = STRING_DEVICE_PAYM;
		} else if (commercialProduct.getProductClass().equalsIgnoreCase(STRING_DATA_DEVICE)) {
			strGroupType = STRING_DATADEVICE_PAYM;
		}

		log.info("Start -->  calling  productGroupRepository.getProductGroupsByType");
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(strGroupType);
		log.info("End -->  After calling  productGroupRepository.getProductGroupsByType");

		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			memberPriority = deviceServiceImplUtility.getDevicevariantMemberPriority(id, deviceTile,
					listOfProductGroup);
		}
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;

		bundleAndHardwareTupleList = getListOfPriceForBundleAndHardware(commercialProduct, journeyType);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;

		// Calling Pricing Api
		if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			listOfPriceForBundleAndHardware = commonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
					journeyType, null);
		}

		String leadPlanId = null;
		if (commercialProduct.getLeadPlanId() != null) {
			leadPlanId = commercialProduct.getLeadPlanId();
			log.info("::::: LeadPlanId " + leadPlanId + ":::::");
		} else if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
			log.info("::::: LeadPlanId " + leadPlanId + " ::::: ");
		}

		log.info("Start -->  calling  bundleRepository.get");
		CommercialBundle comBundle = (leadPlanId == null || StringUtils.isEmpty(leadPlanId)) ? null
				: deviceEs.getCommercialBundle(leadPlanId);
		log.info("End -->  After calling  bundleRepository.get");

		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceSummary = getFinalDeviceSummary(id, journeyType, commercialProduct, memberPriority,
				listOfPriceForBundleAndHardware, comBundle, bundleHardwareTupleList);
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
	 * @return DeviceSummary
	 */
	public DeviceSummary getFinalDeviceSummary(String id, String journeyType, CommercialProduct commercialProduct,
			Long memberPriority, List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			CommercialBundle comBundle, List<BundleAndHardwareTuple> bundleHardwareTupleList) {
		List<BundleAndHardwarePromotions> promotions = null;
		DeviceSummary deviceSummary;
		PriceForBundleAndHardware priceForBundleAndHardware = null;
		if (comBundle != null) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(comBundle.getId());
			bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		}
		if (!bundleHardwareTupleList.isEmpty()) {
			promotions = commonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
					journeyType);
		}
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			priceForBundleAndHardware = listOfPriceForBundleAndHardware.get(0);
		}
		if ((deviceServiceImplUtility.isUpgrade(journeyType)
				&& deviceServiceImplUtility.isUpgradeFromCommercialProduct(commercialProduct))
				|| (deviceServiceImplUtility.isNonUpgrade(journeyType)
						&& deviceServiceImplUtility.isNonUpgradeCommercialProduct(commercialProduct))) {
			deviceSummary = deviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority,
					commercialProduct, comBundle, priceForBundleAndHardware, promotions, null, false, null, cdnDomain);
		} else {
			log.error("No data found for given criteria :" + id);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST, ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA,
					"404");
		}
		return deviceSummary;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @return List<BundleAndHardwareTuple>
	 */
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardware(CommercialProduct commercialProduct,
			String journeyType) {

		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		CommercialBundle commercialBundle = null;
		if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())) {
			commercialBundle = deviceEs.getCommercialBundle(commercialProduct.getLeadPlanId());
		}
		boolean sellableCheck = deviceServiceImplUtility.isSellable(journeyType, commercialBundle);
		List<String> compatiblePlans = commercialProduct.getListOfCompatiblePlanIds() == null
				|| commercialProduct.getListOfCompatiblePlanIds().isEmpty() ? Collections.emptyList()
						: commercialProduct.getListOfCompatiblePlanIds();
		if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
				&& compatiblePlans.contains(commercialProduct.getLeadPlanId()) && sellableCheck) {
			deviceServiceImplUtility.getBundleHardwareTrupleList(commercialProduct, bundleAndHardwareTupleList);
		} else {

			try {

				deviceServiceCommonUtility.getTupleList(commercialProduct, journeyType, bundleAndHardwareTupleList,
						listOfBundleHeaderForDevice);
			} catch (Exception e) {
				log.error("Exception occured when call happen to compatible bundles api: " + e);
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
	 * @return FacetedDevice
	 */
	public FacetedDevice getDeviceListofFacetedDevice(String make, String groupType, String sortCriteria,
			int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, String journeyType, String offerCode) {
		FacetedDevice facetedDevice;
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		List<String> criteriaOfSort = deviceServiceImplUtility.getSortCriteriaForList(sortCriteria);
		String sortOption = criteriaOfSort.get(0);
		String sortBy = criteriaOfSort.get(1);
		Map<String, Object> productGroupFacetMap = getProductGroupFacetMap(groupType, make, capacity, colour,
				operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
		ProductGroupFacetModel productGroupFacetModel = (ProductGroupFacetModel) productGroupFacetMap
				.get(PRODUCT_GROUP_FACET_MODEL);
		ProductGroupFacetModel productGroupFacetModelForFacets = (ProductGroupFacetModel) productGroupFacetMap
				.get(PRODUCT_GROUP_FACET_MODEL_FOR_FACETS);
		log.info("Facets :"
				+ (null != productGroupFacetModelForFacets ? productGroupFacetModelForFacets.getNumFound() : null));
		List<String> listOfProducts = new ArrayList<>();
		Map<String, String> groupNameWithProdId = new HashMap<>();
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put(LEAD_MEMBER, false);
		if (productGroupFacetModel != null && productGroupFacetModel.getListOfProductGroups() != null
				&& !productGroupFacetModel.getListOfProductGroups().isEmpty()) {
			List<ProductGroupModel> productGroupModelList = productGroupFacetModel.getListOfProductGroups();
			if (productGroupModelList != null && !productGroupModelList.isEmpty()) {
				productGroupModelList.forEach(
						productGroupModel -> getProductGropDetailsForDeviceList(journeyType, productGroupdetailsMap,
								listOfProducts, groupNameWithProdId, isLeadMemberFromSolr, productGroupModel));
			}
			throwExceptionListOfProductEmpty(listOfProducts);
			log.error("Lead DeviceId List Coming From Solr------------:  " + listOfProducts);
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
				log.error("No Data Found for the given list of Products : " + listOfProductModel);
				throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST,
						ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_PRODUCT_LIST, "404");
			}

			List<FacetField> facetFields = (null != productGroupFacetModelForFacets)
					? productGroupFacetModelForFacets.getListOfFacetsFields() : null;
			facetedDevice = deviceTilesDaoUtils.convertProductModelListToDeviceList(listOfProductModel, listOfProducts,
					facetFields, groupType, null, offerPriceMap, offerCode, groupNameWithProdId, null, promotionmap,
					isLeadMemberFromSolr, withoutOfferPriceMap, journeyType, productGroupdetailsMap, cdnDomain);

		} else {
			log.error("No ProductGroups Found for the given search criteria: ");
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST,
					ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST, "404");
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
	 * @return FacetedDevice
	 */
	public FacetedDevice getDeviceListForConditionalAccept(String make, String groupType, String sortCriteria,
			int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, Float creditLimit, String journeyType) {
		log.info("Entering getDeviceListForConditionalAccept ");

		FacetedDevice facetedDevice;
		Map<String, BundleModel> bundleModelMap = new HashMap<>();
		Map<String, com.vf.uk.dal.device.client.entity.price.BundlePrice> bundleModelAndPriceMap = new HashMap<>();
		List<ProductModel> listOfProductModel = new ArrayList<>();
		List<String> listOfProducts = new ArrayList<>();
		List<String> criteriaOfSort = deviceServiceImplUtility.getSortCriteriaForList(sortCriteria);
		String sortOption = criteriaOfSort.get(0);
		String sortBy = criteriaOfSort.get(1);
		Map<String, Object> productGroupFacetMap = getProductGroupFacetMap(groupType, make, capacity, colour,
				operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
		ProductGroupFacetModel productGroupFacetModel = (ProductGroupFacetModel) productGroupFacetMap
				.get(PRODUCT_GROUP_FACET_MODEL);
		ProductGroupFacetModel productGroupFacetModelForFacets = (ProductGroupFacetModel) productGroupFacetMap
				.get(PRODUCT_GROUP_FACET_MODEL_FOR_FACETS);
		log.info("Facets :"
				+ (null != productGroupFacetModelForFacets ? productGroupFacetModelForFacets.getNumFound() : null));

		if (productGroupFacetModel != null && productGroupFacetModel.getListOfProductGroups() != null
				&& !productGroupFacetModel.getListOfProductGroups().isEmpty()) {

			Map<String, String> groupNameWithProdId = new HashMap<>();
			List<ProductGroupModel> productGroupModelList = productGroupFacetModel.getListOfProductGroups();
			if (productGroupModelList != null && !productGroupModelList.isEmpty()) {

				setBundleModelAndPriceMap(creditLimit, journeyType, bundleModelMap, bundleModelAndPriceMap,
						listOfProductModel, listOfProducts, groupNameWithProdId, productGroupModelList);

			}

			Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
			isLeadMemberFromSolr.put(LEAD_MEMBER, false);
			log.info("Entering convertProductModelListToDeviceList ");
			List<FacetField> listOfFacetField = null;
			if (productGroupFacetModelForFacets != null) {
				listOfFacetField = productGroupFacetModelForFacets.getListOfFacetsFields() == null
						? Collections.emptyList() : productGroupFacetModelForFacets.getListOfFacetsFields();
			}
			facetedDevice = deviceTilesDaoUtils.convertProductModelListToDeviceList(listOfProductModel, listOfProducts,
					listOfFacetField, groupType, bundleModelMap, null, null, groupNameWithProdId,
					bundleModelAndPriceMap, null, isLeadMemberFromSolr, null, journeyType, Collections.emptyMap(),
					cdnDomain);
			log.info("exiting convertProductModelListToDeviceList ");
			facetedDevice.setNoOfRecordsFound(productGroupFacetModel.getNumFound());

		} else {
			log.error("No ProductGroups Found for the given search criteria: ");
			throw new DeviceCustomException(ERROR_CODE_DEVICE_LIST,
					ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST, "404");
		}

		log.info("exiting getDeviceListForConditionalAccept ");
		return facetedDevice;
	}

	private void setBundleModelAndPriceMap(Float creditLimit, String journeyType,
			Map<String, BundleModel> bundleModelMap,
			Map<String, com.vf.uk.dal.device.client.entity.price.BundlePrice> bundleModelAndPriceMap,
			List<ProductModel> listOfProductModel, List<String> listOfProducts, Map<String, String> groupNameWithProdId,
			List<ProductGroupModel> productGroupModelList) {
		List<String> listOfProductVariants;
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
	 * @return List<ProductModel>
	 */
	public List<ProductModel> getListOfProductModelForDeviceList(String groupType, String journeyType, String offerCode,
			List<String> listOfProducts, List<ProductModel> listOfProductModel,
			List<BundleAndHardwareTuple> bundleHardwareTupleList,
			Map<String, List<OfferAppliedPriceModel>> offerPriceMap,
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap,
			Map<String, BundleAndHardwarePromotions> promotionmap) {
		List<ProductModel> listOfProductModelLocal = deviceUtils.sortListForProductModel(listOfProductModel,
				listOfProducts);

		listOfProductModelLocal.forEach(productModel -> deviceServiceImplUtility.getBundleAndHardwareList(groupType,
				journeyType, bundleHardwareTupleList, productModel));
		getPromotionMapForDeviceList(journeyType, bundleHardwareTupleList, promotionmap);
		if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)) {
			boolean offerCodeJourney = (StringUtils.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType));
			if (offerCodeJourney || (StringUtils.isBlank(offerCode) && (StringUtils.isNotBlank(journeyType)))
					&& !StringUtils.equals(JOURNEY_TYPE_ACQUISITION, journeyType)) {
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
				.getListOfOfferAppliedPriceModel(listOfProducts, journeyType, DATA_NOT_FOUND);
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
		listOfMerchandisingPromotions = deviceEs.getListOfMerchandisingPromotionModel(OFFERCODE_PAYM, journeyType);
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
			promotions = commonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
					journeyType);
		}
		if (promotions != null) {
			promotions.forEach(promotion -> promotionmap.put(promotion.getHardwareId(), promotion));
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
	public void getProductGropDetailsForDeviceList(String journeyType,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, List<String> listOfProducts,
			Map<String, String> groupNameWithProdId, Map<String, Boolean> isLeadMemberFromSolr,
			ProductGroupModel productGroupModel) {
		if (StringUtils.isNotBlank(productGroupModel.getNonUpgradeLeadDeviceId())
				&& (StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
						&& !StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)))) {
			listOfProducts.add(productGroupModel.getNonUpgradeLeadDeviceId());
			isLeadMemberFromSolr.put(LEAD_MEMBER, true);
			deviceServiceImplUtility.getProductGroupdetailsMap(productGroupModel, productGroupdetailsMap,
					productGroupModel.getNonUpgradeLeadDeviceId());
		} else if (StringUtils.isNotBlank(productGroupModel.getUpgradeLeadDeviceId())
				&& StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
			listOfProducts.add(productGroupModel.getUpgradeLeadDeviceId());
			isLeadMemberFromSolr.put(LEAD_MEMBER, true);
			deviceServiceImplUtility.getProductGroupdetailsMap(productGroupModel, productGroupdetailsMap,
					productGroupModel.getUpgradeLeadDeviceId());
		} else {
			List<String> variantsList = productGroupModel.getListOfVariants();
			if (variantsList != null && !variantsList.isEmpty()) {
				List<com.vf.uk.dal.device.model.Member> listOfMember = deviceServiceImplUtility
						.getListOfMembers(variantsList);
				String leadMember = getMemeberBasedOnRules1(listOfMember, journeyType);
				if (StringUtils.isNotBlank(leadMember)) {
					groupNameWithProdId.put(leadMember, productGroupModel.getName());
					listOfProducts.add(leadMember);
					deviceServiceImplUtility.getProductGroupdetailsMap(productGroupModel, productGroupdetailsMap,
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
	 * @param handsetOnlineModelEnabled
	 * @return productGroupFacetMap
	 */
	public Map<String, Object> getProductGroupFacetMap(String groupType, String make, String capacity, String colour,
			String operatingSystem, String mustHaveFeatures, String sortBy, String sortOption, Integer pageNumber,
			Integer pageSize, String journeyType) {
		Map<String, Object> productGroupFacetMap = new HashMap<>();
		ProductGroupFacetModel productGroupFacetModel = null;
		ProductGroupFacetModel productGroupFacetModelForFacets = null;
		if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)) {
			productGroupFacetModel = deviceEs.getProductGroupFacetModel(STRING_DEVICE_PAYG, make, capacity, colour,
					operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = deviceEs.getProductGroupFacetModel(STRING_DEVICE_PAYG, journeyType);
			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		} else if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)) {
			productGroupFacetModel = deviceEs.getProductGroupFacetModel(STRING_DEVICE_PAYM, make, capacity, colour,
					operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = deviceEs.getProductGroupFacetModel(STRING_DEVICE_PAYM, journeyType);

			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		}
		productGroupFacetMap.put(PRODUCT_GROUP_FACET_MODEL, productGroupFacetModel);
		productGroupFacetMap.put(PRODUCT_GROUP_FACET_MODEL_FOR_FACETS, productGroupFacetModelForFacets);
		return productGroupFacetMap;
	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return leadDeviceSkuId
	 */
	public String getMemeberBasedOnRules1(List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			String journeyType) {
		String leadDeviceSkuId = null;
		DeviceTilesDaoUtils daoUtils = new DeviceTilesDaoUtils();
		List<com.vf.uk.dal.device.model.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.model.Member member : listOfSortedMember) {
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
	 * @return memberFlag
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
				startDateTime = deviceServiceImplUtility.getStartdateFromProductModel(productModel2);
				endDateTime = deviceServiceImplUtility.getEndDateFromProductModel(productModel2);
				boolean preOrderableFlag = Boolean.parseBoolean(productModel2.getPreOrderable());

				memberFlag = deviceServiceImplUtility.isMember(journeyType, startDateTime, endDateTime, productModel2,
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
				log.error("Device Id :" + deviceId);
				String nextdeviceId = deviceMap.get(((Integer) nextId).toString());

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
			List<com.vf.uk.dal.device.model.Member> listOfMember = deviceServiceImplUtility
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