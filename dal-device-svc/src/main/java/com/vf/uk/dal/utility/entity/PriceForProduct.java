package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PriceForProduct
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-17T05:56:29.550Z")

public class PriceForProduct   {
  @JsonProperty("priceForExtras")
  private List<PriceForExtra> priceForExtras = null;

  @JsonProperty("priceForAccessoryes")
  private List<PriceForAccessory> priceForAccessoryes = null;
  /**
   * 
   * @param priceForExtras
   * @return
   */
  public PriceForProduct priceForExtras(List<PriceForExtra> priceForExtras) {
    this.priceForExtras = priceForExtras;
    return this;
  }
  /**
   * 
   * @param priceForExtrasItem
   * @return
   */
  public PriceForProduct addPriceForExtrasItem(PriceForExtra priceForExtrasItem) {
    if (this.priceForExtras == null) {
      this.priceForExtras = new ArrayList<PriceForExtra>();
    }
    this.priceForExtras.add(priceForExtrasItem);
    return this;
  }

   /**
   * Get priceForExtras
   * @return priceForExtras
  **/

  @Valid

  public List<PriceForExtra> getPriceForExtras() {
    return priceForExtras;
  }
  /**
   * 
   * @param priceForExtras
   */
  public void setPriceForExtras(List<PriceForExtra> priceForExtras) {
    this.priceForExtras = priceForExtras;
  }
  /**
   * 
   * @param priceForAccessoryes
   * @return
   */
  public PriceForProduct priceForAccessoryes(List<PriceForAccessory> priceForAccessoryes) {
    this.priceForAccessoryes = priceForAccessoryes;
    return this;
  }
  /**
   * 
   * @param priceForAccessoryesItem
   * @return
   */
  public PriceForProduct addPriceForAccessoryesItem(PriceForAccessory priceForAccessoryesItem) {
    if (this.priceForAccessoryes == null) {
      this.priceForAccessoryes = new ArrayList<PriceForAccessory>();
    }
    this.priceForAccessoryes.add(priceForAccessoryesItem);
    return this;
  }

   /**
   * Get priceForAccessoryes
   * @return priceForAccessoryes
  **/

  @Valid

  public List<PriceForAccessory> getPriceForAccessoryes() {
    return priceForAccessoryes;
  }
  /**
   * 
   * @param priceForAccessoryes
   */
  public void setPriceForAccessoryes(List<PriceForAccessory> priceForAccessoryes) {
    this.priceForAccessoryes = priceForAccessoryes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceForProduct priceForProduct = (PriceForProduct) o;
    return Objects.equals(this.priceForExtras, priceForProduct.priceForExtras) &&
        Objects.equals(this.priceForAccessoryes, priceForProduct.priceForAccessoryes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(priceForExtras, priceForAccessoryes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PriceForProduct {\n");
    
    sb.append("    priceForExtras: ").append(toIndentedString(priceForExtras)).append("\n");
    sb.append("    priceForAccessoryes: ").append(toIndentedString(priceForAccessoryes)).append("\n");
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

