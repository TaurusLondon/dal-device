package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * GroupDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-05-02T12:09:49.110Z")
@Data
public class ProductGroupDetailsForDeviceList {
	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("groupId")
	private String groupId = null;

	@JsonProperty("size")
	private List<String> size = null;

	@JsonProperty("color")
	private List<Colour> color = null;

}
