package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Commitment
 * @author manoj.bera
 *
 */
@Data
public class Commitment {

	@JsonProperty("period")
	private String period;

	@JsonProperty("penalty")
	private Boolean penalty;
}
