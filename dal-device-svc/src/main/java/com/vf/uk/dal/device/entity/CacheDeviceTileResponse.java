package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class CacheDeviceTileResponse.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-17T15:39:15.544Z")
@Data
public class CacheDeviceTileResponse {
	@JsonProperty("jobId")
	private String jobId = null;

	@JsonProperty("jobStatus")
	private String jobStatus = null;

}
