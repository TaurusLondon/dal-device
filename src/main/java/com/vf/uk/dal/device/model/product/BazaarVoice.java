package com.vf.uk.dal.device.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BazaarVoice
 * @author manoj.bera
 *
 */
@Data
public class BazaarVoice {

	@JsonProperty("skuId")
	private String skuId;

	@JsonProperty("jsonsource")
	private String jsonsource;
}
