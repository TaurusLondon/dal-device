package com.vf.uk.dal.device.datamodel.productgroups;

import lombok.Data;

@Data
public class CacheProductGroupModel {

	private String id;

	private String leadPlanId;

	private Float minimumCost;

	private String leadDeviceId;

	private Float rating;

	private String upgradeLeadDeviceId;

	private String nonUpgradeLeadDeviceId;

	private String upgradeLeadPlanId;

	private String nonUpgradeLeadPlanId;

	
}
