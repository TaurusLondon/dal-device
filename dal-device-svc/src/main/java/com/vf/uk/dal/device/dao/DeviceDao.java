package com.vf.uk.dal.device.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
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
	//public List<DeviceTile> getListOfDeviceTile(String make,String model,String groupType, String deviceId, String journeyType, Double creditLimit, String offerCode, String bundleId);
	//public DeviceDetails getDeviceDetails(String deviceId,String journeyType,String offerCode);
	public List<DeviceTile> getDeviceTileById(String id, String offerCode, String journeyType);
	public List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType, String groupName);
	//public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId,String journeyType,String offerCode);
	//public FacetedDevice getDeviceList(String productClass,String make,String model,String groupType,String sortCriteria,int pageNumber,int pageSize,String capacity,String colour,String operatingSystem,String mustHaveFeatures);
	//public Insurances getInsuranceById(String deviceId);
	//public List<ProductGroupForDeviceListing> getDeviceListFromPricing(String grouptype);
	//public List<StockInfo> getStockAvailability(String groupType);
	//public boolean getStockInfo(String memberId);
	//public void cacheStockInfo(List<StockAvailability> listOfStockAvailability);
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria);
	public String getDeviceReviewDetails(String deviceId);
	public com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotionByPromotionName(String promotionName);
	public List<Group> getProductGroupsByType(String groupType);
	public CommercialProduct getCommercialProductRepositoryByLeadMemberId(String leadMemberId);
	public StockAvailability getStockAvailabilityByMemberId(String memberId);
	public void movePreCalcDataToSolr(List<DevicePreCalculatedData> preCalcPlanList);
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey,String filterCriteria,
			String sortBy,String sortOption,Integer pageNumber,Integer pageSize,String journeyType);
	public ProductGroupFacetModel getProductGroupsWithFacets(Filters filterKey,String journeyType);
	public List<ProductModel> getProductModel(List<String> listOfProducts);
	public List<BundleModel> getBundleDetails(List<String> listOfLeadPlanId);
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds);
	public Map<String, String> getDeviceReviewRating(List<String> listMemberIds);
	public CommercialProduct getCommercialProductByProductId(String productId);
	public CommercialBundle getCommercialBundleByBundleId(String bundleId);
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,String offerCode,String journeyType);
	public Collection<CommercialProduct> getListCommercialProductRepositoryByLeadMemberId(List<String> leadMemberId);
	//public CacheDeviceTileResponse insertCacheDeviceToDb();
	//public void updateCacheDeviceToDb(String jobId, String jobStatus);
	//public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId)throws ApplicationException;
	public Collection<CommercialBundle> getListCommercialBundleRepositoryByCompatiblePlanList(List<String> planIdList);
	public  List<OfferAppliedPriceModel> getBundleAndHardwarePriceFromSolr(List<String> deviceIds, String offerCode,String journeyType);
	public Group getGroupByProdGroupName(String groupName,String groupType);
	public List<CommercialProduct> getCommercialProductsList(List<String> productIdsList);
	
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String journeyType);
	public List<MerchandisingPromotionModel> getJourneyTypeCompatibleOfferCodes(String bundleClass,String journeyType);
	
	/*
	 * ============================================================================================================================================
	 * ==========================================================AFTER CODE REFACTORING============================================================
	 * ============================================================================================================================================
	 */
	public RequestManager getRequestManager();
	public CommercialProductRepository getCommercialProductRepository();
	public CommercialBundleRepository getCommercialBundleRepository();
	public ProductGroupRepository getProductGroupRepository();
	public MerchandisingPromotionRepository getMerchandisingPromotionRepository();
	public List<Group> getListOfProductGroupFromProductGroupRepository(String groupType);
	public Collection<CommercialBundle> getAllCommercialBundlesFromCommercialBundleRepository(List<String> listofLeadPlan);
	public CommercialBundle getCommercialBundleFromCommercialBundleRepository(String bundleId);
	public CommercialProduct getCommercialProductFromCommercialProductRepository(String deviceId);
	public List<ProductGroupModel> getListOfProductGroupsFromSolr();
	public List<Group> getListOfGroupsFromProductGroupRepository(List<String> listOfDeviceGroupName);
	public Collection<CommercialProduct> getCommercialProductListFromCommercialProductRepository(
			List<String> finalAccessoryList);
	public List<ProductModel> getListOfProductModelFromSolr(List<String> listOfProduct);
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModelFromSolr(List<String> deviceIds, String offerCode);
	public ProductGroupFacetModel getProductGroupFacetModelfromSolr(Filters filterKey, String filterCriteria, String sortBy,
			String sortOption, Integer pageNumber, Integer pageSize);
	public ProductGroupFacetModel getProductGroupFacetModelForFilterKeyfromSolr(Filters filterKey);
	public List<BundleModel> getBundleModelListFromSolr(List<String> listOfLeadPlanId);
	public Group getGroupFromProductGroupRepository(String groupName, String groupType);
	public MerchandisingPromotion getMerchandisingPromotionBasedOnPromotionName(
			String promotionName);
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModelFromSolr(String groupType,
			String journeyType);
	public List<CommercialProduct> getListOfCommercialProductsFromCommercialProductRepository(String make, String model);
	public BazaarReviewRepository getBazaarReviewRepository();
	public BazaarVoice getBazaarVoice(String skuId);
	public CacheDeviceTileResponse insertCacheDeviceToDb();
	public void updateCacheDeviceToDb(String jobId, String jobStatus);
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) throws ApplicationException;
}
