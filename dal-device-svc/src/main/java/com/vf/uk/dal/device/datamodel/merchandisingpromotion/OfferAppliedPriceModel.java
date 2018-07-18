package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption;

public class OfferAppliedPriceModel {

	private String id;

	private String productId;

	private String offerCode;

	private String bundleId;

	private Float monthlyGrossPrice;

	private Float monthlyNetPrice;

	private Float monthlyVatPrice;

	private Float monthlyDiscountedGrossPrice;

	private Float monthlyDiscountedNetPrice;

	private Float monthlyDiscountedVatPrice;

	private String hardwareId;

	private Float oneOffGrossPrice;

	private Float oneOffNetPrice;

	private Float oneOffVatPrice;

	private Float oneOffDiscountedGrossPrice;

	private Float oneOffDiscountedNetPrice;

	private Float oneOffDiscountedVatPrice;

	private List<DeviceFinancingOption> financingOptions = null;

	public List<DeviceFinancingOption> getFinancingOptions() {
		return financingOptions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public Float getMonthlyGrossPrice() {
		return monthlyGrossPrice;
	}

	public void setMonthlyGrossPrice(Float monthlyGrossPrice) {
		this.monthlyGrossPrice = monthlyGrossPrice;
	}

	public Float getMonthlyNetPrice() {
		return monthlyNetPrice;
	}

	public void setMonthlyNetPrice(Float monthlyNetPrice) {
		this.monthlyNetPrice = monthlyNetPrice;
	}

	public Float getMonthlyVatPrice() {
		return monthlyVatPrice;
	}

	public void setMonthlyVatPrice(Float monthlyVatPrice) {
		this.monthlyVatPrice = monthlyVatPrice;
	}

	public Float getMonthlyDiscountedGrossPrice() {
		return monthlyDiscountedGrossPrice;
	}

	public void setMonthlyDiscountedGrossPrice(Float monthlyDiscountedGrossPrice) {
		this.monthlyDiscountedGrossPrice = monthlyDiscountedGrossPrice;
	}

	public Float getMonthlyDiscountedNetPrice() {
		return monthlyDiscountedNetPrice;
	}

	public void setMonthlyDiscountedNetPrice(Float monthlyDiscountedNetPrice) {
		this.monthlyDiscountedNetPrice = monthlyDiscountedNetPrice;
	}

	public Float getMonthlyDiscountedVatPrice() {
		return monthlyDiscountedVatPrice;
	}

	public void setMonthlyDiscountedVatPrice(Float monthlyDiscountedVatPrice) {
		this.monthlyDiscountedVatPrice = monthlyDiscountedVatPrice;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
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

}
