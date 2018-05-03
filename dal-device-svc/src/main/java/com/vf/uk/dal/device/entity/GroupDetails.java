package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * GroupDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-05-02T12:09:49.110Z")

public class GroupDetails   {
  @JsonProperty("groupName")
  private String groupName = null;

  @JsonProperty("groupId")
  private String groupId = null;

  @JsonProperty("size")
  private List<String> size = null;

  @JsonProperty("color")
  private List<String> color = null;

  public GroupDetails groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  /**
   * Get groupName
   * @return groupName
  **/
  @ApiModelProperty(value = "")


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public GroupDetails groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  /**
   * Get groupId
   * @return groupId
  **/
  @ApiModelProperty(value = "")


  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public GroupDetails size(List<String> size) {
    this.size = size;
    return this;
  }

  public GroupDetails addSizeItem(String sizeItem) {
    if (this.size == null) {
      this.size = new ArrayList<>();
    }
    this.size.add(sizeItem);
    return this;
  }

  /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(value = "")


  public List<String> getSize() {
    return size;
  }

  public void setSize(List<String> size) {
    this.size = size;
  }

  public GroupDetails color(List<String> color) {
    this.color = color;
    return this;
  }

  public GroupDetails addColorItem(String colorItem) {
    if (this.color == null) {
      this.color = new ArrayList<>();
    }
    this.color.add(colorItem);
    return this;
  }

  /**
   * Get color
   * @return color
  **/
  @ApiModelProperty(value = "")


  public List<String> getColor() {
    return color;
  }

  public void setColor(List<String> color) {
    this.color = color;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupDetails groupDetails = (GroupDetails) o;
    return Objects.equals(this.groupName, groupDetails.groupName) &&
        Objects.equals(this.groupId, groupDetails.groupId) &&
        Objects.equals(this.size, groupDetails.size) &&
        Objects.equals(this.color, groupDetails.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, groupId, size, color);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupDetails {\n");
    
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
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

