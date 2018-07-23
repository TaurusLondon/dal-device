package com.vf.uk.dal.utility.solr.entity;

import lombok.Data;

/**
 * 
 * MerchandisingControl
 *
 */
@Data
public class MerchandisingControl {
	private String isDisplayableECare;

	private String isSellableECare;

	private String isDisplayableAcq;

	private String isSellableRet;

	private String isDisplayableRet;

	private String isSellableAcq;

	private String isDisplayableSavedBasket;

	private String order;

	private String preorderable;

	private String availableFrom;

	private String backorderable;

}