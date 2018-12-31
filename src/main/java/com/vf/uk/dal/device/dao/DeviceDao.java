package com.vf.uk.dal.device.dao;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.model.product.BazaarVoice;

/**
 * 
 * This is used to achieve abstraction
 * 
 *
 */
public interface DeviceDao {

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
	 * Gets the bazaar voice.
	 *
	 * @param skuId
	 *            the sku id
	 * @return the bazaar voice
	 */
	public BazaarVoice getBazaarVoice(String skuId);

	/**
	 * Gets the merchandising promotions entity from solr model.
	 *
	 * @param promotionAsTags
	 *            the promotion as tags
	 * @return the merchandising promotions entity from solr model
	 */
	public List<com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion> getMerchandisingPromotionsEntityFromRepo(
			List<String> promotionAsTags);

	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @param offerCode
	 * @param packageType
	 * @param version
	 * @return PriceForBundleAndHardwareListFromTupleListAsync
	 */
	public CompletableFuture<List<PriceForBundleAndHardware>> getPriceForBundleAndHardwareListFromTupleListAsync(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String packageType,
			String version, String groupType);

	/**
	 * 
	 * @param bundleHardwareTupleList
	 * @param journeyType
	 * @param version
	 * @return BundleAndHardwarePromotionsListFromBundleListAsync
	 */
	public CompletableFuture<List<BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType, String version);

	/**
	 * 
	 * @param searchRequest
	 * @return ResponseFromDataSource
	 */
	public SearchResponse getResponseFromDataSource(SearchRequest searchRequest);

	/**
	 * 
	 * @param id
	 * @param data
	 */
	public void getUpdateElasticSearch(String id, String data);

	/**
	 * 
	 * @param id
	 * @param data
	 */
	public void getIndexElasticSearch(String id, String data);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public SearchResponse getResponseFromElasticSearch(SearchRequest request);
}