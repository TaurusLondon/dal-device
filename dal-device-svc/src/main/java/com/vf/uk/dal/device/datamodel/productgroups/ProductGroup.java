package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import lombok.Data;

@Data
public class ProductGroup {

	private List<Group> listOfProductGroups;

	public ProductGroup() {
		super();
	}

	
}
