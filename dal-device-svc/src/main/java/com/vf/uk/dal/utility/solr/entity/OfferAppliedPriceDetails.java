package com.vf.uk.dal.utility.solr.entity;

import lombok.Data;

/**
 * 
 * @author suresh.kumar
 *
 */
@Data
public class OfferAppliedPriceDetails {
	private String deviceId;
	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private String offerCode;
	private String journeyType;
}
