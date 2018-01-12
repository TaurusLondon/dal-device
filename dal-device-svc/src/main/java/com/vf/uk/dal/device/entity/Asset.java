package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * Asset
 *
 */
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

	/**
	 * 
	 * @return
	 */
	public String getPrivateInstalledProductId() {
		return privateInstalledProductId;
	}

	/**
	 * 
	 * @param privateInstalledProductId
	 */
	public void setPrivateInstalledProductId(String privateInstalledProductId) {
		this.privateInstalledProductId = privateInstalledProductId;
	}

	/**
	 * 
	 * @return
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 
	 * @return
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 
	 * @return
	 */
	public String getAssetIntegrationId() {
		return assetIntegrationId;
	}
	/**
	 * 
	 * @return
	 */
	public void setAssetIntegrationId(String assetIntegrationId) {
		this.assetIntegrationId = assetIntegrationId;
	}
	/**
	 * 
	 * @return
	 */
	public String getMsisdnId() {
		return msisdnId;
	}
	/**
	 * 
	 * @return
	 */
	public void setMsisdnId(String msisdnId) {
		this.msisdnId = msisdnId;
	}
	/**
	 * 
	 * @return
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * 
	 * @return
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * 
	 * @return
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 
	 * @return
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrivateRootInstalledProductId() {
		return privateRootInstalledProductId;
	}
	/**
	 * 
	 * @return
	 */
	public void setPrivateRootInstalledProductId(String privateRootInstalledProductId) {
		this.privateRootInstalledProductId = privateRootInstalledProductId;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrivateParentInstalledProductId() {
		return privateParentInstalledProductId;
	}
	/**
	 * 
	 * @return
	 */
	public void setPrivateParentInstalledProductId(String privateParentInstalledProductId) {
		this.privateParentInstalledProductId = privateParentInstalledProductId;
	}
	/**
	 * 
	 * @return
	 */
	public String getCustomerPartyAccountName() {
		return customerPartyAccountName;
	}
	/**
	 * 
	 * @return
	 */
	public void setCustomerPartyAccountName(String customerPartyAccountName) {
		this.customerPartyAccountName = customerPartyAccountName;
	}
	/**
	 * 
	 * @return
	 */
	public String getServiceAccountId() {
		return serviceAccountId;
	}
	/**
	 * 
	 * @return
	 */
	public void setServiceAccountId(String serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
	}
	/**
	 * 
	 * @return
	 */
	public Asset owningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
		return this;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwningAccountLocationName() {
		return owningAccountLocationName;
	}
	/**
	 * 
	 * @return
	 */
	public void setOwningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwningAccountFullName() {
		return owningAccountFullName;
	}
	/**
	 * 
	 * @return
	 */
	public void setOwningAccountFullName(String owningAccountFullName) {
		this.owningAccountFullName = owningAccountFullName;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwningAccountId() {
		return owningAccountId;
	}
	/**
	 * 
	 * @return
	 */
	public void setOwningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwningContactFirstName() {
		return owningContactFirstName;
	}
	/**
	 * 
	 * @return
	 */
	public void setOwningContactFirstName(String owningContactFirstName) {
		this.owningContactFirstName = owningContactFirstName;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwningContactFamilyName() {
		return owningContactFamilyName;
	}
	/**
	 * 
	 * @return
	 */
	public void setOwningContactFamilyName(String owningContactFamilyName) {
		this.owningContactFamilyName = owningContactFamilyName;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwningAccountContactId() {
		return owningAccountContactId;
	}
	/**
	 * 
	 * @return
	 */
	public void setOwningAccountContactId(String owningAccountContactId) {
		this.owningAccountContactId = owningAccountContactId;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillingAccountFullName() {
		return billingAccountFullName;
	}
	/**
	 * 
	 * @return
	 */
	public void setBillingAccountFullName(String billingAccountFullName) {
		this.billingAccountFullName = billingAccountFullName;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillingAccountId() {
		return billingAccountId;
	}
	/**
	 * 
	 * @return
	 */
	public void setBillingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillingProfileId() {
		return billingProfileId;
	}
	/**
	 * 
	 * @return
	 */
	public void setBillingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillingProfileType() {
		return billingProfileType;
	}
	/**
	 * 
	 * @return
	 */
	public void setBillingProfileType(String billingProfileType) {
		this.billingProfileType = billingProfileType;
	}
	/**
	 * 
	 * @return
	 */
	public String getInstallationDate() {
		return installationDate;
	}
	/**
	 * 
	 * @return
	 */
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * 
	 * @return
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * 
	 * @return
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrimaryContactId() {
		return primaryContactId;
	}
	/**
	 * 
	 * @return
	 */
	public void setPrimaryContactId(String primaryContactId) {
		this.primaryContactId = primaryContactId;
	}
	/**
	 * 
	 * @return
	 */
	public String getAssociatedEmployee() {
		/**
		 * 
		 * @return
		 */	return associatedEmployee;
	}
	/**
	 * 
	 * @return
	 */
	public void setAssociatedEmployee(String associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
	}
	/**
	 * 
	 * @return
	 */
	public Double getTrackingQuantity() {
		return trackingQuantity;
	}
	/**
	 * 
	 * @return
	 */
	public void setTrackingQuantity(Double trackingQuantity) {
		this.trackingQuantity = trackingQuantity;
	}
	/**
	 * 
	 * @return
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * 
	 * @return
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * 
	 * @return
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @return
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatusEffectiveDate() {
		return statusEffectiveDate;
	}
	/**
	 * 
	 * @return
	 */
	public void setStatusEffectiveDate(String statusEffectiveDate) {
		this.statusEffectiveDate = statusEffectiveDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getPromotionId() {
		return promotionId;
	}
	/**
	 * 
	 * @return
	 */
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	/**
	 * 
	 * @return
	 */
	public String getPublicInstalledProductId() {
		return publicInstalledProductId;
	}
	/**
	 * 
	 * @return
	 */
	public void setPublicInstalledProductId(String publicInstalledProductId) {
		this.publicInstalledProductId = publicInstalledProductId;
	}
	/**
	 * 
	 * @return
	 */
	public String getHeaderChildAssetId() {
		return headerChildAssetId;
	}
	/**
	 * 
	 * @return
	 */
	public void setHeaderChildAssetId(String headerChildAssetId) {
		this.headerChildAssetId = headerChildAssetId;
	}
	/**
	 * 
	 * @return
	 */
	public String getAgreementId() {
		return agreementId;
	}
	/**
	 * 
	 * @return
	 */
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	/**
	 * 
	 * @return
	 */
	public String getAlternateTrackingNumber() {
		return alternateTrackingNumber;
	}
	/**
	 * 
	 * @return
	 */
	public void setAlternateTrackingNumber(String alternateTrackingNumber) {
		this.alternateTrackingNumber = alternateTrackingNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getInventoryLocationReference() {
		return inventoryLocationReference;
	}
	/**
	 * 
	 * @return
	 */
	public void setInventoryLocationReference(String inventoryLocationReference) {
		this.inventoryLocationReference = inventoryLocationReference;
	}
	/**
	 * 
	 * @return
	 */
	public String getSalesOrderRef() {
		return salesOrderRef;
	}
	/**
	 * 
	 * @return
	 */
	public void setSalesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
	}
	/**
	 * 
	 * @return
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 
	 * @return
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 
	 * @return
	 */
	public String getPriceType() {
		return priceType;
	}
	/**
	 * 
	 * @return
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	/**
	 * 
	 * @return
	 */
	public String getPriceCategory() {
		return priceCategory;
	}
	/**
	 * 
	 * @return
	 */
	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}
	/**
	 * 
	 * @return
	 */
	public String getManufacturedDate() {
		return manufacturedDate;
	}
	/**
	 * 
	 * @return
	 */
	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getInstalledProductName() {
		return installedProductName;
	}
	/**
	 * 
	 * @return
	 */
	public void setInstalledProductName(String installedProductName) {
		this.installedProductName = installedProductName;
	}
	/**
	 * 
	 * @return
	 */
	public Double getProductCost() {
		return productCost;
	}
	/**
	 * 
	 * @return
	 */
	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}
	/**
	 * 
	 * @return
	 */
	public String getUomCode() {
		return uomCode;
	}
	/**
	 * 
	 * @return
	 */
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}
	/**
	 * 
	 * @return
	 */
	public Asset attributes(List<Attributes> attributes) {
		this.attributes = attributes;
		return this;
	}
	/**
	 * 
	 * @return
	 */
	public List<Attributes> getAttributes() {
		return attributes;
	}
	/**
	 * 
	 * @return
	 */
	public void setAttributes(List<Attributes> attributes) {
		this.attributes = attributes;
	}

}
