package com.vf.uk.dal.device.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ItemAttribute
 * @author manoj.bera
 *
 */
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
}
