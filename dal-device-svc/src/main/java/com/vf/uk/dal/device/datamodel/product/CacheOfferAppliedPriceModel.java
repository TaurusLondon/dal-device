package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import lombok.Data;

@Data
public class CacheOfferAppliedPriceModel {

	private String id;

	private String productId;

	private String offerCode;

	private String bundleId;

	private Float monthlyGrossPrice;

	private Float monthlyNetPrice;

	private Float monthlyVatPrice;

	private Float monthlyDiscountedGrossPrice;

	private Float monthlyDiscountedNetPrice;

	private Float monthlyDiscountedVatPrice;

	private String hardwareId;

	private Float oneOffGrossPrice;

	private Float oneOffNetPrice;

	private Float oneOffVatPrice;

	private Float oneOffDiscountedGrossPrice;

	private Float oneOffDiscountedNetPrice;

	private Float oneOffDiscountedVatPrice;

	private String journeyType;

	private List<DeviceFinancingOption> financingOptions = null;

}
