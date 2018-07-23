package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import lombok.Data;

@Data
public class CacheProductModel {

	private String id;

	private Float rating;

	private String leadPlanId;

	private String productGroupName;

	private String productGroupId;

	private String upgradeLeadPlanId;

	private String nonUpgradeLeadPlanId;

	private List<String> merchandisingMedia;

	private Float oneOffGrossPrice;

	private Float oneOffNetPrice;

	private Float oneOffVatPrice;

	private Float oneOffDiscountedGrossPrice;

	private Float oneOffDiscountedNetPrice;

	private Float oneOffDiscountedVatPrice;

	private Float bundleMonthlyPriceGross;

	private Float bundleMonthlyPriceNet;

	private Float bundleMonthlyPriceVat;

	private Float bundleMonthlyDiscPriceGross;

	private Float bundleMonthlyDiscPriceNet;

	private Float bundleMonthlyDiscPriceVat;

	private List<DeviceFinancingOption> financingOptions = null;
}
