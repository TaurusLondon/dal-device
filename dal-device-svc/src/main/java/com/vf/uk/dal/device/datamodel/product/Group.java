package com.vf.uk.dal.device.datamodel.product;

import java.util.List;


public class Group {

	protected String groupName;

	protected Long priority;

	protected boolean comparable;

	protected List<Specification> specifications;

	protected String type;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public boolean isComparable() {
		return comparable;
	}
	public void setComparable(boolean comparable) {
		this.comparable = comparable;
	}
	public List<Specification> getSpecifications() {
		return specifications;
	}
	public void setSpecifications(List<Specification> specifications) {
		this.specifications = specifications;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
