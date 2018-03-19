package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

public class OfferAppliedPriceDetails {

	private String deviceId;
	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private String offerCode;
	private String journeyType;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public BundlePrice getBundlePrice() {
		return bundlePrice;
	}

	public void setBundlePrice(BundlePrice bundlePrice) {
		this.bundlePrice = bundlePrice;
	}

	public HardwarePrice getHardwarePrice() {
		return hardwarePrice;
	}

	public void setHardwarePrice(HardwarePrice hardwarePrice) {
		this.hardwarePrice = hardwarePrice;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getJourneyType() {
		return journeyType;
	}

	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}

}
