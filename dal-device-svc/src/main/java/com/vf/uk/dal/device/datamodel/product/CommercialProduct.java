package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

public class CommercialProduct {

	protected String id;

	protected Long order;

	protected String name;

	protected String productClass;

	protected Boolean isServicesProduct;

	protected Duration duration;

	protected Discount discount;

	protected String warranty;

	protected String condition;

	protected List<String> categories;

	protected List<String> productLines;

	protected PriceDetail priceDetail;

	protected List<ProductAllowance> productAllowance;

	protected BoxPrice boxPrice;

	protected ProductAvailability productAvailability;

	protected List<Relationship> productRelationshipList;

	protected Boolean isDeviceProduct;

	protected String inclusiveEligibility;

	protected boolean omniChannelDiscountEligible;

	protected String version;

	protected String seoCanonical;

	protected String seoDescription;

	protected String seoKeywords;

	protected String seoIndex;

	protected String seoRobots;

	protected String productSubClass;

	protected Equipment equipment;

	protected String eligibilitySubflow;

	protected List<Allowance> allowanceDisplay;

	protected boolean ageRestricted;

	protected Delivery delivery;

	protected boolean isBattery;

	protected String metadataVersion;

	protected String displayName;

	protected String preDesc;

	protected String postDesc;

	protected String preDescMobile;

	protected String postDescMobile;

	protected List<ImageURL> listOfimageURLs;

	protected List<MediaURL> listOfmediaURLs;

	protected List<HelpURL> listOfhelpURLs;

	protected List<Group> specificationGroups;

	protected String inTheBox;

	protected String contentVersion;

	protected ProductControl productControl;

	protected ProductGroups productGroups;

	protected Misc misc;

	protected ProductPriceOverride productPriceOverride;

	protected PromoteAs promoteAs;

	protected String MerchandisingVersion;

	protected String leadPlanId;

	protected List<Recommendations> recommendataions;

	protected List<String> listOfCompatiblePlanIds;

	protected Boolean fastMoving;

	protected String stockThresholdLimit;

	protected List<EligibleChannel> listOfEligibleChannels;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductClass() {
		return productClass;
	}
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	public Boolean getIsServicesProduct() {
		return isServicesProduct;
	}
	public void setIsServicesProduct(Boolean isServicesProduct) {
		this.isServicesProduct = isServicesProduct;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public Discount getDiscount() {
		return discount;
	}
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<String> getProductLines() {
		return productLines;
	}
	public void setProductLines(List<String> productLines) {
		this.productLines = productLines;
	}
	public PriceDetail getPriceDetail() {
		return priceDetail;
	}
	public void setPriceDetail(PriceDetail priceDetail) {
		this.priceDetail = priceDetail;
	}
	public List<ProductAllowance> getProductAllowance() {
		return productAllowance;
	}
	public void setProductAllowance(List<ProductAllowance> productAllowance) {
		this.productAllowance = productAllowance;
	}
	public BoxPrice getBoxPrice() {
		return boxPrice;
	}
	public void setBoxPrice(BoxPrice boxPrice) {
		this.boxPrice = boxPrice;
	}
	public ProductAvailability getProductAvailability() {
		return productAvailability;
	}
	public void setProductAvailability(ProductAvailability productAvailability) {
		this.productAvailability = productAvailability;
	}
	public List<Relationship> getProductRelationshipList() {
		return productRelationshipList;
	}
	public void setProductRelationshipList(List<Relationship> productRelationshipList) {
		this.productRelationshipList = productRelationshipList;
	}
	public Boolean getIsDeviceProduct() {
		return isDeviceProduct;
	}
	public void setIsDeviceProduct(Boolean isDeviceProduct) {
		this.isDeviceProduct = isDeviceProduct;
	}
	public String getInclusiveEligibility() {
		return inclusiveEligibility;
	}
	public void setInclusiveEligibility(String inclusiveEligibility) {
		this.inclusiveEligibility = inclusiveEligibility;
	}
	public boolean isOmniChannelDiscountEligible() {
		return omniChannelDiscountEligible;
	}
	public void setOmniChannelDiscountEligible(boolean omniChannelDiscountEligible) {
		this.omniChannelDiscountEligible = omniChannelDiscountEligible;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSeoCanonical() {
		return seoCanonical;
	}
	public void setSeoCanonical(String seoCanonical) {
		this.seoCanonical = seoCanonical;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoIndex() {
		return seoIndex;
	}
	public void setSeoIndex(String seoIndex) {
		this.seoIndex = seoIndex;
	}
	public String getSeoRobots() {
		return seoRobots;
	}
	public void setSeoRobots(String seoRobots) {
		this.seoRobots = seoRobots;
	}
	public String getProductSubClass() {
		return productSubClass;
	}
	public void setProductSubClass(String productSubClass) {
		this.productSubClass = productSubClass;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public String getEligibilitySubflow() {
		return eligibilitySubflow;
	}
	public void setEligibilitySubflow(String eligibilitySubflow) {
		this.eligibilitySubflow = eligibilitySubflow;
	}
	public List<Allowance> getAllowanceDisplay() {
		return allowanceDisplay;
	}
	public void setAllowanceDisplay(List<Allowance> allowanceDisplay) {
		this.allowanceDisplay = allowanceDisplay;
	}
	public boolean isAgeRestricted() {
		return ageRestricted;
	}
	public void setAgeRestricted(boolean ageRestricted) {
		this.ageRestricted = ageRestricted;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public boolean isBattery() {
		return isBattery;
	}
	public void setBattery(boolean isBattery) {
		this.isBattery = isBattery;
	}
	public String getMetadataVersion() {
		return metadataVersion;
	}
	public void setMetadataVersion(String metadataVersion) {
		this.metadataVersion = metadataVersion;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPreDesc() {
		return preDesc;
	}
	public void setPreDesc(String preDesc) {
		this.preDesc = preDesc;
	}
	public String getPostDesc() {
		return postDesc;
	}
	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	public String getPreDescMobile() {
		return preDescMobile;
	}
	public void setPreDescMobile(String preDescMobile) {
		this.preDescMobile = preDescMobile;
	}
	public String getPostDescMobile() {
		return postDescMobile;
	}
	public void setPostDescMobile(String postDescMobile) {
		this.postDescMobile = postDescMobile;
	}
	public List<ImageURL> getListOfimageURLs() {
		return listOfimageURLs;
	}
	public void setListOfimageURLs(List<ImageURL> listOfimageURLs) {
		this.listOfimageURLs = listOfimageURLs;
	}
	public List<MediaURL> getListOfmediaURLs() {
		return listOfmediaURLs;
	}
	public void setListOfmediaURLs(List<MediaURL> listOfmediaURLs) {
		this.listOfmediaURLs = listOfmediaURLs;
	}
	public List<HelpURL> getListOfhelpURLs() {
		return listOfhelpURLs;
	}
	public void setListOfhelpURLs(List<HelpURL> listOfhelpURLs) {
		this.listOfhelpURLs = listOfhelpURLs;
	}
	public List<Group> getSpecificationGroups() {
		return specificationGroups;
	}
	public void setSpecificationGroups(List<Group> specificationGroups) {
		this.specificationGroups = specificationGroups;
	}
	public String getInTheBox() {
		return inTheBox;
	}
	public void setInTheBox(String inTheBox) {
		this.inTheBox = inTheBox;
	}
	public String getContentVersion() {
		return contentVersion;
	}
	public void setContentVersion(String contentVersion) {
		this.contentVersion = contentVersion;
	}
	public ProductControl getProductControl() {
		return productControl;
	}
	public void setProductControl(ProductControl productControl) {
		this.productControl = productControl;
	}
	public ProductGroups getProductGroups() {
		return productGroups;
	}
	public void setProductGroups(ProductGroups productGroups) {
		this.productGroups = productGroups;
	}
	public Misc getMisc() {
		return misc;
	}
	public void setMisc(Misc misc) {
		this.misc = misc;
	}
	public ProductPriceOverride getProductPriceOverride() {
		return productPriceOverride;
	}
	public void setProductPriceOverride(ProductPriceOverride productPriceOverride) {
		this.productPriceOverride = productPriceOverride;
	}
	public PromoteAs getPromoteAs() {
		return promoteAs;
	}
	public void setPromoteAs(PromoteAs promoteAs) {
		this.promoteAs = promoteAs;
	}
	public String getMerchandisingVersion() {
		return MerchandisingVersion;
	}
	public void setMerchandisingVersion(String merchandisingVersion) {
		MerchandisingVersion = merchandisingVersion;
	}
	public String getLeadPlanId() {
		return leadPlanId;
	}
	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}
	public List<Recommendations> getRecommendataions() {
		return recommendataions;
	}
	public void setRecommendataions(List<Recommendations> recommendataions) {
		this.recommendataions = recommendataions;
	}
	public List<String> getListOfCompatiblePlanIds() {
		return listOfCompatiblePlanIds;
	}
	public void setListOfCompatiblePlanIds(List<String> listOfCompatiblePlanIds) {
		this.listOfCompatiblePlanIds = listOfCompatiblePlanIds;
	}
	public Boolean getFastMoving() {
		return fastMoving;
	}
	public void setFastMoving(Boolean fastMoving) {
		this.fastMoving = fastMoving;
	}
	public String getStockThresholdLimit() {
		return stockThresholdLimit;
	}
	public void setStockThresholdLimit(String stockThresholdLimit) {
		this.stockThresholdLimit = stockThresholdLimit;
	}
	public List<EligibleChannel> getListOfEligibleChannels() {
		return listOfEligibleChannels;
	}
	public void setListOfEligibleChannels(List<EligibleChannel> listOfEligibleChannels) {
		this.listOfEligibleChannels = listOfEligibleChannels;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	@PortableProperty(56)
	protected String paymentType;

}
