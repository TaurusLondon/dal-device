package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.Price;

import lombok.Data;

/**
 * MonthlyCostSaver
 */
@Data
public class MonthlyCostSaver {

	@JsonProperty("mcsPlanId")
	private String mcsPlanId = null;

	@JsonProperty("mcsMessage")
	private String mcsMessage = null;

	@JsonProperty("mcsParentId")
	private String mcsParentId = null;

	@JsonProperty("oneOffPrice")
	private Price oneOffPrice = null;

	@JsonProperty("oneOffDiscountPrice")
	private Price oneOffDiscountPrice = null;

	@JsonProperty("monthlyPrice")
	private Price monthlyPrice = null;

	@JsonProperty("monthlyDiscountPrice")
	private Price monthlyDiscountPrice = null;

}
