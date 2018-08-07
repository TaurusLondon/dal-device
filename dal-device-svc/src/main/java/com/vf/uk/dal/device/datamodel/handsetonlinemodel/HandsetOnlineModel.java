package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class HandsetOnlineModel {
	@JsonProperty("productGroupName")
	private String productGroupName;

	@JsonProperty("productgroupType")
	private String productgroupType;

	@JsonProperty("productGroupId")
	private Integer productGroupId;

	@JsonProperty("productMake")
	private String productMake;

	@JsonProperty("productModel")
	private String productModel;

	@JsonProperty("__type")
	private String __type;

	@JsonProperty("size")
	private List<String> size;

	@JsonProperty("color")
	private List<String> color;
	
	@JsonProperty("mustHaveFeatures")
	private List<String> mustHaveFeatures;

	@JsonProperty("operatingSystem")
	private List<String> operatingSystem;
	
	@JsonProperty("colorNameAndHex")
	private List<Color> colorNameAndHex;

	@JsonProperty("leadUpgradeDeviceId")
	private String leadUpgradeDeviceId;

	@JsonProperty("leadNonUpgradeDeviceId")
	private String leadNonUpgradeDeviceId;

	@JsonProperty("groupLableminimumCost")
	private Float groupLableminimumCost;

	@JsonProperty("device")
	private Map<String, Device> device;

}
