package com.vf.uk.dal.device.svc;


import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;

/**
 Device Service Should consists of all the service methods which will be invoked
 from the Controller
 **/

public interface DeviceRecommendationService {
	/**
	 * 
	 * @param msisdn
	 * @param deviceId
	 * @return
	 */
	public RecommendedProductListRequest getRecommendedDeviceListRequest(String msisdn, String deviceId);
	/**
	 * 
	 * @param msisdn
	 * @param deviceId
	 * @param facetedDevice
	 * @return
	 */
	public FacetedDevice getRecommendedDeviceList(String msisdn, String deviceId, FacetedDevice facetedDevice);
	/**
	 * 
	 * @param objectsToOrder
	 * @param orderedObjects
	 * @return
	 */
	public FacetedDevice sortList(FacetedDevice objectsToOrder, RecommendedProductListResponse orderedObjects);
}