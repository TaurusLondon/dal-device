package com.vf.uk.dal.device.datamodel.product;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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

	private Long order;
	private boolean preOrderable;
	private Timestamp availableFrom;
	private boolean backOrderable;
	private boolean affiliateExport;
	private String compareWith;
	private String backOrderMessage;
}
