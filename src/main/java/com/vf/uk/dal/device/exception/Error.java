package com.vf.uk.dal.device.exception;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vf.uk.dal.common.context.ServiceContext;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Error
{
	private String errorCode;
	private String errorMessage;
	private String referenceId;
	@JsonIgnore
	private int statusCode;
	
	public Error(int statusCode, String errorCode, String errorMessage)
	{
		setStatusCode(statusCode);
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
		setReferenceId(ServiceContext.getCorrelationId());
	}
}

