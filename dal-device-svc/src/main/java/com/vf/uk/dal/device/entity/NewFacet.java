package com.vf.uk.dal.device.entity;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
/**
 * NewFacet
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class NewFacet   {
  @JsonProperty("facetName")
  private String facetName = null;

  @JsonProperty("facetList")
  private List<FacetWithCount> facetList = null;

  public NewFacet facetName(String facetName) {
    this.facetName = facetName;
    return this;
  }

   /**
   * Facet Name like colour,os,capacity
   * @return facetName
  **/
  @ApiModelProperty(value = "Facet Name like colour,os,capacity")


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
    if (this.facetList == null) {
      this.facetList = new ArrayList<FacetWithCount>();
    }
    this.facetList.add(facetListItem);
    return this;
  }

   /**
   * List of facets with count
   * @return facetList
  **/
  @ApiModelProperty(value = "List of facets with count")

  @Valid

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

