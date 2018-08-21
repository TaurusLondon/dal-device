package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Price
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class Price {
	@JsonProperty("gross")
	private String gross = null;

	@JsonProperty("net")
	private String net = null;

	@JsonProperty("vat")
	private String vat = null;
}
