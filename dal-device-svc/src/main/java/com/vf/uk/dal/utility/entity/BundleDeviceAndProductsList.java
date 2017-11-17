package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BundleDeviceAndProductsList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-17T11:31:50.578Z")

public class BundleDeviceAndProductsList   {
  @JsonProperty("offerCode")
  private String offerCode = null;

  @JsonProperty("packageType")
  private String packageType = null;

  @JsonProperty("deviceId")
  private String deviceId = null;

  @JsonProperty("bundleId")
  private String bundleId = null;

  @JsonProperty("accessoryList")
  private List<String> accessoryList = null;

  @JsonProperty("extraList")
  private List<String> extraList = null;

  public BundleDeviceAndProductsList offerCode(String offerCode) {
    this.offerCode = offerCode;
    return this;
  }

   /**
   * offercode will be proived as part of journey
   * @return offerCode
  **/


  public String getOfferCode() {
    return offerCode;
  }

  public void setOfferCode(String offerCode) {
    this.offerCode = offerCode;
  }

  public BundleDeviceAndProductsList packageType(String packageType) {
    this.packageType = packageType;
    return this;
  }

   /**
   * Journey type will be proived as part of journey and its mandatory filed
   * @return packageType
  **/


  public String getPackageType() {
    return packageType;
  }

  public void setPackageType(String packageType) {
    this.packageType = packageType;
  }

  public BundleDeviceAndProductsList deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Unique Device id as available from the product catalogue
   * @return deviceId
  **/


  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public BundleDeviceAndProductsList bundleId(String bundleId) {
    this.bundleId = bundleId;
    return this;
  }

   /**
   * Unique Bundle id as available from the product catalogue
   * @return bundleId
  **/


  public String getBundleId() {
    return bundleId;
  }

  public void setBundleId(String bundleId) {
    this.bundleId = bundleId;
  }

  public BundleDeviceAndProductsList accessoryList(List<String> accessoryList) {
    this.accessoryList = accessoryList;
    return this;
  }

  public BundleDeviceAndProductsList addAccessoryListItem(String accessoryListItem) {
    if (this.accessoryList == null) {
      this.accessoryList = new ArrayList<String>();
    }
    this.accessoryList.add(accessoryListItem);
    return this;
  }

   /**
   * Unique Accessory id as available from the product catalogue
   * @return accessoryList
  **/


  public List<String> getAccessoryList() {
    return accessoryList;
  }

  public void setAccessoryList(List<String> accessoryList) {
    this.accessoryList = accessoryList;
  }

  public BundleDeviceAndProductsList extraList(List<String> extraList) {
    this.extraList = extraList;
    return this;
  }

  public BundleDeviceAndProductsList addExtraListItem(String extraListItem) {
    if (this.extraList == null) {
      this.extraList = new ArrayList<String>();
    }
    this.extraList.add(extraListItem);
    return this;
  }

   /**
   * Unique Extra id as available from the product catalogue
   * @return extraList
  **/


  public List<String> getExtraList() {
    return extraList;
  }

  public void setExtraList(List<String> extraList) {
    this.extraList = extraList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleDeviceAndProductsList bundleDeviceAndProductsList = (BundleDeviceAndProductsList) o;
    return Objects.equals(this.offerCode, bundleDeviceAndProductsList.offerCode) &&
        Objects.equals(this.packageType, bundleDeviceAndProductsList.packageType) &&
        Objects.equals(this.deviceId, bundleDeviceAndProductsList.deviceId) &&
        Objects.equals(this.bundleId, bundleDeviceAndProductsList.bundleId) &&
        Objects.equals(this.accessoryList, bundleDeviceAndProductsList.accessoryList) &&
        Objects.equals(this.extraList, bundleDeviceAndProductsList.extraList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offerCode, packageType, deviceId, bundleId, accessoryList, extraList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleDeviceAndProductsList {\n");
    
    sb.append("    offerCode: ").append(toIndentedString(offerCode)).append("\n");
    sb.append("    packageType: ").append(toIndentedString(packageType)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
    sb.append("    accessoryList: ").append(toIndentedString(accessoryList)).append("\n");
    sb.append("    extraList: ").append(toIndentedString(extraList)).append("\n");
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

