package com.vf.uk.dal.utility.solr.entity;

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
