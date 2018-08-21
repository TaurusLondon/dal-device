package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductAllowance
 * @author manoj.bera
 *
 */
@Data
public class ProductAllowance {

	@JsonProperty("type")
	private String type;

	@JsonProperty("uom")
	private String uom;

	@JsonProperty("value")
	private String value;
}
