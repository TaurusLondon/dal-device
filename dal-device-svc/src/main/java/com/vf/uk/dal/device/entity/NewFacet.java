package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * NewFacet
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class NewFacet {
	@JsonProperty("facetName")
	private String facetName = null;

	@JsonProperty("facetList")
	private List<FacetWithCount> facetList = null;
}
