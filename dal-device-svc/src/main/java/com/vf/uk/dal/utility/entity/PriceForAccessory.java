package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * PriceForAccessory
 *
 */
@Data
public class PriceForAccessory {

	@JsonProperty("hardwarePrice")
	private HardwarePrice hardwarePrice = null;

}
