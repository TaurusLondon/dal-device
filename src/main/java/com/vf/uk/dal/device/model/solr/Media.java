package com.vf.uk.dal.device.model.solr;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Media
 *
 */
@Data
public class Media {
	@JsonProperty("id")
	private String id;
	@JsonProperty("value")
	private String value;
	@JsonProperty("type")
	private String type;
	@JsonProperty("promoCategory")
	private String promoCategory;
	@JsonProperty("offerCode")
	private String offerCode;
	@JsonProperty("description")
	private String description;
	@JsonProperty("discountId")
	private String discountId;

}