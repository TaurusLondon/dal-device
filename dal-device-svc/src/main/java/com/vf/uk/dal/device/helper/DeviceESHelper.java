package com.vf.uk.dal.device.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.handsetonlinemodel.HandsetOnlineModelList;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupFacetModel;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.querybuilder.DeviceQueryBuilderHelper;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;

/**
 * DeviceESHelper
 * @author manoj.bera
 *
 */
@Component
public class DeviceESHelper {

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	ResponseMappingHelper response;

	/**
	 * 
	 * @param displayName
	 * @param groupType
	 * @return ListOfProductGroupModel
	 */
	public List<ProductGroupModel> getListOfProductGroupModel(Set<String> displayName, String groupType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForProductGroupModelForDeliverMethod(displayName, groupType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of Product Model");
		return response.getListOfProductGroupModel(bundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param deviceIds
	 * @param journeyType
	 * @param offerCode
	 * @return ListOfOfferAppliedPriceModel
	 */
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModel(List<String> deviceIds, String journeyType,
			String offerCode) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForOfferAppliedPriceModel(deviceIds,
				journeyType, offerCode);
		SearchResponse bundleModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Offer Applied Price Model");
		return response.getListOfOfferAppliedPriceModel(bundleModelResponse);

	}

	/**
	 * 
	 * @param groupType
	 * @param journeyType
	 * @return ProductGroupFacetModel
	 */
	public List<FacetField> getProductGroupFacetModel(String groupType, String journeyType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForFacetCount(groupType, journeyType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Facet Field");
		return response.getFacetField(bundleResponse);
	}

	/**
	 * 
	 * @param deviceIds
	 * @return ListOfProductModel
	 */
	public List<ProductModel> getListOfProductModel(List<String> deviceIds) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductModel(deviceIds);
		SearchResponse productModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Product Model");
		return response.getListOfProductModel(productModelResponse);

	}

	/**
	 * 
	 * @author manoj.bera
	 * @param groupType
	 * @param make
	 * @param capacity
	 * @param colour
	 * @param operatingSystem
	 * @param mustHaveFeatures
	 * @param sortBy
	 * @param sortOption
	 * @param pageNumber
	 * @param pageSize
	 * @param journeyType
	 * @return ProductGroupFacetModel
	 */
	public ProductGroupFacetModel getProductGroupFacetModel(String groupType, String make, String capacity,
			String colour, String operatingSystem, String mustHaveFeatures, String sortBy, String sortOption,
			Integer pageNumber, Integer pageSize, String journeyType) {
		ProductGroupFacetModel productGroupFacetModel = new ProductGroupFacetModel();
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroupModel(groupType, make,
				capacity, colour, operatingSystem, mustHaveFeatures, sortBy, sortOption, pageNumber, pageSize,
				journeyType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Product Group Facet Model");
		List<ProductGroupModel> listOfProductGroups = response.getListOfProductGroupModel(bundleResponse);
		productGroupFacetModel.setListOfProductGroups(listOfProductGroups);
		productGroupFacetModel.setNumFound(Long.valueOf(listOfProductGroups.size()));
		productGroupFacetModel.setListOfFacetsFields(null);

		return productGroupFacetModel;
	}

	/**
	 * 
	 * @param journeyType
	 * @param groupType
	 * @return ListOfMerchandisingPromotionModel
	 */
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModel(String groupType,
			String journeyType) {
		List<String> journeyTypes = Arrays.asList(journeyType.split(","));
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMerchandisingPromotionModel(journeyTypes,
				groupType);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of Merchandising Promotion Model");
		return response.getListOfMerchandisingPromotionModelFromJson(bundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param tag
	 * @return MerchandisingPromotion
	 */
	public com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion getMerchandisingPromotion(
			String tag) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMerchandisingBySingleTagName(tag);
		SearchResponse merchandisingResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Merchandising Promotion object response");
		return response.getMerchandisingPromotion(merchandisingResponse);
	}

	/**
	 * @author manoj.bera
	 * @param bundleId
	 * @return CommercialBundle
	 */
	public CommercialBundle getCommercialBundle(String bundleId) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForCommercialBundle(bundleId,
				Constants.STRING_BUNDLE);
		SearchResponse commercialBundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Bundle object response");
		return response.getCommercialBundle(commercialBundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param bundleIds
	 * @return ListOfCommercialBundle
	 */
	public List<CommercialBundle> getListOfCommercialBundle(List<String> bundleIds) {

		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForListOfCommercialBundle(bundleIds,
				Constants.STRING_BUNDLE);
		SearchResponse commercialBundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Bundle List object response");
		return response.getListOfCommercialBundleFromJson(commercialBundleResponse);
	}

	/**
	 * @author manoj.bera
	 * @param deviceIds
	 * @return ListOfCommercialProduct
	 */
	public List<CommercialProduct> getListOfCommercialProduct(List<String> deviceIds) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForListOfCommercialProductAndCommercialBundle(deviceIds, Constants.STRING_PRODUCT);
		SearchResponse commercialListForInsuranceResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of CommercialProduct object response");
		return response.getCommercialProductFromJson(commercialListForInsuranceResponse);
	}

	/**
	 * @author manoj.bera
	 * @param deviceId
	 * @return CommercialProduct
	 */
	public CommercialProduct getCommercialProduct(String deviceId) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForCommercialProductAndCommercialBundle(deviceId, Constants.STRING_PRODUCT);
		SearchResponse commercialProduct = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Commercial Product object response");
		return response.getCommercialProduct(commercialProduct);
	}

	/**
	 * @author manoj.bera@
	 * @param make
	 * @param model
	 * @return ListOfCommercialProduct
	 */
	public List<CommercialProduct> getListOfCommercialProductByMakeAndModel(String make, String model) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMakeAndModel(make, model);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of CommercialProduct");
		return response.getCommercialProductFromJson(bundleResponse);
	}

	/**
	 * 
	 * @param groupName
	 * @param groupType
	 * @return ProductGroup
	 */
	public Group getProductGroupByTypeAndGroupName(String groupName, String groupType) {
		SearchRequest queryContextMapForProductGroup = DeviceQueryBuilderHelper
				.searchQueryForProductGroupWithGroupName(groupName, groupType);
		SearchResponse groupResponse = deviceDao.getResponseFromDataSource(queryContextMapForProductGroup);
		LogHelper.info(this, "converting elasticsearch response into get Product Group By Type And GroupName");
		return response.getSingleGroupFromJson(groupResponse);
	}

	/**
	 * 
	 * @param listOfDeviceGroupName
	 * @return ProductGroupByListOfGroupName
	 */
	public List<Group> getProductGroupByListOfGroupName(List<String> listOfDeviceGroupName) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroupByIds(listOfDeviceGroupName);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of Group");
		return response.getListOfGroupFromJson(bundleResponse);
	}

	/**
	 * 
	 * @param groupType
	 * @return List<Group>
	 */
	public List<Group> getProductGroupByType(String groupType) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductGroup(groupType);
		SearchResponse groupResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into Group List");
		return response.getListOfGroupFromJson(groupResponse);
	}

	/**
	 * 
	 * @param bundleIds
	 * @return ListOfBundleModel
	 */
	public List<BundleModel> getListOfBundleModel(List<String> bundleIds) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForBundleModel(bundleIds);
		;
		SearchResponse bundleModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfBundleModel(bundleModelResponse);

	}

	/**
	 * 
	 * @param promotionAsTags
	 * @return MerchandisingPromotion
	 */
	public List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> getMerchandising(
			List<String> promotionAsTags) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForMerchandisingByTagName(promotionAsTags);
		SearchResponse bundleResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfMerchandisingPromotionFromJson(bundleResponse);
	}
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
	public HandsetOnlineModelList getListOfHandsetOnlineModel(String deviceId, String journeyType,
			String make, String model, String groupType, String sort,
			Integer pageNumber, Integer pageSize, String color, String operatingSystem, String capacity,
			String mustHaveFeatures) {
		SearchRequest queryContextMap = DeviceQueryBuilderHelper
				.searchQueryForListOfHandsetOnlineModel(deviceId,journeyType,make,model,groupType,
						sort,pageNumber,pageSize,color,operatingSystem,capacity,
						mustHaveFeatures);
		SearchResponse handsetModelList = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into List Of Handset Online Model object response");
		return response.getOnlineHandsetModelFromJson(handsetModelList);
	}
}
