package com.vf.uk.dal.device.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vodafone.business.service.RequestManager;
import com.vodafone.common.Filters;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.dal.domain.bazaarvoice.BazaarVoice;
import com.vodafone.dal.domain.repository.BazaarReviewRepository;
import com.vodafone.dal.domain.repository.CommercialBundleRepository;
import com.vodafone.dal.domain.repository.CommercialProductRepository;
import com.vodafone.dal.domain.repository.MerchandisingPromotionRepository;
import com.vodafone.dal.domain.repository.ProductGroupRepository;
import com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.productGroups.pojo.Group;
import com.vodafone.solrmodels.BundleModel;
import com.vodafone.solrmodels.MerchandisingPromotionModel;
import com.vodafone.solrmodels.OfferAppliedPriceModel;
import com.vodafone.solrmodels.ProductGroupFacetModel;
import com.vodafone.solrmodels.ProductGroupModel;
import com.vodafone.solrmodels.ProductModel;
import com.vodafone.stockAvailability.pojo.StockAvailability;

/** 
 * 1.DAO layer for coherence and solr. 
 **/

public interface DeviceDao {
	/**
	 * 
	 * @param id
	 * @param offerCode
	 * @param journeyType
	 * @return
	 */
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType);
	/**
	 * 
	 * @param groupType
	 * @param groupName
	 * @return
	 */
	public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType, String groupName);
	/**
	 * 
	 * @param deviceId
	 * @param sortCriteria
	 * @return
	 */
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria);
	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public String getDeviceReviewDetails(String deviceId);
	/**
	 * 
	 * @param promotionName
	 * @return
	 */
	public com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotionByPromotionName(String promotionName);
	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public List<Group> getProductGroupsByType(String groupType);
	/**
	 * 
	 * @param leadMemberId
	 * @return
	 */
	public CommercialProduct getCommercialProductRepositoryByLeadMemberId(String leadMemberId);
	/**
	 * 
	 * @param memberId
	 * @return
	 */
	public StockAvailability getStockAvailabilityByMemberId(String memberId);
	/**
	 * 
	 * @param preCalcPlanList
	 */
	public void movePreCalcDataToSolr(List<DevicePreCalculatedData> preCalcPlanList);
	/**
	 * 
	 * @param filterKey
	 * @param filterCriteria
	 * @param sortBy
	 * @param sortOption
	 * @param pageNumber
	 * @param pageSize
	 * @param journeyType
	 * @return
	 */
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey,String filterCriteria,
			String sortBy,String sortOption,Integer pageNumber,Integer pageSize,String journeyType);
	/**
	 * 
	 * @param filterKey
	 * @param journeyType
	 * @return
	 */
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey,String journeyType);
	/**
	 * 
	 * @param listOfProducts
	 * @return
	 */
	public List<ProductModel> getProductModel(List<String> listOfProducts);
	/**
	 * 
	 * @param listOfLeadPlanId
	 * @return
	 */
	public List<BundleModel> getBundleDetails(List<String> listOfLeadPlanId);
	/**
	 * 
	 * @param listMemberIds
	 * @return
	 */
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds);
	/**
	 * 
	 * @param listMemberIds
	 * @return
	 */
	public Map<String, String> getDeviceReviewRating(List<String> listMemberIds);
	/**
	 * 
	 * @param productId
	 * @return
	 */
	public CommercialProduct getCommercialProductByProductId(String productId);
	/**
	 * 
	 * @param bundleId
	 * @return
	 */
	public CommercialBundle getCommercialBundleByBundleId(String bundleId);
	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @param offerCode
	 * @param journeyType
	 * @return
	 */
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,String offerCode,String journeyType);
	/**
	 * 
	 * @param leadMemberId
	 * @return
	 */
	public Collection<CommercialProduct> getListCommercialProductRepositoryByLeadMemberId(List<String> leadMemberId);
	/**
	 * 
	 * @param planIdList
	 * @return
	 */
	public Collection<CommercialBundle> getListCommercialBundleRepositoryByCompatiblePlanList(List<String> planIdList);
	/**
	 * 
	 * @param deviceIds
	 * @param offerCode
	 * @param journeyType
	 * @return
	 */
	public  List<OfferAppliedPriceModel> getBundleAndHardwarePriceFromSolr(List<String> deviceIds, String offerCode,String journeyType);
	/**
	 * 
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	public Group getGroupByProdGroupName(String groupName,String groupType);
	/**
	 * 
	 * @param productIdsList
	 * @return
	 */
	public List<CommercialProduct> getCommercialProductsList(List<String> productIdsList);
	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String journeyType);
	/**
	 * 
	 * @param bundleClass
	 * @param journeyType
	 * @return
	 */
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String bundleClass,String journeyType);
	/**
	 * 
	 * @return
	 */
	public RequestManager getRequestManager();
	/**
	 * 
	 * @return
	 */
	public CommercialProductRepository getCommercialProductRepository();
	/**
	 * 
	 * @return
	 */
	public CommercialBundleRepository getCommercialBundleRepository();
	/**
	 * 
	 * @return
	 */
	public ProductGroupRepository getProductGroupRepository();
	/**
	 * 
	 * @return
	 */
	public MerchandisingPromotionRepository getMerchandisingPromotionRepository();
	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public List<Group> getListOfProductGroupFromProductGroupRepository(String groupType);
	/**
	 * 
	 * @param listofLeadPlan
	 * @return
	 */
	public Collection<CommercialBundle> getAllCommercialBundlesFromCommercialBundleRepository(List<String> listofLeadPlan);
	/**
	 * 
	 * @param bundleId
	 * @return
	 */
	public CommercialBundle getCommercialBundleFromCommercialBundleRepository(String bundleId);
	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public CommercialProduct getCommercialProductFromCommercialProductRepository(String deviceId);
	/**
	 * 
	 * @return
	 */
	public List<ProductGroupModel> getListOfProductGroupsFromSolr();
	/**
	 * 
	 * @param listOfDeviceGroupName
	 * @return
	 */
	public List<Group> getListOfGroupsFromProductGroupRepository(List<String> listOfDeviceGroupName);
	/**
	 * 
	 * @param finalAccessoryList
	 * @return
	 */
	public Collection<CommercialProduct> getCommercialProductListFromCommercialProductRepository(
			List<String> finalAccessoryList);
	/**
	 * 
	 * @param listOfProduct
	 * @return
	 */
	public List<ProductModel> getListOfProductModelFromSolr(List<String> listOfProduct);
	/**
	 * 
	 * @param deviceIds
	 * @param offerCode
	 * @return
	 */
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModelFromSolr(List<String> deviceIds, String offerCode);
	/**
	 * 
	 * @param filterKey
	 * @param filterCriteria
	 * @param sortBy
	 * @param sortOption
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public ProductGroupFacetModel getProductGroupFacetModelfromSolr(Filters filterKey, String filterCriteria, String sortBy,
			String sortOption, Integer pageNumber, Integer pageSize);
	/**
	 * 
	 * @param filterKey
	 * @return
	 */
	public ProductGroupFacetModel getProductGroupFacetModelForFilterKeyfromSolr(Filters filterKey);
	/**
	 * 
	 * @param listOfLeadPlanId
	 * @return
	 */
	public List<BundleModel> getBundleModelListFromSolr(List<String> listOfLeadPlanId);
	/**
	 * 
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	public Group getGroupFromProductGroupRepository(String groupName, String groupType);
	/**
	 * 
	 * @param promotionName
	 * @return
	 */
	public MerchandisingPromotion getMerchandisingPromotionBasedOnPromotionName(
			String promotionName);
	/**
	 * 
	 * @param groupType
	 * @param journeyType
	 * @return
	 */
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModelFromSolr(String groupType,
			String journeyType);
	/**
	 * 
	 * @param make
	 * @param model
	 * @return
	 */
	public List<CommercialProduct> getListOfCommercialProductsFromCommercialProductRepository(String make, String model);
	/**
	 * 
	 * @return
	 */
	public BazaarReviewRepository getBazaarReviewRepository();
	/**
	 * 
	 * @param skuId
	 * @return
	 */
	public BazaarVoice getBazaarVoice(String skuId);
	/**
	 * 
	 * @return
	 */
	public CacheDeviceTileResponse insertCacheDeviceToDb();
	/**
	 * 
	 * @param jobId
	 * @param jobStatus
	 */
	public void updateCacheDeviceToDb(String jobId, String jobStatus);
	/**
	 * 
	 * @param jobId
	 * @return
	 * @throws ApplicationException
	 */
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) throws ApplicationException;
	List<CommercialBundle> fetchCommericalBundlesbyList(List<String> listOfBundleIds);
}
