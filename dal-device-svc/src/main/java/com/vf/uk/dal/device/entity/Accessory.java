package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.utility.entity.PriceForAccessory;

import io.swagger.annotations.ApiModelProperty;

/**
 * Accessory
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Accessory   {
  @JsonProperty("skuId")
  private String skuId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("colour")
  private String colour = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("merchandisingMedia")
  private List<MediaLink> merchandisingMedia = null;

  @JsonProperty("deviceCost")
  private PriceForAccessory deviceCost = null;

  @JsonProperty("attributes")
  private List<Attributes> attributes = null;

  public Accessory skuId(String skuId) {
    this.skuId = skuId;
    return this;
  }

   /**
   * Unique Accessory id as available from the product catalogue
   * @return skuId
  **/
  @ApiModelProperty(value = "Unique Accessory id as available from the product catalogue")

 /**
 * 
 * @return
 */
  public String getSkuId() {
    return skuId;
  }

  /**
   * 
   * @param skuId
   */
  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  /**
   * 
   * @param name
   * @return
   */
  public Accessory name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the Accessory as provided in the product catalogue
   * @return name
  **/
  @ApiModelProperty(value = "Name of the Accessory as provided in the product catalogue")

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
   * @param colour
   * @return
   */
  public Accessory colour(String colour) {
    this.colour = colour;
    return this;
  }

   /**
   * Colour of the Accessory as provided in the product catalogue
   * @return colour
  **/
  @ApiModelProperty(value = "Colour of the Accessory as provided in the product catalogue")

  /**
   * 
   * @return
   */
  public String getColour() {
    return colour;
  }
  
  /**
   * 
   * @param colour
   */
  public void setColour(String colour) {
    this.colour = colour;
  }
  
  /**
   * 
   * @param description
   * @return
   */
  public Accessory description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the Accessory as provided in the product catalogue
   * @return description
  **/
  @ApiModelProperty(value = "Description of the Accessory as provided in the product catalogue")

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
   * @param merchandisingMedia
   * @return
   */
  public Accessory merchandisingMedia(List<MediaLink> merchandisingMedia) {
    this.merchandisingMedia = merchandisingMedia;
    return this;
  }

  /**
   * 
   * @param merchandisingMediaItem
   * @return
   */
  public Accessory addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
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
  /**
   * 
   * @return
   */
  public List<MediaLink> getMerchandisingMedia() {
    return merchandisingMedia;
  }
 
  /**
   * 
   * @param merchandisingMedia
   */
  public void setMerchandisingMedia(List<MediaLink> merchandisingMedia) {
    this.merchandisingMedia = merchandisingMedia;
  }

  /**
   * 
   * @param deviceCost
   * @return
   */
  public Accessory deviceCost(PriceForAccessory deviceCost) {
    this.deviceCost = deviceCost;
    return this;
  }

   /**
   * Get deviceCost
   * @return deviceCost
  **/
  @ApiModelProperty(value = "")

  @Valid
  /**
   * 
   * @return
   */
  public PriceForAccessory getDeviceCost() {
    return deviceCost;
  }
  /**
  * 
  * @param deviceCost
  */
  public void setDeviceCost(PriceForAccessory deviceCost) {
    this.deviceCost = deviceCost;
  }

  /**
   * 
   * @param attributes
   * @return
   */
  public Accessory attributes(List<Attributes> attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * 
   * @param attributesItem
   * @return
   */
  public Accessory addAttributesItem(Attributes attributesItem) {
    if (this.attributes == null) {
      this.attributes = new ArrayList<Attributes>();
    }
    this.attributes.add(attributesItem);
    return this;
  }

   /**
   * Get attributes
   * @return attributes
  **/
  @ApiModelProperty(value = "")

  @Valid

  /**
   * 
   * @return
   */
  public List<Attributes> getAttributes() {
    return attributes;
  }

  /**
   * 
   * @param attributes
   */
  public void setAttributes(List<Attributes> attributes) {
    this.attributes = attributes;
  }


  @Override
  /**
   * 
   */
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Accessory accessory = (Accessory) o;
    return Objects.equals(this.skuId, accessory.skuId) &&
        Objects.equals(this.name, accessory.name) &&
        Objects.equals(this.colour, accessory.colour) &&
        Objects.equals(this.description, accessory.description) &&
        Objects.equals(this.merchandisingMedia, accessory.merchandisingMedia) &&
        Objects.equals(this.deviceCost, accessory.deviceCost) &&
        Objects.equals(this.attributes, accessory.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(skuId, name, colour, description, merchandisingMedia, deviceCost, attributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Accessory {\n");
    
    sb.append("    skuId: ").append(toIndentedString(skuId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    colour: ").append(toIndentedString(colour)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    merchandisingMedia: ").append(toIndentedString(merchandisingMedia)).append("\n");
    sb.append("    deviceCost: ").append(toIndentedString(deviceCost)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
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

