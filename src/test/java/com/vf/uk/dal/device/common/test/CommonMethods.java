package com.vf.uk.dal.device.common.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.device.client.entity.bundle.Allowance;
import com.vf.uk.dal.device.client.entity.bundle.Availability;
import com.vf.uk.dal.device.client.entity.bundle.BundleControl;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.bundle.BundleHeader;
import com.vf.uk.dal.device.client.entity.bundle.BundleModel;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.bundle.Commitment;
import com.vf.uk.dal.device.client.entity.bundle.DevicePrice;
import com.vf.uk.dal.device.client.entity.bundle.ImageURL;
import com.vf.uk.dal.device.client.entity.bundle.LineRental;
import com.vf.uk.dal.device.client.entity.bundle.ServiceProduct;
import com.vf.uk.dal.device.client.entity.customer.InstalledProduct;
import com.vf.uk.dal.device.client.entity.customer.Preferences;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListRequest;
import com.vf.uk.dal.device.client.entity.customer.SourcePackageSummary;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundleDeviceAndProductsList;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.HardwarePrice;
import com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.client.entity.price.StepPricingInfo;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.model.Accessory;
import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.Colour;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.Discount;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.MerchandisingControl;
import com.vf.uk.dal.device.model.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.model.MerchandisingPromotionsWrapper;
import com.vf.uk.dal.device.model.MetaData;
import com.vf.uk.dal.device.model.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.model.Specification;
import com.vf.uk.dal.device.model.SpecificationGroup;
import com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.BazaarVoice;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.DeviceFinancingOption;
import com.vf.uk.dal.device.model.product.Duration;
import com.vf.uk.dal.device.model.product.Equipment;
import com.vf.uk.dal.device.model.product.MediaURL;
import com.vf.uk.dal.device.model.product.PriceDetail;
import com.vf.uk.dal.device.model.product.ProductAvailability;
import com.vf.uk.dal.device.model.product.ProductControl;
import com.vf.uk.dal.device.model.product.ProductGroups;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.product.PromoteAs;
import com.vf.uk.dal.device.model.productgroups.Count;
import com.vf.uk.dal.device.model.productgroups.FacetField;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.Member;
import com.vf.uk.dal.device.model.productgroups.ProductGroupFacetModel;
import com.vf.uk.dal.device.model.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.model.solr.DevicePreCalculatedData;
import com.vf.uk.dal.device.model.solr.Media;
import com.vf.uk.dal.device.model.solr.OfferAppliedPriceDetails;
import com.vf.uk.dal.device.model.solr.OneOffDiscountPrice;
import com.vf.uk.dal.device.model.solr.OneOffPrice;
import com.vf.uk.dal.device.model.solr.PriceInfo;

public class CommonMethods {
	public static Timestamp timeStamp;
	public static final String STRING_ACCESSORY = "Accessory,Compatible Accessories";
	public static final String CONDITIONAL_FULL_DISCOUNT = "conditional_full_discount";
	public static final String PREFERENCE_NAME_HANDSET = "HANDSET";
	public static final String PREFERENCE_DATATYPE_CODE_PREFERENCE = "PREFERENCE";
	public static final String PREFERENCE_NAME_RECOMMIT = "RECOMMIT";
	public static final String ACCOUNT_CATEGORY_INDIVIDUAL = "Individual";
	public static final String STRING_TARIFF = "TARIFF";
	public static final String PREFERENCE_DATATYPE_ELIGIBILITY_CRITERIA = "ELIGIBILITY_CRITERIA";
	public static final String PREFERENCE_NAME_SEGMENT = "SEGMENT";
	public static final String PREFERENCE_NAME_UPGRADE = "UPGRADE_TYPE";
	public static final String PREFERENCE_DATATYPE_CODE_GENERAL = "GENERAL";
	
	public static byte[] readFile(String filename) throws IOException
	{
        String fileData = null;
        Resource resource = new ClassPathResource(filename);
        InputStream is = resource.getInputStream();
        byte[] fileDataBytes = new byte[is.available()];
        is.read(fileDataBytes);
        fileData = new String(fileDataBytes);
        return fileData.getBytes();
	}
	
	public static DeviceDetails getDevice(String id) {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails.setDeviceId("93353");
		deviceDetails.setName("Apple Iphone 6s");
		deviceDetails.setDescription("one 10 GB red Bundle");
		deviceDetails.setProductClass("handset");
		List<String> productLines = new ArrayList<String>();
		productLines.add("Handset");
		productLines.add("Device");
		productLines.add("Accesory");
		deviceDetails.setProductLines(productLines);
		List<SpecificationGroup> specificationGroupList = new ArrayList<SpecificationGroup>();
		SpecificationGroup specificationGroup = new SpecificationGroup();
		specificationGroup.setComparable(true);
		specificationGroup.setGroupName("DEVICE");
		specificationGroup.setPriority(1);

		List<Specification> specifications = new ArrayList<Specification>();
		Specification specification = new Specification();
		specifications.add(specification);
		specificationGroup.setSpecifications(specifications);
		deviceDetails.setSpecificationsGroups(specificationGroupList);

		MetaData metaData = null;
		metaData = new MetaData();
		metaData.setSeoCanonical("canonical");
		metaData.setSeoDescription("Description");
		metaData.setSeoIndex("Index");
		metaData.setSeoKeyWords("keywords");

		deviceDetails.setMetaData(metaData);

		return deviceDetails;
	}

	public static DeviceDetails getDevice_One(String id) {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails.setDeviceId("093353");
		deviceDetails.setName("Apple Iphone 6s");
		deviceDetails.setDescription("one 10 GB red Bundle");
		deviceDetails.setProductClass("handset");
		List<String> productLines = new ArrayList<String>();
		productLines.add("Handset");
		productLines.add("Device");
		productLines.add("Accesory");
		deviceDetails.setProductLines(productLines);
		List<SpecificationGroup> specificationGroupList = new ArrayList<SpecificationGroup>();
		SpecificationGroup specificationGroup = new SpecificationGroup();
		specificationGroup.setComparable(true);
		specificationGroup.setGroupName("DEVICE");
		specificationGroup.setPriority(1);

		List<Specification> specifications = new ArrayList<Specification>();
		Specification specification = new Specification();
		specifications.add(specification);
		specificationGroup.setSpecifications(specifications);
		deviceDetails.setSpecificationsGroups(specificationGroupList);

		MetaData metaData = null;
		metaData = new MetaData();
		metaData.setSeoCanonical("canonical");
		metaData.setSeoDescription("Description");
		metaData.setSeoIndex("Index");
		metaData.setSeoKeyWords("keywords");

		deviceDetails.setMetaData(metaData);

		return deviceDetails;
	}

	public static List<BundleModel> getBundleModelListForBundleList() {

		List<BundleModel> bundleModelList = new ArrayList<BundleModel>();

		BundleModel bundleModel = new BundleModel();
		bundleModel.setBundleId("109373");
		bundleModel.setName("RedBundle");
		bundleModel.setDesc("This Bundle is valid for 11 months");
		bundleModel.setPaymentType("postPay");
		bundleModel.setRecurringCharge((float) 2.5);
		bundleModel.setUkMinutesUOM("months");
		bundleModel.setUkMinutesValue("10");
		bundleModel.setUkDataUOM("months");
		bundleModel.setUkDataValue("2");
		bundleModel.setUkTextsUOM("Months");
		bundleModel.setUkTextsValue("500");
		bundleModel.setEuropeanRoamingDataUOM("Months");
		bundleModel.setEuropeanRoamingDataValue("12");
		bundleModel.setEuropeanRoamingMinutesUOM("Months");
		bundleModel.setEuropeanRoamingMinutesValue("10");
		bundleModel.setEuropeanRoamingTextsUOM("Months");
		bundleModel.setEuropeanRoamingTextsValue("9");
		bundleModel.setEuropeanRoamingPictureMessagesUOM("Months");
		bundleModel.setEuropeanRoamingPictureMessagesValue("9");
		bundleModel.setInternationalTextsUOM("Months");
		bundleModel.setInternationalTextsValue("10");
		List<String> promoteAs = new ArrayList<>();
		promoteAs.add("CTRL110165TO110154");
		bundleModel.setPromoteAs(promoteAs);
		bundleModelList.add(bundleModel);
		return bundleModelList;

	}

	public static List<BundleModel> getBundleModelListForBundleListForConditional() {

		List<BundleModel> bundleModelList = new ArrayList<BundleModel>();

		BundleModel bundleModel = new BundleModel();
		bundleModel.setBundleId("110151");
		bundleModel.setName("RedBundle");
		bundleModel.setDesc("This Bundle is valid for 11 months");
		bundleModel.setPaymentType("postPay");
		bundleModel.setRecurringCharge((float) 2.5);
		bundleModel.setUkMinutesUOM("months");
		bundleModel.setUkMinutesValue("10");
		bundleModel.setUkDataUOM("months");
		bundleModel.setUkDataValue("2");
		bundleModel.setUkTextsUOM("Months");
		bundleModel.setUkTextsValue("500");
		bundleModel.setEuropeanRoamingDataUOM("Months");
		bundleModel.setEuropeanRoamingDataValue("12");
		bundleModel.setEuropeanRoamingMinutesUOM("Months");
		bundleModel.setEuropeanRoamingMinutesValue("10");
		bundleModel.setEuropeanRoamingTextsUOM("Months");
		bundleModel.setEuropeanRoamingTextsValue("9");
		bundleModel.setEuropeanRoamingPictureMessagesUOM("Months");
		bundleModel.setEuropeanRoamingPictureMessagesValue("9");
		bundleModel.setInternationalTextsUOM("Months");
		bundleModel.setInternationalTextsValue("10");
		List<String> promoteAs = new ArrayList<>();
		promoteAs.add("CTRL110165TO110154");
		bundleModel.setPromoteAs(promoteAs);
		bundleModelList.add(bundleModel);
		return bundleModelList;

	}


	public static List<ProductModel> getProductModel() {
		List<ProductModel> productModelList = new ArrayList<>();
		List<String> list = new ArrayList<>();
		String productgroup = "CompatibleDeliveryMethods_CnC_PremiumDelivery@Compatible Delivery";
		String productgroup1 = "CompatibleDeliveryMethods_CnC_Delivery@Compatible Delivery";
		list.add(productgroup);
		list.add(productgroup1);
		ProductModel model = new ProductModel();
		model.setProductGroups(list);
		model.setProductId("093353");
		model.setPreDesc("preDesc");
		model.setProductClass("HANDSET");
		model.setPreOrderable("true");
		model.setIsDisplayableEcare("true");
		model.setIsSellableECare("true");
		model.setIsDisplayableAcq("true");
		model.setIsSellableRet("true");
		model.setIsDisplayableRet("true");
		model.setIsSellableAcq("true");
		model.setIsDisplaybaleSavedBasket("true");
		model.setOrder(12);
		model.setPreOrderable("true");
		model.setAvailableFrom("Available From");
		model.setBackOrderable("true");
		model.setImageURLsThumbsFront("ThumbsFront");
		model.setImageURLsThumbsLeft("Thumbs Left");
		model.setImageURLsThumbsRight("ThumnsLeft");
		model.setImageURLsThumbsSide("ThumbsSide");
		model.setImageURLsFullLeft("Full Left");
		model.setImageURLsFullRight("Full Right");
		model.setImageURLsFullSide("Full Side");
		model.setImageURLsFullBack("Full Back");
		model.setImageURLsGrid("ImageURLs Grid");
		model.setImageURLsSmall("URLs Small");
		model.setImageURLsSticker("URLs Sticker");
		model.setImageURLsIcon("URLsIcon");
		model.setThreeDSpin("ThreeDSpin");
		model.setSupport("Support");
		model.setOneOffDiscountedGrossPrice(54.0f);
		model.setOneOffDiscountedNetPrice(46.0f);
		model.setOneOffDiscountedVatPrice(9.0f);
		model.setOneOffGrossPrice(54.0f);
		model.setOneOffNetPrice(46.0f);
		model.setOneOffVatPrice(9.0f);
		model.setBundleMonthlyDiscPriceGross(54.0f);
		model.setBundleMonthlyDiscPriceNet(46.0f);
		model.setBundleMonthlyDiscPriceVat(9.0f);
		model.setBundleMonthlyPriceGross(54.0f);
		model.setBundleMonthlyPriceNet(46.0f);
		model.setBundleMonthlyPriceVat(9.0f);
		model.setLeadPlanId("110154");
		model.setLeadPlanIdNew("110154");
		model.setUpgradeLeadPlanId("110154");
		model.setNonUpgradeLeadPlanId("110154");
		model.setFinancingOptions(getDeviceFinaceOptions());
		List<String> merchedn = new ArrayList<>();
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");

		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");

		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");

		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");

		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");

		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add(
				"hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");

		model.setMerchandisingMedia(merchedn);
		// List<String>

		ProductModel model1 = new ProductModel();
		model1.setProductGroups(list);
		model1.setProductId("092660");
		model1.setPreDesc("preDesc");
		model1.setProductClass("HANDSET");
		model1.setPreOrderable("true");
		model1.setIsDisplayableEcare("true");
		model1.setIsSellableECare("true");
		model1.setIsDisplayableAcq("true");
		model1.setIsSellableRet("true");
		model1.setIsDisplayableRet("true");
		model1.setIsSellableAcq("true");
		model1.setIsDisplaybaleSavedBasket("true");
		model1.setOrder(12);
		model1.setPreOrderable("true");
		model1.setAvailableFrom("Available From");
		model1.setBackOrderable("true");
		model1.setImageURLsThumbsFront("ThumbsFront");
		model1.setImageURLsThumbsLeft("Thumbs Left");
		model1.setImageURLsThumbsRight("ThumnsLeft");
		model1.setImageURLsThumbsSide("ThumbsSide");
		model1.setImageURLsFullLeft("Full Left");
		model1.setImageURLsFullRight("Full Right");
		model1.setImageURLsFullSide("Full Side");
		model1.setImageURLsFullBack("Full Back");
		model1.setImageURLsGrid("ImageURLs Grid");
		model1.setImageURLsSmall("URLs Small");
		model1.setImageURLsSticker("URLs Sticker");
		model1.setImageURLsIcon("URLsIcon");
		model1.setThreeDSpin("ThreeDSpin");
		model1.setSupport("Support");
		model1.setOneOffDiscountedGrossPrice(54.0f);
		model1.setOneOffDiscountedNetPrice(46.0f);
		model1.setOneOffDiscountedVatPrice(9.0f);
		model1.setOneOffGrossPrice(55.0f);
		model1.setOneOffNetPrice(46.0f);
		model1.setOneOffVatPrice(9.0f);
		model1.setBundleMonthlyDiscPriceGross(53.0f);
		model1.setBundleMonthlyDiscPriceNet(44.0f);
		model1.setBundleMonthlyDiscPriceVat(9.0f);
		model1.setBundleMonthlyPriceGross(54.0f);
		model1.setBundleMonthlyPriceNet(46.0f);
		model1.setBundleMonthlyPriceVat(9.0f);
		model1.setLeadPlanId("110154");
		model1.setLeadPlanIdNew("110154");
		model1.setUpgradeLeadPlanId("110154");
		model1.setNonUpgradeLeadPlanId("110154");
		model1.setMerchandisingMedia(merchedn);
		model1.setFinancingOptions(getDeviceFinaceOptions());
		productModelList.add(model);
		productModelList.add(model1);
		return productModelList;
	}

	public static List<DevicePreCalculatedData> getDevicePreCal() {
		List<DevicePreCalculatedData> deviceList = new ArrayList<>();
		DevicePreCalculatedData device = new DevicePreCalculatedData();
		device.setDeviceId("093353");
		device.setLeadPlanId("092660");
		device.setPaygProductGroupId("084253");
		device.setProductGroupName("GROUP_PAYM");
		device.setIsLeadMember("Y");
		device.setMinimumCost("5.20");
		List<Media> listmedia = new ArrayList<>();
		Media media = new Media();
		media.setId("imagesURLs.full.right");
		media.setValue("www.vodafone.co.uk/cs/groups/public/documents/images/imageurls.full.right.png");
		media.setType("URL");
		listmedia.add(media);
		device.setMedia(listmedia);
		PriceInfo price = new PriceInfo();
		com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice = new com.vf.uk.dal.device.model.solr.BundlePrice();
		bundlePrice.setBundleId("110154");
		com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice monthly = new com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice();
		monthly.setGross("12.0");
		monthly.setNet("18");
		monthly.setVat("11.5");
		bundlePrice.setMonthlyDiscountPrice(monthly);
		com.vf.uk.dal.device.model.solr.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.model.solr.MonthlyPrice();
		monthlyPrice.setGross("10.3");
		monthlyPrice.setNet("12.4");
		monthlyPrice.setVat("11");
		bundlePrice.setMonthlyPrice(monthlyPrice);
		price.setBundlePrice(bundlePrice);

		com.vf.uk.dal.device.model.solr.HardwarePrice hardware = new com.vf.uk.dal.device.model.solr.HardwarePrice();
		hardware.setHardwareId("092660");
		com.vf.uk.dal.device.model.solr.OneOffDiscountPrice oneOff = new OneOffDiscountPrice();
		oneOff.setGross("10");
		oneOff.setNet("11.3");
		oneOff.setVat("12.5");
		hardware.setOneOffDiscountPrice(oneOff);
		com.vf.uk.dal.device.model.solr.OneOffPrice oneOffPrice = new OneOffPrice();
		oneOffPrice.setGross("9");
		oneOffPrice.setNet("12.8");
		oneOffPrice.setVat("16.6");
		hardware.setOneOffPrice(oneOffPrice);
		price.setHardwarePrice(hardware);

		device.setPriceInfo(price);

		DevicePreCalculatedData device1 = new DevicePreCalculatedData();
		device1.setDeviceId("095597");
		device1.setLeadPlanId("092660");
		device1.setPaymProductGroupId("084253");
		device1.setProductGroupName("GROUP_PAYM");
		device1.setIsLeadMember("Y");
		device1.setMinimumCost("5.20");
		List<Media> listmedia1 = new ArrayList<>();
		Media media1 = new Media();
		media1.setId("imagesURLs.full.right");
		media1.setValue("www.vodafone.co.uk/cs/groups/public/documents/images/imageurls.full.right.png");
		media1.setType("URL");
		listmedia1.add(media);
		device1.setMedia(listmedia);
		PriceInfo price1 = new PriceInfo();
		com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice1 = new com.vf.uk.dal.device.model.solr.BundlePrice();
		bundlePrice1.setBundleId("110154");
		com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice monthly1 = new com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice();
		monthly1.setGross("12.0");
		monthly1.setNet("18");
		monthly1.setVat("11.5");
		bundlePrice.setMonthlyDiscountPrice(monthly1);
		com.vf.uk.dal.device.model.solr.MonthlyPrice monthlyPrice1 = new com.vf.uk.dal.device.model.solr.MonthlyPrice();
		monthlyPrice1.setGross("10.3");
		monthlyPrice1.setNet("12.4");
		monthlyPrice1.setVat("11");
		bundlePrice.setMonthlyPrice(monthlyPrice1);
		price1.setBundlePrice(bundlePrice1);

		com.vf.uk.dal.device.model.solr.HardwarePrice hardware1 = new com.vf.uk.dal.device.model.solr.HardwarePrice();
		hardware1.setHardwareId("095597");
		com.vf.uk.dal.device.model.solr.OneOffDiscountPrice oneOff1 = new OneOffDiscountPrice();
		oneOff1.setGross("10");
		oneOff1.setNet("11.3");
		oneOff1.setVat("12.5");
		hardware.setOneOffDiscountPrice(oneOff1);
		com.vf.uk.dal.device.model.solr.OneOffPrice oneOffPrice1 = new OneOffPrice();
		oneOffPrice1.setGross("9");
		oneOffPrice1.setNet("12.8");
		oneOffPrice1.setVat("16.6");
		hardware1.setOneOffPrice(oneOffPrice1);
		price1.setHardwarePrice(hardware1);

		device1.setPriceInfo(price1);
		deviceList.add(device);
		deviceList.add(device1);
		return deviceList;
	}

	public static List<Device> getDeviceListForFaceting(String productClass, String make, String model,
			String groupType, String sortCriteria, int pageNumber, int pageSize, String JourneyId) {
		List<Device> deviceLists = new ArrayList<>();

		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		merchandisingMedia.add(mediaLink);

		MerchandisingControl merchandisingControl = new MerchandisingControl();
		merchandisingControl.setAvailableFrom("Available From");
		merchandisingControl.setBackorderable(true);
		merchandisingControl.setIsDisplayableAcq(true);
		merchandisingControl.setIsDisplayableECare(false);
		merchandisingControl.setIsDisplayableRet(true);
		merchandisingControl.setIsDisplayableSavedBasket(true);
		merchandisingControl.setIsSellableAcq(true);
		merchandisingControl.setIsSellableECare(false);
		merchandisingControl.setIsSellableRet(true);
		merchandisingControl.setOrder(2);
		merchandisingControl.setPreorderable(true);

		Device deviceList = new Device();
		deviceList.setDeviceId("093353");
		deviceList.setMake("Apple");
		deviceList.setModel("iphone7");
		deviceList.setGroupType("DEVICE_PAYM");
		deviceList.setRating("4");
		deviceList.setDescription("Description");
		deviceList.setProductClass("HANDSET");
		deviceList.setMerchandisingControl(merchandisingControl);
		deviceList.setMedia(merchandisingMedia);
		deviceList.setPriceInfo(getPriceForBundleAndHardware().get(0));

		Device deviceList1 = new Device();
		deviceList.setDeviceId("092660");
		deviceList.setMake("Apple");
		deviceList.setModel("iphone7");
		deviceList.setGroupType("DEVICE_PAYM");
		deviceList.setRating("4");
		deviceList.setDescription("Description");
		deviceList.setProductClass("HANDSET");
		deviceList.setMerchandisingControl(merchandisingControl);
		deviceList.setMedia(merchandisingMedia);
		deviceList.setPriceInfo(getPriceForBundleAndHardware().get(0));

		Device deviceList2 = new Device();
		deviceList.setDeviceId("094127");
		deviceList.setMake("Apple");
		deviceList.setModel("iphone7");
		deviceList.setGroupType("DEVICE_PAYM");
		deviceList.setRating("4");
		deviceList.setDescription("Description");
		deviceList.setProductClass("HANDSET");
		deviceList.setMerchandisingControl(merchandisingControl);
		deviceList.setMedia(merchandisingMedia);
		deviceList.setPriceInfo(getPriceForBundleAndHardware().get(0));

		Device deviceList3 = new Device();
		deviceList.setDeviceId("01234");
		deviceList.setMake("Apple");
		deviceList.setModel("iphone7");
		deviceList.setGroupType("DEVICE_PAYM");
		deviceList.setRating("4");
		deviceList.setDescription("Description");
		deviceList.setProductClass("HANDSET");
		deviceList.setMerchandisingControl(merchandisingControl);
		deviceList.setMedia(merchandisingMedia);
		deviceList.setPriceInfo(getPriceForBundleAndHardware().get(0));
		deviceLists.add(deviceList);
		deviceLists.add(deviceList1);
		deviceLists.add(deviceList2);
		deviceLists.add(deviceList3);

		return deviceLists;
	}

	

	

	
	public static List<DeviceTile> getDeviceTile(String make, String model, String groupType) {

		List<DeviceTile> deviceTileList = null;
		deviceTileList = new ArrayList<DeviceTile>();

		DeviceTile deviceTile = null;
		deviceTile = new DeviceTile();

		List<DeviceSummary> deviceSummaryList = new ArrayList<DeviceSummary>();
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setColourHex("D10000000");
		deviceSummary.setColourName("Grey");
		deviceSummary.setDisplayDescription("5.5 inch");
		deviceSummary.setDisplayName("display name");
		deviceSummary.setDeviceId(String.valueOf(122));
		deviceSummary.setLeadPlanId("Lead plan Id");
		deviceSummary.setLeadPlanDisplayName("Yearly Plan");
		deviceSummary.setMemory("64GB");
		deviceSummary.setPreOrderable(false);
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		deviceSummary.setMerchandisingMedia(merchandisingMedia);

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();

		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("9.11");
		monthlyDiscountPrice.setNet("91.23");
		monthlyDiscountPrice.setVat("10.56");

		Price monthlyPrice = new Price();
		monthlyDiscountPrice.setGross("13.64");
		monthlyDiscountPrice.setNet("12.5");
		monthlyDiscountPrice.setVat("8.56");

		Price oneOffPrice = new Price();
		monthlyDiscountPrice.setGross("5.11");
		monthlyDiscountPrice.setNet("9.23");
		monthlyDiscountPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("Hardware Id");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);

		List<StepPricingInfo> stepPricesList = new ArrayList<StepPricingInfo>();
		StepPricingInfo stePrices = new StepPricingInfo();

		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		deviceSummary.setPriceInfo(priceInfo);
		deviceSummaryList.add(deviceSummary);
		deviceTile.setDeviceSummary(deviceSummaryList);
		deviceTile.setGroupName("Apple iPhone 6s");
		deviceTile.setGroupType("DEVICE");
		deviceTile.setDeviceId(String.valueOf(93353));
		deviceTileList.add(deviceTile);
		return deviceTileList;
	}

	public static CommercialProduct getCommercialProduct() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("093353");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setInvoiceChargeable(true);
		priceDetail.setPriceGross((double) 64);
		priceDetail.setPriceNet((double) 54);
		priceDetail.setPriceVAT((double) 24);
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setOrder((long) 12345);

		PromoteAs promoteAs = new PromoteAs();
		List<String> promotionName = new ArrayList<String>();
		promotionName.add("qwerty");
		promotionName.add("asdfg");
		promoteAs.setPromotionName(promotionName);
		commercialProduct.setPromoteAs(promoteAs);

		ProductControl productControl = new ProductControl();
		productControl.setDisplayableinLife(true);
		productControl.setSellableinLife(true);
		productControl.setDisplayableAcq(true);
		productControl.setSellableRet(true);
		productControl.setDisplayableRet(true);
		productControl.setSellableAcq(true);
		productControl.setDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp = new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(timeStamp);
		productControl.setBackOrderable(true);

		commercialProduct.setProductControl(productControl);
		Equipment equipment = new Equipment();
		equipment.setMake("SetMake");
		equipment.setModel("SetModel");
		commercialProduct.setEquipment(equipment);
		ProductAvailability productAvailability = new ProductAvailability();
		productAvailability.setEnd(null);
		productAvailability.setSalesExpired(false);
		productAvailability.setStart(null);
		commercialProduct.setProductAvailability(productAvailability);
		List<com.vf.uk.dal.device.model.product.Group> specificationGroupsList = new ArrayList<com.vf.uk.dal.device.model.product.Group>();
		com.vf.uk.dal.device.model.product.Group gr = new com.vf.uk.dal.device.model.product.Group();
		com.vf.uk.dal.device.model.product.Group group = new com.vf.uk.dal.device.model.product.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vf.uk.dal.device.model.product.Specification> specificationList = new ArrayList<com.vf.uk.dal.device.model.product.Specification>();
		com.vf.uk.dal.device.model.product.Specification specification = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification1 = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification2 = new com.vf.uk.dal.device.model.product.Specification();
		specification.setComparable(true);
		specification.setIsKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setIsKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setIsKey(true);
		specification2.setName("HexValue");
		specification2.setPriority((long) 1);
		specification2.setValue("#E5000");
		specification2.setValueType("");
		specification2.setValueUOM("");

		specificationList.add(specification1);
		specificationList.add(specification2);
		specificationList.add(specification);
		gr.setSpecifications(specificationList);

		specificationGroupsList.add(gr);
		specificationGroupsList.add(group);
		commercialProduct.setSpecificationGroups(specificationGroupsList);

		List<MediaURL> mediaUrlList = new ArrayList<MediaURL>();
		MediaURL mediaUrl = new MediaURL();
		mediaUrl.setMediaName("MEdiaName");
		mediaUrl.setMediaURL("URL");
		MediaURL mediaUrl1 = new MediaURL();
		mediaUrl.setMediaName("MEdiaName1");
		mediaUrl.setMediaURL("URL2");
		mediaUrlList.add(mediaUrl);
		mediaUrlList.add(mediaUrl1);
		commercialProduct.setListOfmediaURLs(mediaUrlList);

		List<com.vf.uk.dal.device.model.product.ImageURL> listOfimageURLs = new ArrayList<com.vf.uk.dal.device.model.product.ImageURL>();
		com.vf.uk.dal.device.model.product.ImageURL imageURL = new com.vf.uk.dal.device.model.product.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUom("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vf.uk.dal.device.model.product.Discount discount = new com.vf.uk.dal.device.model.product.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		List<String> listOfComplanIds = new ArrayList<>();
		listOfComplanIds.add("123");
		listOfComplanIds.add("456");
		listOfComplanIds.add("789");
		listOfComplanIds.add("101112");
		listOfComplanIds.add("131415");
		commercialProduct.setListOfCompatiblePlanIds(listOfComplanIds);

		return commercialProduct;
	}

	public static List<CommercialProduct> getListOfCommercialProduct() {
		List<CommercialProduct> listOfCommercialProduct = new ArrayList<>();
		listOfCommercialProduct.add(getCommercialProduct());
		return listOfCommercialProduct;

	}

	public static Member getMember() {

		Member member = new Member();
		member.setPriority((long) 2627);
		member.setId("1");
		return member;
	}

	public static List<Member> getMemberList() {
		List<com.vf.uk.dal.device.model.productgroups.Member> productGroupMember = new ArrayList<com.vf.uk.dal.device.model.productgroups.Member>();
		com.vf.uk.dal.device.model.productgroups.Member productMember = new Member();
		productMember.setId("1");
		productMember.setPriority((long) 7346);
		productGroupMember.add(productMember);
		return productGroupMember;
	}

	public static List<Group> getGroup() {
		List<Group> groupList = new ArrayList<>();
		Group group = new Group();
		group.setVersion("1.0");
		group.setGroupPriority((long) 3);
		group.setGroupType("DEVICE");
		Group group1 = new Group();
		group1.setVersion("1.0");
		group1.setGroupPriority((long) 3);
		group1.setGroupType(STRING_ACCESSORY);
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 2);
		Member member1 = new Member();
		member1.setId("124");
		member1.setPriority((long) 1);
		Member member2 = new Member();
		member2.setId("093329");
		member2.setPriority((long) 1);
		memberList.add(member2);
		memberList.add(member1);
		memberList.add(member);
		group.setMembers(memberList);
		group.setName("Apple iPhone 6s");
		group.setVersion("1.0");
		group1.setMembers(memberList);
		group1.setName("Apple iPhone 6s cover");
		group1.setVersion("1.0");
		groupList.add(group);
		groupList.add(group1);

		return groupList;
	}

	public static List<Group> getGroupForVariant() {
		List<Group> groupList = new ArrayList<>();
		Group group = new Group();
		group.setVersion("1.0");
		group.setGroupPriority((long) 3);
		group.setGroupType("DEVICE");
		Group group1 = new Group();
		group1.setVersion("1.0");
		group1.setGroupPriority((long) 3);
		group1.setGroupType(STRING_ACCESSORY);
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 2);
		Member member1 = new Member();
		member1.setId("124");
		member1.setPriority((long) 1);
		Member member2 = new Member();
		member2.setId("091191");
		member2.setPriority((long) 1);
		memberList.add(member2);
		memberList.add(member1);
		memberList.add(member);
		group.setEquipmentMake("apple");
		group.setEquipmentModel("iPhone-7");
		group.setMembers(memberList);
		group.setName("Apple iPhone 7");
		group.setVersion("1.0");
		group1.setMembers(memberList);
		group1.setName("Apple iPhone 7 cover");
		group1.setVersion("1.0");
		groupList.add(group);
		groupList.add(group1);

		return groupList;
	}

	public static List<Group> getGroup_Two() {
		List<Group> groupList = new ArrayList<>();
		Group group = new Group();
		group.setVersion("1.0");
		group.setGroupPriority((long) 3);
		group.setGroupType("DEVICE");
		Group group1 = new Group();
		group1.setVersion("1.0");
		group1.setGroupPriority((long) 3);
		group1.setGroupType(STRING_ACCESSORY);
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 2);
		Member member1 = new Member();
		member1.setId("124");
		member1.setPriority((long) 1);
		Member member2 = new Member();
		member2.setId("1245");
		member2.setPriority((long) 1);
		memberList.add(member2);
		memberList.add(member1);
		memberList.add(member);
		group.setMembers(memberList);
		group.setName("Apple iPhone 6s");
		group.setVersion("1.0");
		group1.setMembers(memberList);
		group1.setName("Apple iPhone 6s cover");
		group1.setVersion("1.0");
		groupList.add(group);
		groupList.add(group1);

		return groupList;
	}

	public static CommercialBundle getCommercialBundle() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setId("110154");
		bundle.setName("24mth BandO 500min 500MB Standard");
		bundle.setDesc("CTR12 Standard bundle comes with 500 mins, unlimited texts and 500MB of UK data");
		bundle.setPaymentType("postpaid");
		bundle.setDisplayName("Red Bundle");
		Commitment commitment = new Commitment();
		commitment.setPeriod("24 Months");
		bundle.setCommitment(commitment);
		Allowance allowances = new Allowance();
		Allowance roamingAllowances = new Allowance();
		List<Allowance> listOfAllowances = new ArrayList<Allowance>();
		allowances.setType("DATA");
		allowances.setUom("MB");
		allowances.setValue("500.00");
		listOfAllowances.add(allowances);

		List<ServiceProduct> listOfServiceProducts = new ArrayList<ServiceProduct>();
		ServiceProduct serviceProducts = new ServiceProduct();
		serviceProducts.setBundled(true);
		serviceProducts.setId("109154");
		listOfServiceProducts.add(serviceProducts);
		LineRental lineRentals = new LineRental();
		lineRentals.setLineRentalAmount((long) 200);
		lineRentals.setLineRentalProductId("122");
		List<LineRental> listOfLineRentals = new ArrayList<LineRental>();
		listOfLineRentals.add(lineRentals);
		serviceProducts.setLineRentals(listOfLineRentals);
		bundle.setServiceProducts(listOfServiceProducts);
		roamingAllowances.setType("UK Text");
		roamingAllowances.setUom("MONTHS");
		roamingAllowances.setValue("2000.00");
		listOfAllowances.add(roamingAllowances);
		bundle.setAllowances(listOfAllowances);
		List<String> listOfPromoteAs = new ArrayList<>();
		com.vf.uk.dal.device.client.entity.bundle.PromoteAs promoteAs = new com.vf.uk.dal.device.client.entity.bundle.PromoteAs();
		listOfPromoteAs.add("EXTRA.1GB.DATA");
		listOfPromoteAs.add("W_HH_PAYM_OC_01");
		promoteAs.setPromotionName(listOfPromoteAs);
		bundle.setPromoteAs(promoteAs);
		ImageURL imageURL = new ImageURL();
		imageURL.setImageName("Left_Thumb");
		imageURL.setImageURL("http://Url");
		List<ImageURL> listOfImageUrl = new ArrayList<ImageURL>();
		listOfImageUrl.add(imageURL);
		bundle.setListOfimageURLs(listOfImageUrl);

		MediaURL mediaURL = new MediaURL();
		mediaURL.setMediaName("Right_Front");
		mediaURL.setMediaURL("http://media");
		List<MediaURL> listOfMediaUrl = new ArrayList<MediaURL>();
		listOfMediaUrl.add(mediaURL);
		// bundle.setListOfmediaURLs(listOfMediaUrl);

		bundle.setRecurringCharge(21.3f);

		List<DevicePrice> listOfDevicePrice = new ArrayList<DevicePrice>();
		DevicePrice devicePrice = new DevicePrice();
		devicePrice.setDeviceId("93353");
		devicePrice.setPriceGross((float) 14.09);
		devicePrice.setPriceNet((float) 13.4);
		devicePrice.setPriceVAT((float) 12);
		devicePrice.setProductLine("Product Line");
		listOfDevicePrice.add(devicePrice);
		bundle.setDeviceSpecificPricing(listOfDevicePrice);
		List<String> productLines = new ArrayList<String>();
		productLines.add("Mobile Phone Service Sellable");
		productLines.add("asdfg");
		productLines.add("oiut");
		bundle.setProductLines(productLines);

		Availability availability = new Availability();
		availability.setEnd((Date.valueOf("2019-03-30")));
		availability.setSalesExpired(false);
		availability.setStart((Date.valueOf("2018-01-05")));
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundleControl.setDisplayableRet(true);
		bundleControl.setSellableRet(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}


	public static List<AccessoryTileGroup> getAccessoriesTileGroup(String deviceId) {
		List<AccessoryTileGroup> tileGroup = new ArrayList<>();
		AccessoryTileGroup tile = new AccessoryTileGroup();
		tile.setGroupName("Apple AirPods");
		tile.setAccessories(getAccessoriesOfDevice(deviceId));
		return tileGroup;
	}

	public static List<Accessory> getAccessoriesOfDevice(String deviceId) {
		List<Accessory> accessoryList = new ArrayList<Accessory>();
		Accessory accessory = new Accessory();
		accessory.setColour("Red");
		accessory.setDescription("Ear Phones with portable Charger");
		Price deviceCost = new Price();
		deviceCost.setGross("175.11");
		deviceCost.setNet("20.23");
		deviceCost.setVat("14.56");
		// accessory.setDeviceCost(deviceCost);
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		merchandisingMedia.addAll(merchandisingMedia);
		accessory.setMerchandisingMedia(merchandisingMedia);
		accessory.setName("Ear Phones");
		accessory.setSkuId("88432");
		accessoryList.add(accessory);
		return accessoryList;
	}

	public static List<PriceForBundleAndHardware> getPriceForBundleAndHardware() {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<PriceForBundleAndHardware>();

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setMpType("limited_discount");
		merchandisingPromotions.setPriceEstablishedLabel("priceEstablishedLabel");
		merchandisingPromotions.setPromotionMedia("promotionMedia");
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");

		Price monthlyPrice = new Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");

		Price oneOffPrice = new Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);
		List<StepPricingInfo> stepPricesList = new ArrayList<StepPricingInfo>();
		StepPricingInfo stePrices = new StepPricingInfo();

		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount();
		discount.setSkuId("93353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		// stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		priceForBundleAndHardwareList.add(priceInfo);
		return priceForBundleAndHardwareList;
	}

	public static List<ProductGroupModel> getProductGroupModel() {
		List<ProductGroupModel> productGroupModelList = new ArrayList<ProductGroupModel>();
		ProductGroupModel productGroupModel = new ProductGroupModel();
		productGroupModel.setId("93353");
		productGroupModel.setImageUrl("http://image.png");
		List<String> variantsList = new ArrayList<String>();
		variantsList.add("093353|1");
		variantsList.add("091221|5");
		productGroupModel.setListOfVariants(variantsList);
		productGroupModel.setName("Apple iPhone091221|5-7");
		productGroupModel.setPriority(1);
		productGroupModel.setType("DEVICE");
		productGroupModelList.add(productGroupModel);
		return productGroupModelList;
	}

	public static FacetedDevice getFacetedDeviceList(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String journeyId) {
		FacetedDevice facetedDevice = new FacetedDevice();
		facetedDevice.setDevice(getDeviceListForFaceting(productClass, make, model, groupType, sortCriteria, pageNumber,
				pageSize, journeyId));
		facetedDevice.setNoOfRecordsFound(2);

		return facetedDevice;
	}

	public static List<String> getListOfProducts() {
		List<String> listOfProducts = new ArrayList<>();
		listOfProducts.add("088417");
		listOfProducts.add("093353");
		listOfProducts.add("092660");
		listOfProducts.add("093354");
		return listOfProducts;
	}

	

	public static BundleDetails getCompatibleBundleListJson() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(readFile("\\TEST-MOCK\\BUNDLESV1_compatibleList.json"),
					BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson_Three() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(readFile("\\TEST-MOCK\\BUNDLESV1_compatibleList3.json"),
					BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson_Four() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(readFile("\\TEST-MOCK\\\\BUNDLESV2_compatibleList.json"),
					BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson_Five() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(readFile("\\TEST-MOCK\\BUNDLESV3_compatibleList.json"),
					BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static ProductGroupFacetModel getProductGroupFacetModel_One() {
		ProductGroupFacetModel product = new ProductGroupFacetModel();
		List<FacetField> faceList = new ArrayList<>();
		List<Count> lcont = new ArrayList<>();
		FacetField facet = new FacetField();
		Count c1 = new Count();
		c1.setName("apple");
		c1.setCount(2);
		lcont.add(c1);
		facet.setName("equipmentMake");
		facet.setValues(lcont);
		FacetField facet1 = new FacetField();
		Count c2 = new Count();
		c2.setName("grey");
		c2.setCount(2);
		lcont.clear();
		lcont.add(c2);
		facet1.setName("facetColour");
		facet1.setValues(lcont);
		faceList.add(facet);
		faceList.add(facet1);
		product.setListOfFacetsFields(faceList);
		List<ProductGroupModel> groupList = new ArrayList<>();
		ProductGroupModel group = new ProductGroupModel();
		group.setId("093353");
		group.setLeadDeviceId("093353");
		group.setUpgradeLeadDeviceId("093353");
		group.setNonUpgradeLeadDeviceId("093353");
		ProductGroupModel group1 = new ProductGroupModel();
		group1.setId("092660");
		group1.setLeadDeviceId(null);
		group1.setListOfVariants(Arrays.asList("092660|1"));
		groupList.add(group);
		groupList.add(group1);
		product.setListOfProductGroups(groupList);
		product.setNumFound((long) 2);
		return product;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static ProductGroupFacetModel getProductGroupFacetModel() {
		ProductGroupFacetModel bundleDetails = new ProductGroupFacetModel();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(readFile("\\TEST-MOCK\\ProductGroupForFacets.json"),
					ProductGroupFacetModel.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson_One() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(readFile("\\TEST-MOCK\\BUNDLESV1_compatibleList1.json"),
					BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}


	public static List<BundleHeader> getBundleHeaderList(String bundleClass) {
		if (bundleClass.equals("SIMO")) {
			List<BundleHeader> bundleHeaderList = new ArrayList<BundleHeader>();
			BundleHeader bundleHeader = new BundleHeader();
			List<com.vf.uk.dal.device.client.entity.bundle.MediaLink> mediaLinkList = new ArrayList<>();
			com.vf.uk.dal.device.client.entity.bundle.MediaLink mediaLink = new com.vf.uk.dal.device.client.entity.bundle.MediaLink();
			mediaLink.setId("SIMO.JAN.2017");
			mediaLink.setType("MMS");
			mediaLink.setValue("URL");
			mediaLinkList.add(mediaLink);
			List<com.vf.uk.dal.device.client.entity.bundle.BundleAllowance> bundleAllowanceList = new ArrayList<>();
			com.vf.uk.dal.device.client.entity.bundle.BundleAllowance bundleAllowance = new com.vf.uk.dal.device.client.entity.bundle.BundleAllowance();
			bundleAllowance.setType("Red Bundle");
			bundleAllowance.setUom("Months");
			bundleAllowance.setValue("10Gb");
			bundleAllowanceList.add(bundleAllowance);
			com.vf.uk.dal.device.client.entity.price.Price price = new com.vf.uk.dal.device.client.entity.price.Price();
			price.setGross("22");
			price.setNet("20");
			price.setVat("2");
			com.vf.uk.dal.device.client.entity.price.Price price1 = new com.vf.uk.dal.device.client.entity.price.Price();
			price1.setGross("22");
			price1.setNet("20");
			price1.setVat("2");
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
			bundlePrice.setBundleId("110154");
			bundlePrice.setMonthlyDiscountPrice(price);
			bundlePrice.setMonthlyPrice(price);
			com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.client.entity.price.HardwarePrice();
			hardwarePrice.setHardwareId("093353");
			hardwarePrice.setOneOffPrice(price1);
			hardwarePrice.setOneOffDiscountPrice(price1);
			com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware priceInfo = new com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware();
			priceInfo.setBundlePrice(bundlePrice);
			priceInfo.setMonthlyPrice(price1);
			priceInfo.setOneOffPrice(price1);
			priceInfo.setHardwarePrice(hardwarePrice);
			bundleHeader.setPriceInfo(priceInfo);
			bundleHeader.setSkuId("1");
			bundleHeader.setBundleType("SIMO");
			bundleHeader.setCommitmentPeriod("10 Months");
			bundleHeader.setMerchandisingMedia(mediaLinkList);
			bundleHeader.setPaymentType("PostPay");
			bundleHeader.setBundleClass("SIMO");
			bundleHeader.setDescription("SIMO plans only");
			bundleHeader.setName("RedBundle");
			bundleHeader.setAllowance(bundleAllowanceList);
			bundleHeader.setRoamingAllowance(bundleAllowanceList);
			bundleHeader.setPlanCoupleFlag(true);
			bundleHeader.setPlanCoupleId("110083");
			bundleHeader.setPlanCoupleLable("Double the data and get entertainment for just 12 pounds more!");

			BundleHeader bundleHeader1 = new BundleHeader();
			List<com.vf.uk.dal.device.client.entity.bundle.MediaLink> mediaLinkList1 = new ArrayList<>();
			com.vf.uk.dal.device.client.entity.bundle.MediaLink mediaLink1 = new com.vf.uk.dal.device.client.entity.bundle.MediaLink();
			mediaLink1.setId("SIMO.JAN.2017");
			mediaLink1.setType("MMS");
			mediaLink1.setValue("URL");
			mediaLinkList1.add(mediaLink1);
			List<com.vf.uk.dal.device.client.entity.bundle.BundleAllowance> bundleAllowanceList1 = new ArrayList<>();
			com.vf.uk.dal.device.client.entity.bundle.BundleAllowance bundleAllowance1 = new com.vf.uk.dal.device.client.entity.bundle.BundleAllowance();
			bundleAllowance1.setType("Red Bundle");
			bundleAllowance1.setUom("Months");
			bundleAllowance1.setValue("10Gb");
			bundleAllowanceList1.add(bundleAllowance1);
			com.vf.uk.dal.device.client.entity.price.Price price2 = new com.vf.uk.dal.device.client.entity.price.Price();
			price2.setGross("20");
			price2.setNet("21");
			price2.setVat("2");
			com.vf.uk.dal.device.client.entity.price.Price price3 = new com.vf.uk.dal.device.client.entity.price.Price();
			price3.setGross("18");
			price3.setNet("16");
			price3.setVat("2");
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice1 = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
			bundlePrice1.setBundleId("110154");
			bundlePrice1.setMonthlyDiscountPrice(price2);
			bundlePrice1.setMonthlyPrice(price2);
			com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice1 = new com.vf.uk.dal.device.client.entity.price.HardwarePrice();
			hardwarePrice1.setHardwareId("093353");
			hardwarePrice1.setOneOffPrice(price3);
			hardwarePrice1.setOneOffDiscountPrice(price3);
			com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware priceInfo1 = new com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware();
			priceInfo1.setBundlePrice(bundlePrice1);
			priceInfo1.setMonthlyPrice(price3);
			priceInfo1.setOneOffPrice(price3);
			priceInfo.setHardwarePrice(hardwarePrice1);
			bundleHeader1.setPriceInfo(priceInfo1);
			bundleHeader1.setSkuId("1");
			bundleHeader1.setBundleType("SIMO");
			bundleHeader1.setCommitmentPeriod("10 Months");
			bundleHeader1.setMerchandisingMedia(mediaLinkList);
			bundleHeader1.setPaymentType("PostPay");
			bundleHeader1.setBundleClass("SIMO");
			bundleHeader1.setDescription("SIMO plans only");
			bundleHeader1.setName("RedBundle");
			bundleHeader1.setAllowance(bundleAllowanceList);
			bundleHeader1.setRoamingAllowance(bundleAllowanceList);
			bundleHeader1.setPlanCoupleFlag(true);
			bundleHeader1.setPlanCoupleId("110083");
			bundleHeader1.setPlanCoupleLable("Double the data and get entertainment for just 12 pounds more!");
			bundleHeaderList.add(bundleHeader);
			bundleHeaderList.add(bundleHeader1);
			return bundleHeaderList;
		} else {
			return null;
		}
	}

	

	

	public static com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion getMemPro() {
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mem = new com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion();
		mem.setTag("handset.limited.GBP");
		mem.setType("full_duration");
		return mem;
	}

	/**
	 * public static List<BazaarVoice> getReviewsJsonObject() throws
	 * IOException, ParseException{ ObjectMapper mapper = new ObjectMapper();
	 * mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	 * false);
	 * mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	 * String productModel=new
	 * String(readFile("\\BazaarVoiceResponse.json")); BazaarVoice
	 * product=mapper.readValue(productModel, BazaarVoice.class); List
	 * <BazaarVoice> bazaarList=new ArrayList<>(); bazaarList.add(product);
	 * return bazaarList; }
	 */

	public static List<BazaarVoice> getReviewsJsonObject() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String productModel = new String(readFile("\\TEST-MOCK\\BazaarVoiceResponse.json"));
		List<BazaarVoice> bazaarList = new ArrayList<>();
		BazaarVoice bazaar = new BazaarVoice();
		bazaar.setJsonsource(productModel);
		bazaarList.add(bazaar);
		return bazaarList;
	}

	public static Map<String, String> getleadMemberMap() {
		Map<String, String> leadMemberMap = new HashMap<>();
		leadMemberMap.put("093353", "Y");
		return leadMemberMap;
	}

	public static Map<String, List<String>> getleadMemberMapp() {
		Map<String, List<String>> leadMemberMap = new HashMap<>();
		List<String> string = new ArrayList<>();
		string.add("Y");
		leadMemberMap.put("093353", string);
		return leadMemberMap;
	}

	public static List<com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware> getPrice() {
		List<com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware>();

		com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware priceInfo = new com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware();

		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		bundlePrice.setBundleId("183099");
		List<com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion> merchandisingPromotionsList = new ArrayList<com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion>();
		com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion merchandisingPromotions = new com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setPriceEstablishedLabel("WAS");
		merchandisingPromotions.setDescription("3 months free data for e more");
		merchandisingPromotions.setMpType("full_duration");

		merchandisingPromotionsList.add(merchandisingPromotions);
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		com.vf.uk.dal.device.client.entity.price.Price monthlyDiscountPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		com.vf.uk.dal.device.client.entity.price.Price oneOffDiscountPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");

		com.vf.uk.dal.device.client.entity.price.Price monthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");

		com.vf.uk.dal.device.client.entity.price.Price oneOffPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.client.entity.price.HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);
		List<com.vf.uk.dal.device.client.entity.price.StepPricingInfo> stepPricesList = new ArrayList<com.vf.uk.dal.device.client.entity.price.StepPricingInfo>();
		com.vf.uk.dal.device.client.entity.price.StepPricingInfo stePrices = new com.vf.uk.dal.device.client.entity.price.StepPricingInfo();

		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount();
		discount.setSkuId("93353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		// stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		/*
		 * stePrices.setMonthlyPrice(monthlyPrice);
		 * stePrices.setOneOffPrice(oneOffPrice);
		 */
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		priceForBundleAndHardwareList.add(priceInfo);
		return priceForBundleAndHardwareList;
	}

	public static Map<String, String> getQueryParamsMapForDeviceDetails(String... arguments) {
		Map<String, String> queryparams = new HashMap<String, String>();
		if (arguments == null)
			return queryparams;
		if (arguments.length == 1) {
			queryparams.put("deviceId", arguments[0]);
		} else if (arguments.length == 2) {
			queryparams.put("deviceId", null);
		}
		return queryparams;
	}


	public static com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion getMerchPromotion() {
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mem = new com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion();
		mem.setTag("EXTRA.1GB.DATA");
		mem.setType("entertainment");
		return mem;
	}

	public static com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion getMerchPromotion1() {
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mem = new com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion();
		mem.setTag("qwerty");
		mem.setType("entertainment");
		return mem;
	}

	public static com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion getMerchPromotionForListOfDevicedetails() {
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mem = new com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion();
		mem.setTag("sash.banner");
		mem.setType("entertainment");
		return mem;
	}

	

	public static CommercialProduct getCommercialProduct_Five() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");

		List<String> compatiblePlans = new ArrayList<>();
		compatiblePlans.add("110154");
		commercialProduct.setListOfCompatiblePlanIds(compatiblePlans);
		commercialProduct.setId("123");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setInvoiceChargeable(true);
		priceDetail.setPriceGross((double) 64);
		priceDetail.setPriceNet((double) 54);
		priceDetail.setPriceVAT((double) 24);
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setOrder((long) 12345);

		PromoteAs promoteAs = new PromoteAs();
		List<String> promotionName = new ArrayList<String>();
		promotionName.add("qwerty");
		promotionName.add("asdfg");
		promoteAs.setPromotionName(promotionName);
		commercialProduct.setPromoteAs(promoteAs);

		ProductControl productControl = new ProductControl();
		productControl.setDisplayableinLife(true);
		productControl.setSellableinLife(true);
		productControl.setDisplayableAcq(true);
		productControl.setSellableRet(true);
		productControl.setDisplayableRet(true);
		productControl.setSellableAcq(true);
		productControl.setDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp = new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(timeStamp);
		productControl.setBackOrderable(true);

		commercialProduct.setProductControl(productControl);
		Equipment equipment = new Equipment();
		equipment.setMake("SetMake");
		equipment.setModel("SetModel");
		commercialProduct.setEquipment(equipment);
		ProductAvailability productAvailability = new ProductAvailability();
		productAvailability.setEnd(null);
		productAvailability.setSalesExpired(false);
		productAvailability.setStart(null);
		commercialProduct.setProductAvailability(productAvailability);
		List<com.vf.uk.dal.device.model.product.Group> specificationGroupsList = new ArrayList<com.vf.uk.dal.device.model.product.Group>();
		com.vf.uk.dal.device.model.product.Group gr = new com.vf.uk.dal.device.model.product.Group();
		com.vf.uk.dal.device.model.product.Group group = new com.vf.uk.dal.device.model.product.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vf.uk.dal.device.model.product.Specification> specificationList = new ArrayList<com.vf.uk.dal.device.model.product.Specification>();
		com.vf.uk.dal.device.model.product.Specification specification = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification1 = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification2 = new com.vf.uk.dal.device.model.product.Specification();
		specification.setComparable(true);
		specification.setIsKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setIsKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setIsKey(true);
		specification2.setName("HexValue");
		specification2.setPriority((long) 1);
		specification2.setValue("#E5000");
		specification2.setValueType("");
		specification2.setValueUOM("");

		specificationList.add(specification1);
		specificationList.add(specification2);
		specificationList.add(specification);
		gr.setSpecifications(specificationList);

		specificationGroupsList.add(gr);
		specificationGroupsList.add(group);
		commercialProduct.setSpecificationGroups(specificationGroupsList);

		List<MediaURL> mediaUrlList = new ArrayList<MediaURL>();
		MediaURL mediaUrl = new MediaURL();
		mediaUrl.setMediaName("MEdiaName");
		mediaUrl.setMediaURL("URL");
		MediaURL mediaUrl1 = new MediaURL();
		mediaUrl.setMediaName("MEdiaName1");
		mediaUrl.setMediaURL("URL2");
		mediaUrlList.add(mediaUrl);
		mediaUrlList.add(mediaUrl1);
		commercialProduct.setListOfmediaURLs(mediaUrlList);

		List<com.vf.uk.dal.device.model.product.ImageURL> listOfimageURLs = new ArrayList<com.vf.uk.dal.device.model.product.ImageURL>();
		com.vf.uk.dal.device.model.product.ImageURL imageURL = new com.vf.uk.dal.device.model.product.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUom("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vf.uk.dal.device.model.product.Discount discount = new com.vf.uk.dal.device.model.product.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}

	public static CacheDeviceTileResponse getCacheDeviceTileResponse() {

		CacheDeviceTileResponse cacheDeviceTileResponse = new CacheDeviceTileResponse();
		cacheDeviceTileResponse.setJobId("1234");
		cacheDeviceTileResponse.setJobStatus("Success");
		return cacheDeviceTileResponse;

	}

	public static List<PriceForBundleAndHardware> getOfferAppliedPrice() {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<PriceForBundleAndHardware>();

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion merchandisingPromotions = new com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion();

		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("W_HH_OC_02");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setPriceEstablishedLabel("priceEstablishedLabel");
		merchandisingPromotions.setPromotionMedia("promotionMedia");
		merchandisingPromotions.setMpType(CONDITIONAL_FULL_DISCOUNT);
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		com.vf.uk.dal.device.client.entity.price.Price monthlyDiscountPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");

		com.vf.uk.dal.device.client.entity.price.Price monthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");

		com.vf.uk.dal.device.client.entity.price.Price oneOffPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);
		List<com.vf.uk.dal.device.client.entity.price.StepPricingInfo> stepPricesList = new ArrayList<com.vf.uk.dal.device.client.entity.price.StepPricingInfo>();
		com.vf.uk.dal.device.client.entity.price.StepPricingInfo stePrices = new com.vf.uk.dal.device.client.entity.price.StepPricingInfo();

		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount();
		discount.setSkuId("93353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		// stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		priceForBundleAndHardwareList.add(priceInfo);
		return priceForBundleAndHardwareList;
	}

	public static CommercialProduct getCommercialProductForCacheDeviceTile() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("123");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		List<String> listOfCompatiblePlan = new ArrayList<>();
		listOfCompatiblePlan.add("110154");
		listOfCompatiblePlan.add("110166");
		listOfCompatiblePlan.add("110156");
		commercialProduct.setListOfCompatiblePlanIds(listOfCompatiblePlan);
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setInvoiceChargeable(true);
		priceDetail.setPriceGross((double) 64);
		priceDetail.setPriceNet((double) 54);
		priceDetail.setPriceVAT((double) 24);
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setOrder((long) 12345);

		PromoteAs promoteAs = new PromoteAs();
		List<String> promotionName = new ArrayList<String>();
		promotionName.add("qwerty");
		promotionName.add("asdfg");
		promoteAs.setPromotionName(promotionName);
		commercialProduct.setPromoteAs(promoteAs);

		ProductControl productControl = new ProductControl();
		productControl.setDisplayableinLife(true);
		productControl.setSellableinLife(true);
		productControl.setDisplayableAcq(true);
		productControl.setSellableRet(true);
		productControl.setDisplayableRet(true);
		productControl.setSellableAcq(true);
		productControl.setDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp = new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(timeStamp);
		productControl.setBackOrderable(true);

		commercialProduct.setProductControl(productControl);
		Equipment equipment = new Equipment();
		equipment.setMake("SetMake");
		equipment.setModel("SetModel");
		commercialProduct.setEquipment(equipment);
		ProductAvailability productAvailability = new ProductAvailability();
		productAvailability.setEnd(null);
		productAvailability.setSalesExpired(false);
		productAvailability.setStart(null);
		commercialProduct.setProductAvailability(productAvailability);
		List<com.vf.uk.dal.device.model.product.Group> specificationGroupsList = new ArrayList<com.vf.uk.dal.device.model.product.Group>();
		com.vf.uk.dal.device.model.product.Group gr = new com.vf.uk.dal.device.model.product.Group();
		com.vf.uk.dal.device.model.product.Group group = new com.vf.uk.dal.device.model.product.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vf.uk.dal.device.model.product.Specification> specificationList = new ArrayList<com.vf.uk.dal.device.model.product.Specification>();
		com.vf.uk.dal.device.model.product.Specification specification = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification1 = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification2 = new com.vf.uk.dal.device.model.product.Specification();
		specification.setComparable(true);
		specification.setIsKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setIsKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setIsKey(true);
		specification2.setName("HexValue");
		specification2.setPriority((long) 1);
		specification2.setValue("#E5000");
		specification2.setValueType("");
		specification2.setValueUOM("");

		specificationList.add(specification1);
		specificationList.add(specification2);
		specificationList.add(specification);
		gr.setSpecifications(specificationList);

		specificationGroupsList.add(gr);
		specificationGroupsList.add(group);
		commercialProduct.setSpecificationGroups(specificationGroupsList);

		List<MediaURL> mediaUrlList = new ArrayList<MediaURL>();
		MediaURL mediaUrl = new MediaURL();
		mediaUrl.setMediaName("MEdiaName");
		mediaUrl.setMediaURL("URL");
		MediaURL mediaUrl1 = new MediaURL();
		mediaUrl.setMediaName("MEdiaName1");
		mediaUrl.setMediaURL("URL2");
		mediaUrlList.add(mediaUrl);
		mediaUrlList.add(mediaUrl1);
		commercialProduct.setListOfmediaURLs(mediaUrlList);

		List<com.vf.uk.dal.device.model.product.ImageURL> listOfimageURLs = new ArrayList<com.vf.uk.dal.device.model.product.ImageURL>();
		com.vf.uk.dal.device.model.product.ImageURL imageURL = new com.vf.uk.dal.device.model.product.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUom("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vf.uk.dal.device.model.product.Discount discount = new com.vf.uk.dal.device.model.product.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}

	public static CommercialProduct getCommercialProductForCacheDeviceTile_One() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("124");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		List<String> listOfCompatiblePlan = new ArrayList<>();
		listOfCompatiblePlan.add("110154");
		listOfCompatiblePlan.add("110166");
		listOfCompatiblePlan.add("110156");
		commercialProduct.setListOfCompatiblePlanIds(listOfCompatiblePlan);
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setInvoiceChargeable(true);
		priceDetail.setPriceGross((double) 64);
		priceDetail.setPriceNet((double) 54);
		priceDetail.setPriceVAT((double) 24);
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setOrder((long) 12345);

		PromoteAs promoteAs = new PromoteAs();
		List<String> promotionName = new ArrayList<String>();
		promotionName.add("qwerty");
		promotionName.add("asdfg");
		promoteAs.setPromotionName(promotionName);
		commercialProduct.setPromoteAs(promoteAs);

		ProductControl productControl = new ProductControl();
		productControl.setDisplayableinLife(true);
		productControl.setSellableinLife(true);
		productControl.setDisplayableAcq(true);
		productControl.setSellableRet(true);
		productControl.setDisplayableRet(true);
		productControl.setSellableAcq(true);
		productControl.setDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp = new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(timeStamp);
		productControl.setBackOrderable(true);

		commercialProduct.setProductControl(productControl);
		Equipment equipment = new Equipment();
		equipment.setMake("SetMake");
		equipment.setModel("SetModel");
		commercialProduct.setEquipment(equipment);
		ProductAvailability productAvailability = new ProductAvailability();
		productAvailability.setEnd(null);
		productAvailability.setSalesExpired(false);
		productAvailability.setStart(null);
		commercialProduct.setProductAvailability(productAvailability);
		List<com.vf.uk.dal.device.model.product.Group> specificationGroupsList = new ArrayList<com.vf.uk.dal.device.model.product.Group>();
		com.vf.uk.dal.device.model.product.Group gr = new com.vf.uk.dal.device.model.product.Group();
		com.vf.uk.dal.device.model.product.Group group = new com.vf.uk.dal.device.model.product.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vf.uk.dal.device.model.product.Specification> specificationList = new ArrayList<com.vf.uk.dal.device.model.product.Specification>();
		com.vf.uk.dal.device.model.product.Specification specification = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification1 = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification2 = new com.vf.uk.dal.device.model.product.Specification();
		specification.setComparable(true);
		specification.setIsKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setIsKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setIsKey(true);
		specification2.setName("HexValue");
		specification2.setPriority((long) 1);
		specification2.setValue("#E5000");
		specification2.setValueType("");
		specification2.setValueUOM("");

		specificationList.add(specification1);
		specificationList.add(specification2);
		specificationList.add(specification);
		gr.setSpecifications(specificationList);

		specificationGroupsList.add(gr);
		specificationGroupsList.add(group);
		commercialProduct.setSpecificationGroups(specificationGroupsList);

		List<MediaURL> mediaUrlList = new ArrayList<MediaURL>();
		MediaURL mediaUrl = new MediaURL();
		mediaUrl.setMediaName("MEdiaName");
		mediaUrl.setMediaURL("URL");
		MediaURL mediaUrl1 = new MediaURL();
		mediaUrl.setMediaName("MEdiaName1");
		mediaUrl.setMediaURL("URL2");
		mediaUrlList.add(mediaUrl);
		mediaUrlList.add(mediaUrl1);
		commercialProduct.setListOfmediaURLs(mediaUrlList);

		List<com.vf.uk.dal.device.model.product.ImageURL> listOfimageURLs = new ArrayList<com.vf.uk.dal.device.model.product.ImageURL>();
		com.vf.uk.dal.device.model.product.ImageURL imageURL = new com.vf.uk.dal.device.model.product.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUom("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vf.uk.dal.device.model.product.Discount discount = new com.vf.uk.dal.device.model.product.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}

	public static CommercialProduct getCommercialProductForInsurance() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("093353");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setInvoiceChargeable(true);
		priceDetail.setPriceGross((double) 64);
		priceDetail.setPriceNet((double) 54);
		priceDetail.setPriceVAT((double) 24);
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setOrder((long) 12345);

		PromoteAs promoteAs = new PromoteAs();
		List<String> promotionName = new ArrayList<String>();
		promotionName.add("qwerty");
		promotionName.add("asdfg");
		promoteAs.setPromotionName(promotionName);
		commercialProduct.setPromoteAs(promoteAs);
		commercialProduct.setIsDeviceProduct(true);
		ProductControl productControl = new ProductControl();
		productControl.setDisplayableinLife(true);
		productControl.setSellableinLife(true);
		productControl.setDisplayableAcq(true);
		productControl.setSellableRet(true);
		productControl.setDisplayableRet(true);
		productControl.setSellableAcq(true);
		productControl.setDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp = new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(timeStamp);
		productControl.setBackOrderable(true);

		commercialProduct.setProductControl(productControl);
		Equipment equipment = new Equipment();
		equipment.setMake("SetMake");
		equipment.setModel("SetModel");
		commercialProduct.setEquipment(equipment);
		ProductAvailability productAvailability = new ProductAvailability();
		productAvailability.setEnd(null);
		productAvailability.setSalesExpired(false);
		productAvailability.setStart(null);
		commercialProduct.setProductAvailability(productAvailability);
		ProductGroups pgs = new ProductGroups();
		List<com.vf.uk.dal.device.model.product.ProductGroup> listOfProductGroup = new ArrayList<>();
		com.vf.uk.dal.device.model.product.ProductGroup pg = new com.vf.uk.dal.device.model.product.ProductGroup();
		pg.setProductGroupRole("Compatible Insurance");
		pg.setProductGroupName("DEVICE_PAYM");
		listOfProductGroup.add(pg);
		pgs.setProductGroup(listOfProductGroup);
		commercialProduct.setProductGroups(pgs);
		List<com.vf.uk.dal.device.model.product.Group> specificationGroupsList = new ArrayList<com.vf.uk.dal.device.model.product.Group>();
		com.vf.uk.dal.device.model.product.Group gr = new com.vf.uk.dal.device.model.product.Group();
		com.vf.uk.dal.device.model.product.Group group = new com.vf.uk.dal.device.model.product.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);
		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vf.uk.dal.device.model.product.Specification> specificationList = new ArrayList<com.vf.uk.dal.device.model.product.Specification>();
		com.vf.uk.dal.device.model.product.Specification specification = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification1 = new com.vf.uk.dal.device.model.product.Specification();
		com.vf.uk.dal.device.model.product.Specification specification2 = new com.vf.uk.dal.device.model.product.Specification();
		specification.setComparable(true);
		specification.setIsKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setIsKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setIsKey(true);
		specification2.setName("HexValue");
		specification2.setPriority((long) 1);
		specification2.setValue("#E5000");
		specification2.setValueType("");
		specification2.setValueUOM("");

		specificationList.add(specification1);
		specificationList.add(specification2);
		specificationList.add(specification);
		gr.setSpecifications(specificationList);

		specificationGroupsList.add(gr);
		specificationGroupsList.add(group);
		commercialProduct.setSpecificationGroups(specificationGroupsList);

		List<MediaURL> mediaUrlList = new ArrayList<MediaURL>();
		MediaURL mediaUrl = new MediaURL();
		mediaUrl.setMediaName("MEdiaName");
		mediaUrl.setMediaURL("URL");
		MediaURL mediaUrl1 = new MediaURL();
		mediaUrl.setMediaName("MEdiaName1");
		mediaUrl.setMediaURL("URL2");
		mediaUrlList.add(mediaUrl);
		mediaUrlList.add(mediaUrl1);
		commercialProduct.setListOfmediaURLs(mediaUrlList);

		List<com.vf.uk.dal.device.model.product.ImageURL> listOfimageURLs = new ArrayList<com.vf.uk.dal.device.model.product.ImageURL>();
		com.vf.uk.dal.device.model.product.ImageURL imageURL = new com.vf.uk.dal.device.model.product.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUom("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vf.uk.dal.device.model.product.Discount discount = new com.vf.uk.dal.device.model.product.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		List<String> listOfComplanIds = new ArrayList<>();
		listOfComplanIds.add("123");
		listOfComplanIds.add("456");
		listOfComplanIds.add("789");
		listOfComplanIds.add("101112");
		listOfComplanIds.add("131415");
		commercialProduct.setListOfCompatiblePlanIds(listOfComplanIds);

		return commercialProduct;
	}

	public static List<MerchandisingPromotionModel> getMerChandisingPromotion() {
		List<MerchandisingPromotionModel> merchandisingPromotionModelList = new ArrayList<>();
		MerchandisingPromotionModel merchandisingPromotionModel = new MerchandisingPromotionModel();

		merchandisingPromotionModel.setTag("sdffd");
		merchandisingPromotionModelList.add(merchandisingPromotionModel);
		return merchandisingPromotionModelList;
	}

	public static List<MerchandisingPromotionModel> getMerChandisingPromotion_One() {
		List<MerchandisingPromotionModel> merchandisingPromotionModelList = new ArrayList<>();
		MerchandisingPromotionModel merchandisingPromotionModel = new MerchandisingPromotionModel();

		merchandisingPromotionModel.setTag("W_HH_PAYM_OC_01");
		merchandisingPromotionModelList.add(merchandisingPromotionModel);
		return merchandisingPromotionModelList;
	}

	public static List<OfferAppliedPriceModel> getOfferAppliedPriceModel() {
		List<OfferAppliedPriceModel> list = new ArrayList<>();
		OfferAppliedPriceModel model1 = new OfferAppliedPriceModel();
		model1.setOneOffDiscountedGrossPrice(54.0f);
		model1.setOneOffDiscountedNetPrice(46.0f);
		model1.setOneOffDiscountedVatPrice(9.0f);
		model1.setOneOffGrossPrice(55.0f);
		model1.setOneOffNetPrice(46.0f);
		model1.setOneOffVatPrice(9.0f);
		model1.setHardwareId("092660");
		model1.setProductId("092660");
		model1.setMonthlyDiscountedGrossPrice(53.0f);
		model1.setMonthlyDiscountedNetPrice(44.0f);
		model1.setMonthlyDiscountedVatPrice(9.0f);
		model1.setMonthlyGrossPrice(54.0f);
		model1.setMonthlyNetPrice(46.0f);
		model1.setMonthlyVatPrice(9.0f);
		model1.setBundleId("110154");
		OfferAppliedPriceModel model = new OfferAppliedPriceModel();
		model.setOneOffDiscountedGrossPrice(54.0f);
		model.setOneOffDiscountedNetPrice(46.0f);
		model.setOneOffDiscountedVatPrice(9.0f);
		model.setOneOffGrossPrice(54.0f);
		model.setOneOffNetPrice(46.0f);
		model.setOneOffVatPrice(9.0f);
		model.setHardwareId("093353");
		model.setMonthlyDiscountedGrossPrice(54.0f);
		model.setMonthlyDiscountedNetPrice(44.0f);
		model.setMonthlyDiscountedVatPrice(9.0f);
		model.setMonthlyGrossPrice(54.0f);
		model.setMonthlyNetPrice(46.0f);
		model.setMonthlyVatPrice(9.0f);
		model.setBundleId("110154");
		list.add(model);
		list.add(model1);
		return list;
	}

	public static List<MerchandisingPromotionModel> getModel() {
		List<MerchandisingPromotionModel> modelList = new ArrayList<>();
		MerchandisingPromotionModel model = new MerchandisingPromotionModel();
		model.setTag("W_HH_OC_02");
		model.setPackageType(Arrays.asList("Upgrade", "SecondLine"));
		modelList.add(model);
		return modelList;
	}

	public static List<BundleAndHardwarePromotions> getListOfBundleAndHardwarePromotions() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(readFile("\\TEST-MOCK\\BundleandhardwarePromotuions.json"));
			BundleAndHardwarePromotions[] bundleList = mapper.readValue(bundle, BundleAndHardwarePromotions[].class);

			return mapper.convertValue(bundleList, new TypeReference<List<BundleAndHardwarePromotions>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	/**
	 * @author manoj.bera
	 * @return
	 */
	public static com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware getUtilityPriceForBundleAndHardware() {
		List<com.vf.uk.dal.device.model.BundleAllowance> bundleAllowanceList = new ArrayList<>();
		com.vf.uk.dal.device.model.BundleAllowance bundleAllowance = new com.vf.uk.dal.device.model.BundleAllowance();
		bundleAllowance.setType("Red Bundle");
		bundleAllowance.setUom("Months");
		bundleAllowance.setValue("10Gb");
		bundleAllowanceList.add(bundleAllowance);
		com.vf.uk.dal.device.client.entity.price.Price price = new com.vf.uk.dal.device.client.entity.price.Price();
		price.setGross("22");
		price.setNet("20");
		price.setVat("2");
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		bundlePrice.setBundleId("110154");
		/*
		 * bundlePrice.setMonthlyDiscountPrice(price);
		 * bundlePrice.setMonthlyPrice(price);
		 */
		com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.client.entity.price.HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setOneOffPrice(price);
		hardwarePrice.setOneOffDiscountPrice(price);
		com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware priceInfo = new com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware();
		priceInfo.setBundlePrice(bundlePrice);
		priceInfo.setMonthlyPrice(price);
		priceInfo.setOneOffPrice(price);
		priceInfo.setHardwarePrice(hardwarePrice);
		return priceInfo;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<CommercialProduct> getCommercialProductsListOfMakeAndModel() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(
					readFile("\\TEST-MOCK\\CommercialProductListOfMakeAndModel.json"));
			CommercialProduct[] commercialProductList = mapper.readValue(commercialProduct, CommercialProduct[].class);

			return mapper.convertValue(commercialProductList, new TypeReference<List<CommercialProduct>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<Group> getListOfProductGroupFromProductGroupRepository() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String listOfGroupsString = new String(
					readFile("\\TEST-MOCK\\ListOfProductGroupFromProductGroupRepository.json"));
			Group[] groupList = mapper.readValue(listOfGroupsString, Group[].class);

			return mapper.convertValue(groupList, new TypeReference<List<Group>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static BazaarVoice getBazaarVoice() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String productModel;
		BazaarVoice product = null;
		try {
			productModel = new String(readFile("\\TEST-MOCK\\BazaarVoiceResponse.json"));
			product = mapper.readValue(productModel, BazaarVoice.class);
			product.setJsonsource(productModel);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return product;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CommercialBundle getCommercialBundleFromCommercialBundleRepository() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String bundleModel = new String(
					readFile("\\TEST-MOCK\\Coherence_CommercialBundleRepo_CommercialBundle"));
			CommercialBundle bundleModelList = mapper.readValue(bundleModel, CommercialBundle.class);

			return mapper.convertValue(bundleModelList, new TypeReference<CommercialBundle>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CommercialProduct getCommercialProductByDeviceId_093353() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(readFile("\\TEST-MOCK\\CommercialProduct_093353.json"));
			CommercialProduct commercialProductList = mapper.readValue(commercialProduct, CommercialProduct.class);

			return mapper.convertValue(commercialProductList, new TypeReference<CommercialProduct>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<Group> getListOfProductGroupForAccessory() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String listOfGroupsString = new String(readFile("\\TEST-MOCK\\ListOfAccessoryGroup.json"));
			Group[] groupList = mapper.readValue(listOfGroupsString, Group[].class);

			return mapper.convertValue(groupList, new TypeReference<List<Group>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static PriceForProduct getPriceForProduct_For_GetAccessoriesForDevice() {
		PriceForProduct navigation = new PriceForProduct();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(
					readFile("\\TEST-MOCK\\priceForProduct_For_GetAccessoriesForDevice.json"));
			PriceForProduct bundleList = mapper.readValue(bundle, PriceForProduct.class);

			return mapper.convertValue(bundleList, new TypeReference<PriceForProduct>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return navigation;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static BundleDeviceAndProductsList bundleDeviceAndProductsList_For_GetAccessoriesOfDevice() {
		BundleDeviceAndProductsList navigation = new BundleDeviceAndProductsList();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(
					readFile("\\TEST-MOCK\\bundleDeviceAndProductsList_For_GetAccessoriesOfDevice.json"));
			BundleDeviceAndProductsList bundleList = mapper.readValue(bundle, BundleDeviceAndProductsList.class);

			return mapper.convertValue(bundleList, new TypeReference<BundleDeviceAndProductsList>() {
			});

		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return navigation;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static BundleDeviceAndProductsList bundleDeviceAndProductsList_For_GetAccessoriesOfDeviceIntegration() {
		BundleDeviceAndProductsList navigation = new BundleDeviceAndProductsList();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(readFile("\\TEST-MOCK\\bundleDeviceAndProductsList_For_GetAccessoriesOfDevice_Integration.json"));
			BundleDeviceAndProductsList bundleList = mapper.readValue(bundle, BundleDeviceAndProductsList.class);

			return mapper.convertValue(bundleList, new TypeReference<BundleDeviceAndProductsList>() {
			});

		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return navigation;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<CommercialProduct> getCommercialProductsListOfAccessories() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(
					readFile("\\TEST-MOCK\\CommercialProductListOfAccessory.json"));
			CommercialProduct[] commercialProductList = mapper.readValue(commercialProduct, CommercialProduct[].class);

			return mapper.convertValue(commercialProductList, new TypeReference<List<CommercialProduct>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<CommercialProduct> getCommercialProductsListOfAccessoriesWithEndDate() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(
					readFile("\\TEST-MOCK\\CommercialProductOfAccessorywithEndDate.json"));
			CommercialProduct[] commercialProductList = mapper.readValue(commercialProduct, CommercialProduct[].class);

			return mapper.convertValue(commercialProductList, new TypeReference<List<CommercialProduct>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static String getReviewsJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String productModel = new String(readFile("\\TEST-MOCK\\BazaarVoiceResponse.json"));
		BazaarVoice product = new BazaarVoice();
		product = mapper.readValue(productModel, BazaarVoice.class);
		product.setJsonsource(productModel);

		return product.getJsonsource();

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<ProductGroupModel> getListOfProductGroupModels() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String productGroupModel = new String(
					readFile("\\TEST-MOCK\\listOfProductGroupModel_For_DevicePAYM_apple.json"));
			ProductGroupModel[] productGroupModelList = mapper.readValue(productGroupModel, ProductGroupModel[].class);

			return mapper.convertValue(productGroupModelList, new TypeReference<List<ProductGroupModel>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	

	public static BundleDetailsForAppSrv getCoupledBundleListForDevice() {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String bundleDetailsForAppSrv = new String(readFile("\\TEST-MOCK\\ByCoupledBundleList.json"));
			BundleDetailsForAppSrv bundleDetails = mapper.readValue(bundleDetailsForAppSrv,
					BundleDetailsForAppSrv.class);

			return mapper.convertValue(bundleDetails, new TypeReference<BundleDetailsForAppSrv>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<PriceForBundleAndHardware> getPriceForBundleAndHardwareListFromTupleList() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String price = new String(readFile("\\TEST-MOCK\\PriceForBundleAndHardware.json"));
			PriceForBundleAndHardware[] priceList = mapper.readValue(price, PriceForBundleAndHardware[].class);

			return mapper.convertValue(priceList, new TypeReference<List<PriceForBundleAndHardware>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static SourcePackageSummary getSourcePackageSummary() {
		SourcePackageSummary s = new SourcePackageSummary();
		s.setPromotionId("109381");
		return s;
	}

	public static RecommendedProductListRequest getRecommendedDeviceListRequest(String msisdn, String deviceId) {

		RecommendedProductListRequest recomProdListReq = new RecommendedProductListRequest();

		recomProdListReq.setSerialNumber(msisdn);
		recomProdListReq.setAccountCategory(ACCOUNT_CATEGORY_INDIVIDUAL);

		List<InstalledProduct> instProds = new ArrayList<>();
		InstalledProduct instProd = new InstalledProduct();
		instProd.setId(deviceId);
		instProd.setTypeCode(STRING_TARIFF);
		instProd.setAmount("220000.00");
		instProds.add(instProd);
		recomProdListReq.setInstalledProducts(instProds);

		List<Preferences> prefs = new ArrayList<>();
		Preferences handsetPref = new Preferences();
		handsetPref.setName(PREFERENCE_NAME_HANDSET);
		handsetPref.setDataTypeCode(PREFERENCE_DATATYPE_CODE_PREFERENCE);
		handsetPref.setValue("all");
		prefs.add(handsetPref);

		Preferences upgradePref = new Preferences();
		upgradePref.setName(PREFERENCE_NAME_UPGRADE);
		upgradePref.setDataTypeCode(PREFERENCE_DATATYPE_CODE_GENERAL);
		upgradePref.setValue("SIMOFLEX");
		prefs.add(upgradePref);

		Preferences recommitPref = new Preferences();
		recommitPref.setName(PREFERENCE_NAME_RECOMMIT);
		recommitPref.setDataTypeCode(PREFERENCE_DATATYPE_CODE_GENERAL);
		recommitPref.setValue("FALSE");
		prefs.add(recommitPref);

		Preferences segmentPref = new Preferences();
		segmentPref.setName(PREFERENCE_NAME_SEGMENT);
		segmentPref.setDataTypeCode(PREFERENCE_DATATYPE_ELIGIBILITY_CRITERIA);
		segmentPref.setValue("cbu");
		prefs.add(segmentPref);

		recomProdListReq.setPreferences(prefs);

		List<String> recomPrdTypes;
		recomPrdTypes = new ArrayList<>();
		recomPrdTypes.add(PREFERENCE_NAME_HANDSET);

		recomProdListReq.setBasketItems(null);
		recomProdListReq.setNoOfRecommendations("100");

		recomProdListReq.setRecommendedProductTypes(recomPrdTypes);

		return recomProdListReq;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CommercialProduct getCommercialProductByDeviceId_093353_PAYG() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(readFile("\\TEST-MOCK\\CommercialProduct_088417_PAYG.json"));
			CommercialProduct commercialProductList = mapper.readValue(commercialProduct, CommercialProduct.class);

			return mapper.convertValue(commercialProductList, new TypeReference<CommercialProduct>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CommercialProduct getCommercialProductsListOfAccessoriesWithEndDate_One() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(
					readFile("\\TEST-MOCK\\CommercialProductOfAccessorywithEndDate.json"));
			CommercialProduct commercialProductList = mapper.readValue(commercialProduct, CommercialProduct.class);

			return mapper.convertValue(commercialProductList, new TypeReference<CommercialProduct>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static List<com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion> getMerchandisingPromotion_One() {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion> merchandisingPromotionsList = new ArrayList<com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion>();
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion merchandisingPromotions = new com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion();
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setBelowTheLine(true);
		merchandisingPromotions.setPriority(Long.valueOf(1));
		merchandisingPromotions.setLabel("Label");
		merchandisingPromotions.setVersion("Version");
		merchandisingPromotionsList.add(merchandisingPromotions);
		return merchandisingPromotionsList;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CompletableFuture<List<PriceForBundleAndHardware>> getPriceForBundleAndHardwareListFromTupleListAsync() {
		CompletableFuture<List<PriceForBundleAndHardware>> future = null;
		try {
			future = new CompletableFuture<List<PriceForBundleAndHardware>>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String price = new String(readFile("\\TEST-MOCK\\API_Price_calculateForBundleAndHardware"));
			PriceForBundleAndHardware[] priceList = mapper.readValue(price, PriceForBundleAndHardware[].class);

			future.complete(mapper.convertValue(priceList, new TypeReference<List<PriceForBundleAndHardware>>() {
			}));
		} catch (IOException e) {
			future.completeExceptionally(e);
			e.printStackTrace();
		}
		return future;
	}

	public static BundlePrice getBundlePrice() {
		BundlePrice bundlePrice = new BundlePrice();
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();

		merchandisingPromotions.setPromotionMedia("promotion media");
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price monthlyPrice = new Price();
		monthlyDiscountPrice.setGross("13.64");
		monthlyDiscountPrice.setNet("12.5");
		monthlyDiscountPrice.setVat("8.56");

		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setBundleId("183099");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		return bundlePrice;
	}
	
	

	public static CompletableFuture<List<com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions>> getBundleAndHardwarePromotionsListFromBundleListAsync() {
		CompletableFuture<List<com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions>> future = new CompletableFuture<>();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String promotion = new String(readFile("\\TEST-MOCK\\API_BundlePromotions_ListOfBundlePromotions"));
			BundleAndHardwarePromotions[] promotionList = mapper.readValue(promotion,
					BundleAndHardwarePromotions[].class);

			future.complete(mapper.convertValue(promotionList,
					new TypeReference<List<com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions>>() {
					}));
		} catch (IOException e) {

			e.printStackTrace();
			future.completeExceptionally(e);
		}
		return future;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CommercialProduct getCommercialProductByDeviceIdForAccessory() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(readFile("\\TEST-MOCK\\CommercialProductForAccessory.json"));
			CommercialProduct commercialProductList = mapper.readValue(commercialProduct, CommercialProduct.class);

			return mapper.convertValue(commercialProductList, new TypeReference<CommercialProduct>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<CommercialProduct> getListOfCommercialProductsForAccessory() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(
					readFile("\\TEST-MOCK\\ListOfCommercialProductsForAccessory.json"));
			CommercialProduct[] commercialProductList = mapper.readValue(commercialProduct, CommercialProduct[].class);

			return mapper.convertValue(commercialProductList, new TypeReference<List<CommercialProduct>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<Group> getListOfProductGroupForAccessories() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String listOfGroupsString = new String(
					readFile("\\TEST-MOCK\\ListOfProductGroupsForAccessory.json"));
			Group[] groupList = mapper.readValue(listOfGroupsString, Group[].class);

			return mapper.convertValue(groupList, new TypeReference<List<Group>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static PriceForProduct getPriceForProduct_For_GetAccessories() {
		PriceForProduct navigation = new PriceForProduct();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(readFile("\\TEST-MOCK\\PriceForProductForAccessory.json"));
			PriceForProduct bundleList = mapper.readValue(bundle, PriceForProduct.class);

			return mapper.convertValue(bundleList, new TypeReference<PriceForProduct>() {
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return navigation;
	}

	public static List<ProductGroupModel> getListOfProductGroupMode() {
		List<ProductGroupModel> groupList = new ArrayList<>();
		ProductGroupModel group = new ProductGroupModel();
		group.setId("093353");
		group.setLeadDeviceId("093353");
		group.setUpgradeLeadDeviceId("093353");
		group.setNonUpgradeLeadDeviceId("093353");
		ProductGroupModel group1 = new ProductGroupModel();
		group1.setId("092660");
		group1.setLeadDeviceId(null);
		group1.setListOfVariants(Arrays.asList("092660|1"));
		group.setHexCode(getColourHes());
		group1.setHexCode(getColourHes());
		groupList.add(group);
		groupList.add(group1);
		return groupList;
	}

	public static List<ProductGroupModel> getListOfProductGroupModeForNullLeadPlan() {
		List<ProductGroupModel> groupList = new ArrayList<>();
		ProductGroupModel group = new ProductGroupModel();
		group.setId("093353");
		group.setLeadDeviceId("093353");
		group.setUpgradeLeadDeviceId(null);
		group.setNonUpgradeLeadDeviceId(null);
		ProductGroupModel group1 = new ProductGroupModel();
		group1.setId("092660");
		group1.setLeadDeviceId(null);
		group1.setListOfVariants(Collections.emptyList());
		group.setHexCode(getColourHes());
		group1.setHexCode(getColourHes());
		groupList.add(group);
		groupList.add(group1);
		return groupList;
	}

	public static List<String> getColourHes() {
		List<String> hex = new ArrayList<>();
		hex.add("Black|#212327");
		hex.add("Jet Black|#000000");
		hex.add("Silver|#dcddde");
		hex.add("PRODUCT(RED) Special Edition|#aa2831");
		hex.add("Rose Gold|#efc5bf");
		return hex;
	}

	public static List<FacetField> getListOfFacetField() {
		List<FacetField> facetList = new ArrayList<>();
		List<Count> lcont = new ArrayList<>();
		FacetField facet = new FacetField();
		Count c1 = new Count();
		c1.setName("apple");
		c1.setCount(2);
		lcont.add(c1);
		facet.setName("equipmentMake");
		facet.setValues(lcont);
		FacetField facet1 = new FacetField();
		Count c2 = new Count();
		c2.setName("grey");
		c2.setCount(2);
		lcont.clear();
		lcont.add(c2);
		facet1.setName("facetColour");
		facet1.setValues(lcont);
		facetList.add(facet);
		facetList.add(facet1);
		return facetList;
	}

	public static List<ProductGroupModel> getProductGroupModelForDeliveryMethod() {
		List<ProductGroupModel> productGroupModelList = new ArrayList<ProductGroupModel>();
		ProductGroupModel productGroupModel = new ProductGroupModel();
		productGroupModel.setId("productGroup_1");
		productGroupModel.setImageUrl("http://image.png");
		List<String> variantsList = new ArrayList<String>();
		variantsList.add("093353|1");
		variantsList.add("092660|5");
		productGroupModel.setListOfVariants(variantsList);
		productGroupModel.setName("CompatibleDeliveryMethods_CnC_PremiumDelivery");
		productGroupModel.setPriority(1);
		productGroupModel.setType("Compatible Delivery");
		productGroupModel.setHexCode(getColourHes());
		ProductGroupModel productGroupMode = new ProductGroupModel();
		productGroupMode.setId("productGroup_2");
		productGroupMode.setImageUrl("http://image.png");
		productGroupMode.setListOfVariants(variantsList);
		productGroupMode.setName("CompatibleDeliveryMethods_CnC_Delivery");
		productGroupMode.setPriority(1);
		productGroupMode.setType("Compatible Delivery");
		productGroupMode.setHexCode(getColourHes());
		productGroupModelList.add(productGroupMode);
		productGroupModelList.add(productGroupModel);
		return productGroupModelList;
	}

	public static ProductGroupDetailsForDeviceList getGroupDetails() {
		List<String> size = new ArrayList<>();
		size.add("32 GB");
		size.add("128 GB");
		List<Colour> colour = new ArrayList<>();
		Colour c1 = new Colour();
		c1.setColorName("Black");
		c1.setColorHex("#dcddde");
		Colour c2 = new Colour();
		c2.setColorName("White");
		c2.setColorHex("#efc5bf");
		colour.add(c1);
		colour.add(c2);
		ProductGroupDetailsForDeviceList groupDetails = new ProductGroupDetailsForDeviceList();
		groupDetails.setGroupId("1");
		groupDetails.setGroupName("Apple iPhone 7");
		groupDetails.setColor(colour);
		groupDetails.setSize(size);
		return groupDetails;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<CommercialProduct> getCommercialProductsListOfVariant() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String commercialProduct = new String(readFile("\\TEST-MOCK\\CommercialProductForVariant.json"));
			CommercialProduct[] commercialProductList = mapper.readValue(commercialProduct, CommercialProduct[].class);

			return mapper.convertValue(commercialProductList, new TypeReference<List<CommercialProduct>>() {
			});
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> getDevicePreCalculatedDataFromSolr() {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> deviceList = new ArrayList<>();
		com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData device = new com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData();
		device.setDeviceId("093353");
		device.setRating(2.0F);
		device.setLeadPlanId("110154");
		device.setPaygProductGroupId("084253");
		device.setProductGroupName("GROUP_PAYM");
		device.setIsLeadMember("Y");
		device.setMinimumCost(5.20F);
		device.setLeadDeviceId("093353");
		device.setNonUpgradeLeadDeviceId("093353");
		device.setUpgradeLeadDeviceId("093353");
		device.setNonUpgradeLeadPlanId("110154");
		device.setUpgradeLeadDeviceId("110154");

		List<com.vf.uk.dal.device.model.merchandisingpromotion.Media> listmedia = new ArrayList<>();
		com.vf.uk.dal.device.model.merchandisingpromotion.Media media = new com.vf.uk.dal.device.model.merchandisingpromotion.Media();
		media.setId("hardware_discount.merchandisingPromotions.merchandisingPromotion.label");
		media.setValue("25% off the device");
		media.setType("TEXT&&110588&&HW&&handset.hardware.percentage");
		media.setDescription("NA");
		media.setDiscountId("NA");
		media.setOfferCode("NA");
		media.setPromoCategory("Pricing_Automatic_Discount");

		listmedia.add(media);
		device.setMedia(listmedia);
		com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo price = new com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo();
		com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice bundlePrice = new com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice();
		bundlePrice.setBundleId("110154");
		com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice monthly = new com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice();
		monthly.setGross(12.0F);
		monthly.setNet(18F);
		monthly.setVat(11.5F);
		bundlePrice.setMonthlyDiscountPrice(monthly);
		com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice();
		monthlyPrice.setGross(10.30F);
		monthlyPrice.setNet(12.4F);
		monthlyPrice.setVat(11F);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		price.setBundlePrice(bundlePrice);

		com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice hardware = new com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice();
		hardware.setHardwareId("092660");
		com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice oneOff = new com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice();
		oneOff.setGross(10F);
		oneOff.setNet(11.3F);
		oneOff.setVat(12.5F);
		hardware.setOneOffDiscountPrice(oneOff);
		com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice();
		oneOffPrice.setGross(9F);
		oneOffPrice.setNet(12.8F);
		oneOffPrice.setVat(16.6F);
		hardware.setOneOffPrice(oneOffPrice);
		price.setHardwarePrice(hardware);
		List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> listOfofferAppliedPrice = new ArrayList<>();
		com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails offerAppliedPriceDetails = new com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails();
		offerAppliedPriceDetails.setBundlePrice(bundlePrice);
		offerAppliedPriceDetails.setHardwarePrice(hardware);
		offerAppliedPriceDetails.setJourneyType("Upgrade");
		offerAppliedPriceDetails.setOfferCode("W_HH_OC_01");
		offerAppliedPriceDetails.setDeviceId("093353");
		com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails offerAppliedPriceDetails1 = new com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails();
		offerAppliedPriceDetails1.setBundlePrice(bundlePrice);
		offerAppliedPriceDetails1.setHardwarePrice(hardware);
		offerAppliedPriceDetails1.setJourneyType("Upgrade");
		offerAppliedPriceDetails1.setOfferCode("W_HH_OC_01");
		offerAppliedPriceDetails1.setDeviceId("093353");
		listOfofferAppliedPrice.add(offerAppliedPriceDetails);
		listOfofferAppliedPrice.add(offerAppliedPriceDetails1);
		price.setOfferAppliedPrices(listOfofferAppliedPrice);
		device.setPriceInfo(price);

		com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData device1 = new com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData();
		device1.setDeviceId("093354");
		device1.setRating(2.0F);
		device1.setLeadPlanId("110154");
		device1.setPaymProductGroupId("084253");
		device1.setProductGroupName("GROUP_PAYM");
		device1.setIsLeadMember("Y");
		device1.setMinimumCost(4.20F);
		device1.setLeadDeviceId("093353");
		device1.setNonUpgradeLeadDeviceId("093353");
		device1.setUpgradeLeadDeviceId("093353");
		device1.setNonUpgradeLeadPlanId("110154");
		device1.setUpgradeLeadPlanId("110154");
		device1.setPriceInfo(price);
		device1.setMedia(listmedia);
		deviceList.add(device);
		deviceList.add(device1);
		return deviceList;
	}

	public static List<PriceForBundleAndHardware> getPriceForBundleAndHardwareForCacheDeviceTile() {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<PriceForBundleAndHardware>();

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("110154");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setMpType("limited_discount");
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");

		Price monthlyPrice = new Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");

		Price oneOffPrice = new Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);
		hardwarePrice.setFinancingOptions(getDeviceFinaceOptions1());

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);

		PriceForBundleAndHardware priceInfo1 = new PriceForBundleAndHardware();

		BundlePrice bundlePrice1 = new BundlePrice();
		bundlePrice.setBundleId("110163");
		bundlePrice1.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice1 = new Price();
		monthlyDiscountPrice1.setGross("10.11");
		monthlyDiscountPrice1.setNet("11.23");
		monthlyDiscountPrice1.setVat("14.56");

		Price oneOffDiscountPrice1 = new Price();
		oneOffDiscountPrice1.setGross("9.11");
		oneOffDiscountPrice1.setNet("91.23");
		oneOffDiscountPrice1.setVat("10.56");

		Price monthlyPrice1 = new Price();
		monthlyPrice1.setGross("13.64");
		monthlyPrice1.setNet("12.5");
		monthlyPrice1.setVat("8.56");

		Price oneOffPrice1 = new Price();
		oneOffPrice1.setGross("5.11");
		oneOffPrice1.setNet("9.23");
		oneOffPrice1.setVat("22.56");
		bundlePrice1.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice1.setMonthlyPrice(monthlyPrice);
		priceInfo1.setBundlePrice(bundlePrice1);
		HardwarePrice hardwarePrice1 = new HardwarePrice();
		hardwarePrice1.setHardwareId("093353");
		hardwarePrice1.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice1.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice1.setOneOffPrice(oneOffPrice);
		hardwarePrice1.setFinancingOptions(getDeviceFinaceOptions1());

		priceInfo1.setHardwarePrice(hardwarePrice);
		priceInfo1.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo1.setMonthlyPrice(monthlyPrice);
		priceInfo1.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo1.setOneOffPrice(oneOffPrice);

		priceForBundleAndHardwareList.add(priceInfo);
		return priceForBundleAndHardwareList;
	}

	public static CommercialBundle getCommercialBundleForcacheDevice() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setId("110163");
		bundle.setName("24mth BandO 500min 500MB Standard");
		bundle.setDesc("CTR12 Standard bundle comes with 500 mins, unlimited texts and 500MB of UK data");
		bundle.setPaymentType("postpaid");
		bundle.setDisplayName("Red Bundle");
		Commitment commitment = new Commitment();
		commitment.setPeriod("24 Months");
		bundle.setCommitment(commitment);
		Allowance allowances = new Allowance();
		Allowance roamingAllowances = new Allowance();
		List<Allowance> listOfAllowances = new ArrayList<Allowance>();
		allowances.setType("DATA");
		allowances.setUom("MB");
		allowances.setValue("500.00");
		listOfAllowances.add(allowances);

		List<ServiceProduct> listOfServiceProducts = new ArrayList<ServiceProduct>();
		ServiceProduct serviceProducts = new ServiceProduct();
		serviceProducts.setBundled(true);
		serviceProducts.setId("109155");
		listOfServiceProducts.add(serviceProducts);
		LineRental lineRentals = new LineRental();
		lineRentals.setLineRentalAmount((long) 200);
		lineRentals.setLineRentalProductId("122");
		List<LineRental> listOfLineRentals = new ArrayList<LineRental>();
		listOfLineRentals.add(lineRentals);
		serviceProducts.setLineRentals(listOfLineRentals);
		bundle.setServiceProducts(listOfServiceProducts);
		roamingAllowances.setType("UK Text");
		roamingAllowances.setUom("MONTHS");
		roamingAllowances.setValue("2000.00");
		listOfAllowances.add(roamingAllowances);
		bundle.setAllowances(listOfAllowances);
		List<String> listOfPromoteAs = new ArrayList<>();
		com.vf.uk.dal.device.client.entity.bundle.PromoteAs promoteAs = new com.vf.uk.dal.device.client.entity.bundle.PromoteAs();
		listOfPromoteAs.add("EXTRA.1GB.DATA");
		listOfPromoteAs.add("W_HH_PAYM_OC_01");
		promoteAs.setPromotionName(listOfPromoteAs);
		bundle.setPromoteAs(promoteAs);
		ImageURL imageURL = new ImageURL();
		imageURL.setImageName("Left_Thumb");
		imageURL.setImageURL("http://Url");
		List<ImageURL> listOfImageUrl = new ArrayList<ImageURL>();
		listOfImageUrl.add(imageURL);
		bundle.setListOfimageURLs(listOfImageUrl);

		MediaURL mediaURL = new MediaURL();
		mediaURL.setMediaName("Right_Front");
		mediaURL.setMediaURL("http://media");
		List<MediaURL> listOfMediaUrl = new ArrayList<MediaURL>();
		listOfMediaUrl.add(mediaURL);
		// bundle.setListOfmediaURLs(listOfMediaUrl);

		bundle.setRecurringCharge(21.3f);

		List<DevicePrice> listOfDevicePrice = new ArrayList<DevicePrice>();
		DevicePrice devicePrice = new DevicePrice();
		devicePrice.setDeviceId("093353");
		devicePrice.setPriceGross((float) 14.09);
		devicePrice.setPriceNet((float) 13.4);
		devicePrice.setPriceVAT((float) 12);
		devicePrice.setProductLine("Product Line");
		listOfDevicePrice.add(devicePrice);
		bundle.setDeviceSpecificPricing(listOfDevicePrice);
		List<String> productLines = new ArrayList<String>();
		productLines.add("Mobile Phone Service Sellable");
		productLines.add("asdfg");
		productLines.add("oiut");
		bundle.setProductLines(productLines);

		Availability availability = new Availability();
		availability.setEnd((Date.valueOf("2019-03-30")));
		availability.setSalesExpired(false);
		availability.setStart((Date.valueOf("2018-01-05")));
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundleControl.setDisplayableRet(true);
		bundleControl.setSellableRet(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}

	public static CommercialBundle getCommercialBundleForMAkeAndModel() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setId("183099");
		bundle.setName("24mth BandO 500min 500MB Standard");
		bundle.setDesc("CTR12 Standard bundle comes with 500 mins, unlimited texts and 500MB of UK data");
		bundle.setPaymentType("postpaid");
		bundle.setDisplayName("Red Bundle");
		Commitment commitment = new Commitment();
		commitment.setPeriod("24 Months");
		bundle.setCommitment(commitment);
		Allowance allowances = new Allowance();
		Allowance roamingAllowances = new Allowance();
		List<Allowance> listOfAllowances = new ArrayList<Allowance>();
		allowances.setType("DATA");
		allowances.setUom("MB");
		allowances.setValue("500.00");
		listOfAllowances.add(allowances);

		List<ServiceProduct> listOfServiceProducts = new ArrayList<ServiceProduct>();
		ServiceProduct serviceProducts = new ServiceProduct();
		serviceProducts.setBundled(true);
		serviceProducts.setId("109154");
		listOfServiceProducts.add(serviceProducts);
		LineRental lineRentals = new LineRental();
		lineRentals.setLineRentalAmount((long) 200);
		lineRentals.setLineRentalProductId("122");
		List<LineRental> listOfLineRentals = new ArrayList<LineRental>();
		listOfLineRentals.add(lineRentals);
		serviceProducts.setLineRentals(listOfLineRentals);
		bundle.setServiceProducts(listOfServiceProducts);
		roamingAllowances.setType("UK Text");
		roamingAllowances.setUom("MONTHS");
		roamingAllowances.setValue("2000.00");
		listOfAllowances.add(roamingAllowances);
		bundle.setAllowances(listOfAllowances);
		List<String> listOfPromoteAs = new ArrayList<>();
		com.vf.uk.dal.device.client.entity.bundle.PromoteAs promoteAs = new com.vf.uk.dal.device.client.entity.bundle.PromoteAs();
		listOfPromoteAs.add("EXTRA.1GB.DATA");
		listOfPromoteAs.add("W_HH_PAYM_OC_01");
		promoteAs.setPromotionName(listOfPromoteAs);
		bundle.setPromoteAs(promoteAs);
		ImageURL imageURL = new ImageURL();
		imageURL.setImageName("Left_Thumb");
		imageURL.setImageURL("http://Url");
		List<ImageURL> listOfImageUrl = new ArrayList<ImageURL>();
		listOfImageUrl.add(imageURL);
		bundle.setListOfimageURLs(listOfImageUrl);

		MediaURL mediaURL = new MediaURL();
		mediaURL.setMediaName("Right_Front");
		mediaURL.setMediaURL("http://media");
		List<MediaURL> listOfMediaUrl = new ArrayList<MediaURL>();
		listOfMediaUrl.add(mediaURL);

		bundle.setRecurringCharge(21.3f);

		List<DevicePrice> listOfDevicePrice = new ArrayList<DevicePrice>();
		DevicePrice devicePrice = new DevicePrice();
		devicePrice.setDeviceId("93353");
		devicePrice.setPriceGross((float) 14.09);
		devicePrice.setPriceNet((float) 13.4);
		devicePrice.setPriceVAT((float) 12);
		devicePrice.setProductLine("Product Line");
		listOfDevicePrice.add(devicePrice);
		bundle.setDeviceSpecificPricing(listOfDevicePrice);
		List<String> productLines = new ArrayList<String>();
		productLines.add("Mobile Phone Service Sellable");
		productLines.add("asdfg");
		productLines.add("oiut");
		bundle.setProductLines(productLines);

		Availability availability = new Availability();
		availability.setEnd((Date.valueOf("2019-03-30")));
		availability.setSalesExpired(false);
		availability.setStart((Date.valueOf("2018-01-05")));
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundleControl.setDisplayableRet(true);
		bundleControl.setSellableRet(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}

	public static List<OfferAppliedPriceDetails> getOfferAppliedPriceDetails() {
		List<OfferAppliedPriceDetails> listOfofferAppliedPrice = new ArrayList<>();
		com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice = new com.vf.uk.dal.device.model.solr.BundlePrice();
		bundlePrice.setBundleId("110154");
		com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice monthly = new com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice();
		monthly.setGross("12.0");
		monthly.setNet("18");
		monthly.setVat("11.5");
		bundlePrice.setMonthlyDiscountPrice(monthly);
		com.vf.uk.dal.device.model.solr.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.model.solr.MonthlyPrice();
		monthlyPrice.setGross("10.30");
		monthlyPrice.setNet("12.4");
		monthlyPrice.setVat("11");
		bundlePrice.setMonthlyPrice(monthlyPrice);

		com.vf.uk.dal.device.model.solr.HardwarePrice hardware = new com.vf.uk.dal.device.model.solr.HardwarePrice();
		hardware.setHardwareId("092660");
		com.vf.uk.dal.device.model.solr.OneOffDiscountPrice oneOff = new com.vf.uk.dal.device.model.solr.OneOffDiscountPrice();
		oneOff.setGross("10");
		oneOff.setNet("11.3");
		oneOff.setVat("12.5");
		hardware.setOneOffDiscountPrice(oneOff);
		com.vf.uk.dal.device.model.solr.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.model.solr.OneOffPrice();
		oneOffPrice.setGross("9");
		oneOffPrice.setNet("12.8");
		oneOffPrice.setVat("16.6");
		hardware.setOneOffPrice(oneOffPrice);
		OfferAppliedPriceDetails offerAppliedPriceDetails = new OfferAppliedPriceDetails();
		offerAppliedPriceDetails.setBundlePrice(bundlePrice);
		offerAppliedPriceDetails.setHardwarePrice(hardware);
		offerAppliedPriceDetails.setJourneyType("Upgrade");
		offerAppliedPriceDetails.setOfferCode("W_HH_OC_01");
		offerAppliedPriceDetails.setDeviceId("093353");
		OfferAppliedPriceDetails offerAppliedPriceDetails1 = new OfferAppliedPriceDetails();
		offerAppliedPriceDetails1.setBundlePrice(bundlePrice);
		offerAppliedPriceDetails1.setHardwarePrice(hardware);
		offerAppliedPriceDetails1.setJourneyType("Upgrade");
		offerAppliedPriceDetails1.setOfferCode("W_HH_OC_01");
		offerAppliedPriceDetails1.setDeviceId("093353");
		listOfofferAppliedPrice.add(offerAppliedPriceDetails);
		listOfofferAppliedPrice.add(offerAppliedPriceDetails1);
		return listOfofferAppliedPrice;
	}

	public static com.vf.uk.dal.device.model.solr.PriceInfo getPriceinforForSorl() {
		com.vf.uk.dal.device.model.solr.PriceInfo priceInfo = new com.vf.uk.dal.device.model.solr.PriceInfo();
		com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice = new com.vf.uk.dal.device.model.solr.BundlePrice();
		bundlePrice.setBundleId("110154");
		com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice monthly = new com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice();
		monthly.setGross("12.0");
		monthly.setNet("18");
		monthly.setVat("11.5");
		bundlePrice.setMonthlyDiscountPrice(monthly);
		com.vf.uk.dal.device.model.solr.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.model.solr.MonthlyPrice();
		monthlyPrice.setGross("10.30");
		monthlyPrice.setNet("12.4");
		monthlyPrice.setVat("11");
		bundlePrice.setMonthlyPrice(monthlyPrice);

		com.vf.uk.dal.device.model.solr.HardwarePrice hardware = new com.vf.uk.dal.device.model.solr.HardwarePrice();
		hardware.setHardwareId("092660");
		com.vf.uk.dal.device.model.solr.OneOffDiscountPrice oneOff = new com.vf.uk.dal.device.model.solr.OneOffDiscountPrice();
		oneOff.setGross("10");
		oneOff.setNet("11.3");
		oneOff.setVat("12.5");
		hardware.setOneOffDiscountPrice(oneOff);
		com.vf.uk.dal.device.model.solr.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.model.solr.OneOffPrice();
		oneOffPrice.setGross("9");
		oneOffPrice.setNet("12.8");
		oneOffPrice.setVat("16.6");
		hardware.setOneOffPrice(oneOffPrice);
		priceInfo.setBundlePrice(bundlePrice);
		priceInfo.setHardwarePrice(hardware);
		priceInfo.setOfferAppliedPrices(getOfferAppliedPriceDetails());
		return priceInfo;
	}

	public static List<com.vf.uk.dal.device.model.solr.Media> getmediaForSorl() {
		List<com.vf.uk.dal.device.model.solr.Media> listOfMedia = new ArrayList<>();
		com.vf.uk.dal.device.model.solr.Media media = new com.vf.uk.dal.device.model.solr.Media();
		media.setDescription("description");
		media.setDiscountId("discountId");
		media.setId("id");
		media.setOfferCode("offerCode");
		media.setPromoCategory("promoCategory");
		media.setType("type");
		media.setValue("value");
		listOfMedia.add(media);
		return listOfMedia;
	}

	public static Device getDevice() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		mpp.setBundlePromotions(null);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDevice_One() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper mpw = new MerchandisingPromotionsWrapper();
		mpw.setPricePromotion(null);
		mpp.setBundlePromotions(mpw);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDevice_Two() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper mpw = new MerchandisingPromotionsWrapper();
		MerchandisingPromotion mp = new MerchandisingPromotion();
		mp.setTag(null);
		mpw.setPricePromotion(mp);
		mpp.setBundlePromotions(mpw);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDeviceHW() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		mpp.setHardwarePromotions(null);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDeviceHW_One() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper mpw = new MerchandisingPromotionsWrapper();
		mpw.setPricePromotion(null);
		mpp.setHardwarePromotions(mpw);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDeviceHW_Two() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper mpw = new MerchandisingPromotionsWrapper();
		MerchandisingPromotion mp = new MerchandisingPromotion();
		mp.setTag(null);
		mpw.setPricePromotion(mp);
		mpp.setHardwarePromotions(mpw);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDeviceWithDetails_Two() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper mpw = new MerchandisingPromotionsWrapper();
		MerchandisingPromotion mp = new MerchandisingPromotion();
		mp.setTag("1234");
		mpw.setPricePromotion(mp);
		mpp.setHardwarePromotions(mpw);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static Device getDeviceWithDetails_One() {
		Device device = new Device();
		MerchandisingPromotionsPackage mpp = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper mpw = new MerchandisingPromotionsWrapper();
		MerchandisingPromotion mp = new MerchandisingPromotion();
		mp.setTag("1234");
		mpw.setPricePromotion(mp);
		mpp.setBundlePromotions(mpw);
		device.setPromotionsPackage(mpp);
		return device;
	}

	public static com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion getMP() {
		com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion merchandisingPromotions = new com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setPriceEstablishedLabel("WAS");
		merchandisingPromotions.setDescription("3 months free data for e more");
		merchandisingPromotions.setMpType("full_duration");
		return merchandisingPromotions;
	}

	public static List<BundleModel> getBundleModelListForBundleListForDeviceList() {

		List<BundleModel> bundleModelList = new ArrayList<BundleModel>();

		BundleModel bundleModel = new BundleModel();
		bundleModel.setBundleId("109373");
		bundleModel.setName("RedBundle");
		bundleModel.setDesc("This Bundle is valid for 11 months");
		bundleModel.setPaymentType("postPay");
		bundleModel.setRecurringCharge((float) 2.5);
		bundleModel.setUkMinutesUOM("months");
		bundleModel.setUkMinutesValue("10");
		bundleModel.setUkDataUOM("months");
		bundleModel.setUkDataValue("2");
		bundleModel.setUkTextsUOM("Months");
		bundleModel.setUkTextsValue("500");
		bundleModel.setEuropeanRoamingDataUOM("Months");
		bundleModel.setEuropeanRoamingDataValue("12");
		bundleModel.setEuropeanRoamingMinutesUOM("Months");
		bundleModel.setEuropeanRoamingMinutesValue("10");
		bundleModel.setEuropeanRoamingTextsUOM("Months");
		bundleModel.setEuropeanRoamingTextsValue("9");
		bundleModel.setEuropeanRoamingPictureMessagesUOM("Months");
		bundleModel.setEuropeanRoamingPictureMessagesValue("9");
		bundleModel.setInternationalTextsUOM("Months");
		bundleModel.setInternationalTextsValue("10");
		bundleModel.setMonthlyGrossPrice(10F);
		bundleModel.setMonthlyNetPrice(8F);
		bundleModel.setMonthlyVatPrice(2F);
		bundleModel.setMonthlyDiscountedGrossPrice(8F);
		bundleModel.setMonthlyDiscountedNetPrice(6F);
		bundleModel.setMonthlyDiscountedVatPrice(2F);
		List<String> promoteAs = new ArrayList<>();
		promoteAs.add("CTRL110165TO110154");
		bundleModel.setPromoteAs(promoteAs);
		BundleModel bundleModel1 = new BundleModel();
		bundleModel1.setBundleId("109372");
		bundleModel1.setName("RedBundle");
		bundleModel1.setDesc("This Bundle is valid for 11 months");
		bundleModel1.setPaymentType("postPay");
		bundleModel1.setRecurringCharge((float) 2.5);
		bundleModel1.setUkMinutesUOM("months");
		bundleModel1.setUkMinutesValue("10");
		bundleModel1.setUkDataUOM("months");
		bundleModel1.setUkDataValue("2");
		bundleModel1.setUkTextsUOM("Months");
		bundleModel1.setUkTextsValue("500");
		bundleModel1.setEuropeanRoamingDataUOM("Months");
		bundleModel1.setEuropeanRoamingDataValue("12");
		bundleModel1.setEuropeanRoamingMinutesUOM("Months");
		bundleModel1.setEuropeanRoamingMinutesValue("10");
		bundleModel1.setEuropeanRoamingTextsUOM("Months");
		bundleModel1.setEuropeanRoamingTextsValue("9");
		bundleModel1.setEuropeanRoamingPictureMessagesUOM("Months");
		bundleModel1.setEuropeanRoamingPictureMessagesValue("9");
		bundleModel1.setInternationalTextsUOM("Months");
		bundleModel1.setInternationalTextsValue("10");
		bundleModel1.setMonthlyGrossPrice(10F);
		bundleModel1.setMonthlyNetPrice(8F);
		bundleModel1.setMonthlyVatPrice(2F);
		bundleModel1.setMonthlyDiscountedGrossPrice(10F);
		bundleModel1.setMonthlyDiscountedNetPrice(8F);
		bundleModel1.setMonthlyDiscountedVatPrice(2F);
		bundleModelList.add(bundleModel);
		bundleModelList.add(bundleModel1);
		return bundleModelList;

	}

	public static List<PriceForBundleAndHardware> getForUtilityPriceForBundleAndHardware() {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<>();

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setMpType("limited_discount");
		merchandisingPromotions.setPriceEstablishedLabel("priceEstablishedLabel");
		merchandisingPromotions.setPromotionMedia("promotionMedia");
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");

		Price monthlyPrice = new Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");

		Price oneOffPrice = new Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");

		Price monthlyDiscountPrice1 = new Price();
		monthlyDiscountPrice1.setGross("10.11");
		monthlyDiscountPrice1.setNet("11.23");
		monthlyDiscountPrice1.setVat("14.56");

		Price oneOffDiscountPrice1 = new Price();
		oneOffDiscountPrice1.setGross("9.11");
		oneOffDiscountPrice1.setNet("91.23");
		oneOffDiscountPrice1.setVat("10.56");

		Price monthlyPrice1 = new Price();
		monthlyPrice1.setGross("13.64");
		monthlyPrice1.setNet("12.5");
		monthlyPrice1.setVat("8.56");

		Price oneOffPrice1 = new Price();
		oneOffPrice1.setGross("5.11");
		oneOffPrice1.setNet("9.23");
		oneOffPrice1.setVat("22.56");
		MerchandisingPromotion merchandisingPromotions1 = new MerchandisingPromotion();
		merchandisingPromotions1.setDiscountId("107531");
		merchandisingPromotions1.setLabel("20% off with any handset");
		merchandisingPromotions1.setTag("AllPhone.full.2017");
		merchandisingPromotions1.setDescription("description");
		merchandisingPromotions1.setMpType("limited_discount");
		merchandisingPromotions1.setPriceEstablishedLabel("priceEstablishedLabel");
		merchandisingPromotions1.setPromotionMedia("promotionMedia");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions1);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice1);
		hardwarePrice.setOneOffPrice(oneOffPrice1);
		hardwarePrice.setFinancingOptions(getDeviceFinaceOptions2());

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice1);
		priceInfo.setMonthlyPrice(monthlyPrice1);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice1);
		priceInfo.setOneOffPrice(oneOffPrice1);
		List<StepPricingInfo> stepPricesList = new ArrayList<>();
		StepPricingInfo stePrices = new StepPricingInfo();

		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount();
		discount.setSkuId("93353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		// stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		priceForBundleAndHardwareList.add(priceInfo);
		return priceForBundleAndHardwareList;
	}

	public static List<PriceForBundleAndHardware> getPriceForBundleAndHardwareSorting() {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<PriceForBundleAndHardware>();

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion merchandisingPromotions = new com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion();

		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("W_HH_OC_02");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setPriceEstablishedLabel("priceEstablishedLabel");
		merchandisingPromotions.setPromotionMedia("promotionMedia");
		merchandisingPromotions.setMpType(CONDITIONAL_FULL_DISCOUNT);
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		com.vf.uk.dal.device.client.entity.price.Price monthlyDiscountPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyDiscountPrice.setGross("13.11");
		monthlyDiscountPrice.setNet("12.23");
		monthlyDiscountPrice.setVat("1.56");

		Price oneOffDiscountPrice = new Price();
		oneOffDiscountPrice.setGross("8.11");
		oneOffDiscountPrice.setNet("7.23");
		oneOffDiscountPrice.setVat("1.56");

		com.vf.uk.dal.device.client.entity.price.Price monthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyPrice.setGross("12.64");
		monthlyPrice.setNet("11.5");
		monthlyPrice.setVat("1.56");

		com.vf.uk.dal.device.client.entity.price.Price oneOffPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		oneOffPrice.setGross("15.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("6.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);
		hardwarePrice.setFinancingOptions(getDeviceFinaceOptions1());

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);
		List<com.vf.uk.dal.device.client.entity.price.StepPricingInfo> stepPricesList = new ArrayList<com.vf.uk.dal.device.client.entity.price.StepPricingInfo>();
		com.vf.uk.dal.device.client.entity.price.StepPricingInfo stePrices = new com.vf.uk.dal.device.client.entity.price.StepPricingInfo();

		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount();
		discount.setSkuId("093353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		// stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		priceForBundleAndHardwareList.add(priceInfo);
		priceForBundleAndHardwareList.addAll(getOfferAppliedPrice());
		return priceForBundleAndHardwareList;
	}

	public static List<DeviceFinancingOption> getDeviceFinaceOptions() {
		List<DeviceFinancingOption> financeOptions = new ArrayList<>();
		DeviceFinancingOption finance = new DeviceFinancingOption();
		finance.setApr("16.9");
		finance.setDeviceFinancingId("OVCTYUVZRR");
		finance.setFinanceProvider("PayPal");
		finance.setFinanceTerm("24");
		com.vf.uk.dal.device.model.product.Price deviceMonthlyPrice = new com.vf.uk.dal.device.model.product.Price();
		deviceMonthlyPrice.setGross("14.41");
		deviceMonthlyPrice.setNet("12");
		deviceMonthlyPrice.setVat("2.40");
		finance.setMonthlyPrice(deviceMonthlyPrice);
		com.vf.uk.dal.device.model.product.Price totalPriceWithInterest = new com.vf.uk.dal.device.model.product.Price();
		totalPriceWithInterest.setGross("345.68");
		totalPriceWithInterest.setNet("288.07");
		totalPriceWithInterest.setVat("57.61");
		finance.setTotalPriceWithInterest(totalPriceWithInterest);
		financeOptions.add(finance);
		return financeOptions;
	}

	public static List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> getDeviceFinaceOptions1() {
		List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> financeOptions = new ArrayList<>();
		com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption finance = new com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption();
		finance.setApr("16.9");
		finance.setDeviceFinancingId("OVCTYUVZRR");
		finance.setFinanceProvider("PayPal");
		finance.setFinanceTerm("24");
		com.vf.uk.dal.device.client.entity.price.Price deviceMonthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		deviceMonthlyPrice.setGross("14.41");
		deviceMonthlyPrice.setNet("12");
		deviceMonthlyPrice.setVat("2.40");
		finance.setMonthlyPrice(deviceMonthlyPrice);
		com.vf.uk.dal.device.client.entity.price.Price totalPriceWithInterest = new com.vf.uk.dal.device.client.entity.price.Price();
		totalPriceWithInterest.setGross("345.68");
		totalPriceWithInterest.setNet("288.07");
		totalPriceWithInterest.setVat("57.61");
		finance.setTotalPriceWithInterest(totalPriceWithInterest);
		financeOptions.add(finance);
		return financeOptions;
	}

	public static List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> getDeviceFinaceOptions2() {
		List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> financeOptions = new ArrayList<>();
		com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption finance = new com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption();
		finance.setApr("16.9");
		finance.setDeviceFinancingId("OVCTYUVZRR");
		finance.setFinanceProvider("PayPal");
		finance.setFinanceTerm("24");
		com.vf.uk.dal.device.client.entity.price.Price deviceMonthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		deviceMonthlyPrice.setGross("14.41");
		deviceMonthlyPrice.setNet("12");
		deviceMonthlyPrice.setVat("2.40");
		finance.setMonthlyPrice(deviceMonthlyPrice);
		com.vf.uk.dal.device.client.entity.price.Price totalPriceWithInterest = new com.vf.uk.dal.device.client.entity.price.Price();
		totalPriceWithInterest.setGross("345.68");
		totalPriceWithInterest.setNet("288.07");
		totalPriceWithInterest.setVat("57.61");
		finance.setTotalPriceWithInterest(totalPriceWithInterest);
		financeOptions.add(finance);
		return financeOptions;
	}

	public static DeviceTile getDeviceTilee() {

		DeviceTile deviceTile = null;
		deviceTile = new DeviceTile();

		List<DeviceSummary> deviceSummaryList = new ArrayList<DeviceSummary>();
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setColourHex("D10000000");
		deviceSummary.setColourName("Grey");
		deviceSummary.setDisplayDescription("5.5 inch");
		deviceSummary.setDisplayName("display name");
		deviceSummary.setDeviceId(String.valueOf(122));
		deviceSummary.setLeadPlanId("Lead plan Id");
		deviceSummary.setLeadPlanDisplayName("Yearly Plan");
		deviceSummary.setMemory("64GB");
		deviceSummary.setPreOrderable(false);
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		deviceSummary.setMerchandisingMedia(merchandisingMedia);

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();

		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("9.11");
		monthlyDiscountPrice.setNet("91.23");
		monthlyDiscountPrice.setVat("10.56");

		Price monthlyPrice = new Price();
		monthlyDiscountPrice.setGross("13.64");
		monthlyDiscountPrice.setNet("12.5");
		monthlyDiscountPrice.setVat("8.56");

		Price oneOffPrice = new Price();
		monthlyDiscountPrice.setGross("5.11");
		monthlyDiscountPrice.setNet("9.23");
		monthlyDiscountPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("Hardware Id");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);

		List<StepPricingInfo> stepPricesList = new ArrayList<StepPricingInfo>();
		StepPricingInfo stePrices = new StepPricingInfo();

		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		deviceSummary.setPriceInfo(priceInfo);
		deviceSummaryList.add(deviceSummary);
		deviceTile.setDeviceSummary(deviceSummaryList);
		deviceTile.setGroupName("Apple iPhone 6s");
		deviceTile.setGroupType("DEVICE");
		deviceTile.setDeviceId(String.valueOf(93353));
		return deviceTile;
	}

	public static List<DeviceSummary> getDeviceSummary() {
		List<DeviceSummary> deviceSummaryList = new ArrayList<DeviceSummary>();
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setColourHex("D10000000");
		deviceSummary.setColourName("Grey");
		deviceSummary.setDisplayDescription("5.5 inch");
		deviceSummary.setDisplayName("display name");
		deviceSummary.setDeviceId(String.valueOf(122));
		deviceSummary.setLeadPlanId("Lead plan Id");
		deviceSummary.setLeadPlanDisplayName("Yearly Plan");
		deviceSummary.setMemory("64GB");
		deviceSummary.setPreOrderable(false);
		deviceSummary.setIsAffordable(true);
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		deviceSummary.setMerchandisingMedia(merchandisingMedia);

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();

		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price oneOffDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("9.11");
		monthlyDiscountPrice.setNet("91.23");
		monthlyDiscountPrice.setVat("10.56");

		Price monthlyPrice = new Price();
		monthlyDiscountPrice.setGross("13.64");
		monthlyDiscountPrice.setNet("12.5");
		monthlyDiscountPrice.setVat("8.56");

		Price oneOffPrice = new Price();
		monthlyDiscountPrice.setGross("5.11");
		monthlyDiscountPrice.setNet("9.23");
		monthlyDiscountPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setBundlePrice(bundlePrice);
		HardwarePrice hardwarePrice = new HardwarePrice();
		hardwarePrice.setHardwareId("Hardware Id");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);

		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);

		List<StepPricingInfo> stepPricesList = new ArrayList<StepPricingInfo>();
		StepPricingInfo stePrices = new StepPricingInfo();

		com.vf.uk.dal.device.client.entity.price.Duration duration = new com.vf.uk.dal.device.client.entity.price.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		deviceSummary.setPriceInfo(priceInfo);
		deviceSummaryList.add(deviceSummary);
		return deviceSummaryList;
	}

	public static List<BundleAndHardwareTuple> getBundleAndHardwareTuple() {
		List<BundleAndHardwareTuple>  bhTuple = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId("108245");
		bundleAndHardwareTuple.setHardwareId("093353");
		bhTuple.add(bundleAndHardwareTuple);
		return bhTuple;
	}
	public static BundleAndHardwareTuple getBundleAndHardwareTuplee() {
		BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId("108245");
		bundleAndHardwareTuple.setHardwareId("093353");
		return bundleAndHardwareTuple;
	}

	public static List<com.vf.uk.dal.device.model.Member> getMemberListPojo() {
		List<com.vf.uk.dal.device.model.Member> memberList = new ArrayList<>();
		com.vf.uk.dal.device.model.Member member = new com.vf.uk.dal.device.model.Member();
		member.setId("123");
		member.setPriority("12");
		memberList.add(member);
		return memberList;
	}
	public static Group getGroupp() {
		Group group = new Group();
		group.setVersion("1.0");
		group.setGroupPriority((long) 3);
		group.setGroupType("DEVICE");
		Group group1 = new Group();
		group1.setVersion("1.0");
		group1.setGroupPriority((long) 3);
		group1.setGroupType(STRING_ACCESSORY);
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 2);
		Member member1 = new Member();
		member1.setId("124");
		member1.setPriority((long) 1);
		Member member2 = new Member();
		member2.setId("093329");
		member2.setPriority((long) 1);
		memberList.add(member2);
		memberList.add(member1);
		memberList.add(member);
		group.setMembers(memberList);
		group.setName("Apple iPhone 6s");
		group.setVersion("1.0");
		group1.setMembers(memberList);
		group1.setName("Apple iPhone 6s cover");
		group1.setVersion("1.0");

		return group;
	}
}