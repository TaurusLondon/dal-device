package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.DeviceMakeAndModelService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;

@Component
public class DeviceMakeAndModelServiceImpl implements DeviceMakeAndModelService {

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;
	
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
		String journeyTypeLocal = Validator.validateAllParameters(make, model, groupType, journeyType);
		deviceTileList = getListOfDeviceTile_Implementation(make, model, groupType, deviceId, journeyTypeLocal,
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
	public List<DeviceTile> getListOfDeviceTile_Implementation(String make, String model, String groupType,
			String deviceId, String journeyTypeInput, Double creditLimit, String offerCode, String bundleId) {
		boolean isConditionalAcceptJourney = (null != creditLimit) ? true : false;
		String journeyType;
		journeyType = DeviceServiceImplUtility.getJourneyTypeForVariantAndList(journeyTypeInput);
		List<DeviceTile> listOfDeviceTile = new ArrayList<>();

		DeviceTile deviceTile = new DeviceTile();
		String groupName = null;
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();

		List<CommercialProduct> listOfCommercialProducts = null;
		if (DeviceServiceImplUtility.validateGroupType(groupType)) {
			LogHelper.info(this, "Start -->  calling  CommericalProduct.getByMakeAndModel");
			listOfCommercialProducts = deviceEs.getListOfCommercialProductByMakeAndModel(make, model);
			LogHelper.info(this, "End -->  After calling  CommericalProduct.getByMakeAndModel");

		} else {
			LogHelper.error(this, Constants.NO_DATA_FOUND_FOR_GROUP_TYPE + groupType);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(groupType);
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
			if (CollectionUtils.isNotEmpty(listOfCommercialProducts)) {
				getCommercialBundleMap(make, model, journeyType, listOfCommercialProducts, commerProdMemMap,
						commerBundleIdMap);
				if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
					groupName = getMembersForGroup(bundleId, journeyType, listOfDeviceGroupMember, listOfProductGroup,
							commercialProductsMatchedMemList, commerProdMemMap, commerBundleIdMap,
							bundleAndHardwareTupleList, bundleIdMap, fromPricingMap, leadPlanIdMap, listofLeadPlan);
				}
			}

			if (commercialProductsMatchedMemList != null && !commercialProductsMatchedMemList.isEmpty()) {

				if (listOfDeviceGroupMember != null && !listOfDeviceGroupMember.isEmpty()) {
					String leadMemberId = deviceServiceCommonUtility
							.getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember, journeyType);
					if (leadMemberId != null) {
						deviceTile.setDeviceId(leadMemberId);
						deviceTile.setRating(deviceServiceCommonUtility.getDeviceTileRating(leadMemberId));
					}
					List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
					List<BundleAndHardwarePromotions> listOfBundleAndHardPromo = null;
					Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
					Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
					if (!groupType.equals(Constants.STRING_DEVICE_PAYG) && bundleAndHardwareTupleList != null
							&& !bundleAndHardwareTupleList.isEmpty()) {
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

					if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
						getBundleAndHardwarePromotions(journeyType, commerBundleIdMap, fromPricingMap, leadPlanIdMap,
								listofLeadPlan, listOfPriceForBundleAndHardware, listOfBundleAndHardPromo,
								bundleAndHardwarePromotionsMap, priceMapForParticularDevice);
					}
					Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
					if (!listofLeadPlan.isEmpty()) {
						List<CommercialBundle> comBundle = deviceEs
								.getListOfCommercialBundle(new ArrayList<>(listofLeadPlan));
						if (comBundle != null && !comBundle.isEmpty()) {
							comBundle.forEach(commercialBundle -> commercialBundleMap.put(commercialBundle.getId(),
									commercialBundle));
						}
					}
					deviceTile.setGroupName(groupName);
					deviceTile.setGroupType(groupType);
					CompletableFuture<List<DeviceSummary>> future1 = getDeviceSummery_Implementation(
							listOfDeviceGroupMember, listOfPriceForBundleAndHardware, commerProdMemMap,
							isConditionalAcceptJourney, journeyType, creditLimit, commercialBundleMap, bundleIdMap,
							bundleId, bundleAndHardwarePromotionsMap, leadPlanIdMap, groupType,
							priceMapForParticularDevice, fromPricingMap, Constants.CATALOG_VERSION.get());
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
			if (priceForBundleAndHardware != null) {
				priceMapForParticularDevice.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
						priceForBundleAndHardware);
				leadPlanIdMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
						priceForBundleAndHardware.getBundlePrice().getBundleId());
				listofLeadPlan.add(priceForBundleAndHardware.getBundlePrice().getBundleId());
			}
			if (!promotionsMap.isEmpty() && promotionsMap.containsKey(hardwareID)) {
				List<BundleAndHardwarePromotions> listOfPromotionLocal = promotionsMap.get(hardwareID);
				listOfPromotionLocal.forEach(promotion -> {
					if (promotion.getBundleId()
							.equalsIgnoreCase(priceForBundleAndHardware.getBundlePrice().getBundleId()))
						bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion);

				});
			}
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
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
		com.vf.uk.dal.device.entity.Member entityMember;
		List<CommercialProduct> commercialProductsMatchedMemListForPAYG = new ArrayList<>();
		Map<String, CommercialProduct> commerProdMemMapPAYG = new HashMap<>();
		List<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple;
		if (!CollectionUtils.isEmpty(listOfCommercialProducts)) {
			listOfCommercialProducts.forEach(commercialProduct -> {
				if (DeviceServiceImplUtility.getProductclassValidation(commercialProduct)
						&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
						&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)
						&& DeviceServiceImplUtility.isNonUpgradeCommercialProduct(commercialProduct)) {
					commerProdMemMapPAYG.put(commercialProduct.getId(), commercialProduct);
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
		if (!commercialProductsMatchedMemListForPAYG.isEmpty()) {
			getMemberByRules(groupType, listOfDeviceTile, deviceTile, groupName, listOfDeviceGroupMember,
					commerProdMemMapPAYG, bundleAndHardwareTupleListPAYG);
		} else {
			LogHelper.error(this, "No data found for given make and mmodel :" + make + " and " + model);
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}
		return listOfDeviceTile;

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
			String groupName, List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			Map<String, CommercialProduct> commerProdMemMapPAYG,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListPAYG) {
		if (listOfDeviceGroupMember != null && !listOfDeviceGroupMember.isEmpty()) {
			String leadMemberId = deviceServiceCommonUtility
					.getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember, null);
			if (leadMemberId != null) {
				deviceTile.setDeviceId(leadMemberId);
				deviceTile.setRating(deviceServiceCommonUtility.getDeviceTileRating(leadMemberId));
			}

			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
			// Calling Pricing Api
			if (bundleAndHardwareTupleListPAYG != null && !bundleAndHardwareTupleListPAYG.isEmpty()) {
				listOfPriceForBundleAndHardware = commonUtility.getPriceDetails(bundleAndHardwareTupleListPAYG, null,
						null);
			}
			Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
			if (bundleAndHardwareTupleListPAYG != null && !bundleAndHardwareTupleListPAYG.isEmpty()) {
				List<BundleAndHardwarePromotions> allPromotions = commonUtility
						.getPromotionsForBundleAndHardWarePromotions(bundleAndHardwareTupleListPAYG, null);
				if (allPromotions != null && !allPromotions.isEmpty()) {
					allPromotions.forEach(
							promotion -> bundleAndHardwarePromotionsMap.put(promotion.getHardwareId(), promotion));
				}
			}
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
			if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
				listOfPriceForBundleAndHardware.forEach(priceForBundleAndHardware -> priceMapForParticularDevice
						.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(), priceForBundleAndHardware));
			}

			deviceTile.setGroupName(groupName);
			deviceTile.setGroupType(groupType);
			CompletableFuture<List<DeviceSummary>> future1 = getDeviceSummery_Implementation_PAYG(
					listOfDeviceGroupMember, commerProdMemMapPAYG, groupType, priceMapForParticularDevice,
					bundleAndHardwarePromotionsMap, Constants.CATALOG_VERSION.get());
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
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST);
		}
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
		return DeviceServiceImplUtility.isSellable(journeyType, commercialBundle);
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
	public CompletableFuture<List<DeviceSummary>> getDeviceSummery_Implementation(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
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
				Constants.CATALOG_VERSION.set(version);
				for (com.vf.uk.dal.device.entity.Member member : listOfDeviceGroupMember) {
					boolean isConditional =false;
					PriceForBundleAndHardware priceForBundleAndHardware = null;
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					CommercialBundle comBundle = null;
					List<BundleAndHardwarePromotions> promotions = null;
					if (isConditionalAcceptJourney && commercialProduct != null) {
						isConditional=true;
						if (isLeadPlanWithinCreditLimit_Implementation(commercialProduct, creditLimit,
								listOfPriceForBundleAndHardwareLocal, journeyType)) {
							comBundle = deviceEs.getCommercialBundle(commercialProduct.getLeadPlanId());
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
							promotions = commonUtility.getPromotionsForBundleAndHardWarePromotions(
									bundleHardwareTupleList, journeyType);
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
					if(isConditional){
						priceForBundleAndHardware = !listOfPriceForBundleAndHardwareLocal.isEmpty()?listOfPriceForBundleAndHardwareLocal.get(0):null;
					}else if (priceMapForParticularDevice.containsKey(member.getId()) && !isConditional) {
						priceForBundleAndHardware = priceMapForParticularDevice.get(member.getId());
					}
					deviceSummary = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile(
							memberPriority, commercialProduct, comBundle, priceForBundleAndHardware, promotions,
							groupType, isConditionalAcceptJourney, fromPricingMap,cdnDomain);

					if (null != deviceSummary && commercialProduct != null) {
						DeviceServiceImplUtility.isPlanAffordable_Implementation(deviceSummary, comBundle, creditLimit,
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
	public void resetDeviceId_Implementation(boolean isConditionalAcceptJourney, DeviceTile deviceTile,
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
	 * @param listOfDeviceGroupMember
	 * @param commerProdMemMap
	 * @param groupType=PAYG
	 * @param priceMapForParticularDevice
	 * @return CompletableFuture<List<DeviceSummary>>
	 */
	public CompletableFuture<List<DeviceSummary>> getDeviceSummery_Implementation_PAYG(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember,
			Map<String, CommercialProduct> commerProdMemMap, String groupType,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice,
			Map<String, BundleAndHardwarePromotions> promotions, String version) {
		return CompletableFuture.supplyAsync(new Supplier<List<DeviceSummary>>() {

			List<DeviceSummary> listOfDeviceSummaryLocal = new ArrayList<>();
			DeviceSummary deviceSummary;

			@Override
			public List<DeviceSummary> get() {
				Constants.CATALOG_VERSION.set(version);
				for (com.vf.uk.dal.device.entity.Member member : listOfDeviceGroupMember) {
					CommercialProduct commercialProduct = commerProdMemMap.get(member.getId());
					BundleAndHardwarePromotions promotion = promotions.get(member.getId());
					Long memberPriority = Long.valueOf(member.getPriority());
					if (commercialProduct != null) {
						PriceForBundleAndHardware priceForBundleAndHardware = null;
						if (priceMapForParticularDevice.containsKey(member.getId())) {
							priceForBundleAndHardware = priceMapForParticularDevice.get(member.getId());
						}
						deviceSummary = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile_PAYG(
								memberPriority, commercialProduct, priceForBundleAndHardware, groupType, promotion,cdnDomain);
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
	 * Check if lead plan associated with commercial product is within credit
	 * limit.
	 * 
	 * @param product
	 * @param creditDetails
	 * @return
	 */
	public boolean isLeadPlanWithinCreditLimit_Implementation(CommercialProduct product, Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String journeyType) {
		List<BundleAndHardwareTuple> bundles = new ArrayList<>();

		BundleAndHardwareTuple tuple = new BundleAndHardwareTuple();
		tuple.setBundleId(product.getLeadPlanId());
		tuple.setHardwareId(product.getId());

		bundles.add(tuple);

		List<PriceForBundleAndHardware> priceForBundleAndHardwares = commonUtility.getPriceDetails(bundles, null,
				 journeyType);

		if (DeviceServiceImplUtility.isPlanPriceWithinCreditLimit_Implementation(creditLimit,
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
	public CommercialBundle getLeadBundleBasedOnAllPlans_Implementation(Double creditLimit,
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
				priceForBundleAndHardwares = commonUtility.getPriceDetails(bundleAndHardwareTupleList, null,
						journeyType);
			}
			if (priceForBundleAndHardwares != null && CollectionUtils.isNotEmpty(priceForBundleAndHardwares)) {
				Iterator<PriceForBundleAndHardware> iterator = priceForBundleAndHardwares.iterator();
				while (iterator.hasNext()) {

					PriceForBundleAndHardware priceForBundleAndHardware = iterator.next();
					if (null != priceForBundleAndHardware.getBundlePrice()) {
						DeviceServiceImplUtility.calculateDiscount(creditLimit, iterator, priceForBundleAndHardware);

					}

				}
				if (CollectionUtils.isNotEmpty(priceForBundleAndHardwares)) {
					listOfPriceForBundleAndHardware.clear();
					List<PriceForBundleAndHardware> sortedPlanList = DeviceTilesDaoUtils
							.sortPlansBasedOnMonthlyPrice(priceForBundleAndHardwares);
					listOfPriceForBundleAndHardware.addAll(sortedPlanList);
					PriceForBundleAndHardware leadBundle = sortedPlanList.get(0);
					return deviceEs.getCommercialBundle(leadBundle.getBundlePrice().getBundleId());
				}

			}
		}

		return null;

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
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember, List<Group> listOfProductGroup,
			List<CommercialProduct> commercialProductsMatchedMemList, Map<String, CommercialProduct> commerProdMemMap,
			Map<String, CommercialBundle> commerBundleIdMap, List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> bundleIdMap, Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap,
			List<String> listofLeadPlan) {
		com.vf.uk.dal.device.entity.Member entityMember;
		String groupName = null;
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
						List<String> listOfCompatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds() == null
								? Collections.emptyList() : commercialProduct.getListOfCompatiblePlanIds();
						if (StringUtils.isNotBlank(bundleId) && listOfCompatiblePlanIds.contains(bundleId)) {
							getListOfLeadPlan(bundleId, bundleAndHardwareTupleList, bundleIdMap, fromPricingMap,
									leadPlanIdMap, listofLeadPlan, member, commercialProduct);
						} else {
							bundleIdMap.put(commercialProduct.getId(), false);
							if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
									&& isJourneySpecificLeadPlan(commerBundleIdMap, commercialProduct.getLeadPlanId(),
											journeyType)
									&& listOfCompatiblePlanIds.contains(commercialProduct.getLeadPlanId())) {
								DeviceServiceImplUtility.getPricingMap(bundleAndHardwareTupleList, fromPricingMap,
										leadPlanIdMap, listofLeadPlan, commercialProduct);
							} else {
								if (CollectionUtils.isNotEmpty(commercialProduct.getListOfCompatiblePlanIds()))

								{
									DeviceServiceImplUtility.getBundleAndHardwareTupleList(bundleAndHardwareTupleList,
											fromPricingMap, commercialProduct);
								}
							}
						}
					}
				}
			}
		}
		return groupName;
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
		listofLeadBundleId = DeviceServiceImplUtility.getlistofLeadBundleId(listOfCommercialProducts, make, model,
				journeyType, commerProdMemMap);
		List<CommercialBundle> commercialBundles = deviceEs
				.getListOfCommercialBundle(new ArrayList<>(listofLeadBundleId));
		commercialBundles
				.forEach(commercialBundle -> commerBundleIdMap.put(commercialBundle.getId(), commercialBundle));
	}
}