package com.vf.uk.dal.utility.solr.entity;

import lombok.Data;

/**
 * 
 * BundlePrice
 *
 */
@Data
public class BundlePrice {
	private String bundleId;

	private MonthlyPrice monthlyPrice;

	private MonthlyDiscountPrice monthlyDiscountPrice;

}