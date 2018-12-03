package com.vf.uk.dal.device.service;

import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListRequest;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.model.FacetedDevice;

/**
 * Device Service Should consists of all the service methods which will be
 * invoked from the Controller
 **/

public interface DeviceRecommendationService {
	/**
	 * 
	 * @param msisdn
	 * @param deviceId
	 * @return RecommendedProductListRequest
	 */
	public RecommendedProductListRequest getRecommendedDeviceListRequest(String msisdn, String deviceId);

	/**
	 * 
	 * @param msisdn
	 * @param deviceId
	 * @param facetedDevice
	 * @return FacetedDevice
	 */
	public FacetedDevice getRecommendedDeviceList(String msisdn, String deviceId, FacetedDevice facetedDevice);

	/**
	 * 
	 * @param objectsToOrder
	 * @param orderedObjects
	 * @return FacetedDevice
	 */
	public FacetedDevice sortList(FacetedDevice objectsToOrder, RecommendedProductListResponse orderedObjects);
}
