package com.vf.uk.dal.device.svc;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Member;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;

/**
 Device Service Should consists of all the service methods which will be invoked
 from the Controller
 **/

public interface DeviceService {
	
	public List<DeviceTile> getListOfDeviceTile(String make,String model,String groupType, String deviceId, Double creditLimit, String journeyType, String offerCode, String bundleId);
	public List<DeviceTile> getDeviceTileById(String id, String offerCode,String journeyType);
	public DeviceDetails getDeviceDetails(String deviceId,String journeyType,String offerCode);
	public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType,String groupName);
	public List<Accessory> getAccessoriesOfDevice(String deviceId,String journeyType);
	public FacetedDevice getDeviceList(String productClass,String listOfMake,String model,String groupType,String sortCriteria,
			int pageNumber,int pageSize,String capacity,String colour,String operatingSystem,String mustHaveFeatures,String journeyType,
			Float creditLimit,String offerCode, String msisdn, boolean includeRecommendations);
	//public Insurances getInsuranceById(String deviceId);
	@Transactional(rollbackFor = {Exception.class})
	public CompletableFuture<Integer> cacheDeviceTile(String groupType,String jobId) ;
	//public List<StockInfo> getStockAvailability(String groupType);
	public BundleDetails getBundlesOfDeviceId(String deviceId, String bundleId, String allowedRecurringPriceLimit, String plansLimit);
	public JSONObject getDeviceReviewDetails(String deviceId);
	public List<DeviceDetails> getListOfDeviceDetails(String deviceId,String offerCode,String journeyType);
	public CacheDeviceTileResponse insertCacheDeviceToDb();
	void updateCacheDeviceToDb(String jobId, String jobStatus);
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId);
	List<DevicePreCalculatedData> getDeviceListFromPricing(String groupType);
	List<Member> getListOfMembers(List<String> variantsList);
	String getMemeberBasedOnRules1(List<Member> listOfDeviceGroupMember);
	Boolean validateMemeber1(String memberId);
	FacetedDevice getDeviceListForConditionalAccept(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, Float creditLimit);
	String getFilterForDeviceList(String filter, String parameter);
	public String getLeadPlanIdForDeviceId(String deviceId);
	public Boolean validateMemeber(String memberId);
	Insurances getInsuranceByDeviceId(String deviceId,String journeyType);
}