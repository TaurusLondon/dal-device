package com.vf.uk.dal.device.client.entity.customer;

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
