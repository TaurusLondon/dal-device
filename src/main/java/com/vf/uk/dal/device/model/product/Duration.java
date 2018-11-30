package com.vf.uk.dal.device.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author manoj.bera
 * Duration
 *
 */
@Data
public class Duration {

	@JsonProperty("uom")
	private String uom;

	@JsonProperty("starts")
	private String starts;

	@JsonProperty("value")
	private String value;

}
