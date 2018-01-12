package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * Facet
 */

public class Facet   {
  private String equipmentMake = null;

  private List<Make> makeList = new ArrayList<>();

  /**
   * 
   * @param equipmentMake
   * @return
   */
  public Facet equipmentMake(String equipmentMake) {
    this.equipmentMake = equipmentMake;
    return this;
  }

   /**
   * Equipment Make
   * @return equipmentMake
  **/
  public String getEquipmentMake() {
    return equipmentMake;
  }
/**
 * 
 * @param equipmentMake
 */
  public void setEquipmentMake(String equipmentMake) {
    this.equipmentMake = equipmentMake;
  }
/**
 * 
 * @param makeList
 * @return
 */
  public Facet makeList(List<Make> makeList) {
    this.makeList = makeList;
    return this;
  }
/**
 * 
 * @param makeListItem
 * @return
 */
  public Facet addMakeListItem(Make makeListItem) {
    this.makeList.add(makeListItem);
    return this;
  }

   /**
   * List of makes
   * @return makeList
  **/
  public List<Make> getMakeList() {
    return makeList;
  }
/**
 * 
 * @param makeList
 */
  public void setMakeList(List<Make> makeList) {
    this.makeList = makeList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Facet facet = (Facet) o;
    return Objects.equals(this.equipmentMake, facet.equipmentMake) &&
        Objects.equals(this.makeList, facet.makeList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(equipmentMake, makeList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Facet {\n");
    
    sb.append("    equipmentMake: ").append(toIndentedString(equipmentMake)).append("\n");
    sb.append("    makeList: ").append(toIndentedString(makeList)).append("\n");
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

