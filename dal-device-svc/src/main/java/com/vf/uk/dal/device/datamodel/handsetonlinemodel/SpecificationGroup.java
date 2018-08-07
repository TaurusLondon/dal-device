package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * SpecificationGroup
 */
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class SpecificationGroup {
	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("priority")
	private Integer priority = null;

	@JsonProperty("comparable")
	private Boolean comparable = null;

	@JsonProperty("specifications")
	private List<Specification> specifications = null;

}
