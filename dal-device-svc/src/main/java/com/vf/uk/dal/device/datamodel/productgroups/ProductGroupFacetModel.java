package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductGroupFacetModel
 * @author manoj.bera
 *
 */
@Data
public class ProductGroupFacetModel {
	@JsonProperty("listOfProductGroups")
	private List<ProductGroupModel> listOfProductGroups;
	@JsonProperty("listOfFacetsFields")
	private List<FacetField> listOfFacetsFields;
	@JsonProperty("numFound")
	private Long numFound;

	
}
