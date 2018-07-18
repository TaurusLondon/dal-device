package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Equipment
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Equipment {
	@JsonProperty("make")
	private String make = null;

	@JsonProperty("model")
	private String model = null;

	/**
	 * 
	 * @param make
	 * @return
	 */
	public Equipment make(String make) {
		this.make = make;
		return this;
	}

	/**
	 * Make of the device
	 * 
	 * @return make
	 **/
	@ApiModelProperty(value = "Make of the device")

	/**
	 * 
	 * @return
	 */
	public String getMake() {
		return make;
	}

	/**
	 * 
	 * @param make
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public Equipment model(String model) {
		this.model = model;
		return this;
	}

	/**
	 * Model of the device
	 * 
	 * @return model
	 **/
	@ApiModelProperty(value = "Model of the device")

	/**
	 * 
	 * @return
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Equipment equipment = (Equipment) o;
		return Objects.equals(this.make, equipment.make) && Objects.equals(this.model, equipment.model);
	}

	@Override
	public int hashCode() {
		return Objects.hash(make, model);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Equipment {\n");

		sb.append("    make: ").append(toIndentedString(make)).append("\n");
		sb.append("    model: ").append(toIndentedString(model)).append("\n");
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
