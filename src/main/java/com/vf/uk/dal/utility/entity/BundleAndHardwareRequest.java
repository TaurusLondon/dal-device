package com.vf.uk.dal.utility.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;

import lombok.Data;

/**
 * RequestForBundleAndHardware
 */
@Data
public class BundleAndHardwareRequest {

	@JsonProperty("bundleAndHardwareList")
	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;

	@JsonProperty("journeyType")
	private String journeyType = "Acquisition";

}
