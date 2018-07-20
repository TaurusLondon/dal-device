package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import lombok.Data;

@Data
public class ProductGroupFacetModel {

	private List<ProductGroupModel> listOfProductGroups;

	private List<FacetField> listOfFacetsFields;

	private Long numFound;

	
}
