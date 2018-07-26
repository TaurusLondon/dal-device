package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Misc {

	@JsonProperty("itemAttribute")
	private List<ItemAttribute> itemAttribute;

}
