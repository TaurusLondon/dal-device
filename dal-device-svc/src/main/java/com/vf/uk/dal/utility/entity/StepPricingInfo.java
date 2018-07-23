package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

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

	private List<String> discountSkuIds = new ArrayList<String>();
}
