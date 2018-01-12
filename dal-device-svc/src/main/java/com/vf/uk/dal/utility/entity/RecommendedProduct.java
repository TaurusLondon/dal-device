package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * RecommendedProduct
 *
 */
public class RecommendedProduct {
  private String typeCode = null;

  private String priorityCode = null;

  private String id = null;

  private String content = null;

  private List<Reason> recommendationReasons = new ArrayList<Reason>();

  private String anyTimeUpgradeAmount = null;

  private String description = null;

  private Boolean isCompatible = null;
  /**
   * 
   * @param typeCode
   * @return
   */
  public RecommendedProduct typeCode(String typeCode) {
    this.typeCode = typeCode;
    return this;
  }
  /**
   * 
   * @return
   */
  public String getTypeCode() {
    return typeCode;
  }
  /**
   * 
   * @param typeCode
   */
  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }
  /**
   * 
   * @param priorityCode
   * @return
   */
  public RecommendedProduct priorityCode(String priorityCode) {
    this.priorityCode = priorityCode;
    return this;
  }
  /**
   * 
   * @return
   */
  public String getPriorityCode() {
    return priorityCode;
  }
  /**
   * 
   * @param priorityCode
   */
  public void setPriorityCode(String priorityCode) {
    this.priorityCode = priorityCode;
  }
  /**
   * 
   * @param id
   * @return
   */
  public RecommendedProduct id(String id) {
    this.id = id;
    return this;
  }
  /**
   * 
   * @return
   */
  public String getId() {
    return id;
  }
  /**
   * 
   * @param id
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * 
   * @param content
   * @return
   */
  public RecommendedProduct content(String content) {
    this.content = content;
    return this;
  }
  /**
   * 
   * @return
   */
  public String getContent() {
    return content;
  }
  /**
   * 
   * @param content
   */
  public void setContent(String content) {
    this.content = content;
  }
  /**
   * 
   * @param recommendationReasons
   * @return
   */
  public RecommendedProduct recommendationReasons(List<Reason> recommendationReasons) {
    this.recommendationReasons = recommendationReasons;
    return this;
  }
  /**
   * 
   * @param recommendationReasonsItem
   * @return
   */
  public RecommendedProduct addRecommendationReasonsItem(Reason recommendationReasonsItem) {
    this.recommendationReasons.add(recommendationReasonsItem);
    return this;
  }
  /**
   * 
   * @return
   */
  public List<Reason> getRecommendationReasons() {
    return recommendationReasons;
  }
  /**
   * 
   * @param recommendationReasons
   */
  public void setRecommendationReasons(List<Reason> recommendationReasons) {
    this.recommendationReasons = recommendationReasons;
  }
  /**
   * 
   * @param anyTimeUpgradeAmount
   * @return
   */
  public RecommendedProduct anyTimeUpgradeAmount(String anyTimeUpgradeAmount) {
    this.anyTimeUpgradeAmount = anyTimeUpgradeAmount;
    return this;
  }
  /**
   * 
   * @return
   */
  public String getAnyTimeUpgradeAmount() {
    return anyTimeUpgradeAmount;
  }
  /**
   * 
   * @param anyTimeUpgradeAmount
   */
  public void setAnyTimeUpgradeAmount(String anyTimeUpgradeAmount) {
    this.anyTimeUpgradeAmount = anyTimeUpgradeAmount;
  }
  /**
   * /**
   * @param description
   * @return
   */
  public RecommendedProduct description(String description) {
    this.description = description;
    return this;
  }

  /**
   * 
   * @return
   */
  public String getDescription() {
    return description;
  }
  /**
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * 
   * @param isCompatible
   * @return
   */
  public RecommendedProduct isCompatible(Boolean isCompatible) {
    this.isCompatible = isCompatible;
    return this;
  }
  /**
   * 
   * @return
   */
  public Boolean getIsCompatible() {
    return isCompatible;
  }
  /**
   * 
   * @param isCompatible
   */
  public void setIsCompatible(Boolean isCompatible) {
    this.isCompatible = isCompatible;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecommendedProduct recommendedProduct = (RecommendedProduct) o;
    return Objects.equals(this.typeCode, recommendedProduct.typeCode) &&
        Objects.equals(this.priorityCode, recommendedProduct.priorityCode) &&
        Objects.equals(this.id, recommendedProduct.id) &&
        Objects.equals(this.content, recommendedProduct.content) &&
        Objects.equals(this.recommendationReasons, recommendedProduct.recommendationReasons) &&
        Objects.equals(this.anyTimeUpgradeAmount, recommendedProduct.anyTimeUpgradeAmount) &&
        Objects.equals(this.description, recommendedProduct.description) &&
        Objects.equals(this.isCompatible, recommendedProduct.isCompatible);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeCode, priorityCode, id, content, recommendationReasons, anyTimeUpgradeAmount, description, isCompatible);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecommendedProduct {\n");
    
    sb.append("    typeCode: ").append(toIndentedString(typeCode)).append("\n");
    sb.append("    priorityCode: ").append(toIndentedString(priorityCode)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    recommendationReasons: ").append(toIndentedString(recommendationReasons)).append("\n");
    sb.append("    anyTimeUpgradeAmount: ").append(toIndentedString(anyTimeUpgradeAmount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isCompatible: ").append(toIndentedString(isCompatible)).append("\n");
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

