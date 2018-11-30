package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Make
 */
@Data
public class Make {

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("count")
	private long count;

}
