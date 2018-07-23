package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import lombok.Data;

@Data
public class PriceInfo {

	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private List<OfferAppliedPriceDetails> offerAppliedPrices;

	
}
