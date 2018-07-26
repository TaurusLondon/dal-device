package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Group {

	@JsonProperty("name")
	private String name;

	@JsonProperty("groupPriority")
	private Long groupPriority;

	@JsonProperty("groupType")
	private String groupType;
	@JsonIgnore
	@JsonProperty("version")
	private String version;
	@JsonProperty("members")
	private List<Member> members;
	@JsonProperty("groupId")
	private Integer groupId;

	
}
