package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductGroupModel {

	private String id;

	@JsonProperty("productId")
	private String leadSkuId;

	private String name;

	@JsonProperty("imageURLsFullFront")
	private String imageUrl;

	private float minimumRecurringPrice;

	private float minimumUpfrontCost;

	private float rating;

	private List<String> listOfVariants;

	private int priority;

	private String type;

	private String leadPlanId;

	private String equipmentMake;

	private String equipmentModel;

	private List<String> colour;

	private List<String> capacity;

	private List<String> operatingSystem;

	private String leadStartDate;

	private List<String> mustHaveFeatures;

	private String leadDeviceId;

	private Float minimumCost;

	private String isLeadMember;

	private List<String> facetColour;

	private String upgradeLeadDeviceId;

	private String nonUpgradeLeadDeviceId;

	private String upgradeLeadPlanId;

	private String nonUpgradeLeadPlanId;

	private Date createDate;

	private Date modifiedDate;
	
	private List<String> hexCode;

	public List<String> getHexCode() {
		return hexCode;
	}

	public void setHexCode(List<String> hexCode) {
		this.hexCode = hexCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeadSkuId() {
		return leadSkuId;
	}

	public void setLeadSkuId(String leadSkuId) {
		this.leadSkuId = leadSkuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public float getMinimumRecurringPrice() {
		return minimumRecurringPrice;
	}

	public void setMinimumRecurringPrice(float minimumRecurringPrice) {
		this.minimumRecurringPrice = minimumRecurringPrice;
	}

	public float getMinimumUpfrontCost() {
		return minimumUpfrontCost;
	}

	public void setMinimumUpfrontCost(float minimumUpfrontCost) {
		this.minimumUpfrontCost = minimumUpfrontCost;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public List<String> getListOfVariants() {
		return listOfVariants;
	}

	public void setListOfVariants(List<String> listOfVariants) {
		this.listOfVariants = listOfVariants;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeadPlanId() {
		return leadPlanId;
	}

	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}

	public String getEquipmentMake() {
		return equipmentMake;
	}

	public void setEquipmentMake(String equipmentMake) {
		this.equipmentMake = equipmentMake;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public List<String> getColour() {
		return colour;
	}

	public void setColour(List<String> colour) {
		this.colour = colour;
	}

	public List<String> getCapacity() {
		return capacity;
	}

	public void setCapacity(List<String> capacity) {
		this.capacity = capacity;
	}

	public List<String> getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(List<String> operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getLeadStartDate() {
		return leadStartDate;
	}

	public void setLeadStartDate(String leadStartDate) {
		this.leadStartDate = leadStartDate;
	}

	public List<String> getMustHaveFeatures() {
		return mustHaveFeatures;
	}

	public void setMustHaveFeatures(List<String> mustHaveFeatures) {
		this.mustHaveFeatures = mustHaveFeatures;
	}

	public String getLeadDeviceId() {
		return leadDeviceId;
	}

	public void setLeadDeviceId(String leadDeviceId) {
		this.leadDeviceId = leadDeviceId;
	}

	public Float getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(Float minimumCost) {
		this.minimumCost = minimumCost;
	}

	public String getIsLeadMember() {
		return isLeadMember;
	}

	public void setIsLeadMember(String isLeadMember) {
		this.isLeadMember = isLeadMember;
	}

	public List<String> getFacetColour() {
		return facetColour;
	}

	public void setFacetColour(List<String> facetColour) {
		this.facetColour = facetColour;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
