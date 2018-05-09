package com.vf.uk.dal.device.svc.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.BazaarVoice;
import com.vf.uk.dal.device.datamodel.product.CacheOfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CacheProductModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductGroups;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.CacheProductGroupModel;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupFacetModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModelMap;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.Equipment;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.GroupDetails;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.MerchandisingControl;
import com.vf.uk.dal.device.entity.MerchandisingPromotions;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.querybuilder.DeviceQueryBuilderHelper;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.DeviceServiceImplHelper;
import com.vf.uk.dal.device.utils.DeviceTileCacheDAO;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.MediaConstants;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.BundleHeader;
import com.vf.uk.dal.utility.entity.BundleModelAndPrice;
import com.vf.uk.dal.utility.entity.BundlePrice;
import com.vf.uk.dal.utility.entity.CoupleRelation;
import com.vf.uk.dal.utility.entity.PriceForAccessory;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails;

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
	DeviceTileCacheDAO deviceTileCacheDAO;

	@Autowired
	RegistryClient registryclnt;

	@Autowired
	DeviceServiceImplHelper deviceHelper;

	@Autowired
	DeviceRecommendationService deviceRecommendationService;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<DeviceTile> getListOfDeviceTile(String make, String model, String groupType, String deviceId,
			Double creditLimit, String journeyType, String offerCode, String bundleId) {
		List<DeviceTile> deviceTileList;
		String journeyTypeLocal = null;

		if (make == null || make.isEmpty()) {
			LogHelper.error(this, "make is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE);
		}
		if (model == null || model.isEmpty()) {
			LogHelper.error(this, "model is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MODEL);
		}

		if (groupType == null || groupType.isEmpty()) {
			LogHelper.error(this, " Group Type is null ");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
		} else if (!Validator.validateGroupType(groupType)) {
			LogHelper.error(this, "Invalid Group Type");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG) && (StringUtils.isNotBlank(journeyType)
				&& (journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE)
						|| journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE)))) {
			LogHelper.error(this, "JourneyType is Not Compatible with given GroupType");
			throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_JOURNEY_TYPE);
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
			journeyTypeLocal = journeyType;
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			journeyTypeLocal = Constants.JOURNEY_TYPE_ACQUISITION;
		}
		deviceTileList = getListOfDeviceTile_Implementation(make, model, groupType, deviceId, journeyTypeLocal,
				creditLimit, offerCode, bundleId);

		return deviceTileList;
	}

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param id
	 * @return List<DeviceTile>
	 */
	@Override
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType) {
		List<DeviceTile> deviceTileList;
		deviceTileList = deviceDao.getDeviceTileById(id, offerCode, journeyType);
		if (deviceTileList == null || deviceTileList.isEmpty()) {
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
		} else
			return deviceTileList;
	}

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param groupType
	 * @param groupName
	 * @return List<ProductGroup>
	 */
	/*
	 * @Override public List<ProductGroup>
	 * getProductGroupByGroupTypeGroupName(String groupType, String groupName) {
	 * List<ProductGroup> productGroup; if (groupType == null ||
	 * groupType.isEmpty()) { LogHelper.error(this, "Group Type is null"); throw
	 * new
	 * ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
	 * } else { productGroup =
	 * deviceDao.getProductGroupByGroupTypeGroupName(groupType, groupName); }
	 * return productGroup;
	 * 
	 * }
	 */

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param id
	 * @return DeviceDetails
	 */
	@Override
	public DeviceDetails getDeviceDetails(String deviceId, String journeyType, String offerCode) {
		DeviceDetails deviceDetails;

		deviceDetails = getDeviceDetails_Implementation(deviceId, journeyType, offerCode);
		return deviceDetails;
	}

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param deviceId
	 * @return List<Accessory>
	 */

	@Override
	public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId, String journeyType, String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTileGroup;
		listOfAccessoryTileGroup = getAccessoriesOfDevice_Implementation(deviceId, journeyType, offerCode);
		return listOfAccessoryTileGroup;
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
		String message = null;
		String sortCriteriaLocal = null;
		if (sortCriteria == null || sortCriteria.isEmpty()) {
			LogHelper.error(this, "sortCriteria is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_SORT);
		}

		if (StringUtils.isNotBlank(sortCriteria) && sortCriteria.startsWith(Constants.SORT_HYPEN)) {
			sortCriteriaLocal = sortCriteria.substring(1);
		} else {
			sortCriteriaLocal = sortCriteria;
		}
		if (StringUtils.isNotBlank(sortCriteriaLocal) && !Validator.validateSortCriteria(sortCriteriaLocal)) {
			LogHelper.error(this, "Received sortCriteria is invalid.");
			throw new ApplicationException(ExceptionMessages.RECEVIED_INVALID_SORTCRITERIA);
		}

		if (groupType == null || groupType.isEmpty()) {
			LogHelper.error(this, "Group Type is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
		}
		if (productClass == null || productClass.isEmpty()) {
			LogHelper.error(this, "productClass is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_PRODUCT_CLASS);
		}
		if (!productClass.equalsIgnoreCase(Constants.STRING_HANDSET)) {
			LogHelper.error(this, "Invalid Product class");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_PRODUCT_CLASS);
		}
		if (!groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			LogHelper.error(this, "Invalid Group Type");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
		}
		String journeytype = null;
		if (StringUtils.isBlank(journeyType)
				|| StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_ACQUISITION, journeyType)
				|| (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
						&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyType)
						&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyType))) {
			journeytype = Constants.JOURNEY_TYPE_ACQUISITION;
		} else if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE, journeyType)) {
			journeytype = Constants.JOURNEY_TYPE_UPGRADE;
		} else if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE, journeyType)) {
			journeytype = Constants.JOURNEY_TYPE_SECONDLINE;
		}

		if (StringUtils.isNotBlank(groupType) && groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			if (StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE, journeytype)
					|| StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE, journeytype)) {
				LogHelper.error(this, "JourneyType is not compatible for given GroupType");
				throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_JOURNEY_TYPE);
			} else if (StringUtils.isNotBlank(offerCode)) {
				LogHelper.error(this, "offerCode is not compatible for given GroupType");
				throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_OFFER_CODE);
			} else {
				journeytype = Constants.JOURNEY_TYPE_ACQUISITION;
			}
		}

		LogHelper.info(this, "Start -->  calling  getDeviceList in ServiceImpl");
		if (includeRecommendations && StringUtils.isBlank(msisdn)) {
			LogHelper.error(this, "Invalid MSISDN provided. MSISDN is required for retrieving recommendations.");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MSISDN);
		} else {
			// if conditional accept
			if (creditLimit != null) {
				LogHelper.info(this, "Getting devices for conditional Accept, with credit limit :" + creditLimit);
				facetedDevice = getDeviceListForConditionalAccept(productClass, make, model, groupType, sortCriteria,
						pageNumber, pageSize, capacity, colour, operatingSystem, mustHaveFeatures, creditLimit,
						journeytype);
			} else {
				facetedDevice = getDeviceListofFacetedDevice(productClass, make, model, groupType, sortCriteria,
						pageNumber, pageSize, capacity, colour, operatingSystem, mustHaveFeatures, journeytype,
						offerCode);
			}
			if (facetedDevice != null) {
				if (facetedDevice.getDevice() != null && !facetedDevice.getDevice().isEmpty()) {
					List<Device> deviceList = facetedDevice.getDevice();
					List<String> promoteAsTags = new ArrayList<>();
					deviceList.forEach(device -> {
						if (device.getPromotionsPackage() != null) {
							if (device.getPromotionsPackage().getBundlePromotions() != null && device
									.getPromotionsPackage().getBundlePromotions().getPricePromotion() != null) {
								promoteAsTags.add(device.getPromotionsPackage().getBundlePromotions()
										.getPricePromotion().getTag());
							}
							if (device.getPromotionsPackage().getHardwarePromotions() != null && device
									.getPromotionsPackage().getHardwarePromotions().getPricePromotion() != null) {
								promoteAsTags.add(device.getPromotionsPackage().getHardwarePromotions()
										.getPricePromotion().getTag());
							}
						}
					});
					if (CollectionUtils.isNotEmpty(promoteAsTags)) {
						Map<String, com.vf.uk.dal.device.entity.MerchandisingPromotion> promotionMap = deviceDao
								.getMerchandisingPromotionsEntityFromRepo(promoteAsTags);
						deviceList.forEach(device -> {
							if (device.getPromotionsPackage() != null) {
								if (device.getPromotionsPackage().getBundlePromotions() != null && device
										.getPromotionsPackage().getBundlePromotions().getPricePromotion() != null) {
									if (promotionMap.get(device.getPromotionsPackage().getBundlePromotions()
											.getPricePromotion().getTag()) != null) {
										device.getPromotionsPackage().getBundlePromotions()
												.setPricePromotion(promotionMap.get(device.getPromotionsPackage()
														.getBundlePromotions().getPricePromotion().getTag()));
									}
									;

								}
								if (device.getPromotionsPackage().getHardwarePromotions() != null && device
										.getPromotionsPackage().getHardwarePromotions().getPricePromotion() != null) {
									if (promotionMap.get(device.getPromotionsPackage().getHardwarePromotions()
											.getPricePromotion().getTag()) != null) {

										device.getPromotionsPackage().getHardwarePromotions()
												.setPricePromotion(promotionMap.get(device.getPromotionsPackage()
														.getHardwarePromotions().getPricePromotion().getTag()));

									}
								}
							}
						});
					}
				}
			}
			if (facetedDevice != null && includeRecommendations) {
				String deviceId = CommonUtility.getSubscriptionBundleId(msisdn, Constants.SUBSCRIPTION_TYPE_MSISDN,
						registryclnt);
				LogHelper.info(this, "Getting subscription asset for msisdn " + msisdn + "  deviceID " + deviceId);

				if (StringUtils.isNotBlank(deviceId)) {
					LogHelper.info(this,
							"Getting recommendationed devices for msisdn " + msisdn + " deviceID " + deviceId);
					FacetedDevice sortedFacetedDevice = deviceRecommendationService.getRecommendedDeviceList(msisdn,
							deviceId, facetedDevice);
					if (null != sortedFacetedDevice && null != Long.valueOf(sortedFacetedDevice.getNoOfRecordsFound())
							&& sortedFacetedDevice.getNoOfRecordsFound() > 0
							&& StringUtils.isBlank(sortedFacetedDevice.getMessage())) {
						facetedDevice = sortedFacetedDevice;
					} else {
						message = "RECOMMENDATIONS_NOT_AVAILABLE_GRPL_FAILURE";
						facetedDevice.setMessage(message);
						LogHelper.info(this,
								"Failed to sort based on recommendations. Returning original device list. msisdn "
										+ msisdn + " deviceID " + deviceId);
						return facetedDevice;
					}
				} else {
					LogHelper.info(this, "Failed to get subscription asset for msisdn " + msisdn);
					message = "RECOMMENDATIONS_NOT_AVAILABLE_SUBSCRIPTION_FAILURE";
					facetedDevice.setMessage(message);
				}

			}
		}
		LogHelper.info(this, "End -->  calling  GetDeviceList in ServiceImpl");
		return facetedDevice;
	}

	/**
	 * 
	 * @param variantsList
	 * @return
	 */
	@Override
	public List<com.vf.uk.dal.device.entity.Member> getListOfMembers(List<String> variantsList) {
		com.vf.uk.dal.device.entity.Member member;
		List<com.vf.uk.dal.device.entity.Member> listOfMember = new ArrayList<>();
		for (String variants : variantsList) {
			member = new com.vf.uk.dal.device.entity.Member();
			String[] variantIdPriority = variants.split("\\|");
			member.setId(variantIdPriority[0]);
			member.setPriority(variantIdPriority[1]);
			listOfMember.add(member);
		}
		return listOfMember;
	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return
	 */
	@Override
	public String getMemeberBasedOnRules1(List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			String journeyType) {
		String leadDeviceSkuId = null;
		DaoUtils daoUtils = new DaoUtils();
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

	@Override
	public Boolean validateMemeber1(String memberId, String journeyType) {
		Boolean memberFlag = false;
		List<String> listOfProduct = new ArrayList<>();
		listOfProduct.add(memberId);
		Date startDateTime = null;
		Date endDateTime = null;
		List<ProductModel> productModel = getListOfProductModel(listOfProduct);
		if (productModel != null && !productModel.isEmpty()) {
			for (ProductModel productModel2 : productModel) {
				if (productModel2.getProductStartDate() != null) {
					try {
						startDateTime = new SimpleDateFormat(Constants.DATE_FORMAT)
								.parse(productModel2.getProductStartDate());
					} catch (ParseException e) {
						LogHelper.error(this, "Parse Exception: " + e);
					}
				}
				if (productModel2.getProductEndDate() != null) {
					try {
						endDateTime = new SimpleDateFormat(Constants.DATE_FORMAT)
								.parse(productModel2.getProductEndDate());
					} catch (ParseException ex) {
						LogHelper.error(this, "Parse Exception: " + ex);
					}
				}
				boolean preOrderableFlag = Boolean.parseBoolean(productModel2.getPreOrderable());

				if ((StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
						&& !StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)))
						&& productModel2.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						&& dateValidation(startDateTime, endDateTime, preOrderableFlag)
						&& (Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableAcq())
								&& Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableAcq()))) {
					memberFlag = true;
				} else if (StringUtils.isNotBlank(journeyType)
						&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)
						&& productModel2.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						&& dateValidation(startDateTime, endDateTime, preOrderableFlag)
						&& (Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableRet())
								&& Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableRet()))) {
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
	 */
	public Boolean dateValidation(Date startDateTime, Date endDateTime, boolean preOrderableFlag) {
		Date currentDate = new Date();
		boolean flag = false;

		if (startDateTime != null && endDateTime != null) {
			Boolean x = currentDate.before(startDateTime);
			Boolean y = preOrderableFlag;
			boolean z = x && y;

			Boolean a = currentDate.after(startDateTime);
			Boolean b = currentDate.before(endDateTime);
			Boolean c = a && b;
			if (z || c) {
				flag = true;
			}
		}
		if (startDateTime == null && endDateTime != null && currentDate.before(endDateTime)) {
			flag = true;
		}
		if (startDateTime != null && endDateTime == null && currentDate.after(startDateTime)) {
			flag = true;
		}
		if (startDateTime == null && endDateTime == null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param productGroupModel
	 * @param productGroupdetailsMap
	 * @param deviceId
	 */
	public void getProductGroupdetailsMap(ProductGroupModel productGroupModel, Map<String, GroupDetails> productGroupdetailsMap,
			String deviceId) {
		GroupDetails groupDetails = new GroupDetails();
		groupDetails.setGroupName(productGroupModel.getName());
		groupDetails.setGroupId(productGroupModel.getId());
		groupDetails.setColor(productGroupModel.getColour());
		groupDetails.size(productGroupModel.getCapacity());
		productGroupdetailsMap.put(deviceId, groupDetails);

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
		ProductGroupFacetModel productGroupFacetModel = null;
		ProductGroupFacetModel productGroupFacetModelForFacets = null;
		Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		String sortBy;
		String sortOption;
		List<String> criteriaOfSort = getSortCriteria(sortCriteria);
		sortOption = criteriaOfSort.get(0);
		sortBy = criteriaOfSort.get(1);
		if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			productGroupFacetModel = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYG, make, capacity, colour,
					operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYG, journeyType);
			productGroupFacetModelForFacets = null;
			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
			productGroupFacetModel = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYM, make, capacity, colour,
					operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYM, journeyType);
			productGroupFacetModelForFacets = null;
			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}

		}
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
				productGroupModelList.forEach(productGroupModel -> {
					if (StringUtils.isNotBlank(productGroupModel.getNonUpgradeLeadDeviceId())
							&& (StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
									&& !StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)))) {
						listOfProducts.add(productGroupModel.getNonUpgradeLeadDeviceId());
						isLeadMemberFromSolr.put("leadMember", true);
						getProductGroupdetailsMap(productGroupModel,productGroupdetailsMap,productGroupModel.getNonUpgradeLeadDeviceId());
					} else if (StringUtils.isNotBlank(productGroupModel.getUpgradeLeadDeviceId())
							&& StringUtils.isNotBlank(journeyType)
							&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)) {
						listOfProducts.add(productGroupModel.getUpgradeLeadDeviceId());
						isLeadMemberFromSolr.put("leadMember", true);
						getProductGroupdetailsMap(productGroupModel,productGroupdetailsMap,productGroupModel.getUpgradeLeadDeviceId());
					} else {
						List<String> variantsList = productGroupModel.getListOfVariants();
						if (variantsList != null && !variantsList.isEmpty()) {
							List<com.vf.uk.dal.device.entity.Member> listOfMember = getListOfMembers(variantsList);
							String leadMember = getMemeberBasedOnRules1(listOfMember, journeyType);
							if (StringUtils.isNotBlank(leadMember)) {
								groupNameWithProdId.put(leadMember, productGroupModel.getName());
								listOfProducts.add(leadMember);
								getProductGroupdetailsMap(productGroupModel,productGroupdetailsMap,leadMember);
							}
						}
					}
				});
			}
			if (listOfProducts.isEmpty()) {
				LogHelper.error(this, "Empty Lead DeviceId List Coming From Solr :  " + listOfProducts);
				throw new ApplicationException(ExceptionMessages.NO_LEAD_MEMBER_ID_COMING_FROM_SOLR);
			}
			LogHelper.error(this, "Lead DeviceId List Coming From Solr------------:  " + listOfProducts);
			List<ProductModel> listOfProductModel = getListOfProductModel(listOfProducts);
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();

			Map<String, List<OfferAppliedPriceModel>> offerPriceMap = new HashMap<>();
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap = new HashMap<>();
			Map<String, BundleAndHardwarePromotions> promotionmap = new HashMap<>();

			if (listOfProductModel != null && !listOfProductModel.isEmpty()) {
				listOfProductModel = sortListForProductModel(listOfProductModel, listOfProducts);

				listOfProductModel.forEach(productModel -> {
					if (StringUtils.isNotBlank(journeyType)
							&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)
							&& StringUtils.isNotBlank(productModel.getUpgradeLeadPlanId())
							&& productModel.getUpgradeLeadPlanId().length() == 6) {
						BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
						bundleAndHardwareTuple.setBundleId(productModel.getUpgradeLeadPlanId());
						bundleAndHardwareTuple.setHardwareId(productModel.getProductId());
						bundleHardwareTupleList.add(bundleAndHardwareTuple);
					} else if ((StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
							&& !StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)))
							&& StringUtils.isNotBlank(productModel.getNonUpgradeLeadPlanId())
							&& productModel.getNonUpgradeLeadPlanId().length() == 6) {
						BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
						bundleAndHardwareTuple.setBundleId(productModel.getNonUpgradeLeadPlanId());
						bundleAndHardwareTuple.setHardwareId(productModel.getProductId());
						bundleHardwareTupleList.add(bundleAndHardwareTuple);
					} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)
							&& (StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
									&& !StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)))) {
						BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
						bundleAndHardwareTuple.setBundleId(null);
						bundleAndHardwareTuple.setHardwareId(productModel.getProductId());
						bundleHardwareTupleList.add(bundleAndHardwareTuple);
					}
				});
				List<BundleAndHardwarePromotions> promotions = null;

				if (!bundleHardwareTupleList.isEmpty()) {
					promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
							journeyType, registryclnt);
				}
				if (promotions != null) {
					promotions.forEach(promotion -> {
						promotionmap.put(promotion.getHardwareId(), promotion);
					});
				}
				if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
					if ((StringUtils.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType))
							|| (StringUtils.isBlank(offerCode) && (StringUtils.isNotBlank(journeyType)))
									&& !StringUtils.equals(Constants.JOURNEY_TYPE_ACQUISITION, journeyType)) {
						if (StringUtils.isNotBlank(offerCode)) {
							List<MerchandisingPromotionModel> listOfMerchandisingPromotions = null;
							listOfMerchandisingPromotions = getListOfMerchandisingPromotionModel(
									Constants.OFFERCODE_PAYM, journeyType);
							MerchandisingPromotionModel merchandisingPromotionModel = listOfMerchandisingPromotions
									.stream().filter(promotionModel -> offerCode.equals(promotionModel.getTag()))
									.findAny().orElse(null);
							if (merchandisingPromotionModel != null) {
								List<OfferAppliedPriceModel> listOfOfferAppliedPrice = getListOfOfferAppliedPriceModel(
										listOfProducts, journeyType, offerCode);
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
						List<OfferAppliedPriceModel> listOfWithoutOfferAppliedPrice = getListOfOfferAppliedPriceModel(
								listOfProducts, journeyType, Constants.DATA_NOT_FOUND);
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

				}
			} else {
				LogHelper.error(this, "No Data Found for the given list of Products : " + listOfProductModel);
				throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_PRODUCT_LIST);
			}

			List<FacetField> facetFields = (null != productGroupFacetModelForFacets)
					? productGroupFacetModelForFacets.getListOfFacetsFields() : null;
			facetedDevice = DaoUtils.convertProductModelListToDeviceList(listOfProductModel, listOfProducts,
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
	 * @param objectsToOrder
	 * @param orderedObjects
	 * @return
	 */
	public List<ProductModel> sortListForProductModel(List<ProductModel> objectsToOrder, List<String> orderedObjects) {
		HashMap<String, Integer> indexMap = new HashMap<>();
		int index = 0;
		for (String object : orderedObjects) {
			indexMap.put(object, index);
			index++;
		}
		Collections.sort(objectsToOrder, new Comparator<ProductModel>() {
			public int compare(ProductModel left, ProductModel right) {
				Integer leftIndex = indexMap.get(left.getProductId());
				Integer rightIndex = indexMap.get(right.getProductId());
				if (leftIndex == null && rightIndex == null) {

					return 1;
				}
				if (leftIndex == null) {

					return 1;
				}
				if (rightIndex == null) {

					return -1;
				}
				return Integer.compare(leftIndex, rightIndex);
			}
		});
		return objectsToOrder;
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
	@Override
	public FacetedDevice getDeviceListForConditionalAccept(String productClass, String make, String model,
			String groupType, String sortCriteria, int pageNumber, int pageSize, String capacity, String colour,
			String operatingSystem, String mustHaveFeatures, Float creditLimit, String journeyType) {
		LogHelper.info(DaoUtils.class, "Entering getDeviceListForConditionalAccept ");

		FacetedDevice facetedDevice;
		ProductGroupFacetModel productGroupFacetModel;
		ProductGroupFacetModel productGroupFacetModelForFacets;
		String sortBy;
		String sortOption;
		List<CommercialProduct> ls = null;

		Map<String, BundleModel> bundleModelMap = new HashMap<>();
		Map<String, BundlePrice> bundleModelAndPriceMap = new HashMap<>();
		List<ProductModel> listOfProductModel = new ArrayList<>();
		List<String> listOfProducts = new ArrayList<>();
		List<String> criteriaOfSort = getSortCriteria(sortCriteria);
		sortOption = criteriaOfSort.get(0);
		sortBy = criteriaOfSort.get(1);
		if (groupType.equals(Constants.STRING_DEVICE_PAYG)) {
			productGroupFacetModel = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYG, make, capacity, colour,
					operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYG, journeyType);
			productGroupFacetModelForFacets = null;
			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		} else {
			productGroupFacetModel = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYM, make, capacity, colour,
					operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize, journeyType);
			List<FacetField> facetList = getProductGroupFacetModel(Constants.STRING_DEVICE_PAYM, journeyType);
			productGroupFacetModelForFacets = null;
			if (facetList != null && CollectionUtils.isNotEmpty(facetList)) {
				productGroupFacetModelForFacets = new ProductGroupFacetModel();
				productGroupFacetModelForFacets.setListOfFacetsFields(facetList);
			}
		}
		LogHelper.info(this, "Facets :"
				+ (null != productGroupFacetModelForFacets ? productGroupFacetModelForFacets.getNumFound() : null));

		List<String> listOfProductVariants = new ArrayList<>();
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

						if (listOfProductVariants != null && !listOfProductVariants.isEmpty()) {
							List<com.vf.uk.dal.device.entity.Member> listOfMember = getListOfMembers(
									listOfProductVariants);

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
						if (listOfProductVariants != null && !(listOfProductVariants.isEmpty())) {
							int nextId = 2;
							Map<String, String> deviceMap = deviceHelper.getLeadDeviceMap(listOfProductVariants);
							for (String deviceId : listOfProductVariants) {
								String nextdeviceId = deviceMap.get(nextId + "");

								nextId++;
								listOfProductsNew = new ArrayList<>();
								listOfProductsNew.add(nextdeviceId);
								listOfProductModelNew = new ArrayList<>();
								bundleModelAndPrice = deviceHelper.calculatePlan(creditLimit, listOfProductsNew,
										listOfProductModelNew);
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

				}

			}

			Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
			isLeadMemberFromSolr.put("leadMember", false);
			LogHelper.info(DaoUtils.class, "Entering convertProductModelListToDeviceList ");
			facetedDevice = DaoUtils.convertProductModelListToDeviceList(listOfProductModel, listOfProducts,
					productGroupFacetModelForFacets.getListOfFacetsFields(), groupType, ls, bundleModelMap, null, null,
					groupNameWithProdId, bundleModelAndPriceMap, null, isLeadMemberFromSolr, null, journeyType, Collections.emptyMap());
			LogHelper.info(DaoUtils.class, "exiting convertProductModelListToDeviceList ");
			facetedDevice.setNoOfRecordsFound(productGroupFacetModel.getNumFound());

		} else {
			LogHelper.error(this, "No ProductGroups Found for the given search criteria: ");
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}

		LogHelper.info(DaoUtils.class, "exiting getDeviceListForConditionalAccept ");
		return facetedDevice;
	}

	/**
	 * 
	 * @param filter
	 * @param parameter
	 * @return
	 */
	@Override
	public String getFilterForDeviceList(String filter, String parameter) {
		String newFilter;
		String[] filterArray;
		List<String> filterList = new ArrayList<>();
		if (filter == null || filter.isEmpty() || "".equals(filter) || "\"\"".equals(filter)) {
			newFilter = parameter + "*";
		} else {
			filterArray = filter.split(",");

			if (filterArray != null && filterArray.length > 0) {
				for (String filter1 : filterArray) {
					filterList.add(parameter + filter1);
				}
			}
			newFilter = convertMakeListToString(filterList);
		}
		return newFilter;
	}

	/**
	 * 
	 * @param makeList
	 * @return
	 */
	public String convertMakeListToString(List<String> makeList) {
		return String.join(" OR ", makeList);
	}

	/**
	 * 
	 * @param sortCriteria
	 * @return
	 */
	public List<String> getSortCriteria(String sortCriteria) {
		String sortBy = null;
		String sortOption = null;
		List<String> criteria = new ArrayList<>();
		if (sortCriteria != null) {
			if (sortCriteria.startsWith(Constants.SORT_HYPEN)) {
				sortOption = Constants.SORT_OPTION_DESC;
				sortBy = sortCriteria.substring(1, sortCriteria.length());
			} else if (!sortCriteria.startsWith(Constants.SORT_HYPEN)) {
				sortOption = Constants.SORT_OPTION_ASC;
				sortBy = sortCriteria;
			}
			criteria.add(sortOption);
			criteria.add(sortBy);
		}
		return criteria;
	}

	@Override
	public Insurances getInsuranceByDeviceId(String deviceId, String journeyType) {
		Insurances insurance = null;
		CommercialProduct cohProduct = getCommercialProduct(deviceId);
		if (cohProduct != null) {

			if (cohProduct.getIsDeviceProduct()
					&& cohProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {

				ProductGroups productGroups = cohProduct.getProductGroups();
				String insuranceGroupName = null;
				String insuranceGroupType = null;
				List<Member> listOfInsuranceMembers = new ArrayList<>();
				if (productGroups != null && productGroups.getProductGroup() != null
						&& !productGroups.getProductGroup().isEmpty()) {
					for (com.vf.uk.dal.device.datamodel.product.ProductGroup productGroup : productGroups
							.getProductGroup()) {
						if (productGroup.getProductGroupRole() != null && productGroup.getProductGroupRole().trim()
								.equalsIgnoreCase(Constants.STRING_COMPATIBLE_INSURANCE)) {
							insuranceGroupName = productGroup.getProductGroupName();
							insuranceGroupType = productGroup.getProductGroupRole();
						}
					}
					LogHelper.info(this, "::::: Insurance GroupName " + insuranceGroupName + " ::::::");
					if (StringUtils.isNotBlank(insuranceGroupName)) {

						Group productGroup = getProductGroupByTypeAndGroupName(insuranceGroupName, insuranceGroupType);
						if (productGroup != null && productGroup.getGroupType() != null && productGroup.getGroupType()
								.trim().equalsIgnoreCase(Constants.STRING_COMPATIBLE_INSURANCE)) {
							listOfInsuranceMembers.addAll(productGroup.getMembers());
						}

						List<String> insuranceProductList = new ArrayList<>();
						if (listOfInsuranceMembers != null && !listOfInsuranceMembers.isEmpty()) {
							for (com.vf.uk.dal.device.datamodel.productgroups.Member member : listOfInsuranceMembers) {
								if (member.getId() != null) {
									insuranceProductList.add(member.getId().trim());
								}
							}
						}
						List<CommercialProduct> listOfInsuranceProducts = getListOfCommercialProduct(
								insuranceProductList);
						List<CommercialProduct> listOfFilteredInsurances = listOfInsuranceProducts.stream()
								.filter(commercialProduct -> CommonUtility.isProductNotExpired(commercialProduct)
										&& CommonUtility.isProductJourneySpecific(commercialProduct, journeyType))
								.collect(Collectors.toList());
						if (listOfFilteredInsurances != null && !listOfFilteredInsurances.isEmpty()) {
							insurance = DaoUtils.convertCommercialProductToInsurance(listOfFilteredInsurances);
						}
					}
				}
			} else {
				LogHelper.error(this, "Given DeviceId is not ProductClass Handset  :" + deviceId);
				throw new ApplicationException(ExceptionMessages.DEVICE_ID_NOT_HANDSET);
			}
		} else {
			LogHelper.error(this, "No data found for given Device Id :" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID);
		}
		if (insurance != null && !insurance.getInsuranceList().isEmpty()) {
			getFormattedPriceForGetCompatibleInsurances(insurance);
			insurance.setMinCost(FormatPrice(insurance.getMinCost()));
		} else {
			LogHelper.error(this, "No Compatible Insurances found for given device Id" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID);
		}
		return insurance;
	}

	public Insurances getFormattedPriceForGetCompatibleInsurances(Insurances insurances) {

		if (insurances.getInsuranceList() != null && !insurances.getInsuranceList().isEmpty()) {
			List<Insurance> insuranceList = insurances.getInsuranceList();
			for (Insurance insurance : insuranceList) {
				if (insurance.getPrice() != null) {
					Price price = insurance.getPrice();
					if (StringUtils.isNotBlank(price.getNet())) {
						price.setNet(FormatPrice(price.getNet()));
					}
					if (StringUtils.isNotBlank(price.getVat())) {
						price.setVat(FormatPrice(price.getVat()));
					}
					if (StringUtils.isNotBlank(price.getGross())) {
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
			bundleDetailsWithFullDuration = removeWithoutFullDurtnPlans(bundleDetails);
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
	 * 
	 * @param bundleDetails
	 * @return
	 */
	public BundleDetails removeWithoutFullDurtnPlans(BundleDetails bundleDetails) {
		BundleDetails bundleDetailsWithFullDuration = new BundleDetails();
		List<BundleHeader> bundleHeaderlist = new ArrayList<>();
		Iterator<BundleHeader> it = bundleDetails.getPlanList().iterator();

		while (it.hasNext()) {
			boolean flag = false;
			BundleHeader bundleHeader = it.next();
			BundlePrice bundlePrice = bundleHeader.getPriceInfo().getBundlePrice();
			if (bundlePrice != null) {
				com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotion = bundlePrice
						.getMerchandisingPromotions();
				if (merchandisingPromotion != null && StringUtils.containsIgnoreCase(Constants.FULL_DURATION,
						merchandisingPromotion.getMpType())) {
					flag = true;
				} else {
					flag = true;
				}
			}
			if (flag) {
				bundleHeaderlist.add(bundleHeader);
			}
		}
		bundleDetailsWithFullDuration.setPlanList(bundleHeaderlist);
		return bundleDetailsWithFullDuration;
	}

	/**
	 * Returns Device review details
	 * 
	 * @param deviceId
	 * @return
	 */
	@Override
	public JSONObject getDeviceReviewDetails(String deviceId) {
		JSONObject jsonObject = null;
		String deviceIdMdfd = CommonUtility.appendPrefixString(deviceId);
		LogHelper.info(this, "::::: deviceIdMdfd :: " + deviceIdMdfd + ":::::");
		String response = deviceDao.getDeviceReviewDetails(deviceIdMdfd);
		if (StringUtils.isNotBlank(response)) {
			jsonObject = CommonUtility.getJSONFromString(response);
		} else {
			LogHelper.error(this, "No reviews found");
			throw new ApplicationException(ExceptionMessages.NO_REVIEWS_FOUND);
		}
		return jsonObject;
	}

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param groupType
	 * 
	 */
	@Override
	@Async
	public CompletableFuture<Integer> cacheDeviceTile(String groupType, String jobId, String version) {
		Constants.CATALOG_VERSION.set(version);
		int i = 0;
		List<OfferAppliedPriceDetails> offerAppliedPrices = new ArrayList<>();

		boolean exceptionFlag = false;
		List<DevicePreCalculatedData> devicePreCalculatedData = new ArrayList<>();
		List<DevicePreCalculatedData> devicePreCalculatedDataForPayG = null;
		try {
			deviceTileCacheDAO.beginTransaction();
			if (StringUtils.containsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYM)) {
				devicePreCalculatedData = getDeviceListFromPricing(Constants.STRING_DEVICE_PAYM);
			}
			if (StringUtils.containsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYG)) {
				devicePreCalculatedDataForPayG = getDeviceListFromPricingForPayG(Constants.STRING_DEVICE_PAYG);
				devicePreCalculatedData.addAll(devicePreCalculatedDataForPayG);
			}
			if (devicePreCalculatedData != null && !devicePreCalculatedData.isEmpty()) {
				i = deviceTileCacheDAO.saveDeviceListPreCalcData(devicePreCalculatedData);
				devicePreCalculatedData.forEach(deviceData -> {
					if (deviceData.getPriceInfo() != null && deviceData.getPriceInfo().getOfferAppliedPrices() != null
							&& !deviceData.getPriceInfo().getOfferAppliedPrices().isEmpty()) {
						offerAppliedPrices.addAll(deviceData.getPriceInfo().getOfferAppliedPrices());
					}
				});
				if (!offerAppliedPrices.isEmpty()) {
					deviceTileCacheDAO.saveDeviceListILSCalcData(offerAppliedPrices);
				}

			} else {
				LogHelper.error(this, jobId + "==>No Device Pre Calculated Data found To Store");
				exceptionFlag = true;
				throw new ApplicationException(ExceptionMessages.NO_DEVICE_PRE_CALCULATED_DATA);
			}
			if (i > 0) {
				devicePreCalculatedData.forEach(deviceData -> {
					if (deviceData.getMedia() != null && !deviceData.getMedia().isEmpty()) {
						deviceTileCacheDAO.saveDeviceMediaData(deviceData.getMedia(), deviceData.getDeviceId());
					}
				});
			}
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> deviceListObjectList = DaoUtils
					.convertDevicePreCalDataToSolrData(devicePreCalculatedData);
			indexPrecalData(deviceListObjectList);
		} catch (Exception e) {
			exceptionFlag = true;
			LogHelper.error(this, jobId + "==>" + e);
			deviceTileCacheDAO.rollBackTransaction();
		} finally {
			if (exceptionFlag) {
				deviceDao.updateCacheDeviceToDb(jobId, "FAILED");
			} else {
				deviceDao.updateCacheDeviceToDb(jobId, "SUCCESS");
			}
			deviceTileCacheDAO.endTransaction();
		}
		return CompletableFuture.completedFuture(i);
	}

	@Override
	public void indexPrecalData(
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> preCalcDataList) {
		try {
			Map<String, CacheProductGroupModel> productModelMap = new HashMap<>();
			for (com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData deviceListObject : preCalcDataList)
			{
				CacheProductModel productModel = new CacheProductModel();

				String productId = deviceListObject.getDeviceId();
				productModel.setId(productId);
				productModel.setRating(deviceListObject.getRating());
				productModel.setLeadPlanId(deviceListObject.getLeadPlanId());
				productModel.setProductGroupName(deviceListObject.getProductGroupName());
				productModel.setProductGroupId(deviceListObject.getProductGroupId());
				productModel.setUpgradeLeadPlanId(deviceListObject.getUpgradeLeadPlanId());
				productModel.setNonUpgradeLeadPlanId(deviceListObject.getNonUpgradeLeadPlanId());

				if (deviceListObject.getPriceInfo() != null
						&& deviceListObject.getPriceInfo().getHardwarePrice() != null
						&& deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice() != null) {
					productModel.setOneOffGrossPrice(
							deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross());
					productModel.setOneOffNetPrice(
							deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getNet());
					productModel.setOneOffVatPrice(
							deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getVat());
				}
				if (deviceListObject.getPriceInfo() != null
						&& deviceListObject.getPriceInfo().getHardwarePrice() != null
						&& deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null) {
					productModel.setOneOffDiscountedGrossPrice(
							deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross());
					productModel.setOneOffDiscountedNetPrice(
							deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getNet());
					productModel.setOneOffDiscountedVatPrice(
							deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getVat());
				}

				if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getBundlePrice() != null
						&& deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice() != null) {
					productModel.setBundleMonthlyPriceGross(
							deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross());
					productModel.setBundleMonthlyPriceNet(
							deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice().getNet());
					productModel.setBundleMonthlyPriceVat(
							deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice().getVat());
				}
				if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getBundlePrice() != null
						&& deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null) {
					productModel.setBundleMonthlyDiscPriceGross(
							deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross());
					productModel.setBundleMonthlyDiscPriceNet(
							deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getNet());
					productModel.setBundleMonthlyDiscPriceVat(
							deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getVat());
				}
				List<String> merchandisngList = new ArrayList<>();
				if (deviceListObject.getMedia() != null && CollectionUtils.isNotEmpty(deviceListObject.getMedia())) {
					for (com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media mediaObject : deviceListObject
							.getMedia()) {
						String media = mediaObject.getId() + "|" + mediaObject.getValue() + "|" + mediaObject.getType()
								+ "|" + mediaObject.getPromoCategory() + "|" + mediaObject.getOfferCode() + "|"
								+ mediaObject.getDescription() + "|" + mediaObject.getDiscountId();
						merchandisngList.add(media);
					}
					productModel.setMerchandisingMedia(merchandisngList);
				}
				String productIdForUpdate = Constants.STRING_OPT + Constants.STRING_PRODUCT + "_"
						+ deviceListObject.getDeviceId();
				deviceDao.getUpdateElasticSearch(productIdForUpdate, mapper.writeValueAsString(productModel));

				if (StringUtils.isNotBlank(deviceListObject.getProductGroupId())) {
					String productGroupId = deviceListObject.getProductGroupId();
					if (productModelMap.containsKey(productGroupId)) {
						CacheProductGroupModel productgroupModel = productModelMap.get(productGroupId);
						if (StringUtils.isNotBlank(deviceListObject.getLeadPlanId())) {
							productgroupModel.setLeadPlanId(deviceListObject.getLeadPlanId());
						}
						if (deviceListObject.getMinimumCost() != null) {
							if (productgroupModel.getMinimumCost() != null
									&& deviceListObject.getMinimumCost() < productgroupModel.getMinimumCost()) {
								productgroupModel.setMinimumCost(deviceListObject.getMinimumCost());
							}
						} else if (deviceListObject.getMinimumCost() != null
								&& productgroupModel.getMinimumCost() == null) {
							productgroupModel.setMinimumCost(deviceListObject.getMinimumCost());
						}
						if (StringUtils.isNotBlank(deviceListObject.getDeviceId())) {
							productgroupModel.setLeadDeviceId(deviceListObject.getDeviceId());
						}
						productgroupModel.setRating(deviceListObject.getRating());

						if (StringUtils.isNotBlank(deviceListObject.getUpgradeLeadDeviceId())) {
							productgroupModel.setUpgradeLeadDeviceId(deviceListObject.getUpgradeLeadDeviceId());
						}
						if (StringUtils.isNotBlank(deviceListObject.getNonUpgradeLeadDeviceId())) {
							productgroupModel.setNonUpgradeLeadDeviceId(deviceListObject.getNonUpgradeLeadDeviceId());
						}
						if (StringUtils.isNotBlank(deviceListObject.getUpgradeLeadPlanId())) {
							productgroupModel.setUpgradeLeadPlanId(deviceListObject.getUpgradeLeadPlanId());
						}
						if (StringUtils.isNotBlank(deviceListObject.getNonUpgradeLeadPlanId())) {
							productgroupModel.setNonUpgradeLeadPlanId(deviceListObject.getNonUpgradeLeadPlanId());
						}
						productModelMap.put(productGroupId, productgroupModel);
					} else {
						CacheProductGroupModel productgroupModel = new CacheProductGroupModel();
						productgroupModel.setId(productGroupId);
						productgroupModel.setLeadPlanId(deviceListObject.getLeadPlanId());
						productgroupModel.setMinimumCost(deviceListObject.getMinimumCost());
						productgroupModel.setLeadDeviceId(deviceListObject.getDeviceId());
						productgroupModel.setRating(deviceListObject.getRating());
						productgroupModel.setUpgradeLeadDeviceId(deviceListObject.getUpgradeLeadDeviceId());
						productgroupModel.setNonUpgradeLeadDeviceId(deviceListObject.getNonUpgradeLeadDeviceId());
						productgroupModel.setUpgradeLeadPlanId(deviceListObject.getUpgradeLeadPlanId());
						productgroupModel.setNonUpgradeLeadPlanId(deviceListObject.getNonUpgradeLeadPlanId());
						productModelMap.put(productGroupId, productgroupModel);
					}
				}
				if (deviceListObject.getPriceInfo() != null) {
					List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails> offerAppliedPrice = deviceListObject
							.getPriceInfo().getOfferAppliedPrices();
					if (offerAppliedPrice != null && CollectionUtils.isNotEmpty(offerAppliedPrice)) {
						for (com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails offerAppliedPriceObject : offerAppliedPrice) {

							CacheOfferAppliedPriceModel offerPrice = new CacheOfferAppliedPriceModel();
							if (offerAppliedPriceObject.getBundlePrice() != null
									&& offerAppliedPriceObject.getDeviceId() != null
									&& offerAppliedPriceObject.getOfferCode() != null
									&& offerAppliedPriceObject.getBundlePrice().getBundleId() != null) {
								String id = "offerApplied_" + offerAppliedPriceObject.getDeviceId() + "_"
										+ offerAppliedPriceObject.getOfferCode() + "_"
										+ offerAppliedPriceObject.getBundlePrice().getBundleId() + "_"
										+ offerAppliedPriceObject.getJourneyType();
								offerPrice.setId(id);
								offerPrice.setProductId(offerAppliedPriceObject.getDeviceId());
								offerPrice.setOfferCode(offerAppliedPriceObject.getOfferCode());
								offerPrice.setBundleId(offerAppliedPriceObject.getBundlePrice().getBundleId());
								offerPrice.setJourneyType(offerAppliedPriceObject.getJourneyType());
								com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyPrice monthlyPrice = offerAppliedPriceObject
										.getBundlePrice().getMonthlyPrice();
								com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = offerAppliedPriceObject
										.getBundlePrice().getMonthlyDiscountPrice();
								if (monthlyPrice != null) {
									offerPrice.setMonthlyGrossPrice(monthlyPrice.getGross());
									offerPrice.setMonthlyNetPrice(monthlyPrice.getNet());
									offerPrice.setMonthlyVatPrice(monthlyPrice.getVat());
								}

								if (monthlyDiscountPrice != null) {
									offerPrice.setMonthlyDiscountedGrossPrice(monthlyDiscountPrice.getGross());
									offerPrice.setMonthlyDiscountedNetPrice(monthlyDiscountPrice.getNet());
									offerPrice.setMonthlyDiscountedVatPrice(monthlyDiscountPrice.getVat());
								}

								com.vf.uk.dal.device.datamodel.merchandisingpromotion.HardwarePrice hardwarePrice = offerAppliedPriceObject
										.getHardwarePrice();
								if (hardwarePrice != null) {
									offerPrice.setHardwareId(hardwarePrice.getHardwareId());
									com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffPrice oneOffPrice = hardwarePrice
											.getOneOffPrice();
									com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice = hardwarePrice
											.getOneOffDiscountPrice();
									if (oneOffPrice != null) {
										offerPrice.setOneOffGrossPrice(oneOffPrice.getGross());
										offerPrice.setOneOffNetPrice(oneOffPrice.getNet());
										offerPrice.setOneOffVatPrice(oneOffPrice.getVat());
									}

									if (oneOffDiscountPrice != null) {
										offerPrice.setOneOffDiscountedGrossPrice(oneOffDiscountPrice.getGross());
										offerPrice.setOneOffDiscountedNetPrice(oneOffDiscountPrice.getNet());
										offerPrice.setOneOffDiscountedVatPrice(oneOffDiscountPrice.getVat());

									}
								}
								deviceDao.getIndexElasticSearch(id, mapper.writeValueAsString(offerPrice));
							}
						}
					}
				}
			}
			for (Map.Entry<String, CacheProductGroupModel> entry : productModelMap.entrySet()) {
				String productGroupId = Constants.STRING_OPT + "productGroup_" + entry.getKey();
				deviceDao.getUpdateElasticSearch(productGroupId, mapper.writeValueAsString(entry.getValue()));
			}

		} catch (Exception e) {
			LogHelper.error(this, "::::::Exception From es ::::::" + e);
		}
	}

	/**
	 * 
	 * @param groupType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DevicePreCalculatedData> getDeviceListFromPricing(String groupType) {
		List<String> deviceIds = new ArrayList<>();
		Map<String, String> minimumPriceMap = new HashMap<>();
		List<DevicePreCalculatedData> listOfProductGroupRepository = new ArrayList<>();
		DevicePreCalculatedData productGroupForDeviceListing;
		com.vf.uk.dal.device.entity.Member entityMember;
		List<Group> listOfProductGroup = getProductGroupByType(groupType);
		List<String> listOfDeviceId = new ArrayList<>();
		String minimumPrice = null;
		List<String> listOfOfferCodesForUpgrade = new ArrayList<>();

		List<String> listOfSecondLineOfferCode = new ArrayList<>();

		Set<String> listOfOfferCodes = new HashSet<>();
		List<MerchandisingPromotionModel> listOfMerchandisingPromotions = getListOfMerchandisingPromotionModel(
				Constants.OFFERCODE_PAYM, Constants.JOURNEY_TYPE_SECONDLINE_UPGRADE);
		listOfMerchandisingPromotions.forEach(promotionModel -> {
			if (StringUtils.isNotBlank(promotionModel.getTag())
					&& promotionModel.getPackageType().contains(Constants.JOURNEY_TYPE_SECONDLINE)) {
				listOfSecondLineOfferCode.add(promotionModel.getTag());
			}
			if (StringUtils.isNotBlank(promotionModel.getTag())
					&& promotionModel.getPackageType().contains(Constants.JOURNEY_TYPE_UPGRADE)) {
				listOfOfferCodesForUpgrade.add(promotionModel.getTag());
			}
			if (StringUtils.isNotBlank(promotionModel.getTag())) {
				listOfOfferCodes.add(promotionModel.getTag());
			}

		});

		Map<String, String> leadMemberMap = new HashMap<>();
		Map<String, String> leadMemberMapForUpgrade = new HashMap<>();
		Map<String, String> groupIdAndNameMap = new HashMap<>();
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderForDevice = null;
		Map<String, Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware = new HashMap<>();
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroup : listOfProductGroup) {

				List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
				String groupId = String.valueOf(productGroup.getGroupId());
				String groupname = productGroup.getName();
				if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
					for (Member member : productGroup.getMembers()) {
						entityMember = new com.vf.uk.dal.device.entity.Member();
						entityMember.setId(member.getId());
						entityMember.setPriority(String.valueOf(member.getPriority()));
						listOfDeviceGroupMember.add(entityMember);
						listOfDeviceId.add(member.getId());
						groupIdAndNameMap.put(member.getId(), groupname + "&&" + groupId);
					}
				}
				String leadMemberId = null;
				String leadMemberIdForUpgrade = null;

				leadMemberId = getMemeberBasedOnRulesForStock(listOfDeviceGroupMember,
						Constants.JOURNEY_TYPE_ACQUISITION);
				leadMemberIdForUpgrade = getMemeberBasedOnRulesForStock(listOfDeviceGroupMember,
						Constants.JOURNEY_TYPE_UPGRADE);

				if (leadMemberId != null) {
					leadMemberMap.put(leadMemberId, Constants.IS_LEAD_MEMEBER_YES);
				}
				if (leadMemberIdForUpgrade != null) {
					leadMemberMapForUpgrade.put(leadMemberIdForUpgrade, Constants.IS_LEAD_MEMEBER_YES);
				}
			}
			Map<String, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> leadPlanIdPriceMap = new HashMap<>();
			Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
			Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> groupNamePriceMap = new HashMap<>();
			Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
			Set<String> setOfCompatiblePlanIds = new HashSet<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId = new ArrayList<>();
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListJourneyAware = new HashSet<>();
			if (!listOfDeviceId.isEmpty()) {
				Map<String, String> listOfLeadPlanId = new HashMap<>();
				Map<String, List<String>> listOfCimpatiblePlanMap = new HashMap<>();
				List<CommercialProduct> listOfCommercialProduct = getListOfCommercialProduct(listOfDeviceId);
				if (listOfCommercialProduct != null && !listOfCommercialProduct.isEmpty()) {
					listOfCommercialProduct.forEach(commercialProduct -> {
						List<String> listOfCompatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds() == null
								|| commercialProduct.getListOfCompatiblePlanIds().isEmpty() ? Collections.emptyList()
										: commercialProduct.getListOfCompatiblePlanIds();
						if (!listOfCompatiblePlanIds.isEmpty()) {
							List<String> compatibleBundleIds = commercialProduct.getListOfCompatiblePlanIds();
							listOfCimpatiblePlanMap.put(commercialProduct.getId(), compatibleBundleIds);
							setOfCompatiblePlanIds.addAll(compatibleBundleIds);
							compatibleBundleIds.forEach(compatiblePlanId -> {
								BundleAndHardwareTuple bundleAndHardwareTupleLocal = new BundleAndHardwareTuple();
								bundleAndHardwareTupleLocal.setBundleId(compatiblePlanId);
								bundleAndHardwareTupleLocal.setHardwareId(commercialProduct.getId());
								bundleAndHardwareTupleListForNonLeanPlanId.add(bundleAndHardwareTupleLocal);
								bundleAndHardwareTupleListJourneyAware.add(bundleAndHardwareTupleLocal);
							});
						}
						if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
								&& listOfCompatiblePlanIds.contains(commercialProduct.getLeadPlanId())) {
							listOfLeadPlanId.put(commercialProduct.getId(), commercialProduct.getLeadPlanId());
							BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
							bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
							bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
							bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
							bundleAndHardwareTupleListJourneyAware.add(bundleAndHardwareTuple);
						}
					});
				}
				List<CommercialBundle> listOfCommercialBundle = null;
				if (!setOfCompatiblePlanIds.isEmpty()) {
					listOfCommercialBundle = getListOfCommercialBundle(new ArrayList<>(setOfCompatiblePlanIds));
				}
				if (listOfCommercialBundle != null && !listOfCommercialBundle.isEmpty()) {
					listOfCommercialBundle.forEach(

							commercialBundle -> commercialBundleMap.put(commercialBundle.getId(), commercialBundle));
				}
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForLeadPlanIds = null;
				if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
					listOfPriceForBundleAndHardwareForLeadPlanIds = CommonUtility
							.getPriceDetailsUsingBundleHarwareTrouple(bundleAndHardwareTupleList, null, null,
									registryclnt);
					if (listOfPriceForBundleAndHardwareForLeadPlanIds != null
							&& !listOfPriceForBundleAndHardwareForLeadPlanIds.isEmpty()) {
						listOfPriceForBundleAndHardwareForLeadPlanIds.forEach(priceForBundleAndHardware -> {
							if (priceForBundleAndHardware != null
									&& priceForBundleAndHardware.getHardwarePrice() != null) {
								leadPlanIdPriceMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
										priceForBundleAndHardware);
							}
						});
					}
				}
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForNonLeadPlanIds = null;
				if (bundleAndHardwareTupleListForNonLeanPlanId != null
						&& !bundleAndHardwareTupleListForNonLeanPlanId.isEmpty()) {
					listOfPriceForBundleAndHardwareForNonLeadPlanIds = CommonUtility
							.getPriceDetailsUsingBundleHarwareTrouple(bundleAndHardwareTupleListForNonLeanPlanId, null,
									null, registryclnt);
					if (listOfPriceForBundleAndHardwareForNonLeadPlanIds != null
							&& !listOfPriceForBundleAndHardwareForNonLeadPlanIds.isEmpty()) {
						listOfPriceForBundleAndHardwareForNonLeadPlanIds.forEach(priceForBundleAndHardware -> {
							if (priceForBundleAndHardware != null
									&& priceForBundleAndHardware.getHardwarePrice() != null) {
								List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> price = null;
								if (nonLeadPlanIdPriceMap
										.containsKey(priceForBundleAndHardware.getHardwarePrice().getHardwareId())) {
									price = nonLeadPlanIdPriceMap
											.get(priceForBundleAndHardware.getHardwarePrice().getHardwareId());
									price.add(priceForBundleAndHardware);
								} else {
									price = new ArrayList<>();
									price.add(priceForBundleAndHardware);
									nonLeadPlanIdPriceMap
											.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(), price);
								}
							}
						});
					}
				}
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
				Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap = new HashMap<>();
				Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
				for (String deviceId : listOfDeviceId) {
					String groupId = null;
					String groupname = null;
					String nonUpgradeLeadPlanId = null;
					String upgradeLeadPlanId = null;
					List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForGroupName = null;
					if (groupIdAndNameMap.containsKey(deviceId)) {
						String[] groupDetails = groupIdAndNameMap.get(deviceId).split("&&");
						groupname = groupDetails[0];
						groupId = groupDetails[1];
					}
					try {
						if (!listOfLeadPlanId.isEmpty() && listOfLeadPlanId.containsKey(deviceId)) {
							String bundleId = listOfLeadPlanId.get(deviceId);
							CommercialBundle coommercialbundle = commercialBundleMap.containsKey(bundleId)
									? commercialBundleMap.get(bundleId) : null;
							if (CommonUtility.isValidBundleForJourney(coommercialbundle,
									Constants.JOURNEY_TYPE_ACQUISITION)) {
								nonUpgradeLeadPlanId = bundleId;
							}
							if (CommonUtility.isValidBundleForJourney(coommercialbundle,
									Constants.JOURNEY_TYPE_UPGRADE)) {
								upgradeLeadPlanId = bundleId;
							}
						}
						if (StringUtils.isNotBlank(nonUpgradeLeadPlanId) || StringUtils.isNotBlank(upgradeLeadPlanId)) {
							if (StringUtils.isBlank(nonUpgradeLeadPlanId)
									&& nonLeadPlanIdPriceMap.containsKey(deviceId)) {
								List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap
										.get(deviceId);
								DaoUtils daoutils = new DaoUtils();
								bundleHeaderForDevice = daoutils.getListOfPriceForBundleAndHardwareForCacheDevice(
										listOfPrice, commercialBundleMap, Constants.JOURNEY_TYPE_ACQUISITION);
								if (bundleHeaderForDevice != null) {
									listOfPriceForBundleAndHardware.add(bundleHeaderForDevice);
									nonUpgradeLeadPlanId = bundleHeaderForDevice.getBundlePrice().getBundleId();
									LogHelper.info(this, "Lead Plan Id Not Present "
											+ bundleHeaderForDevice.getBundlePrice().getBundleId());
									if (groupNamePriceMap.containsKey(groupname)) {
										listOfPriceForGroupName = groupNamePriceMap.get(groupname);
										listOfPriceForGroupName.add(bundleHeaderForDevice);

									} else {
										listOfPriceForGroupName = new ArrayList<>();
										listOfPriceForGroupName.add(bundleHeaderForDevice);
										groupNamePriceMap.put(groupname, listOfPriceForGroupName);
									}
								}
							}
							if (StringUtils.isBlank(upgradeLeadPlanId) && nonLeadPlanIdPriceMap.containsKey(deviceId)) {
								List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap
										.get(deviceId);
								DaoUtils daoutils = new DaoUtils();
								com.vf.uk.dal.utility.entity.PriceForBundleAndHardware upgradeCompatiblePlan = daoutils
										.getListOfPriceForBundleAndHardwareForCacheDevice(listOfPrice,
												commercialBundleMap, Constants.JOURNEY_TYPE_UPGRADE);
								if (upgradeCompatiblePlan != null) {
									upgradeLeadPlanId = upgradeCompatiblePlan.getBundlePrice().getBundleId();
								}

							}
							LogHelper.info(this, "Lead Plan Id Present " + nonUpgradeLeadPlanId);
							if (StringUtils.isNotBlank(nonUpgradeLeadPlanId) && !leadPlanIdPriceMap.isEmpty()
									&& leadPlanIdPriceMap.containsKey(deviceId)) {
								listOfPriceForBundleAndHardware.add(leadPlanIdPriceMap.get(deviceId));
								if (groupNamePriceMap.containsKey(groupname)) {
									listOfPriceForGroupName = groupNamePriceMap.get(groupname);
									listOfPriceForGroupName.add(leadPlanIdPriceMap.get(deviceId));
								} else {
									listOfPriceForGroupName = new ArrayList<>();
									listOfPriceForGroupName.add(leadPlanIdPriceMap.get(deviceId));
									groupNamePriceMap.put(groupname, listOfPriceForGroupName);
								}
							}
						} else {

							if (nonLeadPlanIdPriceMap.containsKey(deviceId)) {
								List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap
										.get(deviceId);
								DaoUtils daoutils = new DaoUtils();
								bundleHeaderForDevice = daoutils.getListOfPriceForBundleAndHardwareForCacheDevice(
										listOfPrice, commercialBundleMap, Constants.JOURNEY_TYPE_ACQUISITION);
								com.vf.uk.dal.utility.entity.PriceForBundleAndHardware upgradeCompatiblePlan = daoutils
										.getListOfPriceForBundleAndHardwareForCacheDevice(listOfPrice,
												commercialBundleMap, Constants.JOURNEY_TYPE_UPGRADE);
								if (upgradeCompatiblePlan != null) {
									upgradeLeadPlanId = upgradeCompatiblePlan.getBundlePrice().getBundleId();
								}

								if (bundleHeaderForDevice != null) {
									listOfPriceForBundleAndHardware.add(bundleHeaderForDevice);
									nonUpgradeLeadPlanId = bundleHeaderForDevice.getBundlePrice().getBundleId();
									LogHelper.info(this, "Lead Plan Id Not Present "
											+ bundleHeaderForDevice.getBundlePrice().getBundleId());
									if (groupNamePriceMap.containsKey(groupname)) {
										listOfPriceForGroupName = groupNamePriceMap.get(groupname);
										listOfPriceForGroupName.add(bundleHeaderForDevice);

									} else {
										listOfPriceForGroupName = new ArrayList<>();
										listOfPriceForGroupName.add(bundleHeaderForDevice);
										groupNamePriceMap.put(groupname, listOfPriceForGroupName);
									}
								}
							}
						}
						// ILS OfferCode
						if (nonUpgradeLeadPlanId != null && listOfCimpatiblePlanMap.containsKey(deviceId)) {
							List<String> deviceSpecificCompatiblePlan = null;
							if (listOfCimpatiblePlanMap.containsKey(deviceId)) {
								deviceSpecificCompatiblePlan = listOfCimpatiblePlanMap.get(deviceId);
								if (deviceSpecificCompatiblePlan != null && !deviceSpecificCompatiblePlan.isEmpty()) {
									deviceSpecificCompatiblePlan.forEach(planId -> {
										CommercialBundle commercialBundle = commercialBundleMap.get(planId);
										String bundleId = commercialBundle.getId();
										List<String> listOfPromoteAs = commercialBundle.getPromoteAs() == null
												? Collections.emptyList()
												: commercialBundle.getPromoteAs().getPromotionName();
										if (listOfPromoteAs != null && !listOfPromoteAs.isEmpty()) {
											listOfPromoteAs.forEach(promoteAs -> {
												if (listOfOfferCodes.contains(promoteAs)) {
													List<BundleAndHardwareTuple> bundleAndHardwareTupleListOffer = null;
													BundleAndHardwareTuple bundleAndHardwareTupleOffer = new BundleAndHardwareTuple();
													bundleAndHardwareTupleOffer.setBundleId(bundleId);
													bundleAndHardwareTupleOffer.setHardwareId(deviceId);
													if (bundleHardwareTroupleMap.containsKey(promoteAs)) {
														bundleAndHardwareTupleListOffer = bundleHardwareTroupleMap
																.get(promoteAs);
														bundleAndHardwareTupleListOffer
																.add(bundleAndHardwareTupleOffer);
													} else {
														bundleAndHardwareTupleListOffer = new ArrayList<>();
														bundleAndHardwareTupleListOffer
																.add(bundleAndHardwareTupleOffer);
														bundleHardwareTroupleMap.put(promoteAs,
																bundleAndHardwareTupleListOffer);
													}
												}
											});
										}
									});
								}
							}
						}
						productGroupForDeviceListing = DaoUtils
								.convertBundleHeaderForDeviceToProductGroupForDeviceListing(deviceId,
										nonUpgradeLeadPlanId, groupname, groupId, listOfPriceForBundleAndHardware,
										leadMemberMap, iLSPriceMap, leadMemberMapForUpgrade, upgradeLeadPlanId,
										groupType);
						if (productGroupForDeviceListing != null) {
							deviceIds.add(productGroupForDeviceListing.getDeviceId());
							listOfProductGroupRepository.add(productGroupForDeviceListing);
						}
						listOfPriceForBundleAndHardware.clear();
					} catch (Exception e) {
						listOfPriceForBundleAndHardware.clear();
						LogHelper.error(this, " Exception occured when call happen to compatible bundles api: " + e);
					}
				}
				Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap = new HashMap<>();
				if (!bundleHardwareTroupleMap.isEmpty()) {
					String jouneyType = null;
					for (Entry<String, List<BundleAndHardwareTuple>> entry : bundleHardwareTroupleMap.entrySet()) {
						if (entry.getValue() != null && !entry.getValue().isEmpty()) {
							Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> iLSPriceMapLocalMain = new HashMap<>();
							if ((listOfOfferCodesForUpgrade.contains(entry.getKey())
									&& listOfSecondLineOfferCode.contains(entry.getKey()))
									|| (listOfOfferCodesForUpgrade.contains(entry.getKey()))) {
								jouneyType = Constants.JOURNEY_TYPE_UPGRADE;
							} else if (listOfSecondLineOfferCode.contains(entry.getKey())) {
								jouneyType = Constants.JOURNEY_TYPE_SECONDLINE;
							}
							List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForOffer = CommonUtility
									.getPriceDetailsUsingBundleHarwareTrouple(entry.getValue(), entry.getKey(),
											jouneyType, registryclnt);
							if (listOfPriceForBundleAndHardwareForOffer != null
									&& !listOfPriceForBundleAndHardwareForOffer.isEmpty()) {
								iLSPriceMapLocalMain.put(entry.getKey(), listOfPriceForBundleAndHardwareForOffer);
								if (ilsPriceForJourneyAwareOfferCodeMap.containsKey(jouneyType)) {
									Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> iLSPriceMapLocal = ilsPriceForJourneyAwareOfferCodeMap
											.get(jouneyType);
									iLSPriceMapLocal.putAll(iLSPriceMapLocalMain);
									ilsPriceForJourneyAwareOfferCodeMap.put(jouneyType, iLSPriceMapLocal);
								} else {
									ilsPriceForJourneyAwareOfferCodeMap.put(jouneyType, iLSPriceMapLocalMain);
								}
							}
						}
					}

				}
				if (!ilsPriceForJourneyAwareOfferCodeMap.isEmpty()) {
					for (Entry<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> journeyEntry : ilsPriceForJourneyAwareOfferCodeMap
							.entrySet()) {

						Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> entireOfferedPriceMap = new HashMap<>();

						Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> ilsPriceJourney = journeyEntry
								.getValue();
						if (!ilsPriceJourney.isEmpty()) {
							for (Entry<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> entry : ilsPriceJourney
									.entrySet()) {
								Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> mapOfDevicePrice = new HashMap<>();
								if (entry.getValue() != null && !entry.getValue().isEmpty()) {
									List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> offerdPrice = entry
											.getValue();
									offerdPrice.forEach(price -> {
										List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfDevicePrice = null;
										if (price != null && price.getHardwarePrice() != null) {
											if (mapOfDevicePrice
													.containsKey(price.getHardwarePrice().getHardwareId())) {
												listOfDevicePrice = mapOfDevicePrice
														.get(price.getHardwarePrice().getHardwareId());
												listOfDevicePrice.add(price);
											} else {
												listOfDevicePrice = new ArrayList<>();
												listOfDevicePrice.add(price);
												mapOfDevicePrice.put(price.getHardwarePrice().getHardwareId(),
														listOfDevicePrice);
											}
										}
									});
								}
								entireOfferedPriceMap.put(entry.getKey(), mapOfDevicePrice);
							}
						}
						ilsOfferPriceWithJourneyAware.put(journeyEntry.getKey(), entireOfferedPriceMap);
					}
				}
				if (!groupNamePriceMap.isEmpty()) {
					for (Entry<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> entry : groupNamePriceMap
							.entrySet()) {
						if (entry.getValue() != null && !entry.getValue().isEmpty()) {
							minimumPrice = DeviceUtils.leastMonthlyPrice(entry.getValue());
						}
						minimumPriceMap.put(entry.getKey(), minimumPrice);
					}

				}
			}
			/**
			 * ILSPrice Price With Journey Without OfferCode
			 */
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade = CommonUtility
					.getPriceDetailsUsingBundleHarwareTrouple(
							new ArrayList<com.vf.uk.dal.device.entity.BundleAndHardwareTuple>(
									bundleAndHardwareTupleListJourneyAware),
							null, Constants.JOURNEY_TYPE_UPGRADE, registryclnt);

			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardwareWithoutOfferCodeForSecondLine = CommonUtility
					.getPriceDetailsUsingBundleHarwareTrouple(
							new ArrayList<com.vf.uk.dal.device.entity.BundleAndHardwareTuple>(
									bundleAndHardwareTupleListJourneyAware),
							null, Constants.JOURNEY_TYPE_SECONDLINE, registryclnt);

			Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> mapOfIlsPriceWithoutOfferCode = new HashMap<>();
			mapOfIlsPriceWithoutOfferCode.put(Constants.JOURNEY_TYPE_UPGRADE,
					DaoUtils.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade));
			mapOfIlsPriceWithoutOfferCode.put(Constants.JOURNEY_TYPE_SECONDLINE,
					DaoUtils.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForSecondLine));

			// Ratings population logic
			Map<String, String> ratingsReviewMap = deviceDao.getDeviceReviewRating(deviceIds);
			listOfProductGroupRepository.forEach(deviceDataRating -> {
				com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfo = deviceDataRating.getPriceInfo();
				Map<String, Object> offeredPriceMediaMap = DaoUtils
						.getListOfOfferAppliedPrice(deviceDataRating.getDeviceId(), ilsOfferPriceWithJourneyAware);
				List<OfferAppliedPriceDetails> iLSPrice = (List<OfferAppliedPriceDetails>) offeredPriceMediaMap
						.get("offeredPrice");
				List<com.vf.uk.dal.utility.solr.entity.Media> listOfOfferdMedia = (List<com.vf.uk.dal.utility.solr.entity.Media>) offeredPriceMediaMap
						.get("media");
				Map<String, Object> withoutOfferedPriceMediaMap = DaoUtils.getListOfIlsPriceWithoutOfferCode(
						deviceDataRating.getDeviceId(), mapOfIlsPriceWithoutOfferCode);
				List<OfferAppliedPriceDetails> iLSPriceWithoutOfferCode = (List<OfferAppliedPriceDetails>) withoutOfferedPriceMediaMap
						.get("offeredPrice");
				List<com.vf.uk.dal.utility.solr.entity.Media> listOfdMediaWithoutOfferCode = (List<com.vf.uk.dal.utility.solr.entity.Media>) withoutOfferedPriceMediaMap
						.get("media");
				if (iLSPriceWithoutOfferCode != null && !iLSPriceWithoutOfferCode.isEmpty()) {
					iLSPrice.addAll(iLSPriceWithoutOfferCode);
				}
				if (listOfdMediaWithoutOfferCode != null && !listOfdMediaWithoutOfferCode.isEmpty()) {
					listOfOfferdMedia.addAll(listOfdMediaWithoutOfferCode);
				}
				if (iLSPrice != null && !iLSPrice.isEmpty()) {
					if (priceInfo == null) {
						com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfoLocal = new com.vf.uk.dal.utility.solr.entity.PriceInfo();
						priceInfoLocal.setOfferAppliedPrices(iLSPrice);
						deviceDataRating.setPriceInfo(priceInfoLocal);
					} else {
						priceInfo.setOfferAppliedPrices(iLSPrice);
					}

				}
				if (listOfOfferdMedia != null && !listOfOfferdMedia.isEmpty()) {
					List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = deviceDataRating.getMedia();
					if (listOfMedia == null) {
						deviceDataRating.setMedia(listOfOfferdMedia);
					} else {
						listOfMedia.addAll(listOfOfferdMedia);
					}

				}
				if (minimumPriceMap.containsKey(deviceDataRating.getProductGroupName())) {
					deviceDataRating.setMinimumCost(minimumPriceMap.get(deviceDataRating.getProductGroupName()));
				}
				if (ratingsReviewMap.containsKey(CommonUtility.appendPrefixString(deviceDataRating.getDeviceId()))) {
					String avarageOverallRating = ratingsReviewMap
							.get(CommonUtility.appendPrefixString(deviceDataRating.getDeviceId()));
					if (avarageOverallRating != null
							&& !Constants.DEVICE_RATING_NA.equalsIgnoreCase(avarageOverallRating)) {
						deviceDataRating.setRating(Float.parseFloat(avarageOverallRating));
					} else {
						deviceDataRating.setRating(null);
					}
				} else {
					deviceDataRating.setRating(null);
				}
			});
		} else {
			LogHelper.error(this, "Receieved Null Values for the given product group type");
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		return listOfProductGroupRepository;

	}

	/**
	 * Identifies members based on the validation rules. and also as per stock
	 * availability
	 * 
	 * @param listOfDeviceGroupMembers
	 * @return leadDeviceSkuId
	 */
	public String getMemeberBasedOnRulesForStock(List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
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
	 * @return memberFlag
	 */
	@Override
	public Boolean validateMemeber(String memberId, String journeyType) {
		Boolean memberFlag = false;
		try {
			CommercialProduct comProduct = getCommercialProduct(memberId);
			if (comProduct != null) {
				Date startDateTime = null;
				Date endDateTime = null;
				if (comProduct.getProductAvailability() != null) {
					startDateTime = comProduct.getProductAvailability().getStart();
					endDateTime = comProduct.getProductAvailability().getEnd();
				}
				boolean preOrderableFlag = comProduct.getProductControl().isPreOrderable();
				if (Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
						&& (comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
								|| comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))
						&& DaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
						&& (comProduct.getProductControl().isIsDisplayableRet()
								&& comProduct.getProductControl().isIsSellableRet())) {
					memberFlag = true;
				} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
						&& (comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
								|| comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))
						&& DaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
						&& (comProduct.getProductControl().isIsDisplayableAcq()
								&& comProduct.getProductControl().isIsSellableAcq())) {
					memberFlag = true;
				}
			}
		} catch (Exception np) {
			LogHelper.error(this, "Invalid Data Coming From Coherence " + np);
			LogHelper.info(this, "Invalid MemberId " + memberId);
			memberFlag = false;
		}

		return memberFlag;

	}

	@Override
	public List<DeviceDetails> getListOfDeviceDetails(String deviceId, String offerCode, String journeyType) {
		List<DeviceDetails> listOfDevices;
		List<String> listOfDeviceIds;
		LogHelper.info(this, "Get the list of device details of device id(s) " + deviceId);
		if (deviceId.contains(",")) {
			String[] deviceIds = deviceId.split(",");
			listOfDeviceIds = Arrays.asList(deviceIds);
			listOfDevices = getDeviceDetailsList(listOfDeviceIds, offerCode, journeyType);
		} else {
			listOfDeviceIds = new ArrayList<>();
			listOfDeviceIds.add(deviceId);
			listOfDevices = getDeviceDetailsList(listOfDeviceIds, offerCode, journeyType);
		}
		if (listOfDevices == null || listOfDevices.isEmpty()) {
			LogHelper.error(this, "Invalid Device Id" + ExceptionMessages.INVALID_DEVICE_ID);
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
		}
		return listOfDevices;
	}

	public List<DeviceDetails> getDeviceDetailsList(List<String> listOfDeviceIds, String offerCode,
			String journeyType) {

		List<DeviceDetails> listOfDevices = new ArrayList<>();
		List<BundleAndHardwareTuple> listOfBundleAndHardwareTuple = new ArrayList<>();

		for (String deviceSkuId : listOfDeviceIds) {
			DeviceDetails deviceDetails;
			CommercialProduct commercialProduct = getCommercialProduct(deviceSkuId);
			if (commercialProduct != null && commercialProduct.getIsDeviceProduct()
					&& commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
				List<MediaLink> listOfmerchandisingMedia = new ArrayList<>();
				deviceDetails = new DeviceDetails();
				deviceDetails.setDeviceId(commercialProduct.getId());
				deviceDetails.setName(commercialProduct.getDisplayName());
				deviceDetails.setDescription(commercialProduct.getPreDesc());
				deviceDetails.setDeviceId(commercialProduct.getId());
				deviceDetails.setName(commercialProduct.getName());
				deviceDetails.setDescription(commercialProduct.getPreDesc());
				deviceDetails.setProductClass(commercialProduct.getProductClass());
				deviceDetails.setProductLines(commercialProduct.getProductLines());

				MerchandisingControl merchandisingControl = new MerchandisingControl();
				merchandisingControl
						.setIsDisplayableECare(commercialProduct.getProductControl().isIsDisplayableinLife());
				merchandisingControl.setIsSellableECare(commercialProduct.getProductControl().isIsSellableinLife());
				merchandisingControl.setIsDisplayableAcq(commercialProduct.getProductControl().isIsDisplayableAcq());
				merchandisingControl.setIsSellableRet(commercialProduct.getProductControl().isIsSellableRet());
				merchandisingControl.setIsDisplayableRet(commercialProduct.getProductControl().isIsDisplayableRet());
				merchandisingControl.setIsSellableAcq(commercialProduct.getProductControl().isIsSellableAcq());
				merchandisingControl.setIsDisplayableSavedBasket(
						commercialProduct.getProductControl().isIsDisplayableSavedBasket());
				merchandisingControl.setOrder(commercialProduct.getOrder().intValue());
				merchandisingControl.setPreorderable(commercialProduct.getProductControl().isPreOrderable());
				String dateFormat = Constants.DATE_FORMAT_COHERENCE;
				if (commercialProduct.getProductControl().getAvailableFrom() != null) {
					merchandisingControl.setAvailableFrom(CommonUtility
							.getDateToString(commercialProduct.getProductControl().getAvailableFrom(), dateFormat));
				}
				merchandisingControl.setBackorderable(commercialProduct.getProductControl().isBackOrderable());
				deviceDetails.setMerchandisingControl(merchandisingControl);

				List<MerchandisingPromotions> listOfMerchandisingPromotion = new ArrayList<>();
				for (String singlePromotion : commercialProduct.getPromoteAs().getPromotionName()) {
					MerchandisingPromotions merchandisingPromotion = new MerchandisingPromotions();
					merchandisingPromotion.setPromotionName(singlePromotion);
					listOfMerchandisingPromotion.add(merchandisingPromotion);
				}
				deviceDetails.setMerchandisingPromotion(listOfMerchandisingPromotion);

				Equipment equipment = new Equipment();
				equipment.setMake(commercialProduct.getEquipment().getMake());
				equipment.setModel(commercialProduct.getEquipment().getModel());
				deviceDetails.setEquipmentDetail(equipment);

				if (commercialProduct.getEquipment() != null) {
					deviceDetails.setProductPageURI(commercialProduct.getEquipment().getMake() + "/"
							+ commercialProduct.getEquipment().getModel());
				}
				// Preparing list for Pricing API
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				if (commercialProduct.getLeadPlanId() != null) {
					bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
					bundleAndHardwareTuple.setHardwareId(deviceSkuId);
					deviceDetails.setLeadPlanId(commercialProduct.getLeadPlanId());
					listOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
				} else {
					String leadPlanId = getLeadPlanIdForDeviceId(deviceSkuId, journeyType);
					bundleAndHardwareTuple.setBundleId(leadPlanId);
					bundleAndHardwareTuple.setHardwareId(deviceSkuId);
					deviceDetails.setLeadPlanId(leadPlanId);
					listOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
				}

				MediaLink mediaLink;
				if (commercialProduct.getListOfimageURLs() != null) {
					for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : commercialProduct
							.getListOfimageURLs()) {
						mediaLink = new MediaLink();
						mediaLink.setId(imageURL.getImageName());
						mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(imageURL.getImageURL());
						listOfmerchandisingMedia.add(mediaLink);
					}
				}
				if (commercialProduct.getListOfmediaURLs() != null) {
					for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : commercialProduct
							.getListOfmediaURLs()) {
						mediaLink = new MediaLink();
						mediaLink.setId(mediaURL.getMediaName());
						mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(mediaURL.getMediaURL());
						listOfmerchandisingMedia.add(mediaLink);
					}
				}
				CommercialBundle commercialBundle = getCommercialBundle(deviceDetails.getLeadPlanId());
				if (commercialBundle != null) {
					listOfmerchandisingMedia.addAll(mediaListForBundle(commercialBundle));
				}
				listOfmerchandisingMedia.addAll(getMediaListForDevice(commercialProduct));

				List<MediaLink> finalListOfMediaLink = new ArrayList<>();
				if (listOfmerchandisingMedia != null && !listOfmerchandisingMedia.isEmpty()) {
					for (MediaLink merchandisingMediaLink : listOfmerchandisingMedia) {
						if (merchandisingMediaLink != null && merchandisingMediaLink.getValue() != null
								&& !"".equals(merchandisingMediaLink.getValue())) {
							finalListOfMediaLink.add(merchandisingMediaLink);
						}
					}
				}

				deviceDetails.setMedia(finalListOfMediaLink);

				listOfDevices.add(deviceDetails);
			}

		}
		// Calling pricing API
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = deviceDao
				.getPriceForBundleAndHardware(listOfBundleAndHardwareTuple, offerCode, journeyType);

		LogHelper.info(this, "Setting prices and its corresponding promotions");
		settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, listOfDevices);

		return listOfDevices;
	}

	public String getLeadPlanIdForDeviceId(String deviceId, String journeyType) {
		String leadPlanId = null;
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<CoupleRelation> listOfCoupleRelationForMcs;
		List<BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		BundleHeader bundleHeaderForDevice = null;
		List<BundleHeader> listOfBundles;
		try {
			CommercialProduct commercialProduct = getCommercialProduct(deviceId);
			if (commercialProduct != null) {
				leadPlanId = commercialProduct.getLeadPlanId();
			}

			if (leadPlanId == null || leadPlanId.isEmpty()) {

				bundleDetailsForDevice = CommonUtility.getPriceDetailsForCompatibaleBundle(deviceId, journeyType,
						registryclnt);
				listOfBundles = bundleDetailsForDevice.getStandalonePlansList();
				listOfCoupleRelationForMcs = bundleDetailsForDevice.getCouplePlansList();
				listOfBundleHeaderForDevice.addAll(listOfBundles);
				listOfCoupleRelationForMcs.forEach(
						coupleRelationMcs -> listOfBundleHeaderForDevice.addAll(coupleRelationMcs.getPlanList()));

				if (listOfBundleHeaderForDevice.isEmpty()) {
					LogHelper.error(this,
							"No Compatible Bundles found for given device Id from bundles api: " + listOfBundles);

				} else {
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

					DaoUtils daoutils = new DaoUtils();
					bundleHeaderForDevice = daoutils
							.getListOfPriceForBundleAndHardwareForDevice(listOfBundleHeaderForDevice);
					if (bundleHeaderForDevice != null) {
						leadPlanId = bundleHeaderForDevice.getSkuId();
					}
				}
				return leadPlanId;
			}

		} catch (Exception e) {
			LogHelper.error(this, " Exception occured when call happen to compatible bundles api : " + e);
		}
		return leadPlanId;

	}

	public List<MediaLink> mediaListForBundle(CommercialBundle commercialBundle) {
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		if (commercialBundle.getPromoteAs() != null && commercialBundle.getPromoteAs().getPromotionName() != null
				&& !commercialBundle.getPromoteAs().getPromotionName().isEmpty()) {
			for (String promotionName : commercialBundle.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = getMerchandisingPromotion(promotionName);
				if (merchandisingPromotion != null
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_full_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_limited_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("full_duration")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("limited_time")) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag()) && CommonUtility
							.dateValidationForOffers(startDateTime, endDateTime, Constants.DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
		}
		return listOfMediaLink;
	}

	public List<MediaLink> listOfMediaLinkBasedOnMerchandising(MerchandisingPromotion merchandisingPromotion) {
		MediaLink mediaLinkForDescription;
		MediaLink mediaLinkForLabel;
		MediaLink mediaLinkForUrlGrid;
		List<MediaLink> listOfMediaLink = new ArrayList<>();

		mediaLinkForLabel = new MediaLink();
		mediaLinkForLabel.setId(merchandisingPromotion.getType() + "." + Constants.STRING_OFFERS_LABEL);
		mediaLinkForLabel.setType(Constants.STRING_TEXT_ALLOWANCE);
		mediaLinkForLabel.setValue(merchandisingPromotion.getLabel());
		listOfMediaLink.add(mediaLinkForLabel);

		mediaLinkForDescription = new MediaLink();
		mediaLinkForDescription.setId(merchandisingPromotion.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
		mediaLinkForDescription.setType(Constants.STRING_TEXT_ALLOWANCE);
		mediaLinkForDescription.setValue(merchandisingPromotion.getDescription());
		listOfMediaLink.add(mediaLinkForDescription);
		if (merchandisingPromotion.getType() != null && StringUtils.containsIgnoreCase(merchandisingPromotion.getType(),
				Constants.STRING_FOR_ENTERTAINMENT)) {
			mediaLinkForUrlGrid = new MediaLink();
			mediaLinkForUrlGrid.setId(merchandisingPromotion.getType() + "." + Constants.STRING_PROMOTION_MEDIA);
			mediaLinkForUrlGrid.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
			mediaLinkForUrlGrid.setValue(merchandisingPromotion.getPromotionMedia());
			listOfMediaLink.add(mediaLinkForUrlGrid);
		}
		return listOfMediaLink;
	}

	public List<MediaLink> getMediaListForDevice(CommercialProduct commercialProduct) {
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = getMerchandisingPromotion(promotionName);
				if (merchandisingPromotion != null
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_full_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_limited_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("full_duration")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("limited_time")) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag()) && CommonUtility
							.dateValidationForOffers(startDateTime, endDateTime, Constants.DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
		}
		return listOfMediaLink;

	}

	public void settingPriceAndPromotionsToListOfDevices(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, List<DeviceDetails> listOfDeviceDetails) {
		for (DeviceDetails deviceDetails : listOfDeviceDetails) {
			if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
				for (PriceForBundleAndHardware priceForBundleAndHardware : listOfPriceForBundleAndHardware) {
					if (priceForBundleAndHardware.getHardwarePrice() != null && priceForBundleAndHardware
							.getHardwarePrice().getHardwareId().equalsIgnoreCase(deviceDetails.getDeviceId())) {
						List<MediaLink> listOfmerchandisingMedia = new ArrayList<>();
						if (priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross().equalsIgnoreCase(
								priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross())) {
							priceForBundleAndHardware.getBundlePrice().setMonthlyDiscountPrice(null);
							priceForBundleAndHardware.setMonthlyDiscountPrice(null);
						}
						if (priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
								priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
							priceForBundleAndHardware.getHardwarePrice().setOneOffDiscountPrice(null);
							priceForBundleAndHardware.setOneOffDiscountPrice(null);
						}
						deviceDetails.setPriceInfo(priceForBundleAndHardware);
						listOfmerchandisingMedia.addAll(deviceDetails.getMedia());
						priceForBundleAndHardware.getBundlePrice().getMerchandisingPromotions();
						com.vf.uk.dal.device.entity.MerchandisingPromotion listOfHardwareMerch = priceForBundleAndHardware
								.getHardwarePrice().getMerchandisingPromotions();
						if (listOfHardwareMerch != null) {
							MediaLink mediaLinkForLabel = new MediaLink();
							mediaLinkForLabel
									.setId(listOfHardwareMerch.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
							mediaLinkForLabel.setType(Constants.STRING_TEXT);
							mediaLinkForLabel.setValue(listOfHardwareMerch.getLabel());
							listOfmerchandisingMedia.add(mediaLinkForLabel);

							MediaLink mediaLinkForDescription = new MediaLink();
							mediaLinkForDescription
									.setId(listOfHardwareMerch.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
							mediaLinkForDescription.setType(Constants.STRING_TEXT);
							mediaLinkForDescription.setValue(listOfHardwareMerch.getDescription());
							listOfmerchandisingMedia.add(mediaLinkForDescription);
						}
						com.vf.uk.dal.device.entity.MerchandisingPromotion priceBundleMerch = priceForBundleAndHardware
								.getBundlePrice().getMerchandisingPromotions();
						if (priceBundleMerch != null && priceBundleMerch.getTag() != null) {
							MerchandisingPromotion merchandisingPromotion = getMerchandisingPromotion(
									priceBundleMerch.getTag());
							if (merchandisingPromotion != null) {
								MediaLink mediaLinkForLabel = new MediaLink();
								mediaLinkForLabel
										.setId(merchandisingPromotion.getType() + "." + Constants.STRING_OFFERS_LABEL);
								mediaLinkForLabel.setType(Constants.STRING_TEXT);
								mediaLinkForLabel.setValue(merchandisingPromotion.getLabel());
								listOfmerchandisingMedia.add(mediaLinkForLabel);

								MediaLink mediaLinkForDescription = new MediaLink();
								mediaLinkForDescription.setId(
										merchandisingPromotion.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
								mediaLinkForDescription.setType(Constants.STRING_TEXT);
								mediaLinkForDescription.setValue(merchandisingPromotion.getDescription());
								listOfmerchandisingMedia.add(mediaLinkForDescription);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public CacheDeviceTileResponse insertCacheDeviceToDb() {
		return deviceDao.insertCacheDeviceToDb();
	}

	@Override
	public void updateCacheDeviceToDb(String jobId, String jobStatus) {
		deviceDao.updateCacheDeviceToDb(jobId, jobStatus);
	}

	@Override
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) {

		return deviceDao.getCacheDeviceJobStatus(jobId);
	}

	/**
	 * 
	 * @author manoj.bera
	 * @param leadPlanFromCommercialProduct
	 * @param compatiblePlans
	 * @param deviceId
	 * @return
	 */
	public Set<BundleAndHardwareTuple> getBundleHardwarePriceMap(String leadPlanFromCommercialProduct,
			List<String> compatiblePlans, String deviceId) {
		Set<BundleAndHardwareTuple> setOfBundleAndHardwareTuple = new HashSet<>();
		if (StringUtils.isNotBlank(leadPlanFromCommercialProduct)) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(leadPlanFromCommercialProduct);
			bundleAndHardwareTuple.setHardwareId(deviceId);
			setOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
		} else if (CollectionUtils.isNotEmpty(compatiblePlans)) {
			compatiblePlans.forEach(plan -> {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(plan);
				bundleAndHardwareTuple.setHardwareId(deviceId);
				setOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
			});
		}
		return setOfBundleAndHardwareTuple;
	}

	/**
	 * @author manoj.bera
	 * @param commerBundleIdMap
	 * @param leadPlanId
	 * @param journeyType
	 * @return
	 */
	public boolean isJourneySpecificLeadPlan(Map<String, CommercialBundle> commerBundleIdMap, String leadPlanId,
			String journeyType) {
		CommercialBundle commercialBundle = null;
		if (commerBundleIdMap != null) {
			commercialBundle = commerBundleIdMap.get(leadPlanId);
		} else if (StringUtils.isNotBlank(leadPlanId)) {
			commercialBundle = getCommercialBundle(leadPlanId);
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
		return sellableCheck;
	}

	/**
	 * Returns List of Device Tile based on groupType and groupName.
	 * 
	 * @param groupType
	 * @param groupName
	 * @return List<DeviceTile> performance improved by @author manoj.bera
	 */
	public List<DeviceTile> getListOfDeviceTile_Implementation(String make, String model, String groupType,
			String deviceId, String journeyTypeInput, Double creditLimit, String offerCode, String bundleId) {
		boolean isConditionalAcceptJourney = (null != creditLimit) ? true : false;
		String journeyType;
		if (StringUtils.isBlank(journeyTypeInput)
				|| (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput))) {
			journeyType = Constants.JOURNEY_TYPE_ACQUISITION;
		} else {
			journeyType = journeyTypeInput;
		}
		List<DeviceTile> listOfDeviceTile = new ArrayList<>();

		DeviceTile deviceTile = new DeviceTile();
		String groupName = null;
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();

		List<CommercialProduct> listOfCommercialProducts = null;
		com.vf.uk.dal.device.entity.Member entityMember;
		if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				|| groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)
				|| groupType.equalsIgnoreCase(Constants.STRING_DEVICE_NEARLY_NEW)
				|| groupType.equalsIgnoreCase(Constants.STRING_DATADEVICE_PAYM)
				|| groupType.equalsIgnoreCase(Constants.STRING_DATADEVICE_PAYG)) {
			LogHelper.info(this, "Start -->  calling  CommericalProduct.getByMakeAndModel");
			listOfCommercialProducts = getListOfCommercialProductByMakeAndModel(make, model);
			LogHelper.info(this, "End -->  After calling  CommericalProduct.getByMakeAndModel");

		} else {
			LogHelper.error(this, Constants.NO_DATA_FOUND_FOR_GROUP_TYPE + groupType);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		List<Group> listOfProductGroup = getProductGroupByType(groupType);
		if (groupType.equals(Constants.STRING_DEVICE_PAYG)) {
			listOfDeviceTile = getDeviceTileByMakeAndModelForPAYG(listOfCommercialProducts, listOfProductGroup, make,
					model, groupType);
		} else if (!groupType.equals(Constants.STRING_DEVICE_PAYG)) {
			List<CommercialProduct> commercialProductsMatchedMemList = new ArrayList<>();
			Map<String, CommercialProduct> commerProdMemMap = new HashMap<>();
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			Map<String, Boolean> bundleIdMap = new HashMap<>();
			Map<String, Boolean> fromPricingMap = new HashMap<>();
			Map<String, String> leadPlanIdMap = new HashMap<>();
			List<String> listofLeadPlan = new ArrayList<>();
			Set<String> listofLeadBundleId = new HashSet<>();
			if (CollectionUtils.isNotEmpty(listOfCommercialProducts)) {
				listOfCommercialProducts.forEach(commercialProduct -> {
					if ((Constants.STRING_HANDSET.equalsIgnoreCase(commercialProduct.getProductClass())
							|| Constants.STRING_DATA_DEVICE.equalsIgnoreCase(commercialProduct.getProductClass()))
							&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
							&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)) {
						// Begin User Story 9116
						String leadPlanFromCommercialProduct = commercialProduct.getLeadPlanId();
						List<String> compatiblePlans = commercialProduct.getListOfCompatiblePlanIds() == null
								? Collections.emptyList() : commercialProduct.getListOfCompatiblePlanIds();
						if (StringUtils.isNotBlank(journeyType)
								&& Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
								&& commercialProduct.getProductControl() != null
								&& commercialProduct.getProductControl().isIsSellableRet()
								&& commercialProduct.getProductControl().isIsDisplayableRet()) {
							commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
							listofLeadBundleId.addAll(compatiblePlans);
						} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
								&& commercialProduct.getProductControl() != null
								&& commercialProduct.getProductControl().isIsDisplayableAcq()
								&& commercialProduct.getProductControl().isIsSellableAcq()) {
							commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
							if (StringUtils.isNotBlank(leadPlanFromCommercialProduct)) {
								listofLeadBundleId.add(leadPlanFromCommercialProduct);
							}
							listofLeadBundleId.addAll(compatiblePlans);
						}
						// End User Story 9116
					}
				});
				List<CommercialBundle> commercialBundles = getListOfCommercialBundle(
						new ArrayList<>(listofLeadBundleId));
				commercialBundles.forEach(commercialBundle -> {
					commerBundleIdMap.put(commercialBundle.getId(), commercialBundle);
				});
				if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
					for (Group productGroup : listOfProductGroup) {
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
										fromPricingMap.put(commercialProduct.getId(), false);
										bundleIdMap.put(commercialProduct.getId(), true);
										BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
										bundleAndHardwareTuple.setBundleId(bundleId);
										bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
										bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
										leadPlanIdMap.put(member.getId(), bundleId);
										listofLeadPlan.add(bundleId);
									} else {
										bundleIdMap.put(commercialProduct.getId(), false);
										if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
												&& isJourneySpecificLeadPlan(commerBundleIdMap,
														commercialProduct.getLeadPlanId(), journeyType)
												&& commercialProduct.getListOfCompatiblePlanIds()
														.contains(commercialProduct.getLeadPlanId())) {
											Set<BundleAndHardwareTuple> setOfBundleAndHardwareTuple = getBundleHardwarePriceMap(
													commercialProduct.getLeadPlanId(), null, commercialProduct.getId());
											bundleAndHardwareTupleList.addAll(setOfBundleAndHardwareTuple);
											leadPlanIdMap.put(commercialProduct.getId(),
													commercialProduct.getLeadPlanId());
											listofLeadPlan.add(commercialProduct.getLeadPlanId());
											fromPricingMap.put(commercialProduct.getId(), false);
										} else {
											if (CollectionUtils
													.isNotEmpty(commercialProduct.getListOfCompatiblePlanIds()))

											{
												Set<BundleAndHardwareTuple> setOfBundleAndHardwareTuple = getBundleHardwarePriceMap(
														null, commercialProduct.getListOfCompatiblePlanIds(),
														commercialProduct.getId());
												fromPricingMap.put(commercialProduct.getId(), true);
												bundleAndHardwareTupleList.addAll(setOfBundleAndHardwareTuple);
											}
										}
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

					String leadMemberId = getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember, journeyType);
					if (leadMemberId != null) {
						deviceTile.setDeviceId(leadMemberId);
						Map<String, String> rating = getDeviceReviewRating_Implementation(
								new ArrayList<>(Arrays.asList(leadMemberId)));
						String avarageOverallRating = rating.containsKey(CommonUtility.appendPrefixString(leadMemberId))
								? rating.get(CommonUtility.appendPrefixString(leadMemberId)) : "na";
						LogHelper.info(this, "AvarageOverallRating for deviceId: " + leadMemberId + " Rating: "
								+ avarageOverallRating);
						deviceTile.setRating(avarageOverallRating);
					}
					/**
					 * @author manoj.bera below code for remove multiple Loops
					 *         for price
					 */
					List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
					List<BundleAndHardwarePromotions> listOfBundleAndHardPromo = null;
					Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
					Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
					if (!groupType.equals(Constants.STRING_DEVICE_PAYG)) {
						if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
							CompletableFuture<List<PriceForBundleAndHardware>> calculatePriceTask = deviceDao
									.getPriceForBundleAndHardwareListFromTupleListAsync(bundleAndHardwareTupleList,
											offerCode, journeyType, Constants.CATALOG_VERSION.get());
							CompletableFuture<List<com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions>> promotionTask = deviceDao
									.getBundleAndHardwarePromotionsListFromBundleListAsync(bundleAndHardwareTupleList,
											journeyType, Constants.CATALOG_VERSION.get());

							try {
								CompletableFuture.allOf(calculatePriceTask, promotionTask).get();
								listOfPriceForBundleAndHardware = calculatePriceTask.get();
								listOfBundleAndHardPromo = promotionTask.get();
							} catch (Exception e) {
								LogHelper.error(this, "Exception occured while executing thread pool :" + e);
								throw new ApplicationException(ExceptionMessages.ERROR_IN_FUTURE_TASK);
							}
						}
					}

					if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
						Map<String, List<BundleAndHardwarePromotions>> promotionsMap = new HashMap<>();
						Map<String, List<PriceForBundleAndHardware>> priceMap = new HashMap<>();
						List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwre = listOfPriceForBundleAndHardware;
						List<BundleAndHardwarePromotions> listOfBundleAndHardPromo1 = listOfBundleAndHardPromo;
						listOfPriceForBundleAndHardwre.forEach(price -> {
							List<PriceForBundleAndHardware> priceLocal = null;
							if (priceMap.containsKey(price.getHardwarePrice().getHardwareId())) {
								priceLocal = priceMap.get(price.getHardwarePrice().getHardwareId());
								priceLocal.add(price);
							} else {
								priceLocal = new ArrayList<>();
								priceLocal.add(price);
								priceMap.put(price.getHardwarePrice().getHardwareId(), priceLocal);
							}
						});
						listOfBundleAndHardPromo1.forEach(promotion -> {
							List<BundleAndHardwarePromotions> promotionLocal = null;
							if (promotionsMap.containsKey(promotion.getHardwareId())) {
								promotionLocal = promotionsMap.get(promotion.getHardwareId());
								promotionLocal.add(promotion);
							} else {
								promotionLocal = new ArrayList<>();
								promotionLocal.add(promotion);
								promotionsMap.put(promotion.getHardwareId(), promotionLocal);
							}
						});
						fromPricingMap.forEach((hardwareID, flag) -> {
							LogHelper.info("", "Item : " + hardwareID + " Count : " + flag);
							if (flag) {
								List<PriceForBundleAndHardware> listOfpriceForBundleANdHardware = priceMap
										.containsKey(hardwareID) ? priceMap.get(hardwareID) : null;
								PriceForBundleAndHardware priceForBundleAndHardware = identifyLowestPriceOfPlanForDevice(
										listOfpriceForBundleANdHardware, commerBundleIdMap, journeyType);
								if (priceForBundleAndHardware != null) {
									priceMapForParticularDevice.put(
											priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
											priceForBundleAndHardware);
									leadPlanIdMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
											priceForBundleAndHardware.getBundlePrice().getBundleId());
									listofLeadPlan.add(priceForBundleAndHardware.getBundlePrice().getBundleId());
								}
								if (!promotionsMap.isEmpty() && promotionsMap.containsKey(hardwareID)) {
									List<BundleAndHardwarePromotions> listOfPromotionLocal = promotionsMap
											.get(hardwareID);
									listOfPromotionLocal.forEach(promotion -> {
										if (promotion.getBundleId().equalsIgnoreCase(
												priceForBundleAndHardware.getBundlePrice().getBundleId()))
											bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion);

									});
								}
							} else {
								PriceForBundleAndHardware priceForBundleAndHardware = priceMap.containsKey(hardwareID)
										? priceMap.get(hardwareID).get(0) : null;
								;
								if (priceForBundleAndHardware != null) {
									priceMapForParticularDevice.put(
											priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
											priceForBundleAndHardware);
								}

								BundleAndHardwarePromotions promotion = promotionsMap.containsKey(hardwareID)
										? promotionsMap.get(hardwareID).get(0) : null;

								if (promotion != null) {
									bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion);
								}

							}
						});
					}
					/**
					 * @author manoj.bera promotion API calling
					 */
					Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
					if (!listofLeadPlan.isEmpty()) {
						List<CommercialBundle> comBundle = getListOfCommercialBundle(new ArrayList<>(listofLeadPlan));
						if (comBundle != null && !comBundle.isEmpty()) {
							comBundle.forEach(commercialBundle -> {
								commercialBundleMap.put(commercialBundle.getId(), commercialBundle);
							});
						}
					}
					deviceTile.setGroupName(groupName);
					deviceTile.setGroupType(groupType);
					/**
					 * @author manoj.bera For Performance improvement Using
					 *         below code
					 */
					CompletableFuture<List<DeviceSummary>> future1 = getDeviceSummery_Implementation(
							listOfDeviceGroupMember, listOfPriceForBundleAndHardware, commerProdMemMap,
							isConditionalAcceptJourney, journeyType, creditLimit, commercialBundleMap, bundleIdMap,
							bundleId, bundleAndHardwarePromotionsMap, leadPlanIdMap, groupType,
							priceMapForParticularDevice, fromPricingMap);
					List<DeviceSummary> listOfDeviceSummary;
					try {
						listOfDeviceSummary = future1.get();
					} catch (Exception e) {
						LogHelper.error(this, "Exception occured while executing thread pool :" + e);
						throw new ApplicationException(ExceptionMessages.ERROR_IN_FUTURE_TASK);
					}
					resetDeviceId_Implementation(isConditionalAcceptJourney, deviceTile, listOfDeviceSummary, deviceId);
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
				throw new ApplicationException(
						ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
			}
		}
		if (CollectionUtils.isEmpty(listOfDeviceTile)) {
			LogHelper.error(this, "No data found for given make and mmodel :" + make + " and " + model);
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}
		return listOfDeviceTile;

	}

	/**
	 * @author manoj.bera
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public PriceForBundleAndHardware identifyLowestPriceOfPlanForDevice(
			List<PriceForBundleAndHardware> listOfPriceForBundleHeaderLocal,
			Map<String, CommercialBundle> commercialbundleMap, String journeyType) {

		String gross;
		List<String> productLinesList = new ArrayList<>();
		productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		productLinesList.add(Constants.STRING_MBB_SELLABLE);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = listOfPriceForBundleHeaderLocal.stream()
				.filter(price -> CommonUtility.isValidJourneySpecificBundle(price, commercialbundleMap,
						productLinesList, journeyType))
				.collect(Collectors.toList());
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			List<PriceForBundleAndHardware> listOfOneOffPriceSorted = getAscendingOrderForOneoffPrice(
					listOfPriceForBundleAndHardware);
			if (listOfOneOffPriceSorted != null && !listOfOneOffPriceSorted.isEmpty()) {
				if (listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross = listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffPrice().getGross();
				}

				List<PriceForBundleAndHardware> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
				for (PriceForBundleAndHardware bundleAndHardwarePrice : listOfOneOffPriceSorted) {
					if (bundleAndHardwarePrice.getHardwarePrice() != null
							&& (bundleAndHardwarePrice.getHardwarePrice().getOneOffDiscountPrice() != null
									|| bundleAndHardwarePrice.getHardwarePrice().getOneOffPrice() != null)
							&& gross != null) {
						if ((bundleAndHardwarePrice.getHardwarePrice().getOneOffDiscountPrice().getGross() != null
								|| bundleAndHardwarePrice.getHardwarePrice().getOneOffPrice().getGross() != null)
								&& (gross
										.equalsIgnoreCase(bundleAndHardwarePrice.getHardwarePrice()
												.getOneOffDiscountPrice().getGross())
										|| gross.equalsIgnoreCase(bundleAndHardwarePrice.getHardwarePrice()
												.getOneOffPrice().getGross()))) {
							listOfEqualOneOffPriceForBundleHeader.add(bundleAndHardwarePrice);
						}
					}
				}
				List<PriceForBundleAndHardware> listOfBundelMonthlyPriceForBundleHeader;
				if (listOfEqualOneOffPriceForBundleHeader != null && !listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
					listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePrice(
							listOfEqualOneOffPriceForBundleHeader);
					if (listOfBundelMonthlyPriceForBundleHeader != null
							&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
						return listOfBundelMonthlyPriceForBundleHeader.get(0);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return
	 */
	public List<PriceForBundleAndHardware> getAscendingOrderForOneoffPrice(
			List<PriceForBundleAndHardware> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceList1());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceList1 implements Comparator<PriceForBundleAndHardware> {

		@Override
		public int compare(PriceForBundleAndHardware priceForBundleAndHard,
				PriceForBundleAndHardware priceForBundleAndHard1) {
			String gross = null;
			String gross1 = null;
			if (priceForBundleAndHard.getHardwarePrice() != null && priceForBundleAndHard1.getHardwarePrice() != null) {
				if (priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice() != null
						&& priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross = priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = priceForBundleAndHard.getHardwarePrice().getOneOffPrice().getGross();
				}
				if (priceForBundleAndHard1.getHardwarePrice().getOneOffDiscountPrice() != null
						&& priceForBundleAndHard1.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross1 = priceForBundleAndHard1.getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross1 = priceForBundleAndHard1.getHardwarePrice().getOneOffPrice().getGross();
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
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public List<PriceForBundleAndHardware> getAscendingOrderForBundlePrice(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware, new SortedBundlePriceList1());

		return listOfPriceForBundleAndHardware;
	}

	class SortedBundlePriceList1 implements Comparator<PriceForBundleAndHardware> {

		@Override
		public int compare(PriceForBundleAndHardware priceForBundleAndHardware,
				PriceForBundleAndHardware priceForBundleAndHardware1) {
			String gross = null;
			String gross1 = null;
			if (priceForBundleAndHardware.getBundlePrice() != null
					&& priceForBundleAndHardware1.getBundlePrice() != null) {
				if (priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
						&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
					gross = priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross = priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross();
				}
				if (priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice() != null
						&& priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
					gross1 = priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross1 = priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice().getGross();
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
	 * @param commercialProduct
	 * @param commerBundleIdMap
	 * @param journeyType
	 * @return
	 */
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardware_Implementation(
			CommercialProduct commercialProduct, Map<String, CommercialBundle> commerBundleIdMap, String journeyType) {

		BundleAndHardwareTuple bundleAndHardwareTuple;
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundles;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		List<CoupleRelation> listOfCoupleRelationForMcs;
		CommercialBundle commercialBundle = null;
		if (commerBundleIdMap != null) {
			commercialBundle = commerBundleIdMap.get(commercialProduct.getLeadPlanId());
		} else if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())) {
			commercialBundle = getCommercialBundle(commercialProduct.getLeadPlanId());
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
		if (commercialProduct.getLeadPlanId() != null
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
					List<com.vf.uk.dal.utility.entity.BundleHeader> listOfOneOffPriceForBundleHeader = getAscendingOrderForOneoffPrice_Implementation(
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
							listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePrice_Implementation(
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

	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForOneoffPrice_Implementation(
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

	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundlePrice_Implementation(
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
	 * Identifies members based on the validation rules.
	 * 
	 * @param listOfDeviceGroupMembers
	 * @return leadDeviceSkuId
	 */
	public String getMemeberBasedOnRules_Implementation(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember, String journeyType) {
		String leadDeviceSkuId = null;
		DaoUtils daoUtils = new DaoUtils();
		List<com.vf.uk.dal.device.entity.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.entity.Member member : listOfSortedMember) {
			if (validateMemeber_Implementation(member.getId(), journeyType)) {
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
	public Boolean validateMemeber_Implementation(String memberId, String journeyType) {
		Boolean memberFlag = false;

		LogHelper.info(this, " Start -->  calling  CommercialProductRepository.get");
		CommercialProduct comProduct = getCommercialProduct(memberId);
		LogHelper.info(this, " End -->  After calling  CommercialProductRepository.get");

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

	public Map<String, String> getDeviceReviewRating_Implementation(List<String> listMemberIds) {

		List<BazaarVoice> response = getReviewRatingList_Implementation(listMemberIds);
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

	/**
	 * @author manoj.bera
	 * @sprint 6.4
	 * @param listOfDeviceGroupMember
	 * @param listOfPriceForBundleAndHardwareLocal
	 * @param commerProdMemMap
	 * @param isConditionalAcceptJourney
	 * @param journeyType
	 * @param creditLimit
	 * @param commercialBundleMap
	 * @param bundleIdMap
	 * @param bundleId
	 * @param bundleAndHardwarePromotionsMap
	 * @param leadPlanIdMap
	 * @param groupType
	 * @param priceMapForParticularDevice
	 * @param fromPricingMap
	 * @return
	 */
	public CompletableFuture<List<DeviceSummary>> getDeviceSummery_Implementation(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareLocal,
			Map<String, CommercialProduct> commerProdMemMap, boolean isConditionalAcceptJourney, String journeyType,
			Double creditLimit, Map<String, CommercialBundle> commercialBundleMap, Map<String, Boolean> bundleIdMap,
			String bundleId, Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
			Map<String, String> leadPlanIdMap, String groupType,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice, Map<String, Boolean> fromPricingMap) {
		return CompletableFuture.supplyAsync(new Supplier<List<DeviceSummary>>() {

			List<DeviceSummary> listOfDeviceSummaryLocal = new ArrayList<>();
			DeviceSummary deviceSummary;

			@Override

			public List<DeviceSummary> get() {
				for (com.vf.uk.dal.device.entity.Member member : listOfDeviceGroupMember) {
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					CommercialBundle comBundle = null;
					List<BundleAndHardwarePromotions> promotions = null;
					if (isConditionalAcceptJourney && commercialProduct != null) {
						if (isLeadPlanWithinCreditLimit_Implementation(commercialProduct, creditLimit,
								listOfPriceForBundleAndHardwareLocal, journeyType)) {
							comBundle = getCommercialBundle(commercialProduct.getLeadPlanId());
						} else {
							comBundle = getLeadBundleBasedOnAllPlans_Implementation(creditLimit, commercialProduct,
									listOfPriceForBundleAndHardwareLocal, journeyType);
						}
						List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
						if (comBundle != null) {
							BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
							bundleAndHardwareTuple.setBundleId(comBundle.getId());
							bundleAndHardwareTuple.setHardwareId(member.getId());
							bundleHardwareTupleList.add(bundleAndHardwareTuple);
						}
						if (!bundleHardwareTupleList.isEmpty()) {
							promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(
									bundleHardwareTupleList, journeyType, registryclnt);
						}

					} else if (StringUtils.isNotBlank(bundleId) && commercialProduct != null
							&& bundleIdMap.get(member.getId())) {
						if (commercialBundleMap.containsKey(bundleId)) {
							comBundle = commercialBundleMap.get(bundleId);
						}
						if (bundleAndHardwarePromotionsMap.containsKey(member.getId())) {
							promotions = Arrays.asList(bundleAndHardwarePromotionsMap.get(member.getId()));
						}
					} else {
						String planId = null;
						if (!leadPlanIdMap.isEmpty() && leadPlanIdMap.containsKey(member.getId())) {
							planId = leadPlanIdMap.get(member.getId());
						}
						if (commercialBundleMap.containsKey(planId)) {
							comBundle = commercialBundleMap.get(planId);
						}
						if (bundleAndHardwarePromotionsMap.containsKey(member.getId())) {
							promotions = Arrays.asList(bundleAndHardwarePromotionsMap.get(member.getId()));
						}
					}
					PriceForBundleAndHardware priceForBundleAndHardware = null;
					if (priceMapForParticularDevice.containsKey(member.getId())) {
						priceForBundleAndHardware = priceMapForParticularDevice.get(member.getId());
					}
					deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile(memberPriority, commercialProduct,
							comBundle, priceForBundleAndHardware, promotions, groupType, isConditionalAcceptJourney,
							fromPricingMap);

					if (null != deviceSummary && commercialProduct != null) {
						isPlanAffordable_Implementation(deviceSummary, comBundle, creditLimit,
								isConditionalAcceptJourney);
						if (StringUtils.isNotBlank(bundleId))
							if (bundleIdMap.get(member.getId()))
								deviceSummary.setIsCompatible(true);
							else
								deviceSummary.setIsCompatible(false);
						listOfDeviceSummaryLocal.add(deviceSummary);
					}

				}
				return listOfDeviceSummaryLocal;
			}
		});

	}

	/**
	 * If journey is ConditionAccept and then in list of device summary the
	 * first plan which is affordable is lead device plan.
	 * 
	 * @param isConditionalAcceptJourney
	 * @param deviceTile
	 * @param listOfDeviceSummary
	 */
	private void resetDeviceId_Implementation(boolean isConditionalAcceptJourney, DeviceTile deviceTile,
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
	 * 
	 * @param listMemberIds
	 * @return
	 */
	public List<BazaarVoice> getReviewRatingList_Implementation(List<String> listMemberIds) {

		try {
			LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
			List<BazaarVoice> response = new ArrayList<>();
			for (String skuId : listMemberIds) {
				response.add(deviceDao.getBazaarVoice(skuId));
			}
			LogHelper.info(this, "End --> After calling  BazaarReviewRepository.get");
			return response;
		} catch (Exception e) {
			LogHelper.error(this, "Bazar Voice Exception: " + e);
			throw new ApplicationException(ExceptionMessages.BAZARVOICE_SERVICE_EXCEPTION);
		}
	}

	/**
	 * Check if plan is affordable as per credit limit and plan monthly price,
	 * and set flag.
	 * 
	 * @param deviceSummary
	 * @param comBundle
	 */
	public void isPlanAffordable_Implementation(DeviceSummary deviceSummary, CommercialBundle comBundle,
			Double creditLimit, boolean isConditionalAcceptJourney) {
		if (null == comBundle) {
			deviceSummary.setIsAffordable(false);
		} else if (isConditionalAcceptJourney) {
			if (null != deviceSummary.getPriceInfo() && null != deviceSummary.getPriceInfo().getBundlePrice()) {
				String discountType = DaoUtils
						.isPartialOrFullTenureDiscount(deviceSummary.getPriceInfo().getBundlePrice());
				Double monthlyPrice = getBundlePriceBasedOnDiscountDuration_Implementation(deviceSummary, discountType);

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

	/**
	 * Check if lead plan associated with commercial product is within credit
	 * limit.
	 * 
	 * @param product
	 * @param creditDetails
	 * @return
	 */
	private boolean isLeadPlanWithinCreditLimit_Implementation(CommercialProduct product, Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String journeyType) {
		List<BundleAndHardwareTuple> bundles = new ArrayList<>();

		BundleAndHardwareTuple tuple = new BundleAndHardwareTuple();
		tuple.setBundleId(product.getLeadPlanId());
		tuple.setHardwareId(product.getId());

		bundles.add(tuple);

		List<PriceForBundleAndHardware> priceForBundleAndHardwares = CommonUtility.getPriceDetails(bundles, null,
				registryclnt, journeyType);

		if (isPlanPriceWithinCreditLimit_Implementation(creditLimit, priceForBundleAndHardwares,
				product.getLeadPlanId())) {
			listOfPriceForBundleAndHardware.clear();
			listOfPriceForBundleAndHardware.addAll(priceForBundleAndHardwares);

			return true;
		} else {
			return false;
		}

	}

	/**
	 * Get lead bundle based on all plans excluding lead plan.
	 * 
	 * @param creditDetails
	 * @param commercialProduct
	 * @param commercialBundleRepository
	 * @return
	 */
	private CommercialBundle getLeadBundleBasedOnAllPlans_Implementation(Double creditLimit,
			CommercialProduct commercialProduct, List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			String journeyType) {

		if (CollectionUtils.isNotEmpty(commercialProduct.getListOfCompatiblePlanIds())) {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			List<String> compatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds();

			for (String planId : compatiblePlanIds) {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				if (StringUtils.isBlank(commercialProduct.getLeadPlanId())
						|| (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
								&& !commercialProduct.getLeadPlanId().equalsIgnoreCase(planId))) {
					bundleAndHardwareTuple.setBundleId(planId);
					bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
					bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
				}
			}
			List<PriceForBundleAndHardware> priceForBundleAndHardwares = null;
			if (CollectionUtils.isNotEmpty(bundleAndHardwareTupleList)) {
				priceForBundleAndHardwares = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, null,
						registryclnt, journeyType);
			}
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
					return getCommercialBundle(leadBundle.getBundlePrice().getBundleId());
				}

			}
		}

		return null;

	}

	@Override
	public Double getBundlePriceBasedOnDiscountDuration_Implementation(DeviceSummary deviceSummary,
			String discountType) {
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
	 * @param creditDetails
	 * @param listOfPriceForBundleAndHardware
	 */
	private boolean isPlanPriceWithinCreditLimit_Implementation(Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String bundleId) {
		if (CollectionUtils.isNotEmpty(listOfPriceForBundleAndHardware)) {
			for (PriceForBundleAndHardware priceForBundleAndHardware : listOfPriceForBundleAndHardware) {
				if (null != priceForBundleAndHardware.getBundlePrice()
						&& getDiscountTypeAndComparePrice_Implementation(creditLimit,
								priceForBundleAndHardware.getBundlePrice())
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
	private boolean getDiscountTypeAndComparePrice_Implementation(Double creditLimit,
			com.vf.uk.dal.device.entity.BundlePrice bundlePrice) {
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
	 * Returns device details based on the deviceId.
	 * 
	 * @param id
	 * @return DeviceDetails
	 */
	public DeviceDetails getDeviceDetails_Implementation(String deviceId, String journeyTypeInput, String offerCode) {
		LogHelper.info(this, "Start -->  calling  CommercialProductRepository.get");
		String journeyTypeLocal = null;
		String journeyType;
		if (StringUtils.isBlank(journeyTypeInput)
				|| (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput))) {
			journeyType = Constants.JOURNEY_TYPE_ACQUISITION;
		} else {
			journeyType = journeyTypeInput;
		}

		CommercialProduct commercialProduct = getCommercialProduct(deviceId);
		LogHelper.info(this, "End -->  After calling  CommercialProductRepository.get");
		DeviceDetails deviceDetails = new DeviceDetails();
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& (commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
						|| commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE))) {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
			if (commercialProduct.getProductLines() != null && !commercialProduct.getProductLines().isEmpty()
					&& commercialProduct.getProductLines().contains(Constants.PAYG_DEVICE)) {
				if (StringUtils.isNotBlank(journeyType)
						&& (journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE)
								|| journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE))) {
					LogHelper.error(this, "JourneyType is not compatible for given DeviceId");
					throw new ApplicationException(ExceptionMessages.INVALID_DEVICEID_JOURNEY_TYPE);
				} else if (StringUtils.isNotBlank(offerCode)) {
					LogHelper.error(this, "offerCode is not compatible for given DeviceId");
					throw new ApplicationException(ExceptionMessages.INVALID_DEVICEID_OFFER_CODE);
				} else {
					journeyTypeLocal = Constants.JOURNEY_TYPE_ACQUISITION;
					bundleAndHardwareTupleList = new ArrayList<>();
					BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
					bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
					bundleAndHardwareTuple.setBundleId(null);
					bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
				}
			} else {
				journeyTypeLocal = journeyType;
				bundleAndHardwareTupleList = getListOfPriceForBundleAndHardware_Implementation(commercialProduct, null,
						journeyTypeLocal);
			}
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
			if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
						registryclnt, journeyTypeLocal);
			}

			String leadPlanId = null;
			if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
				leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
				LogHelper.info(this, "::::: LeadPlanId " + leadPlanId + " :::::");
			}

			LogHelper.info(this, "Start -->  calling  bundleRepository.get");

			CommercialBundle commercialBundle = null;
			if (StringUtils.isNotBlank(leadPlanId)) {
				commercialBundle = getCommercialBundle(leadPlanId);
				LogHelper.info(this, "End -->  After calling  bundleRepository.get");

			}

			List<BundleAndHardwarePromotions> promotions = null;
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			if (commercialBundle != null) {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(commercialBundle.getId());
				bundleAndHardwareTuple.setHardwareId(deviceId);
				bundleHardwareTupleList.add(bundleAndHardwareTuple);
			} else if (commercialProduct.getProductLines() != null && !commercialProduct.getProductLines().isEmpty()
					&& commercialProduct.getProductLines().contains(Constants.PAYG_DEVICE)) {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(null);
				bundleAndHardwareTuple.setHardwareId(deviceId);
				bundleHardwareTupleList.add(bundleAndHardwareTuple);
			}
			if (!bundleHardwareTupleList.isEmpty()) {
				promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
						journeyTypeLocal, registryclnt);
			}
			if (StringUtils.isNotBlank(journeyTypeLocal)
					&& Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyTypeLocal)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsSellableRet()
					&& commercialProduct.getProductControl().isIsDisplayableRet()) {
				deviceDetails = DaoUtils.convertCoherenceDeviceToDeviceDetails(commercialProduct,
						listOfPriceForBundleAndHardware, promotions);
			} else if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyTypeLocal)
					&& commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsDisplayableAcq()
					&& commercialProduct.getProductControl().isIsSellableAcq()) {
				deviceDetails = DaoUtils.convertCoherenceDeviceToDeviceDetails(commercialProduct,
						listOfPriceForBundleAndHardware, promotions);
			} else {
				LogHelper.error(this, "No data found for given journeyType :" + deviceId);
				throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
			}
			if (StringUtils.isNotEmpty(offerCode) && StringUtils.isNotEmpty(journeyTypeLocal)) {
				deviceDetails.setValidOffer(
						validateOfferValidForDevice_Implementation(commercialProduct, journeyTypeLocal, offerCode));
			}

		} else {
			LogHelper.error(this, "No data found for given Device Id :" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}
		return deviceDetails;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public boolean validateOfferValidForDevice_Implementation(CommercialProduct commercialProduct, String journeyType,
			String offerCode) {
		List<String> offerCodes = new ArrayList<>();
		boolean validOffer = false;

		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			LogHelper.info(this, "Start -->  calling  MerchandisingPromotion.get");
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = getMerchandisingPromotion(promotionName);
				if (merchandisingPromotion != null) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							Constants.DATE_FORMAT_COHERENCE);
					String promotionPackageType = merchandisingPromotion.getCondition().getPackageType();
					List<String> promotionPackagesList = new ArrayList<>();
					if (StringUtils.isNotEmpty(promotionPackageType)) {
						promotionPackagesList = Arrays.asList(promotionPackageType.toLowerCase().split(","));
					}

					LogHelper.info(this, ":::::::: MERCHE_PROMOTION_TAG :::: " + merchandisingPromotion.getTag()
							+ "::::: START DATE :: " + startDateTime + ":::: END DATE ::: " + endDateTime + " :::: ");
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag())
							&& dateValidationForOffers_Implementation(startDateTime, endDateTime,
									Constants.DATE_FORMAT_COHERENCE)
							&& promotionPackagesList.contains(journeyType.toLowerCase())) {
						offerCodes.add(promotionName);
					}
				}
			}
			LogHelper.info(this, "End -->  After calling  MerchandisingPromotion.get");
		}
		validOffer = offerCodes.contains(offerCode) ? true : false;
		return validOffer;
	}

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return flag
	 */
	public Boolean dateValidationForOffers_Implementation(String startDateTime, String endDateTime,
			String strDateFormat) {
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
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public List<AccessoryTileGroup> getAccessoriesOfDevice_Implementation(String deviceId, String journeyType,
			String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTile = new ArrayList<>();

		CommercialProduct commercialProduct = getCommercialProduct(deviceId);
		LogHelper.info(this, "End -->  After calling  CommercialProductRepository.get");

		if (commercialProduct != null && commercialProduct.getId() != null) {

			if (commercialProduct.getIsDeviceProduct()
					&& commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {

				LogHelper.info(this, "Start -->  calling  CommercialProduct.getProductGroups");
				ProductGroups productGroups = commercialProduct.getProductGroups();
				LogHelper.info(this, "End -->  After calling  CommercialProduct.getProductGroups");

				List<String> listOfDeviceGroupName = new ArrayList<>();
				List<String> finalAccessoryList = new ArrayList<>();
				if (productGroups != null && productGroups.getProductGroup() != null
						&& !productGroups.getProductGroup().isEmpty()) {
					for (com.vf.uk.dal.device.datamodel.product.ProductGroup productGroup : productGroups
							.getProductGroup()) {
						if (productGroup.getProductGroupRole()
								.equalsIgnoreCase(Constants.STRING_COMPATIBLE_ACCESSORIES)) {
							listOfDeviceGroupName.add(productGroup.getProductGroupName());
						}
					}

					if (listOfDeviceGroupName.isEmpty()) {
						LogHelper.error(this, " No Compatible Accessories found for given device Id:" + deviceId);
						throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
					}

					Map<String, List<String>> mapForGroupName = new LinkedHashMap<>();
					List<Group> listOfProductGroup = getProductGroupByListOfGroupName(listOfDeviceGroupName);
					listOfProductGroup = getGroupBasedOnPriority(listOfProductGroup);
					for (Group productGroup : listOfProductGroup) {
						List<Member> listOfAccesoriesMembers = new ArrayList<>();
						if (productGroup != null && StringUtils.containsIgnoreCase(Constants.STRING_ACCESSORY,
								productGroup.getGroupType())) {
							listOfAccesoriesMembers.addAll(productGroup.getMembers());
							if (!listOfAccesoriesMembers.isEmpty()) {
								listOfAccesoriesMembers = getAccessoryMembersBasedOnPriority_Implementation(
										listOfAccesoriesMembers);
							}
						}

						List<String> accessoryList = new ArrayList<>();
						if (listOfAccesoriesMembers != null && !listOfAccesoriesMembers.isEmpty()) {
							for (com.vf.uk.dal.device.datamodel.productgroups.Member member : listOfAccesoriesMembers) {
								if (member.getId() != null) {
									accessoryList.add(member.getId().trim());
								}
							}
							mapForGroupName.put(productGroup.getName(), accessoryList);
							finalAccessoryList.addAll(accessoryList);
						}
					}
					LogHelper.info(this, "Start -->   calling  CommercialProduct.getAll From ES");
					List<CommercialProduct> comercialProductList = getListOfCommercialProduct(finalAccessoryList);
					List<CommercialProduct> listOfFilteredAccessories = comercialProductList.stream()
							.filter(commercialProductAccessories -> CommonUtility
									.isProductNotExpired(commercialProductAccessories)
									&& CommonUtility.isProductJourneySpecific(commercialProductAccessories,
											journeyType))
							.collect(Collectors.toList());

					List<String> listOfValidAccesoryIds = listOfFilteredAccessories.stream().filter(Objects::nonNull)
							.map(CommercialProduct::getId).filter(Objects::nonNull).collect(Collectors.toList());

					BundleDeviceAndProductsList bundleDeviceAndProductsList = new BundleDeviceAndProductsList();
					bundleDeviceAndProductsList.setAccessoryList(listOfValidAccesoryIds);
					bundleDeviceAndProductsList.setDeviceId(deviceId);
					bundleDeviceAndProductsList.setExtraList(new ArrayList<>());
					bundleDeviceAndProductsList.setOfferCode(offerCode);
					bundleDeviceAndProductsList.setPackageType(journeyType);
					PriceForProduct priceForProduct = null;
					if (bundleDeviceAndProductsList != null) {
						priceForProduct = CommonUtility.getAccessoryPriceDetails(bundleDeviceAndProductsList,
								registryclnt);
					}

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
							Accessory accessory = null;
							if (mapforCommercialProduct.containsKey(hardwareId)
									&& mapforPrice.containsKey(hardwareId)) {
								accessory = DaoUtils.convertCoherenceAccesoryToAccessory(
										mapforCommercialProduct.get(hardwareId), mapforPrice.get(hardwareId));
							}
							if (accessory != null) {
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
				LogHelper.error(this, "Given DeviceId is not ProductClass Handset  :" + deviceId);
				throw new ApplicationException(ExceptionMessages.DEVICE_ID_NOT_HANDSET);
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

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return
	 */
	public List<Member> getAccessoryMembersBasedOnPriority_Implementation(List<Member> listOfDeviceGroupMember) {
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

	/**
	 * 
	 * @param listOfCommercialProducts
	 * @param listOfProductGroup
	 * @param make
	 * @param model
	 * @param groupType=PAYG
	 * @return
	 */
	public List<DeviceTile> getDeviceTileByMakeAndModelForPAYG(List<CommercialProduct> listOfCommercialProducts,
			List<Group> listOfProductGroup, String make, String model, String groupType) {

		List<DeviceTile> listOfDeviceTile = new ArrayList<>();
		DeviceTile deviceTile = new DeviceTile();
		String groupName = null;
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
		com.vf.uk.dal.device.entity.Member entityMember;
		List<CommercialProduct> commercialProductsMatchedMemListForPAYG = new ArrayList<>();
		Map<String, CommercialProduct> commerProdMemMapPAYG = new HashMap<>();
		List<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple;
		if (!CollectionUtils.isEmpty(listOfCommercialProducts)) {
			listOfCommercialProducts.forEach(commercialProduct -> {
				if ((Constants.STRING_HANDSET.equalsIgnoreCase(commercialProduct.getProductClass())
						|| Constants.STRING_DATA_DEVICE.equalsIgnoreCase(commercialProduct.getProductClass()))
						&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
						&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)) {
					if (commercialProduct.getProductControl() != null
							&& commercialProduct.getProductControl().isIsDisplayableAcq()
							&& commercialProduct.getProductControl().isIsSellableAcq()) {
						commerProdMemMapPAYG.put(commercialProduct.getId(), commercialProduct);
					}
				}
			});
			if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
				for (Group productGroupPAYG : listOfProductGroup) {
					if (productGroupPAYG.getMembers() != null && !productGroupPAYG.getMembers().isEmpty()) {
						for (Member member : productGroupPAYG.getMembers()) {
							if (commerProdMemMapPAYG.containsKey(member.getId())) {
								groupName = productGroupPAYG.getName();
								entityMember = new com.vf.uk.dal.device.entity.Member();
								entityMember.setId(member.getId());
								entityMember.setPriority(String.valueOf(member.getPriority()));
								listOfDeviceGroupMember.add(entityMember);
								CommercialProduct commercialProduct = commerProdMemMapPAYG.get(member.getId());
								commercialProductsMatchedMemListForPAYG.add(commercialProduct);
								bundleAndHardwareTuple = new BundleAndHardwareTuple();
								bundleAndHardwareTuple.setBundleId(null);
								bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
								bundleAndHardwareTupleListPAYG.add(bundleAndHardwareTuple);
							}
						}
					}
				}
			}
		}
		if (commercialProductsMatchedMemListForPAYG != null && !commercialProductsMatchedMemListForPAYG.isEmpty()) {

			if (listOfDeviceGroupMember != null && !listOfDeviceGroupMember.isEmpty()) {

				/****
				 * Identify the member based on rules
				 */

				String leadMemberId = getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember, null);
				if (leadMemberId != null) {
					deviceTile.setDeviceId(leadMemberId);
					Map<String, String> rating = getDeviceReviewRating_Implementation(
							new ArrayList<>(Arrays.asList(leadMemberId)));
					String avarageOverallRating = rating.containsKey(CommonUtility.appendPrefixString(leadMemberId))
							? rating.get(CommonUtility.appendPrefixString(leadMemberId)) : "na";
					LogHelper.info(this,
							"AvarageOverallRating for deviceId: " + leadMemberId + " Rating: " + avarageOverallRating);
					deviceTile.setRating(avarageOverallRating);
				}

				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
				// Calling Pricing Api
				if (bundleAndHardwareTupleListPAYG != null && !bundleAndHardwareTupleListPAYG.isEmpty()) {
					listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleListPAYG,
							null, registryclnt, null);
				}
				Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
				if (!bundleAndHardwareTupleListPAYG.isEmpty()) {
					List<BundleAndHardwarePromotions> allPromotions = CommonUtility
							.getPromotionsForBundleAndHardWarePromotions(bundleAndHardwareTupleListPAYG, null,
									registryclnt);
					if (allPromotions != null && !allPromotions.isEmpty()) {
						allPromotions.forEach(promotion -> {
							bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion);
						});
					}
				}
				Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
				if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
					listOfPriceForBundleAndHardware.forEach(priceForBundleAndHardware -> {
						priceMapForParticularDevice.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
								priceForBundleAndHardware);
					});
				}

				deviceTile.setGroupName(groupName);
				deviceTile.setGroupType(groupType);
				CompletableFuture<List<DeviceSummary>> future1 = getDeviceSummery_Implementation_PAYG(
						listOfDeviceGroupMember, commerProdMemMapPAYG, groupType, priceMapForParticularDevice,
						bundleAndHardwarePromotionsMap);
				List<DeviceSummary> listOfDeviceSummary;
				try {
					listOfDeviceSummary = future1.get();
				} catch (Exception e) {
					LogHelper.error(this, "Exception occured while executing thread pool :" + e);
					throw new ApplicationException(ExceptionMessages.ERROR_IN_FUTURE_TASK);
				}
				deviceTile.setDeviceSummary(listOfDeviceSummary);
				listOfDeviceTile.add(deviceTile);
			} else {
				LogHelper.error(this, "Requested Make and Model Not found in given group type:" + groupType);
				throw new ApplicationException(
						ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
			}
		} else {
			LogHelper.error(this, "No data found for given make and mmodel :" + make + " and " + model);
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}
		return listOfDeviceTile;

	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @param commerProdMemMap
	 * @param groupType=PAYG
	 * @param priceMapForParticularDevice
	 * @return
	 */
	public CompletableFuture<List<DeviceSummary>> getDeviceSummery_Implementation_PAYG(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			Map<String, CommercialProduct> commerProdMemMap, String groupType,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice,
			Map<String, BundleAndHardwarePromotions> promotions) {
		return CompletableFuture.supplyAsync(new Supplier<List<DeviceSummary>>() {

			List<DeviceSummary> listOfDeviceSummaryLocal = new ArrayList<>();
			DeviceSummary deviceSummary;

			@Override
			public List<DeviceSummary> get() {
				for (com.vf.uk.dal.device.entity.Member member : listOfDeviceGroupMember) {
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					BundleAndHardwarePromotions promotion = promotions.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					if (commercialProduct != null) {
						PriceForBundleAndHardware priceForBundleAndHardware = null;
						if (priceMapForParticularDevice.containsKey(member.getId())) {
							priceForBundleAndHardware = priceMapForParticularDevice.get(member.getId());
						}
						deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile_PAYG(memberPriority,
								commercialProduct, priceForBundleAndHardware, groupType, promotion);
						if (deviceSummary != null) {
							listOfDeviceSummaryLocal.add(deviceSummary);
						}
					}

				}
				return listOfDeviceSummaryLocal;
			}
		});

	}

	/**
	 * @author manoj.bera
	 */
	@Override
	public List<DevicePreCalculatedData> getDeviceListFromPricingForPayG(String groupType) {
		List<String> deviceIds = new ArrayList<>();
		Map<String, String> minimumPriceMap = new HashMap<>();
		List<DevicePreCalculatedData> listOfProductGroupRepository = new ArrayList<>();
		DevicePreCalculatedData productGroupForDeviceListing;
		com.vf.uk.dal.device.entity.Member entityMember;
		List<Group> listOfProductGroup = getProductGroupByType(groupType);
		List<String> listOfDeviceId = new ArrayList<>();
		String minimumPrice = null;

		Map<String, String> leadMemberMap = new HashMap<>();
		Map<String, String> groupIdAndNameMap = new HashMap<>();
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroup : listOfProductGroup) {

				List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
				String groupId = String.valueOf(productGroup.getGroupId());
				String groupname = productGroup.getName();
				if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
					for (Member member : productGroup.getMembers()) {
						entityMember = new com.vf.uk.dal.device.entity.Member();
						entityMember.setId(member.getId());
						entityMember.setPriority(String.valueOf(member.getPriority()));
						listOfDeviceGroupMember.add(entityMember);
						listOfDeviceId.add(member.getId());
						groupIdAndNameMap.put(member.getId(), groupname + "&&" + groupId);
					}
				}
				String leadMemberId = null;
				leadMemberId = getMemeberBasedOnRulesForStock(listOfDeviceGroupMember,
						Constants.JOURNEY_TYPE_ACQUISITION);
				if (leadMemberId != null) {
					leadMemberMap.put(leadMemberId, Constants.IS_LEAD_MEMEBER_YES);
				}
			}
			Map<String, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> leadPlanIdPriceMap = new HashMap<>();
			Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> groupNamePriceMap = new HashMap<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			if (!listOfDeviceId.isEmpty()) {
				List<CommercialProduct> listOfCommercialProduct = getListOfCommercialProduct(listOfDeviceId);
				if (listOfCommercialProduct != null && !listOfCommercialProduct.isEmpty()) {
					listOfCommercialProduct.forEach(commercialProduct -> {
						BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
						bundleAndHardwareTuple.setBundleId(null);
						bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
						bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
					});
				}
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForLeadPlanIds = null;
				if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
					listOfPriceForBundleAndHardwareForLeadPlanIds = CommonUtility
							.getPriceDetailsUsingBundleHarwareTrouple(bundleAndHardwareTupleList, null, null,
									registryclnt);
					if (listOfPriceForBundleAndHardwareForLeadPlanIds != null
							&& !listOfPriceForBundleAndHardwareForLeadPlanIds.isEmpty()) {
						listOfPriceForBundleAndHardwareForLeadPlanIds.forEach(priceForBundleAndHardware -> {
							if (priceForBundleAndHardware != null
									&& priceForBundleAndHardware.getHardwarePrice() != null) {
								leadPlanIdPriceMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
										priceForBundleAndHardware);
							}
						});
					}
				}
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
				for (String deviceId : listOfDeviceId) {
					String groupId = null;
					String groupname = null;
					List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForGroupName = null;
					if (groupIdAndNameMap.containsKey(deviceId)) {
						String[] groupDetails = groupIdAndNameMap.get(deviceId).split("&&");
						groupname = groupDetails[0];
						groupId = groupDetails[1];
					}
					try {
						if (!leadPlanIdPriceMap.isEmpty() && leadPlanIdPriceMap.containsKey(deviceId)) {
							listOfPriceForBundleAndHardware.add(leadPlanIdPriceMap.get(deviceId));
							if (groupNamePriceMap.containsKey(groupname)) {
								listOfPriceForGroupName = groupNamePriceMap.get(groupname);
								listOfPriceForGroupName.add(leadPlanIdPriceMap.get(deviceId));
							} else {
								listOfPriceForGroupName = new ArrayList<>();
								listOfPriceForGroupName.add(leadPlanIdPriceMap.get(deviceId));
								groupNamePriceMap.put(groupname, listOfPriceForGroupName);
							}
						}
						productGroupForDeviceListing = DaoUtils
								.convertBundleHeaderForDeviceToProductGroupForDeviceListing(deviceId, null, groupname,
										groupId, listOfPriceForBundleAndHardware, leadMemberMap, null, null, null,
										groupType);
						if (productGroupForDeviceListing != null) {
							deviceIds.add(productGroupForDeviceListing.getDeviceId());
							listOfProductGroupRepository.add(productGroupForDeviceListing);
						}
						listOfPriceForBundleAndHardware.clear();
					} catch (Exception e) {
						listOfPriceForBundleAndHardware.clear();
						LogHelper.error(this, "Exception occured when call happen to compatible bundles api: " + e);
					}
				}
				if (!groupNamePriceMap.isEmpty()) {
					for (Entry<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> entry : groupNamePriceMap
							.entrySet()) {
						if (entry.getValue() != null && !entry.getValue().isEmpty()) {
							minimumPrice = DeviceUtils.leastMonthlyPriceForpayG(entry.getValue());
						}
						minimumPriceMap.put(entry.getKey(), minimumPrice);
					}

				}
			}

			// Ratings population logic
			Map<String, String> ratingsReviewMap = deviceDao.getDeviceReviewRating(deviceIds);
			listOfProductGroupRepository.forEach(deviceDataRating -> {
				if (minimumPriceMap.containsKey(deviceDataRating.getProductGroupName())) {
					deviceDataRating.setMinimumCost(minimumPriceMap.get(deviceDataRating.getProductGroupName()));
				}
				if (ratingsReviewMap.containsKey(CommonUtility.appendPrefixString(deviceDataRating.getDeviceId()))) {
					String avarageOverallRating = ratingsReviewMap
							.get(CommonUtility.appendPrefixString(deviceDataRating.getDeviceId()));
					if (avarageOverallRating != null
							&& !Constants.DEVICE_RATING_NA.equalsIgnoreCase(avarageOverallRating)) {
						deviceDataRating.setRating(Float.parseFloat(avarageOverallRating));
					} else {
						deviceDataRating.setRating(null);
					}
				} else {
					deviceDataRating.setRating(null);
				}
			});
		} else {
			LogHelper.error(this, "Receieved Null Values for the given product group type");
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		return listOfProductGroupRepository;

	}

	/**
	 * @author manoj.bera
	 * @param tag
	 * @return
	 */
	public com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion getMerchandisingPromotion(
			String tag) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMerchandisingBySingleTagName(tag);
		SearchResponse merchandisingResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Merchandising Promotion object response");
		return response.getMerchandisingPromotion(merchandisingResponse);
	}

	/**
	 * @author manoj.bera
	 * @param bundleId
	 * @return
	 */
	public CommercialBundle getCommercialBundle(String bundleId) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForCommercialBundle(bundleId,
				Constants.STRING_BUNDLE);
		SearchResponse commercialBundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Bundle object response");
		return response.getCommercialBundle(commercialBundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param bundleIds
	 * @return
	 */
	public List<CommercialBundle> getListOfCommercialBundle(List<String> bundleIds) {

		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForListOfCommercialBundle(bundleIds,
				Constants.STRING_BUNDLE);
		SearchResponse commercialBundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Bundle List object response");
		return response.getListOfCommercialBundleFromJson(commercialBundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param deviceIds
	 * @return
	 */
	public List<CommercialProduct> getListOfCommercialProduct(List<String> deviceIds) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForListOfCommercialProductAndCommercialBundle(deviceIds, Constants.STRING_PRODUCT);
		SearchResponse commercialListForInsuranceResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of CommercialProduct object response");
		return response.getCommercialProductFromJson(commercialListForInsuranceResponse);
	}

	/**
	 * @author manoj.bera
	 * @param deviceId
	 * @return
	 */
	public CommercialProduct getCommercialProduct(String deviceId) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForCommercialProductAndCommercialBundle(deviceId, Constants.STRING_PRODUCT);
		SearchResponse commercialProduct = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Product object response");
		return response.getCommercialProduct(commercialProduct);
	}

	/**
	 * @author manoj.bera@
	 * @param make
	 * @param model
	 * @return
	 */
	public List<CommercialProduct> getListOfCommercialProductByMakeAndModel(String make, String model) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMakeAndModel(make, model);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getCommercialProductFromJson(bundleResponse);
	}

	/**
	 * @param groupType
	 * @return
	 */
	@Override
	public List<Group> getProductGroupByType(String groupType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroup(groupType);
		SearchResponse groupResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		List<Group> listOfGroup = response.getListOfGroupFromJson(groupResponse);
		if (CollectionUtils.isEmpty(listOfGroup)) {
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_GROUP_TYPE + " : " + groupType);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		return listOfGroup;
	}

	/**
	 * 
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	public Group getProductGroupByTypeAndGroupName(String groupName, String groupType) {
		SearchRequest queryContextMapForProductGroup = DeviceQueryBuilderHelper
				.searchQueryForProductGroupWithGroupName(groupName, groupType);
		SearchResponse groupResponse = deviceDao.getResponseFromDataSource(queryContextMapForProductGroup);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getSingleGroupFromJson(groupResponse);
	}

	/**
	 * 
	 * @param listOfDeviceGroupName
	 * @return
	 */
	public List<Group> getProductGroupByListOfGroupName(List<String> listOfDeviceGroupName) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroupByIds(listOfDeviceGroupName);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfGroupFromJson(bundleResponse);
	}

	/**
	 * Method to prepare the list from the request construct and build the query
	 * and finally return the List of Commercial Products.
	 * 
	 * @param productIdOrName
	 * @return List of CommercialProducts
	 */
	@Override
	public List<CommercialProduct> getCommercialProductDetails(String productIdOrName) {
		// Prepare the list of strings which can be product ids or names.
		List<String> listOfProdIdsOrNames = new ArrayList<>();
		if (productIdOrName.contains(",")) {
			String[] prodIdsOrNames = productIdOrName.split(",");
			listOfProdIdsOrNames = Arrays.asList(prodIdsOrNames);
		} else {
			listOfProdIdsOrNames.add(productIdOrName);
		}
		List<CommercialProduct> listOfCommercialProduct = getListOfCommercialProduct(listOfProdIdsOrNames);
		if (CollectionUtils.isEmpty(listOfCommercialProduct)) {
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + " : " + productIdOrName);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}
		return getListOfCommercialProduct(listOfProdIdsOrNames);
	}

	/**
	 * 
	 * @param journeyType
	 * @param groupType
	 * @return
	 */
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModel(String groupType,
			String journeyType) {
		List<String> journeyTypes = Arrays.asList(journeyType.split(","));
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMerchandisingPromotionModel(journeyTypes,
				groupType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfMerchandisingPromotionModelFromJson(bundleResponse);
	}

	/**
	 * 
	 * @author manoj.bera
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
	public ProductGroupFacetModel getProductGroupFacetModel(String groupType, String make, String capacity,
			String colour, String operatingSystem, String mustHaveFeatures, String sortBy, String sortOption,
			Integer pageNumber, Integer pageSize, String journeyType) {
		ProductGroupFacetModel productGroupFacetModel = new ProductGroupFacetModel();
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroupModel(groupType, make,
				capacity, colour, operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize,
				journeyType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		List<ProductGroupModel> listOfProductGroups = response.getListOfProductGroupModel(bundleResponse);
		productGroupFacetModel.setListOfProductGroups(listOfProductGroups);
		productGroupFacetModel.setNumFound(Long.valueOf(listOfProductGroups.size()));
		productGroupFacetModel.setListOfFacetsFields(null);

		return productGroupFacetModel;
	}

	/**
	 * 
	 * @param groupType
	 * @param journeyType
	 * @return
	 */
	public List<FacetField> getProductGroupFacetModel(String groupType, String journeyType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForFacetCount(groupType, journeyType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getFacetField(bundleResponse);
	}

	/**
	 * 
	 * @param deviceIds
	 * @return
	 */
	public List<ProductModel> getListOfProductModel(List<String> deviceIds) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductModel(deviceIds);
		SearchResponse productModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfProductModel(productModelResponse);

	}

	/**
	 * @author manoj.bera
	 * @param deviceIds
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModel(List<String> deviceIds, String journeyType,
			String offerCode) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForOfferAppliedPriceModel(deviceIds,
				journeyType, offerCode);
		SearchResponse bundleModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfOfferAppliedPriceModel(bundleModelResponse);

	}

	/**
	 * 
	 * @param deviceIds
	 * @return
	 */
	@Override
	public ProductGroupModelMap getMapOfProductModelForGetDeliveryMethod(String deviceIds) {
		ProductGroupModelMap ProductGroupModelMap = null;
		Map<String, List<ProductGroupModel>> result = new HashMap<>();
		List<String> deviceId = Arrays.asList(deviceIds.split(","));
		List<ProductModel> productModels = getListOfProductModel(deviceId);
		Set<String> displayName = new HashSet<>();
		Map<String, List<String>> productGroupmap = new HashMap<>();
		productModels.forEach(model -> {
			List<String> productGroups = (model.getProductGroups() == null || model.getProductGroups().isEmpty())
					? Collections.emptyList() : model.getProductGroups();
			productGroups.forEach(group -> {
				String[] arr = group.split("@");
				String groupName = arr[0];
				String groupRole = arr[1];
				if (StringUtils.isNotBlank(groupRole) && Constants.COMPATIBLE_DELIVERY.equalsIgnoreCase(groupRole)) {
					displayName.add(groupName);
					if (productGroupmap.containsKey(group)) {
						List<String> products = productGroupmap.get(group);
						products.add(model.getProductId());
						productGroupmap.put(group, products);
					} else {
						List<String> products = new ArrayList<>();
						products.add(model.getProductId());
						productGroupmap.put(group.trim(), products);
					}
				}
			});
		});
		List<ProductGroupModel> productGroupModels = getListOfProductGroupModel(displayName,
				Constants.COMPATIBLE_DELIVERY);
		productGroupModels.forEach(groupModel -> {
			String id = groupModel.getName() + "@" + groupModel.getType().trim();
			List<String> productIds = productGroupmap.containsKey(id) ? productGroupmap.get(id) : null;
			productIds.forEach(deviceid -> {
				if (result.containsKey(deviceid)) {
					List<ProductGroupModel> groupModels = result.get(deviceid);
					groupModels.add(groupModel);
					result.put(deviceid, groupModels);
				} else {
					List<ProductGroupModel> groupModels = new ArrayList<>();
					groupModels.add(groupModel);
					result.put(deviceid, groupModels);
				}
			});
		});
		if (result.isEmpty()) {
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + " : " + deviceIds);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		} else {
			ProductGroupModelMap = new ProductGroupModelMap();
			ProductGroupModelMap.setProductgroupMap(result);
		}

		return ProductGroupModelMap;
	}

	/**
	 * 
	 * @param displayName
	 * @param groupType
	 * @return
	 */
	public List<ProductGroupModel> getListOfProductGroupModel(Set<String> displayName, String groupType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForProductGroupModelForDeliverMethod(displayName, groupType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfProductGroupModel(bundleResponse);
	}
}