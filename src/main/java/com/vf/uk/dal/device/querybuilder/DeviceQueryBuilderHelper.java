package com.vf.uk.dal.device.querybuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Device Query Builder Helper
 *
 */
@Slf4j
public class DeviceQueryBuilderHelper {
	
	private DeviceQueryBuilderHelper(){
		
	}

	public static final String ELASTIC_SEARCH_INDEX_SIZE = "ELASTIC_SEARCH_INDEX_SIZE";
	public static final int DEFAULT_ELASTIC_SEARCH_INDEX_SIZE = 1000;
	public static final String ELASTIC_SEARCH_INDEX_START_FROM = "ELASTIC_SEARCH_INDEX_START_FROM";
	public static final int DEFAULT_ELASTIC_SEARCH_START_INDEX = 0;
	public static final String STRING_SOURCE = "_source";
	public static final String STRING_MAKE = "equipment.make";
	public static final String STRING_MODEL = "equipment.model";
	public static final String STRING_GROUP_TYPE = "groupType";
	public static final String STRING_GROUP_NAME = "name";
	public static final String STRING_ID = "id";
	public static final String SEARCH_FOR_VODAFONE5_INDEX = "_search";
	public static final String STRING_KEY_WORD = ".keyword";
	public static final String STRING_TAG = "tag";
	public static final String STRING_ALL_TYPE = "__type";
	public static final String STRING_RAW = "raw_";
	public static final String STRING_OPT = "opt_";
	public static final String NUMER_REG_EXP = "[0-9]{6}";
	public static final String STRING_TYPE = "type";
	public static final String STRING_PRODUCT_ID = "productId";
	public static final String STRING_BUNDLE_ID = "bundleId";
	public static final String STRING_OFFER_CODE = "offerCode";
	public static final String STRING_JOURNEY_TYPE = "journeyType";
	public static final String STRING_PRODUCT = "product";
	public static final String STRING_BUNDLE = "bundle";
	public static final String STRING_OFFER = "offer";
	public static final String STRING_PROMOTION = "promotion";
	public static final String STRING_COMPATIBLE_ACCESSORIES = "Compatible Accessories";
	public static final String STRING_EQUIPMENT_MAKE_COLON = "equipmentMake";
	public static final String STRING_COLOUR_COLON = "facetColour";
	public static final String STRING_CAPACITY_COLON = "capacity";
	public static final String STRING_OPERATING_SYSTEM = "operatingSystem";
	public static final String STRING_MUST_HAVE_FEATURES_WITH_COLON = "mustHaveFeatures";
	public static final String STRING_AND = " AND ";
	public static final String STRING_FACET_COLOUR = "FacetColour";
	public static final String STRING_FACET_CAPACITY = "Capacity";
	public static final String STRING_FACET_OPERATING_SYSYTEM = "OperatingSystem";
	public static final String STRING_MUST_HAVE_FEATURES = "MustHaveFeatures";
	public static final String STRING_COLOUR_FOR_FACET = "colour";
	public static final String STRING_PRODUCT_LINE = "productLine";
	public static final String STRING_PACKAGE_TYPE = "packageType";
	public static final String SORT_OPTION_DESC = "desc";
	public static final String SORT_OPTION_ASC = "asc";
	public static final String STRING_UPGRADE = "upgrade";
	public static final String STRING_UPGRADED_LEAD_DEVICE_ID = "upgradeLeadDeviceId";
	public static final String STRING_NON_UPGRADED_LEAD_DEVICE_ID = "nonUpgradeLeadDeviceId";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String STRING_SECOND_LINE = "secondline";
	private static int size = ConfigHelper.getInt(ELASTIC_SEARCH_INDEX_SIZE, DEFAULT_ELASTIC_SEARCH_INDEX_SIZE);
	private static int from = ConfigHelper.getInt(ELASTIC_SEARCH_INDEX_START_FROM, DEFAULT_ELASTIC_SEARCH_START_INDEX);
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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(STRING_MAKE, make));
			qb.must(QueryBuilders.matchPhraseQuery(STRING_MODEL, model));
			searchRequestBuilder.query(qb);
			log.info(" <-----  Setting up Elasticsearch parameters and query   ----->");
			searchRequest.source(searchRequestBuilder);
		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder  :::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			log.info(" <-----  Setting up Elasticsearch parameters and query ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder:::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(STRING_GROUP_NAME, groupName));
			qb.must(QueryBuilders.matchPhraseQuery(STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			log.info(" <------ Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder::::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param Id
	 * @param type
	 * @return
	 */
	public static SearchRequest searchQueryForCommercialProductAndCommercialBundle(String id, String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<------Elasticsearch query mapping----->");
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(STRING_ID + STRING_KEY_WORD, id));
			qb.must(QueryBuilders.termQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_RAW + type));
			searchRequestBuilder.query(qb);
			log.info(" <-----  Setting up Elasticsearch parameters and query ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder : ::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param idsOrNames
	 * @param type
	 * @return
	 */
	public static SearchRequest searchQueryForListOfCommercialProductAndCommercialBundle(List<String> idsOrNames,
			String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<------Elasticsearch query mapping------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(idsOrNames.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			if (idsOrNames.get(0).matches(NUMER_REG_EXP)) {
				qb.must(QueryBuilders.termsQuery(STRING_ID + STRING_KEY_WORD, idsOrNames));
			} else {
				qb.must(QueryBuilders.termsQuery(STRING_GROUP_NAME + STRING_KEY_WORD, idsOrNames));
			}
			qb.must(QueryBuilders.termQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_RAW + type).boost(2.0F))
					.boost(3.0F);
			searchRequestBuilder.query(qb);
			log.info(" <-----  Setting up Elasticsearch parameters and query  ------>");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error(":::::: Exception in using Elasticsearch QueryBuilder :::::: " + e);
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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<-----Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(listOfDeviceIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(STRING_GROUP_TYPE, STRING_COMPATIBLE_ACCESSORIES));
			qb.must(QueryBuilders.termsQuery(STRING_GROUP_NAME + STRING_KEY_WORD, listOfDeviceIds.toArray()));
			searchRequestBuilder.query(qb);
			log.info(" <-----   Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error(":::::: Exception in using Elasticsearch QueryBuilder :::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<-------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(STRING_GROUP_TYPE, groupType));
			searchRequestBuilder.query(qb);
			log.info(" <------  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::: Exception in using Elasticsearch QueryBuilder :::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(promotionAsTags.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(STRING_TAG + STRING_KEY_WORD, promotionAsTags.toArray()));
			qb.must(QueryBuilders.termQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_RAW + STRING_PROMOTION));
			searchRequestBuilder.query(qb);
			log.info(" <-------  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.matchPhraseQuery(STRING_TAG, promotionAsTag));
			qb.must(QueryBuilders.termQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_RAW + STRING_PROMOTION));
			searchRequestBuilder.query(qb);
			log.info(" <------  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error(":::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}

	/**
	 * 
	 * @param journeyType
	 * @param groupName
	 * @return
	 */
	public static SearchRequest searchQueryForMerchandisingPromotionModel(List<String> journeyType, String groupName) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<--------Elasticsearch query mapping------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(STRING_PRODUCT_LINE + STRING_KEY_WORD, groupName));// .operator(Operator.AND)
			qb.must(QueryBuilders.termsQuery(STRING_PACKAGE_TYPE + STRING_KEY_WORD, journeyType));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder ::::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<--------Elasticsearch query mapping------>");
			searchRequestBuilder.from(pageNumber);
			searchRequestBuilder.size(pageSize);
			if (StringUtils.isNotEmpty(sortOption) && sortOption.equalsIgnoreCase(SORT_OPTION_ASC)) {
				searchRequestBuilder.sort(sortBy.toLowerCase(), SortOrder.ASC);
			} else if (StringUtils.isNotEmpty(sortOption) && sortOption.equalsIgnoreCase(SORT_OPTION_DESC)) {
				searchRequestBuilder.sort(sortBy.toLowerCase(), SortOrder.DESC);
			}
			BoolQueryBuilder qb = getFilterCriteria(make, capacity, colour, operatingSystem, mustHaveFeatures);
			qb.must(QueryBuilders.termQuery(STRING_TYPE + STRING_KEY_WORD, groupType));

			if (STRING_UPGRADE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(STRING_UPGRADED_LEAD_DEVICE_ID, "*"));
			} else if (StringUtils.isBlank(journeyType) || JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
					|| STRING_SECOND_LINE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(STRING_NON_UPGRADED_LEAD_DEVICE_ID, "*"));
			}
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder ::::: " + e);

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
			qb.must(QueryBuilders.termsQuery(STRING_EQUIPMENT_MAKE_COLON + STRING_KEY_WORD, Arrays.asList(makeArray)));
		}
		if (capacity != null && !"\"\"".equals(capacity)) {
			String[] capa = capacity.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(STRING_CAPACITY_COLON + STRING_KEY_WORD, Arrays.asList(capa)));
		}
		if (colour != null && !"\"\"".equals(colour)) {
			String[] color = colour.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(STRING_COLOUR_COLON + STRING_KEY_WORD, Arrays.asList(color)));
		}
		if (operatingSystem != null && !"\"\"".equals(operatingSystem)) {
			String[] os = operatingSystem.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(STRING_OPERATING_SYSTEM + STRING_KEY_WORD, Arrays.asList(os)));
		}
		if (mustHaveFeatures != null && !"\"\"".equals(mustHaveFeatures)) {
			String[] mhf = mustHaveFeatures.replace("\"", "").split(",");
			qb.must(QueryBuilders.termsQuery(STRING_MUST_HAVE_FEATURES_WITH_COLON + STRING_KEY_WORD,
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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<-------Elasticsearch query mapping--------->");
			searchRequestBuilder.size(from);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(STRING_TYPE + STRING_KEY_WORD, groupType));
			if (STRING_UPGRADE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(STRING_UPGRADED_LEAD_DEVICE_ID, "*"));
			} else if (StringUtils.isBlank(journeyType) || JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
					|| STRING_SECOND_LINE.equalsIgnoreCase(journeyType)) {
				qb.must(QueryBuilders.wildcardQuery(STRING_NON_UPGRADED_LEAD_DEVICE_ID, "*"));
			}
			TermsAggregationBuilder make = AggregationBuilders.terms(STRING_EQUIPMENT_MAKE_COLON)
					.field(STRING_EQUIPMENT_MAKE_COLON + STRING_KEY_WORD);
			TermsAggregationBuilder capacity = AggregationBuilders.terms(STRING_CAPACITY_COLON)
					.field(STRING_CAPACITY_COLON + STRING_KEY_WORD);
			TermsAggregationBuilder facetColour = AggregationBuilders.terms(STRING_COLOUR_COLON)
					.field(STRING_COLOUR_COLON + STRING_KEY_WORD);
			TermsAggregationBuilder operatingSystem = AggregationBuilders.terms(STRING_OPERATING_SYSTEM)
					.field(STRING_OPERATING_SYSTEM + STRING_KEY_WORD);
			TermsAggregationBuilder mustHaveFeatures = AggregationBuilders.terms(STRING_MUST_HAVE_FEATURES_WITH_COLON)
					.field(STRING_MUST_HAVE_FEATURES_WITH_COLON + STRING_KEY_WORD);
			TermsAggregationBuilder colour = AggregationBuilders.terms(STRING_COLOUR_FOR_FACET)
					.field(STRING_COLOUR_FOR_FACET + STRING_KEY_WORD);
			searchRequestBuilder.aggregation(make);
			searchRequestBuilder.aggregation(capacity);
			searchRequestBuilder.aggregation(facetColour);
			searchRequestBuilder.aggregation(operatingSystem);
			searchRequestBuilder.aggregation(mustHaveFeatures);
			searchRequestBuilder.aggregation(colour);
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder :::::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<--------Elasticsearch query mapping-------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(deviceIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(STRING_PRODUCT_ID + STRING_KEY_WORD, deviceIds));
			qb.must(QueryBuilders.termsQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_OPT + STRING_PRODUCT));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::::Exception in using Elasticsearch QueryBuilder :::::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<-----Elasticsearch query mapping----->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(bundleIds.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(STRING_BUNDLE_ID + STRING_KEY_WORD, bundleIds));
			qb.must(QueryBuilders.termsQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_OPT + STRING_BUNDLE));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error(":::::: Exception in using Elasticsearch QueryBuilder  :::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<-------Elasticsearch query mapping------->");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(size);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(STRING_PRODUCT_ID + STRING_KEY_WORD, deviceIds));
			qb.must(QueryBuilders.termQuery(STRING_OFFER_CODE + STRING_KEY_WORD, offerCode));
			qb.must(QueryBuilders.termQuery(STRING_JOURNEY_TYPE + STRING_KEY_WORD, journeyType));
			qb.must(QueryBuilders.wildcardQuery(STRING_ID, STRING_OFFER + "*"));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error(":::::: Exception in using Elasticsearch QueryBuilder  :::::: " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			log.info("<--------Elasticsearch query mapping------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(displayNames.size());
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(STRING_TYPE + STRING_KEY_WORD, groupType));
			qb.must(QueryBuilders.termsQuery(STRING_GROUP_NAME + STRING_KEY_WORD, displayNames));
			searchRequestBuilder.query(qb);
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::::Exception in using Elasticsearch QueryBuilder ::::::  " + e);

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
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {

			log.info("<------Elasticsearch query mapping ForCommercial Bundle------>");
			searchRequestBuilder.from(from);
			searchRequestBuilder.size(idsOrNames.size());

			searchRequestBuilder.fetchSource(includes, ex);
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termsQuery(STRING_ID + STRING_KEY_WORD, idsOrNames));
			qb.must(QueryBuilders.termQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_RAW + type).boost(2.0F))
					.boost(3.0F);
			searchRequestBuilder.query(qb);
			log.info(" <-----  Setting up Elasticsearch parameters and query For Commercial Bundle ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder For Commercial Bundle :::::: " + e);
		}
		return searchRequest;
	}

	/**
	 * 
	 * @param Id
	 * @param type
	 * @return SearchRequest
	 */
	public static SearchRequest searchQueryForCommercialBundle(String id, String type) {
		SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());

		searchRequestBuilder.fetchSource(includes, ex);
		try {
			log.info("<------Elasticsearch query mapping------>");
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			qb.must(QueryBuilders.termQuery(STRING_ID + STRING_KEY_WORD, id));
			qb.must(QueryBuilders.termQuery(STRING_ALL_TYPE + STRING_KEY_WORD, STRING_RAW + type));
			searchRequestBuilder.query(qb);
			log.info(" <-----  Setting up Elasticsearch parameters and query  ----->");
			searchRequest.source(searchRequestBuilder);

		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);

		}
		return searchRequest;
	}
	public static SearchRequest searchQueryForPriceForBundleAndHardware(List<String> compatibleIds) {
		SearchRequest searchRequest = new SearchRequest(CatalogServiceAspect.CATALOG_VERSION.get());
		try {
			IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery();
			queryBuilder.ids().addAll(compatibleIds);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.from(0);
			sourceBuilder.size(compatibleIds.size());
			sourceBuilder.query(queryBuilder);
			searchRequest.source(sourceBuilder);
		} catch (Exception e) {
			log.error("::::::Exception in using Elasticsearch QueryBuilder :::::: " + e);
		}
		return searchRequest;

	}
	
}
