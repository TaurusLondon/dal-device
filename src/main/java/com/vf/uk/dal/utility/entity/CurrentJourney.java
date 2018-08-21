package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * CurrentJourney
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-13T09:29:52.184Z")
@Data
public class CurrentJourney {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("username")
	private String username = null;

	@JsonProperty("contextMSISDN")
	private String contextMSISDN = null;

	@JsonProperty("journeyData")
	private JourneyData journeyData = null;

}
