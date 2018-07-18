package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

public class HardwarePrice {

	private String hardwareId;
	private OneOffPrice oneOffPrice;
	private OneOffDiscountPrice oneOffDiscountPrice;
	private List<DeviceFinancingOption> financingOptions = null;

	public List<DeviceFinancingOption> getFinancingOptions() {
		return financingOptions;
	}

	public void setFinancingOptions(List<DeviceFinancingOption> financingOptions) {
		this.financingOptions = financingOptions;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	public OneOffPrice getOneOffPrice() {
		return oneOffPrice;
	}

	public void setOneOffPrice(OneOffPrice oneOffPrice) {
		this.oneOffPrice = oneOffPrice;
	}

	public OneOffDiscountPrice getOneOffDiscountPrice() {
		return oneOffDiscountPrice;
	}

	public void setOneOffDiscountPrice(OneOffDiscountPrice oneOffDiscountPrice) {
		this.oneOffDiscountPrice = oneOffDiscountPrice;
	}

}
