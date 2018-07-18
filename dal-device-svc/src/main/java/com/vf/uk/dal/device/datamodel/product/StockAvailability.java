package com.vf.uk.dal.device.datamodel.product;

import java.sql.Timestamp;

public class StockAvailability {

	private String skuId;

	private String sourceId;

	private int quantity;

	private String status;

	private String availableBy;

	private Timestamp lastModified;

	private int orderLimit;

	public String getSkuId() {
		return this.skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvailableBy() {
		return this.availableBy;
	}

	public void setAvailableBy(String availableBy) {
		this.availableBy = availableBy;
	}

	public Timestamp getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public int getOrderLimit() {
		return this.orderLimit;
	}

	public void setOrderLimit(int orderLimit) {
		this.orderLimit = orderLimit;
	}

	public String getId() {
		return this.skuId;
	}
}
