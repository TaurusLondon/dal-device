package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * StepPricingInfo
 */
@Data
public class StepPricingInfo {
	
	@JsonProperty("sequence")
	private String sequence = null;

	@JsonProperty("oneOffPrice")
	private Price oneOffPrice = null;

	@JsonProperty("monthlyPrice")
	private Price monthlyPrice = null;

	@JsonProperty("duration")
	private Duration duration = null;

	@JsonProperty("discountSkuIds")
	private List<UUID> discountSkuIds = new ArrayList<>();

}
