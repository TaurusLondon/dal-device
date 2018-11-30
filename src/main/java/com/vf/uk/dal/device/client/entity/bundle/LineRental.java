package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *  LineRental
 * @author manoj.bera
 *
 */
@Data
public class LineRental {

	@JsonProperty("lineRentalProductId")
	private String lineRentalProductId;

	@JsonProperty("lineRentalAmount")
	private Long lineRentalAmount;
}
