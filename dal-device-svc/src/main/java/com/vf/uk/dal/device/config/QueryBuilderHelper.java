package com.vf.uk.dal.device.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;

public class QueryBuilderHelper {

	
	public static Map<String, Object> searchQueryForMakeAndModel(String make, String model) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(0);
			searchRequestBuilder.size(250);
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_MAKE, make));
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_MODEL, model));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(QueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(QueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
		
	}
	public static Map<String, Object> searchQueryForProductGroup(String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(0);
			searchRequestBuilder.size(250);
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_GROUP_TYPE, groupType));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(QueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(QueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	public static Map<String, Object> searchQueryForProductGroupWithGroupName(String groupName,String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchQuery(Constants.STRING_GROUP_NAME, groupName));
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_GROUP_TYPE, groupType));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(QueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(QueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
	public static Map<String, Object> searchQueryForCommercialProductAndCommercialBundle(String Id) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_ID, Id));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(QueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(QueryBuilderHelper.class,
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
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchRequestBuilder.from(0);
			searchRequestBuilder.size(250);
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_ID, ids.toString()));
		    searchRequestBuilder.query(qb);
		    query = searchRequestBuilder.toString();
			LogHelper.info(QueryBuilderHelper.class, " <-----  Setting up Elasticsearch parameters and query  ----->");
			searchQueryMap.put(Constants.STRING_PARAMS, params);
			searchQueryMap.put(Constants.STRING_QUERY, query);

		} catch (Exception e) {
			LogHelper.error(QueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchQueryMap;
	}
}
