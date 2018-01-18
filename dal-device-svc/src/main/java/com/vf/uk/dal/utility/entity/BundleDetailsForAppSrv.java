package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * BundleDetailsForAppSrv
 */

public class BundleDetailsForAppSrv   {
  private List<CoupleRelation> couplePlansList = new ArrayList<CoupleRelation>();

  private List<BundleHeader> standalonePlansList = new ArrayList<BundleHeader>();
/**
 * 
 * @param couplePlansList
 * @return
 */
  public BundleDetailsForAppSrv couplePlansList(List<CoupleRelation> couplePlansList) {
    this.couplePlansList = couplePlansList;
    return this;
  }
/**
 * 
 * @param couplePlansListItem
 * @return
 */
  public BundleDetailsForAppSrv addCouplePlansListItem(CoupleRelation couplePlansListItem) {
    this.couplePlansList.add(couplePlansListItem);
    return this;
  }

   /**
   * Get couplePlansList
   * @return couplePlansList
  **/
  public List<CoupleRelation> getCouplePlansList() {
    return couplePlansList;
  }
/**
 * 
 * @param couplePlansList
 */
  public void setCouplePlansList(List<CoupleRelation> couplePlansList) {
    this.couplePlansList = couplePlansList;
  }
/**
 * 
 * @param standalonePlansList
 * @return
 */
  public BundleDetailsForAppSrv standalonePlansList(List<BundleHeader> standalonePlansList) {
    this.standalonePlansList = standalonePlansList;
    return this;
  }
/**
 * 
 * @param standalonePlansListItem
 * @return
 */
  public BundleDetailsForAppSrv addStandalonePlansListItem(BundleHeader standalonePlansListItem) {
    this.standalonePlansList.add(standalonePlansListItem);
    return this;
  }

   /**
   * Get standalonePlansList
   * @return standalonePlansList
  **/
  public List<BundleHeader> getStandalonePlansList() {
    return standalonePlansList;
  }
/**
 * 
 * @param standalonePlansList
 */
  public void setStandalonePlansList(List<BundleHeader> standalonePlansList) {
    this.standalonePlansList = standalonePlansList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleDetailsForAppSrv bundleDetailsForAppSrv = (BundleDetailsForAppSrv) o;
    return Objects.equals(this.couplePlansList, bundleDetailsForAppSrv.couplePlansList) &&
        Objects.equals(this.standalonePlansList, bundleDetailsForAppSrv.standalonePlansList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(couplePlansList, standalonePlansList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleDetailsForAppSrv {\n");
    
    sb.append("    couplePlansList: ").append(toIndentedString(couplePlansList)).append("\n");
    sb.append("    standalonePlansList: ").append(toIndentedString(standalonePlansList)).append("\n");
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

