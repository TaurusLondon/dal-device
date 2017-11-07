package com.vf.uk.dal.utility.solr.entity;

import java.util.Objects;

public class BundlePrice {
	private String bundleId;

	private MonthlyPrice monthlyPrice;

	private MonthlyDiscountPrice monthlyDiscountPrice;

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getBundleId() {
		return this.bundleId;
	}

	public void setMonthlyPrice(MonthlyPrice monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}

	public MonthlyPrice getMonthlyPrice() {
		return this.monthlyPrice;
	}

	public void setMonthlyDiscountPrice(MonthlyDiscountPrice monthlyDiscountPrice) {
		this.monthlyDiscountPrice = monthlyDiscountPrice;
	}

	public MonthlyDiscountPrice getMonthlyDiscountPrice() {
		return this.monthlyDiscountPrice;
	}

	public BundlePrice monthlyDiscountPrice(MonthlyDiscountPrice monthlyDiscountPrice) {
		this.monthlyDiscountPrice = monthlyDiscountPrice;
		return this;
	}

	public BundlePrice monthlyPrice(MonthlyPrice monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
		return this;
	}

	public BundlePrice bundleId(String bundleId) {
		this.bundleId = bundleId;
		return this;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BundlePrice bundlePrice = (BundlePrice) o;
		return Objects.equals(this.bundleId, bundlePrice.bundleId)
				&& Objects.equals(this.monthlyPrice, bundlePrice.monthlyPrice)
				&& Objects.equals(this.monthlyDiscountPrice, bundlePrice.monthlyDiscountPrice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bundleId, monthlyPrice, monthlyDiscountPrice);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BundlePrice {\n");

		sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
		sb.append("    monthlyPrice: ").append(toIndentedString(monthlyPrice)).append("\n");
		sb.append("    monthlyDiscountPrice: ").append(toIndentedString(monthlyDiscountPrice)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}