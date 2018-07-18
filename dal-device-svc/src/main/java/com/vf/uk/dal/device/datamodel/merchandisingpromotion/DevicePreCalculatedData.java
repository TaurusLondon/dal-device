package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getLeadPlanId() {
		return leadPlanId;
	}

	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public List<Media> getMedia() {
		return media;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	public PriceInfo getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}

	public String getIsLeadMember() {
		return isLeadMember;
	}

	public void setIsLeadMember(String isLeadMember) {
		this.isLeadMember = isLeadMember;
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
