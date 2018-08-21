package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Allowance
 * @author manoj.bera
 * @Data implemented for setter getter ,to string, hashcode
 */
@Data
public class Allowance {
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("uom")
	private String uom;

	@JsonProperty("displayUOM")
	private String displayUom;

	@JsonProperty("tilUOM")
	private String tilUOM;

	@JsonProperty("productId")
	private String productId;

	@JsonProperty("conversionFactor")
	private Long conversionFactor;

}