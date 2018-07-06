package com.vf.uk.dal.device.datamodel.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author ashish.adhikari
 *
 */
public class ProductModel {
	private String id;
	private String productId;
	private String productName;
	private String parentProductId;
	private Integer order;
	private String productClass;
	private String isServicesProduct;
	@JsonProperty(value="durationOpt")
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
	@JsonProperty(value="promoteAsOpt")
	private List<String> promoteAs;
	
	private Float bundleMonthlyPriceGross;
	
	private Float bundleMonthlyPriceNet;
	
	private Float bundleMonthlyPriceVat;
	
	private Float bundleMonthlyDiscPriceGross;
	
	private Float bundleMonthlyDiscPriceNet;
	
	private Float bundleMonthlyDiscPriceVat;
	@JsonProperty(value="productGroupsOpt")
	private List<String> productGroups;
	
	private List<String> merchandisingMedia=new ArrayList<>();
	
	//@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date startDateFormatted;
	
	//@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
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
	
	//@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date createDate;
	
	//@JsonFormat(pattern="EEE MMM dd HH:mm:ss z yyyy")
	private Date modifiedDate;
	
	private List<String> seoRobots;
	
	private String maxQuantity;
	
	private String ageRestricted;
	private String internationalMinutesUOM;
	private List<DeviceFinancingOption> financingOptions = null;

	public List<DeviceFinancingOption> getFinancingOptions() {
		return financingOptions;
	}
	
	/**
	 * 
	 */
	public ProductModel() {
		/*
		 * 
		 */
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the parentProductId
	 */
	public String getParentProductId() {
		return parentProductId;
	}
	/**
	 * @param parentProductId the parentProductId to set
	 */
	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * @return the productClass
	 */
	public String getProductClass() {
		return productClass;
	}
	/**
	 * @param productClass the productClass to set
	 */
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	/**
	 * @return the isServicesProduct
	 */
	public String getIsServicesProduct() {
		return isServicesProduct;
	}
	/**
	 * @param isServicesProduct the isServicesProduct to set
	 */
	public void setIsServicesProduct(String isServicesProduct) {
		this.isServicesProduct = isServicesProduct;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * @return the durationUOM
	 */
	public String getDurationUOM() {
		return durationUOM;
	}
	/**
	 * @param durationUOM the durationUOM to set
	 */
	public void setDurationUOM(String durationUOM) {
		this.durationUOM = durationUOM;
	}
	/**
	 * @return the productStartDate
	 */
	public String getProductStartDate() {
		return productStartDate;
	}
	/**
	 * @param productStartDate the productStartDate to set
	 */
	public void setProductStartDate(String productStartDate) {
		this.productStartDate = productStartDate;
	}
	/**
	 * @return the productEndDate
	 */
	public String getProductEndDate() {
		return productEndDate;
	}
	/**
	 * @param productEndDate the productEndDate to set
	 */
	public void setProductEndDate(String productEndDate) {
		this.productEndDate = productEndDate;
	}
	/**
	 * @return the salesExpired
	 */
	public String getSalesExpired() {
		return salesExpired;
	}
	/**
	 * @param salesExpired the salesExpired to set
	 */
	public void setSalesExpired(String salesExpired) {
		this.salesExpired = salesExpired;
	}
	/**
	 * @return the discountType
	 */
	public String getDiscountType() {
		return discountType;
	}
	/**
	 * @param discountType the discountType to set
	 */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	/**
	 * @return the discountAmount
	 */
	public float getDiscountAmount() {
		return discountAmount;
	}
	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}
	/**
	 * @return the warranty
	 */
	public String getWarranty() {
		return warranty;
	}
	/**
	 * @param warranty the warranty to set
	 */
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the priceType
	 */
	public String getPriceType() {
		return priceType;
	}
	/**
	 * @param priceType the priceType to set
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the isDisplayableAcq
	 */
	public String getIsDisplayableAcq() {
		return isDisplayableAcq;
	}
	/**
	 * @param isDisplayableAcq the isDisplayableAcq to set
	 */
	public void setIsDisplayableAcq(String isDisplayableAcq) {
		this.isDisplayableAcq = isDisplayableAcq;
	}
	/**
	 * @return the isSellableECare
	 */
	public String getIsSellableECare() {
		return isSellableECare;
	}
	/**
	 * @param isSellableECare the isSellableECare to set
	 */
	public void setIsSellableECare(String isSellableECare) {
		this.isSellableECare = isSellableECare;
	}
	/**
	 * @return the isSellableRet
	 */
	public String getIsSellableRet() {
		return isSellableRet;
	}
	/**
	 * @param isSellableRet the isSellableRet to set
	 */
	public void setIsSellableRet(String isSellableRet) {
		this.isSellableRet = isSellableRet;
	}
	/**
	 * @return the isDisplayableRet
	 */
	public String getIsDisplayableRet() {
		return isDisplayableRet;
	}
	/**
	 * @param isDisplayableRet the isDisplayableRet to set
	 */
	public void setIsDisplayableRet(String isDisplayableRet) {
		this.isDisplayableRet = isDisplayableRet;
	}
	/**
	 * @return the isDisplaybaleSavedBasket
	 */
	public String getIsDisplaybaleSavedBasket() {
		return isDisplaybaleSavedBasket;
	}
	/**
	 * @param isDisplaybaleSavedBasket the isDisplaybaleSavedBasket to set
	 */
	public void setIsDisplaybaleSavedBasket(String isDisplaybaleSavedBasket) {
		this.isDisplaybaleSavedBasket = isDisplaybaleSavedBasket;
	}
	/**
	 * @return the controlOrder
	 */
	public String getControlOrder() {
		return controlOrder;
	}
	/**
	 * @param controlOrder the controlOrder to set
	 */
	public void setControlOrder(String controlOrder) {
		this.controlOrder = controlOrder;
	}
	/**
	 * @return the preOrderable
	 */
	public String getPreOrderable() {
		return preOrderable;
	}
	/**
	 * @param preOrderable the preOrderable to set
	 */
	public void setPreOrderable(String preOrderable) {
		this.preOrderable = preOrderable;
	}
	/**
	 * @return the backOrderable
	 */
	public String getBackOrderable() {
		return backOrderable;
	}
	/**
	 * @param backOrderable the backOrderable to set
	 */
	public void setBackOrderable(String backOrderable) {
		this.backOrderable = backOrderable;
	}
	/**
	 * @return the affiliateExport
	 */
	public String getAffiliateExport() {
		return affiliateExport;
	}
	/**
	 * @param affiliateExport the affiliateExport to set
	 */
	public void setAffiliateExport(String affiliateExport) {
		this.affiliateExport = affiliateExport;
	}
	/**
	 * @return the compareWith
	 */
	public String getCompareWith() {
		return compareWith;
	}
	/**
	 * @param compareWith the compareWith to set
	 */
	public void setCompareWith(String compareWith) {
		this.compareWith = compareWith;
	}
	/**
	 * @return the pdGrpvariant
	 */
	public String getPdGrpvariant() {
		return pdGrpvariant;
	}
	/**
	 * @param pdGrpvariant the pdGrpvariant to set
	 */
	public void setPdGrpvariant(String pdGrpvariant) {
		this.pdGrpvariant = pdGrpvariant;
	}
	/**
	 * @return the pdGrpCmpAcc
	 */
	public String getPdGrpCmpAcc() {
		return pdGrpCmpAcc;
	}
	/**
	 * @param pdGrpCmpAcc the pdGrpCmpAcc to set
	 */
	public void setPdGrpCmpAcc(String pdGrpCmpAcc) {
		this.pdGrpCmpAcc = pdGrpCmpAcc;
	}
	/**
	 * @return the pdGrpRecAcc
	 */
	public String getPdGrpRecAcc() {
		return pdGrpRecAcc;
	}
	/**
	 * @param pdGrpRecAcc the pdGrpRecAcc to set
	 */
	public void setPdGrpRecAcc(String pdGrpRecAcc) {
		this.pdGrpRecAcc = pdGrpRecAcc;
	}
	/**
	 * @return the pdGrpRecExtr
	 */
	public String getPdGrpRecExtr() {
		return pdGrpRecExtr;
	}
	/**
	 * @param pdGrpRecExtr the pdGrpRecExtr to set
	 */
	public void setPdGrpRecExtr(String pdGrpRecExtr) {
		this.pdGrpRecExtr = pdGrpRecExtr;
	}
	/**
	 * @return the compatibleEntertainment
	 */
	public String getCompatibleEntertainment() {
		return compatibleEntertainment;
	}
	/**
	 * @param compatibleEntertainment the compatibleEntertainment to set
	 */
	public void setCompatibleEntertainment(String compatibleEntertainment) {
		this.compatibleEntertainment = compatibleEntertainment;
	}
	/**
	 * @return the priceNetOVR
	 */
	public String getPriceNetOVR() {
		return priceNetOVR;
	}
	/**
	 * @param priceNetOVR the priceNetOVR to set
	 */
	public void setPriceNetOVR(String priceNetOVR) {
		this.priceNetOVR = priceNetOVR;
	}
	/**
	 * @return the priceGrossOVR
	 */
	public String getPriceGrossOVR() {
		return priceGrossOVR;
	}
	/**
	 * @param priceGrossOVR the priceGrossOVR to set
	 */
	public void setPriceGrossOVR(String priceGrossOVR) {
		this.priceGrossOVR = priceGrossOVR;
	}
	/**
	 * @return the priceVatOVR
	 */
	public String getPriceVatOVR() {
		return priceVatOVR;
	}
	/**
	 * @param priceVatOVR the priceVatOVR to set
	 */
	public void setPriceVatOVR(String priceVatOVR) {
		this.priceVatOVR = priceVatOVR;
	}
	/**
	 * @return the seoCanonical
	 */
	public String getSeoCanonical() {
		return seoCanonical;
	}
	/**
	 * @param seoCanonical the seoCanonical to set
	 */
	public void setSeoCanonical(String seoCanonical) {
		this.seoCanonical = seoCanonical;
	}
	/**
	 * @return the seoDescription
	 */
	public String getSeoDescription() {
		return seoDescription;
	}
	/**
	 * @param seoDescription the seoDescription to set
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	/**
	 * @return the seoKeywords
	 */
	public String getSeoKeywords() {
		return seoKeywords;
	}
	/**
	 * @param seoKeywords the seoKeywords to set
	 */
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	/**
	 * @return the seoIndex
	 */
	public String getSeoIndex() {
		return seoIndex;
	}
	/**
	 * @param seoIndex the seoIndex to set
	 */
	public void setSeoIndex(String seoIndex) {
		this.seoIndex = seoIndex;
	}
	/**
	 * @return the eligibiltiySubflow
	 */
	public String getEligibiltiySubflow() {
		return eligibiltiySubflow;
	}
	/**
	 * @param eligibiltiySubflow the eligibiltiySubflow to set
	 */
	public void setEligibiltiySubflow(String eligibiltiySubflow) {
		this.eligibiltiySubflow = eligibiltiySubflow;
	}
	/**
	 * @return the equipmentMake
	 */
	public String getEquipmentMake() {
		return equipmentMake;
	}
	/**
	 * @param equipmentMake the equipmentMake to set
	 */
	public void setEquipmentMake(String equipmentMake) {
		this.equipmentMake = equipmentMake;
	}
	/**
	 * @return the equipmentModel
	 */
	public String getEquipmentModel() {
		return equipmentModel;
	}
	/**
	 * @param equipmentModel the equipmentModel to set
	 */
	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the postDescMobile
	 */
	public String getPostDescMobile() {
		return postDescMobile;
	}
	/**
	 * @param postDescMobile the postDescMobile to set
	 */
	public void setPostDescMobile(String postDescMobile) {
		this.postDescMobile = postDescMobile;
	}
	/**
	 * @return the imageURLsThumbsFront
	 */
	public String getImageURLsThumbsFront() {
		return imageURLsThumbsFront;
	}
	/**
	 * @param imageURLsThumbsFront the imageURLsThumbsFront to set
	 */
	public void setImageURLsThumbsFront(String imageURLsThumbsFront) {
		this.imageURLsThumbsFront = imageURLsThumbsFront;
	}
	/**
	 * @return the imageURLsThumbsLeft
	 */
	public String getImageURLsThumbsLeft() {
		return imageURLsThumbsLeft;
	}
	/**
	 * @param imageURLsThumbsLeft the imageURLsThumbsLeft to set
	 */
	public void setImageURLsThumbsLeft(String imageURLsThumbsLeft) {
		this.imageURLsThumbsLeft = imageURLsThumbsLeft;
	}
	/**
	 * @return the imageURLsThumbsRight
	 */
	public String getImageURLsThumbsRight() {
		return imageURLsThumbsRight;
	}
	/**
	 * @param imageURLsThumbsRight the imageURLsThumbsRight to set
	 */
	public void setImageURLsThumbsRight(String imageURLsThumbsRight) {
		this.imageURLsThumbsRight = imageURLsThumbsRight;
	}
	/**
	 * @return the imageURLsThumbsSide
	 */
	public String getImageURLsThumbsSide() {
		return imageURLsThumbsSide;
	}
	/**
	 * @param imageURLsThumbsSide the imageURLsThumbsSide to set
	 */
	public void setImageURLsThumbsSide(String imageURLsThumbsSide) {
		this.imageURLsThumbsSide = imageURLsThumbsSide;
	}
	/**
	 * @return the imageURLsFullHero
	 */
	public String getImageURLsFullHero() {
		return imageURLsFullHero;
	}
	/**
	 * @param imageURLsFullHero the imageURLsFullHero to set
	 */
	public void setImageURLsFullHero(String imageURLsFullHero) {
		this.imageURLsFullHero = imageURLsFullHero;
	}
	/**
	 * @return the imageURLsFullFront
	 */
	public String getImageURLsFullFront() {
		return imageURLsFullFront;
	}
	/**
	 * @param imageURLsFullFront the imageURLsFullFront to set
	 */
	public void setImageURLsFullFront(String imageURLsFullFront) {
		this.imageURLsFullFront = imageURLsFullFront;
	}
	/**
	 * @return the imageURLsFullLeft
	 */
	public String getImageURLsFullLeft() {
		return imageURLsFullLeft;
	}
	/**
	 * @param imageURLsFullLeft the imageURLsFullLeft to set
	 */
	public void setImageURLsFullLeft(String imageURLsFullLeft) {
		this.imageURLsFullLeft = imageURLsFullLeft;
	}
	/**
	 * @return the imageURLsFullRight
	 */
	public String getImageURLsFullRight() {
		return imageURLsFullRight;
	}
	/**
	 * @param imageURLsFullRight the imageURLsFullRight to set
	 */
	public void setImageURLsFullRight(String imageURLsFullRight) {
		this.imageURLsFullRight = imageURLsFullRight;
	}
	/**
	 * @return the imageURLsFullSide
	 */
	public String getImageURLsFullSide() {
		return imageURLsFullSide;
	}
	/**
	 * @param imageURLsFullSide the imageURLsFullSide to set
	 */
	public void setImageURLsFullSide(String imageURLsFullSide) {
		this.imageURLsFullSide = imageURLsFullSide;
	}
	/**
	 * @return the imageURLsFullBack
	 */
	public String getImageURLsFullBack() {
		return imageURLsFullBack;
	}
	/**
	 * @param imageURLsFullBack the imageURLsFullBack to set
	 */
	public void setImageURLsFullBack(String imageURLsFullBack) {
		this.imageURLsFullBack = imageURLsFullBack;
	}
	/**
	 * @return the imageURLsGrid
	 */
	public String getImageURLsGrid() {
		return imageURLsGrid;
	}
	/**
	 * @param imageURLsGrid the imageURLsGrid to set
	 */
	public void setImageURLsGrid(String imageURLsGrid) {
		this.imageURLsGrid = imageURLsGrid;
	}
	/**
	 * @return the imageURLsSmall
	 */
	public String getImageURLsSmall() {
		return imageURLsSmall;
	}
	/**
	 * @param imageURLsSmall the imageURLsSmall to set
	 */
	public void setImageURLsSmall(String imageURLsSmall) {
		this.imageURLsSmall = imageURLsSmall;
	}
	/**
	 * @return the imageURLsSticker
	 */
	public String getImageURLsSticker() {
		return imageURLsSticker;
	}
	/**
	 * @param imageURLsSticker the imageURLsSticker to set
	 */
	public void setImageURLsSticker(String imageURLsSticker) {
		this.imageURLsSticker = imageURLsSticker;
	}
	/**
	 * @return the imageURLsIcon
	 */
	public String getImageURLsIcon() {
		return imageURLsIcon;
	}
	/**
	 * @param imageURLsIcon the imageURLsIcon to set
	 */
	public void setImageURLsIcon(String imageURLsIcon) {
		this.imageURLsIcon = imageURLsIcon;
	}
	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}
	/**
	 * @param video the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}
	/**
	 * @return the threeDSpin
	 */
	public String getThreeDSpin() {
		return threeDSpin;
	}
	/**
	 * @param threeDSpin the threeDSpin to set
	 */
	public void setThreeDSpin(String threeDSpin) {
		this.threeDSpin = threeDSpin;
	}
	/**
	 * @return the support
	 */
	public String getSupport() {
		return support;
	}
	/**
	 * @param support the support to set
	 */
	public void setSupport(String support) {
		this.support = support;
	}
	/**
	 * @return the helpurl
	 */
	public String getHelpurl() {
		return helpurl;
	}
	/**
	 * @param helpurl the helpurl to set
	 */
	public void setHelpurl(String helpurl) {
		this.helpurl = helpurl;
	}
	/**
	 * @return the helptext
	 */
	public String getHelptext() {
		return helptext;
	}
	/**
	 * @param helptext the helptext to set
	 */
	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}
	/**
	 * @return the inTheBox
	 */
	public String getInTheBox() {
		return inTheBox;
	}
	/**
	 * @param inTheBox the inTheBox to set
	 */
	public void setInTheBox(String inTheBox) {
		this.inTheBox = inTheBox;
	}
	/**
	 * @return the rating
	 */
	public Float getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Float rating) {
		this.rating = rating;
	}
	/**
	 * @return the numReviews
	 */
	public String getNumReviews() {
		return numReviews;
	}
	/**
	 * @param numReviews the numReviews to set
	 */
	public void setNumReviews(String numReviews) {
		this.numReviews = numReviews;
	}
	/**
	 * @return the mefProductName
	 */
	public String getMefProductName() {
		return mefProductName;
	}
	/**
	 * @param mefProductName the mefProductName to set
	 */
	public void setMefProductName(String mefProductName) {
		this.mefProductName = mefProductName;
	}
	/**
	 * @return the isDeviceProduct
	 */
	public String getIsDeviceProduct() {
		return isDeviceProduct;
	}
	/**
	 * @param isDeviceProduct the isDeviceProduct to set
	 */
	public void setIsDeviceProduct(String isDeviceProduct) {
		this.isDeviceProduct = isDeviceProduct;
	}
	/**
	 * @return the deliveryClassification
	 */
	public String getDeliveryClassification() {
		return deliveryClassification;
	}
	/**
	 * @param deliveryClassification the deliveryClassification to set
	 */
	public void setDeliveryClassification(String deliveryClassification) {
		this.deliveryClassification = deliveryClassification;
	}
	/**
	 * @return the deliveryPartner
	 */
	public String getDeliveryPartner() {
		return deliveryPartner;
	}
	/**
	 * @param deliveryPartner the deliveryPartner to set
	 */
	public void setDeliveryPartner(String deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}
	/**
	 * @return the deliveryMethod
	 */
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	/**
	 * @param deliveryMethod the deliveryMethod to set
	 */
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	/**
	 * @return the isBattery
	 */
	public String getIsBattery() {
		return isBattery;
	}
	/**
	 * @param isBattery the isBattery to set
	 */
	public void setIsBattery(String isBattery) {
		this.isBattery = isBattery;
	}
	/**
	 * @return the deliveryOnWeekend
	 */
	public String getDeliveryOnWeekend() {
		return deliveryOnWeekend;
	}
	/**
	 * @param deliveryOnWeekend the deliveryOnWeekend to set
	 */
	public void setDeliveryOnWeekend(String deliveryOnWeekend) {
		this.deliveryOnWeekend = deliveryOnWeekend;
	}
	/**
	 * @return the availableFrom
	 */
	public String getAvailableFrom() {
		return availableFrom;
	}
	/**
	 * @param availableFrom the availableFrom to set
	 */
	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}
	/**
	 * @return the estDeliveryDate
	 */
	public String getEstDeliveryDate() {
		return estDeliveryDate;
	}
	/**
	 * @param estDeliveryDate the estDeliveryDate to set
	 */
	public void setEstDeliveryDate(String estDeliveryDate) {
		this.estDeliveryDate = estDeliveryDate;
	}
	/**
	 * @return the isDisplayableEcare
	 */
	public String getIsDisplayableEcare() {
		return isDisplayableEcare;
	}
	/**
	 * @param isDisplayableEcare the isDisplayableEcare to set
	 */
	public void setIsDisplayableEcare(String isDisplayableEcare) {
		this.isDisplayableEcare = isDisplayableEcare;
	}
	/**
	 * @return the isSellableAcq
	 */
	public String getIsSellableAcq() {
		return isSellableAcq;
	}
	/**
	 * @param isSellableAcq the isSellableAcq to set
	 */
	public void setIsSellableAcq(String isSellableAcq) {
		this.isSellableAcq = isSellableAcq;
	}
	/**
	 * @return the productSubClass
	 */
	public String getProductSubClass() {
		return productSubClass;
	}
	/**
	 * @param productSubClass the productSubClass to set
	 */
	public void setProductSubClass(String productSubClass) {
		this.productSubClass = productSubClass;
	}
	/**
	 * @return the weekEnd
	 */
	public String getWeekEnd() {
		return weekEnd;
	}
	/**
	 * @param weekEnd the weekEnd to set
	 */
	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the allowanceUOM
	 */
	public String getAllowanceUOM() {
		return allowanceUOM;
	}
	/**
	 * @param allowanceUOM the allowanceUOM to set
	 */
	public void setAllowanceUOM(String allowanceUOM) {
		this.allowanceUOM = allowanceUOM;
	}
	/**
	 * @return the tilUOM
	 */
	public String getTilUOM() {
		return tilUOM;
	}
	/**
	 * @param tilUOM the tilUOM to set
	 */
	public void setTilUOM(String tilUOM) {
		this.tilUOM = tilUOM;
	}
	/**
	 * @return the displayUOM
	 */
	public String getDisplayUOM() {
		return displayUOM;
	}
	/**
	 * @param displayUOM the displayUOM to set
	 */
	public void setDisplayUOM(String displayUOM) {
		this.displayUOM = displayUOM;
	}
	/**
	 * @return the conversionFactor
	 */
	public String getConversionFactor() {
		return conversionFactor;
	}
	/**
	 * @param conversionFactor the conversionFactor to set
	 */
	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	/**
	 * @return the preDesc
	 */
	public String getPreDesc() {
		return preDesc;
	}
	/**
	 * @param preDesc the preDesc to set
	 */
	public void setPreDesc(String preDesc) {
		this.preDesc = preDesc;
	}
	/**
	 * @return the postDesc
	 */
	public String getPostDesc() {
		return postDesc;
	}
	/**
	 * @param postDesc the postDesc to set
	 */
	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	/**
	 * @return the preDescMobile
	 */
	public String getPreDescMobile() {
		return preDescMobile;
	}
	/**
	 * @param preDescMobile the preDescMobile to set
	 */
	public void setPreDescMobile(String preDescMobile) {
		this.preDescMobile = preDescMobile;
	}
	/**
	 * @return the listOfCompatibleBundles
	 */
	public List<String> getListOfCompatibleBundles() {
		if(listOfCompatibleBundles!=null) {
			Set<String> set=new HashSet<>(listOfCompatibleBundles);
			listOfCompatibleBundles=new ArrayList<>(set);
		}
		return listOfCompatibleBundles;
	}
	/**
	 * @param listOfCompatibleBundles the listOfCompatibleBundles to set
	 */
	public void setListOfCompatibleBundles(List<String> listOfCompatibleBundles) {
		this.listOfCompatibleBundles = listOfCompatibleBundles;
	}
	/**
	 * @return the leadPlanId
	 */
	public String getLeadPlanId() {
		return leadPlanId;
	}
	/**
	 * @param leadPlanId the leadPlanId to set
	 */
	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}
	/**
	 * @return the dataUOM
	 */
	public String getDataUOM() {
		return dataUOM;
	}
	/**
	 * @param dataUOM the dataUOM to set
	 */
	public void setDataUOM(String dataUOM) {
		this.dataUOM = dataUOM;
	}
	/**
	 * @return the dataValue
	 */
	public String getDataValue() {
		return dataValue;
	}
	/**
	 * @param dataValue the dataValue to set
	 */
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	/**
	 * @return the ukTextsUOM
	 */
	public String getUkTextsUOM() {
		return ukTextsUOM;
	}
	/**
	 * @param ukTextsUOM the ukTextsUOM to set
	 */
	public void setUkTextsUOM(String ukTextsUOM) {
		this.ukTextsUOM = ukTextsUOM;
	}
	/**
	 * @return the ukTextsValue
	 */
	public String getUkTextsValue() {
		return ukTextsValue;
	}
	/**
	 * @param ukTextsValue the ukTextsValue to set
	 */
	public void setUkTextsValue(String ukTextsValue) {
		this.ukTextsValue = ukTextsValue;
	}
	/**
	 * @return the ukMinutesUOM
	 */
	public String getUkMinutesUOM() {
		return ukMinutesUOM;
	}
	/**
	 * @param ukMinutesUOM the ukMinutesUOM to set
	 */
	public void setUkMinutesUOM(String ukMinutesUOM) {
		this.ukMinutesUOM = ukMinutesUOM;
	}
	/**
	 * @return the ukMinutesValue
	 */
	public String getUkMinutesValue() {
		return ukMinutesValue;
	}
	/**
	 * @param ukMinutesValue the ukMinutesValue to set
	 */
	public void setUkMinutesValue(String ukMinutesValue) {
		this.ukMinutesValue = ukMinutesValue;
	}
	/**
	 * @return the ukDataUOM
	 */
	public String getUkDataUOM() {
		return ukDataUOM;
	}
	/**
	 * @param ukDataUOM the ukDataUOM to set
	 */
	public void setUkDataUOM(String ukDataUOM) {
		this.ukDataUOM = ukDataUOM;
	}
	/**
	 * @return the ukDataValue
	 */
	public String getUkDataValue() {
		return ukDataValue;
	}
	/**
	 * @param ukDataValue the ukDataValue to set
	 */
	public void setUkDataValue(String ukDataValue) {
		this.ukDataValue = ukDataValue;
	}
	/**
	 * @return the wifiUOM
	 */
	public String getWifiUOM() {
		return wifiUOM;
	}
	/**
	 * @param wifiUOM the wifiUOM to set
	 */
	public void setWifiUOM(String wifiUOM) {
		this.wifiUOM = wifiUOM;
	}
	/**
	 * @return the wifiValue
	 */
	public String getWifiValue() {
		return wifiValue;
	}
	/**
	 * @param wifiValue the wifiValue to set
	 */
	public void setWifiValue(String wifiValue) {
		this.wifiValue = wifiValue;
	}
	/**
	 * @return the europeanRoamingDataUOM
	 */
	public String getEuropeanRoamingDataUOM() {
		return europeanRoamingDataUOM;
	}
	/**
	 * @param europeanRoamingDataUOM the europeanRoamingDataUOM to set
	 */
	public void setEuropeanRoamingDataUOM(String europeanRoamingDataUOM) {
		this.europeanRoamingDataUOM = europeanRoamingDataUOM;
	}
	/**
	 * @return the europeanRoamingDataValue
	 */
	public String getEuropeanRoamingDataValue() {
		return europeanRoamingDataValue;
	}
	/**
	 * @param europeanRoamingDataValue the europeanRoamingDataValue to set
	 */
	public void setEuropeanRoamingDataValue(String europeanRoamingDataValue) {
		this.europeanRoamingDataValue = europeanRoamingDataValue;
	}
	/**
	 * @return the europeanRoamingTextsUOM
	 */
	public String getEuropeanRoamingTextsUOM() {
		return europeanRoamingTextsUOM;
	}
	/**
	 * @param europeanRoamingTextsUOM the europeanRoamingTextsUOM to set
	 */
	public void setEuropeanRoamingTextsUOM(String europeanRoamingTextsUOM) {
		this.europeanRoamingTextsUOM = europeanRoamingTextsUOM;
	}
	/**
	 * @return the europeanRoamingTextsValue
	 */
	public String getEuropeanRoamingTextsValue() {
		return europeanRoamingTextsValue;
	}
	/**
	 * @param europeanRoamingTextsValue the europeanRoamingTextsValue to set
	 */
	public void setEuropeanRoamingTextsValue(String europeanRoamingTextsValue) {
		this.europeanRoamingTextsValue = europeanRoamingTextsValue;
	}
	/**
	 * @return the europeanRoamingMinutesUOM
	 */
	public String getEuropeanRoamingMinutesUOM() {
		return europeanRoamingMinutesUOM;
	}
	/**
	 * @param europeanRoamingMinutesUOM the europeanRoamingMinutesUOM to set
	 */
	public void setEuropeanRoamingMinutesUOM(String europeanRoamingMinutesUOM) {
		this.europeanRoamingMinutesUOM = europeanRoamingMinutesUOM;
	}
	/**
	 * @return the europeanRoamingMinutesValue
	 */
	public String getEuropeanRoamingMinutesValue() {
		return europeanRoamingMinutesValue;
	}
	/**
	 * @param europeanRoamingMinutesValue the europeanRoamingMinutesValue to set
	 */
	public void setEuropeanRoamingMinutesValue(String europeanRoamingMinutesValue) {
		this.europeanRoamingMinutesValue = europeanRoamingMinutesValue;
	}
	/**
	 * @return the europeanRoamingPictureMessagesUOM
	 */
	public String getEuropeanRoamingPictureMessagesUOM() {
		return europeanRoamingPictureMessagesUOM;
	}
	/**
	 * @param europeanRoamingPictureMessagesUOM the europeanRoamingPictureMessagesUOM to set
	 */
	public void setEuropeanRoamingPictureMessagesUOM(String europeanRoamingPictureMessagesUOM) {
		this.europeanRoamingPictureMessagesUOM = europeanRoamingPictureMessagesUOM;
	}
	/**
	 * @return the europeanRoamingPictureMessagesValue
	 */
	public String getEuropeanRoamingPictureMessagesValue() {
		return europeanRoamingPictureMessagesValue;
	}
	/**
	 * @param europeanRoamingPictureMessagesValue the europeanRoamingPictureMessagesValue to set
	 */
	public void setEuropeanRoamingPictureMessagesValue(String europeanRoamingPictureMessagesValue) {
		this.europeanRoamingPictureMessagesValue = europeanRoamingPictureMessagesValue;
	}
	/**
	 * @return the internationalTextsUOM
	 */
	public String getInternationalTextsUOM() {
		return internationalTextsUOM;
	}
	/**
	 * @param internationalTextsUOM the internationalTextsUOM to set
	 */
	public void setInternationalTextsUOM(String internationalTextsUOM) {
		this.internationalTextsUOM = internationalTextsUOM;
	}
	/**
	 * @return the internationalTextsValue
	 */
	public String getInternationalTextsValue() {
		return internationalTextsValue;
	}
	/**
	 * @param internationalTextsValue the internationalTextsValue to set
	 */
	public void setInternationalTextsValue(String internationalTextsValue) {
		this.internationalTextsValue = internationalTextsValue;
	}
	/**
	 * @return the etdataUOM
	 */
	public String getEtdataUOM() {
		return etdataUOM;
	}
	/**
	 * @param etdataUOM the etdataUOM to set
	 */
	public void setEtdataUOM(String etdataUOM) {
		this.etdataUOM = etdataUOM;
	}
	/**
	 * @return the etdataValue
	 */
	public String getEtdataValue() {
		return etdataValue;
	}
	/**
	 * @param etdataValue the etdataValue to set
	 */
	public void setEtdataValue(String etdataValue) {
		this.etdataValue = etdataValue;
	}
	/**
	 * @return the globalRoamingDataUOM
	 */
	public String getGlobalRoamingDataUOM() {
		return globalRoamingDataUOM;
	}
	/**
	 * @param globalRoamingDataUOM the globalRoamingDataUOM to set
	 */
	public void setGlobalRoamingDataUOM(String globalRoamingDataUOM) {
		this.globalRoamingDataUOM = globalRoamingDataUOM;
	}
	/**
	 * @return the globalRoamingDataValue
	 */
	public String getGlobalRoamingDataValue() {
		return globalRoamingDataValue;
	}
	/**
	 * @param globalRoamingDataValue the globalRoamingDataValue to set
	 */
	public void setGlobalRoamingDataValue(String globalRoamingDataValue) {
		this.globalRoamingDataValue = globalRoamingDataValue;
	}
	/**
	 * @return the globalRoamingTextsUOM
	 */
	public String getGlobalRoamingTextsUOM() {
		return globalRoamingTextsUOM;
	}
	/**
	 * @param globalRoamingTextsUOM the globalRoamingTextsUOM to set
	 */
	public void setGlobalRoamingTextsUOM(String globalRoamingTextsUOM) {
		this.globalRoamingTextsUOM = globalRoamingTextsUOM;
	}
	/**
	 * @return the globalRoamingTextsValue
	 */
	public String getGlobalRoamingTextsValue() {
		return globalRoamingTextsValue;
	}
	/**
	 * @param globalRoamingTextsValue the globalRoamingTextsValue to set
	 */
	public void setGlobalRoamingTextsValue(String globalRoamingTextsValue) {
		this.globalRoamingTextsValue = globalRoamingTextsValue;
	}
	/**
	 * @return the globalRoamingMinutesUOM
	 */
	public String getGlobalRoamingMinutesUOM() {
		return globalRoamingMinutesUOM;
	}
	/**
	 * @param globalRoamingMinutesUOM the globalRoamingMinutesUOM to set
	 */
	public void setGlobalRoamingMinutesUOM(String globalRoamingMinutesUOM) {
		this.globalRoamingMinutesUOM = globalRoamingMinutesUOM;
	}
	/**
	 * @return the globalRoamingMinutesValue
	 */
	public String getGlobalRoamingMinutesValue() {
		return globalRoamingMinutesValue;
	}
	/**
	 * @param globalRoamingMinutesValue the globalRoamingMinutesValue to set
	 */
	public void setGlobalRoamingMinutesValue(String globalRoamingMinutesValue) {
		this.globalRoamingMinutesValue = globalRoamingMinutesValue;
	}
	/**
	 * @return the roamingDataUOM
	 */
	public String getRoamingDataUOM() {
		return roamingDataUOM;
	}
	/**
	 * @param roamingDataUOM the roamingDataUOM to set
	 */
	public void setRoamingDataUOM(String roamingDataUOM) {
		this.roamingDataUOM = roamingDataUOM;
	}
	/**
	 * @return the roamingDataValue
	 */
	public String getRoamingDataValue() {
		return roamingDataValue;
	}
	/**
	 * @param roamingDataValue the roamingDataValue to set
	 */
	public void setRoamingDataValue(String roamingDataValue) {
		this.roamingDataValue = roamingDataValue;
	}
	/**
	 * @return the uKmobileMinutesUOM
	 */
	public String getuKmobileMinutesUOM() {
		return uKmobileMinutesUOM;
	}
	/**
	 * @param uKmobileMinutesUOM the uKmobileMinutesUOM to set
	 */
	public void setuKmobileMinutesUOM(String uKmobileMinutesUOM) {
		this.uKmobileMinutesUOM = uKmobileMinutesUOM;
	}
	/**
	 * @return the uKmobileMinutesValue
	 */
	public String getuKmobileMinutesValue() {
		return uKmobileMinutesValue;
	}
	/**
	 * @param uKmobileMinutesValue the uKmobileMinutesValue to set
	 */
	public void setuKmobileMinutesValue(String uKmobileMinutesValue) {
		this.uKmobileMinutesValue = uKmobileMinutesValue;
	}
	/**
	 * @return the vodafoneVodafonetextsUOM
	 */
	public String getVodafoneVodafonetextsUOM() {
		return vodafoneVodafonetextsUOM;
	}
	/**
	 * @param vodafoneVodafonetextsUOM the vodafoneVodafonetextsUOM to set
	 */
	public void setVodafoneVodafonetextsUOM(String vodafoneVodafonetextsUOM) {
		this.vodafoneVodafonetextsUOM = vodafoneVodafonetextsUOM;
	}
	/**
	 * @return the vodafoneVodafonetextsValue
	 */
	public String getVodafoneVodafonetextsValue() {
		return vodafoneVodafonetextsValue;
	}
	/**
	 * @param vodafoneVodafonetextsValue the vodafoneVodafonetextsValue to set
	 */
	public void setVodafoneVodafonetextsValue(String vodafoneVodafonetextsValue) {
		this.vodafoneVodafonetextsValue = vodafoneVodafonetextsValue;
	}
	/**
	 * @return the pictureMessagesUOM
	 */
	public String getPictureMessagesUOM() {
		return pictureMessagesUOM;
	}
	/**
	 * @param pictureMessagesUOM the pictureMessagesUOM to set
	 */
	public void setPictureMessagesUOM(String pictureMessagesUOM) {
		this.pictureMessagesUOM = pictureMessagesUOM;
	}
	/**
	 * @return the pictureMessagesValue
	 */
	public String getPictureMessagesValue() {
		return pictureMessagesValue;
	}
	/**
	 * @param pictureMessagesValue the pictureMessagesValue to set
	 */
	public void setPictureMessagesValue(String pictureMessagesValue) {
		this.pictureMessagesValue = pictureMessagesValue;
	}
	/**
	 * @return the uKANDInternationalCreditUOM
	 */
	public String getuKANDInternationalCreditUOM() {
		return uKANDInternationalCreditUOM;
	}
	/**
	 * @param uKANDInternationalCreditUOM the uKANDInternationalCreditUOM to set
	 */
	public void setuKANDInternationalCreditUOM(String uKANDInternationalCreditUOM) {
		this.uKANDInternationalCreditUOM = uKANDInternationalCreditUOM;
	}
	/**
	 * @return the uKANDInternationalCreditValue
	 */
	public String getuKANDInternationalCreditValue() {
		return uKANDInternationalCreditValue;
	}
	/**
	 * @param uKANDInternationalCreditValue the uKANDInternationalCreditValue to set
	 */
	public void setuKANDInternationalCreditValue(String uKANDInternationalCreditValue) {
		this.uKANDInternationalCreditValue = uKANDInternationalCreditValue;
	}
	/**
	 * @return the internationalMinutesUOM
	 */
	public String getInternationalMinutesUOM() {
		return internationalMinutesUOM;
	}
	/**
	 * @param internationalMinutesUOM the internationalMinutesUOM to set
	 */
	public void setInternationalMinutesUOM(String internationalMinutesUOM) {
		this.internationalMinutesUOM = internationalMinutesUOM;
	}
	/**
	 * @return the internationalMinutesValue
	 */
	public String getInternationalMinutesValue() {
		return internationalMinutesValue;
	}
	/**
	 * @param internationalMinutesValue the internationalMinutesValue to set
	 */
	public void setInternationalMinutesValue(String internationalMinutesValue) {
		this.internationalMinutesValue = internationalMinutesValue;
	}
	/**
	 * @return the vodafoneVodafoneUOM
	 */
	public String getVodafoneVodafoneUOM() {
		return vodafoneVodafoneUOM;
	}
	/**
	 * @param vodafoneVodafoneUOM the vodafoneVodafoneUOM to set
	 */
	public void setVodafoneVodafoneUOM(String vodafoneVodafoneUOM) {
		this.vodafoneVodafoneUOM = vodafoneVodafoneUOM;
	}
	/**
	 * @return the vodafoneVodafoneValue
	 */
	public String getVodafoneVodafoneValue() {
		return vodafoneVodafoneValue;
	}
	/**
	 * @param vodafoneVodafoneValue the vodafoneVodafoneValue to set
	 */
	public void setVodafoneVodafoneValue(String vodafoneVodafoneValue) {
		this.vodafoneVodafoneValue = vodafoneVodafoneValue;
	}
	/**
	 * @return the vodafoneVodafoneMinutesUOM
	 */
	public String getVodafoneVodafoneMinutesUOM() {
		return vodafoneVodafoneMinutesUOM;
	}
	/**
	 * @param vodafoneVodafoneMinutesUOM the vodafoneVodafoneMinutesUOM to set
	 */
	public void setVodafoneVodafoneMinutesUOM(String vodafoneVodafoneMinutesUOM) {
		this.vodafoneVodafoneMinutesUOM = vodafoneVodafoneMinutesUOM;
	}
	/**
	 * @return the vodafoneVodafoneMinutesValue
	 */
	public String getVodafoneVodafoneMinutesValue() {
		return vodafoneVodafoneMinutesValue;
	}
	/**
	 * @param vodafoneVodafoneMinutesValue the vodafoneVodafoneMinutesValue to set
	 */
	public void setVodafoneVodafoneMinutesValue(String vodafoneVodafoneMinutesValue) {
		this.vodafoneVodafoneMinutesValue = vodafoneVodafoneMinutesValue;
	}
	/**
	 * @return the landlineMinutesUOM
	 */
	public String getLandlineMinutesUOM() {
		return landlineMinutesUOM;
	}
	/**
	 * @param landlineMinutesUOM the landlineMinutesUOM to set
	 */
	public void setLandlineMinutesUOM(String landlineMinutesUOM) {
		this.landlineMinutesUOM = landlineMinutesUOM;
	}
	/**
	 * @return the landlineMinutesValue
	 */
	public String getLandlineMinutesValue() {
		return landlineMinutesValue;
	}
	/**
	 * @param landlineMinutesValue the landlineMinutesValue to set
	 */
	public void setLandlineMinutesValue(String landlineMinutesValue) {
		this.landlineMinutesValue = landlineMinutesValue;
	}
	/**
	 * @return the zereightnumberminutesUOM
	 */
	public String getZereightnumberminutesUOM() {
		return zereightnumberminutesUOM;
	}
	/**
	 * @param zereightnumberminutesUOM the zereightnumberminutesUOM to set
	 */
	public void setZereightnumberminutesUOM(String zereightnumberminutesUOM) {
		this.zereightnumberminutesUOM = zereightnumberminutesUOM;
	}
	/**
	 * @return the zereightnumberminutesValue
	 */
	public String getZereightnumberminutesValue() {
		return zereightnumberminutesValue;
	}
	/**
	 * @param zereightnumberminutesValue the zereightnumberminutesValue to set
	 */
	public void setZereightnumberminutesValue(String zereightnumberminutesValue) {
		this.zereightnumberminutesValue = zereightnumberminutesValue;
	}
	/**
	 * @return the extraimeuandrowUOM
	 */
	public String getExtraimeuandrowUOM() {
		return extraimeuandrowUOM;
	}
	/**
	 * @param extraimeuandrowUOM the extraimeuandrowUOM to set
	 */
	public void setExtraimeuandrowUOM(String extraimeuandrowUOM) {
		this.extraimeuandrowUOM = extraimeuandrowUOM;
	}
	/**
	 * @return the extraimeuandrowValue
	 */
	public String getExtraimeuandrowValue() {
		return extraimeuandrowValue;
	}
	/**
	 * @param extraimeuandrowValue the extraimeuandrowValue to set
	 */
	public void setExtraimeuandrowValue(String extraimeuandrowValue) {
		this.extraimeuandrowValue = extraimeuandrowValue;
	}
	/**
	 * @return the extraimeuandusUOM
	 */
	public String getExtraimeuandusUOM() {
		return extraimeuandusUOM;
	}
	/**
	 * @param extraimeuandusUOM the extraimeuandusUOM to set
	 */
	public void setExtraimeuandusUOM(String extraimeuandusUOM) {
		this.extraimeuandusUOM = extraimeuandusUOM;
	}
	/**
	 * @return the extraimeuandusValue
	 */
	public String getExtraimeuandusValue() {
		return extraimeuandusValue;
	}
	/**
	 * @param extraimeuandusValue the extraimeuandusValue to set
	 */
	public void setExtraimeuandusValue(String extraimeuandusValue) {
		this.extraimeuandusValue = extraimeuandusValue;
	}
	/**
	 * @return the leadPlanIdNew
	 */
	public String getLeadPlanIdNew() {
		return leadPlanIdNew;
	}
	/**
	 * @param leadPlanIdNew the leadPlanIdNew to set
	 */
	public void setLeadPlanIdNew(String leadPlanIdNew) {
		this.leadPlanIdNew = leadPlanIdNew;
	}
	/**
	 * @return the productGroupName
	 */
	public String getProductGroupName() {
		return productGroupName;
	}
	/**
	 * @param productGroupName the productGroupName to set
	 */
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	/**
	 * @return the productGroupId
	 */
	public String getProductGroupId() {
		return productGroupId;
	}
	/**
	 * @param productGroupId the productGroupId to set
	 */
	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}
	/**
	 * @return the oneOffGrossPrice
	 */
	public Float getOneOffGrossPrice() {
		return oneOffGrossPrice;
	}
	/**
	 * @param oneOffGrossPrice the oneOffGrossPrice to set
	 */
	public void setOneOffGrossPrice(Float oneOffGrossPrice) {
		this.oneOffGrossPrice = oneOffGrossPrice;
	}
	/**
	 * @return the oneOffNetPrice
	 */
	public Float getOneOffNetPrice() {
		return oneOffNetPrice;
	}
	/**
	 * @param oneOffNetPrice the oneOffNetPrice to set
	 */
	public void setOneOffNetPrice(Float oneOffNetPrice) {
		this.oneOffNetPrice = oneOffNetPrice;
	}
	/**
	 * @return the oneOffVatPrice
	 */
	public Float getOneOffVatPrice() {
		return oneOffVatPrice;
	}
	/**
	 * @param oneOffVatPrice the oneOffVatPrice to set
	 */
	public void setOneOffVatPrice(Float oneOffVatPrice) {
		this.oneOffVatPrice = oneOffVatPrice;
	}
	/**
	 * @return the oneOffDiscountedGrossPrice
	 */
	public Float getOneOffDiscountedGrossPrice() {
		return oneOffDiscountedGrossPrice;
	}
	/**
	 * @param oneOffDiscountedGrossPrice the oneOffDiscountedGrossPrice to set
	 */
	public void setOneOffDiscountedGrossPrice(Float oneOffDiscountedGrossPrice) {
		this.oneOffDiscountedGrossPrice = oneOffDiscountedGrossPrice;
	}
	/**
	 * @return the oneOffDiscountedNetPrice
	 */
	public Float getOneOffDiscountedNetPrice() {
		return oneOffDiscountedNetPrice;
	}
	/**
	 * @param oneOffDiscountedNetPrice the oneOffDiscountedNetPrice to set
	 */
	public void setOneOffDiscountedNetPrice(Float oneOffDiscountedNetPrice) {
		this.oneOffDiscountedNetPrice = oneOffDiscountedNetPrice;
	}
	/**
	 * @return the oneOffDiscountedVatPrice
	 */
	public Float getOneOffDiscountedVatPrice() {
		return oneOffDiscountedVatPrice;
	}
	/**
	 * @param oneOffDiscountedVatPrice the oneOffDiscountedVatPrice to set
	 */
	public void setOneOffDiscountedVatPrice(Float oneOffDiscountedVatPrice) {
		this.oneOffDiscountedVatPrice = oneOffDiscountedVatPrice;
	}
	public List<String> getPromoteAs() {
		return promoteAs;
	}
	public void setPromoteAs(List<String> promoteAs) {
		this.promoteAs = promoteAs;
	}
	/**
	 * @return the bundleMonthlyPriceGross
	 */
	public Float getBundleMonthlyPriceGross() {
		return bundleMonthlyPriceGross;
	}
	/**
	 * @param bundleMonthlyPriceGross the bundleMonthlyPriceGross to set
	 */
	public void setBundleMonthlyPriceGross(Float bundleMonthlyPriceGross) {
		this.bundleMonthlyPriceGross = bundleMonthlyPriceGross;
	}
	/**
	 * @return the bundleMonthlyPriceNet
	 */
	public Float getBundleMonthlyPriceNet() {
		return bundleMonthlyPriceNet;
	}
	/**
	 * @param bundleMonthlyPriceNet the bundleMonthlyPriceNet to set
	 */
	public void setBundleMonthlyPriceNet(Float bundleMonthlyPriceNet) {
		this.bundleMonthlyPriceNet = bundleMonthlyPriceNet;
	}
	/**
	 * @return the bundleMonthlyPriceVat
	 */
	public Float getBundleMonthlyPriceVat() {
		return bundleMonthlyPriceVat;
	}
	/**
	 * @param bundleMonthlyPriceVat the bundleMonthlyPriceVat to set
	 */
	public void setBundleMonthlyPriceVat(Float bundleMonthlyPriceVat) {
		this.bundleMonthlyPriceVat = bundleMonthlyPriceVat;
	}
	/**
	 * @return the bundleMonthlyDiscPriceGross
	 */
	public Float getBundleMonthlyDiscPriceGross() {
		return bundleMonthlyDiscPriceGross;
	}
	/**
	 * @param bundleMonthlyDiscPriceGross the bundleMonthlyDiscPriceGross to set
	 */
	public void setBundleMonthlyDiscPriceGross(Float bundleMonthlyDiscPriceGross) {
		this.bundleMonthlyDiscPriceGross = bundleMonthlyDiscPriceGross;
	}
	/**
	 * @return the bundleMonthlyDiscPriceNet
	 */
	public Float getBundleMonthlyDiscPriceNet() {
		return bundleMonthlyDiscPriceNet;
	}
	/**
	 * @param bundleMonthlyDiscPriceNet the bundleMonthlyDiscPriceNet to set
	 */
	public void setBundleMonthlyDiscPriceNet(Float bundleMonthlyDiscPriceNet) {
		this.bundleMonthlyDiscPriceNet = bundleMonthlyDiscPriceNet;
	}
	/**
	 * @return the bundleMonthlyDiscPriceVat
	 */
	public Float getBundleMonthlyDiscPriceVat() {
		return bundleMonthlyDiscPriceVat;
	}
	/**
	 * @param bundleMonthlyDiscPriceVat the bundleMonthlyDiscPriceVat to set
	 */
	public void setBundleMonthlyDiscPriceVat(Float bundleMonthlyDiscPriceVat) {
		this.bundleMonthlyDiscPriceVat = bundleMonthlyDiscPriceVat;
	}
	/**
	 * @return the productGroups
	 */
	public List<String> getProductGroups() {
		return productGroups;
	}
	/**
	 * @param productGroups the productGroups to set
	 */
	public void setProductGroups(List<String> productGroups) {
		this.productGroups = productGroups;
	}
	/**
	 * @return the merchandisingMedia
	 */
	public List<String> getMerchandisingMedia() {
		return merchandisingMedia;
	}
	/**
	 * @param merchandisingMedia the merchandisingMedia to set
	 */
	public void setMerchandisingMedia(List<String> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
	}
	/**
	 * @return the startDateFormatted
	 */
	public Date getStartDateFormatted() {
		return startDateFormatted;
	}
	/**
	 * @param startDateFormatted the startDateFormatted to set
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
	 * @param endDateFormatted the endDateFormatted to set
	 */
	public void setEndDateFormatted(Date endDateFormatted) {
		this.endDateFormatted = endDateFormatted;
	}
	/**
	 * @return the upgradeLeadPlanId
	 */
	public String getUpgradeLeadPlanId() {
		return upgradeLeadPlanId;
	}
	/**
	 * @param upgradeLeadPlanId the upgradeLeadPlanId to set
	 */
	public void setUpgradeLeadPlanId(String upgradeLeadPlanId) {
		this.upgradeLeadPlanId = upgradeLeadPlanId;
	}
	/**
	 * @return the nonUpgradeLeadPlanId
	 */
	public String getNonUpgradeLeadPlanId() {
		return nonUpgradeLeadPlanId;
	}
	/**
	 * @param nonUpgradeLeadPlanId the nonUpgradeLeadPlanId to set
	 */
	public void setNonUpgradeLeadPlanId(String nonUpgradeLeadPlanId) {
		this.nonUpgradeLeadPlanId = nonUpgradeLeadPlanId;
	}
	/**
	 * @return the miscKeyValue
	 */
	public List<String> getMiscKeyValue() {
		return miscKeyValue;
	}
	/**
	 * @param miscKeyValue the miscKeyValue to set
	 */
	public void setMiscKeyValue(List<String> miscKeyValue) {
		this.miscKeyValue = miscKeyValue;
	}
	/**
	 * @return the imageURLsThumbsMarketing1
	 */
	public String getImageURLsThumbsMarketing1() {
		return imageURLsThumbsMarketing1;
	}
	/**
	 * @param imageURLsThumbsMarketing1 the imageURLsThumbsMarketing1 to set
	 */
	public void setImageURLsThumbsMarketing1(String imageURLsThumbsMarketing1) {
		this.imageURLsThumbsMarketing1 = imageURLsThumbsMarketing1;
	}
	/**
	 * @return the imageURLsThumbsMarketing2
	 */
	public String getImageURLsThumbsMarketing2() {
		return imageURLsThumbsMarketing2;
	}
	/**
	 * @param imageURLsThumbsMarketing2 the imageURLsThumbsMarketing2 to set
	 */
	public void setImageURLsThumbsMarketing2(String imageURLsThumbsMarketing2) {
		this.imageURLsThumbsMarketing2 = imageURLsThumbsMarketing2;
	}
	/**
	 * @return the imageURLsThumbsMarketing3
	 */
	public String getImageURLsThumbsMarketing3() {
		return imageURLsThumbsMarketing3;
	}
	/**
	 * @param imageURLsThumbsMarketing3 the imageURLsThumbsMarketing3 to set
	 */
	public void setImageURLsThumbsMarketing3(String imageURLsThumbsMarketing3) {
		this.imageURLsThumbsMarketing3 = imageURLsThumbsMarketing3;
	}
	/**
	 * @return the imageURLsThumbsFrontAngle
	 */
	public String getImageURLsThumbsFrontAngle() {
		return imageURLsThumbsFrontAngle;
	}
	/**
	 * @param imageURLsThumbsFrontAngle the imageURLsThumbsFrontAngle to set
	 */
	public void setImageURLsThumbsFrontAngle(String imageURLsThumbsFrontAngle) {
		this.imageURLsThumbsFrontAngle = imageURLsThumbsFrontAngle;
	}
	/**
	 * @return the imageURLsThumbsBackAngle
	 */
	public String getImageURLsThumbsBackAngle() {
		return imageURLsThumbsBackAngle;
	}
	/**
	 * @param imageURLsThumbsBackAngle the imageURLsThumbsBackAngle to set
	 */
	public void setImageURLsThumbsBackAngle(String imageURLsThumbsBackAngle) {
		this.imageURLsThumbsBackAngle = imageURLsThumbsBackAngle;
	}
	/**
	 * @return the imageURLsFullMarketing1
	 */
	public String getImageURLsFullMarketing1() {
		return imageURLsFullMarketing1;
	}
	/**
	 * @param imageURLsFullMarketing1 the imageURLsFullMarketing1 to set
	 */
	public void setImageURLsFullMarketing1(String imageURLsFullMarketing1) {
		this.imageURLsFullMarketing1 = imageURLsFullMarketing1;
	}
	/**
	 * @return the imageURLsFullMarketing2
	 */
	public String getImageURLsFullMarketing2() {
		return imageURLsFullMarketing2;
	}
	/**
	 * @param imageURLsFullMarketing2 the imageURLsFullMarketing2 to set
	 */
	public void setImageURLsFullMarketing2(String imageURLsFullMarketing2) {
		this.imageURLsFullMarketing2 = imageURLsFullMarketing2;
	}
	/**
	 * @return the imageURLsFullMarketing3
	 */
	public String getImageURLsFullMarketing3() {
		return imageURLsFullMarketing3;
	}
	/**
	 * @param imageURLsFullMarketing3 the imageURLsFullMarketing3 to set
	 */
	public void setImageURLsFullMarketing3(String imageURLsFullMarketing3) {
		this.imageURLsFullMarketing3 = imageURLsFullMarketing3;
	}
	/**
	 * @return the imageURLsFullFrontAngle
	 */
	public String getImageURLsFullFrontAngle() {
		return imageURLsFullFrontAngle;
	}
	/**
	 * @param imageURLsFullFrontAngle the imageURLsFullFrontAngle to set
	 */
	public void setImageURLsFullFrontAngle(String imageURLsFullFrontAngle) {
		this.imageURLsFullFrontAngle = imageURLsFullFrontAngle;
	}
	/**
	 * @return the imageURLsFullBackAngle
	 */
	public String getImageURLsFullBackAngle() {
		return imageURLsFullBackAngle;
	}
	/**
	 * @param imageURLsFullBackAngle the imageURLsFullBackAngle to set
	 */
	public void setImageURLsFullBackAngle(String imageURLsFullBackAngle) {
		this.imageURLsFullBackAngle = imageURLsFullBackAngle;
	}
	/**
	 * @return the imageURLsThumbsBack
	 */
	public String getImageURLsThumbsBack() {
		return imageURLsThumbsBack;
	}
	/**
	 * @param imageURLsThumbsBack the imageURLsThumbsBack to set
	 */
	public void setImageURLsThumbsBack(String imageURLsThumbsBack) {
		this.imageURLsThumbsBack = imageURLsThumbsBack;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
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
	 * @param modifiedDate the modifiedDate to set
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
	 * @param seoRobots the seoRobots to set
	 */
	public void setSeoRobots(List<String> seoRobots) {
		this.seoRobots = seoRobots;
	}
	
	/**
	 * @return the maxQuantity
	 */
	public String getMaxQuantity() {
		return maxQuantity;
	}
	/**
	 * @param maxQuantity the maxQuantity to set
	 */
	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	/**
	 * @return the ageRestricted
	 */
	public String getAgeRestricted() {
		return ageRestricted;
	}
	/**
	 * @param ageRestricted the ageRestricted to set
	 */
	public void setAgeRestricted(String ageRestricted) {
		this.ageRestricted = ageRestricted;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", productId=" + productId + ", productName=" + productName
				+ ", parentProductId=" + parentProductId + ", order=" + order + ", productClass=" + productClass
				+ ", isServicesProduct=" + isServicesProduct + ", duration=" + duration + ", durationUOM=" + durationUOM
				+ ", productStartDate=" + productStartDate + ", productEndDate=" + productEndDate + ", salesExpired="
				+ salesExpired + ", discountType=" + discountType + ", discountAmount=" + discountAmount + ", warranty="
				+ warranty + ", condition=" + condition + ", priceType=" + priceType + ", productType=" + productType
				+ ", isDisplayableAcq=" + isDisplayableAcq + ", isSellableECare=" + isSellableECare + ", isSellableRet="
				+ isSellableRet + ", isDisplayableRet=" + isDisplayableRet + ", isDisplaybaleSavedBasket="
				+ isDisplaybaleSavedBasket + ", controlOrder=" + controlOrder + ", preOrderable=" + preOrderable
				+ ", backOrderable=" + backOrderable + ", affiliateExport=" + affiliateExport + ", compareWith="
				+ compareWith + ", pdGrpvariant=" + pdGrpvariant + ", pdGrpCmpAcc=" + pdGrpCmpAcc + ", pdGrpRecAcc="
				+ pdGrpRecAcc + ", pdGrpRecExtr=" + pdGrpRecExtr + ", compatibleEntertainment="
				+ compatibleEntertainment + ", priceNetOVR=" + priceNetOVR + ", priceGrossOVR=" + priceGrossOVR
				+ ", priceVatOVR=" + priceVatOVR + ", seoCanonical=" + seoCanonical + ", seoDescription="
				+ seoDescription + ", seoKeywords=" + seoKeywords + ", seoIndex=" + seoIndex + ", eligibiltiySubflow="
				+ eligibiltiySubflow + ", equipmentMake=" + equipmentMake + ", equipmentModel=" + equipmentModel
				+ ", displayName=" + displayName + ", postDescMobile=" + postDescMobile + ", imageURLsThumbsFront="
				+ imageURLsThumbsFront + ", imageURLsThumbsLeft=" + imageURLsThumbsLeft + ", imageURLsThumbsRight="
				+ imageURLsThumbsRight + ", imageURLsThumbsSide=" + imageURLsThumbsSide + ", imageURLsFullHero="
				+ imageURLsFullHero + ", imageURLsFullFront=" + imageURLsFullFront + ", imageURLsFullLeft="
				+ imageURLsFullLeft + ", imageURLsFullRight=" + imageURLsFullRight + ", imageURLsFullSide="
				+ imageURLsFullSide + ", imageURLsFullBack=" + imageURLsFullBack + ", imageURLsGrid=" + imageURLsGrid
				+ ", imageURLsSmall=" + imageURLsSmall + ", imageURLsSticker=" + imageURLsSticker + ", imageURLsIcon="
				+ imageURLsIcon + ", video=" + video + ", threeDSpin=" + threeDSpin + ", support=" + support
				+ ", helpurl=" + helpurl + ", helptext=" + helptext + ", inTheBox=" + inTheBox + ", rating=" + rating
				+ ", numReviews=" + numReviews + ", mefProductName=" + mefProductName + ", isDeviceProduct="
				+ isDeviceProduct + ", deliveryClassification=" + deliveryClassification + ", deliveryPartner="
				+ deliveryPartner + ", deliveryMethod=" + deliveryMethod + ", isBattery=" + isBattery
				+ ", deliveryOnWeekend=" + deliveryOnWeekend + ", availableFrom=" + availableFrom + ", estDeliveryDate="
				+ estDeliveryDate + ", isDisplayableEcare=" + isDisplayableEcare + ", isSellableAcq=" + isSellableAcq
				+ ", productSubClass=" + productSubClass + ", weekEnd=" + weekEnd + ", type=" + type + ", amount="
				+ amount + ", allowanceUOM=" + allowanceUOM + ", tilUOM=" + tilUOM + ", displayUOM=" + displayUOM
				+ ", conversionFactor=" + conversionFactor + ", preDesc=" + preDesc + ", postDesc=" + postDesc
				+ ", preDescMobile=" + preDescMobile + ", listOfCompatibleBundles=" + listOfCompatibleBundles
				+ ", leadPlanId=" + leadPlanId + ", dataUOM=" + dataUOM + ", dataValue=" + dataValue + ", ukTextsUOM="
				+ ukTextsUOM + ", ukTextsValue=" + ukTextsValue + ", ukMinutesUOM=" + ukMinutesUOM + ", ukMinutesValue="
				+ ukMinutesValue + ", ukDataUOM=" + ukDataUOM + ", ukDataValue=" + ukDataValue + ", wifiUOM=" + wifiUOM
				+ ", wifiValue=" + wifiValue + ", europeanRoamingDataUOM=" + europeanRoamingDataUOM
				+ ", europeanRoamingDataValue=" + europeanRoamingDataValue + ", europeanRoamingTextsUOM="
				+ europeanRoamingTextsUOM + ", europeanRoamingTextsValue=" + europeanRoamingTextsValue
				+ ", europeanRoamingMinutesUOM=" + europeanRoamingMinutesUOM + ", europeanRoamingMinutesValue="
				+ europeanRoamingMinutesValue + ", europeanRoamingPictureMessagesUOM="
				+ europeanRoamingPictureMessagesUOM + ", europeanRoamingPictureMessagesValue="
				+ europeanRoamingPictureMessagesValue + ", internationalTextsUOM=" + internationalTextsUOM
				+ ", internationalTextsValue=" + internationalTextsValue + ", etdataUOM=" + etdataUOM + ", etdataValue="
				+ etdataValue + ", globalRoamingDataUOM=" + globalRoamingDataUOM + ", globalRoamingDataValue="
				+ globalRoamingDataValue + ", globalRoamingTextsUOM=" + globalRoamingTextsUOM
				+ ", globalRoamingTextsValue=" + globalRoamingTextsValue + ", globalRoamingMinutesUOM="
				+ globalRoamingMinutesUOM + ", globalRoamingMinutesValue=" + globalRoamingMinutesValue
				+ ", roamingDataUOM=" + roamingDataUOM + ", roamingDataValue=" + roamingDataValue
				+ ", uKmobileMinutesUOM=" + uKmobileMinutesUOM + ", uKmobileMinutesValue=" + uKmobileMinutesValue
				+ ", vodafoneVodafonetextsUOM=" + vodafoneVodafonetextsUOM + ", vodafoneVodafonetextsValue="
				+ vodafoneVodafonetextsValue + ", pictureMessagesUOM=" + pictureMessagesUOM + ", pictureMessagesValue="
				+ pictureMessagesValue + ", uKANDInternationalCreditUOM=" + uKANDInternationalCreditUOM
				+ ", uKANDInternationalCreditValue=" + uKANDInternationalCreditValue + ", internationalMinutesUOM="
				+ internationalMinutesUOM + ", internationalMinutesValue=" + internationalMinutesValue
				+ ", vodafoneVodafoneUOM=" + vodafoneVodafoneUOM + ", vodafoneVodafoneValue=" + vodafoneVodafoneValue
				+ ", vodafoneVodafoneMinutesUOM=" + vodafoneVodafoneMinutesUOM + ", vodafoneVodafoneMinutesValue="
				+ vodafoneVodafoneMinutesValue + ", landlineMinutesUOM=" + landlineMinutesUOM
				+ ", landlineMinutesValue=" + landlineMinutesValue + ", zereightnumberminutesUOM="
				+ zereightnumberminutesUOM + ", zereightnumberminutesValue=" + zereightnumberminutesValue
				+ ", extraimeuandrowUOM=" + extraimeuandrowUOM + ", extraimeuandrowValue=" + extraimeuandrowValue
				+ ", extraimeuandusUOM=" + extraimeuandusUOM + ", extraimeuandusValue=" + extraimeuandusValue
				+ ", leadPlanIdNew=" + leadPlanIdNew + ", productGroupName=" + productGroupName + ", productGroupId="
				+ productGroupId + ", oneOffGrossPrice=" + oneOffGrossPrice + ", oneOffNetPrice=" + oneOffNetPrice
				+ ", oneOffVatPrice=" + oneOffVatPrice + ", oneOffDiscountedGrossPrice=" + oneOffDiscountedGrossPrice
				+ ", oneOffDiscountedNetPrice=" + oneOffDiscountedNetPrice + ", oneOffDiscountedVatPrice="
				+ oneOffDiscountedVatPrice + ", promoteAs=" + promoteAs + ", bundleMonthlyPriceGross="
				+ bundleMonthlyPriceGross + ", bundleMonthlyPriceNet=" + bundleMonthlyPriceNet
				+ ", bundleMonthlyPriceVat=" + bundleMonthlyPriceVat + ", bundleMonthlyDiscPriceGross="
				+ bundleMonthlyDiscPriceGross + ", bundleMonthlyDiscPriceNet=" + bundleMonthlyDiscPriceNet
				+ ", bundleMonthlyDiscPriceVat=" + bundleMonthlyDiscPriceVat + ", productGroups=" + productGroups
				+ ", merchandisingMedia=" + merchandisingMedia + ", startDateFormatted=" + startDateFormatted
				+ ", endDateFormatted=" + endDateFormatted + ", upgradeLeadPlanId=" + upgradeLeadPlanId
				+ ", nonUpgradeLeadPlanId=" + nonUpgradeLeadPlanId + ", miscKeyValue=" + miscKeyValue
				+ ", imageURLsThumbsMarketing1=" + imageURLsThumbsMarketing1 + ", imageURLsThumbsMarketing2="
				+ imageURLsThumbsMarketing2 + ", imageURLsThumbsMarketing3=" + imageURLsThumbsMarketing3
				+ ", imageURLsThumbsFrontAngle=" + imageURLsThumbsFrontAngle + ", imageURLsThumbsBackAngle="
				+ imageURLsThumbsBackAngle + ", imageURLsFullMarketing1=" + imageURLsFullMarketing1
				+ ", imageURLsFullMarketing2=" + imageURLsFullMarketing2 + ", imageURLsFullMarketing3="
				+ imageURLsFullMarketing3 + ", imageURLsFullFrontAngle=" + imageURLsFullFrontAngle
				+ ", imageURLsFullBackAngle=" + imageURLsFullBackAngle + ", imageURLsThumbsBack=" + imageURLsThumbsBack
				+ ", createDate=" + createDate + ", modifiedDate=" + modifiedDate + ", seoRobots=" + seoRobots
				+ ", maxQuantity=" + maxQuantity + ", ageRestricted=" + ageRestricted + "]";
	}
	
	
}
