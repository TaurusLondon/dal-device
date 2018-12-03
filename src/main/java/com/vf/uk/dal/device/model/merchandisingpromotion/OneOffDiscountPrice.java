package com.vf.uk.dal.device.model.merchandisingpromotion;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * OneOffDiscountPrice
 * @author manoj.bera
 *
 */
@Data
public class OneOffDiscountPrice {
	@JsonProperty("gross")
	private Float gross;
	@JsonProperty("net")
	private Float net;
	@JsonProperty("vat")
	private Float vat;

	
}
