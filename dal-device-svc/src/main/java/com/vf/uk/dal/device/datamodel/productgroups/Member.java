package com.vf.uk.dal.device.datamodel.productgroups;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Member {

	@JsonProperty("id")
	private String id;

	@JsonProperty("priority")
	private Long priority;

	
}
