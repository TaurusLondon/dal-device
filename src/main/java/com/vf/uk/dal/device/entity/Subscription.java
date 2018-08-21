package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Subscription
 * 
 *
 */
@Data
public class Subscription {

	@JsonProperty("privateInstalledProductId")
	private String privateInstalledProductId = null;

	@JsonProperty("productName")
	private String productName = null;

	@JsonProperty("assetIntegrationId")
	private String assetIntegrationId = null;

	@JsonProperty("msisdnId")
	private String msisdnId = null;

	@JsonProperty("productDescription")
	private String productDescription = null;

	@JsonProperty("productId")
	private String productId = null;

	@JsonProperty("privateRootInstalledProductId")
	private String privateRootInstalledProductId = null;

	@JsonProperty("privateParentInstalledProductId")
	private String privateParentInstalledProductId = null;

	@JsonProperty("customerPartyAccountName")
	private String customerPartyAccountName = null;

	@JsonProperty("serviceAccountId")
	private String serviceAccountId = null;

	@JsonProperty("owningAccountLocationName")
	private String owningAccountLocationName = null;

	@JsonProperty("owningAccountFullName")
	private String owningAccountFullName = null;

	@JsonProperty("owningAccountId")
	private String owningAccountId = null;

	@JsonProperty("owningContactFirstName")
	private String owningContactFirstName = null;

	@JsonProperty("owningContactFamilyName")
	private String owningContactFamilyName = null;

	@JsonProperty("owningAccountContactId")
	private String owningAccountContactId = null;

	@JsonProperty("billingAccountFullName")
	private String billingAccountFullName = null;

	@JsonProperty("billingAccountId")
	private String billingAccountId = null;

	@JsonProperty("billingProfileId")
	private String billingProfileId = null;

	@JsonProperty("billingProfileType")
	private String billingProfileType = null;

	@JsonProperty("installationDate")
	private String installationDate = null;

	@JsonProperty("startDate")
	private String startDate = null;

	@JsonProperty("endDate")
	private String endDate = null;

	@JsonProperty("primaryContactId")
	private String primaryContactId = null;

	@JsonProperty("associatedEmployee")
	private String associatedEmployee = null;

	@JsonProperty("trackingQuantity")
	private Double trackingQuantity = null;

	@JsonProperty("registrationDate")
	private String registrationDate = null;

	@JsonProperty("serialNumber")
	private String serialNumber = null;

	@JsonProperty("status")
	private String status = null;

	@JsonProperty("statusEffectiveDate")
	private String statusEffectiveDate = null;

	@JsonProperty("promotionId")
	private String promotionId = null;

	@JsonProperty("publicInstalledProductId")
	private String publicInstalledProductId = null;

	@JsonProperty("headerChildAssetId")
	private String headerChildAssetId = null;

	@JsonProperty("agreementId")
	private String agreementId = null;

	@JsonProperty("alternateTrackingNumber")
	private String alternateTrackingNumber = null;

	@JsonProperty("inventoryLocationReference")
	private String inventoryLocationReference = null;

	@JsonProperty("salesOrderRef")
	private String salesOrderRef = null;

	@JsonProperty("price")
	private Double price = null;

	@JsonProperty("priceType")
	private String priceType = null;

	@JsonProperty("priceCategory")
	private String priceCategory = null;

	@JsonProperty("manufacturedDate")
	private String manufacturedDate = null;

	@JsonProperty("installedProductName")
	private String installedProductName = null;

	@JsonProperty("productCost")
	private Double productCost = null;

	@JsonProperty("uomCode")
	private String uomCode = null;

	@JsonProperty("asset")
	private List<Asset> asset = new ArrayList<>();

	@JsonProperty("isInFlightOrderPresent")
	private Boolean isInFlightOrderPresent = null;

}
