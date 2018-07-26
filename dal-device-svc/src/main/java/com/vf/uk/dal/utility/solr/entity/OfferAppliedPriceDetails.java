package com.vf.uk.dal.utility.solr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author suresh.kumar
 *
 */
@Data
public class OfferAppliedPriceDetails {
	@JsonProperty("deviceId")
	private String deviceId;
	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice;
	@JsonProperty("hardwarePrice")
	private HardwarePrice hardwarePrice;
	@JsonProperty("offerCode")
	private String offerCode;
	@JsonProperty("journeyType")
	private String journeyType;
}
