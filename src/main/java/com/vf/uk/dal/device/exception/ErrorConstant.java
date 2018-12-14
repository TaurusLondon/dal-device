package com.vf.uk.dal.device.exception;

public class ErrorConstant {
	public static final ThreadLocal<String> apiErrorCode = new ThreadLocal<>();
	
	private ErrorConstant(){}
}
