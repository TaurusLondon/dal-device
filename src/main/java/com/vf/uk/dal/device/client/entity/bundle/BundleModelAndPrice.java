package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;

import lombok.Data;

@Data
public class BundleModelAndPrice {

	@JsonProperty("bundleModel")
	private BundleModel bundleModel;

	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice;

}
