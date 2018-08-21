package com.vf.uk.dal.device.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Insurance
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class Insurance {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("price")
	private Price price = null;
	@JsonProperty("merchandisingMedia")
	private List<MediaLink> merchandisingMedia = null;
	@JsonProperty("specsGroup")
	private List<SpecificationGroup> specsGroup = null;

	
}
