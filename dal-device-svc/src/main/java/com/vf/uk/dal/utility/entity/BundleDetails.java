package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * BundleDetails
 */

public class BundleDetails   {
  private List<BundleHeader> planList = new ArrayList<BundleHeader>();

  public BundleDetails planList(List<BundleHeader> planList) {
    this.planList = planList;
    return this;
  }

  public BundleDetails addPlanListItem(BundleHeader planListItem) {
    this.planList.add(planListItem);
    return this;
  }

   /**
   * Get planList
   * @return planList
  **/
  public List<BundleHeader> getPlanList() {
    return planList;
  }

  public void setPlanList(List<BundleHeader> planList) {
    this.planList = planList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleDetails bundleDetails = (BundleDetails) o;
    return Objects.equals(this.planList, bundleDetails.planList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(planList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleDetails {\n");
    
    sb.append("    planList: ").append(toIndentedString(planList)).append("\n");
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

