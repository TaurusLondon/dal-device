package com.vf.uk.dal.utility.entity;

import java.util.Objects;

/**
 * MonthlyCostSaver
 */

public class MonthlyCostSaver {
	private String mcsPlanId = null;

	private String mcsMessage = null;

	private String mcsParentId = null;

	private Price oneOffPrice = null;

	private Price oneOffDiscountPrice = null;

	private Price monthlyPrice = null;

	private Price monthlyDiscountPrice = null;

	/**
	 * 
	 * @param mcsPlanId
	 * @return
	 */
	public MonthlyCostSaver mcsPlanId(String mcsPlanId) {
		this.mcsPlanId = mcsPlanId;
		return this;
	}

	/**
	 * PlanId of the MCS
	 * 
	 * @return mcsPlanId
	 **/
	public String getMcsPlanId() {
		return mcsPlanId;
	}

	/**
	 * 
	 * @param mcsPlanId
	 */
	public void setMcsPlanId(String mcsPlanId) {
		this.mcsPlanId = mcsPlanId;
	}

	/**
	 * 
	 * @param mcsMessage
	 * @return
	 */
	public MonthlyCostSaver mcsMessage(String mcsMessage) {
		this.mcsMessage = mcsMessage;
		return this;
	}

	/**
	 * PlanId of the MCS
	 * 
	 * @return mcsMessage
	 **/
	public String getMcsMessage() {
		return mcsMessage;
	}

	/**
	 * 
	 * @param mcsMessage
	 */
	public void setMcsMessage(String mcsMessage) {
		this.mcsMessage = mcsMessage;
	}

	/**
	 * 
	 * @param mcsParentId
	 * @return
	 */
	public MonthlyCostSaver mcsParentId(String mcsParentId) {
		this.mcsParentId = mcsParentId;
		return this;
	}

	/**
	 * PlanId of the MCS
	 * 
	 * @return mcsParentId
	 **/
	public String getMcsParentId() {
		return mcsParentId;
	}

	/**
	 * 
	 * @param mcsParentId
	 */
	public void setMcsParentId(String mcsParentId) {
		this.mcsParentId = mcsParentId;
	}

	/**
	 * 
	 * @param oneOffPrice
	 * @return
	 */
	public MonthlyCostSaver oneOffPrice(Price oneOffPrice) {
		this.oneOffPrice = oneOffPrice;
		return this;
	}

	/**
	 * Get oneOffPrice
	 * 
	 * @return oneOffPrice
	 **/
	public Price getOneOffPrice() {
		return oneOffPrice;
	}

	/**
	 * 
	 * @param oneOffPrice
	 */
	public void setOneOffPrice(Price oneOffPrice) {
		this.oneOffPrice = oneOffPrice;
	}

	/**
	 * 
	 * @param oneOffDiscountPrice
	 * @return
	 */
	public MonthlyCostSaver oneOffDiscountPrice(Price oneOffDiscountPrice) {
		this.oneOffDiscountPrice = oneOffDiscountPrice;
		return this;
	}

	/**
	 * Get oneOffDiscountPrice
	 * 
	 * @return oneOffDiscountPrice
	 **/
	public Price getOneOffDiscountPrice() {
		return oneOffDiscountPrice;
	}

	/**
	 * 
	 * @param oneOffDiscountPrice
	 */
	public void setOneOffDiscountPrice(Price oneOffDiscountPrice) {
		this.oneOffDiscountPrice = oneOffDiscountPrice;
	}

	/**
	 * 
	 * @param monthlyPrice
	 * @return
	 */
	public MonthlyCostSaver monthlyPrice(Price monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
		return this;
	}

	/**
	 * Get monthlyPrice
	 * 
	 * @return monthlyPrice
	 **/
	public Price getMonthlyPrice() {
		return monthlyPrice;
	}

	/**
	 * 
	 * @param monthlyPrice
	 */
	public void setMonthlyPrice(Price monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}

	/**
	 * 
	 * @param monthlyDiscountPrice
	 * @return
	 */
	public MonthlyCostSaver monthlyDiscountPrice(Price monthlyDiscountPrice) {
		this.monthlyDiscountPrice = monthlyDiscountPrice;
		return this;
	}

	/**
	 * Get monthlyDiscountPrice
	 * 
	 * @return monthlyDiscountPrice
	 **/
	public Price getMonthlyDiscountPrice() {
		return monthlyDiscountPrice;
	}

	/**
	 * 
	 * @param monthlyDiscountPrice
	 */
	public void setMonthlyDiscountPrice(Price monthlyDiscountPrice) {
		this.monthlyDiscountPrice = monthlyDiscountPrice;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MonthlyCostSaver monthlyCostSaver = (MonthlyCostSaver) o;
		return Objects.equals(this.mcsPlanId, monthlyCostSaver.mcsPlanId)
				&& Objects.equals(this.mcsMessage, monthlyCostSaver.mcsMessage)
				&& Objects.equals(this.mcsParentId, monthlyCostSaver.mcsParentId)
				&& Objects.equals(this.oneOffPrice, monthlyCostSaver.oneOffPrice)
				&& Objects.equals(this.oneOffDiscountPrice, monthlyCostSaver.oneOffDiscountPrice)
				&& Objects.equals(this.monthlyPrice, monthlyCostSaver.monthlyPrice)
				&& Objects.equals(this.monthlyDiscountPrice, monthlyCostSaver.monthlyDiscountPrice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mcsPlanId, mcsMessage, mcsParentId, oneOffPrice, oneOffDiscountPrice, monthlyPrice,
				monthlyDiscountPrice);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class MonthlyCostSaver {\n");

		sb.append("    mcsPlanId: ").append(toIndentedString(mcsPlanId)).append("\n");
		sb.append("    mcsMessage: ").append(toIndentedString(mcsMessage)).append("\n");
		sb.append("    mcsParentId: ").append(toIndentedString(mcsParentId)).append("\n");
		sb.append("    oneOffPrice: ").append(toIndentedString(oneOffPrice)).append("\n");
		sb.append("    oneOffDiscountPrice: ").append(toIndentedString(oneOffDiscountPrice)).append("\n");
		sb.append("    monthlyPrice: ").append(toIndentedString(monthlyPrice)).append("\n");
		sb.append("    monthlyDiscountPrice: ").append(toIndentedString(monthlyDiscountPrice)).append("\n");
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
