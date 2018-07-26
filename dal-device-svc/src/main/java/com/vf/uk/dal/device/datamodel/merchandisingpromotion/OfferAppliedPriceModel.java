package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption;

import lombok.Data;
@Data
public class OfferAppliedPriceModel {
	@JsonProperty("id")
	private String id;
	@JsonProperty("productId")
	private String productId;
	@JsonProperty("offerCode")
	private String offerCode;
	@JsonProperty("bundleId")
	private String bundleId;
	@JsonProperty("monthlyGrossPrice")
	private Float monthlyGrossPrice;
	@JsonProperty("monthlyNetPrice")
	private Float monthlyNetPrice;
	@JsonProperty("monthlyVatPrice")
	private Float monthlyVatPrice;
	@JsonProperty("monthlyDiscountedGrossPrice")
	private Float monthlyDiscountedGrossPrice;
	@JsonProperty("monthlyDiscountedNetPrice")
	private Float monthlyDiscountedNetPrice;
	@JsonProperty("monthlyDiscountedVatPrice")
	private Float monthlyDiscountedVatPrice;
	@JsonProperty("hardwareId")
	private String hardwareId;
	@JsonProperty("oneOffGrossPrice")
	private Float oneOffGrossPrice;
	@JsonProperty("oneOffNetPrice")
	private Float oneOffNetPrice;
	@JsonProperty("oneOffVatPrice")
	private Float oneOffVatPrice;
	@JsonProperty("oneOffDiscountedGrossPrice")
	private Float oneOffDiscountedGrossPrice;
	@JsonProperty("oneOffDiscountedNetPrice")
	private Float oneOffDiscountedNetPrice;
	@JsonProperty("oneOffDiscountedVatPrice")
	private Float oneOffDiscountedVatPrice;
	@JsonProperty("financingOptions")
	private List<DeviceFinancingOption> financingOptions = null;
}
