package com.vf.uk.dal.device.svc;


import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;

/**
 Device Service Should consists of all the service methods which will be invoked
 from the Controller
 **/

public interface DeviceRecommendationService {
	
	public RecommendedProductListRequest getRecommendedDeviceListRequest(String msisdn, String deviceId);
	
	public FacetedDevice getRecommendedDeviceList(String msisdn, String deviceId, FacetedDevice facetedDevice);
	
	public FacetedDevice sortList(FacetedDevice objectsToOrder, RecommendedProductListResponse orderedObjects);
}