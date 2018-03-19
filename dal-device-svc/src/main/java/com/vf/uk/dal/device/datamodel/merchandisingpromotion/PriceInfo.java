package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

public class PriceInfo {

	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private List<OfferAppliedPriceDetails> offerAppliedPrices;

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

	public List<OfferAppliedPriceDetails> getOfferAppliedPrices() {
		return offerAppliedPrices;
	}

	public void setOfferAppliedPrices(List<OfferAppliedPriceDetails> offerAppliedPrices) {
		this.offerAppliedPrices = offerAppliedPrices;
	}

}
