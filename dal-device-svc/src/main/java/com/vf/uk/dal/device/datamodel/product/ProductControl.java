package com.vf.uk.dal.device.datamodel.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductControl {

	private boolean isDisplayableinLife;
	private boolean isSellableinLife;
	private boolean isDisplayableAcq;
	private boolean isSellableRet;
	private boolean isDisplayableRet;
	private boolean isSellableAcq;
	private boolean isDisplayableSavedBasket;

	private Long order;
	private boolean preOrderable;
	private Timestamp availableFrom;
	private boolean backOrderable;
	private boolean affiliateExport;
	private String compareWith;
	private String backOrderMessage;
}
