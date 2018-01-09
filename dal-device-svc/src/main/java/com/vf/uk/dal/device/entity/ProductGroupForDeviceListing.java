package com.vf.uk.dal.device.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductGroupForDeviceListing {
	@JsonProperty("groupId")
	  private String groupId = null;
	
	@JsonProperty("leadMemberid")
	  private String leadMemberid = null;

	 @JsonProperty("leadPlanid")
	  private String leadPlanid = null;

	  @JsonProperty("devicePlanId")
	  private String devicePlanId = null;

	  @JsonProperty("monthlyPrice")
	  private String monthlyPrice = null;
	  
	  @JsonProperty("oneOffPrice")
	  private String oneOffPrice = null;
	  
	  @JsonProperty("rating")
	  private String rating = null;
	  
	  /**
	   * 
	   * @return
	   */
	  public String getRating() {
		return rating;
	}
	  /**
	   * 
	   * @param rating
	   */
	public void setRating(String rating) {
		this.rating = rating;
	}
	/**
	 * 
	 * @return
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 
	 * @return
	 */
	public String getLeadMemberid() {
		return leadMemberid;
	}
	/**
	 * 
	 * @param leadMemberid
	 */
	public void setLeadMemberid(String leadMemberid) {
		this.leadMemberid = leadMemberid;
	}
	/**
	 * 
	 * @return
	 */
	public String getLeadPlanid() {
		return leadPlanid;
	}
	/**
	 * 
	 * @param leadPlanid
	 */
	public void setLeadPlanid(String leadPlanid) {
		this.leadPlanid = leadPlanid;
	}
	/**
	 * 
	 * @return
	 */
	public String getDevicePlanId() {
		return devicePlanId;
	}
	/**
	 * 
	 * @param devicePlanId
	 */
	public void setDevicePlanId(String devicePlanId) {
		this.devicePlanId = devicePlanId;
	}
	/**
	 * 
	 * @return
	 */
	public String getMonthlyPrice() {
		return monthlyPrice;
	}
	/**
	 * 
	 * @param monthlyPrice
	 */
	public void setMonthlyPrice(String monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}
	/**
	 * 
	 * @return
	 */
	public String getOneOffPrice() {
		return oneOffPrice;
	}
	/**
	 * 
	 * @param oneOffPrice
	 */
	public void setOneOffPrice(String oneOffPrice) {
		this.oneOffPrice = oneOffPrice;
	}
	

}
