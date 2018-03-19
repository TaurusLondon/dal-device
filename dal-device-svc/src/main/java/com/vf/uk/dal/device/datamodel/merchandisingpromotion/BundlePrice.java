package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

public class BundlePrice {

	private String bundleId;
	private MonthlyPrice monthlyPrice;
	private MonthlyDiscountPrice monthlyDiscountPrice;
	public String getBundleId() {
		return bundleId;
	}
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}
	public MonthlyPrice getMonthlyPrice() {
		return monthlyPrice;
	}
	public void setMonthlyPrice(MonthlyPrice monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}
	public MonthlyDiscountPrice getMonthlyDiscountPrice() {
		return monthlyDiscountPrice;
	}
	public void setMonthlyDiscountPrice(MonthlyDiscountPrice monthlyDiscountPrice) {
		this.monthlyDiscountPrice = monthlyDiscountPrice;
	}
	
}
