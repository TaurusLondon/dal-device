package com.vf.uk.dal.device.client.entity.price;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * PriceForExtra
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-17T05:56:29.550Z")
@Data
public class PriceForExtra {
	@JsonProperty("extraPrice")
	private ExtraPrice extraPrice = null;
}
