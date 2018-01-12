package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * PriceForBundleAndHardware
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-14T09:23:00.845Z")

public class PriceForBundleAndHardware   {
  @JsonProperty("bundlePrice")
  private BundlePrice bundlePrice = null;

  @JsonProperty("hardwarePrice")
  private HardwarePrice hardwarePrice = null;

  @JsonProperty("oneOffPrice")
  private Price oneOffPrice = null;

  @JsonProperty("oneOffDiscountPrice")
  private Price oneOffDiscountPrice = null;

  @JsonProperty("monthlyPrice")
  private Price monthlyPrice = null;

  @JsonProperty("monthlyDiscountPrice")
  private Price monthlyDiscountPrice = null;

  @JsonProperty("stepPrices")
  private List<StepPricingInfo> stepPrices = new ArrayList<>();
  /**
   * 
   * @param bundlePrice
   * @return
   */
  public PriceForBundleAndHardware bundlePrice(BundlePrice bundlePrice) {
    this.bundlePrice = bundlePrice;
    return this;
  }

   /**
   * Get bundlePrice
   * @return bundlePrice
  **/

  public BundlePrice getBundlePrice() {
    return bundlePrice;
  }
  /**
   * 
   * @param bundlePrice
   */
  public void setBundlePrice(BundlePrice bundlePrice) {
    this.bundlePrice = bundlePrice;
  }
  /**
   * 
   * @param hardwarePrice
   * @return
   */
  public PriceForBundleAndHardware hardwarePrice(HardwarePrice hardwarePrice) {
    this.hardwarePrice = hardwarePrice;
    return this;
  }

   /**
   * Get hardwarePrice
   * @return hardwarePrice
  **/

  public HardwarePrice getHardwarePrice() {
    return hardwarePrice;
  }
  /**
   * 
   * @param hardwarePrice
   */
  public void setHardwarePrice(HardwarePrice hardwarePrice) {
    this.hardwarePrice = hardwarePrice;
  }
  /**
   * 
   * @param oneOffPrice
   * @return
   */
  public PriceForBundleAndHardware oneOffPrice(Price oneOffPrice) {
    this.oneOffPrice = oneOffPrice;
    return this;
  }

   /**
   * Get oneOffPrice
   * @return oneOffPrice
  **/

  public Price getOneOffPrice() {
    return oneOffPrice;
  }
  /**
   * /**
   * 
   * @param oneOffPrice
   */
  public void setOneOffPrice(Price oneOffPrice) {
    this.oneOffPrice = oneOffPrice;
  }
  /**
   * 
   * @param oneOffDiscountPrice
   * @return
   */
  public PriceForBundleAndHardware oneOffDiscountPrice(Price oneOffDiscountPrice) {
    this.oneOffDiscountPrice = oneOffDiscountPrice;
    return this;
  }

   /**
   * Get oneOffDiscountPrice
   * @return oneOffDiscountPrice
  **/

  public Price getOneOffDiscountPrice() {
    return oneOffDiscountPrice;
  }
  /**
   * 
   * @param oneOffDiscountPrice
   */
  public void setOneOffDiscountPrice(Price oneOffDiscountPrice) {
    this.oneOffDiscountPrice = oneOffDiscountPrice;
  }
  /**
   * 
   * @param monthlyPrice
   * @return
   */
  public PriceForBundleAndHardware monthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
    return this;
  }

   /**
   * Get monthlyPrice
   * @return monthlyPrice
  **/

  public Price getMonthlyPrice() {
    return monthlyPrice;
  }
  /**
   * 
   * @param monthlyPrice
   */
  public void setMonthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
  }
  /**
   * 
   * @param monthlyDiscountPrice
   * @return
   */
  public PriceForBundleAndHardware monthlyDiscountPrice(Price monthlyDiscountPrice) {
    this.monthlyDiscountPrice = monthlyDiscountPrice;
    return this;
  }

   /**
   * Get monthlyDiscountPrice
   * @return monthlyDiscountPrice
  **/

  public Price getMonthlyDiscountPrice() {
    return monthlyDiscountPrice;
  }
  /**
   * 
   * @param monthlyDiscountPrice
   */
  public void setMonthlyDiscountPrice(Price monthlyDiscountPrice) {
    this.monthlyDiscountPrice = monthlyDiscountPrice;
  }
  /**
   * 
   * @param stepPrices
   * @return
   */
  public PriceForBundleAndHardware stepPrices(List<StepPricingInfo> stepPrices) {
    this.stepPrices = stepPrices;
    return this;
  }
  /**
   * 
   * @param stepPricesItem
   * @return
   */
  public PriceForBundleAndHardware addStepPricesItem(StepPricingInfo stepPricesItem) {
    this.stepPrices.add(stepPricesItem);
    return this;
  }

   /**
   * Get stepPrices
   * @return stepPrices
  **/

  public List<StepPricingInfo> getStepPrices() {
    return stepPrices;
  }
  /**
   * 
   * @param stepPrices
   */
  public void setStepPrices(List<StepPricingInfo> stepPrices) {
    this.stepPrices = stepPrices;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceForBundleAndHardware priceForBundleAndHardware = (PriceForBundleAndHardware) o;
    return Objects.equals(this.bundlePrice, priceForBundleAndHardware.bundlePrice) &&
        Objects.equals(this.hardwarePrice, priceForBundleAndHardware.hardwarePrice) &&
        Objects.equals(this.oneOffPrice, priceForBundleAndHardware.oneOffPrice) &&
        Objects.equals(this.oneOffDiscountPrice, priceForBundleAndHardware.oneOffDiscountPrice) &&
        Objects.equals(this.monthlyPrice, priceForBundleAndHardware.monthlyPrice) &&
        Objects.equals(this.monthlyDiscountPrice, priceForBundleAndHardware.monthlyDiscountPrice) &&
        Objects.equals(this.stepPrices, priceForBundleAndHardware.stepPrices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bundlePrice, hardwarePrice, oneOffPrice, oneOffDiscountPrice, monthlyPrice, monthlyDiscountPrice, stepPrices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PriceForBundleAndHardware {\n");
    
    sb.append("    bundlePrice: ").append(toIndentedString(bundlePrice)).append("\n");
    sb.append("    hardwarePrice: ").append(toIndentedString(hardwarePrice)).append("\n");
    sb.append("    oneOffPrice: ").append(toIndentedString(oneOffPrice)).append("\n");
    sb.append("    oneOffDiscountPrice: ").append(toIndentedString(oneOffDiscountPrice)).append("\n");
    sb.append("    monthlyPrice: ").append(toIndentedString(monthlyPrice)).append("\n");
    sb.append("    monthlyDiscountPrice: ").append(toIndentedString(monthlyDiscountPrice)).append("\n");
    sb.append("    stepPrices: ").append(toIndentedString(stepPrices)).append("\n");
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

