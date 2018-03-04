package com.vodafone.product.pojo;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

@Portable
public class Specification {
	@PortableProperty(1)
	protected String name;
	@PortableProperty(2)
	protected String value;
	@PortableProperty(3)
	protected Long priority;
	@PortableProperty(4)
	protected boolean comparable;
	@PortableProperty(5)
	protected boolean isKey;
	@PortableProperty(6)
	protected String valueType;
	@PortableProperty(7)
	protected String valueUOM;
	@PortableProperty(8)
	protected String footNote;
	@PortableProperty(9)
	protected String description;
	@PortableProperty(10)
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
