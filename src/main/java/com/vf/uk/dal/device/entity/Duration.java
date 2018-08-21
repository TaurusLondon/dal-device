package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Duration
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class Duration {
	@JsonProperty("uom")
	private String uom = null;

	@JsonProperty("value")
	private String value = null;
}
