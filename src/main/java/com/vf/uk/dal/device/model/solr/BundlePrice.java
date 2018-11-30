package com.vf.uk.dal.device.model.solr;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * BundlePrice
 *
 */
@Data
public class BundlePrice {
	@JsonProperty("bundleId")
	private String bundleId;
	@JsonProperty("monthlyPrice")
	private MonthlyPrice monthlyPrice;
	@JsonProperty("monthlyDiscountPrice")
	private MonthlyDiscountPrice monthlyDiscountPrice;

}