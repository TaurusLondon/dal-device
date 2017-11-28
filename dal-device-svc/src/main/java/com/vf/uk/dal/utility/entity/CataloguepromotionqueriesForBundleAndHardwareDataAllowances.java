package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
/**
 * CataloguepromotionqueriesForBundleAndHardwareDataAllowances
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-05T11:10:07.841Z")

public class CataloguepromotionqueriesForBundleAndHardwareDataAllowances   {
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

  @JsonProperty("freeData")
  private CataloguepromotionqueriesForBundleAndHardwareFreeData freeData = null;

  public CataloguepromotionqueriesForBundleAndHardwareDataAllowances tag(String tag) {
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

  public CataloguepromotionqueriesForBundleAndHardwareDataAllowances label(String label) {
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

  public CataloguepromotionqueriesForBundleAndHardwareDataAllowances type(String type) {
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

  public CataloguepromotionqueriesForBundleAndHardwareDataAllowances priority(String priority) {
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

  public CataloguepromotionqueriesForBundleAndHardwareDataAllowances description(String description) {
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

  public CataloguepromotionqueriesForBundleAndHardwareDataAllowances freeData(CataloguepromotionqueriesForBundleAndHardwareFreeData freeData) {
    this.freeData = freeData;
    return this;
  }

   /**
   * Get freeData
   * @return freeData
  **/
  public CataloguepromotionqueriesForBundleAndHardwareFreeData getFreeData() {
    return freeData;
  }

  public void setFreeData(CataloguepromotionqueriesForBundleAndHardwareFreeData freeData) {
    this.freeData = freeData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CataloguepromotionqueriesForBundleAndHardwareDataAllowances cataloguepromotionqueriesForBundleAndHardwareDataAllowances = (CataloguepromotionqueriesForBundleAndHardwareDataAllowances) o;
    return Objects.equals(this.tag, cataloguepromotionqueriesForBundleAndHardwareDataAllowances.tag) &&
        Objects.equals(this.label, cataloguepromotionqueriesForBundleAndHardwareDataAllowances.label) &&
        Objects.equals(this.type, cataloguepromotionqueriesForBundleAndHardwareDataAllowances.type) &&
        Objects.equals(this.priority, cataloguepromotionqueriesForBundleAndHardwareDataAllowances.priority) &&
        Objects.equals(this.description, cataloguepromotionqueriesForBundleAndHardwareDataAllowances.description) &&
        Objects.equals(this.freeData, cataloguepromotionqueriesForBundleAndHardwareDataAllowances.freeData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag, label, type, priority, description, freeData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CataloguepromotionqueriesForBundleAndHardwareDataAllowances {\n");
    
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    freeData: ").append(toIndentedString(freeData)).append("\n");
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

