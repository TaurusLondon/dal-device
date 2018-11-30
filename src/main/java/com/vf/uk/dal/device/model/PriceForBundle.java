package com.vf.uk.dal.device.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.StepPricingInfo;

import lombok.Data;

/**
 * PriceForBundle
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-23T08:27:04.967Z")
@Data
public class PriceForBundle {
	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice = null;

	@JsonProperty("monthlyPrice")
	private Price monthlyPrice = null;

	@JsonProperty("monthlyDiscountPrice")
	private Price monthlyDiscountPrice = null;

	@JsonProperty("stepPrices")
	private List<StepPricingInfo> stepPrices = new ArrayList<>();

}
