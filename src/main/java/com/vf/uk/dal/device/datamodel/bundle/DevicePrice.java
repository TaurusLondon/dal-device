
package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DevicePrice
 * @author manoj.bera
 *
 */
@Data
public class DevicePrice {

	@JsonProperty("deviceId")
	private String deviceId;

	@JsonProperty("priceNet")
	private float priceNet;

	@JsonProperty("priceGross")
	private float priceGross;

	@JsonProperty("priceVAT")
	private float priceVAT;

	@JsonProperty("productLine")
	private String productLine;
}
