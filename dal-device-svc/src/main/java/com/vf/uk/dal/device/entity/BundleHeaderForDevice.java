package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * BundleHeaderForDevice
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-27T13:35:59.151Z")

public class BundleHeaderForDevice   {
  @JsonProperty("skuId")
  private String skuId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("bundleName")
  private String bundleName = null;

  @JsonProperty("bundleDescription")
  private String bundleDescription = null;

  @JsonProperty("bundleClass")
  private String bundleClass = null;

  @JsonProperty("paymentType")
  private String paymentType = null;

  @JsonProperty("bundleType")
  private String bundleType = null;

  @JsonProperty("commitmentPeriod")
  private String commitmentPeriod = null;

  @JsonProperty("allowance")
  private List<BundleAllowance> allowance = new ArrayList<>();

  @JsonProperty("roamingAllowance")
  private List<BundleAllowance> roamingAllowance = new ArrayList<>();

  @JsonProperty("merchandisingMedia")
  private List<MediaLink> merchandisingMedia = new ArrayList<>();

  @JsonProperty("priceInfo")
  private PriceForBundleAndHardware priceInfo = null;

  public BundleHeaderForDevice skuId(String skuId) {
    this.skuId = skuId;
    return this;
  }

   /**
   * Unique bundle id as available from the product catalogue
   * @return skuId
  **/
  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public BundleHeaderForDevice name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the bundle as provided in the product catalogue
   * @return name
  **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BundleHeaderForDevice description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the bundle as provided in the product catalogue
   * @return description
  **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BundleHeaderForDevice bundleName(String bundleName) {
    this.bundleName = bundleName;
    return this;
  }

   /**
   * Name of the bundle as provided in the merchandising file
   * @return bundleName
  **/
  public String getBundleName() {
    return bundleName;
  }

  public void setBundleName(String bundleName) {
    this.bundleName = bundleName;
  }

  public BundleHeaderForDevice bundleDescription(String bundleDescription) {
    this.bundleDescription = bundleDescription;
    return this;
  }

   /**
   * Description of the bundle as provided in the merchandising file
   * @return bundleDescription
  **/
  public String getBundleDescription() {
    return bundleDescription;
  }

  public void setBundleDescription(String bundleDescription) {
    this.bundleDescription = bundleDescription;
  }

  public BundleHeaderForDevice bundleClass(String bundleClass) {
    this.bundleClass = bundleClass;
    return this;
  }

   /**
   * Catalogue product class identifies products - SIMO, HANDSET etc.
   * @return bundleClass
  **/
  public String getBundleClass() {
    return bundleClass;
  }

  public void setBundleClass(String bundleClass) {
    this.bundleClass = bundleClass;
  }

  public BundleHeaderForDevice paymentType(String paymentType) {
    this.paymentType = paymentType;
    return this;
  }

   /**
   * Payment type of the bundle. For example, \"postpaid\", \"prepaid\" etc.
   * @return paymentType
  **/
  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public BundleHeaderForDevice bundleType(String bundleType) {
    this.bundleType = bundleType;
    return this;
  }

   /**
   * Type of the bundle. For example, Standard, Red, RedPlus etc.
   * @return bundleType
  **/
  public String getBundleType() {
    return bundleType;
  }

  public void setBundleType(String bundleType) {
    this.bundleType = bundleType;
  }

  public BundleHeaderForDevice commitmentPeriod(String commitmentPeriod) {
    this.commitmentPeriod = commitmentPeriod;
    return this;
  }

   /**
   * Commitment period of the bundle
   * @return commitmentPeriod
  **/
  public String getCommitmentPeriod() {
    return commitmentPeriod;
  }

  public void setCommitmentPeriod(String commitmentPeriod) {
    this.commitmentPeriod = commitmentPeriod;
  }

  public BundleHeaderForDevice allowance(List<BundleAllowance> allowance) {
    this.allowance = allowance;
    return this;
  }

  public BundleHeaderForDevice addAllowanceItem(BundleAllowance allowanceItem) {
    this.allowance.add(allowanceItem);
    return this;
  }

   /**
   * Get allowance
   * @return allowance
  **/
  public List<BundleAllowance> getAllowance() {
    return allowance;
  }

  public void setAllowance(List<BundleAllowance> allowance) {
    this.allowance = allowance;
  }

  public BundleHeaderForDevice roamingAllowance(List<BundleAllowance> roamingAllowance) {
    this.roamingAllowance = roamingAllowance;
    return this;
  }

  public BundleHeaderForDevice addRoamingAllowanceItem(BundleAllowance roamingAllowanceItem) {
    this.roamingAllowance.add(roamingAllowanceItem);
    return this;
  }

   /**
   * Get roamingAllowance
   * @return roamingAllowance
  **/
  public List<BundleAllowance> getRoamingAllowance() {
    return roamingAllowance;
  }

  public void setRoamingAllowance(List<BundleAllowance> roamingAllowance) {
    this.roamingAllowance = roamingAllowance;
  }

  public BundleHeaderForDevice merchandisingMedia(List<MediaLink> merchandisingMedia) {
    this.merchandisingMedia = merchandisingMedia;
    return this;
  }

  public BundleHeaderForDevice addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
    this.merchandisingMedia.add(merchandisingMediaItem);
    return this;
  }

   /**
   * Get merchandisingMedia
   * @return merchandisingMedia
  **/
  public List<MediaLink> getMerchandisingMedia() {
    return merchandisingMedia;
  }

  public void setMerchandisingMedia(List<MediaLink> merchandisingMedia) {
    this.merchandisingMedia = merchandisingMedia;
  }

  public BundleHeaderForDevice priceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
    return this;
  }

   /**
   * Get priceInfo
   * @return priceInfo
  **/
  public PriceForBundleAndHardware getPriceInfo() {
    return priceInfo;
  }

  public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
    this.priceInfo = priceInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleHeaderForDevice bundleHeaderForDevice = (BundleHeaderForDevice) o;
    return Objects.equals(this.skuId, bundleHeaderForDevice.skuId) &&
        Objects.equals(this.name, bundleHeaderForDevice.name) &&
        Objects.equals(this.description, bundleHeaderForDevice.description) &&
        Objects.equals(this.bundleName, bundleHeaderForDevice.bundleName) &&
        Objects.equals(this.bundleDescription, bundleHeaderForDevice.bundleDescription) &&
        Objects.equals(this.bundleClass, bundleHeaderForDevice.bundleClass) &&
        Objects.equals(this.paymentType, bundleHeaderForDevice.paymentType) &&
        Objects.equals(this.bundleType, bundleHeaderForDevice.bundleType) &&
        Objects.equals(this.commitmentPeriod, bundleHeaderForDevice.commitmentPeriod) &&
        Objects.equals(this.allowance, bundleHeaderForDevice.allowance) &&
        Objects.equals(this.roamingAllowance, bundleHeaderForDevice.roamingAllowance) &&
        Objects.equals(this.merchandisingMedia, bundleHeaderForDevice.merchandisingMedia) &&
        Objects.equals(this.priceInfo, bundleHeaderForDevice.priceInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(skuId, name, description, bundleName, bundleDescription, bundleClass, paymentType, bundleType, commitmentPeriod, allowance, roamingAllowance, merchandisingMedia, priceInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleHeaderForDevice {\n");
    
    sb.append("    skuId: ").append(toIndentedString(skuId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    bundleName: ").append(toIndentedString(bundleName)).append("\n");
    sb.append("    bundleDescription: ").append(toIndentedString(bundleDescription)).append("\n");
    sb.append("    bundleClass: ").append(toIndentedString(bundleClass)).append("\n");
    sb.append("    paymentType: ").append(toIndentedString(paymentType)).append("\n");
    sb.append("    bundleType: ").append(toIndentedString(bundleType)).append("\n");
    sb.append("    commitmentPeriod: ").append(toIndentedString(commitmentPeriod)).append("\n");
    sb.append("    allowance: ").append(toIndentedString(allowance)).append("\n");
    sb.append("    roamingAllowance: ").append(toIndentedString(roamingAllowance)).append("\n");
    sb.append("    merchandisingMedia: ").append(toIndentedString(merchandisingMedia)).append("\n");
    sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
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

