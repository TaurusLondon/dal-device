package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * Specification
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Specification   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("value")
  private String value = null;

  @JsonProperty("priority")
  private Integer priority = null;

  @JsonProperty("comparable")
  private Boolean comparable = null;

  @JsonProperty("isKey")
  private Boolean isKey = null;

  @JsonProperty("valueType")
  private String valueType = null;

  @JsonProperty("valueUOM")
  private String valueUOM = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("footNote")
  private String footNote = null;

  public Specification name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the specification, color, storage, vedeo recording, etc
   * @return name
  **/
  @ApiModelProperty(value = "Name of the specification, color, storage, vedeo recording, etc")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Specification value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Value of the field
   * @return value
  **/
  @ApiModelProperty(value = "Value of the field")


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Specification priority(Integer priority) {
    this.priority = priority;
    return this;
  }

   /**
   * display Priority of the specification field
   * @return priority
  **/
  @ApiModelProperty(value = "display Priority of the specification field")


  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Specification comparable(Boolean comparable) {
    this.comparable = comparable;
    return this;
  }

   /**
   * Identify if the feature is coparable
   * @return comparable
  **/
  @ApiModelProperty(value = "Identify if the feature is coparable")


  public Boolean getComparable() {
    return comparable;
  }

  public void setComparable(Boolean comparable) {
    this.comparable = comparable;
  }

  public Specification isKey(Boolean isKey) {
    this.isKey = isKey;
    return this;
  }

   /**
   * Identify if this a key feature
   * @return isKey
  **/
  @ApiModelProperty(value = "Identify if this a key feature")


  public Boolean getIsKey() {
    return isKey;
  }

  public void setIsKey(Boolean isKey) {
    this.isKey = isKey;
  }

  public Specification valueType(String valueType) {
    this.valueType = valueType;
    return this;
  }

   /**
   * Type of the value, TEXT, INTEGER, DECOMAL, BOOLEAN
   * @return valueType
  **/
  @ApiModelProperty(value = "Type of the value, TEXT, INTEGER, DECOMAL, BOOLEAN")


  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public Specification valueUOM(String valueUOM) {
    this.valueUOM = valueUOM;
    return this;
  }

   /**
   * Unit of measure of the value if applicable
   * @return valueUOM
  **/
  @ApiModelProperty(value = "Unit of measure of the value if applicable")


  public String getValueUOM() {
    return valueUOM;
  }

  public void setValueUOM(String valueUOM) {
    this.valueUOM = valueUOM;
  }

  public Specification description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Friendly description, Alternative to tabular name value
   * @return description
  **/
  @ApiModelProperty(value = "Friendly description, Alternative to tabular name value")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Specification footNote(String footNote) {
    this.footNote = footNote;
    return this;
  }

   /**
   * A foot note to the specification
   * @return footNote
  **/
  @ApiModelProperty(value = "A foot note to the specification")


  public String getFootNote() {
    return footNote;
  }

  public void setFootNote(String footNote) {
    this.footNote = footNote;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Specification specification = (Specification) o;
    return Objects.equals(this.name, specification.name) &&
        Objects.equals(this.value, specification.value) &&
        Objects.equals(this.priority, specification.priority) &&
        Objects.equals(this.comparable, specification.comparable) &&
        Objects.equals(this.isKey, specification.isKey) &&
        Objects.equals(this.valueType, specification.valueType) &&
        Objects.equals(this.valueUOM, specification.valueUOM) &&
        Objects.equals(this.description, specification.description) &&
        Objects.equals(this.footNote, specification.footNote);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, priority, comparable, isKey, valueType, valueUOM, description, footNote);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Specification {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    comparable: ").append(toIndentedString(comparable)).append("\n");
    sb.append("    isKey: ").append(toIndentedString(isKey)).append("\n");
    sb.append("    valueType: ").append(toIndentedString(valueType)).append("\n");
    sb.append("    valueUOM: ").append(toIndentedString(valueUOM)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    footNote: ").append(toIndentedString(footNote)).append("\n");
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

