package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModelMap;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.DeviceEntityService;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceEntityServiceImpl implements DeviceEntityService {

	public static final String COMPATIBLE_DELIVERY = "Compatible Delivery";
	@Autowired
	DeviceESHelper deviceEs;
	
	DeviceUtils deviceUtils = new DeviceUtils();

	/**
	 * Method to prepare the list from the request construct and build the query
	 * and finally return the List of Commercial Products.
	 * 
	 * @param productIdOrName
	 * @return List of CommercialProducts
	 */
	@Override
	public List<CommercialProduct> getCommercialProductDetails(String productIdOrName) {
		List<String> listOfProdIdsOrNames = DeviceServiceImplUtility.getListOfProductIdsOrNames(productIdOrName);
		List<CommercialProduct> listOfCommercialProduct = deviceEs.getListOfCommercialProduct(listOfProdIdsOrNames);
		if (CollectionUtils.isEmpty(listOfCommercialProduct)) {
			log.error( ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + " : " + productIdOrName);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}
		return listOfCommercialProduct;
	}

	/**
	 * 
	 * @param deviceIds
	 * @return ProductGroupModelMap
	 */
	@Override
	public ProductGroupModelMap getMapOfProductModelForGetDeliveryMethod(String deviceIds) {
		ProductGroupModelMap ProductGroupModelMap = null;
		Map<String, List<ProductGroupModel>> result = new HashMap<>();
		List<String> deviceId = Arrays.asList(deviceIds.split(","));
		List<ProductModel> productModels = deviceEs.getListOfProductModel(deviceId);
		Set<String> displayName = new HashSet<>();
		Map<String, List<String>> productGroupmap = new HashMap<>();
		getProductGroupMap(productModels, displayName, productGroupmap);
		List<ProductGroupModel> productGroupModels = deviceEs.getListOfProductGroupModel(displayName,
				COMPATIBLE_DELIVERY);
		getProductGroupModelBasedOnDelivery(result, productGroupmap, productGroupModels);
		if (result.isEmpty()) {
			log.error( ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + " : " + deviceIds);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		} else {
			ProductGroupModelMap = new ProductGroupModelMap();
			ProductGroupModelMap.setProductgroupMap(result);
		}

		return ProductGroupModelMap;
	}

	/**
	 * @param groupType
	 * @return listOfGroup
	 */
	@Override
	public List<Group> getProductGroupByType(String groupType) {
		List<Group> listOfGroup = deviceEs.getProductGroupByType(groupType);
		if (CollectionUtils.isEmpty(listOfGroup)) {
			log.error( ExceptionMessages.NULL_VALUE_GROUP_TYPE + " : " + groupType);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		}
		return listOfGroup;
	}

	/**
	 * 
	 * @param result
	 * @param productGroupmap
	 * @param productGroupModels
	 */
	@Override
	public void getProductGroupModelBasedOnDelivery(Map<String, List<ProductGroupModel>> result,
			Map<String, List<String>> productGroupmap, List<ProductGroupModel> productGroupModels) {
		productGroupModels.forEach(groupModel -> {
			String id = groupModel.getName() + "@" + groupModel.getType().trim();
			List<String> productIds = productGroupmap.containsKey(id) ? productGroupmap.get(id) : null;
			productIds.forEach(deviceid -> {
				if (result.containsKey(deviceid)) {
					List<ProductGroupModel> groupModels = result.get(deviceid);
					groupModels.add(groupModel);
					result.put(deviceid, groupModels);
				} else {
					List<ProductGroupModel> groupModels = new ArrayList<>();
					groupModels.add(groupModel);
					result.put(deviceid, groupModels);
				}
			});
		});
	}

	/**
	 * 
	 * @param productModels
	 * @param displayName
	 * @param productGroupmap
	 */
	@Override
	public void getProductGroupMap(List<ProductModel> productModels, Set<String> displayName,
			Map<String, List<String>> productGroupmap) {
		productModels.forEach(model -> {
			List<String> productGroups = (model.getProductGroups() == null || model.getProductGroups().isEmpty())
					? Collections.emptyList() : model.getProductGroups();
			productGroups.forEach(group -> {
				String[] arr = group.split("@");
				String groupName = arr[0];
				String groupRole = arr[1];
				if (StringUtils.isNotBlank(groupRole) && COMPATIBLE_DELIVERY.equalsIgnoreCase(groupRole)) {
					displayName.add(groupName);
					if (productGroupmap.containsKey(group)) {
						List<String> products = productGroupmap.get(group);
						products.add(model.getProductId());
						productGroupmap.put(group, products);
					} else {
						List<String> products = new ArrayList<>();
						products.add(model.getProductId());
						productGroupmap.put(group.trim(), products);
					}
				}
			});
		});
	}
}
