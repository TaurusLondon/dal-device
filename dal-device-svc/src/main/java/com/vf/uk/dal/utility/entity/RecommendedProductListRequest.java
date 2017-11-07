package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class RecommendedProductListRequest {
  private String serialNumber = null;

  private String accountCategory = null;

  private List<InstalledProduct> installedProducts = new ArrayList<InstalledProduct>();

  private List<Preferences> preferences = new ArrayList<Preferences>();

  private List<String> recommendedProductTypes = new ArrayList<String>();

  private List<BasketItem> basketItems = new ArrayList<BasketItem>();

  private String noOfRecommendations = null;

  public RecommendedProductListRequest serialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public RecommendedProductListRequest accountCategory(String accountCategory) {
    this.accountCategory = accountCategory;
    return this;
  }

  public String getAccountCategory() {
    return accountCategory;
  }

  public void setAccountCategory(String accountCategory) {
    this.accountCategory = accountCategory;
  }

  public RecommendedProductListRequest installedProducts(List<InstalledProduct> installedProducts) {
    this.installedProducts = installedProducts;
    return this;
  }

  public RecommendedProductListRequest addInstalledProductsItem(InstalledProduct installedProductsItem) {
    this.installedProducts.add(installedProductsItem);
    return this;
  }

  public List<InstalledProduct> getInstalledProducts() {
    return installedProducts;
  }

  public void setInstalledProducts(List<InstalledProduct> installedProducts) {
    this.installedProducts = installedProducts;
  }

  public RecommendedProductListRequest preferences(List<Preferences> preferences) {
    this.preferences = preferences;
    return this;
  }

  public RecommendedProductListRequest addPreferencesItem(Preferences preferencesItem) {
    this.preferences.add(preferencesItem);
    return this;
  }

  public List<Preferences> getPreferences() {
    return preferences;
  }

  public void setPreferences(List<Preferences> preferences) {
    this.preferences = preferences;
  }

  public RecommendedProductListRequest recommendedProductTypes(List<String> recommendedProductTypes) {
    this.recommendedProductTypes = recommendedProductTypes;
    return this;
  }

  public RecommendedProductListRequest addRecommendedProductTypesItem(String recommendedProductTypesItem) {
    this.recommendedProductTypes.add(recommendedProductTypesItem);
    return this;
  }

  public List<String> getRecommendedProductTypes() {
    return recommendedProductTypes;
  }

  public void setRecommendedProductTypes(List<String> recommendedProductTypes) {
    this.recommendedProductTypes = recommendedProductTypes;
  }

  public RecommendedProductListRequest basketItems(List<BasketItem> basketItems) {
    this.basketItems = basketItems;
    return this;
  }

  public RecommendedProductListRequest addBasketItemsItem(BasketItem basketItemsItem) {
    this.basketItems.add(basketItemsItem);
    return this;
  }

  public List<BasketItem> getBasketItems() {
    return basketItems;
  }

  public void setBasketItems(List<BasketItem> basketItems) {
    this.basketItems = basketItems;
  }

  public RecommendedProductListRequest noOfRecommendations(String noOfRecommendations) {
    this.noOfRecommendations = noOfRecommendations;
    return this;
  }

  public String getNoOfRecommendations() {
    return noOfRecommendations;
  }

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
    return Objects.equals(this.serialNumber, recommendedProductListRequest.serialNumber) &&
        Objects.equals(this.accountCategory, recommendedProductListRequest.accountCategory) &&
        Objects.equals(this.installedProducts, recommendedProductListRequest.installedProducts) &&
        Objects.equals(this.preferences, recommendedProductListRequest.preferences) &&
        Objects.equals(this.recommendedProductTypes, recommendedProductListRequest.recommendedProductTypes) &&
        Objects.equals(this.basketItems, recommendedProductListRequest.basketItems) &&
        Objects.equals(this.noOfRecommendations, recommendedProductListRequest.noOfRecommendations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serialNumber, accountCategory, installedProducts, preferences, recommendedProductTypes, basketItems, noOfRecommendations);
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

