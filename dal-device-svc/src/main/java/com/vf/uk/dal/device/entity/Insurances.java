package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * Insurances
 */

public class Insurances   {
  private List<Insurance> insuranceList = new ArrayList<Insurance>();

  private String minCost = null;

  public Insurances insuranceList(List<Insurance> insuranceList) {
    this.insuranceList = insuranceList;
    return this;
  }

  public Insurances addInsuranceListItem(Insurance insuranceListItem) {
    this.insuranceList.add(insuranceListItem);
    return this;
  }

   /**
   * Get insuranceList
   * @return insuranceList
  **/
  public List<Insurance> getInsuranceList() {
    return insuranceList;
  }

  public void setInsuranceList(List<Insurance> insuranceList) {
    this.insuranceList = insuranceList;
  }

  public Insurances minCost(String minCost) {
    this.minCost = minCost;
    return this;
  }

   /**
   * Contains minimum cost of Insurance
   * @return minCost
  **/
  public String getMinCost() {
    return minCost;
  }

  public void setMinCost(String minCost) {
    this.minCost = minCost;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Insurances insurances = (Insurances) o;
    return Objects.equals(this.insuranceList, insurances.insuranceList) &&
        Objects.equals(this.minCost, insurances.minCost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(insuranceList, minCost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Insurances {\n");
    
    sb.append("    insuranceList: ").append(toIndentedString(insuranceList)).append("\n");
    sb.append("    minCost: ").append(toIndentedString(minCost)).append("\n");
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

