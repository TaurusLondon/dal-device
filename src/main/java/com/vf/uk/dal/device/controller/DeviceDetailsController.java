package com.vf.uk.dal.device.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.service.DeviceDetailsService;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags="DeviceDetails")
@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
/**
 * 
 * 1.Controller should able handle all the request and response for the device
 * services. 2.Controller should able to produce and consume Json Format for the
 * device services. 3.The service layer needs to be invoked from the device
 * services inside the controller.
 *
 */
@Slf4j
public class DeviceDetailsController {

	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String DEVICE_ID = "deviceId";
	public static final String JOURNEY_TYPE = "journeyType";
	public static final String OFFER_CODE = "offerCode";
	public static final String DEVICE_ID_IS_EMPTY = "Device Id is Empty";
	@Autowired
	DeviceDetailsService deviceDetailsService;
	
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
	 * Handles requests for getDeviceDetails Service with input as deviceId.
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return DeviceDetails
	 */
	@ApiOperation(value = "Get the device details for the given device Id", notes = "The service gets the details of the device specially price, equipment, specification, features, merchandising, etc in the response.", response = DeviceDetails.class)
	@RequestMapping(value = "/device/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DeviceDetails.class),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.model.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.model.Error.class) })
	public DeviceDetails getDeviceDetails(
			@NotNull @ApiParam(value = "Unique Id of the device being requested", required = true) @PathVariable("deviceId") String deviceId,
			@ApiParam(value = "Type of journey that the user undertakes e.g. \"Acquisition\", \"upgrade\", \"ils\" etc.") @RequestParam(value = "journeyType", required = false) String journeyType,
			@ApiParam(value = "Offer code that defines what type of promotional discount needs to be displaced.") @RequestParam(value = "offerCode", required = false) String offerCode) {
		DeviceDetails deviceDetails;
		log.info( ":::::::Test Logger for VSTS migration And Validate Pipeline Validation::::::::");
		Validator.validateDeviceDetails(deviceId);
		String journeyTypeLocal = StringUtils.isNotBlank(journeyType) ? journeyType
				: JOURNEY_TYPE_ACQUISITION;
		deviceDetails = deviceDetailsService.getDeviceDetails(deviceId, journeyTypeLocal, offerCode);

		return deviceDetails;
	}
	/**
	 * Returns List of Device details for the given List of devices
	 * 
	 * @param queryParams
	 * @return
	 */
	@ApiIgnore
	@RequestMapping(value = "/device/", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<DeviceDetails> getListOfDeviceDetails(@RequestParam Map<String, String> queryParams) {

		if (!queryParams.isEmpty() && Validator.validateDeviceId(queryParams)) {

			log.info( "Query parameter(s) passed in the request " + queryParams);
			List<DeviceDetails> listOfDeviceDetails;

			String deviceId = queryParams.containsKey(DEVICE_ID) ? queryParams.get(DEVICE_ID)
					: null;
			String journeyType = queryParams.containsKey(JOURNEY_TYPE)
					? queryParams.get(JOURNEY_TYPE) : null;
			String offerCode = queryParams.containsKey(OFFER_CODE) ? queryParams.get(OFFER_CODE)
					: null;

			if (deviceId != null) {
				log.info(
						"Get the list of device details for the device id passed as request params " + deviceId);
				log.info( "Start -->  calling  getListOfDeviceDetails");
				listOfDeviceDetails = deviceDetailsService.getListOfDeviceDetails(deviceId, offerCode, journeyType);
				log.info( "End -->  calling  getListofDeviceDetails");
			} else {
				log.error( DEVICE_ID_IS_EMPTY);
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
			}
			return listOfDeviceDetails;
		} else {
			log.error( ExceptionMessages.INVALID_QUERY_PARAMS);
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
		}

	}
}
