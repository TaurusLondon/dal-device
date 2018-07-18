package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MerchandisingPromotionModel {

	private String tag;

	private String label;

	private String startDateTime;

	private String endDateTime;

	private String confirmRequired;

	private String productPath;

	private String objectType;

	@JsonProperty("desc")
	private String description;

	private String promotionMedia;

	private Integer priority;

	private String promotionId;

	private String productLine;

	private String action;

	private List<String> packageType = new ArrayList<>();

	private String priceEstablishedLabel;

	private List<String> footnotes = new ArrayList<>();

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

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getConfirmRequired() {
		return confirmRequired;
	}

	public void setConfirmRequired(String confirmRequired) {
		this.confirmRequired = confirmRequired;
	}

	public String getProductPath() {
		return productPath;
	}

	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getPackageType() {
		return packageType;
	}

	public void setPackageType(List<String> packageType) {
		this.packageType = packageType;
	}

	public String getPriceEstablishedLabel() {
		return priceEstablishedLabel;
	}

	public void setPriceEstablishedLabel(String priceEstablishedLabel) {
		this.priceEstablishedLabel = priceEstablishedLabel;
	}

	public List<String> getFootnotes() {
		return footnotes;
	}

	public void setFootnotes(List<String> footnotes) {
		this.footnotes = footnotes;
	}

}
