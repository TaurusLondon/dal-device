package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import lombok.Data;

@Data
public class OfferAppliedPriceDetails {

	private String deviceId;
	private BundlePrice bundlePrice;
	private HardwarePrice hardwarePrice;
	private String offerCode;
	private String journeyType;

	
}
