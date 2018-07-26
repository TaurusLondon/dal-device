package com.vf.uk.dal.utility.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleDeviceAndAccessoryList
 */
@Data
public class BundleDeviceAndAccessoryList {

	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

	@JsonProperty("accessoryList")
	private List<String> accessoryList = null;

}
