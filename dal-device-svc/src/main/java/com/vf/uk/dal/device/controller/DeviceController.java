package com.vf.uk.dal.device.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import springfox.documentation.annotations.ApiIgnore;
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
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public com.vf.uk.dal.common.exception.ErrorResponse handleMissingParams(MissingServletRequestParameterException ex) {
		
		com.vf.uk.dal.common.exception.ErrorResponse error = new com.vf.uk.dal.common.exception.ErrorResponse(400, "DEVICE_INVALID_INPUT", "Missing mandatory parameter "+ex.getParameterName()); 
		
		return error;
	}
	@ApiOperation(value = "Get the list of device tiles based on the filter criteria. Pagination also defined", notes = "The service gets the details of the device tiles from coherence based on the filter criteria in the response.", response = DeviceTile.class, responseContainer = "List", tags={ "DeviceTile", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = DeviceTile.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not found", response = Void.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/deviceTile/queries/byMakeModel/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<DeviceTile> getListOfDeviceTile(@NotNull@ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"apple\".", required = true) @RequestParam(value = "make", required = true) String make,
	         @NotNull@ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"iphone7\".", required = true) @RequestParam(value = "model", required = true) String model,
	         @NotNull@ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"Handset\" or \"Accessory\".", required = true) @RequestParam(value = "groupType", required = true) String groupType,
	        @ApiParam(value = "The journey that user undertakes \"acquisition\", \"upgrade\", \"ils\" etc.") @RequestParam(value = "journeyType", required = false) String journeyType,
	        @ApiParam(value = "Promotional offer code applicable") @RequestParam(value = "offerCode", required = false) String offerCode,
	        @ApiParam(value = "bundle Id for comaptible devices needs to displayed") @RequestParam(value = "bundleId", required = false) String bundleId,
	        @ApiParam(value = "device Id for comaptible devices needs to displayed") @RequestParam(value = "deviceId", required = false) String deviceId,
	        @ApiParam(value = "creditLimit applicable for the customer in case of conditional accept state") @RequestParam(value = "creditLimit", required = false) String creditLimit) {
		
			List<DeviceTile> listOfDeviceTile;
			Double creditLimitParam = null;
			if (deviceId != null && !deviceId.matches("[0-9]{6}")) {
				LogHelper.error(this, "DeviceId is Invalid");
				throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
			}
			if (creditLimit != null) {
				if(StringUtils.isNotBlank(creditLimit)){
					if(!creditLimit.matches("[0-9]")) {
						throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
					}
					if (!Validator.validateCreditLimit(creditLimit)) {
						throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
					} else {
					try{
						creditLimitParam = Double.parseDouble(creditLimit);
					} catch(NumberFormatException ex){
						LogHelper.error(this, "Credit limit value not correct " + ex);
						throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
					}
					}
				} else if(StringUtils.isBlank(creditLimit)){
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
					creditLimitParam, journeyType, offerCode, bundleId);
			LogHelper.info(this, "End -->  calling  getListofDeviceTile");
			return listOfDeviceTile;
		
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
	public DeviceDetails getDeviceDetails(@NotNull@ApiParam(value = "Unique Id of the device being requested",required=true ) @PathVariable("deviceId") String deviceId,
	        @ApiParam(value = "Type of journey that the user undertakes e.g. \"Acquisition\", \"upgrade\", \"ils\" etc.") @RequestParam(value = "journeyType", required = false) String journeyType,
	        @ApiParam(value = "Offer code that defines what type of promotional discount needs to be displaced.") @RequestParam(value = "offerCode", required = false) String offerCode) {
		DeviceDetails deviceDetails;
		LogHelper.info(this, ":::::::Test Logger for VSTS migration And Validate Pipeline Validation::::::::");
		if (StringUtils.isNotBlank(deviceId)) {
			if (!deviceId.matches("[0-9]{6}")) {
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
	@ApiIgnore
	@RequestMapping(value = "/deviceTile/queries/byDeviceVariant/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<DeviceTile> getDeviceTileById(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {
			List<DeviceTile> listOfDeviceTile;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE) ? queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE) : null;
			
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
	@ApiIgnore
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
	@ApiOperation(value = "Get compatible accessory details for the given device Id", notes = "The service gets the details of compatible accessory along with the necessary information in the response.", response = AccessoryTileGroup.class, responseContainer = "List", tags={ "AccessoryTileGroup", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = AccessoryTileGroup.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not found", response = Void.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/accessory/queries/byDeviceId/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<AccessoryTileGroup> getAccessoriesOfDevice(@NotNull@ApiParam(value = "Unique Id of the device being requested", required = true) @RequestParam(value = "deviceId", required = true) String deviceId,
	        @ApiParam(value = "The journey that the user undertakes") @RequestParam(value = "journeyType", required = false) String journeyType,
	        @ApiParam(value = "Promotional offer applicable") @RequestParam(value = "offerCode", required = false) String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTileGroup;
		if (StringUtils.isNotBlank(deviceId)) {
				if (!deviceId.matches("[0-9]{6}")) {
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
		
	}
			
			/**
			 * Handles requests for getDeviceList Service .
			 * 
			 * @return FacetedDevice
			 * **/
	 @ApiOperation(value = "Get the list of devices based on the filter criteria, like productGroup brand Name. Pagination, sorting, filteration also defined", notes = "The service gets the details of the device list from Solr based on the filter criteria in the response.", response = FacetedDevice.class, tags={ "DeviceTile", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Success", response = FacetedDevice.class),
	        @ApiResponse(code = 404, message = "Not found", response = Void.class),
	        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
			@RequestMapping(value = "/deviceTile", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
			public FacetedDevice getDeviceList(@NotNull@ApiParam(value = "Values on which the attributes should be filtered upon.", required = true) @RequestParam(value = "productClass", required = true) String productClass,
			         @NotNull@ApiParam(value = "Values on which the attributes should be filtered upon.", required = true) @RequestParam(value = "groupType", required = true) String groupType,
			         @NotNull@ApiParam(value = "Values of attributes based on which solr will provide the sorted response, like Most Popular(Priority),Rating, New Releases, Brand(a-z)(z-a) but need to pass EquipmentMake to api, MonthlyPrice(lo-hi)(hi-lo)(Need to pass RecurringCharge).", required = true) @RequestParam(value = "sort", required = true) String sort,
			        @ApiParam(value = "Page Number") @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			        @ApiParam(value = "Page Size") @RequestParam(value = "pageSize", required = false) Integer pageSize,
			        @ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"apple\". Make is also known as Manufacturer.") @RequestParam(value = "make", required = false) String make,
			        @ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"iphone7\".", required = false) @RequestParam(value = "model", required = false) String model,
			        @ApiParam(value = "Filter by Colour of the device as in specification groups. Please note the value of this filter should be passed in double quotes. example: colour = \"Black\",\"Gold\"") @RequestParam(value = "color", required = false) String color,
			        @ApiParam(value = "Filter by OS of the device as in specification groups. Please note the value of this filter should be passed in double quotes. example: operatingSystem = \"iOS 10\",\"iOS 9\"") @RequestParam(value = "operatingSystem", required = false) String operatingSystem,
			        @ApiParam(value = "Filter by capacity of the device as in specification groups. Please note the value of this filter should be passed in double quotes. example: capacity = \"32 GB\",\"8 GB\"") @RequestParam(value = "capacity", required = false) String capacity,
			        @ApiParam(value = "Msisdn of the customer.") @RequestParam(value = "msisdn", required = false) String msisdn,
			        @ApiParam(value = "One or more of the following token separated by comma. \"physicalKeyboard\", \"greatCamera\", \"goodBattery\", \"bigScreen\", \"4GEnabled\", 'lightWeight\"") @RequestParam(value = "mustHaveFeatures", required = false) String mustHaveFeatures,
			        @ApiParam(value = "When user selects device for Upgrade.") @RequestParam(value = "journeyType", required = false) String journeyType,
			        @ApiParam(value = "When user selects device for Upgrade.") @RequestParam(value = "includeRecommendations", required = false) String includeRecommendations,
			        @ApiParam(value = "Promotional offer code applicable.") @RequestParam(value = "offerCode", required = false) String offerCode,
			        @ApiParam(value = "Monthly credit limit applicable in case of conditional accept.") @RequestParam(value = "creditLimit", required = false) String creditLimit){
				
				boolean includeRecommendationsParam = false;
				FacetedDevice facetedDevice;
					
					/**
					 * @author suranjit_kashyap 
					 * @Sprint 6.6 Start
					 */
					
					if (StringUtils.isNotBlank(includeRecommendations) && !Validator.validateIncludeRecommendation(includeRecommendations)) {
						throw new ApplicationException(ExceptionMessages.INVALID_INCLUDERECOMMENDATION);
					}
					int pageNumberParam =0;
					int pageSizeParam=0;
					//Retrieving Pagesize and Pagenumber
                    PaginationCriteria paginationCriteria = ServiceContext.getPaginationCriteria();
                   if(paginationCriteria!=null)
                    {
                	   pageNumberParam = paginationCriteria.getPageNumber();
                	   pageSizeParam = paginationCriteria.getPageSize();
                    }
					if (!Validator.validatePageSize(pageSizeParam)) {
						throw new SystemException(ExceptionMessages.PAGESIZE_NEGATIVE_ERROR);
					}
					
					if (!Validator.validatePageNumber(pageNumberParam)) {
						throw new SystemException(ExceptionMessages.PAGENUMBER_NEGATIVE_ERROR);
					}
					
					if (StringUtils.isNotBlank(msisdn) && !Validator.validateMSISDN(msisdn) && Constants.STRING_TRUE.equalsIgnoreCase(includeRecommendations)) {
						throw new ApplicationException(ExceptionMessages.INVALID_MSISDN);
					}
					
					/**
					 * @author suranjit_kashyap
					 * @Sprint 6.6 End
					 */
					
					includeRecommendationsParam = Boolean.valueOf(includeRecommendations);
					Float creditLimitparam = null;
					
					if (creditLimit != null) {
						if(StringUtils.isNotBlank(creditLimit)){
							if(!creditLimit.matches("[0-9]")) {
								throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
							}
							if (!Validator.validateCreditLimit(creditLimit)) {
								throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
							} else {
							try{
								creditLimitparam = Float.parseFloat(creditLimit);
							} catch(NumberFormatException ex){
								LogHelper.error(this, "Credit limit value not correct " + ex);
								throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
							}
							}
						} else if(StringUtils.isBlank(creditLimit)){
							throw new ApplicationException(ExceptionMessages.INVALID_CREDIT_LIMIT);
						}
						
					} 					
					
					//Retrieving sort value
					String sortCriteria = ServiceContext.getSortCriteria();
					LogHelper.info(this, "Start -->  calling  getDeviceList");
					facetedDevice = deviceService.getDeviceList(productClass,make,model,groupType,sortCriteria,pageNumberParam,
							pageSizeParam,capacity,color,operatingSystem,mustHaveFeatures,
							journeyType, creditLimitparam,offerCode, msisdn, includeRecommendationsParam);
					LogHelper.info(this, "End -->  calling  getDeviceList");
				
			return facetedDevice;
	}

	/*
	 * Handles requests for getInsuranceById Service with input as deviceId.
	 * 
	 * @return insurance
	 */
	 
			 @ApiOperation(value = "Get the list of insurance", notes = "The service gets the details of insurance available with device.", response = Insurances.class, tags={ "Insurances", })
			    @ApiResponses(value = { 
			        @ApiResponse(code = 200, message = "Success", response = Insurances.class),
			        @ApiResponse(code = 404, message = "Not found", response = Void.class),
			        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/insurance/queries/byDeviceId/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Insurances getInsuranceById(@NotNull@ApiParam(value = "Values based on which inssurnace will be fetched.", required = true) @RequestParam(value = "deviceId", required = true) String deviceId,
	        @ApiParam(value = "user journey") @RequestParam(value = "journeyType", required = false) String journeyType) {

		Insurances insurance;
			if (StringUtils.isNotBlank(deviceId)) {
				if (!deviceId.matches("[0-9]{6}")) {
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
		
	}

	/*
	 * Saves the details of the devices into database
	 * 
	 * @param groupType.
	 */
			 @ApiIgnore
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
	@ApiIgnore
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
	 @ApiOperation(value = "Get the reviews for a specific device Id. Response is coming from Bazar Voice(third party) API.", notes = "The service gets the reviews of a particular device variant",tags={ "Review", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Success"),
	        @ApiResponse(code = 404, message = "Not found", response = Void.class),
	        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/device/{deviceId}/review", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
	public JSONObject getDeviceReviewDetails(@NotNull@ApiParam(value = "Unique Id of the device for which the review is being requested",
	required = true) @PathVariable(DEVICE_ID) String deviceId) {

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
	 @ApiIgnore
	@RequestMapping(value = "/device/", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public List<DeviceDetails> getListOfDeviceDetails(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {
			
			LogHelper.info(this, "Query parameter(s) passed in the request "+queryParams);
			List<DeviceDetails> listOfDeviceDetails;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID) : null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE) ? queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE) : null;
			
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
@ApiIgnore
	@RequestMapping(value = "/deviceTile/cacheDeviceTile/{jobId}/status", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
	public CacheDeviceTileResponse getCacheDeviceJobStatus(@PathVariable("jobId") String jobId) {

		return deviceService.getCacheDeviceJobStatus(jobId);

	}
}