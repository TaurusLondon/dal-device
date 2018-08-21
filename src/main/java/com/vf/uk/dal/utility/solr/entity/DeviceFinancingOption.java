package com.vf.uk.dal.utility.solr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DeviceFinancingOption
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-17T05:45:22.340Z")
@Data
public class DeviceFinancingOption {

	@JsonProperty("financeTerm")
	private String financeTerm = null;

	@JsonProperty("financeProvider")
	private String financeProvider = null;

	@JsonProperty("apr")
	private String apr = null;

	@JsonProperty("monthlyPrice")
	private Price monthlyPrice = null;

	@JsonProperty("totalPriceWithInterest")
	private Price totalPriceWithInterest = null;

	@JsonProperty("deviceFinancingId")
	private String deviceFinancingId = null;

}
