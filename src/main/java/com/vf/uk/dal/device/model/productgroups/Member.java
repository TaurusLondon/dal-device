package com.vf.uk.dal.device.model.productgroups;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Member
 * @author manoj.bera
 *
 */
@Data
public class Member {

	@JsonProperty("id")
	private String id;

	@JsonProperty("priority")
	private Long priority;

	
}
