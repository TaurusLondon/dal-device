package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

public class MerchandisingProduct {

	private String promotionId;

	private String productLine;

	private String productPath;

	private String action;

	private List<DiscountMP> discounts;

	public MerchandisingProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getProductPath() {
		return productPath;
	}

	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<DiscountMP> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<DiscountMP> discounts) {
		this.discounts = discounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((discounts == null) ? 0 : discounts.hashCode());
		result = prime * result + ((productLine == null) ? 0 : productLine.hashCode());
		result = prime * result + ((productPath == null) ? 0 : productPath.hashCode());
		result = prime * result + ((promotionId == null) ? 0 : promotionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerchandisingProduct other = (MerchandisingProduct) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (discounts == null) {
			if (other.discounts != null)
				return false;
		} else if (!discounts.equals(other.discounts))
			return false;
		if (productLine == null) {
			if (other.productLine != null)
				return false;
		} else if (!productLine.equals(other.productLine))
			return false;
		if (productPath == null) {
			if (other.productPath != null)
				return false;
		} else if (!productPath.equals(other.productPath))
			return false;
		if (promotionId == null) {
			if (other.promotionId != null)
				return false;
		} else if (!promotionId.equals(other.promotionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MerchandisingProduct [promotionId=" + promotionId + ", productLine=" + productLine + ", productPath="
				+ productPath + ", action=" + action + ", discounts=" + discounts + "]";
	}

}
