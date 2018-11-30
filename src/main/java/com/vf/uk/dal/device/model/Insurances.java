package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Insurances
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class Insurances {
	@JsonProperty("insuranceList")
	private List<Insurance> insuranceList = null;

	@JsonProperty("minCost")
	private String minCost = null;

	
}
