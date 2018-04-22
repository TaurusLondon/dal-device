package com.vf.uk.dal.device.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.datamodel.product.BazaarVoice;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;

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
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String packageType,
			String version);

	/**
	 * 
	 * @param bundleHardwareTupleList
	 * @param journeyType
	 * @return
	 */
	public CompletableFuture<List<BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType, String version);

	/**
	 * 
	 * @param searchRequest
	 * @return
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
}