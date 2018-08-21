package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Discount
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-27T13:05:05.553Z")
@Data
public class Discount {
	@JsonProperty("skuId")
	private String skuId = null;

	@JsonProperty("tag")
	private String tag = null;
}
