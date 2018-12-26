package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.Date;

import lombok.Data;

/**
 * PriceDetail
 * @author manoj.bera
 *
 */
@Data
public class PriceDetail {

	/** priceType */
	private String priceType;

	/** isInvoiceChargeable */
	private Boolean isInvoiceChargeable; 

	/** priceNet */
	private Double priceNet;

	/** priceGross */
	private Double priceGross;

	/** priceVAT */
	private Double priceVAT;

	/** vatCode */
	private Double vatCode;

	/** priceStartDate */
	private Date priceStartDate;

	/** priceEndDate */
	private Date priceEndDate;

	/** recurrence */
	private String recurrence;
}
