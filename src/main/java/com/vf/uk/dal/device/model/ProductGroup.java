package com.vf.uk.dal.device.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductGroup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-03T09:08:40.062Z")
@Data
public class ProductGroup {
	@JsonProperty("id")
	private Integer id = null;

	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("groupPriority")
	private String groupPriority = null;

	@JsonProperty("groupType")
	private String groupType = null;

	@JsonProperty("members")
	private List<Member> members = new ArrayList<>();
}
