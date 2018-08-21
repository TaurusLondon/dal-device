package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleDeviceSpecificPricingOverrides
 * @author manoj.bera
 *
 */
@Data
public class BundleDeviceSpecificPricingOverrides {

	@JsonProperty("bundlePriceOverride")
	private BundlePriceOverride bundlePriceOverride;

}
