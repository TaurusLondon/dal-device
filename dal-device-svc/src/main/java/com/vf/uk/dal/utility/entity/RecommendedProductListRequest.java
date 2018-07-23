package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * RecommendedProductListRequest
 *
 */
@Data
public class RecommendedProductListRequest {
	private String serialNumber = null;

	private String accountCategory = null;

	private List<InstalledProduct> installedProducts = new ArrayList<InstalledProduct>();

	private List<Preferences> preferences = new ArrayList<Preferences>();

	private List<String> recommendedProductTypes = new ArrayList<String>();

	private List<BasketItem> basketItems = new ArrayList<BasketItem>();

	private String noOfRecommendations = null;
}
