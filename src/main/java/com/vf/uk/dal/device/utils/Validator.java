package com.vf.uk.dal.device.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validator {
	
	private Validator() {
	};
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";
	public static final String DEVICE_ID = "deviceId";
	public static final String GROUP_TYPE = "groupType";
	public static final String JOURNEY_TYPE = "journeyType";
	public static final String OFFER_CODE = "offerCode";
	public static final String STRING_TRUE = "true";
	public static final String STRING_FALSE = "false";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String STRING_DATA_DEVICE_PAYM = "DATA_DEVICE_PAYM";
	public static final String STRING_DATA_DEVICE_PAYG = "DATA_DEVICE_PAYG";
	public static final String numberExp = "[0-9]{6}";
	public static final String creditLimitExp = "[0-9]*";
	public static final String zero = "0";
	public static final String STRING_HANDSET = "Handset";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String DEVICE_ID_IS_EMPTY = "Device Id is Empty";
	
	/**
	 * 
	 * @param deviceId
	 */
	public static void validateDeviceId(String deviceId) {
		if (StringUtils.isBlank(deviceId)) {
			log.error( "DeviceId is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		} else if (Validator.validateId(deviceId)) {
			log.error( "DeviceId is Invalid");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
		}
	}

	/**
	 * 
	 * @param productId
	 * @return
	 */
	public static boolean validateId(String productId) {
		return !productId.matches("[0-9]{6}") || productId.matches("[0]*");
	}

	/**
	 * 
	 * @param queryParams
	 * @return
	 */
	public static boolean validateGetListOfDeviceTile(Map<String, String> queryParams) {
		List<String> validParams = Arrays.asList("make", "model", GROUP_TYPE, "creditLimit", JOURNEY_TYPE,
				"deviceId", OFFER_CODE, "bundleId", "sort", "pageSize", "pageNumber");

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
		if (!showRecommendations.equalsIgnoreCase(STRING_TRUE)
				&& !showRecommendations.equalsIgnoreCase(STRING_FALSE)) {
			throw new ApplicationException(ExceptionMessages.INVALID_INCLUDERECOMMENDATION);
		}
	}

	/**
	 * 
	 * @param msisdn
	 * @param includeRecommendations
	 */
	public static void validateMSISDN(String msisdn, String includeRecommendations) {
		if (!msisdn.matches("[0-9]{10}") && STRING_TRUE.equalsIgnoreCase(includeRecommendations)) {
			throw new ApplicationException(ExceptionMessages.INVALID_MSISDN);
		}
	}

	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public static boolean validateGroupType(String groupType) {
		boolean result = !groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(STRING_DEVICE_PAYG);
		return result && !groupType.equalsIgnoreCase(STRING_DATA_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(STRING_DATA_DEVICE_PAYG) ? false : true;
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
			if (!creditLimit.matches(creditLimitExp)) {
				throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
			}
			/*
			 * if (!Validator.validateCreditLimit(creditLimit)) { throw new
			 * ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT); }
			 */else {
				try {
					creditLimitparam = Float.parseFloat(creditLimit);
				} catch (NumberFormatException ex) {
					log.error( "Credit limit value not correct " + ex);
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
			log.error( "sortCriteria is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_SORT);
		}
		if (StringUtils.isNotBlank(sortCriteriaLocal) && !Validator.validateSortCriteria(sortCriteriaLocal)) {
			log.error( "Received sortCriteria is invalid.");
			throw new ApplicationException(ExceptionMessages.RECEVIED_INVALID_SORTCRITERIA);
		}

		if (groupType == null || groupType.isEmpty()) {
			log.error( "Group Type is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
		}
		if (productClass == null || productClass.isEmpty()) {
			log.error( "productClass is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_PRODUCT_CLASS);
		}
		if (!productClass.equalsIgnoreCase(STRING_HANDSET)) {
			log.error( "Invalid Product class");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_PRODUCT_CLASS);
		}
		if (!groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)) {
			log.error( "Invalid Group Type");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
		}

	}

	/**
	 * 
	 * @param journeytype
	 * @param offerCode
	 */
	public static void validateForPAYG(String journeytype, String offerCode) {
		if (StringUtils.equalsIgnoreCase(JOURNEY_TYPE_UPGRADE, journeytype)
				|| StringUtils.equalsIgnoreCase(JOURNEY_TYPE_SECONDLINE, journeytype)) {
			log.error( "JourneyType is not compatible for given GroupType");
			throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_JOURNEY_TYPE);
		} else if (StringUtils.isNotBlank(offerCode)) {
			log.error( "offerCode is not compatible for given GroupType");
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
			log.error( "make is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE);
		}
		if (model == null || model.isEmpty()) {
			log.error( "model is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MODEL);
		}

		if (groupType == null || groupType.isEmpty()) {
			log.error( " Group Type is null ");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_GROUPTYPE);
		} else if (!Validator.validateGroupType(groupType)) {
			log.error( "Invalid Group Type");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
		} else if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYG) && (StringUtils.isNotBlank(journeyType)
				&& (journeyType.equalsIgnoreCase(JOURNEY_TYPE_SECONDLINE)
						|| journeyType.equalsIgnoreCase(JOURNEY_TYPE_UPGRADE)))) {
			log.error( "JourneyType is Not Compatible with given GroupType");
			throw new ApplicationException(ExceptionMessages.INVALID_GROUP_TYPE_JOURNEY_TYPE);
		} else if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)) {
			journeyTypeLocal = journeyType;
		} else if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)) {
			journeyTypeLocal = JOURNEY_TYPE_ACQUISITION;
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
		if (deviceId != null && !deviceId.matches(numberExp)) {
			log.error( ExceptionMessages.INVALID_DEVICE);
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

		if (bundleId != null && (!bundleId.matches(numberExp) || bundleId.matches("[0]*"))) {
			log.error( ExceptionMessages.INVALID_BUNDLE);
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
			if (!deviceId.matches(numberExp)) {
				log.error( ExceptionMessages.INVALID_DEVICE);
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}

		} else {
			log.error( DEVICE_ID_IS_EMPTY);
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
	}

	/**
	 * 
	 * @param deviceId
	 */
	public static void validateInsuranceDetails(String deviceId) {
		if (StringUtils.isNotBlank(deviceId)) {
			if (!deviceId.matches(numberExp)) {
				log.error( ExceptionMessages.INVALID_DEVICE);
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
		} else {
			log.error( DEVICE_ID_IS_EMPTY);
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
			if (!deviceId.matches(numberExp)) {
				log.error( ExceptionMessages.INVALID_DEVICE);
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
		} else {
			log.error( DEVICE_ID_IS_EMPTY);
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
	}

	/**
	 * 
	 * @param offerCode
	 * @param journeyType
	 */
	public static void getJourneyAndOfferCodeValidationForPAYG(String offerCode, String journeyType) {
		if (StringUtils.isNotBlank(journeyType) && (journeyType.equalsIgnoreCase(JOURNEY_TYPE_SECONDLINE)
				|| journeyType.equalsIgnoreCase(JOURNEY_TYPE_UPGRADE))) {
			log.error( "JourneyType is not compatible for given DeviceId");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICEID_JOURNEY_TYPE);
		} else if (StringUtils.isNotBlank(offerCode)) {
			log.error( "offerCode is not compatible for given DeviceId");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICEID_OFFER_CODE);
		}
	}
}
