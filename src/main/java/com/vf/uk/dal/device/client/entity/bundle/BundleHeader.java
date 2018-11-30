package com.vf.uk.dal.device.client.entity.bundle;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;

import lombok.Data;

/**
 * BundleHeader.
 */
@Data
public class BundleHeader {

	/** The sku id. */
	@JsonProperty("skuId")
	private String skuId = null;

	/** The name. */
	@JsonProperty("name")
	private String name = null;

	/** The description. */
	@JsonProperty("description")
	private String description = null;

	/** The bundle name. */
	@JsonProperty("bundleName")
	private String bundleName = null;

	/** The bundle description. */
	@JsonProperty("bundleDescription")
	private String bundleDescription = null;

	/** The bundle class. */
	@JsonProperty("bundleClass")
	private String bundleClass = null;

	/** The product class. */
	@JsonProperty("productClass")
	private String productClass = null;

	/** The payment type. */
	@JsonProperty("paymentType")
	private String paymentType = null;

	/** The bundle type. */
	@JsonProperty("bundleType")
	private String bundleType = null;

	/** The plan couple id. */
	@JsonProperty("planCoupleId")
	private String planCoupleId = null;

	/** The plan couple flag. */
	@JsonProperty("planCoupleFlag")
	private Boolean planCoupleFlag = null;

	/** The plan couple lable. */
	@JsonProperty("planCoupleLable")
	private String planCoupleLable = null;

	/** The global roaming flag. */
	@JsonProperty("globalRoamingFlag")
	private Boolean globalRoamingFlag = null;

	/** The Secure Net */
	@JsonProperty("secureNetFlag")
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
