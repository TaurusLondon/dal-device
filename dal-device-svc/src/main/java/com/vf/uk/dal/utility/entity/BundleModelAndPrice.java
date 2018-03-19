package com.vf.uk.dal.utility.entity;

import com.vf.uk.dal.device.datamodel.bundle.BundleModel;

public class BundleModelAndPrice {
	
	private BundleModel bundleModel;
	
	private BundlePrice bundlePrice;
/**
 * 
 * @return
 */
	public BundleModel getBundleModel() {
		return bundleModel;
	}
/**
 * 
 * @param bundleModel
 */
	public void setBundleModel(BundleModel bundleModel) {
		this.bundleModel = bundleModel;
	}
/**
 * 
 * @return
 */
	public BundlePrice getBundlePrice() {
		return bundlePrice;
	}
/**
 * 
 * @param bundlePrice
 */
	public void setBundlePrice(BundlePrice bundlePrice) {
		this.bundlePrice = bundlePrice;
	}

	
	

}
