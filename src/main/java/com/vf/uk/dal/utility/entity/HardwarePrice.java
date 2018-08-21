package com.vf.uk.dal.utility.entity;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * HardwarePrice
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-17T05:45:22.340Z")
@Data
public class HardwarePrice {
	@JsonProperty("hardwareId")
	private String hardwareId = null;

	@JsonProperty("oneOffPrice")
	private Price oneOffPrice = null;

	@JsonProperty("oneOffDiscountPrice")
	private Price oneOffDiscountPrice = null;

	@JsonProperty("merchandisingPromotions")
	private MerchandisingPromotion merchandisingPromotions = null;

	@JsonProperty("financingOptions")
	@Valid
	private List<DeviceFinancingOption> financingOptions = null;
}
