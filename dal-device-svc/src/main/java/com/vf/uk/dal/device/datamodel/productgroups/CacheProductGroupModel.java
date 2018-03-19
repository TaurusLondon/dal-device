package com.vf.uk.dal.device.datamodel.productgroups;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeadPlanId() {
		return leadPlanId;
	}

	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}

	public Float getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(Float minimumCost) {
		this.minimumCost = minimumCost;
	}

	public String getLeadDeviceId() {
		return leadDeviceId;
	}

	public void setLeadDeviceId(String leadDeviceId) {
		this.leadDeviceId = leadDeviceId;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getUpgradeLeadDeviceId() {
		return upgradeLeadDeviceId;
	}

	public void setUpgradeLeadDeviceId(String upgradeLeadDeviceId) {
		this.upgradeLeadDeviceId = upgradeLeadDeviceId;
	}

	public String getNonUpgradeLeadDeviceId() {
		return nonUpgradeLeadDeviceId;
	}

	public void setNonUpgradeLeadDeviceId(String nonUpgradeLeadDeviceId) {
		this.nonUpgradeLeadDeviceId = nonUpgradeLeadDeviceId;
	}

	public String getUpgradeLeadPlanId() {
		return upgradeLeadPlanId;
	}

	public void setUpgradeLeadPlanId(String upgradeLeadPlanId) {
		this.upgradeLeadPlanId = upgradeLeadPlanId;
	}

	public String getNonUpgradeLeadPlanId() {
		return nonUpgradeLeadPlanId;
	}

	public void setNonUpgradeLeadPlanId(String nonUpgradeLeadPlanId) {
		this.nonUpgradeLeadPlanId = nonUpgradeLeadPlanId;
	}
}
