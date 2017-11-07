package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * StepPricingInfo
 */

public class StepPricingInfo   {
  private String sequence = null;

  private Price oneOffPrice = null;

  private Price monthlyPrice = null;

  private Duration duration = null;

  private List<String> discountSkuIds = new ArrayList<String>();

  public StepPricingInfo sequence(String sequence) {
    this.sequence = sequence;
    return this;
  }

   /**
   * Sequence no of the line item
   * @return sequence
  **/
  public String getSequence() {
    return sequence;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public StepPricingInfo oneOffPrice(Price oneOffPrice) {
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

  public void setOneOffPrice(Price oneOffPrice) {
    this.oneOffPrice = oneOffPrice;
  }

  public StepPricingInfo monthlyPrice(Price monthlyPrice) {
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

  public StepPricingInfo duration(Duration duration) {
    this.duration = duration;
    return this;
  }

   /**
   * Get duration
   * @return duration
  **/
  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public StepPricingInfo discountSkuIds(List<String> discountSkuIds) {
    this.discountSkuIds = discountSkuIds;
    return this;
  }

  public StepPricingInfo addDiscountSkuIdsItem(String discountSkuIdsItem) {
    this.discountSkuIds.add(discountSkuIdsItem);
    return this;
  }

   /**
   * List of applicable discount sku Ids for this duration
   * @return discountSkuIds
  **/
  public List<String> getDiscountSkuIds() {
    return discountSkuIds;
  }

  public void setDiscountSkuIds(List<String> discountSkuIds) {
    this.discountSkuIds = discountSkuIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StepPricingInfo stepPricingInfo = (StepPricingInfo) o;
    return Objects.equals(this.sequence, stepPricingInfo.sequence) &&
        Objects.equals(this.oneOffPrice, stepPricingInfo.oneOffPrice) &&
        Objects.equals(this.monthlyPrice, stepPricingInfo.monthlyPrice) &&
        Objects.equals(this.duration, stepPricingInfo.duration) &&
        Objects.equals(this.discountSkuIds, stepPricingInfo.discountSkuIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sequence, oneOffPrice, monthlyPrice, duration, discountSkuIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StepPricingInfo {\n");
    
    sb.append("    sequence: ").append(toIndentedString(sequence)).append("\n");
    sb.append("    oneOffPrice: ").append(toIndentedString(oneOffPrice)).append("\n");
    sb.append("    monthlyPrice: ").append(toIndentedString(monthlyPrice)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    discountSkuIds: ").append(toIndentedString(discountSkuIds)).append("\n");
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

