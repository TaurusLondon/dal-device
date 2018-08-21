package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductPriceOverride
 * @author manoj.bera
 *
 */
@Data
public class ProductPriceOverride {

	@JsonProperty("priceNet")
	private float priceNet;

	@JsonProperty("priceGross")
	private float priceGross;

	@JsonProperty("priceVAT")
	private float priceVAT;
}
