package com.vf.uk.dal.device.datamodel.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductModel {


	private String id;

	private String productId;

	private String productName;

	private String parentProductId;

	private Integer order;

	private String productClass;

	private String isServicesProduct;

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

	private String internationalMinutesUOM;

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
	
	@JsonProperty("leadPlanId")
	private String leadPlanIdNew;

	private String productGroupName;

	private String productGroupId;

	private Float oneOffGrossPrice;

	private Float oneOffNetPrice;

	private Float oneOffVatPrice;

	private Float oneOffDiscountedGrossPrice;

	private Float oneOffDiscountedNetPrice;

	private Float oneOffDiscountedVatPrice;

	private List<String> promoteAs;

	private Float bundleMonthlyPriceGross;

	private Float bundleMonthlyPriceNet;

	private Float bundleMonthlyPriceVat;

	private Float bundleMonthlyDiscPriceGross;

	private Float bundleMonthlyDiscPriceNet;

	private Float bundleMonthlyDiscPriceVat;

	private List<String> productGroups;

	private List<String> merchandisingMedia = new ArrayList<>();
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date startDateFormatted;
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
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
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date createDate;
	
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date modifiedDate;

	private List<String> seoRobots;

	private String maxQuantity;

	private String ageRestricted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getProductClass() {
		return productClass;
	}

	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	public String getIsServicesProduct() {
		return isServicesProduct;
	}

	public void setIsServicesProduct(String isServicesProduct) {
		this.isServicesProduct = isServicesProduct;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDurationUOM() {
		return durationUOM;
	}

	public void setDurationUOM(String durationUOM) {
		this.durationUOM = durationUOM;
	}

	public String getProductStartDate() {
		return productStartDate;
	}

	public void setProductStartDate(String productStartDate) {
		this.productStartDate = productStartDate;
	}

	public String getProductEndDate() {
		return productEndDate;
	}

	public void setProductEndDate(String productEndDate) {
		this.productEndDate = productEndDate;
	}

	public String getSalesExpired() {
		return salesExpired;
	}

	public void setSalesExpired(String salesExpired) {
		this.salesExpired = salesExpired;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public float getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
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

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getControlOrder() {
		return controlOrder;
	}

	public void setControlOrder(String controlOrder) {
		this.controlOrder = controlOrder;
	}

	public String getPreOrderable() {
		return preOrderable;
	}

	public void setPreOrderable(String preOrderable) {
		this.preOrderable = preOrderable;
	}

	public String getBackOrderable() {
		return backOrderable;
	}

	public void setBackOrderable(String backOrderable) {
		this.backOrderable = backOrderable;
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

	public String getPdGrpvariant() {
		return pdGrpvariant;
	}

	public void setPdGrpvariant(String pdGrpvariant) {
		this.pdGrpvariant = pdGrpvariant;
	}

	public String getPdGrpCmpAcc() {
		return pdGrpCmpAcc;
	}

	public void setPdGrpCmpAcc(String pdGrpCmpAcc) {
		this.pdGrpCmpAcc = pdGrpCmpAcc;
	}

	public String getPdGrpRecAcc() {
		return pdGrpRecAcc;
	}

	public void setPdGrpRecAcc(String pdGrpRecAcc) {
		this.pdGrpRecAcc = pdGrpRecAcc;
	}

	public String getPdGrpRecExtr() {
		return pdGrpRecExtr;
	}

	public void setPdGrpRecExtr(String pdGrpRecExtr) {
		this.pdGrpRecExtr = pdGrpRecExtr;
	}

	public String getCompatibleEntertainment() {
		return compatibleEntertainment;
	}

	public void setCompatibleEntertainment(String compatibleEntertainment) {
		this.compatibleEntertainment = compatibleEntertainment;
	}

	public String getPriceNetOVR() {
		return priceNetOVR;
	}

	public void setPriceNetOVR(String priceNetOVR) {
		this.priceNetOVR = priceNetOVR;
	}

	public String getPriceGrossOVR() {
		return priceGrossOVR;
	}

	public void setPriceGrossOVR(String priceGrossOVR) {
		this.priceGrossOVR = priceGrossOVR;
	}

	public String getPriceVatOVR() {
		return priceVatOVR;
	}

	public void setPriceVatOVR(String priceVatOVR) {
		this.priceVatOVR = priceVatOVR;
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

	public String getEligibiltiySubflow() {
		return eligibiltiySubflow;
	}

	public void setEligibiltiySubflow(String eligibiltiySubflow) {
		this.eligibiltiySubflow = eligibiltiySubflow;
	}

	public String getEquipmentMake() {
		return equipmentMake;
	}

	public void setEquipmentMake(String equipmentMake) {
		this.equipmentMake = equipmentMake;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPostDescMobile() {
		return postDescMobile;
	}

	public void setPostDescMobile(String postDescMobile) {
		this.postDescMobile = postDescMobile;
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

	public String getImageURLsFullHero() {
		return imageURLsFullHero;
	}

	public void setImageURLsFullHero(String imageURLsFullHero) {
		this.imageURLsFullHero = imageURLsFullHero;
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

	public String getHelpurl() {
		return helpurl;
	}

	public void setHelpurl(String helpurl) {
		this.helpurl = helpurl;
	}

	public String getHelptext() {
		return helptext;
	}

	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}

	public String getInTheBox() {
		return inTheBox;
	}

	public void setInTheBox(String inTheBox) {
		this.inTheBox = inTheBox;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getNumReviews() {
		return numReviews;
	}

	public void setNumReviews(String numReviews) {
		this.numReviews = numReviews;
	}

	public String getMefProductName() {
		return mefProductName;
	}

	public void setMefProductName(String mefProductName) {
		this.mefProductName = mefProductName;
	}

	public String getIsDeviceProduct() {
		return isDeviceProduct;
	}

	public void setIsDeviceProduct(String isDeviceProduct) {
		this.isDeviceProduct = isDeviceProduct;
	}

	public String getDeliveryClassification() {
		return deliveryClassification;
	}

	public void setDeliveryClassification(String deliveryClassification) {
		this.deliveryClassification = deliveryClassification;
	}

	public String getDeliveryPartner() {
		return deliveryPartner;
	}

	public void setDeliveryPartner(String deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getIsBattery() {
		return isBattery;
	}

	public void setIsBattery(String isBattery) {
		this.isBattery = isBattery;
	}

	public String getDeliveryOnWeekend() {
		return deliveryOnWeekend;
	}

	public void setDeliveryOnWeekend(String deliveryOnWeekend) {
		this.deliveryOnWeekend = deliveryOnWeekend;
	}

	public String getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}

	public String getEstDeliveryDate() {
		return estDeliveryDate;
	}

	public void setEstDeliveryDate(String estDeliveryDate) {
		this.estDeliveryDate = estDeliveryDate;
	}

	public String getIsDisplayableEcare() {
		return isDisplayableEcare;
	}

	public void setIsDisplayableEcare(String isDisplayableEcare) {
		this.isDisplayableEcare = isDisplayableEcare;
	}

	public String getIsSellableAcq() {
		return isSellableAcq;
	}

	public void setIsSellableAcq(String isSellableAcq) {
		this.isSellableAcq = isSellableAcq;
	}

	public String getProductSubClass() {
		return productSubClass;
	}

	public void setProductSubClass(String productSubClass) {
		this.productSubClass = productSubClass;
	}

	public String getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAllowanceUOM() {
		return allowanceUOM;
	}

	public void setAllowanceUOM(String allowanceUOM) {
		this.allowanceUOM = allowanceUOM;
	}

	public String getTilUOM() {
		return tilUOM;
	}

	public void setTilUOM(String tilUOM) {
		this.tilUOM = tilUOM;
	}

	public String getDisplayUOM() {
		return displayUOM;
	}

	public void setDisplayUOM(String displayUOM) {
		this.displayUOM = displayUOM;
	}

	public String getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
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

	public List<String> getListOfCompatibleBundles() {
		return listOfCompatibleBundles;
	}

	public void setListOfCompatibleBundles(List<String> listOfCompatibleBundles) {
		this.listOfCompatibleBundles = listOfCompatibleBundles;
	}

	public String getLeadPlanId() {
		return leadPlanId;
	}

	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
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

	public String getZereightnumberminutesUOM() {
		return zereightnumberminutesUOM;
	}

	public void setZereightnumberminutesUOM(String zereightnumberminutesUOM) {
		this.zereightnumberminutesUOM = zereightnumberminutesUOM;
	}

	public String getZereightnumberminutesValue() {
		return zereightnumberminutesValue;
	}

	public void setZereightnumberminutesValue(String zereightnumberminutesValue) {
		this.zereightnumberminutesValue = zereightnumberminutesValue;
	}

	public String getExtraimeuandrowUOM() {
		return extraimeuandrowUOM;
	}

	public void setExtraimeuandrowUOM(String extraimeuandrowUOM) {
		this.extraimeuandrowUOM = extraimeuandrowUOM;
	}

	public String getExtraimeuandrowValue() {
		return extraimeuandrowValue;
	}

	public void setExtraimeuandrowValue(String extraimeuandrowValue) {
		this.extraimeuandrowValue = extraimeuandrowValue;
	}

	public String getExtraimeuandusUOM() {
		return extraimeuandusUOM;
	}

	public void setExtraimeuandusUOM(String extraimeuandusUOM) {
		this.extraimeuandusUOM = extraimeuandusUOM;
	}

	public String getExtraimeuandusValue() {
		return extraimeuandusValue;
	}

	public void setExtraimeuandusValue(String extraimeuandusValue) {
		this.extraimeuandusValue = extraimeuandusValue;
	}

	public String getLeadPlanIdNew() {
		return leadPlanIdNew;
	}
	@JsonProperty("leadPlanId")
	public void setLeadPlanIdNew(String leadPlanIdNew) {
		this.leadPlanIdNew = leadPlanIdNew;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public Float getOneOffGrossPrice() {
		return oneOffGrossPrice;
	}

	public void setOneOffGrossPrice(Float oneOffGrossPrice) {
		this.oneOffGrossPrice = oneOffGrossPrice;
	}

	public Float getOneOffNetPrice() {
		return oneOffNetPrice;
	}

	public void setOneOffNetPrice(Float oneOffNetPrice) {
		this.oneOffNetPrice = oneOffNetPrice;
	}

	public Float getOneOffVatPrice() {
		return oneOffVatPrice;
	}

	public void setOneOffVatPrice(Float oneOffVatPrice) {
		this.oneOffVatPrice = oneOffVatPrice;
	}

	public Float getOneOffDiscountedGrossPrice() {
		return oneOffDiscountedGrossPrice;
	}

	public void setOneOffDiscountedGrossPrice(Float oneOffDiscountedGrossPrice) {
		this.oneOffDiscountedGrossPrice = oneOffDiscountedGrossPrice;
	}

	public Float getOneOffDiscountedNetPrice() {
		return oneOffDiscountedNetPrice;
	}

	public void setOneOffDiscountedNetPrice(Float oneOffDiscountedNetPrice) {
		this.oneOffDiscountedNetPrice = oneOffDiscountedNetPrice;
	}

	public Float getOneOffDiscountedVatPrice() {
		return oneOffDiscountedVatPrice;
	}

	public void setOneOffDiscountedVatPrice(Float oneOffDiscountedVatPrice) {
		this.oneOffDiscountedVatPrice = oneOffDiscountedVatPrice;
	}

	public List<String> getPromoteAs() {
		return promoteAs;
	}

	public void setPromoteAs(List<String> promoteAs) {
		this.promoteAs = promoteAs;
	}

	public Float getBundleMonthlyPriceGross() {
		return bundleMonthlyPriceGross;
	}

	public void setBundleMonthlyPriceGross(Float bundleMonthlyPriceGross) {
		this.bundleMonthlyPriceGross = bundleMonthlyPriceGross;
	}

	public Float getBundleMonthlyPriceNet() {
		return bundleMonthlyPriceNet;
	}

	public void setBundleMonthlyPriceNet(Float bundleMonthlyPriceNet) {
		this.bundleMonthlyPriceNet = bundleMonthlyPriceNet;
	}

	public Float getBundleMonthlyPriceVat() {
		return bundleMonthlyPriceVat;
	}

	public void setBundleMonthlyPriceVat(Float bundleMonthlyPriceVat) {
		this.bundleMonthlyPriceVat = bundleMonthlyPriceVat;
	}

	public Float getBundleMonthlyDiscPriceGross() {
		return bundleMonthlyDiscPriceGross;
	}

	public void setBundleMonthlyDiscPriceGross(Float bundleMonthlyDiscPriceGross) {
		this.bundleMonthlyDiscPriceGross = bundleMonthlyDiscPriceGross;
	}

	public Float getBundleMonthlyDiscPriceNet() {
		return bundleMonthlyDiscPriceNet;
	}

	public void setBundleMonthlyDiscPriceNet(Float bundleMonthlyDiscPriceNet) {
		this.bundleMonthlyDiscPriceNet = bundleMonthlyDiscPriceNet;
	}

	public Float getBundleMonthlyDiscPriceVat() {
		return bundleMonthlyDiscPriceVat;
	}

	public void setBundleMonthlyDiscPriceVat(Float bundleMonthlyDiscPriceVat) {
		this.bundleMonthlyDiscPriceVat = bundleMonthlyDiscPriceVat;
	}

	public List<String> getProductGroups() {
		return productGroups;
	}

	public void setProductGroups(List<String> productGroups) {
		this.productGroups = productGroups;
	}

	public List<String> getMerchandisingMedia() {
		return merchandisingMedia;
	}

	public void setMerchandisingMedia(List<String> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
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

	public String getUpgradeLeadPlanId() {
		return upgradeLeadPlanId;
	}

	public void setUpgradeLeadPlanId(String upgradeLeadPlanId) {
		this.upgradeLeadPlanId = upgradeLeadPlanId;
	}

	public String getNonUpgradeLeadPlanId() {
		return nonUpgradeLeadPlanId;
	}

	public void setNonUpgradeLeadPlanId(String nonUpgradeLeadPlanId) {
		this.nonUpgradeLeadPlanId = nonUpgradeLeadPlanId;
	}

	public List<String> getMiscKeyValue() {
		return miscKeyValue;
	}

	public void setMiscKeyValue(List<String> miscKeyValue) {
		this.miscKeyValue = miscKeyValue;
	}

	public String getImageURLsThumbsMarketing1() {
		return imageURLsThumbsMarketing1;
	}

	public void setImageURLsThumbsMarketing1(String imageURLsThumbsMarketing1) {
		this.imageURLsThumbsMarketing1 = imageURLsThumbsMarketing1;
	}

	public String getImageURLsThumbsMarketing2() {
		return imageURLsThumbsMarketing2;
	}

	public void setImageURLsThumbsMarketing2(String imageURLsThumbsMarketing2) {
		this.imageURLsThumbsMarketing2 = imageURLsThumbsMarketing2;
	}

	public String getImageURLsThumbsMarketing3() {
		return imageURLsThumbsMarketing3;
	}

	public void setImageURLsThumbsMarketing3(String imageURLsThumbsMarketing3) {
		this.imageURLsThumbsMarketing3 = imageURLsThumbsMarketing3;
	}

	public String getImageURLsThumbsFrontAngle() {
		return imageURLsThumbsFrontAngle;
	}

	public void setImageURLsThumbsFrontAngle(String imageURLsThumbsFrontAngle) {
		this.imageURLsThumbsFrontAngle = imageURLsThumbsFrontAngle;
	}

	public String getImageURLsThumbsBackAngle() {
		return imageURLsThumbsBackAngle;
	}

	public void setImageURLsThumbsBackAngle(String imageURLsThumbsBackAngle) {
		this.imageURLsThumbsBackAngle = imageURLsThumbsBackAngle;
	}

	public String getImageURLsFullMarketing1() {
		return imageURLsFullMarketing1;
	}

	public void setImageURLsFullMarketing1(String imageURLsFullMarketing1) {
		this.imageURLsFullMarketing1 = imageURLsFullMarketing1;
	}

	public String getImageURLsFullMarketing2() {
		return imageURLsFullMarketing2;
	}

	public void setImageURLsFullMarketing2(String imageURLsFullMarketing2) {
		this.imageURLsFullMarketing2 = imageURLsFullMarketing2;
	}

	public String getImageURLsFullMarketing3() {
		return imageURLsFullMarketing3;
	}

	public void setImageURLsFullMarketing3(String imageURLsFullMarketing3) {
		this.imageURLsFullMarketing3 = imageURLsFullMarketing3;
	}

	public String getImageURLsFullFrontAngle() {
		return imageURLsFullFrontAngle;
	}

	public void setImageURLsFullFrontAngle(String imageURLsFullFrontAngle) {
		this.imageURLsFullFrontAngle = imageURLsFullFrontAngle;
	}

	public String getImageURLsFullBackAngle() {
		return imageURLsFullBackAngle;
	}

	public void setImageURLsFullBackAngle(String imageURLsFullBackAngle) {
		this.imageURLsFullBackAngle = imageURLsFullBackAngle;
	}

	public String getImageURLsThumbsBack() {
		return imageURLsThumbsBack;
	}

	public void setImageURLsThumbsBack(String imageURLsThumbsBack) {
		this.imageURLsThumbsBack = imageURLsThumbsBack;
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

	public String getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public String getAgeRestricted() {
		return ageRestricted;
	}

	public void setAgeRestricted(String ageRestricted) {
		this.ageRestricted = ageRestricted;
	}
	
	
}
