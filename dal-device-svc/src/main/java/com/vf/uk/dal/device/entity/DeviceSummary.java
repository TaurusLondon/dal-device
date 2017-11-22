package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * DeviceSummary
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-02T21:05:20.521Z")

public class DeviceSummary   {
  @JsonProperty("deviceId")
  private String deviceId = null;

  @JsonProperty("displayName")
  private String displayName = null;
  
  @JsonProperty("isCompatible")
  private Boolean isCompatible = null;
  
  @JsonProperty("preOrderable")
  private Boolean preOrderable = null;

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
  private List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();

  @JsonProperty("priceInfo")
  private PriceForBundleAndHardware priceInfo = null;
  
  @JsonProperty("isAffordable")
  private Boolean isAffordable = true;
  
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
  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
  
  public DeviceSummary preOrderable(Boolean preOrderable) {
	this.preOrderable = preOrderable;
	return this;
  }

  public Boolean getPreOrderable() {
	return preOrderable;
  }

  public void setPreOrderable(Boolean preOrderable) {
	this.preOrderable = preOrderable;
  }
  
  public DeviceSummary isCompatible(Boolean isCompatible) {
	this.isCompatible = isCompatible;
	return this;
  }

  public Boolean getIsCompatible() {
	return isCompatible;
  }

  public void setIsCompatible(Boolean isCompatible) {
	this.isCompatible = isCompatible;
  }
  
  public DeviceSummary fromPricing(Boolean fromPricing) {
		this.fromPricing = fromPricing;
		return this;
	  }

	  public Boolean getFromPricing() {
		return fromPricing;
	  }

	  public void setFromPricing(Boolean fromPricing) {
		this.fromPricing = fromPricing;
	  } 

  public DeviceSummary displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * Display Name of a device This should come form MEF content file
   * @return displayName
  **/
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
    this.merchandisingMedia.add(merchandisingMediaItem);
    return this;
  }

   /**
   * Get merchandisingMedia
   * @return merchandisingMedia
  **/
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
  public PriceForBundleAndHardware getPriceInfo() {
    return priceInfo;
  }

  public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
  }

  public DeviceSummary isAffordable(Boolean isAffordable) {
	    this.isAffordable = isAffordable;
	    return this;
	  }
 

/**
 * @return the isAffordable
 */
public Boolean getIsAffordable() {
	return isAffordable;
}

/**
 * @param isAffordable the isAffordable to set
 */
public void setIsAffordable(Boolean isAffordable) {
	this.isAffordable = isAffordable;
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
        Objects.equals(this.preOrderable, deviceSummary.preOrderable) &&
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
        Objects.equals(this.priceInfo, deviceSummary.priceInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, displayName, priority, displayDescription, colourName, colourHex, memory, leadPlanId, leadPlanDisplayName, uom, uomValue, bundleType, productGroupURI, merchandisingMedia, priceInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceSummary {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    preOrderable: ").append(toIndentedString(preOrderable)).append("\n");
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

