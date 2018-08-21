package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Discount Price
 */
@Data
public class DiscountPrice {

	@JsonProperty("value")
	private Double value;

	@JsonProperty("types")
	private String type;
}
