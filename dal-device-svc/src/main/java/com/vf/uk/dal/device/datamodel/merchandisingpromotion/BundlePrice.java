package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BundlePrice {
	@JsonProperty("bundleId")
	private String bundleId;
	@JsonProperty("monthlyPrice")
	private MonthlyPrice monthlyPrice;
	@JsonProperty("monthlyDiscountPrice")
	private MonthlyDiscountPrice monthlyDiscountPrice;

	

}
