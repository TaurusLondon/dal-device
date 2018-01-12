package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Insurance
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Insurance   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("price")
  private Price price = null;
  @JsonProperty("merchandisingMedia")
  private List<MediaLink> merchandisingMedia = null;
  @JsonProperty("specsGroup")
  private List<SpecificationGroup> specsGroup = null;

  /**
   * 
   * @param id
   * @return
   */
  public Insurance id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique id of the insurance product as available from the product catalogue
   * @return id
  **/
  @ApiModelProperty(value = "Unique id of the insurance product as available from the product catalogue")

/**
 * 
 * @return
 */
  public String getId() {
    return id;
  }
/**
 * 
 * @param id
 */
  public void setId(String id) {
    this.id = id;
  }
/**
 * 
 * @param name
 * @return
 */
  public Insurance name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the Insurance as provided in the product catalogue
   * @return name
  **/
  @ApiModelProperty(value = "Name of the Insurance as provided in the product catalogue")

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
 * @param price
 * @return
 */
  public Insurance price(Price price) {
    this.price = price;
    return this;
  }

   /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public Price getPrice() {
    return price;
  }
/**
 * 
 * @param price
 */
  public void setPrice(Price price) {
    this.price = price;
  }
/**
 * 
 * @param specsGroup
 * @return
 */
  public Insurance specsGroup(List<SpecificationGroup> specsGroup) {
    this.specsGroup = specsGroup;
    return this;
  }
/**
 * 
 * @param specsGroupItem
 * @return
 */
  public Insurance addSpecsGroupItem(SpecificationGroup specsGroupItem) {
    if (this.specsGroup == null) {
      this.specsGroup = new ArrayList<>();
    }
    this.specsGroup.add(specsGroupItem);
    return this;
  }

   /**
   * Get specsGroup
   * @return specsGroup
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public List<SpecificationGroup> getSpecsGroup() {
    return specsGroup;
  }
/**
 * 
 * @param specsGroup
 */
  public void setSpecsGroup(List<SpecificationGroup> specsGroup) {
    this.specsGroup = specsGroup;
  }
/**
 * 
 * @param merchandisingMedia
 * @return
 */
  public Insurance merchandisingMedia(List<MediaLink> merchandisingMedia) {
	    this.merchandisingMedia = merchandisingMedia;
	    return this;
	  }
/**
 * 
 * @param merchandisingMediaItem
 * @return
 */
	  public Insurance addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
	    if (this.merchandisingMedia == null) {
	      this.merchandisingMedia = new ArrayList<>();
	    }
	    this.merchandisingMedia.add(merchandisingMediaItem);
	    return this;
	  }

	   /**
	   * Get merchandisingMedia
	   * @return merchandisingMedia
	  **/

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Insurance insurance = (Insurance) o;
    return Objects.equals(this.id, insurance.id) &&
        Objects.equals(this.name, insurance.name) &&
        Objects.equals(this.price, insurance.price) &&
        Objects.equals(this.specsGroup, insurance.specsGroup) &&
    Objects.equals(this.merchandisingMedia, insurance.merchandisingMedia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, specsGroup,merchandisingMedia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Insurance {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    specsGroup: ").append(toIndentedString(specsGroup)).append("\n");
    sb.append("    merchandisingMedia: ").append(toIndentedString(merchandisingMedia)).append("\n");
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

