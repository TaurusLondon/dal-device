package com.vf.uk.dal.device.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.svc.AccessoryInsuranceService;
import com.vf.uk.dal.device.validator.Validator;

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
@Api(tags="DeviceAccessoryInsurance")
@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class AccessoryInsuranceController {

	@Autowired
	AccessoryInsuranceService accessoryInsuranceService;

	/**
	 * Handles requests for getDeviceTile Service with input as
	 * GROUP_NAME,GROUP_TYPE in URL as query. performance improved by @author
	 * manoj.bera
	 * 
	 * @param ex
	 * @return
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
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return getAccessoriesOfDevice
	 */
	@ApiOperation(value = "Get compatible accessory details for the given device Id", notes = "The service gets the details of compatible accessory along with the necessary information in the response.", response = AccessoryTileGroup.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = AccessoryTileGroup.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/accessory/queries/byDeviceId/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AccessoryTileGroup> getAccessoriesOfDevice(
			@NotNull @ApiParam(value = "Unique Id of the device being requested", required = true) @RequestParam(value = "deviceId", required = true) String deviceId,
			@ApiParam(value = "The journey that the user undertakes") @RequestParam(value = "journeyType", required = false) String journeyType,
			@ApiParam(value = "Promotional offer applicable") @RequestParam(value = "offerCode", required = false) String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTileGroup;
		Validator.validateAccessoryFields(deviceId);
		log.info( "Start -->  calling  getAccessoriesOfDevice");
		listOfAccessoryTileGroup = accessoryInsuranceService.getAccessoriesOfDevice(deviceId, journeyType, offerCode);
		log.info( "End -->  calling  getAccessoriesOfDevice");
		return listOfAccessoryTileGroup;

	}

	/**
	 * Handles requests for getInsuranceById Service with input as deviceId.
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @return getInsuranceById
	 */
	@ApiOperation(value = "Get the list of insurance", notes = "The service gets the details of insurance available with device.", response = Insurances.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Insurances.class),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/insurance/queries/byDeviceId/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Insurances getInsuranceById(
			@NotNull @ApiParam(value = "Values based on which inssurnace will be fetched.", required = true) @RequestParam(value = "deviceId", required = true) String deviceId,
			@ApiParam(value = "user journey") @RequestParam(value = "journeyType", required = false) String journeyType) {

		Insurances insurance;
		log.info( "Start -->  calling  getInusranceByDeviceId");
		Validator.validateInsuranceDetails(deviceId);
		log.info( "End -->  calling  getInsuranceDeviceId");
		insurance = accessoryInsuranceService.getInsuranceByDeviceId(deviceId, journeyType);
		return insurance;

	}
}