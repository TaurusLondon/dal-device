package com.vf.uk.dal.device.client.entity.price;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundlePrice
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

@Data
public class BundlePrice {
	@JsonProperty("bundleId")
	private String bundleId = null;

	@JsonProperty("monthlyPrice")
	private Price monthlyPrice = null;

	@JsonProperty("monthlyDiscountPrice")
	private Price monthlyDiscountPrice = null;

	@JsonProperty("merchandisingPromotions")
	private MerchandisingPromotion merchandisingPromotions = null;

}
