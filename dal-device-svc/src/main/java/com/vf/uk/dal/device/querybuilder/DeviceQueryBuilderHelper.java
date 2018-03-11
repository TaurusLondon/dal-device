package com.vf.uk.dal.device.querybuilder;

import java.util.List;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.SingletonMapperUtility;

public class DeviceQueryBuilderHelper {

	private static int size = ConfigHelper.getInt(Constants.ELASTIC_SEARCH_INDEX_SIZE,
			Constants.DEFAULT_ELASTIC_SEARCH_INDEX_SIZE);
	private static int from = ConfigHelper.getInt(Constants.ELASTIC_SEARCH_INDEX_START_FROM,
			Constants.DEFAULT_ELASTIC_SEARCH_START_INDEX);
	private static SearchSourceBuilder searchRequestBuilder = SingletonMapperUtility.getSearchSourceBuilder();
	private static SearchRequest searchRequest = new SearchRequest(ConfigHelper.getString(
			Constants.ELASTIC_SEARCH_ENDPOINT_NORMALISED_DATA, Constants.DEFAULT_ENDPOINT_FOR_NORMALIZED_INDEX));

	/**
	 * 
	 * @author manoj.bera
	 * @param make
	 * @param model
	 * @return
	 */
	public static SearchRequest searchQueryForMakeAndModel(String make, String model) {
		searchRequestBuilder.clearRescorers();
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_MAKE, make));
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_MODEL, model));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);
		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;

	}

	/**
	 * @author manoj.bera
	 * @param groupType
	 * @return
	 */
	public static SearchRequest searchQueryForProductGroup(String groupType) {
		searchRequestBuilder.clearRescorers();
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	public static SearchRequest searchQueryForProductGroupWithGroupName(String groupName, String groupType) {
		searchRequestBuilder.clearRescorers();
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_NAME, groupName));
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param Id
	 * @return
	 */
	public static SearchRequest searchQueryForCommercialProductAndCommercialBundle(String Id) {
		searchRequestBuilder.clearRescorers();
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchQuery(Constants.STRING_ID, Id));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @author manoj.bera
	 * Method to build query for either list of ProductIds or list of Product
	 * Names.
	 * 
	 * @param ids
	 * @return searchQueryMap
	 */
	public static SearchRequest searchQueryForListOfCommercialProductAndCommercialBundle(List<String> idsOrNames) {
		searchRequestBuilder.clearRescorers();
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(idsOrNames.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();

			if (idsOrNames.get(0).matches(Constants.NUMER_REG_EXP)) {
				qb.must(QueryBuilders.termsQuery(Constants.STRING_ID + Constants.STRING_KEY_WORD, idsOrNames));
			} else {
				qb.must(QueryBuilders.termsQuery(Constants.STRING_GROUP_NAME + Constants.STRING_KEY_WORD, idsOrNames));
			}
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);
		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param listOfDeviceIds
	 * @return
	 */
	public static SearchRequest searchQueryForProductGroupByIds(List<String> listOfDeviceIds) {
		searchRequestBuilder.clearRescorers();
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(listOfDeviceIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE,
					Constants.STRING_COMPATIBLE_ACCESSORIES));
			qb.must(QueryBuilders.termsQuery(Constants.STRING_GROUP_NAME + Constants.STRING_KEY_WORD,
					listOfDeviceIds.toArray()));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param groupType
	 * @return
	 */
	public static SearchRequest searchQueryForProductGroupByGroupType(String groupType) {
		searchRequestBuilder.clearRescorers();
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param promotionAsTags
	 * @return
	 */
	public static SearchRequest searchQueryForMerchandisingByTagName(List<String> promotionAsTags) {
		searchRequestBuilder.clearRescorers();
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(promotionAsTags.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(Constants.STRING_Tag + Constants.STRING_KEY_WORD,
					promotionAsTags.toArray()));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param promotionAsTags
	 * @return
	 */
	public static SearchRequest searchQueryForMerchandisingBySingleTagName(String promotionAsTag) {
		searchRequestBuilder.clearRescorers();
		try {
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_Tag, promotionAsTag));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

}
