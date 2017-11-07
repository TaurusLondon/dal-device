package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DeviceDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-15T08:45:19.115Z")

public class DeviceDetails   {
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
  private ProductAvailability productAvailability = null;

  @JsonProperty("media")
  private List<MediaLink> media = null;

  @JsonProperty("specificationsGroups")
  private List<SpecificationGroup> specificationsGroups = null;

  @JsonProperty("priceInfo")
  private PriceForBundleAndHardware priceInfo = null;

  @JsonProperty("metaData")
  private MetaData metaData = null;

  public DeviceDetails deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Product id of the requested device from the product catalogue
   * @return deviceId
  **/


  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public DeviceDetails name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the product as provided in the product catalogue
   * @return name
  **/


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeviceDetails description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the product as provided in the product catalogue
   * @return description
  **/


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  
  /**
   * Informs offercode passed is valid for the journey and device
   * @return validOffer
  **/
  


  public Boolean getValidOffer() {
    return validOffer;
  }

  public void setValidOffer(Boolean validOffer) {
    this.validOffer = validOffer;
  }
  public DeviceDetails productClass(String productClass) {
    this.productClass = productClass;
    return this;
  }

   /**
   * Catalogue product class identifies products - SIMO, HANDSET etc.
   * @return productClass
  **/


  public String getProductClass() {
    return productClass;
  }

  public void setProductClass(String productClass) {
    this.productClass = productClass;
  }

  public DeviceDetails productLines(List<String> productLines) {
    this.productLines = productLines;
    return this;
  }

  public DeviceDetails addProductLinesItem(String productLinesItem) {
    if (this.productLines == null) {
      this.productLines = new ArrayList<String>();
    }
    this.productLines.add(productLinesItem);
    return this;
  }

   /**
   * Get productLines
   * @return productLines
  **/


  public List<String> getProductLines() {
    return productLines;
  }

  public void setProductLines(List<String> productLines) {
    this.productLines = productLines;
  }

  public DeviceDetails merchandisingControl(MerchandisingControl merchandisingControl) {
    this.merchandisingControl = merchandisingControl;
    return this;
  }

   /**
   * Get merchandisingControl
   * @return merchandisingControl
  **/

  @Valid

  public MerchandisingControl getMerchandisingControl() {
    return merchandisingControl;
  }

  public void setMerchandisingControl(MerchandisingControl merchandisingControl) {
    this.merchandisingControl = merchandisingControl;
  }

  public DeviceDetails merchandisingPromotion(List<MerchandisingPromotions> merchandisingPromotion) {
    this.merchandisingPromotion = merchandisingPromotion;
    return this;
  }

  public DeviceDetails addMerchandisingPromotionItem(MerchandisingPromotions merchandisingPromotionItem) {
    if (this.merchandisingPromotion == null) {
      this.merchandisingPromotion = new ArrayList<MerchandisingPromotions>();
    }
    this.merchandisingPromotion.add(merchandisingPromotionItem);
    return this;
  }

   /**
   * Get merchandisingPromotion
   * @return merchandisingPromotion
  **/

  @Valid

  public List<MerchandisingPromotions> getMerchandisingPromotion() {
    return merchandisingPromotion;
  }

  public void setMerchandisingPromotion(List<MerchandisingPromotions> merchandisingPromotion) {
    this.merchandisingPromotion = merchandisingPromotion;
  }

  public DeviceDetails stockAvailability(String stockAvailability) {
    this.stockAvailability = stockAvailability;
    return this;
  }

   /**
   * Stock Availability for Product
   * @return stockAvailability
  **/


  public String getStockAvailability() {
    return stockAvailability;
  }

  public void setStockAvailability(String stockAvailability) {
    this.stockAvailability = stockAvailability;
  }

  public DeviceDetails rating(String rating) {
    this.rating = rating;
    return this;
  }

   /**
   * Rating for the Product
   * @return rating
  **/


  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public DeviceDetails productPageURI(String productPageURI) {
    this.productPageURI = productPageURI;
    return this;
  }

   /**
   * Concatenation of Make/Model
   * @return productPageURI
  **/


  public String getProductPageURI() {
    return productPageURI;
  }

  public void setProductPageURI(String productPageURI) {
    this.productPageURI = productPageURI;
  }

  public DeviceDetails equipmentDetail(Equipment equipmentDetail) {
    this.equipmentDetail = equipmentDetail;
    return this;
  }

   /**
   * Get equipmentDetail
   * @return equipmentDetail
  **/

  @Valid

  public Equipment getEquipmentDetail() {
    return equipmentDetail;
  }

  public void setEquipmentDetail(Equipment equipmentDetail) {
    this.equipmentDetail = equipmentDetail;
  }

  public DeviceDetails leadPlanId(String leadPlanId) {
    this.leadPlanId = leadPlanId;
    return this;
  }

   /**
   * Lead Plan ID for Product
   * @return leadPlanId
  **/


  public String getLeadPlanId() {
    return leadPlanId;
  }

  public void setLeadPlanId(String leadPlanId) {
    this.leadPlanId = leadPlanId;
  }

  public DeviceDetails productAvailability(ProductAvailability productAvailability) {
    this.productAvailability = productAvailability;
    return this;
  }

   /**
   * Get productAvailability
   * @return productAvailability
  **/

  @Valid

  public ProductAvailability getProductAvailability() {
    return productAvailability;
  }

  public void setProductAvailability(ProductAvailability productAvailability) {
    this.productAvailability = productAvailability;
  }

  public DeviceDetails media(List<MediaLink> media) {
    this.media = media;
    return this;
  }

  public DeviceDetails addMediaItem(MediaLink mediaItem) {
    if (this.media == null) {
      this.media = new ArrayList<MediaLink>();
    }
    this.media.add(mediaItem);
    return this;
  }

   /**
   * Get media
   * @return media
  **/

  @Valid

  public List<MediaLink> getMedia() {
    return media;
  }

  public void setMedia(List<MediaLink> media) {
    this.media = media;
  }

  public DeviceDetails specificationsGroups(List<SpecificationGroup> specificationsGroups) {
    this.specificationsGroups = specificationsGroups;
    return this;
  }

  public DeviceDetails addSpecificationsGroupsItem(SpecificationGroup specificationsGroupsItem) {
    if (this.specificationsGroups == null) {
      this.specificationsGroups = new ArrayList<SpecificationGroup>();
    }
    this.specificationsGroups.add(specificationsGroupsItem);
    return this;
  }

   /**
   * Get specificationsGroups
   * @return specificationsGroups
  **/

  @Valid

  public List<SpecificationGroup> getSpecificationsGroups() {
    return specificationsGroups;
  }

  public void setSpecificationsGroups(List<SpecificationGroup> specificationsGroups) {
    this.specificationsGroups = specificationsGroups;
  }

  public DeviceDetails priceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
    return this;
  }

   /**
   * Get priceInfo
   * @return priceInfo
  **/

  @Valid

  public PriceForBundleAndHardware getPriceInfo() {
    return priceInfo;
  }

  public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
  }

  public DeviceDetails metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

   /**
   * Get metaData
   * @return metaData
  **/

  @Valid

  public MetaData getMetaData() {
    return metaData;
  }

  public void setMetaData(MetaData metaData) {
    this.metaData = metaData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceDetails deviceDetails = (DeviceDetails) o;
    return Objects.equals(this.deviceId, deviceDetails.deviceId) &&
        Objects.equals(this.name, deviceDetails.name) &&
        Objects.equals(this.description, deviceDetails.description) &&
        Objects.equals(this.productClass, deviceDetails.productClass) &&
        Objects.equals(this.productLines, deviceDetails.productLines) &&
        Objects.equals(this.merchandisingControl, deviceDetails.merchandisingControl) &&
        Objects.equals(this.merchandisingPromotion, deviceDetails.merchandisingPromotion) &&
        Objects.equals(this.stockAvailability, deviceDetails.stockAvailability) &&
        Objects.equals(this.rating, deviceDetails.rating) &&
        Objects.equals(this.productPageURI, deviceDetails.productPageURI) &&
        Objects.equals(this.equipmentDetail, deviceDetails.equipmentDetail) &&
        Objects.equals(this.leadPlanId, deviceDetails.leadPlanId) &&
        Objects.equals(this.productAvailability, deviceDetails.productAvailability) &&
        Objects.equals(this.media, deviceDetails.media) &&
        Objects.equals(this.specificationsGroups, deviceDetails.specificationsGroups) &&
        Objects.equals(this.priceInfo, deviceDetails.priceInfo) &&
        Objects.equals(this.metaData, deviceDetails.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, name, description, productClass, productLines, merchandisingControl, merchandisingPromotion, stockAvailability, rating, productPageURI, equipmentDetail, leadPlanId, productAvailability, media, specificationsGroups, priceInfo, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceDetails {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
    sb.append("    productLines: ").append(toIndentedString(productLines)).append("\n");
    sb.append("    merchandisingControl: ").append(toIndentedString(merchandisingControl)).append("\n");
    sb.append("    merchandisingPromotion: ").append(toIndentedString(merchandisingPromotion)).append("\n");
    sb.append("    stockAvailability: ").append(toIndentedString(stockAvailability)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    productPageURI: ").append(toIndentedString(productPageURI)).append("\n");
    sb.append("    equipmentDetail: ").append(toIndentedString(equipmentDetail)).append("\n");
    sb.append("    leadPlanId: ").append(toIndentedString(leadPlanId)).append("\n");
    sb.append("    productAvailability: ").append(toIndentedString(productAvailability)).append("\n");
    sb.append("    media: ").append(toIndentedString(media)).append("\n");
    sb.append("    specificationsGroups: ").append(toIndentedString(specificationsGroups)).append("\n");
    sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
    sb.append("    metaData: ").append(toIndentedString(metaData)).append("\n");
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

