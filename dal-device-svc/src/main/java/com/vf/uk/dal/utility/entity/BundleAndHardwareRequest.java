package com.vf.uk.dal.utility.entity;

import java.util.List;

import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.utils.Constants;

import lombok.Data;

/**
 * RequestForBundleAndHardware
 */
@Data
public class BundleAndHardwareRequest {

	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;
	private String journeyType = Constants.JOURNEY_TYPE_ACQUISITION;

}
