package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * RecommendedProduct
 *
 */
@Data
public class RecommendedProduct {

	@JsonProperty("typeCode")
	private String typeCode = null;

	@JsonProperty("priorityCode")
	private String priorityCode = null;

	@JsonProperty("id")
	private String id = null;

	@JsonProperty("content")
	private String content = null;

	@JsonProperty("recommendationReasons")
	private List<Reason> recommendationReasons = new ArrayList<Reason>();

	@JsonProperty("anyTimeUpgradeAmount")
	private String anyTimeUpgradeAmount = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("isCompatible")
	private Boolean isCompatible = null;
}
