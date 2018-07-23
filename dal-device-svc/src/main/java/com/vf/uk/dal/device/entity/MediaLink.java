package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MediaLink
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class MediaLink {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("value")
	private String value = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("priority")
	private Integer priority = null;

}
