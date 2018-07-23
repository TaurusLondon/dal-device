package com.vf.uk.dal.utility.entity;

import java.util.List;

import lombok.Data;

/**
 * BundleDeviceAndAccessoryList
 */
@Data
public class BundleDeviceAndAccessoryList {
	private String deviceId = null;

	private String bundleId = null;

	private List<String> accessoryList = null;

}
