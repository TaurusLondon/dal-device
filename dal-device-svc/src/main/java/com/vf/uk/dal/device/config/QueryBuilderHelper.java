package com.vf.uk.dal.device.config;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;

public class QueryBuilderHelper {

	public static Map<String, Object> searchQueryForMakeAndModel(String make, String model) {
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_MAKE, make));
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_MODEL, model));
			query = "{ \"query\" :" + qb.toString() + "}";
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
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_GROUP_TYPE, groupType));
			query = "{ \"query\" :" + qb.toString() + "}";
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
		Map<String, Object> searchQueryMap = null;
		Map<String, String> params = null;
		String query = null;
		try {
			LogHelper.info(QueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			searchQueryMap = new HashMap<>();
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
		    qb.must(QueryBuilders.matchQuery(Constants.STRING_ID, Id));
			query = "{ \"query\" :" + qb.toString() + "}";
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
