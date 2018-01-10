package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

// TODO: Auto-generated Javadoc
/**
 * DeviceSummary.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-30T13:33:13.488Z")

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

	/**
	 * Promotions package.
	 *
	 * @param promotionsPackage
	 *            the promotions package
	 * @return the device summary
	 */
	public DeviceSummary promotionsPackage(MerchandisingPromotionsPackage promotionsPackage) {
		this.promotionsPackage = promotionsPackage;
		return this;
	}

	/**
	 * Get promotionsPackage.
	 *
	 * @return promotionsPackage
	 */
	public MerchandisingPromotionsPackage getPromotionsPackage() {
		return promotionsPackage;
	}

	/**
	 * Sets the promotions package.
	 *
	 * @param promotionsPackage
	 *            the new promotions package
	 */
	public void setPromotionsPackage(MerchandisingPromotionsPackage promotionsPackage) {
		this.promotionsPackage = promotionsPackage;
	}

	/**
	 * Device id.
	 *
	 * @param deviceId
	 *            the device id
	 * @return the device summary
	 */
	public DeviceSummary deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	/**
	 * Device Id of the member.
	 *
	 * @return deviceId
	 */
	@ApiModelProperty(value = "Device Id of the member")

	/**
	 * 
	 * @return
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId
	 *            the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Display name.
	 *
	 * @param displayName
	 *            the display name
	 * @return the device summary
	 */
	public DeviceSummary displayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	/**
	 * Display Name of a device This should come form MEF content file.
	 *
	 * @return displayName
	 */
	@ApiModelProperty(value = "Display Name of a device This should come form MEF content file")

	/**
	 * 
	 * @return
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Priority.
	 *
	 * @param priority
	 *            the priority
	 * @return the device summary
	 */
	public DeviceSummary priority(String priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * Display priority of the Member.
	 *
	 * @return priority
	 */
	@ApiModelProperty(value = "Display priority of the Member")

	/**
	 * 
	 * @return
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Display description.
	 *
	 * @param displayDescription
	 *            the display description
	 * @return the device summary
	 */
	public DeviceSummary displayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
		return this;
	}

	/**
	 * Description of the device as provided in the merchandising content file,
	 * preDec of product/handset, eg.With 3D Touch, Live Photos, 7000 series ...
	 * 
	 * @return displayDescription
	 **/
	@ApiModelProperty(value = "Description of the device as provided in the merchandising content file, preDec of product/handset, eg.With 3D Touch, Live Photos, 7000 series ...")

	/**
	 * 
	 * @return
	 */
	public String getDisplayDescription() {
		return displayDescription;
	}

	/**
	 * Sets the display description.
	 *
	 * @param displayDescription
	 *            the new display description
	 */
	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	/**
	 * Colour name.
	 *
	 * @param colourName
	 *            the colour name
	 * @return the device summary
	 */
	public DeviceSummary colourName(String colourName) {
		this.colourName = colourName;
		return this;
	}

	/**
	 * Color of the HandSet - Default colour, eg. Gold
	 * 
	 * @return colourName
	 **/
	@ApiModelProperty(value = "Color of the HandSet - Default colour, eg. Gold")

	/**
	 * 
	 * @return
	 */
	public String getColourName() {
		return colourName;
	}

	/**
	 * Sets the colour name.
	 *
	 * @param colourName
	 *            the new colour name
	 */
	public void setColourName(String colourName) {
		this.colourName = colourName;
	}

	/**
	 * Colour hex.
	 *
	 * @param colourHex
	 *            the colour hex
	 * @return the device summary
	 */
	public DeviceSummary colourHex(String colourHex) {
		this.colourHex = colourHex;
		return this;
	}

	/**
	 * Hex representation of the colour.
	 *
	 * @return colourHex
	 */
	@ApiModelProperty(value = " Hex representation of the colour")

	/**
	 * 
	 * @return
	 */
	public String getColourHex() {
		return colourHex;
	}

	/**
	 * Sets the colour hex.
	 *
	 * @param colourHex
	 *            the new colour hex
	 */
	public void setColourHex(String colourHex) {
		this.colourHex = colourHex;
	}

	/**
	 * Memory.
	 *
	 * @param memory
	 *            the memory
	 * @return the device summary
	 */
	public DeviceSummary memory(String memory) {
		this.memory = memory;
		return this;
	}

	/**
	 * Storage Capacity of the Device 32GB, 64 GB, 128 GB, Default to be
	 * highlighted.
	 *
	 * @return memory
	 */
	@ApiModelProperty(value = "Storage Capacity of the Device  32GB, 64 GB, 128 GB, Default to be highlighted")

	/**
	 * 
	 * @return
	 */
	public String getMemory() {
		return memory;
	}

	/**
	 * Sets the memory.
	 *
	 * @param memory
	 *            the new memory
	 */
	public void setMemory(String memory) {
		this.memory = memory;
	}

	/**
	 * Lead plan id.
	 *
	 * @param leadPlanId
	 *            the lead plan id
	 * @return the device summary
	 */
	public DeviceSummary leadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
		return this;
	}

	/**
	 * Lead Plan ID for the device.
	 *
	 * @return leadPlanId
	 */
	@ApiModelProperty(value = "Lead Plan ID for the device")

	/**
	 * 
	 * @return
	 */
	public String getLeadPlanId() {
		return leadPlanId;
	}

	/**
	 * Sets the lead plan id.
	 *
	 * @param leadPlanId
	 *            the new lead plan id
	 */
	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}

	/**
	 * Lead plan display name.
	 *
	 * @param leadPlanDisplayName
	 *            the lead plan display name
	 * @return the device summary
	 */
	public DeviceSummary leadPlanDisplayName(String leadPlanDisplayName) {
		this.leadPlanDisplayName = leadPlanDisplayName;
		return this;
	}

	/**
	 * Plan Name of the lead plan, from MEF Content file.
	 *
	 * @return leadPlanDisplayName
	 */
	@ApiModelProperty(value = "Plan Name of the lead plan, from MEF Content file")

	/**
	 * 
	 * @return
	 */
	public String getLeadPlanDisplayName() {
		return leadPlanDisplayName;
	}

	/**
	 * Sets the lead plan display name.
	 *
	 * @param leadPlanDisplayName
	 *            the new lead plan display name
	 */
	public void setLeadPlanDisplayName(String leadPlanDisplayName) {
		this.leadPlanDisplayName = leadPlanDisplayName;
	}

	/**
	 * Uom.
	 *
	 * @param uom
	 *            the uom
	 * @return the device summary
	 */
	public DeviceSummary uom(String uom) {
		this.uom = uom;
		return this;
	}

	/**
	 * Associated Plan UOM.
	 *
	 * @return uom
	 */
	@ApiModelProperty(value = "Associated Plan UOM")

	/**
	 * 
	 * @return
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * Sets the uom.
	 *
	 * @param uom
	 *            the new uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * Uom value.
	 *
	 * @param uomValue
	 *            the uom value
	 * @return the device summary
	 */
	public DeviceSummary uomValue(String uomValue) {
		this.uomValue = uomValue;
		return this;
	}

	/**
	 * Associated Plan UOM Value.
	 *
	 * @return uomValue
	 */
	@ApiModelProperty(value = "Associated Plan UOM Value")

	/**
	 * 
	 * @return
	 */
	public String getUomValue() {
		return uomValue;
	}

	/**
	 * Sets the uom value.
	 *
	 * @param uomValue
	 *            the new uom value
	 */
	public void setUomValue(String uomValue) {
		this.uomValue = uomValue;
	}

	/**
	 * Bundle type.
	 *
	 * @param bundleType
	 *            the bundle type
	 * @return the device summary
	 */
	public DeviceSummary bundleType(String bundleType) {
		this.bundleType = bundleType;
		return this;
	}

	/**
	 * Associated Bundle Type.
	 *
	 * @return bundleType
	 */
	@ApiModelProperty(value = "Associated Bundle Type")
	/**
	 * 
	 * @return
	 */

	public String getBundleType() {
		return bundleType;
	}

	/**
	 * Sets the bundle type.
	 *
	 * @param bundleType
	 *            the new bundle type
	 */
	public void setBundleType(String bundleType) {
		this.bundleType = bundleType;
	}

	/**
	 * Product group URI.
	 *
	 * @param productGroupURI
	 *            the product group URI
	 * @return the device summary
	 */
	public DeviceSummary productGroupURI(String productGroupURI) {
		this.productGroupURI = productGroupURI;
		return this;
	}

	/**
	 * Concatenation of Make/Model.
	 *
	 * @return productGroupURI
	 */
	@ApiModelProperty(value = "Concatenation of Make/Model")

	/**
	 * 
	 * @return
	 */
	public String getProductGroupURI() {
		return productGroupURI;
	}

	/**
	 * Sets the product group URI.
	 *
	 * @param productGroupURI
	 *            the new product group URI
	 */
	public void setProductGroupURI(String productGroupURI) {
		this.productGroupURI = productGroupURI;
	}

	/**
	 * Merchandising media.
	 *
	 * @param merchandisingMedia
	 *            the merchandising media
	 * @return the device summary
	 */
	public DeviceSummary merchandisingMedia(List<MediaLink> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
		return this;
	}

	/**
	 * Adds the merchandising media item.
	 *
	 * @param merchandisingMediaItem
	 *            the merchandising media item
	 * @return the device summary
	 */
	public DeviceSummary addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
		if (this.merchandisingMedia == null) {
			this.merchandisingMedia = new ArrayList<MediaLink>();
		}
		this.merchandisingMedia.add(merchandisingMediaItem);
		return this;
	}

	/**
	 * Get merchandisingMedia.
	 *
	 * @return merchandisingMedia
	 */
	@ApiModelProperty(value = "")

	@Valid
	/**
	 * 
	 * @return
	 */
	public List<MediaLink> getMerchandisingMedia() {
		return merchandisingMedia;
	}

	/**
	 * Sets the merchandising media.
	 *
	 * @param merchandisingMedia
	 *            the new merchandising media
	 */
	public void setMerchandisingMedia(List<MediaLink> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
	}

	/**
	 * Price info.
	 *
	 * @param priceInfo
	 *            the price info
	 * @return the device summary
	 */
	public DeviceSummary priceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
		return this;
	}

	/**
	 * Get priceInfo.
	 *
	 * @return priceInfo
	 */
	@ApiModelProperty(value = "")

	@Valid
	/**
	 * 
	 * @return
	 */
	public PriceForBundleAndHardware getPriceInfo() {
		return priceInfo;
	}

	/**
	 * Sets the price info.
	 *
	 * @param priceInfo
	 *            the new price info
	 */
	public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
	}

	/**
	 * Checks if is compatible.
	 *
	 * @param isCompatible
	 *            the is compatible
	 * @return the device summary
	 */
	public DeviceSummary isCompatible(Boolean isCompatible) {
		this.isCompatible = isCompatible;
		return this;
	}

	/**
	 * Is the device compatible with the given bundle.
	 *
	 * @return isCompatible
	 */
	@ApiModelProperty(value = "Is the device compatible with the given bundle")

	/**
	 * 
	 * @return
	 */
	public Boolean getIsCompatible() {
		return isCompatible;
	}

	/**
	 * Sets the checks if is compatible.
	 *
	 * @param isCompatible
	 *            the new checks if is compatible
	 */
	public void setIsCompatible(Boolean isCompatible) {
		this.isCompatible = isCompatible;
	}

	/**
	 * Pre orderable.
	 *
	 * @param preOrderable
	 *            the pre orderable
	 * @return the device summary
	 */
	public DeviceSummary preOrderable(Boolean preOrderable) {
		this.preOrderable = preOrderable;
		return this;
	}

	/**
	 * Is the device pre-orderable as per MEF.
	 *
	 * @return preOrderable
	 */
	@ApiModelProperty(value = "Is the device pre-orderable as per MEF")

	/**
	 * 
	 * @return
	 */
	public Boolean getPreOrderable() {
		return preOrderable;
	}

	/**
	 * Sets the pre orderable.
	 *
	 * @param preOrderable
	 *            the new pre orderable
	 */
	public void setPreOrderable(Boolean preOrderable) {
		this.preOrderable = preOrderable;
	}

	/**
	 * Available from.
	 *
	 * @param availableFrom
	 *            the available from
	 * @return the device summary
	 */
	public DeviceSummary availableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
		return this;
	}

	/**
	 * Available from date of the product as provided in the product catalogue.
	 *
	 * @return availableFrom
	 */
	@ApiModelProperty(value = "Available from date of the product as provided in the product catalogue")

	/**
	 * 
	 * @return
	 */
	public String getAvailableFrom() {
		return availableFrom;
	}

	/**
	 * Sets the available from.
	 *
	 * @param availableFrom
	 *            the new available from
	 */
	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}

	/**
	 * Checks if is affordable.
	 *
	 * @param isAffordable
	 *            the is affordable
	 * @return the device summary
	 */
	public DeviceSummary isAffordable(Boolean isAffordable) {
		this.isAffordable = isAffordable;
		return this;
	}

	/**
	 * flag to showcase whether the device is affordable or not in conditional
	 * accept scenario.
	 *
	 * @return isAffordable
	 */
	@ApiModelProperty(value = "flag to showcase whether the device is affordable or not in conditional accept scenario")

	/**
	 * 
	 * @return
	 */
	public Boolean getIsAffordable() {
		return isAffordable;
	}

	/**
	 * Sets the checks if is affordable.
	 *
	 * @param isAffordable
	 *            the new checks if is affordable
	 */
	public void setIsAffordable(Boolean isAffordable) {
		this.isAffordable = isAffordable;
	}

	/**
	 * From pricing.
	 *
	 * @param fromPricing
	 *            the from pricing
	 * @return the device summary
	 */
	public DeviceSummary fromPricing(Boolean fromPricing) {
		this.fromPricing = fromPricing;
		return this;
	}

	/**
	 * flag to show associated/from price.
	 *
	 * @return fromPricing
	 */
	@ApiModelProperty(value = "flag to show associated/from price")

	/**
	 * 
	 * @return
	 */
	public Boolean getFromPricing() {
		return fromPricing;
	}

	/**
	 * Sets the from pricing.
	 *
	 * @param fromPricing
	 *            the new from pricing
	 */
	public void setFromPricing(Boolean fromPricing) {
		this.fromPricing = fromPricing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DeviceSummary deviceSummary = (DeviceSummary) o;
		return Objects.equals(this.deviceId, deviceSummary.deviceId)
				&& Objects.equals(this.displayName, deviceSummary.displayName)
				&& Objects.equals(this.priority, deviceSummary.priority)
				&& Objects.equals(this.displayDescription, deviceSummary.displayDescription)
				&& Objects.equals(this.colourName, deviceSummary.colourName)
				&& Objects.equals(this.colourHex, deviceSummary.colourHex)
				&& Objects.equals(this.memory, deviceSummary.memory)
				&& Objects.equals(this.leadPlanId, deviceSummary.leadPlanId)
				&& Objects.equals(this.leadPlanDisplayName, deviceSummary.leadPlanDisplayName)
				&& Objects.equals(this.uom, deviceSummary.uom) && Objects.equals(this.uomValue, deviceSummary.uomValue)
				&& Objects.equals(this.bundleType, deviceSummary.bundleType)
				&& Objects.equals(this.productGroupURI, deviceSummary.productGroupURI)
				&& Objects.equals(this.merchandisingMedia, deviceSummary.merchandisingMedia)
				&& Objects.equals(this.priceInfo, deviceSummary.priceInfo)
				&& Objects.equals(this.isCompatible, deviceSummary.isCompatible)
				&& Objects.equals(this.preOrderable, deviceSummary.preOrderable)
				&& Objects.equals(this.availableFrom, deviceSummary.availableFrom)
				&& Objects.equals(this.isAffordable, deviceSummary.isAffordable)
				&& Objects.equals(this.fromPricing, deviceSummary.fromPricing)
				&& Objects.equals(this.promotionsPackage, deviceSummary.promotionsPackage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(deviceId, displayName, priority, displayDescription, colourName, colourHex, memory,
				leadPlanId, leadPlanDisplayName, uom, uomValue, bundleType, productGroupURI, merchandisingMedia,
				priceInfo, isCompatible, preOrderable, availableFrom, isAffordable, fromPricing, promotionsPackage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DeviceSummary {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
		sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
		sb.append("    displayDescription: ").append(toIndentedString(displayDescription)).append("\n");
		sb.append("    colourName: ").append(toIndentedString(colourName)).append("\n");
		sb.append("    colourHex: ").append(toIndentedString(colourHex)).append("\n");
		sb.append("    memory: ").append(toIndentedString(memory)).append("\n");
		sb.append("    leadPlanId: ").append(toIndentedString(leadPlanId)).append("\n");
		sb.append("    leadPlanDisplayName: ").append(toIndentedString(leadPlanDisplayName)).append("\n");
		sb.append("    uom: ").append(toIndentedString(uom)).append("\n");
		sb.append("    uomValue: ").append(toIndentedString(uomValue)).append("\n");
		sb.append("    bundleType: ").append(toIndentedString(bundleType)).append("\n");
		sb.append("    productGroupURI: ").append(toIndentedString(productGroupURI)).append("\n");
		sb.append("    merchandisingMedia: ").append(toIndentedString(merchandisingMedia)).append("\n");
		sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
		sb.append("    isCompatible: ").append(toIndentedString(isCompatible)).append("\n");
		sb.append("    preOrderable: ").append(toIndentedString(preOrderable)).append("\n");
		sb.append("    availableFrom: ").append(toIndentedString(availableFrom)).append("\n");
		sb.append("    isAffordable: ").append(toIndentedString(isAffordable)).append("\n");
		sb.append("    fromPricing: ").append(toIndentedString(fromPricing)).append("\n");
		sb.append("    promotionsPackage: ").append(toIndentedString(promotionsPackage)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 *
	 * @param o
	 *            the o
	 * @return the string
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
