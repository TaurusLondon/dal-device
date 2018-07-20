package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * BundleHeader.
 */
@Data
public class BundleHeader {

	/** The sku id. */
	private String skuId = null;

	/** The name. */
	private String name = null;

	/** The description. */
	private String description = null;

	/** The bundle name. */
	private String bundleName = null;

	/** The bundle description. */
	private String bundleDescription = null;

	/** The bundle class. */
	private String bundleClass = null;

	/** The product class. */
	private String productClass = null;

	/** The payment type. */
	private String paymentType = null;

	/** The bundle type. */
	private String bundleType = null;

	/** The plan couple id. */
	private String planCoupleId = null;

	/** The plan couple flag. */
	private Boolean planCoupleFlag = null;

	/** The plan couple lable. */
	private String planCoupleLable = null;

	/** The global roaming flag. */
	private Boolean globalRoamingFlag = null;

	/** The Secure Net */
	private Boolean secureNetFlag = null;

	

	/** The commitment period. */
	private String commitmentPeriod = null;

	/** The mobile line rental id. */
	private String mobileLineRentalId = null;

	/** The mobile service id. */
	private String mobileServiceId = null;

	/** The allowance. */
	private List<BundleAllowance> allowance = new ArrayList<>();

	/** The roaming allowance. */
	private List<BundleAllowance> roamingAllowance = new ArrayList<>();

	/** The merchandising media. */
	private List<MediaLink> merchandisingMedia = new ArrayList<>();

	/** The price info. */
	private PriceForBundleAndHardware priceInfo = null;

	/** The mcs. */
	private List<MonthlyCostSaver> mcs = new ArrayList<>();

	

}
