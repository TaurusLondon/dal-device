package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class ItemAttribute {

	private String key;

	private String value;

	private String type;

	private String valueUOM;

	public ItemAttribute() {
		super();
		// TODO Auto-generated constructor stub
	}
}
