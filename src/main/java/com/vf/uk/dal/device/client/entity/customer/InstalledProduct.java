package com.vf.uk.dal.device.client.entity.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * InstalledProduct
 *
 */
@Data
public class InstalledProduct {

	@JsonProperty("id")
	private String id = null;

	@JsonProperty("typeCode")
	private String typeCode = null;

	@JsonProperty("amount")
	private String amount = null;
}
