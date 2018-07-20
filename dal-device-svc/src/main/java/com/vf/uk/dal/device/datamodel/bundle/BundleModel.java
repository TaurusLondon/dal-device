package com.vf.uk.dal.device.datamodel.bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
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
}
