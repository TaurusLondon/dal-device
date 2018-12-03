package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * ProductGroupForDeviceListing
 *
 */
@Data
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

}
