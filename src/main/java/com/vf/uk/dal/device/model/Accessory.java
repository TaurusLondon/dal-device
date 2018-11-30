package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.PriceForAccessory;

import lombok.Data;

/**
 * Accessory
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

@Data
public class Accessory {
	@JsonProperty("skuId")
	private String skuId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("colour")
	private String colour = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("merchandisingMedia")
	private List<MediaLink> merchandisingMedia = null;

	@JsonProperty("deviceCost")
	private PriceForAccessory deviceCost = null;

	@JsonProperty("attributes")
	private List<Attributes> attributes = null;

}
