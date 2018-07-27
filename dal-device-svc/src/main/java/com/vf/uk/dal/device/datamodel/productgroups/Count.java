package com.vf.uk.dal.device.datamodel.productgroups;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Count
 * @author manoj.bera
 *
 */
@Data
public class Count {

	@JsonProperty("name")
	private String name;

	@JsonProperty("count")
	private long count;

	@JsonProperty("facetField")
	private FacetField facetField;

	@JsonProperty("asFilterQuery")
	private String asFilterQuery;

	
}
