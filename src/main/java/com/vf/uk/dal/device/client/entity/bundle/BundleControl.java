package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleControl
 * @author manoj.bera
 *
 */
@Data
public class BundleControl {

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
	
	@JsonProperty("affiliateExport")
	private boolean affiliateExport;
	
	@JsonProperty("compareWith")
	private String compareWith;
}
