package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * Error
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Error   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("referenceId")
  private String referenceId = null;
/**
 * 
 * @param code
 * @return
 */
  public Error code(String code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(value = "")

/**
 * 
 * @return
 */
  public String getCode() {
    return code;
  }
/**
 * 
 * @param code
 */
  public void setCode(String code) {
    this.code = code;
  }
/**
 * 
 * @param message
 * @return
 */
  public Error message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")

/**
 * 
 * @return
 */
  public String getMessage() {
    return message;
  }
/**
 * 
 * @param message
 */
  public void setMessage(String message) {
    this.message = message;
  }
/**
 * 
 * @param referenceId
 * @return
 */
  public Error referenceId(String referenceId) {
    this.referenceId = referenceId;
    return this;
  }

   /**
   * Get referenceId
   * @return referenceId
  **/
  @ApiModelProperty(value = "")

/**
 * 
 * @return
 */
  public String getReferenceId() {
    return referenceId;
  }
/**
 * 
 * @param referenceId
 */
  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.code, error.code) &&
        Objects.equals(this.message, error.message) &&
        Objects.equals(this.referenceId, error.referenceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, referenceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    referenceId: ").append(toIndentedString(referenceId)).append("\n");
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

