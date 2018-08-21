package com.vf.uk.dal.device.datamodel.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductModel
 * @author ashish.adhikari
 *
 */
@Data
public class ProductModel {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("productId")
	private String productId;
	
	@JsonProperty("productName")
	private String productName;
	
	@JsonProperty("parentProductId")
	private String parentProductId;
	
	@JsonProperty("order")
	private Integer order;
	
	@JsonProperty("productClass")
	private String productClass;
	
	@JsonProperty("isServicesProduct")
	private String isServicesProduct;

	@JsonProperty(value = "durationOpt")
	private int duration;
	
	@JsonProperty("durationUOM")
	private String durationUOM;

	
	@JsonProperty("productStartDate")
	private String productStartDate;
	
	@JsonProperty("productEndDate")
	private String productEndDate;
	
	@JsonProperty("salesExpired")
	private String salesExpired;
	
	@JsonProperty("discountType")
	private String discountType;
	
	@JsonProperty("discountAmount")
	private float discountAmount;
	
	@JsonProperty("warranty")
	private String warranty;
	
	@JsonProperty("condition")
	private String condition;
	
	@JsonProperty("priceType")
	private String priceType;
	
	@JsonProperty("productType")
	private String productType;
	
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
	
	@JsonProperty("controlOrder")
	private String controlOrder;
	
	@JsonProperty("preOrderable")
	private String preOrderable;
	
	@JsonProperty("backOrderable")
	private String backOrderable;
	
	@JsonProperty("affiliateExport")
	private String affiliateExport;
	
	@JsonProperty("compareWith")
	private String compareWith;
	
	@JsonProperty("pdGrpvariant")
	private String pdGrpvariant;
	
	@JsonProperty("pdGrpCmpAcc")
	private String pdGrpCmpAcc;
	
	@JsonProperty("pdGrpRecAcc")
	private String pdGrpRecAcc;
	
	@JsonProperty("pdGrpRecExtr")
	private String pdGrpRecExtr;
	
	@JsonProperty("compatibleEntertainment")
	private String compatibleEntertainment;
	
	@JsonProperty("priceNetOVR")
	private String priceNetOVR;
	
	@JsonProperty("priceGrossOVR")
	private String priceGrossOVR;
	
	@JsonProperty("priceVatOVR")
	private String priceVatOVR;
	
	@JsonProperty("seoCanonical")
	private String seoCanonical;
	
	@JsonProperty("seoDescription")
	private String seoDescription;
	
	@JsonProperty("seoKeywords")
	private String seoKeywords;
	
	@JsonProperty("seoIndex")
	private String seoIndex;
	
	@JsonProperty("eligibiltiySubflow")
	private String eligibiltiySubflow;
	
	@JsonProperty("equipmentMake")
	private String equipmentMake;
	
	@JsonProperty("equipmentModel")
	private String equipmentModel;
	
	@JsonProperty("displayName")
	private String displayName;
	
	@JsonProperty("postDescMobile")
	private String postDescMobile;
	
	@JsonProperty("imageURLsThumbsFront")
	private String imageURLsThumbsFront;
	
	@JsonProperty("imageURLsThumbsLeft")
	private String imageURLsThumbsLeft;
	
	@JsonProperty("imageURLsThumbsRight")
	private String imageURLsThumbsRight;
	
	@JsonProperty("imageURLsThumbsSide")
	private String imageURLsThumbsSide;
	
	@JsonProperty("imageURLsFullHero")
	private String imageURLsFullHero;
	
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
	
	@JsonProperty("video")
	private String video;
	
	@JsonProperty("threeDSpin")
	private String threeDSpin;
	
	@JsonProperty("support")
	private String support;
	
	@JsonProperty("helpurl")
	private String helpurl;
	
	@JsonProperty("helptext")
	private String helptext;
	
	@JsonProperty("inTheBox")
	private String inTheBox;
	
	@JsonProperty("rating")
	private Float rating;
	
	@JsonProperty("numReviews")
	private String numReviews;
	
	@JsonProperty("mefProductName")
	private String mefProductName;
	
	@JsonProperty("isDeviceProduct")
	private String isDeviceProduct;
	
	@JsonProperty("deliveryClassification")
	private String deliveryClassification;
	
	@JsonProperty("deliveryPartner")
	private String deliveryPartner;
	
	@JsonProperty("deliveryMethod")
	private String deliveryMethod;
	
	@JsonProperty("isBattery")
	private String isBattery;
	
	@JsonProperty("deliveryOnWeekend")
	private String deliveryOnWeekend;
	
	@JsonProperty("availableFrom")
	private String availableFrom;
	
	@JsonProperty("estDeliveryDate")
	private String estDeliveryDate;
	
	@JsonProperty("isDisplayableEcare")
	private String isDisplayableEcare;
	
	@JsonProperty("isSellableAcq")
	private String isSellableAcq;
	
	@JsonProperty("productSubClass")
	private String productSubClass;
	
	@JsonProperty("weekEnd")
	private String weekEnd;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("amount")
	private String amount;
	
	@JsonProperty("allowanceUOM")
	private String allowanceUOM;
	
	@JsonProperty("tilUOM")
	private String tilUOM;
	
	@JsonProperty("displayUOM")
	private String displayUOM;
	
	@JsonProperty("conversionFactor")
	private String conversionFactor;
	
	@JsonProperty("preDesc")
	private String preDesc;
	
	@JsonProperty("postDesc")
	private String postDesc;
	
	@JsonProperty("preDescMobile")
	private String preDescMobile;
	
	@JsonProperty("listOfCompatibleBundles")
	private List<String> listOfCompatibleBundles;
	
	@JsonProperty("leadPlanId")
	private String leadPlanId;

	@JsonProperty("dataUOM")
	private String dataUOM;
	
	@JsonProperty("dataValue")
	private String dataValue;
	
	@JsonProperty("ukTextsUOM")
	private String ukTextsUOM;
	
	@JsonProperty("ukTextsValue")
	private String ukTextsValue;
	
	@JsonProperty("ukMinutesUOM")
	private String ukMinutesUOM;
	
	@JsonProperty("ukMinutesValue")
	private String ukMinutesValue;
	
	@JsonProperty("ukDataUOM")
	private String ukDataUOM;
	
	@JsonProperty("ukDataValue")
	private String ukDataValue;
	
	@JsonProperty("wifiUOM")
	private String wifiUOM;
	
	@JsonProperty("wifiValue")
	private String wifiValue;
	
	@JsonProperty("europeanRoamingDataUOM")
	private String europeanRoamingDataUOM;
	
	@JsonProperty("europeanRoamingDataValue")
	private String europeanRoamingDataValue;
	
	@JsonProperty("europeanRoamingTextsUOM")
	private String europeanRoamingTextsUOM;
	
	@JsonProperty("europeanRoamingTextsValue")
	private String europeanRoamingTextsValue;
	
	@JsonProperty("europeanRoamingMinutesUOM")
	private String europeanRoamingMinutesUOM;
	
	@JsonProperty("europeanRoamingMinutesValue")
	private String europeanRoamingMinutesValue;
	
	@JsonProperty("europeanRoamingPictureMessagesUOM")
	private String europeanRoamingPictureMessagesUOM;
	
	@JsonProperty("europeanRoamingPictureMessagesValue")
	private String europeanRoamingPictureMessagesValue;
	
	@JsonProperty("internationalTextsUOM")
	private String internationalTextsUOM;
	
	@JsonProperty("internationalTextsValue")
	private String internationalTextsValue;
	
	@JsonProperty("etdataUOM")
	private String etdataUOM;
	
	@JsonProperty("etdataValue")
	private String etdataValue;
	
	@JsonProperty("globalRoamingDataUOM")
	private String globalRoamingDataUOM;
	
	@JsonProperty("globalRoamingDataValue")
	private String globalRoamingDataValue;
	
	@JsonProperty("globalRoamingTextsUOM")
	private String globalRoamingTextsUOM;
	
	@JsonProperty("globalRoamingTextsValue")
	private String globalRoamingTextsValue;
	
	@JsonProperty("globalRoamingMinutesUOM")
	private String globalRoamingMinutesUOM;
	
	@JsonProperty("globalRoamingMinutesValue")
	private String globalRoamingMinutesValue;
	
	@JsonProperty("roamingDataUOM")
	private String roamingDataUOM;
	
	@JsonProperty("roamingDataValue")
	private String roamingDataValue;
	
	@JsonProperty("uKmobileMinutesUOM")
	private String uKmobileMinutesUOM;
	
	@JsonProperty("uKmobileMinutesValue")
	private String uKmobileMinutesValue;
	
	@JsonProperty("vodafoneVodafonetextsUOM")
	private String vodafoneVodafonetextsUOM;
	
	@JsonProperty("vodafoneVodafonetextsValue")
	private String vodafoneVodafonetextsValue;
	
	@JsonProperty("pictureMessagesUOM")
	private String pictureMessagesUOM;
	
	@JsonProperty("pictureMessagesValue")
	private String pictureMessagesValue;
	
	@JsonProperty("uKANDInternationalCreditUOM")
	private String uKANDInternationalCreditUOM;
	
	@JsonProperty("uKANDInternationalCreditValue")
	private String uKANDInternationalCreditValue;
	
	@JsonProperty("internationalMinutesValue")
	private String internationalMinutesValue;
	
	@JsonProperty("vodafoneVodafoneUOM")
	private String vodafoneVodafoneUOM;
	
	@JsonProperty("vodafoneVodafoneValue")
	private String vodafoneVodafoneValue;
	
	@JsonProperty("vodafoneVodafoneMinutesUOM")
	private String vodafoneVodafoneMinutesUOM;
	
	@JsonProperty("vodafoneVodafoneMinutesValue")
	private String vodafoneVodafoneMinutesValue;
	
	@JsonProperty("landlineMinutesUOM")
	private String landlineMinutesUOM;
	
	@JsonProperty("landlineMinutesValue")
	private String landlineMinutesValue;
	
	@JsonProperty("zereightnumberminutesUOM")
	private String zereightnumberminutesUOM;
	
	@JsonProperty("zereightnumberminutesValue")
	private String zereightnumberminutesValue;
	
	@JsonProperty("extraimeuandrowUOM")
	private String extraimeuandrowUOM;
	
	@JsonProperty("extraimeuandrowValue")
	private String extraimeuandrowValue;
	
	@JsonProperty("extraimeuandusUOM")
	private String extraimeuandusUOM;
	
	@JsonProperty("extraimeuandusValue")
	private String extraimeuandusValue;

	@JsonProperty("leadPlanIdNew")
	private String leadPlanIdNew;

	@JsonProperty("productGroupName")
	private String productGroupName;

	@JsonProperty("productGroupId")
	private String productGroupId;

	@JsonProperty("oneOffGrossPrice")
	private Float oneOffGrossPrice;
	
	@JsonProperty("oneOffNetPrice")
	private Float oneOffNetPrice;

	@JsonProperty("oneOffVatPrice")
	private Float oneOffVatPrice;

	@JsonProperty("oneOffDiscountedGrossPrice")
	private Float oneOffDiscountedGrossPrice;

	@JsonProperty("oneOffDiscountedNetPrice")
	private Float oneOffDiscountedNetPrice;

	@JsonProperty("oneOffDiscountedVatPrice")
	private Float oneOffDiscountedVatPrice;
	
	@JsonProperty(value = "promoteAsOpt")
	private List<String> promoteAs;

	@JsonProperty("bundleMonthlyPriceGross")
	private Float bundleMonthlyPriceGross;

	@JsonProperty("bundleMonthlyPriceNet")
	private Float bundleMonthlyPriceNet;

	@JsonProperty("bundleMonthlyPriceVat")
	private Float bundleMonthlyPriceVat;

	@JsonProperty("bundleMonthlyDiscPriceGross")
	private Float bundleMonthlyDiscPriceGross;

	@JsonProperty("bundleMonthlyDiscPriceNet")
	private Float bundleMonthlyDiscPriceNet;

	@JsonProperty("bundleMonthlyDiscPriceVat")
	private Float bundleMonthlyDiscPriceVat;
	
	@JsonProperty(value = "productGroupsOpt")
	private List<String> productGroups;

	@JsonProperty("merchandisingMedia")
	private List<String> merchandisingMedia = new ArrayList<>();

	@JsonProperty("startDateFormatted")
	private Date startDateFormatted;

	@JsonProperty("endDateFormatted")
	private Date endDateFormatted;

	@JsonProperty("upgradeLeadPlanId")
	private String upgradeLeadPlanId;

	@JsonProperty("nonUpgradeLeadPlanId")
	private String nonUpgradeLeadPlanId;

	@JsonProperty("miscKeyValue")
	private List<String> miscKeyValue;

	@JsonProperty("imageURLsThumbsMarketing1")
	private String imageURLsThumbsMarketing1;

	@JsonProperty("imageURLsThumbsMarketing2")
	private String imageURLsThumbsMarketing2;

	@JsonProperty("imageURLsThumbsMarketing3")
	private String imageURLsThumbsMarketing3;

	@JsonProperty("imageURLsThumbsFrontAngle")
	private String imageURLsThumbsFrontAngle;

	@JsonProperty("imageURLsThumbsBackAngle")
	private String imageURLsThumbsBackAngle;

	@JsonProperty("imageURLsFullMarketing1")
	private String imageURLsFullMarketing1;

	@JsonProperty("imageURLsFullMarketing2")
	private String imageURLsFullMarketing2;

	@JsonProperty("imageURLsFullMarketing3")
	private String imageURLsFullMarketing3;

	@JsonProperty("imageURLsFullFrontAngle")
	private String imageURLsFullFrontAngle;

	@JsonProperty("imageURLsFullBackAngle")
	private String imageURLsFullBackAngle;

	@JsonProperty("imageURLsThumbsBack")
	private String imageURLsThumbsBack;

	@JsonProperty("createDate")
	private Date createDate;

	@JsonProperty("modifiedDate")
	private Date modifiedDate;

	@JsonProperty("seoRobots")
	private List<String> seoRobots;

	@JsonProperty("maxQuantity")
	private String maxQuantity;

	@JsonProperty("ageRestricted")
	private String ageRestricted;

	@JsonProperty("internationalMinutesUOM")
	private String internationalMinutesUOM;

	@JsonProperty("financingOptions")
	private List<DeviceFinancingOption> financingOptions = null;

}
