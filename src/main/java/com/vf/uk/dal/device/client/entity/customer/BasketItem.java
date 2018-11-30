package com.vf.uk.dal.device.client.entity.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BasketItem
 * 
 * @author
 *
 */
@Data
public class BasketItem {

	@JsonProperty("typeCode")
	private String typeCode = null;

	@JsonProperty("id")
	private String id = null;

}
