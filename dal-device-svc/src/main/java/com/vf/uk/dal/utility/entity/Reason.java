package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Reason
 *
 */
@Data
public class Reason {
	
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("value")
	private String value = null;
}
