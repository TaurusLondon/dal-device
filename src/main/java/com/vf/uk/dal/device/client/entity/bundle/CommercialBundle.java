package com.vf.uk.dal.device.client.entity.bundle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * CommercialBundle
 *
 */
@Data
public class CommercialBundle {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("desc")
	private String desc;

	@JsonProperty("paymentType")
	private String paymentType;

	@JsonProperty("availability")
	private Availability availability;

	@JsonProperty("commitment")
	private Commitment commitment;

	@JsonProperty("productLines")
	private List<String> productLines;

	@JsonProperty("deviceSpecificPricing")
	private List<DevicePrice> deviceSpecificPricing;

	@JsonProperty("serviceProducts")
	private List<ServiceProduct> serviceProducts;

	@JsonProperty("allowances")
	private List<Allowance> allowances;

	@JsonProperty("recurringCharge")
	private Float recurringCharge;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("listOfimageURLs")
	private List<ImageURL> listOfimageURLs;

	@JsonProperty("specificationGroups")
	private List<Group> specificationGroups;

	@JsonProperty("bundleControl")
	private BundleControl bundleControl;

	@JsonProperty("promoteAs")
	private PromoteAs promoteAs;

	@JsonProperty("displayGroup")
	private String displayGroup;

}
