package com.vf.uk.dal.device.model.solr;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * MonthlyPrice
 *
 */
@Data
public class MonthlyPrice {
	@JsonProperty("gross")
	private String gross;

	@JsonProperty("net")
	private String net;

	@JsonProperty("vat")
	private String vat;
}