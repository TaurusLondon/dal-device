package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Equipment {

	@JsonProperty("make")
	private String make;

	@JsonProperty("model")
	private String model;

}
