package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductGroupModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("productId")
	private String leadSkuId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("imageURLsFullFront")
	private String imageUrl;

	@JsonProperty("minimumRecurringPrice")
	private float minimumRecurringPrice;
	@JsonProperty("minimumUpfrontCost")
	private float minimumUpfrontCost;
	@JsonProperty("rating")
	private float rating;
	@JsonProperty("listOfVariants")
	private List<String> listOfVariants;
	@JsonProperty("priority")
	private int priority;
	@JsonProperty("type")
	private String type;
	@JsonProperty("leadPlanId")
	private String leadPlanId;
	@JsonProperty("equipmentMake")
	private String equipmentMake;
	@JsonProperty("equipmentModel")
	private String equipmentModel;
	@JsonProperty("colour")
	private List<String> colour;
	@JsonProperty("capacity")
	private List<String> capacity;
	@JsonProperty("operatingSystem")
	private List<String> operatingSystem;
	@JsonProperty("leadStartDate")
	private String leadStartDate;
	@JsonProperty("mustHaveFeatures")
	private List<String> mustHaveFeatures;
	@JsonProperty("leadDeviceId")
	private String leadDeviceId;
	@JsonProperty("minimumCost")
	private Float minimumCost;
	@JsonProperty("isLeadMember")
	private String isLeadMember;
	@JsonProperty("facetColour")
	private List<String> facetColour;
	@JsonProperty("upgradeLeadDeviceId")
	private String upgradeLeadDeviceId;
	@JsonProperty("nonUpgradeLeadDeviceId")
	private String nonUpgradeLeadDeviceId;
	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;
	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;
	@JsonProperty("createDate")
	private Date createDate;
	@JsonProperty("modifiedDate")
	private Date modifiedDate;
	@JsonProperty("hexCode")
	private List<String> hexCode;

	

}
