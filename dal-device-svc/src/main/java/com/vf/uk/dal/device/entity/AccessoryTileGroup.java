package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessoryTileGroup {

  @JsonProperty("groupName")
  private String groupName = null;

  @JsonProperty("accessories")
  private List<Accessory> accessories = null;

  public AccessoryTileGroup groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public AccessoryTileGroup accessories(List<Accessory> accessories) {
    this.accessories = accessories;
    return this;
  }

  public AccessoryTileGroup addAccessoriesItem(Accessory accessoriesItem) {
    if (this.accessories == null) {
      this.accessories = new ArrayList<Accessory>();
    }
    this.accessories.add(accessoriesItem);
    return this;
  }

  public List<Accessory> getAccessories() {
    return accessories;
  }

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

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

