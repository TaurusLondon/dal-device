package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

/**
 * Condition
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class Condition {

	@JsonProperty("productPath")
	private String productPath;
	@JsonProperty("packageType")
	private String packageType;
	@JsonProperty("complexConditions")
	private List<ComplexCondition> complexConditions;

}
