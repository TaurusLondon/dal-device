package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DeviceTile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-30T13:33:13.488Z")
@Data
public class DeviceTile {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("groupType")
	private String groupType = null;

	@JsonProperty("rating")
	private String rating = null;

	@JsonProperty("reviewCount")
	private String reviewCount = null;

	@JsonProperty("deviceSummary")
	private List<DeviceSummary> deviceSummary = null;
}
