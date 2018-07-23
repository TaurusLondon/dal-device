package com.vf.uk.dal.utility.solr.entity;

import lombok.Data;

/**
 * 
 * MonthlyPrice
 *
 */
@Data
public class MonthlyPrice {
	private String gross;

	private String net;

	private String vat;
}