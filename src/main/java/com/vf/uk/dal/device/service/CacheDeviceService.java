package com.vf.uk.dal.device.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;

import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.solr.DevicePreCalculatedData;
/**
 * 
 * @author sahil.monga
 *
 */
public interface CacheDeviceService {

	/**
	 * 
	 * @param groupType
	 * @param jobId
	 * @param version
	 * @return
	 */
	public CompletableFuture<Integer> cacheDeviceTile(String groupType, String jobId, String version);

	/**
	 * 
	 * @param deviceId
	 * @return DeviceReviewDetails
	 */
	public JSONObject getDeviceReviewDetails(String deviceId);

	/**
	 * 
	 * @return
	 */
	public CacheDeviceTileResponse insertCacheDeviceToDb();

	/**
	 * 
	 * @param jobId
	 * @param jobStatus
	 */
	void updateCacheDeviceToDb(String jobId, String jobStatus);

	/**
	 * 
	 * @param jobId
	 * @return CacheDeviceTileResponse
	 */
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId);

	/**
	 * 
	 * @param listOfOfferCodesForUpgrade
	 * @param listOfSecondLineOfferCode
	 * @param bundleHardwareTroupleMap
	 * @param ilsPriceForJourneyAwareOfferCodeMap
	 */
	public void getIlsPriceWithOfferCodeAndJourney(List<String> listOfOfferCodesForUpgrade,
			List<String> listOfSecondLineOfferCode, Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap, String groupType);

	/**
	 * 
	 * @param preCalcDataList
	 */
	public void indexPrecalData(
			List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> preCalcDataList);

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param groupNamePriceMap
	 * @param commercialBundleMap
	 * @param listOfPriceForBundleAndHardware
	 * @param deviceId
	 * @param groupname
	 * @return NonUpgradeLeadPlanIdForPaymCacheDevice
	 */
	public String getNonUpgradeLeadPlanIdForPaymCacheDevice(
			Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			Map<String, CommercialBundle> commercialBundleMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String deviceId, String groupname);

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param commercialBundleMap
	 * @param deviceId
	 * @return UpgradeLeadPlanIdForCacheDevice
	 */
	public String getUpgradeLeadPlanIdForCacheDevice(Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			Map<String, CommercialBundle> commercialBundleMap, String deviceId);

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
			Map<String, List<PriceForBundleAndHardware>> iLSPriceMap, String deviceId);
}
