package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Discount
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-14T09:23:00.845Z")
@Data
public class Discount {
	@JsonProperty("skuId")
	private String skuId = null;

	@JsonProperty("tag")
	private String tag = null;

	
}
