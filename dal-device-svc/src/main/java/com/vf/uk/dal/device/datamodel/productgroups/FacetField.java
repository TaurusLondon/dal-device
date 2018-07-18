package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

public class FacetField {

	private String name;

	private List<Count> values;

	private int valueCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Count> getValues() {
		return values;
	}

	public void setValues(List<Count> values) {
		this.values = values;
	}

	public int getValueCount() {
		return valueCount;
	}

	public void setValueCount(int valueCount) {
		this.valueCount = valueCount;
	}

}
