package com.vf.uk.dal.device.svc;


import java.util.List;
import java.util.Map;

import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.utility.entity.BundleDetails;

/**
 Device Service Should consists of all the service methods which will be invoked
 from the Controller
 **/

public interface DeviceService {
	
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
	 * @param groupType
	 * @param groupName
	 * @return
	 */
	//public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType,String groupName);
	
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
	 * @param deviceId
	 * @param bundleId
	 * @param allowedRecurringPriceLimit
	 * @param plansLimit
	 * @return
	 */
	public BundleDetails getBundlesOfDeviceId(String deviceId, String bundleId, String allowedRecurringPriceLimit, String plansLimit);
	/**
	 * 
	 * @param journeyType
	 * @param listOfProductVariants
	 * @param groupNameWithProdId
	 * @param productGroupModel
	 * @param listOfProductsNew
	 */
	public void getGroupNameWithListOfProduct(String journeyType, List<String> listOfProductVariants,
			Map<String, String> groupNameWithProdId, ProductGroupModel productGroupModel,
			List<String> listOfProductsNew);
	/**
	 * 
	 * @param msisdn
	 * @param facetedDevice
	 * @return
	 */
	public FacetedDevice getConditionalForDeviceList(String msisdn, FacetedDevice facetedDevice);
	
	/**
	 * 
	 * @param creditLimit
	 * @param bundleModelMap
	 * @param bundleModelAndPriceMap
	 * @param listOfProductModel
	 * @param listOfProducts
	 * @param listOfProductVariants
	 */
	public void getConditionalCriteriaForDeviceList(Float creditLimit, Map<String, BundleModel> bundleModelMap,
			Map<String, BundlePrice> bundleModelAndPriceMap, List<ProductModel> listOfProductModel,
			List<String> listOfProducts, List<String> listOfProductVariants);
}
