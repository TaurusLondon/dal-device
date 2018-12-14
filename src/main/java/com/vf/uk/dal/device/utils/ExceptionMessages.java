package com.vf.uk.dal.device.utils;

/**
 * Exception Messages.
 * 
 **/
public class ExceptionMessages {
	public static final String INVALID_INPUT_MISSING_MAKE_MODEL = "Invalid input request received. Missing make and model in the filter criteria";
	public static final String NULL_VALUE_GROUP_NAME = "Received Null Values for the given product group name";
	public static final String NULL_VALUE_GROUP_TYPE = "Received Null Values for the given product group type";
	public static final String NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID = "Received Null Values for the given device id";
	public static final String INVALID_INPUT_MISSING_GROUPNAME = "Invalid input request received. Missing groupName in the filter criteria";
	public static final String INVALID_INPUT_MISSING_DEVICEID = "Invalid input request received. Missing Device Id";
	public static final String INVALID_INPUT_MISSING_GROUPTYPE = "Invalid input request received. Missing groupType in the filter criteria";
	public static final String INVALID_INPUT_MISSING_PRODUCT_CLASS = "Invalid input request received. Missing Product Class in the filter criteria";
	public static final String INVALID_INPUT_PRODUCT_CLASS = "Invalid Product Class sent in the request";
	public static final String INVALID_INPUT_GROUP_TYPE = "Invalid Group Type sent in the request";
	public static final String SOLR_CONNECTION_CLOSE_EXCEPTION = "Exception occured while closing solr connection";
	public static final String SOLR_CONNECTION_EXCEPTION = "Connection refused from Solr";
	public static final String INVALID_INPUT_MISSING_MAKE = "Invalid input request received. Missing make in the filter criteria";
	public static final String INVALID_INPUT_MISSING_SORT = "Invalid input request received. Missing Sort in the filter criteria";
	public static final String INVALID_INPUT_MISSING_MODEL = "Invalid input request received. Missing model in the filter criteria";
	public static final String NULL_VALUE_FOR_MAKE_AND_MODEL = "Received Null Values for the given make and model";
	public static final String NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID = "No Compatible Accessories found for given device Id";
	public static final String NO_DATA_FOUND_FOR_MAKE = "No Data Found for the Given Make";
	public static final String NO_DATA_FOUND_FOR_GIVEN_SEARCH_CRITERIA_FOR_DEVICELIST = "No Devices Found for the given input search criteria";
	public static final String NULL_VALUE_FROM_COHERENCE_FOR_INSURANCE_PRODUCTLINE_ID = "No Product Compatability found for given inusrance product line";
	public static final String NULL_VALUE_FROM_COHERENCE_FOR_INSURANCE_PRODUCT_COMPATABILITY = "No Related product Id's found for the Product compatability";
	public static final String NO_DATA_FOUND_FOR_GIVEN_PRODUCT_LIST = "No Data Found for the given product list";
	public static final String NO_PRODUCT_LINE_FOUND_FOR_GIVEN_DEVICE_ID = "No Product Line found for given Device Id";
	public static final String NO_INSURANCE_PRODUCT_FOR_GIVEN_COMPATABILITY = "No Insurance Product Line found for given compatibility";
	public static final String MAKE_AND_MODEL_NOT_FOUND_IN_GROUPTYPE = "Requested Make and Model Not found in given group type";
	public static final String NULL_OR_EMPTY_GROUP_TYPE = "Group Type is null or Empty String.";
	public static final String NULL_VALUES_FOR_COMPATIBLE_BUNDLES = "No Compatible Bundles found for given device Id";
	public static final String NULL_VALUES_FOR_STOCK_AVAILABILITY = "Received null value from stock availability API.";
	public static final String NO_PRODUCT_RATINGS_FOUND_FOR_GIVEN_DEVICE_ID = "No Product Ratings Found for given device id";
	public static final String INVALID_JOURNEY_ID = "Invalid Journey Id sent in the request";
	public static final String INVALID_GRPL_SERVICE_CALL = "Exception occured while connecting to GRPL API";

	public static final String INVALID_REQUEST_PARAMETER = "Invalid input request received";
	public static final String STRING_NO_SIMILAR_PLAN = "No Similar Plan Found For Given Device Id";
	public static final String INVALID_INPUT_MISSING_BUNDLEID = "Invalid input request received. Missing Bundle Id";
	public static final String INVALID_INPUT_MISSING_ALLOWED_RECURRING_PRICE_LIMIT = "Invalid input request received. Missing AllowedRecurringPriceLimit";
	public static final String INVALID_INPUT_MISSING_PLANS_LIMIT = "Invalid input request received. Missing Plans Limit";
	public static final String NULL_COMPATIBLE_PLANS_FOR_DEVICE_ID = "No Compatible Plans List found for given device Id";
	public static final String INVALID_BUNDLE_ID = "Invalid Bundle Id Sent In Request";
	public static final String INVALID_QUERY_PARAMS = "Invalid query parameters";

	public static final String INVALID_DEVICE_ID = "Invalid Device Id Sent In Request";
	public static final String INVALID_ALLOWED_RECURRING_PRICE_LIMIT = "Invalid AllowedRecurringPriceLimit Sent In Request";
	public static final String INVALID_PLANS_LIMIT = "Invalid Plans Limit Sent In Request";
	public static final String NO_REVIEWS_FOUND = "No reviews found for the given deviceId";

	public static final String ERROR_STRING_TO_JSONOBJECT = "Invalid data received";
	public static final String SOLR_INDEXING_ERROR = "Not Able To Indexing To Solr, IndexManager Throwing Exception";
	public static final String NO_DEVICE_PRE_CALCULATED_DATA = "No Device Pre Calculated Data found To Store";
	public static final String NO_LEAD_MEMBER_ID_COMING_FROM_SOLR = "Empty Lead DeviceId List Coming From Solr";
	public static final String NO_LEAD_PLAN_ID_COMING_FROM_SOLR = "Empty Lead Plan List Coming From Solr";

	public static final String INVALID_CREDIT_LIMIT = "Please enter valid credit limit.";
	public static final String NO_CONTEXT_NAME = "Please enter valid context name.";

	public static final String INVALID_COHERENCE_DATA = "Invalid Data Coming From Coherence";

	public static final String INVALID_JOB_ID = "Invalid Job Id";

	public static final String INVALID_INPUT_MSISDN = "Invalid input parameters, MSISDN is mandatory when recommendations requested.";

	public static final String INVALID_JOURNEY_TYPE_AND_OFFER_CODE_COMBINATION = "OfferCode is not compatible with JourneyType";
	public static final String REQUIRED_JOURNEY_TYPE = "Required JourneyType with Offercode.";
	public static final String INVALID_JOURNEY_TYPE = "Received invalid journeyType in the request.";

	public static final String PRICING_API_EXCEPTION = "Pricing API throwing Exception While calling";
	public static final String BAZARVOICE_SERVICE_EXCEPTION = "Exception Occured While calling BazarVoiceRepo"; // update
																												// script

	// Sprint 6.3
	public static final String NULL_VALUES_FROM_PRICING_API = "Null value received from Pricing API";
	public static final String NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID = "No Compatible Insurances found for given device Id";
	public static final String NO_DATA_FOR_GIVEN_SEARCH_CRITERIA = "No details found for given criteria";

	public static final String BUNDLECOMPATIBLELIST_API_EXCEPTION = "Error while calling BundleCompatibleList API";
	public static final String COUPLEBUNDLELIST_API_EXCEPTION = "Error while calling CoupleBundleList API";

	/**
	 * Sprint 6.5 Start
	 */
	public static final String ERROR_IN_FUTURE_TASK = "Exception occured while executing thread pool";
	/**
	 * Sprint 6.5 End
	 */

	public static final String RECEVIED_INVALID_SORTCRITERIA = "Received sortCriteria is invalid.";
	public static final String DEVICE_ID_NOT_HANDSET = "Given DeviceId is not ProductClass Handset";
	/*
	 * Sprint 6.6 Start
	 */
	public static final String NULL_VALUE_FROM_SOLR = "No data Received from Solr";
	/*
	 * Sprint 6.6 End
	 */

	/**
	 * Sprint 6.6
	 * 
	 * @author suranjit.kashyap Start
	 */
	public static final String PROMOTION_API_EXCEPTION = "Promotion API throwing exception while calling";
	public static final String BAZAARVOICE_RESPONSE_EXCEPTION = "Unable to get expected BazaarVoice API response from Coherence";
	public static final String INVALID_INCLUDERECOMMENDATION = "Invalid include recommendation value passed";
	public static final String INVALID_MSISDN = "Invalid MSISDN passed in the request";
	public static final String PAGESIZE_NEGATIVE_ERROR = "Page Size Value cannot be negative";
	public static final String PAGENUMBER_NEGATIVE_ERROR = "Page Number Value cannot be negative";

	public static final String INVALID_DEVICE = "DeviceId is Invalid";
	public static final String INVALID_BUNDLE = "BundleId is Invalid";

	/**
	 * Spring 6.6 End
	 */
	public static final String INVALID_GROUP_TYPE_JOURNEY_TYPE = "JourneyType is not compatible for given GroupType";
	public static final String INVALID_GROUP_TYPE_OFFER_CODE = "offerCode is not compatible for given GroupType";

	/**
	 * Sprint-7.2 Start
	 * 
	 * @author krishna.reddy
	 */
	public static final String INVALID_DEVICEID_JOURNEY_TYPE = "JourneyType is not compatible for given DeviceId";
	public static final String INVALID_DEVICEID_OFFER_CODE = "offerCode is not compatible for given DeviceId";
	public static final String INDEX_NOT_FOUND_EXCEPTION = "index_not_found_exception";
}
