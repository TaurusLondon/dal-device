package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MerchandisingPromotionsWrapper
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T10:55:14.550Z")

public class MerchandisingPromotionsWrapper {
	@JsonProperty("dataPromotion")
	private MerchandisingPromotion dataPromotion = null;

	@JsonProperty("textPromotion")
	private MerchandisingPromotion textPromotion = null;

	@JsonProperty("talkTimePromotion")
	private MerchandisingPromotion talkTimePromotion = null;

	@JsonProperty("entertainmentPackPromotion")
	private MerchandisingPromotion entertainmentPackPromotion = null;

	@JsonProperty("secureNetPromotion")
	private MerchandisingPromotion secureNetPromotion = null;

	@JsonProperty("sashBannerPromotion")
	private MerchandisingPromotion sashBannerPromotion = null;

	@JsonProperty("freeExtraPromotion")
	private MerchandisingPromotion freeExtraPromotion = null;

	@JsonProperty("freeAccessoryPromotion")
	private MerchandisingPromotion freeAccessoryPromotion = null;

	@JsonProperty("pricePromotion")
	private MerchandisingPromotion pricePromotion = null;

	public MerchandisingPromotionsWrapper dataPromotion(MerchandisingPromotion dataPromotion) {
		this.dataPromotion = dataPromotion;
		return this;
	}

	/**
	 * Get dataPromotion
	 * 
	 * @return dataPromotion
	 **/
	public MerchandisingPromotion getDataPromotion() {
		return dataPromotion;
	}

	public void setDataPromotion(MerchandisingPromotion dataPromotion) {
		this.dataPromotion = dataPromotion;
	}

	public MerchandisingPromotionsWrapper textPromotion(MerchandisingPromotion textPromotion) {
		this.textPromotion = textPromotion;
		return this;
	}

	/**
	 * Get textPromotion
	 * 
	 * @return textPromotion
	 **/

	public MerchandisingPromotion getTextPromotion() {
		return textPromotion;
	}

	public void setTextPromotion(MerchandisingPromotion textPromotion) {
		this.textPromotion = textPromotion;
	}

	public MerchandisingPromotionsWrapper talkTimePromotion(MerchandisingPromotion talkTimePromotion) {
		this.talkTimePromotion = talkTimePromotion;
		return this;
	}

	/**
	 * Get talkTimePromotion
	 * 
	 * @return talkTimePromotion
	 **/
	public MerchandisingPromotion getTalkTimePromotion() {
		return talkTimePromotion;
	}

	public void setTalkTimePromotion(MerchandisingPromotion talkTimePromotion) {
		this.talkTimePromotion = talkTimePromotion;
	}

	public MerchandisingPromotionsWrapper entertainmentPackPromotion(
			MerchandisingPromotion entertainmentPackPromotion) {
		this.entertainmentPackPromotion = entertainmentPackPromotion;
		return this;
	}

	/**
	 * Get entertainmentPackPromotion
	 * 
	 * @return entertainmentPackPromotion
	 **/

	public MerchandisingPromotion getEntertainmentPackPromotion() {
		return entertainmentPackPromotion;
	}

	public void setEntertainmentPackPromotion(MerchandisingPromotion entertainmentPackPromotion) {
		this.entertainmentPackPromotion = entertainmentPackPromotion;
	}

	public MerchandisingPromotionsWrapper secureNetPromotion(MerchandisingPromotion secureNetPromotion) {
		this.secureNetPromotion = secureNetPromotion;
		return this;
	}

	/**
	 * Get secureNetPromotion
	 * 
	 * @return secureNetPromotion
	 **/
	public MerchandisingPromotion getSecureNetPromotion() {
		return secureNetPromotion;
	}

	public void setSecureNetPromotion(MerchandisingPromotion secureNetPromotion) {
		this.secureNetPromotion = secureNetPromotion;
	}

	public MerchandisingPromotionsWrapper sashBannerPromotion(MerchandisingPromotion sashBannerPromotion) {
		this.sashBannerPromotion = sashBannerPromotion;
		return this;
	}

	/**
	 * Get sashBannerPromotion
	 * 
	 * @return sashBannerPromotion
	 **/
	public MerchandisingPromotion getSashBannerPromotion() {
		return sashBannerPromotion;
	}

	public void setSashBannerPromotion(MerchandisingPromotion sashBannerPromotion) {
		this.sashBannerPromotion = sashBannerPromotion;
	}

	public MerchandisingPromotionsWrapper freeExtraPromotion(MerchandisingPromotion freeExtraPromotion) {
		this.freeExtraPromotion = freeExtraPromotion;
		return this;
	}

	/**
	 * Get freeExtraPromotion
	 * 
	 * @return freeExtraPromotion
	 **/
	public MerchandisingPromotion getFreeExtraPromotion() {
		return freeExtraPromotion;
	}

	public void setFreeExtraPromotion(MerchandisingPromotion freeExtraPromotion) {
		this.freeExtraPromotion = freeExtraPromotion;
	}

	public MerchandisingPromotionsWrapper freeAccessoryPromotion(MerchandisingPromotion freeAccessoryPromotion) {
		this.freeAccessoryPromotion = freeAccessoryPromotion;
		return this;
	}

	/**
	 * Get freeAccessoryPromotion
	 * 
	 * @return freeAccessoryPromotion
	 **/

	public MerchandisingPromotion getFreeAccessoryPromotion() {
		return freeAccessoryPromotion;
	}

	public void setFreeAccessoryPromotion(MerchandisingPromotion freeAccessoryPromotion) {
		this.freeAccessoryPromotion = freeAccessoryPromotion;
	}

	public MerchandisingPromotionsWrapper pricePromotion(MerchandisingPromotion pricePromotion) {
		this.pricePromotion = pricePromotion;
		return this;
	}

	/**
	 * Get pricePromotion
	 * 
	 * @return pricePromotion
	 **/
	public MerchandisingPromotion getPricePromotion() {
		return pricePromotion;
	}

	public void setPricePromotion(MerchandisingPromotion pricePromotion) {
		this.pricePromotion = pricePromotion;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MerchandisingPromotionsWrapper merchandisingPromotionsWrapper = (MerchandisingPromotionsWrapper) o;
		return Objects.equals(this.dataPromotion, merchandisingPromotionsWrapper.dataPromotion)
				&& Objects.equals(this.textPromotion, merchandisingPromotionsWrapper.textPromotion)
				&& Objects.equals(this.talkTimePromotion, merchandisingPromotionsWrapper.talkTimePromotion)
				&& Objects.equals(this.entertainmentPackPromotion,
						merchandisingPromotionsWrapper.entertainmentPackPromotion)
				&& Objects.equals(this.secureNetPromotion, merchandisingPromotionsWrapper.secureNetPromotion)
				&& Objects.equals(this.sashBannerPromotion, merchandisingPromotionsWrapper.sashBannerPromotion)
				&& Objects.equals(this.freeExtraPromotion, merchandisingPromotionsWrapper.freeExtraPromotion)
				&& Objects.equals(this.freeAccessoryPromotion, merchandisingPromotionsWrapper.freeAccessoryPromotion)
				&& Objects.equals(this.pricePromotion, merchandisingPromotionsWrapper.pricePromotion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataPromotion, textPromotion, talkTimePromotion, entertainmentPackPromotion,
				secureNetPromotion, sashBannerPromotion, freeExtraPromotion, freeAccessoryPromotion, pricePromotion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class MerchandisingPromotionsWrapper {\n");

		sb.append("    dataPromotion: ").append(toIndentedString(dataPromotion)).append("\n");
		sb.append("    textPromotion: ").append(toIndentedString(textPromotion)).append("\n");
		sb.append("    talkTimePromotion: ").append(toIndentedString(talkTimePromotion)).append("\n");
		sb.append("    entertainmentPackPromotion: ").append(toIndentedString(entertainmentPackPromotion)).append("\n");
		sb.append("    secureNetPromotion: ").append(toIndentedString(secureNetPromotion)).append("\n");
		sb.append("    sashBannerPromotion: ").append(toIndentedString(sashBannerPromotion)).append("\n");
		sb.append("    freeExtraPromotion: ").append(toIndentedString(freeExtraPromotion)).append("\n");
		sb.append("    freeAccessoryPromotion: ").append(toIndentedString(freeAccessoryPromotion)).append("\n");
		sb.append("    pricePromotion: ").append(toIndentedString(pricePromotion)).append("\n");
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
