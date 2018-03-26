package com.vf.uk.dal.device.datamodel.product;


public class ProductGroup {

	
	  
    private String productGroupName;
	 
    private String productGroupRole;
	
	
	public ProductGroup() {
		super();
	}
	public String getProductGroupName() {
		return productGroupName;
	}
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	public String getProductGroupRole() {
		return productGroupRole;
	}
	public void setProductGroupRole(String productGroupRole) {
		this.productGroupRole = productGroupRole;
	}
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productGroupName == null) ? 0 : productGroupName.hashCode());
		result = prime * result + ((prolductGroupRole == null) ? 0 : prolductGroupRole.hashCode());
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
		if (prolductGroupRole == null) {
			if (other.prolductGroupRole != null)
				return false;
		} else if (!prolductGroupRole.equals(other.prolductGroupRole))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProductGroup [productGroupName=" + productGroupName + ", prolductGroupRole=" + prolductGroupRole + "]";
	}*/
    
	

}
