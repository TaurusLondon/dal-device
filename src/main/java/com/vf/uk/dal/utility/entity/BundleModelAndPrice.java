package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.entity.BundlePrice;

import lombok.Data;

@Data
public class BundleModelAndPrice {

	@JsonProperty("bundleModel")
	private BundleModel bundleModel;

	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice;

}
