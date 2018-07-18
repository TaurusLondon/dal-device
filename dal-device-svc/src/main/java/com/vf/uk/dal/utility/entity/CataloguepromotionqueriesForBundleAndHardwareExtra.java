package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * CataloguepromotionqueriesForBundleAndHardwareExtra
 *
 */
public class CataloguepromotionqueriesForBundleAndHardwareExtra {

	@JsonProperty("tag")
	private String tag = null;

	/**
	 * 
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * 
	 * @param priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
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
	 * @return
	 */
	public String getPromotionMedia() {
		return promotionMedia;
	}

	/**
	 * 
	 * @param promotionMedia
	 */
	public void setPromotionMedia(String promotionMedia) {
		this.promotionMedia = promotionMedia;
	}

	@JsonProperty("label")
	private String label = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("priority")
	private String priority = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("promotionMedia")
	private String promotionMedia = null;

}
