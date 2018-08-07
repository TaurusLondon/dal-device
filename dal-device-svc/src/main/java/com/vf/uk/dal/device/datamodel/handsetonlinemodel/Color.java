package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class Color {

	@JsonProperty("colorName")
	private String colorName;
	
	@JsonProperty("colorHex")
	private String colorHex;
}
