package com.vf.uk.dal.utility.entity;

import java.util.Objects;

/**
 * BasketItem
 * 
 * @author
 *
 */
public class BasketItem {
	private String typeCode = null;

	private String id = null;

	/**
	 * 
	 * @param typeCode
	 * @return
	 */
	public BasketItem typeCode(String typeCode) {
		this.typeCode = typeCode;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * 
	 * @param typeCode
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BasketItem id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BasketItem basketItem = (BasketItem) o;
		return Objects.equals(this.typeCode, basketItem.typeCode) && Objects.equals(this.id, basketItem.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(typeCode, id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BasketItem {\n");

		sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
