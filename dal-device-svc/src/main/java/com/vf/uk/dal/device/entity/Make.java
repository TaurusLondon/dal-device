package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * Make
 */

public class Make   {
  private String name = null;

  private long count;

  public Make name(String name) {
    this.name = name;
    return this;
  }

   /**
   * equipment name
   * @return name
  **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Make count(long count) {
    this.count = count;
    return this;
  }

   /**
   * euipment count
   * @return count
  **/
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
    Make make = (Make) o;
    return Objects.equals(this.name, make.name) &&
        Objects.equals(this.count, make.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Make {\n");
    
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

