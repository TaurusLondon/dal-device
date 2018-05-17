package com.vf.uk.dal.device.svc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;

import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;

public interface CacheDeviceService {

	/**
	 * 
	 * @param groupType
	 * @param jobId
	 * @return
	 */
	//@Transactional(rollbackFor = {Exception.class})
	public CompletableFuture<Integer> cacheDeviceTile(String groupType,String jobId , String version) ;
	
	/**
	 * 
	 * @param deviceId
	 * @return
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
	 * @return
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
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap);
	/**
	 * 
	 * @param preCalcDataList
	 */
	public void indexPrecalData(
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> preCalcDataList);
	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param groupNamePriceMap
	 * @param commercialBundleMap
	 * @param listOfPriceForBundleAndHardware
	 * @param deviceId
	 * @param groupname
	 * @return
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
	 * @return
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
