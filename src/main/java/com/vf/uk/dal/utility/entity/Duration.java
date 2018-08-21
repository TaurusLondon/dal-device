package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Duration
 */
@Data
public class Duration {
	
	@JsonProperty("uom")
	private String uom = null;


}
