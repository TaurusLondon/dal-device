package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Allowance class
 *
 */
@Data
public class Allowance {
	private String type;

	private String value;

	private String uom;

	@JsonProperty("displayUOM")
	private String displayUom;

	private String tilUOM;

	private String productId;

	private Long conversionFactor;
}