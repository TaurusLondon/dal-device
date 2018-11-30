package com.vf.uk.dal.device.client.entity.price;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * PriceForProduct
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-17T05:56:29.550Z")
@Data
public class PriceForProduct {
	@JsonProperty("priceForExtras")
	private List<PriceForExtra> priceForExtras = null;

	@JsonProperty("priceForAccessoryes")
	private List<PriceForAccessory> priceForAccessoryes = null;
}
