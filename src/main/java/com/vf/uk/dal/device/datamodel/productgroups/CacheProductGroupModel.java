package com.vf.uk.dal.device.datamodel.productgroups;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * CacheProductGroupModel
 * @author manoj.bera
 *
 */
@Data
public class CacheProductGroupModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("leadPlanId")
	private String leadPlanId;

	@JsonProperty("minimumCost")
	private Float minimumCost;

	@JsonProperty("leadDeviceId")
	private String leadDeviceId;

	@JsonProperty("rating")
	private Float rating;

	@JsonProperty("upgradeLeadDeviceId")
	private String upgradeLeadDeviceId;

	@JsonProperty("nonUpgradeLeadDeviceId")
	private String nonUpgradeLeadDeviceId;

	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;

	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;

	
}
