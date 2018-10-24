package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price;
import com.vf.uk.dal.device.datamodel.product.CacheOfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CacheProductModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.CacheProductGroupModel;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.CacheDeviceService;
import com.vf.uk.dal.device.utils.CacheDeviceDaoUtils;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails;
/**
 * 
 * @author sahil.monga
 *
 */
@Component
public class CacheDeviceServiceImpl implements CacheDeviceService {

	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";
	public static final String IS_LEAD_MEMEBER_YES = "Y";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String JOURNEY_TYPE_SECONDLINE_UPGRADE = "Upgrade,SecondLine";
	public static final String OFFERCODE_PAYM = "PAYM";
	public static final String STRING_OPT = "opt_";
	public static final String STRING_PRODUCT = "product";
	
	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	DeviceTileCacheDAO deviceTileCacheDAO;

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;
	
	@Autowired
	CommonUtility commonUtility;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @param groupType
	 * @return List<DevicePreCalculatedData>
	 */
	public List<DevicePreCalculatedData> getDeviceListFromPricing(String groupType) {
		List<String> deviceIds = new ArrayList<>();
		Map<String, String> minimumPriceMap = new ConcurrentHashMap<>();
		List<DevicePreCalculatedData> listOfProductGroupRepository = new ArrayList<>();
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(groupType);
		List<String> listOfDeviceId = new ArrayList<>();
		List<String> listOfOfferCodesForUpgrade = new ArrayList<>();
		List<String> listOfSecondLineOfferCode = new ArrayList<>();
		Set<String> listOfOfferCodes = new HashSet<>();
		getOfferCodeForCacheDeviceTile(listOfOfferCodesForUpgrade, listOfSecondLineOfferCode, listOfOfferCodes);

		Map<String, String> leadMemberMap = new ConcurrentHashMap<>();
		Map<String, String> leadMemberMapForUpgrade = new ConcurrentHashMap<>();
		Map<String, String> groupIdAndNameMap = new ConcurrentHashMap<>();
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware = new ConcurrentHashMap<>();
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroup : listOfProductGroup) {

				getMemberForPayMCacheDevice(listOfDeviceId, leadMemberMap, leadMemberMapForUpgrade, groupIdAndNameMap,
						productGroup);
			}
			Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap = new ConcurrentHashMap<>();
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new ConcurrentHashMap<>();
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap = new ConcurrentHashMap<>();
			Map<String, CommercialBundle> commercialBundleMap = new ConcurrentHashMap<>();
			Set<String> setOfCompatiblePlanIds = new HashSet<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId = new ArrayList<>();
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListJourneyAware = new HashSet<>();
			if (!listOfDeviceId.isEmpty()) {
				Map<String, String> listOfLeadPlanId = new ConcurrentHashMap<>();
				Map<String, List<String>> listOfCimpatiblePlanMap = new ConcurrentHashMap<>();
				List<CommercialProduct> listOfCommercialProduct = deviceEs.getListOfCommercialProduct(listOfDeviceId);
				if (listOfCommercialProduct != null && !listOfCommercialProduct.isEmpty()) {
					DeviceUtils.getBundleharwareTrupleForPaymCacheDevice(setOfCompatiblePlanIds,
							bundleAndHardwareTupleList, bundleAndHardwareTupleListForNonLeanPlanId,
							bundleAndHardwareTupleListJourneyAware, listOfLeadPlanId, listOfCimpatiblePlanMap,
							listOfCommercialProduct);
				}
				getCoomercialBundleMapForPaymCacheDevice(commercialBundleMap, setOfCompatiblePlanIds);
				if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
					getLeadPlanMapForPaymCacheDevice(leadPlanIdPriceMap, bundleAndHardwareTupleList);
				}
				if (bundleAndHardwareTupleListForNonLeanPlanId != null
						&& !bundleAndHardwareTupleListForNonLeanPlanId.isEmpty()) {
					getNonLeadPlanMapForPaymCachedevice(nonLeadPlanIdPriceMap,
							bundleAndHardwareTupleListForNonLeanPlanId);
				}
				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
				Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap = new ConcurrentHashMap<>();
				Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new ConcurrentHashMap<>();
				for (String deviceId : listOfDeviceId) {
					getDevicePrecaldataForPaymCacheDeviceTile(groupType, deviceIds, listOfProductGroupRepository,
							listOfOfferCodes, leadMemberMap, leadMemberMapForUpgrade, groupIdAndNameMap,
							leadPlanIdPriceMap, nonLeadPlanIdPriceMap, groupNamePriceMap, commercialBundleMap,
							listOfLeadPlanId, listOfCimpatiblePlanMap, listOfPriceForBundleAndHardware,
							bundleHardwareTroupleMap, iLSPriceMap, deviceId);
				}
				Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap = new ConcurrentHashMap<>();
				if (!bundleHardwareTroupleMap.isEmpty()) {
					getIlsPriceWithOfferCodeAndJourney(listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
							bundleHardwareTroupleMap, ilsPriceForJourneyAwareOfferCodeMap);

				}
				if (!ilsPriceForJourneyAwareOfferCodeMap.isEmpty()) {
					DeviceUtils.getEntireIlsJourneyAwareMap(ilsOfferPriceWithJourneyAware,
							ilsPriceForJourneyAwareOfferCodeMap);
				}
				if (!groupNamePriceMap.isEmpty()) {
					DeviceUtils.getMinimumPriceMap(minimumPriceMap, groupNamePriceMap);

				}
			}
			/**
			 * ILSPrice Price With Journey Without OfferCode
			 */
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade = commonUtility
					.getPriceDetailsUsingBundleHarwareTrouple(
							new ArrayList<com.vf.uk.dal.device.entity.BundleAndHardwareTuple>(
									bundleAndHardwareTupleListJourneyAware),
							null, JOURNEY_TYPE_UPGRADE);

			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareWithoutOfferCodeForSecondLine = commonUtility
					.getPriceDetailsUsingBundleHarwareTrouple(
							new ArrayList<com.vf.uk.dal.device.entity.BundleAndHardwareTuple>(
									bundleAndHardwareTupleListJourneyAware),
							null, JOURNEY_TYPE_SECONDLINE);

			Map<String, Map<String, List<PriceForBundleAndHardware>>> mapOfIlsPriceWithoutOfferCode = new ConcurrentHashMap<>();
			mapOfIlsPriceWithoutOfferCode.put(JOURNEY_TYPE_UPGRADE, CacheDeviceDaoUtils
					.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade));
			mapOfIlsPriceWithoutOfferCode.put(JOURNEY_TYPE_SECONDLINE, CacheDeviceDaoUtils
					.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForSecondLine));

			// Ratings population logic
			Map<String, String> ratingsReviewMap = deviceServiceCommonUtility
					.getDeviceReviewRating_Implementation(deviceIds);
			listOfProductGroupRepository.forEach(
					deviceDataRating -> DeviceUtils.updateDevicePrecaldataBasedOnIlsPriceAndRating(minimumPriceMap,
							ilsOfferPriceWithJourneyAware, mapOfIlsPriceWithoutOfferCode, ratingsReviewMap,
							deviceDataRating));
		} else {
			LogHelper.error(this, "Receieved Null Values for the given product group type");
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		return listOfProductGroupRepository;

	}

	/**
	 * 
	 * @param listOfOfferCodesForUpgrade
	 * @param listOfSecondLineOfferCode
	 * @param bundleHardwareTroupleMap
	 * @param ilsPriceForJourneyAwareOfferCodeMap
	 */
	@Override
	public void getIlsPriceWithOfferCodeAndJourney(List<String> listOfOfferCodesForUpgrade,
			List<String> listOfSecondLineOfferCode, Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap) {
		String jouneyType = null;
		for (Entry<String, List<BundleAndHardwareTuple>> entry : bundleHardwareTroupleMap.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty()) {
				Map<String, List<PriceForBundleAndHardware>> iLSPriceMapLocalMain = new ConcurrentHashMap<>();
				jouneyType = DeviceUtils.getJourneybasedOnOfferCode(listOfOfferCodesForUpgrade,
						listOfSecondLineOfferCode, entry);
				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForOffer = commonUtility
						.getPriceDetailsUsingBundleHarwareTrouple(entry.getValue(), entry.getKey(), jouneyType);
				DeviceUtils.getIlsPriceForJourneyAwareOfferCodeMap(ilsPriceForJourneyAwareOfferCodeMap, jouneyType,
						entry, iLSPriceMapLocalMain, listOfPriceForBundleAndHardwareForOffer);
			}
		}
	}

	/**
	 * 
	 * @param groupType
	 * @param deviceIds
	 * @param listOfProductGroupRepository
	 * @param listOfOfferCodes
	 * @param leadMemberMap
	 * @param leadMemberMapForUpgrade
	 * @param groupIdAndNameMap
	 * @param leadPlanIdPriceMap
	 * @param nonLeadPlanIdPriceMap
	 * @param groupNamePriceMap
	 * @param commercialBundleMap
	 * @param listOfLeadPlanId
	 * @param listOfCimpatiblePlanMap
	 * @param listOfPriceForBundleAndHardware
	 * @param bundleHardwareTroupleMap
	 * @param iLSPriceMap
	 * @param deviceId
	 */
	@Override
	public void getDevicePrecaldataForPaymCacheDeviceTile(String groupType, List<String> deviceIds,
			List<DevicePreCalculatedData> listOfProductGroupRepository, Set<String> listOfOfferCodes,
			Map<String, String> leadMemberMap, Map<String, String> leadMemberMapForUpgrade,
			Map<String, String> groupIdAndNameMap, Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			Map<String, CommercialBundle> commercialBundleMap, Map<String, String> listOfLeadPlanId,
			Map<String, List<String>> listOfCimpatiblePlanMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap,
			Map<String, List<PriceForBundleAndHardware>> iLSPriceMap, String deviceId) {
		DevicePreCalculatedData productGroupForDeviceListing;
		PriceForBundleAndHardware bundleHeaderForDevice;
		String groupId = null;
		String groupname = null;
		String nonUpgradeLeadPlanId = null;
		String upgradeLeadPlanId = null;
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
				if (DeviceServiceImplUtility.isSellable(JOURNEY_TYPE_ACQUISITION, coommercialbundle)) {
					nonUpgradeLeadPlanId = bundleId;
				}
				if (DeviceServiceImplUtility.isSellable(JOURNEY_TYPE_UPGRADE, coommercialbundle)) {
					upgradeLeadPlanId = bundleId;
				}
			}
			if (StringUtils.isNotBlank(nonUpgradeLeadPlanId) || StringUtils.isNotBlank(upgradeLeadPlanId)) {
				if (StringUtils.isBlank(nonUpgradeLeadPlanId) && nonLeadPlanIdPriceMap.containsKey(deviceId)) {
					nonUpgradeLeadPlanId = getNonUpgradeLeadPlanIdForPaymCacheDevice(nonLeadPlanIdPriceMap,
							groupNamePriceMap, commercialBundleMap, listOfPriceForBundleAndHardware, deviceId,
							groupname);
				}
				if (StringUtils.isBlank(upgradeLeadPlanId) && nonLeadPlanIdPriceMap.containsKey(deviceId)) {
					upgradeLeadPlanId = getUpgradeLeadPlanIdForCacheDevice(nonLeadPlanIdPriceMap, commercialBundleMap,
							deviceId);

				}
				LogHelper.info(this, "Lead Plan Id Present " + nonUpgradeLeadPlanId);
				if (StringUtils.isNotBlank(nonUpgradeLeadPlanId) && !leadPlanIdPriceMap.isEmpty()
						&& leadPlanIdPriceMap.containsKey(deviceId)) {
					DeviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, groupNamePriceMap,
							listOfPriceForBundleAndHardware, deviceId, groupname);
				}
			} else {

				if (nonLeadPlanIdPriceMap.containsKey(deviceId)) {
					List<PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap.get(deviceId);
					bundleHeaderForDevice = deviceServiceCommonUtility.identifyLowestPriceOfPlanForDevice(listOfPrice,
							commercialBundleMap, JOURNEY_TYPE_ACQUISITION);
					PriceForBundleAndHardware upgradeCompatiblePlan = deviceServiceCommonUtility
							.identifyLowestPriceOfPlanForDevice(listOfPrice, commercialBundleMap,
									JOURNEY_TYPE_UPGRADE);
					if (upgradeCompatiblePlan != null) {
						upgradeLeadPlanId = upgradeCompatiblePlan.getBundlePrice().getBundleId();
					}

					if (bundleHeaderForDevice != null) {
						nonUpgradeLeadPlanId = DeviceUtils.getGroupNamePriceMap(groupNamePriceMap,
								listOfPriceForBundleAndHardware, bundleHeaderForDevice, groupname);
					}
				}
			}
			// ILS OfferCode
			if (nonUpgradeLeadPlanId != null && listOfCimpatiblePlanMap.containsKey(deviceId)) {
				DeviceUtils.getIlsPriceMap(listOfOfferCodes, commercialBundleMap, listOfCimpatiblePlanMap,
						bundleHardwareTroupleMap, deviceId);
			}
			productGroupForDeviceListing = CacheDeviceDaoUtils
					.convertBundleHeaderForDeviceToProductGroupForDeviceListing(deviceId, nonUpgradeLeadPlanId,
							groupname, groupId, listOfPriceForBundleAndHardware, leadMemberMap, iLSPriceMap,
							leadMemberMapForUpgrade, upgradeLeadPlanId, groupType);
			if (productGroupForDeviceListing != null) {
				deviceIds.add(productGroupForDeviceListing.getDeviceId());
				listOfProductGroupRepository.add(productGroupForDeviceListing);
			}
		} catch (Exception e) {
			listOfPriceForBundleAndHardware.clear();
			LogHelper.error(this, " Exception occured when call happen to compatible bundles api: " + e);
		}finally{
			listOfPriceForBundleAndHardware.clear();
		}
	}

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param commercialBundleMap
	 * @param deviceId
	 * @return upgradeLeadPlanId
	 */
	@Override
	public String getUpgradeLeadPlanIdForCacheDevice(Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, CommercialBundle> commercialBundleMap, String deviceId) {
		String upgradeLeadPlanId = null;
		List<PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap.get(deviceId);
		PriceForBundleAndHardware upgradeCompatiblePlan = deviceServiceCommonUtility
				.identifyLowestPriceOfPlanForDevice(listOfPrice, commercialBundleMap, JOURNEY_TYPE_UPGRADE);
		if (upgradeCompatiblePlan != null) {
			upgradeLeadPlanId = upgradeCompatiblePlan.getBundlePrice().getBundleId();
		}
		return upgradeLeadPlanId;
	}

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param groupNamePriceMap
	 * @param commercialBundleMap
	 * @param listOfPriceForBundleAndHardware
	 * @param deviceId
	 * @param groupname
	 * @param nonUpgradeLeadPlanId
	 * @return nonUpgradeLeadPlanId
	 */
	@Override
	public String getNonUpgradeLeadPlanIdForPaymCacheDevice(
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			Map<String, CommercialBundle> commercialBundleMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String deviceId, String groupname) {
		String nonUpgradeLeadPlanId = null;
		PriceForBundleAndHardware bundleHeaderForDevice;
		List<PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap.get(deviceId);
		bundleHeaderForDevice = deviceServiceCommonUtility.identifyLowestPriceOfPlanForDevice(listOfPrice,
				commercialBundleMap, JOURNEY_TYPE_ACQUISITION);
		if (bundleHeaderForDevice != null) {
			nonUpgradeLeadPlanId = DeviceUtils.getGroupNamePriceMap(groupNamePriceMap, listOfPriceForBundleAndHardware,
					bundleHeaderForDevice, groupname);
		}
		return nonUpgradeLeadPlanId;
	}

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param bundleAndHardwareTupleListForNonLeanPlanId
	 */
	public void getNonLeadPlanMapForPaymCachedevice(Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForNonLeadPlanIds;
		listOfPriceForBundleAndHardwareForNonLeadPlanIds = commonUtility.getPriceDetailsUsingBundleHarwareTrouple(
				bundleAndHardwareTupleListForNonLeanPlanId, null, null);
		if (listOfPriceForBundleAndHardwareForNonLeadPlanIds != null
				&& !listOfPriceForBundleAndHardwareForNonLeadPlanIds.isEmpty()) {
			DeviceUtils.getNonLeadPlanMap(nonLeadPlanIdPriceMap, listOfPriceForBundleAndHardwareForNonLeadPlanIds);
		}
	}

	/**
	 * 
	 * @param commercialBundleMap
	 * @param setOfCompatiblePlanIds
	 */
	public void getCoomercialBundleMapForPaymCacheDevice(Map<String, CommercialBundle> commercialBundleMap,
			Set<String> setOfCompatiblePlanIds) {
		List<CommercialBundle> listOfCommercialBundle = null;
		if (!setOfCompatiblePlanIds.isEmpty()) {
			listOfCommercialBundle = deviceEs.getListOfCommercialBundle(new ArrayList<>(setOfCompatiblePlanIds));
		}
		if (listOfCommercialBundle != null && !listOfCommercialBundle.isEmpty()) {
			listOfCommercialBundle.forEach(

					commercialBundle -> commercialBundleMap.put(commercialBundle.getId(), commercialBundle));
		}
	}

	/**
	 * 
	 * @param leadPlanIdPriceMap
	 * @param bundleAndHardwareTupleList
	 */
	public void getLeadPlanMapForPaymCacheDevice(Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForLeadPlanIds;
		listOfPriceForBundleAndHardwareForLeadPlanIds = commonUtility
				.getPriceDetailsUsingBundleHarwareTrouple(bundleAndHardwareTupleList, null, null);
		if (listOfPriceForBundleAndHardwareForLeadPlanIds != null
				&& !listOfPriceForBundleAndHardwareForLeadPlanIds.isEmpty()) {
			DeviceUtils.getLeadPlanMap(leadPlanIdPriceMap, listOfPriceForBundleAndHardwareForLeadPlanIds);
		}
	}

	/**
	 * 
	 * @param listOfDeviceId
	 * @param leadMemberMap
	 * @param leadMemberMapForUpgrade
	 * @param groupIdAndNameMap
	 * @param productGroup
	 */
	public void getMemberForPayMCacheDevice(List<String> listOfDeviceId, Map<String, String> leadMemberMap,
			Map<String, String> leadMemberMapForUpgrade, Map<String, String> groupIdAndNameMap, Group productGroup) {
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
		String groupId = String.valueOf(productGroup.getGroupId());
		String groupname = productGroup.getName();
		if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
			DeviceUtils.getMemberForCaceDevice(listOfDeviceId, groupIdAndNameMap, productGroup, listOfDeviceGroupMember,
					groupId, groupname);
		}
		String leadMemberId = null;
		String leadMemberIdForUpgrade = null;

		leadMemberId = deviceServiceCommonUtility.getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember,
				JOURNEY_TYPE_ACQUISITION);
		leadMemberIdForUpgrade = deviceServiceCommonUtility
				.getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember, JOURNEY_TYPE_UPGRADE);

		if (leadMemberId != null) {
			leadMemberMap.put(leadMemberId, IS_LEAD_MEMEBER_YES);
		}
		if (leadMemberIdForUpgrade != null) {
			leadMemberMapForUpgrade.put(leadMemberIdForUpgrade, IS_LEAD_MEMEBER_YES);
		}
	}

	/**
	 * 
	 * @param listOfOfferCodesForUpgrade
	 * @param listOfSecondLineOfferCode
	 * @param listOfOfferCodes
	 */
	public void getOfferCodeForCacheDeviceTile(List<String> listOfOfferCodesForUpgrade,
			List<String> listOfSecondLineOfferCode, Set<String> listOfOfferCodes) {
		List<MerchandisingPromotionModel> listOfMerchandisingPromotions = deviceEs.getListOfMerchandisingPromotionModel(
				OFFERCODE_PAYM, JOURNEY_TYPE_SECONDLINE_UPGRADE);
		listOfMerchandisingPromotions.forEach(promotionModel -> {
			if (StringUtils.isNotBlank(promotionModel.getTag())
					&& promotionModel.getPackageType().contains(JOURNEY_TYPE_SECONDLINE)) {
				listOfSecondLineOfferCode.add(promotionModel.getTag());
			}
			if (StringUtils.isNotBlank(promotionModel.getTag())
					&& promotionModel.getPackageType().contains(JOURNEY_TYPE_UPGRADE)) {
				listOfOfferCodesForUpgrade.add(promotionModel.getTag());
			}
			if (StringUtils.isNotBlank(promotionModel.getTag())) {
				listOfOfferCodes.add(promotionModel.getTag());
			}

		});
	}

	/**
	 * 
	 * @param listOfCommercialProducts
	 * @param listOfProductGroup
	 * @param make
	 * @param model
	 * @param groupType=PAYG
	 * @return listOfProductGroupRepository
	 */
	public List<DevicePreCalculatedData> getDeviceListFromPricingForPayG(String groupType) {
		List<String> deviceIds = new ArrayList<>();
		Map<String, String> minimumPriceMap = new ConcurrentHashMap<>();
		List<DevicePreCalculatedData> listOfProductGroupRepository = new ArrayList<>();
		DevicePreCalculatedData productGroupForDeviceListing;
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(groupType);
		List<String> listOfDeviceId = new ArrayList<>();
		Map<String, String> leadMemberMap = new ConcurrentHashMap<>();
		Map<String, String> groupIdAndNameMap = new ConcurrentHashMap<>();
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroup : listOfProductGroup) {
				getLeadMembermapForCacheDevicePayg(listOfDeviceId, leadMemberMap, groupIdAndNameMap, productGroup);
			}
			Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap = new ConcurrentHashMap<>();
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap = new ConcurrentHashMap<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			if (!listOfDeviceId.isEmpty()) {
				List<CommercialProduct> listOfCommercialProduct = deviceEs.getListOfCommercialProduct(listOfDeviceId);
				if (listOfCommercialProduct != null && !listOfCommercialProduct.isEmpty()) {
					listOfCommercialProduct.forEach(commercialProduct -> {
						BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
						bundleAndHardwareTuple.setBundleId(null);
						bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
						bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
					});
				}
				if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
					getLeadPlanMapForPaymCacheDevice(leadPlanIdPriceMap, bundleAndHardwareTupleList);
				}
				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
				for (String deviceId : listOfDeviceId) {
					String groupId = null;
					String groupname = null;
					if (groupIdAndNameMap.containsKey(deviceId)) {
						String[] groupDetails = groupIdAndNameMap.get(deviceId).split("&&");
						groupname = groupDetails[0];
						groupId = groupDetails[1];
					}
					try {
						if (!leadPlanIdPriceMap.isEmpty() && leadPlanIdPriceMap.containsKey(deviceId)) {
							DeviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, groupNamePriceMap,
									listOfPriceForBundleAndHardware, deviceId, groupname);
						}
						productGroupForDeviceListing = CacheDeviceDaoUtils
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
					DeviceUtils.getMinimumPriceMapForPayG(minimumPriceMap, groupNamePriceMap);
				}
			}
			Map<String, String> ratingsReviewMap = deviceServiceCommonUtility
					.getDeviceReviewRating_Implementation(deviceIds);
			listOfProductGroupRepository.forEach(deviceDataRating -> {
				if (minimumPriceMap.containsKey(deviceDataRating.getProductGroupName())) {
					deviceDataRating.setMinimumCost(minimumPriceMap.get(deviceDataRating.getProductGroupName()));
				}
				DeviceUtils.updateDeviceratingForCacheDevice(ratingsReviewMap, deviceDataRating);
			});
		} else {
			LogHelper.error(this, "Receieved Null Values for the given product group type");
		}
		return listOfProductGroupRepository;

	}

	/**
	 * 
	 * @param listOfDeviceId
	 * @param leadMemberMap
	 * @param groupIdAndNameMap
	 * @param productGroup
	 */
	public void getLeadMembermapForCacheDevicePayg(List<String> listOfDeviceId, Map<String, String> leadMemberMap,
			Map<String, String> groupIdAndNameMap, Group productGroup) {
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
		String groupId = String.valueOf(productGroup.getGroupId());
		String groupname = productGroup.getName();
		if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
			DeviceUtils.getMemberForCaceDevice(listOfDeviceId, groupIdAndNameMap, productGroup, listOfDeviceGroupMember,
					groupId, groupname);
		}
		String leadMemberId = null;
		leadMemberId = deviceServiceCommonUtility.getMemeberBasedOnRules_Implementation(listOfDeviceGroupMember,
				JOURNEY_TYPE_ACQUISITION);
		if (leadMemberId != null) {
			leadMemberMap.put(leadMemberId, IS_LEAD_MEMEBER_YES);
		}
	}

	/**
	 * 
	 * @param preCalcDataList
	 */
	@Override
	public void indexPrecalData(
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> preCalcDataList) {
		try {
			Map<String, CacheProductGroupModel> productModelMap = new ConcurrentHashMap<>();
			for (com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData deviceListObject : preCalcDataList) {
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
				if (deviceListObject.getPriceInfo() != null
						&& deviceListObject.getPriceInfo().getHardwarePrice() != null) {
					List<DeviceFinancingOption> deviceFinancingOption = deviceListObject.getPriceInfo()
							.getHardwarePrice().getFinancingOptions();
					List<com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption> financeOptions = null;

					if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
						financeOptions = new ArrayList<>();
						for (DeviceFinancingOption financsOption : deviceFinancingOption) {
							com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption finance = new com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption();
							finance.setApr(financsOption.getApr());
							finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
							finance.setFinanceProvider(financsOption.getFinanceProvider());
							finance.setFinanceTerm(financsOption.getFinanceTerm());
							Price monthly = financsOption.getMonthlyPrice();
							com.vf.uk.dal.device.datamodel.product.Price deviceMonthlyPrice = new com.vf.uk.dal.device.datamodel.product.Price();
							deviceMonthlyPrice.setGross(monthly.getGross());
							deviceMonthlyPrice.setNet(monthly.getNet());
							deviceMonthlyPrice.setVat(monthly.getVat());
							finance.setMonthlyPrice(deviceMonthlyPrice);
							Price totalInterest = financsOption.getTotalPriceWithInterest();
							com.vf.uk.dal.device.datamodel.product.Price totalPriceWithInterest = new com.vf.uk.dal.device.datamodel.product.Price();
							totalPriceWithInterest.setGross(totalInterest.getGross());
							totalPriceWithInterest.setNet(totalInterest.getNet());
							totalPriceWithInterest.setVat(totalInterest.getVat());
							finance.setTotalPriceWithInterest(totalPriceWithInterest);
							financeOptions.add(finance);
						}
					}
					productModel.setFinancingOptions(financeOptions);
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
				String productIdForUpdate = STRING_OPT + STRING_PRODUCT + "_"
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
									List<DeviceFinancingOption> deviceFinancingOption = deviceListObject.getPriceInfo()
											.getHardwarePrice().getFinancingOptions();
									List<com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption> financeOptions = null;

									if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
										financeOptions = new ArrayList<>();
										for (DeviceFinancingOption financsOption : deviceFinancingOption) {
											com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption finance = new com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption();
											finance.setApr(financsOption.getApr());
											finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
											finance.setFinanceProvider(financsOption.getFinanceProvider());
											finance.setFinanceTerm(financsOption.getFinanceTerm());
											Price monthly = financsOption.getMonthlyPrice();
											com.vf.uk.dal.device.datamodel.product.Price deviceMonthlyPrice = new com.vf.uk.dal.device.datamodel.product.Price();
											deviceMonthlyPrice.setGross(monthly.getGross());
											deviceMonthlyPrice.setNet(monthly.getNet());
											deviceMonthlyPrice.setVat(monthly.getVat());
											finance.setMonthlyPrice(deviceMonthlyPrice);
											Price totalInterest = financsOption.getTotalPriceWithInterest();
											com.vf.uk.dal.device.datamodel.product.Price totalPriceWithInterest = new com.vf.uk.dal.device.datamodel.product.Price();
											totalPriceWithInterest.setGross(totalInterest.getGross());
											totalPriceWithInterest.setNet(totalInterest.getNet());
											totalPriceWithInterest.setVat(totalInterest.getVat());
											finance.setTotalPriceWithInterest(totalPriceWithInterest);
											financeOptions.add(finance);
										}
									}
									offerPrice.setFinancingOptions(financeOptions);
								}
								deviceDao.getIndexElasticSearch(id, mapper.writeValueAsString(offerPrice));
							}
						}
					}
				}
			}
			for (Map.Entry<String, CacheProductGroupModel> entry : productModelMap.entrySet()) {
				String productGroupId = STRING_OPT + "productGroup_" + entry.getKey();
				deviceDao.getUpdateElasticSearch(productGroupId, mapper.writeValueAsString(entry.getValue()));
			}

		} catch (Exception e) {
			LogHelper.error(this, "::::::Exception From es ::::::" + e);
		}
	}

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param groupType
	 * @return CompletableFuture
	 */
	@Override
	@Async
	public CompletableFuture<Integer> cacheDeviceTile(String groupType, String jobId, String version) {
		CatalogServiceAspect.CATALOG_VERSION.set(version);
		int i = 0;
		List<OfferAppliedPriceDetails> offerAppliedPrices = new ArrayList<>();

		boolean exceptionFlag = false;
		List<DevicePreCalculatedData> devicePreCalculatedData = new ArrayList<>();
		List<DevicePreCalculatedData> devicePreCalculatedDataForPayG = null;
		try {
			deviceTileCacheDAO.beginTransaction();
			if (StringUtils.containsIgnoreCase(groupType, STRING_DEVICE_PAYM)) {
				devicePreCalculatedData = getDeviceListFromPricing(STRING_DEVICE_PAYM);
			}
			if (StringUtils.containsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
				devicePreCalculatedDataForPayG = getDeviceListFromPricingForPayG(STRING_DEVICE_PAYG);
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
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> deviceListObjectList = CacheDeviceDaoUtils
					.convertDevicePreCalDataToSolrData(devicePreCalculatedData);
			indexPrecalData(deviceListObjectList);
		} catch (Exception e) {
			exceptionFlag = true;
			LogHelper.error(this, jobId + "==>" + e);
			deviceTileCacheDAO.rollBackTransaction();
		} finally {
			if (exceptionFlag) {
				deviceTileCacheDAO.updateCacheDeviceToDb(jobId, "FAILED");
			} else {
				deviceTileCacheDAO.updateCacheDeviceToDb(jobId, "SUCCESS");
			}
			deviceTileCacheDAO.endTransaction();
		}
		return CompletableFuture.completedFuture(i);
	}

	@Override
	public CacheDeviceTileResponse insertCacheDeviceToDb() {
		return deviceTileCacheDAO.insertCacheDeviceToDb();
	}

	@Override
	public void updateCacheDeviceToDb(String jobId, String jobStatus) {
		deviceTileCacheDAO.updateCacheDeviceToDb(jobId, jobStatus);
	}

	@Override
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) {

		return deviceTileCacheDAO.getCacheDeviceJobStatus(jobId);
	}

	/**
	 * Returns Device review details
	 * 
	 * @param deviceId
	 * @return DeviceReviewDetails
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
}
