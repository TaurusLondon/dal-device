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
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.datamodel.handsetonlinemodel.HandsetOnlineModel;
import com.vf.uk.dal.device.datamodel.handsetonlinemodel.HandsetOnlineModelList;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModelMap;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.DeviceEntityService;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;

@Component
public class DeviceEntityServiceImpl implements DeviceEntityService {

	@Autowired
	DeviceESHelper deviceEs;
	
	@Autowired
	RegistryClient registryclnt;
	
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
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + " : " + productIdOrName);
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
				Constants.COMPATIBLE_DELIVERY);
		getProductGroupModelBasedOnDelivery(result, productGroupmap, productGroupModels);
		if (result.isEmpty()) {
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID + " : " + deviceIds);
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
			LogHelper.error(this, ExceptionMessages.NULL_VALUE_GROUP_TYPE + " : " + groupType);
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
				if (StringUtils.isNotBlank(groupRole) && Constants.COMPATIBLE_DELIVERY.equalsIgnoreCase(groupRole)) {
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
	/**
	 * @queryParam
	 * @return 
	 */
	@Override
	public HandsetOnlineModelList getHandsetOnlineModelDetails(Map<String,String> queryParam) {
		
		try{
		HandsetOnlineModelList handsetOnlineModelList = new HandsetOnlineModelList();
		String journeyTypeLocal = queryParam.get(Constants.JOURNEY_TYPE);
		String journeyType = StringUtils.isBlank(journeyTypeLocal) ? Constants.JOURNEY_TYPE_ACQUISITION : journeyTypeLocal;
		queryParam.put(Constants.JOURNEY_TYPE, journeyType);
		String sortCriteria = queryParam.get(Constants.SORT);
		List<String> criteriaOfSort = DeviceServiceImplUtility.getSortCriteriaForList(sortCriteria);
		String sortOption;
		String sortBy;
		if(CollectionUtils.isNotEmpty(criteriaOfSort)){
			sortOption = criteriaOfSort.get(0);
			sortBy = criteriaOfSort.get(1);
		queryParam.put("sortOption", sortOption);
		queryParam.put("sortBy", sortBy);
		}
		
		List<HandsetOnlineModel> listOfHandsetOnlineModel = deviceEs.getListOfHandsetOnlineModel(queryParam);
		List<FacetField> facetFieldList = deviceEs.getFacetFieldForHandsetOnlineModel(queryParam);
		
		if(facetFieldList != null && !facetFieldList.isEmpty()){
			handsetOnlineModelList.setFacetField(facetFieldList);
		}
		if(!listOfHandsetOnlineModel.isEmpty()){
			handsetOnlineModelList.setHandsetList(listOfHandsetOnlineModel);
		}
		return handsetOnlineModelList;
		}
		catch (Exception e) {
			LogHelper.error(this, "Received Null Values for the given handset online model" + e);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_HANDSET_ONLINE_MODEL);
		}
	}
	
	
}
