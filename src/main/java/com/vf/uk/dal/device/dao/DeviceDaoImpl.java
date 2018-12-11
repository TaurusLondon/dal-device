package com.vf.uk.dal.device.dao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.model.product.BazaarVoice;
import com.vf.uk.dal.device.utils.BazaarVoiceCache;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

/**
 * 1.Implementation of DeviceDAO Interface 2.DeviceDaoImpl should make call to
 * the exact methods of Solr and Coherence and should retrieve all the details.
 * 
 * 
 **/

@Slf4j
@Component("deviceDao")
public class DeviceDaoImpl implements DeviceDao {

	public static final String STRING_MODELS = "models";
	@Autowired
	CommonUtility commonUtility;

	@Autowired
	RestHighLevelClient restClient;

	@Autowired
	BazaarVoiceCache bzrVoiceCache;

	@Autowired
	DeviceESHelper deviceEs;

	/**
	 * 
	 * @param deviceId
	 * @param sortCriteria
	 * @return BundleDetailsFromComplansListingAPI
	 */
	@Override
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria) {
		BundleDetails bundleDetails = null;
		try {
			log.info( "Getting Compatible bundle details by calling Compatible Plan List API");
			bundleDetails = commonUtility.getBundleDetailsFromComplansListingAPI(deviceId, sortCriteria);
		} catch (ApplicationException e) {
			log.error( "No Compatible bundle Found By Given Bundle Id " + e);
			bundleDetails = null;
		}
		return bundleDetails;
	}

	/**
	 * Returns Device review details
	 * 
	 * @param deviceId
	 * @return DeviceReviewDetails
	 * @throws org.json.simple.parser.ParseException
	 */
	@Override
	public String getDeviceReviewDetails(String deviceId) {
		String jsonObject;
		log.info( "Start -->  calling  BazaarReviewRepository.get");
		jsonObject = bzrVoiceCache.getBazaarVoiceReviews(deviceId);
		return jsonObject;
	}

	/**
	 * @param offerCode
	 * @param journeyType
	 * @param bundleAndHardwareTupleList
	 * @return List<PriceForBundleAndHardware>
	 */
	@Override
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware;
		log.info( "Get the price details for Bundle and Hardware list from Pricing API");
		listOfPriceForBundleAndHardware = commonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
				journeyType,null);
		return listOfPriceForBundleAndHardware;
	}

	/**
	 * @author aditya.oli This method checks whether Bazaar Review Repository
	 *         has been initialized or not. If yes, it gets the Bazaar Voice
	 *         from the repository. Else it will initialize the Repository and
	 *         then fetch the Bazaar Voice for the service.
	 * @param String
	 *            skuId
	 * @return BazaarVoice
	 */
	@Override
	public BazaarVoice getBazaarVoice(String skuId) {
		BazaarVoice bazaarVoice = new BazaarVoice();
		bazaarVoice.setSkuId(skuId);
		bazaarVoice.setJsonsource(getDeviceReviewDetails(commonUtility.appendPrefixString(skuId)));
		return bazaarVoice;
	}

	@Override
	public List<com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion> getMerchandisingPromotionsEntityFromRepo(List<String> promotionAsTags) {
		return deviceEs.getMerchandising(promotionAsTags);
	}

	/**
	 * @param offerCode
	 * @param journeyType
	 * @param bundleAndHardwareTupleList
	 * @return CompletableFuture<List<PriceForBundleAndHardware>>
	 */
	@Override
	public CompletableFuture<List<PriceForBundleAndHardware>> getPriceForBundleAndHardwareListFromTupleListAsync(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType,
			String version, String groupType) {
		log.info( "Start -->  calling  getPriceForBundleAndHardwareListFromTupleList_PriceAPI");

		return CompletableFuture.supplyAsync(() -> {
			CatalogServiceAspect.CATALOG_VERSION.set(version);
			return commonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,journeyType, groupType);
		});

	}

	/**
	 * @param journeyType
	 * @param version
	 * @param bundleHardwareTupleList
	 * @return BundleAndHardwarePromotionsListFromBundleListAsync
	 */
	@Override
	public CompletableFuture<List<com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType, String version) {
		log.info( "Start -->  calling  getBundleAndHardwarePromotionsListFromBundleListAsync");

		return CompletableFuture.supplyAsync(() -> {
			CatalogServiceAspect.CATALOG_VERSION.set(version);
			return commonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList, journeyType);
		});

	}

	/**
	 * @param searchRequest
	 * @return SearchResponse
	 */
	@Override
	public SearchResponse getResponseFromDataSource(SearchRequest searchRequest) {
		log.info( "Start call time Elasticsearch" + System.currentTimeMillis());
		SearchResponse response = null;
		try {
			response = restClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			if (StringUtils.containsIgnoreCase(e.getMessage(), ExceptionMessages.INDEX_NOT_FOUND_EXCEPTION)) {
				log.error( ExceptionMessages.INDEX_NOT_FOUND_EXCEPTION);
				throw new ApplicationException(ExceptionMessages.INDEX_NOT_FOUND_EXCEPTION);
			}
			log.error( "::::::Exception occured while querieng bundle models from ES " + e);
		}
		log.info( "End call time Elasticsearch" + System.currentTimeMillis());
		return response;
	}

	/**
	 * @author manoj.bera
	 * @param id
	 * @param data
	 */
	@Override
	public void getUpdateElasticSearch(String id, String data) {
		try {
			UpdateRequest updateRequest = new UpdateRequest(CatalogServiceAspect.CATALOG_VERSION.get(), STRING_MODELS, id);
			updateRequest.doc(data, XContentType.JSON);
			restClient.update(updateRequest, RequestOptions.DEFAULT);

			UpdateRequest updateRequestForNull = new UpdateRequest(CatalogServiceAspect.CATALOG_VERSION.get(), STRING_MODELS, id)
					.doc(org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder().startObject().endObject());
			restClient.update(updateRequestForNull, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error( "::::::Exception From es ::::::" + e);
		}
	}

	/**
	 * @author manoj.bera
	 * @param id
	 * @param data
	 */
	@Override
	public void getIndexElasticSearch(String id, String data) {
		try {
			IndexRequest updateRequestForILSPromo = Requests.indexRequest(CatalogServiceAspect.CATALOG_VERSION.get());
			updateRequestForILSPromo.type(STRING_MODELS);
			updateRequestForILSPromo.id(id);
			updateRequestForILSPromo.source(data, XContentType.JSON);
			restClient.index(updateRequestForILSPromo, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error( "::::::Exception From es ::::::" + e);
		}
	}
}