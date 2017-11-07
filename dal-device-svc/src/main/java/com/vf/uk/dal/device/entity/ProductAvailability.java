package com.vf.uk.dal.device.entity;

import java.util.Objects;
/**
 * ProductAvailability
 */

public class ProductAvailability   {
  private String startDate = null;

  private String endDate = null;

  private Boolean salesExpired = null;

  public ProductAvailability startDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

   /**
   * Start Date of the Product \"2012-04-01+05:30\"
   * @return startDate
  **/
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public ProductAvailability endDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * End date of the Product 2012-04-21+05:30\"
   * @return endDate
  **/
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public ProductAvailability salesExpired(Boolean salesExpired) {
    this.salesExpired = salesExpired;
    return this;
  }

   /**
   * Whether the sale of the product has been expired, Yes or No
   * @return salesExpired
  **/
  public Boolean getSalesExpired() {
    return salesExpired;
  }

  public void setSalesExpired(Boolean salesExpired) {
    this.salesExpired = salesExpired;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductAvailability productAvailability = (ProductAvailability) o;
    return Objects.equals(this.startDate, productAvailability.startDate) &&
        Objects.equals(this.endDate, productAvailability.endDate) &&
        Objects.equals(this.salesExpired, productAvailability.salesExpired);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, salesExpired);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductAvailability {\n");
    
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    salesExpired: ").append(toIndentedString(salesExpired)).append("\n");
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

