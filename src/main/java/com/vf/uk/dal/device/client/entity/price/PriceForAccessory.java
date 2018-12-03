package com.vf.uk.dal.device.client.entity.price;

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
