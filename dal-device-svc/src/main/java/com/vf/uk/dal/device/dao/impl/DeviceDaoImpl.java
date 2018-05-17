package com.vf.uk.dal.device.dao.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.product.BazaarVoice;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.utils.BazaarVoiceCache;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.utility.entity.BundleDetails;

/**
 * 1.Implementation of DeviceDAO Interface 2.DeviceDaoImpl should make call to
 * the exact methods of Solr and Coherence and should retrieve all the details.
 * 
 * 
 **/

@Component("deviceDao")
public class DeviceDaoImpl implements DeviceDao {

	@Autowired
	RegistryClient registryclnt;

	@Autowired
	RestHighLevelClient restClient;

	@Autowired
	BazaarVoiceCache bzrVoiceCache;

	@Autowired
	DeviceESHelper deviceEs;

	/**
	 * Returns list of ProductGroup based on groupType and groupName.
	 * 
	 * @param groupType
	 * @param groupName
	 * @return List<ProductGroup>
	 */
	/*
	 * @Override public List<ProductGroup>
	 * getProductGroupByGroupTypeGroupName(String groupType, String groupName) {
	 * List<ProductGroup> productGroupList = null; List<ProductGroupModel>
	 * listOfProductGroupModel = null; List<ProductGroupModel>
	 * listOfProductGroupModelForGroupName; listOfProductGroupModelForGroupName
	 * = new ArrayList<>(); try { if (requestManager == null) { requestManager =
	 * SolrConnectionProvider.getSolrConnection(); } if (groupType != null &&
	 * groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
	 * 
	 * LogHelper.info(this, "Start -->  calling  getProductGroups_Solr");
	 * listOfProductGroupModel =
	 * requestManager.getProductGroups(Filters.HANDSET); LogHelper.info(this,
	 * "End -->  After calling  getProductGroups_Solr");
	 * 
	 * } else { LogHelper.error(this, Constants.NO_DATA_FOUND_FOR_GROUP_TYPE +
	 * groupType); throw new
	 * ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE); }
	 * 
	 * if (listOfProductGroupModel != null &&
	 * !listOfProductGroupModel.isEmpty()) { productGroupList = new
	 * ArrayList<>(); if (groupName == null) { productGroupList =
	 * DaoUtils.convertGroupProductToProductGroupDetails(listOfProductGroupModel
	 * ); } else { for (ProductGroupModel productGroupModel :
	 * listOfProductGroupModel) { if
	 * (productGroupModel.getName().equals(groupName)) {
	 * listOfProductGroupModelForGroupName.add(productGroupModel);
	 * productGroupList = DaoUtils .convertGroupProductToProductGroupDetails(
	 * listOfProductGroupModelForGroupName); } } } if
	 * (productGroupList.isEmpty()) { LogHelper.error(this,
	 * "No data found for given group name:" + groupName); throw new
	 * ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_NAME); } } else {
	 * LogHelper.error(this, "No data found for given group name:" + groupType);
	 * throw new ApplicationException(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
	 * }
	 * 
	 * } catch (org.apache.solr.common.SolrException solrExcp) {
	 * SolrConnectionProvider.closeSolrConnection(); LogHelper.error(this,
	 * " SolrException: " + solrExcp); throw new
	 * ApplicationException(ExceptionMessages.SOLR_CONNECTION_EXCEPTION); }
	 * return productGroupList; }
	 */

	/**
	 * 
	 * @param deviceId
	 * @param sortCriteria
	 * @return
	 */
	@Override
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria) {
		BundleDetails bundleDetails = null;
		try {
			LogHelper.info(this, "Getting Compatible bundle details by calling Compatible Plan List API");
			bundleDetails = CommonUtility.getBundleDetailsFromComplansListingAPI(deviceId, sortCriteria, registryclnt);
		} catch (ApplicationException e) {
			LogHelper.error(this, "No Compatible bundle Found By Given Bundle Id " + e);
			bundleDetails = null;
		}
		return bundleDetails;
	}

	/**
	 * Returns Device review details
	 * 
	 * @param deviceId
	 * @return
	 * @throws org.json.simple.parser.ParseException
	 */
	@Override
	public String getDeviceReviewDetails(String deviceId) {
		String jsonObject;
		LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
		jsonObject = bzrVoiceCache.getBazaarVoiceReviews(deviceId);
		return jsonObject;
	}

	/**
	 * 
	 */
	@Override
	public List<PriceForBundleAndHardware> getPriceForBundleAndHardware(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware;
		LogHelper.info(this, "Get the price details for Bundle and Hardware list from Pricing API");
		listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
				registryclnt, journeyType);
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
		bazaarVoice.setJsonsource(getDeviceReviewDetails(CommonUtility.appendPrefixString(skuId)));
		return bazaarVoice;
	}

	@Override
	public Map<String, MerchandisingPromotion> getMerchandisingPromotionsEntityFromRepo(List<String> promotionAsTags) {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> listOfMerchandisingPromotions;
		Map<String, MerchandisingPromotion> promotions = new HashMap<>();
		listOfMerchandisingPromotions = deviceEs.getMerchandising(promotionAsTags);
		if (listOfMerchandisingPromotions != null && !listOfMerchandisingPromotions.isEmpty()) {
			listOfMerchandisingPromotions.forEach(solrModel -> {
				MerchandisingPromotion promotion = new MerchandisingPromotion();
				promotion.setTag(solrModel.getTag());
				promotion.setDescription(solrModel.getDescription());
				String footNoteKey = solrModel.getFootNoteKey();
				if (StringUtils.isNotBlank(footNoteKey)) {
					promotion.setFootNotes(Arrays.asList(footNoteKey.split(",")));
				}

				promotion.setLabel(solrModel.getLabel());
				promotion.setPriceEstablishedLabel(solrModel.getPriceEstablishedLabel());
				String packagesList = solrModel.getCondition().getPackageType();
				if (StringUtils.isNotBlank(packagesList)) {
					promotion.setPackageType(Arrays.asList(packagesList.split(",")));
				}
				if (solrModel.getPriority() != null) {
					promotion.setPriority(solrModel.getPriority().intValue());
				}
				promotion.setMpType(solrModel.getType());
				promotion.setPromotionMedia(solrModel.getPromotionMedia());
				promotions.put(solrModel.getTag(), promotion);
			});
		}
		return promotions;
	}

	@Override
	public CompletableFuture<List<PriceForBundleAndHardware>> getPriceForBundleAndHardwareListFromTupleListAsync(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType,
			String version) {
		LogHelper.info(this, "Start -->  calling  getPriceForBundleAndHardwareListFromTupleList_PriceAPI");

		return CompletableFuture.supplyAsync(() -> {
			Constants.CATALOG_VERSION.set(version);
			return CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode, registryclnt, journeyType);
		});

	}

	@Override
	public CompletableFuture<List<com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType, String version) {
		LogHelper.info(this, "Start -->  calling  getBundleAndHardwarePromotionsListFromBundleListAsync");

		return CompletableFuture.supplyAsync(() -> {
			Constants.CATALOG_VERSION.set(version);
			return CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList, journeyType,
					registryclnt);
		});

	}

	@Override
	public SearchResponse getResponseFromDataSource(SearchRequest searchRequest) {
		LogHelper.info(this, "Start call time Elasticsearch" + System.currentTimeMillis());
		SearchResponse response = null;
		try {
			response = restClient.search(searchRequest,
					new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
		} catch (Exception e) {
			if (StringUtils.containsIgnoreCase(e.getMessage(), ExceptionMessages.Index_NOT_FOUND_EXCEPTION)) {
				LogHelper.error(this, ExceptionMessages.Index_NOT_FOUND_EXCEPTION);
				throw new ApplicationException(ExceptionMessages.Index_NOT_FOUND_EXCEPTION);
			}
			LogHelper.error(this, "::::::Exception occured while querieng bundle models from ES " + e);
		}
		LogHelper.info(this, "End call time Elasticsearch" + System.currentTimeMillis());
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
			UpdateRequest updateRequest = new UpdateRequest(Constants.CATALOG_VERSION.get(), "models", id);
			updateRequest.doc(data, XContentType.JSON);
			restClient.update(updateRequest,
					new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));

			UpdateRequest updateRequestForNull = new UpdateRequest(Constants.CATALOG_VERSION.get(), "models", id)
					.doc(org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder().startObject().endObject());
			restClient.update(updateRequestForNull);
		} catch (IOException e) {
			LogHelper.error(this, "::::::Exception From es ::::::" + e);
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
			IndexRequest updateRequestForILSPromo = Requests.indexRequest(Constants.CATALOG_VERSION.get());
			updateRequestForILSPromo.type("models");
			updateRequestForILSPromo.id(id);
			updateRequestForILSPromo.source(data, XContentType.JSON);
			restClient.index(updateRequestForILSPromo,
					new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
		} catch (IOException e) {
			LogHelper.error(this, "::::::Exception From es ::::::" + e);
		}
	}
}
