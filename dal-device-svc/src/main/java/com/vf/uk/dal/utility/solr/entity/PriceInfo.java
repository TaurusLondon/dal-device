package com.vf.uk.dal.utility.solr.entity;

import java.util.List;

/**
 * 
 * 
 *
 */
public class PriceInfo
{
    private BundlePrice bundlePrice;

    private HardwarePrice hardwarePrice;

    private List<OfferAppliedPriceDetails> offerAppliedPrices;
    
    /**
     * 
     * @return
     */
    public List<OfferAppliedPriceDetails> getOfferAppliedPrices() {
		return offerAppliedPrices;
	}
    /**
     * 
     * @param offerAppliedPrices
     */
	public void setOfferAppliedPrices(List<OfferAppliedPriceDetails> offerAppliedPrices) {
		this.offerAppliedPrices = offerAppliedPrices;
	}
	/**
	 * 
	 * @param bundlePrice
	 */
	public void setBundlePrice(BundlePrice bundlePrice){
        this.bundlePrice = bundlePrice;
    }
	/**
	 * 
	 * @return
	 */
    public BundlePrice getBundlePrice(){
        return this.bundlePrice;
    }
    /**
     * 
     * @param hardwarePrice
     */
    public void setHardwarePrice(HardwarePrice hardwarePrice){
        this.hardwarePrice = hardwarePrice;
    }
    /**
     * 
     * @return
     */
    public HardwarePrice getHardwarePrice(){
        return this.hardwarePrice;
    }
}