package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


/**
 * DeviceDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

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
  private com.vf.uk.dal.device.entity.ProductAvailability1 productAvailability = null;

  @JsonProperty("media")
  private List<MediaLink> media = null;

  @JsonProperty("specificationsGroups")
  private List<SpecificationGroup> specificationsGroups = null;

  @JsonProperty("priceInfo")
  private PriceForBundleAndHardware priceInfo = null;

  @JsonProperty("metaData")
  private MetaData metaData = null;

  /**
   * 
   * @param deviceId
   * @return
   */
  public DeviceDetails deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Product id of the requested device from the product catalogue
   * @return deviceId
  **/
  @ApiModelProperty(value = "Product id of the requested device from the product catalogue")

/**
 * 
 * @return
 */
  public String getDeviceId() {
    return deviceId;
  }
/**
 * 
 * @param deviceId
 */
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
/**
 * 
 * @param name
 * @return
 */
  public DeviceDetails name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the product as provided in the product catalogue
   * @return name
  **/
  @ApiModelProperty(value = "Name of the product as provided in the product catalogue")

/**
 * 
 * @return
 */
  public String getName() {
    return name;
  }
/**
 * 
 * @param name
 */
  public void setName(String name) {
    this.name = name;
  }
/**
 * 
 * @param description
 * @return
 */
  public DeviceDetails description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the product as provided in the product catalogue
   * @return description
  **/
  @ApiModelProperty(value = "Description of the product as provided in the product catalogue")

/**
 * 
 * @return
 */
  public String getDescription() {
    return description;
  }
/**
 * 
 * @param description
 */
  public void setDescription(String description) {
    this.description = description;
  }
/**
 * 
 * @param validOffer
 * @return
 */
  public DeviceDetails validOffer(Boolean validOffer) {
    this.validOffer = validOffer;
    return this;
  }

   /**
   * Is Valid Offer Present
   * @return validOffer
  **/

  /**
 * 
 * @return
 */
  public Boolean getValidOffer() {
    return validOffer;
  }
/**
 * 
 * @param validOffer
 */
  public void setValidOffer(Boolean validOffer) {
    this.validOffer = validOffer;
  }
/**
 * 
 * @param productClass
 * @return
 */
  public DeviceDetails productClass(String productClass) {
    this.productClass = productClass;
    return this;
  }

   /**
   * Catalogue product class identifies products - SIMO, HANDSET etc.
   * @return productClass
  **/
  @ApiModelProperty(value = "Catalogue product class identifies products - SIMO, HANDSET etc.")

/**
 * 
 * @return
 */
  public String getProductClass() {
    return productClass;
  }
/**
 * 
 * @param productClass
 */
  public void setProductClass(String productClass) {
    this.productClass = productClass;
  }
/**
 * 
 * @param productLines
 * @return
 */
  public DeviceDetails productLines(List<String> productLines) {
    this.productLines = productLines;
    return this;
  }
/**
 * 
 * @param productLinesItem
 * @return
 */
  public DeviceDetails addProductLinesItem(String productLinesItem) {
    if (this.productLines == null) {
      this.productLines = new ArrayList<>();
    }
    this.productLines.add(productLinesItem);
    return this;
  }

   /**
   * Get productLines
   * @return productLines
  **/
  @ApiModelProperty(value = "")

/**
 * 
 * @return
 */
  public List<String> getProductLines() {
    return productLines;
  }
/**
 * 
 * @param productLines
 */
  public void setProductLines(List<String> productLines) {
    this.productLines = productLines;
  }
/**
 * 
 * @param merchandisingControl
 * @return
 */
  public DeviceDetails merchandisingControl(MerchandisingControl merchandisingControl) {
    this.merchandisingControl = merchandisingControl;
    return this;
  }

   /**
   * Get merchandisingControl
   * @return merchandisingControl
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public MerchandisingControl getMerchandisingControl() {
    return merchandisingControl;
  }
/**
 * 
 * @param merchandisingControl
 */
  public void setMerchandisingControl(MerchandisingControl merchandisingControl) {
    this.merchandisingControl = merchandisingControl;
  }
/**
 * 
 * @param merchandisingPromotion
 * @return
 */
  public DeviceDetails merchandisingPromotion(List<MerchandisingPromotions> merchandisingPromotion) {
    this.merchandisingPromotion = merchandisingPromotion;
    return this;
  }
/**
 * 
 * @param merchandisingPromotionItem
 * @return
 */
  public DeviceDetails addMerchandisingPromotionItem(MerchandisingPromotions merchandisingPromotionItem) {
    if (this.merchandisingPromotion == null) {
      this.merchandisingPromotion = new ArrayList<>();
    }
    this.merchandisingPromotion.add(merchandisingPromotionItem);
    return this;
  }

   /**
   * Get merchandisingPromotion
   * @return merchandisingPromotion
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public List<MerchandisingPromotions> getMerchandisingPromotion() {
    return merchandisingPromotion;
  }
/**
 * 
 * @param merchandisingPromotion
 */
  public void setMerchandisingPromotion(List<MerchandisingPromotions> merchandisingPromotion) {
    this.merchandisingPromotion = merchandisingPromotion;
  }
/**
 * 
 * @param stockAvailability
 * @return
 */
  public DeviceDetails stockAvailability(String stockAvailability) {
    this.stockAvailability = stockAvailability;
    return this;
  }

   /**
   * Availability of Stock
   * @return stockAvailability
  **/
  @ApiModelProperty(value = "Availability of Stock")

/**
 * 
 * @return
 */
  public String getStockAvailability() {
    return stockAvailability;
  }
/**
 * 
 * @param stockAvailability
 */
  public void setStockAvailability(String stockAvailability) {
    this.stockAvailability = stockAvailability;
  }
/**
 * 
 * @param rating
 * @return
 */
  public DeviceDetails rating(String rating) {
    this.rating = rating;
    return this;
  }

   /**
   * Bazaar voice rating of the product
   * @return rating
  **/
  @ApiModelProperty(value = "Bazaar voice rating of the product")

/**
 * 
 * @return
 */
  public String getRating() {
    return rating;
  }
/**
 * 
 * @param rating
 */
  public void setRating(String rating) {
    this.rating = rating;
  }
/**
 * 
 * @param productPageURI
 * @return
 */
  public DeviceDetails productPageURI(String productPageURI) {
    this.productPageURI = productPageURI;
    return this;
  }

   /**
   * Product Page URI
   * @return productPageURI
  **/
  @ApiModelProperty(value = "Product Page URI")

/**
 * 
 * @return
 */
  public String getProductPageURI() {
    return productPageURI;
  }
/**
 * 
 * @param productPageURI
 */
  public void setProductPageURI(String productPageURI) {
    this.productPageURI = productPageURI;
  }
/**
 * 
 * @param equipmentDetail
 * @return
 */
  public DeviceDetails equipmentDetail(Equipment equipmentDetail) {
    this.equipmentDetail = equipmentDetail;
    return this;
  }

   /**
   * Get equipmentDetail
   * @return equipmentDetail
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public Equipment getEquipmentDetail() {
    return equipmentDetail;
  }
/**
 * 
 * @param equipmentDetail
 */
  public void setEquipmentDetail(Equipment equipmentDetail) {
    this.equipmentDetail = equipmentDetail;
  }
/**
 * 
 * @param leadPlanId
 * @return
 */
  public DeviceDetails leadPlanId(String leadPlanId) {
    this.leadPlanId = leadPlanId;
    return this;
  }

   /**
   * Lead Plan ID for Product
   * @return leadPlanId
  **/
  @ApiModelProperty(value = "Lead Plan ID for Product")

/**
 * 
 * @return
 */
  public String getLeadPlanId() {
    return leadPlanId;
  }
/**
 * 
 * @param leadPlanId
 */
  public void setLeadPlanId(String leadPlanId) {
    this.leadPlanId = leadPlanId;
  }
/**
 * 
 * @param productAvailability
 * @return
 */
  public DeviceDetails productAvailability(ProductAvailability1 productAvailability) {
    this.productAvailability = productAvailability;
    return this;
  }

   /**
   * Get productAvailability
   * @return productAvailability
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public ProductAvailability1 getProductAvailability() {
    return productAvailability;
  }
/**
 * 
 * @param productAvailability
 */
  public void setProductAvailability(ProductAvailability1 productAvailability) {
    this.productAvailability = productAvailability;
  }
/**
 * 
 * @param media
 * @return
 */
  public DeviceDetails media(List<MediaLink> media) {
    this.media = media;
    return this;
  }
/**
 * 
 * @param mediaItem
 * @return
 */
  public DeviceDetails addMediaItem(MediaLink mediaItem) {
    if (this.media == null) {
      this.media = new ArrayList<>();
    }
    this.media.add(mediaItem);
    return this;
  }

   /**
   * Get media
   * @return media
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public List<MediaLink> getMedia() {
    return media;
  }
/**
 * 
 * @param media
 */
  public void setMedia(List<MediaLink> media) {
    this.media = media;
  }
/**
 * 
 * @param specificationsGroups
 * @return
 */
  public DeviceDetails specificationsGroups(List<SpecificationGroup> specificationsGroups) {
    this.specificationsGroups = specificationsGroups;
    return this;
  }
/**
 * 
 * @param specificationsGroupsItem
 * @return
 */
  public DeviceDetails addSpecificationsGroupsItem(SpecificationGroup specificationsGroupsItem) {
    if (this.specificationsGroups == null) {
      this.specificationsGroups = new ArrayList<>();
    }
    this.specificationsGroups.add(specificationsGroupsItem);
    return this;
  }

   /**
   * Get specificationsGroups
   * @return specificationsGroups
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public List<SpecificationGroup> getSpecificationsGroups() {
    return specificationsGroups;
  }
/**
 * 
 * @param specificationsGroups
 */
  public void setSpecificationsGroups(List<SpecificationGroup> specificationsGroups) {
    this.specificationsGroups = specificationsGroups;
  }
/**
 * 
 * @param priceInfo
 * @return
 */
  public DeviceDetails priceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
    return this;
  }

   /**
   * Get priceInfo
   * @return priceInfo
  **/
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
 * 
 * @param priceInfo
 */
  public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
  }
/**
 * 
 * @param metaData
 * @return
 */
  public DeviceDetails metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

   /**
   * Get metaData
   * @return metaData
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public MetaData getMetaData() {
    return metaData;
  }
/**
 * 
 * @param metaData
 */
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
        Objects.equals(this.validOffer, deviceDetails.validOffer) &&
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
    return Objects.hash(deviceId, name, description, validOffer, productClass, productLines, merchandisingControl, merchandisingPromotion, stockAvailability, rating, productPageURI, equipmentDetail, leadPlanId, productAvailability, media, specificationsGroups, priceInfo, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceDetails {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    validOffer: ").append(toIndentedString(validOffer)).append("\n");
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

