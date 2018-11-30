package com.vf.uk.dal.device.model.merchandisingpromotion;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * MerchandisingPromotionModel
 * @author manoj.bera
 *
 */
@Data
public class MerchandisingPromotionModel {
	@JsonProperty("tag")
	private String tag;
	@JsonProperty("label")
	private String label;
	@JsonProperty("startDateTime")
	private String startDateTime;
	@JsonProperty("endDateTime")
	private String endDateTime;
	@JsonProperty("confirmRequired")
	private String confirmRequired;
	@JsonProperty("productPath")
	private String productPath;
	@JsonProperty("objectType")
	private String objectType;

	@JsonProperty("desc")
	private String description;
	@JsonProperty("promotionMedia")
	private String promotionMedia;
	@JsonProperty("priority")
	private Integer priority;
	@JsonProperty("promotionId")
	private String promotionId;
	@JsonProperty("productLine")
	private String productLine;
	@JsonProperty("action")
	private String action;
	@JsonProperty("packageType")
	private List<String> packageType = new ArrayList<>();
	@JsonProperty("priceEstablishedLabel")
	private String priceEstablishedLabel;
	@JsonProperty("footnotes")
	private List<String> footnotes = new ArrayList<>();
}
