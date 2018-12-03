package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * FacetedDevice
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class FacetedDevice {
	@JsonProperty("facet")
	private List<NewFacet> newFacet = null;

	@JsonProperty("device")
	private List<Device> device = null;

	@JsonProperty("noOfRecordsFound")
	private long noOfRecordsFound;

	@JsonProperty("message")
	private String message = null;
}
