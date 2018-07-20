package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Specification
 */

@Data
public class Specification {

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("value")
	private String value = null;

	@JsonProperty("priority")
	private Long priority = null;

	@JsonProperty("comparable")
	private Boolean comparable = null;

	@JsonProperty("isKey")
	private Boolean isKey = null;

	@JsonProperty("valueType")
	private String valueType = null;

	@JsonProperty("valueUOM")
	private String valueUOM = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("footNote")
	private String footNote = null;

	@JsonProperty("hideInList")
	private Boolean hideInList = null;
}
