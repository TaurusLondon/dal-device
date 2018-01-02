package com.vf.uk.dal.device.entity;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

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

  public Insurance id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique id of the insurance product as available from the product catalogue
   * @return id
  **/
  @ApiModelProperty(value = "Unique id of the insurance product as available from the product catalogue")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Insurance name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the Insurance as provided in the product catalogue
   * @return name
  **/
  @ApiModelProperty(value = "Name of the Insurance as provided in the product catalogue")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = price;
  }

  public Insurance specsGroup(List<SpecificationGroup> specsGroup) {
    this.specsGroup = specsGroup;
    return this;
  }

  public Insurance addSpecsGroupItem(SpecificationGroup specsGroupItem) {
    if (this.specsGroup == null) {
      this.specsGroup = new ArrayList<SpecificationGroup>();
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

  public List<SpecificationGroup> getSpecsGroup() {
    return specsGroup;
  }

  public void setSpecsGroup(List<SpecificationGroup> specsGroup) {
    this.specsGroup = specsGroup;
  }

  public Insurance merchandisingMedia(List<MediaLink> merchandisingMedia) {
	    this.merchandisingMedia = merchandisingMedia;
	    return this;
	  }

	  public Insurance addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
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

	  @Valid

	  public List<MediaLink> getMerchandisingMedia() {
	    return merchandisingMedia;
	  }

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

