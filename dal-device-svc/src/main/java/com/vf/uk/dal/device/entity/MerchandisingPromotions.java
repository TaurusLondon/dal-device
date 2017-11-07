package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * MerchandisingPromotions
 */

public class MerchandisingPromotions   {
  private String promotionName = null;

  public MerchandisingPromotions promotionName(String promotionName) {
    this.promotionName = promotionName;
    return this;
  }

   /**
   * List of promotion like, Popular, Limited Time, Household, etc
   * @return promotionName
  **/
  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MerchandisingPromotions merchandisingPromotions = (MerchandisingPromotions) o;
    return Objects.equals(this.promotionName, merchandisingPromotions.promotionName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(promotionName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MerchandisingPromotions {\n");
    
    sb.append("    promotionName: ").append(toIndentedString(promotionName)).append("\n");
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

