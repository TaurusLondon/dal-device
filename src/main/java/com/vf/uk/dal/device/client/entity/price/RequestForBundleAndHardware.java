package com.vf.uk.dal.device.client.entity.price;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;

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
	@JsonProperty("billingType")
	private String billingType = null; 
	@JsonProperty("channel")
	private String channel;
	@JsonProperty("bundleAndHardwareList")
	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;
	@JsonProperty("activePackages")
	private List<ActivePackagesInRequest> activePackages = new ArrayList<>();
	@JsonProperty("affiliate")
	private boolean affiliate;
	@JsonProperty("customerType")
	private String customerType = null;

}
