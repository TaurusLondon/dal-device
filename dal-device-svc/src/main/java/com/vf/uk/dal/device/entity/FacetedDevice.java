package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
 * FacetedDevice
 */
@JsonPropertyOrder({ "facet", "device", "noOfRecordsFound", "message" })
public class FacetedDevice   {

  private List<NewFacet> facet = new ArrayList<>();

  private List<Device> device = new ArrayList<>();

  private long noOfRecordsFound;

  private String message;
  

   /**
   * Get facet
   * @return facet
  **/


  public FacetedDevice newFacet(List<NewFacet> newFacet) {
    this.facet = newFacet;
    return this;
  }

  public FacetedDevice addNewFacetItem(NewFacet newFacetItem) {
    this.facet.add(newFacetItem);
    return this;
  }

   /**
   * Get newFacet
   * @return newFacet
  **/
  @JsonProperty(value="facet")
  public List<NewFacet> getNewFacet() {
    return facet;
  }

  public void setNewFacet(List<NewFacet> newFacet) {
    this.facet = newFacet;
  }

  public FacetedDevice device(List<Device> device) {
    this.device = device;
    return this;
  }

  public FacetedDevice addDeviceItem(Device deviceItem) {
    this.device.add(deviceItem);
    return this;
  }

   /**
   * Get device
   * @return device
  **/
  public List<Device> getDevice() {
    return device;
  }

  public void setDevice(List<Device> device) {
    this.device = device;
  }

  public FacetedDevice noOfRecordsFound(long noOfRecordsFound) {
    this.noOfRecordsFound = noOfRecordsFound;
    return this;
  }

   /**
   * Number of Records found
   * @return noOfRecordsFound
  **/
  public long getNoOfRecordsFound() {
    return noOfRecordsFound;
  }

  public void setNoOfRecordsFound(long noOfRecordsFound) {
    this.noOfRecordsFound = noOfRecordsFound;
  }
  
  public String getMessage() {
	return message;
  }
  
  public void setMessage(String message) {
	  this.message = message;
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

  
@Override
public String toString() {
	return "FacetedDevice [newFacet=" + facet + ", device=" + device + ", noOfRecordsFound=" + noOfRecordsFound
			+ ", message=" + message + "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((device == null) ? 0 : device.hashCode());
	result = prime * result + ((message == null) ? 0 : message.hashCode());
	result = prime * result + ((facet == null) ? 0 : facet.hashCode());
	result = prime * result + (int) (noOfRecordsFound ^ (noOfRecordsFound >>> 32));
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	FacetedDevice other = (FacetedDevice) obj;
	if (device == null) {
		if (other.device != null)
			return false;
	} else if (!device.equals(other.device))
		return false;
	if (message == null) {
		if (other.message != null)
			return false;
	} else if (!message.equals(other.message))
		return false;
	if (facet == null) {
		if (other.facet != null)
			return false;
	} else if (!facet.equals(other.facet))
		return false;
	if (noOfRecordsFound != other.noOfRecordsFound)
		return false;
	return true;
}
  
  
}

