package com.vf.uk.dal.utility.solr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * MerchandisingControl
 *
 */
@Data
public class MerchandisingControl {
	@JsonProperty("isDisplayableECare")
	private String isDisplayableECare;
	@JsonProperty("isSellableECare")
	private String isSellableECare;
	@JsonProperty("isDisplayableAcq")
	private String isDisplayableAcq;
	@JsonProperty("isSellableRet")
	private String isSellableRet;
	@JsonProperty("isDisplayableRet")
	private String isDisplayableRet;
	@JsonProperty("isSellableAcq")
	private String isSellableAcq;
	@JsonProperty("isDisplayableSavedBasket")
	private String isDisplayableSavedBasket;
	@JsonProperty("order")
	private String order;
	@JsonProperty("preorderable")
	private String preorderable;
	@JsonProperty("availableFrom")
	private String availableFrom;
	@JsonProperty("backorderable")
	private String backorderable;

}