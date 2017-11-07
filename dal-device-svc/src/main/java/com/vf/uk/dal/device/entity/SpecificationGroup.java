package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * SpecificationGroup
 */

public class SpecificationGroup   {
  private String groupName = null;

  private Integer priority = null;

  private Boolean comparable = null;

  private List<Specification> specifications = new ArrayList<Specification>();

  public SpecificationGroup groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

   /**
   * Name of the specification
   * @return groupName
  **/
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public SpecificationGroup priority(Integer priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Display Priority of the specification
   * @return priority
  **/
  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public SpecificationGroup comparable(Boolean comparable) {
    this.comparable = comparable;
    return this;
  }

   /**
   *  Identify if the feature is coparable
   * @return comparable
  **/
  public Boolean getComparable() {
    return comparable;
  }

  public void setComparable(Boolean comparable) {
    this.comparable = comparable;
  }

  public SpecificationGroup specifications(List<Specification> specifications) {
    this.specifications = specifications;
    return this;
  }

  public SpecificationGroup addSpecificationsItem(Specification specificationsItem) {
    this.specifications.add(specificationsItem);
    return this;
  }

   /**
   * Get specifications
   * @return specifications
  **/
  public List<Specification> getSpecifications() {
    return specifications;
  }

  public void setSpecifications(List<Specification> specifications) {
    this.specifications = specifications;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpecificationGroup specificationGroup = (SpecificationGroup) o;
    return Objects.equals(this.groupName, specificationGroup.groupName) &&
        Objects.equals(this.priority, specificationGroup.priority) &&
        Objects.equals(this.comparable, specificationGroup.comparable) &&
        Objects.equals(this.specifications, specificationGroup.specifications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, priority, comparable, specifications);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpecificationGroup {\n");
    
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    comparable: ").append(toIndentedString(comparable)).append("\n");
    sb.append("    specifications: ").append(toIndentedString(specifications)).append("\n");
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

