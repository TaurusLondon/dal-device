package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * RecommendedProductListRequest
 *
 */
@Data
public class RecommendedProductListRequest {

	@JsonProperty("serialNumber")
	private String serialNumber = null;

	@JsonProperty("accountCategory")
	private String accountCategory = null;

	@JsonProperty("installedProducts")
	private List<InstalledProduct> installedProducts = new ArrayList<InstalledProduct>();

	@JsonProperty("preferences")
	private List<Preferences> preferences = new ArrayList<Preferences>();

	@JsonProperty("recommendedProductTypes")
	private List<String> recommendedProductTypes = new ArrayList<String>();

	@JsonProperty("basketItems")
	private List<BasketItem> basketItems = new ArrayList<BasketItem>();

	@JsonProperty("noOfRecommendations")
	private String noOfRecommendations = null;
}
