package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Colour {

	@JsonProperty("colorName")
	private String colorName;
	
	@JsonProperty("colorHex")
	private String colorHex;
}
