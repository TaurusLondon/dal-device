package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Device
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-27T15:23:01.152Z")

public class Device   {
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

  public Device deviceId(String deviceId) {
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

  public Device name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the product group as provided in the product catalogue
   * @return name
  **/


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Device make(String make) {
    this.make = make;
    return this;
  }

   /**
   * Make of the product
   * @return make
  **/


  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public Device model(String model) {
    this.model = model;
    return this;
  }

   /**
   * Model of the product
   * @return model
  **/


  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Device groupType(String groupType) {
    this.groupType = groupType;
    return this;
  }

   /**
   * Group type of product group DEVICE_PAYM, DEVICE_PAYG
   * @return groupType
  **/


  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }

  public Device rating(String rating) {
    this.rating = rating;
    return this;
  }

   /**
   * This will indicate the number of rating starts to be displayed on screen.
   * @return rating
  **/


  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public Device description(String description) {
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

  public Device productClass(String productClass) {
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

  public Device merchandisingControl(MerchandisingControl merchandisingControl) {
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

  public Device media(List<MediaLink> media) {
    this.media = media;
    return this;
  }

  public Device addMediaItem(MediaLink mediaItem) {
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

  public Device priceInfo(PriceForBundleAndHardware priceInfo) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Device device = (Device) o;
    return Objects.equals(this.deviceId, device.deviceId) &&
        Objects.equals(this.name, device.name) &&
        Objects.equals(this.make, device.make) &&
        Objects.equals(this.model, device.model) &&
        Objects.equals(this.groupType, device.groupType) &&
        Objects.equals(this.rating, device.rating) &&
        Objects.equals(this.description, device.description) &&
        Objects.equals(this.productClass, device.productClass) &&
        Objects.equals(this.merchandisingControl, device.merchandisingControl) &&
        Objects.equals(this.media, device.media) &&
        Objects.equals(this.priceInfo, device.priceInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, name, make, model, groupType, rating, description, productClass, merchandisingControl, media, priceInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Device {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    make: ").append(toIndentedString(make)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    groupType: ").append(toIndentedString(groupType)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
    sb.append("    merchandisingControl: ").append(toIndentedString(merchandisingControl)).append("\n");
    sb.append("    media: ").append(toIndentedString(media)).append("\n");
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

