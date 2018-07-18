package com.vf.uk.dal.device.entity;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Insurances
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Insurances {
	@JsonProperty("insuranceList")
	private List<Insurance> insuranceList = null;

	@JsonProperty("minCost")
	private String minCost = null;

	/**
	 * 
	 * @param insuranceList
	 * @return
	 */
	public Insurances insuranceList(List<Insurance> insuranceList) {
		this.insuranceList = insuranceList;
		return this;
	}

	/**
	 * 
	 * @param insuranceListItem
	 * @return
	 */
	public Insurances addInsuranceListItem(Insurance insuranceListItem) {
		if (this.insuranceList == null) {
			this.insuranceList = new ArrayList<>();
		}
		this.insuranceList.add(insuranceListItem);
		return this;
	}

	/**
	 * Get insuranceList
	 * 
	 * @return insuranceList
	 **/
	@ApiModelProperty(value = "")

	@Valid
	/**
	 * 
	 * @return
	 */
	public List<Insurance> getInsuranceList() {
		return insuranceList;
	}

	/**
	 * 
	 * @param insuranceList
	 */
	public void setInsuranceList(List<Insurance> insuranceList) {
		this.insuranceList = insuranceList;
	}

	/**
	 * 
	 * @param minCost
	 * @return
	 */
	public Insurances minCost(String minCost) {
		this.minCost = minCost;
		return this;
	}

	/**
	 * Contains minimum cost of Insurance
	 * 
	 * @return minCost
	 **/
	@ApiModelProperty(value = "Contains minimum cost of Insurance")

	/**
	 * 
	 * @return
	 */
	public String getMinCost() {
		return minCost;
	}

	/**
	 * 
	 * @param minCost
	 */
	public void setMinCost(String minCost) {
		this.minCost = minCost;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Insurances insurances = (Insurances) o;
		return Objects.equals(this.insuranceList, insurances.insuranceList)
				&& Objects.equals(this.minCost, insurances.minCost);
	}

	@Override
	public int hashCode() {
		return Objects.hash(insuranceList, minCost);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Insurances {\n");

		sb.append("    insuranceList: ").append(toIndentedString(insuranceList)).append("\n");
		sb.append("    minCost: ").append(toIndentedString(minCost)).append("\n");
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
