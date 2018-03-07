package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;


public class Group {
	
	
	
    private String groupName;
	
    private Long priority;
	
    private boolean comparable;
	
    private List<Specification> specifications;
	
	
	
	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (comparable ? 1231 : 1237);
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((specifications == null) ? 0 : specifications.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (comparable != other.comparable)
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (specifications == null) {
			if (other.specifications != null)
				return false;
		} else if (!specifications.equals(other.specifications))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Group [groupName=" + groupName + ", priority=" + priority + ", comparable=" + comparable
				+ ", specifications=" + specifications + "]";
	}
    
   

}
