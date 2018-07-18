package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

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

	public List<DeviceFinancingOption> getFinancingOptions() {
		return financingOptions;
	}

	public void setFinancingOptions(List<DeviceFinancingOption> financingOptions) {
		this.financingOptions = financingOptions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getLeadPlanId() {
		return leadPlanId;
	}

	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getUpgradeLeadPlanId() {
		return upgradeLeadPlanId;
	}

	public void setUpgradeLeadPlanId(String upgradeLeadPlanId) {
		this.upgradeLeadPlanId = upgradeLeadPlanId;
	}

	public String getNonUpgradeLeadPlanId() {
		return nonUpgradeLeadPlanId;
	}

	public void setNonUpgradeLeadPlanId(String nonUpgradeLeadPlanId) {
		this.nonUpgradeLeadPlanId = nonUpgradeLeadPlanId;
	}

	public List<String> getMerchandisingMedia() {
		return merchandisingMedia;
	}

	public void setMerchandisingMedia(List<String> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
	}

	public Float getOneOffGrossPrice() {
		return oneOffGrossPrice;
	}

	public void setOneOffGrossPrice(Float oneOffGrossPrice) {
		this.oneOffGrossPrice = oneOffGrossPrice;
	}

	public Float getOneOffNetPrice() {
		return oneOffNetPrice;
	}

	public void setOneOffNetPrice(Float oneOffNetPrice) {
		this.oneOffNetPrice = oneOffNetPrice;
	}

	public Float getOneOffVatPrice() {
		return oneOffVatPrice;
	}

	public void setOneOffVatPrice(Float oneOffVatPrice) {
		this.oneOffVatPrice = oneOffVatPrice;
	}

	public Float getOneOffDiscountedGrossPrice() {
		return oneOffDiscountedGrossPrice;
	}

	public void setOneOffDiscountedGrossPrice(Float oneOffDiscountedGrossPrice) {
		this.oneOffDiscountedGrossPrice = oneOffDiscountedGrossPrice;
	}

	public Float getOneOffDiscountedNetPrice() {
		return oneOffDiscountedNetPrice;
	}

	public void setOneOffDiscountedNetPrice(Float oneOffDiscountedNetPrice) {
		this.oneOffDiscountedNetPrice = oneOffDiscountedNetPrice;
	}

	public Float getOneOffDiscountedVatPrice() {
		return oneOffDiscountedVatPrice;
	}

	public void setOneOffDiscountedVatPrice(Float oneOffDiscountedVatPrice) {
		this.oneOffDiscountedVatPrice = oneOffDiscountedVatPrice;
	}

	public Float getBundleMonthlyPriceGross() {
		return bundleMonthlyPriceGross;
	}

	public void setBundleMonthlyPriceGross(Float bundleMonthlyPriceGross) {
		this.bundleMonthlyPriceGross = bundleMonthlyPriceGross;
	}

	public Float getBundleMonthlyPriceNet() {
		return bundleMonthlyPriceNet;
	}

	public void setBundleMonthlyPriceNet(Float bundleMonthlyPriceNet) {
		this.bundleMonthlyPriceNet = bundleMonthlyPriceNet;
	}

	public Float getBundleMonthlyPriceVat() {
		return bundleMonthlyPriceVat;
	}

	public void setBundleMonthlyPriceVat(Float bundleMonthlyPriceVat) {
		this.bundleMonthlyPriceVat = bundleMonthlyPriceVat;
	}

	public Float getBundleMonthlyDiscPriceGross() {
		return bundleMonthlyDiscPriceGross;
	}

	public void setBundleMonthlyDiscPriceGross(Float bundleMonthlyDiscPriceGross) {
		this.bundleMonthlyDiscPriceGross = bundleMonthlyDiscPriceGross;
	}

	public Float getBundleMonthlyDiscPriceNet() {
		return bundleMonthlyDiscPriceNet;
	}

	public void setBundleMonthlyDiscPriceNet(Float bundleMonthlyDiscPriceNet) {
		this.bundleMonthlyDiscPriceNet = bundleMonthlyDiscPriceNet;
	}

	public Float getBundleMonthlyDiscPriceVat() {
		return bundleMonthlyDiscPriceVat;
	}

	public void setBundleMonthlyDiscPriceVat(Float bundleMonthlyDiscPriceVat) {
		this.bundleMonthlyDiscPriceVat = bundleMonthlyDiscPriceVat;
	}
}
