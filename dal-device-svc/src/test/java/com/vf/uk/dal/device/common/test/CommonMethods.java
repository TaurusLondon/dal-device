package com.vf.uk.dal.device.common.test;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.response.FacetField;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vf.uk.dal.common.registry.client.Utility;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceList;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.Discount;
import com.vf.uk.dal.device.entity.Facet;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.HardwarePrice;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Make;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.MerchandisingControl;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.MetaData;
import com.vf.uk.dal.device.entity.OfferPacks;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.entity.Specification;
import com.vf.uk.dal.device.entity.SpecificationGroup;
import com.vf.uk.dal.device.entity.StepPricingInfo;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.BundleHeader;
import com.vf.uk.dal.utility.entity.ExtraPrice;
import com.vf.uk.dal.utility.entity.PriceForAccessory;
import com.vf.uk.dal.utility.entity.PriceForExtra;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.entity.StockInfo;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.Media;
import com.vf.uk.dal.utility.solr.entity.OneOffDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.OneOffPrice;
import com.vf.uk.dal.utility.solr.entity.PriceInfo;
import com.vodafone.dal.bundle.pojo.Allowance;
import com.vodafone.dal.bundle.pojo.Availability;
import com.vodafone.dal.bundle.pojo.BundleControl;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.dal.bundle.pojo.Commitment;
import com.vodafone.dal.bundle.pojo.DevicePrice;
import com.vodafone.dal.bundle.pojo.ImageURL;
import com.vodafone.dal.bundle.pojo.LineRental;
import com.vodafone.dal.bundle.pojo.ServiceProduct;
import com.vodafone.dal.domain.bazaarvoice.BazaarVoice;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.product.pojo.Duration;
import com.vodafone.product.pojo.Equipment;
import com.vodafone.product.pojo.MediaURL;
import com.vodafone.product.pojo.PriceDetail;
import com.vodafone.product.pojo.ProductAvailability;
import com.vodafone.product.pojo.ProductControl;
import com.vodafone.product.pojo.ProductGroups;
import com.vodafone.product.pojo.PromoteAs;
import com.vodafone.productGroups.pojo.Group;
import com.vodafone.productGroups.pojo.Member;
import com.vodafone.solrmodels.BundleModel;
import com.vodafone.solrmodels.MerchandisingPromotionModel;
import com.vodafone.solrmodels.OfferAppliedPriceModel;
import com.vodafone.solrmodels.ProductGroupFacetModel;
import com.vodafone.solrmodels.ProductGroupModel;
import com.vodafone.solrmodels.ProductModel;
import com.vodafone.stockAvailability.pojo.StockAvailability;

public class CommonMethods {
	public static Timestamp timeStamp;
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
	/*
	 * public static ProductGroupFacetModel getProductGroupFacet(){
	 * ProductGroupFacetModel productGroup=new ProductGroupFacetModel();
	 * productGroup.set }
	 */

	public static List<CommercialProduct> getListofdataPayGCoherence() {
		List<CommercialProduct> ls = new ArrayList<>();

		CommercialProduct commercialProduct = new CommercialProduct();
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setPriceGross(new Double(10));
		priceDetail.setPriceNet(new Double(10));
		priceDetail.setPriceVAT(new Double(10));
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setId("088417");
		ls.add(commercialProduct);

		return ls;
	}

	public static List<ProductModel> getProductModel() {
		List<ProductModel> productModelList = new ArrayList<>();
		ProductModel model = new ProductModel();
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
		List<String> merchedn=new ArrayList<>();
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Automatic_Discount|NA|For 3 Months, then|095597");
		
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Discount|W_HH_OC_02|For 3 Months, then|095597");
		
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&HW&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.label|40% off the phone|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel|WAS|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		merchedn.add("hardware_discount.merchandisingPromotions.merchandisingPromotion.description|For 3 Months, then|TEXT&&110154&&BP&&handset.conditional.full.GBP|Pricing_Upgrade_Discount|NA|For 3 Months, then|095597");
		
		
		model.setMerchandisingMedia(merchedn);
		//List<String>
		
		ProductModel model1 = new ProductModel();
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
		productModelList.add(model);
		productModelList.add(model1);
		return productModelList;
	}

	public static List<DevicePreCalculatedData> getDevicePreCal() {
		List<DevicePreCalculatedData> deviceList = new ArrayList<>();
		DevicePreCalculatedData device = new DevicePreCalculatedData();
		device.setDeviceId("093353");
		device.setLeadPlanId("092660");
		device.setProductGroupId("084253");
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
		com.vf.uk.dal.utility.solr.entity.BundlePrice bundlePrice = new com.vf.uk.dal.utility.solr.entity.BundlePrice();
		bundlePrice.setBundleId("110154");
		com.vf.uk.dal.utility.solr.entity.MonthlyDiscountPrice monthly = new com.vf.uk.dal.utility.solr.entity.MonthlyDiscountPrice();
		monthly.setGross("12.0");
		monthly.setNet("18");
		monthly.setVat("11.5");
		bundlePrice.setMonthlyDiscountPrice(monthly);
		com.vf.uk.dal.utility.solr.entity.MonthlyPrice monthlyPrice = new com.vf.uk.dal.utility.solr.entity.MonthlyPrice();
		monthlyPrice.setGross("10.3");
		monthlyPrice.setNet("12.4");
		monthlyPrice.setVat("11");
		bundlePrice.setMonthlyPrice(monthlyPrice);
		price.setBundlePrice(bundlePrice);

		com.vf.uk.dal.utility.solr.entity.HardwarePrice hardware = new com.vf.uk.dal.utility.solr.entity.HardwarePrice();
		hardware.setHardwareId("092660");
		com.vf.uk.dal.utility.solr.entity.OneOffDiscountPrice oneOff = new OneOffDiscountPrice();
		oneOff.setGross("10");
		oneOff.setNet("11.3");
		oneOff.setVat("12.5");
		hardware.setOneOffDiscountPrice(oneOff);
		com.vf.uk.dal.utility.solr.entity.OneOffPrice oneOffPrice = new OneOffPrice();
		oneOffPrice.setGross("9");
		oneOffPrice.setNet("12.8");
		oneOffPrice.setVat("16.6");
		hardware.setOneOffPrice(oneOffPrice);
		price.setHardwarePrice(hardware);

		device.setPriceInfo(price);
		deviceList.add(device);
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

	public static List<DeviceList> getDeviceList(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize) {
		List<DeviceList> deviceLists = new ArrayList<>();
		DeviceList deviceList = new DeviceList();
		deviceList.setDescription("Description");
		deviceList.setDeviceId("93353");
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		merchandisingMedia.add(mediaLink);
		deviceList.setMedia(merchandisingMedia);
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
		deviceList.setMerchandisingControl(merchandisingControl);

		deviceList.setPriceInfo(getPriceForBundleAndHardware().get(0));
		deviceList.setProductClass("ProductClass");
		deviceList.setRating(4);
		deviceLists.add(deviceList);
		return deviceLists;
	}

	public static List<DevicePreCalculatedData> getDevicePreCalculatedData() {
		DevicePreCalculatedData deviceListing = new DevicePreCalculatedData();
		List<DevicePreCalculatedData> deviceListingList = new ArrayList<DevicePreCalculatedData>();
		deviceListing.setDeviceId("093353");
		;
		deviceListing.setLeadPlanId("110154");
		deviceListing.setProductGroupId("productGroupId");
		deviceListing.setProductGroupName("productGroupName");
		deviceListing.setRating(Float.valueOf("2.0"));
		List<Media> listmedia = new ArrayList<>();
		Media media = new Media();
		media.setId("imagesURLs.full.right");
		media.setValue("www.vodafone.co.uk/cs/groups/public/documents/images/imageurls.full.right.png");
		media.setType("URL");
		listmedia.add(media);
		deviceListing.setMedia(listmedia);
		deviceListingList.add(deviceListing);
		return deviceListingList;
	}

	public static List<StockInfo> getStockInfo() {
		StockInfo info = new StockInfo();
		info.setAvailableBy("Available");
		info.setQuantity(2);
		info.setSkuId("109272");
		info.setSourceId("110154");
		info.setStatus("Progress");
		List<StockInfo> infoList = new ArrayList<>();
		infoList.add(info);
		return infoList;
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
		/*
		 * merchandisingPromotions.setPromotionName("Promotion Name");
		 * merchandisingPromotions1.setPromotionName("Promotion Name1");
		 */
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

		com.vf.uk.dal.device.entity.Duration duration = new com.vf.uk.dal.device.entity.Duration();
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

	public static List<ProductGroup> getProductGroupByGroupTypeGroupName(String groupType, String groupName) {
		List<ProductGroup> productgroupList = new ArrayList<ProductGroup>();
		ProductGroup productGroup = null;
		productGroup = new ProductGroup();
		productGroup.setGroupPriority("1");
		productGroup.setGroupType("DEVICE");
		productGroup.setGroupName("Apple iPhone 6s");
		List<com.vf.uk.dal.device.entity.Member> member = new ArrayList<com.vf.uk.dal.device.entity.Member>();
		com.vf.uk.dal.device.entity.Member productGroupMember = new com.vf.uk.dal.device.entity.Member();
		productGroupMember.setPriority("1");
		productGroupMember.setId("83833");
		member.add(productGroupMember);
		productgroupList.add(productGroup);
		return productgroupList;
	}

	public static List<DeviceTile> getDeviceTileById(String id) {
		List<DeviceTile> deviceTileList = null;
		deviceTileList = new ArrayList<DeviceTile>();

		DeviceTile deviceTile = null;
		deviceTile = new DeviceTile();

		deviceTile.setGroupName("Apple iPhone 6s");
		deviceTile.setGroupType("DEVICE");
		deviceTile.setDeviceId(id);
		List<DeviceSummary> deviceSummaryList = new ArrayList<DeviceSummary>();
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setColourHex("D10000000");
		deviceSummary.setColourName("Grey");
		deviceSummary.setDisplayDescription("5.5 inch");
		deviceSummary.setDisplayName("display name");
		deviceSummary.setDeviceId(String.valueOf(93427));
		deviceSummary.setLeadPlanId("Lead plan Id");
		deviceSummary.setLeadPlanDisplayName("Yearly Plan");
		deviceSummary.setMemory("64GB");
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		deviceSummary.setMerchandisingMedia(merchandisingMedia);
		Price Mothlyprice = new Price();
		Mothlyprice.setGross("7367");
		Mothlyprice.setNet("78565");
		Mothlyprice.setVat("74567");
		Price oneOffprice = new Price();
		oneOffprice.setGross("674");
		oneOffprice.setNet("2536");
		oneOffprice.setVat("1385");
		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		/*
		 * merchandisingPromotions.setPromotionName("Promotion Name");
		 * merchandisingPromotions1.setPromotionName("Promotion Name1");
		 */
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

		List<UUID> discountSkuIdsList = new ArrayList<UUID>();
		UUID uuid = new UUID((long) 74, (long) 53);
		UUID uuid1 = new UUID((long) 43, (long) 21);
		discountSkuIdsList.add(uuid);
		discountSkuIdsList.add(uuid1);
		// stePrices.setDiscountsApplicable(discountSkuIdsList);
		com.vf.uk.dal.device.entity.Duration duration = new com.vf.uk.dal.device.entity.Duration();
		duration.setUom("UOM");
		duration.setValue("124");
		stePrices.setDuration(duration);
		stePrices.setMonthlyPrice(monthlyPrice);
		stePrices.setOneOffPrice(oneOffPrice);
		stePrices.setSequence("Sequence");
		priceInfo.setStepPrices(stepPricesList);
		deviceSummary.setPriceInfo(priceInfo);
		deviceSummary.setPriority("Medium");
		deviceSummaryList.add(deviceSummary);
		deviceTile.setDeviceSummary(deviceSummaryList);
		deviceTileList.add(deviceTile);
		return deviceTileList;
	}

	public static List<CommercialProduct> getCommercialProductWithNullLeadPlanID()
	{
		List<CommercialProduct> list= new ArrayList<>();
		CommercialProduct commercialProduct = getCommercialProduct();
		commercialProduct.setLeadPlanId(null);
		list.add(commercialProduct);
		list.add(getCommercialProduct1());
		list.add(getCommercialProduct2());
		list.add(getCommercialProduct3());
		list.add(getCommercialProduct5());
		return list;
	}
	public static CommercialProduct getCommercialProduct() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("093353");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
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
		List<com.vodafone.productGroups.pojo.Member> productGroupMember = new ArrayList<com.vodafone.productGroups.pojo.Member>();
		com.vodafone.productGroups.pojo.Member productMember = new Member();
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
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 2);
		Member member1 = new Member();
		member1.setId("124");
		member1.setPriority((long) 1);
		memberList.add(member1);
		memberList.add(member);
		group.setMembers(memberList);
		group.setName("Apple iPhone 6s");
		group.setVersion("1.0");
		groupList.add(group);

		return groupList;
	}

	public static CommercialBundle getCommercialBundle() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setBundleID("110154");
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
		List<String> listOfPromoteAs=new ArrayList<>();
		com.vodafone.dal.bundle.pojo.PromoteAs promoteAs = new com.vodafone.dal.bundle.pojo.PromoteAs();
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
		availability.setStart((Date.valueOf("2019-01-05")));
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}

	public static CommercialProduct getCommercialProduct1() {
		CommercialProduct commercialProduct = new CommercialProduct();

		commercialProduct.setProductClass("pClass");
		commercialProduct.setId("93353");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
		priceDetail.setPriceGross((double) 64);
		priceDetail.setPriceNet((double) 54);
		priceDetail.setPriceVAT((double) 24);
		commercialProduct.setPriceDetail(priceDetail);
		commercialProduct.setOrder((long) 64);
		PromoteAs promoteAs = new PromoteAs();
		List<String> promotionName = new ArrayList<String>();
		promotionName.add("qwerty");
		promotionName.add("asdfg");
		promoteAs.setPromotionName(promotionName);
		commercialProduct.setPromoteAs(promoteAs);

		ProductControl productControl = new ProductControl();
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");

		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
		specification2.setName("HexValue");
		specification2.setPriority((long) 1);
		specification2.setValue("#E5000");
		specification2.setValueType("");
		specification2.setValueUOM("");

		specificationList.add(specification1);
		specificationList.add(specification2);
		specificationList.add(specification);
		gr.setSpecifications(specificationList);
		group.setSpecifications(specificationList);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		return commercialProduct;
	}

	public static CommercialBundle getCommercialBundle1() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setBundleID("109154");
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
		availability.setEnd(null);
		availability.setSalesExpired(false);
		availability.setStart(null);
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}

	public static CommercialBundle getCommercialBundle2() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setBundleID("109154");
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
		availability.setStart(null);
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}

	public static CommercialBundle getCommercialBundle3() {
		CommercialBundle bundle = new CommercialBundle();
		bundle.setBundleID("109154");
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
		availability.setEnd(null);
		availability.setSalesExpired(false);
		availability.setStart((Date.valueOf("2017-03-05")));
		bundle.setAvailability(availability);
		BundleControl bundleControl = new BundleControl();
		bundleControl.setDisplayableAcq(true);
		bundleControl.setSellableAcq(true);
		bundle.setBundleControl(bundleControl);
		return bundle;
	}

	public static List<AccessoryTileGroup> getAccessoriesTileGroup(String deviceId){
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
		//List<MerchandisingPromotion> merchandisingPromotionsList = new ArrayList<MerchandisingPromotion>();
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		//MerchandisingPromotion merchandisingPromotions1 = new MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setMpType("limited_discount");
		/*merchandisingPromotions1.setDiscountId("107526");
		merchandisingPromotions1.setLabel("9 months, 15% off, Any Handset");
		merchandisingPromotions1.setTag("AllPhone.limit.2017");*/
		//merchandisingPromotionsList.add(merchandisingPromotions);
		//merchandisingPromotionsList.add(merchandisingPromotions1);
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
		hardwarePrice.setHardwareId("93353");
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
		com.vf.uk.dal.device.entity.Duration duration = new com.vf.uk.dal.device.entity.Duration();
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

	public static List<OfferPacks> getListOfOfferPacks() {
		OfferPacks offerPacks = new OfferPacks();
		List<OfferPacks> listOfOfferPacks = new ArrayList<OfferPacks>();
		offerPacks.setBundleId("109154");
		List<MediaLink> listOfMediaLink = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("SIMO.ENTERTAINMENT.2017");
		mediaLink.setType("TEXT");
		mediaLink.setValue("URL");
		listOfMediaLink.add(mediaLink);
		offerPacks.setMediaLinkList(listOfMediaLink);
		listOfOfferPacks.add(offerPacks);
		return listOfOfferPacks;

	}

	public static List<Device> getDevice(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize) {
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
		deviceList.setDeviceId("93353");
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
		return deviceLists;
	}

	public static Facet getFacet() {
		List<Make> makes = new ArrayList<>();
		Make make = new Make();
		make.setName("Apple");
		make.setCount(1);
		makes.add(make);
		Facet facet = new Facet();
		facet.setEquipmentMake("equipmentMake");
		facet.setMakeList(makes);

		return facet;
	}

	public static FacetedDevice getFacetedDevice(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String journeyId) {
		FacetedDevice facetedDevice = new FacetedDevice();
		facetedDevice.setDevice(getDevice(productClass, make, model, groupType, sortCriteria, pageNumber, pageSize));
		//facetedDevice.setFacet(getFacet());

		return facetedDevice;
	}

	public static FacetedDevice getFacetedDeviceList(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize, String journeyId) {
		FacetedDevice facetedDevice = new FacetedDevice();
		facetedDevice.setDevice(getDeviceListForFaceting(productClass, make, model, groupType, sortCriteria, pageNumber,
				pageSize, journeyId));
		//facetedDevice.setFacet(getFacet());

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

	public static Insurances getInsurances(String deviceId) {
		if (deviceId.equals("93353")) {
			Insurances insurances = new Insurances();
			List<Specification> specifications = new ArrayList<>();
			Specification specification = new Specification();
			specification.setComparable(false);
			specification.setDescription("description");
			specification.setFootNote("footNote");
			specification.setIsKey(false);
			specification.setName("name");
			specification.setPriority(0);
			specification.setValue("value");
			specification.setValueType("valueType");
			specification.setValueUOM("GB");
			specifications.add(specification);

			List<SpecificationGroup> specsGroup = new ArrayList<>();
			SpecificationGroup specificationGroup = new SpecificationGroup();
			specificationGroup.setComparable(false);
			specificationGroup.setGroupName("groupName");
			specificationGroup.setPriority(0);
			specificationGroup.setSpecifications(specifications);
			specsGroup.add(specificationGroup);
			Price price = new Price();
			price.setGross("10.11");
			price.setNet("11.23");
			price.setVat("14.56");
			List<Insurance> insuranceList = new ArrayList<>();
			Insurance insurance = new Insurance();
			insurance.setId("93353");
			insurance.setName("name");
			insurance.setPrice(price);
			insurance.setSpecsGroup(specsGroup);
			insuranceList.add(insurance);
			insurances.setInsuranceList(insuranceList);
			insurances.setMinCost("5");
			return insurances;
		} else {
			return null;
		}
	}

	public static List<StockInfo> getListOfStockInfo() {
		List<StockInfo> listOfStockInfo = new ArrayList<>();
		StockInfo stockInfo;

		stockInfo = new StockInfo();
		stockInfo.setAvailableBy("AvailableBy");
		stockInfo.setQuantity(1);
		stockInfo.setSkuId("12345");
		stockInfo.setSourceId("741852");
		stockInfo.setStatus("On");
		listOfStockInfo.add(stockInfo);

		stockInfo = new StockInfo();
		stockInfo.setAvailableBy("AvailableBy");
		stockInfo.setQuantity(1);
		stockInfo.setSkuId("68522");
		stockInfo.setSourceId("5456464");
		stockInfo.setStatus("On");
		listOfStockInfo.add(stockInfo);

		stockInfo = new StockInfo();
		stockInfo.setAvailableBy("AvailableBy");
		stockInfo.setQuantity(1);
		stockInfo.setSkuId("857894");
		stockInfo.setSourceId("98494156");
		stockInfo.setStatus("On");
		listOfStockInfo.add(stockInfo);

		stockInfo = new StockInfo();
		stockInfo.setAvailableBy("AvailableBy");
		stockInfo.setQuantity(1);
		stockInfo.setSkuId("9684216");
		stockInfo.setSourceId("231354");
		stockInfo.setStatus("On");
		listOfStockInfo.add(stockInfo);

		stockInfo = new StockInfo();
		stockInfo.setAvailableBy("AvailableBy");
		stockInfo.setQuantity(1);
		stockInfo.setSkuId("96849");
		stockInfo.setSourceId("63415489");
		stockInfo.setStatus("On");
		listOfStockInfo.add(stockInfo);

		stockInfo = new StockInfo();
		stockInfo.setAvailableBy("AvailableBy");
		stockInfo.setQuantity(1);
		stockInfo.setSkuId("5849821");
		stockInfo.setSourceId("6849654");
		stockInfo.setStatus("On");
		listOfStockInfo.add(stockInfo);

		return listOfStockInfo;
	}

	public static BundleDetails getCompatibleBundleListJson() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			bundleDetails = mapper.readValue(Utility.readFile("\\TEST-MOCK\\BUNDLESV1_compatibleList.json"), BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson3() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			bundleDetails = mapper.readValue(Utility.readFile("\\TEST-MOCK\\BUNDLESV1_compatibleList3.json"), BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson4() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			bundleDetails = mapper.readValue(Utility.readFile("\\TEST-MOCK\\\\BUNDLESV2_compatibleList.json"), BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson5() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			bundleDetails = mapper.readValue(Utility.readFile("\\TEST-MOCK\\BUNDLESV3_compatibleList.json"), BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static ProductGroupFacetModel getProductGroupFacetModel1() {
		ProductGroupFacetModel product = new ProductGroupFacetModel();
		List<FacetField> faceList = new ArrayList<>();
		FacetField facet = new FacetField("EquipmentMake");
		facet.insert("EquipmentMake", 2);
		FacetField facet1 = new FacetField("Colour");
		facet1.insert("Color", 2);
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
		return product;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static ProductGroupFacetModel getProductGroupFacetModel() {
		ProductGroupFacetModel bundleDetails = new ProductGroupFacetModel();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			bundleDetails = mapper.readValue(Utility.readFile("\\TEST-MOCK\\ProductGroupForFacets.json"),
					ProductGroupFacetModel.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static BundleDetails getCompatibleBundleListJson1() {
		BundleDetails bundleDetails = new BundleDetails();
		try {
			ObjectMapper mapper = new ObjectMapper();
			bundleDetails = mapper.readValue(Utility.readFile("\\TEST-MOCK\\BUNDLESV1_compatibleList1.json"), BundleDetails.class);
			return bundleDetails;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleDetails;
	}

	public static List<Device> getDevice1(String productClass, String make, String model, String groupType,
			String sortCriteria, int pageNumber, int pageSize) {
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
		deviceList.setDeviceId("088274");
		deviceList.setMake("Apple");
		deviceList.setModel("iphone7");
		deviceList.setGroupType("DEVICE_PAYM");
		deviceList.setRating("4");
		deviceList.setDescription("Description");
		deviceList.setProductClass("HANDSET");
		deviceList.setMerchandisingControl(merchandisingControl);
		deviceList.setMedia(merchandisingMedia);
		deviceList.setPriceInfo(getPriceForBundleAndHardware().get(0));

		List<MediaLink> merchandisingMedia1 = new ArrayList<MediaLink>();
		MediaLink mediaLink1 = new MediaLink();
		mediaLink1.setId("1022");
		mediaLink1.setType("JPEG");
		mediaLink1.setValue("283");
		merchandisingMedia1.add(mediaLink1);

		MerchandisingControl merchandisingControl1 = new MerchandisingControl();
		merchandisingControl1.setAvailableFrom("Available From");
		merchandisingControl1.setBackorderable(true);
		merchandisingControl1.setIsDisplayableAcq(true);
		merchandisingControl1.setIsDisplayableECare(false);
		merchandisingControl1.setIsDisplayableRet(true);
		merchandisingControl1.setIsDisplayableSavedBasket(true);
		merchandisingControl1.setIsSellableAcq(true);
		merchandisingControl1.setIsSellableECare(false);
		merchandisingControl1.setIsSellableRet(true);
		merchandisingControl1.setOrder(2);
		merchandisingControl1.setPreorderable(true);

		Device deviceList1 = new Device();
		deviceList1.setDeviceId("091232");
		deviceList1.setMake("Apple");
		deviceList1.setModel("iphone7");
		deviceList1.setGroupType("DEVICE_PAYM");
		deviceList1.setRating("4");
		deviceList1.setDescription("Description");
		deviceList1.setProductClass("HANDSET");
		deviceList1.setMerchandisingControl(merchandisingControl);
		deviceList1.setMedia(merchandisingMedia);
		deviceList1.setPriceInfo(getPriceForBundleAndHardware().get(0));
		deviceLists.add(deviceList);
		deviceLists.add(deviceList1);
		return deviceLists;
	}

	public static FacetedDevice getFacetedDeviceForSorting(String productClass, String make, String model,
			String groupType, String sortCriteria, int pageNumber, int pageSize, String journeyId) {
		FacetedDevice facetedDevice = new FacetedDevice();
		facetedDevice.setDevice(getDevice1(productClass, make, model, groupType, sortCriteria, pageNumber, pageSize));
		//facetedDevice.setFacet(getFacet());

		return facetedDevice;
	}

	public static List<BundleHeader> getBundleHeaderList(String bundleClass) {
		if (bundleClass.equals("SIMO")) {
			List<BundleHeader> bundleHeaderList = new ArrayList<BundleHeader>();
			BundleHeader bundleHeader = new BundleHeader();
			List<com.vf.uk.dal.utility.entity.MediaLink> mediaLinkList = new ArrayList<>();
			com.vf.uk.dal.utility.entity.MediaLink mediaLink = new com.vf.uk.dal.utility.entity.MediaLink();
			mediaLink.setId("SIMO.JAN.2017");
			mediaLink.setType("MMS");
			mediaLink.setValue("URL");
			mediaLinkList.add(mediaLink);
			List<com.vf.uk.dal.utility.entity.BundleAllowance> bundleAllowanceList = new ArrayList<>();
			com.vf.uk.dal.utility.entity.BundleAllowance bundleAllowance = new com.vf.uk.dal.utility.entity.BundleAllowance();
			bundleAllowance.setType("Red Bundle");
			bundleAllowance.setUom("Months");
			bundleAllowance.setValue("10Gb");
			bundleAllowanceList.add(bundleAllowance);
			com.vf.uk.dal.utility.entity.Price price = new com.vf.uk.dal.utility.entity.Price();
			price.setGross("22");
			price.setNet("20");
			price.setVat("2");
			com.vf.uk.dal.utility.entity.BundlePrice bundlePrice = new com.vf.uk.dal.utility.entity.BundlePrice();
			bundlePrice.setBundleId("110154");
			bundlePrice.setMonthlyDiscountPrice(price);
			bundlePrice.setMonthlyPrice(price);
			com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = new com.vf.uk.dal.utility.entity.HardwarePrice();
			hardwarePrice.setHardwareId("093353");
			hardwarePrice.setOneOffPrice(price);
			hardwarePrice.setOneOffDiscountPrice(price);
			com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceInfo = new com.vf.uk.dal.utility.entity.PriceForBundleAndHardware();
			priceInfo.setBundlePrice(bundlePrice);
			priceInfo.setMonthlyPrice(price);
			priceInfo.setOneOffPrice(price);
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
			bundleHeaderList.add(bundleHeader);
			return bundleHeaderList;
		} else {
			return null;
		}
	}

	// Utkarsh - getting queryParams Map
	public static Map<String, String> getQueryParamsMap(String... arguments) {
		Map<String, String> queryparams = new HashMap<String, String>();
		if (arguments == null)
			return queryparams;
		if (arguments.length == 3) {
			queryparams.put("make", arguments[0]);
			queryparams.put("model", arguments[1]);
			queryparams.put("groupType", arguments[2]);
		} else if (arguments.length == 10) {
			queryparams.put("make", arguments[0]);
			queryparams.put("model", arguments[1]);
			queryparams.put("groupType", arguments[2]);
			queryparams.put("productClass", arguments[3]);
			queryparams.put("capacity", arguments[4]);
			queryparams.put("colour", arguments[5]);
			queryparams.put("operatingSystem", arguments[6]);
			queryparams.put("mustHaveFeatures", arguments[7]);
			//queryparams.put("journeyId", arguments[8]);
			queryparams.put("journeyType", arguments[8]);
			queryparams.put("offerCode", arguments[9]);
		} else if (arguments.length == 1) {
			queryparams.put("deviceId", arguments[0]);
		}
		return queryparams;
	}

	public static Map<String, String> getInvalidQueryParamsMap(String... arguments) {
		Map<String, String> queryparams = new HashMap<String, String>();
		if (arguments == null)
			return queryparams;
		if (arguments.length == 3) {
			queryparams.put("productClass", arguments[0]);
			queryparams.put("capacity", arguments[1]);
			queryparams.put("colour", arguments[2]);
		} else if (arguments.length == 10) {
			queryparams.put("deviceId", arguments[0]);
			queryparams.put("model", arguments[1]);
			queryparams.put("groupType", arguments[2]);
			queryparams.put("productClass", arguments[3]);
			queryparams.put("capacity", arguments[4]);
			queryparams.put("colour", arguments[5]);
			queryparams.put("operatingSystem", arguments[6]);
			queryparams.put("mustHaveFeatures", arguments[7]);
			//queryparams.put("journeyId", arguments[8]);
			queryparams.put("journeyType", arguments[8]);
			queryparams.put("offerCode", arguments[9]);
		} else if (arguments.length == 1) {
			queryparams.put("operatingSystem", arguments[0]);
		}
		return queryparams;
	}

	public static com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMemPro() {
		com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion mem = new com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion();
		mem.setTag("handset.limited.GBP");
		mem.setType("full_duration");
		return mem;
	}

	/*
	 * public static List<BazaarVoice> getReviewsJsonObject() throws
	 * IOException, ParseException{ ObjectMapper mapper = new ObjectMapper();
	 * mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	 * false);
	 * mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	 * String productModel=new
	 * String(Utility.readFile("\\BazaarVoiceResponse.json")); BazaarVoice
	 * product=mapper.readValue(productModel, BazaarVoice.class);
	 * List<BazaarVoice> bazaarList=new ArrayList<>(); bazaarList.add(product);
	 * return bazaarList; }
	 */
	public static List<BazaarVoice> getReviewsJsonObject() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String productModel = new String(Utility.readFile("\\TEST-MOCK\\BazaarVoiceResponse.json"));
		List<BazaarVoice> bazaarList = new ArrayList<>();
		BazaarVoice bazaar = new BazaarVoice();
		bazaar.setJsonsource(productModel);
		bazaarList.add(bazaar);
		return bazaarList;
	}

	public static StockAvailability getStockAvailability1() {
		StockAvailability cohbundle = new StockAvailability();
		String jsonString = "";
		try {
			jsonString = new String(Utility.readFile("\\rest-mock\\UTILITY-V1.json"));
			Gson gson = new Gson();
			cohbundle = gson.fromJson(jsonString, StockAvailability.class);
		} catch (Exception e) {
			System.out.println(e);
		}
		return cohbundle;
	}

	public static Map<String, String> getleadMemberMap() {
		Map<String, String> leadMemberMap = new HashMap<>();
		leadMemberMap.put("093353", "Y");
		return leadMemberMap;
	}
	public static List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> getPrice()
	{
		List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> priceForBundleAndHardwareList=new ArrayList<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>();
		
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceInfo=new com.vf.uk.dal.utility.entity.PriceForBundleAndHardware();
		
		com.vf.uk.dal.utility.entity.BundlePrice bundlePrice=new com.vf.uk.dal.utility.entity.BundlePrice();
		bundlePrice.setBundleId("183099");
		//List<com.vf.uk.dal.utility.entity.MerchandisingPromotion> merchandisingPromotionsList=new ArrayList<com.vf.uk.dal.utility.entity.MerchandisingPromotion>();
		com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotions=new com.vf.uk.dal.utility.entity.MerchandisingPromotion();
		//com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotions1=new com.vf.uk.dal.utility.entity.MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setPriceEstablishedLabel("WAS");
		merchandisingPromotions.setDescription("3 months free data for e more");
		merchandisingPromotions.setMpType("full_duration");
		/*merchandisingPromotions1.setDiscountId("107526");
		merchandisingPromotions1.setLabel("9 months, 15% off, Any Handset");
		merchandisingPromotions1.setTag("AllPhone.limit.2017");*/
	//	merchandisingPromotionsList.add(merchandisingPromotions);
		//merchandisingPromotionsList.add(merchandisingPromotions1);
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		com.vf.uk.dal.utility.entity.Price monthlyDiscountPrice=new com.vf.uk.dal.utility.entity.Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");
		
		com.vf.uk.dal.utility.entity.Price oneOffDiscountPrice=new com.vf.uk.dal.utility.entity.Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");
		
		com.vf.uk.dal.utility.entity.Price monthlyPrice=new com.vf.uk.dal.utility.entity.Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");
		
		com.vf.uk.dal.utility.entity.Price oneOffPrice=new com.vf.uk.dal.utility.entity.Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice=new com.vf.uk.dal.utility.entity.HardwarePrice();
		hardwarePrice.setHardwareId("93353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);
		
		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);
		List<com.vf.uk.dal.utility.entity.StepPricingInfo> stepPricesList=new ArrayList<com.vf.uk.dal.utility.entity.StepPricingInfo>();
		com.vf.uk.dal.utility.entity.StepPricingInfo stePrices=new com.vf.uk.dal.utility.entity.StepPricingInfo();
		
		List<Discount> discountList=new ArrayList<Discount>();
		Discount discount=new Discount();
		discount.setSkuId("93353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		//stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.utility.entity.Duration duration=new com.vf.uk.dal.utility.entity.Duration();
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
	public static Map<String, String> getQueryParamsMapForDeviceDetails(String... arguments) {
		Map<String, String> queryparams = new HashMap<String, String>();
		if (arguments == null)
			return queryparams;
		if (arguments.length == 1) {
			queryparams.put("deviceId", arguments[0]);
		}else if (arguments.length == 2) {
			queryparams.put("deviceId", null);
		}
		return queryparams;
	}
	public static CommercialProduct getCommercialProduct2() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("093353");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		commercialProduct.setIsDeviceProduct(true);;
		commercialProduct.setProductClass(Constants.STRING_HANDSET);
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}
	public static com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchPromotion() {
		com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion mem = new com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion();
		mem.setTag("EXTRA.1GB.DATA");
		mem.setType("entertainment");
		return mem;
	}
	public static com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchPromotion1() {
		com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion mem = new com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion();
		mem.setTag("qwerty");
		mem.setType("entertainment");
		return mem;
	}
	public static CommercialProduct getCommercialProduct3() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId(null);
		commercialProduct.setId("093311");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		commercialProduct.setIsDeviceProduct(true);;
		commercialProduct.setProductClass(Constants.STRING_HANDSET);
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}
	public static List<PriceForBundleAndHardware> getPriceForBundleAndHardware1() {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareList = new ArrayList<PriceForBundleAndHardware>();

		PriceForBundleAndHardware priceInfo = new PriceForBundleAndHardware();

		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		//List<MerchandisingPromotion> merchandisingPromotionsList = new ArrayList<MerchandisingPromotion>();
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		//MerchandisingPromotion merchandisingPromotions1 = new MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		/*merchandisingPromotions1.setDiscountId("107526");
		merchandisingPromotions1.setLabel("9 months, 15% off, Any Handset");
		merchandisingPromotions1.setTag("AllPhone.limit.2017");
		merchandisingPromotionsList.add(merchandisingPromotions);
		merchandisingPromotionsList.add(merchandisingPromotions1);*/
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
		hardwarePrice.setHardwareId("93353");
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
		discount.setSkuId("093311");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		// stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.device.entity.Duration duration = new com.vf.uk.dal.device.entity.Duration();
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
	public static CommercialProduct getCommercialProduct5() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("123");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}
	
	public static CacheDeviceTileResponse getCacheDeviceTileResponse(){
		
		CacheDeviceTileResponse cacheDeviceTileResponse = new CacheDeviceTileResponse();
		cacheDeviceTileResponse.setJobId("1234");
		cacheDeviceTileResponse.setJobStatus("Success");
		return cacheDeviceTileResponse;
		
	}
	
	
	public static List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> getOfferAppliedPrice()
	{
		List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> priceForBundleAndHardwareList=new ArrayList<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>();
		
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceInfo=new com.vf.uk.dal.utility.entity.PriceForBundleAndHardware();
		
		com.vf.uk.dal.utility.entity.BundlePrice bundlePrice=new com.vf.uk.dal.utility.entity.BundlePrice();
		bundlePrice.setBundleId("183099");
		//List<com.vf.uk.dal.utility.entity.MerchandisingPromotion> merchandisingPromotionsList=new ArrayList<com.vf.uk.dal.utility.entity.MerchandisingPromotion>();
		com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotions=new com.vf.uk.dal.utility.entity.MerchandisingPromotion();
		//com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotions1=new com.vf.uk.dal.utility.entity.MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("W_HH_OC_02");
		/*merchandisingPromotions1.setDiscountId("107526");
		merchandisingPromotions1.setLabel("9 months, 15% off, Any Handset");
		merchandisingPromotions1.setTag("W_HH_SIMONLY_01");
		merchandisingPromotionsList.add(merchandisingPromotions);
		merchandisingPromotionsList.add(merchandisingPromotions1);*/
		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		com.vf.uk.dal.utility.entity.Price monthlyDiscountPrice=new com.vf.uk.dal.utility.entity.Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");
		
		com.vf.uk.dal.utility.entity.Price oneOffDiscountPrice=new com.vf.uk.dal.utility.entity.Price();
		oneOffDiscountPrice.setGross("9.11");
		oneOffDiscountPrice.setNet("91.23");
		oneOffDiscountPrice.setVat("10.56");
		
		com.vf.uk.dal.utility.entity.Price monthlyPrice=new com.vf.uk.dal.utility.entity.Price();
		monthlyPrice.setGross("13.64");
		monthlyPrice.setNet("12.5");
		monthlyPrice.setVat("8.56");
		
		com.vf.uk.dal.utility.entity.Price oneOffPrice=new com.vf.uk.dal.utility.entity.Price();
		oneOffPrice.setGross("5.11");
		oneOffPrice.setNet("9.23");
		oneOffPrice.setVat("22.56");
		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		priceInfo.setBundlePrice(bundlePrice);
		com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice=new com.vf.uk.dal.utility.entity.HardwarePrice();
		hardwarePrice.setHardwareId("93353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotions);
		hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
		hardwarePrice.setOneOffPrice(oneOffPrice);
		
		priceInfo.setHardwarePrice(hardwarePrice);
		priceInfo.setMonthlyDiscountPrice(monthlyDiscountPrice);
		priceInfo.setMonthlyPrice(monthlyPrice);
		priceInfo.setOneOffDiscountPrice(monthlyDiscountPrice);
		priceInfo.setOneOffPrice(oneOffPrice);
		List<com.vf.uk.dal.utility.entity.StepPricingInfo> stepPricesList=new ArrayList<com.vf.uk.dal.utility.entity.StepPricingInfo>();
		com.vf.uk.dal.utility.entity.StepPricingInfo stePrices=new com.vf.uk.dal.utility.entity.StepPricingInfo();
		
		List<Discount> discountList=new ArrayList<Discount>();
		Discount discount=new Discount();
		discount.setSkuId("93353");
		discount.setTag("AllPhone.limit.2017");
		discountList.add(discount);
		//stePrices.setDiscountsApplicable(discountList);
		com.vf.uk.dal.utility.entity.Duration duration=new com.vf.uk.dal.utility.entity.Duration();
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
		List<String> listOfCompatiblePlan=new ArrayList<>();
		listOfCompatiblePlan.add("110154");
		listOfCompatiblePlan.add("110166");
		listOfCompatiblePlan.add("110156");
		commercialProduct.setListOfCompatiblePlanIds(listOfCompatiblePlan);
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
		discount.setType("Percentage");
		discount.setAmount(10.20);
		commercialProduct.setDiscount(discount);
		commercialProduct.setDuration(duration);
		commercialProduct.setProductClass("Handset");
		return commercialProduct;
	}
	
	public static CommercialProduct getCommercialProductForCacheDeviceTile1() {
		CommercialProduct commercialProduct = new CommercialProduct();

		// commercialProduct.setProductClass("pClass");
		commercialProduct.setLeadPlanId("110154");
		commercialProduct.setId("124");
		commercialProduct.setPreDesc("");
		commercialProduct.setDisplayName("asbd");
		List<String> listOfCompatiblePlan=new ArrayList<>();
		listOfCompatiblePlan.add("110154");
		listOfCompatiblePlan.add("110166");
		listOfCompatiblePlan.add("110156");
		commercialProduct.setListOfCompatiblePlanIds(listOfCompatiblePlan);
		PriceDetail priceDetail = new PriceDetail();
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);

		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
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
		priceDetail.setIsInvoiceChargeable(true);
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
		productControl.setIsDisplayableinLife(true);
		productControl.setIsSellableinLife(true);
		productControl.setIsDisplayableAcq(true);
		productControl.setIsSellableRet(true);
		productControl.setIsDisplayableRet(true);
		productControl.setIsSellableAcq(true);
		productControl.setIsDisplayableSavedBasket(true);
		productControl.setOrder((long) 754);
		productControl.setPreOrderable(true);
		timeStamp=new Timestamp(Date.valueOf("2003-09-05").getTime());
		productControl.setAvailableFrom(Date.valueOf("2003-09-05"));
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
		ProductGroups pgs=new ProductGroups();
		List<com.vodafone.product.pojo.ProductGroup> listOfProductGroup= new ArrayList<>();
		com.vodafone.product.pojo.ProductGroup  pg=new com.vodafone.product.pojo.ProductGroup();
		pg.setProductGroupRole("Compatible Insurance");
		pg.setProductGroupName("DEVICE_PAYM");
		listOfProductGroup.add(pg);
		pgs.setProductGroup(listOfProductGroup);
		commercialProduct.setProductGroups(pgs);
		List<com.vodafone.product.pojo.Group> specificationGroupsList = new ArrayList<com.vodafone.product.pojo.Group>();
		com.vodafone.product.pojo.Group gr = new com.vodafone.product.pojo.Group();
		com.vodafone.product.pojo.Group group = new com.vodafone.product.pojo.Group();
		group.setComparable(true);
		group.setGroupName("Capacity");
		group.setPriority((long) 1);
		gr.setComparable(true);
		gr.setGroupName("Colour");
		gr.setPriority((long) 1);
		List<com.vodafone.product.pojo.Specification> specificationList = new ArrayList<com.vodafone.product.pojo.Specification>();
		com.vodafone.product.pojo.Specification specification = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification1 = new com.vodafone.product.pojo.Specification();
		com.vodafone.product.pojo.Specification specification2 = new com.vodafone.product.pojo.Specification();
		specification.setComparable(true);
		specification.setKey(true);
		specification.setName("Colour");
		specification.setPriority((long) 1);
		specification.setValue("Red");
		specification.setValueType("");
		specification.setValueUOM("");
		specification1.setComparable(true);
		specification1.setKey(true);
		specification1.setName("Capacity");
		specification1.setPriority((long) 1);
		specification1.setValue("60");
		specification1.setValueType("");
		specification1.setValueUOM("GB");

		specification2.setComparable(true);
		specification2.setKey(true);
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

		List<com.vodafone.product.pojo.ImageURL> listOfimageURLs = new ArrayList<com.vodafone.product.pojo.ImageURL>();
		com.vodafone.product.pojo.ImageURL imageURL = new com.vodafone.product.pojo.ImageURL();
		imageURL.setImageName("images.left");
		imageURL.setImageURL("URL");
		listOfimageURLs.add(imageURL);
		commercialProduct.setListOfimageURLs(listOfimageURLs);

		Duration duration = new Duration();
		duration.setStarts("Januray");
		duration.setUOM("MB");
		duration.setValue("30");
		commercialProduct.setDuration(duration);
		com.vodafone.product.pojo.Discount discount = new com.vodafone.product.pojo.Discount();
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
	public static com.vodafone.productGroups.pojo.Group getGropuFromProductGroups()
	{
		com.vodafone.productGroups.pojo.Group gr=new com.vodafone.productGroups.pojo.Group();
		List<com.vodafone.productGroups.pojo.Member> listOfM1=new ArrayList<>();
		com.vodafone.productGroups.pojo.Member m1=new com.vodafone.productGroups.pojo.Member();
		m1.setId("093353");
		m1.setPriority((long)2);
		com.vodafone.productGroups.pojo.Member m2=new com.vodafone.productGroups.pojo.Member();
		m2.setId("093354");
		m2.setPriority((long)2);
		listOfM1.add(m1);
		listOfM1.add(m2);
		gr.setGroupType("Compatible Insurance");
		gr.setMembers(listOfM1);
		return gr;
	}
	
	public static PriceForProduct GetPriceforproduct(){
		PriceForProduct priceForProduct = new PriceForProduct();
		
		List<PriceForExtra> priceForExtras = new ArrayList<>();
		PriceForExtra priceForExtra = new PriceForExtra();
		ExtraPrice extraPrice = new ExtraPrice();
		extraPrice.setExtraId("1234");
		com.vf.uk.dal.utility.entity.Price price = new com.vf.uk.dal.utility.entity.Price();
		price.setGross("56.56");
		price.setNet("234.4");
		price.setVat("23.56");
		
		//List<com.vf.uk.dal.utility.entity.MerchandisingPromotion> merchandisingPromotionList = new ArrayList<>();
		com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotion = new com.vf.uk.dal.utility.entity.MerchandisingPromotion();
		merchandisingPromotion.setDescription("dfgdfg");
		merchandisingPromotion.setDiscountId("sdf");
		merchandisingPromotion.setLabel("werr");
		merchandisingPromotion.setMpType("werr");
		merchandisingPromotion.setTag("ert");
		//merchandisingPromotionList.add(merchandisingPromotion);
		
		extraPrice.setMerchandisingPromotions(merchandisingPromotion);
		extraPrice.setMonthlyDiscountPrice(price);
		extraPrice.setMonthlyDiscountPrice(price);
		extraPrice.setMonthlyPrice(price);
		extraPrice.setOneOffDiscountPrice(price);
		extraPrice.setOneOffPrice(price);
		
		priceForExtra.setExtraPrice(extraPrice);
		
		List<PriceForAccessory> priceForAccessoryes = new ArrayList<>();
		PriceForAccessory priceForAccessory = new PriceForAccessory();
		com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = new com.vf.uk.dal.utility.entity.HardwarePrice();
		
		hardwarePrice.setHardwareId("93353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotion);
		hardwarePrice.setOneOffDiscountPrice(price);
		hardwarePrice.setOneOffPrice(price);
		
		priceForAccessory.setHardwarePrice(hardwarePrice);
		
		priceForExtras.add(priceForExtra);
		priceForAccessoryes.add(priceForAccessory);
		priceForProduct.setPriceForExtras(priceForExtras);
		priceForProduct.setPriceForAccessoryes(priceForAccessoryes);
		
		return priceForProduct;
	}
	
	public static List<MerchandisingPromotionModel> getMerChandisingPromotion(){
		List<MerchandisingPromotionModel> merchandisingPromotionModelList = new ArrayList<>();
		MerchandisingPromotionModel merchandisingPromotionModel = new MerchandisingPromotionModel();
		
		merchandisingPromotionModel.setTag("sdffd");
		merchandisingPromotionModelList.add(merchandisingPromotionModel);
		return merchandisingPromotionModelList;
	}
	public static List<MerchandisingPromotionModel> getMerChandisingPromotion1(){
		List<MerchandisingPromotionModel> merchandisingPromotionModelList = new ArrayList<>();
		MerchandisingPromotionModel merchandisingPromotionModel = new MerchandisingPromotionModel();
		
		merchandisingPromotionModel.setTag("W_HH_PAYM_OC_01");
		merchandisingPromotionModelList.add(merchandisingPromotionModel);
		return merchandisingPromotionModelList;
	}
	public static List<OfferAppliedPriceModel> getOfferAppliedPriceModel(){
		List<OfferAppliedPriceModel> list=new ArrayList<>();
		OfferAppliedPriceModel model1=new OfferAppliedPriceModel();
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
		OfferAppliedPriceModel model=new OfferAppliedPriceModel();
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
	public static List<com.vodafone.solrmodels.MerchandisingPromotionModel> getModel()
	{
		List<com.vodafone.solrmodels.MerchandisingPromotionModel> modelList=new ArrayList<>();
		com.vodafone.solrmodels.MerchandisingPromotionModel model=new MerchandisingPromotionModel();
		model.setTag("W_HH_OC_02");
		model.setPackageType(Arrays.asList("Upgrade","SecondLine"));
		modelList.add(model);
		return modelList;
	}
	public static List<BundleAndHardwarePromotions> getListOfBundleAndHardwarePromotions() {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle=new String(Utility.readFile("\\TEST-MOCK\\BundleandhardwarePromotuions.json"));
			BundleAndHardwarePromotions[] bundleList=mapper.readValue(bundle, BundleAndHardwarePromotions[].class);

			return  mapper.convertValue(bundleList, new TypeReference<List<BundleAndHardwarePromotions>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static PriceForAccessory getPriceForAccessory()
	{
		PriceForAccessory priceForAccessory = new PriceForAccessory();
		com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice= new com.vf.uk.dal.utility.entity.HardwarePrice();
		com.vf.uk.dal.utility.entity.Price price= new com.vf.uk.dal.utility.entity.Price();
		price.setGross("25.0");
		price.setNet("16.0");
		price.setVat("9.0");
		com.vf.uk.dal.utility.entity.Price price1= new com.vf.uk.dal.utility.entity.Price();
		price1.setGross("25.0");
		price1.setNet("16.0");
		price1.setVat("9.0");
		com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotion=new com.vf.uk.dal.utility.entity.MerchandisingPromotion();
		merchandisingPromotion.setTag("tag");
		merchandisingPromotion.setDescription("description");
		merchandisingPromotion.setDiscountId("discountId");
		merchandisingPromotion.setLabel("label");
		merchandisingPromotion.setMpType("mpType");
		merchandisingPromotion.setPriceEstablishedLabel("priceEstablishedLabel");
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setMerchandisingPromotions(merchandisingPromotion);
		hardwarePrice.setOneOffPrice(price);
		hardwarePrice.setOneOffDiscountPrice(price1);
		 priceForAccessory.setHardwarePrice(hardwarePrice);
		 return priceForAccessory;
	}
	/**
	 * @author manoj.bera
	 * @return
	 */
	public static com.vf.uk.dal.utility.entity.PriceForBundleAndHardware getUtilityPriceForBundleAndHardware()
	{
		List<com.vf.uk.dal.utility.entity.BundleAllowance> bundleAllowanceList = new ArrayList<>();
		com.vf.uk.dal.utility.entity.BundleAllowance bundleAllowance = new com.vf.uk.dal.utility.entity.BundleAllowance();
		bundleAllowance.setType("Red Bundle");
		bundleAllowance.setUom("Months");
		bundleAllowance.setValue("10Gb");
		bundleAllowanceList.add(bundleAllowance);
		com.vf.uk.dal.utility.entity.Price price = new com.vf.uk.dal.utility.entity.Price();
		price.setGross("22");
		price.setNet("20");
		price.setVat("2");
		com.vf.uk.dal.utility.entity.BundlePrice bundlePrice = new com.vf.uk.dal.utility.entity.BundlePrice();
		bundlePrice.setBundleId("110154");
		bundlePrice.setMonthlyDiscountPrice(price);
		bundlePrice.setMonthlyPrice(price);
		com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = new com.vf.uk.dal.utility.entity.HardwarePrice();
		hardwarePrice.setHardwareId("093353");
		hardwarePrice.setOneOffPrice(price);
		hardwarePrice.setOneOffDiscountPrice(price);
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceInfo = new com.vf.uk.dal.utility.entity.PriceForBundleAndHardware();
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
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String commercialProduct = new String(
					Utility.readFile("\\TEST-MOCK\\CommercialProductListOfMakeAndModel.json"));
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
	public static List<Group> getListOfProductGroupFromProductGroupRepository() 
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String listOfGroupsString = new String(
					Utility.readFile("\\TEST-MOCK\\ListOfProductGroupFromProductGroupRepository.json"));
			Group[] groupList = mapper.readValue(listOfGroupsString, Group[].class);

			return mapper.convertValue(groupList, new TypeReference<List<Group>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static CommercialProduct getCommercialProductByDeviceId() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String commercialProduct = new String(
					Utility.readFile("\\TEST-MOCK\\Coherence_CommercialProductRepo_CommercialProductByDeviceId"));
			CommercialProduct commercialProductList = mapper.readValue(commercialProduct, CommercialProduct.class);

			return mapper.convertValue(commercialProductList, new TypeReference<CommercialProduct>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static BazaarVoice getBazaarVoice(){
		ObjectMapper mapper = new ObjectMapper();
	 mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	 mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	 String productModel;
	 BazaarVoice  product=null;
	try {
		productModel = new
		 String(Utility.readFile("\\TEST-MOCK\\BazaarVoiceResponse.json"));
		   product=mapper.readValue(productModel, BazaarVoice.class);
		   product.setJsonsource(productModel);
	} catch (IOException e) {
		// TODO Auto-generated catch block
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
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String bundleModel = new String(
					Utility.readFile("\\TEST-MOCK\\Coherence_CommercialBundleRepo_CommercialBundle"));
			CommercialBundle bundleModelList = mapper.readValue(bundleModel, CommercialBundle.class);

			return mapper.convertValue(bundleModelList, new TypeReference<CommercialBundle>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion getMerchandisingPromotion() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String merchPromotion = new String(
					Utility.readFile("\\TEST-MOCK\\merchandisingPromotion_hardware_discount.json"));
			com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion merchPromo = mapper.readValue(merchPromotion, com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion.class);

			return mapper.convertValue(merchPromo, new TypeReference<com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String commercialProduct = new String(
					Utility.readFile("\\TEST-MOCK\\CommercialProduct_093353.json"));
			CommercialProduct commercialProductList = mapper.readValue(commercialProduct, CommercialProduct.class);

			return mapper.convertValue(commercialProductList, new TypeReference<CommercialProduct>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<Group> getListOfProductGroupForAccessory() 
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String listOfGroupsString = new String(
					Utility.readFile("\\TEST-MOCK\\ListOfAccessoryGroup.json"));
			Group[] groupList = mapper.readValue(listOfGroupsString, Group[].class);

			return mapper.convertValue(groupList, new TypeReference<List<Group>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static PriceForProduct getPriceForProduct_For_GetAccessoriesForDevice() {
		PriceForProduct navigation = new PriceForProduct();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(Utility
					.readFile("\\TEST-MOCK\\priceForProduct_For_GetAccessoriesForDevice.json"));
			PriceForProduct bundleList = new ObjectMapper().readValue(bundle,
					PriceForProduct.class);

			return mapper.convertValue(bundleList, new TypeReference<PriceForProduct>() {
			});

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return navigation;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static BundleDeviceAndProductsList bundleDeviceAndProductsList_For_GetAccessoriesOfDevice() {
		BundleDeviceAndProductsList navigation = new BundleDeviceAndProductsList();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			String bundle = new String(Utility
					.readFile("\\TEST-MOCK\\bundleDeviceAndProductsList_For_GetAccessoriesOfDevice.json"));
			BundleDeviceAndProductsList bundleList = new ObjectMapper().readValue(bundle,
					BundleDeviceAndProductsList.class);

			return mapper.convertValue(bundleList, new TypeReference<BundleDeviceAndProductsList>() {
			});

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String commercialProduct = new String(
					Utility.readFile("\\TEST-MOCK\\CommercialProductListOfAccessory.json"));
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
		String productModel = new String(Utility.readFile("\\TEST-MOCK\\BazaarVoiceResponse.json"));
		BazaarVoice product = new BazaarVoice();
		 product=mapper.readValue(productModel, BazaarVoice.class);
		 product.setJsonsource(productModel);
		 
		return  product.getJsonsource();
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static List<ProductGroupModel> getListOfProductGroupModels() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// mapper = new
			// ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
			// true);
			String productGroupModel = new String(Utility
					.readFile("\\TEST-MOCK\\listOfProductGroupModel_For_DevicePAYM_apple.json"));
			ProductGroupModel[] productGroupModelList = mapper
					.readValue(productGroupModel, ProductGroupModel[].class);

			return mapper.convertValue(productGroupModelList,
					new TypeReference<List<ProductGroupModel>>() {
					});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
