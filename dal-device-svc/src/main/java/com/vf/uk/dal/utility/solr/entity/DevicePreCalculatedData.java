package com.vf.uk.dal.utility.solr.entity;

import java.util.List;

import lombok.Data;

/**
 * 
 * DevicePreCalculatedData
 *
 */
@Data
public class DevicePreCalculatedData {
	private String deviceId;

	private Float rating;

	private String leadPlanId;

	private String nonUpgradeLeadPlanId;

	private String upgradeLeadPlanId;

	private String productGroupName;

	private String productGroupId;

	private List<Media> media;

	private PriceInfo priceInfo;

	private String minimumCost;

	private String isLeadMember;

	private String leadDeviceId;

	private String upgradeLeadDeviceId;

	private String nonUpgradeLeadDeviceId;
}
