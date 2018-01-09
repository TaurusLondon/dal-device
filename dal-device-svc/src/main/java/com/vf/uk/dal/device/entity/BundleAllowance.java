package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * BundleAllowance
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-14T09:23:00.845Z")

public class BundleAllowance   {
  @JsonProperty("type")
  private String type = null;

  @JsonProperty("value")
  private String value = null;

  @JsonProperty("uom")
  private String uom = null;
  
  @JsonProperty("displayUom")
  private String displayUom = null;

  /**
	 * 
	 * @return
	 */
  public String getDisplayUom() {
	return displayUom;
}

  /**
	 * 
	 * @return
	 */
public void setDisplayUom(String displayUom) {
	this.displayUom = displayUom;
}
/**
 * 
 * @return
 */
public BundleAllowance type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Allowance type - \"DATA\",\"TEXT\",\"CALL\"
   * @return type
  **/
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
  public BundleAllowance value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Value of the allowance
   * @return value
  **/
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
  public BundleAllowance uom(String uom) {
    this.uom = uom;
    return this;
  }

   /**
   * Unit of measurement to be used for the allowance - \"MB\" for DATA. \"COUNT\" fpr TEXT amd \"MIN\" for CALL
   * @return uom
  **/
  public String getUom() {
    return uom;
  }

  /**
	 * 
	 * @return
	 */
  public void setUom(String uom) {
    this.uom = uom;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleAllowance bundleAllowance = (BundleAllowance) o;
    return Objects.equals(this.type, bundleAllowance.type) &&
        Objects.equals(this.value, bundleAllowance.value) &&
        Objects.equals(this.uom, bundleAllowance.uom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, value, uom);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleAllowance {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    uom: ").append(toIndentedString(uom)).append("\n");
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

