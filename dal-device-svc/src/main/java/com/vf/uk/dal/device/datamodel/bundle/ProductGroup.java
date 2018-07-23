package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class ProductGroup {

	private String productGroupName;

	private String groupRole;

	public ProductGroup() {
		super();
	}
}
