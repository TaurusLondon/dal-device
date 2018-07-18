package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

public class ProductGroup {

	private List<Group> listOfProductGroups;

	public ProductGroup() {
		super();
	}

	public List<Group> getListOfProductGroups() {
		return listOfProductGroups;
	}

	public void setListOfProductGroups(List<Group> listOfProductGroups) {
		this.listOfProductGroups = listOfProductGroups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listOfProductGroups == null) ? 0 : listOfProductGroups.hashCode());
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
		if (listOfProductGroups == null) {
			if (other.listOfProductGroups != null)
				return false;
		} else if (!listOfProductGroups.equals(other.listOfProductGroups))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductGroup [listOfProductGroups=" + listOfProductGroups + "]";
	}

}
