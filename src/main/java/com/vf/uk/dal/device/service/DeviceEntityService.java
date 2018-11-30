package com.vf.uk.dal.device.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.model.productgroups.ProductGroupModelMap;

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
}
