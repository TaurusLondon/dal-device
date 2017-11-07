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
	  
	  public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getLeadMemberid() {
		return leadMemberid;
	}
	public void setLeadMemberid(String leadMemberid) {
		this.leadMemberid = leadMemberid;
	}
	public String getLeadPlanid() {
		return leadPlanid;
	}
	public void setLeadPlanid(String leadPlanid) {
		this.leadPlanid = leadPlanid;
	}
	public String getDevicePlanId() {
		return devicePlanId;
	}
	public void setDevicePlanId(String devicePlanId) {
		this.devicePlanId = devicePlanId;
	}
	public String getMonthlyPrice() {
		return monthlyPrice;
	}
	public void setMonthlyPrice(String monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}
	public String getOneOffPrice() {
		return oneOffPrice;
	}
	public void setOneOffPrice(String oneOffPrice) {
		this.oneOffPrice = oneOffPrice;
	}
	

}
