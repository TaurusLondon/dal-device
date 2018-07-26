package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Group {

	@JsonProperty("groupName")
	private String groupName;

	@JsonProperty("priority")
	private Long priority;

	@JsonProperty("comparable")
	private boolean comparable;

	@JsonProperty("specifications")
	private List<Specification> specifications;

	public Group() {
		super();
	}
}
