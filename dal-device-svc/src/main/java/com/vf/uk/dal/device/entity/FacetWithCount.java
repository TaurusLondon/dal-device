package com.vf.uk.dal.device.entity;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * FacetWithCount
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class FacetWithCount   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("count")
  private long count;

  public FacetWithCount name(String name) {
    this.name = name;
    return this;
  }

   /**
   * equipment name
   * @return name
  **/
  @ApiModelProperty(value = "equipment name")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FacetWithCount count(long count) {
    this.count = count;
    return this;
  }

   /**
   * euipment count
   * @return count
  **/
  @ApiModelProperty(value = "euipment count")

  @Valid

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FacetWithCount facetWithCount = (FacetWithCount) o;
    return Objects.equals(this.name, facetWithCount.name) &&
        Objects.equals(this.count, facetWithCount.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FacetWithCount {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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

