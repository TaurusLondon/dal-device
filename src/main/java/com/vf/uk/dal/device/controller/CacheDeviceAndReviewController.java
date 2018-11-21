package com.vf.uk.dal.device.controller;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.svc.CacheDeviceService;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.validator.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Api(tags="DeviceCacheAndReview")
@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
/**
 * 
 * @author sahil.monga
 *
 */
@Slf4j
public class CacheDeviceAndReviewController {

	public static final String DEVICE_ID = "deviceId";
	public static final String GROUP_TYPE = "groupType";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String STRING_DEVICE_NEARLY_NEW = "DEVICE_NEARLY_NEW";
	@Autowired
	CacheDeviceService cacheDeviceService;

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
	 * Saves the details of the devices into database
	 * 
	 * @return cacheDeviceTile
	 */
	@ApiOperation(value = "Cache the Device Tile Details in Solr.", notes = "Cache the Device Tile Details in Solr.", response = CacheDeviceTileResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 201, message = "Success", response = CacheDeviceTileResponse.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })

	@RequestMapping(value = "/deviceTile/cacheDeviceTile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CacheDeviceTileResponse> cacheDeviceTile(
			@ApiParam(value = "Device group Type", required = true) @RequestParam("groupType") String groupType) {
		
		if (StringUtils.isNotBlank(groupType)) {
			if (StringUtils.containsIgnoreCase(groupType, STRING_DEVICE_PAYG)
					|| StringUtils.containsIgnoreCase(groupType, STRING_DEVICE_PAYM)
					|| StringUtils.containsIgnoreCase(groupType, STRING_DEVICE_NEARLY_NEW)) {
				CacheDeviceTileResponse cacheDeviceTileResponse = cacheDeviceService.insertCacheDeviceToDb();
				ResponseEntity<CacheDeviceTileResponse> response = new ResponseEntity<>(cacheDeviceTileResponse,
						HttpStatus.CREATED);
				cacheDeviceService.cacheDeviceTile(groupType, cacheDeviceTileResponse.getJobId(),
						CatalogServiceAspect.CATALOG_VERSION.get());

				return response;
			} else {
				throw new ApplicationException(ExceptionMessages.INVALID_INPUT_GROUP_TYPE);
			}
		} else
			throw new ApplicationException(ExceptionMessages.NULL_OR_EMPTY_GROUP_TYPE);
	}

	/**
	 * 
	 * @param jobId
	 * @return getCacheDeviceJobStatus
	 */
	@ApiOperation(value = "Get the Cache Device Tile job status.", notes = "Get the Cache Device Tile job status.", response = CacheDeviceTileResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = CacheDeviceTileResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })

	@RequestMapping(value = "/deviceTile/cacheDeviceTile/{jobId}/status", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public CacheDeviceTileResponse getCacheDeviceJobStatus(
			@ApiParam(value = "Device group Type", required = true) @PathVariable("jobId") String jobId) {

		return cacheDeviceService.getCacheDeviceJobStatus(jobId);

	}

	/**
	 * Returns review details for the given deviceId
	 * 
	 * @param deviceId
	 * @return getDeviceReviewDetails
	 */
	@ApiOperation(value = "Get the reviews for a specific device Id. Response is coming from Bazar Voice(third party) API.", notes = "The service gets the reviews of a particular device variant")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/device/{deviceId}/review", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public JSONObject getDeviceReviewDetails(
			@NotNull @ApiParam(value = "Unique Id of the device for which the review is being requested", required = true) @PathVariable(DEVICE_ID) String deviceId) {

		Validator.validateDeviceId(deviceId);
		log.info( "Start -->  calling  getDeviceReviewDetails");
		return cacheDeviceService.getDeviceReviewDetails(deviceId);
	}
}
