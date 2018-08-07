package com.vf.uk.dal.device.querybuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;

public class DeviceQueryBuilderHelper {

	private static int size = ConfigHelper.getInt(Constants.ELASTIC_SEARCH_INDEX_SIZE,
			Constants.DEFAULT_ELASTIC_SEARCH_INDEX_SIZE);
	private static int from = ConfigHelper.getInt(Constants.ELASTIC_SEARCH_INDEX_START_FROM,
			Constants.DEFAULT_ELASTIC_SEARCH_START_INDEX);
	private static String[] includes = null;
	private static final String[] ex = new String[0];
	static {
		includes = new String[] { "id", "name", "desc", "paymentType", "availability", "commitment", "productLines",
				"deviceSpecificPricing", "serviceProducts", "allowances", "recurringCharge", "displayName",
				"listOfimageURLs", "specificationGroups", "bundleControl", "displayGroup" };
	}

	/**
	 * 
	 * @author manoj.bera
	 * @param make
	 * @param model
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForMakeAndModel(String make, String model) {
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_MAKE, make));
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_MODEL, model));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query   ----->");
			searchRequest.source(searchRequestBuilder);
		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder  :::::: " + e);

		}
		return searchRequest;

	}

	/**
	 * @author manoj.bera
	 * @param groupType
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductGroup(String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder:::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param groupName
	 * @param groupType
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductGroupWithGroupName(String groupName, String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_NAME, groupName));
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <------ Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder::::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param Id
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForCommercialProductAndCommercialBundle(String Id, String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping----->");
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(Constants.STRING_ID + Constants.STRING_KEY_WORD, Id));
			qb.must(QueryBuilders.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.STRING_RAW + type));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder : ::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @author manoj.bera Method to build query for either list of ProductIds or
	 *         list of Product Names.
	 * 
	 * @param ids
	 * @return searchQueryMap
	 */
	public static SearchRequest searchQueryForListOfCommercialProductAndCommercialBundle(List<String> idsOrNames,
			String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(idsOrNames.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			if (idsOrNames.get(0).matches(Constants.NUMER_REG_EXP)) {
				qb.must(QueryBuilders.termsQuery(Constants.STRING_ID + Constants.STRING_KEY_WORD, idsOrNames));
			} else {
				qb.must(QueryBuilders.termsQuery(Constants.STRING_GROUP_NAME + Constants.STRING_KEY_WORD, idsOrNames));
			}
			qb.must(QueryBuilders
					.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD, Constants.STRING_RAW + type)
					.boost(2.0F)).boost(3.0F);
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query  ------>");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					":::::: Exception in using Elasticsearch QueryBuilder :::::: " + e);
		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param listOfDeviceIds
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductGroupByIds(List<String> listOfDeviceIds) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<-----Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(listOfDeviceIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE,
					Constants.STRING_COMPATIBLE_ACCESSORIES));
			qb.must(QueryBuilders.termsQuery(Constants.STRING_GROUP_NAME + Constants.STRING_KEY_WORD,
					listOfDeviceIds.toArray()));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----   Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					":::::: Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param groupType
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductGroupByGroupType(String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<-------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <------  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::: Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param promotionAsTags
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForMerchandisingByTagName(List<String> promotionAsTags) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(promotionAsTags.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(Constants.STRING_Tag + Constants.STRING_KEY_WORD,
					promotionAsTags.toArray()));
			qb.must(QueryBuilders.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.STRING_RAW + Constants.STRING_PROMOTION));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-------  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param promotionAsTags
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForMerchandisingBySingleTagName(String promotionAsTag) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_Tag, promotionAsTag));
			qb.must(QueryBuilders.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.STRING_RAW + Constants.STRING_PROMOTION));
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <------  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					":::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param listOfDeviceIds
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForMerchandisingPromotionModel(List<String> journeyType, String groupName) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<--------Elasticsearch query mapping------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(Constants.STRING_PRODUCT_LINE + Constants.STRING_KEY_WORD, groupName));// .operator(Operator.AND)
			qb.must(QueryBuilders.termsQuery(Constants.STRING_PACKAGE_TYPE + Constants.STRING_KEY_WORD, journeyType));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder ::::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param groupType
	 * @param make
	 * @param capacity
	 * @param colour
	 * @param operatingSystem
	 * @param mustHaveFeatures
	 * @param sortBy
	 * @param sortOption
	 * @param pageNumber
	 * @param pageSize
	 * @param journeyType
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductGroupModel(String groupType, String make, String capacity,
			String colour, String operatingSystem, String mustHaveFeatures, String sortBy, String sortOption,
			Integer pageNumber, Integer pageSize, String journeyType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<--------Elasticsearch query mapping------>");
			searchRequestBuilder.from(pageNumber);
			searchRequestBuilder.size(pageSize);
			if (StringUtils.isNotEmpty(sortOption) && sortOption.equalsIgnoreCase(Constants.SORT_OPTION_ASC)) {
				searchRequestBuilder.sort(sortBy.toLowerCase(), SortOrder.ASC);
			} else if (StringUtils.isNotEmpty(sortOption) && sortOption.equalsIgnoreCase(Constants.SORT_OPTION_DESC)) {
				searchRequestBuilder.sort(sortBy.toLowerCase(), SortOrder.DESC);
			}
			BoolQueryBuilder qb = getFilterCriteria(make, capacity, colour, operatingSystem, mustHaveFeatures);
			qb.must(QueryBuilders.termQuery(Constants.STRING_TYPE + Constants.STRING_KEY_WORD, groupType));

			if (Constants.STRING_UPGRADE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(Constants.STRING_UPGRADED_LEAD_DEVICE_ID, "*"));
			} else if (StringUtils.isBlank(journeyType)
					|| Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
					|| Constants.STRING_SECOND_LINE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(Constants.STRING_NON_UPGRADED_LEAD_DEVICE_ID, "*"));
			}
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder ::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param make
	 * @param capacity
	 * @param colour
	 * @param operatingSystem
	 * @param mustHaveFeatures
	 * @return BoolQueryBuilder
	 */
	private static BoolQueryBuilder getFilterCriteria(String make, String capacity, String colour,
			String operatingSystem, String mustHaveFeatures) {
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		if (StringUtils.isNotBlank(make)) {
			String[] makeArray = make.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_EQUIPMENT_MAKE_COLON + Constants.STRING_KEY_WORD,
					Arrays.asList(makeArray)));
		}
		if (capacity != null && !"\"\"".equals(capacity)) {
			String[] capa = capacity.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_CAPACITY_COLON + Constants.STRING_KEY_WORD,
					Arrays.asList(capa)));
		}
		if (colour != null && !"\"\"".equals(colour)) {
			String[] color = colour.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_COLOUR_COLON + Constants.STRING_KEY_WORD,
					Arrays.asList(color)));
		}
		if (operatingSystem != null && !"\"\"".equals(operatingSystem)) {
			String[] os = operatingSystem.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_OPERATING_SYSTEM + Constants.STRING_KEY_WORD,
					Arrays.asList(os)));
		}
		if (mustHaveFeatures != null && !"\"\"".equals(mustHaveFeatures)) {
			String[] mhf = mustHaveFeatures.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_MUST_HAVE_FEATURES_WITH_COLON + Constants.STRING_KEY_WORD,
					Arrays.asList(mhf)));
		}
		return qb;
	}

	/**
	 * 
	 * @param groupType
	 * @param journeyType
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForFacetCount(String groupType, String journeyType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<-------Elasticsearch query mapping------->");
			searchRequestBuilder.size(from);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(Constants.STRING_TYPE + Constants.STRING_KEY_WORD, groupType));
			if (Constants.STRING_UPGRADE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(Constants.STRING_UPGRADED_LEAD_DEVICE_ID, "*"));
			} else if (StringUtils.isBlank(journeyType)
					|| Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
					|| Constants.STRING_SECOND_LINE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(Constants.STRING_NON_UPGRADED_LEAD_DEVICE_ID, "*"));
			}
			TermsAggregationBuilder make = AggregationBuilders.terms(Constants.STRING_EQUIPMENT_MAKE_COLON)
					.field(Constants.STRING_EQUIPMENT_MAKE_COLON + Constants.STRING_KEY_WORD);
			TermsAggregationBuilder capacity = AggregationBuilders.terms(Constants.STRING_CAPACITY_COLON)
					.field(Constants.STRING_CAPACITY_COLON + Constants.STRING_KEY_WORD);
			TermsAggregationBuilder facetColour = AggregationBuilders.terms(Constants.STRING_COLOUR_COLON)
					.field(Constants.STRING_COLOUR_COLON + Constants.STRING_KEY_WORD);
			TermsAggregationBuilder operatingSystem = AggregationBuilders.terms(Constants.STRING_OPERATING_SYSTEM)
					.field(Constants.STRING_OPERATING_SYSTEM + Constants.STRING_KEY_WORD);
			TermsAggregationBuilder mustHaveFeatures = AggregationBuilders
					.terms(Constants.STRING_MUST_HAVE_FEATURES_WITH_COLON)
					.field(Constants.STRING_MUST_HAVE_FEATURES_WITH_COLON + Constants.STRING_KEY_WORD);
			TermsAggregationBuilder colour = AggregationBuilders.terms(Constants.STRING_COLOUR_FOR_FACET)
					.field(Constants.STRING_COLOUR_FOR_FACET + Constants.STRING_KEY_WORD);
			searchRequestBuilder.aggregation(make);
			searchRequestBuilder.aggregation(capacity);
			searchRequestBuilder.aggregation(facetColour);
			searchRequestBuilder.aggregation(operatingSystem);
			searchRequestBuilder.aggregation(mustHaveFeatures);
			searchRequestBuilder.aggregation(colour);
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder :::::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param deviceIds
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductModel(List<String> deviceIds) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<--------Elasticsearch query mapping-------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(deviceIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(Constants.STRING_PRODUCT_ID + Constants.STRING_KEY_WORD, deviceIds));
			qb.must(QueryBuilders.termsQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.STRING_OPT + Constants.STRING_PRODUCT));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::::Exception in using Elasticsearch QueryBuilder :::::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param bundleIds
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForBundleModel(List<String> bundleIds) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<-----Elasticsearch query mapping----->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(bundleIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(Constants.STRING_BUNDLE_ID + Constants.STRING_KEY_WORD, bundleIds));
			qb.must(QueryBuilders.termsQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.STRING_OPT + Constants.STRING_BUNDLE));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					":::::: Exception in using Elasticsearch QueryBuilder  :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * @author manoj.bera
	 * @param deviceIds
	 * @param journeyType
	 * @param offerCode
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForOfferAppliedPriceModel(List<String> deviceIds, String journeyType,
			String offerCode) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<-------Elasticsearch query mapping------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(Constants.STRING_PRODUCT_ID + Constants.STRING_KEY_WORD, deviceIds));
			qb.must(QueryBuilders.termQuery(Constants.STRING_OFFER_CODE + Constants.STRING_KEY_WORD, offerCode));
			qb.must(QueryBuilders.termQuery(Constants.STRING_JOURNEY_TYPE + Constants.STRING_KEY_WORD, journeyType));
			qb.must(QueryBuilders.wildcardQuery(Constants.STRING_ID, Constants.STRING_OFFER + "*"));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					":::::: Exception in using Elasticsearch QueryBuilder  :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param displayNames
	 * @param groupType
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForProductGroupModelForDeliverMethod(Set<String> displayNames,
			String groupType) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<--------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(displayNames.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(Constants.STRING_TYPE + Constants.STRING_KEY_WORD, groupType));
			qb.must(QueryBuilders.termsQuery(Constants.STRING_GROUP_NAME + Constants.STRING_KEY_WORD, displayNames));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::::Exception in using Elasticsearch QueryBuilder ::::::  " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param idsOrNames
	 * @param type
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForListOfCommercialBundle(List<String> idsOrNames, String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {

			LogHelper.info(DeviceQueryBuilderHelper.class,
					"<------Elasticsearch query mapping ForCommercial Bundle------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(idsOrNames.size());

			searchRequestBuilder.fetchSource(includes, ex);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(Constants.STRING_ID + Constants.STRING_KEY_WORD, idsOrNames));
			qb.must(QueryBuilders
					.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD, Constants.STRING_RAW + type)
					.boost(2.0F)).boost(3.0F);
			searchRequestBuilder.query(qb);
			LogHelper.info(DeviceQueryBuilderHelper.class,
					" <-----  Setting up Elasticsearch parameters and query For Commercial Bundle ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			LogHelper.error(DeviceQueryBuilderHelper.class,
					"::::::Exception in using Elasticsearch QueryBuilder For Commercial Bundle :::::: " + e);
		}
		return searchRequest;
	}

	/**
	 * 
	 * @param Id
	 * @param type
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForCommercialBundle(String Id, String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());

		searchRequestBuilder.fetchSource(includes, ex);
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(Constants.STRING_ID + Constants.STRING_KEY_WORD, Id));
			qb.must(QueryBuilders.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.STRING_RAW + type));
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
	 * @param queryParam
	 * @return
	 */
	public static SearchRequest searchQueryForListOfHandsetOnlineModel(Map<String, String> queryParam) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(Constants.CATALOG_VERSION.get());
		try {
			LogHelper.info(DeviceQueryBuilderHelper.class, "<------Elasticsearch query mapping------>");
			
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(Constants.STRING_ALL_TYPE + Constants.STRING_KEY_WORD,
					Constants.HANDSET_ONLINE_MODEL));
			setPageNumberAndSize(queryParam, searchRequestBuilder);
			setMakeQuery(queryParam, qb);
			setModelQuery(queryParam, qb);
			setSizeQuery(queryParam, qb);
			setProductGroupType(queryParam, qb);
			setColorQuery(queryParam, qb);
			setOperatingSystem(queryParam, qb);
			setMustHaveFeatures(queryParam, qb);
			setNonLeadUpgradeDeviceID(queryParam, qb);
			setLeadUpgradeDeviceId( queryParam, qb);
			setDeviceId(queryParam, qb);
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
	 * @param queryParam
	 * @param qb
	 */
	private static void setDeviceId(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if(queryParam.containsKey(Constants.DEVICE_ID) && StringUtils.isNotBlank(queryParam.get(Constants.DEVICE_ID))){
			qb.must(QueryBuilders.termQuery("device."+queryParam.get(Constants.DEVICE_ID)+".deviceId" + Constants.STRING_KEY_WORD, queryParam.get(Constants.DEVICE_ID)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setLeadUpgradeDeviceId(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.SORT) && StringUtils.isNotBlank(queryParam.get(Constants.SORT))
				&& checkForJourneyTypeQueryUpgrade(queryParam)) {
			qb.must(QueryBuilders.wildcardQuery(Constants.STRING_LEAD_UPGRADED_DEVICE_ID, "*"));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setNonLeadUpgradeDeviceID(Map<String, String> queryParam, BoolQueryBuilder qb) {
		
		if (queryParam.containsKey(Constants.SORT) && StringUtils.isNotBlank(queryParam.get(Constants.SORT))
				&& checkForJourneyTypeQuery(queryParam)){
			qb.must(QueryBuilders.wildcardQuery(Constants.STRING_LEAD_NON_UPGRADE_DEVICE_ID, "*"));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	private static boolean checkForJourneyTypeQuery(Map<String, String> queryParam) {
		return queryParam.containsKey(Constants.JOURNEY_TYPE)
					&& StringUtils.isBlank(queryParam.get(Constants.JOURNEY_TYPE))
					|| Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(queryParam.get(Constants.JOURNEY_TYPE))
					|| Constants.STRING_SECOND_LINE.equalsIgnoreCase(queryParam.get(Constants.JOURNEY_TYPE));
	}
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	private static boolean checkForJourneyTypeQueryUpgrade(Map<String, String> queryParam) {
		return queryParam.containsKey(Constants.JOURNEY_TYPE)
				&& StringUtils.isNotBlank(queryParam.get(Constants.JOURNEY_TYPE))&& 
				Constants.STRING_UPGRADE.equalsIgnoreCase(queryParam.get(Constants.JOURNEY_TYPE));
	}
	/**
	 * 
	 * @param mustHaveFeatures
	 * @param qb
	 */
	private static void setMustHaveFeatures(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.MUST_HAVE_FEATURES) && StringUtils.isNotBlank(queryParam.get(Constants.MUST_HAVE_FEATURES)) && !"\"\"".equals(queryParam.get(Constants.MUST_HAVE_FEATURES))) {
			String[] mhf = queryParam.get(Constants.MUST_HAVE_FEATURES).replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_MUST_HAVE_FEATURES_WITH_COLON + Constants.STRING_KEY_WORD,
					Arrays.asList(mhf)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setOperatingSystem(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.OPERATING_SYSTEM) && StringUtils.isNotBlank(queryParam.get(Constants.OPERATING_SYSTEM))  && !"\"\"".equals(queryParam.get(Constants.OPERATING_SYSTEM))) {
			String[] os = queryParam.get(Constants.OPERATING_SYSTEM).replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_OPERATING_SYSTEM + Constants.STRING_KEY_WORD,
					Arrays.asList(os)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setColorQuery(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.COLOR) && StringUtils.isNotBlank(queryParam.get(Constants.COLOR))  && !"\"\"".equals(queryParam.get(Constants.COLOR))) {
			String[] colors = queryParam.get(Constants.COLOR).replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_COLOR + Constants.STRING_KEY_WORD,
					Arrays.asList(colors)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setProductGroupType(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.GROUP_TYPE) && StringUtils.isNotBlank(queryParam.get(Constants.GROUP_TYPE)) && !"\"\"".equals(queryParam.get(Constants.GROUP_TYPE))) {
			String[] groupTypes = queryParam.get(Constants.GROUP_TYPE).replace("\"", "").split(",");
			qb.must(QueryBuilders.matchPhraseQuery(Constants.STRING_PRODUCT_GROUP_TYPE, groupTypes));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setSizeQuery(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.CAPACITY) && StringUtils.isNotBlank(queryParam.get(Constants.CAPACITY)) && !"\"\"".equals(queryParam.get(Constants.CAPACITY))) {
			String[] capa = queryParam.get(Constants.CAPACITY).replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.STRING_SIZE + Constants.STRING_KEY_WORD,
					Arrays.asList(capa)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setModelQuery(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.MODEL) && StringUtils.isNotBlank(queryParam.get(Constants.MODEL))) {
			String[] modelArray = queryParam.get(Constants.MODEL).replace("\"", "").split(",");
			qb.must(QueryBuilders.termQuery(Constants.PRODUCT_MODEL + Constants.STRING_KEY_WORD,
					Arrays.asList(modelArray)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param qb
	 */
	private static void setMakeQuery(Map<String, String> queryParam, BoolQueryBuilder qb) {
		if (queryParam.containsKey(Constants.MAKE) && StringUtils.isNotBlank(queryParam.get(Constants.MAKE))) {
			String[] makeArray = queryParam.get(Constants.MAKE).replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(Constants.PRODUCT_MAKE + Constants.STRING_KEY_WORD,
					Arrays.asList(makeArray)));
		}
	}
	/**
	 * 
	 * @param queryParam
	 * @param searchRequestBuilder
	 */
	private static void setPageNumberAndSize(Map<String, String> queryParam,
			SearchSourceBuilder searchRequestBuilder) {
		
		if(queryParam.containsKey(Constants.STRING_PAGENUMBER) && queryParam.get(Constants.STRING_PAGENUMBER) != null && Integer.valueOf(queryParam.get(Constants.STRING_PAGENUMBER)) > 0){
			searchRequestBuilder.from(Integer.valueOf(queryParam.get(Constants.STRING_PAGENUMBER)));
		}
		else{
		searchRequestBuilder.from(from);
		}
		if(queryParam.containsKey(Constants.STRING_PAGESIZE) && queryParam.get(Constants.STRING_PAGESIZE) != null &&Integer.valueOf(queryParam.get(Constants.STRING_PAGESIZE)) > 0){
			searchRequestBuilder.size(Integer.valueOf(queryParam.get(Constants.STRING_PAGESIZE)));
		}
		else{
			searchRequestBuilder.size(size);
		}
	}
}
