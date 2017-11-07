package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * NewFacet
 */

public class NewFacet   {
  private String facetName = null;

  private List<FacetWithCount> facetList = new ArrayList<FacetWithCount>();

  public NewFacet facetName(String facetName) {
    this.facetName = facetName;
    return this;
  }

   /**
   * Facet Name like colour,os,capacity
   * @return facetName
  **/
  public String getFacetName() {
    return facetName;
  }

  public void setFacetName(String facetName) {
    this.facetName = facetName;
  }

  public NewFacet facetList(List<FacetWithCount> facetList) {
    this.facetList = facetList;
    return this;
  }

  public NewFacet addFacetListItem(FacetWithCount facetListItem) {
    this.facetList.add(facetListItem);
    return this;
  }

   /**
   * List of facets with count
   * @return facetList
  **/
  public List<FacetWithCount> getFacetList() {
    return facetList;
  }

  public void setFacetList(List<FacetWithCount> facetList) {
    this.facetList = facetList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewFacet newFacet = (NewFacet) o;
    return Objects.equals(this.facetName, newFacet.facetName) &&
        Objects.equals(this.facetList, newFacet.facetList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facetName, facetList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewFacet {\n");
    
    sb.append("    facetName: ").append(toIndentedString(facetName)).append("\n");
    sb.append("    facetList: ").append(toIndentedString(facetList)).append("\n");
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

