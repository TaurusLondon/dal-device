package com.vf.uk.dal.device.datamodel.bundle;

public class ProductGroup {

	private String productGroupName;

	private String groupRole;

	public ProductGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductGroupRole() {
		return groupRole;
	}

	public void setProductGroupRole(String productGroupRole) {
		this.groupRole = productGroupRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productGroupName == null) ? 0 : productGroupName.hashCode());
		result = prime * result + ((groupRole == null) ? 0 : groupRole.hashCode());
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
		ProductGroup other = (ProductGroup) obj;
		if (productGroupName == null) {
			if (other.productGroupName != null)
				return false;
		} else if (!productGroupName.equals(other.productGroupName))
			return false;
		if (groupRole == null) {
			if (other.groupRole != null)
				return false;
		} else if (!groupRole.equals(other.groupRole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductGroup [productGroupName=" + productGroupName + ", productGroupType=" + groupRole + "]";
	}

}
