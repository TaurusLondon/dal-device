package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * RequestForBundleAndHardware
 */
public class RequestForBundleAndHardware {
  private String offerCode = null;
  
  private String packageType=null;

  private List<BundleAndHardwareTuple> bundleAndHardwareList = null;

  /**
   * 
   * @param offerCode
   * @return
   */
  public RequestForBundleAndHardware offerCode(String offerCode) {
    this.offerCode = offerCode;
    return this;
  }

   /**
   * offercode will be proived as part of journey
   * @return offerCode
  **/
  public String getOfferCode() {
    return offerCode;
  }

  /**
   * 
   * @param offerCode
   */
  public void setOfferCode(String offerCode) {
    this.offerCode = offerCode;
  }

  /**
   * 
   * @return
   */
  public String getPackageType() {
	return packageType;
}
/**
 * 
 * @param packageType
 */
public void setPackageType(String packageType) {
	this.packageType = packageType;
}
/**
 * 
 * @param bundleAndHardwareList
 * @return
 */
public RequestForBundleAndHardware bundleAndHardwareList(List<BundleAndHardwareTuple> bundleAndHardwareList) {
    this.bundleAndHardwareList = bundleAndHardwareList;
    return this;
  }
/**
 * 
 * @param bundleAndHardwareListItem
 * @return
 */
  public RequestForBundleAndHardware addBundleAndHardwareListItem(BundleAndHardwareTuple bundleAndHardwareListItem) {
    if (this.bundleAndHardwareList == null) {
      this.bundleAndHardwareList = new ArrayList<>();
    }
    this.bundleAndHardwareList.add(bundleAndHardwareListItem);
    return this;
  }

   /**
   * Get bundleAndHardwareList
   * @return bundleAndHardwareList
  **/
  public List<BundleAndHardwareTuple> getBundleAndHardwareList() {
    return bundleAndHardwareList;
  }
/**
 * 
 * @param bundleAndHardwareList
 */
  public void setBundleAndHardwareList(List<BundleAndHardwareTuple> bundleAndHardwareList) {
    this.bundleAndHardwareList = bundleAndHardwareList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestForBundleAndHardware requestForBundleAndHardware = (RequestForBundleAndHardware) o;
    return Objects.equals(this.offerCode, requestForBundleAndHardware.offerCode) &&
        Objects.equals(this.bundleAndHardwareList, requestForBundleAndHardware.bundleAndHardwareList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offerCode, bundleAndHardwareList);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestForBundleAndHardware {\n");
    
    sb.append("    offerCode: ").append(toIndentedString(offerCode)).append("\n");
    sb.append("    bundleAndHardwareList: ").append(toIndentedString(bundleAndHardwareList)).append("\n");
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

