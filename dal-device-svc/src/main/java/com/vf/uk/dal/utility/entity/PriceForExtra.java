package com.vf.uk.dal.utility.entity;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PriceForExtra
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-17T05:56:29.550Z")

public class PriceForExtra   {
  @JsonProperty("extraPrice")
  private ExtraPrice extraPrice = null;
  /**
   * 
   * @param extraPrice
   * @return
   */
  public PriceForExtra extraPrice(ExtraPrice extraPrice) {
    this.extraPrice = extraPrice;
    return this;
  }

   /**
   * Get extraPrice
   * @return extraPrice
  **/

  @Valid

  public ExtraPrice getExtraPrice() {
    return extraPrice;
  }
  /**
   * 
   * @param extraPrice
   */
  public void setExtraPrice(ExtraPrice extraPrice) {
    this.extraPrice = extraPrice;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceForExtra priceForExtra = (PriceForExtra) o;
    return Objects.equals(this.extraPrice, priceForExtra.extraPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(extraPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PriceForExtra {\n");
    
    sb.append("    extraPrice: ").append(toIndentedString(extraPrice)).append("\n");
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

