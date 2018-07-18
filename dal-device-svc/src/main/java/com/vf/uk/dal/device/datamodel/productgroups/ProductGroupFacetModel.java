package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

public class ProductGroupFacetModel {

	private List<ProductGroupModel> listOfProductGroups;

	private List<FacetField> listOfFacetsFields;

	private Long numFound;

	public List<ProductGroupModel> getListOfProductGroups() {
		return listOfProductGroups;
	}

	public void setListOfProductGroups(List<ProductGroupModel> listOfProductGroups) {
		this.listOfProductGroups = listOfProductGroups;
	}

	public List<FacetField> getListOfFacetsFields() {
		return listOfFacetsFields;
	}

	public void setListOfFacetsFields(List<FacetField> listOfFacetsFields) {
		this.listOfFacetsFields = listOfFacetsFields;
	}

	public Long getNumFound() {
		return numFound;
	}

	public void setNumFound(Long numFound) {
		this.numFound = numFound;
	}

}
