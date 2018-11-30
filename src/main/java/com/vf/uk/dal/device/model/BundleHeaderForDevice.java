package com.vf.uk.dal.device.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;

import lombok.Data;

/**
 * BundleHeaderForDevice
 */
@Data
public class BundleHeaderForDevice {
	@JsonProperty("skuId")
	private String skuId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("bundleName")
	private String bundleName = null;

	@JsonProperty("bundleDescription")
	private String bundleDescription = null;

	@JsonProperty("bundleClass")
	private String bundleClass = null;

	@JsonProperty("paymentType")
	private String paymentType = null;

	@JsonProperty("bundleType")
	private String bundleType = null;

	@JsonProperty("commitmentPeriod")
	private String commitmentPeriod = null;

	@JsonProperty("allowance")
	private List<BundleAllowance> allowance = new ArrayList<>();

	@JsonProperty("roamingAllowance")
	private List<BundleAllowance> roamingAllowance = new ArrayList<>();

	@JsonProperty("merchandisingMedia")
	private List<MediaLink> merchandisingMedia = new ArrayList<>();

	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

}
