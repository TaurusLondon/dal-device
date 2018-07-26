package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ComplexCondition {
	@JsonProperty("key")
	private String key;
	@JsonProperty("operator")
	private String operator;
	@JsonProperty("value")
	private String value;

	}
