package com.vodafone.productGroups.pojo;

import java.util.List;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

@Portable
public class Group {

	@PortableProperty(0)
	protected String name;
	@PortableProperty(1)
	protected Long groupPriority;
	@PortableProperty(2)
	protected String groupType;
	@PortableProperty(3)
	protected String version;
	@PortableProperty(4)
	protected List<Member> members;
	@PortableProperty(5)
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
