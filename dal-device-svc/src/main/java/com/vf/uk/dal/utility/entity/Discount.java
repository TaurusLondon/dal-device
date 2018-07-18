package com.vf.uk.dal.utility.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Discount
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-14T09:23:00.845Z")

public class Discount {
	@JsonProperty("skuId")
	private String skuId = null;

	@JsonProperty("tag")
	private String tag = null;

	/**
	 * 
	 * @param skuId
	 * @return
	 */
	public Discount skuId(String skuId) {
		this.skuId = skuId;
		return this;
	}

	/**
	 * Sku ID of the discount product - when available
	 * 
	 * @return skuId
	 **/
	public String getSkuId() {
		return skuId;
	}

	/**
	 * 
	 * @param skuId
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * 
	 * @param tag
	 * @return
	 */
	public Discount tag(String tag) {
		this.tag = tag;
		return this;
	}

	/**
	 * Tag name of the associated merchandising promotion
	 * 
	 * @return tag
	 **/
	public String getTag() {
		return tag;
	}

	/**
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Discount discount = (Discount) o;
		return Objects.equals(this.skuId, discount.skuId) && Objects.equals(this.tag, discount.tag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(skuId, tag);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Discount {\n");

		sb.append("    skuId: ").append(toIndentedString(skuId)).append("\n");
		sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
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
