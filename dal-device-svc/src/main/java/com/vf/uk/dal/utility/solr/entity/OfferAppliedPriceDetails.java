package com.vf.uk.dal.utility.solr.entity;

public class OfferAppliedPriceDetails {
	private String deviceId;
	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private String offerCode;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public BundlePrice getBundlePrice() {
		return this.bundlePrice;
	}

	public void setBundlePrice(BundlePrice bundlePrice) {
		this.bundlePrice = bundlePrice;
	}

	public HardwarePrice getHardwarePrice() {
		return this.hardwarePrice;
	}

	public void setHardwarePrice(HardwarePrice hardwarePrice) {
		this.hardwarePrice = hardwarePrice;
	}

	public String getOfferCode() {
		return this.offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String toString() {
		return "OfferAppliedPriceDetails [deviceId=" + this.deviceId + ", bundlePrice=" + this.bundlePrice
				+ ", hardwarePrice=" + this.hardwarePrice + ", offerCode=" + this.offerCode + "]";
	}
}
