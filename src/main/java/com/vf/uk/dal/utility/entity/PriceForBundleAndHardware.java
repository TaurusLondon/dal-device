package com.vf.uk.dal.utility.entity;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.entity.BundlePrice;

import lombok.Data;

/**
 * PriceForBundleAndHardware
 */
@Validated
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
	@Valid
	private List<StepPricingInfo> stepPrices = null;
}
