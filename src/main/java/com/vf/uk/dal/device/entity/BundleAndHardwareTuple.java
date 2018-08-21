package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleAndHardwareTuple
 */
@Data
public class BundleAndHardwareTuple {
	@JsonProperty("hardwareId")
	private String hardwareId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

}
