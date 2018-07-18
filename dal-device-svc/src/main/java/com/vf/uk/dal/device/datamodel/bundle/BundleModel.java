package com.vf.uk.dal.device.datamodel.bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	@JsonProperty(value = "serviceProductsOpt")
	private List<String> serviceProducts = new ArrayList<>();
	@JsonProperty(value = "relationshipsOpt")
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
	@JsonProperty(value = "migrationPathsOpt")
	private List<String> migrationPaths;
	@JsonProperty(value = "upgradePathsOpt")
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

	private String etdataUOM;

	private String etdataValue;

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

	@JsonProperty(value = "promoteAsOpt")
	private List<String> promoteAs;

	private String endDate;

	private List<String> merchandisingMedia = new ArrayList<>();

	@JsonProperty("allowance")
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

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date startDateFormatted;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date endDateFormatted;

	private String routerDeviceId;

	private String flbbSpendLimit;

	private String flbbUsage;

	private String speedLimitAndUsage;

	private List<String> miscKeyValue;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date createDate;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date modifiedDate;

	private List<String> seoRobots;

	/**
	 * 
	 */
	public BundleModel() {
		/*
		 * 
		 */
	}

	/**
	 * @return the affiliateExport
	 */
	public String getAffiliateExport() {
		return affiliateExport;
	}

	/**
	 * @return the allowanceList
	 */
	public List<String> getAllowanceList() {
		return allowanceList;
	}

	/**
	 * @return the bundleId
	 */
	public String getBundleId() {
		return bundleId;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @return the charge
	 */
	public String getCharge() {
		return charge;
	}

	/**
	 * @return the commitmentPenalty
	 */
	public String getCommitmentPenalty() {
		return commitmentPenalty;
	}

	/**
	 * @return the commitmentPeriod
	 */
	public String getCommitmentPeriod() {
		return commitmentPeriod;
	}

	/**
	 * @return the compareWith
	 */
	public String getCompareWith() {
		return compareWith;
	}

	/**
	 * @return the contentDesc
	 */
	public String getContentDesc() {
		return contentDesc;
	}

	/**
	 * @return the dataDisplayUOM
	 */
	public String getDataDisplayUOM() {
		return dataDisplayUOM;
	}

	/**
	 * @return the dataUOM
	 */
	public String getDataUOM() {
		return dataUOM;
	}

	/**
	 * @return the dataValue
	 */
	public String getDataValue() {
		return dataValue;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return the descMobile
	 */
	public String getDescMobile() {
		return descMobile;
	}

	/**
	 * @return the displayGroup
	 */
	public String getDisplayGroup() {
		return displayGroup;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the eligibilitySubflow
	 */
	public String getEligibilitySubflow() {
		return eligibilitySubflow;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @return the etdataDisplayUOM
	 */
	public String getEtdataDisplayUOM() {
		return etdataDisplayUOM;
	}

	/**
	 * @return the etdataUOM
	 */
	public String getEtdataUOM() {
		return etdataUOM;
	}

	/**
	 * @return the etdataValue
	 */
	public String getEtdataValue() {
		return etdataValue;
	}

	/**
	 * @return the europeanRoamingDataDisplayUOM
	 */
	public String getEuropeanRoamingDataDisplayUOM() {
		return europeanRoamingDataDisplayUOM;
	}

	/**
	 * @return the europeanRoamingDataUOM
	 */
	public String getEuropeanRoamingDataUOM() {
		return europeanRoamingDataUOM;
	}

	/**
	 * @return the europeanRoamingDataValue
	 */
	public String getEuropeanRoamingDataValue() {
		return europeanRoamingDataValue;
	}

	/**
	 * @return the europeanRoamingMinutesDisplayUOM
	 */
	public String getEuropeanRoamingMinutesDisplayUOM() {
		return europeanRoamingMinutesDisplayUOM;
	}

	/**
	 * @return the europeanRoamingMinutesUOM
	 */
	public String getEuropeanRoamingMinutesUOM() {
		return europeanRoamingMinutesUOM;
	}

	/**
	 * @return the europeanRoamingMinutesValue
	 */
	public String getEuropeanRoamingMinutesValue() {
		return europeanRoamingMinutesValue;
	}

	/**
	 * @return the europeanRoamingPictureMessagesDisplayUOM
	 */
	public String getEuropeanRoamingPictureMessagesDisplayUOM() {
		return europeanRoamingPictureMessagesDisplayUOM;
	}

	/**
	 * @return the europeanRoamingPictureMessagesUOM
	 */
	public String getEuropeanRoamingPictureMessagesUOM() {
		return europeanRoamingPictureMessagesUOM;
	}

	/**
	 * @return the europeanRoamingPictureMessagesValue
	 */
	public String getEuropeanRoamingPictureMessagesValue() {
		return europeanRoamingPictureMessagesValue;
	}

	/**
	 * @return the europeanRoamingTextsDisplayUOM
	 */
	public String getEuropeanRoamingTextsDisplayUOM() {
		return europeanRoamingTextsDisplayUOM;
	}

	/**
	 * @return the europeanRoamingTextsUOM
	 */
	public String getEuropeanRoamingTextsUOM() {
		return europeanRoamingTextsUOM;
	}

	/**
	 * @return the europeanRoamingTextsValue
	 */
	public String getEuropeanRoamingTextsValue() {
		return europeanRoamingTextsValue;
	}

	/**
	 * @return the fullDetails
	 */
	public String getFullDetails() {
		return fullDetails;
	}

	/**
	 * @return the fullDetailsMobile
	 */
	public String getFullDetailsMobile() {
		return fullDetailsMobile;
	}

	/**
	 * @return the globalRoamingDataDisplayUOM
	 */
	public String getGlobalRoamingDataDisplayUOM() {
		return globalRoamingDataDisplayUOM;
	}

	/**
	 * @return the globalRoamingDataUOM
	 */
	public String getGlobalRoamingDataUOM() {
		return globalRoamingDataUOM;
	}

	/**
	 * @return the globalRoamingDataValue
	 */
	public String getGlobalRoamingDataValue() {
		return globalRoamingDataValue;
	}

	/**
	 * @return the globalRoamingMinutesDisplayUOM
	 */
	public String getGlobalRoamingMinutesDisplayUOM() {
		return globalRoamingMinutesDisplayUOM;
	}

	/**
	 * @return the globalRoamingMinutesUOM
	 */
	public String getGlobalRoamingMinutesUOM() {
		return globalRoamingMinutesUOM;
	}

	/**
	 * @return the globalRoamingMinutesValue
	 */
	public String getGlobalRoamingMinutesValue() {
		return globalRoamingMinutesValue;
	}

	/**
	 * @return the globalRoamingTextsDisplayUOM
	 */
	public String getGlobalRoamingTextsDisplayUOM() {
		return globalRoamingTextsDisplayUOM;
	}

	/**
	 * @return the globalRoamingTextsUOM
	 */
	public String getGlobalRoamingTextsUOM() {
		return globalRoamingTextsUOM;
	}

	/**
	 * @return the globalRoamingTextsValue
	 */
	public String getGlobalRoamingTextsValue() {
		return globalRoamingTextsValue;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the imageURLsFullBack
	 */
	public String getImageURLsFullBack() {
		return imageURLsFullBack;
	}

	/**
	 * @return the imageURLsFullFront
	 */
	public String getImageURLsFullFront() {
		return imageURLsFullFront;
	}

	/**
	 * @return the imageURLsFullLeft
	 */
	public String getImageURLsFullLeft() {
		return imageURLsFullLeft;
	}

	/**
	 * @return the imageURLsFullRight
	 */
	public String getImageURLsFullRight() {
		return imageURLsFullRight;
	}

	/**
	 * @return the imageURLsFullSide
	 */
	public String getImageURLsFullSide() {
		return imageURLsFullSide;
	}

	/**
	 * @return the imageURLsGrid
	 */
	public String getImageURLsGrid() {
		return imageURLsGrid;
	}

	/**
	 * @return the imageURLsIcon
	 */
	public String getImageURLsIcon() {
		return imageURLsIcon;
	}

	/**
	 * @return the imageURLsSmall
	 */
	public String getImageURLsSmall() {
		return imageURLsSmall;
	}

	/**
	 * @return the imageURLsSticker
	 */
	public String getImageURLsSticker() {
		return imageURLsSticker;
	}

	/**
	 * @return the imageURLsThumbsBack
	 */
	public String getImageURLsThumbsBack() {
		return imageURLsThumbsBack;
	}

	/**
	 * @return the imageURLsThumbsFront
	 */
	public String getImageURLsThumbsFront() {
		return imageURLsThumbsFront;
	}

	/**
	 * @return the imageURLsThumbsLeft
	 */
	public String getImageURLsThumbsLeft() {
		return imageURLsThumbsLeft;
	}

	/**
	 * @return the imageURLsThumbsRight
	 */
	public String getImageURLsThumbsRight() {
		return imageURLsThumbsRight;
	}

	/**
	 * @return the imageURLsThumbsSide
	 */
	public String getImageURLsThumbsSide() {
		return imageURLsThumbsSide;
	}

	/**
	 * @return the internationalMinutesDisplayUOM
	 */
	public String getInternationalMinutesDisplayUOM() {
		return internationalMinutesDisplayUOM;
	}

	/**
	 * @return the internationalMinutesUOM
	 */
	public String getInternationalMinutesUOM() {
		return internationalMinutesUOM;
	}

	/**
	 * @return the internationalMinutesValue
	 */
	public String getInternationalMinutesValue() {
		return internationalMinutesValue;
	}

	/**
	 * @return the internationalTextsDisplayUOM
	 */
	public String getInternationalTextsDisplayUOM() {
		return internationalTextsDisplayUOM;
	}

	/**
	 * @return the internationalTextsUOM
	 */
	public String getInternationalTextsUOM() {
		return internationalTextsUOM;
	}

	/**
	 * @return the internationalTextsValue
	 */
	public String getInternationalTextsValue() {
		return internationalTextsValue;
	}

	/**
	 * @return the isDisplayableAcq
	 */
	public String getIsDisplayableAcq() {
		return isDisplayableAcq;
	}

	/**
	 * @return the isDisplayableRet
	 */
	public String getIsDisplayableRet() {
		return isDisplayableRet;
	}

	/**
	 * @return the isDisplaybaleSavedBasket
	 */
	public String getIsDisplaybaleSavedBasket() {
		return isDisplaybaleSavedBasket;
	}

	/**
	 * @return the isSellableACQ
	 */
	public String getIsSellableACQ() {
		return isSellableACQ;
	}

	/**
	 * @return the isSellableECare
	 */
	public String getIsSellableECare() {
		return isSellableECare;
	}

	/**
	 * @return the isSellableRet
	 */
	public String getIsSellableRet() {
		return isSellableRet;
	}

	/**
	 * @return the landlineMinutesDisplayUOM
	 */
	public String getLandlineMinutesDisplayUOM() {
		return landlineMinutesDisplayUOM;
	}

	/**
	 * @return the landlineMinutesUOM
	 */
	public String getLandlineMinutesUOM() {
		return landlineMinutesUOM;
	}

	/**
	 * @return the landlineMinutesValue
	 */
	public String getLandlineMinutesValue() {
		return landlineMinutesValue;
	}

	public List<String> getListOfCompatibleProducts() {
		return listOfCompatibleProducts;
	}

	public List<String> getMerchandisingMedia() {
		return merchandisingMedia;
	}

	/**
	 * @return the migrationPaths
	 */
	public List<String> getMigrationPaths() {
		return migrationPaths;
	}

	/**
	 * @return the monthlyDiscountedGrossPrice
	 */
	public Float getMonthlyDiscountedGrossPrice() {
		return monthlyDiscountedGrossPrice;
	}

	/**
	 * @return the monthlyDiscountedNetPrice
	 */
	public Float getMonthlyDiscountedNetPrice() {
		return monthlyDiscountedNetPrice;
	}

	/**
	 * @return the monthlyDiscountedVatPrice
	 */
	public Float getMonthlyDiscountedVatPrice() {
		return monthlyDiscountedVatPrice;
	}

	/**
	 * @return the monthlyGrossPrice
	 */
	public Float getMonthlyGrossPrice() {
		return monthlyGrossPrice;
	}

	/**
	 * @return the monthlyNetPrice
	 */
	public Float getMonthlyNetPrice() {
		return monthlyNetPrice;
	}

	/**
	 * @return the monthlyVatPrice
	 */
	public Float getMonthlyVatPrice() {
		return monthlyVatPrice;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @return the pictureMessagesDisplayUOM
	 */
	public String getPictureMessagesDisplayUOM() {
		return pictureMessagesDisplayUOM;
	}

	/**
	 * @return the pictureMessagesUOM
	 */
	public String getPictureMessagesUOM() {
		return pictureMessagesUOM;
	}

	/**
	 * @return the pictureMessagesValue
	 */
	public String getPictureMessagesValue() {
		return pictureMessagesValue;
	}

	/**
	 * @return the planCoupleFlag
	 */
	public String getPlanCoupleFlag() {
		return planCoupleFlag;
	}

	/**
	 * @return the planCoupleId
	 */
	public String getPlanCoupleId() {
		return planCoupleId;
	}

	/**
	 * @return the planCoupleLabel
	 */
	public String getPlanCoupleLabel() {
		return planCoupleLabel;
	}

	/**
	 * @return the postSaleJourney
	 */
	public String getPostSaleJourney() {
		return postSaleJourney;
	}

	/**
	 * @return the productGroupName
	 */
	public String getProductGroupName() {
		return productGroupName;
	}

	/**
	 * @return the productLines
	 */
	public List<String> getProductLines() {
		return productLines;
	}

	/**
	 * @return the promoteAs
	 */
	public List<String> getPromoteAs() {
		return promoteAs;
	}

	/**
	 * @return the recommendedAccessories
	 */
	public String getRecommendedAccessories() {
		return recommendedAccessories;
	}

	/**
	 * @return the recommendedExtras
	 */
	public String getRecommendedExtras() {
		return recommendedExtras;
	}

	/**
	 * @return the recurringCharge
	 */
	public float getRecurringCharge() {
		return recurringCharge;
	}

	/**
	 * @return the relationships
	 */
	public List<String> getRelationships() {
		return relationships;
	}

	/**
	 * @return the roamingDataDisplayUOM
	 */
	public String getRoamingDataDisplayUOM() {
		return roamingDataDisplayUOM;
	}

	/**
	 * @return the roamingDataUOM
	 */
	public String getRoamingDataUOM() {
		return roamingDataUOM;
	}

	/**
	 * @return the roamingDataValue
	 */
	public String getRoamingDataValue() {
		return roamingDataValue;
	}

	/**
	 * @return the salesExpired
	 */
	public String getSalesExpired() {
		return salesExpired;
	}

	/**
	 * @return the seoCanonical
	 */
	public String getSeoCanonical() {
		return seoCanonical;
	}

	/**
	 * @return the seoDescription
	 */
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * @return the seoIndex
	 */
	public String getSeoIndex() {
		return seoIndex;
	}

	/**
	 * @return the seoKeywords
	 */
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * @return the serviceProducts
	 */
	public List<String> getServiceProducts() {
		return serviceProducts;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @return the support
	 */
	public String getSupport() {
		return support;
	}

	/**
	 * @return the threeDSpin
	 */
	public String getThreeDSpin() {
		return threeDSpin;
	}

	/**
	 * @return the uKANDInternationalCreditDisplayUOM
	 */
	public String getuKANDInternationalCreditDisplayUOM() {
		return uKANDInternationalCreditDisplayUOM;
	}

	/**
	 * @return the uKANDInternationalCreditUOM
	 */
	public String getuKANDInternationalCreditUOM() {
		return uKANDInternationalCreditUOM;
	}

	/**
	 * @return the uKANDInternationalCreditValue
	 */
	public String getuKANDInternationalCreditValue() {
		return uKANDInternationalCreditValue;
	}

	/**
	 * @return the ukDataDisplayUOM
	 */
	public String getUkDataDisplayUOM() {
		return ukDataDisplayUOM;
	}

	/**
	 * @return the ukDataUOM
	 */
	public String getUkDataUOM() {
		return ukDataUOM;
	}

	/**
	 * @return the ukDataValue
	 */
	public String getUkDataValue() {
		return ukDataValue;
	}

	/**
	 * @return the ukMinutesDisplayUOM
	 */
	public String getUkMinutesDisplayUOM() {
		return ukMinutesDisplayUOM;
	}

	/**
	 * @return the ukMinutesUOM
	 */
	public String getUkMinutesUOM() {
		return ukMinutesUOM;
	}

	/**
	 * @return the ukMinutesValue
	 */
	public String getUkMinutesValue() {
		return ukMinutesValue;
	}

	/**
	 * @return the uKmobileMinutesDisplayUOM
	 */
	public String getuKmobileMinutesDisplayUOM() {
		return uKmobileMinutesDisplayUOM;
	}

	/**
	 * @return the uKmobileMinutesUOM
	 */
	public String getuKmobileMinutesUOM() {
		return uKmobileMinutesUOM;
	}

	/**
	 * @return the uKmobileMinutesValue
	 */
	public String getuKmobileMinutesValue() {
		return uKmobileMinutesValue;
	}

	/**
	 * @return the ukTextsDisplayUOM
	 */
	public String getUkTextsDisplayUOM() {
		return ukTextsDisplayUOM;
	}

	/**
	 * @return the ukTextsUOM
	 */
	public String getUkTextsUOM() {
		return ukTextsUOM;
	}

	/**
	 * @return the ukTextsValue
	 */
	public String getUkTextsValue() {
		return ukTextsValue;
	}

	/**
	 * @return the upgradePaths
	 */
	public List<String> getUpgradePaths() {
		return upgradePaths;
	}

	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @return the vodafoneVodafoneDisplayUOM
	 */
	public String getVodafoneVodafoneDisplayUOM() {
		return vodafoneVodafoneDisplayUOM;
	}

	/**
	 * @return the vodafoneVodafoneMinutesDisplayUOM
	 */
	public String getVodafoneVodafoneMinutesDisplayUOM() {
		return vodafoneVodafoneMinutesDisplayUOM;
	}

	/**
	 * @return the vodafoneVodafoneMinutesUOM
	 */
	public String getVodafoneVodafoneMinutesUOM() {
		return vodafoneVodafoneMinutesUOM;
	}

	/**
	 * @return the vodafoneVodafoneMinutesValue
	 */
	public String getVodafoneVodafoneMinutesValue() {
		return vodafoneVodafoneMinutesValue;
	}

	/**
	 * @return the vodafoneVodafonetextsDisplayUOM
	 */
	public String getVodafoneVodafonetextsDisplayUOM() {
		return vodafoneVodafonetextsDisplayUOM;
	}

	/**
	 * @return the vodafoneVodafonetextsUOM
	 */
	public String getVodafoneVodafonetextsUOM() {
		return vodafoneVodafonetextsUOM;
	}

	/**
	 * @return the vodafoneVodafonetextsValue
	 */
	public String getVodafoneVodafonetextsValue() {
		return vodafoneVodafonetextsValue;
	}

	/**
	 * @return the vodafoneVodafoneUOM
	 */
	public String getVodafoneVodafoneUOM() {
		return vodafoneVodafoneUOM;
	}

	/**
	 * @return the vodafoneVodafoneValue
	 */
	public String getVodafoneVodafoneValue() {
		return vodafoneVodafoneValue;
	}

	/**
	 * @return the wifiDisplayUOM
	 */
	public String getWifiDisplayUOM() {
		return wifiDisplayUOM;
	}

	/**
	 * @return the wifiUOM
	 */
	public String getWifiUOM() {
		return wifiUOM;
	}

	/**
	 * @return the wifiValue
	 */
	public String getWifiValue() {
		return wifiValue;
	}

	/**
	 * @param affiliateExport
	 *            the affiliateExport to set
	 */
	public void setAffiliateExport(String affiliateExport) {
		this.affiliateExport = affiliateExport;
	}

	/**
	 * @param allowanceList
	 *            the allowanceList to set
	 */
	public void setAllowanceList(List<String> allowanceList) {
		this.allowanceList = allowanceList;
	}

	/**
	 * @param bundleId
	 *            the bundleId to set
	 */
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * @param charge
	 *            the charge to set
	 */
	public void setCharge(String charge) {
		this.charge = charge;
	}

	/**
	 * @param commitmentPenalty
	 *            the commitmentPenalty to set
	 */
	public void setCommitmentPenalty(String commitmentPenalty) {
		this.commitmentPenalty = commitmentPenalty;
	}

	/**
	 * @param commitmentPeriod
	 *            the commitmentPeriod to set
	 */
	public void setCommitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

	/**
	 * @param compareWith
	 *            the compareWith to set
	 */
	public void setCompareWith(String compareWith) {
		this.compareWith = compareWith;
	}

	/**
	 * @param contentDesc
	 *            the contentDesc to set
	 */
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	/**
	 * @param dataDisplayUOM
	 *            the dataDisplayUOM to set
	 */
	public void setDataDisplayUOM(String dataDisplayUOM) {
		this.dataDisplayUOM = dataDisplayUOM;
	}

	/**
	 * @param dataUOM
	 *            the dataUOM to set
	 */
	public void setDataUOM(String dataUOM) {
		this.dataUOM = dataUOM;
	}

	/**
	 * @param dataValue
	 *            the dataValue to set
	 */
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @param descMobile
	 *            the descMobile to set
	 */
	public void setDescMobile(String descMobile) {
		this.descMobile = descMobile;
	}

	/**
	 * @param displayGroup
	 *            the displayGroup to set
	 */
	public void setDisplayGroup(String displayGroup) {
		this.displayGroup = displayGroup;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param eligibilitySubflow
	 *            the eligibilitySubflow to set
	 */
	public void setEligibilitySubflow(String eligibilitySubflow) {
		this.eligibilitySubflow = eligibilitySubflow;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param etdataDisplayUOM
	 *            the etdataDisplayUOM to set
	 */
	public void setEtdataDisplayUOM(String etdataDisplayUOM) {
		this.etdataDisplayUOM = etdataDisplayUOM;
	}

	/**
	 * @param etdataUOM
	 *            the etdataUOM to set
	 */
	public void setEtdataUOM(String etdataUOM) {
		this.etdataUOM = etdataUOM;
	}

	/**
	 * @param etdataValue
	 *            the etdataValue to set
	 */
	public void setEtdataValue(String etdataValue) {
		this.etdataValue = etdataValue;
	}

	/**
	 * @param europeanRoamingDataDisplayUOM
	 *            the europeanRoamingDataDisplayUOM to set
	 */
	public void setEuropeanRoamingDataDisplayUOM(String europeanRoamingDataDisplayUOM) {
		this.europeanRoamingDataDisplayUOM = europeanRoamingDataDisplayUOM;
	}

	/**
	 * @param europeanRoamingDataUOM
	 *            the europeanRoamingDataUOM to set
	 */
	public void setEuropeanRoamingDataUOM(String europeanRoamingDataUOM) {
		this.europeanRoamingDataUOM = europeanRoamingDataUOM;
	}

	/**
	 * @param europeanRoamingDataValue
	 *            the europeanRoamingDataValue to set
	 */
	public void setEuropeanRoamingDataValue(String europeanRoamingDataValue) {
		this.europeanRoamingDataValue = europeanRoamingDataValue;
	}

	/**
	 * @param europeanRoamingMinutesDisplayUOM
	 *            the europeanRoamingMinutesDisplayUOM to set
	 */
	public void setEuropeanRoamingMinutesDisplayUOM(String europeanRoamingMinutesDisplayUOM) {
		this.europeanRoamingMinutesDisplayUOM = europeanRoamingMinutesDisplayUOM;
	}

	/**
	 * @param europeanRoamingMinutesUOM
	 *            the europeanRoamingMinutesUOM to set
	 */
	public void setEuropeanRoamingMinutesUOM(String europeanRoamingMinutesUOM) {
		this.europeanRoamingMinutesUOM = europeanRoamingMinutesUOM;
	}

	/**
	 * @param europeanRoamingMinutesValue
	 *            the europeanRoamingMinutesValue to set
	 */
	public void setEuropeanRoamingMinutesValue(String europeanRoamingMinutesValue) {
		this.europeanRoamingMinutesValue = europeanRoamingMinutesValue;
	}

	/**
	 * @param europeanRoamingPictureMessagesDisplayUOM
	 *            the europeanRoamingPictureMessagesDisplayUOM to set
	 */
	public void setEuropeanRoamingPictureMessagesDisplayUOM(String europeanRoamingPictureMessagesDisplayUOM) {
		this.europeanRoamingPictureMessagesDisplayUOM = europeanRoamingPictureMessagesDisplayUOM;
	}

	/**
	 * @param europeanRoamingPictureMessagesUOM
	 *            the europeanRoamingPictureMessagesUOM to set
	 */
	public void setEuropeanRoamingPictureMessagesUOM(String europeanRoamingPictureMessagesUOM) {
		this.europeanRoamingPictureMessagesUOM = europeanRoamingPictureMessagesUOM;
	}

	/**
	 * @param europeanRoamingPictureMessagesValue
	 *            the europeanRoamingPictureMessagesValue to set
	 */
	public void setEuropeanRoamingPictureMessagesValue(String europeanRoamingPictureMessagesValue) {
		this.europeanRoamingPictureMessagesValue = europeanRoamingPictureMessagesValue;
	}

	/**
	 * @param europeanRoamingTextsDisplayUOM
	 *            the europeanRoamingTextsDisplayUOM to set
	 */
	public void setEuropeanRoamingTextsDisplayUOM(String europeanRoamingTextsDisplayUOM) {
		this.europeanRoamingTextsDisplayUOM = europeanRoamingTextsDisplayUOM;
	}

	/**
	 * @param europeanRoamingTextsUOM
	 *            the europeanRoamingTextsUOM to set
	 */
	public void setEuropeanRoamingTextsUOM(String europeanRoamingTextsUOM) {
		this.europeanRoamingTextsUOM = europeanRoamingTextsUOM;
	}

	/**
	 * @param europeanRoamingTextsValue
	 *            the europeanRoamingTextsValue to set
	 */
	public void setEuropeanRoamingTextsValue(String europeanRoamingTextsValue) {
		this.europeanRoamingTextsValue = europeanRoamingTextsValue;
	}

	/**
	 * @param fullDetails
	 *            the fullDetails to set
	 */
	public void setFullDetails(String fullDetails) {
		this.fullDetails = fullDetails;
	}

	/**
	 * @param fullDetailsMobile
	 *            the fullDetailsMobile to set
	 */
	public void setFullDetailsMobile(String fullDetailsMobile) {
		this.fullDetailsMobile = fullDetailsMobile;
	}

	/**
	 * @param globalRoamingDataDisplayUOM
	 *            the globalRoamingDataDisplayUOM to set
	 */
	public void setGlobalRoamingDataDisplayUOM(String globalRoamingDataDisplayUOM) {
		this.globalRoamingDataDisplayUOM = globalRoamingDataDisplayUOM;
	}

	/**
	 * @param globalRoamingDataUOM
	 *            the globalRoamingDataUOM to set
	 */
	public void setGlobalRoamingDataUOM(String globalRoamingDataUOM) {
		this.globalRoamingDataUOM = globalRoamingDataUOM;
	}

	/**
	 * @param globalRoamingDataValue
	 *            the globalRoamingDataValue to set
	 */
	public void setGlobalRoamingDataValue(String globalRoamingDataValue) {
		this.globalRoamingDataValue = globalRoamingDataValue;
	}

	/**
	 * @param globalRoamingMinutesDisplayUOM
	 *            the globalRoamingMinutesDisplayUOM to set
	 */
	public void setGlobalRoamingMinutesDisplayUOM(String globalRoamingMinutesDisplayUOM) {
		this.globalRoamingMinutesDisplayUOM = globalRoamingMinutesDisplayUOM;
	}

	/**
	 * @param globalRoamingMinutesUOM
	 *            the globalRoamingMinutesUOM to set
	 */
	public void setGlobalRoamingMinutesUOM(String globalRoamingMinutesUOM) {
		this.globalRoamingMinutesUOM = globalRoamingMinutesUOM;
	}

	/**
	 * @param globalRoamingMinutesValue
	 *            the globalRoamingMinutesValue to set
	 */
	public void setGlobalRoamingMinutesValue(String globalRoamingMinutesValue) {
		this.globalRoamingMinutesValue = globalRoamingMinutesValue;
	}

	/**
	 * @param globalRoamingTextsDisplayUOM
	 *            the globalRoamingTextsDisplayUOM to set
	 */
	public void setGlobalRoamingTextsDisplayUOM(String globalRoamingTextsDisplayUOM) {
		this.globalRoamingTextsDisplayUOM = globalRoamingTextsDisplayUOM;
	}

	/**
	 * @param globalRoamingTextsUOM
	 *            the globalRoamingTextsUOM to set
	 */
	public void setGlobalRoamingTextsUOM(String globalRoamingTextsUOM) {
		this.globalRoamingTextsUOM = globalRoamingTextsUOM;
	}

	/**
	 * @param globalRoamingTextsValue
	 *            the globalRoamingTextsValue to set
	 */
	public void setGlobalRoamingTextsValue(String globalRoamingTextsValue) {
		this.globalRoamingTextsValue = globalRoamingTextsValue;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param imageURLsFullBack
	 *            the imageURLsFullBack to set
	 */
	public void setImageURLsFullBack(String imageURLsFullBack) {
		this.imageURLsFullBack = imageURLsFullBack;
	}

	/**
	 * @param imageURLsFullFront
	 *            the imageURLsFullFront to set
	 */
	public void setImageURLsFullFront(String imageURLsFullFront) {
		this.imageURLsFullFront = imageURLsFullFront;
	}

	/**
	 * @param imageURLsFullLeft
	 *            the imageURLsFullLeft to set
	 */
	public void setImageURLsFullLeft(String imageURLsFullLeft) {
		this.imageURLsFullLeft = imageURLsFullLeft;
	}

	/**
	 * @param imageURLsFullRight
	 *            the imageURLsFullRight to set
	 */
	public void setImageURLsFullRight(String imageURLsFullRight) {
		this.imageURLsFullRight = imageURLsFullRight;
	}

	/**
	 * @param imageURLsFullSide
	 *            the imageURLsFullSide to set
	 */
	public void setImageURLsFullSide(String imageURLsFullSide) {
		this.imageURLsFullSide = imageURLsFullSide;
	}

	/**
	 * @param imageURLsGrid
	 *            the imageURLsGrid to set
	 */
	public void setImageURLsGrid(String imageURLsGrid) {
		this.imageURLsGrid = imageURLsGrid;
	}

	/**
	 * @param imageURLsIcon
	 *            the imageURLsIcon to set
	 */
	public void setImageURLsIcon(String imageURLsIcon) {
		this.imageURLsIcon = imageURLsIcon;
	}

	/**
	 * @param imageURLsSmall
	 *            the imageURLsSmall to set
	 */
	public void setImageURLsSmall(String imageURLsSmall) {
		this.imageURLsSmall = imageURLsSmall;
	}

	/**
	 * @param imageURLsSticker
	 *            the imageURLsSticker to set
	 */
	public void setImageURLsSticker(String imageURLsSticker) {
		this.imageURLsSticker = imageURLsSticker;
	}

	/**
	 * @param imageURLsThumbsBack
	 *            the imageURLsThumbsBack to set
	 */
	public void setImageURLsThumbsBack(String imageURLsThumbsBack) {
		this.imageURLsThumbsBack = imageURLsThumbsBack;
	}

	/**
	 * @param imageURLsThumbsFront
	 *            the imageURLsThumbsFront to set
	 */
	public void setImageURLsThumbsFront(String imageURLsThumbsFront) {
		this.imageURLsThumbsFront = imageURLsThumbsFront;
	}

	/**
	 * @param imageURLsThumbsLeft
	 *            the imageURLsThumbsLeft to set
	 */
	public void setImageURLsThumbsLeft(String imageURLsThumbsLeft) {
		this.imageURLsThumbsLeft = imageURLsThumbsLeft;
	}

	/**
	 * @param imageURLsThumbsRight
	 *            the imageURLsThumbsRight to set
	 */
	public void setImageURLsThumbsRight(String imageURLsThumbsRight) {
		this.imageURLsThumbsRight = imageURLsThumbsRight;
	}

	/**
	 * @param imageURLsThumbsSide
	 *            the imageURLsThumbsSide to set
	 */
	public void setImageURLsThumbsSide(String imageURLsThumbsSide) {
		this.imageURLsThumbsSide = imageURLsThumbsSide;
	}

	/**
	 * @param internationalMinutesDisplayUOM
	 *            the internationalMinutesDisplayUOM to set
	 */
	public void setInternationalMinutesDisplayUOM(String internationalMinutesDisplayUOM) {
		this.internationalMinutesDisplayUOM = internationalMinutesDisplayUOM;
	}

	/**
	 * @param internationalMinutesUOM
	 *            the internationalMinutesUOM to set
	 */
	public void setInternationalMinutesUOM(String internationalMinutesUOM) {
		this.internationalMinutesUOM = internationalMinutesUOM;
	}

	/**
	 * @param internationalMinutesValue
	 *            the internationalMinutesValue to set
	 */
	public void setInternationalMinutesValue(String internationalMinutesValue) {
		this.internationalMinutesValue = internationalMinutesValue;
	}

	/**
	 * @param internationalTextsDisplayUOM
	 *            the internationalTextsDisplayUOM to set
	 */
	public void setInternationalTextsDisplayUOM(String internationalTextsDisplayUOM) {
		this.internationalTextsDisplayUOM = internationalTextsDisplayUOM;
	}

	/**
	 * @param internationalTextsUOM
	 *            the internationalTextsUOM to set
	 */
	public void setInternationalTextsUOM(String internationalTextsUOM) {
		this.internationalTextsUOM = internationalTextsUOM;
	}

	/**
	 * @param internationalTextsValue
	 *            the internationalTextsValue to set
	 */
	public void setInternationalTextsValue(String internationalTextsValue) {
		this.internationalTextsValue = internationalTextsValue;
	}

	/**
	 * @param isDisplayableAcq
	 *            the isDisplayableAcq to set
	 */
	public void setIsDisplayableAcq(String isDisplayableAcq) {
		this.isDisplayableAcq = isDisplayableAcq;
	}

	/**
	 * @param isDisplayableRet
	 *            the isDisplayableRet to set
	 */
	public void setIsDisplayableRet(String isDisplayableRet) {
		this.isDisplayableRet = isDisplayableRet;
	}

	/**
	 * @param isDisplaybaleSavedBasket
	 *            the isDisplaybaleSavedBasket to set
	 */
	public void setIsDisplaybaleSavedBasket(String isDisplaybaleSavedBasket) {
		this.isDisplaybaleSavedBasket = isDisplaybaleSavedBasket;
	}

	/**
	 * @param isSellableACQ
	 *            the isSellableACQ to set
	 */
	public void setIsSellableACQ(String isSellableACQ) {
		this.isSellableACQ = isSellableACQ;
	}

	/**
	 * @param isSellableECare
	 *            the isSellableECare to set
	 */
	public void setIsSellableECare(String isSellableECare) {
		this.isSellableECare = isSellableECare;
	}

	/**
	 * @param isSellableRet
	 *            the isSellableRet to set
	 */
	public void setIsSellableRet(String isSellableRet) {
		this.isSellableRet = isSellableRet;
	}

	/**
	 * @param landlineMinutesDisplayUOM
	 *            the landlineMinutesDisplayUOM to set
	 */
	public void setLandlineMinutesDisplayUOM(String landlineMinutesDisplayUOM) {
		this.landlineMinutesDisplayUOM = landlineMinutesDisplayUOM;
	}

	/**
	 * @param landlineMinutesUOM
	 *            the landlineMinutesUOM to set
	 */
	public void setLandlineMinutesUOM(String landlineMinutesUOM) {
		this.landlineMinutesUOM = landlineMinutesUOM;
	}

	/**
	 * @param landlineMinutesValue
	 *            the landlineMinutesValue to set
	 */
	public void setLandlineMinutesValue(String landlineMinutesValue) {
		this.landlineMinutesValue = landlineMinutesValue;
	}

	public void setListOfCompatibleProducts(List<String> listOfCompatibleProducts) {
		this.listOfCompatibleProducts = listOfCompatibleProducts;
	}

	public void setMerchandisingMedia(List<String> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
	}

	/**
	 * @param migrationPaths
	 *            the migrationPaths to set
	 */
	public void setMigrationPaths(List<String> migrationPaths) {
		this.migrationPaths = migrationPaths;
	}

	/**
	 * @param monthlyDiscountedGrossPrice
	 *            the monthlyDiscountedGrossPrice to set
	 */
	public void setMonthlyDiscountedGrossPrice(Float monthlyDiscountedGrossPrice) {
		this.monthlyDiscountedGrossPrice = monthlyDiscountedGrossPrice;
	}

	/**
	 * @param monthlyDiscountedNetPrice
	 *            the monthlyDiscountedNetPrice to set
	 */
	public void setMonthlyDiscountedNetPrice(Float monthlyDiscountedNetPrice) {
		this.monthlyDiscountedNetPrice = monthlyDiscountedNetPrice;
	}

	/**
	 * @param monthlyDiscountedVatPrice
	 *            the monthlyDiscountedVatPrice to set
	 */
	public void setMonthlyDiscountedVatPrice(Float monthlyDiscountedVatPrice) {
		this.monthlyDiscountedVatPrice = monthlyDiscountedVatPrice;
	}

	/**
	 * @param monthlyGrossPrice
	 *            the monthlyGrossPrice to set
	 */
	public void setMonthlyGrossPrice(Float monthlyGrossPrice) {
		this.monthlyGrossPrice = monthlyGrossPrice;
	}

	/**
	 * @param monthlyNetPrice
	 *            the monthlyNetPrice to set
	 */
	public void setMonthlyNetPrice(Float monthlyNetPrice) {
		this.monthlyNetPrice = monthlyNetPrice;
	}

	/**
	 * @param monthlyVatPrice
	 *            the monthlyVatPrice to set
	 */
	public void setMonthlyVatPrice(Float monthlyVatPrice) {
		this.monthlyVatPrice = monthlyVatPrice;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @param pictureMessagesDisplayUOM
	 *            the pictureMessagesDisplayUOM to set
	 */
	public void setPictureMessagesDisplayUOM(String pictureMessagesDisplayUOM) {
		this.pictureMessagesDisplayUOM = pictureMessagesDisplayUOM;
	}

	/**
	 * @param pictureMessagesUOM
	 *            the pictureMessagesUOM to set
	 */
	public void setPictureMessagesUOM(String pictureMessagesUOM) {
		this.pictureMessagesUOM = pictureMessagesUOM;
	}

	/**
	 * @param pictureMessagesValue
	 *            the pictureMessagesValue to set
	 */
	public void setPictureMessagesValue(String pictureMessagesValue) {
		this.pictureMessagesValue = pictureMessagesValue;
	}

	/**
	 * @param planCoupleFlag
	 *            the planCoupleFlag to set
	 */
	public void setPlanCoupleFlag(String planCoupleFlag) {
		this.planCoupleFlag = planCoupleFlag;
	}

	/**
	 * @param planCoupleId
	 *            the planCoupleId to set
	 */
	public void setPlanCoupleId(String planCoupleId) {
		this.planCoupleId = planCoupleId;
	}

	/**
	 * @param planCoupleLabel
	 *            the planCoupleLabel to set
	 */
	public void setPlanCoupleLabel(String planCoupleLabel) {
		this.planCoupleLabel = planCoupleLabel;
	}

	/**
	 * @param postSaleJourney
	 *            the postSaleJourney to set
	 */
	public void setPostSaleJourney(String postSaleJourney) {
		this.postSaleJourney = postSaleJourney;
	}

	/**
	 * @param productGroupName
	 *            the productGroupName to set
	 */
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	/**
	 * @param productLines
	 *            the productLines to set
	 */
	public void setProductLines(List<String> productLines) {
		this.productLines = productLines;
	}

	/**
	 * @param promoteAs
	 *            the promoteAs to set
	 */
	public void setPromoteAs(List<String> promoteAs) {
		this.promoteAs = promoteAs;
	}

	/**
	 * @param recommendedAccessories
	 *            the recommendedAccessories to set
	 */
	public void setRecommendedAccessories(String recommendedAccessories) {
		this.recommendedAccessories = recommendedAccessories;
	}

	/**
	 * @param recommendedExtras
	 *            the recommendedExtras to set
	 */
	public void setRecommendedExtras(String recommendedExtras) {
		this.recommendedExtras = recommendedExtras;
	}

	/**
	 * @param recurringCharge
	 *            the recurringCharge to set
	 */
	public void setRecurringCharge(float recurringCharge) {
		this.recurringCharge = recurringCharge;
	}

	/**
	 * @param relationships
	 *            the relationships to set
	 */
	public void setRelationships(List<String> relationships) {
		this.relationships = relationships;
	}

	/**
	 * @param roamingDataDisplayUOM
	 *            the roamingDataDisplayUOM to set
	 */
	public void setRoamingDataDisplayUOM(String roamingDataDisplayUOM) {
		this.roamingDataDisplayUOM = roamingDataDisplayUOM;
	}

	/**
	 * @param roamingDataUOM
	 *            the roamingDataUOM to set
	 */
	public void setRoamingDataUOM(String roamingDataUOM) {
		this.roamingDataUOM = roamingDataUOM;
	}

	/**
	 * @param roamingDataValue
	 *            the roamingDataValue to set
	 */
	public void setRoamingDataValue(String roamingDataValue) {
		this.roamingDataValue = roamingDataValue;
	}

	/**
	 * @param salesExpired
	 *            the salesExpired to set
	 */
	public void setSalesExpired(String salesExpired) {
		this.salesExpired = salesExpired;
	}

	/**
	 * @param seoCanonical
	 *            the seoCanonical to set
	 */
	public void setSeoCanonical(String seoCanonical) {
		this.seoCanonical = seoCanonical;
	}

	/**
	 * @param seoDescription
	 *            the seoDescription to set
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * @param seoIndex
	 *            the seoIndex to set
	 */
	public void setSeoIndex(String seoIndex) {
		this.seoIndex = seoIndex;
	}

	/**
	 * @param seoKeywords
	 *            the seoKeywords to set
	 */
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	/**
	 * @param serviceProducts
	 *            the serviceProducts to set
	 */
	public void setServiceProducts(List<String> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param support
	 *            the support to set
	 */
	public void setSupport(String support) {
		this.support = support;
	}

	/**
	 * @param threeDSpin
	 *            the threeDSpin to set
	 */
	public void setThreeDSpin(String threeDSpin) {
		this.threeDSpin = threeDSpin;
	}

	/**
	 * @param uKANDInternationalCreditDisplayUOM
	 *            the uKANDInternationalCreditDisplayUOM to set
	 */
	public void setuKANDInternationalCreditDisplayUOM(String uKANDInternationalCreditDisplayUOM) {
		this.uKANDInternationalCreditDisplayUOM = uKANDInternationalCreditDisplayUOM;
	}

	/**
	 * @param uKANDInternationalCreditUOM
	 *            the uKANDInternationalCreditUOM to set
	 */
	public void setuKANDInternationalCreditUOM(String uKANDInternationalCreditUOM) {
		this.uKANDInternationalCreditUOM = uKANDInternationalCreditUOM;
	}

	/**
	 * @param uKANDInternationalCreditValue
	 *            the uKANDInternationalCreditValue to set
	 */
	public void setuKANDInternationalCreditValue(String uKANDInternationalCreditValue) {
		this.uKANDInternationalCreditValue = uKANDInternationalCreditValue;
	}

	/**
	 * @param ukDataDisplayUOM
	 *            the ukDataDisplayUOM to set
	 */
	public void setUkDataDisplayUOM(String ukDataDisplayUOM) {
		this.ukDataDisplayUOM = ukDataDisplayUOM;
	}

	/**
	 * @param ukDataUOM
	 *            the ukDataUOM to set
	 */
	public void setUkDataUOM(String ukDataUOM) {
		this.ukDataUOM = ukDataUOM;
	}

	/**
	 * @param ukDataValue
	 *            the ukDataValue to set
	 */
	public void setUkDataValue(String ukDataValue) {
		this.ukDataValue = ukDataValue;
	}

	/**
	 * @param ukMinutesDisplayUOM
	 *            the ukMinutesDisplayUOM to set
	 */
	public void setUkMinutesDisplayUOM(String ukMinutesDisplayUOM) {
		this.ukMinutesDisplayUOM = ukMinutesDisplayUOM;
	}

	/**
	 * @param ukMinutesUOM
	 *            the ukMinutesUOM to set
	 */
	public void setUkMinutesUOM(String ukMinutesUOM) {
		this.ukMinutesUOM = ukMinutesUOM;
	}

	/**
	 * @param ukMinutesValue
	 *            the ukMinutesValue to set
	 */
	public void setUkMinutesValue(String ukMinutesValue) {
		this.ukMinutesValue = ukMinutesValue;
	}

	/**
	 * @param uKmobileMinutesDisplayUOM
	 *            the uKmobileMinutesDisplayUOM to set
	 */
	public void setuKmobileMinutesDisplayUOM(String uKmobileMinutesDisplayUOM) {
		this.uKmobileMinutesDisplayUOM = uKmobileMinutesDisplayUOM;
	}

	/**
	 * @param uKmobileMinutesUOM
	 *            the uKmobileMinutesUOM to set
	 */
	public void setuKmobileMinutesUOM(String uKmobileMinutesUOM) {
		this.uKmobileMinutesUOM = uKmobileMinutesUOM;
	}

	/**
	 * @param uKmobileMinutesValue
	 *            the uKmobileMinutesValue to set
	 */
	public void setuKmobileMinutesValue(String uKmobileMinutesValue) {
		this.uKmobileMinutesValue = uKmobileMinutesValue;
	}

	/**
	 * @param ukTextsDisplayUOM
	 *            the ukTextsDisplayUOM to set
	 */
	public void setUkTextsDisplayUOM(String ukTextsDisplayUOM) {
		this.ukTextsDisplayUOM = ukTextsDisplayUOM;
	}

	/**
	 * @param ukTextsUOM
	 *            the ukTextsUOM to set
	 */
	public void setUkTextsUOM(String ukTextsUOM) {
		this.ukTextsUOM = ukTextsUOM;
	}

	/**
	 * @param ukTextsValue
	 *            the ukTextsValue to set
	 */
	public void setUkTextsValue(String ukTextsValue) {
		this.ukTextsValue = ukTextsValue;
	}

	/**
	 * @param upgradePaths
	 *            the upgradePaths to set
	 */
	public void setUpgradePaths(List<String> upgradePaths) {
		this.upgradePaths = upgradePaths;
	}

	/**
	 * @param video
	 *            the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	 * @param vodafoneVodafoneDisplayUOM
	 *            the vodafoneVodafoneDisplayUOM to set
	 */
	public void setVodafoneVodafoneDisplayUOM(String vodafoneVodafoneDisplayUOM) {
		this.vodafoneVodafoneDisplayUOM = vodafoneVodafoneDisplayUOM;
	}

	/**
	 * @param vodafoneVodafoneMinutesDisplayUOM
	 *            the vodafoneVodafoneMinutesDisplayUOM to set
	 */
	public void setVodafoneVodafoneMinutesDisplayUOM(String vodafoneVodafoneMinutesDisplayUOM) {
		this.vodafoneVodafoneMinutesDisplayUOM = vodafoneVodafoneMinutesDisplayUOM;
	}

	/**
	 * @param vodafoneVodafoneMinutesUOM
	 *            the vodafoneVodafoneMinutesUOM to set
	 */
	public void setVodafoneVodafoneMinutesUOM(String vodafoneVodafoneMinutesUOM) {
		this.vodafoneVodafoneMinutesUOM = vodafoneVodafoneMinutesUOM;
	}

	/**
	 * @param vodafoneVodafoneMinutesValue
	 *            the vodafoneVodafoneMinutesValue to set
	 */
	public void setVodafoneVodafoneMinutesValue(String vodafoneVodafoneMinutesValue) {
		this.vodafoneVodafoneMinutesValue = vodafoneVodafoneMinutesValue;
	}

	/**
	 * @param vodafoneVodafonetextsDisplayUOM
	 *            the vodafoneVodafonetextsDisplayUOM to set
	 */
	public void setVodafoneVodafonetextsDisplayUOM(String vodafoneVodafonetextsDisplayUOM) {
		this.vodafoneVodafonetextsDisplayUOM = vodafoneVodafonetextsDisplayUOM;
	}

	/**
	 * @param vodafoneVodafonetextsUOM
	 *            the vodafoneVodafonetextsUOM to set
	 */
	public void setVodafoneVodafonetextsUOM(String vodafoneVodafonetextsUOM) {
		this.vodafoneVodafonetextsUOM = vodafoneVodafonetextsUOM;
	}

	/**
	 * @param vodafoneVodafonetextsValue
	 *            the vodafoneVodafonetextsValue to set
	 */
	public void setVodafoneVodafonetextsValue(String vodafoneVodafonetextsValue) {
		this.vodafoneVodafonetextsValue = vodafoneVodafonetextsValue;
	}

	/**
	 * @param vodafoneVodafoneUOM
	 *            the vodafoneVodafoneUOM to set
	 */
	public void setVodafoneVodafoneUOM(String vodafoneVodafoneUOM) {
		this.vodafoneVodafoneUOM = vodafoneVodafoneUOM;
	}

	/**
	 * @param vodafoneVodafoneValue
	 *            the vodafoneVodafoneValue to set
	 */
	public void setVodafoneVodafoneValue(String vodafoneVodafoneValue) {
		this.vodafoneVodafoneValue = vodafoneVodafoneValue;
	}

	/**
	 * @param wifiDisplayUOM
	 *            the wifiDisplayUOM to set
	 */
	public void setWifiDisplayUOM(String wifiDisplayUOM) {
		this.wifiDisplayUOM = wifiDisplayUOM;
	}

	/**
	 * @param wifiUOM
	 *            the wifiUOM to set
	 */
	public void setWifiUOM(String wifiUOM) {
		this.wifiUOM = wifiUOM;
	}

	/**
	 * @param wifiValue
	 *            the wifiValue to set
	 */
	public void setWifiValue(String wifiValue) {
		this.wifiValue = wifiValue;
	}

	/**
	 * @return the secureNetFlag
	 */
	public Boolean getSecureNetFlag() {
		return secureNetFlag;
	}

	/**
	 * @param secureNetFlag
	 *            the secureNetFlag to set
	 */
	public void setSecureNetFlag(Boolean secureNetFlag) {
		this.secureNetFlag = secureNetFlag;
	}

	/**
	 * @return the globalRoamingFlag
	 */
	public Boolean getGlobalRoamingFlag() {
		return globalRoamingFlag;
	}

	/**
	 * @param globalRoamingFlag
	 *            the globalRoamingFlag to set
	 */
	public void setGlobalRoamingFlag(Boolean globalRoamingFlag) {
		this.globalRoamingFlag = globalRoamingFlag;
	}

	/**
	 * @return the startDateFormatted
	 */
	public Date getStartDateFormatted() {
		return startDateFormatted;
	}

	/**
	 * @param startDateFormatted
	 *            the startDateFormatted to set
	 */
	public void setStartDateFormatted(Date startDateFormatted) {
		this.startDateFormatted = startDateFormatted;
	}

	/**
	 * @return the endDateFormatted
	 */
	public Date getEndDateFormatted() {
		return endDateFormatted;
	}

	/**
	 * @param endDateFormatted
	 *            the endDateFormatted to set
	 */
	public void setEndDateFormatted(Date endDateFormatted) {
		this.endDateFormatted = endDateFormatted;
	}

	/**
	 * @return the routerDeviceId
	 */
	public String getRouterDeviceId() {
		return routerDeviceId;
	}

	/**
	 * @param routerDeviceId
	 *            the routerDeviceId to set
	 */
	public void setRouterDeviceId(String routerDeviceId) {
		this.routerDeviceId = routerDeviceId;
	}

	/**
	 * @return the flbbSpendLimit
	 */
	public String getFlbbSpendLimit() {
		return flbbSpendLimit;
	}

	/**
	 * @param flbbSpendLimit
	 *            the flbbSpendLimit to set
	 */
	public void setFlbbSpendLimit(String flbbSpendLimit) {
		this.flbbSpendLimit = flbbSpendLimit;
	}

	/**
	 * @return the flbbUsage
	 */
	public String getFlbbUsage() {
		return flbbUsage;
	}

	/**
	 * @param flbbUsage
	 *            the flbbUsage to set
	 */
	public void setFlbbUsage(String flbbUsage) {
		this.flbbUsage = flbbUsage;
	}

	/**
	 * @return the speedLimitAndUsage
	 */
	public String getSpeedLimitAndUsage() {
		return speedLimitAndUsage;
	}

	/**
	 * @param speedLimitAndUsage
	 *            the speedLimitAndUsage to set
	 */
	public void setSpeedLimitAndUsage(String speedLimitAndUsage) {
		this.speedLimitAndUsage = speedLimitAndUsage;
	}

	/**
	 * @return the miscKeyValue
	 */
	public List<String> getMiscKeyValue() {
		return miscKeyValue;
	}

	/**
	 * @param miscKeyValue
	 *            the miscKeyValue to set
	 */
	public void setMiscKeyValue(List<String> miscKeyValue) {
		this.miscKeyValue = miscKeyValue;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the seoRobots
	 */
	public List<String> getSeoRobots() {
		return seoRobots;
	}

	/**
	 * @param seoRobots
	 *            the seoRobots to set
	 */
	public void setSeoRobots(List<String> seoRobots) {
		this.seoRobots = seoRobots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BundleModel [id=" + id + ", bundleId=" + bundleId + ", name=" + name + ", desc=" + desc
				+ ", paymentType=" + paymentType + ", commitmentPeriod=" + commitmentPeriod + ", commitmentPenalty="
				+ commitmentPenalty + ", salesExpired=" + salesExpired + ", startDate=" + startDate + ", productLines="
				+ productLines + ", recurringCharge=" + recurringCharge + ", categories=" + categories
				+ ", serviceProducts=" + serviceProducts + ", relationships=" + relationships
				+ ", recommendedAccessories=" + recommendedAccessories + ", recommendedExtras=" + recommendedExtras
				+ ", postSaleJourney=" + postSaleJourney + ", seoCanonical=" + seoCanonical + ", seoDescription="
				+ seoDescription + ", seoKeywords=" + seoKeywords + ", seoIndex=" + seoIndex + ", eligibilitySubflow="
				+ eligibilitySubflow + ", displayName=" + displayName + ", contentDesc=" + contentDesc
				+ ", fullDetails=" + fullDetails + ", descMobile=" + descMobile + ", fullDetailsMobile="
				+ fullDetailsMobile + ", video=" + video + ", threeDSpin=" + threeDSpin + ", support=" + support
				+ ", order=" + order + ", affiliateExport=" + affiliateExport + ", compareWith=" + compareWith
				+ ", productGroupName=" + productGroupName + ", imageURLsThumbsFront=" + imageURLsThumbsFront
				+ ", imageURLsThumbsLeft=" + imageURLsThumbsLeft + ", imageURLsThumbsRight=" + imageURLsThumbsRight
				+ ", imageURLsThumbsSide=" + imageURLsThumbsSide + ", imageURLsThumbsBack=" + imageURLsThumbsBack
				+ ", imageURLsFullFront=" + imageURLsFullFront + ", imageURLsFullLeft=" + imageURLsFullLeft
				+ ", imageURLsFullRight=" + imageURLsFullRight + ", imageURLsFullSide=" + imageURLsFullSide
				+ ", imageURLsFullBack=" + imageURLsFullBack + ", imageURLsGrid=" + imageURLsGrid + ", imageURLsSmall="
				+ imageURLsSmall + ", imageURLsSticker=" + imageURLsSticker + ", imageURLsIcon=" + imageURLsIcon
				+ ", charge=" + charge + ", displayGroup=" + displayGroup + ", migrationPaths=" + migrationPaths
				+ ", upgradePaths=" + upgradePaths + ", dataUOM=" + dataUOM + ", dataValue=" + dataValue
				+ ", dataDisplayUOM=" + dataDisplayUOM + ", ukTextsUOM=" + ukTextsUOM + ", ukTextsValue=" + ukTextsValue
				+ ", ukTextsDisplayUOM=" + ukTextsDisplayUOM + ", ukMinutesUOM=" + ukMinutesUOM + ", ukMinutesValue="
				+ ukMinutesValue + ", ukMinutesDisplayUOM=" + ukMinutesDisplayUOM + ", ukDataUOM=" + ukDataUOM
				+ ", ukDataValue=" + ukDataValue + ", ukDataDisplayUOM=" + ukDataDisplayUOM + ", wifiUOM=" + wifiUOM
				+ ", wifiValue=" + wifiValue + ", wifiDisplayUOM=" + wifiDisplayUOM + ", europeanRoamingDataUOM="
				+ europeanRoamingDataUOM + ", europeanRoamingDataValue=" + europeanRoamingDataValue
				+ ", europeanRoamingDataDisplayUOM=" + europeanRoamingDataDisplayUOM + ", europeanRoamingTextsUOM="
				+ europeanRoamingTextsUOM + ", europeanRoamingTextsValue=" + europeanRoamingTextsValue
				+ ", europeanRoamingTextsDisplayUOM=" + europeanRoamingTextsDisplayUOM + ", europeanRoamingMinutesUOM="
				+ europeanRoamingMinutesUOM + ", europeanRoamingMinutesValue=" + europeanRoamingMinutesValue
				+ ", europeanRoamingMinutesDisplayUOM=" + europeanRoamingMinutesDisplayUOM
				+ ", europeanRoamingPictureMessagesUOM=" + europeanRoamingPictureMessagesUOM
				+ ", europeanRoamingPictureMessagesValue=" + europeanRoamingPictureMessagesValue
				+ ", europeanRoamingPictureMessagesDisplayUOM=" + europeanRoamingPictureMessagesDisplayUOM
				+ ", internationalTextsUOM=" + internationalTextsUOM + ", internationalTextsValue="
				+ internationalTextsValue + ", internationalTextsDisplayUOM=" + internationalTextsDisplayUOM
				+ ", etdataUOM=" + etdataUOM + ", etdataValue=" + etdataValue + ", etdataDisplayUOM=" + etdataDisplayUOM
				+ ", globalRoamingDataUOM=" + globalRoamingDataUOM + ", globalRoamingDataValue="
				+ globalRoamingDataValue + ", globalRoamingDataDisplayUOM=" + globalRoamingDataDisplayUOM
				+ ", globalRoamingTextsUOM=" + globalRoamingTextsUOM + ", globalRoamingTextsValue="
				+ globalRoamingTextsValue + ", globalRoamingTextsDisplayUOM=" + globalRoamingTextsDisplayUOM
				+ ", globalRoamingMinutesUOM=" + globalRoamingMinutesUOM + ", globalRoamingMinutesValue="
				+ globalRoamingMinutesValue + ", globalRoamingMinutesDisplayUOM=" + globalRoamingMinutesDisplayUOM
				+ ", roamingDataUOM=" + roamingDataUOM + ", roamingDataValue=" + roamingDataValue
				+ ", roamingDataDisplayUOM=" + roamingDataDisplayUOM + ", uKmobileMinutesUOM=" + uKmobileMinutesUOM
				+ ", uKmobileMinutesValue=" + uKmobileMinutesValue + ", uKmobileMinutesDisplayUOM="
				+ uKmobileMinutesDisplayUOM + ", vodafoneVodafonetextsUOM=" + vodafoneVodafonetextsUOM
				+ ", vodafoneVodafonetextsValue=" + vodafoneVodafonetextsValue + ", vodafoneVodafonetextsDisplayUOM="
				+ vodafoneVodafonetextsDisplayUOM + ", pictureMessagesUOM=" + pictureMessagesUOM
				+ ", pictureMessagesValue=" + pictureMessagesValue + ", pictureMessagesDisplayUOM="
				+ pictureMessagesDisplayUOM + ", uKANDInternationalCreditUOM=" + uKANDInternationalCreditUOM
				+ ", uKANDInternationalCreditValue=" + uKANDInternationalCreditValue
				+ ", uKANDInternationalCreditDisplayUOM=" + uKANDInternationalCreditDisplayUOM
				+ ", internationalMinutesUOM=" + internationalMinutesUOM + ", internationalMinutesValue="
				+ internationalMinutesValue + ", internationalMinutesDisplayUOM=" + internationalMinutesDisplayUOM
				+ ", vodafoneVodafoneUOM=" + vodafoneVodafoneUOM + ", vodafoneVodafoneValue=" + vodafoneVodafoneValue
				+ ", vodafoneVodafoneDisplayUOM=" + vodafoneVodafoneDisplayUOM + ", vodafoneVodafoneMinutesUOM="
				+ vodafoneVodafoneMinutesUOM + ", vodafoneVodafoneMinutesValue=" + vodafoneVodafoneMinutesValue
				+ ", vodafoneVodafoneMinutesDisplayUOM=" + vodafoneVodafoneMinutesDisplayUOM + ", landlineMinutesUOM="
				+ landlineMinutesUOM + ", landlineMinutesValue=" + landlineMinutesValue + ", landlineMinutesDisplayUOM="
				+ landlineMinutesDisplayUOM + ", isDisplayableAcq=" + isDisplayableAcq + ", isSellableECare="
				+ isSellableECare + ", isSellableRet=" + isSellableRet + ", isDisplayableRet=" + isDisplayableRet
				+ ", isDisplaybaleSavedBasket=" + isDisplaybaleSavedBasket + ", isSellableACQ=" + isSellableACQ
				+ ", promoteAs=" + promoteAs + ", endDate=" + endDate + ", merchandisingMedia=" + merchandisingMedia
				+ ", allowanceList=" + allowanceList + ", planCoupleId=" + planCoupleId + ", planCoupleFlag="
				+ planCoupleFlag + ", planCoupleLabel=" + planCoupleLabel + ", monthlyGrossPrice=" + monthlyGrossPrice
				+ ", monthlyNetPrice=" + monthlyNetPrice + ", monthlyVatPrice=" + monthlyVatPrice
				+ ", monthlyDiscountedGrossPrice=" + monthlyDiscountedGrossPrice + ", monthlyDiscountedNetPrice="
				+ monthlyDiscountedNetPrice + ", monthlyDiscountedVatPrice=" + monthlyDiscountedVatPrice
				+ ", listOfCompatibleProducts=" + listOfCompatibleProducts + ", secureNetFlag=" + secureNetFlag
				+ ", globalRoamingFlag=" + globalRoamingFlag + ", startDateFormatted=" + startDateFormatted
				+ ", endDateFormatted=" + endDateFormatted + ", routerDeviceId=" + routerDeviceId + ", flbbSpendLimit="
				+ flbbSpendLimit + ", flbbUsage=" + flbbUsage + ", speedLimitAndUsage=" + speedLimitAndUsage
				+ ", miscKeyValue=" + miscKeyValue + ", seoRobots=" + seoRobots + "]";
	}

}
