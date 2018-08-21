package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Attributes
 */
@Data
public class Attributes {
	@JsonProperty("key")
	private String key = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("value")
	private String value = null;

	@JsonProperty("valueUOM")
	private String valueUOM = null;

}
