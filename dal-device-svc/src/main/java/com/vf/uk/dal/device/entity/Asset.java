package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * Asset
 *
 */
@Data
public class Asset {
	private String privateInstalledProductId = null;

	private String productName = null;

	private String assetIntegrationId = null;

	private String msisdnId = null;

	private String productDescription = null;

	private String productId = null;

	private String privateRootInstalledProductId = null;

	private String privateParentInstalledProductId = null;

	private String customerPartyAccountName = null;

	private String serviceAccountId = null;

	private String owningAccountLocationName = null;

	private String owningAccountFullName = null;

	private String owningAccountId = null;

	private String owningContactFirstName = null;

	private String owningContactFamilyName = null;

	private String owningAccountContactId = null;

	private String billingAccountFullName = null;

	private String billingAccountId = null;

	private String billingProfileId = null;

	private String billingProfileType = null;

	private String installationDate = null;

	private String startDate = null;

	private String endDate = null;

	private String primaryContactId = null;

	private String associatedEmployee = null;

	private Double trackingQuantity = null;

	private String registrationDate = null;

	private String serialNumber = null;

	private String status = null;

	private String statusEffectiveDate = null;

	private String promotionId = null;

	private String publicInstalledProductId = null;

	private String headerChildAssetId = null;

	private String agreementId = null;

	private String alternateTrackingNumber = null;

	private String inventoryLocationReference = null;

	private String salesOrderRef = null;

	private Double price = null;

	private String priceType = null;

	private String priceCategory = null;

	private String manufacturedDate = null;

	private String installedProductName = null;

	private Double productCost = null;

	private String uomCode = null;

	private List<Attributes> attributes = new ArrayList<>();

}
