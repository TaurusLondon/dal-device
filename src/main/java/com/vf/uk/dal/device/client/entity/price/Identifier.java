package com.vf.uk.dal.device.client.entity.price;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 
 *Contains details of the items present in customer's account
 */
@Data
public class Identifier {
	@JsonProperty("key")
	private String key;
	@JsonProperty("value")
	private String value;

}
