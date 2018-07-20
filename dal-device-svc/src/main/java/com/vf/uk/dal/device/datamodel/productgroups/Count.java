package com.vf.uk.dal.device.datamodel.productgroups;

import lombok.Data;

@Data
public class Count {

	private String name;

	private long count;

	private FacetField facetField;

	private String asFilterQuery;

	
}
