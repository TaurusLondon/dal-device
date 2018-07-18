package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * MerchandisingControl
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class MerchandisingControl {
	@JsonProperty("isDisplayableECare")
	private Boolean isDisplayableECare = null;

	@JsonProperty("isSellableECare")
	private Boolean isSellableECare = null;

	@JsonProperty("isDisplayableAcq")
	private Boolean isDisplayableAcq = null;

	@JsonProperty("isSellableRet")
	private Boolean isSellableRet = null;

	@JsonProperty("isDisplayableRet")
	private Boolean isDisplayableRet = null;

	@JsonProperty("isSellableAcq")
	private Boolean isSellableAcq = null;

	@JsonProperty("isDisplayableSavedBasket")
	private Boolean isDisplayableSavedBasket = null;

	@JsonProperty("order")
	private Integer order = null;

	@JsonProperty("preorderable")
	private Boolean preorderable = null;

	@JsonProperty("availableFrom")
	private String availableFrom = null;

	@JsonProperty("backorderable")
	private Boolean backorderable = null;

	/**
	 * 
	 * @param isDisplayableECare
	 * @return
	 */
	public MerchandisingControl isDisplayableECare(Boolean isDisplayableECare) {
		this.isDisplayableECare = isDisplayableECare;
		return this;
	}

	/**
	 * IS the device Disellable in ECare
	 * 
	 * @return isDisplayableECare
	 **/

	public Boolean getIsDisplayableECare() {
		return isDisplayableECare;
	}

	/**
	 * 
	 * @param isDisplayableECare
	 */
	public void setIsDisplayableECare(Boolean isDisplayableECare) {
		this.isDisplayableECare = isDisplayableECare;
	}

	/**
	 * 
	 * @param isSellableECare
	 * @return
	 */
	public MerchandisingControl isSellableECare(Boolean isSellableECare) {
		this.isSellableECare = isSellableECare;
		return this;
	}

	/**
	 * IS the device sellable ECare
	 * 
	 * @return isSellableECare
	 **/

	public Boolean getIsSellableECare() {
		return isSellableECare;
	}

	/**
	 * 
	 * @param isSellableECare
	 */
	public void setIsSellableECare(Boolean isSellableECare) {
		this.isSellableECare = isSellableECare;
	}

	/**
	 * 
	 * @param isDisplayableAcq
	 * @return
	 */
	public MerchandisingControl isDisplayableAcq(Boolean isDisplayableAcq) {
		this.isDisplayableAcq = isDisplayableAcq;
		return this;
	}

	/**
	 * IS the prdevice Dsellable
	 * 
	 * @return isDisplayableAcq
	 **/

	public Boolean getIsDisplayableAcq() {
		return isDisplayableAcq;
	}

	/**
	 * 
	 * @param isDisplayableAcq
	 */
	public void setIsDisplayableAcq(Boolean isDisplayableAcq) {
		this.isDisplayableAcq = isDisplayableAcq;
	}

	/**
	 * 
	 * @param isSellableRet
	 * @return
	 */
	public MerchandisingControl isSellableRet(Boolean isSellableRet) {
		this.isSellableRet = isSellableRet;
		return this;
	}

	/**
	 * IS the prdevice Dsellable
	 * 
	 * @return isSellableRet
	 **/

	public Boolean getIsSellableRet() {
		return isSellableRet;
	}

	/**
	 * 
	 * @param isSellableRet
	 */
	public void setIsSellableRet(Boolean isSellableRet) {
		this.isSellableRet = isSellableRet;
	}

	/**
	 * 
	 * @param isDisplayableRet
	 * @return
	 */
	public MerchandisingControl isDisplayableRet(Boolean isDisplayableRet) {
		this.isDisplayableRet = isDisplayableRet;
		return this;
	}

	/**
	 * IS the device Disellable Ret
	 * 
	 * @return isDisplayableRet
	 **/

	/**
	 * 
	 * @return
	 */
	public Boolean getIsDisplayableRet() {
		return isDisplayableRet;
	}

	/**
	 * 
	 * @param isDisplayableRet
	 */
	public void setIsDisplayableRet(Boolean isDisplayableRet) {
		this.isDisplayableRet = isDisplayableRet;
	}

	/**
	 * 
	 * @param isSellableAcq
	 * @return
	 */
	public MerchandisingControl isSellableAcq(Boolean isSellableAcq) {
		this.isSellableAcq = isSellableAcq;
		return this;
	}

	/**
	 * IS the device sellable acq
	 * 
	 * @return isSellableAcq
	 **/

	public Boolean getIsSellableAcq() {
		return isSellableAcq;
	}

	/**
	 * 
	 * @param isSellableAcq
	 */
	public void setIsSellableAcq(Boolean isSellableAcq) {
		this.isSellableAcq = isSellableAcq;
	}

	/**
	 * 
	 * @param isDisplayableSavedBasket
	 * @return
	 */
	public MerchandisingControl isDisplayableSavedBasket(Boolean isDisplayableSavedBasket) {
		this.isDisplayableSavedBasket = isDisplayableSavedBasket;
		return this;
	}

	/**
	 * IS the device Displayable in basket
	 * 
	 * @return isDisplayableSavedBasket
	 **/

	public Boolean getIsDisplayableSavedBasket() {
		return isDisplayableSavedBasket;
	}

	/**
	 * 
	 * @param isDisplayableSavedBasket
	 */
	public void setIsDisplayableSavedBasket(Boolean isDisplayableSavedBasket) {
		this.isDisplayableSavedBasket = isDisplayableSavedBasket;
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	public MerchandisingControl order(Integer order) {
		this.order = order;
		return this;
	}

	/**
	 * Order number
	 * 
	 * @return order
	 **/
	@ApiModelProperty(value = "Order number")

	public Integer getOrder() {
		return order;
	}

	/**
	 * 
	 * @param order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * 
	 * @param preorderable
	 * @return
	 */
	public MerchandisingControl preorderable(Boolean preorderable) {
		this.preorderable = preorderable;
		return this;
	}

	/**
	 * Can the device pre order
	 * 
	 * @return preorderable
	 **/

	public Boolean getPreorderable() {
		return preorderable;
	}

	/**
	 * 
	 * @param preorderable
	 */
	public void setPreorderable(Boolean preorderable) {
		this.preorderable = preorderable;
	}

	/**
	 * 
	 * @param availableFrom
	 * @return
	 */
	public MerchandisingControl availableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
		return this;
	}

	/**
	 * Date from which the device can be available
	 * 
	 * @return availableFrom
	 **/
	@ApiModelProperty(value = "Date from which the device can be available")

	public String getAvailableFrom() {
		return availableFrom;
	}

	/**
	 * 
	 * @param availableFrom
	 */
	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}

	/**
	 * 
	 * @param backorderable
	 * @return
	 */
	public MerchandisingControl backorderable(Boolean backorderable) {
		this.backorderable = backorderable;
		return this;
	}

	/**
	 * Can the device back order
	 * 
	 * @return backorderable
	 **/

	public Boolean getBackorderable() {
		return backorderable;
	}

	/**
	 * 
	 * @param backorderable
	 */
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
		return Objects.equals(this.isDisplayableECare, merchandisingControl.isDisplayableECare)
				&& Objects.equals(this.isSellableECare, merchandisingControl.isSellableECare)
				&& Objects.equals(this.isDisplayableAcq, merchandisingControl.isDisplayableAcq)
				&& Objects.equals(this.isSellableRet, merchandisingControl.isSellableRet)
				&& Objects.equals(this.isDisplayableRet, merchandisingControl.isDisplayableRet)
				&& Objects.equals(this.isSellableAcq, merchandisingControl.isSellableAcq)
				&& Objects.equals(this.isDisplayableSavedBasket, merchandisingControl.isDisplayableSavedBasket)
				&& Objects.equals(this.order, merchandisingControl.order)
				&& Objects.equals(this.preorderable, merchandisingControl.preorderable)
				&& Objects.equals(this.availableFrom, merchandisingControl.availableFrom)
				&& Objects.equals(this.backorderable, merchandisingControl.backorderable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isDisplayableECare, isSellableECare, isDisplayableAcq, isSellableRet, isDisplayableRet,
				isSellableAcq, isDisplayableSavedBasket, order, preorderable, availableFrom, backorderable);
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
