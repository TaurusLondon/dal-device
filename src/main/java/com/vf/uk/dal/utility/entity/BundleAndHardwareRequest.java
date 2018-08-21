package com.vf.uk.dal.utility.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.utils.Constants;

import lombok.Data;

/**
 * RequestForBundleAndHardware
 */
@Data
public class BundleAndHardwareRequest {

	@JsonProperty("bundleAndHardwareList")
	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;

	@JsonProperty("journeyType")
	private String journeyType = Constants.JOURNEY_TYPE_ACQUISITION;

}
