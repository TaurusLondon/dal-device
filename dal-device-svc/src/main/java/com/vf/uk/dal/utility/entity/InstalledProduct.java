package com.vf.uk.dal.utility.entity;

import java.util.Objects;

/**
 * 
 * InstalledProduct
 *
 */
public class InstalledProduct {
	private String id = null;

	private String typeCode = null;

	private String amount = null;

	/**
	 * 
	 * @param id
	 * @return
	 */
	public InstalledProduct id(String id) {
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

	/**
	 * 
	 * @param typeCode
	 * @return
	 */
	public InstalledProduct typeCode(String typeCode) {
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
	 * @param amount
	 * @return
	 */
	public InstalledProduct amount(String amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		InstalledProduct installedProduct = (InstalledProduct) o;
		return Objects.equals(this.id, installedProduct.id) && Objects.equals(this.typeCode, installedProduct.typeCode)
				&& Objects.equals(this.amount, installedProduct.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, typeCode, amount);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class InstalledProduct {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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
