package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Equipment
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class Equipment {
	@JsonProperty("make")
	private String make = null;

	@JsonProperty("model")
	private String model = null;

}
