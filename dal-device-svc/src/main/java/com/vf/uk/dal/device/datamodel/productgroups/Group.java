package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;


public class Group {

	protected String name;

	protected Long groupPriority;

	protected String groupType;

	protected String version;

	protected List<Member> members;

	protected Integer groupId;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getGroupPriority() {
		return groupPriority;
	}
	public void setGroupPriority(Long groupPriority) {
		this.groupPriority = groupPriority;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
