package com.vf.uk.dal.device.datamodel.productgroups;

public class Count {

	private String name;

	private long count;

	private FacetField facetField;

	private String asFilterQuery;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public FacetField getFacetField() {
		return facetField;
	}

	public void setFacetField(FacetField facetField) {
		this.facetField = facetField;
	}

	public String getAsFilterQuery() {
		return asFilterQuery;
	}

	public void setAsFilterQuery(String asFilterQuery) {
		this.asFilterQuery = asFilterQuery;
	}

}
