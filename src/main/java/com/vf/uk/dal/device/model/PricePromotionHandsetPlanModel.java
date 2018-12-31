package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.model.solr.BundlePrice;
import com.vf.uk.dal.device.model.solr.HardwarePrice;

import lombok.Data;

/**
 * PricePromotionHandsetPlanModel
 * 
 * @author manoj.bera
 *
 */
@Data
public class PricePromotionHandsetPlanModel {
	@JsonProperty("planHardwarePricePromokey")
	private String planHardwarePricePromokey;
	@JsonProperty("planId")
	private String planId;
	@JsonProperty("hardwareId")
	private String hardwareId;
	@JsonProperty("journeyType")
	private String journeyType;
	@JsonProperty("promotionCode")
	private String promotionCode;
	@JsonProperty("bundlePrice")
	private BundlePrice bundlePrice;
	@JsonProperty("hardwarePrice")
	private HardwarePrice hardwarePrice;
	@JsonProperty("promotionsPackage")
	private MerchandisingPromotionsPackage promotionsPackage;
}