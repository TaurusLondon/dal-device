package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * Member
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Member   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("priority")
  private String priority = null;

  public Member id(String id) {
    this.id = id;
    return this;
  }

   /**
   * identifier (product sku id)
   * @return id
  **/
  @ApiModelProperty(value = "identifier (product sku id)")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Member priority(String priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Priority defined for the product group in MEF
   * @return priority
  **/
  @ApiModelProperty(value = "Priority defined for the product group in MEF")


  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Member member = (Member) o;
    return Objects.equals(this.id, member.id) &&
        Objects.equals(this.priority, member.priority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, priority);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Member {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
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

