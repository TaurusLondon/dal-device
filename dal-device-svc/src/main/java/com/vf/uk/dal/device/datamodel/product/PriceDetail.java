package com.vf.uk.dal.device.datamodel.product;

import java.sql.Date;

import lombok.Data;

@Data
public class PriceDetail {

	private String priceType;

	private boolean isInvoiceChargeable;

	private Double priceNet;

	private Double priceGross;

	private Double priceVAT;

	private Double vatCode;

	private Date priceStartDate;

	private Date priceEndDate;

	private String recurrence;
}
