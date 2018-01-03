package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
/**
 * MetaData
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class MetaData   {
  @JsonProperty("seoCanonical")
  private String seoCanonical = null;

  @JsonProperty("seoDescription")
  private String seoDescription = null;

  @JsonProperty("seoKeyWords")
  private String seoKeyWords = null;

  @JsonProperty("seoIndex")
  private String seoIndex = null;

  @JsonProperty("seoRobots")
  private List<UUID> seoRobots = null;

  public MetaData seoCanonical(String seoCanonical) {
    this.seoCanonical = seoCanonical;
    return this;
  }

   /**
   * SEO Canonical
   * @return seoCanonical
  **/
  @ApiModelProperty(value = "SEO Canonical")


  public String getSeoCanonical() {
    return seoCanonical;
  }

  public void setSeoCanonical(String seoCanonical) {
    this.seoCanonical = seoCanonical;
  }

  public MetaData seoDescription(String seoDescription) {
    this.seoDescription = seoDescription;
    return this;
  }

   /**
   * SEO Description
   * @return seoDescription
  **/
  @ApiModelProperty(value = "SEO Description")


  public String getSeoDescription() {
    return seoDescription;
  }

  public void setSeoDescription(String seoDescription) {
    this.seoDescription = seoDescription;
  }

  public MetaData seoKeyWords(String seoKeyWords) {
    this.seoKeyWords = seoKeyWords;
    return this;
  }

   /**
   * SEO DKey words
   * @return seoKeyWords
  **/
  @ApiModelProperty(value = "SEO DKey words")


  public String getSeoKeyWords() {
    return seoKeyWords;
  }

  public void setSeoKeyWords(String seoKeyWords) {
    this.seoKeyWords = seoKeyWords;
  }

  public MetaData seoIndex(String seoIndex) {
    this.seoIndex = seoIndex;
    return this;
  }

   /**
   * SEO Index
   * @return seoIndex
  **/
  @ApiModelProperty(value = "SEO Index")


  public String getSeoIndex() {
    return seoIndex;
  }

  public void setSeoIndex(String seoIndex) {
    this.seoIndex = seoIndex;
  }

  public MetaData seoRobots(List<UUID> seoRobots) {
    this.seoRobots = seoRobots;
    return this;
  }

  public MetaData addSeoRobotsItem(UUID seoRobotsItem) {
    if (this.seoRobots == null) {
      this.seoRobots = new ArrayList<UUID>();
    }
    this.seoRobots.add(seoRobotsItem);
    return this;
  }

   /**
   * List of robots
   * @return seoRobots
  **/
  @ApiModelProperty(value = "List of robots")

  @Valid

  public List<UUID> getSeoRobots() {
    return seoRobots;
  }

  public void setSeoRobots(List<UUID> seoRobots) {
    this.seoRobots = seoRobots;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetaData metaData = (MetaData) o;
    return Objects.equals(this.seoCanonical, metaData.seoCanonical) &&
        Objects.equals(this.seoDescription, metaData.seoDescription) &&
        Objects.equals(this.seoKeyWords, metaData.seoKeyWords) &&
        Objects.equals(this.seoIndex, metaData.seoIndex) &&
        Objects.equals(this.seoRobots, metaData.seoRobots);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seoCanonical, seoDescription, seoKeyWords, seoIndex, seoRobots);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetaData {\n");
    
    sb.append("    seoCanonical: ").append(toIndentedString(seoCanonical)).append("\n");
    sb.append("    seoDescription: ").append(toIndentedString(seoDescription)).append("\n");
    sb.append("    seoKeyWords: ").append(toIndentedString(seoKeyWords)).append("\n");
    sb.append("    seoIndex: ").append(toIndentedString(seoIndex)).append("\n");
    sb.append("    seoRobots: ").append(toIndentedString(seoRobots)).append("\n");
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

