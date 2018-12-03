package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Allowance class
 *
 */
@Data
public class Allowance {
	
	@JsonProperty("type")
	private String type;

	@JsonProperty("value")
	private String value;

	@JsonProperty("uom")
	private String uom;

	@JsonProperty("displayUOM")
	private String displayUom;

	@JsonProperty("tilUOM")
	private String tilUOM;

	@JsonProperty("productId")
	private String productId;

	@JsonProperty("conversionFactor")
	private Long conversionFactor;
}