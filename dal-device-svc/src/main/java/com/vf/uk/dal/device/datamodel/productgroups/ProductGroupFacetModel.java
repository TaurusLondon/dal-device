package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductGroupFacetModel {
	@JsonProperty("listOfProductGroups")
	private List<ProductGroupModel> listOfProductGroups;
	@JsonProperty("listOfFacetsFields")
	private List<FacetField> listOfFacetsFields;
	@JsonProperty("numFound")
	private Long numFound;

	
}
