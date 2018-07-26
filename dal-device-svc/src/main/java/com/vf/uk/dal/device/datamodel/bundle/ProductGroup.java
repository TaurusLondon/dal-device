package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductGroup {

	@JsonProperty("productGroupName")
	private String productGroupName;

	@JsonProperty("groupRole")
	private String groupRole;

	public ProductGroup() {
		super();
	}
}
