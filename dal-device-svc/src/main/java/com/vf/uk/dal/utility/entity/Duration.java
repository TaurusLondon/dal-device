package com.vf.uk.dal.utility.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Duration
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-14T09:23:00.845Z")

public class Duration   {
  @JsonProperty("uom")
  private String uom = null;

  public String getUom() {
	return uom;
}

public void setUom(String uom) {
	this.uom = uom;
}

@JsonProperty("value")
  private String value = null;

  public Duration uom(String uom) {
    this.uom = uom;
    return this;
  }

   /**
   * Unit of measurement for duration value
   * @return uom
  **/
 /* public String getuom() {
    return uom;
  }

  public void setuom(String uom) {
    this.uom = uom;
  }*/

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

