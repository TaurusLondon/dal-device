package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * KeepDeviceChangePlanRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-08-21T05:02:16.131Z")
@Data
public class KeepDeviceChangePlanRequest {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

	@JsonProperty("allowedRecurringPriceLimit")
	private String allowedRecurringPriceLimit = null;

	@JsonProperty("plansLimit")
	private String plansLimit = null;
}
