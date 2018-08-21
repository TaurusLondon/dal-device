package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * CacheProductModel
 * @author manoj.bera
 *
 */
@Data
public class CacheProductModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("rating")
	private Float rating;

	@JsonProperty("leadPlanId")
	private String leadPlanId;

	@JsonProperty("productGroupName")
	private String productGroupName;

	@JsonProperty("productGroupId")
	private String productGroupId;

	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;

	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;

	@JsonProperty("merchandisingMedia")
	private List<String> merchandisingMedia;

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

	@JsonProperty("bundleMonthlyPriceGross")
	private Float bundleMonthlyPriceGross;

	@JsonProperty("bundleMonthlyPriceNet")
	private Float bundleMonthlyPriceNet;

	@JsonProperty("bundleMonthlyPriceVat")
	private Float bundleMonthlyPriceVat;

	@JsonProperty("bundleMonthlyDiscPriceGross")
	private Float bundleMonthlyDiscPriceGross;

	@JsonProperty("bundleMonthlyDiscPriceNet")
	private Float bundleMonthlyDiscPriceNet;

	@JsonProperty("bundleMonthlyDiscPriceVat")
	private Float bundleMonthlyDiscPriceVat;

	@JsonProperty("financingOptions")
	private List<DeviceFinancingOption> financingOptions = null;
}
