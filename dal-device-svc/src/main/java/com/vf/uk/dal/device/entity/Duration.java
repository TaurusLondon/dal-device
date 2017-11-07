package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * Duration
 */

public class Duration   {
  private String uom = null;

  private String value = null;

  public Duration uom(String uom) {
    this.uom = uom;
    return this;
  }

   /**
   * Unit of measurement for duration value
   * @return uom
  **/
  public String getUom() {
    return uom;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public Duration value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Duration value
   * @return value
  **/
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Duration duration = (Duration) o;
    return Objects.equals(this.uom, duration.uom) &&
        Objects.equals(this.value, duration.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uom, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Duration {\n");
    
    sb.append("    uom: ").append(toIndentedString(uom)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

