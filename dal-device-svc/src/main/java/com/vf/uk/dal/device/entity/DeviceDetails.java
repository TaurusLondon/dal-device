package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DeviceDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class DeviceDetails {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("validOffer")
	private Boolean validOffer = null;

	@JsonProperty("productClass")
	private String productClass = null;

	@JsonProperty("productLines")
	private List<String> productLines = null;

	@JsonProperty("merchandisingControl")
	private MerchandisingControl merchandisingControl = null;

	@JsonProperty("merchandisingPromotion")
	private List<MerchandisingPromotions> merchandisingPromotion = null;

	@JsonProperty("stockAvailability")
	private String stockAvailability = null;

	@JsonProperty("rating")
	private String rating = null;

	@JsonProperty("productPageURI")
	private String productPageURI = null;

	@JsonProperty("equipmentDetail")
	private Equipment equipmentDetail = null;

	@JsonProperty("leadPlanId")
	private String leadPlanId = null;

	@JsonProperty("productAvailability")
	private com.vf.uk.dal.device.entity.ProductAvailability1 productAvailability = null;

	@JsonProperty("media")
	private List<MediaLink> media = null;

	@JsonProperty("specificationsGroups")
	private List<SpecificationGroup> specificationsGroups = null;

	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

	@JsonProperty("metaData")
	private MetaData metaData = null;

}
