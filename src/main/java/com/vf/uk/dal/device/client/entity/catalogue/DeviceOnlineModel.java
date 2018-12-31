package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.List;

import lombok.Data;

@Data
public class DeviceOnlineModel{
	
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

	/** Size of all devices belong to particular group */
	private List<String> size;

	/** Color of all devices belong to particular group */
	private List<String> color;
	
	/** MustHaveFeatures of all devices belong to particular group */
	private List<String> mustHaveFeatures;

	/** OperatingSystem of all devices belong to particular group */
	private List<String> operatingSystem;
	
	/** color Name And Hex of all devices belong to particular group */
	private List<Color> colorNameAndHex;

	/** lead Upgrade Device Id of Group */
	private String leadUpgradeDeviceId;

	/** lead Non Upgrade Device Id of Group */
	private String leadNonUpgradeDeviceId;

	/** Group Level minimum Cost */
	private String groupLevelMinimumCost;
	
	/** Group priority */
	private Long priority;

	/** Devices Information of the Group */
	private List<Device> devices;
	
}
