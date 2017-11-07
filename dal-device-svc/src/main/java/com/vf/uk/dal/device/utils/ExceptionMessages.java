package com.vf.uk.dal.device.utils;
/**
 * Exception Messages.
 * 
 * **/
public class ExceptionMessages 
{
	public static final String INVALID_INPUT_MISSING_MAKE_MODEL = "Invalid input request received. Missing make and model in the filter criteria";
	public static final String NULL_VALUE_GROUP_NAME = "Receieved Null Values for the given product group name";
	public static final String NULL_VALUE_GROUP_TYPE = "Receieved Null Values for the given product group type";
	public static final String NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID = "Receieved Null Values for the given device id";
	public static final String INVALID_INPUT_MISSING_GROUPNAME = "Invalid input request received. Missing groupName in the filter criteria";
	public static final String INVALID_INPUT_MISSING_DEVICEID = "Invalid input request received. Missing Device Id";
	public static final String INVALID_INPUT_MISSING_GROUPTYPE = "Invalid input request received. Missing groupType in the filter criteria";
	public static final String INVALID_INPUT_MISSING_PRODUCT_CLASS = "Invalid input request received. Missing Product Class in the filter criteria";
	public static final String INVALID_INPUT_PRODUCT_CLASS = "Invalid Product Class sent in the request";
	public static final String INVALID_INPUT_GROUP_TYPE = "Invalid Group Type sent in the request";
	public static final String SOLR_CONNECTION_CLOSE_EXCEPTION ="Exception occured while closing solr connection";
	public static final String SOLR_CONNECTION_EXCEPTION ="Connection refused from Solr";
	public static final String INVALID_INPUT_MISSING_MAKE = "Invalid input request received. Missing make in the filter criteria";
	public static final String INVALID_INPUT_MISSING_SORT = "Invalid input request received. Missing Sort in the filter criteria";
	public static final String INVALID_INPUT_MISSING_MODEL = "Invalid input request received. Missing model in the filter criteria";
	public static final String NULL_VALUE_FOR_MAKE_AND_MODEL = "Receieved Null Values for the given make and model";
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
	
	public static final String ERROR_STRING_TO_JSONOBJECT="Invalid data received";
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
	public static final String BAZARVOICE_SERVICE_EXCEPTION = "Exception Occured While calling BazarVoice";
}

