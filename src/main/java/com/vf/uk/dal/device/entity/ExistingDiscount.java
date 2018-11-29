package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ExistingDiscount
 * @author Chandrasekhar.Chandr
 *
 */
@Data
public class ExistingDiscount {
	@JsonProperty("discountId")
	private String discountId;
	@JsonProperty("offerId")
	private String offerId;
	@JsonProperty("qualifier")
	private Identifier qualifier;

}
