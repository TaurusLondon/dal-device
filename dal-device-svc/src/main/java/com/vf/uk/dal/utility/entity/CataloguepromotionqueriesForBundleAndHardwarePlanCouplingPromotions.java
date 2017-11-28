package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
/**
 * CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-05T11:10:07.841Z")

public class CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions   {
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

  @JsonProperty("plancoupleId")
  private String plancoupleId = null;

  public CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions tag(String tag) {
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

  public CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions label(String label) {
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

  public CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions type(String type) {
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

  public CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions priority(String priority) {
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

  public CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions description(String description) {
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

  public CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions plancoupleId(String plancoupleId) {
    this.plancoupleId = plancoupleId;
    return this;
  }

   /**
   * Plan couple Id for the merchandising promotion
   * @return plancoupleId
  **/
  public String getPlancoupleId() {
    return plancoupleId;
  }

  public void setPlancoupleId(String plancoupleId) {
    this.plancoupleId = plancoupleId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions = (CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions) o;
    return Objects.equals(this.tag, cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.tag) &&
        Objects.equals(this.label, cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.label) &&
        Objects.equals(this.type, cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.type) &&
        Objects.equals(this.priority, cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.priority) &&
        Objects.equals(this.description, cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.description) &&
        Objects.equals(this.plancoupleId, cataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.plancoupleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag, label, type, priority, description, plancoupleId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions {\n");
    
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    plancoupleId: ").append(toIndentedString(plancoupleId)).append("\n");
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

