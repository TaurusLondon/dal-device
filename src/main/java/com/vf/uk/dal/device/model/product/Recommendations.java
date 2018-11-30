package com.vf.uk.dal.device.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Recommendations
 * @author manoj.bera
 *
 */
@Data
public class Recommendations {

	@JsonProperty("type")
	private String type;

	@JsonProperty("name")
	private String name;

}
