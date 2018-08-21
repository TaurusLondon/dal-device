package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * SpecificationGroup
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class SpecificationGroup {
	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("priority")
	private Integer priority = null;

	@JsonProperty("comparable")
	private Boolean comparable = null;

	@JsonProperty("specifications")
	private List<Specification> specifications = null;

}
