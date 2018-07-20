package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Group {

	protected String name;

	protected Long groupPriority;

	protected String groupType;
	@JsonIgnore
	protected String version;

	protected List<Member> members;

	protected Integer groupId;

	
}
