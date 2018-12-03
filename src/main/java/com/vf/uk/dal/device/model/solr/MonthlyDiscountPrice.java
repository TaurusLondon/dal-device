package com.vf.uk.dal.device.model.solr;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * MonthlyDiscountPrice
 *
 */
@Data
public class MonthlyDiscountPrice {
	@JsonProperty("gross")
	private String gross;
	@JsonProperty("net")
	private String net;
	@JsonProperty("vat")
	private String vat;
}
