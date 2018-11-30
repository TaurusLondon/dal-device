package com.vf.uk.dal.device.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MetaData
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class MetaData {
	@JsonProperty("seoCanonical")
	private String seoCanonical = null;

	@JsonProperty("seoDescription")
	private String seoDescription = null;

	@JsonProperty("seoKeyWords")
	private String seoKeyWords = null;

	@JsonProperty("seoIndex")
	private String seoIndex = null;

	@JsonProperty("seoRobots")
	private List<UUID> seoRobots = null;
}
