package com.vf.uk.dal.utility.solr.entity;

import java.util.List;

import lombok.Data;

/**
 * 
 * PriceInfo
 *
 */
@Data
public class PriceInfo {
	private BundlePrice bundlePrice;

	private HardwarePrice hardwarePrice;

	private List<OfferAppliedPriceDetails> offerAppliedPrices;
}