package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class Color Stored Color Hex value and name of the color.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-17T15:39:15.544Z")
@Data
public class Colour {

	@JsonProperty("colorName")
	private String colorName;
	
	@JsonProperty("colorHex")
	private String colorHex;
}
