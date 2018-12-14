package com.vf.uk.dal.device.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption;
import com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.model.merchandisingpromotion.Price;
import com.vf.uk.dal.device.model.product.CacheOfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.CacheProductModel;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.productgroups.CacheProductGroupModel;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.solr.DevicePreCalculatedData;
import com.vf.uk.dal.device.model.solr.OfferAppliedPriceDetails;
import com.vf.uk.dal.device.utils.CacheDeviceDaoUtils;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author sahil.monga
 *
 */
@Slf4j
@Component
public class CacheDeviceServiceImpl implements CacheDeviceService {

	private static final String ERROR_CODE_SELECT_CAHCE_DEVICE = "error_device_cache_device_failed";
	private static final String ERROR_CODE_SELECT_REVIEW = "error_device_review_failed";
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
	DeviceUtils deviceUtils;
	
	@Autowired
	DeviceTileCacheDAO deviceTileCacheDAO;

	@Autowired
	CacheDeviceDaoUtils cacheDeviceDaoUtils;
	
	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;
	
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

		Map<String, String> leadMemberMap = new HashMap<>();
		Map<String, String> leadMemberMapForUpgrade = new HashMap<>();
		Map<String, String> groupIdAndNameMap = new HashMap<>();
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware = new ConcurrentHashMap<>();
		setListOfProductGroupRepository(groupType, deviceIds, minimumPriceMap, listOfProductGroupRepository,
				listOfProductGroup, listOfDeviceId, listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
				listOfOfferCodes, leadMemberMap, leadMemberMapForUpgrade, groupIdAndNameMap,
				ilsOfferPriceWithJourneyAware);
		return listOfProductGroupRepository;

	}

	public  void setListOfProductGroupRepository(String groupType, List<String> deviceIds,
			Map<String, String> minimumPriceMap, List<DevicePreCalculatedData> listOfProductGroupRepository,
			List<Group> listOfProductGroup, List<String> listOfDeviceId, List<String> listOfOfferCodesForUpgrade,
			List<String> listOfSecondLineOfferCode, Set<String> listOfOfferCodes, Map<String, String> leadMemberMap,
			Map<String, String> leadMemberMapForUpgrade, Map<String, String> groupIdAndNameMap,
			Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware) {
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroup : listOfProductGroup) {

				getMemberForPayMCacheDevice(listOfDeviceId, leadMemberMap, leadMemberMapForUpgrade, groupIdAndNameMap,
						productGroup);
			}
			Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap = new ConcurrentHashMap<>();
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new ConcurrentHashMap<>();
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap = new ConcurrentHashMap<>();
			Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
			Set<String> setOfCompatiblePlanIds = new HashSet<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId = new ArrayList<>();
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListJourneyAware = new HashSet<>();
			if (!listOfDeviceId.isEmpty()) {
				Map<String, String> listOfLeadPlanId = new HashMap<>();
				Map<String, List<String>> listOfCimpatiblePlanMap = new HashMap<>();
				List<CommercialProduct> listOfCommercialProduct = deviceEs.getListOfCommercialProduct(listOfDeviceId);
				setBundleAndHardwareTupleListJourneyAware(setOfCompatiblePlanIds, bundleAndHardwareTupleList,
						bundleAndHardwareTupleListForNonLeanPlanId, bundleAndHardwareTupleListJourneyAware,
						listOfLeadPlanId, listOfCimpatiblePlanMap, listOfCommercialProduct);
				getCoomercialBundleMapForPaymCacheDevice(commercialBundleMap, setOfCompatiblePlanIds);
				setLeadNonLeadPriceMap(groupType, leadPlanIdPriceMap, nonLeadPlanIdPriceMap, bundleAndHardwareTupleList,
						bundleAndHardwareTupleListForNonLeanPlanId);
				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
				Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap = new HashMap<>();
				Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new ConcurrentHashMap<>();
				for (String deviceId : listOfDeviceId) {
					getDevicePrecaldataForPaymCacheDeviceTile(groupType, deviceIds, listOfProductGroupRepository,
							listOfOfferCodes, leadMemberMap, leadMemberMapForUpgrade, groupIdAndNameMap,
							leadPlanIdPriceMap, nonLeadPlanIdPriceMap, groupNamePriceMap, commercialBundleMap,
							listOfLeadPlanId, listOfCimpatiblePlanMap, listOfPriceForBundleAndHardware,
							bundleHardwareTroupleMap, iLSPriceMap, deviceId);
				}
				Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap = new ConcurrentHashMap<>();
				getMinimumPriceMap(groupType, minimumPriceMap, listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
						ilsOfferPriceWithJourneyAware, groupNamePriceMap, bundleHardwareTroupleMap,
						ilsPriceForJourneyAwareOfferCodeMap);
			}
			/**
			 * ILSPrice Price With Journey Without OfferCode
			 */
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade = commonUtility
					.getPriceDetailsUsingBundleHarwareTrouple(
							new ArrayList<com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple>(
									bundleAndHardwareTupleListJourneyAware),
							null, JOURNEY_TYPE_UPGRADE, groupType);

			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareWithoutOfferCodeForSecondLine = commonUtility
					.getPriceDetailsUsingBundleHarwareTrouple(
							new ArrayList<com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple>(
									bundleAndHardwareTupleListJourneyAware),
							null, JOURNEY_TYPE_SECONDLINE, groupType);

			Map<String, Map<String, List<PriceForBundleAndHardware>>> mapOfIlsPriceWithoutOfferCode = new ConcurrentHashMap<>();
			mapOfIlsPriceWithoutOfferCode.put(JOURNEY_TYPE_UPGRADE, cacheDeviceDaoUtils
					.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade));
			mapOfIlsPriceWithoutOfferCode.put(JOURNEY_TYPE_SECONDLINE, cacheDeviceDaoUtils
					.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForSecondLine));

			/** Ratings population logic */
			Map<String, String> ratingsReviewMap = deviceServiceCommonUtility
					.getDeviceReviewRatingImplementation(deviceIds);
			listOfProductGroupRepository.forEach(
					deviceDataRating -> deviceUtils.updateDevicePrecaldataBasedOnIlsPriceAndRating(minimumPriceMap,
							ilsOfferPriceWithJourneyAware, mapOfIlsPriceWithoutOfferCode, ratingsReviewMap,
							deviceDataRating));
		} else {
			log.error("Receieved Null Values for the given product group type");
			throw new DeviceCustomException(ERROR_CODE_SELECT_CAHCE_DEVICE,ExceptionMessages.NULL_VALUE_GROUP_TYPE,"404");
		}
	}

	public  void getMinimumPriceMap(String groupType, Map<String, String> minimumPriceMap,
			List<String> listOfOfferCodesForUpgrade, List<String> listOfSecondLineOfferCode,
			Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap) {
		if (!bundleHardwareTroupleMap.isEmpty()) {
			getIlsPriceWithOfferCodeAndJourney(listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
					bundleHardwareTroupleMap, ilsPriceForJourneyAwareOfferCodeMap, groupType);

		}
		if (!ilsPriceForJourneyAwareOfferCodeMap.isEmpty()) {
			deviceUtils.getEntireIlsJourneyAwareMap(ilsOfferPriceWithJourneyAware, ilsPriceForJourneyAwareOfferCodeMap);
		}
		if (!groupNamePriceMap.isEmpty()) {
			deviceUtils.getMinimumPriceMap(minimumPriceMap, groupNamePriceMap);

		}
	}

	public  void setLeadNonLeadPriceMap(String groupType, Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId) {
		getLeadPlanMapForPaymCacheDeviceForPayg(groupType, leadPlanIdPriceMap, bundleAndHardwareTupleList);
		if (bundleAndHardwareTupleListForNonLeanPlanId != null
				&& !bundleAndHardwareTupleListForNonLeanPlanId.isEmpty()) {
			getNonLeadPlanMapForPaymCachedevice(nonLeadPlanIdPriceMap, bundleAndHardwareTupleListForNonLeanPlanId,
					groupType);
		}
	}

	public  void setBundleAndHardwareTupleListJourneyAware(Set<String> setOfCompatiblePlanIds,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId,
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListJourneyAware, Map<String, String> listOfLeadPlanId,
			Map<String, List<String>> listOfCimpatiblePlanMap, List<CommercialProduct> listOfCommercialProduct) {
		if (listOfCommercialProduct != null && !listOfCommercialProduct.isEmpty()) {
			deviceUtils.getBundleharwareTrupleForPaymCacheDevice(setOfCompatiblePlanIds, bundleAndHardwareTupleList,
					bundleAndHardwareTupleListForNonLeanPlanId, bundleAndHardwareTupleListJourneyAware,
					listOfLeadPlanId, listOfCimpatiblePlanMap, listOfCommercialProduct);
		}
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
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap,
			String groupType) {
		String jouneyType = null;
		for (Entry<String, List<BundleAndHardwareTuple>> entry : bundleHardwareTroupleMap.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty()) {
				Map<String, List<PriceForBundleAndHardware>> iLSPriceMapLocalMain = new ConcurrentHashMap<>();
				jouneyType = deviceUtils.getJourneybasedOnOfferCode(listOfOfferCodesForUpgrade,
						listOfSecondLineOfferCode, entry);
				List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForOffer = commonUtility
						.getPriceDetailsUsingBundleHarwareTrouple(entry.getValue(), entry.getKey(), jouneyType,
								groupType);
				deviceUtils.getIlsPriceForJourneyAwareOfferCodeMap(ilsPriceForJourneyAwareOfferCodeMap, jouneyType,
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
				nonUpgradeLeadPlanId = getNonUpgradeLeadPlanId(bundleId, coommercialbundle);
				upgradeLeadPlanId = getUpgradeLeadPlanId(bundleId, coommercialbundle);
			}
			if (StringUtils.isNotBlank(nonUpgradeLeadPlanId) || StringUtils.isNotBlank(upgradeLeadPlanId)) {
				nonUpgradeLeadPlanId = getNonUpgradeLeadPlanIdForPaymDevice(nonLeadPlanIdPriceMap, groupNamePriceMap,
						commercialBundleMap, listOfPriceForBundleAndHardware, deviceId, groupname,
						nonUpgradeLeadPlanId);
				upgradeLeadPlanId = getUpgradeLeadPlanIdForPaymDevice(nonLeadPlanIdPriceMap, commercialBundleMap,
						deviceId, upgradeLeadPlanId);
				log.info("Lead Plan Id Present " + nonUpgradeLeadPlanId);
				getLeadPlanGroupPriceMap(leadPlanIdPriceMap, groupNamePriceMap, listOfPriceForBundleAndHardware,
						deviceId, groupname, nonUpgradeLeadPlanId);
			} else {

				if (nonLeadPlanIdPriceMap.containsKey(deviceId)) {
					List<PriceForBundleAndHardware> listOfPrice = nonLeadPlanIdPriceMap.get(deviceId);
					bundleHeaderForDevice = deviceServiceCommonUtility.identifyLowestPriceOfPlanForDevice(listOfPrice,
							commercialBundleMap, JOURNEY_TYPE_ACQUISITION);
					PriceForBundleAndHardware upgradeCompatiblePlan = deviceServiceCommonUtility
							.identifyLowestPriceOfPlanForDevice(listOfPrice, commercialBundleMap, JOURNEY_TYPE_UPGRADE);
					upgradeLeadPlanId = setUpgradeLeadPlanIdWhenListOfLeadPlanIdEmpty(upgradeCompatiblePlan);

					nonUpgradeLeadPlanId = setNonUpgradeLeadPlanIdWhenListOfLeadPlanIdEmpty(groupNamePriceMap,
							listOfPriceForBundleAndHardware, bundleHeaderForDevice, groupname);
				}
			}
			// ILS OfferCode
			getILSPriceMap(listOfOfferCodes, commercialBundleMap, listOfCimpatiblePlanMap, bundleHardwareTroupleMap,
					deviceId, nonUpgradeLeadPlanId);
			productGroupForDeviceListing = cacheDeviceDaoUtils
					.convertBundleHeaderForDeviceToProductGroupForDeviceListing(deviceId, nonUpgradeLeadPlanId,
							groupname, groupId, listOfPriceForBundleAndHardware, leadMemberMap,
							leadMemberMapForUpgrade, upgradeLeadPlanId, groupType);
			setListOfProductGroupRepository(deviceIds, listOfProductGroupRepository, productGroupForDeviceListing);
		} catch (Exception e) {
			listOfPriceForBundleAndHardware.clear();
			log.error(" Exception occured when call happen to compatible bundles api: " + e);
		} finally {
			listOfPriceForBundleAndHardware.clear();
		}
	}

	public  void setListOfProductGroupRepository(List<String> deviceIds,
			List<DevicePreCalculatedData> listOfProductGroupRepository,
			DevicePreCalculatedData productGroupForDeviceListing) {
		if (productGroupForDeviceListing != null) {
			deviceIds.add(productGroupForDeviceListing.getDeviceId());
			listOfProductGroupRepository.add(productGroupForDeviceListing);
		}
	}

	public  void getILSPriceMap(Set<String> listOfOfferCodes, Map<String, CommercialBundle> commercialBundleMap,
			Map<String, List<String>> listOfCimpatiblePlanMap,
			Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap, String deviceId,
			String nonUpgradeLeadPlanId) {
		if (nonUpgradeLeadPlanId != null && listOfCimpatiblePlanMap.containsKey(deviceId)) {
			deviceUtils.getIlsPriceMap(listOfOfferCodes, commercialBundleMap, listOfCimpatiblePlanMap,
					bundleHardwareTroupleMap, deviceId);
		}
	}

	private String setNonUpgradeLeadPlanIdWhenListOfLeadPlanIdEmpty(
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			PriceForBundleAndHardware bundleHeaderForDevice, String groupname) {
		String nonUpgradeLeadPlanIdLocal = null;
		if (bundleHeaderForDevice != null) {
			nonUpgradeLeadPlanIdLocal = deviceUtils.getGroupNamePriceMap(groupNamePriceMap,
					listOfPriceForBundleAndHardware, bundleHeaderForDevice, groupname);
		}
		return nonUpgradeLeadPlanIdLocal;
	}

	private String setUpgradeLeadPlanIdWhenListOfLeadPlanIdEmpty(PriceForBundleAndHardware upgradeCompatiblePlan) {
		String upgradeLeadPlanIdLocal = null;
		if (upgradeCompatiblePlan != null) {
			upgradeLeadPlanIdLocal = upgradeCompatiblePlan.getBundlePrice().getBundleId();
		}
		return upgradeLeadPlanIdLocal;
	}

	public  void getLeadPlanGroupPriceMap(Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String deviceId, String groupname,
			String nonUpgradeLeadPlanId) {
		if (StringUtils.isNotBlank(nonUpgradeLeadPlanId) && !leadPlanIdPriceMap.isEmpty()
				&& leadPlanIdPriceMap.containsKey(deviceId)) {
			deviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, groupNamePriceMap, listOfPriceForBundleAndHardware,
					deviceId, groupname);
		}
	}

	public  String getUpgradeLeadPlanIdForPaymDevice(Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, CommercialBundle> commercialBundleMap, String deviceId, String upgradeLeadPlanId) {
		String upgradeLeadPlanIdLocal = null;
		if (StringUtils.isBlank(upgradeLeadPlanId) && nonLeadPlanIdPriceMap.containsKey(deviceId)) {
			upgradeLeadPlanIdLocal = getUpgradeLeadPlanIdForCacheDevice(nonLeadPlanIdPriceMap, commercialBundleMap,
					deviceId);

		}
		return upgradeLeadPlanIdLocal;
	}

	public  String getNonUpgradeLeadPlanIdForPaymDevice(
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			Map<String, CommercialBundle> commercialBundleMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String deviceId, String groupname,
			String nonUpgradeLeadPlanId) {
		String nonUpgradeLeadPlanIdLocal = null;
		if (StringUtils.isBlank(nonUpgradeLeadPlanId) && nonLeadPlanIdPriceMap.containsKey(deviceId)) {
			nonUpgradeLeadPlanIdLocal = getNonUpgradeLeadPlanIdForPaymCacheDevice(nonLeadPlanIdPriceMap,
					groupNamePriceMap, commercialBundleMap, listOfPriceForBundleAndHardware, deviceId, groupname);
		}
		return nonUpgradeLeadPlanIdLocal;
	}

	private String getUpgradeLeadPlanId(String bundleId, CommercialBundle coommercialbundle) {
		String upgradeLeadPlanIdLocal = null;
		if (deviceServiceImplUtility.isSellable(JOURNEY_TYPE_UPGRADE, coommercialbundle)) {
			upgradeLeadPlanIdLocal = bundleId;
		}
		return upgradeLeadPlanIdLocal;
	}

	private String getNonUpgradeLeadPlanId(String bundleId, CommercialBundle coommercialbundle) {
		String nonUpgradeLeadPlanIdLocal = null;
		if (deviceServiceImplUtility.isSellable(JOURNEY_TYPE_ACQUISITION, coommercialbundle)) {
			nonUpgradeLeadPlanIdLocal = bundleId;
		}
		return nonUpgradeLeadPlanIdLocal;
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
		upgradeLeadPlanId = setUpgradeLeadPlanIdWhenListOfLeadPlanIdEmpty(upgradeCompatiblePlan);
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
		nonUpgradeLeadPlanId = setNonUpgradeLeadPlanIdWhenListOfLeadPlanIdEmpty(groupNamePriceMap,
				listOfPriceForBundleAndHardware, bundleHeaderForDevice, groupname);
		return nonUpgradeLeadPlanId;
	}

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param bundleAndHardwareTupleListForNonLeanPlanId
	 */
	public void getNonLeadPlanMapForPaymCachedevice(Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId, String groupType) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForNonLeadPlanIds;
		listOfPriceForBundleAndHardwareForNonLeadPlanIds = commonUtility.getPriceDetailsUsingBundleHarwareTrouple(
				bundleAndHardwareTupleListForNonLeanPlanId, null, null, groupType);
		if (listOfPriceForBundleAndHardwareForNonLeadPlanIds != null
				&& !listOfPriceForBundleAndHardwareForNonLeadPlanIds.isEmpty()) {
			deviceUtils.getNonLeadPlanMap(nonLeadPlanIdPriceMap, listOfPriceForBundleAndHardwareForNonLeadPlanIds);
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
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String groupType) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForLeadPlanIds;
		listOfPriceForBundleAndHardwareForLeadPlanIds = commonUtility
				.getPriceDetailsUsingBundleHarwareTrouple(bundleAndHardwareTupleList, null, null, groupType);
		if (listOfPriceForBundleAndHardwareForLeadPlanIds != null
				&& !listOfPriceForBundleAndHardwareForLeadPlanIds.isEmpty()) {
			getLeadPlanMap(leadPlanIdPriceMap, listOfPriceForBundleAndHardwareForLeadPlanIds);
		}
	}

	/**
	 * 
	 * @param leadPlanIdPriceMap
	 * @param listOfPriceForBundleAndHardwareForLeadPlanIds
	 */
	public void getLeadPlanMap(Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForLeadPlanIds) {
		listOfPriceForBundleAndHardwareForLeadPlanIds.forEach(priceForBundleAndHardware -> {

			if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null) {
				leadPlanIdPriceMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
						priceForBundleAndHardware);
			}
		});
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
		List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember = new ArrayList<>();
		String groupId = String.valueOf(productGroup.getGroupId());
		String groupname = productGroup.getName();
		if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
			deviceUtils.getMemberForCaceDevice(listOfDeviceId, groupIdAndNameMap, productGroup, listOfDeviceGroupMember,
					groupId, groupname);
		}
		String leadMemberId = null;
		String leadMemberIdForUpgrade = null;

		leadMemberId = deviceServiceCommonUtility.getMemeberBasedOnRulesImplementation(listOfDeviceGroupMember,
				JOURNEY_TYPE_ACQUISITION);
		leadMemberIdForUpgrade = deviceServiceCommonUtility
				.getMemeberBasedOnRulesImplementation(listOfDeviceGroupMember, JOURNEY_TYPE_UPGRADE);

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
		List<MerchandisingPromotionModel> listOfMerchandisingPromotions = deviceEs
				.getListOfMerchandisingPromotionModel(OFFERCODE_PAYM, JOURNEY_TYPE_SECONDLINE_UPGRADE);
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
		List<Group> listOfProductGroup = deviceEs.getProductGroupByType(groupType);
		List<String> listOfDeviceId = new ArrayList<>();
		Map<String, String> leadMemberMap = new HashMap<>();
		Map<String, String> groupIdAndNameMap = new HashMap<>();
		if (listOfProductGroup != null && !listOfProductGroup.isEmpty()) {
			for (Group productGroup : listOfProductGroup) {
				getLeadMembermapForCacheDevicePayg(listOfDeviceId, leadMemberMap, groupIdAndNameMap, productGroup);
			}
			Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap = Collections.synchronizedMap(new HashMap<>());
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap = Collections
					.synchronizedMap(new HashMap<>());
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			setListOfProductGroupRepo(groupType, deviceIds, minimumPriceMap, listOfProductGroupRepository,
					listOfDeviceId, leadMemberMap, groupIdAndNameMap, leadPlanIdPriceMap, groupNamePriceMap,
					bundleAndHardwareTupleList);
			Map<String, String> ratingsReviewMap = deviceServiceCommonUtility
					.getDeviceReviewRatingImplementation(deviceIds);
			listOfProductGroupRepository.forEach(deviceDataRating -> {
				setMinimumCost(minimumPriceMap, deviceDataRating);
				deviceUtils.updateDeviceratingForCacheDevice(ratingsReviewMap, deviceDataRating);
			});
		} else {
			log.error("Receieved Null Values for the given product group type");
		}
		return listOfProductGroupRepository;

	}

	private void setListOfProductGroupRepo(String groupType, List<String> deviceIds,
			Map<String, String> minimumPriceMap, List<DevicePreCalculatedData> listOfProductGroupRepository,
			List<String> listOfDeviceId, Map<String, String> leadMemberMap, Map<String, String> groupIdAndNameMap,
			Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		DevicePreCalculatedData productGroupForDeviceListing;
		if (!listOfDeviceId.isEmpty()) {
			List<CommercialProduct> listOfCommercialProduct = deviceEs.getListOfCommercialProduct(listOfDeviceId);
			setBundleAndHardwareTupleListForPayg(bundleAndHardwareTupleList, listOfCommercialProduct);
			getLeadPlanMapForPaymCacheDeviceForPayg(groupType, leadPlanIdPriceMap, bundleAndHardwareTupleList);
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
					getLeadPlanGroupPriceMapForPayg(leadPlanIdPriceMap, groupNamePriceMap,
							listOfPriceForBundleAndHardware, deviceId, groupname);
					productGroupForDeviceListing = cacheDeviceDaoUtils
							.convertBundleHeaderForDeviceToProductGroupForDeviceListing(deviceId, null, groupname,
									groupId, listOfPriceForBundleAndHardware, leadMemberMap, null, null,
									groupType);
					setListOfProductGroupRepository(deviceIds, listOfProductGroupRepository,
							productGroupForDeviceListing);
					listOfPriceForBundleAndHardware.clear();
				} catch (Exception e) {
					listOfPriceForBundleAndHardware.clear();
					log.error("Exception occured when call happen to compatible bundles api: " + e);
				}
			}
			getMinimumPriceMapForPayG(minimumPriceMap, groupNamePriceMap);
		}
	}

	private void setMinimumCost(Map<String, String> minimumPriceMap, DevicePreCalculatedData deviceDataRating) {
		if (minimumPriceMap.containsKey(deviceDataRating.getProductGroupName())) {
			deviceDataRating.setMinimumCost(minimumPriceMap.get(deviceDataRating.getProductGroupName()));
		}
	}

	private void getMinimumPriceMapForPayG(Map<String, String> minimumPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap) {
		if (!groupNamePriceMap.isEmpty()) {
			deviceUtils.getMinimumPriceMapForPayG(minimumPriceMap, groupNamePriceMap);
		}
	}

	private void getLeadPlanGroupPriceMapForPayg(Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String deviceId, String groupname) {
		if (!leadPlanIdPriceMap.isEmpty() && leadPlanIdPriceMap.containsKey(deviceId)) {
			deviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, groupNamePriceMap, listOfPriceForBundleAndHardware,
					deviceId, groupname);
		}
	}

	private void getLeadPlanMapForPaymCacheDeviceForPayg(String groupType,
			Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			getLeadPlanMapForPaymCacheDevice(leadPlanIdPriceMap, bundleAndHardwareTupleList, groupType);
		}
	}

	private void setBundleAndHardwareTupleListForPayg(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			List<CommercialProduct> listOfCommercialProduct) {
		if (listOfCommercialProduct != null && !listOfCommercialProduct.isEmpty()) {
			listOfCommercialProduct.forEach(commercialProduct -> {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(null);
				bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
				bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
			});
		}
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
		List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember = new ArrayList<>();
		String groupId = String.valueOf(productGroup.getGroupId());
		String groupname = productGroup.getName();
		if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
			deviceUtils.getMemberForCaceDevice(listOfDeviceId, groupIdAndNameMap, productGroup, listOfDeviceGroupMember,
					groupId, groupname);
		}
		String leadMemberId = null;
		leadMemberId = deviceServiceCommonUtility.getMemeberBasedOnRulesImplementation(listOfDeviceGroupMember,
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
			List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> preCalcDataList) {
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			Map<String, CacheProductGroupModel> productModelMap = Collections.synchronizedMap(new HashMap<>());
			for (com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject : preCalcDataList) {
				CacheProductModel productModel = new CacheProductModel();

				String productId = deviceListObject.getDeviceId();
				productModel.setId(productId);
				productModel.setRating(deviceListObject.getRating());
				productModel.setLeadPlanId(deviceListObject.getLeadPlanId());
				productModel.setProductGroupName(deviceListObject.getProductGroupName());
				setPaygProductGroupId(deviceListObject, productModel);
				productModel.setUpgradeLeadPlanId(deviceListObject.getUpgradeLeadPlanId());
				productModel.setNonUpgradeLeadPlanId(deviceListObject.getNonUpgradeLeadPlanId());

				setPaygOneOffPrice(deviceListObject, productModel);
				setPaygOneOffDiscountedPrice(deviceListObject, productModel);
				setFinancingOptions(deviceListObject, productModel);
				setBundleMonthlyPrice(deviceListObject, productModel);
				setBundleMonthlyDiscountedPrice(deviceListObject, productModel);
				List<String> merchandisngList = new ArrayList<>();
				setMerchandisingMedia(deviceListObject, productModel, merchandisngList);
				String productIdForUpdate = STRING_OPT + STRING_PRODUCT + "_" + deviceListObject.getDeviceId();
				deviceDao.getUpdateElasticSearch(productIdForUpdate, mapper.writeValueAsString(productModel));

				if (StringUtils.isNotBlank(deviceListObject.getPaymProductGroupId())
						|| StringUtils.isNotBlank(deviceListObject.getPaygProductGroupId())) {
					String productGroupId = setProductGroupId(deviceListObject);
					if (productModelMap.containsKey(productGroupId)) {
						setProductGroupmodelMapWhenContainsKey(productModelMap, deviceListObject, productGroupId);
					} else {
						setProductGroupModelMapWhenNoKey(productModelMap, deviceListObject, productGroupId);
					}
				}
				if (deviceListObject.getPriceInfo() != null) {
					List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> offerAppliedPrice = deviceListObject
							.getPriceInfo().getOfferAppliedPrices();
					setOfferAppliedPrice(deviceListObject, offerAppliedPrice);
				}
			}
			for (Map.Entry<String, CacheProductGroupModel> entry : productModelMap.entrySet()) {
				String productGroupId = STRING_OPT + "productGroup_" + entry.getKey();
				deviceDao.getUpdateElasticSearch(productGroupId, mapper.writeValueAsString(entry.getValue()));
			}

		} catch (Exception e) {
			log.error("::::::Exception From es ::::::" + e);
		}
	}

	private void setOfferAppliedPrice(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> offerAppliedPrice)
			throws JsonProcessingException {
		if (offerAppliedPrice != null && CollectionUtils.isNotEmpty(offerAppliedPrice)) {
			for (com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails offerAppliedPriceObject : offerAppliedPrice) {

				CacheOfferAppliedPriceModel offerPrice = new CacheOfferAppliedPriceModel();
				if (offerAppliedPriceObject.getBundlePrice() != null && offerAppliedPriceObject.getDeviceId() != null
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
					com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice monthlyPrice = offerAppliedPriceObject
							.getBundlePrice().getMonthlyPrice();
					com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = offerAppliedPriceObject
							.getBundlePrice().getMonthlyDiscountPrice();
					setOfferMonthlyPrice(offerPrice, monthlyPrice);

					setOfferMonthlyDiscountPrice(offerPrice, monthlyDiscountPrice);

					com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice hardwarePrice = offerAppliedPriceObject
							.getHardwarePrice();
					if (hardwarePrice != null) {
						offerPrice.setHardwareId(hardwarePrice.getHardwareId());
						com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice oneOffPrice = hardwarePrice
								.getOneOffPrice();
						com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice = hardwarePrice
								.getOneOffDiscountPrice();
						setOfferOneOffPrice(offerPrice, oneOffPrice);

						setOfferOneOffDiscountPrice(offerPrice, oneOffDiscountPrice);
						List<DeviceFinancingOption> deviceFinancingOption = deviceListObject.getPriceInfo()
								.getHardwarePrice().getFinancingOptions();
						setFinanceOptions(offerPrice, deviceFinancingOption);
					}
					deviceDao.getIndexElasticSearch(id, mapper.writeValueAsString(offerPrice));
				}
			}
		}
	}

	private void setFinanceOptions(CacheOfferAppliedPriceModel offerPrice,
			List<DeviceFinancingOption> deviceFinancingOption) {
		List<com.vf.uk.dal.device.model.product.DeviceFinancingOption> financeOptions = null;
		if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
			financeOptions = new ArrayList<>();
			for (DeviceFinancingOption financsOption : deviceFinancingOption) {
				com.vf.uk.dal.device.model.product.DeviceFinancingOption finance = new com.vf.uk.dal.device.model.product.DeviceFinancingOption();
				finance.setApr(financsOption.getApr());
				finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
				finance.setFinanceProvider(financsOption.getFinanceProvider());
				finance.setFinanceTerm(financsOption.getFinanceTerm());
				Price monthly = financsOption.getMonthlyPrice();
				com.vf.uk.dal.device.model.product.Price deviceMonthlyPrice = new com.vf.uk.dal.device.model.product.Price();
				deviceMonthlyPrice.setGross(monthly.getGross());
				deviceMonthlyPrice.setNet(monthly.getNet());
				deviceMonthlyPrice.setVat(monthly.getVat());
				finance.setMonthlyPrice(deviceMonthlyPrice);
				Price totalInterest = financsOption.getTotalPriceWithInterest();
				com.vf.uk.dal.device.model.product.Price totalPriceWithInterest = new com.vf.uk.dal.device.model.product.Price();
				totalPriceWithInterest.setGross(totalInterest.getGross());
				totalPriceWithInterest.setNet(totalInterest.getNet());
				totalPriceWithInterest.setVat(totalInterest.getVat());
				finance.setTotalPriceWithInterest(totalPriceWithInterest);
				financeOptions.add(finance);
			}
		}
		offerPrice.setFinancingOptions(financeOptions);
	}

	private void setOfferOneOffDiscountPrice(CacheOfferAppliedPriceModel offerPrice,
			com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice) {
		if (oneOffDiscountPrice != null) {
			offerPrice.setOneOffDiscountedGrossPrice(oneOffDiscountPrice.getGross());
			offerPrice.setOneOffDiscountedNetPrice(oneOffDiscountPrice.getNet());
			offerPrice.setOneOffDiscountedVatPrice(oneOffDiscountPrice.getVat());

		}
	}

	private void setOfferOneOffPrice(CacheOfferAppliedPriceModel offerPrice,
			com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice oneOffPrice) {
		if (oneOffPrice != null) {
			offerPrice.setOneOffGrossPrice(oneOffPrice.getGross());
			offerPrice.setOneOffNetPrice(oneOffPrice.getNet());
			offerPrice.setOneOffVatPrice(oneOffPrice.getVat());
		}
	}

	private void setOfferMonthlyDiscountPrice(CacheOfferAppliedPriceModel offerPrice,
			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice) {
		if (monthlyDiscountPrice != null) {
			offerPrice.setMonthlyDiscountedGrossPrice(monthlyDiscountPrice.getGross());
			offerPrice.setMonthlyDiscountedNetPrice(monthlyDiscountPrice.getNet());
			offerPrice.setMonthlyDiscountedVatPrice(monthlyDiscountPrice.getVat());
		}
	}

	private void setOfferMonthlyPrice(CacheOfferAppliedPriceModel offerPrice,
			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice monthlyPrice) {
		if (monthlyPrice != null) {
			offerPrice.setMonthlyGrossPrice(monthlyPrice.getGross());
			offerPrice.setMonthlyNetPrice(monthlyPrice.getNet());
			offerPrice.setMonthlyVatPrice(monthlyPrice.getVat());
		}
	}

	private void setProductGroupModelMapWhenNoKey(Map<String, CacheProductGroupModel> productModelMap,
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			String productGroupId) {
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

	private void setProductGroupmodelMapWhenContainsKey(Map<String, CacheProductGroupModel> productModelMap,
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			String productGroupId) {
		CacheProductGroupModel productgroupModel = productModelMap.get(productGroupId);
		if (StringUtils.isNotBlank(deviceListObject.getLeadPlanId())) {
			productgroupModel.setLeadPlanId(deviceListObject.getLeadPlanId());
		}
		if (deviceListObject.getMinimumCost() != null) {
			if (productgroupModel.getMinimumCost() != null
					&& deviceListObject.getMinimumCost() < productgroupModel.getMinimumCost()) {
				productgroupModel.setMinimumCost(deviceListObject.getMinimumCost());
			}
		} else if (deviceListObject.getMinimumCost() != null && productgroupModel.getMinimumCost() == null) {
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
		if (StringUtils.isNotBlank(deviceListObject.getUpgradeLeadDeviceId())
				&& StringUtils.isNotBlank(deviceListObject.getUpgradeLeadPlanId())) {
			productgroupModel.setUpgradeLeadPlanId(deviceListObject.getUpgradeLeadPlanId());
		}
		if (StringUtils.isNotBlank(deviceListObject.getNonUpgradeLeadDeviceId())
				&& StringUtils.isNotBlank(deviceListObject.getNonUpgradeLeadPlanId())) {
			productgroupModel.setNonUpgradeLeadPlanId(deviceListObject.getNonUpgradeLeadPlanId());
		}
		productModelMap.put(productGroupId, productgroupModel);
	}

	private String setProductGroupId(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject) {
		String productGroupId = null;
		if (deviceListObject.getPaymProductGroupId() != null
				&& StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYM)) {
			productGroupId = deviceListObject.getPaymProductGroupId();
		} else if (deviceListObject.getPaygProductGroupId() != null
				&& StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYG)) {
			productGroupId = deviceListObject.getPaygProductGroupId();
		}
		return productGroupId;
	}

	private void setMerchandisingMedia(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel, List<String> merchandisngList) {
		if (deviceListObject.getMedia() != null && CollectionUtils.isNotEmpty(deviceListObject.getMedia())) {
			for (com.vf.uk.dal.device.model.merchandisingpromotion.Media mediaObject : deviceListObject.getMedia()) {
				String media = mediaObject.getId() + "|" + mediaObject.getValue() + "|" + mediaObject.getType() + "|"
						+ mediaObject.getPromoCategory() + "|" + mediaObject.getOfferCode() + "|"
						+ mediaObject.getDescription() + "|" + mediaObject.getDiscountId();
				merchandisngList.add(media);
			}
			productModel.setMerchandisingMedia(merchandisngList);
		}
	}

	private void setBundleMonthlyDiscountedPrice(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel) {
		if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getBundlePrice() != null
				&& deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null) {
			productModel.setBundleMonthlyDiscPriceGross(
					deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross());
			productModel.setBundleMonthlyDiscPriceNet(
					deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getNet());
			productModel.setBundleMonthlyDiscPriceVat(
					deviceListObject.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getVat());
		}
	}

	private void setBundleMonthlyPrice(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel) {
		if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getBundlePrice() != null
				&& deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice() != null) {
			productModel.setBundleMonthlyPriceGross(
					deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross());
			productModel.setBundleMonthlyPriceNet(
					deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice().getNet());
			productModel.setBundleMonthlyPriceVat(
					deviceListObject.getPriceInfo().getBundlePrice().getMonthlyPrice().getVat());
		}
	}

	private void setFinancingOptions(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel) {
		if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getHardwarePrice() != null) {
			List<DeviceFinancingOption> deviceFinancingOption = deviceListObject.getPriceInfo().getHardwarePrice()
					.getFinancingOptions();
			List<com.vf.uk.dal.device.model.product.DeviceFinancingOption> financeOptions = null;

			if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
				financeOptions = new ArrayList<>();
				for (DeviceFinancingOption financsOption : deviceFinancingOption) {
					com.vf.uk.dal.device.model.product.DeviceFinancingOption finance = new com.vf.uk.dal.device.model.product.DeviceFinancingOption();
					finance.setApr(financsOption.getApr());
					finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
					finance.setFinanceProvider(financsOption.getFinanceProvider());
					finance.setFinanceTerm(financsOption.getFinanceTerm());
					Price monthly = financsOption.getMonthlyPrice();
					com.vf.uk.dal.device.model.product.Price deviceMonthlyPrice = new com.vf.uk.dal.device.model.product.Price();
					deviceMonthlyPrice.setGross(monthly.getGross());
					deviceMonthlyPrice.setNet(monthly.getNet());
					deviceMonthlyPrice.setVat(monthly.getVat());
					finance.setMonthlyPrice(deviceMonthlyPrice);
					Price totalInterest = financsOption.getTotalPriceWithInterest();
					com.vf.uk.dal.device.model.product.Price totalPriceWithInterest = new com.vf.uk.dal.device.model.product.Price();
					totalPriceWithInterest.setGross(totalInterest.getGross());
					totalPriceWithInterest.setNet(totalInterest.getNet());
					totalPriceWithInterest.setVat(totalInterest.getVat());
					finance.setTotalPriceWithInterest(totalPriceWithInterest);
					financeOptions.add(finance);
				}
			}
			productModel.setFinancingOptions(financeOptions);
		}
	}

	private void setPaygOneOffDiscountedPrice(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel) {
		if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getHardwarePrice() != null
				&& deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null) {
			if (StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYM)) {
				productModel.setOneOffDiscountedGrossPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross());
				productModel.setOneOffDiscountedNetPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getNet());
				productModel.setOneOffDiscountedVatPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getVat());
			} else if (StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYG)) {
				productModel.setPaygOneOffDiscountedGrossPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross());
				productModel.setPaygOneOffDiscountedNetPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getNet());
				productModel.setPaygOneOffDiscountedVatPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getVat());
			}
		}
	}

	private void setPaygOneOffPrice(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel) {
		if (deviceListObject.getPriceInfo() != null && deviceListObject.getPriceInfo().getHardwarePrice() != null
				&& deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice() != null) {
			if (StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYM)) {
				productModel.setOneOffGrossPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross());
				productModel.setOneOffNetPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getNet());
				productModel.setOneOffVatPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getVat());
			} else if (StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYG)) {
				productModel.setPaygOneOffGrossPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross());
				productModel.setPaygOneOffNetPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getNet());
				productModel.setPaygOneOffVatPrice(
						deviceListObject.getPriceInfo().getHardwarePrice().getOneOffPrice().getVat());
			}
		}
	}

	private void setPaygProductGroupId(
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject,
			CacheProductModel productModel) {
		if (StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYM)) {
			productModel.setPaymProductGroupId(deviceListObject.getPaymProductGroupId());
		} else if (StringUtils.equalsIgnoreCase(deviceListObject.getGroupType(), STRING_DEVICE_PAYG)) {
			productModel.setPaygProductGroupId(deviceListObject.getPaygProductGroupId());
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
				log.error(jobId + "==>No Device Pre Calculated Data found To Store");
				exceptionFlag = true;
				throw new DeviceCustomException(ERROR_CODE_SELECT_CAHCE_DEVICE,ExceptionMessages.NO_DEVICE_PRE_CALCULATED_DATA,"404");
			}
			saveDeviceMediaData(i, devicePreCalculatedData);
			List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> deviceListObjectList = cacheDeviceDaoUtils
					.convertDevicePreCalDataToSolrData(devicePreCalculatedData);
			indexPrecalData(deviceListObjectList);
		} catch (Exception e) {
			exceptionFlag = true;
			log.error(jobId + "==>" + e);
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

	private void saveDeviceMediaData(int i, List<DevicePreCalculatedData> devicePreCalculatedData) {
		if (i > 0) {
			devicePreCalculatedData.forEach(deviceData -> {
				if (deviceData.getMedia() != null && !deviceData.getMedia().isEmpty()) {
					deviceTileCacheDAO.saveDeviceMediaData(deviceData.getMedia(), deviceData.getDeviceId());
				}
			});
		}
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
		String deviceIdMdfd = commonUtility.appendPrefixString(deviceId);
		log.info("::::: deviceIdMdfd :: " + deviceIdMdfd + ":::::");
		String response = deviceDao.getDeviceReviewDetails(deviceIdMdfd);
		if (StringUtils.isNotBlank(response)) {
			jsonObject = commonUtility.getJSONFromString(response);
		} else {
			log.error("No reviews found");
			throw new DeviceCustomException(ERROR_CODE_SELECT_REVIEW,ExceptionMessages.NO_REVIEWS_FOUND,"404");
		}
		return jsonObject;
	}
}
