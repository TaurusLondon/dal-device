package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * MediaLink
 */

public class MediaLink   {
  private String id = null;

  private String value = null;

  private String type = null;

  private Integer priority = null;
	
  public Integer getPriority() {
		return priority;
	}
  public void setPriority(Integer priority) {
		this.priority = priority;
	}
  
  public MediaLink id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique id given for this link which provide a reference for UI to place this on the screen
   * @return id
  **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public MediaLink value(String value) {
    this.value = value;
    return this;
  }

   /**
   * URL Link from the content site for the media
   * @return value
  **/
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public MediaLink type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Will provide UI information on the type of the link (like URL)
   * @return type
  **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaLink mediaLink = (MediaLink) o;
    return Objects.equals(this.id, mediaLink.id) &&
        Objects.equals(this.value, mediaLink.value) &&
        Objects.equals(this.type, mediaLink.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MediaLink {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

