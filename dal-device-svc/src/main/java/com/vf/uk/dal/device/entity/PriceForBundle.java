package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * PriceForBundle
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-23T08:27:04.967Z")

public class PriceForBundle   {
  @JsonProperty("bundlePrice")
  private BundlePrice bundlePrice = null;

  @JsonProperty("monthlyPrice")
  private Price monthlyPrice = null;

  @JsonProperty("monthlyDiscountPrice")
  private Price monthlyDiscountPrice = null;

  @JsonProperty("stepPrices")
  private List<StepPricingInfo> stepPrices = new ArrayList<>();

  public PriceForBundle bundlePrice(BundlePrice bundlePrice) {
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

  public void setBundlePrice(BundlePrice bundlePrice) {
    this.bundlePrice = bundlePrice;
  }

  public PriceForBundle monthlyPrice(Price monthlyPrice) {
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

  public void setMonthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
  }

  public PriceForBundle monthlyDiscountPrice(Price monthlyDiscountPrice) {
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

  public void setMonthlyDiscountPrice(Price monthlyDiscountPrice) {
    this.monthlyDiscountPrice = monthlyDiscountPrice;
  }

  public PriceForBundle stepPrices(List<StepPricingInfo> stepPrices) {
    this.stepPrices = stepPrices;
    return this;
  }

  public PriceForBundle addStepPricesItem(StepPricingInfo stepPricesItem) {
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
    PriceForBundle priceForBundle = (PriceForBundle) o;
    return Objects.equals(this.bundlePrice, priceForBundle.bundlePrice) &&
        Objects.equals(this.monthlyPrice, priceForBundle.monthlyPrice) &&
        Objects.equals(this.monthlyDiscountPrice, priceForBundle.monthlyDiscountPrice) &&
        Objects.equals(this.stepPrices, priceForBundle.stepPrices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bundlePrice, monthlyPrice, monthlyDiscountPrice, stepPrices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PriceForBundle {\n");
    
    sb.append("    bundlePrice: ").append(toIndentedString(bundlePrice)).append("\n");
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

