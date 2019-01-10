package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.entity.bundle.BundleModel;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.catalogue.DeviceOnlineModel;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.HardwarePrice;
import com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareAccessory;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareDataAllowances;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareExtras;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareSash;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareSecureNet;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForHardwareSash;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.Facet;
import com.vf.uk.dal.device.model.FacetWithCount;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.model.Make;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.MerchandisingControl;
import com.vf.uk.dal.device.model.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.model.MerchandisingPromotionsWrapper;
import com.vf.uk.dal.device.model.NewFacet;
import com.vf.uk.dal.device.model.PricePromotionHandsetPlanModel;
import com.vf.uk.dal.device.model.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.DeviceFinancingOption;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.productgroups.Count;
import com.vf.uk.dal.device.model.productgroups.FacetField;

import lombok.extern.slf4j.Slf4j;

/**
 * Mapping of coherence and solr entities to Device entities.
 * 
 * @author
 **/

@Slf4j
@Component
public class DeviceTilesDaoUtils {

	private static final String STRING_MAKE = "productMake";
	private static final String STRING_SIZE = "size";
	private static final String STRING_COLOR = "color";
	public static final String DATA_NOT_FOUND = "NA";
	public static final String IS_PREORDERABLE_YES = "true";
	private static String leadMember = "leadMember";
	public static final String LIMITED_TIME_DISCOUNT = "limited_time";
	public static final String FULL_DURATION_DISCOUNT = "full_duration";
	public static final String CONDITIONAL_LIMITED_DISCOUNT = "conditional_limited_discount";
	public static final String CONDITIONAL_FULL_DISCOUNT = "conditional_full_discount";
	public static final String STRING_FACET_COLOUR = "facetColour";
	public static final String STRING_FACET_CAPACITY = "capacity";
	public static final String STRING_FACET_OPERATING_SYSYTEM = "operatingSystem";
	public static final String STRING_MUST_HAVE_FEATURES = "mustHaveFeatures";
	public static final String STRING_EQUIPMENT_MAKE = "equipmentMake";
	public static final String DATE_FORMAT_SOLR = "dd/MM/yyyy HH:mm:ss";
	public static final String STRING_MEDIA_PRICEESTABLISH = "priceEstablishedLabel";
	public static final String STRING_MEDIA_DESCRIPTION = "description";
	public static final String STRING_MEDIA_LABEL = "label";
	public static final String STRING_URL_ALLOWANCE = "URL";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String STRING_MEDIA_PROMOTION = "promotionMedia";
	public static final String PROMO_TYPE_BUNDLEPROMOTION = "BP";
	public static final String PROMO_TYPE_HARDWAREPROMOTION = "HW";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String STRING_PRODUCT_MODEL = "HANDSET";
	public static final String PROMO_CATEGORY_PRICING_DISCOUNT = "Pricing_Discount";
	public static final String PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT = "Pricing_Upgrade_Discount";
	public static final String PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT = "Pricing_SecondLine_Discount";
	public static final String PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT = "Pricing_Automatic_Discount";
	public static final String STRING_FOR_MEDIA_TYPE = "URL";
	public static final String STRING_FOR_IMAGE_THUMBS_SIDE = "imageURLs.thumbs.side";
	public static final String STRING_FOR_IMAGE_THUMBS_FRONT = "imageURLs.thumbs.front";
	public static final String STRING_FOR_IMAGE_THUMBS_LEFT = "imageURLs.thumbs.left";
	public static final String STRING_FOR_IMAGE_THUMBS_RIGHT = "imageURLs.thumbs.right";
	public static final String DATE_FORMAT_COHERENCE = "dd/MM/yyyy HH:mm:ss";
	public static final String STRING_FOR_IMAGE_FULL_FRONT = "imageURLs.full.front";
	public static final String STRING_FOR_IMAGE_FULL_LEFT = "imageURLs.full.left";
	public static final String STRING_FOR_IMAGE_FULL_RIGHT = "imageURLs.full.right";
	public static final String STRING_FOR_IMAGE_FULL_SIDE = "imageURLs.full.side";
	public static final String STRING_FOR_IMAGE_FULL_BACK = "imageURLs.full.back";

	public static final String STRING_FOR_IMAGE_GRID = "imageURLs.grid";
	public static final String STRING_FOR_IMAGE_SMALL = "imageURLs.small";
	public static final String STRING_FOR_IMAGE_STICKER = "imageURLs.sticker";
	public static final String STRING_FOR_IMAGE_3DSPIN = "imageURLs.3dSpin";
	public static final String STRING_FOR_IMAGE_SUPPORT = "imageURLs.support";
	public static final String STRING_FOR_IMAGE_ICON = "imageURLs.icon";

	@Autowired
	CommonUtility commonUtility;

	/**
	 * @author manoj.bera
	 * @param priceForBundleAndHardware
	 * @param deviceId
	 * @param isConditionalAcceptJourney
	 * @param comBundle
	 * @return PriceForBundleAndHardware
	 */
	public PriceForBundleAndHardware getBundleAndHardwarePrice(PriceForBundleAndHardware priceForBundleAndHardware,
			String deviceId, boolean isConditionalAcceptJourney, CommercialBundle comBundle) {
		PriceForBundleAndHardware priceForBundleAndHardware1 = null;
		if (priceForBundleAndHardware != null && checkHardwarePriceNull(priceForBundleAndHardware)) {
			Integer deviceID = new Integer(deviceId);
			Integer hardwareID = new Integer(priceForBundleAndHardware.getHardwarePrice().getHardwareId());
			if (isConditionalAcceptJourney) {
				if (null != priceForBundleAndHardware.getBundlePrice() && null != comBundle
						&& comBundle.getId().equals(priceForBundleAndHardware.getBundlePrice().getBundleId())
						&& deviceID.equals(hardwareID)) {
					priceForBundleAndHardware1 = priceForBundleAndHardware;
					priceForBundleAndHardware1 = getCalculatedPrice(priceForBundleAndHardware1);
				}
			} else if (deviceID.equals(hardwareID)) {
				priceForBundleAndHardware1 = priceForBundleAndHardware;
				priceForBundleAndHardware1 = getCalculatedPrice(priceForBundleAndHardware1);
			}
		}
		return priceForBundleAndHardware1;
	}

	private boolean checkHardwarePriceNull(PriceForBundleAndHardware priceForBundleAndHardware) {
		return priceForBundleAndHardware.getHardwarePrice() != null
				&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null
				&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
				&& priceForBundleAndHardware.getHardwarePrice().getHardwareId() != null;
	}

	/**
	 * @author manoj.bera
	 * @param priceForBundleAndHardware1
	 * @return PriceForBundleAndHardware
	 */
	public PriceForBundleAndHardware getCalculatedPrice(PriceForBundleAndHardware priceForBundleAndHardware1) {
		if (priceForBundleAndHardware1.getHardwarePrice() != null && checkOeOnffPriceNull(priceForBundleAndHardware1)
				&& priceForBundleAndHardware1.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
						priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
			priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().setGross(null);
			priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().setVat(null);
			priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().setNet(null);
			priceForBundleAndHardware1.getOneOffDiscountPrice().setGross(null);
			priceForBundleAndHardware1.getOneOffDiscountPrice().setVat(null);
			priceForBundleAndHardware1.getOneOffDiscountPrice().setNet(null);
		}
		if (priceForBundleAndHardware1.getBundlePrice() != null && checkMonthlyPriceNull(priceForBundleAndHardware1)
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross()
						.equalsIgnoreCase(priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice().getGross())) {
			priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().setGross(null);
			priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().setVat(null);
			priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().setNet(null);
			priceForBundleAndHardware1.getMonthlyDiscountPrice().setGross(null);
			priceForBundleAndHardware1.getMonthlyDiscountPrice().setVat(null);
			priceForBundleAndHardware1.getMonthlyDiscountPrice().setNet(null);
		}
		return priceForBundleAndHardware1;
	}

	private boolean checkMonthlyPriceNull(PriceForBundleAndHardware priceForBundleAndHardware1) {
		return priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice().getGross() != null;
	}

	private boolean checkOeOnffPriceNull(PriceForBundleAndHardware priceForBundleAndHardware1) {
		return priceForBundleAndHardware1.getHardwarePrice().getOneOffPrice() != null
				&& priceForBundleAndHardware1.getOneOffDiscountPrice() != null
				&& priceForBundleAndHardware1.getOneOffDiscountPrice().getGross() != null
				&& priceForBundleAndHardware1.getOneOffPrice().getGross() != null;
	}

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param preOrderableFlag
	 * @return flag
	 */
	public Boolean dateValidation(Date startDateTime, Date endDateTime, boolean preOrderableFlag) {
		Date currentDate = new Date();
		boolean flag = false;

		if (startDateTime != null && endDateTime != null) {
			Boolean x = currentDate.before(startDateTime);
			Boolean y = preOrderableFlag;
			boolean z = x && y;

			Boolean a = currentDate.after(startDateTime);
			Boolean b = currentDate.before(endDateTime);
			Boolean c = a && b;
			if (z || c) {
				flag = true;
			}
		}
		if (startDateTime == null && endDateTime != null && currentDate.before(endDateTime)) {
			flag = true;
		}
		if (startDateTime != null && endDateTime == null && currentDate.after(startDateTime)) {
			flag = true;
		}
		if (startDateTime == null && endDateTime == null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return listOfDeviceGroupMember
	 */
	public List<com.vf.uk.dal.device.model.Member> getAscendingOrderForMembers(
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember) {
		Collections.sort(listOfDeviceGroupMember,
				(com.vf.uk.dal.device.model.Member member1, com.vf.uk.dal.device.model.Member member2) -> {
					if (member1.getPriority() != null && member2.getPriority() != null) {
						if (Integer.valueOf(member1.getPriority()) < Integer.valueOf(member2.getPriority())) {
							return -1;
						} else {
							return 1;
						}

					}
					return 1;
				});

		return listOfDeviceGroupMember;
	}

	/**
	 * Sort plans in ascending order based on monthly price.
	 * 
	 * @param plans
	 * @return List<PriceForBundleAndHardware>
	 */
	public List<PriceForBundleAndHardware> sortPlansBasedOnMonthlyPrice(List<PriceForBundleAndHardware> plans) {
		Collections.sort(plans, (PriceForBundleAndHardware plans1, PriceForBundleAndHardware plans2) -> {
			Double gross1 = null;
			if (null != plans1.getBundlePrice()) {
				String discountType = isPartialOrFullTenureDiscount(plans1.getBundlePrice());
				gross1 = getBundlePriceBasedOnDiscountDuration(plans1.getBundlePrice(), discountType);
			}
			Double gross2 = null;
			if (null != plans2.getBundlePrice()) {
				String discountType = isPartialOrFullTenureDiscount(plans2.getBundlePrice());
				gross2 = getBundlePriceBasedOnDiscountDuration(plans2.getBundlePrice(), discountType);
			}
			return Double.compare(gross1, gross2);
		});

		return plans;
	}

	/**
	 * 
	 * @param bundlePrice
	 * @param discountType
	 * @return monthlyPrice
	 */
	public Double getBundlePriceBasedOnDiscountDuration(
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice, String discountType) {
		Double monthlyPrice = null;
		if (null != discountType && discountType.equals(FULL_DURATION_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyDiscountPrice()
					&& null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
				monthlyPrice = Double.parseDouble(bundlePrice.getMonthlyDiscountPrice().getGross());
			}
		} else if ((null == discountType || discountType.equals(LIMITED_TIME_DISCOUNT))
				&& null != bundlePrice.getMonthlyPrice()
				&& StringUtils.isNotBlank(bundlePrice.getMonthlyPrice().getGross())) {
			monthlyPrice = Double.parseDouble(bundlePrice.getMonthlyPrice().getGross());
		}
		return monthlyPrice;
	}

	/**
	 * Check if bundle has partial or full tenure discount.
	 * 
	 * @param bundlePrice
	 * @return
	 */
	public String isPartialOrFullTenureDiscount(com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice) {
		if (null != bundlePrice.getMerchandisingPromotions()
				&& null != bundlePrice.getMerchandisingPromotions().getMpType()) {
			if (bundlePrice.getMerchandisingPromotions().getMpType().equalsIgnoreCase(FULL_DURATION_DISCOUNT)
					|| bundlePrice.getMerchandisingPromotions().getMpType()
							.equalsIgnoreCase(CONDITIONAL_FULL_DISCOUNT)) {
				return FULL_DURATION_DISCOUNT;
			} else if (bundlePrice.getMerchandisingPromotions().getMpType().equalsIgnoreCase(LIMITED_TIME_DISCOUNT)
					|| bundlePrice.getMerchandisingPromotions().getMpType()
							.equalsIgnoreCase(CONDITIONAL_LIMITED_DISCOUNT)) {
				return LIMITED_TIME_DISCOUNT;
			}
		}
		return null;
	}

	/**
	 * @author manoj.bera
	 * @param listOfProductModel
	 * @param listOfProducts
	 * @param facetFieldList
	 * @param groupType
	 * @param ls
	 * @param bundleModelMap
	 * @return FacetedDevice
	 */
	public FacetedDevice convertProductModelListToDeviceList(List<ProductModel> listOfProductModel,
			List<String> listOfProducts, List<FacetField> facetFieldList, String groupType,
			Map<String, BundleModel> bundleModelMap, Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice,
			String offerCode1, Map<String, String> groupNameWithProdId, Map<String, BundlePrice> bundleModelAndPriceMap,
			Map<String, BundleAndHardwarePromotions> promotionmap, Map<String, Boolean> isLeadMemberFromSolr,
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap, String journeyType,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, String cdnDomain) {
		String offerCode = offerCode1;
		List<Device> deviceList = new ArrayList<>();
		FacetedDevice facetedDevice = new FacetedDevice();
		MerchandisingPromotionsPackage merchandisingPromotionsPackage = null;
		// New Factes
		Facet facet = new Facet();
		List<Make> listOfMake = new ArrayList<>();
		List<NewFacet> listOfNewFacet = setListOfNewFacet(facetFieldList);
		facet.setMakeList(listOfMake);
		facetedDevice.setNewFacet(listOfNewFacet);

		int count = 0;
		Device deviceDetails;
		if (listOfProducts != null && !listOfProducts.isEmpty()) {
			for (ProductModel productModel : listOfProductModel) {
				if (listOfProducts.contains(productModel.getProductId())
						&& productModel.getProductClass().equalsIgnoreCase(STRING_PRODUCT_MODEL)) {
					String leadPlanId = null;
					deviceDetails = new Device();
					ProductGroupDetailsForDeviceList groupdeatils = productGroupdetailsMap
							.containsKey(productModel.getProductId())
									? productGroupdetailsMap.get(productModel.getProductId()) : null;
					setDeviceDetailsColourSizeGroupId(deviceDetails, groupdeatils);
					leadPlanId = setLeadPlanId(journeyType, productModel);
					merchandisingPromotionsPackage = new MerchandisingPromotionsPackage();
					deviceDetails.setDeviceId(productModel.getProductId());
					deviceDetails.setDescription(productModel.getPreDesc());
					setDeviceDetailsName(groupNameWithProdId, deviceDetails, productModel);
					deviceDetails.setProductClass(productModel.getProductClass());
					setDeviceDetailsRating(deviceDetails, productModel);
					deviceDetails.setMake(productModel.getEquipmentMake());
					deviceDetails.setModel(productModel.getEquipmentModel());
					deviceDetails.setGroupType(groupType);

					// Added MerchandisingControl to Device

					MerchandisingControl merchandisingControl;
					merchandisingControl = new MerchandisingControl();
					merchandisingControl.setIsDisplayableECare(Boolean.valueOf(productModel.getIsDisplayableEcare()));
					merchandisingControl.setIsSellableECare(Boolean.valueOf(productModel.getIsSellableECare()));
					merchandisingControl.setIsDisplayableAcq(Boolean.valueOf(productModel.getIsDisplayableAcq()));
					merchandisingControl.setIsSellableRet(Boolean.valueOf(productModel.getIsSellableRet()));
					merchandisingControl.setIsDisplayableRet(Boolean.valueOf(productModel.getIsDisplayableRet()));
					merchandisingControl.setIsSellableAcq(Boolean.valueOf(productModel.getIsSellableAcq()));
					merchandisingControl
							.setIsDisplayableSavedBasket(Boolean.valueOf(productModel.getIsDisplaybaleSavedBasket()));
					setOrderPreorderableAndAvailableFrom(productModel, merchandisingControl);
					merchandisingControl.setBackorderable(getPreOrBackOderable(productModel.getBackOrderable()));
					deviceDetails.setMerchandisingControl(merchandisingControl);
					// Price Info Device
					boolean offerFlag = false;
					BundleModel bundleModel = null;
					BundlePrice bundlePrice = null;
					log.info("IN Facet " + leadPlanId);
					log.info("IN Facet for" + bundleModelMap);
					if (leadPlanId != null && bundleModelMap != null) {
						bundleModel = bundleModelMap.get(leadPlanId);
						if (null != bundleModelAndPriceMap && !bundleModelAndPriceMap.isEmpty()) {
							bundlePrice = bundleModelAndPriceMap.get(leadPlanId);
						}
						log.info("IN Facet " + bundleModel);

						if (bundleModel == null) {

							List<String> listOfBundles = productModel.getListOfCompatibleBundles();
							for (String id : listOfBundles) {
								bundleModel = bundleModelMap.get(id);
								if (null != bundleModelAndPriceMap && !bundleModelAndPriceMap.isEmpty()) {
									bundlePrice = bundleModelAndPriceMap.get(id);
								}
								if (bundleModel != null)
									break;
							}

						}
					}
					if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)
							|| groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)) {

						if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)
								&& ((StringUtils.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType))
										|| checkAcqJourney(journeyType, offerCode))

						) {

							if (StringUtils.isNotBlank(offerCode)
									&& listOfOfferAppliedPrice.containsKey(productModel.getProductId())) {
								PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtils(
										listOfOfferAppliedPrice.get(productModel.getProductId()), leadPlanId);
								if (priceForOfferCode.getBundlePrice() != null
										&& priceForOfferCode.getHardwarePrice() != null) {
									offerFlag = true;
									deviceDetails.setPriceInfo(priceForOfferCode);
								}
							}
							if (StringUtils.isNotBlank(journeyType) && checkPriceInfoNull(deviceDetails)
									&& withoutOfferPriceMap != null
									&& withoutOfferPriceMap.containsKey(productModel.getProductId())) {
								PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtils(
										withoutOfferPriceMap.get(productModel.getProductId()), leadPlanId);
								if (priceForOfferCode.getBundlePrice() != null
										&& priceForOfferCode.getHardwarePrice() != null) {
									offerFlag = true;
									offerCode = null;
									deviceDetails.setPriceInfo(priceForOfferCode);
								}
							}
							if (deviceDetails.getPriceInfo() == null) {
								PriceForBundleAndHardware priceForWithOutOfferCode = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
										productModel, bundleModel, leadPlanId, groupType);
								offerFlag = false;
								deviceDetails.setPriceInfo(priceForWithOutOfferCode);
							}
						} else {
							PriceForBundleAndHardware priceForBundleAndHardware = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
									productModel, bundleModel, leadPlanId, groupType);
							if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYM)
									&& priceForBundleAndHardware.getBundlePrice() != null) {
								if (bundlePrice != null) {
									populateMerchandisingPromotions(priceForBundleAndHardware, bundlePrice);
								}
								deviceDetails.setPriceInfo(priceForBundleAndHardware);
							} else if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
								deviceDetails.setPriceInfo(priceForBundleAndHardware);
							}

						}
					}
					// Media Link
					List<MediaLink> mediaList = new ArrayList<>();
					com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchandising = null;
					com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchandising = null;
					// Merchandising Media
					String bundleTag = null;
					String bundleLabel = null;
					String bundleDescription = null;
					String bundleDiscountId = null;
					String bundleMpType = null;
					String bundlePriceEstablishedLabel = null;
					String promotionMedia = null;

					String hardwareTag = null;
					String hardwareLabel = null;
					String hardwareDescription = null;
					String hardwareDiscountId = null;
					String hardwareMpType = null;
					String hardwarePriceEstablishedLabel = null;
					String hardwarePromotionMedia = null;
					List<String> listOfMerchendising = (productModel.getMerchandisingMedia() == null
							|| productModel.getMerchandisingMedia().isEmpty()) ? Collections.emptyList()
									: productModel.getMerchandisingMedia();
					for (String mediaStr : listOfMerchendising) {

						String[] mediaStrList = mediaStr.split("\\|");
						if (mediaStrList.length == 7) {
							for (int i = 0; i < mediaStrList.length; i = i + 7) {

								String[] typeArray = mediaStrList[i + 2].split("&&");
								if (StringUtils.isNotBlank(offerCode) && offerFlag && checkOfferCodNullForMedia(
										offerCode, leadPlanId, mediaStrList, i, typeArray)) {
									MediaLink mediaLink = new MediaLink();
									mediaLink.setId(mediaStrList[i]);
									mediaLink.setValue(mediaStrList[i + 1]);
									mediaLink.setType(typeArray[0]);
									mediaList.add(mediaLink);

									if (PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										bundleTag = typeArray[3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											bundleLabel = mediaStrList[i + 1];
											bundleMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											bundleDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											bundleDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											bundlePriceEstablishedLabel = mediaStrList[i + 1];
										}

										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
										bundleMerchecdising.setDescription(bundleDescription);
										bundleMerchecdising.setDiscountId(bundleDiscountId);
										bundleMerchecdising.setLabel(bundleLabel);
										bundleMerchecdising.setMpType(bundleMpType);
										bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											promotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(promotionMedia))
											bundleMerchecdising.setPromotionMedia(promotionMedia);
										bundleMerchecdising.setTag(bundleTag);
										bundleMerchandising = bundleMerchecdising;
									}
									if (PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										hardwareTag = typeArray[i + 3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											hardwareLabel = mediaStrList[i + 1];
											hardwareMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											hardwareDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											hardwareDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
										hardwareMerchecdising.setDescription(hardwareDescription);
										hardwareMerchecdising.setDiscountId(hardwareDiscountId);
										hardwareMerchecdising.setLabel(hardwareLabel);
										hardwareMerchecdising.setMpType(hardwareMpType);
										hardwareMerchecdising.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePromotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(promotionMedia))
											hardwareMerchandising.setPromotionMedia(hardwarePromotionMedia);
										hardwareMerchecdising.setTag(hardwareTag);
										hardwareMerchandising = hardwareMerchecdising;
									}
								}
								if (StringUtils.isNotBlank(offerCode) && offerFlag
										&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4]) && leadPlanId != null
										&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
									boolean nonOfferAppliedPriceFlag = false;
									if (StringUtils.isNotBlank(journeyType)
											&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)
											&& PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])) {
										nonOfferAppliedPriceFlag = true;
									}
									if (StringUtils.isNotBlank(journeyType)
											&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_SECONDLINE)
											&& PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])) {
										nonOfferAppliedPriceFlag = true;
									}
									if (nonOfferAppliedPriceFlag) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												promotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(promotionMedia))
												bundleMerchecdising.setPromotionMedia(promotionMedia);
											bundleMerchandising = bundleMerchecdising;
										}
										if (PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
											hardwareMerchecdising.setDescription(hardwareDescription);
											hardwareMerchecdising.setDiscountId(hardwareDiscountId);
											hardwareMerchecdising.setLabel(hardwareLabel);
											hardwareMerchecdising.setMpType(hardwareMpType);
											hardwareMerchecdising
													.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
											hardwareMerchecdising.setTag(hardwareTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePromotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(hardwarePromotionMedia))
												hardwareMerchecdising.setPromotionMedia(hardwarePromotionMedia);
											hardwareMerchandising = hardwareMerchecdising;
										}
									}
								}

								if (StringUtils.isNotBlank(offerCode) && !offerFlag
										&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
										&& PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT
												.equalsIgnoreCase(mediaStrList[i + 3])
										&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])) {
									MediaLink mediaLink = new MediaLink();
									mediaLink.setId(mediaStrList[i]);
									mediaLink.setValue(mediaStrList[i + 1]);
									mediaLink.setType(typeArray[0]);
									mediaList.add(mediaLink);

									if (PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										bundleTag = typeArray[3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											bundleLabel = mediaStrList[i + 1];
											bundleMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											bundleDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											bundleDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											bundlePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
										bundleMerchecdising.setDescription(bundleDescription);
										bundleMerchecdising.setDiscountId(bundleDiscountId);
										bundleMerchecdising.setLabel(bundleLabel);
										bundleMerchecdising.setMpType(bundleMpType);
										bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
										bundleMerchecdising.setTag(bundleTag);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											promotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(promotionMedia))
											bundleMerchecdising.setPromotionMedia(promotionMedia);
										bundleMerchandising = bundleMerchecdising;
									}
									if (PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										hardwareTag = typeArray[i + 3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											hardwareLabel = mediaStrList[i + 1];
											hardwareMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											hardwareDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											hardwareDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
										hardwareMerchecdising.setDescription(hardwareDescription);
										hardwareMerchecdising.setDiscountId(hardwareDiscountId);
										hardwareMerchecdising.setLabel(hardwareLabel);
										hardwareMerchecdising.setMpType(hardwareMpType);
										hardwareMerchecdising.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
										hardwareMerchecdising.setTag(hardwareTag);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePromotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(hardwarePromotionMedia))
											hardwareMerchecdising.setPromotionMedia(hardwarePromotionMedia);
										hardwareMerchandising = hardwareMerchecdising;
									}
								}

								if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)
										&& StringUtils.isBlank(offerCode) && StringUtils.isNotBlank(journeyType)
										&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_ACQUISITION)
										&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
										&& StringUtils.isNotBlank(mediaStrList[i + 3])) {
									MediaLink mediaLink = new MediaLink();
									mediaLink.setId(mediaStrList[i]);
									mediaLink.setValue(mediaStrList[i + 1]);
									mediaLink.setType(typeArray[0]);
									mediaList.add(mediaLink);

									if (PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										bundleTag = typeArray[3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											bundleLabel = mediaStrList[i + 1];
											bundleMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											bundleDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											bundleDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											bundlePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
										bundleMerchecdising.setDescription(bundleDescription);
										bundleMerchecdising.setDiscountId(bundleDiscountId);
										bundleMerchecdising.setLabel(bundleLabel);
										bundleMerchecdising.setMpType(bundleMpType);
										bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
										bundleMerchecdising.setTag(bundleTag);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											promotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(promotionMedia))
											bundleMerchecdising.setPromotionMedia(promotionMedia);
										bundleMerchandising = bundleMerchecdising;
									}
									if (PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										hardwareTag = typeArray[i + 3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											hardwareLabel = mediaStrList[i + 1];
											hardwareMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											hardwareDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											hardwareDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
										hardwareMerchecdising.setDescription(hardwareDescription);
										hardwareMerchecdising.setDiscountId(hardwareDiscountId);
										hardwareMerchecdising.setLabel(hardwareLabel);
										hardwareMerchecdising.setMpType(hardwareMpType);
										hardwareMerchecdising.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
										hardwareMerchecdising.setTag(hardwareTag);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePromotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(hardwarePromotionMedia))
											hardwareMerchecdising.setPromotionMedia(hardwarePromotionMedia);
										hardwareMerchandising = hardwareMerchecdising;
									}
								}

								if (StringUtils.isBlank(offerCode) && offerFlag && StringUtils.isNotBlank(journeyType)
										&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4]) && leadPlanId != null
										&& leadPlanId.equalsIgnoreCase(typeArray[1])
										&& StringUtils.isNotBlank(mediaStrList[i + 3])
										&& StringUtils.containsIgnoreCase(mediaStrList[i + 3], journeyType)) {
									MediaLink mediaLink = new MediaLink();
									mediaLink.setId(mediaStrList[i]);
									mediaLink.setValue(mediaStrList[i + 1]);
									mediaLink.setType(typeArray[0]);
									mediaList.add(mediaLink);

									if (PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										bundleTag = typeArray[3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											bundleLabel = mediaStrList[i + 1];
											bundleMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											bundleDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											bundleDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											bundlePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
										bundleMerchecdising.setDescription(bundleDescription);
										bundleMerchecdising.setDiscountId(bundleDiscountId);
										bundleMerchecdising.setLabel(bundleLabel);
										bundleMerchecdising.setMpType(bundleMpType);
										bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
										bundleMerchecdising.setTag(bundleTag);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											promotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(promotionMedia))
											bundleMerchecdising.setPromotionMedia(promotionMedia);
										bundleMerchandising = bundleMerchecdising;
									}
									if (PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										hardwareTag = typeArray[i + 3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											hardwareLabel = mediaStrList[i + 1];
											hardwareMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											hardwareDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											hardwareDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePriceEstablishedLabel = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
										hardwareMerchecdising.setDescription(hardwareDescription);
										hardwareMerchecdising.setDiscountId(hardwareDiscountId);
										hardwareMerchecdising.setLabel(hardwareLabel);
										hardwareMerchecdising.setMpType(hardwareMpType);
										hardwareMerchecdising.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
										hardwareMerchecdising.setTag(hardwareTag);
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePromotionMedia = mediaStrList[i + 1];
										}
										if (StringUtils.isNotBlank(hardwarePromotionMedia))
											hardwareMerchecdising.setPromotionMedia(hardwarePromotionMedia);
										hardwareMerchandising = hardwareMerchecdising;
									}
								}

								if (StringUtils.isBlank(offerCode)
										&& StringUtils.equalsIgnoreCase(JOURNEY_TYPE_ACQUISITION, journeyType)
										&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
										&& PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT
												.equalsIgnoreCase(mediaStrList[i + 3])
										&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])) {
									MediaLink mediaLink = new MediaLink();
									mediaLink.setId(mediaStrList[i]);
									mediaLink.setValue(mediaStrList[i + 1]);
									mediaLink.setType(typeArray[0]);
									mediaList.add(mediaLink);

									if (PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										bundleTag = typeArray[3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											bundleLabel = mediaStrList[i + 1];
											bundleMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											bundleDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											bundleDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											bundlePriceEstablishedLabel = mediaStrList[i + 1];
										}

										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											promotionMedia = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
										bundleMerchecdising.setDescription(bundleDescription);
										bundleMerchecdising.setDiscountId(bundleDiscountId);
										bundleMerchecdising.setLabel(bundleLabel);
										bundleMerchecdising.setMpType(bundleMpType);
										bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
										bundleMerchecdising.setTag(bundleTag);
										if (StringUtils.isNotBlank(promotionMedia))
											bundleMerchecdising.setPromotionMedia(promotionMedia);
										bundleMerchandising = bundleMerchecdising;
									}
									if (PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
										hardwareTag = typeArray[i + 3];
										if (StringUtils.contains(
												mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
														mediaStrList[i].length()),
												STRING_MEDIA_LABEL)) {
											hardwareLabel = mediaStrList[i + 1];
											hardwareMpType = mediaStrList[i].substring(0, mediaStrList[i].indexOf('.'));
											hardwareDiscountId = mediaStrList[i + 6];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_DESCRIPTION)) {
											hardwareDescription = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PRICEESTABLISH)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePriceEstablishedLabel = mediaStrList[i + 1];
										}
										if (StringUtils.containsIgnoreCase(mediaStrList[i], STRING_MEDIA_PROMOTION)
												&& StringUtils.isNotBlank(mediaStrList[i + 1])
												&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
											hardwarePromotionMedia = mediaStrList[i + 1];
										}
										com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
										hardwareMerchecdising.setDescription(hardwareDescription);
										hardwareMerchecdising.setDiscountId(hardwareDiscountId);
										hardwareMerchecdising.setLabel(hardwareLabel);
										hardwareMerchecdising.setMpType(hardwareMpType);
										hardwareMerchecdising.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);

										if (StringUtils.isNotBlank(hardwarePromotionMedia))
											hardwareMerchecdising.setPromotionMedia(hardwarePromotionMedia);
										hardwareMerchecdising.setTag(hardwareTag);
										hardwareMerchandising = hardwareMerchecdising;
									}
								}
							}
						}
					}
					/**
					 * Promotions from promotion API
					 * 
					 * @author manoj.bera
					 */
					if (promotionmap != null && !promotionmap.isEmpty()
							&& promotionmap.containsKey(productModel.getProductId())) {

						BundleAndHardwarePromotions promotions = promotionmap.get(productModel.getProductId());
						merchandisingPromotionsPackage = commonUtility.getNonPricingPromotions(promotions, mediaList);

					}
					if (StringUtils.isNotBlank(productModel.getImageURLsThumbsFront())) {
						MediaLink mediaThumbsFrontLink = new MediaLink();
						mediaThumbsFrontLink.setId(STRING_FOR_IMAGE_THUMBS_FRONT);
						mediaThumbsFrontLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaThumbsFrontLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsFront()));
						mediaList.add(mediaThumbsFrontLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsThumbsLeft())) {
						MediaLink mediaThumbsLeftLink = new MediaLink();
						mediaThumbsLeftLink.setId(STRING_FOR_IMAGE_THUMBS_LEFT);
						mediaThumbsLeftLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaThumbsLeftLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsLeft()));
						mediaList.add(mediaThumbsLeftLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsThumbsRight())) {
						MediaLink mediaThumbsRightLink = new MediaLink();
						mediaThumbsRightLink.setId(STRING_FOR_IMAGE_THUMBS_RIGHT);
						mediaThumbsRightLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaThumbsRightLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsRight()));
						mediaList.add(mediaThumbsRightLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsThumbsSide())) {
						MediaLink mediaThumbsSideLink = new MediaLink();
						mediaThumbsSideLink.setId(STRING_FOR_IMAGE_THUMBS_SIDE);
						mediaThumbsSideLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaThumbsSideLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsSide()));
						mediaList.add(mediaThumbsSideLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsFullLeft())) {
						MediaLink mediaFullLeftLink = new MediaLink();
						mediaFullLeftLink.setId(STRING_FOR_IMAGE_FULL_LEFT);
						mediaFullLeftLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaFullLeftLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullLeft()));
						mediaList.add(mediaFullLeftLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsFullRight())) {
						MediaLink mediaFullRightLink = new MediaLink();
						mediaFullRightLink.setId(STRING_FOR_IMAGE_FULL_RIGHT);
						mediaFullRightLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaFullRightLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullRight()));
						mediaList.add(mediaFullRightLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsFullSide())) {
						MediaLink mediaFullSideLink = new MediaLink();
						mediaFullSideLink.setId(STRING_FOR_IMAGE_FULL_SIDE);
						mediaFullSideLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaFullSideLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullSide()));
						mediaList.add(mediaFullSideLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsFullBack())) {
						MediaLink mediaFullBackLink = new MediaLink();
						mediaFullBackLink.setId(STRING_FOR_IMAGE_FULL_BACK);
						mediaFullBackLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaFullBackLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullBack()));
						mediaList.add(mediaFullBackLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsGrid())) {
						MediaLink mediaGridLink = new MediaLink();
						mediaGridLink.setId(STRING_FOR_IMAGE_GRID);
						mediaGridLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaGridLink
								.setValue(commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsGrid()));
						mediaList.add(mediaGridLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsSmall())) {
						MediaLink mediaSmallLink = new MediaLink();
						mediaSmallLink.setId(STRING_FOR_IMAGE_SMALL);
						mediaSmallLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaSmallLink
								.setValue(commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsSmall()));
						mediaList.add(mediaSmallLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsSticker())) {
						MediaLink mediaStickerLink = new MediaLink();
						mediaStickerLink.setId(STRING_FOR_IMAGE_STICKER);
						mediaStickerLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaStickerLink.setValue(
								commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsSticker()));
						mediaList.add(mediaStickerLink);
					}

					if (StringUtils.isNotBlank(productModel.getImageURLsIcon())) {
						MediaLink mediaIconLink = new MediaLink();
						mediaIconLink.setId(STRING_FOR_IMAGE_ICON);
						mediaIconLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaIconLink
								.setValue(commonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsIcon()));
						mediaList.add(mediaIconLink);
					}

					if (StringUtils.isNotBlank(productModel.getThreeDSpin())) {
						MediaLink media3DSpinLink = new MediaLink();
						media3DSpinLink.setId(STRING_FOR_IMAGE_3DSPIN);
						media3DSpinLink.setType(STRING_FOR_MEDIA_TYPE);
						media3DSpinLink
								.setValue(commonUtility.getImageMediaUrl(cdnDomain, productModel.getThreeDSpin()));
						mediaList.add(media3DSpinLink);
					}

					if (StringUtils.isNotBlank(productModel.getSupport())) {
						MediaLink mediaSupportLink = new MediaLink();
						mediaSupportLink.setId(STRING_FOR_IMAGE_SUPPORT);
						mediaSupportLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaSupportLink.setValue(commonUtility.getImageMediaUrl(cdnDomain, productModel.getSupport()));
						mediaList.add(mediaSupportLink);
					}

					deviceDetails.setMedia(mediaList);
					PriceForBundleAndHardware priceForWithOutOfferCode = deviceDetails.getPriceInfo();
					if (priceForWithOutOfferCode != null) {
						if (priceForWithOutOfferCode.getBundlePrice() != null) {
							priceForWithOutOfferCode.getBundlePrice().setMerchandisingPromotions(bundleMerchandising);
							if (merchandisingPromotionsPackage != null
									&& merchandisingPromotionsPackage.getBundlePromotions() != null)
								merchandisingPromotionsPackage.getBundlePromotions()
										.setPricePromotion(bundleMerchandising);

						}
						if (priceForWithOutOfferCode.getHardwarePrice() != null) {
							priceForWithOutOfferCode.getHardwarePrice()
									.setMerchandisingPromotions(hardwareMerchandising);
							if (merchandisingPromotionsPackage != null
									&& merchandisingPromotionsPackage.getHardwarePromotions() != null)
								merchandisingPromotionsPackage.getHardwarePromotions()
										.setPricePromotion(hardwareMerchandising);
						}
					}
					if ((isLeadMemberFromSolr.get(leadMember) && StringUtils.isNotBlank(leadPlanId)
							&& groupType.equalsIgnoreCase(STRING_DEVICE_PAYM))
							|| (isLeadMemberFromSolr.get(leadMember) && groupType.equalsIgnoreCase(STRING_DEVICE_PAYG))
							|| (!isLeadMemberFromSolr.get(leadMember))) {
						deviceDetails.setPromotionsPackage(merchandisingPromotionsPackage);
						deviceList.add(deviceDetails);
						count++;
					}
				}
			}
		} else {
			log.info("Products not provided while converting ProductModelListToDeviceList.");
		}
		facetedDevice.setDevice(deviceList);
		facetedDevice.setNoOfRecordsFound(count);
		return facetedDevice;
	}

	/**
	 * @param listOfProductModel
	 * @param listOfProducts
	 * @param facetFieldList
	 * @param groupType
	 * @param mapForDeviceAndPriceForBAndWWIthoutOffer
	 * @param productGroupModelList
	 * @param offerCode
	 * @param ls
	 * @param bundleModelMap
	 * @return FacetedDevice
	 */
	public FacetedDevice convertProductModelListToDeviceListForHandsetOnlineModel(
			List<com.vf.uk.dal.device.client.entity.catalogue.Device> listOfProductModel, List<String> listOfProducts,
			List<FacetField> facetList, String groupType, String journeyType,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, String cdnDomain,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthOffer,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthoutOffer,
			List<DeviceOnlineModel> productGroupModelList, String offerCode) {
		List<Device> deviceList = new ArrayList<>();
		FacetedDevice facetedDevice = new FacetedDevice();
		List<NewFacet> listOfNewFacet = setListOfNewFacetForHandsetOnlineModel(facetList);
		facetedDevice.setNewFacet(listOfNewFacet);
		int count = 0;
			for (com.vf.uk.dal.device.client.entity.catalogue.Device productModel : listOfProductModel) {
				setDeviceDetailsList(listOfProducts, groupType, journeyType, productGroupdetailsMap,
						mapForDeviceAndPriceForBAndWWIthOffer, mapForDeviceAndPriceForBAndWWIthoutOffer,
						productGroupModelList, offerCode, deviceList, productModel, cdnDomain);
				count++;
			}
		facetedDevice.setDevice(deviceList);
		facetedDevice.setNoOfRecordsFound(count);
		return facetedDevice;
	}

	private void setDeviceDetailsList(List<String> listOfProducts, String groupType, String journeyType,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthOffer,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthoutOffer,
			List<DeviceOnlineModel> productGroupModelList, String offerCode, List<Device> deviceList,
			com.vf.uk.dal.device.client.entity.catalogue.Device productModel, String cdnDomain) {
		Device deviceDetails;
		if (listOfProducts.contains(productModel.getDeviceId())
				&& productModel.getProductClass().equalsIgnoreCase(STRING_PRODUCT_MODEL)) {
			String leadPlanId = null;
			deviceDetails = new Device();
			ProductGroupDetailsForDeviceList groupdeatils = productGroupdetailsMap.containsKey(
					productModel.getDeviceId()) ? productGroupdetailsMap.get(productModel.getDeviceId()) : null;
			setDeviceDetailsColourSizeGroupId(deviceDetails, groupdeatils);
			leadPlanId = setLeadPlanIdForHandsetOnlineModel(journeyType, productModel);
			deviceDetails.setDeviceId(productModel.getDeviceId());
			deviceDetails.setDescription(productModel.getDisplayDescription());
			deviceDetails.setName(deviceDetails.getProductGroupName());
			deviceDetails.setProductClass(productModel.getProductClass());
			setDeviceDetailsRatingForHandsetOnlineModel(deviceDetails, productGroupModelList);
			deviceDetails.setMake(productModel.getMake());
			deviceDetails.setModel(productModel.getModel());
			deviceDetails.setGroupType(groupType);

			MerchandisingControl merchandisingControl;
			merchandisingControl = new MerchandisingControl();
			merchandisingControl.setIsDisplayableECare(productModel.getMerchandisingControl().getIsDisplayableECare());
			merchandisingControl.setIsSellableECare(productModel.getMerchandisingControl().getIsSellableECare());
			merchandisingControl.setIsDisplayableAcq(productModel.getMerchandisingControl().getIsDisplayableAcq());
			merchandisingControl.setIsSellableRet(productModel.getMerchandisingControl().getIsSellableRet());
			merchandisingControl.setIsDisplayableRet(productModel.getMerchandisingControl().getIsDisplayableRet());
			merchandisingControl.setIsSellableAcq(productModel.getMerchandisingControl().getIsSellableAcq());
			merchandisingControl
					.setIsDisplayableSavedBasket(productModel.getMerchandisingControl().getIsDisplayableSavedBasket());
			setOrderPreorderableAndAvailableFromForHandsetOnlineModel(productModel, merchandisingControl);
			merchandisingControl.setBackorderable(productModel.getMerchandisingControl().getBackorderable());
			deviceDetails.setMerchandisingControl(merchandisingControl);

			setPriceInfoForDeviceDetails(groupType, journeyType, mapForDeviceAndPriceForBAndWWIthOffer,
					mapForDeviceAndPriceForBAndWWIthoutOffer, offerCode, deviceDetails, productModel, leadPlanId,
					cdnDomain);
			deviceList.add(deviceDetails);
		}
	}

	private void setPriceInfoForDeviceDetails(String groupType, String journeyType,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthOffer,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthoutOffer, String offerCode,
			Device deviceDetails, com.vf.uk.dal.device.client.entity.catalogue.Device productModel, String leadPlanId,
			String cdnDomain) {
		PricePromotionHandsetPlanModel promotions = null;
		if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)
				&& ((StringUtils.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType))
						|| checkAcqJourney(journeyType, offerCode))) {
			promotions = setPriceInfoAndPromotion(groupType, journeyType, mapForDeviceAndPriceForBAndWWIthOffer,
					mapForDeviceAndPriceForBAndWWIthoutOffer, offerCode, deviceDetails, productModel, leadPlanId);
		} else {
			PriceForBundleAndHardware priceForBundleAndHardware = getBundleAndHardwarePriceFromSolrWithoutOfferCodeForHandsetOnlineModel(
					mapForDeviceAndPriceForBAndWWIthoutOffer.get(productModel.getDeviceId()), leadPlanId, groupType);
			deviceDetails.setPriceInfo(priceForBundleAndHardware);
			promotions = mapForDeviceAndPriceForBAndWWIthoutOffer.get(productModel.getDeviceId());
		}
		setMediaLinkAndPromotions(productModel, promotions, deviceDetails, cdnDomain);
	}

	private PricePromotionHandsetPlanModel setPriceInfoAndPromotion(String groupType, String journeyType,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthOffer,
			Map<String, PricePromotionHandsetPlanModel> mapForDeviceAndPriceForBAndWWIthoutOffer, String offerCode,
			Device deviceDetails, com.vf.uk.dal.device.client.entity.catalogue.Device productModel, String leadPlanId) {
		PricePromotionHandsetPlanModel promotions = null;
		if (StringUtils.isNotBlank(offerCode)
				&& mapForDeviceAndPriceForBAndWWIthOffer != null && mapForDeviceAndPriceForBAndWWIthOffer.containsKey(productModel.getDeviceId())) {
			PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtilsForHandsetOnlineModel(
					mapForDeviceAndPriceForBAndWWIthOffer.get(productModel.getDeviceId()), leadPlanId);
			if (priceForOfferCode.getBundlePrice() != null && priceForOfferCode.getHardwarePrice() != null) {
				deviceDetails.setPriceInfo(priceForOfferCode);
				promotions = mapForDeviceAndPriceForBAndWWIthOffer.get(productModel.getDeviceId());
			}
		}
		if (StringUtils.isNotBlank(journeyType) && checkPriceInfoNull(deviceDetails)
				&& mapForDeviceAndPriceForBAndWWIthoutOffer != null
				&& mapForDeviceAndPriceForBAndWWIthoutOffer.containsKey(productModel.getDeviceId())) {
			PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtilsForHandsetOnlineModel(
					mapForDeviceAndPriceForBAndWWIthoutOffer.get(productModel.getDeviceId()), leadPlanId);
			if (priceForOfferCode.getBundlePrice() != null && priceForOfferCode.getHardwarePrice() != null) {
				deviceDetails.setPriceInfo(priceForOfferCode);
				promotions = mapForDeviceAndPriceForBAndWWIthoutOffer.get(productModel.getDeviceId());
			}
		}
		if (deviceDetails.getPriceInfo() == null && mapForDeviceAndPriceForBAndWWIthoutOffer != null
				&& mapForDeviceAndPriceForBAndWWIthoutOffer.containsKey(productModel.getDeviceId())) {
			PriceForBundleAndHardware priceForWithOutOfferCode = getBundleAndHardwarePriceFromSolrWithoutOfferCodeForHandsetOnlineModel(
					mapForDeviceAndPriceForBAndWWIthoutOffer.get(productModel.getDeviceId()), leadPlanId, groupType);
			deviceDetails.setPriceInfo(priceForWithOutOfferCode);
			promotions = mapForDeviceAndPriceForBAndWWIthoutOffer.get(productModel.getDeviceId());
		}
		return promotions;
	}

	/**
	 * 
	 * @param sizeVariant
	 * @param promotions
	 * @param deviceDetails
	 * @param footNotes
	 */
	public void setDevicePromoMedia(MerchandisingPromotionsWrapper promotions, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		if (promotions != null) {
			setPromotionsMediaList(promotions, mediaList, merchandisingPromotionsWrapper);
		}
	}

	/**
	 * 
	 * @param sizeVariant
	 * @param promotions
	 * @param footNotes
	 */
	public void setBundlePromoMedia(MerchandisingPromotionsWrapper promotions, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		if (promotions != null) {
			setPromotionsMediaList(promotions, mediaList, merchandisingPromotionsWrapper);
		}
	}

	/**
	 * 
	 * @param promotion
	 * @param merchandisingPromotionsWrapper
	 * @param footNotes
	 * @param mapForPriority
	 * @return
	 */
	public void setPromotionsMediaList(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {

		getConditionalSashBanner(promotion, mediaList, merchandisingPromotionsWrapper);
		getDataPromotion(promotion, mediaList, merchandisingPromotionsWrapper);
		getEntertainmentPromo(promotion, mediaList, merchandisingPromotionsWrapper);
		getFreeAccessoryPromo(promotion, mediaList, merchandisingPromotionsWrapper);
		setFreeExtraPromo(promotion, mediaList, merchandisingPromotionsWrapper);
		setPricePromotion(promotion, mediaList, merchandisingPromotionsWrapper);
		setSashBannerPromo(promotion, mediaList, merchandisingPromotionsWrapper);
		setSecureNetPromo(promotion, mediaList, merchandisingPromotionsWrapper);
	}

	private void setSecureNetPromo(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getSecureNetPromotion() != null) {
			promotionLocal = promotion.getSecureNetPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setSecureNetPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setSecureNetPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getSecureNetPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setSecureNetPromotion(null);
		}
	}

	private void setSashBannerPromo(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getSashBannerPromotion() != null) {
			promotionLocal = promotion.getSashBannerPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setSashBannerPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setSashBannerPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getSashBannerPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setSashBannerPromotion(null);
		}
	}

	private void setPricePromotion(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getPricePromotion() != null) {
			promotionLocal = promotion.getPricePromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setPricePromotion(null);

			} else {
				merchandisingPromotionsWrapper.setPricePromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getPricePromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setPricePromotion(null);
		}
	}

	private void setFreeExtraPromo(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getFreeExtraPromotion() != null) {
			promotionLocal = promotion.getFreeExtraPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setFreeExtraPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setFreeExtraPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getFreeExtraPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setFreeExtraPromotion(null);
		}
	}

	private void getFreeAccessoryPromo(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getFreeAccessoryPromotion() != null) {
			promotionLocal = promotion.getFreeAccessoryPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setFreeAccessoryPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setFreeAccessoryPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getFreeAccessoryPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setFreeAccessoryPromotion(null);
		}
	}

	private void getEntertainmentPromo(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getEntertainmentPackPromotion() != null) {
			promotionLocal = promotion.getEntertainmentPackPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setEntertainmentPackPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setEntertainmentPackPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getEntertainmentPackPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setEntertainmentPackPromotion(null);
		}
	}

	private void getDataPromotion(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getDataPromotion() != null) {
			promotionLocal = promotion.getDataPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setDataPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setDataPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getDataPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setDataPromotion(null);
		}
	}

	private void getConditionalSashBanner(MerchandisingPromotionsWrapper promotion, List<MediaLink> mediaList,
			MerchandisingPromotionsWrapper merchandisingPromotionsWrapper) {
		MerchandisingPromotion promotionLocal;
		if (promotion.getConditionalSashBannerPromotion() != null) {
			promotionLocal = promotion.getConditionalSashBannerPromotion();
			if (!commonUtility.dateValidationForOffers(promotionLocal.getStartDate(), promotionLocal.getEndDate(),
					DATE_FORMAT_COHERENCE)) {
				merchandisingPromotionsWrapper.setConditionalSashBannerPromotion(null);

			} else {
				merchandisingPromotionsWrapper.setConditionalSashBannerPromotion(promotionLocal);
				if (CollectionUtils.isEmpty(promotionLocal.getFootNotes())) {
					merchandisingPromotionsWrapper.getConditionalSashBannerPromotion().setFootNotes(null);
				}
				setMediaLinkForPromotions(promotionLocal, mediaList);
			}
		} else {
			merchandisingPromotionsWrapper.setConditionalSashBannerPromotion(null);
		}
	}

	private void setMediaLinkAndPromotions(com.vf.uk.dal.device.client.entity.catalogue.Device productModel,
			PricePromotionHandsetPlanModel promotions, Device deviceDetails, String cdnDomain) {
		MerchandisingPromotionsPackage merchandisingPromotionsPackage = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper bundlePromotions = new MerchandisingPromotionsWrapper();
		MerchandisingPromotionsWrapper hardwarePromotions = new MerchandisingPromotionsWrapper();
		List<MediaLink> mediaList = new ArrayList<>();
		if (promotions != null) {
			setDevicePromoMedia(promotions.getPromotionsPackage().getHardwarePromotions(), mediaList,
					hardwarePromotions);
			setBundlePromoMedia(promotions.getPromotionsPackage().getBundlePromotions(), mediaList, bundlePromotions);
			merchandisingPromotionsPackage.setBundlePromotions(bundlePromotions);
			merchandisingPromotionsPackage.setHardwarePromotions(hardwarePromotions);
			merchandisingPromotionsPackage.setHardwareId(promotions.getHardwareId());
			merchandisingPromotionsPackage.setPlanId(promotions.getPlanId());
			deviceDetails.setPromotionsPackage(merchandisingPromotionsPackage);
		}
		if (productModel.getListOfimageURLs() != null
				&& CollectionUtils.isNotEmpty(productModel.getListOfimageURLs())) {
			productModel.getListOfimageURLs().forEach(imageURL -> {
				MediaLink mediaLink = new MediaLink();
				mediaLink.setId(imageURL.getImageName());
				mediaLink.setType("URL");
				if (imageURL.getImageName().contains("image")) {
					mediaLink.setValue(commonUtility.getImageMediaUrl(cdnDomain, imageURL.getImageURL()));
				} else {
					mediaLink.setValue(imageURL.getImageURL());
				}
				mediaList.add(mediaLink);
			});
		}
		if (productModel.getListOfMediaURLs() != null
				&& CollectionUtils.isNotEmpty(productModel.getListOfMediaURLs())) {
			productModel.getListOfMediaURLs().forEach(mediaURL -> {
				MediaLink mediaLink = new MediaLink();
				mediaLink.setId(mediaURL.getMediaName());
				mediaLink.setType("URL");
				if (mediaURL.getMediaName().contains("image")) {
					mediaLink.setValue(commonUtility.getImageMediaUrl(cdnDomain, mediaURL.getMediaURL()));
				} else {
					mediaLink.setValue(mediaURL.getMediaURL());
				}

				mediaList.add(mediaLink);
			});
		}
		deviceDetails.setMedia(mediaList);
	}

	private void setMediaLinkForPromotions(MerchandisingPromotion merchandisingPromotion, List<MediaLink> mediaList) {

		if (merchandisingPromotion.getLabel() != null) {
			MediaLink mediaLink = new MediaLink();
			mediaLink.setId(
					merchandisingPromotion.getMpType() + ".merchandisingPromotions.merchandisingPromotion.label");
			mediaLink.setType("TEXT");
			mediaLink.setPriority(merchandisingPromotion.getPriority());
			mediaLink.setValue(merchandisingPromotion.getLabel());
			mediaList.add(mediaLink);
		}
		if (merchandisingPromotion.getDescription() != null) {
			MediaLink mediaLink = new MediaLink();
			mediaLink.setId(
					merchandisingPromotion.getMpType() + ".merchandisingPromotions.merchandisingPromotion.description");
			mediaLink.setType("TEXT");
			mediaLink.setPriority(merchandisingPromotion.getPriority());
			mediaLink.setValue(merchandisingPromotion.getDescription());
			mediaList.add(mediaLink);
		}
		if (merchandisingPromotion.getPriceEstablishedLabel() != null) {
			MediaLink mediaLink = new MediaLink();
			mediaLink.setId(merchandisingPromotion.getMpType()
					+ ".merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel");
			mediaLink.setType("TEXT");
			mediaLink.setPriority(merchandisingPromotion.getPriority());
			mediaLink.setValue(merchandisingPromotion.getPriceEstablishedLabel());
			mediaList.add(mediaLink);
		}
		if (merchandisingPromotion.getPromotionMedia() != null) {
			MediaLink mediaLink = new MediaLink();
			mediaLink.setId(merchandisingPromotion.getMpType()
					+ ".merchandisingPromotions.merchandisingPromotion.promotionMedia");
			mediaLink.setType("TEXT");
			mediaLink.setPriority(merchandisingPromotion.getPriority());
			mediaLink.setValue(merchandisingPromotion.getPromotionMedia());
			mediaList.add(mediaLink);
		}
	}

	private boolean checkOfferCodNullForMedia(String offerCode, String leadPlanId, String[] mediaStrList, int i,
			String[] typeArray) {
		return !DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
				&& PROMO_CATEGORY_PRICING_DISCOUNT.equalsIgnoreCase(mediaStrList[i + 3])
				&& offerCode.equalsIgnoreCase(mediaStrList[i + 4]) && leadPlanId != null
				&& leadPlanId.equalsIgnoreCase(typeArray[1]);
	}

	private boolean checkPriceInfoNull(Device deviceDetails) {
		return deviceDetails.getPriceInfo() == null
				|| (deviceDetails.getPriceInfo() != null && deviceDetails.getPriceInfo().getBundlePrice() == null
						&& deviceDetails.getPriceInfo().getHardwarePrice() == null);
	}

	private boolean checkAcqJourney(String journeyType, String offerCode) {
		return StringUtils.isBlank(offerCode)
				&& (StringUtils.isNotBlank(journeyType) && !StringUtils.equals(JOURNEY_TYPE_ACQUISITION, journeyType));
	}

	private void setOrderPreorderableAndAvailableFrom(ProductModel productModel,
			MerchandisingControl merchandisingControl) {
		if (productModel.getOrder() != null) {
			merchandisingControl.setOrder(productModel.getOrder());
		}
		if (getPreOrBackOderable(productModel.getPreOrderable())) {
			if (productModel.getAvailableFrom() != null
					&& commonUtility.dateValidationForProduct(productModel.getAvailableFrom(), DATE_FORMAT_SOLR)) {
				merchandisingControl.setPreorderable(true);
				merchandisingControl.setAvailableFrom(productModel.getAvailableFrom());
			} else {
				merchandisingControl.setPreorderable(false);
			}
		} else {
			merchandisingControl.setPreorderable(false);
		}
	}

	private void setOrderPreorderableAndAvailableFromForHandsetOnlineModel(
			com.vf.uk.dal.device.client.entity.catalogue.Device productModel,
			MerchandisingControl merchandisingControl) {
		if (productModel.getMerchandisingControl().getOrder() != null) {
			merchandisingControl.setOrder(productModel.getMerchandisingControl().getOrder());
		}
		if (productModel.getPreOrderable()) {
			if (productModel.getAvailableFrom() != null
					&& commonUtility.dateValidationForProduct(productModel.getAvailableFrom(), DATE_FORMAT_SOLR)) {
				merchandisingControl.setPreorderable(true);
				merchandisingControl.setAvailableFrom(productModel.getAvailableFrom());
			} else {
				merchandisingControl.setPreorderable(false);
			}
		} else {
			merchandisingControl.setPreorderable(false);
		}
	}

	private void setDeviceDetailsRating(Device deviceDetails, ProductModel productModel) {
		if (productModel.getRating() != null && productModel.getRating() > 0.0) {
			deviceDetails.setRating(String.valueOf(productModel.getRating()));
		} else {
			deviceDetails.setRating(DATA_NOT_FOUND.toLowerCase());
		}
	}

	/**
	 * 
	 * @param productGroupModelList
	 * @param deviceTile
	 */
	public void setDeviceDetailsRatingForHandsetOnlineModel(Device deviceDetails,
			List<DeviceOnlineModel> productGroupModelList) {
		productGroupModelList.stream().forEach(productGroupModel -> {
			if (productGroupModel.getLeadNonUpgradeDeviceId() != null) {
				String[] avgCountRating = commonUtility.getRating(productGroupModel.getLeadNonUpgradeDeviceId())
						.split("\\|");
				deviceDetails.setRating(avgCountRating[0]);
			}
		});
	}

	private void setDeviceDetailsName(Map<String, String> groupNameWithProdId, Device deviceDetails,
			ProductModel productModel) {
		if (productModel.getProductGroupName() != null) {
			deviceDetails.setName(productModel.getProductGroupName());
		} else {
			if (groupNameWithProdId != null && groupNameWithProdId.containsKey(productModel.getProductId())) {
				deviceDetails.setName(groupNameWithProdId.get(productModel.getProductId()));
			}
		}
	}

	private String setLeadPlanId(String journeyType, ProductModel productModel) {
		String leadPlanId;
		if (StringUtils.isNotBlank(journeyType) && StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
			leadPlanId = productModel.getUpgradeLeadPlanId();
		} else {
			leadPlanId = productModel.getNonUpgradeLeadPlanId();
		}
		return leadPlanId;
	}

	private String setLeadPlanIdForHandsetOnlineModel(String journeyType,
			com.vf.uk.dal.device.client.entity.catalogue.Device productModel) {
		String leadPlanId = null;
		if (productModel.getNonUpgradeLeadPlanDetails() != null || productModel.getUpgradeLeadPlanDetails() != null) {

			if (StringUtils.isNotBlank(journeyType)
					&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
				leadPlanId = productModel.getUpgradeLeadPlanDetails().getLeadPlanId();
			} else {
				leadPlanId = productModel.getNonUpgradeLeadPlanDetails().getLeadPlanId();
			}
		}
		return leadPlanId;
	}

	private void setDeviceDetailsColourSizeGroupId(Device deviceDetails,
			ProductGroupDetailsForDeviceList groupdeatils) {
		if (groupdeatils != null) {
			deviceDetails.setColor(groupdeatils.getColor());
			deviceDetails.setSize(groupdeatils.getSize());
			deviceDetails.setProductGroupName(groupdeatils.getGroupName());
			deviceDetails.setProductGroupId(groupdeatils.getGroupId());
		}
	}

	private List<NewFacet> setListOfNewFacet(List<FacetField> facetFieldList) {
		List<NewFacet> listOfNewFacet = new ArrayList<>();
		if (facetFieldList != null && !facetFieldList.isEmpty()) {
			for (FacetField facetFields : facetFieldList) {
				setListOfNewFacett(listOfNewFacet, facetFields);
			}
		}
		return listOfNewFacet;
	}

	private List<FacetWithCount> setListOfNewFacett(List<NewFacet> listOfNewFacet, FacetField facetFields) {
		NewFacet newFacet;
		List<FacetWithCount> listOfFacetWithCount = null;
		FacetWithCount facetWithCount;
		if (facetFields.getName().equalsIgnoreCase(STRING_FACET_COLOUR)
				|| facetFields.getName().equalsIgnoreCase(STRING_FACET_CAPACITY) || checkFacetFieldNames(facetFields)) {
			newFacet = new NewFacet();
			if (facetFields.getValues() != null && !facetFields.getValues().isEmpty()) {
				listOfFacetWithCount = new ArrayList<>();
				for (Count count : facetFields.getValues()) {
					facetWithCount = new FacetWithCount();
					facetWithCount.setName(count.getName());
					facetWithCount.setCount(count.getCount());
					listOfFacetWithCount.add(facetWithCount);
				}
			}
			newFacet.setFacetName(facetFields.getName());
			newFacet.setFacetList(listOfFacetWithCount);
			listOfNewFacet.add(newFacet);
		}
		return listOfFacetWithCount;
	}

	private boolean checkFacetFieldNames(FacetField facetFields) {
		return facetFields.getName().equalsIgnoreCase(STRING_FACET_OPERATING_SYSYTEM)
				|| facetFields.getName().equalsIgnoreCase(STRING_MUST_HAVE_FEATURES)
				|| facetFields.getName().equalsIgnoreCase(STRING_EQUIPMENT_MAKE);
	}

	private List<NewFacet> setListOfNewFacetForHandsetOnlineModel(List<FacetField> facetFieldList) {
		List<NewFacet> listOfNewFacet = new ArrayList<>();
		if (facetFieldList != null && !facetFieldList.isEmpty()) {
			for (FacetField facetFields : facetFieldList) {
				setListOfNewFacettForHandsetOnlineModel(listOfNewFacet, facetFields);
			}
		}
		return listOfNewFacet;
	}

	private List<FacetWithCount> setListOfNewFacettForHandsetOnlineModel(List<NewFacet> listOfNewFacet,
			FacetField facetFields) {
		NewFacet newFacet;
		List<FacetWithCount> listOfFacetWithCount = null;
		FacetWithCount facetWithCount;
		if (facetFields.getName().equalsIgnoreCase(STRING_COLOR) || facetFields.getName().equalsIgnoreCase(STRING_SIZE)
				|| checkFacetFieldNamesForHandsetOnlineModel(facetFields)) {
			newFacet = new NewFacet();
			if (facetFields.getValues() != null && !facetFields.getValues().isEmpty()) {
				listOfFacetWithCount = new ArrayList<>();
				for (Count count : facetFields.getValues()) {
					facetWithCount = new FacetWithCount();
					facetWithCount.setName(count.getName());
					facetWithCount.setCount(count.getCount());
					listOfFacetWithCount.add(facetWithCount);
				}
			}
			if (facetFields.getName().equalsIgnoreCase(STRING_COLOR)) {
				newFacet.setFacetName(STRING_FACET_COLOUR);
			} else if (facetFields.getName().equalsIgnoreCase(STRING_SIZE)) {
				newFacet.setFacetName(STRING_FACET_CAPACITY);
			} else if (facetFields.getName().equalsIgnoreCase(STRING_MAKE)) {
				newFacet.setFacetName(STRING_EQUIPMENT_MAKE);
			} else {
				newFacet.setFacetName(facetFields.getName());
			}
			newFacet.setFacetList(listOfFacetWithCount);
			listOfNewFacet.add(newFacet);
		}
		return listOfFacetWithCount;
	}

	private boolean checkFacetFieldNamesForHandsetOnlineModel(FacetField facetFields) {
		return facetFields.getName().equalsIgnoreCase(STRING_FACET_OPERATING_SYSYTEM)
				|| facetFields.getName().equalsIgnoreCase(STRING_MUST_HAVE_FEATURES)
				|| facetFields.getName().equalsIgnoreCase(STRING_MAKE);
	}

	/**
	 * Compare prices and set merchandising promotions.
	 * 
	 * @param priceForBundleAndHardware
	 * @param bundlePrice
	 */
	public void populateMerchandisingPromotions(PriceForBundleAndHardware priceForBundleAndHardware,
			BundlePrice bundlePrice) {
		if (null != priceForBundleAndHardware && null != bundlePrice
				&& null != priceForBundleAndHardware.getBundlePrice()
				&& priceForBundleAndHardware.getBundlePrice().getBundleId().equals(bundlePrice.getBundleId())) {
			com.vf.uk.dal.device.client.entity.price.BundlePrice priceInfo = priceForBundleAndHardware.getBundlePrice();
			if (null != priceInfo.getMonthlyPrice() && null != bundlePrice.getMonthlyPrice()
					&& null == priceInfo.getMerchandisingPromotions()
					&& null != bundlePrice.getMerchandisingPromotions()
					&& null != priceInfo.getMonthlyPrice().getGross()
					&& null != bundlePrice.getMonthlyPrice().getGross()) {

				Float grossPriceInfo = new Float(priceInfo.getMonthlyPrice().getGross());
				Float grossBundlePrice = new Float(bundlePrice.getMonthlyPrice().getGross());
				if (grossPriceInfo.compareTo(grossBundlePrice) == 0) {
					MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
					merchandisingPromotions.setDescription(bundlePrice.getMerchandisingPromotions().getDescription());
					merchandisingPromotions.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
					merchandisingPromotions.setLabel(bundlePrice.getMerchandisingPromotions().getLabel());
					merchandisingPromotions.setMpType(bundlePrice.getMerchandisingPromotions().getMpType());
					merchandisingPromotions.setTag(bundlePrice.getMerchandisingPromotions().getTag());
					merchandisingPromotions
							.setPromotionMedia(bundlePrice.getMerchandisingPromotions().getPromotionMedia());

					merchandisingPromotions.setPriceEstablishedLabel(
							bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
					priceForBundleAndHardware.getBundlePrice().setMerchandisingPromotions(merchandisingPromotions);

					mapDiscountMonthlyPrice(priceForBundleAndHardware, bundlePrice);
				}

			}

		}

	}

	/**
	 * Map discount monthly price response.
	 * 
	 * @param priceForBundleAndHardware
	 * @param bundlePrice
	 */
	private void mapDiscountMonthlyPrice(PriceForBundleAndHardware priceForBundleAndHardware, BundlePrice bundlePrice) {
		if (null != bundlePrice.getMonthlyDiscountPrice() && null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
			Price price = new Price();
			price.setGross(bundlePrice.getMonthlyDiscountPrice().getGross());
			price.setNet(bundlePrice.getMonthlyDiscountPrice().getNet());
			price.setVat(bundlePrice.getMonthlyDiscountPrice().getVat());

			priceForBundleAndHardware.setMonthlyDiscountPrice(price);
			priceForBundleAndHardware.getBundlePrice().setMonthlyDiscountPrice(price);
		}

	}

	/**
	 * 
	 * @param deviceFinancingOption
	 * @return List <DeviceFinancingOption>
	 */
	public List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> getDeviceFinaceOptions(
			List<DeviceFinancingOption> deviceFinancingOption) {
		List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> financeOptions = null;
		if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
			financeOptions = new ArrayList<>();
			for (DeviceFinancingOption financsOption : deviceFinancingOption) {
				com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption finance = new com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption();
				finance.setApr(financsOption.getApr());
				finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
				finance.setFinanceProvider(financsOption.getFinanceProvider());
				finance.setFinanceTerm(financsOption.getFinanceTerm());
				com.vf.uk.dal.device.model.product.Price monthly = financsOption.getMonthlyPrice();
				com.vf.uk.dal.device.client.entity.price.Price deviceMonthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
				deviceMonthlyPrice.setGross(monthly.getGross());
				deviceMonthlyPrice.setNet(monthly.getNet());
				deviceMonthlyPrice.setVat(monthly.getVat());
				finance.setMonthlyPrice(deviceMonthlyPrice);
				com.vf.uk.dal.device.model.product.Price totalInterest = financsOption.getTotalPriceWithInterest();
				com.vf.uk.dal.device.client.entity.price.Price totalPriceWithInterest = new com.vf.uk.dal.device.client.entity.price.Price();
				totalPriceWithInterest.setGross(totalInterest.getGross());
				totalPriceWithInterest.setNet(totalInterest.getNet());
				totalPriceWithInterest.setVat(totalInterest.getVat());
				finance.setTotalPriceWithInterest(totalPriceWithInterest);
				financeOptions.add(finance);
			}
		}
		return financeOptions;
	}

	/**
	 * 
	 * @param financeOption
	 * @return List <DeviceFinancingOption>
	 */
	public List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> getDeviceFinaceOptionsForHandsetOnlineModel(
			List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> financeOption) {
		List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> financeOptions = null;
		if (financeOption != null && !financeOption.isEmpty()) {
			financeOptions = new ArrayList<>();
			for (com.vf.uk.dal.device.model.solr.DeviceFinancingOption financsOption : financeOption) {
				com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption finance = new com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption();
				finance.setApr(financsOption.getApr());
				finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
				finance.setFinanceProvider(financsOption.getFinanceProvider());
				finance.setFinanceTerm(financsOption.getFinanceTerm());
				com.vf.uk.dal.device.model.solr.Price monthly = financsOption.getMonthlyPrice();
				com.vf.uk.dal.device.client.entity.price.Price deviceMonthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
				deviceMonthlyPrice.setGross(monthly.getGross());
				deviceMonthlyPrice.setNet(monthly.getNet());
				deviceMonthlyPrice.setVat(monthly.getVat());
				finance.setMonthlyPrice(deviceMonthlyPrice);
				com.vf.uk.dal.device.model.solr.Price totalInterest = financsOption.getTotalPriceWithInterest();
				com.vf.uk.dal.device.client.entity.price.Price totalPriceWithInterest = new com.vf.uk.dal.device.client.entity.price.Price();
				totalPriceWithInterest.setGross(totalInterest.getGross());
				totalPriceWithInterest.setNet(totalInterest.getNet());
				totalPriceWithInterest.setVat(totalInterest.getVat());
				finance.setTotalPriceWithInterest(totalPriceWithInterest);
				financeOptions.add(finance);
			}
		}
		return financeOptions;
	}

	/**
	 * 
	 * @param productModel
	 * @param bundleModel
	 * @return PriceForBundleAndHardware
	 */
	public PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrUtils(List<OfferAppliedPriceModel> offers,
			String leadPlanId) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		if (offers != null && !offers.isEmpty()) {
			offers.forEach(offer -> {
				if (offer.getBundleId() != null && leadPlanId != null && offer.getBundleId().equals(leadPlanId)) {
					setHardwarePriceForOffer(priceForBundleAndHardware, hardwarePrice, offer);
					setBundlePriceForOffer(priceForBundleAndHardware, bundlePrice, offer);
				}
			});
		}

		return priceForBundleAndHardware;

	}

	/**
	 * 
	 * @param productModel
	 * @param bundleModel
	 * @return PriceForBundleAndHardware
	 */
	public PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrUtilsForHandsetOnlineModel(
			PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel, String leadPlanId) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		if (pricePromotionHandsetPlanModel != null && pricePromotionHandsetPlanModel.getPlanId().equals(leadPlanId)) {
			setHardwarePriceForOfferForHandsetOnlineModel(priceForBundleAndHardware, hardwarePrice,
					pricePromotionHandsetPlanModel, pricePromotionHandsetPlanModel.getHardwarePrice());
			setBundlePriceForOfferForHandsetOnlineModel(priceForBundleAndHardware, bundlePrice,
					pricePromotionHandsetPlanModel, pricePromotionHandsetPlanModel.getBundlePrice());
		}

		return priceForBundleAndHardware;

	}

	private void setBundlePriceForOffer(PriceForBundleAndHardware priceForBundleAndHardware,
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice, OfferAppliedPriceModel offer) {
		if (offer.getMonthlyDiscountedGrossPrice() != null && offer.getMonthlyGrossPrice() != null
				&& offer.getMonthlyDiscountedGrossPrice().equals(offer.getMonthlyGrossPrice())) {
			Price monthlyDiscountPrice = new Price();
			monthlyDiscountPrice.setGross(null);
			monthlyDiscountPrice.setNet(null);
			monthlyDiscountPrice.setVat(null);
			Price monthlyPrice = new Price();

			monthlyPrice.setGross(commonUtility.getpriceFormat(offer.getMonthlyGrossPrice()));
			monthlyPrice.setNet(commonUtility.getpriceFormat(offer.getMonthlyNetPrice()));
			monthlyPrice.setVat(commonUtility.getpriceFormat(offer.getMonthlyVatPrice()));
			bundlePrice.setMonthlyPrice(monthlyPrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
			bundlePrice.setBundleId(offer.getBundleId());
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
			priceForBundleAndHardware.setBundlePrice(bundlePrice);
		} else {
			Price monthlyDiscountPrice = new Price();

			monthlyDiscountPrice.setGross(commonUtility.getpriceFormat(offer.getMonthlyDiscountedGrossPrice()));
			monthlyDiscountPrice.setNet(commonUtility.getpriceFormat(offer.getMonthlyDiscountedNetPrice()));
			monthlyDiscountPrice.setVat(commonUtility.getpriceFormat(offer.getMonthlyDiscountedVatPrice()));
			Price monthlyPrice = new Price();

			monthlyPrice.setGross(commonUtility.getpriceFormat(offer.getMonthlyGrossPrice()));
			monthlyPrice.setNet(commonUtility.getpriceFormat(offer.getMonthlyNetPrice()));
			monthlyPrice.setVat(commonUtility.getpriceFormat(offer.getMonthlyVatPrice()));
			bundlePrice.setMonthlyPrice(monthlyPrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
			bundlePrice.setBundleId(offer.getBundleId());
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
			priceForBundleAndHardware.setBundlePrice(bundlePrice);
		}
	}

	private void setHardwarePriceForOffer(PriceForBundleAndHardware priceForBundleAndHardware,
			HardwarePrice hardwarePrice, OfferAppliedPriceModel offer) {
		if (offer.getOneOffDiscountedGrossPrice() != null && offer.getOneOffGrossPrice() != null
				&& offer.getOneOffGrossPrice().equals(offer.getOneOffDiscountedGrossPrice())) {
			Price oneOffDiscountPrice = new Price();
			oneOffDiscountPrice.setGross(null);
			oneOffDiscountPrice.setNet(null);
			oneOffDiscountPrice.setVat(null);
			Price oneOffPrice = new Price();

			oneOffPrice.setGross(commonUtility.getpriceFormat(offer.getOneOffGrossPrice()));
			oneOffPrice.setNet(commonUtility.getpriceFormat(offer.getOneOffNetPrice()));
			oneOffPrice.setVat(commonUtility.getpriceFormat(offer.getOneOffVatPrice()));
			hardwarePrice.setOneOffPrice(oneOffPrice);
			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(offer.getProductId());
			priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
			priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
			priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
		} else {
			Price oneOffDiscountPrice = new Price();

			oneOffDiscountPrice.setGross(commonUtility.getpriceFormat(offer.getOneOffDiscountedGrossPrice()));
			oneOffDiscountPrice.setNet(commonUtility.getpriceFormat(offer.getOneOffDiscountedNetPrice()));
			oneOffDiscountPrice.setVat(commonUtility.getpriceFormat(offer.getOneOffDiscountedVatPrice()));
			Price oneOffPrice = new Price();

			oneOffPrice.setGross(commonUtility.getpriceFormat(offer.getOneOffGrossPrice()));
			oneOffPrice.setNet(commonUtility.getpriceFormat(offer.getOneOffNetPrice()));
			oneOffPrice.setVat(commonUtility.getpriceFormat(offer.getOneOffVatPrice()));
			hardwarePrice.setOneOffPrice(oneOffPrice);
			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(offer.getProductId());
			priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
			priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
			priceForBundleAndHardware.setHardwarePrice(hardwarePrice);

		}
	}

	private void setBundlePriceForOfferForHandsetOnlineModel(PriceForBundleAndHardware priceForBundleAndHardware,
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice,
			PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel,
			com.vf.uk.dal.device.model.solr.BundlePrice bundlePriceFromMap) {
		if (bundlePriceFromMap != null && bundlePriceFromMap.getMonthlyDiscountPrice() != null
				&& bundlePriceFromMap.getMonthlyPrice() != null) {
			if (bundlePriceFromMap.getMonthlyDiscountPrice().getGross()
					.equals(bundlePriceFromMap.getMonthlyPrice().getGross())) {
				Price monthlyDiscountPrice = new Price();
				monthlyDiscountPrice.setGross(null);
				monthlyDiscountPrice.setNet(null);
				monthlyDiscountPrice.setVat(null);
				Price monthlyPrice = new Price();

				monthlyPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getGross())));
				monthlyPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getNet())));
				monthlyPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getVat())));
				bundlePrice.setMonthlyPrice(monthlyPrice);
				bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
				bundlePrice.setBundleId(bundlePriceFromMap.getBundleId());
				setPricePromotionBundle(bundlePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
				priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
				priceForBundleAndHardware.setBundlePrice(bundlePrice);
			} else {
				Price monthlyDiscountPrice = new Price();

				monthlyDiscountPrice.setGross(commonUtility
						.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyDiscountPrice().getGross())));
				monthlyDiscountPrice.setNet(commonUtility
						.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyDiscountPrice().getNet())));
				monthlyDiscountPrice.setVat(commonUtility
						.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyDiscountPrice().getVat())));
				Price monthlyPrice = new Price();

				monthlyPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getGross())));
				monthlyPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getNet())));
				monthlyPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getVat())));
				bundlePrice.setMonthlyPrice(monthlyPrice);
				bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
				bundlePrice.setBundleId(bundlePriceFromMap.getBundleId());
				setPricePromotionBundle(bundlePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
				priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
				priceForBundleAndHardware.setBundlePrice(bundlePrice);
			}
		}
	}

	private void setPricePromotionBundle(com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice,
			PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel) {
		if (pricePromotionHandsetPlanModel.getPromotionsPackage() != null
				&& pricePromotionHandsetPlanModel.getPromotionsPackage().getBundlePromotions() != null
				&& pricePromotionHandsetPlanModel.getPromotionsPackage().getBundlePromotions()
						.getPricePromotion() != null) {
			bundlePrice.setMerchandisingPromotions(
					pricePromotionHandsetPlanModel.getPromotionsPackage().getBundlePromotions().getPricePromotion());
		}
	}

	private void setHardwarePriceForOfferForHandsetOnlineModel(PriceForBundleAndHardware priceForBundleAndHardware,
			HardwarePrice hardwarePrice, PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel,
			com.vf.uk.dal.device.model.solr.HardwarePrice hardwarePriceFromMap) {
		if (hardwarePriceFromMap != null && hardwarePriceFromMap.getOneOffDiscountPrice() != null
				&& hardwarePriceFromMap.getOneOffPrice() != null) {
			if (hardwarePriceFromMap.getOneOffPrice().getGross()
					.equals(hardwarePriceFromMap.getOneOffDiscountPrice().getGross())) {
				Price oneOffDiscountPrice = new Price();
				oneOffDiscountPrice.setGross(null);
				oneOffDiscountPrice.setNet(null);
				oneOffDiscountPrice.setVat(null);
				Price oneOffPrice = new Price();

				oneOffPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getGross())));
				oneOffPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getNet())));
				oneOffPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getVat())));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(hardwarePriceFromMap.getHardwareId());
				setPricePromotionHardware(hardwarePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			} else {
				Price oneOffDiscountPrice = new Price();

				oneOffDiscountPrice.setGross(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getGross())));
				oneOffDiscountPrice.setNet(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getNet())));
				oneOffDiscountPrice.setVat(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getVat())));
				Price oneOffPrice = new Price();
				oneOffPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getGross())));
				oneOffPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getNet())));
				oneOffPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getVat())));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(hardwarePriceFromMap.getHardwareId());
				setPricePromotionHardware(hardwarePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			}
		}
	}

	private void setPricePromotionHardware(HardwarePrice hardwarePrice,
			PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel) {
		if (pricePromotionHandsetPlanModel.getPromotionsPackage() != null
				&& pricePromotionHandsetPlanModel.getPromotionsPackage().getHardwarePromotions() != null
				&& pricePromotionHandsetPlanModel.getPromotionsPackage().getHardwarePromotions()
						.getPricePromotion() != null) {
			hardwarePrice.setMerchandisingPromotions(
					pricePromotionHandsetPlanModel.getPromotionsPackage().getHardwarePromotions().getPricePromotion());
		}
	}

	/**
	 * 
	 * @param productModel
	 * @param bundleModel
	 * @param leadPlanId
	 * @return PriceForBundleAndHardware
	 */
	public PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrWithoutOfferCode(ProductModel productModel,
			BundleModel bundleModel, String leadPlanId, String groupType) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		if (productModel != null) {
			if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYM)) {
				setHardwarePricePaym(productModel, priceForBundleAndHardware, hardwarePrice);
				setBundlePricePaym(productModel, leadPlanId, priceForBundleAndHardware, bundlePrice);
				if (bundleModel != null) {
					setMonthlyDiscountPriceBundle(bundleModel, priceForBundleAndHardware, bundlePrice);
				}
			} else if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
				setPriceForPayg(productModel, priceForBundleAndHardware, hardwarePrice);
			}
		}
		return priceForBundleAndHardware;
	}

	private void setPriceForPayg(ProductModel productModel, PriceForBundleAndHardware priceForBundleAndHardware,
			HardwarePrice hardwarePrice) {
		if (productModel.getPaygOneOffDiscountedGrossPrice() != null && productModel.getPaygOneOffGrossPrice() != null
				&& productModel.getPaygOneOffGrossPrice().equals(productModel.getPaygOneOffDiscountedGrossPrice())) {
			BundlePrice bundlePriceLocal = new BundlePrice();
			Price monthlyPrice = new Price();
			monthlyPrice.setGross(null);
			monthlyPrice.setNet(null);
			monthlyPrice.setVat(null);
			bundlePriceLocal.setMonthlyPrice(monthlyPrice);
			bundlePriceLocal.setMonthlyDiscountPrice(monthlyPrice);
			Price oneOffDiscountPrice = new Price();
			oneOffDiscountPrice.setGross(null);
			oneOffDiscountPrice.setNet(null);
			oneOffDiscountPrice.setVat(null);
			Price oneOffPrice = new Price();

			oneOffPrice.setGross(commonUtility.getpriceFormat(productModel.getPaygOneOffGrossPrice()));
			oneOffPrice.setNet(commonUtility.getpriceFormat(productModel.getPaygOneOffNetPrice()));
			oneOffPrice.setVat(commonUtility.getpriceFormat(productModel.getPaygOneOffVatPrice()));
			hardwarePrice.setOneOffPrice(oneOffPrice);
			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(productModel.getProductId());
			hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(productModel.getFinancingOptions()));
			priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
			priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
			priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			priceForBundleAndHardware.setBundlePrice(bundlePriceLocal);
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyPrice);
		} else if (productModel.getPaygOneOffDiscountedGrossPrice() != null
				&& productModel.getPaygOneOffGrossPrice() != null
				&& !productModel.getPaygOneOffGrossPrice().equals(productModel.getPaygOneOffDiscountedGrossPrice())) {
			BundlePrice bundlePriceLocal = new BundlePrice();
			Price monthlyPrice = new Price();
			monthlyPrice.setGross(null);
			monthlyPrice.setNet(null);
			monthlyPrice.setVat(null);
			bundlePriceLocal.setMonthlyPrice(monthlyPrice);
			bundlePriceLocal.setMonthlyDiscountPrice(monthlyPrice);
			Price oneOffDiscountPrice = new Price();
			oneOffDiscountPrice
					.setGross(commonUtility.getpriceFormat(productModel.getPaygOneOffDiscountedGrossPrice()));
			oneOffDiscountPrice.setNet(commonUtility.getpriceFormat(productModel.getPaygOneOffDiscountedNetPrice()));
			oneOffDiscountPrice.setVat(commonUtility.getpriceFormat(productModel.getPaygOneOffDiscountedVatPrice()));
			Price oneOffPrice = new Price();
			oneOffPrice.setGross(commonUtility.getpriceFormat(productModel.getPaygOneOffGrossPrice()));
			oneOffPrice.setNet(commonUtility.getpriceFormat(productModel.getPaygOneOffNetPrice()));
			oneOffPrice.setVat(commonUtility.getpriceFormat(productModel.getPaygOneOffVatPrice()));
			hardwarePrice.setOneOffPrice(oneOffPrice);
			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(productModel.getProductId());
			hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(productModel.getFinancingOptions()));
			priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
			priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
			priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			priceForBundleAndHardware.setBundlePrice(bundlePriceLocal);
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyPrice);
		}
	}

	private void setMonthlyDiscountPriceBundle(BundleModel bundleModel,
			PriceForBundleAndHardware priceForBundleAndHardware,
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice) {
		if (bundleModel.getMonthlyDiscountedGrossPrice() != null && bundleModel.getMonthlyGrossPrice() != null
				&& bundleModel.getMonthlyDiscountedGrossPrice().equals(bundleModel.getMonthlyGrossPrice())) {
			Price monthlyDiscountPrice = new Price();
			monthlyDiscountPrice.setGross(null);
			monthlyDiscountPrice.setNet(null);
			monthlyDiscountPrice.setVat(null);
			Price monthlyPrice = new Price();
			monthlyPrice.setGross(commonUtility.getpriceFormat(bundleModel.getMonthlyGrossPrice()));
			monthlyPrice.setNet(commonUtility.getpriceFormat(bundleModel.getMonthlyNetPrice()));
			monthlyPrice.setVat(commonUtility.getpriceFormat(bundleModel.getMonthlyVatPrice()));
			bundlePrice.setMonthlyPrice(monthlyPrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
			bundlePrice.setBundleId(bundleModel.getBundleId());
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
			priceForBundleAndHardware.setBundlePrice(bundlePrice);
		} else {
			Price monthlyDiscountPrice = new Price();
			monthlyDiscountPrice.setGross(commonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedGrossPrice()));
			monthlyDiscountPrice.setNet(commonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedNetPrice()));
			monthlyDiscountPrice.setVat(commonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedVatPrice()));
			Price monthlyPrice = new Price();
			monthlyPrice.setGross(commonUtility.getpriceFormat(bundleModel.getMonthlyGrossPrice()));
			monthlyPrice.setNet(commonUtility.getpriceFormat(bundleModel.getMonthlyNetPrice()));
			monthlyPrice.setVat(commonUtility.getpriceFormat(bundleModel.getMonthlyVatPrice()));
			bundlePrice.setMonthlyPrice(monthlyPrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
			bundlePrice.setBundleId(bundleModel.getBundleId());
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
			priceForBundleAndHardware.setBundlePrice(bundlePrice);
		}
	}

	private void setBundlePricePaym(ProductModel productModel, String leadPlanId,
			PriceForBundleAndHardware priceForBundleAndHardware,
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice) {
		if (productModel.getBundleMonthlyDiscPriceGross() != null && productModel.getBundleMonthlyPriceGross() != null
				&& productModel.getBundleMonthlyDiscPriceGross().equals(productModel.getBundleMonthlyPriceGross())) {
			Price monthlyDiscountPrice = new Price();
			monthlyDiscountPrice.setGross(null);
			monthlyDiscountPrice.setNet(null);
			monthlyDiscountPrice.setVat(null);
			Price monthlyPrice = new Price();
			monthlyPrice.setGross(commonUtility.getpriceFormat(productModel.getBundleMonthlyPriceGross()));
			monthlyPrice.setNet(commonUtility.getpriceFormat(productModel.getBundleMonthlyPriceNet()));
			monthlyPrice.setVat(commonUtility.getpriceFormat(productModel.getBundleMonthlyPriceVat()));
			bundlePrice.setMonthlyPrice(monthlyPrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
			bundlePrice.setBundleId(leadPlanId);
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
			priceForBundleAndHardware.setBundlePrice(bundlePrice);
		} else {
			Price monthlyDiscountPrice = new Price();
			monthlyDiscountPrice.setGross(commonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceGross()));
			monthlyDiscountPrice.setNet(commonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceNet()));
			monthlyDiscountPrice.setVat(commonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceVat()));
			Price monthlyPrice = new Price();
			monthlyPrice.setGross(commonUtility.getpriceFormat(productModel.getBundleMonthlyPriceGross()));
			monthlyPrice.setNet(commonUtility.getpriceFormat(productModel.getBundleMonthlyPriceNet()));
			monthlyPrice.setVat(commonUtility.getpriceFormat(productModel.getBundleMonthlyPriceVat()));
			bundlePrice.setMonthlyPrice(monthlyPrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
			bundlePrice.setBundleId(leadPlanId);
			priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
			priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
			priceForBundleAndHardware.setBundlePrice(bundlePrice);
		}
	}

	private void setHardwarePricePaym(ProductModel productModel, PriceForBundleAndHardware priceForBundleAndHardware,
			HardwarePrice hardwarePrice) {
		if (productModel.getOneOffDiscountedGrossPrice() != null && productModel.getOneOffGrossPrice() != null
				&& productModel.getOneOffGrossPrice().equals(productModel.getOneOffDiscountedGrossPrice())) {
			Price oneOffDiscountPrice = new Price();
			oneOffDiscountPrice.setGross(null);
			oneOffDiscountPrice.setNet(null);
			oneOffDiscountPrice.setVat(null);
			Price oneOffPrice = new Price();

			oneOffPrice.setGross(commonUtility.getpriceFormat(productModel.getOneOffGrossPrice()));
			oneOffPrice.setNet(commonUtility.getpriceFormat(productModel.getOneOffNetPrice()));
			oneOffPrice.setVat(commonUtility.getpriceFormat(productModel.getOneOffVatPrice()));
			hardwarePrice.setOneOffPrice(oneOffPrice);
			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(productModel.getProductId());
			priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
			priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
			priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
		} else if (productModel.getOneOffDiscountedGrossPrice() != null && productModel.getOneOffGrossPrice() != null
				&& !productModel.getOneOffGrossPrice().equals(productModel.getOneOffDiscountedGrossPrice())) {
			Price oneOffDiscountPrice = new Price();
			oneOffDiscountPrice.setGross(commonUtility.getpriceFormat(productModel.getOneOffDiscountedGrossPrice()));
			oneOffDiscountPrice.setNet(commonUtility.getpriceFormat(productModel.getOneOffDiscountedNetPrice()));
			oneOffDiscountPrice.setVat(commonUtility.getpriceFormat(productModel.getOneOffDiscountedVatPrice()));
			Price oneOffPrice = new Price();
			oneOffPrice.setGross(commonUtility.getpriceFormat(productModel.getOneOffGrossPrice()));
			oneOffPrice.setNet(commonUtility.getpriceFormat(productModel.getOneOffNetPrice()));
			oneOffPrice.setVat(commonUtility.getpriceFormat(productModel.getOneOffVatPrice()));
			hardwarePrice.setOneOffPrice(oneOffPrice);
			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(productModel.getProductId());
			priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
			priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
			priceForBundleAndHardware.setHardwarePrice(hardwarePrice);

		}
	}

	public Boolean getPreOrBackOderable(String preOrderable) {
		Boolean result;
		if (IS_PREORDERABLE_YES.equalsIgnoreCase(preOrderable)) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @param promotions
	 * @param entertainmentPacks
	 * @param dataAllowances
	 * @param planCouplingPromotions
	 * @param sash
	 * @param secureNet
	 * @param sashBannerForHardwares
	 * @param freeExtras
	 * @param freeAccessories
	 * @param freeExtrasForPlans
	 * @param freeAccForPlans
	 * @param freeExtrasForHardwares
	 * @param freeAccForHardwares
	 * @return MerchandisingPromotionsPackage
	 */
	public MerchandisingPromotionsPackage assembleMerchandisingPromotion(BundleAndHardwarePromotions promotions,
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks,
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash,
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet,
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional) {

		MerchandisingPromotionsPackage promotionsPackage = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper bundlePromotions = new MerchandisingPromotionsWrapper();
		MerchandisingPromotionsWrapper hardwarePromotions = new MerchandisingPromotionsWrapper();

		setDataPromo(dataAllowances, bundlePromotions);
		setSecureNetPromo(secureNet, bundlePromotions);

		setSashBannerPromo(sash, bundlePromotions);

		setEntertainmentPackPromo(entertainmentPacks, bundlePromotions);

		setFreeExtraPromo(freeExtrasForPlans, bundlePromotions);

		if (CollectionUtils.isNotEmpty(freeAccForPlans)) {
			/* Assembly of freeAccessoryPromotion */
			MerchandisingPromotion freeAccessoryPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForPlan = freeAccForPlans.get(0);
			freeAccessoryPromotion.setTag(freeAccForPlan.getTag());
			freeAccessoryPromotion.setDescription(freeAccForPlan.getDescription());
			freeAccessoryPromotion.setLabel(freeAccForPlan.getLabel());
			freeAccessoryPromotion.setMpType(freeAccForPlan.getType());
			freeAccessoryPromotion.setPackageType(freeAccForPlan.getPackageType());
			freeAccessoryPromotion.setFootNotes(freeAccForPlan.getFootNotes());
			if (StringUtils.isNotBlank(freeAccForPlan.getPriority())) {
				freeAccessoryPromotion.setPriority(Integer.valueOf(freeAccForPlan.getPriority()));
			}

			if (StringUtils.isNotBlank(freeAccForPlan.getPromotionMedia())) {
				freeAccessoryPromotion.setPromotionMedia(freeAccForPlan.getPromotionMedia());
			}

			bundlePromotions.setFreeAccessoryPromotion(freeAccessoryPromotion);
		}

		if (CollectionUtils.isNotEmpty(sashBannerForHardwares)) {
			/* Assembly of sashBannerForHardware */
			MerchandisingPromotion sashBannerPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForHardwareSash sashBannerForHardware = sashBannerForHardwares.get(0);
			sashBannerPromotion.setTag(sashBannerForHardware.getTag());
			sashBannerPromotion.setDescription(sashBannerForHardware.getDescription());
			sashBannerPromotion.setLabel(sashBannerForHardware.getLabel());
			sashBannerPromotion.setMpType(sashBannerForHardware.getType());
			sashBannerPromotion.setPackageType(sashBannerForHardware.getPackageType());
			sashBannerPromotion.setFootNotes(sashBannerForHardware.getFootNotes());
			if (StringUtils.isNotBlank(sashBannerForHardware.getPriority())) {
				sashBannerPromotion.setPriority(Integer.valueOf(sashBannerForHardware.getPriority()));
			}
			if (StringUtils.isNotBlank(sashBannerForHardware.getPromotionMedia())) {
				sashBannerPromotion.setPromotionMedia(sashBannerForHardware.getPromotionMedia());
			}
			hardwarePromotions.setSashBannerPromotion(sashBannerPromotion);
		}
		setConditionalSashBannerPromo(sashBundleConditional, hardwarePromotions);

		if (CollectionUtils.isNotEmpty(freeAccForHardwares)) {
			/* Assembly of freeAccForHardware */
			MerchandisingPromotion freeAccessoryPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForHardware = freeAccForHardwares.get(0);
			freeAccessoryPromotion.setTag(freeAccForHardware.getTag());
			freeAccessoryPromotion.setDescription(freeAccForHardware.getDescription());
			freeAccessoryPromotion.setLabel(freeAccForHardware.getLabel());
			freeAccessoryPromotion.setMpType(freeAccForHardware.getType());
			freeAccessoryPromotion.setPackageType(freeAccForHardware.getPackageType());
			freeAccessoryPromotion.setFootNotes(freeAccForHardware.getFootNotes());
			if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
				freeAccessoryPromotion.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
			}
			if (StringUtils.isNotBlank(freeAccForHardware.getPromotionMedia())) {
				freeAccessoryPromotion.setPromotionMedia(freeAccForHardware.getPromotionMedia());
			}
			hardwarePromotions.setFreeAccessoryPromotion(freeAccessoryPromotion);
		}

		setFreeExtraPromo(freeExtrasForHardwares, hardwarePromotions);
		promotionsPackage.setPlanId(promotions.getBundleId());
		promotionsPackage.setHardwareId(promotions.getHardwareId());
		promotionsPackage.setHardwarePromotions(hardwarePromotions);
		promotionsPackage.setBundlePromotions(bundlePromotions);

		return promotionsPackage;

	}

	private void setConditionalSashBannerPromo(
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional,
			MerchandisingPromotionsWrapper hardwarePromotions) {
		if (CollectionUtils.isNotEmpty(sashBundleConditional)) {
			/* Assembly of sashBannerForHardware */
			MerchandisingPromotion sashBannerConditionalMerch = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareSash sashBannerCondition = sashBundleConditional.get(0);
			sashBannerConditionalMerch.setTag(sashBannerCondition.getTag());
			sashBannerConditionalMerch.setDescription(sashBannerCondition.getDescription());
			sashBannerConditionalMerch.setLabel(sashBannerCondition.getLabel());
			sashBannerConditionalMerch.setMpType(sashBannerCondition.getType());
			sashBannerConditionalMerch.setPackageType(sashBannerCondition.getPackageType());
			sashBannerConditionalMerch.setFootNotes(sashBannerCondition.getFootNotes());
			if (StringUtils.isNotBlank(sashBannerCondition.getPriority())) {
				sashBannerConditionalMerch.setPriority(Integer.valueOf(sashBannerCondition.getPriority()));
			}
			if (StringUtils.isNotBlank(sashBannerCondition.getPromotionMedia())) {
				sashBannerConditionalMerch.setPromotionMedia(sashBannerCondition.getPromotionMedia());
			}
			hardwarePromotions.setConditionalSashBannerPromotion(sashBannerConditionalMerch);
		}
	}

	private void setFreeExtraPromo(List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(freeExtrasForPlans)) {
			/* Assembly of FreeExtrasForPlan */
			MerchandisingPromotion freeExtraPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForPlan = freeExtrasForPlans.get(0);
			freeExtraPromotion.setTag(freeExtrasForPlan.getTag());
			freeExtraPromotion.setDescription(freeExtrasForPlan.getDescription());
			freeExtraPromotion.setLabel(freeExtrasForPlan.getLabel());
			freeExtraPromotion.setMpType(freeExtrasForPlan.getType());
			freeExtraPromotion.setPackageType(freeExtrasForPlan.getPackageType());
			freeExtraPromotion.setFootNotes(freeExtrasForPlan.getFootNotes());
			if (StringUtils.isNotBlank(freeExtrasForPlan.getPriority())) {
				freeExtraPromotion.setPriority(Integer.valueOf(freeExtrasForPlan.getPriority()));
			}
			if (StringUtils.isNotBlank(freeExtrasForPlan.getPromotionMedia())) {
				freeExtraPromotion.setPromotionMedia(freeExtrasForPlan.getPromotionMedia());
			}

			bundlePromotions.setFreeExtraPromotion(freeExtraPromotion);
		}
	}

	private void setEntertainmentPackPromo(
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(entertainmentPacks)) {
			/* Assembly of entertainmentPackPromotion */
			MerchandisingPromotion entertainmentPackPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainmentPack = entertainmentPacks
					.get(0);
			entertainmentPackPromotion.setTag(entertainmentPack.getTag());
			entertainmentPackPromotion.setDescription(entertainmentPack.getDescription());
			entertainmentPackPromotion.setLabel(entertainmentPack.getLabel());
			entertainmentPackPromotion.setMpType(entertainmentPack.getType());
			entertainmentPackPromotion.setPackageType(entertainmentPack.getPackageType());
			entertainmentPackPromotion.setFootNotes(entertainmentPack.getFootNotes());
			entertainmentPackPromotion.setPromotionMedia(entertainmentPack.getPromotionMedia());
			if (StringUtils.isNotBlank(entertainmentPack.getPriority())) {
				entertainmentPackPromotion.setPriority(Integer.valueOf(entertainmentPack.getPriority()));
			}
			bundlePromotions.setEntertainmentPackPromotion(entertainmentPackPromotion);
		}
	}

	private void setSashBannerPromo(List<CataloguepromotionqueriesForBundleAndHardwareSash> sash,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(sash)) {
			/* Assembly of sashBannerPromotion */
			MerchandisingPromotion sashBannerPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareSash sashBannerForPlan = sash.get(0);
			sashBannerPromotion.setTag(sashBannerForPlan.getTag());
			sashBannerPromotion.setDescription(sashBannerForPlan.getDescription());
			sashBannerPromotion.setLabel(sashBannerForPlan.getLabel());
			sashBannerPromotion.setMpType(sashBannerForPlan.getType());
			sashBannerPromotion.setPackageType(sashBannerForPlan.getPackageType());
			sashBannerPromotion.setFootNotes(sashBannerForPlan.getFootNotes());
			if (StringUtils.isNotBlank(sashBannerForPlan.getPriority())) {
				sashBannerPromotion.setPriority(Integer.valueOf(sashBannerForPlan.getPriority()));
			}
			if (StringUtils.isNotBlank(sashBannerForPlan.getPromotionMedia())) {
				sashBannerPromotion.setPromotionMedia(sashBannerForPlan.getPromotionMedia());
			}
			bundlePromotions.setSashBannerPromotion(sashBannerPromotion);
		}
	}

	private void setSecureNetPromo(List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(secureNet)) {
			/* Assembly of secureNetPromotion */
			MerchandisingPromotion secureNetPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareSecureNet secureeNet = secureNet.get(0);
			secureNetPromotion.setTag(secureeNet.getTag());
			secureNetPromotion.setDescription(secureeNet.getDescription());
			secureNetPromotion.setLabel(secureeNet.getLabel());
			secureNetPromotion.setMpType(secureeNet.getType());
			secureNetPromotion.setPackageType(secureeNet.getPackageType());
			secureNetPromotion.setFootNotes(secureeNet.getFootNotes());
			if (StringUtils.isNotBlank(secureeNet.getPriority())) {
				secureNetPromotion.setPriority(Integer.valueOf(secureeNet.getPriority()));
			}
			if (StringUtils.isNotBlank(secureeNet.getPromotionMedia())) {
				secureNetPromotion.setPromotionMedia(secureeNet.getPromotionMedia());
			}
			bundlePromotions.setSecureNetPromotion(secureNetPromotion);
		}
	}

	private void setDataPromo(List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(dataAllowances)) {

			/* Assembly of dataPromotions */
			MerchandisingPromotion dataPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance = dataAllowances.get(0);
			dataPromotion.setTag(dataAllowance.getTag());
			dataPromotion.setDescription(dataAllowance.getDescription());
			dataPromotion.setLabel(dataAllowance.getLabel());
			dataPromotion.setMpType(dataAllowance.getType());
			dataPromotion.setPackageType(dataAllowance.getPackageType());
			dataPromotion.setFootNotes(dataAllowance.getFootNotes());
			if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
				dataPromotion.setPriority(Integer.valueOf(dataAllowance.getPriority()));
			}
			if (StringUtils.isNotBlank(dataAllowance.getPromotionMedia())) {
				dataPromotion.setPromotionMedia(dataAllowance.getPromotionMedia());
			}
			bundlePromotions.setDataPromotion(dataPromotion);
		}
	}

	/**
	 * 
	 * @param pricePromotionHandsetPlanModel
	 * @param bundleModel
	 * @param leadPlanId
	 * @return PriceForBundleAndHardware
	 */
	public PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrWithoutOfferCodeForHandsetOnlineModel(
			PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel, String leadPlanId, String groupType) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		if (pricePromotionHandsetPlanModel != null) {
			if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYM)) {
				setHardwarePricePaymForHandsetOnlineModel(pricePromotionHandsetPlanModel.getHardwarePrice(),
						pricePromotionHandsetPlanModel, priceForBundleAndHardware, hardwarePrice);
				setBundlePricePaymForHandsetOnlineModel(pricePromotionHandsetPlanModel.getBundlePrice(), leadPlanId,
						pricePromotionHandsetPlanModel, priceForBundleAndHardware, bundlePrice);
			} else if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
				setPriceForPaygForHandsetOnlineModel(pricePromotionHandsetPlanModel.getHardwarePrice(),
						priceForBundleAndHardware, hardwarePrice);
			}
		}
		return priceForBundleAndHardware;
	}

	private void setHardwarePricePaymForHandsetOnlineModel(
			com.vf.uk.dal.device.model.solr.HardwarePrice hardwarePriceFromMap,
			PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel,
			PriceForBundleAndHardware priceForBundleAndHardware, HardwarePrice hardwarePrice) {
		if (hardwarePriceFromMap != null && hardwarePriceFromMap.getOneOffDiscountPrice() != null
				&& hardwarePriceFromMap.getOneOffPrice() != null) {
			if (hardwarePriceFromMap.getOneOffPrice().getGross()
					.equals(hardwarePriceFromMap.getOneOffDiscountPrice().getGross())) {
				Price oneOffDiscountPrice = new Price();
				oneOffDiscountPrice.setGross(null);
				oneOffDiscountPrice.setNet(null);
				oneOffDiscountPrice.setVat(null);
				Price oneOffPrice = new Price();

				oneOffPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getGross())));
				oneOffPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getNet())));
				oneOffPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getVat())));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(hardwarePriceFromMap.getHardwareId());
				setPricePromotionHardware(hardwarePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			} else {
				Price oneOffDiscountPrice = new Price();
				oneOffDiscountPrice.setGross(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getGross())));
				oneOffDiscountPrice.setNet(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getNet())));
				oneOffDiscountPrice.setVat(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getVat())));
				Price oneOffPrice = new Price();
				oneOffPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getGross())));
				oneOffPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getNet())));
				oneOffPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getVat())));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(hardwarePriceFromMap.getHardwareId());
				setPricePromotionHardware(hardwarePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			}
		}
	}

	private void setBundlePricePaymForHandsetOnlineModel(com.vf.uk.dal.device.model.solr.BundlePrice bundlePriceFromMap,
			String leadPlanId, PricePromotionHandsetPlanModel pricePromotionHandsetPlanModel,
			PriceForBundleAndHardware priceForBundleAndHardware,
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice) {
		if (bundlePriceFromMap != null && bundlePriceFromMap.getMonthlyDiscountPrice() != null
				&& bundlePriceFromMap.getMonthlyPrice() != null) {
			if (bundlePriceFromMap.getMonthlyPrice().getGross()
					.equals(bundlePriceFromMap.getMonthlyDiscountPrice().getGross())) {
				Price monthlyDiscountPrice = new Price();
				monthlyDiscountPrice.setGross(null);
				monthlyDiscountPrice.setNet(null);
				monthlyDiscountPrice.setVat(null);
				Price monthlyPrice = new Price();
				monthlyPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getGross())));
				monthlyPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getNet())));
				monthlyPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getVat())));
				bundlePrice.setMonthlyPrice(monthlyPrice);
				bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
				bundlePrice.setBundleId(leadPlanId);
				setPricePromotionBundle(bundlePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
				priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
				priceForBundleAndHardware.setBundlePrice(bundlePrice);
			} else {
				Price monthlyDiscountPrice = new Price();
				monthlyDiscountPrice.setGross(commonUtility
						.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyDiscountPrice().getGross())));
				monthlyDiscountPrice.setNet(commonUtility
						.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyDiscountPrice().getNet())));
				monthlyDiscountPrice.setVat(commonUtility
						.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyDiscountPrice().getVat())));
				Price monthlyPrice = new Price();
				monthlyPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getGross())));
				monthlyPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getNet())));
				monthlyPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(bundlePriceFromMap.getMonthlyPrice().getVat())));
				bundlePrice.setMonthlyPrice(monthlyPrice);
				bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
				bundlePrice.setBundleId(leadPlanId);
				setPricePromotionBundle(bundlePrice, pricePromotionHandsetPlanModel);
				priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
				priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
				priceForBundleAndHardware.setBundlePrice(bundlePrice);
			}
		}
	}

	private void setPriceForPaygForHandsetOnlineModel(
			com.vf.uk.dal.device.model.solr.HardwarePrice hardwarePriceFromMap,
			PriceForBundleAndHardware priceForBundleAndHardware, HardwarePrice hardwarePrice) {
		if (hardwarePriceFromMap != null && hardwarePriceFromMap.getOneOffDiscountPrice() != null
				&& hardwarePriceFromMap.getOneOffPrice() != null) {
			if (hardwarePriceFromMap.getOneOffPrice().getGross()
					.equals(hardwarePriceFromMap.getOneOffDiscountPrice().getGross())) {
				BundlePrice bundlePriceLocal = new BundlePrice();
				Price monthlyPrice = new Price();
				monthlyPrice.setGross(null);
				monthlyPrice.setNet(null);
				monthlyPrice.setVat(null);
				bundlePriceLocal.setMonthlyPrice(monthlyPrice);
				bundlePriceLocal.setMonthlyDiscountPrice(monthlyPrice);
				Price oneOffDiscountPrice = new Price();
				oneOffDiscountPrice.setGross(null);
				oneOffDiscountPrice.setNet(null);
				oneOffDiscountPrice.setVat(null);
				Price oneOffPrice = new Price();
				oneOffPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getGross())));
				oneOffPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getNet())));
				oneOffPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getVat())));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(hardwarePriceFromMap.getHardwareId());
				hardwarePrice.setFinancingOptions(
						getDeviceFinaceOptionsForHandsetOnlineModel(hardwarePriceFromMap.getFinancingOptions()));
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
				priceForBundleAndHardware.setBundlePrice(bundlePriceLocal);
				priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
				priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyPrice);
			} else {
				BundlePrice bundlePriceLocal = new BundlePrice();
				Price monthlyPrice = new Price();
				monthlyPrice.setGross(null);
				monthlyPrice.setNet(null);
				monthlyPrice.setVat(null);
				bundlePriceLocal.setMonthlyPrice(monthlyPrice);
				bundlePriceLocal.setMonthlyDiscountPrice(monthlyPrice);
				Price oneOffDiscountPrice = new Price();
				oneOffDiscountPrice.setGross(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getGross())));
				oneOffDiscountPrice.setNet(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getNet())));
				oneOffDiscountPrice.setVat(commonUtility
						.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffDiscountPrice().getVat())));
				Price oneOffPrice = new Price();
				oneOffPrice.setGross(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getGross())));
				oneOffPrice.setNet(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getNet())));
				oneOffPrice.setVat(
						commonUtility.getpriceFormat(Float.valueOf(hardwarePriceFromMap.getOneOffPrice().getVat())));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(hardwarePriceFromMap.getHardwareId());
				hardwarePrice.setFinancingOptions(
						getDeviceFinaceOptionsForHandsetOnlineModel(hardwarePriceFromMap.getFinancingOptions()));
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
				priceForBundleAndHardware.setBundlePrice(bundlePriceLocal);
				priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
				priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyPrice);
			}
		}
	}
}