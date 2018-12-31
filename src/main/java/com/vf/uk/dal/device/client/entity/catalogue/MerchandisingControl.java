package com.vf.uk.dal.device.client.entity.catalogue;

import lombok.Data;

/**
 * MerchandisingControl
 */

@Data
public class MerchandisingControl {
	
	/** Is Display able ECare*/
	private Boolean isDisplayableECare;

	/** Is Sell able ECare */
	private Boolean isSellableECare;

	/**Is Display able Acq */
	private Boolean isDisplayableAcq ;

	/** Is Sell able Ret */
	private Boolean isSellableRet;

	/** Is Display able Ret*/
	private Boolean isDisplayableRet;

	/** Is Sell able Acq*/
	private Boolean isSellableAcq;

	/** Is Display able Saved Basket*/
	private Boolean isDisplayableSavedBasket;

	/** Order */
	private Integer order;

	/** PreOrder From*/
	private Boolean preorderable;

	/** available From*/
	private String availableFrom;

	/** back order able */ 
	private Boolean backorderable;
}
