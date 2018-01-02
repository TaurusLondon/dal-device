package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * DeviceSummary
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-30T13:33:13.488Z")

public class DeviceSummary   {
  @JsonProperty("deviceId")
  private String deviceId = null;

  @JsonProperty("displayName")
  private String displayName = null;

  @JsonProperty("priority")
  private String priority = null;

  @JsonProperty("displayDescription")
  private String displayDescription = null;

  @JsonProperty("colourName")
  private String colourName = null;

  @JsonProperty("colourHex")
  private String colourHex = null;

  @JsonProperty("memory")
  private String memory = null;

  @JsonProperty("leadPlanId")
  private String leadPlanId = null;

  @JsonProperty("leadPlanDisplayName")
  private String leadPlanDisplayName = null;

  @JsonProperty("uom")
  private String uom = null;

  @JsonProperty("uomValue")
  private String uomValue = null;

  @JsonProperty("bundleType")
  private String bundleType = null;

  @JsonProperty("productGroupURI")
  private String productGroupURI = null;

  @JsonProperty("merchandisingMedia")
  private List<MediaLink> merchandisingMedia = null;

  @JsonProperty("priceInfo")
  private PriceForBundleAndHardware priceInfo = null;

  @JsonProperty("isCompatible")
  private Boolean isCompatible = null;

  @JsonProperty("preOrderable")
  private Boolean preOrderable = null;

  @JsonProperty("availableFrom")
  private String availableFrom = null;

  @JsonProperty("isAffordable")
  private Boolean isAffordable = null;

  @JsonProperty("fromPricing")
  private Boolean fromPricing = null;

  public DeviceSummary deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Device Id of the member
   * @return deviceId
  **/
  @ApiModelProperty(value = "Device Id of the member")


  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public DeviceSummary displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * Display Name of a device This should come form MEF content file
   * @return displayName
  **/
  @ApiModelProperty(value = "Display Name of a device This should come form MEF content file")


  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public DeviceSummary priority(String priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Display priority of the Member
   * @return priority
  **/
  @ApiModelProperty(value = "Display priority of the Member")


  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public DeviceSummary displayDescription(String displayDescription) {
    this.displayDescription = displayDescription;
    return this;
  }

   /**
   * Description of the device as provided in the merchandising content file, preDec of product/handset, eg.With 3D Touch, Live Photos, 7000 series ...
   * @return displayDescription
  **/
  @ApiModelProperty(value = "Description of the device as provided in the merchandising content file, preDec of product/handset, eg.With 3D Touch, Live Photos, 7000 series ...")


  public String getDisplayDescription() {
    return displayDescription;
  }

  public void setDisplayDescription(String displayDescription) {
    this.displayDescription = displayDescription;
  }

  public DeviceSummary colourName(String colourName) {
    this.colourName = colourName;
    return this;
  }

   /**
   * Color of the HandSet - Default colour, eg. Gold
   * @return colourName
  **/
  @ApiModelProperty(value = "Color of the HandSet - Default colour, eg. Gold")


  public String getColourName() {
    return colourName;
  }

  public void setColourName(String colourName) {
    this.colourName = colourName;
  }

  public DeviceSummary colourHex(String colourHex) {
    this.colourHex = colourHex;
    return this;
  }

   /**
   *  Hex representation of the colour
   * @return colourHex
  **/
  @ApiModelProperty(value = " Hex representation of the colour")


  public String getColourHex() {
    return colourHex;
  }

  public void setColourHex(String colourHex) {
    this.colourHex = colourHex;
  }

  public DeviceSummary memory(String memory) {
    this.memory = memory;
    return this;
  }

   /**
   * Storage Capacity of the Device  32GB, 64 GB, 128 GB, Default to be highlighted
   * @return memory
  **/
  @ApiModelProperty(value = "Storage Capacity of the Device  32GB, 64 GB, 128 GB, Default to be highlighted")


  public String getMemory() {
    return memory;
  }

  public void setMemory(String memory) {
    this.memory = memory;
  }

  public DeviceSummary leadPlanId(String leadPlanId) {
    this.leadPlanId = leadPlanId;
    return this;
  }

   /**
   * Lead Plan ID for the device
   * @return leadPlanId
  **/
  @ApiModelProperty(value = "Lead Plan ID for the device")


  public String getLeadPlanId() {
    return leadPlanId;
  }

  public void setLeadPlanId(String leadPlanId) {
    this.leadPlanId = leadPlanId;
  }

  public DeviceSummary leadPlanDisplayName(String leadPlanDisplayName) {
    this.leadPlanDisplayName = leadPlanDisplayName;
    return this;
  }

   /**
   * Plan Name of the lead plan, from MEF Content file
   * @return leadPlanDisplayName
  **/
  @ApiModelProperty(value = "Plan Name of the lead plan, from MEF Content file")


  public String getLeadPlanDisplayName() {
    return leadPlanDisplayName;
  }

  public void setLeadPlanDisplayName(String leadPlanDisplayName) {
    this.leadPlanDisplayName = leadPlanDisplayName;
  }

  public DeviceSummary uom(String uom) {
    this.uom = uom;
    return this;
  }

   /**
   * Associated Plan UOM
   * @return uom
  **/
  @ApiModelProperty(value = "Associated Plan UOM")


  public String getUom() {
    return uom;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public DeviceSummary uomValue(String uomValue) {
    this.uomValue = uomValue;
    return this;
  }

   /**
   * Associated Plan UOM Value
   * @return uomValue
  **/
  @ApiModelProperty(value = "Associated Plan UOM Value")


  public String getUomValue() {
    return uomValue;
  }

  public void setUomValue(String uomValue) {
    this.uomValue = uomValue;
  }

  public DeviceSummary bundleType(String bundleType) {
    this.bundleType = bundleType;
    return this;
  }

   /**
   * Associated Bundle Type
   * @return bundleType
  **/
  @ApiModelProperty(value = "Associated Bundle Type")


  public String getBundleType() {
    return bundleType;
  }

  public void setBundleType(String bundleType) {
    this.bundleType = bundleType;
  }

  public DeviceSummary productGroupURI(String productGroupURI) {
    this.productGroupURI = productGroupURI;
    return this;
  }

   /**
   * Concatenation of Make/Model
   * @return productGroupURI
  **/
  @ApiModelProperty(value = "Concatenation of Make/Model")


  public String getProductGroupURI() {
    return productGroupURI;
  }

  public void setProductGroupURI(String productGroupURI) {
    this.productGroupURI = productGroupURI;
  }

  public DeviceSummary merchandisingMedia(List<MediaLink> merchandisingMedia) {
    this.merchandisingMedia = merchandisingMedia;
    return this;
  }

  public DeviceSummary addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
    if (this.merchandisingMedia == null) {
      this.merchandisingMedia = new ArrayList<MediaLink>();
    }
    this.merchandisingMedia.add(merchandisingMediaItem);
    return this;
  }

   /**
   * Get merchandisingMedia
   * @return merchandisingMedia
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<MediaLink> getMerchandisingMedia() {
    return merchandisingMedia;
  }

  public void setMerchandisingMedia(List<MediaLink> merchandisingMedia) {
    this.merchandisingMedia = merchandisingMedia;
  }

  public DeviceSummary priceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
    return this;
  }

   /**
   * Get priceInfo
   * @return priceInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public PriceForBundleAndHardware getPriceInfo() {
    return priceInfo;
  }

  public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
  }

  public DeviceSummary isCompatible(Boolean isCompatible) {
    this.isCompatible = isCompatible;
    return this;
  }

   /**
   * Is the device compatible with the given bundle
   * @return isCompatible
  **/
  @ApiModelProperty(value = "Is the device compatible with the given bundle")


  public Boolean getIsCompatible() {
    return isCompatible;
  }

  public void setIsCompatible(Boolean isCompatible) {
    this.isCompatible = isCompatible;
  }

  public DeviceSummary preOrderable(Boolean preOrderable) {
    this.preOrderable = preOrderable;
    return this;
  }

   /**
   * Is the device pre-orderable as per MEF
   * @return preOrderable
  **/
  @ApiModelProperty(value = "Is the device pre-orderable as per MEF")


  public Boolean getPreOrderable() {
    return preOrderable;
  }

  public void setPreOrderable(Boolean preOrderable) {
    this.preOrderable = preOrderable;
  }

  public DeviceSummary availableFrom(String availableFrom) {
    this.availableFrom = availableFrom;
    return this;
  }

   /**
   * Available from date of the product as provided in the product catalogue
   * @return availableFrom
  **/
  @ApiModelProperty(value = "Available from date of the product as provided in the product catalogue")


  public String getAvailableFrom() {
    return availableFrom;
  }

  public void setAvailableFrom(String availableFrom) {
    this.availableFrom = availableFrom;
  }

  public DeviceSummary isAffordable(Boolean isAffordable) {
    this.isAffordable = isAffordable;
    return this;
  }

   /**
   * flag to showcase whether the device is affordable or not in conditional accept scenario
   * @return isAffordable
  **/
  @ApiModelProperty(value = "flag to showcase whether the device is affordable or not in conditional accept scenario")


  public Boolean getIsAffordable() {
    return isAffordable;
  }

  public void setIsAffordable(Boolean isAffordable) {
    this.isAffordable = isAffordable;
  }

  public DeviceSummary fromPricing(Boolean fromPricing) {
    this.fromPricing = fromPricing;
    return this;
  }

   /**
   * flag to show associated/from price
   * @return fromPricing
  **/
  @ApiModelProperty(value = "flag to show associated/from price")


  public Boolean getFromPricing() {
    return fromPricing;
  }

  public void setFromPricing(Boolean fromPricing) {
    this.fromPricing = fromPricing;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceSummary deviceSummary = (DeviceSummary) o;
    return Objects.equals(this.deviceId, deviceSummary.deviceId) &&
        Objects.equals(this.displayName, deviceSummary.displayName) &&
        Objects.equals(this.priority, deviceSummary.priority) &&
        Objects.equals(this.displayDescription, deviceSummary.displayDescription) &&
        Objects.equals(this.colourName, deviceSummary.colourName) &&
        Objects.equals(this.colourHex, deviceSummary.colourHex) &&
        Objects.equals(this.memory, deviceSummary.memory) &&
        Objects.equals(this.leadPlanId, deviceSummary.leadPlanId) &&
        Objects.equals(this.leadPlanDisplayName, deviceSummary.leadPlanDisplayName) &&
        Objects.equals(this.uom, deviceSummary.uom) &&
        Objects.equals(this.uomValue, deviceSummary.uomValue) &&
        Objects.equals(this.bundleType, deviceSummary.bundleType) &&
        Objects.equals(this.productGroupURI, deviceSummary.productGroupURI) &&
        Objects.equals(this.merchandisingMedia, deviceSummary.merchandisingMedia) &&
        Objects.equals(this.priceInfo, deviceSummary.priceInfo) &&
        Objects.equals(this.isCompatible, deviceSummary.isCompatible) &&
        Objects.equals(this.preOrderable, deviceSummary.preOrderable) &&
        Objects.equals(this.availableFrom, deviceSummary.availableFrom) &&
        Objects.equals(this.isAffordable, deviceSummary.isAffordable) &&
        Objects.equals(this.fromPricing, deviceSummary.fromPricing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, displayName, priority, displayDescription, colourName, colourHex, memory, leadPlanId, leadPlanDisplayName, uom, uomValue, bundleType, productGroupURI, merchandisingMedia, priceInfo, isCompatible, preOrderable, availableFrom, isAffordable, fromPricing);
  }

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
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

