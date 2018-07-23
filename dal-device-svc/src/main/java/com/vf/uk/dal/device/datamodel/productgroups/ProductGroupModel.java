package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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

	

}
