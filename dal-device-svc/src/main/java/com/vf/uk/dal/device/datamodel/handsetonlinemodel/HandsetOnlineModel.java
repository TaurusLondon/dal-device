package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class HandsetOnlineModel{
	
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

	/** Devices Information of the Group */
	private Map<String, Device> devices;
	
}
