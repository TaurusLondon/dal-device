package com.vf.uk.dal.device.client.entity.price;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleDeviceAndProductsList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T11:31:50.578Z")
@Data
public class BundleDeviceAndProductsList {
	@JsonProperty("offerCode")
	private String offerCode = null;

	@JsonProperty("packageType")
	private String packageType = null;

	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

	@JsonProperty("accessoryList")
	private List<String> accessoryList = null;

	@JsonProperty("extraList")
	private List<String> extraList = null;

	
}
