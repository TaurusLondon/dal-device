package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundlePriceOverride
 * @author manoj.bera
 *
 */
@Data
public class BundlePriceOverride {

	@JsonProperty("deviceId")
	private String deviceId;

	@JsonProperty("priceNet")
	private float priceNet;

	@JsonProperty("priceGross")
	private float priceGross;

	@JsonProperty("priceVAT")
	private float priceVAT;

	@JsonProperty("productLine")
	private String productLine;
}
