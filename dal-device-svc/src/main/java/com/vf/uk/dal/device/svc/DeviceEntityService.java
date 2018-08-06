package com.vf.uk.dal.device.svc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vf.uk.dal.device.datamodel.handsetonlinemodel.HandsetOnlineModelList;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModelMap;

public interface DeviceEntityService {

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
	 * @param deviceIds
	 * @return ProductGroupModelMap
	 */
	public ProductGroupModelMap getMapOfProductModelForGetDeliveryMethod(String deviceIds);

	/**
	 * 
	 * @param result
	 * @param productGroupmap
	 * @param productGroupModels
	 */
	public void getProductGroupModelBasedOnDelivery(Map<String, List<ProductGroupModel>> result,
			Map<String, List<String>> productGroupmap, List<ProductGroupModel> productGroupModels);

	/**
	 * 
	 * @param productModels
	 * @param displayName
	 * @param productGroupmap
	 */
	public void getProductGroupMap(List<ProductModel> productModels, Set<String> displayName,
			Map<String, List<String>> productGroupmap);
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param make
	 * @param model
	 * @param groupType
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @param color
	 * @param operatingSystem
	 * @param capacity
	 * @param mustHaveFeatures
	 * @return
	 */
	public HandsetOnlineModelList getHandsetOnlineModelDetails(String deviceId, String journeyType,
			String make, String model, String groupType, String sort,
			Integer pageNumber, Integer pageSize, String color, String operatingSystem, String capacity,
			String mustHaveFeatures);

		
}
