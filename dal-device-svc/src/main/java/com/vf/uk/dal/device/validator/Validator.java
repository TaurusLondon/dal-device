package com.vf.uk.dal.device.validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.exception.SystemException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ExceptionMessages;

public class Validator {
	private Validator() {
	};

	/**
	 * 
	 * @param deviceId
	 */
	public static void validateDeviceId(String deviceId) {
		if (StringUtils.isBlank(deviceId)) {
			LogHelper.error(Validator.class, "DeviceId is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		} else if (Validator.validateId(deviceId)) {
			LogHelper.error(Validator.class, "DeviceId is Invalid");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
		}
	}

	/**
	 * 
	 * @param productId
	 * @return
	 */
	public static boolean validateId(String productId) {
		return StringUtils.isNotBlank(productId) && (!productId.matches("[0-9]{6}") || productId.matches("[0]*"));
	}

	/**
	 * 
	 * @param queryParams
	 * @return
	 */
	public static boolean validateGetListOfDeviceTile(Map<String, String> queryParams) {
		List<String> validParams = Arrays.asList("make", "model", Constants.GROUP_TYPE, "creditLimit", Constants.JOURNEY_TYPE,
				"deviceId", Constants.OFFER_CODE, "bundleId", "sort", "pageSize", "pageNumber");

		return validateParams(queryParams, validParams);
	}

	/**
	 * 
	 * @param queryParams
	 * @return
	 */
	public static boolean validateDeviceId(Map<String, String> queryParams) {
		List<String> validParams = Arrays.asList("deviceId", "offerCode", "journeyType");

		return validateParams(queryParams, validParams);
	}

	/**
	 * 
	 * @param queryParams
	 * @return
	 */
	public static boolean validateGetDeviceList(Map<String, String> queryParams) {
		List<String> validParams = Arrays.asList("groupType", "productClass", "creditLimit", "make", "model",
				"capacity", "colour", "operatingSystem", "journeyType", "offerCode", "sort", "mustHaveFeatures",
				"pageSize", "pageNumber", "msisdn", "includeRecommendations");

		return validateParams(queryParams, validParams);
	}

	/**
	 * 
	 * @param queryParams
	 * @param validParams
	 * @return
	 */
	private static boolean validateParams(Map<String, String> queryParams, List<String> validParams) {
		for (String key : queryParams.keySet()) {
			boolean found = false;
			for (String param : validParams) {
				if (param.equals(key)) {
					found = true;
					break;
				}
			}
			if (!found)
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public static boolean validateJourneyType(String journeyType) {
		List<String> journeyList = Arrays.asList("acquisition", "upgrade", "secondline");
		return journeyList.contains(journeyType.toLowerCase()) ? true : false;
	}

	/**
	 * 
	 * @param sortCriteria
	 * @return
	 */
	public static boolean validateSortCriteria(String sortCriteria) {
		List<String> sortCriteriaList = Arrays.asList("Rating", "Priority", "EquipmentMake", "ReccuringCharge");
		return sortCriteriaList.contains(sortCriteria) ? true : false;
	}

	/**
	 * @author suranjit_kashyap
	 * @Sprint 6.6 Validator Start
	 */
	public static void validatePageSize(int pageSize, int pageNumber) {
		if (pageSize < 0) {
			throw new SystemException(ExceptionMessages.PAGESIZE_NEGATIVE_ERROR);
		}
		if (pageNumber < 0) {
			throw new SystemException(ExceptionMessages.PAGENUMBER_NEGATIVE_ERROR);
		}
	}

	/**
	 * 
	 * @param showRecommendations
	 */
	public static void validateIncludeRecommendation(String showRecommendations) {
		if (!showRecommendations.equalsIgnoreCase(Constants.STRING_TRUE)
				&& !showRecommendations.equalsIgnoreCase(Constants.STRING_FALSE)) {
			throw new ApplicationException(ExceptionMessages.INVALID_INCLUDERECOMMENDATION);
		}
	}

	/**
	 * 
	 * @param msisdn
	 * @param includeRecommendations
	 */
	public static void validateMSISDN(String msisdn, String includeRecommendations) {
		if (!msisdn.matches("[0-9]{10}") && Constants.STRING_TRUE.equalsIgnoreCase(includeRecommendations)) {
			throw new ApplicationException(ExceptionMessages.INVALID_MSISDN);
		}
	}

	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public static boolean validateGroupType(String groupType) {
		boolean result = !groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG);
		return result && !groupType.equalsIgnoreCase(Constants.STRING_DATA_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(Constants.STRING_DATA_DEVICE_PAYG) ? false : true;
	}

	/**
	 * 
	 * @param creditLimit
	 * @return
	 */
	/*
	 * public static boolean validateCreditLimit(String creditLimit) { int
	 * creditLimitParam = Integer.parseInt(creditLimit); return creditLimitParam
	 * < 0 ? false : true; }
	 */

	/**
	 * 
	 * @param creditLimit
	 * @return
	 */
	public static boolean validateCreditLimitValue(String creditLimit) {
		return creditLimit.matches("[0-9]") ? true : false;
	}

	/**
	 * @author manoj.bera
	 * @param queryParams
	 * @return
	 */
	public static boolean validateProduct(Map<String, String> queryParams) {
		List<String> validParams = Arrays.asList("productId", "productName");
		return validateParams(queryParams, validParams);
	}

	/**
	 * @author manoj.bera
	 * @param queryParams
	 * @return
	 */
	public static boolean validateProductGroup(Map<String, String> queryParams) {
		List<String> validParams = Arrays.asList("groupType");

		return validateParams(queryParams, validParams);
	}

	/**
	 * 
	 * @param creditLimit
	 * @return
	 */
	public static Float validateForCreditLimit(String creditLimit) {
		Float creditLimitparam = null;
		if (StringUtils.isNotBlank(creditLimit)) {
			if (!creditLimit.matches(Constants.creditLimitExp)) {
				throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
			}
			/*
			 * if (!Validator.validateCreditLimit(creditLimit)) { throw new
			 * ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT); }
			 */else {
				try {
					creditLimitparam = Float.parseFloat(creditLimit);
				} catch (NumberFormatException ex) {
					LogHelper.error(Validator.class, "Credit limit value not correct " + ex);
					throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
				}
			}
		} else if (StringUtils.isBlank(creditLimit)) {
			throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
		}
		return creditLimitparam;
	}

	/**
	 * 
	 * @param sortCriteria
	 * @param sortCriteriaLocal
	 * @param groupType
	 * @param productClass
	 */
	public static void validateForDeviceList(String sortCriteria, String sortCriteriaLocal, String groupType,
			String productClass) {
		if (sortCriteria == null || sortCriteria.isEmpty()) {
			LogHelper.error(Validator.class, "sortCriteria is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_SORT);
		}
		if (StringUtils.isNotBlank(sortCriteriaLocal) && !Validator.validateSortCriteria(sortCriteriaLocal)) {
			LogHelper.error(Validator.class, "Received sortCriteria is invalid.");
			throw new ApplicationException(ExceptionMessages.RECEVIED_INVALID_SORTCRITERIA);
		}

		if (groupType == null || groupType.isEmpty()) {
			LogHelper.error(Validator.class, "Group Type is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
		}
		if (productClass == null || productClass.isEmpty()) {
			LogHelper.error(Validator.class, "productClass is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_PRODUCT_CLASS);
		}
		if (!productClass.equalsIgnoreCase(Constants.STRING_HANDSET)) {
			LogHelper.error(Validator.class, "Invalid Product class");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_PRODUCT_CLASS);
		}
		if (!groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			LogHelper.error(Validator.class, "Invalid Group Type");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
		}

	}

	/**
	 * 
	 * @param journeytype
	 * @param offerCode
	 */
	public static void validateForPAYG(String journeytype, String offerCode) {
		if (StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE, journeytype)
				|| StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE, journeytype)) {
			LogHelper.error(Validator.class, "JourneyType is not compatible for given GroupType");
			throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_JOURNEY_TYPE);
		} else if (StringUtils.isNotBlank(offerCode)) {
			LogHelper.error(Validator.class, "offerCode is not compatible for given GroupType");
			throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_OFFER_CODE);
		}
	}

	/**
	 * 
	 * @param make
	 * @param model
	 * @param groupType
	 * @param journeyType
	 * @return
	 */
	public static String validateAllParameters(String make, String model, String groupType, String journeyType) {
		String journeyTypeLocal = null;
		if (make == null || make.isEmpty()) {
			LogHelper.error(Validator.class, "make is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE);
		}
		if (model == null || model.isEmpty()) {
			LogHelper.error(Validator.class, "model is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MODEL);
		}

		if (groupType == null || groupType.isEmpty()) {
			LogHelper.error(Validator.class, " Group Type is null ");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
		} else if (!Validator.validateGroupType(groupType)) {
			LogHelper.error(Validator.class, "Invalid Group Type");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG) && (StringUtils.isNotBlank(journeyType)
				&& (journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE)
						|| journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE)))) {
			LogHelper.error(Validator.class, "JourneyType is Not Compatible with given GroupType");
			throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_JOURNEY_TYPE);
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
			journeyTypeLocal = journeyType;
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			journeyTypeLocal = Constants.JOURNEY_TYPE_ACQUISITION;
		}
		return journeyTypeLocal;
	}

	/**
	 * @param make
	 * @param model
	 * @param bundleId
	 * @param deviceId
	 * @param creditLimit
	 * @param creditLimitParam
	 * @return creditLimitParam
	 */
	public static Double validateCreditLimitAndIds(String make, String model, String bundleId, String deviceId,
			String creditLimit) {
		Double creditLimitParam;
		if (deviceId != null && !deviceId.matches(Constants.numberExp)) {
			LogHelper.error(Validator.class, ExceptionMessages.INVALID_DEVICE);
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
		}
		creditLimitParam = validateCreditValue(creditLimit);
		if ((StringUtils.isBlank(make) || "\"\"".equals(make))
				&& (StringUtils.isBlank(model) || "\"\"".equals(model))) {
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE_MODEL);
		} else if (StringUtils.isBlank(make) || "\"\"".equals(make)) {
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE);
		} else if (StringUtils.isBlank(model) || "\"\"".equals(model)) {
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MODEL);
		}

		if (bundleId != null && (!bundleId.matches(Constants.numberExp) || bundleId.matches("[0]*"))) {
			LogHelper.error(Validator.class, ExceptionMessages.INVALID_BUNDLE);
			throw new ApplicationException(ExceptionMessages.INVALID_BUNDLE_ID);
		}
		return creditLimitParam;
	}
	/**
	 * 
	 * @param creditLimit
	 * @return
	 */
	private static Double validateCreditValue(String creditLimit) {
		Double creditLimitParam = null;
		if (creditLimit != null) {
			Float creditValue = validateForCreditLimit(creditLimit);
			if(creditValue != null){
			creditLimitParam = Double.valueOf(creditValue.toString());
			}
		}
		return creditLimitParam;
	}

	/**
	 * 
	 * @param deviceId
	 */
	public static void validateAccessoryFields(String deviceId) {
		if (StringUtils.isNotBlank(deviceId)) {
			if (!deviceId.matches(Constants.numberExp)) {
				LogHelper.error(Validator.class, ExceptionMessages.INVALID_DEVICE);
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}

		} else {
			LogHelper.error(Validator.class, Constants.DEVICE_ID_IS_EMPTY);
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
	}

	/**
	 * 
	 * @param deviceId
	 */
	public static void validateInsuranceDetails(String deviceId) {
		if (StringUtils.isNotBlank(deviceId)) {
			if (!deviceId.matches(Constants.numberExp)) {
				LogHelper.error(Validator.class, ExceptionMessages.INVALID_DEVICE);
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
		} else {
			LogHelper.error(Validator.class, Constants.DEVICE_ID_IS_EMPTY);
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 */
	public static void validateDeviceDetails(String deviceId) {
		if (StringUtils.isNotBlank(deviceId)) {
			if (!deviceId.matches(Constants.numberExp)) {
				LogHelper.error(Validator.class, ExceptionMessages.INVALID_DEVICE);
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
		} else {
			LogHelper.error(Validator.class, Constants.DEVICE_ID_IS_EMPTY);
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
	}

	/**
	 * 
	 * @param offerCode
	 * @param journeyType
	 */
	public static void getJourneyAndOfferCodeValidationForPAYG(String offerCode, String journeyType) {
		if (StringUtils.isNotBlank(journeyType) && (journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE)
				|| journeyType.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE))) {
			LogHelper.error(Validator.class, "JourneyType is not compatible for given DeviceId");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICEID_JOURNEY_TYPE);
		} else if (StringUtils.isNotBlank(offerCode)) {
			LogHelper.error(Validator.class, "offerCode is not compatible for given DeviceId");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICEID_OFFER_CODE);
		}
	}
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param make
	 * @param model
	 * @param groupType
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @param color
	 * @param operatingSystem
	 * @param capacity
	 * @param mustHaveFeatures
	 * @return
	 */ 
	public static boolean validateNullValuesFOrHandsetOnlineModel(String deviceId, String journeyType,
			String make, String model, String groupType, String sort, Integer pageNumber, Integer pageSize,
			String color, String operatingSystem, String capacity, String mustHaveFeatures) {
		boolean flag =false;
		if(validateMakeModelDeviceIDNull(make,model,deviceId)
		&& validateJourneyTypeGroupType(journeyType,groupType)
		&& validateSortPageNumberSizeOSCOlorCapacity(sort,pageNumber,pageSize,color,operatingSystem,capacity,mustHaveFeatures)){
			flag= true;
		}
		return flag;
	}
	/**
	 * 
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @param color
	 * @param operatingSystem
	 * @param capacity
	 * @param mustHaveFeatures
	 * @return
	 */
	private static boolean validateSortPageNumberSizeOSCOlorCapacity(String sort, Integer pageNumber, Integer pageSize, String color, String operatingSystem, String capacity, String mustHaveFeatures) {
		boolean flag = false;
		if(validateSortPageNumberPageSize(sort,pageNumber,pageSize,color,operatingSystem)
		&& StringUtils.isBlank(capacity)&& StringUtils.isBlank(mustHaveFeatures)){
			flag = true;
		}
		return flag; 
	}
	/**
	 * 
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @param color
	 * @param operatingSystem
	 * @return
	 */
	private static boolean validateSortPageNumberPageSize(String sort, Integer pageNumber, Integer pageSize, String color, String operatingSystem) {
		boolean flag = false;
		if(validatePageSortNumber(sort , pageNumber, pageSize) && StringUtils.isBlank(color)&& StringUtils.isBlank(operatingSystem)){
			flag=true;
		}
		return flag;
	}
	/**
	 * 
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	private static boolean validatePageSortNumber(String sort, Integer pageNumber, Integer pageSize) {
		return StringUtils.isBlank(sort)&& 
				(pageNumber ==null)&& (pageSize ==null);
	}
	/**
	 * 
	 * @param journeyType
	 * @param groupType
	 * @return
	 */
	private static boolean validateJourneyTypeGroupType(String journeyType, String groupType) {
		return StringUtils.isBlank(journeyType) && StringUtils.isBlank(groupType);
	}
	/**
	 * 
	 * @param make
	 * @param model
	 * @param deviceId
	 * @return
	 */
	private static boolean validateMakeModelDeviceIDNull(String make, String model, String deviceId) {
		
		return  StringUtils.isBlank(make) && StringUtils.isBlank(model) && StringUtils.isBlank(deviceId);
	}
}
