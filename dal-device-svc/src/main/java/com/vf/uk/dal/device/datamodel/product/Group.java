package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Group
 * @author manoj.bera
 *
 */
@Data
public class Group {

	@JsonProperty("groupName")
	private String groupName;

	@JsonProperty("priority")
	private Long priority;

	@JsonProperty("comparable")
	private boolean comparable;

	@JsonProperty("specifications")
	private List<Specification> specifications;

	@JsonProperty("type")
	private String type;
}
