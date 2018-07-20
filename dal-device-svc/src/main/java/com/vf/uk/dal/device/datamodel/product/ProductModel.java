package com.vf.uk.dal.device.datamodel.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author ashish.adhikari
 *
 */
@Data
public class ProductModel {
	private String id;
	private String productId;
	private String productName;
	private String parentProductId;
	private Integer order;
	private String productClass;
	private String isServicesProduct;
	@JsonProperty(value = "durationOpt")
	private int duration;
	private String durationUOM;

	private String productStartDate;
	private String productEndDate;
	private String salesExpired;
	private String discountType;
	private float discountAmount;
	private String warranty;
	private String condition;
	private String priceType;
	private String productType;
	private String isDisplayableAcq;
	private String isSellableECare;
	private String isSellableRet;
	private String isDisplayableRet;
	private String isDisplaybaleSavedBasket;
	private String controlOrder;
	private String preOrderable;
	private String backOrderable;
	private String affiliateExport;
	private String compareWith;
	private String pdGrpvariant;
	private String pdGrpCmpAcc;
	private String pdGrpRecAcc;
	private String pdGrpRecExtr;
	private String compatibleEntertainment;
	private String priceNetOVR;
	private String priceGrossOVR;
	private String priceVatOVR;
	private String seoCanonical;
	private String seoDescription;
	private String seoKeywords;
	private String seoIndex;
	private String eligibiltiySubflow;
	private String equipmentMake;
	private String equipmentModel;
	private String displayName;
	private String postDescMobile;
	private String imageURLsThumbsFront;
	private String imageURLsThumbsLeft;
	private String imageURLsThumbsRight;
	private String imageURLsThumbsSide;
	private String imageURLsFullHero;
	private String imageURLsFullFront;
	private String imageURLsFullLeft;
	private String imageURLsFullRight;
	private String imageURLsFullSide;
	private String imageURLsFullBack;
	private String imageURLsGrid;
	private String imageURLsSmall;
	private String imageURLsSticker;
	private String imageURLsIcon;
	private String video;
	private String threeDSpin;
	private String support;
	private String helpurl;
	private String helptext;
	private String inTheBox;
	private Float rating;
	private String numReviews;
	private String mefProductName;
	private String isDeviceProduct;
	private String deliveryClassification;
	private String deliveryPartner;
	private String deliveryMethod;
	private String isBattery;
	private String deliveryOnWeekend;
	private String availableFrom;
	private String estDeliveryDate;
	private String isDisplayableEcare;
	private String isSellableAcq;
	private String productSubClass;
	private String weekEnd;
	private String type;
	private String amount;
	private String allowanceUOM;
	private String tilUOM;
	private String displayUOM;
	private String conversionFactor;
	private String preDesc;
	private String postDesc;
	private String preDescMobile;
	private List<String> listOfCompatibleBundles;
	private String leadPlanId;

	private String dataUOM;
	private String dataValue;
	private String ukTextsUOM;
	private String ukTextsValue;
	private String ukMinutesUOM;
	private String ukMinutesValue;
	private String ukDataUOM;
	private String ukDataValue;
	private String wifiUOM;
	private String wifiValue;
	private String europeanRoamingDataUOM;
	private String europeanRoamingDataValue;
	private String europeanRoamingTextsUOM;
	private String europeanRoamingTextsValue;
	private String europeanRoamingMinutesUOM;
	private String europeanRoamingMinutesValue;
	private String europeanRoamingPictureMessagesUOM;
	private String europeanRoamingPictureMessagesValue;
	private String internationalTextsUOM;
	private String internationalTextsValue;
	private String etdataUOM;
	private String etdataValue;
	private String globalRoamingDataUOM;
	private String globalRoamingDataValue;
	private String globalRoamingTextsUOM;
	private String globalRoamingTextsValue;
	private String globalRoamingMinutesUOM;
	private String globalRoamingMinutesValue;
	private String roamingDataUOM;
	private String roamingDataValue;
	private String uKmobileMinutesUOM;
	private String uKmobileMinutesValue;
	private String vodafoneVodafonetextsUOM;
	private String vodafoneVodafonetextsValue;
	private String pictureMessagesUOM;
	private String pictureMessagesValue;
	private String uKANDInternationalCreditUOM;
	private String uKANDInternationalCreditValue;
	private String internationalMinutesValue;
	private String vodafoneVodafoneUOM;
	private String vodafoneVodafoneValue;
	private String vodafoneVodafoneMinutesUOM;
	private String vodafoneVodafoneMinutesValue;
	private String landlineMinutesUOM;
	private String landlineMinutesValue;
	private String zereightnumberminutesUOM;
	private String zereightnumberminutesValue;
	private String extraimeuandrowUOM;
	private String extraimeuandrowValue;
	private String extraimeuandusUOM;
	private String extraimeuandusValue;

	private String leadPlanIdNew;

	private String productGroupName;

	private String productGroupId;

	private Float oneOffGrossPrice;

	private Float oneOffNetPrice;

	private Float oneOffVatPrice;

	private Float oneOffDiscountedGrossPrice;

	private Float oneOffDiscountedNetPrice;

	private Float oneOffDiscountedVatPrice;
	@JsonProperty(value = "promoteAsOpt")
	private List<String> promoteAs;

	private Float bundleMonthlyPriceGross;

	private Float bundleMonthlyPriceNet;

	private Float bundleMonthlyPriceVat;

	private Float bundleMonthlyDiscPriceGross;

	private Float bundleMonthlyDiscPriceNet;

	private Float bundleMonthlyDiscPriceVat;
	@JsonProperty(value = "productGroupsOpt")
	private List<String> productGroups;

	private List<String> merchandisingMedia = new ArrayList<>();

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date startDateFormatted;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date endDateFormatted;

	private String upgradeLeadPlanId;

	private String nonUpgradeLeadPlanId;

	private List<String> miscKeyValue;

	private String imageURLsThumbsMarketing1;

	private String imageURLsThumbsMarketing2;

	private String imageURLsThumbsMarketing3;

	private String imageURLsThumbsFrontAngle;

	private String imageURLsThumbsBackAngle;

	private String imageURLsFullMarketing1;

	private String imageURLsFullMarketing2;

	private String imageURLsFullMarketing3;

	private String imageURLsFullFrontAngle;

	private String imageURLsFullBackAngle;

	private String imageURLsThumbsBack;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date createDate;

	// @JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date modifiedDate;

	private List<String> seoRobots;

	private String maxQuantity;

	private String ageRestricted;

	private String internationalMinutesUOM;

	private List<DeviceFinancingOption> financingOptions = null;

}
