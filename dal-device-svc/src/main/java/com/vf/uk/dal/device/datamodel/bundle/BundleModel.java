package com.vf.uk.dal.device.datamodel.bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BundleModel {

	private String id;

	private String bundleId;

	private String name;

	private String desc;

	private String paymentType;

	private String commitmentPeriod;

	private String commitmentPenalty;

	private String salesExpired;

	private String startDate;

	private List<String> productLines = new ArrayList<>();

	private float recurringCharge;

	private List<String> categories = new ArrayList<>();

	private List<String> serviceProducts = new ArrayList<>();
	
	@JsonProperty("RelationshipProductIds")
	private List<String> relationships = new ArrayList<>();

	private String recommendedAccessories;

	private String recommendedExtras;
	
	private String postSaleJourney;

	private String seoCanonical;

	private String seoDescription;

	private String seoKeywords;

	private String seoIndex;

	private String eligibilitySubflow;

	private String displayName;

	private String contentDesc;

	private String fullDetails;

	private String descMobile;

	private String fullDetailsMobile;

	private String video;

	private String threeDSpin;

	private String support;

	private Integer order;

	private String affiliateExport;

	private String compareWith;
	@JsonProperty("ProductGroup")
	private String productGroupName;

	private String imageURLsThumbsFront;

	private String imageURLsThumbsLeft;

	private String imageURLsThumbsRight;

	private String imageURLsThumbsSide;

	private String imageURLsThumbsBack;

	private String imageURLsFullFront;

	private String imageURLsFullLeft;

	private String imageURLsFullRight;

	private String imageURLsFullSide;

	private String imageURLsFullBack;

	private String imageURLsGrid;

	private String imageURLsSmall;

	private String imageURLsSticker;

	private String imageURLsIcon;

	private String charge;

	private String displayGroup;

	private List<String> migrationPaths;

	private List<String> upgradePaths;

	private String dataUOM;

	private String dataValue;

	private String dataDisplayUOM;

	private String ukTextsUOM;

	private String ukTextsValue;

	private String ukTextsDisplayUOM;

	private String ukMinutesUOM;

	private String ukMinutesValue;

	private String ukMinutesDisplayUOM;

	private String ukDataUOM;

	private String ukDataValue;

	private String ukDataDisplayUOM;

	private String wifiUOM;

	private String wifiValue;

	private String wifiDisplayUOM;

	private String europeanRoamingDataUOM;

	private String europeanRoamingDataValue;

	private String europeanRoamingDataDisplayUOM;

	private String europeanRoamingTextsUOM;

	private String europeanRoamingTextsValue;

	private String europeanRoamingTextsDisplayUOM;

	private String europeanRoamingMinutesUOM;

	private String europeanRoamingMinutesValue;

	private String europeanRoamingMinutesDisplayUOM;

	private String europeanRoamingPictureMessagesUOM;

	private String europeanRoamingPictureMessagesValue;

	private String europeanRoamingPictureMessagesDisplayUOM;

	private String internationalTextsUOM;

	private String internationalTextsValue;

	private String internationalTextsDisplayUOM;
	@JsonProperty("ETDATAUOM")
	private String etdataUOM;
	@JsonProperty("ETDATAValue")
	private String etdataValue;
	@JsonProperty("ETDATADisplayUOM")
	private String etdataDisplayUOM;

	private String globalRoamingDataUOM;

	private String globalRoamingDataValue;

	private String globalRoamingDataDisplayUOM;

	private String globalRoamingTextsUOM;

	private String globalRoamingTextsValue;

	private String globalRoamingTextsDisplayUOM;

	private String globalRoamingMinutesUOM;

	private String globalRoamingMinutesValue;

	private String globalRoamingMinutesDisplayUOM;

	private String roamingDataUOM;

	private String roamingDataValue;

	private String roamingDataDisplayUOM;

	private String uKmobileMinutesUOM;

	private String uKmobileMinutesValue;

	private String uKmobileMinutesDisplayUOM;

	private String vodafoneVodafonetextsUOM;

	private String vodafoneVodafonetextsValue;

	private String vodafoneVodafonetextsDisplayUOM;

	private String pictureMessagesUOM;

	private String pictureMessagesValue;

	private String pictureMessagesDisplayUOM;

	private String uKANDInternationalCreditUOM;

	private String uKANDInternationalCreditValue;

	private String uKANDInternationalCreditDisplayUOM;

	private String internationalMinutesUOM;

	private String internationalMinutesValue;

	private String internationalMinutesDisplayUOM;

	private String vodafoneVodafoneUOM;

	private String vodafoneVodafoneValue;

	private String vodafoneVodafoneDisplayUOM;

	private String vodafoneVodafoneMinutesUOM;

	private String vodafoneVodafoneMinutesValue;

	private String vodafoneVodafoneMinutesDisplayUOM;

	private String landlineMinutesUOM;

	private String landlineMinutesValue;

	private String landlineMinutesDisplayUOM;

	private String isDisplayableAcq;

	private String isSellableECare;

	private String isSellableRet;

	private String isDisplayableRet;

	private String isDisplaybaleSavedBasket;

	private String isSellableACQ;

	private List<String> promoteAs;

	private String endDate;

	private List<String> merchandisingMedia = new ArrayList<>();
	
	@JsonProperty("Allowance")
	private List<String> allowanceList = new ArrayList<>();

	private String planCoupleId;

	private String planCoupleFlag;

	private String planCoupleLabel;

	private Float monthlyGrossPrice;

	private Float monthlyNetPrice;

	private Float monthlyVatPrice;

	private Float monthlyDiscountedGrossPrice;

	private Float monthlyDiscountedNetPrice;

	private Float monthlyDiscountedVatPrice;

	private List<String> listOfCompatibleProducts;

	private Boolean secureNetFlag;

	private Boolean globalRoamingFlag;
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date startDateFormatted;
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date endDateFormatted;

	private String routerDeviceId;

	private String flbbSpendLimit;

	private String flbbUsage;

	private String speedLimitAndUsage;

	private List<String> miscKeyValue;
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date createDate;
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date modifiedDate;

	private List<String> seoRobots;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCommitmentPeriod() {
		return commitmentPeriod;
	}

	public void setCommitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

	public String getCommitmentPenalty() {
		return commitmentPenalty;
	}

	public void setCommitmentPenalty(String commitmentPenalty) {
		this.commitmentPenalty = commitmentPenalty;
	}

	public String getSalesExpired() {
		return salesExpired;
	}

	public void setSalesExpired(String salesExpired) {
		this.salesExpired = salesExpired;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<String> getProductLines() {
		return productLines;
	}

	public void setProductLines(List<String> productLines) {
		this.productLines = productLines;
	}

	public float getRecurringCharge() {
		return recurringCharge;
	}

	public void setRecurringCharge(float recurringCharge) {
		this.recurringCharge = recurringCharge;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getServiceProducts() {
		return serviceProducts;
	}

	public void setServiceProducts(List<String> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}

	public List<String> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<String> relationships) {
		this.relationships = relationships;
	}

	public String getRecommendedAccessories() {
		return recommendedAccessories;
	}

	public void setRecommendedAccessories(String recommendedAccessories) {
		this.recommendedAccessories = recommendedAccessories;
	}

	public String getRecommendedExtras() {
		return recommendedExtras;
	}

	public void setRecommendedExtras(String recommendedExtras) {
		this.recommendedExtras = recommendedExtras;
	}

	public String getPostSaleJourney() {
		return postSaleJourney;
	}

	public void setPostSaleJourney(String postSaleJourney) {
		this.postSaleJourney = postSaleJourney;
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

	public String getEligibilitySubflow() {
		return eligibilitySubflow;
	}

	public void setEligibilitySubflow(String eligibilitySubflow) {
		this.eligibilitySubflow = eligibilitySubflow;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getFullDetails() {
		return fullDetails;
	}

	public void setFullDetails(String fullDetails) {
		this.fullDetails = fullDetails;
	}

	public String getDescMobile() {
		return descMobile;
	}

	public void setDescMobile(String descMobile) {
		this.descMobile = descMobile;
	}

	public String getFullDetailsMobile() {
		return fullDetailsMobile;
	}

	public void setFullDetailsMobile(String fullDetailsMobile) {
		this.fullDetailsMobile = fullDetailsMobile;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getThreeDSpin() {
		return threeDSpin;
	}

	public void setThreeDSpin(String threeDSpin) {
		this.threeDSpin = threeDSpin;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getAffiliateExport() {
		return affiliateExport;
	}

	public void setAffiliateExport(String affiliateExport) {
		this.affiliateExport = affiliateExport;
	}

	public String getCompareWith() {
		return compareWith;
	}

	public void setCompareWith(String compareWith) {
		this.compareWith = compareWith;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getImageURLsThumbsFront() {
		return imageURLsThumbsFront;
	}

	public void setImageURLsThumbsFront(String imageURLsThumbsFront) {
		this.imageURLsThumbsFront = imageURLsThumbsFront;
	}

	public String getImageURLsThumbsLeft() {
		return imageURLsThumbsLeft;
	}

	public void setImageURLsThumbsLeft(String imageURLsThumbsLeft) {
		this.imageURLsThumbsLeft = imageURLsThumbsLeft;
	}

	public String getImageURLsThumbsRight() {
		return imageURLsThumbsRight;
	}

	public void setImageURLsThumbsRight(String imageURLsThumbsRight) {
		this.imageURLsThumbsRight = imageURLsThumbsRight;
	}

	public String getImageURLsThumbsSide() {
		return imageURLsThumbsSide;
	}

	public void setImageURLsThumbsSide(String imageURLsThumbsSide) {
		this.imageURLsThumbsSide = imageURLsThumbsSide;
	}

	public String getImageURLsThumbsBack() {
		return imageURLsThumbsBack;
	}

	public void setImageURLsThumbsBack(String imageURLsThumbsBack) {
		this.imageURLsThumbsBack = imageURLsThumbsBack;
	}

	public String getImageURLsFullFront() {
		return imageURLsFullFront;
	}

	public void setImageURLsFullFront(String imageURLsFullFront) {
		this.imageURLsFullFront = imageURLsFullFront;
	}

	public String getImageURLsFullLeft() {
		return imageURLsFullLeft;
	}

	public void setImageURLsFullLeft(String imageURLsFullLeft) {
		this.imageURLsFullLeft = imageURLsFullLeft;
	}

	public String getImageURLsFullRight() {
		return imageURLsFullRight;
	}

	public void setImageURLsFullRight(String imageURLsFullRight) {
		this.imageURLsFullRight = imageURLsFullRight;
	}

	public String getImageURLsFullSide() {
		return imageURLsFullSide;
	}

	public void setImageURLsFullSide(String imageURLsFullSide) {
		this.imageURLsFullSide = imageURLsFullSide;
	}

	public String getImageURLsFullBack() {
		return imageURLsFullBack;
	}

	public void setImageURLsFullBack(String imageURLsFullBack) {
		this.imageURLsFullBack = imageURLsFullBack;
	}

	public String getImageURLsGrid() {
		return imageURLsGrid;
	}

	public void setImageURLsGrid(String imageURLsGrid) {
		this.imageURLsGrid = imageURLsGrid;
	}

	public String getImageURLsSmall() {
		return imageURLsSmall;
	}

	public void setImageURLsSmall(String imageURLsSmall) {
		this.imageURLsSmall = imageURLsSmall;
	}

	public String getImageURLsSticker() {
		return imageURLsSticker;
	}

	public void setImageURLsSticker(String imageURLsSticker) {
		this.imageURLsSticker = imageURLsSticker;
	}

	public String getImageURLsIcon() {
		return imageURLsIcon;
	}

	public void setImageURLsIcon(String imageURLsIcon) {
		this.imageURLsIcon = imageURLsIcon;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getDisplayGroup() {
		return displayGroup;
	}

	public void setDisplayGroup(String displayGroup) {
		this.displayGroup = displayGroup;
	}

	public List<String> getMigrationPaths() {
		return migrationPaths;
	}

	public void setMigrationPaths(List<String> migrationPaths) {
		this.migrationPaths = migrationPaths;
	}

	public List<String> getUpgradePaths() {
		return upgradePaths;
	}

	public void setUpgradePaths(List<String> upgradePaths) {
		this.upgradePaths = upgradePaths;
	}

	public String getDataUOM() {
		return dataUOM;
	}

	public void setDataUOM(String dataUOM) {
		this.dataUOM = dataUOM;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getDataDisplayUOM() {
		return dataDisplayUOM;
	}

	public void setDataDisplayUOM(String dataDisplayUOM) {
		this.dataDisplayUOM = dataDisplayUOM;
	}

	public String getUkTextsUOM() {
		return ukTextsUOM;
	}

	public void setUkTextsUOM(String ukTextsUOM) {
		this.ukTextsUOM = ukTextsUOM;
	}

	public String getUkTextsValue() {
		return ukTextsValue;
	}

	public void setUkTextsValue(String ukTextsValue) {
		this.ukTextsValue = ukTextsValue;
	}

	public String getUkTextsDisplayUOM() {
		return ukTextsDisplayUOM;
	}

	public void setUkTextsDisplayUOM(String ukTextsDisplayUOM) {
		this.ukTextsDisplayUOM = ukTextsDisplayUOM;
	}

	public String getUkMinutesUOM() {
		return ukMinutesUOM;
	}

	public void setUkMinutesUOM(String ukMinutesUOM) {
		this.ukMinutesUOM = ukMinutesUOM;
	}

	public String getUkMinutesValue() {
		return ukMinutesValue;
	}

	public void setUkMinutesValue(String ukMinutesValue) {
		this.ukMinutesValue = ukMinutesValue;
	}

	public String getUkMinutesDisplayUOM() {
		return ukMinutesDisplayUOM;
	}

	public void setUkMinutesDisplayUOM(String ukMinutesDisplayUOM) {
		this.ukMinutesDisplayUOM = ukMinutesDisplayUOM;
	}

	public String getUkDataUOM() {
		return ukDataUOM;
	}

	public void setUkDataUOM(String ukDataUOM) {
		this.ukDataUOM = ukDataUOM;
	}

	public String getUkDataValue() {
		return ukDataValue;
	}

	public void setUkDataValue(String ukDataValue) {
		this.ukDataValue = ukDataValue;
	}

	public String getUkDataDisplayUOM() {
		return ukDataDisplayUOM;
	}

	public void setUkDataDisplayUOM(String ukDataDisplayUOM) {
		this.ukDataDisplayUOM = ukDataDisplayUOM;
	}

	public String getWifiUOM() {
		return wifiUOM;
	}

	public void setWifiUOM(String wifiUOM) {
		this.wifiUOM = wifiUOM;
	}

	public String getWifiValue() {
		return wifiValue;
	}

	public void setWifiValue(String wifiValue) {
		this.wifiValue = wifiValue;
	}

	public String getWifiDisplayUOM() {
		return wifiDisplayUOM;
	}

	public void setWifiDisplayUOM(String wifiDisplayUOM) {
		this.wifiDisplayUOM = wifiDisplayUOM;
	}

	public String getEuropeanRoamingDataUOM() {
		return europeanRoamingDataUOM;
	}

	public void setEuropeanRoamingDataUOM(String europeanRoamingDataUOM) {
		this.europeanRoamingDataUOM = europeanRoamingDataUOM;
	}

	public String getEuropeanRoamingDataValue() {
		return europeanRoamingDataValue;
	}

	public void setEuropeanRoamingDataValue(String europeanRoamingDataValue) {
		this.europeanRoamingDataValue = europeanRoamingDataValue;
	}

	public String getEuropeanRoamingDataDisplayUOM() {
		return europeanRoamingDataDisplayUOM;
	}

	public void setEuropeanRoamingDataDisplayUOM(String europeanRoamingDataDisplayUOM) {
		this.europeanRoamingDataDisplayUOM = europeanRoamingDataDisplayUOM;
	}

	public String getEuropeanRoamingTextsUOM() {
		return europeanRoamingTextsUOM;
	}

	public void setEuropeanRoamingTextsUOM(String europeanRoamingTextsUOM) {
		this.europeanRoamingTextsUOM = europeanRoamingTextsUOM;
	}

	public String getEuropeanRoamingTextsValue() {
		return europeanRoamingTextsValue;
	}

	public void setEuropeanRoamingTextsValue(String europeanRoamingTextsValue) {
		this.europeanRoamingTextsValue = europeanRoamingTextsValue;
	}

	public String getEuropeanRoamingTextsDisplayUOM() {
		return europeanRoamingTextsDisplayUOM;
	}

	public void setEuropeanRoamingTextsDisplayUOM(String europeanRoamingTextsDisplayUOM) {
		this.europeanRoamingTextsDisplayUOM = europeanRoamingTextsDisplayUOM;
	}

	public String getEuropeanRoamingMinutesUOM() {
		return europeanRoamingMinutesUOM;
	}

	public void setEuropeanRoamingMinutesUOM(String europeanRoamingMinutesUOM) {
		this.europeanRoamingMinutesUOM = europeanRoamingMinutesUOM;
	}

	public String getEuropeanRoamingMinutesValue() {
		return europeanRoamingMinutesValue;
	}

	public void setEuropeanRoamingMinutesValue(String europeanRoamingMinutesValue) {
		this.europeanRoamingMinutesValue = europeanRoamingMinutesValue;
	}

	public String getEuropeanRoamingMinutesDisplayUOM() {
		return europeanRoamingMinutesDisplayUOM;
	}

	public void setEuropeanRoamingMinutesDisplayUOM(String europeanRoamingMinutesDisplayUOM) {
		this.europeanRoamingMinutesDisplayUOM = europeanRoamingMinutesDisplayUOM;
	}

	public String getEuropeanRoamingPictureMessagesUOM() {
		return europeanRoamingPictureMessagesUOM;
	}

	public void setEuropeanRoamingPictureMessagesUOM(String europeanRoamingPictureMessagesUOM) {
		this.europeanRoamingPictureMessagesUOM = europeanRoamingPictureMessagesUOM;
	}

	public String getEuropeanRoamingPictureMessagesValue() {
		return europeanRoamingPictureMessagesValue;
	}

	public void setEuropeanRoamingPictureMessagesValue(String europeanRoamingPictureMessagesValue) {
		this.europeanRoamingPictureMessagesValue = europeanRoamingPictureMessagesValue;
	}

	public String getEuropeanRoamingPictureMessagesDisplayUOM() {
		return europeanRoamingPictureMessagesDisplayUOM;
	}

	public void setEuropeanRoamingPictureMessagesDisplayUOM(String europeanRoamingPictureMessagesDisplayUOM) {
		this.europeanRoamingPictureMessagesDisplayUOM = europeanRoamingPictureMessagesDisplayUOM;
	}

	public String getInternationalTextsUOM() {
		return internationalTextsUOM;
	}

	public void setInternationalTextsUOM(String internationalTextsUOM) {
		this.internationalTextsUOM = internationalTextsUOM;
	}

	public String getInternationalTextsValue() {
		return internationalTextsValue;
	}

	public void setInternationalTextsValue(String internationalTextsValue) {
		this.internationalTextsValue = internationalTextsValue;
	}

	public String getInternationalTextsDisplayUOM() {
		return internationalTextsDisplayUOM;
	}

	public void setInternationalTextsDisplayUOM(String internationalTextsDisplayUOM) {
		this.internationalTextsDisplayUOM = internationalTextsDisplayUOM;
	}

	public String getEtdataUOM() {
		return etdataUOM;
	}

	public void setEtdataUOM(String etdataUOM) {
		this.etdataUOM = etdataUOM;
	}

	public String getEtdataValue() {
		return etdataValue;
	}

	public void setEtdataValue(String etdataValue) {
		this.etdataValue = etdataValue;
	}

	public String getEtdataDisplayUOM() {
		return etdataDisplayUOM;
	}

	public void setEtdataDisplayUOM(String etdataDisplayUOM) {
		this.etdataDisplayUOM = etdataDisplayUOM;
	}

	public String getGlobalRoamingDataUOM() {
		return globalRoamingDataUOM;
	}

	public void setGlobalRoamingDataUOM(String globalRoamingDataUOM) {
		this.globalRoamingDataUOM = globalRoamingDataUOM;
	}

	public String getGlobalRoamingDataValue() {
		return globalRoamingDataValue;
	}

	public void setGlobalRoamingDataValue(String globalRoamingDataValue) {
		this.globalRoamingDataValue = globalRoamingDataValue;
	}

	public String getGlobalRoamingDataDisplayUOM() {
		return globalRoamingDataDisplayUOM;
	}

	public void setGlobalRoamingDataDisplayUOM(String globalRoamingDataDisplayUOM) {
		this.globalRoamingDataDisplayUOM = globalRoamingDataDisplayUOM;
	}

	public String getGlobalRoamingTextsUOM() {
		return globalRoamingTextsUOM;
	}

	public void setGlobalRoamingTextsUOM(String globalRoamingTextsUOM) {
		this.globalRoamingTextsUOM = globalRoamingTextsUOM;
	}

	public String getGlobalRoamingTextsValue() {
		return globalRoamingTextsValue;
	}

	public void setGlobalRoamingTextsValue(String globalRoamingTextsValue) {
		this.globalRoamingTextsValue = globalRoamingTextsValue;
	}

	public String getGlobalRoamingTextsDisplayUOM() {
		return globalRoamingTextsDisplayUOM;
	}

	public void setGlobalRoamingTextsDisplayUOM(String globalRoamingTextsDisplayUOM) {
		this.globalRoamingTextsDisplayUOM = globalRoamingTextsDisplayUOM;
	}

	public String getGlobalRoamingMinutesUOM() {
		return globalRoamingMinutesUOM;
	}

	public void setGlobalRoamingMinutesUOM(String globalRoamingMinutesUOM) {
		this.globalRoamingMinutesUOM = globalRoamingMinutesUOM;
	}

	public String getGlobalRoamingMinutesValue() {
		return globalRoamingMinutesValue;
	}

	public void setGlobalRoamingMinutesValue(String globalRoamingMinutesValue) {
		this.globalRoamingMinutesValue = globalRoamingMinutesValue;
	}

	public String getGlobalRoamingMinutesDisplayUOM() {
		return globalRoamingMinutesDisplayUOM;
	}

	public void setGlobalRoamingMinutesDisplayUOM(String globalRoamingMinutesDisplayUOM) {
		this.globalRoamingMinutesDisplayUOM = globalRoamingMinutesDisplayUOM;
	}

	public String getRoamingDataUOM() {
		return roamingDataUOM;
	}

	public void setRoamingDataUOM(String roamingDataUOM) {
		this.roamingDataUOM = roamingDataUOM;
	}

	public String getRoamingDataValue() {
		return roamingDataValue;
	}

	public void setRoamingDataValue(String roamingDataValue) {
		this.roamingDataValue = roamingDataValue;
	}

	public String getRoamingDataDisplayUOM() {
		return roamingDataDisplayUOM;
	}

	public void setRoamingDataDisplayUOM(String roamingDataDisplayUOM) {
		this.roamingDataDisplayUOM = roamingDataDisplayUOM;
	}

	public String getuKmobileMinutesUOM() {
		return uKmobileMinutesUOM;
	}

	public void setuKmobileMinutesUOM(String uKmobileMinutesUOM) {
		this.uKmobileMinutesUOM = uKmobileMinutesUOM;
	}

	public String getuKmobileMinutesValue() {
		return uKmobileMinutesValue;
	}

	public void setuKmobileMinutesValue(String uKmobileMinutesValue) {
		this.uKmobileMinutesValue = uKmobileMinutesValue;
	}

	public String getuKmobileMinutesDisplayUOM() {
		return uKmobileMinutesDisplayUOM;
	}

	public void setuKmobileMinutesDisplayUOM(String uKmobileMinutesDisplayUOM) {
		this.uKmobileMinutesDisplayUOM = uKmobileMinutesDisplayUOM;
	}

	public String getVodafoneVodafonetextsUOM() {
		return vodafoneVodafonetextsUOM;
	}

	public void setVodafoneVodafonetextsUOM(String vodafoneVodafonetextsUOM) {
		this.vodafoneVodafonetextsUOM = vodafoneVodafonetextsUOM;
	}

	public String getVodafoneVodafonetextsValue() {
		return vodafoneVodafonetextsValue;
	}

	public void setVodafoneVodafonetextsValue(String vodafoneVodafonetextsValue) {
		this.vodafoneVodafonetextsValue = vodafoneVodafonetextsValue;
	}

	public String getVodafoneVodafonetextsDisplayUOM() {
		return vodafoneVodafonetextsDisplayUOM;
	}

	public void setVodafoneVodafonetextsDisplayUOM(String vodafoneVodafonetextsDisplayUOM) {
		this.vodafoneVodafonetextsDisplayUOM = vodafoneVodafonetextsDisplayUOM;
	}

	public String getPictureMessagesUOM() {
		return pictureMessagesUOM;
	}

	public void setPictureMessagesUOM(String pictureMessagesUOM) {
		this.pictureMessagesUOM = pictureMessagesUOM;
	}

	public String getPictureMessagesValue() {
		return pictureMessagesValue;
	}

	public void setPictureMessagesValue(String pictureMessagesValue) {
		this.pictureMessagesValue = pictureMessagesValue;
	}

	public String getPictureMessagesDisplayUOM() {
		return pictureMessagesDisplayUOM;
	}

	public void setPictureMessagesDisplayUOM(String pictureMessagesDisplayUOM) {
		this.pictureMessagesDisplayUOM = pictureMessagesDisplayUOM;
	}

	public String getuKANDInternationalCreditUOM() {
		return uKANDInternationalCreditUOM;
	}

	public void setuKANDInternationalCreditUOM(String uKANDInternationalCreditUOM) {
		this.uKANDInternationalCreditUOM = uKANDInternationalCreditUOM;
	}

	public String getuKANDInternationalCreditValue() {
		return uKANDInternationalCreditValue;
	}

	public void setuKANDInternationalCreditValue(String uKANDInternationalCreditValue) {
		this.uKANDInternationalCreditValue = uKANDInternationalCreditValue;
	}

	public String getuKANDInternationalCreditDisplayUOM() {
		return uKANDInternationalCreditDisplayUOM;
	}

	public void setuKANDInternationalCreditDisplayUOM(String uKANDInternationalCreditDisplayUOM) {
		this.uKANDInternationalCreditDisplayUOM = uKANDInternationalCreditDisplayUOM;
	}

	public String getInternationalMinutesUOM() {
		return internationalMinutesUOM;
	}

	public void setInternationalMinutesUOM(String internationalMinutesUOM) {
		this.internationalMinutesUOM = internationalMinutesUOM;
	}

	public String getInternationalMinutesValue() {
		return internationalMinutesValue;
	}

	public void setInternationalMinutesValue(String internationalMinutesValue) {
		this.internationalMinutesValue = internationalMinutesValue;
	}

	public String getInternationalMinutesDisplayUOM() {
		return internationalMinutesDisplayUOM;
	}

	public void setInternationalMinutesDisplayUOM(String internationalMinutesDisplayUOM) {
		this.internationalMinutesDisplayUOM = internationalMinutesDisplayUOM;
	}

	public String getVodafoneVodafoneUOM() {
		return vodafoneVodafoneUOM;
	}

	public void setVodafoneVodafoneUOM(String vodafoneVodafoneUOM) {
		this.vodafoneVodafoneUOM = vodafoneVodafoneUOM;
	}

	public String getVodafoneVodafoneValue() {
		return vodafoneVodafoneValue;
	}

	public void setVodafoneVodafoneValue(String vodafoneVodafoneValue) {
		this.vodafoneVodafoneValue = vodafoneVodafoneValue;
	}

	public String getVodafoneVodafoneDisplayUOM() {
		return vodafoneVodafoneDisplayUOM;
	}

	public void setVodafoneVodafoneDisplayUOM(String vodafoneVodafoneDisplayUOM) {
		this.vodafoneVodafoneDisplayUOM = vodafoneVodafoneDisplayUOM;
	}

	public String getVodafoneVodafoneMinutesUOM() {
		return vodafoneVodafoneMinutesUOM;
	}

	public void setVodafoneVodafoneMinutesUOM(String vodafoneVodafoneMinutesUOM) {
		this.vodafoneVodafoneMinutesUOM = vodafoneVodafoneMinutesUOM;
	}

	public String getVodafoneVodafoneMinutesValue() {
		return vodafoneVodafoneMinutesValue;
	}

	public void setVodafoneVodafoneMinutesValue(String vodafoneVodafoneMinutesValue) {
		this.vodafoneVodafoneMinutesValue = vodafoneVodafoneMinutesValue;
	}

	public String getVodafoneVodafoneMinutesDisplayUOM() {
		return vodafoneVodafoneMinutesDisplayUOM;
	}

	public void setVodafoneVodafoneMinutesDisplayUOM(String vodafoneVodafoneMinutesDisplayUOM) {
		this.vodafoneVodafoneMinutesDisplayUOM = vodafoneVodafoneMinutesDisplayUOM;
	}

	public String getLandlineMinutesUOM() {
		return landlineMinutesUOM;
	}

	public void setLandlineMinutesUOM(String landlineMinutesUOM) {
		this.landlineMinutesUOM = landlineMinutesUOM;
	}

	public String getLandlineMinutesValue() {
		return landlineMinutesValue;
	}

	public void setLandlineMinutesValue(String landlineMinutesValue) {
		this.landlineMinutesValue = landlineMinutesValue;
	}

	public String getLandlineMinutesDisplayUOM() {
		return landlineMinutesDisplayUOM;
	}

	public void setLandlineMinutesDisplayUOM(String landlineMinutesDisplayUOM) {
		this.landlineMinutesDisplayUOM = landlineMinutesDisplayUOM;
	}

	public String getIsDisplayableAcq() {
		return isDisplayableAcq;
	}

	public void setIsDisplayableAcq(String isDisplayableAcq) {
		this.isDisplayableAcq = isDisplayableAcq;
	}

	public String getIsSellableECare() {
		return isSellableECare;
	}

	public void setIsSellableECare(String isSellableECare) {
		this.isSellableECare = isSellableECare;
	}

	public String getIsSellableRet() {
		return isSellableRet;
	}

	public void setIsSellableRet(String isSellableRet) {
		this.isSellableRet = isSellableRet;
	}

	public String getIsDisplayableRet() {
		return isDisplayableRet;
	}

	public void setIsDisplayableRet(String isDisplayableRet) {
		this.isDisplayableRet = isDisplayableRet;
	}

	public String getIsDisplaybaleSavedBasket() {
		return isDisplaybaleSavedBasket;
	}

	public void setIsDisplaybaleSavedBasket(String isDisplaybaleSavedBasket) {
		this.isDisplaybaleSavedBasket = isDisplaybaleSavedBasket;
	}

	public String getIsSellableACQ() {
		return isSellableACQ;
	}

	public void setIsSellableACQ(String isSellableACQ) {
		this.isSellableACQ = isSellableACQ;
	}

	public List<String> getPromoteAs() {
		return promoteAs;
	}

	public void setPromoteAs(List<String> promoteAs) {
		this.promoteAs = promoteAs;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getMerchandisingMedia() {
		return merchandisingMedia;
	}

	public void setMerchandisingMedia(List<String> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
	}

	public List<String> getAllowanceList() {
		return allowanceList;
	}

	public void setAllowanceList(List<String> allowanceList) {
		this.allowanceList = allowanceList;
	}

	public String getPlanCoupleId() {
		return planCoupleId;
	}

	public void setPlanCoupleId(String planCoupleId) {
		this.planCoupleId = planCoupleId;
	}

	public String getPlanCoupleFlag() {
		return planCoupleFlag;
	}

	public void setPlanCoupleFlag(String planCoupleFlag) {
		this.planCoupleFlag = planCoupleFlag;
	}

	public String getPlanCoupleLabel() {
		return planCoupleLabel;
	}

	public void setPlanCoupleLabel(String planCoupleLabel) {
		this.planCoupleLabel = planCoupleLabel;
	}

	public Float getMonthlyGrossPrice() {
		return monthlyGrossPrice;
	}

	public void setMonthlyGrossPrice(Float monthlyGrossPrice) {
		this.monthlyGrossPrice = monthlyGrossPrice;
	}

	public Float getMonthlyNetPrice() {
		return monthlyNetPrice;
	}

	public void setMonthlyNetPrice(Float monthlyNetPrice) {
		this.monthlyNetPrice = monthlyNetPrice;
	}

	public Float getMonthlyVatPrice() {
		return monthlyVatPrice;
	}

	public void setMonthlyVatPrice(Float monthlyVatPrice) {
		this.monthlyVatPrice = monthlyVatPrice;
	}

	public Float getMonthlyDiscountedGrossPrice() {
		return monthlyDiscountedGrossPrice;
	}

	public void setMonthlyDiscountedGrossPrice(Float monthlyDiscountedGrossPrice) {
		this.monthlyDiscountedGrossPrice = monthlyDiscountedGrossPrice;
	}

	public Float getMonthlyDiscountedNetPrice() {
		return monthlyDiscountedNetPrice;
	}

	public void setMonthlyDiscountedNetPrice(Float monthlyDiscountedNetPrice) {
		this.monthlyDiscountedNetPrice = monthlyDiscountedNetPrice;
	}

	public Float getMonthlyDiscountedVatPrice() {
		return monthlyDiscountedVatPrice;
	}

	public void setMonthlyDiscountedVatPrice(Float monthlyDiscountedVatPrice) {
		this.monthlyDiscountedVatPrice = monthlyDiscountedVatPrice;
	}

	public List<String> getListOfCompatibleProducts() {
		return listOfCompatibleProducts;
	}

	public void setListOfCompatibleProducts(List<String> listOfCompatibleProducts) {
		this.listOfCompatibleProducts = listOfCompatibleProducts;
	}

	public Boolean getSecureNetFlag() {
		return secureNetFlag;
	}

	public void setSecureNetFlag(Boolean secureNetFlag) {
		this.secureNetFlag = secureNetFlag;
	}

	public Boolean getGlobalRoamingFlag() {
		return globalRoamingFlag;
	}

	public void setGlobalRoamingFlag(Boolean globalRoamingFlag) {
		this.globalRoamingFlag = globalRoamingFlag;
	}

	public Date getStartDateFormatted() {
		return startDateFormatted;
	}

	public void setStartDateFormatted(Date startDateFormatted) {
		this.startDateFormatted = startDateFormatted;
	}

	public Date getEndDateFormatted() {
		return endDateFormatted;
	}

	public void setEndDateFormatted(Date endDateFormatted) {
		this.endDateFormatted = endDateFormatted;
	}

	public String getRouterDeviceId() {
		return routerDeviceId;
	}

	public void setRouterDeviceId(String routerDeviceId) {
		this.routerDeviceId = routerDeviceId;
	}

	public String getFlbbSpendLimit() {
		return flbbSpendLimit;
	}

	public void setFlbbSpendLimit(String flbbSpendLimit) {
		this.flbbSpendLimit = flbbSpendLimit;
	}

	public String getFlbbUsage() {
		return flbbUsage;
	}

	public void setFlbbUsage(String flbbUsage) {
		this.flbbUsage = flbbUsage;
	}

	public String getSpeedLimitAndUsage() {
		return speedLimitAndUsage;
	}

	public void setSpeedLimitAndUsage(String speedLimitAndUsage) {
		this.speedLimitAndUsage = speedLimitAndUsage;
	}

	public List<String> getMiscKeyValue() {
		return miscKeyValue;
	}

	public void setMiscKeyValue(List<String> miscKeyValue) {
		this.miscKeyValue = miscKeyValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<String> getSeoRobots() {
		return seoRobots;
	}

	public void setSeoRobots(List<String> seoRobots) {
		this.seoRobots = seoRobots;
	}
	
	
}
