package com.vf.uk.dal.utility.entity;

import lombok.Data;

/**
 * 
 * StockInfo
 *
 */
@Data
public class StockInfo {

	private String skuId = null;

	private String sourceId = null;

	private Integer quantity = null;

	private String status = null;

	private String availableBy = null;
}
