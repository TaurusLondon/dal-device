package com.vf.uk.dal.device.datamodel.product;


public class Specification {
	protected String name;

	protected String value;

	protected Long priority;

	protected boolean comparable;

	protected boolean isKey;

	protected String valueType;

	protected String valueUOM;

	protected String footNote;

	protected String description;

	protected boolean hideInList;
	
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
	

}
