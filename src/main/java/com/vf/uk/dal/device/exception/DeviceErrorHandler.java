package com.vf.uk.dal.device.exception;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

import com.vf.uk.dal.authorization.filter.util.exception.AuthorizationException;
import com.vf.uk.dal.common.context.ServiceContext;
import com.vf.uk.dal.common.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
/**
 * SimoJourneyErrorHandler provides error handling for all Custom , HttpClient
 * and HttpServer Exceptions.
 */
@Slf4j
@ControllerAdvice
public class DeviceErrorHandler {

	@ExceptionHandler
	public ResponseEntity<?> handleException(final DeviceCustomException err) {
		Error error;
		
		if(NumberUtils.isDigits(err.getStatusCode())){
			error = new Error(Integer.valueOf(err.getStatusCode()) , err.getErrorCode(), err.getMessage());
			error.setReferenceId(ServiceContext.getCorrelationId());
			return ResponseEntity.status(Integer.valueOf(err.getStatusCode())).body(error);
		}else{
			error = new Error(Integer.valueOf(err.getErrorCode()) , err.getStatusCode(), err.getMessage());
			error.setReferenceId(ServiceContext.getCorrelationId());
			return ResponseEntity.status(Integer.valueOf(err.getErrorCode())).body(error);
		}
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleException(final ServletRequestBindingException err) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value() , "SJC_MISSING_MANDATORY_INPUT", err.getLocalizedMessage());
		error.setReferenceId(ServiceContext.getCorrelationId());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<?> handleException(final RestClientResponseException err) {
		ErrorResponse error = errorResponse(err);
		return ResponseEntity.status(err.getRawStatusCode()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<?> handleException(final MethodArgumentNotValidException err) {
		ObjectError objErr = err.getBindingResult().getAllErrors().get(0);
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value() , "SJC_INVALID_INPUT", objErr.getDefaultMessage());
		error.setReferenceId(ServiceContext.getCorrelationId());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}
	
	private ErrorResponse errorResponse(final RestClientResponseException err) {
		JSONParser parser = new JSONParser();
		JSONObject jsonError = new JSONObject();
		try {
			jsonError = (JSONObject) parser.parse(err.getResponseBodyAsString());
		} catch (org.json.simple.parser.ParseException pe) {
			log.error("Exception while parsing json object: " + pe);
		}
		String errorMessage = (String) jsonError.get("errorMessage");
		String errorCode = (String) jsonError.get("errorCode");
		int errCode = err.getRawStatusCode();
		ErrorResponse error = new ErrorResponse(errCode, errorCode, errorMessage);
		error.setReferenceId(ServiceContext.getCorrelationId());
		return error;
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleException(final AuthorizationException err) {
	 
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value() , err.getErrorCode(), err.getMessage());
		error.setReferenceId(ServiceContext.getCorrelationId());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	 }
	private String getErrorMessage(HttpMessageConversionException err) {
        String errorMessage = null;
        if (err.getMessage().contains("No enum constant")||
        		err.getMessage().contains("java.lang.NullPointerException")) {
            errorMessage = "Invalid Context Sent in the Request";
        } else if(err.getMessage().contains("Required request body is missing")) {
            errorMessage = "Required request body is missing";
        }
        return errorMessage;
	}
	@ExceptionHandler
	   public ResponseEntity<?> handleException(final HttpMessageConversionException err) {
	        String apiErrorCode = ErrorConstant.apiErrorCode.get();
	        String errorMessage = getErrorMessage(err);
	       Error error = new Error(HttpStatus.BAD_REQUEST.value() , StringUtils.isNotEmpty(apiErrorCode)?apiErrorCode:"device_purchase_create_device_grid_list_journey_failed", StringUtils.isNotEmpty(errorMessage)?errorMessage:err.getLocalizedMessage());
	       error.setReferenceId(ServiceContext.getCorrelationId());
	       return ResponseEntity.status(403).body(error);
	   }
}