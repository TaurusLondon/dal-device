package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductGroup {

	@JsonProperty("productGroupName")
	private String productGroupName;

	@JsonProperty("productGroupRole")
	private String productGroupRole;
}
