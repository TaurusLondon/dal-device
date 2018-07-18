package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * RecommendedProductListRequest
 *
 */
public class RecommendedProductListRequest {
	private String serialNumber = null;

	private String accountCategory = null;

	private List<InstalledProduct> installedProducts = new ArrayList<InstalledProduct>();

	private List<Preferences> preferences = new ArrayList<Preferences>();

	private List<String> recommendedProductTypes = new ArrayList<String>();

	private List<BasketItem> basketItems = new ArrayList<BasketItem>();

	private String noOfRecommendations = null;

	/**
	 * 
	 * @param serialNumber
	 * @return
	 */
	public RecommendedProductListRequest serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 
	 * @param serialNumber
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 
	 * @param accountCategory
	 * @return
	 */
	public RecommendedProductListRequest accountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getAccountCategory() {
		return accountCategory;
	}

	/**
	 * 
	 * @param accountCategory
	 */
	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	/**
	 * 
	 * @param installedProducts
	 * @return
	 */
	public RecommendedProductListRequest installedProducts(List<InstalledProduct> installedProducts) {
		this.installedProducts = installedProducts;
		return this;
	}

	/**
	 * 
	 * @param installedProductsItem
	 * @return
	 */
	public RecommendedProductListRequest addInstalledProductsItem(InstalledProduct installedProductsItem) {
		this.installedProducts.add(installedProductsItem);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<InstalledProduct> getInstalledProducts() {
		return installedProducts;
	}

	/**
	 * 
	 * @param installedProducts
	 */
	public void setInstalledProducts(List<InstalledProduct> installedProducts) {
		this.installedProducts = installedProducts;
	}

	/**
	 * 
	 * @param preferences
	 * @return
	 */
	public RecommendedProductListRequest preferences(List<Preferences> preferences) {
		this.preferences = preferences;
		return this;
	}

	/**
	 * 
	 * @param preferencesItem
	 * @return
	 */
	public RecommendedProductListRequest addPreferencesItem(Preferences preferencesItem) {
		this.preferences.add(preferencesItem);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<Preferences> getPreferences() {
		return preferences;
	}

	/**
	 * 
	 * @param preferences
	 */
	public void setPreferences(List<Preferences> preferences) {
		this.preferences = preferences;
	}

	/**
	 * 
	 * @param recommendedProductTypes
	 * @return
	 */
	public RecommendedProductListRequest recommendedProductTypes(List<String> recommendedProductTypes) {
		this.recommendedProductTypes = recommendedProductTypes;
		return this;
	}

	/**
	 * 
	 * @param recommendedProductTypesItem
	 * @return
	 */
	public RecommendedProductListRequest addRecommendedProductTypesItem(String recommendedProductTypesItem) {
		this.recommendedProductTypes.add(recommendedProductTypesItem);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getRecommendedProductTypes() {
		return recommendedProductTypes;
	}

	/**
	 * 
	 * @param recommendedProductTypes
	 */
	public void setRecommendedProductTypes(List<String> recommendedProductTypes) {
		this.recommendedProductTypes = recommendedProductTypes;
	}

	/**
	 * 
	 * @param basketItems
	 * @return
	 */
	public RecommendedProductListRequest basketItems(List<BasketItem> basketItems) {
		this.basketItems = basketItems;
		return this;
	}

	/**
	 * 
	 * @param basketItemsItem
	 * @return
	 */
	public RecommendedProductListRequest addBasketItemsItem(BasketItem basketItemsItem) {
		this.basketItems.add(basketItemsItem);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<BasketItem> getBasketItems() {
		return basketItems;
	}

	/**
	 * 
	 * @param basketItems
	 */
	public void setBasketItems(List<BasketItem> basketItems) {
		this.basketItems = basketItems;
	}

	/**
	 * 
	 * @param noOfRecommendations
	 * @return
	 */
	public RecommendedProductListRequest noOfRecommendations(String noOfRecommendations) {
		this.noOfRecommendations = noOfRecommendations;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getNoOfRecommendations() {
		return noOfRecommendations;
	}

	/**
	 * 
	 * @param noOfRecommendations
	 */
	public void setNoOfRecommendations(String noOfRecommendations) {
		this.noOfRecommendations = noOfRecommendations;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RecommendedProductListRequest recommendedProductListRequest = (RecommendedProductListRequest) o;
		return Objects.equals(this.serialNumber, recommendedProductListRequest.serialNumber)
				&& Objects.equals(this.accountCategory, recommendedProductListRequest.accountCategory)
				&& Objects.equals(this.installedProducts, recommendedProductListRequest.installedProducts)
				&& Objects.equals(this.preferences, recommendedProductListRequest.preferences)
				&& Objects.equals(this.recommendedProductTypes, recommendedProductListRequest.recommendedProductTypes)
				&& Objects.equals(this.basketItems, recommendedProductListRequest.basketItems)
				&& Objects.equals(this.noOfRecommendations, recommendedProductListRequest.noOfRecommendations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(serialNumber, accountCategory, installedProducts, preferences, recommendedProductTypes,
				basketItems, noOfRecommendations);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class RecommendedProductListRequest {\n");

		sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
		sb.append("    accountCategory: ").append(toIndentedString(accountCategory)).append("\n");
		sb.append("    installedProducts: ").append(toIndentedString(installedProducts)).append("\n");
		sb.append("    preferences: ").append(toIndentedString(preferences)).append("\n");
		sb.append("    recommendedProductTypes: ").append(toIndentedString(recommendedProductTypes)).append("\n");
		sb.append("    basketItems: ").append(toIndentedString(basketItems)).append("\n");
		sb.append("    noOfRecommendations: ").append(toIndentedString(noOfRecommendations)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
