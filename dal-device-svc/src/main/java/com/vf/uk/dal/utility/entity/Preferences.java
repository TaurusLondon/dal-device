package com.vf.uk.dal.utility.entity;

import java.util.Objects;

public class Preferences {
  private String name = null;

  private String dataTypeCode = null;

  private String value = null;

  public Preferences name(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Preferences dataTypeCode(String dataTypeCode) {
    this.dataTypeCode = dataTypeCode;
    return this;
  }

  public String getDataTypeCode() {
    return dataTypeCode;
  }

  public void setDataTypeCode(String dataTypeCode) {
    this.dataTypeCode = dataTypeCode;
  }

  public Preferences value(String value) {
    this.value = value;
    return this;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Preferences preferences = (Preferences) o;
    return Objects.equals(this.name, preferences.name) &&
        Objects.equals(this.dataTypeCode, preferences.dataTypeCode) &&
        Objects.equals(this.value, preferences.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dataTypeCode, value);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Preferences {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dataTypeCode: ").append(toIndentedString(dataTypeCode)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

