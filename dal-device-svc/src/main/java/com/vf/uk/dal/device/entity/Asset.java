package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;

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

	private List<Attributes> attributes = new ArrayList<Attributes>();

	public String getPrivateInstalledProductId() {
		return privateInstalledProductId;
	}

	public void setPrivateInstalledProductId(String privateInstalledProductId) {
		this.privateInstalledProductId = privateInstalledProductId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAssetIntegrationId() {
		return assetIntegrationId;
	}

	public void setAssetIntegrationId(String assetIntegrationId) {
		this.assetIntegrationId = assetIntegrationId;
	}

	public String getMsisdnId() {
		return msisdnId;
	}

	public void setMsisdnId(String msisdnId) {
		this.msisdnId = msisdnId;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPrivateRootInstalledProductId() {
		return privateRootInstalledProductId;
	}

	public void setPrivateRootInstalledProductId(String privateRootInstalledProductId) {
		this.privateRootInstalledProductId = privateRootInstalledProductId;
	}

	public String getPrivateParentInstalledProductId() {
		return privateParentInstalledProductId;
	}

	public void setPrivateParentInstalledProductId(String privateParentInstalledProductId) {
		this.privateParentInstalledProductId = privateParentInstalledProductId;
	}

	public String getCustomerPartyAccountName() {
		return customerPartyAccountName;
	}

	public void setCustomerPartyAccountName(String customerPartyAccountName) {
		this.customerPartyAccountName = customerPartyAccountName;
	}

	public String getServiceAccountId() {
		return serviceAccountId;
	}

	public void setServiceAccountId(String serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
	}

	public Asset owningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
		return this;
	}

	public String getOwningAccountLocationName() {
		return owningAccountLocationName;
	}

	public void setOwningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
	}

	public String getOwningAccountFullName() {
		return owningAccountFullName;
	}

	public void setOwningAccountFullName(String owningAccountFullName) {
		this.owningAccountFullName = owningAccountFullName;
	}

	public String getOwningAccountId() {
		return owningAccountId;
	}

	public void setOwningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
	}

	public String getOwningContactFirstName() {
		return owningContactFirstName;
	}

	public void setOwningContactFirstName(String owningContactFirstName) {
		this.owningContactFirstName = owningContactFirstName;
	}

	public String getOwningContactFamilyName() {
		return owningContactFamilyName;
	}

	public void setOwningContactFamilyName(String owningContactFamilyName) {
		this.owningContactFamilyName = owningContactFamilyName;
	}

	public String getOwningAccountContactId() {
		return owningAccountContactId;
	}

	public void setOwningAccountContactId(String owningAccountContactId) {
		this.owningAccountContactId = owningAccountContactId;
	}

	public String getBillingAccountFullName() {
		return billingAccountFullName;
	}

	public void setBillingAccountFullName(String billingAccountFullName) {
		this.billingAccountFullName = billingAccountFullName;
	}

	public String getBillingAccountId() {
		return billingAccountId;
	}

	public void setBillingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
	}

	public String getBillingProfileId() {
		return billingProfileId;
	}

	public void setBillingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
	}

	public String getBillingProfileType() {
		return billingProfileType;
	}

	public void setBillingProfileType(String billingProfileType) {
		this.billingProfileType = billingProfileType;
	}

	public String getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPrimaryContactId() {
		return primaryContactId;
	}

	public void setPrimaryContactId(String primaryContactId) {
		this.primaryContactId = primaryContactId;
	}

	public String getAssociatedEmployee() {
		return associatedEmployee;
	}

	public void setAssociatedEmployee(String associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
	}

	public Double getTrackingQuantity() {
		return trackingQuantity;
	}

	public void setTrackingQuantity(Double trackingQuantity) {
		this.trackingQuantity = trackingQuantity;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusEffectiveDate() {
		return statusEffectiveDate;
	}

	public void setStatusEffectiveDate(String statusEffectiveDate) {
		this.statusEffectiveDate = statusEffectiveDate;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPublicInstalledProductId() {
		return publicInstalledProductId;
	}

	public void setPublicInstalledProductId(String publicInstalledProductId) {
		this.publicInstalledProductId = publicInstalledProductId;
	}

	public String getHeaderChildAssetId() {
		return headerChildAssetId;
	}

	public void setHeaderChildAssetId(String headerChildAssetId) {
		this.headerChildAssetId = headerChildAssetId;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public String getAlternateTrackingNumber() {
		return alternateTrackingNumber;
	}

	public void setAlternateTrackingNumber(String alternateTrackingNumber) {
		this.alternateTrackingNumber = alternateTrackingNumber;
	}

	public String getInventoryLocationReference() {
		return inventoryLocationReference;
	}

	public void setInventoryLocationReference(String inventoryLocationReference) {
		this.inventoryLocationReference = inventoryLocationReference;
	}

	public String getSalesOrderRef() {
		return salesOrderRef;
	}

	public void setSalesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}

	public String getManufacturedDate() {
		return manufacturedDate;
	}

	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}

	public String getInstalledProductName() {
		return installedProductName;
	}

	public void setInstalledProductName(String installedProductName) {
		this.installedProductName = installedProductName;
	}

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}

	public String getUomCode() {
		return uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	public Asset attributes(List<Attributes> attributes) {
		this.attributes = attributes;
		return this;
	}

	public List<Attributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attributes> attributes) {
		this.attributes = attributes;
	}

}
