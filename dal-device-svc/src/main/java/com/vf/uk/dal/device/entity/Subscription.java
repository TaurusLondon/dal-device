package com.vf.uk.dal.device.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

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

	public Boolean getIsInFlightOrderPresent() {
		return isInFlightOrderPresent;
	}

	public void setIsInFlightOrderPresent(Boolean isInFlightOrderPresent) {
		this.isInFlightOrderPresent = isInFlightOrderPresent;
	}

	public Subscription isInFlightOrderPresent(Boolean isInFlightOrderPresent) {
		this.isInFlightOrderPresent = isInFlightOrderPresent;
		return this;
	}

	public Subscription privateInstalledProductId(String privateInstalledProductId) {
		this.privateInstalledProductId = privateInstalledProductId;
		return this;
	}

	public String getPrivateInstalledProductId() {
		return privateInstalledProductId;
	}

	public void setPrivateInstalledProductId(String privateInstalledProductId) {
		this.privateInstalledProductId = privateInstalledProductId;
	}

	public Subscription productName(String productName) {
		this.productName = productName;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Subscription assetIntegrationId(String assetIntegrationId) {
		this.assetIntegrationId = assetIntegrationId;
		return this;
	}

	public String getAssetIntegrationId() {
		return assetIntegrationId;
	}

	public void setAssetIntegrationId(String assetIntegrationId) {
		this.assetIntegrationId = assetIntegrationId;
	}

	public Subscription msisdnId(String msisdnId) {
		this.msisdnId = msisdnId;
		return this;
	}

	public String getMsisdnId() {
		return msisdnId;
	}

	public void setMsisdnId(String msisdnId) {
		this.msisdnId = msisdnId;
	}

	public Subscription productDescription(String productDescription) {
		this.productDescription = productDescription;
		return this;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Subscription productId(String productId) {
		this.productId = productId;
		return this;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Subscription privateRootInstalledProductId(String privateRootInstalledProductId) {
		this.privateRootInstalledProductId = privateRootInstalledProductId;
		return this;
	}

	public String getPrivateRootInstalledProductId() {
		return privateRootInstalledProductId;
	}

	public void setPrivateRootInstalledProductId(String privateRootInstalledProductId) {
		this.privateRootInstalledProductId = privateRootInstalledProductId;
	}

	public Subscription privateParentInstalledProductId(String privateParentInstalledProductId) {
		this.privateParentInstalledProductId = privateParentInstalledProductId;
		return this;
	}

	public String getPrivateParentInstalledProductId() {
		return privateParentInstalledProductId;
	}

	public void setPrivateParentInstalledProductId(String privateParentInstalledProductId) {
		this.privateParentInstalledProductId = privateParentInstalledProductId;
	}

	public Subscription customerPartyAccountName(String customerPartyAccountName) {
		this.customerPartyAccountName = customerPartyAccountName;
		return this;
	}

	public String getCustomerPartyAccountName() {
		return customerPartyAccountName;
	}

	public void setCustomerPartyAccountName(String customerPartyAccountName) {
		this.customerPartyAccountName = customerPartyAccountName;
	}

	public Subscription serviceAccountId(String serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
		return this;
	}

	public String getServiceAccountId() {
		return serviceAccountId;
	}

	public void setServiceAccountId(String serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
	}

	public Subscription owningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
		return this;
	}

	public String getOwningAccountLocationName() {
		return owningAccountLocationName;
	}

	public void setOwningAccountLocationName(String owningAccountLocationName) {
		this.owningAccountLocationName = owningAccountLocationName;
	}

	public Subscription owningAccountFullName(String owningAccountFullName) {
		this.owningAccountFullName = owningAccountFullName;
		return this;
	}

	public String getOwningAccountFullName() {
		return owningAccountFullName;
	}

	public void setOwningAccountFullName(String owningAccountFullName) {
		this.owningAccountFullName = owningAccountFullName;
	}

	public Subscription owningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
		return this;
	}

	public String getOwningAccountId() {
		return owningAccountId;
	}

	public void setOwningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
	}

	public Subscription owningContactFirstName(String owningContactFirstName) {
		this.owningContactFirstName = owningContactFirstName;
		return this;
	}

	public String getOwningContactFirstName() {
		return owningContactFirstName;
	}

	public void setOwningContactFirstName(String owningContactFirstName) {
		this.owningContactFirstName = owningContactFirstName;
	}

	public Subscription owningContactFamilyName(String owningContactFamilyName) {
		this.owningContactFamilyName = owningContactFamilyName;
		return this;
	}

	public String getOwningContactFamilyName() {
		return owningContactFamilyName;
	}

	public void setOwningContactFamilyName(String owningContactFamilyName) {
		this.owningContactFamilyName = owningContactFamilyName;
	}

	public Subscription owningAccountContactId(String owningAccountContactId) {
		this.owningAccountContactId = owningAccountContactId;
		return this;
	}

	public String getOwningAccountContactId() {
		return owningAccountContactId;
	}

	public void setOwningAccountContactId(String owningAccountContactId) {
		this.owningAccountContactId = owningAccountContactId;
	}

	public Subscription billingAccountFullName(String billingAccountFullName) {
		this.billingAccountFullName = billingAccountFullName;
		return this;
	}

	public String getBillingAccountFullName() {
		return billingAccountFullName;
	}

	public void setBillingAccountFullName(String billingAccountFullName) {
		this.billingAccountFullName = billingAccountFullName;
	}

	public Subscription billingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
		return this;
	}

	public String getBillingAccountId() {
		return billingAccountId;
	}

	public void setBillingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
	}

	public Subscription billingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
		return this;
	}

	public String getBillingProfileId() {
		return billingProfileId;
	}

	public void setBillingProfileId(String billingProfileId) {
		this.billingProfileId = billingProfileId;
	}

	public Subscription billingProfileType(String billingProfileType) {
		this.billingProfileType = billingProfileType;
		return this;
	}

	public String getBillingProfileType() {
		return billingProfileType;
	}

	public void setBillingProfileType(String billingProfileType) {
		this.billingProfileType = billingProfileType;
	}

	public Subscription installationDate(String installationDate) {
		this.installationDate = installationDate;
		return this;
	}

	public String getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}

	public Subscription startDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Subscription endDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Subscription primaryContactId(String primaryContactId) {
		this.primaryContactId = primaryContactId;
		return this;
	}

	public String getPrimaryContactId() {
		return primaryContactId;
	}

	public void setPrimaryContactId(String primaryContactId) {
		this.primaryContactId = primaryContactId;
	}

	public Subscription associatedEmployee(String associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
		return this;
	}

	public String getAssociatedEmployee() {
		return associatedEmployee;
	}

	public void setAssociatedEmployee(String associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
	}

	public Subscription trackingQuantity(Double trackingQuantity) {
		this.trackingQuantity = trackingQuantity;
		return this;
	}

	public Double getTrackingQuantity() {
		return trackingQuantity;
	}

	public void setTrackingQuantity(Double trackingQuantity) {
		this.trackingQuantity = trackingQuantity;
	}

	public Subscription registrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Subscription serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Subscription status(String status) {
		this.status = status;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Subscription statusEffectiveDate(String statusEffectiveDate) {
		this.statusEffectiveDate = statusEffectiveDate;
		return this;
	}

	public String getStatusEffectiveDate() {
		return statusEffectiveDate;
	}

	public void setStatusEffectiveDate(String statusEffectiveDate) {
		this.statusEffectiveDate = statusEffectiveDate;
	}

	public Subscription promotionId(String promotionId) {
		this.promotionId = promotionId;
		return this;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Subscription publicInstalledProductId(String publicInstalledProductId) {
		this.publicInstalledProductId = publicInstalledProductId;
		return this;
	}

	public String getPublicInstalledProductId() {
		return publicInstalledProductId;
	}

	public void setPublicInstalledProductId(String publicInstalledProductId) {
		this.publicInstalledProductId = publicInstalledProductId;
	}

	public Subscription headerChildAssetId(String headerChildAssetId) {
		this.headerChildAssetId = headerChildAssetId;
		return this;
	}

	public String getHeaderChildAssetId() {
		return headerChildAssetId;
	}

	public void setHeaderChildAssetId(String headerChildAssetId) {
		this.headerChildAssetId = headerChildAssetId;
	}

	public Subscription agreementId(String agreementId) {
		this.agreementId = agreementId;
		return this;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public Subscription alternateTrackingNumber(String alternateTrackingNumber) {
		this.alternateTrackingNumber = alternateTrackingNumber;
		return this;
	}

	public String getAlternateTrackingNumber() {
		return alternateTrackingNumber;
	}

	public void setAlternateTrackingNumber(String alternateTrackingNumber) {
		this.alternateTrackingNumber = alternateTrackingNumber;
	}

	public Subscription inventoryLocationReference(String inventoryLocationReference) {
		this.inventoryLocationReference = inventoryLocationReference;
		return this;
	}

	public String getInventoryLocationReference() {
		return inventoryLocationReference;
	}

	public void setInventoryLocationReference(String inventoryLocationReference) {
		this.inventoryLocationReference = inventoryLocationReference;
	}

	public Subscription salesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
		return this;
	}

	public String getSalesOrderRef() {
		return salesOrderRef;
	}

	public void setSalesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
	}

	public Subscription price(Double price) {
		this.price = price;
		return this;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Subscription priceType(String priceType) {
		this.priceType = priceType;
		return this;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Subscription priceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
		return this;
	}

	public String getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}

	public Subscription manufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
		return this;
	}

	public String getManufacturedDate() {
		return manufacturedDate;
	}

	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}

	public Subscription installedProductName(String installedProductName) {
		this.installedProductName = installedProductName;
		return this;
	}

	public String getInstalledProductName() {
		return installedProductName;
	}

	public void setInstalledProductName(String installedProductName) {
		this.installedProductName = installedProductName;
	}

	public Subscription productCost(Double productCost) {
		this.productCost = productCost;
		return this;
	}

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}

	public Subscription uomCode(String uomCode) {
		this.uomCode = uomCode;
		return this;
	}

	public String getUomCode() {
		return uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	public Subscription asset(List<Asset> asset) {
		this.asset = asset;
		return this;
	}

	public Subscription addAssetItem(Asset assetItem) {
		this.asset.add(assetItem);
		return this;
	}

	public List<Asset> getAsset() {
		return asset;
	}

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
