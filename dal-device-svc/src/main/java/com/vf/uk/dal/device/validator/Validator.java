package com.vf.uk.dal.device.validator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ExceptionMessages;

public class Validator {
	public static void validateDeviceId(String deviceId)
	{
		if (StringUtils.isBlank(deviceId)) {
			LogHelper.error(Validator.class, "DeviceId is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}else if (Validator.validateId(deviceId)) {
			LogHelper.error(Validator.class, "DeviceId is Invalid");
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
		}
	}
	public static boolean validateId(String productId) {
		return StringUtils.isNotBlank(productId) && (!productId.matches("[0-9]{6}") || productId.matches("[0]*"));
	}
	
	public static boolean validateGetListOfDeviceTile(Map<String, String> queryParams)
	{
		List<String> validParams = Arrays.asList("make", "model", "groupType", "creditLimit", "journeyType", "deviceId", "offerCode", "bundleId", "sort", "pageSize", "pageNumber");
		
		return validateParams(queryParams, validParams);
	}
	
	public static boolean validateDeviceId(Map<String, String> queryParams)
	{
		List<String> validParams = Arrays.asList("deviceId","offerCode","journeyType");
		
		return validateParams(queryParams, validParams);
	}
	
	public static boolean validateGetDeviceList(Map<String, String> queryParams)
	{
		List<String> validParams = Arrays.asList("groupType", "productClass", "creditLimit",
				"make","model","capacity","colour","operatingSystem","journeyType","offerCode",
				"sort", "mustHaveFeatures", "pageSize", "pageNumber", "msisdn", "includeRecommendations");
		
		return validateParams(queryParams, validParams);
	}
	
	private static boolean validateParams(Map<String, String> queryParams, List<String> validParams)
	{
		for(String key : queryParams.keySet())
		{
			boolean found = false;
			for(String param : validParams)
			{
				if(param.equals(key)){
					found = true;
					break;
				}
			}
			if(!found)
				return false;
		}
		return true;
	}
	
	
	public static boolean validateJourneyType(String journeyType)
	{
		List<String> journeyList = Arrays.asList("acquisition", "upgrade", "secondline");
		if(journeyList.contains(journeyType.toLowerCase()))
			return true;
		else
			return false;
	}
	
	public static boolean validateSortCriteria(String sortCriteria)    {  
		List<String> sortCriteriaList = Arrays.asList("Rating", "Priority", "EquipmentMake", "ReccuringCharge");  
		if(sortCriteriaList.contains(sortCriteria))         
			return true;      
		else          
			return false;    
		}
	/**
	 * @author suranjit_kashyap 
	 * @Sprint 6.6 Validator Start
	 */
	public static boolean validatePageSize(int pageSize) {
		if (pageSize < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validatePageNumber(int pageNumber) {
		if (pageNumber < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validateIncludeRecommendation(String showRecommendations) {
		if(!showRecommendations.equalsIgnoreCase(Constants.STRING_TRUE) && !showRecommendations.equalsIgnoreCase(Constants.STRING_FALSE)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validateMSISDN(String msisdn) {
		if (!msisdn.matches("[0-9]{10}")) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean validateGroupType(String groupType) {
		if (!groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				&& !groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @author suranjit_kashyap
	 * @Sprint 6.6 Validator Start
	 */

}
