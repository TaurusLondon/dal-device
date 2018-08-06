package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Color {

	@JsonProperty("colorName")
	private String colorName;
	
	@JsonProperty("colorHex")
	private String colorHex;
}
