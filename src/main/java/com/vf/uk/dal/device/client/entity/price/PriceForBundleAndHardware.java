package com.vf.uk.dal.device.client.entity.price;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * PriceForBundleAndHardware
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-17T05:45:22.340Z")
@Data
public class PriceForBundleAndHardware {
	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice = null;

	@JsonProperty("hardwarePrice")
	private HardwarePrice hardwarePrice = null;

	@JsonProperty("oneOffPrice")
	private Price oneOffPrice = null;

	@JsonProperty("oneOffDiscountPrice")
	private Price oneOffDiscountPrice = null;

	@JsonProperty("monthlyPrice")
	private Price monthlyPrice = null;

	@JsonProperty("monthlyDiscountPrice")
	private Price monthlyDiscountPrice = null;

	@JsonProperty("stepPrices")
	private List<StepPricingInfo> stepPrices = null;
}
