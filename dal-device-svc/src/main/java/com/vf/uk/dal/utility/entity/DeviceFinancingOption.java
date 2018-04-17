package com.vf.uk.dal.utility.entity;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * DeviceFinancingOption
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-17T05:45:22.340Z")

public class DeviceFinancingOption   {
  @JsonProperty("financeTerm")
  private String financeTerm = null;

  @JsonProperty("financeProvider")
  private String financeProvider = null;

  @JsonProperty("apr")
  private String apr = null;

  @JsonProperty("monthlyPrice")
  private Price monthlyPrice = null;

  @JsonProperty("totalPriceWithInterest")
  private Price totalPriceWithInterest = null;

  /*@ApiModelProperty(hidden=true)
  @JsonProperty("deviceFinancingId")
  private String deviceFinancingId = null;*/

  public DeviceFinancingOption financeTerm(String financeTerm) {
    this.financeTerm = financeTerm;
    return this;
  }

  /**
   * Duration of finance
   * @return financeTerm
  **/
  @ApiModelProperty(value = "Duration of finance")


  public String getFinanceTerm() {
    return financeTerm;
  }

  public void setFinanceTerm(String financeTerm) {
    this.financeTerm = financeTerm;
  }

  public DeviceFinancingOption financeProvider(String financeProvider) {
    this.financeProvider = financeProvider;
    return this;
  }

  /**
   * Name of the finance provider
   * @return financeProvider
  **/
  @ApiModelProperty(value = "Name of the finance provider")


  public String getFinanceProvider() {
    return financeProvider;
  }

  public void setFinanceProvider(String financeProvider) {
    this.financeProvider = financeProvider;
  }

  public DeviceFinancingOption apr(String apr) {
    this.apr = apr;
    return this;
  }

  /**
   * Annual interest rate
   * @return apr
  **/
  @ApiModelProperty(value = "Annual interest rate")


  public String getApr() {
    return apr;
  }

  public void setApr(String apr) {
    this.apr = apr;
  }

  public DeviceFinancingOption monthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
    return this;
  }

  /**
   * Get monthlyPrice
   * @return monthlyPrice
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Price getMonthlyPrice() {
    return monthlyPrice;
  }

  public void setMonthlyPrice(Price monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
  }

  public DeviceFinancingOption totalPriceWithInterest(Price totalPriceWithInterest) {
    this.totalPriceWithInterest = totalPriceWithInterest;
    return this;
  }

  /**
   * Get totalPriceWithInterest
   * @return totalPriceWithInterest
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Price getTotalPriceWithInterest() {
    return totalPriceWithInterest;
  }

  public void setTotalPriceWithInterest(Price totalPriceWithInterest) {
    this.totalPriceWithInterest = totalPriceWithInterest;
  }
 /* public DeviceFinancingOption deviceFinancingId(String deviceFinancingId) {
    this.deviceFinancingId = deviceFinancingId;
    return this;
  }*/

  /**
   * device financing Id
   * @return deviceFinancingId
  **/
  /*@ApiModelProperty(value = "device financing Id")

  public String getDeviceFinancingId() {
    return deviceFinancingId;
  }

  public void setDeviceFinancingId(String deviceFinancingId) {
    this.deviceFinancingId = deviceFinancingId;
  }*/


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceFinancingOption deviceFinancingOption = (DeviceFinancingOption) o;
    return Objects.equals(this.financeTerm, deviceFinancingOption.financeTerm) &&
        Objects.equals(this.financeProvider, deviceFinancingOption.financeProvider) &&
        Objects.equals(this.apr, deviceFinancingOption.apr) &&
        Objects.equals(this.monthlyPrice, deviceFinancingOption.monthlyPrice) &&
        Objects.equals(this.totalPriceWithInterest, deviceFinancingOption.totalPriceWithInterest);
    // && Objects.equals(this.deviceFinancingId, deviceFinancingOption.deviceFinancingId)
  }

  @Override
  public int hashCode() {
    return Objects.hash(financeTerm, financeProvider, apr, monthlyPrice, totalPriceWithInterest);
    //, deviceFinancingId
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceFinancingOption {\n");
    
    sb.append("    financeTerm: ").append(toIndentedString(financeTerm)).append("\n");
    sb.append("    financeProvider: ").append(toIndentedString(financeProvider)).append("\n");
    sb.append("    apr: ").append(toIndentedString(apr)).append("\n");
    sb.append("    monthlyPrice: ").append(toIndentedString(monthlyPrice)).append("\n");
    sb.append("    totalPriceWithInterest: ").append(toIndentedString(totalPriceWithInterest)).append("\n");
   // sb.append("    deviceFinancingId: ").append(toIndentedString(deviceFinancingId)).append("\n");
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

