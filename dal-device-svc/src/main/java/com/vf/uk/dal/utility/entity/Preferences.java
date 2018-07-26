package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Preferences
 *
 */
@Data
public class Preferences {
	
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("dataTypeCode")
	private String dataTypeCode = null;

	@JsonProperty("value")
	private String value = null;
}
