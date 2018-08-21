package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DiscountMP
 * @author manoj.bera
 *
 */
@Data
public class DiscountMP {
	@JsonProperty("type")
	private String type;
	@JsonProperty("value")
	private float value;
	@JsonProperty("qualifyingRecurringCost")
	private float qualifyingRecurringCost;
	@JsonProperty("priority")
	private Long priority;

	
}
