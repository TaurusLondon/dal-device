package com.vf.uk.dal.device.controller;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.common.context.ServiceContext;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.urlparams.FilterCriteria;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.svc.CacheDeviceService;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.validator.Validator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CacheDeviceAndReviewController {

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
	@ApiOperation(value = "Cache the Device Tile Details in Solr.", notes = "Cache the Device Tile Details in Solr.", response = CacheDeviceTileResponse.class, tags = {
			"DeviceTile", })
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 201, message = "Success", response = CacheDeviceTileResponse.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })

	@RequestMapping(value = "/deviceTile/cacheDeviceTile", method = RequestMethod.POST, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public ResponseEntity<CacheDeviceTileResponse> cacheDeviceTile() {
		String groupType = getFilterValue(Constants.GROUP_TYPE);

		if (StringUtils.isNotBlank(groupType)) {
			if (StringUtils.containsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYG)
					|| StringUtils.containsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYM)
					|| StringUtils.containsIgnoreCase(groupType, Constants.STRING_DEVICE_NEARLY_NEW)) {
				CacheDeviceTileResponse cacheDeviceTileResponse = cacheDeviceService.insertCacheDeviceToDb();
				ResponseEntity<CacheDeviceTileResponse> response = new ResponseEntity<>(cacheDeviceTileResponse,
						HttpStatus.CREATED);
				cacheDeviceService.cacheDeviceTile(groupType, cacheDeviceTileResponse.getJobId(),
						Constants.CATALOG_VERSION.get());

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
	@ApiOperation(value = "Get the Cache Device Tile job status.", notes = "Get the Cache Device Tile job status.", response = CacheDeviceTileResponse.class, tags = {
			"DeviceTile", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = CacheDeviceTileResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })

	@RequestMapping(value = "/deviceTile/cacheDeviceTile/{jobId}/status", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
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
	@ApiOperation(value = "Get the reviews for a specific device Id. Response is coming from Bazar Voice(third party) API.", notes = "The service gets the reviews of a particular device variant", tags = {
			"Review", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/device/{deviceId}/review", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
	public JSONObject getDeviceReviewDetails(
			@NotNull @ApiParam(value = "Unique Id of the device for which the review is being requested", required = true) @PathVariable(Constants.DEVICE_ID) String deviceId) {

		Validator.validateDeviceId(deviceId);
		LogHelper.info(this, "Start -->  calling  getDeviceReviewDetails");
		return cacheDeviceService.getDeviceReviewDetails(deviceId);
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
}
