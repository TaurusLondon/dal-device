package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * RecommendedProductListResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-06-12T10:39:17.866Z")
public class RecommendedProductListResponse {

  private List<RecommendedProduct> recommendedProductList = new ArrayList<RecommendedProduct>();

  private StatusInfo statusInfo = null;
  /**
   * 
   * @param recommendedProductList
   * @return
   */
  public RecommendedProductListResponse recommendedProductList(List<RecommendedProduct> recommendedProductList) {
    this.recommendedProductList = recommendedProductList;
    return this;
  }
  /**
   * 
   * @param recommendedProductListItem
   * @return
   */
  public RecommendedProductListResponse addRecommendedProductListItem(RecommendedProduct recommendedProductListItem) {
    this.recommendedProductList.add(recommendedProductListItem);
    return this;
  }

   /**
   * Get recommendedProductList
   * @return recommendedProductList
  **/

  public List<RecommendedProduct> getRecommendedProductList() {
    return recommendedProductList;
  }
  /**
   * 
   * @param recommendedProductList
   */
  public void setRecommendedProductList(List<RecommendedProduct> recommendedProductList) {
    this.recommendedProductList = recommendedProductList;
  }
  /**
   * 
   * @param statusInfo
   * @return
   */
  public RecommendedProductListResponse statusInfo(StatusInfo statusInfo) {
    this.statusInfo = statusInfo;
    return this;
  }

   /**
   * Get statusInfo
   * @return statusInfo
  **/

  public StatusInfo getStatusInfo() {
    return statusInfo;
  }
  /**
   * 
   * @param statusInfo
   */
  public void setStatusInfo(StatusInfo statusInfo) {
    this.statusInfo = statusInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecommendedProductListResponse recommendedProductListResponse = (RecommendedProductListResponse) o;
    return Objects.equals(this.recommendedProductList, recommendedProductListResponse.recommendedProductList) &&
        Objects.equals(this.statusInfo, recommendedProductListResponse.statusInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recommendedProductList, statusInfo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecommendedProductListResponse {\n");
    
    sb.append("    recommendedProductList: ").append(toIndentedString(recommendedProductList)).append("\n");
    sb.append("    statusInfo: ").append(toIndentedString(statusInfo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

