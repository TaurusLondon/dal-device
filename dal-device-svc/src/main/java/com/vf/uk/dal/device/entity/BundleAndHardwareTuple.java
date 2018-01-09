package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * BundleAndHardwareTuple
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-23T04:18:28.504Z")

public class BundleAndHardwareTuple   {
  @JsonProperty("hardwareId")
  private String hardwareId = null;

  @JsonProperty("bundleId")
  private String bundleId = null;

  /**
	 * 
	 * @return
	 */
  public BundleAndHardwareTuple hardwareId(String hardwareId) {
    this.hardwareId = hardwareId;
    return this;
  }

   /**
   * Unique hardware id as available from the product catalogue
   * @return hardwareId
  **/
  public String getHardwareId() {
    return hardwareId;
  }

  /**
	 * 
	 * @return
	 */
  public void setHardwareId(String hardwareId) {
    this.hardwareId = hardwareId;
  }

  /**
	 * 
	 * @return
	 */
  public BundleAndHardwareTuple bundleId(String bundleId) {
    this.bundleId = bundleId;
    return this;
  }

   /**
   * Unique bundle id as available from the product catalogue
   * @return bundleId
  **/
  public String getBundleId() {
    return bundleId;
  }

  /**
	 * 
	 * @return
	 */
  public void setBundleId(String bundleId) {
    this.bundleId = bundleId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleAndHardwareTuple bundleAndHardwareTuple = (BundleAndHardwareTuple) o;
    return Objects.equals(this.hardwareId, bundleAndHardwareTuple.hardwareId) &&
        Objects.equals(this.bundleId, bundleAndHardwareTuple.bundleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hardwareId, bundleId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleAndHardwareTuple {\n");
    
    sb.append("    hardwareId: ").append(toIndentedString(hardwareId)).append("\n");
    sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
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

