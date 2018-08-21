package com.vf.uk.dal.device.datamodel.bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleModel
 * @author manoj.bera
 *
 */
@Data
public class BundleModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("bundleId")
	private String bundleId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("desc")
	private String desc;

	@JsonProperty("paymentType")
	private String paymentType;

	@JsonProperty("commitmentPeriod")
	private String commitmentPeriod;

	@JsonProperty("commitmentPenalty")
	private String commitmentPenalty;

	@JsonProperty("salesExpired")
	private String salesExpired;

	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("productLines")
	private List<String> productLines = new ArrayList<>();

	@JsonProperty("recurringCharge")
	private float recurringCharge;

	@JsonProperty(value = "categories")
	private List<String> categories = new ArrayList<>();
	@JsonProperty(value = "serviceProductsOpt")
	private List<String> serviceProducts = new ArrayList<>();
	@JsonProperty(value = "relationshipsOpt")
	private List<String> relationships = new ArrayList<>();

	@JsonProperty("recommendedAccessories")
	private String recommendedAccessories;

	@JsonProperty("recommendedExtras")
	private String recommendedExtras;

	@JsonProperty("postSaleJourney")
	private String postSaleJourney;

	@JsonProperty("seoCanonical")
	private String seoCanonical;

	@JsonProperty("seoDescription")
	private String seoDescription;

	@JsonProperty("seoKeywords")
	private String seoKeywords;

	@JsonProperty("seoIndex")
	private String seoIndex;

	@JsonProperty("eligibilitySubflow")
	private String eligibilitySubflow;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("contentDesc")
	private String contentDesc;

	@JsonProperty("fullDetails")
	private String fullDetails;

	@JsonProperty("descMobile")
	private String descMobile;

	@JsonProperty("fullDetailsMobile")
	private String fullDetailsMobile;

	@JsonProperty("video")
	private String video;

	@JsonProperty("threeDSpin")
	private String threeDSpin;

	@JsonProperty("support")
	private String support;

	@JsonProperty("order")
	private Integer order;

	@JsonProperty("affiliateExport")
	private String affiliateExport;
	@JsonProperty("compareWith")
	private String compareWith;
	@JsonProperty("productGroupName")
	private String productGroupName;
	@JsonProperty("imageURLsThumbsFront")
	private String imageURLsThumbsFront;
	@JsonProperty("imageURLsThumbsLeft")
	private String imageURLsThumbsLeft;
	@JsonProperty("imageURLsThumbsRight")
	private String imageURLsThumbsRight;
	@JsonProperty("imageURLsThumbsSide")
	private String imageURLsThumbsSide;
	@JsonProperty("imageURLsThumbsBack")
	private String imageURLsThumbsBack;
	@JsonProperty("imageURLsFullFront")
	private String imageURLsFullFront;
	@JsonProperty("imageURLsFullLeft")
	private String imageURLsFullLeft;
	@JsonProperty("imageURLsFullRight")
	private String imageURLsFullRight;
	@JsonProperty("imageURLsFullSide")
	private String imageURLsFullSide;
	@JsonProperty("imageURLsFullBack")
	private String imageURLsFullBack;
	@JsonProperty("imageURLsGrid")
	private String imageURLsGrid;
	@JsonProperty("imageURLsSmall")
	private String imageURLsSmall;
	@JsonProperty("imageURLsSticker")
	private String imageURLsSticker;
	@JsonProperty("imageURLsIcon")
	private String imageURLsIcon;
	@JsonProperty("charge")
	private String charge;
	@JsonProperty("displayGroup")
	private String displayGroup;
	@JsonProperty(value = "migrationPathsOpt")
	private List<String> migrationPaths;
	@JsonProperty(value = "upgradePathsOpt")
	private List<String> upgradePaths;
	@JsonProperty("dataUOM")
	private String dataUOM;
	@JsonProperty("dataValue")
	private String dataValue;
	@JsonProperty("dataDisplayUOM")
	private String dataDisplayUOM;
	@JsonProperty("ukTextsUOM")
	private String ukTextsUOM;
	@JsonProperty("ukTextsValue")
	private String ukTextsValue;
	@JsonProperty("ukTextsDisplayUOM")
	private String ukTextsDisplayUOM;
	@JsonProperty("ukMinutesUOM")
	private String ukMinutesUOM;
	@JsonProperty("ukMinutesValue")
	private String ukMinutesValue;
	@JsonProperty("ukMinutesDisplayUOM")
	private String ukMinutesDisplayUOM;
	@JsonProperty("ukDataUOM")
	private String ukDataUOM;
	@JsonProperty("ukDataValue")
	private String ukDataValue;
	@JsonProperty("ukDataDisplayUOM")
	private String ukDataDisplayUOM;
	@JsonProperty("wifiUOM")
	private String wifiUOM;
	@JsonProperty("wifiValue")
	private String wifiValue;
	@JsonProperty("wifiDisplayUOM")
	private String wifiDisplayUOM;
	@JsonProperty("europeanRoamingDataUOM")
	private String europeanRoamingDataUOM;
	@JsonProperty("europeanRoamingDataValue")
	private String europeanRoamingDataValue;
	@JsonProperty("europeanRoamingDataDisplayUOM")
	private String europeanRoamingDataDisplayUOM;
	@JsonProperty("europeanRoamingTextsUOM")
	private String europeanRoamingTextsUOM;
	@JsonProperty("europeanRoamingTextsValue")
	private String europeanRoamingTextsValue;
	@JsonProperty("europeanRoamingTextsDisplayUOM")
	private String europeanRoamingTextsDisplayUOM;
	@JsonProperty("europeanRoamingMinutesUOM")
	private String europeanRoamingMinutesUOM;
	@JsonProperty("europeanRoamingMinutesValue")
	private String europeanRoamingMinutesValue;
	@JsonProperty("europeanRoamingMinutesDisplayUOM")
	private String europeanRoamingMinutesDisplayUOM;
	@JsonProperty("europeanRoamingPictureMessagesUOM")
	private String europeanRoamingPictureMessagesUOM;
	@JsonProperty("europeanRoamingPictureMessagesValue")
	private String europeanRoamingPictureMessagesValue;
	@JsonProperty("europeanRoamingPictureMessagesDisplayUOM")
	private String europeanRoamingPictureMessagesDisplayUOM;
	@JsonProperty("internationalTextsUOM")
	private String internationalTextsUOM;
	@JsonProperty("internationalTextsValue")
	private String internationalTextsValue;
	@JsonProperty("internationalTextsDisplayUOM")
	private String internationalTextsDisplayUOM;
	@JsonProperty("etdataUOM")
	private String etdataUOM;
	@JsonProperty("etdataValue")
	private String etdataValue;
	@JsonProperty("etdataDisplayUOM")
	private String etdataDisplayUOM;
	@JsonProperty("globalRoamingDataUOM")
	private String globalRoamingDataUOM;
	@JsonProperty("globalRoamingDataValue")
	private String globalRoamingDataValue;
	@JsonProperty("globalRoamingDataDisplayUOM")
	private String globalRoamingDataDisplayUOM;
	@JsonProperty("globalRoamingTextsUOM")
	private String globalRoamingTextsUOM;
	@JsonProperty("globalRoamingTextsValue")
	private String globalRoamingTextsValue;
	@JsonProperty("globalRoamingTextsDisplayUOM")
	private String globalRoamingTextsDisplayUOM;
	@JsonProperty("globalRoamingMinutesUOM")
	private String globalRoamingMinutesUOM;
	@JsonProperty("globalRoamingMinutesValue")
	private String globalRoamingMinutesValue;
	@JsonProperty("globalRoamingMinutesDisplayUOM")
	private String globalRoamingMinutesDisplayUOM;
	@JsonProperty("roamingDataUOM")
	private String roamingDataUOM;
	@JsonProperty("roamingDataValue")
	private String roamingDataValue;
	@JsonProperty("roamingDataDisplayUOM")
	private String roamingDataDisplayUOM;
	@JsonProperty("uKmobileMinutesUOM")
	private String uKmobileMinutesUOM;
	@JsonProperty("uKmobileMinutesValue")
	private String uKmobileMinutesValue;
	@JsonProperty("uKmobileMinutesDisplayUOM")
	private String uKmobileMinutesDisplayUOM;
	@JsonProperty("vodafoneVodafonetextsUOM")
	private String vodafoneVodafonetextsUOM;
	@JsonProperty("vodafoneVodafonetextsValue")
	private String vodafoneVodafonetextsValue;
	@JsonProperty("vodafoneVodafonetextsDisplayUOM")
	private String vodafoneVodafonetextsDisplayUOM;
	@JsonProperty("pictureMessagesUOM")
	private String pictureMessagesUOM;
	@JsonProperty("pictureMessagesValue")
	private String pictureMessagesValue;
	@JsonProperty("pictureMessagesDisplayUOM")
	private String pictureMessagesDisplayUOM;
	@JsonProperty("uKANDInternationalCreditUOM")
	private String uKANDInternationalCreditUOM;
	@JsonProperty("uKANDInternationalCreditValue")
	private String uKANDInternationalCreditValue;
	@JsonProperty("uKANDInternationalCreditDisplayUOM")
	private String uKANDInternationalCreditDisplayUOM;
	@JsonProperty("internationalMinutesUOM")
	private String internationalMinutesUOM;
	@JsonProperty("internationalMinutesValue")
	private String internationalMinutesValue;
	@JsonProperty("internationalMinutesDisplayUOM")
	private String internationalMinutesDisplayUOM;
	@JsonProperty("vodafoneVodafoneUOM")
	private String vodafoneVodafoneUOM;
	@JsonProperty("vodafoneVodafoneValue")
	private String vodafoneVodafoneValue;
	@JsonProperty("vodafoneVodafoneDisplayUOM")
	private String vodafoneVodafoneDisplayUOM;
	@JsonProperty("vodafoneVodafoneMinutesUOM")
	private String vodafoneVodafoneMinutesUOM;
	@JsonProperty("vodafoneVodafoneMinutesValue")
	private String vodafoneVodafoneMinutesValue;
	@JsonProperty("vodafoneVodafoneMinutesDisplayUOM")
	private String vodafoneVodafoneMinutesDisplayUOM;
	@JsonProperty("landlineMinutesUOM")
	private String landlineMinutesUOM;
	@JsonProperty("landlineMinutesValue")
	private String landlineMinutesValue;
	@JsonProperty("landlineMinutesDisplayUOM")
	private String landlineMinutesDisplayUOM;
	@JsonProperty("isDisplayableAcq")
	private String isDisplayableAcq;
	@JsonProperty("isSellableECare")
	private String isSellableECare;
	@JsonProperty("isSellableRet")
	private String isSellableRet;
	@JsonProperty("isDisplayableRet")
	private String isDisplayableRet;
	@JsonProperty("isDisplaybaleSavedBasket")
	private String isDisplaybaleSavedBasket;
	@JsonProperty("isSellableACQ")
	private String isSellableACQ;

	@JsonProperty(value = "promoteAsOpt")
	private List<String> promoteAs;
	@JsonProperty("endDate")
	private String endDate;
	@JsonProperty("merchandisingMedia")
	private List<String> merchandisingMedia = new ArrayList<>();

	@JsonProperty("allowance")
	private List<String> allowanceList = new ArrayList<>();
	@JsonProperty("planCoupleId")
	private String planCoupleId;
	@JsonProperty("planCoupleFlag")
	private String planCoupleFlag;
	@JsonProperty("planCoupleLabel")
	private String planCoupleLabel;
	@JsonProperty("monthlyGrossPrice")
	private Float monthlyGrossPrice;
	@JsonProperty("monthlyNetPrice")
	private Float monthlyNetPrice;
	@JsonProperty("monthlyVatPrice")
	private Float monthlyVatPrice;
	@JsonProperty("monthlyDiscountedGrossPrice")
	private Float monthlyDiscountedGrossPrice;
	@JsonProperty("monthlyDiscountedNetPrice")
	private Float monthlyDiscountedNetPrice;
	@JsonProperty("monthlyDiscountedVatPrice")
	private Float monthlyDiscountedVatPrice;
	@JsonProperty("listOfCompatibleProducts")
	private List<String> listOfCompatibleProducts;
	@JsonProperty("secureNetFlag")
	private Boolean secureNetFlag;
	@JsonProperty("globalRoamingFlag")
	private Boolean globalRoamingFlag;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date startDateFormatted;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date endDateFormatted;
	@JsonProperty("routerDeviceId")
	private String routerDeviceId;
	@JsonProperty("flbbSpendLimit")
	private String flbbSpendLimit;
	@JsonProperty("flbbUsage")
	private String flbbUsage;
	@JsonProperty("speedLimitAndUsage")
	private String speedLimitAndUsage;
	@JsonProperty("miscKeyValue")
	private List<String> miscKeyValue;

	@JsonProperty("createDate")
	private Date createDate;

	@JsonProperty("modifiedDate")
	private Date modifiedDate;

	@JsonProperty("seoRobots")
	private List<String> seoRobots;
}
