package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
/**
 * 
 * AccessoryTileGroup
 *
 */
@Data
public class AccessoryTileGroup {
	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("accessories")
	private List<Accessory> accessories = null;

}
