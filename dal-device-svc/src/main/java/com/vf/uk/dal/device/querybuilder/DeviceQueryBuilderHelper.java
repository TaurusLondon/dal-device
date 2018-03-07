package com.vf.uk.dal.device.querybuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;

public class DeviceQueryBuilderHelper {

	private static int size=ConfigHelper.getInt(Constants.ELASTIC_SEARCH_INDEX_SIZE, Constants.DEFAULT_ELASTIC_SEARCH_INDEX_SIZE );
	private static int from=ConfigHelper.getInt(Constants.ELASTIC_SEARCH_INDEX_START_FROM, Constants.DEFAULT_ELASTIC_SEARCH_START_INDEX );
	/**
	 * 
	 * @param make
	 * @param model
	 * @return
	 */
	public static Map<String, Object> searchQueryForMakeAndModel(String make, String model) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_MAKE, make));
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_MODEL, model));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
		
	}
	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public static Map<String, Object> searchQueryForProductGroup(String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	/**
	 * 
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	public static Map<String, Object> searchQueryForProductGroupWithGroupName(String groupName,String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_NAME, groupName));
		    qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	/**
	 * 
	 * @param Id
	 * @return
	 */
	public static Map<String, Object> searchQueryForCommercialProductAndCommercialBundle(String Id) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_ID, Id));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	public static Map<String, Object> searchQueryForListOfCommercialProductAndCommercialBundle(List<String> ids) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_ID, ids.toString()));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	/**
	 * 
	 * @param listOfDeviceIds
	 * @return
	 */
	public static Map<String, Object> searchQueryForProductGroupByIds(List<String> listOfDeviceIds) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(listOfDeviceIds.size());
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, Constants.STRING_COMPATIBLE_ACCESSORIES));
		    qb.must(QueryBuilders.termsQuery(Constants.STRING_GROUP_NAME+Constants.STRING_KEY_WORD,listOfDeviceIds.toArray()));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public static Map<String, Object> searchQueryForProductGroupByGroupType(String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	/**
	 * 
	 * @param promotionAsTags
	 * @return
	 */
	public static Map<String, Object> searchQueryForMerchandisingByTagName(List<String> promotionAsTags) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(promotionAsTags.size());
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.termsQuery(Constants.STRING_Tag+Constants.STRING_KEY_WORD,promotionAsTags.toArray()));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	/**
	 * 
	 * @param promotionAsTags
	 * @return
	 */
	public static Map<String, Object> searchQueryForMerchandisingBySingleTagName(String promotionAsTag) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_Tag,promotionAsTag));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(DeviceQueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}

}
