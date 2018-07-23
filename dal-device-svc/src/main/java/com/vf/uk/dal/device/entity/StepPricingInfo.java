package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

/**
 * StepPricingInfo
 */
@Data
public class StepPricingInfo {
	private String sequence = null;

	private Price oneOffPrice = null;

	private Price monthlyPrice = null;

	private Duration duration = null;

	private List<UUID> discountSkuIds = new ArrayList<>();

}
