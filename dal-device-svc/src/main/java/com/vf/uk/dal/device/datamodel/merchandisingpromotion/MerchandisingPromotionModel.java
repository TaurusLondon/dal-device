package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
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



}
