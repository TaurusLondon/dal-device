package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Source
 */
public class SourcePackageSummary   {
  @JsonProperty("promotionId")
  private String promotionId = null;

  @JsonProperty("displayName")
  private String displayName = null;

  @JsonProperty("desc")
  private String desc = null;

  @JsonProperty("imageUrl")
  private String imageUrl = null;

  @JsonProperty("assetId")
  private String assetId = null;

  @JsonProperty("subscriptionId")
  private String subscriptionId = null;
/**
 * 
 * @param promotionId
 * @return
 */
  public SourcePackageSummary promotionId(String promotionId) {
    this.promotionId = promotionId;
    return this;
  }

   /**
   * Current promotion ID
   * @return promotionId
  **/
  public String getPromotionId() {
    return promotionId;
  }

  /**
   * 
   * @param promotionId
   */
  public void setPromotionId(String promotionId) {
    this.promotionId = promotionId;
  }

  /**
   * 
   * @param name
   * @return
   */
  public SourcePackageSummary displayName(String name) {
    this.displayName = name;
    return this;
  }

   /**
   * Product catalogue Promotion name
   * @return name
  **/
  public String getDisplayName() {
    return displayName;
  }

  /**
   * 
   * @param name
   */
  public void setDisplayName(String name) {
    this.displayName = name;
  }

  /**
   * 
   * @param desc
   * @return
   */
  public SourcePackageSummary desc(String desc) {
    this.desc = desc;
    return this;
  }

   /**
   * Product catalogue Promotion short description
   * @return desc
  **/
  public String getDesc() {
    return desc;
  }

  /**
   * 
   * @param desc
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }

  /**
   * 
   * @param imageUrl
   * @return
   */
  public SourcePackageSummary imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

   /**
   * Product catalogue Promotion thumbnail image URL
   * @return imageUrl
  **/
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * 
   * @param imageUrl
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
/**
 * 
 * @param assetId
 * @return
 */
  public SourcePackageSummary assetId(String assetId) {
    this.assetId = assetId;
    return this;
  }

   /**
   * Asset ID of an current promotion ID
   * @return assetId
  **/
  public String getAssetId() {
    return assetId;
  }
/**
 * 
 * @param assetId
 */
  public void setAssetId(String assetId) {
    this.assetId = assetId;
  }
/**
 * 
 * @param subscriptionId
 * @return
 */
  public SourcePackageSummary subscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
    return this;
  }

   /**
   * Current service identifier (e.g. MSISDN)
   * @return subscriptionId
  **/
  public String getSubscriptionId() {
    return subscriptionId;
  }
/**
 * 
 * @param subscriptionId
 */
  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SourcePackageSummary source = (SourcePackageSummary) o;
    return Objects.equals(this.promotionId, source.promotionId) &&
        Objects.equals(this.displayName, source.displayName) &&
        Objects.equals(this.desc, source.desc) &&
        Objects.equals(this.imageUrl, source.imageUrl) &&
        Objects.equals(this.assetId, source.assetId) &&
        Objects.equals(this.subscriptionId, source.subscriptionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(promotionId, displayName, desc, imageUrl, assetId, subscriptionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Source {\n");
    
    sb.append("    promotionId: ").append(toIndentedString(promotionId)).append("\n");
    sb.append("    name: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
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
