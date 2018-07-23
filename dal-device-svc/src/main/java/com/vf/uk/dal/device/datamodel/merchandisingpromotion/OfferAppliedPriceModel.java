package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption;

import lombok.Data;
@Data
public class OfferAppliedPriceModel {

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

	private List<DeviceFinancingOption> financingOptions = null;
}
