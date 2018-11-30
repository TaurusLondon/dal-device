package com.vf.uk.dal.device.model.solr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * HardwarePrice
 *
 */
@Data
public class HardwarePrice {
	@JsonProperty("hardwareId")
	private String hardwareId;
	@JsonProperty("oneOffPrice")
	private OneOffPrice oneOffPrice;
	@JsonProperty("oneOffDiscountPrice")
	private OneOffDiscountPrice oneOffDiscountPrice;
	@JsonProperty("financingOptions")
	private List<DeviceFinancingOption> financingOptions = null;
}
