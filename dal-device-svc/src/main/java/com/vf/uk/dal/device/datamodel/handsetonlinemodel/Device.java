package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class Device {
	
	@JsonProperty("deviceId")
	private String deviceId;
	
	@JsonProperty("make")
	private String make;
	
	@JsonProperty("model")
	private String model;
	
	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;
	
	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;
	
	@JsonProperty("isFromPricingForUpgradeLeadPlanId")
	private Boolean isFromPricingForUpgradeLeadPlanId;

	@JsonProperty("isFromPricingForNonUpgradeLeadPlanId")
	private Boolean isFromPricingForNonUpgradeLeadPlanId;
	
	@JsonProperty("displayName")
	private String displayName;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("displayDescription")
	private String displayDescription;
	
	@JsonProperty("colourName")
	private String colourName;
	
	@JsonProperty("colourHex")
	private String colourHex;
	
	@JsonProperty("memory")
	private String memory;
	
	@JsonProperty("leadPlanIdForMEF")
	private String leadPlanIdForMEF;
	
	@JsonProperty("leadPlanDisplayNameForNonUpgrade")
	private String leadPlanDisplayNameForNonUpgrade;
	
	@JsonProperty("bundleTypeNonUpgrade")
	private String bundleTypeNonUpgrade;
	
	@JsonProperty("uomNonUpgrade")
	private String uomNonUpgrade;
	
	@JsonProperty("uomValueNonUpgrade")
	private String uomValueNonUpgrade;
	
	@JsonProperty("leadPlanDisplayNameForUpgrade")
	private String leadPlanDisplayNameForUpgrade;
	
	@JsonProperty("bundleTypeUpgrade")
	private String bundleTypeUpgrade;
	
	@JsonProperty("uomUpgrade")
	private String uomUpgrade;
	
	@JsonProperty("uomValueUpgrade")
	private String uomValueUpgrade;
	
	@JsonProperty("promotAsForNonUpgradeBundle")
	private List<String> promotAsForNonUpgradeBundle;
	
	@JsonProperty("promotAsForUpgradeBundle")
	private List<String> promotAsForUpgradeBundle;
	
	@JsonProperty("bundleHardwareTupleForNonUpgradeLeadPlan")
	private List<BundleAndHardwareTuple> bundleHardwareTupleForNonUpgradeLeadPlan;

	@JsonProperty("bundleHardwareTupleForUpgradeLeadPlan")
	private List<BundleAndHardwareTuple> bundleHardwareTupleForUpgradeLeadPlan;
	
	@JsonProperty("productGroupURI")
	private String productGroupURI;
	
	@JsonProperty("preOrderable")
	private Boolean preOrderable;
	
	@JsonProperty("availableFrom")
	private String availableFrom;
	
	@JsonProperty("productClass")
	private String productClass;
	
	@JsonProperty("productLines")
	private List<String> productLines;
	
	@JsonProperty("merchandisingPromotion")
	private List<MerchandisingPromotions> merchandisingPromotion;
	
	@JsonProperty("promotAsForHardware")
	private List<String> promotAsForHardware;

	@JsonProperty("specificationsGroups")
	private List<SpecificationGroup> specificationsGroups;
	
	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("endDate")
	private String endDate;

	@JsonProperty("salesExpired")
	private Boolean salesExpired;
	
	@JsonProperty("isDeviceProduct")
	private Boolean isDeviceProduct;
	
	@JsonProperty("isServiceProduct")
	private Boolean isServiceProduct;
	
	@JsonProperty("isBattery")
	private String isBattery;
	
	@JsonProperty("merchandisingControl")
	private MerchandisingControl merchandisingControl;
	
	@JsonProperty("compatiblePlans")
	private List<String> compatiblePlans;
	
	@JsonProperty("mediaLink")
	private List<MediaLink> mediaLink;
	
	@JsonProperty("metaData")
	private MetaData metaData;
}
