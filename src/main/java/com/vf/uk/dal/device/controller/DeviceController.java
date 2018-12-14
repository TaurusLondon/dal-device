package com.vf.uk.dal.device.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 1.Controller should able handle all the request and response for the device
 * services. 2.Controller should able to produce and consume Json Format for the
 * device services. 3.The service layer needs to be invoked from the device
 * services inside the controller.
 * 
 */
@Api(tags = "Device")
@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class DeviceController {

	public static final String NUMBEREXP = "[0-9]{6}";
	private static final String ERROR_CODE_DEVICETILE_BY_ID = "error_device_entity_failed";
	@Autowired
	DeviceService deviceService;

	@Autowired
	Validator validator;
	/**
	 * Handles requests for getDeviceTile Service with input as
	 * GROUP_NAME,GROUP_TYPE in URL as query. performance improved by @author
	 * manoj.bera
	 * 
	 * @param ex
	 * @return ErrorResponse
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public com.vf.uk.dal.common.exception.ErrorResponse handleMissingParams(
			MissingServletRequestParameterException ex) {

		return new com.vf.uk.dal.common.exception.ErrorResponse(400, "DEVICE_INVALID_INPUT",
				"Missing mandatory parameter " + ex.getParameterName());

	}

	/**
	 *
	 * Handles requests for getDeviceTile Service with input as deviceId.
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	@ApiOperation(value = "Get the device tile details for the given device tile Id.", notes = "The service gets the details of the device required to be dispalyed on deviceTile.", response = DeviceTile.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = DeviceTile.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.model.Error.class) })
	@GetMapping(value = "/deviceTile/queries/byDeviceVariant/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeviceTile> getDeviceTileById(
			@NotNull @ApiParam(value = "Device Id of the device tile being requested", required = true) @RequestParam(value = "deviceId", required = true) String deviceId,
			@ApiParam(value = "Journey Type") @RequestParam(value = "journeyType", required = false) String journeyType,
			@ApiParam(value = "Promotional Offer Code that's applicable") @RequestParam(value = "offerCode", required = false) String offerCode) {
		List<DeviceTile> listOfDeviceTile;
		if (!deviceId.matches(NUMBEREXP)) {
			log.error(ExceptionMessages.INVALID_DEVICE);
			throw new DeviceCustomException(ERROR_CODE_DEVICETILE_BY_ID,ExceptionMessages.INVALID_DEVICE_ID,"404");
		}
		listOfDeviceTile = deviceService.getDeviceTileById(deviceId, offerCode, journeyType);

		return listOfDeviceTile;
	}

	/**
	 * Handles requests for getDeviceList Service
	 * 
	 * @param productClass
	 * @param groupType
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @param make
	 * @param model
	 * @param color
	 * @param operatingSystem
	 * @param capacity
	 * @param msisdn
	 * @param mustHaveFeatures
	 * @param journeyType
	 * @param includeRecommendations
	 * @param offerCode
	 * @param creditLimit
	 * @return FacetedDevice
	 */
	@ApiOperation(value = "Get the list of devices based on the filter criteria, like productGroup brand Name. Pagination, sorting, filteration also defined", notes = "The service gets the details of the device list from Solr based on the filter criteria in the response.", response = FacetedDevice.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = FacetedDevice.class),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.model.Error.class) })
	@GetMapping(value = "/deviceTile", produces = MediaType.APPLICATION_JSON_VALUE)
	public FacetedDevice getDeviceList(
			@NotNull @ApiParam(value = "Values on which the attributes should be filtered upon.", required = true) @RequestParam(value = "productClass", required = true) String productClass,
			@NotNull @ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"DEVICE_PAYM\" or \"DEVICE_PAYG\" or \"DATA_DEVICE_PAYM\".", required = true) @RequestParam(value = "groupType", required = true) String groupType,
			@NotNull @ApiParam(value = "Values of attributes based on which solr will provide the sorted response, like Most Popular(Priority),Rating, New Releases, Brand(a-z)(z-a) but need to pass EquipmentMake to api, MonthlyPrice(lo-hi)(hi-lo)(Need to pass RecurringCharge).", required = true) @RequestParam(value = "sort", required = true) String sort,
			@ApiParam(value = "Page Number") @RequestParam(value = "pageNumber", required = false, defaultValue = "${icp.service.defaultPageNumber}") Integer pageNumber,
			@ApiParam(value = "Page Size") @RequestParam(value = "pageSize", required = false, defaultValue = "${icp.service.defaultPageSize}") Integer pageSize,
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
			@ApiParam(value = "Monthly credit limit applicable in case of conditional accept.(Credit Limit is not completely implemented for Device List)") @RequestParam(value = "creditLimit", required = false) String creditLimit) {

		FacetedDevice facetedDevice;
		if (StringUtils.isNotBlank(includeRecommendations)) {
			validator.validateIncludeRecommendation(includeRecommendations);
		}

		validator.validatePageSize(pageSize, pageNumber);

		if (StringUtils.isNotBlank(msisdn)) {
			validator.validateMSISDN(msisdn, includeRecommendations);
		}
		boolean includeRecommendationsParam = Boolean.parseBoolean(includeRecommendations);
		Float creditLimitparam = null;

		if (creditLimit != null) {
			creditLimitparam = validator.validateForCreditLimit(creditLimit);
		}
		facetedDevice = deviceService.getDeviceList(productClass, make, model, groupType, sort, pageNumber, pageSize,
				capacity, color, operatingSystem, mustHaveFeatures, journeyType, creditLimitparam, offerCode, msisdn,
				includeRecommendationsParam);
		return facetedDevice;
	}
}