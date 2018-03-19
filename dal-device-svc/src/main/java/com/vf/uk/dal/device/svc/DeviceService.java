package com.vf.uk.dal.device.svc;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;

import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Member;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;

/**
 Device Service Should consists of all the service methods which will be invoked
 from the Controller
 **/

public interface DeviceService {
	
	/**
	 * 
	 * @param make
	 * @param model
	 * @param groupType
	 * @param deviceId
	 * @param creditLimit
	 * @param journeyType
	 * @param offerCode
	 * @param bundleId
	 * @return
	 */
	public List<DeviceTile> getListOfDeviceTile(String make,String model,String groupType, String deviceId, Double creditLimit, String journeyType, String offerCode, String bundleId);
	/**
	 * 
	 * @param id
	 * @param offerCode
	 * @param journeyType
	 * @return
	 */
	public List<DeviceTile> getDeviceTileById(String id, String offerCode,String journeyType);
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public DeviceDetails getDeviceDetails(String deviceId,String journeyType,String offerCode);
	/**
	 * 
	 * @param groupType
	 * @param groupName
	 * @return
	 */
	//public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType,String groupName);
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId,String journeyType,String offerCode);
	/**
	 * 
	 * @param productClass
	 * @param listOfMake
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
	 * @param creditLimit
	 * @param offerCode
	 * @param msisdn
	 * @param includeRecommendations
	 * @return
	 */
	public FacetedDevice getDeviceList(String productClass,String listOfMake,String model,String groupType,String sortCriteria,
			int pageNumber,int pageSize,String capacity,String colour,String operatingSystem,String mustHaveFeatures,String journeyType,
			Float creditLimit,String offerCode, String msisdn, boolean includeRecommendations);
	/**
	 * 
	 * @param groupType
	 * @param jobId
	 * @return
	 */
	//@Transactional(rollbackFor = {Exception.class})
	public CompletableFuture<Integer> cacheDeviceTile(String groupType,String jobId) ;
	/**
	 * 
	 * @param deviceId
	 * @param bundleId
	 * @param allowedRecurringPriceLimit
	 * @param plansLimit
	 * @return
	 */
	public BundleDetails getBundlesOfDeviceId(String deviceId, String bundleId, String allowedRecurringPriceLimit, String plansLimit);
	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public JSONObject getDeviceReviewDetails(String deviceId);
	/**
	 * 
	 * @param deviceId
	 * @param offerCode
	 * @param journeyType
	 * @return
	 */
	public List<DeviceDetails> getListOfDeviceDetails(String deviceId,String offerCode,String journeyType);
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
	 * @param groupType
	 * @return
	 */
	List<DevicePreCalculatedData> getDeviceListFromPricing(String groupType);
	/**
	 * 
	 * @param variantsList
	 * @return
	 */
	List<Member> getListOfMembers(List<String> variantsList);
	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @param journeyType
	 * @return
	 */
	String getMemeberBasedOnRules1(List<Member> listOfDeviceGroupMember,String journeyType);
	/**
	 * 
	 * @param memberId
	 * @param journeyType
	 * @return
	 */
	Boolean validateMemeber1(String memberId,String journeyType);
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
	 * @param journeyType
	 * @return
	 */
	FacetedDevice getDeviceListForConditionalAccept(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures, Float creditLimit,String journeyType);
	/**
	 * 
	 * @param filter
	 * @param parameter
	 * @return
	 */
	String getFilterForDeviceList(String filter, String parameter);
	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public String getLeadPlanIdForDeviceId(String deviceId ,String journeyType);
	/**
	 * 
	 * @param memberId
	 * @param journeyType
	 * @return
	 */
	public Boolean validateMemeber(String memberId,String journeyType);
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @return
	 */
	Insurances getInsuranceByDeviceId(String deviceId,String journeyType);
	/**
	 * 
	 * @param deviceSummary
	 * @param discountType
	 * @return
	 */
	Double getBundlePriceBasedOnDiscountDuration_Implementation(DeviceSummary deviceSummary, String discountType);

	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public List<DevicePreCalculatedData> getDeviceListFromPricingForPayG(String groupType);
	
	/**
	 * 
	 * @param productId
	 * @param productName
	 * @return List of CommercialProducts
	 */
	public List<CommercialProduct> getCommercialProductDetails(String productIdOrName);
	
	/**
	 * @param productId
	 * @param productName
	 * @return List of CommercialProducts
	 */
	public List<Group> getProductGroupByType(String groupType);
	
	/**
	 * 
	 * @param preCalcDataList
	 */
	public void indexPrecalData(List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> preCalcDataList);
}
