package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import lombok.Data;

@Data
public class DevicePreCalculatedData {

	private String deviceId;
	private Float rating;
	private String leadPlanId;
	private String productGroupName;
	private String productGroupId;
	private List<Media> media;
	private PriceInfo priceInfo;
	private String isLeadMember;
	private Float minimumCost;
	private String leadDeviceId;
	private String upgradeLeadDeviceId;
	private String nonUpgradeLeadDeviceId;
	private String upgradeLeadPlanId;
	private String nonUpgradeLeadPlanId;

	
}
