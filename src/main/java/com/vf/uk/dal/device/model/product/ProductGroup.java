package com.vf.uk.dal.device.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductGroup
 * @author manoj.bera
 *
 */
@Data
public class ProductGroup {

	@JsonProperty("productGroupName")
	private String productGroupName;

	@JsonProperty("productGroupRole")
	private String productGroupRole;
}
