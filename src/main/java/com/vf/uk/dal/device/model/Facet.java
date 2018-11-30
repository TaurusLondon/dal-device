package com.vf.uk.dal.device.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Facet
 */
@Data
public class Facet {
	
	@JsonProperty("equipmentMake")
	private String equipmentMake = null;

	@JsonProperty("makeList")
	private List<Make> makeList = new ArrayList<>();

}
