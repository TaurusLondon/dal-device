package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
/**
 * 
 * AccessoryTileGroup
 *
 */
public class AccessoryTileGroup   {
  @JsonProperty("groupName")
  private String groupName = null;

  @JsonProperty("accessories")
  private List<Accessory> accessories = null;

  /**
   * 
   * @param groupName
   * @return
   */
  public AccessoryTileGroup groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

   /**
   * Accessory product group name like \" Apple iPhone 6 cases\"
   * @return groupName
  **/
  @ApiModelProperty(value = "Accessory product group name like \" Apple iPhone 6 cases\"")

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
   * @param accessories
   * @return
   */
  public AccessoryTileGroup accessories(List<Accessory> accessories) {
    this.accessories = accessories;
    return this;
  }

  /**
   * 
   * @param accessoriesItem
   * @return
   */
  public AccessoryTileGroup addAccessoriesItem(Accessory accessoriesItem) {
    if (this.accessories == null) {
      this.accessories = new ArrayList<Accessory>();
    }
    this.accessories.add(accessoriesItem);
    return this;
  }

   /**
   * Get accessories
   * @return accessories
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Accessory> getAccessories() {
    return accessories;
  }

  /**
   * 
   * @param accessories
   */
  public void setAccessories(List<Accessory> accessories) {
    this.accessories = accessories;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessoryTileGroup accessoryTileGroup = (AccessoryTileGroup) o;
    return Objects.equals(this.groupName, accessoryTileGroup.groupName) &&
        Objects.equals(this.accessories, accessoryTileGroup.accessories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, accessories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessoryTileGroup {\n");
    
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    accessories: ").append(toIndentedString(accessories)).append("\n");
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

