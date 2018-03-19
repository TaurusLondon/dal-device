package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

public class HardwarePrice {

	private String hardwareId;
	private OneOffPrice oneOffPrice;
	private OneOffDiscountPrice oneOffDiscountPrice;
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
