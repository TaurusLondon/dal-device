package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CataloguepromotionqueriesForBundleAndHardwareExtra {

	@JsonProperty("tag")
	private String tag = null;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPromotionMedia() {
		return promotionMedia;
	}

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
