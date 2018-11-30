package com.vf.uk.dal.device.model.product;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * ProductControl
 * @author manoj.bera
 *
 */
@Data
public class ProductControl {

	@JsonProperty("isDisplayableinLife")
	private boolean isDisplayableinLife;
	@JsonProperty("isSellableinLife")
	private boolean isSellableinLife;
	@JsonProperty("isDisplayableAcq")
	private boolean isDisplayableAcq;
	@JsonProperty("isSellableRet")
	private boolean isSellableRet;
	@JsonProperty("isDisplayableRet")
	private boolean isDisplayableRet;
	@JsonProperty("isSellableAcq")
	private boolean isSellableAcq;
	@JsonProperty("isDisplayableSavedBasket")
	private boolean isDisplayableSavedBasket;

	@JsonProperty("order")
	private Long order;
	@JsonProperty("preOrderable")
	private boolean preOrderable;
	@JsonProperty("availableFrom")
	private Timestamp availableFrom;
	@JsonProperty("backOrderable")
	private boolean backOrderable;
	@JsonProperty("affiliateExport")
	private boolean affiliateExport;
	@JsonProperty("compareWith")
	private String compareWith;
	@JsonProperty("backOrderMessage")
	private String backOrderMessage;
}
