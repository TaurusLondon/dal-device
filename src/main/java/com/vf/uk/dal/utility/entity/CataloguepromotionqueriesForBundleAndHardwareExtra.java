package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * CataloguepromotionqueriesForBundleAndHardwareExtra
 *
 */
@Data
public class CataloguepromotionqueriesForBundleAndHardwareExtra {

	@JsonProperty("tag")
	private String tag = null;

	

	@JsonProperty("label")
	private String label = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("priority")
	private String priority = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("promotionMedia")
	private String promotionMedia = null;

}
