package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * DeviceTile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-30T13:33:13.488Z")

public class DeviceTile   {
  @JsonProperty("deviceId")
  private String deviceId = null;

  @JsonProperty("groupName")
  private String groupName = null;

  @JsonProperty("groupType")
  private String groupType = null;

  @JsonProperty("rating")
  private String rating = null;

  @JsonProperty("reviewCount")
  private String reviewCount = null;

  @JsonProperty("deviceSummary")
  private List<DeviceSummary> deviceSummary = null;

  /**
   * 
   * @param deviceId
   * @return
   */
  public DeviceTile deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Device of the Lead Member within the group
   * @return deviceId
  **/
  @ApiModelProperty(value = "Device of the Lead Member within the group")

/**
 * 
 * @return
 */
  public String getDeviceId() {
    return deviceId;
  }
/**
 * 
 * @param deviceId
 */
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
/**
 * 
 * @param groupName
 * @return
 */
  public DeviceTile groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

   /**
   * Group Name of the Device
   * @return groupName
  **/
  @ApiModelProperty(value = "Group Name of the Device")

/**
 * 
 * @return
 */
  public String getGroupName() {
    return groupName;
  }
/**
 * 
 * @param groupName
 */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
/**
 * 
 * @param groupType
 * @return
 */
  public DeviceTile groupType(String groupType) {
    this.groupType = groupType;
    return this;
  }

   /**
   * Group Type this devide belongs to, like Handset
   * @return groupType
  **/
  @ApiModelProperty(value = "Group Type this devide belongs to, like Handset")

/**
 * 
 * @return
 */
  public String getGroupType() {
    return groupType;
  }
/**
 * 
 * @param groupType
 */
  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }
/**
 * 
 * @param rating
 * @return
 */
  public DeviceTile rating(String rating) {
    this.rating = rating;
    return this;
  }

   /**
   * Rating for the device Id
   * @return rating
  **/
  @ApiModelProperty(value = "Rating for the device Id")

/**
 * 
 * @return
 */
  public String getRating() {
    return rating;
  }
/**
 * 
 * @param rating
 */
  public void setRating(String rating) {
    this.rating = rating;
  }
/**
 * 
 * @param reviewCount
 * @return
 */
  public DeviceTile reviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
    return this;
  }

   /**
   * Rating for the device Id
   * @return reviewCount
  **/
  @ApiModelProperty(value = "Rating for the device Id")
  public String getReviewCount() {
    return reviewCount;
  }

  /**
   * 
   * @param reviewCount
   */
  public void setReviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
  }

  /**
   * 
   * @param deviceSummary
   * @return
   */
  public DeviceTile deviceSummary(List<DeviceSummary> deviceSummary) {
    this.deviceSummary = deviceSummary;
    return this;
  }

  /**
   * 
   * @param deviceSummaryItem
   * @return
   */
  public DeviceTile addDeviceSummaryItem(DeviceSummary deviceSummaryItem) {
    if (this.deviceSummary == null) {
      this.deviceSummary = new ArrayList<>();
    }
    this.deviceSummary.add(deviceSummaryItem);
    return this;
  }

   /**
   * Device Summary of the each device within the product group
   * @return deviceSummary
  **/
  @ApiModelProperty(value = "Device Summary of the each device within the product group")

  @Valid
/**
 * 
 * @return
 */
  public List<DeviceSummary> getDeviceSummary() {
    return deviceSummary;
  }
/**
 * 
 * @param deviceSummary
 */
  public void setDeviceSummary(List<DeviceSummary> deviceSummary) {
    this.deviceSummary = deviceSummary;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceTile deviceTile = (DeviceTile) o;
    return Objects.equals(this.deviceId, deviceTile.deviceId) &&
        Objects.equals(this.groupName, deviceTile.groupName) &&
        Objects.equals(this.groupType, deviceTile.groupType) &&
        Objects.equals(this.rating, deviceTile.rating) &&
        Objects.equals(this.reviewCount, deviceTile.reviewCount) &&
        Objects.equals(this.deviceSummary, deviceTile.deviceSummary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, groupName, groupType, rating, reviewCount, deviceSummary);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceTile {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    groupType: ").append(toIndentedString(groupType)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    reviewCount: ").append(toIndentedString(reviewCount)).append("\n");
    sb.append("    deviceSummary: ").append(toIndentedString(deviceSummary)).append("\n");
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

