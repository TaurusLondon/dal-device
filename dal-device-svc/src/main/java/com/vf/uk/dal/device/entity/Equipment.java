package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * Equipment
 */

public class Equipment   {
  private String make = null;

  private String model = null;

  public Equipment make(String make) {
    this.make = make;
    return this;
  }

   /**
   * Make of the device
   * @return make
  **/
  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public Equipment model(String model) {
    this.model = model;
    return this;
  }

   /**
   * Model of the device
   * @return model
  **/
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Equipment equipment = (Equipment) o;
    return Objects.equals(this.make, equipment.make) &&
        Objects.equals(this.model, equipment.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(make, model);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Equipment {\n");
    
    sb.append("    make: ").append(toIndentedString(make)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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

