package com.vf.uk.dal.device.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;

import lombok.Data;

/**
 * DeviceList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-15T13:18:08.775Z")
@Data
public class DeviceList {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("rating")
	private Integer rating = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("productClass")
	private String productClass = null;

	@JsonProperty("merchandisingControl")
	private MerchandisingControl merchandisingControl = null;

	@JsonProperty("media")
	private List<MediaLink> media = new ArrayList<>();

	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

}
