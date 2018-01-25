package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class CataloguepromotionqueriesForBundleAndHardwareSecureNet.
 */
public class CataloguepromotionqueriesForBundleAndHardwareSecureNet {

	/** The tag. */
	@JsonProperty("tag")
	private String tag = null;

	/** The label. */
	@JsonProperty("label")
	private String label = null;

	/** The type. */
	@JsonProperty("type")
	private String type = null;

	/** The priority. */
	@JsonProperty("priority")
	private String priority = null;

	/** The description. */
	@JsonProperty("description")
	private String description = null;

	/** The package type. */
	@JsonProperty("packageType")
	private List<String> packageType = new ArrayList<>();

	/** The foot notes. */
	private List<String> footNotes = new ArrayList<>();
	
	/** The promotion media. */
	  @JsonProperty("promotionMedia")
	  private String promotionMedia = null;
	  
	  
	  
	  /**
	   * Promotion media.
	   *
	   * @param promotionMedia the promotion media
	   * @return the cataloguepromotionqueries for bundle and hardware entertainment packs
	   */
	  public CataloguepromotionqueriesForBundleAndHardwareSecureNet promotionMedia(String promotionMedia) {
	    this.promotionMedia = promotionMedia;
	    return this;
	  }
	  
	   /**
	    * Promotion media url for the merchandising promotion.
	    *
	    * @return promotionMedia
	    */
	  public String getPromotionMedia() {
	    return promotionMedia;
	  }
	  
	   /**
	   * Sets the promotion media.
	   *
	   * @param promotionMedia the new promotion media
	   */
	  public void setPromotionMedia(String promotionMedia) {
	    this.promotionMedia = promotionMedia;
	  }
	  

	/**
	 * Gets the foot notes.
	 *
	 * @return the foot notes
	 */
	public List<String> getFootNotes() {
		return footNotes;
	}

	/**
	 * Sets the foot notes.
	 *
	 * @param footNotes
	 *            the new foot notes
	 */
	public void setFootNotes(List<String> footNotes) {
		this.footNotes = footNotes;
	}

	/**
	 * Gets the package type.
	 *
	 * @return the package type
	 */
	public List<String> getPackageType() {
		return packageType;
	}

	/**
	 * Sets the package type.
	 *
	 * @param packageType
	 *            the new package type
	 */
	public void setPackageType(List<String> packageType) {
		this.packageType = packageType;
	}

	/**
	 * Tag.
	 *
	 * @param tag
	 *            the tag
	 * @return the cataloguepromotionqueries for bundle and hardware secure net
	 */
	public CataloguepromotionqueriesForBundleAndHardwareSecureNet tag(String tag) {
		this.tag = tag;
		return this;
	}

	/**
	 * Unique tag name for the merchandising promotion.
	 *
	 * @return tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag
	 *            the new tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Label.
	 *
	 * @param label
	 *            the label
	 * @return the cataloguepromotionqueries for bundle and hardware secure net
	 */
	public CataloguepromotionqueriesForBundleAndHardwareSecureNet label(String label) {
		this.label = label;
		return this;
	}

	/**
	 * Descriptive text for the merchandising promotion.
	 *
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label
	 *            the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Type.
	 *
	 * @param type
	 *            the type
	 * @return the cataloguepromotionqueries for bundle and hardware secure net
	 */
	public CataloguepromotionqueriesForBundleAndHardwareSecureNet type(String type) {
		this.type = type;
		return this;
	}

	/**
	 * Merchandising promotion type.
	 *
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Priority.
	 *
	 * @param priority
	 *            the priority
	 * @return the cataloguepromotionqueries for bundle and hardware secure net
	 */
	public CataloguepromotionqueriesForBundleAndHardwareSecureNet priority(String priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * Priority for the merchandising promotion.
	 *
	 * @return priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Description.
	 *
	 * @param description
	 *            the description
	 * @return the cataloguepromotionqueries for bundle and hardware secure net
	 */
	public CataloguepromotionqueriesForBundleAndHardwareSecureNet description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Description for the merchandising promotion.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CataloguepromotionqueriesForBundleAndHardwareSecureNet cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks = (CataloguepromotionqueriesForBundleAndHardwareSecureNet) o;
		return Objects.equals(this.tag, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.tag)
				&& Objects.equals(this.label, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.label)
				&& Objects.equals(this.type, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.type)
				&& Objects.equals(this.priority,cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.priority)
				&& Objects.equals(this.description,cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.description)
				&& Objects.equals(this.packageType,cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.packageType)
				&& Objects.equals(this.promotionMedia, cataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks.promotionMedia);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(tag, label, type, priority, description, packageType,promotionMedia);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks {\n");

		sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    packageType: ").append(toIndentedString(packageType)).append("\n");
		sb.append("    promotionMedia: ").append(toIndentedString(promotionMedia)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 *
	 * @param o
	 *            the o
	 * @return the string
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
