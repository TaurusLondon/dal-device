package com.vf.uk.dal.device.model.solr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * DevicePreCalculatedData
 *
 */
@Data
public class DevicePreCalculatedData {
	@JsonProperty("deviceId")
	private String deviceId;
	@JsonProperty("rating")
	private Float rating;
	@JsonProperty("leadPlanId")
	private String leadPlanId;
	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;
	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;
	@JsonProperty("productGroupName")
	private String productGroupName;
	@JsonProperty("media")
	private List<Media> media;
	@JsonProperty("priceInfo")
	private PriceInfo priceInfo;
	@JsonProperty("minimumCost")
	private String minimumCost;
	@JsonProperty("isLeadMember")
	private String isLeadMember;
	@JsonProperty("leadDeviceId")
	private String leadDeviceId;
	@JsonProperty("upgradeLeadDeviceId")
	private String upgradeLeadDeviceId;
	@JsonProperty("nonUpgradeLeadDeviceId")
	private String nonUpgradeLeadDeviceId;
	@JsonProperty("groupType")
	private String groupType;
	@JsonProperty("paymProductGroupId")
	private String paymProductGroupId;
	@JsonProperty("paygProductGroupId")
	private String paygProductGroupId;
}
