package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;

import lombok.Data;

/**
 * DeviceSummary.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-30T13:33:13.488Z")
@Data
public class DeviceSummary {

	/** The device id. */
	@JsonProperty("deviceId")
	private String deviceId = null;

	/** The display name. */
	@JsonProperty("displayName")
	private String displayName = null;

	/** The priority. */
	@JsonProperty("priority")
	private String priority = null;

	/** The display description. */
	@JsonProperty("displayDescription")
	private String displayDescription = null;

	/** The colour name. */
	@JsonProperty("colourName")
	private String colourName = null;

	/** The colour hex. */
	@JsonProperty("colourHex")
	private String colourHex = null;

	/** The memory. */
	@JsonProperty("memory")
	private String memory = null;

	/** The lead plan id. */
	@JsonProperty("leadPlanId")
	private String leadPlanId = null;

	/** The lead plan display name. */
	@JsonProperty("leadPlanDisplayName")
	private String leadPlanDisplayName = null;

	/** The uom. */
	@JsonProperty("uom")
	private String uom = null;

	/** The uom value. */
	@JsonProperty("uomValue")
	private String uomValue = null;

	/** The bundle type. */
	@JsonProperty("bundleType")
	private String bundleType = null;

	/** The product group URI. */
	@JsonProperty("productGroupURI")
	private String productGroupURI = null;

	/** The merchandising media. */
	@JsonProperty("merchandisingMedia")
	private List<MediaLink> merchandisingMedia = null;

	/** The price info. */
	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

	/** The is compatible. */
	@JsonProperty("isCompatible")
	private Boolean isCompatible = null;

	/** The pre orderable. */
	@JsonProperty("preOrderable")
	private Boolean preOrderable = null;

	/** The available from. */
	@JsonProperty("availableFrom")
	private String availableFrom = null;

	/** The is affordable. */
	@JsonProperty("isAffordable")
	private Boolean isAffordable = null;

	/** The from pricing. */
	@JsonProperty("fromPricing")
	private Boolean fromPricing = null;

	/** The promotions package. */
	@JsonProperty("promotionsPackage")
	private MerchandisingPromotionsPackage promotionsPackage = null;
}
