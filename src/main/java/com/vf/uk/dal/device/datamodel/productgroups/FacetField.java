package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * FacetField
 * @author manoj.bera
 *
 */
@Data
public class FacetField {
	@JsonProperty("name")
	private String name;
	@JsonProperty("values")
	private List<Count> values;
	@JsonProperty("valueCount")
	private int valueCount;

}
