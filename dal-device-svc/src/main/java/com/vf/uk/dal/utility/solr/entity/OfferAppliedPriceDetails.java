package com.vf.uk.dal.utility.solr.entity;

/**
 * 
 * @author suresh.kumar
 *
 */
public class OfferAppliedPriceDetails {
	private String deviceId;
	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private String offerCode;
	private String journeyType;
	/**
	 * 
	 * 
	 * @return
	 */
	public String getDeviceId() {
		return this.deviceId;
	}
	/**
	 * 
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 
	 * @return
	 */
	public BundlePrice getBundlePrice() {
		return this.bundlePrice;
	}
	/**
	 * 
	 * @param bundlePrice
	 */
	public void setBundlePrice(BundlePrice bundlePrice) {
		this.bundlePrice = bundlePrice;
	}
	/**
	 * 
	 * @return
	 */
	public HardwarePrice getHardwarePrice() {
		return this.hardwarePrice;
	}
	/**
	 * 
	 * @param hardwarePrice
	 */
	public void setHardwarePrice(HardwarePrice hardwarePrice) {
		this.hardwarePrice = hardwarePrice;
	}
	/**
	 * 
	 * @return
	 */
	public String getOfferCode() {
		return this.offerCode;
	}
	/**
	 * 
	 * @param offerCode
	 */
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getJourneyType() {
		return journeyType;
	}
	/**
	 * 
	 * @param journeyType
	 */
	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}
	/**
	 * 
	 */
	public String toString() {
		return "OfferAppliedPriceDetails [deviceId=" + this.deviceId + ", bundlePrice=" + this.bundlePrice
				+ ", hardwarePrice=" + this.hardwarePrice + ", offerCode=" + this.offerCode + ",journeyType=" +this.journeyType + "]";
	}
}
