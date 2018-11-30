package com.vf.uk.dal.device.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * AddonIncompatibleProducts
 * @author manoj.bera
 *
 */
@Data
public class AddonIncompatibleProducts {
	@JsonProperty("incompatibleProductId")
	private String incompatibleProductId;
	@JsonProperty("ruleScope")
	private String ruleScope;
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("endDate")
	private String endDate;
}
