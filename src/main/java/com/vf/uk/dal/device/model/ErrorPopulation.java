package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Error population for integration test
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class ErrorPopulation {
	@JsonProperty("errorCode")
	private String code = null;

	@JsonProperty("errorMessage")
	private String message = null;

	@JsonProperty("referenceId")
	private String referenceId = null;
}
