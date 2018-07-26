package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Recommendations {

	@JsonProperty("type")
	private String type;

	@JsonProperty("name")
	private String name;

}
