package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author manoj.bera
 *  Delivery
 *
 */
@Data
public class Delivery {

	@JsonProperty("classification")
	private String classification;

	@JsonProperty("partner")
	private String partner;

	@JsonProperty("soaDeliveryMethod")
	private String soaDeliveryMethod;

	@JsonProperty("weekend")
	private boolean weekend;

}
