package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.List;

import lombok.Data;

@Data
public class DeviceOnlineModel {
	/** Product group Name */
	private String productGroupName;

	/** product group type */
	private String productgroupType;

	/** product group Id */
	private Integer productGroupId;

	/** product group make from Group Level */
	private String productMake;

	/** product group model from Group Level */
	private String productModel;

	/** Type of the model */
	private String __type;

	/** Size of all devices belong to particular group acquisition */
	private List<String> size;

	/** Size of all devices belong to particular group upgrade */
	private List<String> sizeUpgrade;

	/** Color of all devices belong to particular group */
	private List<String> color;

	/** MustHaveFeatures of all devices belong to particular group */
	private List<String> mustHaveFeatures;

	/** OperatingSystem of all devices belong to particular group */
	private List<String> operatingSystem;

	/**
	 * color Name And Hex of all devices belong to particular group for
	 * acquisition
	 */
	private List<Color> colorNameAndHex;

	/** color Name And Hex of all devices belong to particular group upgrade */
	private List<Color> colorNameAndHexUpgrade;

	/** lead Upgrade Device Id of Group */
	private String leadUpgradeDeviceId;

	/** lead Non Upgrade Device Id of Group */
	private String leadNonUpgradeDeviceId;

	/** Group Level minimum Cost */
	private String groupLevelMinimumCost;

	/** product group model from Group Level */
	private Boolean isLeadNonUpgradePlanId;

	/** product group model from Group Level */
	private Boolean isLeadUpgradePlanId;

	/** product group model from Group Level */
	private Boolean isLeadNonUpgradePlanIdConsumer;

	/** product group model from Group Level */
	private Boolean isLeadUpgradePlanIdConsumer;

	/** product group model from Group Level */
	private Boolean isLeadNonUpgradePlanIdBusiness;

	/** product group model from Group Level */
	private Boolean isLeadUpgradePlanIdBusiness;

	/** Group priority */
	private Long priority;

	/** Devices Information of the Group */
	private List<Device> devices;
}
