package com.vf.uk.dal.utility.entity;

public class StockInfo   {

	  private String skuId = null;

	  private String sourceId = null;

	  private Integer quantity = null;

	  private String status = null;

	  private String availableBy = null;

	  public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvailableBy() {
		return availableBy;
	}

	public void setAvailableBy(String availableBy) {
		this.availableBy = availableBy;
	}


	   
	}

