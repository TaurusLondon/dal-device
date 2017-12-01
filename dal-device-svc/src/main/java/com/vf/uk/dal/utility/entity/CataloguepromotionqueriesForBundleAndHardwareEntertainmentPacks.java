package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
/**
 * CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-05T11:10:07.841Z")

public class CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks   {
  @JsonProperty("tag")
  private String tag = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("priority")
  private String priority = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("promotionMedia")
  private String promotionMedia = null;

  public CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks tag(String tag) {
    this.tag = tag;
    return this;
  }

   /**
   * Unique tag name for the merchandising promotion
   * @return tag
  **/
  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks label(String label) {
    this.label = label;
    return this;
  }

   /**
   * Descriptive text for the merchandising promotion
   * @return label
  **/
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Merchandising promotion type
   * @return type
  **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks priority(String priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Priority for the merchandising promotion
   * @return priority
  **/
  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description for the merchandising promotion
   * @return description
  **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks promotionMedia(String promotionMedia) {
    this.promotionMedia = promotionMedia;
    return this;
  }

   /**
   * Promotion media url for the merchandising promotion
   * @return promotionMedia
  **/
  public String getPromotionMedia() {
    return promotionMedia;
  }

  public void setPromotionMedia(String promotionMedia) {
    this.promotionMedia = promotionMedia;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks = (CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks) o;
    return Objects.equals(this.tag, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.tag) &&
        Objects.equals(this.label, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.label) &&
        Objects.equals(this.type, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.type) &&
        Objects.equals(this.priority, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.priority) &&
        Objects.equals(this.description, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.description) &&
        Objects.equals(this.promotionMedia, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.promotionMedia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag, label, type, priority, description, promotionMedia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks {\n");
    
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    promotionMedia: ").append(toIndentedString(promotionMedia)).append("\n");
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

