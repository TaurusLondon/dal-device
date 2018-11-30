package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MerchandisingControl
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")
@Data
public class MerchandisingControl {
	@JsonProperty("isDisplayableECare")
	private Boolean isDisplayableECare = null;

	@JsonProperty("isSellableECare")
	private Boolean isSellableECare = null;

	@JsonProperty("isDisplayableAcq")
	private Boolean isDisplayableAcq = null;

	@JsonProperty("isSellableRet")
	private Boolean isSellableRet = null;

	@JsonProperty("isDisplayableRet")
	private Boolean isDisplayableRet = null;

	@JsonProperty("isSellableAcq")
	private Boolean isSellableAcq = null;

	@JsonProperty("isDisplayableSavedBasket")
	private Boolean isDisplayableSavedBasket = null;

	@JsonProperty("order")
	private Integer order = null;

	@JsonProperty("preorderable")
	private Boolean preorderable = null;

	@JsonProperty("availableFrom")
	private String availableFrom = null;

	@JsonProperty("backorderable")
	private Boolean backorderable = null;
}
