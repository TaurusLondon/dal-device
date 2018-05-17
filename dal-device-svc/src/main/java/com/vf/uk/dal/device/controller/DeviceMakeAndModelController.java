package com.vf.uk.dal.device.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.svc.DeviceMakeAndModelService;
import com.vf.uk.dal.device.validator.Validator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DeviceMakeAndModelController {

	@Autowired
	DeviceMakeAndModelService deviceMakeAndModelService;
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
	 * @param make
	 * @param model
	 * @param groupType
	 * @param journeyType
	 * @param offerCode
	 * @param bundleId
	 * @param deviceId
	 * @param creditLimit
	 * @return
	 */
	@ApiOperation(value = "Get the list of device tiles based on the filter criteria. Pagination also defined", notes = "The service gets the details of the device tiles from coherence based on the filter criteria in the response.", response = DeviceTile.class, responseContainer = "List", tags = {
			"DeviceTile", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = DeviceTile.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/deviceTile/queries/byMakeModel/", method = RequestMethod.GET, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public List<DeviceTile> getListOfDeviceTile(
			@NotNull @ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"apple\".", required = true) @RequestParam(value = "make", required = true) String make,
			@NotNull @ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"iphone7\".", required = true) @RequestParam(value = "model", required = true) String model,
			@NotNull @ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"DEVICE_PAYM\" or \"DEVICE_PAYG\" or \"DATA_DEVICE_PAYM\".", required = true) @RequestParam(value = "groupType", required = true) String groupType,
			@ApiParam(value = "The journey that user undertakes \"acquisition\", \"upgrade\", \"ils\" etc.") @RequestParam(value = "journeyType", required = false) String journeyType,
			@ApiParam(value = "Promotional offer code applicable") @RequestParam(value = "offerCode", required = false) String offerCode,
			@ApiParam(value = "bundle Id for comaptible devices needs to displayed") @RequestParam(value = "bundleId", required = false) String bundleId,
			@ApiParam(value = "device Id for comaptible devices needs to displayed") @RequestParam(value = "deviceId", required = false) String deviceId,
			@ApiParam(value = "creditLimit applicable for the customer in case of conditional accept state") @RequestParam(value = "creditLimit", required = false) String creditLimit) {

		List<DeviceTile> listOfDeviceTile;
		Double creditLimitParam = Validator.validateCreditLimitAndIds(make, model, bundleId, deviceId, creditLimit);
		listOfDeviceTile = deviceMakeAndModelService.getListOfDeviceTile(make, model, groupType, deviceId, creditLimitParam,
				journeyType, offerCode, bundleId);
		return listOfDeviceTile;

	}
}
