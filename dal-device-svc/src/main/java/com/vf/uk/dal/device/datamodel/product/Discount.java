package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author manoj.bera
 * Discount
 *
 */
@Data
public class Discount {

	@JsonProperty("amount")
	private Double amount;

	@JsonProperty("type")
	private String type;
}
