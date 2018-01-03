package com.vf.uk.dal.device.entity;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
/**
 * HardwarePrice
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class HardwarePrice   {
  @JsonProperty("hardwareId")
  private String hardwareId = null;

  @JsonProperty("oneOffPrice")
  private Price oneOffPrice = null;

  @JsonProperty("oneOffDiscountPrice")
  private Price oneOffDiscountPrice = null;

  @JsonProperty("merchandisingPromotions")
  private MerchandisingPromotion merchandisingPromotions = null;

  public HardwarePrice hardwareId(String hardwareId) {
    this.hardwareId = hardwareId;
    return this;
  }

   /**
   * Hardware id of the price to be calculated
   * @return hardwareId
  **/
  @ApiModelProperty(value = "Hardware id of the price to be calculated")


  public String getHardwareId() {
    return hardwareId;
  }

  public void setHardwareId(String hardwareId) {
    this.hardwareId = hardwareId;
  }

  public HardwarePrice oneOffPrice(Price oneOffPrice) {
    this.oneOffPrice = oneOffPrice;
    return this;
  }

   /**
   * Get oneOffPrice
   * @return oneOffPrice
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Price getOneOffPrice() {
    return oneOffPrice;
  }

  public void setOneOffPrice(Price oneOffPrice) {
    this.oneOffPrice = oneOffPrice;
  }

  public HardwarePrice oneOffDiscountPrice(Price oneOffDiscountPrice) {
    this.oneOffDiscountPrice = oneOffDiscountPrice;
    return this;
  }

   /**
   * Get oneOffDiscountPrice
   * @return oneOffDiscountPrice
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Price getOneOffDiscountPrice() {
    return oneOffDiscountPrice;
  }

  public void setOneOffDiscountPrice(Price oneOffDiscountPrice) {
    this.oneOffDiscountPrice = oneOffDiscountPrice;
  }

  public HardwarePrice merchandisingPromotions(MerchandisingPromotion merchandisingPromotions) {
    this.merchandisingPromotions = merchandisingPromotions;
    return this;
  }

   /**
   * Get merchandisingPromotions
   * @return merchandisingPromotions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public MerchandisingPromotion getMerchandisingPromotions() {
    return merchandisingPromotions;
  }

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
    HardwarePrice hardwarePrice = (HardwarePrice) o;
    return Objects.equals(this.hardwareId, hardwarePrice.hardwareId) &&
        Objects.equals(this.oneOffPrice, hardwarePrice.oneOffPrice) &&
        Objects.equals(this.oneOffDiscountPrice, hardwarePrice.oneOffDiscountPrice) &&
        Objects.equals(this.merchandisingPromotions, hardwarePrice.merchandisingPromotions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hardwareId, oneOffPrice, oneOffDiscountPrice, merchandisingPromotions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HardwarePrice {\n");
    
    sb.append("    hardwareId: ").append(toIndentedString(hardwareId)).append("\n");
    sb.append("    oneOffPrice: ").append(toIndentedString(oneOffPrice)).append("\n");
    sb.append("    oneOffDiscountPrice: ").append(toIndentedString(oneOffDiscountPrice)).append("\n");
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

