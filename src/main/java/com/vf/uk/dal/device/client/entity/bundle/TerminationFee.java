package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * TerminationFee
 * @author manoj.bera
 *
 */
@Data
public class TerminationFee {

	@JsonProperty("charge")
	private Float charge;

	@JsonProperty("charge")
	private String proratePlan;
}
