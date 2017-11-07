package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * MerchandisingControl
 */

public class MerchandisingControl   {
  private Boolean isDisplayableECare = null;

  private Boolean isSellableECare = null;

  private Boolean isDisplayableAcq = null;

  private Boolean isSellableRet = null;

  private Boolean isDisplayableRet = null;

  private Boolean isSellableAcq = null;

  private Boolean isDisplayableSavedBasket = null;

  private Integer order = null;

  private Boolean preorderable = null;

  private String availableFrom = null;

  private Boolean backorderable = null;

  public MerchandisingControl isDisplayableECare(Boolean isDisplayableECare) {
    this.isDisplayableECare = isDisplayableECare;
    return this;
  }

   /**
   * IS the device Disellable in ECare
   * @return isDisplayableECare
  **/
  public Boolean getIsDisplayableECare() {
    return isDisplayableECare;
  }

  public void setIsDisplayableECare(Boolean isDisplayableECare) {
    this.isDisplayableECare = isDisplayableECare;
  }

  public MerchandisingControl isSellableECare(Boolean isSellableECare) {
    this.isSellableECare = isSellableECare;
    return this;
  }

   /**
   * IS the device sellable ECare
   * @return isSellableECare
  **/
  public Boolean getIsSellableECare() {
    return isSellableECare;
  }

  public void setIsSellableECare(Boolean isSellableECare) {
    this.isSellableECare = isSellableECare;
  }

  public MerchandisingControl isDisplayableAcq(Boolean isDisplayableAcq) {
    this.isDisplayableAcq = isDisplayableAcq;
    return this;
  }

   /**
   * IS the prdevice Dsellable
   * @return isDisplayableAcq
  **/
  public Boolean getIsDisplayableAcq() {
    return isDisplayableAcq;
  }

  public void setIsDisplayableAcq(Boolean isDisplayableAcq) {
    this.isDisplayableAcq = isDisplayableAcq;
  }

  public MerchandisingControl isSellableRet(Boolean isSellableRet) {
    this.isSellableRet = isSellableRet;
    return this;
  }

   /**
   * IS the prdevice Dsellable
   * @return isSellableRet
  **/
  public Boolean getIsSellableRet() {
    return isSellableRet;
  }

  public void setIsSellableRet(Boolean isSellableRet) {
    this.isSellableRet = isSellableRet;
  }

  public MerchandisingControl isDisplayableRet(Boolean isDisplayableRet) {
    this.isDisplayableRet = isDisplayableRet;
    return this;
  }

   /**
   * IS the device Disellable Ret
   * @return isDisplayableRet
  **/
  public Boolean getIsDisplayableRet() {
    return isDisplayableRet;
  }

  public void setIsDisplayableRet(Boolean isDisplayableRet) {
    this.isDisplayableRet = isDisplayableRet;
  }

  public MerchandisingControl isSellableAcq(Boolean isSellableAcq) {
    this.isSellableAcq = isSellableAcq;
    return this;
  }

   /**
   * IS the device sellable acq
   * @return isSellableAcq
  **/
  public Boolean getIsSellableAcq() {
    return isSellableAcq;
  }

  public void setIsSellableAcq(Boolean isSellableAcq) {
    this.isSellableAcq = isSellableAcq;
  }

  public MerchandisingControl isDisplayableSavedBasket(Boolean isDisplayableSavedBasket) {
    this.isDisplayableSavedBasket = isDisplayableSavedBasket;
    return this;
  }

   /**
   * IS the device Displayable in basket
   * @return isDisplayableSavedBasket
  **/
  public Boolean getIsDisplayableSavedBasket() {
    return isDisplayableSavedBasket;
  }

  public void setIsDisplayableSavedBasket(Boolean isDisplayableSavedBasket) {
    this.isDisplayableSavedBasket = isDisplayableSavedBasket;
  }

  public MerchandisingControl order(Integer order) {
    this.order = order;
    return this;
  }

   /**
   * Order number
   * @return order
  **/
  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public MerchandisingControl preorderable(Boolean preorderable) {
    this.preorderable = preorderable;
    return this;
  }

   /**
   * Can the device pre order
   * @return preorderable
  **/
  public Boolean getPreorderable() {
    return preorderable;
  }

  public void setPreorderable(Boolean preorderable) {
    this.preorderable = preorderable;
  }

  public MerchandisingControl availableFrom(String availableFrom) {
    this.availableFrom = availableFrom;
    return this;
  }

   /**
   * Date from which the device can be available
   * @return availableFrom
  **/
  public String getAvailableFrom() {
    return availableFrom;
  }

  public void setAvailableFrom(String availableFrom) {
    this.availableFrom = availableFrom;
  }

  public MerchandisingControl backorderable(Boolean backorderable) {
    this.backorderable = backorderable;
    return this;
  }

   /**
   * Can the device back order
   * @return backorderable
  **/
  public Boolean getBackorderable() {
    return backorderable;
  }

  public void setBackorderable(Boolean backorderable) {
    this.backorderable = backorderable;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MerchandisingControl merchandisingControl = (MerchandisingControl) o;
    return Objects.equals(this.isDisplayableECare, merchandisingControl.isDisplayableECare) &&
        Objects.equals(this.isSellableECare, merchandisingControl.isSellableECare) &&
        Objects.equals(this.isDisplayableAcq, merchandisingControl.isDisplayableAcq) &&
        Objects.equals(this.isSellableRet, merchandisingControl.isSellableRet) &&
        Objects.equals(this.isDisplayableRet, merchandisingControl.isDisplayableRet) &&
        Objects.equals(this.isSellableAcq, merchandisingControl.isSellableAcq) &&
        Objects.equals(this.isDisplayableSavedBasket, merchandisingControl.isDisplayableSavedBasket) &&
        Objects.equals(this.order, merchandisingControl.order) &&
        Objects.equals(this.preorderable, merchandisingControl.preorderable) &&
        Objects.equals(this.availableFrom, merchandisingControl.availableFrom) &&
        Objects.equals(this.backorderable, merchandisingControl.backorderable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isDisplayableECare, isSellableECare, isDisplayableAcq, isSellableRet, isDisplayableRet, isSellableAcq, isDisplayableSavedBasket, order, preorderable, availableFrom, backorderable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MerchandisingControl {\n");
    
    sb.append("    isDisplayableECare: ").append(toIndentedString(isDisplayableECare)).append("\n");
    sb.append("    isSellableECare: ").append(toIndentedString(isSellableECare)).append("\n");
    sb.append("    isDisplayableAcq: ").append(toIndentedString(isDisplayableAcq)).append("\n");
    sb.append("    isSellableRet: ").append(toIndentedString(isSellableRet)).append("\n");
    sb.append("    isDisplayableRet: ").append(toIndentedString(isDisplayableRet)).append("\n");
    sb.append("    isSellableAcq: ").append(toIndentedString(isSellableAcq)).append("\n");
    sb.append("    isDisplayableSavedBasket: ").append(toIndentedString(isDisplayableSavedBasket)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    preorderable: ").append(toIndentedString(preorderable)).append("\n");
    sb.append("    availableFrom: ").append(toIndentedString(availableFrom)).append("\n");
    sb.append("    backorderable: ").append(toIndentedString(backorderable)).append("\n");
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

