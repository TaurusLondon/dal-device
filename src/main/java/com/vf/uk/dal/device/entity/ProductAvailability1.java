package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductAvailability
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class ProductAvailability1 {
	@JsonProperty("startDate")
	private String startDate = null;

	@JsonProperty("endDate")
	private String endDate = null;

	@JsonProperty("salesExpired")
	private Boolean salesExpired;
}
