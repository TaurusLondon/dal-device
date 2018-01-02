package com.vf.uk.dal.device.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.common.context.ServiceContext;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.exception.SystemException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.urlparams.FilterCriteria;
import com.vf.uk.dal.common.urlparams.PaginationCriteria;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.KeepDeviceChangePlanRequest;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleDetails;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * 1.Controller should able handle all the request and response for the device
 * services. 2.Controller should able to produce and consume Json Format for the
 * device services. 3.The service layer needs to be invoked from the device
 * services inside the controller.
 * 
 */

@RestController
@RequestMapping(value = "")
public class DeviceController {

	@Autowired
	DeviceService deviceService;

	private static final String GROUP_TYPE = "groupType";
	private static final String GROUP_NAME = "groupName";
	private static final String DEVICE_ID_IS_EMPTY = "Device Id is Empty";
	private static final String DEVICE_MAKE = "make";
	private static final String DEVICE_MODEL = "model";
	private static final String DEVICE_ID = "deviceId";
	private static final String JOURNEY_TYPE = "journeyType";
	private static final String OFFER_CODE = "offerCode";
	private static final String BUNDLE_ID = "bundleId";
	
	/**
	 * Handles requests for getDeviceTile Service with input as
	 * GROUP_NAME,GROUP_TYPE in URL as query.
	 * performance improved by @author manoj.bera
	 * 
	 * @return Device
	 **/
	@RequestMapping(value = "/deviceTile/queries/byMakeModel/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<DeviceTile> getListOfDeviceTile(@RequestParam Map<String, String> queryParams) {
		if (!queryParams.isEmpty() && Validator.validateGetListOfDeviceTile(queryParams)) {
			List<DeviceTile> listOfDeviceTile;
			String make = queryParams.containsKey(DEVICE_MAKE) ? queryParams.get(DEVICE_MAKE) : null;
			String model = queryParams.containsKey(DEVICE_MODEL) ? queryParams.get(DEVICE_MODEL) : null;
			String groupType = queryParams.containsKey(GROUP_TYPE) ? queryParams.get(GROUP_TYPE) : null;
			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE) ? queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE) : null;
			String bundleId = queryParams.containsKey(BUNDLE_ID) ? queryParams.get(BUNDLE_ID) : null;
			Double creditLimit = null;
			if (deviceId != null && !deviceId.matches("[0-9]{6}")) {
				LogHelper.error(this, "DeviceId is Invalid");
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
			if (queryParams.containsKey(Constants.CREDIT_LIMIT)) {
				if (StringUtils.isNotBlank(queryParams.get(Constants.CREDIT_LIMIT))) {
					try {
						creditLimit = Double.parseDouble(queryParams.get(Constants.CREDIT_LIMIT));
					} catch (NumberFormatException ex) {
						LogHelper.error(this, "Credit limit value not correct " + ex);
						throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
					}

				} else if (StringUtils.isBlank(queryParams.get(Constants.CREDIT_LIMIT))) {
					throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
				}

			}
			if ((StringUtils.isBlank(make) || "\"\"".equals(make))
					&& (StringUtils.isBlank(model) || "\"\"".equals(model))) {
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE_MODEL);
			} else if (StringUtils.isBlank(make) || "\"\"".equals(make)) {
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MAKE);
			} else if (StringUtils.isBlank(model) || "\"\"".equals(model)) {
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_MODEL);
			}
			
			if(bundleId != null && (!bundleId.matches("[0-9]{6}") || bundleId.matches("[0]*"))) {
				LogHelper.error(this, "BundleId is Invalid");
				throw new ApplicationException(ExceptionMessages.INVALID_BUNDLE_ID);
			}
			LogHelper.info(this, "Start -->  calling  getListofDeviceTile");
			listOfDeviceTile = deviceService.getListOfDeviceTile(make, model, groupType, deviceId,
					creditLimit, journeyType, offerCode, bundleId);
			LogHelper.info(this, "End -->  calling  getListofDeviceTile");
			return listOfDeviceTile;
		} else
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
	}

	/**
	 * Handles requests for getDeviceDetails Service with input as deviceId.
	 * 
	 * @return DeviceDetails
	 **/
	@ApiOperation(value = "Get the device details for the given device Id", notes = "The service gets the details of the device specially price, equipment, specification, features, merchandising, etc in the response.", response = DeviceDetails.class, tags={ "Device", })
    @RequestMapping(value = "/device/{deviceId}", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Success", response = DeviceDetails.class),
	        @ApiResponse(code = 404, message = "Not found", response = Void.class),
	        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	public DeviceDetails getDeviceDetails(@ApiParam(value = "Unique Id of the device being requested",required=true ) @PathVariable("deviceId") String deviceId,
	        @ApiParam(value = "Type of journey that the user undertakes e.g. \"Acquisition\", \"upgrade\", \"ils\" etc.") @RequestParam(value = "journeyType", required = false) String journeyType,
	        @ApiParam(value = "Offer code that defines what type of promotional discount needs to be displaced.") @RequestParam(value = "offerCode", required = false) String offerCode) {
		DeviceDetails deviceDetails;
		LogHelper.info(this, ":::::::Test Logger for VSTS migration And Validate Pipeline Validation::::::::");
		if (StringUtils.isNotBlank(deviceId)) {
			if(deviceId !=null && !deviceId.matches("[0-9]{6}")) {
					LogHelper.error(this, "DeviceId is Invalid");
					throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
			LogHelper.info(this, "Start -->  calling  getDeviceDetails");
			deviceDetails = deviceService.getDeviceDetails(deviceId, journeyType, offerCode);
			LogHelper.info(this, "End -->  calling  getDeviceDetails");
		} else {
			LogHelper.error(this, DEVICE_ID_IS_EMPTY);
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
		
		return deviceDetails;
	}

	/**
	 * Handles requests for getDeviceTile Service with input as deviceId.
	 * 
	 * @return List<DeviceTile>
	 **/
	@RequestMapping(value = "/deviceTile/queries/byDeviceVariant/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<DeviceTile> getDeviceTileById(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {
			List<DeviceTile> listOfDeviceTile;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE) ? queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE) : null;
			/*if (StringUtils.isNotBlank(offerCode) && (StringUtils.isBlank(journeyType)
					|| (StringUtils.isNotBlank(journeyType) && !Validator.validateJourneyType(journeyType)))) {

				LogHelper.info(this, "Required JourneyType with Offercode.");
				throw new ApplicationException(ExceptionMessages.REQUIRED_JOURNEY_TYPE);
			}	*/
			if (deviceId != null) {
				if(!deviceId.matches("[0-9]{6}")) {
					LogHelper.error(this, "DeviceId is Invalid");
					throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
				}
				LogHelper.info(this, "Start -->  calling  getDeviceTileById");
				listOfDeviceTile = deviceService.getDeviceTileById(deviceId,offerCode,journeyType);
				LogHelper.info(this, "End -->  calling  getDeviceTileById");
			} else {
				LogHelper.error(this, DEVICE_ID_IS_EMPTY);
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
			}
			return listOfDeviceTile;
		} else
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);

	}

	/**
	 * Handles requests for GetProductList Service with input as SIMO in URL as
	 * query.
	 * 
	 * @return List<ProductGroup>
	 **/
	@RequestMapping(value = "/productGroup", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<ProductGroup> getProductGroup() {
		List<ProductGroup> productGroup;
		String groupType;
		String groupName;
		groupType = getFilterValue(GROUP_TYPE);
		groupName = getFilterValue(GROUP_NAME);
		LogHelper.info(this, "Start -->  calling  getProductGroupByGroupTypeGroupName");
		productGroup = deviceService.getProductGroupByGroupTypeGroupName(groupType, groupName);
		LogHelper.info(this, "End -->  calling  getProductGroupByGroupTypeGroupName");
		return productGroup;
	}

	/**
	 * Handles requests for getComaptibleAccessories Service with input as
	 * deviceId.
	 * 
	 * @return List<Accessory>
	 **/
	@RequestMapping(value = "/accessory/queries/byDeviceId/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<AccessoryTileGroup> getAccessoriesOfDevice(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {
			List<AccessoryTileGroup> listOfAccessoryTileGroup;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE)?queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE)?queryParams.get(OFFER_CODE) : null;
			if (StringUtils.isNotBlank(deviceId)) {
				if (!"[0-9]{6}".matches(deviceId)) {
					LogHelper.error(this, "DeviceId is Invalid");
					throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
				}
				LogHelper.info(this, "Start -->  calling  getAccessoriesOfDevice");
				listOfAccessoryTileGroup = deviceService.getAccessoriesOfDevice(deviceId,journeyType,offerCode);
				LogHelper.info(this, "End -->  calling  getAccessoriesOfDevice");
			} else {
				LogHelper.error(this, DEVICE_ID_IS_EMPTY);
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
			}
			return listOfAccessoryTileGroup;
		} else
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
	}
			
			/**
			 * Handles requests for getDeviceList Service .
			 * 
			 * @return FacetedDevice
			 * **/
			
			@RequestMapping(value = "/deviceTile", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
			public FacetedDevice getDeviceList(@RequestParam Map<String, String> queryParams){
				
				boolean includeRecommendations = false;
				FacetedDevice facetedDevice;
				if(!queryParams.isEmpty() && Validator.validateGetDeviceList(queryParams))
				{
					
					String make = queryParams.containsKey(DEVICE_MAKE) ? queryParams.get(DEVICE_MAKE) : null;
					String model = queryParams.containsKey(DEVICE_MODEL) ? queryParams.get(DEVICE_MODEL) : null;
					String groupType = queryParams.containsKey(GROUP_TYPE) ? queryParams.get(GROUP_TYPE) : null;
					String productClass = queryParams.containsKey("productClass") ? queryParams.get("productClass") : null;
					String capacity = queryParams.containsKey("capacity") ? queryParams.get("capacity") : null;
					String colour = queryParams.containsKey("colour") ? queryParams.get("colour") : null;
					String operatingSystem = queryParams.containsKey("operatingSystem") ? queryParams.get("operatingSystem") : null;
					String mustHaveFeatures = queryParams.containsKey("mustHaveFeatures") ? queryParams.get("mustHaveFeatures") : null;
					String journeyType = queryParams.containsKey(JOURNEY_TYPE) ? queryParams.get(JOURNEY_TYPE) : null;
					String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE) : null;
					String msisdn = queryParams.containsKey("msisdn") ? queryParams.get("msisdn") : null;
					String showRrecommendations = queryParams.containsKey("includeRecommendations") ? queryParams.get("includeRecommendations") : null;
					
					/**
					 * @author suranjit_kashyap 
					 * @Sprint 6.6 Start
					 */
					
					if (StringUtils.isNotBlank(showRrecommendations) && !Validator.validateIncludeRecommendation(showRrecommendations)) {
						throw new ApplicationException(ExceptionMessages.INVALID_INCLUDERECOMMENDATION);
					}
					//Retrieving Pagesize and Pagenumber
                    PaginationCriteria paginationCriteria = ServiceContext.getPaginationCriteria();
                    int pageNumber=0;
					int pageSize=0;
                    
                    if(paginationCriteria!=null)
                    {
                    	pageNumber = paginationCriteria.getPageNumber();
                    	pageSize = paginationCriteria.getPageSize();
                    }
					if (!Validator.validatePageSize(pageSize)) {
						throw new SystemException(ExceptionMessages.PAGESIZE_NEGATIVE_ERROR);
					}
					
					if (!Validator.validatePageNumber(pageNumber)) {
						throw new SystemException(ExceptionMessages.PAGENUMBER_NEGATIVE_ERROR);
					}
					
					if (StringUtils.isNotBlank(msisdn) && !Validator.validateMSISDN(msisdn) && Constants.STRING_TRUE.equalsIgnoreCase(showRrecommendations)) {
						throw new ApplicationException(ExceptionMessages.INVALID_MSISDN);
					}
					
					/**
					 * @author suranjit_kashyap
					 * @Sprint 6.6 End
					 */
					
					includeRecommendations = Boolean.valueOf(showRrecommendations);
					Float creditLimit = null;
					
					if (queryParams.containsKey(Constants.CREDIT_LIMIT)) {
						if(StringUtils.isNotBlank(queryParams.get(Constants.CREDIT_LIMIT))){
							try{
								creditLimit = Float.parseFloat(queryParams.get(Constants.CREDIT_LIMIT));
							} catch(NumberFormatException ex){
								LogHelper.error(this, "Credit limit value not correct " + ex);
								throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
							}
							
						} else if(StringUtils.isBlank(queryParams.get(Constants.CREDIT_LIMIT))){
							throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
						}
						
					} 					
					
					//Retrieving sort value
					String sortCriteria = ServiceContext.getSortCriteria();
					LogHelper.info(this, "Start -->  calling  getDeviceList");
					facetedDevice = deviceService.getDeviceList(productClass,make,model,groupType,sortCriteria,pageNumber,
							pageSize,capacity,colour,operatingSystem,mustHaveFeatures,
							journeyType, creditLimit,offerCode, msisdn, includeRecommendations);
					LogHelper.info(this, "End -->  calling  getDeviceList");
				} else {
					throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
			}
			return facetedDevice;
	}

	/*
	 * Handles requests for getInsuranceById Service with input as deviceId.
	 * 
	 * @return insurance
	 */

	@RequestMapping(value = "/insurance/queries/byDeviceId/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Insurances getInsuranceById(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {
			Insurances insurance;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE)?queryParams.get(JOURNEY_TYPE) : null;
			/*
			if (StringUtils.isNotBlank(journeyType) && !Validator.validateJourneyType(journeyType)) {
				LogHelper.info(this, "Received JourneyType is invalid.");
				throw new ApplicationException(ExceptionMessages.INVALID_JOURNEY_TYPE);

			}*/
			if (StringUtils.isNotBlank(deviceId)) {
				if (!"[0-9]{6}".matches(deviceId)) {
					LogHelper.error(this, "DeviceId is Invalid");
					throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
				}
				LogHelper.info(this, "Start -->  calling  getInusranceByDeviceId");
				insurance = deviceService.getInsuranceByDeviceId(deviceId,journeyType);
				LogHelper.info(this, "End -->  calling  getInsuranceDeviceId");
			} else {
				LogHelper.error(this, DEVICE_ID_IS_EMPTY);
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
			}
			return insurance;
		} else
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
	}

	/*
	 * Saves the details of the devices into database
	 * 
	 * @param groupType.
	 */
	@RequestMapping(value = "/deviceTile/cacheDeviceTile", method = RequestMethod.POST, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public ResponseEntity<CacheDeviceTileResponse> cacheDeviceTile() {
		String groupType = getFilterValue(GROUP_TYPE);

		if (StringUtils.isNotBlank(groupType)) {
			if (groupType.equals(Constants.STRING_DEVICE_PAYG) || groupType.equals(Constants.STRING_DEVICE_PAYM)
					|| groupType.equals(Constants.STRING_DEVICE_NEARLY_NEW)) {
				CacheDeviceTileResponse cacheDeviceTileResponse = deviceService.insertCacheDeviceToDb();
				ResponseEntity<CacheDeviceTileResponse> response = new ResponseEntity<>(cacheDeviceTileResponse,
						HttpStatus.CREATED);
				deviceService.cacheDeviceTile(groupType, cacheDeviceTileResponse.getJobId());

				return response;
			} else {
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
			}
		} else
			throw new ApplicationException(ExceptionMessages.NULL_OR_EMPTY_GROUP_TYPE);

	}

	/**
	 * Returns Stock Availability for list of device IDs
	 * 
	 * @param groupType.
	 */
	/*
	 * @RequestMapping(value = "/cacheStockAvailability/", method =
	 * RequestMethod.GET, produces =
	 * javax.ws.rs.core.MediaType.APPLICATION_JSON) public List<StockInfo>
	 * getStockAvailabilityForDeviceList() { List<StockInfo>
	 * stockAvailabilityForGroupType; String
	 * groupType=getFilterValue(GROUP_TYPE);
	 * if(StringUtils.isNotBlank(groupType)) {
	 * if(groupType.equals(Constants.STRING_DEVICE_PAYG) ||
	 * groupType.equals(Constants.STRING_DEVICE_PAYM) ||
	 * groupType.equals(Constants.STRING_DEVICE_NEARLY_NEW))
	 * stockAvailabilityForGroupType =
	 * deviceService.getStockAvailability(groupType); else throw new
	 * ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE); } else
	 * { LogHelper.error(this, "Group Type is null or Empty String"); throw new
	 * ApplicationException(ExceptionMessages.NULL_OR_EMPTY_GROUP_TYPE); }
	 * return stockAvailabilityForGroupType; }
	 */

	/**
	 * 
	 * Checks the filterValue coming from ServiceContext based on incoming
	 * filterName.
	 *
	 * @param String
	 * 
	 * @return String
	 **/

	private String getFilterValue(String filterName) {
		String ret = null;
		List<FilterCriteria> filterCriteriaList = ServiceContext.getFilterCriteria();
		if (filterCriteriaList != null && !filterCriteriaList.isEmpty()) {
			for (FilterCriteria filterCriteria : filterCriteriaList) {
				if (filterCriteria.getFilterFieldName().equals(filterName)) {
					ret = filterCriteria.getFilterFieldValue().trim();
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param deviceId
	 * @param bundleId
	 * @param allowedRecurringPriceLimit
	 * @return
	 */
	@RequestMapping(value = "/plan/action/keepDeviceChangePlan", method = RequestMethod.POST, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public BundleDetails getKeepDeviceChangePlan(@RequestBody KeepDeviceChangePlanRequest keepDeviceChangePlanRequest) {
		BundleDetails bundleDetails;
		if (keepDeviceChangePlanRequest == null) {
			LogHelper.error(this, "AllowedRecurringPriceLimit is null");
			throw new ApplicationException(ExceptionMessages.INVALID_REQUEST_PARAMETER);
		}
		String deviceId = keepDeviceChangePlanRequest.getDeviceId();
		String bundleId = keepDeviceChangePlanRequest.getBundleId();
		String allowedRecurringPriceLimit = keepDeviceChangePlanRequest.getAllowedRecurringPriceLimit();
		String plansLimit = keepDeviceChangePlanRequest.getPlansLimit();
		if (StringUtils.isBlank(deviceId) && StringUtils.isBlank(bundleId)
				&& StringUtils.isBlank(allowedRecurringPriceLimit) && StringUtils.isBlank(plansLimit)) {
			LogHelper.error(this, "All Input Parameter is null");
			throw new ApplicationException(ExceptionMessages.INVALID_REQUEST_PARAMETER);
		}
		Validator.validateDeviceId(deviceId);
		if (StringUtils.isBlank(bundleId)) {
			LogHelper.error(this, "BundleId is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_BUNDLEID);
		} else if (StringUtils.isNotBlank(bundleId) && (!bundleId.matches("[0-9]{6}") || bundleId.matches("[0]*"))) {
			LogHelper.error(this, "BundleId is Invalid");
			throw new ApplicationException(ExceptionMessages.INVALID_BUNDLE_ID);
		} else if (StringUtils.isBlank(allowedRecurringPriceLimit)) {
			LogHelper.error(this, "AllowedRecurringPriceLimit is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_ALLOWED_RECURRING_PRICE_LIMIT);
		} else if (StringUtils.isNotBlank(allowedRecurringPriceLimit)
				&& (!allowedRecurringPriceLimit.matches(".*[0-9].*") || allowedRecurringPriceLimit.matches("[0]*"))) {
			LogHelper.error(this, "AllowedRecurringPriceLimit is Invalid");
			throw new ApplicationException(ExceptionMessages.INVALID_ALLOWED_RECURRING_PRICE_LIMIT);
		} else if (StringUtils.isBlank(plansLimit)) {
			LogHelper.error(this, "Limit is null");
			throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_PLANS_LIMIT);
		} else if (StringUtils.isNotBlank(plansLimit) && (!plansLimit.matches("[0-9]") || plansLimit.matches("[0]*"))) {
			LogHelper.error(this, "Limit is Invalid");
			throw new ApplicationException(ExceptionMessages.INVALID_PLANS_LIMIT);
		} else {
			LogHelper.info(this, "Get the bundle details of a particular device id");
			LogHelper.info(this, "Start -->  calling  getBundleOfDeviceId");
			bundleDetails = deviceService.getBundlesOfDeviceId(deviceId, bundleId, allowedRecurringPriceLimit,
					plansLimit);
			LogHelper.info(this, "End -->  calling  getBundleOfDeviceId");
		}

		return bundleDetails;
	}

	/**
	 * Returns review details for the given deviceId
	 * 
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/device/{deviceId}/review", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
	public JSONObject getDeviceReviewDetails(@PathVariable(DEVICE_ID) String deviceId) {

		Validator.validateDeviceId(deviceId);
		LogHelper.info(this, "Start -->  calling  getDeviceReviewDetails");
		return deviceService.getDeviceReviewDetails(deviceId);

	}

	/**
	 * Returns List of Device details for the given List of devices
	 * 
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/device/", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public List<DeviceDetails> getListOfDeviceDetails(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {
			
			LogHelper.info(this, "Query parameter(s) passed in the request "+queryParams);
			List<DeviceDetails> listOfDeviceDetails;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE) ? queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE) : null;
			/*if (StringUtils.isNotBlank(offerCode) && (StringUtils.isBlank(journeyType)
					|| (StringUtils.isNotBlank(journeyType) && !Validator.validateJourneyType(journeyType)))) {

				LogHelper.info(this, "Required JourneyType with Offercode.");
				throw new ApplicationException(ExceptionMessages.REQUIRED_JOURNEY_TYPE);
			}*/	
			if (deviceId != null) {
				LogHelper.info(this, "Get the list of device details for the device id passed as request params "+deviceId);
				LogHelper.info(this, "Start -->  calling  getListOfDeviceDetails");
				listOfDeviceDetails = deviceService.getListOfDeviceDetails(deviceId,offerCode,journeyType);
				LogHelper.info(this, "End -->  calling  getListofDeviceDetails");
			} else {
				LogHelper.error(this, DEVICE_ID_IS_EMPTY);
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
			}
			return listOfDeviceDetails;
		} else{
			LogHelper.error(this, "Query parameter(s) passed in the request is invalid"+ExceptionMessages.INVALID_QUERY_PARAMS);
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
		}

	}

	@RequestMapping(value = "/deviceTile/cacheDeviceTile/{jobId}/status", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
	public CacheDeviceTileResponse getCacheDeviceJobStatus(@PathVariable("jobId") String jobId) {

		return deviceService.getCacheDeviceJobStatus(jobId);

	}
}