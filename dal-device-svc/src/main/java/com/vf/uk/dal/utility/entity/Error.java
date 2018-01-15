

package com.vf.uk.dal.utility.entity;

import java.util.Objects;
/**
 * 
 * Error
 *
 */
public class Error {
  private String code = null;

  private String message = null;

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

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

