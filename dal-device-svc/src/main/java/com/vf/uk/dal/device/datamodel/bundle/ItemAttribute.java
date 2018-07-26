package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemAttribute {

	@JsonProperty("key")
	private String key;

	@JsonProperty("value")
	private String value;

	@JsonProperty("type")
	private String type;

	@JsonProperty("valueUOM")
	private String valueUOM;

	public ItemAttribute() {
		super();
		// TODO Auto-generated constructor stub
	}
}
