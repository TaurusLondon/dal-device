package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 
 * AccessoryTileGroup
 *
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class AccessoryTileGroup {
	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("accessories")
	private List<Accessory> accessories = null;

}
