package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import lombok.Data;

@Data
public class FacetField {

	private String name;

	private List<Count> values;

	private int valueCount;

}
