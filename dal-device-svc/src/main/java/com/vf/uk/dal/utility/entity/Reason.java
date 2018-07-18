package com.vf.uk.dal.utility.entity;

import java.util.Objects;

/**
 * 
 * Reason
 *
 */
public class Reason {
	private String name = null;

	private String value = null;

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Reason name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Reason value(String value) {
		this.value = value;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Reason reason = (Reason) o;
		return Objects.equals(this.name, reason.name) && Objects.equals(this.value, reason.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Reason {\n");

		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
