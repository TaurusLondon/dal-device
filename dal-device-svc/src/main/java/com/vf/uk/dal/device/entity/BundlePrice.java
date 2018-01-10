package com.vf.uk.dal.device.entity;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * BundlePrice
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class BundlePrice   {
  @JsonProperty("bundleId")
  private String bundleId = null;

  @JsonProperty("monthlyPrice")
  private Price monthlyPrice = null;

  @JsonProperty("monthlyDiscountPrice")
  private Price monthlyDiscountPrice = null;

  @JsonProperty("merchandisingPromotions")
  private MerchandisingPromotion merchandisingPromotions = null;

  /**
   * 
   * @param bundleId
   * @return
   */
  public BundlePrice bundleId(String bundleId) {
    this.bundleId = bundleId;
    return this;
  }

   /**
   * Bundle id of the price to be calculated
   * @return bundleId
  **/
  @ApiModelProperty(value = "Bundle id of the price to be calculated")

  /**
	 * 
	 * @return
	 */
  public String getBundleId() {
    return bundleId;
  }
  /**
	 * 
	 * @return
	 */
  public void setBundleId(String bundleId) {
    this.bundleId = bundleId;
  }
  /**
	 * 
	 * @return
	 */
  public BundlePrice monthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
    return this;
  }

   /**
   * Get monthlyPrice
   * @return monthlyPrice
  **/
  @ApiModelProperty(value = "")

  @Valid
  /**
	 * 
	 * @return
	 */
  public Price getMonthlyPrice() {
    return monthlyPrice;
  }
  /**
	 * 
	 * @return
	 */
  public void setMonthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
  }
  /**
	 * 
	 * @return
	 */
  public BundlePrice monthlyDiscountPrice(Price monthlyDiscountPrice) {
    this.monthlyDiscountPrice = monthlyDiscountPrice;
    return this;
  }

   /**
   * Get monthlyDiscountPrice
   * @return monthlyDiscountPrice
  **/
  @ApiModelProperty(value = "")

  @Valid
  /**
	 * 
	 * @return
	 */
  public Price getMonthlyDiscountPrice() {
    return monthlyDiscountPrice;
  }
  /**
	 * 
	 * @return
	 */
  public void setMonthlyDiscountPrice(Price monthlyDiscountPrice) {
    this.monthlyDiscountPrice = monthlyDiscountPrice;
  }
  /**
	 * 
	 * @return
	 */
  public BundlePrice merchandisingPromotions(MerchandisingPromotion merchandisingPromotions) {
    this.merchandisingPromotions = merchandisingPromotions;
    return this;
  }

   /**
   * Get merchandisingPromotions
   * @return merchandisingPromotions
  **/
  @ApiModelProperty(value = "")

  @Valid
  /**
	 * 
	 * @return
	 */
  public MerchandisingPromotion getMerchandisingPromotions() {
    return merchandisingPromotions;
  }
  /**
	 * 
	 * @return
	 */
  public void setMerchandisingPromotions(MerchandisingPromotion merchandisingPromotions) {
    this.merchandisingPromotions = merchandisingPromotions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundlePrice bundlePrice = (BundlePrice) o;
    return Objects.equals(this.bundleId, bundlePrice.bundleId) &&
        Objects.equals(this.monthlyPrice, bundlePrice.monthlyPrice) &&
        Objects.equals(this.monthlyDiscountPrice, bundlePrice.monthlyDiscountPrice) &&
        Objects.equals(this.merchandisingPromotions, bundlePrice.merchandisingPromotions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bundleId, monthlyPrice, monthlyDiscountPrice, merchandisingPromotions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundlePrice {\n");
    
    sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
    sb.append("    monthlyPrice: ").append(toIndentedString(monthlyPrice)).append("\n");
    sb.append("    monthlyDiscountPrice: ").append(toIndentedString(monthlyDiscountPrice)).append("\n");
    sb.append("    merchandisingPromotions: ").append(toIndentedString(merchandisingPromotions)).append("\n");
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

