package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * CoupleRelation
 */

public class CoupleRelation {
	private List<BundleHeader> planList = new ArrayList<BundleHeader>();

	/**
	 * 
	 * @param planList
	 * @return
	 */
	public CoupleRelation planList(List<BundleHeader> planList) {
		this.planList = planList;
		return this;
	}

	/**
	 * 
	 * @param planListItem
	 * @return
	 */
	public CoupleRelation addPlanListItem(BundleHeader planListItem) {
		this.planList.add(planListItem);
		return this;
	}

	/**
	 * Get planList
	 * 
	 * @return planList
	 **/
	public List<BundleHeader> getPlanList() {
		return planList;
	}

	/**
	 * 
	 * @param planList
	 */
	public void setPlanList(List<BundleHeader> planList) {
		this.planList = planList;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CoupleRelation coupleRelation = (CoupleRelation) o;
		return Objects.equals(this.planList, coupleRelation.planList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(planList);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CoupleRelation {\n");

		sb.append("    planList: ").append(toIndentedString(planList)).append("\n");
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
