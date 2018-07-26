package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BoxPrice {

	@JsonProperty("priceNet")
	private Double priceNet;

	@JsonProperty("priceGross")
	private Double priceGross;

	@JsonProperty("priceVAT")
	private Double priceVAT;

}
