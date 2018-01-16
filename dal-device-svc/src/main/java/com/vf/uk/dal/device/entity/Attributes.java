package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * Attributes
 */

public class Attributes   {
  @JsonProperty("key")
  private String key = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("value")
  private String value = null;

  @JsonProperty("valueUOM")
  private String valueUOM = null;

  /**
   * 
   * @param key
   * @return
   */
  public Attributes key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Item Attribute Key
   * @return key
  **/
  @ApiModelProperty(value = "Item Attribute Key")

  /**
	 * 
	 * @return
	 */
  public String getKey() {
    return key;
  }
  /**
	 * 
	 * @return
	 */
  public void setKey(String key) {
    this.key = key;
  }
  /**
	 * 
	 * @return
	 */
  public Attributes type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Item Attribute Type
   * @return type
  **/
  @ApiModelProperty(value = "Item Attribute Type")

  /**
	 * 
	 * @return
	 */
  public String getType() {
    return type;
  }
  /**
	 * 
	 * @return
	 */
  public void setType(String type) {
    this.type = type;
  }
  /**
	 * 
	 * @return
	 */
  public Attributes value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Item Attribute Value
   * @return value
  **/
  @ApiModelProperty(value = "Item Attribute Value")

  /**
	 * 
	 * @return
	 */
  public String getValue() {
    return value;
  }

  /**
	 * 
	 * @return
	 */
  public void setValue(String value) {
    this.value = value;
  }

  /**
	 * 
	 * @return
	 */
  public Attributes valueUOM(String valueUOM) {
    this.valueUOM = valueUOM;
    return this;
  }

   /**
   * Item Attribute Value UOM
   * @return valueUOM
  **/
  @ApiModelProperty(value = "Item Attribute Value UOM")


  /**
	 * 
	 * @return
	 */
  public String getValueUOM() {
    return valueUOM;
  }
  /**
	 * 
	 * @return
	 */
  public void setValueUOM(String valueUOM) {
    this.valueUOM = valueUOM;
  }

  /**
	 * 
	 * @return
	 */
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attributes attributes = (Attributes) o;
    return Objects.equals(this.key, attributes.key) &&
        Objects.equals(this.type, attributes.type) &&
        Objects.equals(this.value, attributes.value) &&
        Objects.equals(this.valueUOM, attributes.valueUOM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, type, value, valueUOM);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attributes {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    valueUOM: ").append(toIndentedString(valueUOM)).append("\n");
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

