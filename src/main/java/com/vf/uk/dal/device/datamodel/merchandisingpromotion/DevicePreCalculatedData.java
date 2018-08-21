package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DevicePreCalculatedData
 * @author manoj.bera
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
	@JsonProperty("productGroupName")
	private String productGroupName;
	@JsonProperty("productGroupId")
	private String productGroupId;
	@JsonProperty("media")
	private List<Media> media;
	@JsonProperty("priceInfo")
	private PriceInfo priceInfo;
	@JsonProperty("isLeadMember")
	private String isLeadMember;
	@JsonProperty("minimumCost")
	private Float minimumCost;
	@JsonProperty("leadDeviceId")
	private String leadDeviceId;
	@JsonProperty("upgradeLeadDeviceId")
	private String upgradeLeadDeviceId;
	@JsonProperty("nonUpgradeLeadDeviceId")
	private String nonUpgradeLeadDeviceId;
	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;
	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;

	
}
