package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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
