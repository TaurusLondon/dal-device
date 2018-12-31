package com.vf.uk.dal.device.client.entity.catalogue;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BundleAndHardwareTuple
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BundleAndHardwareTuple {
	@JsonProperty("hardwareId")
	private String hardwareId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

}
