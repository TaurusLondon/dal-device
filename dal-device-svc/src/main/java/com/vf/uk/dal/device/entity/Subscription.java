package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * Subscription
 * 
 *
 */
public class Subscription {
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

	private List<Asset> asset = new ArrayList<Asset>();

	private Boolean isInFlightOrderPresent = null;
/**
 * 
 * @return
 */
	public Boolean getIsInFlightOrderPresent() {
		return isInFlightOrderPresent;
	}
/**
 * 
 * @param isInFlightOrderPresent
 */
	public void setIsInFlightOrderPresent(Boolean isInFlightOrderPresent) {
		this.isInFlightOrderPresent = isInFlightOrderPresent;
	}
/**
 * 
 * @param isInFlightOrderPresent
 * @return
 */
	public Subscription isInFlightOrderPresent(Boolean isInFlightOrderPresent) {
		this.isInFlightOrderPresent = isInFlightOrderPresent;
		return this;
	}
/**
 * 
 * @param privateInstalledProductId
 * @return
 */
	public Subscription privateInstalledProductId(String privateInstalledProductId) {
		this.privateInstalledProductId = privateInstalledProductId;
		return this;
	}
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
 * @param productName
 * @return
 */
	public Subscription productName(String productName) {
		this.productName = productName;
		return this;
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
 * @param productName
 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
/**
 * 
 * @param assetIntegrationId
 * @return
 */
	public Subscription assetIntegrationId(String assetIntegrationId) {
		this.assetIntegrationId = assetIntegrationId;
		return this;
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
 * @param assetIntegrationId
 */
	public void setAssetIntegrationId(String assetIntegrationId) {
		this.assetIntegrationId = assetIntegrationId;
	}
/**
 * 
 * @param msisdnId
 * @return
 */
	public Subscription msisdnId(String msisdnId) {
		this.msisdnId = msisdnId;
		return this;
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
 * @param msisdnId
 */
	public void setMsisdnId(String msisdnId) {
		this.msisdnId = msisdnId;
	}
/**
 * 
 * @param productDescription
 * @return
 */
	public Subscription productDescription(String productDescription) {
		this.productDescription = productDescription;
		return this;
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
 * @param productDescription
 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
/**
 * 
 * @param productId
 * @return
 */
	public Subscription productId(String productId) {
		this.productId = productId;
		return this;
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
 * @param productId
 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
/**
 * 
 * @param privateRootInstalledProductId
 * @return
 */
	public Subscription privateRootInstalledProductId(String privateRootInstalledProductId) {
		this.privateRootInstalledProductId = privateRootInstalledProductId;
		return this;
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
 * @param privateRootInstalledProductId
 */
	public void setPrivateRootInstalledProductId(String privateRootInstalledProductId) {
		this.privateRootInstalledProductId = privateRootInstalledProductId;
	}
/**
 * 
 * @param privateParentInstalledProductId
 * @return
 */
	public Subscription privateParentInstalledProductId(String privateParentInstalledProductId) {
		this.privateParentInstalledProductId = privateParentInstalledProductId;
		return this;
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
 * @param privateParentInstalledProductId
 */
	public void setPrivateParentInstalledProductId(String privateParentInstalledProductId) {
		this.privateParentInstalledProductId = privateParentInstalledProductId;
	}
/**
 * 
 * @param customerPartyAccountName
 * @return
 */
	public Subscription customerPartyAccountName(String customerPartyAccountName) {
		this.customerPartyAccountName = customerPartyAccountName;
		return this;
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
 * @param customerPartyAccountName
 */
	public void setCustomerPartyAccountName(String customerPartyAccountName) {
		this.customerPartyAccountName = customerPartyAccountName;
	}
/**
 * 
 * @param serviceAccountId
 * @return
 */
	public Subscription serviceAccountId(String serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
		return this;
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
 * @param serviceAccountId
 */
	public void setServiceAccountId(String serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
	}
/**
 * 
 * @param owningAccountLocationName
 * @return
 */
	public Subscription owningAccountLocationName(String owningAccountLocationName) {
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
 * @param owningAccountLocationName
 */
	public void setOwningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
	}
/**
 * 
 * @param owningAccountFullName
 * @return
 */
	public Subscription owningAccountFullName(String owningAccountFullName) {
		this.owningAccountFullName = owningAccountFullName;
		return this;
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
 * @param owningAccountFullName
 */
	public void setOwningAccountFullName(String owningAccountFullName) {
		this.owningAccountFullName = owningAccountFullName;
	}
/**
 * 
 * @param owningAccountId
 * @return
 */
	public Subscription owningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
		return this;
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
 * @param owningAccountId
 */
	public void setOwningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
	}
/**
 * 
 * @param owningContactFirstName
 * @return
 */
	public Subscription owningContactFirstName(String owningContactFirstName) {
		this.owningContactFirstName = owningContactFirstName;
		return this;
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
 * @param owningContactFirstName
 */
	public void setOwningContactFirstName(String owningContactFirstName) {
		this.owningContactFirstName = owningContactFirstName;
	}
/**
 * 
 * @param owningContactFamilyName
 * @return
 */
	public Subscription owningContactFamilyName(String owningContactFamilyName) {
		this.owningContactFamilyName = owningContactFamilyName;
		return this;
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
 * @param owningContactFamilyName
 */
	public void setOwningContactFamilyName(String owningContactFamilyName) {
		this.owningContactFamilyName = owningContactFamilyName;
	}
/**
 * 
 * @param owningAccountContactId
 * @return
 */
	public Subscription owningAccountContactId(String owningAccountContactId) {
		this.owningAccountContactId = owningAccountContactId;
		return this;
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
 * @param owningAccountContactId
 */
	public void setOwningAccountContactId(String owningAccountContactId) {
		this.owningAccountContactId = owningAccountContactId;
	}
/**
 * 
 * @param billingAccountFullName
 * @return
 */
	public Subscription billingAccountFullName(String billingAccountFullName) {
		this.billingAccountFullName = billingAccountFullName;
		return this;
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
 * @param billingAccountFullName
 */
	public void setBillingAccountFullName(String billingAccountFullName) {
		this.billingAccountFullName = billingAccountFullName;
	}
/**
 * 
 * @param billingAccountId
 * @return
 */
	public Subscription billingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
		return this;
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
 * @param billingAccountId
 */
	public void setBillingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
	}
/**
 * 
 * @param billingProfileId
 * @return
 */
	public Subscription billingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
		return this;
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
 * @param billingProfileId
 */
	public void setBillingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
	}
/**
 * 
 * @param billingProfileType
 * @return
 */
	public Subscription billingProfileType(String billingProfileType) {
		this.billingProfileType = billingProfileType;
		return this;
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
 * @param billingProfileType
 */
	public void setBillingProfileType(String billingProfileType) {
		this.billingProfileType = billingProfileType;
	}
/**
 * 
 * @param installationDate
 * @return
 */
	public Subscription installationDate(String installationDate) {
		this.installationDate = installationDate;
		return this;
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
 * @param installationDate
 */
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
/**
 * 
 * @param startDate
 * @return
 */
	public Subscription startDate(String startDate) {
		this.startDate = startDate;
		return this;
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
 * @param startDate
 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
/**
 * 
 * @param endDate
 * @return
 */
	public Subscription endDate(String endDate) {
		this.endDate = endDate;
		return this;
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
 * @param endDate
 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
/**
 * 
 * @param primaryContactId
 * @return
 */
	public Subscription primaryContactId(String primaryContactId) {
		this.primaryContactId = primaryContactId;
		return this;
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
 * @param primaryContactId
 */
	public void setPrimaryContactId(String primaryContactId) {
		this.primaryContactId = primaryContactId;
	}
/**
 * 
 * @param associatedEmployee
 * @return
 */
	public Subscription associatedEmployee(String associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
		return this;
	}
/**
 * 
 * @return
 */
	public String getAssociatedEmployee() {
		return associatedEmployee;
	}
/**
 * 
 * @param associatedEmployee
 */
	public void setAssociatedEmployee(String associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
	}
/**
 * 
 * @param trackingQuantity
 * @return
 */
	public Subscription trackingQuantity(Double trackingQuantity) {
		this.trackingQuantity = trackingQuantity;
		return this;
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
 * @param trackingQuantity
 */
	public void setTrackingQuantity(Double trackingQuantity) {
		this.trackingQuantity = trackingQuantity;
	}
/**
 * 
 * @param registrationDate
 * @return
 */
	public Subscription registrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
		return this;
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
 * @param registrationDate
 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
/**
 * 
 * @param serialNumber
 * @return
 */
	public Subscription serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
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
 * @param serialNumber
 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
/**
 * 
 * @param status
 * @return
 */
	public Subscription status(String status) {
		this.status = status;
		return this;
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
 * @param status
 */
	public void setStatus(String status) {
		this.status = status;
	}
/**
 * 
 * @param statusEffectiveDate
 * @return
 */
	public Subscription statusEffectiveDate(String statusEffectiveDate) {
		this.statusEffectiveDate = statusEffectiveDate;
		return this;
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
 * @param statusEffectiveDate
 */
	public void setStatusEffectiveDate(String statusEffectiveDate) {
		this.statusEffectiveDate = statusEffectiveDate;
	}
/**
 * 
 * @param promotionId
 * @return
 */
	public Subscription promotionId(String promotionId) {
		this.promotionId = promotionId;
		return this;
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
 * @param promotionId
 */
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
/**
 * 
 * @param publicInstalledProductId
 * @return
 */
	public Subscription publicInstalledProductId(String publicInstalledProductId) {
		this.publicInstalledProductId = publicInstalledProductId;
		return this;
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
 * @param publicInstalledProductId
 */
	public void setPublicInstalledProductId(String publicInstalledProductId) {
		this.publicInstalledProductId = publicInstalledProductId;
	}
/**
 * 
 * @param headerChildAssetId
 * @return
 */
	public Subscription headerChildAssetId(String headerChildAssetId) {
		this.headerChildAssetId = headerChildAssetId;
		return this;
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
 * @param headerChildAssetId
 */
	public void setHeaderChildAssetId(String headerChildAssetId) {
		this.headerChildAssetId = headerChildAssetId;
	}
/**
 * 
 * @param agreementId
 * @return
 */
	public Subscription agreementId(String agreementId) {
		this.agreementId = agreementId;
		return this;
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
 * @param agreementId
 */
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
/**
 * 
 * @param alternateTrackingNumber
 * @return
 */
	public Subscription alternateTrackingNumber(String alternateTrackingNumber) {
		this.alternateTrackingNumber = alternateTrackingNumber;
		return this;
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
 * @param alternateTrackingNumber
 */
	public void setAlternateTrackingNumber(String alternateTrackingNumber) {
		this.alternateTrackingNumber = alternateTrackingNumber;
	}
/**
 * 
 * @param inventoryLocationReference
 * @return
 */
	public Subscription inventoryLocationReference(String inventoryLocationReference) {
		this.inventoryLocationReference = inventoryLocationReference;
		return this;
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
 * @param inventoryLocationReference
 */
	public void setInventoryLocationReference(String inventoryLocationReference) {
		this.inventoryLocationReference = inventoryLocationReference;
	}
/**
 * 
 * @param salesOrderRef
 * @return
 */
	public Subscription salesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
		return this;
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
 * @param salesOrderRef
 */
	public void setSalesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
	}
/**
 * 
 * @param price
 * @return
 */
	public Subscription price(Double price) {
		this.price = price;
		return this;
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
 * @param price
 */
	public void setPrice(Double price) {
		this.price = price;
	}
/**
 * 
 * @param priceType
 * @return
 */
	public Subscription priceType(String priceType) {
		this.priceType = priceType;
		return this;
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
 * @param priceType
 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
/**
 * 
 * @param priceCategory
 * @return
 */
	public Subscription priceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
		return this;
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
 * @param priceCategory
 */
	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}
/**
 * 
 * @param manufacturedDate
 * @return
 */
	public Subscription manufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
		return this;
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
 * @param manufacturedDate
 */
	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}
/**
 * 
 * @param installedProductName
 * @return
 */
	public Subscription installedProductName(String installedProductName) {
		this.installedProductName = installedProductName;
		return this;
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
 * @param installedProductName
 */
	public void setInstalledProductName(String installedProductName) {
		this.installedProductName = installedProductName;
	}
/**
 * 
 * @param productCost
 * @return
 */
	public Subscription productCost(Double productCost) {
		this.productCost = productCost;
		return this;
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
 * @param productCost
 */
	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}
/**
 * 
 * @param uomCode
 * @return
 */
	public Subscription uomCode(String uomCode) {
		this.uomCode = uomCode;
		return this;
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
 * @param uomCode
 */
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}
/**
 * 
 * @param asset
 * @return
 */
	public Subscription asset(List<Asset> asset) {
		this.asset = asset;
		return this;
	}
/**
 * 
 * @param assetItem
 * @return
 */
	public Subscription addAssetItem(Asset assetItem) {
		this.asset.add(assetItem);
		return this;
	}
/**
 * 
 * @return
 */
	public List<Asset> getAsset() {
		return asset;
	}
/**
 * 
 * @param asset
 */
	public void setAsset(List<Asset> asset) {
		this.asset = asset;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Subscription subscription = (Subscription) o;
		return Objects.equals(this.privateInstalledProductId, subscription.privateInstalledProductId)
				&& Objects.equals(this.productName, subscription.productName)
				&& Objects.equals(this.assetIntegrationId, subscription.assetIntegrationId)
				&& Objects.equals(this.msisdnId, subscription.msisdnId)
				&& Objects.equals(this.productDescription, subscription.productDescription)
				&& Objects.equals(this.productId, subscription.productId)
				&& Objects.equals(this.privateRootInstalledProductId, subscription.privateRootInstalledProductId)
				&& Objects.equals(this.privateParentInstalledProductId, subscription.privateParentInstalledProductId)
				&& Objects.equals(this.customerPartyAccountName, subscription.customerPartyAccountName)
				&& Objects.equals(this.serviceAccountId, subscription.serviceAccountId)
				&& Objects.equals(this.owningAccountLocationName, subscription.owningAccountLocationName)
				&& Objects.equals(this.owningAccountFullName, subscription.owningAccountFullName)
				&& Objects.equals(this.owningAccountId, subscription.owningAccountId)
				&& Objects.equals(this.owningContactFirstName, subscription.owningContactFirstName)
				&& Objects.equals(this.owningContactFamilyName, subscription.owningContactFamilyName)
				&& Objects.equals(this.owningAccountContactId, subscription.owningAccountContactId)
				&& Objects.equals(this.billingAccountFullName, subscription.billingAccountFullName)
				&& Objects.equals(this.billingAccountId, subscription.billingAccountId)
				&& Objects.equals(this.billingProfileId, subscription.billingProfileId)
				&& Objects.equals(this.billingProfileType, subscription.billingProfileType)
				&& Objects.equals(this.installationDate, subscription.installationDate)
				&& Objects.equals(this.startDate, subscription.startDate)
				&& Objects.equals(this.endDate, subscription.endDate)
				&& Objects.equals(this.primaryContactId, subscription.primaryContactId)
				&& Objects.equals(this.associatedEmployee, subscription.associatedEmployee)
				&& Objects.equals(this.trackingQuantity, subscription.trackingQuantity)
				&& Objects.equals(this.registrationDate, subscription.registrationDate)
				&& Objects.equals(this.serialNumber, subscription.serialNumber)
				&& Objects.equals(this.status, subscription.status)
				&& Objects.equals(this.statusEffectiveDate, subscription.statusEffectiveDate)
				&& Objects.equals(this.promotionId, subscription.promotionId)
				&& Objects.equals(this.publicInstalledProductId, subscription.publicInstalledProductId)
				&& Objects.equals(this.headerChildAssetId, subscription.headerChildAssetId)
				&& Objects.equals(this.agreementId, subscription.agreementId)
				&& Objects.equals(this.alternateTrackingNumber, subscription.alternateTrackingNumber)
				&& Objects.equals(this.inventoryLocationReference, subscription.inventoryLocationReference)
				&& Objects.equals(this.salesOrderRef, subscription.salesOrderRef)
				&& Objects.equals(this.price, subscription.price)
				&& Objects.equals(this.priceType, subscription.priceType)
				&& Objects.equals(this.priceCategory, subscription.priceCategory)
				&& Objects.equals(this.manufacturedDate, subscription.manufacturedDate)
				&& Objects.equals(this.installedProductName, subscription.installedProductName)
				&& Objects.equals(this.productCost, subscription.productCost)
				&& Objects.equals(this.uomCode, subscription.uomCode) && Objects.equals(this.asset, subscription.asset)
				&& Objects.equals(this.isInFlightOrderPresent, subscription.isInFlightOrderPresent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(privateInstalledProductId, productName, assetIntegrationId, msisdnId, productDescription,
				productId, privateRootInstalledProductId, privateParentInstalledProductId, customerPartyAccountName,
				serviceAccountId, owningAccountLocationName, owningAccountFullName, owningAccountId,
				owningContactFirstName, owningContactFamilyName, owningAccountContactId, billingAccountFullName,
				billingAccountId, billingProfileId, billingProfileType, installationDate, startDate, endDate,
				primaryContactId, associatedEmployee, trackingQuantity, registrationDate, serialNumber, status,
				statusEffectiveDate, promotionId, publicInstalledProductId, headerChildAssetId, agreementId,
				alternateTrackingNumber, inventoryLocationReference, salesOrderRef, price, priceType, priceCategory,
				manufacturedDate, installedProductName, productCost, uomCode, asset, isInFlightOrderPresent);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Subscription {\n");

		sb.append("    privateInstalledProductId: ").append(toIndentedString(privateInstalledProductId)).append("\n");
		sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
		sb.append("    assetIntegrationId: ").append(toIndentedString(assetIntegrationId)).append("\n");
		sb.append("    msisdnId: ").append(toIndentedString(msisdnId)).append("\n");
		sb.append("    productDescription: ").append(toIndentedString(productDescription)).append("\n");
		sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
		sb.append("    privateRootInstalledProductId: ").append(toIndentedString(privateRootInstalledProductId))
				.append("\n");
		sb.append("    privateParentInstalledProductId: ").append(toIndentedString(privateParentInstalledProductId))
				.append("\n");
		sb.append("    customerPartyAccountName: ").append(toIndentedString(customerPartyAccountName)).append("\n");
		sb.append("    serviceAccountId: ").append(toIndentedString(serviceAccountId)).append("\n");
		sb.append("    owningAccountLocationName: ").append(toIndentedString(owningAccountLocationName)).append("\n");
		sb.append("    owningAccountFullName: ").append(toIndentedString(owningAccountFullName)).append("\n");
		sb.append("    owningAccountId: ").append(toIndentedString(owningAccountId)).append("\n");
		sb.append("    owningContactFirstName: ").append(toIndentedString(owningContactFirstName)).append("\n");
		sb.append("    owningContactFamilyName: ").append(toIndentedString(owningContactFamilyName)).append("\n");
		sb.append("    owningAccountContactId: ").append(toIndentedString(owningAccountContactId)).append("\n");
		sb.append("    billingAccountFullName: ").append(toIndentedString(billingAccountFullName)).append("\n");
		sb.append("    billingAccountId: ").append(toIndentedString(billingAccountId)).append("\n");
		sb.append("    billingProfileId: ").append(toIndentedString(billingProfileId)).append("\n");
		sb.append("    billingProfileType: ").append(toIndentedString(billingProfileType)).append("\n");
		sb.append("    installationDate: ").append(toIndentedString(installationDate)).append("\n");
		sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
		sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
		sb.append("    primaryContactId: ").append(toIndentedString(primaryContactId)).append("\n");
		sb.append("    associatedEmployee: ").append(toIndentedString(associatedEmployee)).append("\n");
		sb.append("    trackingQuantity: ").append(toIndentedString(trackingQuantity)).append("\n");
		sb.append("    registrationDate: ").append(toIndentedString(registrationDate)).append("\n");
		sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    statusEffectiveDate: ").append(toIndentedString(statusEffectiveDate)).append("\n");
		sb.append("    promotionId: ").append(toIndentedString(promotionId)).append("\n");
		sb.append("    publicInstalledProductId: ").append(toIndentedString(publicInstalledProductId)).append("\n");
		sb.append("    headerChildAssetId: ").append(toIndentedString(headerChildAssetId)).append("\n");
		sb.append("    agreementId: ").append(toIndentedString(agreementId)).append("\n");
		sb.append("    alternateTrackingNumber: ").append(toIndentedString(alternateTrackingNumber)).append("\n");
		sb.append("    inventoryLocationReference: ").append(toIndentedString(inventoryLocationReference)).append("\n");
		sb.append("    salesOrderRef: ").append(toIndentedString(salesOrderRef)).append("\n");
		sb.append("    price: ").append(toIndentedString(price)).append("\n");
		sb.append("    priceType: ").append(toIndentedString(priceType)).append("\n");
		sb.append("    priceCategory: ").append(toIndentedString(priceCategory)).append("\n");
		sb.append("    manufacturedDate: ").append(toIndentedString(manufacturedDate)).append("\n");
		sb.append("    installedProductName: ").append(toIndentedString(installedProductName)).append("\n");
		sb.append("    productCost: ").append(toIndentedString(productCost)).append("\n");
		sb.append("    uomCode: ").append(toIndentedString(uomCode)).append("\n");
		sb.append("    asset: ").append(toIndentedString(asset)).append("\n");
		sb.append("    isInFlightOrderPresent: ").append(toIndentedString(isInFlightOrderPresent)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
