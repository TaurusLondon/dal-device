package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * FacetWithCount
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class FacetWithCount {
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("count")
	private long count;

}
