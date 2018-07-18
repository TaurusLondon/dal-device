package com.vf.uk.dal.utility.entity;

/**
 * 
 * StockInfo
 *
 */
public class StockInfo {

	private String skuId = null;

	private String sourceId = null;

	private Integer quantity = null;

	private String status = null;

	private String availableBy = null;

	public String getSkuId() {
		return skuId;
	}

	/**
	 * 
	 * @param skuId
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * 
	 * @return
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * 
	 * @param sourceId
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 */
	public String getAvailableBy() {
		return availableBy;
	}

	/**
	 * 
	 * @param availableBy
	 */
	public void setAvailableBy(String availableBy) {
		this.availableBy = availableBy;
	}

}
