package com.vf.uk.dal.utility.solr.entity;

import java.util.List;

public class PriceInfo
{
    private BundlePrice bundlePrice;

    private HardwarePrice hardwarePrice;

    private List<OfferAppliedPriceDetails> offerAppliedPrices;
    
    public List<OfferAppliedPriceDetails> getOfferAppliedPrices() {
		return offerAppliedPrices;
	}
	public void setOfferAppliedPrices(List<OfferAppliedPriceDetails> offerAppliedPrices) {
		this.offerAppliedPrices = offerAppliedPrices;
	}
	public void setBundlePrice(BundlePrice bundlePrice){
        this.bundlePrice = bundlePrice;
    }
    public BundlePrice getBundlePrice(){
        return this.bundlePrice;
    }
    public void setHardwarePrice(HardwarePrice hardwarePrice){
        this.hardwarePrice = hardwarePrice;
    }
    public HardwarePrice getHardwarePrice(){
        return this.hardwarePrice;
    }
}