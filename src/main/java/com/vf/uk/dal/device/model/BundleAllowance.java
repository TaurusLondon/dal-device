package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleAllowance
 */
@Data
public class BundleAllowance {
	@JsonProperty("type")
	private String type = null;

	@JsonProperty("value")
	private String value = null;

	@JsonProperty("uom")
	private String uom = null;

	@JsonProperty("displayUom")
	private String displayUom = null;

}
