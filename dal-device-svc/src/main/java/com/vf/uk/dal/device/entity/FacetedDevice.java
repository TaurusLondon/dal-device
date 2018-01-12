package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;


import io.swagger.annotations.ApiModelProperty;
/**
 * FacetedDevice
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class FacetedDevice   {
  @JsonProperty("facet")
  private List<NewFacet> newFacet = null;

  @JsonProperty("device")
  private List<Device> device = null;

  @JsonProperty("noOfRecordsFound")
  private long noOfRecordsFound;

  @JsonProperty("message")
  private String message = null;
/**
 * 
 * @param newFacet
 * @return
 */
  public FacetedDevice newFacet(List<NewFacet> newFacet) {
    this.newFacet = newFacet;
    return this;
  }
/**
 * 
 * @param newFacetItem
 * @return
 */
  public FacetedDevice addNewFacetItem(NewFacet newFacetItem) {
    if (this.newFacet == null) {
      this.newFacet = new ArrayList<>();
    }
    this.newFacet.add(newFacetItem);
    return this;
  }

   /**
   * Get newFacet
   * @return newFacet
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public List<NewFacet> getNewFacet() {
    return newFacet;
  }
/**
 * 
 * @param newFacet
 */
  public void setNewFacet(List<NewFacet> newFacet) {
    this.newFacet = newFacet;
  }
/**
 * 
 * @param device
 * @return
 */
  public FacetedDevice device(List<Device> device) {
    this.device = device;
    return this;
  }
/**
 * 
 * @param deviceItem
 * @return
 */
  public FacetedDevice addDeviceItem(Device deviceItem) {
    if (this.device == null) {
      this.device = new ArrayList<>();
    }
    this.device.add(deviceItem);
    return this;
  }

   /**
   * Get device
   * @return device
  **/
  @ApiModelProperty(value = "")

  @Valid
/**
 * 
 * @return
 */
  public List<Device> getDevice() {
    return device;
  }
/**
 * 
 * @param device
 */
  public void setDevice(List<Device> device) {
    this.device = device;
  }
/**
 * 
 * @param noOfRecordsFound
 * @return
 */
  public FacetedDevice noOfRecordsFound(long noOfRecordsFound) {
    this.noOfRecordsFound = noOfRecordsFound;
    return this;
  }

   /**
   * Number of Records found
   * @return noOfRecordsFound
  **/
  @ApiModelProperty(value = "Number of Records found")

  @Valid
/**
 * 
 * @return
 */
  public long getNoOfRecordsFound() {
    return noOfRecordsFound;
  }
/**
 * 
 * @param noOfRecordsFound
 */
  public void setNoOfRecordsFound(long noOfRecordsFound) {
    this.noOfRecordsFound = noOfRecordsFound;
  }
/**
 * 
 * @param message
 * @return
 */
  public FacetedDevice message(String message) {
    this.message = message;
    return this;
  }

   /**
   * GRPL error message for Tealium to be checked
   * @return message
  **/
  @ApiModelProperty(value = "GRPL error message for Tealium to be checked")

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FacetedDevice facetedDevice = (FacetedDevice) o;
    return Objects.equals(this.newFacet, facetedDevice.newFacet) &&
        Objects.equals(this.device, facetedDevice.device) &&
        Objects.equals(this.noOfRecordsFound, facetedDevice.noOfRecordsFound) &&
        Objects.equals(this.message, facetedDevice.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(newFacet, device, noOfRecordsFound, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FacetedDevice {\n");
    
    sb.append("    newFacet: ").append(toIndentedString(newFacet)).append("\n");
    sb.append("    device: ").append(toIndentedString(device)).append("\n");
    sb.append("    noOfRecordsFound: ").append(toIndentedString(noOfRecordsFound)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

