package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import lombok.Data;

@Data
public class MerchandisingProduct {

	private String promotionId;

	private String productLine;

	private String productPath;

	private String action;

	private List<DiscountMP> discounts;

	public MerchandisingProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
