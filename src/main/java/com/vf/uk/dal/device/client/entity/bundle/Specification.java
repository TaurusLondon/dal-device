package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Specification
 * @author manoj.bera
 *
 */
@Data
public class Specification {

	@JsonProperty("name")
	private String name;

	@JsonProperty("value")
	private String value;

	@JsonProperty("priority")
	private Long priority;

	@JsonProperty("comparable")
	private boolean comparable;

	@JsonProperty("isKey")
	private boolean isKey;

	@JsonProperty("valueType")
	private String valueType;

	@JsonProperty("valueUOM")
	private String valueUOM;

	@JsonProperty("footNote")
	private String footNote;

	@JsonProperty("description")
	private String description;

	@JsonProperty("hideInList")
	private boolean hideInList;
}
