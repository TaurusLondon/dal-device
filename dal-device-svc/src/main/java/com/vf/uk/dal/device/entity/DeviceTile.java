package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * DeviceTile
 */

public class DeviceTile   {
  private String deviceId = null;

  private String groupName = null;

  private String groupType = null;

  private String rating = null;

  private String reviewCount = null;

  private List<DeviceSummary> deviceSummary = new ArrayList<DeviceSummary>();

  public DeviceTile deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

   /**
   * Device of the Lead Member within the group
   * @return deviceId
  **/
  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public DeviceTile groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

   /**
   * Group Name of the Device
   * @return groupName
  **/
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public DeviceTile groupType(String groupType) {
    this.groupType = groupType;
    return this;
  }

   /**
   * Group Type this devide belongs to, like Handset
   * @return groupType
  **/
  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }

  public DeviceTile rating(String rating) {
    this.rating = rating;
    return this;
  }

   /**
   * Rating for the device Id
   * @return rating
  **/
  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public DeviceTile reviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
    return this;
  }

   /**
   * Rating for the device Id
   * @return reviewCount
  **/
  public String getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
  }

  public DeviceTile deviceSummary(List<DeviceSummary> deviceSummary) {
    this.deviceSummary = deviceSummary;
    return this;
  }

  public DeviceTile addDeviceSummaryItem(DeviceSummary deviceSummaryItem) {
    this.deviceSummary.add(deviceSummaryItem);
    return this;
  }

   /**
   * Device Summary of the each device within the product group
   * @return deviceSummary
  **/
  public List<DeviceSummary> getDeviceSummary() {
    return deviceSummary;
  }

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

