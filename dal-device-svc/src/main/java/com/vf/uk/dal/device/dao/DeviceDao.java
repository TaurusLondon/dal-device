package com.vf.uk.dal.device.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.elasticsearch.client.Response;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vodafone.business.service.RequestManager;
import com.vodafone.common.Filters;
import com.vf.uk.dal.device.datamodel.product.BazaarVoice;
import com.vodafone.solrmodels.BundleModel;
import com.vodafone.solrmodels.MerchandisingPromotionModel;
import com.vodafone.solrmodels.OfferAppliedPriceModel;
import com.vodafone.solrmodels.ProductGroupFacetModel;
import com.vodafone.solrmodels.ProductGroupModel;
import com.vodafone.solrmodels.ProductModel;

// TODO: Auto-generated Javadoc
/**
 * 1.DAO layer for coherence and solr.
 **/

public interface DeviceDao {

	/**
	 * Gets the device tile by id.
	 *
	 * @param id
	 *            the id
	 * @param offerCode
	 *            the offer code
	 * @param journeyType
	 *            the journey type
	 * @return the device tile by id
	 */
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType);

	/**
	 * Gets the product group by group type group name.
	 *
	 * @param groupType
	 *            the group type
	 * @param groupName
	 *            the group name
	 * @return the product group by group type group name
	 */
	public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType, String groupName);

	/**
	 * Gets the bundle details from complans listing API.
	 *
	 * @param deviceId
	 *            the device id
	 * @param sortCriteria
	 *            the sort criteria
	 * @return the bundle details from complans listing API
	 */
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria);

	/**
	 * Gets the device review details.
	 *
	 * @param deviceId
	 *            the device id
	 * @return the device review details
	 */
	public String getDeviceReviewDetails(String deviceId);

	/**
	 * Gets the merchandising promotion by promotion name.
	 *
	 * @param promotionName
	 *            the promotion name
	 * @return the merchandising promotion by promotion name
	 */
	/*public com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotionByPromotionName(
			String promotionName);*/

	/**
	 * Gets the product groups by type.
	 *
	 * @param groupType
	 *            the group type
	 * @return the product groups by type
	 */
	//public List<Group> getProductGroupsByType(String groupType);

	/**
	 * Gets the commercial product repository by lead member id.
	 *
	 * @param leadMemberId
	 *            the lead member id
	 * @return the commercial product repository by lead member id
	 */
	//public CommercialProduct getCommercialProductRepositoryByLeadMemberId(String leadMemberId);

	/**
	 * Gets the stock availability by member id.
	 *
	 * @param memberId
	 *            the member id
	 * @return the stock availability by member id
	 */
	//public StockAvailability getStockAvailabilityByMemberId(String memberId);

	/**
	 * Move pre calc data to solr.
	 *
	 * @param preCalcPlanList
	 *            the pre calc plan list
	 */
	public void movePreCalcDataToSolr(List<DevicePreCalculatedData> preCalcPlanList);

	/**
	 * Gets the product groups with facets.
	 *
	 * @param filterKey
	 *            the filter key
	 * @param filterCriteria
	 *            the filter criteria
	 * @param sortBy
	 *            the sort by
	 * @param sortOption
	 *            the sort option
	 * @param pageNumber
	 *            the page number
	 * @param pageSize
	 *            the page size
	 * @param journeyType
	 *            the journey type
	 * @return the product groups with facets
	 */
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey, String filterCriteria, String sortBy,
			String sortOption, Integer pageNumber, Integer pageSize, String journeyType);

	/**
	 * Gets the product groups with facets.
	 *
	 * @param filterKey
	 *            the filter key
	 * @param journeyType
	 *            the journey type
	 * @return the product groups with facets
	 */
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey, String journeyType);

	/**
	 * Gets the product model.
	 *
	 * @param listOfProducts
	 *            the list of products
	 * @return the product model
	 */
	public List<ProductModel> getProductModel(List<String> listOfProducts);

	/**
	 * Gets the bundle details.
	 *
	 * @param listOfLeadPlanId
	 *            the list of lead plan id
	 * @return the bundle details
	 */
	public List<BundleModel> getBundleDetails(List<String> listOfLeadPlanId);

	/**
	 * Gets the review rating list.
	 *
	 * @param listMemberIds
	 *            the list member ids
	 * @return the review rating list
	 */
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds);

	/**
	 * Gets the device review rating.
	 *
	 * @param listMemberIds
	 *            the list member ids
	 * @return the device review rating
	 */
	public Map<String, String> getDeviceReviewRating(List<String> listMemberIds);

	/**
	 * Gets the commercial product by product id.
	 *
	 * @param productId
	 *            the product id
	 * @return the commercial product by product id
	 */
	//public CommercialProduct getCommercialProductByProductId(String productId);

	/**
	 * Gets the commercial bundle by bundle id.
	 *
	 * @param bundleId
	 *            the bundle id
	 * @return the commercial bundle by bundle id
	 */
	//public CommercialBundle getCommercialBundleByBundleId(String bundleId);

	/**
	 * Gets the price for bundle and hardware.
	 *
	 * @param bundleAndHardwareTupleList
	 *            the bundle and hardware tuple list
	 * @param offerCode
	 *            the offer code
	 * @param journeyType
	 *            the journey type
	 * @return the price for bundle and hardware
	 */
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType);

	/**
	 * Gets the list commercial product repository by lead member id.
	 *
	 * @param leadMemberId
	 *            the lead member id
	 * @return the list commercial product repository by lead member id
	 */
	//public Collection<CommercialProduct> getListCommercialProductRepositoryByLeadMemberId(List<String> leadMemberId);

	/**
	 * Gets the list commercial bundle repository by compatible plan list.
	 *
	 * @param planIdList
	 *            the plan id list
	 * @return the list commercial bundle repository by compatible plan list
	 */
	//public Collection<CommercialBundle> getListCommercialBundleRepositoryByCompatiblePlanList(List<String> planIdList);

	/**
	 * Gets the bundle and hardware price from solr.
	 *
	 * @param deviceIds
	 *            the device ids
	 * @param offerCode
	 *            the offer code
	 * @param journeyType
	 *            the journey type
	 * @return the bundle and hardware price from solr
	 */
	public List<OfferAppliedPriceModel> getBundleAndHardwarePriceFromSolr(List<String> deviceIds, String offerCode,
			String journeyType);

	/**
	 * Gets the group by prod group name.
	 *
	 * @param groupName
	 *            the group name
	 * @param groupType
	 *            the group type
	 * @return the group by prod group name
	 */
	//public Group getGroupByProdGroupName(String groupName, String groupType);

	/**
	 * Gets the commercial products list.
	 *
	 * @param productIdsList
	 *            the product ids list
	 * @return the commercial products list
	 */
	//public List<CommercialProduct> getCommercialProductsList(List<String> productIdsList);

	/**
	 * Gets the journey type compatible offer codes.
	 *
	 * @param journeyType
	 *            the journey type
	 * @return the journey type compatible offer codes
	 */

	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String journeyType);

	/**
	 * Gets the journey type compatible offer codes.
	 *
	 * @param bundleClass
	 *            the bundle class
	 * @param journeyType
	 *            the journey type
	 * @return the journey type compatible offer codes
	 */
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String bundleClass, String journeyType);

	/**
	 * Gets the request manager.
	 *
	 * @return the request manager
	 */
	public RequestManager getRequestManager();

	/**
	 * Gets the commercial product repository.
	 *
	 * @return the commercial product repository
	 */
	//public CommercialProductRepository getCommercialProductRepository();

	/**
	 * Gets the commercial bundle repository.
	 *
	 * @return the commercial bundle repository
	 */
	//public CommercialBundleRepository getCommercialBundleRepository();

	/**
	 * Gets the product group repository.
	 *
	 * @return the product group repository
	 */
	//public ProductGroupRepository getProductGroupRepository();

	/**
	 * Gets the merchandising promotion repository.
	 *
	 * @return the merchandising promotion repository
	 */
	//public MerchandisingPromotionRepository getMerchandisingPromotionRepository();

	/**
	 * Gets the list of product group from product group repository.
	 *
	 * @param groupType
	 *            the group type
	 * @return the list of product group from product group repository
	 */
	//public List<Group> getListOfProductGroupFromProductGroupRepository(String groupType);

	/**
	 * Gets the all commercial bundles from commercial bundle repository.
	 *
	 * @param listofLeadPlan
	 *            the listof lead plan
	 * @return the all commercial bundles from commercial bundle repository
	 */
	//public Collection<CommercialBundle> getAllCommercialBundlesFromCommercialBundleRepository(List<String> listofLeadPlan);

	/**
	 * Gets the commercial bundle from commercial bundle repository.
	 *
	 * @param bundleId
	 *            the bundle id
	 * @return the commercial bundle from commercial bundle repository
	 */
	//public CommercialBundle getCommercialBundleFromCommercialBundleRepository(String bundleId);

	/**
	 * Gets the commercial product from commercial product repository.
	 *
	 * @param deviceId
	 *            the device id
	 * @return the commercial product from commercial product repository
	 */
	//public CommercialProduct getCommercialProductFromCommercialProductRepository(String deviceId);

	/**
	 * Gets the list of product groups from solr.
	 *
	 * @return the list of product groups from solr
	 */
	public List<ProductGroupModel> getListOfProductGroupsFromSolr();

	/**
	 * Gets the list of groups from product group repository.
	 *
	 * @param listOfDeviceGroupName
	 *            the list of device group name
	 * @return the list of groups from product group repository
	 */
	//public List<Group> getListOfGroupsFromProductGroupRepository(List<String> listOfDeviceGroupName);

	/**
	 * Gets the commercial product list from commercial product repository.
	 *
	 * @param finalAccessoryList
	 *            the final accessory list
	 * @return the commercial product list from commercial product repository
	 */
	//public Collection<CommercialProduct> getCommercialProductListFromCommercialProductRepository(
	//		List<String> finalAccessoryList);

	/**
	 * Gets the list of product model from solr.
	 *
	 * @param listOfProduct
	 *            the list of product
	 * @return the list of product model from solr
	 */
	public List<ProductModel> getListOfProductModelFromSolr(List<String> listOfProduct);

	/**
	 * Gets the list of offer applied price model from solr.
	 *
	 * @param deviceIds
	 *            the device ids
	 * @param offerCode
	 *            the offer code
	 * @return the list of offer applied price model from solr
	 */
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModelFromSolr(List<String> deviceIds,
			String offerCode);

	/**
	 * Gets the product group facet modelfrom solr.
	 *
	 * @param filterKey
	 *            the filter key
	 * @param filterCriteria
	 *            the filter criteria
	 * @param sortBy
	 *            the sort by
	 * @param sortOption
	 *            the sort option
	 * @param pageNumber
	 *            the page number
	 * @param pageSize
	 *            the page size
	 * @return the product group facet modelfrom solr
	 */
	public ProductGroupFacetModel getProductGroupFacetModelfromSolr(Filters filterKey, String filterCriteria,
			String sortBy, String sortOption, Integer pageNumber, Integer pageSize);

	/**
	 * Gets the product group facet model for filter keyfrom solr.
	 *
	 * @param filterKey
	 *            the filter key
	 * @return the product group facet model for filter keyfrom solr
	 */
	public ProductGroupFacetModel getProductGroupFacetModelForFilterKeyfromSolr(Filters filterKey);

	/**
	 * Gets the bundle model list from solr.
	 *
	 * @param listOfLeadPlanId
	 *            the list of lead plan id
	 * @return the bundle model list from solr
	 */
	public List<BundleModel> getBundleModelListFromSolr(List<String> listOfLeadPlanId);

	/**
	 * Gets the group from product group repository.
	 *
	 * @param groupName
	 *            the group name
	 * @param groupType
	 *            the group type
	 * @return the group from product group repository
	 */
	//public Group getGroupFromProductGroupRepository(String groupName, String groupType);

	/**
	 * Gets the merchandising promotion based on promotion name.
	 *
	 * @param promotionName
	 *            the promotion name
	 * @return the merchandising promotion based on promotion name
	 */
	//public MerchandisingPromotion getMerchandisingPromotionBasedOnPromotionName(String promotionName);

	/**
	 * Gets the list of merchandising promotion model from solr.
	 *
	 * @param groupType
	 *            the group type
	 * @param journeyType
	 *            the journey type
	 * @return the list of merchandising promotion model from solr
	 */
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModelFromSolr(String groupType,
			String journeyType);

	/**
	 * Gets the list of commercial products from commercial product repository.
	 *
	 * @param make
	 *            the make
	 * @param model
	 *            the model
	 * @return the list of commercial products from commercial product
	 *         repository
	 */
	//public List<CommercialProduct> getListOfCommercialProductsFromCommercialProductRepository(String make,
			//String model);

	/**
	 * Gets the bazaar review repository.
	 *
	 * @return the bazaar review repository
	 */
	//public BazaarReviewRepository getBazaarReviewRepository();

	/**
	 * Gets the bazaar voice.
	 *
	 * @param skuId
	 *            the sku id
	 * @return the bazaar voice
	 */
	public BazaarVoice getBazaarVoice(String skuId);

	/**
	 * Insert cache device to db.
	 *
	 * @return the cache device tile response
	 */
	public CacheDeviceTileResponse insertCacheDeviceToDb();

	/**
	 * Update cache device to db.
	 *
	 * @param jobId
	 *            the job id
	 * @param jobStatus
	 *            the job status
	 */
	public void updateCacheDeviceToDb(String jobId, String jobStatus);

	/**
	 * Gets the cache device job status.
	 *
	 * @param jobId
	 *            the job id
	 * @return the cache device job status
	 * @throws ApplicationException
	 *             the application exception
	 */
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) throws ApplicationException;

	/**
	 * Fetch commerical bundlesby list.
	 *
	 * @param listOfBundleIds
	 *            the list of bundle ids
	 * @return the list
	 */
	//List<CommercialBundle> fetchCommericalBundlesbyList(List<String> listOfBundleIds);

	/**
	 * Gets the merchandising promotions entity from solr model.
	 *
	 * @param promotionAsTags
	 *            the promotion as tags
	 * @return the merchandising promotions entity from solr model
	 */
	public Map<String, com.vf.uk.dal.device.entity.MerchandisingPromotion> getMerchandisingPromotionsEntityFromRepo(
			List<String> promotionAsTags);
	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @param offerCode
	 * @param packageType
	 * @return
	 */
	public CompletableFuture<List<PriceForBundleAndHardware>> getPriceForBundleAndHardwareListFromTupleListAsync(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String packageType);
	
	/**
	 * 
	 * @param bundleHardwareTupleList
	 * @param journeyType
	 * @return
	 */
	public CompletableFuture<List<BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType);
	
	/**
	 * 
	 * @author manoj.bera
	 * @param params
	 * @param query
	 * @param endPoint
	 * @return
	 */
	public Response getResponseFromDataSource(Map<String, String> params, String query);
}
