package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Device
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class Device {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("make")
	private String make = null;

	@JsonProperty("model")
	private String model = null;

	@JsonProperty("groupType")
	private String groupType = null;

	@JsonProperty("rating")
	private String rating = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("productClass")
	private String productClass = null;

	@JsonProperty("merchandisingControl")
	private MerchandisingControl merchandisingControl = null;

	@JsonProperty("media")
	private List<MediaLink> media = null;

	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

	/** The promotions package. */
	@JsonProperty("promotionsPackage")
	private MerchandisingPromotionsPackage promotionsPackage = null;

	@JsonProperty("productGroupName")
	private String productGroupName = null;

	@JsonProperty("productGroupId")
	private String productGroupId = null;
	
	@JsonProperty("size")
	private List<String> size = null;

	@JsonProperty("color")
	private List<String> color = null;
	
	@JsonProperty("colorHex")
	private List<String> colorHex = null;
	

}
