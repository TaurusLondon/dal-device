package com.vf.uk.dal.device.datamodel.product;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * PriceDetail
 * @author manoj.bera
 *
 */
@Data
public class PriceDetail {

	@JsonProperty("priceType")
	private String priceType;

	@JsonProperty("isInvoiceChargeable")
	private boolean isInvoiceChargeable;

	@JsonProperty("priceNet")
	private Double priceNet;

	@JsonProperty("priceGross")
	private Double priceGross;

	@JsonProperty("priceVAT")
	private Double priceVAT;

	@JsonProperty("vatCode")
	private Double vatCode;

	@JsonProperty("priceStartDate")
	private Date priceStartDate;

	@JsonProperty("priceEndDate")
	private Date priceEndDate;

	@JsonProperty("recurrence")
	private String recurrence;
}
