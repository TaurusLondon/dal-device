package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductGroup {

	@JsonProperty("listOfProductGroups")
	private List<Group> listOfProductGroups;

}
