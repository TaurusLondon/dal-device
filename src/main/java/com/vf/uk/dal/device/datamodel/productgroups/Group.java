package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Group
 * @author manoj.bera
 *
 */
@Data
@ApiModel(value="productGroup" , description="Product Group Details")
public class Group {

	@JsonProperty("name")
	private String name;

	@JsonProperty("groupPriority")
	private Long groupPriority;

	@JsonProperty("groupType")
	private String groupType;
	
	@JsonIgnore
	private String version;
	
	@JsonProperty("members")
	private List<Member> members;
	
	@JsonProperty("groupId")
	private Integer groupId;
	
	private String equipmentMake;
	
	private String equipmentModel;

	
}
