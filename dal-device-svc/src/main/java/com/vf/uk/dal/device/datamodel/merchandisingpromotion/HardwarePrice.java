package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import lombok.Data;

@Data
public class HardwarePrice {

	private String hardwareId;
	private OneOffPrice oneOffPrice;
	private OneOffDiscountPrice oneOffDiscountPrice;
	private List<DeviceFinancingOption> financingOptions = null;

	
}
