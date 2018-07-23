package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import lombok.Data;

@Data
public class BundlePrice {

	private String bundleId;
	private MonthlyPrice monthlyPrice;
	private MonthlyDiscountPrice monthlyDiscountPrice;

	

}
