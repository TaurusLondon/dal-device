package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * PriceInfo
 * @author manoj.bera
 *
 */
@Data
public class PriceInfo {
	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice;
	@JsonProperty("hardwarePrice")
	private HardwarePrice hardwarePrice;
	@JsonProperty("offerAppliedPrices")
	private List<OfferAppliedPriceDetails> offerAppliedPrices;

	
}
