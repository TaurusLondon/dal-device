package com.vf.uk.dal.device.entity;

import java.util.List;

import lombok.Data;

/**
 * RequestForBundleAndHardware
 */
@Data
public class RequestForBundleAndHardware {
	private String offerCode = null;

	private String packageType = null;

	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;

}
