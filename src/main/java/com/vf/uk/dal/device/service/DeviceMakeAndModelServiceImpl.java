package com.vf.uk.dal.device.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.Member;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceMakeAndModelServiceImpl implements DeviceMakeAndModelService {
	private static final String ERROR_CODE_DEVICE_MAKE_MODEL = "error_device_make_failed";
	private static final String AND = " and ";
	private static final String NO_DATA_FOUND_FOR_GIVEN_MAKE_AND_MMODEL = "No data found for given make and mmodel :";
	private static final String NO_DATA_FOUND_FOR_GIVEN_MAKE_AND_MMODEL2 = NO_DATA_FOUND_FOR_GIVEN_MAKE_AND_MMODEL;
	private static final String EXCEPTION_OCCURED_WHILE_EXECUTING_THREAD_POOL = "Exception occured while executing thread pool :";
	public static final String NO_DATA_FOUND_FOR_GROUP_TYPE = "No data found for given group type:";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;
	
	@Autowired
	DeviceDetailsMakeAndModelVaiantDaoUtils deviceDetailsMakeAndModelVaiantDaoUtils;
	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;

	@Autowired
	Validator validator;
	
	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;
	
	@Autowired
	CommonUtility commonUtility;

	@Value("${cdn.domain.host}")
	private String cdnDomain;

	/**
	 * @param make
	 * @param model
	 * @param groupType
	 * @param creditLimit
	 * @param journeyType
	 * @param offerCode
	 * @param bundleId
	 * @return deviceTileList
	 */
	@Override
	public List<DeviceTile> getListOfDeviceTile(String make, String model, String groupType, String deviceId,
			Double creditLimit, String journeyType, String offerCode, String bundleId) {
		List<DeviceTile> deviceTileList;
		String journeyTypeLocal = validator.validateAllParameters(make, model, groupType, journeyType);
		deviceTileList = getListOfDeviceTileImplementation(make, model, groupType, deviceId, journeyTypeLocal,
				creditLimit, offerCode, bundleId);

		return deviceTileList;
	}

	/**
	 * @param make
	 * @param model
	 * @param groupType
	 * @param journeyType
	 * @param journeyTypeLocal
	 * @return listOfDeviceTile
	 */

	/**
	 * Returns List of Device Tile based on groupType and groupName.
	 * 
	 * @param groupType
	 * @param groupName
	 * @return List<DeviceTile> performance improved by @author manoj.bera
	 */
	public List<DeviceTile> getListOfDeviceTileImplementation(String make, String model, String groupType,
			String deviceId, String journeyTypeInput, Double creditLimit, String offerCode, String bundleId) {
		boolean isConditionalAcceptJourney = (null != creditLimit) ? true : false;
		String journeyType;
		journeyType = deviceServiceImplUtility.getJourneyTypeForVariantAndList(journeyTypeInput);
		List<DeviceTile> listOfDeviceTile = new ArrayList<>();

		DeviceTile deviceTile = new DeviceTile();
		String groupName = null;
		List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember = new ArrayList<>();

		List<CommercialProduct> listOfCommercialProducts = null;
		listOfCommercialProducts = getListOfCommercialProducts(make, model, groupType);
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(groupType);
		if (groupType.equals(STRING_DEVICE_PAYG)) {
			listOfDeviceTile = getDeviceTileByMakeAndModelForPAYG(listOfCommercialProducts, listOfProductGroup, make,
					model, groupType);
		} else {
			List<CommercialProduct> commercialProductsMatchedMemList = new ArrayList<>();
			Map<String, CommercialProduct> commerProdMemMap = new HashMap<>();
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			Map<String, Boolean> bundleIdMap = new HashMap<>();
			Map<String, Boolean> fromPricingMap = new HashMap<>();
			Map<String, String> leadPlanIdMap = new HashMap<>();
			List<String> listofLeadPlan = new ArrayList<>();
			groupName = getGroupName(make, model, bundleId, journeyType, listOfDeviceGroupMember,
					listOfCommercialProducts, listOfProductGroup, commercialProductsMatchedMemList, commerProdMemMap,
					commerBundleIdMap, bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap,
					listofLeadPlan);

			setDeviceTile(make, model, groupType, deviceId, creditLimit, offerCode, bundleId,
					isConditionalAcceptJourney, journeyType, listOfDeviceTile, deviceTile, groupName,
					listOfDeviceGroupMember, commercialProductsMatchedMemList, commerProdMemMap, commerBundleIdMap,
					bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap, listofLeadPlan);
		}
		if (CollectionUtils.isEmpty(listOfDeviceTile)) {
			log.error(NO_DATA_FOUND_FOR_GIVEN_MAKE_AND_MMODEL2 + make + AND + model);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST,"404");
		}
		return listOfDeviceTile;

	}

	private void setDeviceTile(String make, String model, String groupType, String deviceId, Double creditLimit,
			String offerCode, String bundleId, boolean isConditionalAcceptJourney, String journeyType,
			List<DeviceTile> listOfDeviceTile, DeviceTile deviceTile, String groupName,
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			List<CommercialProduct> commercialProductsMatchedMemList, Map<String, CommercialProduct> commerProdMemMap,
			Map<String, CommercialBundle> commerBundleIdMap, List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> bundleIdMap, Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap,
			List<String> listofLeadPlan) {
		if (commercialProductsMatchedMemList != null && !commercialProductsMatchedMemList.isEmpty()) {
			if (listOfDeviceGroupMember != null && !listOfDeviceGroupMember.isEmpty()) {
				String leadMemberId = deviceServiceCommonUtility
						.getMemeberBasedOnRulesImplementation(listOfDeviceGroupMember, journeyType);
				setRatingAndDeviceId(deviceTile, leadMemberId);

				CompletableFuture<List<DeviceSummary>> future1 = setListOfPriceForBundleAndHardwareAndPromo(groupType,
						creditLimit, offerCode, bundleId, isConditionalAcceptJourney, journeyType, deviceTile,
						groupName, listOfDeviceGroupMember, commerProdMemMap, commerBundleIdMap,
						bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap, listofLeadPlan);
				List<DeviceSummary> listOfDeviceSummary;
				try {
					listOfDeviceSummary = future1.get();
				} catch (Exception e) {
					log.error(EXCEPTION_OCCURED_WHILE_EXECUTING_THREAD_POOL + e);
					throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.ERROR_IN_FUTURE_TASK,"404");
				}
				resetDeviceIdImplementation(isConditionalAcceptJourney, deviceTile, listOfDeviceSummary, deviceId);
				setListOfDeviceTile(isConditionalAcceptJourney, listOfDeviceTile, deviceTile, listOfDeviceSummary);
			} else {
				log.error("Requested Make and Model Not found in given group type:" + groupType);
				throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.MAKE_AND_MODEL_NOT_FOUND_IN_GROUPTYPE,"404");
			}
		} else {
			log.error(NO_DATA_FOUND_FOR_GIVEN_MAKE_AND_MMODEL2 + make + AND + model);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST,"404");
		}
	}

	private CompletableFuture<List<DeviceSummary>> setListOfPriceForBundleAndHardwareAndPromo(String groupType,
			Double creditLimit, String offerCode, String bundleId, boolean isConditionalAcceptJourney,
			String journeyType, DeviceTile deviceTile, String groupName,
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			Map<String, CommercialProduct> commerProdMemMap, Map<String, CommercialBundle> commerBundleIdMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, Map<String, Boolean> bundleIdMap,
			Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap, List<String> listofLeadPlan) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
		List<BundleAndHardwarePromotions> listOfBundleAndHardPromo = null;
		Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
		Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
		if (!groupType.equals(STRING_DEVICE_PAYG) && bundleAndHardwareTupleList != null
				&& !bundleAndHardwareTupleList.isEmpty()) {
			CompletableFuture<List<PriceForBundleAndHardware>> calculatePriceTask = deviceDao
					.getPriceForBundleAndHardwareListFromTupleListAsync(bundleAndHardwareTupleList, offerCode,
							journeyType, CatalogServiceAspect.CATALOG_VERSION.get(), groupType);
			CompletableFuture<List<com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions>> promotionTask = deviceDao
					.getBundleAndHardwarePromotionsListFromBundleListAsync(bundleAndHardwareTupleList, journeyType,
							CatalogServiceAspect.CATALOG_VERSION.get());

			try {
				CompletableFuture.allOf(calculatePriceTask, promotionTask).get();
				listOfPriceForBundleAndHardware = calculatePriceTask.get();
				listOfBundleAndHardPromo = promotionTask.get();
			} catch (Exception e) {
				log.error(EXCEPTION_OCCURED_WHILE_EXECUTING_THREAD_POOL + e);
				throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.ERROR_IN_FUTURE_TASK,"404");
			}
		}

		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			getBundleAndHardwarePromotions(journeyType, commerBundleIdMap, fromPricingMap, leadPlanIdMap,
					listofLeadPlan, listOfPriceForBundleAndHardware, listOfBundleAndHardPromo,
					bundleAndHardwarePromotionsMap, priceMapForParticularDevice);
		}
		Map<String, CommercialBundle> commercialBundleMap = setCommercialBundleMap(listofLeadPlan);
		deviceTile.setGroupName(groupName);
		deviceTile.setGroupType(groupType);
		return getDeviceSummeryImplementation(listOfDeviceGroupMember, listOfPriceForBundleAndHardware,
				commerProdMemMap, isConditionalAcceptJourney, journeyType, creditLimit, commercialBundleMap,
				bundleIdMap, bundleId, bundleAndHardwarePromotionsMap, leadPlanIdMap, groupType,
				priceMapForParticularDevice, fromPricingMap, CatalogServiceAspect.CATALOG_VERSION.get());
	}

	private void setListOfDeviceTile(boolean isConditionalAcceptJourney, List<DeviceTile> listOfDeviceTile,
			DeviceTile deviceTile, List<DeviceSummary> listOfDeviceSummary) {
		if (isConditionalAcceptJourney) {
			if (null != deviceTile.getDeviceId()) {
				deviceTile.setDeviceSummary(listOfDeviceSummary);
				listOfDeviceTile.add(deviceTile);
			}
		} else {
			deviceTile.setDeviceSummary(listOfDeviceSummary);
			listOfDeviceTile.add(deviceTile);
		}
	}

	private Map<String, CommercialBundle> setCommercialBundleMap(List<String> listofLeadPlan) {
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		if (!listofLeadPlan.isEmpty()) {
			List<CommercialBundle> comBundle = deviceEs.getListOfCommercialBundle(new ArrayList<>(listofLeadPlan));
			if (comBundle != null && !comBundle.isEmpty()) {
				comBundle.forEach(
						commercialBundle -> commercialBundleMap.put(commercialBundle.getId(), commercialBundle));
			}
		}
		return commercialBundleMap;
	}

	private void setRatingAndDeviceId(DeviceTile deviceTile, String leadMemberId) {
		if (leadMemberId != null) {
			deviceTile.setDeviceId(leadMemberId);
			deviceTile.setRating(deviceServiceCommonUtility.getDeviceTileRating(leadMemberId));
		}
	}

	private String getGroupName(String make, String model, String bundleId, String journeyType,
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			List<CommercialProduct> listOfCommercialProducts, List<Group> listOfProductGroup,
			List<CommercialProduct> commercialProductsMatchedMemList, Map<String, CommercialProduct> commerProdMemMap,
			Map<String, CommercialBundle> commerBundleIdMap, List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> bundleIdMap, Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap,
			List<String> listofLeadPlan) {
		String groupName = null;
		if (CollectionUtils.isNotEmpty(listOfCommercialProducts)) {
			getCommercialBundleMap(make, model, journeyType, listOfCommercialProducts, commerProdMemMap,
					commerBundleIdMap);
			if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
				groupName = getMembersForGroup(bundleId, journeyType, listOfDeviceGroupMember, listOfProductGroup,
						commercialProductsMatchedMemList, commerProdMemMap, commerBundleIdMap,
						bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap, listofLeadPlan, make,
						model);
			}
		}
		return groupName;
	}

	private List<CommercialProduct> getListOfCommercialProducts(String make, String model, String groupType) {
		List<CommercialProduct> listOfCommercialProducts = null;
		if (deviceServiceImplUtility.validateGroupType(groupType)) {
			log.info("Start -->  calling  CommericalProduct.getByMakeAndModel");
			listOfCommercialProducts = deviceEs.getListOfCommercialProductByMakeAndModel(make, model);
			log.info("End -->  After calling  CommericalProduct.getByMakeAndModel");

		} else {
			log.error(NO_DATA_FOUND_FOR_GROUP_TYPE + groupType);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.NULL_VALUE_GROUP_TYPE,"404");
		}
		return listOfCommercialProducts;
	}

	/**
	 * @param journeyType
	 * @param commerBundleIdMap
	 * @param fromPricingMap
	 * @param leadPlanIdMap
	 * @param listofLeadPlan
	 * @param listOfPriceForBundleAndHardware
	 * @param listOfBundleAndHardPromo
	 * @param bundleAndHardwarePromotionsMap
	 * @param priceMapForParticularDevice
	 */
	public void getBundleAndHardwarePromotions(String journeyType, Map<String, CommercialBundle> commerBundleIdMap,
			Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap, List<String> listofLeadPlan,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			List<BundleAndHardwarePromotions> listOfBundleAndHardPromo,
			Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice) {
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
		fromPricingMap.forEach((hardwareID, flag) -> getPriceAndPromotionMap(journeyType, commerBundleIdMap,
				leadPlanIdMap, listofLeadPlan, bundleAndHardwarePromotionsMap, priceMapForParticularDevice,
				promotionsMap, priceMap, hardwareID, flag));
	}

	/**
	 * 
	 * @param journeyType
	 * @param commerBundleIdMap
	 * @param leadPlanIdMap
	 * @param listofLeadPlan
	 * @param bundleAndHardwarePromotionsMap
	 * @param priceMapForParticularDevice
	 * @param promotionsMap
	 * @param priceMap
	 * @param hardwareID
	 * @param flag
	 */
	@Override
	public void getPriceAndPromotionMap(String journeyType, Map<String, CommercialBundle> commerBundleIdMap,
			Map<String, String> leadPlanIdMap, List<String> listofLeadPlan,
			Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice,
			Map<String, List<BundleAndHardwarePromotions>> promotionsMap,
			Map<String, List<PriceForBundleAndHardware>> priceMap, String hardwareID, Boolean flag) {
		if (flag) {
			List<PriceForBundleAndHardware> listOfpriceForBundleANdHardware = priceMap.containsKey(hardwareID)
					? priceMap.get(hardwareID) : null;
			PriceForBundleAndHardware priceForBundleAndHardware = deviceServiceCommonUtility
					.identifyLowestPriceOfPlanForDevice(listOfpriceForBundleANdHardware, commerBundleIdMap,
							journeyType);
			setLeadPlanMap(leadPlanIdMap, listofLeadPlan, priceMapForParticularDevice, priceForBundleAndHardware);
			setBundleAndHardwarePromotionsMap(bundleAndHardwarePromotionsMap, promotionsMap, hardwareID,
					priceForBundleAndHardware);
		} else {
			PriceForBundleAndHardware priceForBundleAndHardware = priceMap.containsKey(hardwareID)
					? priceMap.get(hardwareID).get(0) : null;
			if (priceForBundleAndHardware != null) {
				priceMapForParticularDevice.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
						priceForBundleAndHardware);
			}

			BundleAndHardwarePromotions promotion = promotionsMap.containsKey(hardwareID)
					? promotionsMap.get(hardwareID).get(0) : null;

			if (promotion != null) {
				bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion);
			}

		}
	}

	private void setBundleAndHardwarePromotionsMap(
			Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
			Map<String, List<BundleAndHardwarePromotions>> promotionsMap, String hardwareID,
			PriceForBundleAndHardware priceForBundleAndHardware) {
		if (!promotionsMap.isEmpty() && promotionsMap.containsKey(hardwareID)) {
			List<BundleAndHardwarePromotions> listOfPromotionLocal = promotionsMap.get(hardwareID);
			listOfPromotionLocal.forEach(promotion -> {
				if (promotion.getBundleId().equalsIgnoreCase(priceForBundleAndHardware.getBundlePrice().getBundleId()))
					bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion);

			});
		}
	}

	private void setLeadPlanMap(Map<String, String> leadPlanIdMap, List<String> listofLeadPlan,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice,
			PriceForBundleAndHardware priceForBundleAndHardware) {
		if (priceForBundleAndHardware != null) {
			priceMapForParticularDevice.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
					priceForBundleAndHardware);
			leadPlanIdMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
					priceForBundleAndHardware.getBundlePrice().getBundleId());
			listofLeadPlan.add(priceForBundleAndHardware.getBundlePrice().getBundleId());
		}
	}

	/**
	 * @param bundleId
	 * @param bundleAndHardwareTupleList
	 * @param bundleIdMap
	 * @param fromPricingMap
	 * @param leadPlanIdMap
	 * @param listofLeadPlan
	 * @param member
	 * @param commercialProduct
	 */
	public void getListOfLeadPlan(String bundleId, List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> bundleIdMap, Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap,
			List<String> listofLeadPlan, Member member, CommercialProduct commercialProduct) {
		fromPricingMap.put(commercialProduct.getId(), false);
		bundleIdMap.put(commercialProduct.getId(), true);
		BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId(bundleId);
		bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
		bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		leadPlanIdMap.put(member.getId(), bundleId);
		listofLeadPlan.add(bundleId);
	}

	/**
	 * 
	 * @param listOfCommercialProducts
	 * @param listOfProductGroup
	 * @param make
	 * @param model
	 * @param groupType
	 * @return List<DeviceTile>
	 */
	public List<DeviceTile> getDeviceTileByMakeAndModelForPAYG(List<CommercialProduct> listOfCommercialProducts,
			List<Group> listOfProductGroup, String make, String model, String groupType) {

		List<DeviceTile> listOfDeviceTile = new ArrayList<>();
		DeviceTile deviceTile = new DeviceTile();
		String groupName = null;
		Set<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember = new HashSet<>();
		List<CommercialProduct> commercialProductsMatchedMemListForPAYG = new ArrayList<>();
		Map<String, CommercialProduct> commerProdMemMapPAYG = new HashMap<>();
		Set<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG = new HashSet<>();
		if (!CollectionUtils.isEmpty(listOfCommercialProducts)) {
			listOfCommercialProducts.forEach(
					commercialProduct -> setCommerProdMemMapPAYG(make, model, commerProdMemMapPAYG, commercialProduct));
			groupName = setBundleAndHardwareTupleList(listOfProductGroup, make, model, listOfDeviceGroupMember,
					commercialProductsMatchedMemListForPAYG, commerProdMemMapPAYG, bundleAndHardwareTupleListPAYG);
		}
		if (!commercialProductsMatchedMemListForPAYG.isEmpty()) {
			getMemberByRules(groupType, listOfDeviceTile, deviceTile, groupName,
					new ArrayList<>(listOfDeviceGroupMember), commerProdMemMapPAYG, bundleAndHardwareTupleListPAYG);
		} else {
			log.error(NO_DATA_FOUND_FOR_GIVEN_MAKE_AND_MMODEL2 + make + AND + model);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST,"404");
		}
		return listOfDeviceTile;

	}

	private String setBundleAndHardwareTupleList(List<Group> listOfProductGroup, String make, String model,
			Set<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			List<CommercialProduct> commercialProductsMatchedMemListForPAYG,
			Map<String, CommercialProduct> commerProdMemMapPAYG,
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG) {
		String groupName = null;
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroupPAYG : listOfProductGroup) {
				if (StringUtils.equalsIgnoreCase(productGroupPAYG.getEquipmentMake(), make)
						&& StringUtils.equalsIgnoreCase(productGroupPAYG.getEquipmentModel(), model)
						&& productGroupPAYG.getMembers() != null && !productGroupPAYG.getMembers().isEmpty()) {
					for (Member member : productGroupPAYG.getMembers()) {
						groupName = setBundleAndHardwareTupleListPAYG(listOfDeviceGroupMember,
								commercialProductsMatchedMemListForPAYG, commerProdMemMapPAYG,
								bundleAndHardwareTupleListPAYG, productGroupPAYG, member);
					}
				}
			}
		}
		return groupName;
	}

	private String setBundleAndHardwareTupleListPAYG(Set<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			List<CommercialProduct> commercialProductsMatchedMemListForPAYG,
			Map<String, CommercialProduct> commerProdMemMapPAYG,
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG, Group productGroupPAYG, Member member) {
		com.vf.uk.dal.device.model.Member entityMember;
		String groupName = null;
		BundleAndHardwareTuple bundleAndHardwareTuple;
		if (commerProdMemMapPAYG.containsKey(member.getId())) {
			groupName = productGroupPAYG.getName();
			entityMember = new com.vf.uk.dal.device.model.Member();
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
		return groupName;
	}

	private void setCommerProdMemMapPAYG(String make, String model, Map<String, CommercialProduct> commerProdMemMapPAYG,
			CommercialProduct commercialProduct) {
		if (deviceServiceImplUtility.getProductclassValidation(commercialProduct)
				&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
				&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)
				&& deviceServiceImplUtility.isNonUpgradeCommercialProduct(commercialProduct)) {
			commerProdMemMapPAYG.put(commercialProduct.getId(), commercialProduct);
		}
	}

	/**
	 * @param groupType
	 * @param listOfDeviceTile
	 * @param deviceTile
	 * @param groupName
	 * @param listOfDeviceGroupMember
	 * @param commerProdMemMapPAYG
	 * @param bundleAndHardwareTupleListPAYG
	 */
	public void getMemberByRules(String groupType, List<DeviceTile> listOfDeviceTile, DeviceTile deviceTile,
			String groupName, List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			Map<String, CommercialProduct> commerProdMemMapPAYG,
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG) {
		if (listOfDeviceGroupMember != null && !listOfDeviceGroupMember.isEmpty()) {
			String leadMemberId = deviceServiceCommonUtility
					.getMemeberBasedOnRulesImplementation(listOfDeviceGroupMember, null);
			setRatingAndDeviceId(deviceTile, leadMemberId);

			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
			if (bundleAndHardwareTupleListPAYG != null && !bundleAndHardwareTupleListPAYG.isEmpty()) {
				listOfPriceForBundleAndHardware = commonUtility
						.getPriceDetails(new ArrayList<>(bundleAndHardwareTupleListPAYG), null, null, groupType);
			}
			Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = setBundleAndHardwarePromotionsMaps(
					bundleAndHardwareTupleListPAYG);
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
			setPriceMapForParticularDevice(listOfPriceForBundleAndHardware, priceMapForParticularDevice);
			deviceTile.setGroupName(groupName);
			deviceTile.setGroupType(groupType);
			CompletableFuture<List<DeviceSummary>> future1 = getDeviceSummeryImplementationPAYG(
					listOfDeviceGroupMember, commerProdMemMapPAYG, priceMapForParticularDevice,
					bundleAndHardwarePromotionsMap, CatalogServiceAspect.CATALOG_VERSION.get());
			List<DeviceSummary> listOfDeviceSummary;
			try {
				listOfDeviceSummary = future1.get();
			} catch (Exception e) {
				log.error(EXCEPTION_OCCURED_WHILE_EXECUTING_THREAD_POOL + e);
				throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.ERROR_IN_FUTURE_TASK,"404");
			}
			deviceTile.setDeviceSummary(listOfDeviceSummary);
			listOfDeviceTile.add(deviceTile);
		} else {
			log.error("Requested Make and Model Not found in given group type:" + groupType);
			throw new DeviceCustomException(ERROR_CODE_DEVICE_MAKE_MODEL,ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST,"404");
		}
	}

	public  void setPriceMapForParticularDevice(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice) {
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			listOfPriceForBundleAndHardware.forEach(priceForBundleAndHardware -> {
				if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null
						&& StringUtils.isNotBlank(priceForBundleAndHardware.getHardwarePrice().getHardwareId())
						&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null) {
					priceMapForParticularDevice.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
							priceForBundleAndHardware);
				} else {
					log.error("PAYG PRICE Coming as null from Pricing API---------------");
				}
			});
		}
	}

	private Map<String, BundleAndHardwarePromotions> setBundleAndHardwarePromotionsMaps(
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG) {
		Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
		if (bundleAndHardwareTupleListPAYG != null && !bundleAndHardwareTupleListPAYG.isEmpty()) {
			List<BundleAndHardwarePromotions> allPromotions = commonUtility
					.getPromotionsForBundleAndHardWarePromotions(new ArrayList<>(bundleAndHardwareTupleListPAYG), null);
			if (allPromotions != null && !allPromotions.isEmpty()) {
				allPromotions
						.forEach(promotion -> bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion));
			}
		}
		return bundleAndHardwarePromotionsMap;
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
			commercialBundle = deviceEs.getCommercialBundle(leadPlanId);
		}
		return deviceServiceImplUtility.isSellable(journeyType, commercialBundle);
	}

	/**
	 * 
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
	 * @return CompletableFuture<List<DeviceSummary>>
	 */
	public CompletableFuture<List<DeviceSummary>> getDeviceSummeryImplementation(
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareLocal,
			Map<String, CommercialProduct> commerProdMemMap, boolean isConditionalAcceptJourney, String journeyType,
			Double creditLimit, Map<String, CommercialBundle> commercialBundleMap, Map<String, Boolean> bundleIdMap,
			String bundleId, Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
			Map<String, String> leadPlanIdMap, String groupType,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice, Map<String, Boolean> fromPricingMap,
			String version) {
		return CompletableFuture.supplyAsync(new Supplier<List<DeviceSummary>>() {

			List<DeviceSummary> listOfDeviceSummaryLocal = new ArrayList<>();
			DeviceSummary deviceSummary;

			@Override

			public List<DeviceSummary> get() {
				CatalogServiceAspect.CATALOG_VERSION.set(version);
				for (com.vf.uk.dal.device.model.Member member : listOfDeviceGroupMember) {
					boolean isConditional = false;
					PriceForBundleAndHardware priceForBundleAndHardware = null;
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					CommercialBundle comBundle = null;
					List<BundleAndHardwarePromotions> promotions = null;
					if (isConditionalAcceptJourney && commercialProduct != null) {
						isConditional = true;
						comBundle = setCommercialBundle(listOfPriceForBundleAndHardwareLocal, journeyType, creditLimit,
								groupType, commercialProduct);
						promotions = setBundleAndHardwarePromotionListAndPromotios(journeyType, member, comBundle);

					} else if (StringUtils.isNotBlank(bundleId) && commercialProduct != null
							&& bundleIdMap.get(member.getId())) {
						comBundle = setCommBundle(commercialBundleMap, bundleId);
						promotions = setPromoIfBundleIdPresent(bundleAndHardwarePromotionsMap, member);
					} else {
						String planId = null;
						if (!leadPlanIdMap.isEmpty() && leadPlanIdMap.containsKey(member.getId())) {
							planId = leadPlanIdMap.get(member.getId());
						}
						comBundle = setCommBundle(commercialBundleMap, planId);
						promotions = setPromoIfBundleIdPresent(bundleAndHardwarePromotionsMap, member);
					}
					if (isConditional) {
						priceForBundleAndHardware = !listOfPriceForBundleAndHardwareLocal.isEmpty()
								? listOfPriceForBundleAndHardwareLocal.get(0) : null;
					} else if (priceMapForParticularDevice.containsKey(member.getId()) && !isConditional) {
						priceForBundleAndHardware = priceMapForParticularDevice.get(member.getId());
					}
					deviceSummary = deviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile(
							memberPriority, commercialProduct, comBundle, priceForBundleAndHardware, promotions,
							groupType, isConditionalAcceptJourney, fromPricingMap, cdnDomain);

					setlistOfDeviceSummaryLocal(isConditionalAcceptJourney, creditLimit, bundleIdMap, bundleId, member,
							commercialProduct, comBundle);
				}
				return listOfDeviceSummaryLocal;
			}

			private List<BundleAndHardwarePromotions> setPromoIfBundleIdPresent(
					Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
					com.vf.uk.dal.device.model.Member member) {
				List<BundleAndHardwarePromotions> promotions = null;
				if (bundleAndHardwarePromotionsMap.containsKey(member.getId())) {
					promotions = Arrays.asList(bundleAndHardwarePromotionsMap.get(member.getId()));
				}
				return promotions;
			}

			private CommercialBundle setCommBundle(Map<String, CommercialBundle> commercialBundleMap, String bundleId) {
				CommercialBundle comBundle = null;
				if (commercialBundleMap.containsKey(bundleId)) {
					comBundle = commercialBundleMap.get(bundleId);
				}
				return comBundle;
			}

			private List<BundleAndHardwarePromotions> setBundleAndHardwarePromotionListAndPromotios(String journeyType,
					com.vf.uk.dal.device.model.Member member, CommercialBundle comBundle) {
				List<BundleAndHardwarePromotions> promotions = null;
				List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
				if (comBundle != null) {
					BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
					bundleAndHardwareTuple.setBundleId(comBundle.getId());
					bundleAndHardwareTuple.setHardwareId(member.getId());
					bundleHardwareTupleList.add(bundleAndHardwareTuple);
				}
				if (!bundleHardwareTupleList.isEmpty()) {
					promotions = commonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
							journeyType);
				}
				return promotions;
			}

			private CommercialBundle setCommercialBundle(
					List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareLocal, String journeyType,
					Double creditLimit, String groupType, CommercialProduct commercialProduct) {
				CommercialBundle comBundle;
				if (isLeadPlanWithinCreditLimit_Implementation(commercialProduct, creditLimit,
						listOfPriceForBundleAndHardwareLocal, journeyType, groupType)) {
					comBundle = deviceEs.getCommercialBundle(commercialProduct.getLeadPlanId());
				} else {
					comBundle = getLeadBundleBasedOnAllPlansImplementation(creditLimit, commercialProduct,
							listOfPriceForBundleAndHardwareLocal, journeyType, groupType);
				}
				return comBundle;
			}

			private void setlistOfDeviceSummaryLocal(boolean isConditionalAcceptJourney, Double creditLimit,
					Map<String, Boolean> bundleIdMap, String bundleId, com.vf.uk.dal.device.model.Member member,
					CommercialProduct commercialProduct, CommercialBundle comBundle) {
				if (null != deviceSummary && commercialProduct != null) {
					deviceServiceImplUtility.isPlanAffordableImplementation(deviceSummary, comBundle, creditLimit,
							isConditionalAcceptJourney);
					if (StringUtils.isNotBlank(bundleId))
						if (bundleIdMap.get(member.getId()))
							deviceSummary.setIsCompatible(true);
						else
							deviceSummary.setIsCompatible(false);
					listOfDeviceSummaryLocal.add(deviceSummary);
				}
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
	public void resetDeviceIdImplementation(boolean isConditionalAcceptJourney, DeviceTile deviceTile,
			List<DeviceSummary> listOfDeviceSummary, String selectedDeviceId) {

		boolean resetDeviceId = false;
		if (isConditionalAcceptJourney) {
			resetDeviceId = resetDeviceId(deviceTile, listOfDeviceSummary, selectedDeviceId);

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

	private boolean resetDeviceId(DeviceTile deviceTile, List<DeviceSummary> listOfDeviceSummary,
			String selectedDeviceId) {
		boolean resetDeviceId = false;
		if (StringUtils.isNotBlank(selectedDeviceId)) {
			for (DeviceSummary deviceSummary : listOfDeviceSummary) {
				if (deviceSummary.getIsAffordable() && deviceSummary.getDeviceId().equals(selectedDeviceId)) {
					deviceTile.setDeviceId(deviceSummary.getDeviceId());
					resetDeviceId = true;
					break;
				}
			}
		}
		return resetDeviceId;
	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @param commerProdMemMap
	 * @param groupType=PAYG
	 * @param priceMapForParticularDevice
	 * @return CompletableFuture<List<DeviceSummary>>
	 */
	public CompletableFuture<List<DeviceSummary>> getDeviceSummeryImplementationPAYG(
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			Map<String, CommercialProduct> commerProdMemMap,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice,
			Map<String, BundleAndHardwarePromotions> promotions, String version) {
		return CompletableFuture.supplyAsync(new Supplier<List<DeviceSummary>>() {

			List<DeviceSummary> listOfDeviceSummaryLocal = new ArrayList<>();
			DeviceSummary deviceSummary;

			@Override
			public List<DeviceSummary> get() {
				CatalogServiceAspect.CATALOG_VERSION.set(version);
				for (com.vf.uk.dal.device.model.Member member : listOfDeviceGroupMember) {
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					BundleAndHardwarePromotions promotion = promotions.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					if (commercialProduct != null) {
						PriceForBundleAndHardware priceForBundleAndHardware = null;
						if (priceMapForParticularDevice.containsKey(member.getId())) {
							priceForBundleAndHardware = priceMapForParticularDevice.get(member.getId());
							deviceSummary = deviceDetailsMakeAndModelVaiantDaoUtils
									.convertCoherenceDeviceToDeviceTilePAYG(memberPriority, commercialProduct,
											priceForBundleAndHardware, promotion, cdnDomain);
						}

						if (deviceSummary != null) {
							listOfDeviceSummaryLocal.add(deviceSummary);
							deviceSummary = null;
						}
					}

				}
				return listOfDeviceSummaryLocal;
			}
		});

	}

	/**
	 * Check if lead plan associated with commercial product is within credit
	 * limit.
	 * 
	 * @param product
	 * @param creditDetails
	 * @return
	 */
	public boolean isLeadPlanWithinCreditLimit_Implementation(CommercialProduct product, Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String journeyType, String groupType) {
		List<BundleAndHardwareTuple> bundles = new ArrayList<>();

		BundleAndHardwareTuple tuple = new BundleAndHardwareTuple();
		tuple.setBundleId(product.getLeadPlanId());
		tuple.setHardwareId(product.getId());

		bundles.add(tuple);

		List<PriceForBundleAndHardware> priceForBundleAndHardwares = commonUtility.getPriceDetails(bundles, null,
				journeyType, groupType);

		if (deviceServiceImplUtility.isPlanPriceWithinCreditLimitImplementation(creditLimit,
				priceForBundleAndHardwares, product.getLeadPlanId())) {
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
	 * @return CommercialBundle
	 */
	public CommercialBundle getLeadBundleBasedOnAllPlansImplementation(Double creditLimit,
			CommercialProduct commercialProduct, List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			String journeyType, String groupType) {

		if (CollectionUtils.isNotEmpty(commercialProduct.getListOfCompatiblePlanIds())) {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			List<String> compatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds();

			setBundleAndHardwareTupleList(commercialProduct, bundleAndHardwareTupleList, compatiblePlanIds);
			List<PriceForBundleAndHardware> priceForBundleAndHardwares = null;
			priceForBundleAndHardwares = setPriceForBundleAndHardwares(journeyType, groupType,
					bundleAndHardwareTupleList);
			if (priceForBundleAndHardwares != null && CollectionUtils.isNotEmpty(priceForBundleAndHardwares)) {
				Iterator<PriceForBundleAndHardware> iterator = priceForBundleAndHardwares.iterator();
				while (iterator.hasNext()) {

					PriceForBundleAndHardware priceForBundleAndHardware = iterator.next();
					if (null != priceForBundleAndHardware.getBundlePrice()) {
						deviceServiceImplUtility.calculateDiscount(creditLimit, iterator, priceForBundleAndHardware);

					}

				}
				if (CollectionUtils.isNotEmpty(priceForBundleAndHardwares)) {
					listOfPriceForBundleAndHardware.clear();
					List<PriceForBundleAndHardware> sortedPlanList = deviceTilesDaoUtils
							.sortPlansBasedOnMonthlyPrice(priceForBundleAndHardwares);
					listOfPriceForBundleAndHardware.addAll(sortedPlanList);
					PriceForBundleAndHardware leadBundle = sortedPlanList.get(0);
					return deviceEs.getCommercialBundle(leadBundle.getBundlePrice().getBundleId());
				}

			}
		}

		return null;

	}

	private List<PriceForBundleAndHardware> setPriceForBundleAndHardwares(String journeyType, String groupType,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		List<PriceForBundleAndHardware> priceForBundleAndHardwares = null;
		if (CollectionUtils.isNotEmpty(bundleAndHardwareTupleList)) {
			priceForBundleAndHardwares = commonUtility.getPriceDetails(bundleAndHardwareTupleList, null, journeyType,
					groupType);
		}
		return priceForBundleAndHardwares;
	}

	private void setBundleAndHardwareTupleList(CommercialProduct commercialProduct,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, List<String> compatiblePlanIds) {
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
	}

	/**
	 * @param bundleId
	 * @param journeyType
	 * @param groupName
	 * @param listOfDeviceGroupMember
	 * @param listOfProductGroup
	 * @param commercialProductsMatchedMemList
	 * @param commerProdMemMap
	 * @param commerBundleIdMap
	 * @param bundleAndHardwareTupleList
	 * @param bundleIdMap
	 * @param fromPricingMap
	 * @param leadPlanIdMap
	 * @param listofLeadPlan
	 * @return groupName
	 */
	public String getMembersForGroup(String bundleId, String journeyType,
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember, List<Group> listOfProductGroup,
			List<CommercialProduct> commercialProductsMatchedMemList, Map<String, CommercialProduct> commerProdMemMap,
			Map<String, CommercialBundle> commerBundleIdMap, List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> bundleIdMap, Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap,
			List<String> listofLeadPlan, String make, String model) {
		String groupName = null;
		for (Group productGroup : listOfProductGroup) {
			if (StringUtils.equalsIgnoreCase(productGroup.getEquipmentMake(), make)
					&& StringUtils.equalsIgnoreCase(productGroup.getEquipmentModel(), model)
					&& productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
				for (Member member : productGroup.getMembers()) {
					groupName = getGroupName(bundleId, journeyType, listOfDeviceGroupMember,
							commercialProductsMatchedMemList, commerProdMemMap, commerBundleIdMap,
							bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap, listofLeadPlan,
							productGroup, member,groupName);
				}
			}
		}
		return groupName;
	}

	private String getGroupName(String bundleId, String journeyType,
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember,
			List<CommercialProduct> commercialProductsMatchedMemList, Map<String, CommercialProduct> commerProdMemMap,
			Map<String, CommercialBundle> commerBundleIdMap, List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> bundleIdMap, Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap,
			List<String> listofLeadPlan, Group productGroup, Member member, String groupNameLocal) {
		String groupName = groupNameLocal;
		com.vf.uk.dal.device.model.Member entityMember;
		if (commerProdMemMap.containsKey(member.getId())) {
			groupName = productGroup.getName();
			entityMember = new com.vf.uk.dal.device.model.Member();
			entityMember.setId(member.getId());
			entityMember.setPriority(String.valueOf(member.getPriority()));
			listOfDeviceGroupMember.add(entityMember);
			CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
			commercialProductsMatchedMemList.add(commercialProduct);
			List<String> listOfCompatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds() == null
					? Collections.emptyList() : commercialProduct.getListOfCompatiblePlanIds();
			if (StringUtils.isNotBlank(bundleId) && listOfCompatiblePlanIds.contains(bundleId)) {
				getListOfLeadPlan(bundleId, bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap,
						listofLeadPlan, member, commercialProduct);
			} else {
				bundleIdMap.put(commercialProduct.getId(), false);
				if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
						&& isJourneySpecificLeadPlan(commerBundleIdMap, commercialProduct.getLeadPlanId(), journeyType)
						&& listOfCompatiblePlanIds.contains(commercialProduct.getLeadPlanId())) {
					deviceServiceImplUtility.getPricingMap(bundleAndHardwareTupleList, fromPricingMap, leadPlanIdMap,
							listofLeadPlan, commercialProduct);
				} else {
					getBundleAndHardwareTupleList(bundleAndHardwareTupleList, fromPricingMap, commercialProduct);
				}
			}
		}
		return groupName;
	}

	private void getBundleAndHardwareTupleList(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> fromPricingMap, CommercialProduct commercialProduct) {
		if (CollectionUtils.isNotEmpty(commercialProduct.getListOfCompatiblePlanIds()))

		{
			deviceServiceImplUtility.getBundleAndHardwareTupleList(bundleAndHardwareTupleList,
					fromPricingMap, commercialProduct);
		}
	}

	/**
	 * 
	 * @param make
	 * @param model
	 * @param journeyType
	 * @param listOfCommercialProducts
	 * @param commerProdMemMap
	 * @param commerBundleIdMap
	 */
	public void getCommercialBundleMap(String make, String model, String journeyType,
			List<CommercialProduct> listOfCommercialProducts, Map<String, CommercialProduct> commerProdMemMap,
			Map<String, CommercialBundle> commerBundleIdMap) {
		Set<String> listofLeadBundleId;
		listofLeadBundleId = deviceServiceImplUtility.getlistofLeadBundleId(listOfCommercialProducts, make, model,
				journeyType, commerProdMemMap);
		List<CommercialBundle> commercialBundles = deviceEs
				.getListOfCommercialBundle(new ArrayList<>(listofLeadBundleId));
		commercialBundles
				.forEach(commercialBundle -> commerBundleIdMap.put(commercialBundle.getId(), commercialBundle));
	}
}