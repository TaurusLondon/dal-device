package com.vf.uk.dal.device.datamodel.bundle;


//import javax.xml.bind.annotation.XmlElement;

public class Specification {
	
	 
    private String name;
	
    private String value;
	
    private Long priority;
	
    private boolean comparable;
	
    private boolean isKey;
	
    private String valueType;
	
	private String valueUOM;
	
	private String footNote;
	
	private String description;
	
	private boolean hideInList;
	
	
	public Specification() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
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


	public boolean isKey() {
		return isKey;
	}


	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}


	public String getValueType() {
		return valueType;
	}


	public void setValueType(String valueType) {
		this.valueType = valueType;
	}


	public String getValueUOM() {
		return valueUOM;
	}


	public void setValueUOM(String valueUOM) {
		this.valueUOM = valueUOM;
	}


	public String getFootNote() {
		return footNote;
	}


	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isHideInList() {
		return hideInList;
	}


	public void setHideInList(boolean hideInList) {
		this.hideInList = hideInList;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (comparable ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((footNote == null) ? 0 : footNote.hashCode());
		result = prime * result + (hideInList ? 1231 : 1237);
		result = prime * result + (isKey ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
		result = prime * result + ((valueUOM == null) ? 0 : valueUOM.hashCode());
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
		Specification other = (Specification) obj;
		if (comparable != other.comparable)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (footNote == null) {
			if (other.footNote != null)
				return false;
		} else if (!footNote.equals(other.footNote))
			return false;
		if (hideInList != other.hideInList)
			return false;
		if (isKey != other.isKey)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (valueType == null) {
			if (other.valueType != null)
				return false;
		} else if (!valueType.equals(other.valueType))
			return false;
		if (valueUOM == null) {
			if (other.valueUOM != null)
				return false;
		} else if (!valueUOM.equals(other.valueUOM))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Specification [name=" + name + ", value=" + value + ", priority=" + priority + ", comparable="
				+ comparable + ", isKey=" + isKey + ", valueType=" + valueType + ", valueUOM=" + valueUOM
				+ ", footNote=" + footNote + ", description=" + description + ", hideInList=" + hideInList + "]";
	}


	}
