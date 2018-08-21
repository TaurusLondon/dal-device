package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * RequestForBundleAndHardware
 */
@Data
public class RequestForBundleAndHardware {
	
	@JsonProperty("offerCode")
	private String offerCode = null;

	@JsonProperty("packageType")
	private String packageType = null;

	@JsonProperty("bundleAndHardwareList")
	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;

}
