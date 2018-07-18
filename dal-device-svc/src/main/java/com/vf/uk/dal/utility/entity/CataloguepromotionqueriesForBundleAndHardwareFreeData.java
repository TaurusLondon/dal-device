package com.vf.uk.dal.utility.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CataloguepromotionqueriesForBundleAndHardwareFreeData.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-05T11:10:07.841Z")

public class CataloguepromotionqueriesForBundleAndHardwareFreeData {

	/** The uom. */
	@JsonProperty("uom")
	private String uom = null;

	/** The value. */
	@JsonProperty("value")
	private String value = null;

	/**
	 * Uom.
	 *
	 * @param uom
	 *            the uom
	 * @return the cataloguepromotionqueries for bundle and hardware free data
	 */
	public CataloguepromotionqueriesForBundleAndHardwareFreeData uom(String uom) {
		this.uom = uom;
		return this;
	}

	/**
	 * Unit of measurement for data value.
	 *
	 * @return uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * Sets the uom.
	 *
	 * @param uom
	 *            the new uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * Value.
	 *
	 * @param value
	 *            the value
	 * @return the cataloguepromotionqueries for bundle and hardware free data
	 */
	public CataloguepromotionqueriesForBundleAndHardwareFreeData value(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Data value.
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CataloguepromotionqueriesForBundleAndHardwareFreeData cataloguepromotionqueriesForBundleAndHardwareFreeData = (CataloguepromotionqueriesForBundleAndHardwareFreeData) o;
		return Objects.equals(this.uom, cataloguepromotionqueriesForBundleAndHardwareFreeData.uom)
				&& Objects.equals(this.value, cataloguepromotionqueriesForBundleAndHardwareFreeData.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(uom, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CataloguepromotionqueriesForBundleAndHardwareFreeData {\n");

		sb.append("    uom: ").append(toIndentedString(uom)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 *
	 * @param o
	 *            the o
	 * @return the string
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
