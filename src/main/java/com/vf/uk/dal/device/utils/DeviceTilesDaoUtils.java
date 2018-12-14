package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.device.client.entity.bundle.BundleModel;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
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
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions;
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
import com.vf.uk.dal.device.model.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.CommercialProduct;
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
public class DeviceTilesDaoUtils {

	public static final String DATA_NOT_FOUND = "NA";
	public static final String IS_PREORDERABLE_YES = "true";
	private static String leadMember = "leadMember";
	public static final String LIMITED_TIME_DISCOUNT = "limited_time";
	public static final String FULL_DURATION_DISCOUNT = "full_duration";
	public static final String CONDITIONAL_LIMITED_DISCOUNT = "conditional_limited_discount";
	public static final String CONDITIONAL_FULL_DISCOUNT = "conditional_full_discount";
	public static final String STRING_FACET_COLOUR = "FacetColour";
	public static final String STRING_FACET_CAPACITY = "Capacity";
	public static final String STRING_FACET_OPERATING_SYSYTEM = "OperatingSystem";
	public static final String STRING_MUST_HAVE_FEATURES = "MustHaveFeatures";
	public static final String STRING_EQUIPMENT_MAKE = "EquipmentMake";
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

	/**
	 * @author manoj.bera
	 * @param priceForBundleAndHardware
	 * @param deviceId
	 * @param isConditionalAcceptJourney
	 * @param comBundle
	 * @return PriceForBundleAndHardware
	 */
	public static PriceForBundleAndHardware getBundleAndHardwarePrice(
			PriceForBundleAndHardware priceForBundleAndHardware, String deviceId, boolean isConditionalAcceptJourney,
			CommercialBundle comBundle) {
		PriceForBundleAndHardware priceForBundleAndHardware1 = null;
		if (priceForBundleAndHardware != null) {
			if (priceForBundleAndHardware.getHardwarePrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getHardwareId() != null) {
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
		}
		return priceForBundleAndHardware1;
	}

	/**
	 * @author manoj.bera
	 * @param priceForBundleAndHardware1
	 * @return PriceForBundleAndHardware
	 */
	public static PriceForBundleAndHardware getCalculatedPrice(PriceForBundleAndHardware priceForBundleAndHardware1) {
		if (priceForBundleAndHardware1.getHardwarePrice() != null
				&& priceForBundleAndHardware1.getHardwarePrice().getOneOffPrice() != null
				&& priceForBundleAndHardware1.getOneOffDiscountPrice() != null
				&& priceForBundleAndHardware1.getOneOffDiscountPrice().getGross() != null
				&& priceForBundleAndHardware1.getOneOffPrice().getGross() != null)
			if (priceForBundleAndHardware1.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
					priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
				priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().setGross(null);
				priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().setVat(null);
				priceForBundleAndHardware1.getHardwarePrice().getOneOffDiscountPrice().setNet(null);
				priceForBundleAndHardware1.getOneOffDiscountPrice().setGross(null);
				priceForBundleAndHardware1.getOneOffDiscountPrice().setVat(null);
				priceForBundleAndHardware1.getOneOffDiscountPrice().setNet(null);
			}
		if (priceForBundleAndHardware1.getBundlePrice() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross() != null
				&& priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice().getGross() != null)
			if (priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross()
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

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param preOrderableFlag
	 * @return flag
	 */
	public static Boolean dateValidation(Date startDateTime, Date endDateTime, boolean preOrderableFlag) {
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
	public static List<PriceForBundleAndHardware> sortPlansBasedOnMonthlyPrice(List<PriceForBundleAndHardware> plans) {
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
	public static Double getBundlePriceBasedOnDiscountDuration(com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice,
			String discountType) {
		Double monthlyPrice = null;
		if (null != discountType && discountType.equals(FULL_DURATION_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyDiscountPrice()
					&& null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
				monthlyPrice = Double.parseDouble(bundlePrice.getMonthlyDiscountPrice().getGross());
			}
		} else if (null == discountType || discountType.equals(LIMITED_TIME_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyPrice()
					&& StringUtils.isNotBlank(bundlePrice.getMonthlyPrice().getGross())) {
				monthlyPrice = Double.parseDouble(bundlePrice.getMonthlyPrice().getGross());
			}
		}
		return monthlyPrice;
	}

	/**
	 * Check if bundle has partial or full tenure discount.
	 * 
	 * @param bundlePrice
	 * @return
	 */
	public static String isPartialOrFullTenureDiscount(com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice) {
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
	public static FacetedDevice convertProductModelListToDeviceList(List<ProductModel> listOfProductModel,
			List<String> listOfProducts, List<FacetField> facetFieldList, String groupType, List<CommercialProduct> ls,
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
		NewFacet newFacet;
		FacetWithCount facetWithCount;
		List<NewFacet> listOfNewFacet = new ArrayList<>();
		List<FacetWithCount> listOfFacetWithCount = null;
		Facet facet = new Facet();
		List<Make> listOfMake = new ArrayList<>();
		if (facetFieldList != null && !facetFieldList.isEmpty()) {
			for (FacetField facetFields : facetFieldList) {
				if (facetFields.getName().equalsIgnoreCase(STRING_FACET_COLOUR)
						|| facetFields.getName().equalsIgnoreCase(STRING_FACET_CAPACITY)
						|| facetFields.getName().equalsIgnoreCase(STRING_FACET_OPERATING_SYSYTEM)
						|| facetFields.getName().equalsIgnoreCase(STRING_MUST_HAVE_FEATURES)
						|| facetFields.getName().equalsIgnoreCase(STRING_EQUIPMENT_MAKE)) {
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
			} // end for loop FacetFieldList
		}
		facet.setMakeList(listOfMake);
		facetedDevice.setNewFacet(listOfNewFacet);

		int count = 0;
		Device deviceDetails;
		if (listOfProducts != null && !listOfProducts.isEmpty()) {
			for (ProductModel productModel : listOfProductModel) {
				if (listOfProducts.contains(productModel.getProductId())) {
					if (productModel.getProductClass().equalsIgnoreCase(STRING_PRODUCT_MODEL)) {
						String leadPlanId = null;
						deviceDetails = new Device();
						ProductGroupDetailsForDeviceList groupdeatils = productGroupdetailsMap
								.containsKey(productModel.getProductId())
										? productGroupdetailsMap.get(productModel.getProductId()) : null;
						if (groupdeatils != null) {
							deviceDetails.setColor(groupdeatils.getColor());
							deviceDetails.setSize(groupdeatils.getSize());
							deviceDetails.setProductGroupName(groupdeatils.getGroupName());
							deviceDetails.setProductGroupId(groupdeatils.getGroupId());
						}
						if (StringUtils.isNotBlank(journeyType)
								&& StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
							leadPlanId = productModel.getUpgradeLeadPlanId();
						} else {
							leadPlanId = productModel.getNonUpgradeLeadPlanId();
						}
						merchandisingPromotionsPackage = new MerchandisingPromotionsPackage();
						deviceDetails.setDeviceId(productModel.getProductId());
						deviceDetails.setDescription(productModel.getPreDesc());
						if (productModel.getProductGroupName() != null) {
							deviceDetails.setName(productModel.getProductGroupName());
						} else {
							if (groupNameWithProdId != null
									&& groupNameWithProdId.containsKey(productModel.getProductId())) {
								deviceDetails.setName(groupNameWithProdId.get(productModel.getProductId()));
							}
						}
						deviceDetails.setProductClass(productModel.getProductClass());
						if (productModel.getRating() != null && productModel.getRating() > 0.0) {
							deviceDetails.setRating(String.valueOf(productModel.getRating()));
						} else {
							deviceDetails.setRating(DATA_NOT_FOUND.toLowerCase());
						}
						deviceDetails.setMake(productModel.getEquipmentMake());
						deviceDetails.setModel(productModel.getEquipmentModel());
						deviceDetails.setGroupType(groupType);

						// Added MerchandisingControl to Device

						MerchandisingControl merchandisingControl;
						merchandisingControl = new MerchandisingControl();
						merchandisingControl
								.setIsDisplayableECare(Boolean.valueOf(productModel.getIsDisplayableEcare()));
						merchandisingControl.setIsSellableECare(Boolean.valueOf(productModel.getIsSellableECare()));
						merchandisingControl.setIsDisplayableAcq(Boolean.valueOf(productModel.getIsDisplayableAcq()));
						merchandisingControl.setIsSellableRet(Boolean.valueOf(productModel.getIsSellableRet()));
						merchandisingControl.setIsDisplayableRet(Boolean.valueOf(productModel.getIsDisplayableRet()));
						merchandisingControl.setIsSellableAcq(Boolean.valueOf(productModel.getIsSellableAcq()));
						merchandisingControl.setIsDisplayableSavedBasket(
								Boolean.valueOf(productModel.getIsDisplaybaleSavedBasket()));
						if (productModel.getOrder() != null) {
							merchandisingControl.setOrder(productModel.getOrder());
						}
						if (getPreOrBackOderable(productModel.getPreOrderable())) {
							if (productModel.getAvailableFrom() != null && CommonUtility
									.dateValidationForProduct(productModel.getAvailableFrom(), DATE_FORMAT_SOLR)) {
								merchandisingControl.setPreorderable(true);
								merchandisingControl.setAvailableFrom(productModel.getAvailableFrom());
							} else {
								merchandisingControl.setPreorderable(false);
							}
						} else {
							merchandisingControl.setPreorderable(false);
						}
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
											|| (StringUtils.isBlank(offerCode) && (StringUtils.isNotBlank(journeyType)
													&& !StringUtils.equals(JOURNEY_TYPE_ACQUISITION, journeyType))))

							) {

								if (StringUtils.isNotBlank(offerCode)
										&& listOfOfferAppliedPrice.containsKey(productModel.getProductId())) {
									PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtils(
											listOfOfferAppliedPrice.get(productModel.getProductId()), leadPlanId,groupType);
									if (priceForOfferCode.getBundlePrice() != null
											&& priceForOfferCode.getHardwarePrice() != null) {
										offerFlag = true;
										deviceDetails.setPriceInfo(priceForOfferCode);
									}
								}
								if (StringUtils.isNotBlank(journeyType)
										&& (deviceDetails.getPriceInfo() == null
												|| (deviceDetails.getPriceInfo() != null
														&& deviceDetails.getPriceInfo().getBundlePrice() == null
														&& deviceDetails.getPriceInfo().getHardwarePrice() == null))
										&& withoutOfferPriceMap != null
										&& withoutOfferPriceMap.containsKey(productModel.getProductId())) {
									PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtils(
											withoutOfferPriceMap.get(productModel.getProductId()), leadPlanId,groupType);
									if (priceForOfferCode.getBundlePrice() != null
											&& priceForOfferCode.getHardwarePrice() != null) {
										offerFlag = true;
										offerCode = null;
										deviceDetails.setPriceInfo(priceForOfferCode);
									}
								}
								if (deviceDetails.getPriceInfo() == null) {
									PriceForBundleAndHardware priceForWithOutOfferCode = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
											productModel, bundleModel, leadPlanId,groupType);
									offerFlag = false;
									deviceDetails.setPriceInfo(priceForWithOutOfferCode);
								}
							} else {
								PriceForBundleAndHardware priceForBundleAndHardware = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
										productModel, bundleModel, leadPlanId,groupType);
								if (StringUtils.equalsIgnoreCase(groupType, "DEVICE_PAYM") && priceForBundleAndHardware.getBundlePrice() != null) {
									if (bundlePrice != null) {
										populateMerchandisingPromotions(priceForBundleAndHardware, bundlePrice);
									}
									deviceDetails.setPriceInfo(priceForBundleAndHardware);
								}else if(StringUtils.equalsIgnoreCase(groupType, "DEVICE_PAYG")){
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
									if (StringUtils.isNotBlank(offerCode) && offerFlag
											&& !DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& PROMO_CATEGORY_PRICING_DISCOUNT.equalsIgnoreCase(mediaStrList[i + 3])
											&& offerCode.equalsIgnoreCase(mediaStrList[i + 4]) && leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
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
											&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])) {
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
												bundleMerchecdising
														.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
												bundleMerchecdising.setTag(bundleTag);
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														STRING_MEDIA_PROMOTION)
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
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														STRING_MEDIA_PROMOTION)
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

									if (StringUtils.isBlank(offerCode) && offerFlag
											&& StringUtils.isNotBlank(journeyType)
											&& DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])
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
											hardwareMerchecdising
													.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);

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
							merchandisingPromotionsPackage = CommonUtility.getNonPricingPromotions(promotions,
									mediaList);

						}
						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsFront())) {
							MediaLink mediaThumbsFrontLink = new MediaLink();
							mediaThumbsFrontLink.setId(STRING_FOR_IMAGE_THUMBS_FRONT);
							mediaThumbsFrontLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaThumbsFrontLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsFront()));
							mediaList.add(mediaThumbsFrontLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsLeft())) {
							MediaLink mediaThumbsLeftLink = new MediaLink();
							mediaThumbsLeftLink.setId(STRING_FOR_IMAGE_THUMBS_LEFT);
							mediaThumbsLeftLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaThumbsLeftLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsLeft()));
							mediaList.add(mediaThumbsLeftLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsRight())) {
							MediaLink mediaThumbsRightLink = new MediaLink();
							mediaThumbsRightLink.setId(STRING_FOR_IMAGE_THUMBS_RIGHT);
							mediaThumbsRightLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaThumbsRightLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsRight()));
							mediaList.add(mediaThumbsRightLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsSide())) {
							MediaLink mediaThumbsSideLink = new MediaLink();
							mediaThumbsSideLink.setId(STRING_FOR_IMAGE_THUMBS_SIDE);
							mediaThumbsSideLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaThumbsSideLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsThumbsSide()));
							mediaList.add(mediaThumbsSideLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullLeft())) {
							MediaLink mediaFullLeftLink = new MediaLink();
							mediaFullLeftLink.setId(STRING_FOR_IMAGE_FULL_LEFT);
							mediaFullLeftLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaFullLeftLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullLeft()));
							mediaList.add(mediaFullLeftLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullRight())) {
							MediaLink mediaFullRightLink = new MediaLink();
							mediaFullRightLink.setId(STRING_FOR_IMAGE_FULL_RIGHT);
							mediaFullRightLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaFullRightLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullRight()));
							mediaList.add(mediaFullRightLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullSide())) {
							MediaLink mediaFullSideLink = new MediaLink();
							mediaFullSideLink.setId(STRING_FOR_IMAGE_FULL_SIDE);
							mediaFullSideLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaFullSideLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullSide()));
							mediaList.add(mediaFullSideLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullBack())) {
							MediaLink mediaFullBackLink = new MediaLink();
							mediaFullBackLink.setId(STRING_FOR_IMAGE_FULL_BACK);
							mediaFullBackLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaFullBackLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsFullBack()));
							mediaList.add(mediaFullBackLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsGrid())) {
							MediaLink mediaGridLink = new MediaLink();
							mediaGridLink.setId(STRING_FOR_IMAGE_GRID);
							mediaGridLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaGridLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsGrid()));
							mediaList.add(mediaGridLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsSmall())) {
							MediaLink mediaSmallLink = new MediaLink();
							mediaSmallLink.setId(STRING_FOR_IMAGE_SMALL);
							mediaSmallLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaSmallLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsSmall()));
							mediaList.add(mediaSmallLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsSticker())) {
							MediaLink mediaStickerLink = new MediaLink();
							mediaStickerLink.setId(STRING_FOR_IMAGE_STICKER);
							mediaStickerLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaStickerLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsSticker()));
							mediaList.add(mediaStickerLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsIcon())) {
							MediaLink mediaIconLink = new MediaLink();
							mediaIconLink.setId(STRING_FOR_IMAGE_ICON);
							mediaIconLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaIconLink.setValue(
									CommonUtility.getImageMediaUrl(cdnDomain, productModel.getImageURLsIcon()));
							mediaList.add(mediaIconLink);
						}

						if (StringUtils.isNotBlank(productModel.getThreeDSpin())) {
							MediaLink media3DSpinLink = new MediaLink();
							media3DSpinLink.setId(STRING_FOR_IMAGE_3DSPIN);
							media3DSpinLink.setType(STRING_FOR_MEDIA_TYPE);
							media3DSpinLink
									.setValue(CommonUtility.getImageMediaUrl(cdnDomain, productModel.getThreeDSpin()));
							mediaList.add(media3DSpinLink);
						}

						if (StringUtils.isNotBlank(productModel.getSupport())) {
							MediaLink mediaSupportLink = new MediaLink();
							mediaSupportLink.setId(STRING_FOR_IMAGE_SUPPORT);
							mediaSupportLink.setType(STRING_FOR_MEDIA_TYPE);
							mediaSupportLink
									.setValue(CommonUtility.getImageMediaUrl(cdnDomain, productModel.getSupport()));
							mediaList.add(mediaSupportLink);
						}

						deviceDetails.setMedia(mediaList);
						PriceForBundleAndHardware priceForWithOutOfferCode = deviceDetails.getPriceInfo();
						if (priceForWithOutOfferCode != null) {
							if (priceForWithOutOfferCode.getBundlePrice() != null) {
								priceForWithOutOfferCode.getBundlePrice()
										.setMerchandisingPromotions(bundleMerchandising);
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
						if (isLeadMemberFromSolr.get(leadMember) && StringUtils.isNotBlank(leadPlanId)
								&& groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)) {
							deviceDetails.setPromotionsPackage(merchandisingPromotionsPackage);
							deviceList.add(deviceDetails);
							count++;
						} else if (isLeadMemberFromSolr.get(leadMember)
								&& groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)) {
							deviceDetails.setPromotionsPackage(merchandisingPromotionsPackage);
							deviceList.add(deviceDetails);
							count++;
						} else if (!isLeadMemberFromSolr.get(leadMember)) {
							deviceDetails.setPromotionsPackage(merchandisingPromotionsPackage);
							deviceList.add(deviceDetails);
							count++;
						}
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
	 * Compare prices and set merchandising promotions.
	 * 
	 * @param priceForBundleAndHardware
	 * @param bundlePrice
	 */
	public static void populateMerchandisingPromotions(PriceForBundleAndHardware priceForBundleAndHardware,
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
	private static void mapDiscountMonthlyPrice(PriceForBundleAndHardware priceForBundleAndHardware,
			BundlePrice bundlePrice) {
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
	public static List<com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption> getDeviceFinaceOptions(
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
	 * @param productModel
	 * @param bundleModel
	 * @return PriceForBundleAndHardware
	 */
	public static PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrUtils(List<OfferAppliedPriceModel> offers,
			String leadPlanId, String groupType) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		if (offers != null && !offers.isEmpty()) {
			offers.forEach(offer -> {
				if (offer.getBundleId() != null && leadPlanId != null && offer.getBundleId().equals(leadPlanId)) {
					if (offer.getOneOffDiscountedGrossPrice() != null && offer.getOneOffGrossPrice() != null
							&& offer.getOneOffGrossPrice().equals(offer.getOneOffDiscountedGrossPrice())) {
						Price oneOffDiscountPrice = new Price();
						oneOffDiscountPrice.setGross(null);
						oneOffDiscountPrice.setNet(null);
						oneOffDiscountPrice.setVat(null);
						Price oneOffPrice = new Price();

						oneOffPrice.setGross(CommonUtility.getpriceFormat(offer.getOneOffGrossPrice()));
						oneOffPrice.setNet(CommonUtility.getpriceFormat(offer.getOneOffNetPrice()));
						oneOffPrice.setVat(CommonUtility.getpriceFormat(offer.getOneOffVatPrice()));
						hardwarePrice.setOneOffPrice(oneOffPrice);
						hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
						hardwarePrice.setHardwareId(offer.getProductId());
						//hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(offer.getFinancingOptions()));
						priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
						priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
						priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
					} else {
						Price oneOffDiscountPrice = new Price();

						oneOffDiscountPrice
								.setGross(CommonUtility.getpriceFormat(offer.getOneOffDiscountedGrossPrice()));
						oneOffDiscountPrice.setNet(CommonUtility.getpriceFormat(offer.getOneOffDiscountedNetPrice()));
						oneOffDiscountPrice.setVat(CommonUtility.getpriceFormat(offer.getOneOffDiscountedVatPrice()));
						Price oneOffPrice = new Price();

						oneOffPrice.setGross(CommonUtility.getpriceFormat(offer.getOneOffGrossPrice()));
						oneOffPrice.setNet(CommonUtility.getpriceFormat(offer.getOneOffNetPrice()));
						oneOffPrice.setVat(CommonUtility.getpriceFormat(offer.getOneOffVatPrice()));
						hardwarePrice.setOneOffPrice(oneOffPrice);
						hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
						hardwarePrice.setHardwareId(offer.getProductId());
						//hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(offer.getFinancingOptions()));
						priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
						priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
						priceForBundleAndHardware.setHardwarePrice(hardwarePrice);

					}
					if (offer.getMonthlyDiscountedGrossPrice() != null && offer.getMonthlyGrossPrice() != null
							&& offer.getMonthlyDiscountedGrossPrice().equals(offer.getMonthlyGrossPrice())) {
						Price monthlyDiscountPrice = new Price();
						monthlyDiscountPrice.setGross(null);
						monthlyDiscountPrice.setNet(null);
						monthlyDiscountPrice.setVat(null);
						Price monthlyPrice = new Price();

						monthlyPrice.setGross(CommonUtility.getpriceFormat(offer.getMonthlyGrossPrice()));
						monthlyPrice.setNet(CommonUtility.getpriceFormat(offer.getMonthlyNetPrice()));
						monthlyPrice.setVat(CommonUtility.getpriceFormat(offer.getMonthlyVatPrice()));
						bundlePrice.setMonthlyPrice(monthlyPrice);
						bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
						bundlePrice.setBundleId(offer.getBundleId());
						priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
						priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
						priceForBundleAndHardware.setBundlePrice(bundlePrice);
					} else  {
						Price monthlyDiscountPrice = new Price();

						monthlyDiscountPrice
								.setGross(CommonUtility.getpriceFormat(offer.getMonthlyDiscountedGrossPrice()));
						monthlyDiscountPrice.setNet(CommonUtility.getpriceFormat(offer.getMonthlyDiscountedNetPrice()));
						monthlyDiscountPrice.setVat(CommonUtility.getpriceFormat(offer.getMonthlyDiscountedVatPrice()));
						Price monthlyPrice = new Price();

						monthlyPrice.setGross(CommonUtility.getpriceFormat(offer.getMonthlyGrossPrice()));
						monthlyPrice.setNet(CommonUtility.getpriceFormat(offer.getMonthlyNetPrice()));
						monthlyPrice.setVat(CommonUtility.getpriceFormat(offer.getMonthlyVatPrice()));
						bundlePrice.setMonthlyPrice(monthlyPrice);
						bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
						bundlePrice.setBundleId(offer.getBundleId());
						priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
						priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
						priceForBundleAndHardware.setBundlePrice(bundlePrice);
					}
				}
			});
		}

		return priceForBundleAndHardware;

	}

	/**
	 * 
	 * @param productModel
	 * @param bundleModel
	 * @param leadPlanId
	 * @return PriceForBundleAndHardware
	 */
	public static PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrWithoutOfferCode(ProductModel productModel,
			BundleModel bundleModel, String leadPlanId, String groupType) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		if (productModel != null) {
			if (StringUtils.equalsIgnoreCase(groupType, "DEVICE_PAYM")) {
				if (productModel.getOneOffDiscountedGrossPrice() != null && productModel.getOneOffGrossPrice() != null
						&& productModel.getOneOffGrossPrice().equals(productModel.getOneOffDiscountedGrossPrice())) {
					Price oneOffDiscountPrice = new Price();
					oneOffDiscountPrice.setGross(null);
					oneOffDiscountPrice.setNet(null);
					oneOffDiscountPrice.setVat(null);
					Price oneOffPrice = new Price();

					oneOffPrice.setGross(CommonUtility.getpriceFormat(productModel.getOneOffGrossPrice()));
					oneOffPrice.setNet(CommonUtility.getpriceFormat(productModel.getOneOffNetPrice()));
					oneOffPrice.setVat(CommonUtility.getpriceFormat(productModel.getOneOffVatPrice()));
					hardwarePrice.setOneOffPrice(oneOffPrice);
					hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
					hardwarePrice.setHardwareId(productModel.getProductId());
					priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
					priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
					priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
				} else if (productModel.getOneOffDiscountedGrossPrice() != null
						&& productModel.getOneOffGrossPrice() != null
						&& !productModel.getOneOffGrossPrice().equals(productModel.getOneOffDiscountedGrossPrice())) {
					Price oneOffDiscountPrice = new Price();
					oneOffDiscountPrice
							.setGross(CommonUtility.getpriceFormat(productModel.getOneOffDiscountedGrossPrice()));
					oneOffDiscountPrice
							.setNet(CommonUtility.getpriceFormat(productModel.getOneOffDiscountedNetPrice()));
					oneOffDiscountPrice
							.setVat(CommonUtility.getpriceFormat(productModel.getOneOffDiscountedVatPrice()));
					Price oneOffPrice = new Price();
					oneOffPrice.setGross(CommonUtility.getpriceFormat(productModel.getOneOffGrossPrice()));
					oneOffPrice.setNet(CommonUtility.getpriceFormat(productModel.getOneOffNetPrice()));
					oneOffPrice.setVat(CommonUtility.getpriceFormat(productModel.getOneOffVatPrice()));
					hardwarePrice.setOneOffPrice(oneOffPrice);
					hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
					hardwarePrice.setHardwareId(productModel.getProductId());
					priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
					priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
					priceForBundleAndHardware.setHardwarePrice(hardwarePrice);

				}
				if (productModel.getBundleMonthlyDiscPriceGross() != null
						&& productModel.getBundleMonthlyPriceGross() != null && productModel
								.getBundleMonthlyDiscPriceGross().equals(productModel.getBundleMonthlyPriceGross())) {
					Price monthlyDiscountPrice = new Price();
					monthlyDiscountPrice.setGross(null);
					monthlyDiscountPrice.setNet(null);
					monthlyDiscountPrice.setVat(null);
					Price monthlyPrice = new Price();
					monthlyPrice.setGross(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceGross()));
					monthlyPrice.setNet(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceNet()));
					monthlyPrice.setVat(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceVat()));
					bundlePrice.setMonthlyPrice(monthlyPrice);
					bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
					bundlePrice.setBundleId(leadPlanId);
					priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
					priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
					priceForBundleAndHardware.setBundlePrice(bundlePrice);
				} else {
					Price monthlyDiscountPrice = new Price();
					monthlyDiscountPrice
							.setGross(CommonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceGross()));
					monthlyDiscountPrice
							.setNet(CommonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceNet()));
					monthlyDiscountPrice
							.setVat(CommonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceVat()));
					Price monthlyPrice = new Price();
					monthlyPrice.setGross(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceGross()));
					monthlyPrice.setNet(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceNet()));
					monthlyPrice.setVat(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceVat()));
					bundlePrice.setMonthlyPrice(monthlyPrice);
					bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
					bundlePrice.setBundleId(leadPlanId);
					priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
					priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
					priceForBundleAndHardware.setBundlePrice(bundlePrice);
				}
				if (bundleModel != null) {
					if (bundleModel.getMonthlyDiscountedGrossPrice() != null
							&& bundleModel.getMonthlyGrossPrice() != null && bundleModel
									.getMonthlyDiscountedGrossPrice().equals(bundleModel.getMonthlyGrossPrice())) {
						Price monthlyDiscountPrice = new Price();
						monthlyDiscountPrice.setGross(null);
						monthlyDiscountPrice.setNet(null);
						monthlyDiscountPrice.setVat(null);
						Price monthlyPrice = new Price();
						monthlyPrice.setGross(CommonUtility.getpriceFormat(bundleModel.getMonthlyGrossPrice()));
						monthlyPrice.setNet(CommonUtility.getpriceFormat(bundleModel.getMonthlyNetPrice()));
						monthlyPrice.setVat(CommonUtility.getpriceFormat(bundleModel.getMonthlyVatPrice()));
						bundlePrice.setMonthlyPrice(monthlyPrice);
						bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
						bundlePrice.setBundleId(bundleModel.getBundleId());
						priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
						priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
						priceForBundleAndHardware.setBundlePrice(bundlePrice);
					} else {
						Price monthlyDiscountPrice = new Price();
						monthlyDiscountPrice
								.setGross(CommonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedGrossPrice()));
						monthlyDiscountPrice
								.setNet(CommonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedNetPrice()));
						monthlyDiscountPrice
								.setVat(CommonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedVatPrice()));
						Price monthlyPrice = new Price();
						monthlyPrice.setGross(CommonUtility.getpriceFormat(bundleModel.getMonthlyGrossPrice()));
						monthlyPrice.setNet(CommonUtility.getpriceFormat(bundleModel.getMonthlyNetPrice()));
						monthlyPrice.setVat(CommonUtility.getpriceFormat(bundleModel.getMonthlyVatPrice()));
						bundlePrice.setMonthlyPrice(monthlyPrice);
						bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
						bundlePrice.setBundleId(bundleModel.getBundleId());
						priceForBundleAndHardware.setMonthlyPrice(monthlyPrice);
						priceForBundleAndHardware.setMonthlyDiscountPrice(monthlyDiscountPrice);
						priceForBundleAndHardware.setBundlePrice(bundlePrice);
					}
				}
			} else if (StringUtils.equalsIgnoreCase(groupType, "DEVICE_PAYG")) {
				if (productModel.getPaygOneOffDiscountedGrossPrice() != null && productModel.getPaygOneOffGrossPrice() != null
						&& productModel.getPaygOneOffGrossPrice().equals(productModel.getPaygOneOffDiscountedGrossPrice())) {
					BundlePrice bundlePriceLocal = new BundlePrice();
					Price monthlyPrice = new Price();
					bundlePriceLocal.setMonthlyPrice(monthlyPrice);
					bundlePriceLocal.setMonthlyDiscountPrice(monthlyPrice);
					Price oneOffDiscountPrice = new Price();
					oneOffDiscountPrice.setGross(null);
					oneOffDiscountPrice.setNet(null);
					oneOffDiscountPrice.setVat(null);
					Price oneOffPrice = new Price();

					oneOffPrice.setGross(CommonUtility.getpriceFormat(productModel.getPaygOneOffGrossPrice()));
					oneOffPrice.setNet(CommonUtility.getpriceFormat(productModel.getPaygOneOffNetPrice()));
					oneOffPrice.setVat(CommonUtility.getpriceFormat(productModel.getPaygOneOffVatPrice()));
					hardwarePrice.setOneOffPrice(oneOffPrice);
					hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
					hardwarePrice.setHardwareId(productModel.getProductId());
					hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(productModel.getFinancingOptions()));
					priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
					priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
					priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
					priceForBundleAndHardware.setBundlePrice(bundlePriceLocal);
				} else if (productModel.getPaygOneOffDiscountedGrossPrice() != null
						&& productModel.getPaygOneOffGrossPrice() != null
						&& !productModel.getPaygOneOffGrossPrice().equals(productModel.getPaygOneOffDiscountedGrossPrice())) {
					BundlePrice bundlePriceLocal = new BundlePrice();
					Price monthlyPrice = new Price();
					bundlePriceLocal.setMonthlyPrice(monthlyPrice);
					bundlePriceLocal.setMonthlyDiscountPrice(monthlyPrice);
					Price oneOffDiscountPrice = new Price();
					oneOffDiscountPrice
							.setGross(CommonUtility.getpriceFormat(productModel.getPaygOneOffDiscountedGrossPrice()));
					oneOffDiscountPrice
							.setNet(CommonUtility.getpriceFormat(productModel.getPaygOneOffDiscountedNetPrice()));
					oneOffDiscountPrice
							.setVat(CommonUtility.getpriceFormat(productModel.getPaygOneOffDiscountedVatPrice()));
					Price oneOffPrice = new Price();
					oneOffPrice.setGross(CommonUtility.getpriceFormat(productModel.getPaygOneOffGrossPrice()));
					oneOffPrice.setNet(CommonUtility.getpriceFormat(productModel.getPaygOneOffNetPrice()));
					oneOffPrice.setVat(CommonUtility.getpriceFormat(productModel.getPaygOneOffVatPrice()));
					hardwarePrice.setOneOffPrice(oneOffPrice);
					hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
					hardwarePrice.setHardwareId(productModel.getProductId());
					hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(productModel.getFinancingOptions()));
					priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
					priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
					priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
					priceForBundleAndHardware.setBundlePrice(bundlePriceLocal);
				}
			}
		}
		return priceForBundleAndHardware;
	}

	public static Boolean getPreOrBackOderable(String preOrderable) {
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
	public static MerchandisingPromotionsPackage assembleMerchandisingPromotion(BundleAndHardwarePromotions promotions,
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks,
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances,
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash,
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet,
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional) {

		MerchandisingPromotionsPackage promotionsPackage = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper bundlePromotions = new MerchandisingPromotionsWrapper();
		MerchandisingPromotionsWrapper hardwarePromotions = new MerchandisingPromotionsWrapper();

		if (CollectionUtils.isNotEmpty(dataAllowances)) {

			/* Assembly of dataPromotions */
			MerchandisingPromotion dataPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance = dataAllowances.get(0);
			dataPromotion.setTag(dataAllowance.getTag());
			dataPromotion.setDescription(dataAllowance.getDescription());
			// dataPromotion.setDiscountId(dataAllowance.get);
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

		if (CollectionUtils.isNotEmpty(freeExtrasForHardwares)) {
			/* Assembly of freeExtrasForHardware */
			MerchandisingPromotion freeExtraPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForHardware = freeExtrasForHardwares.get(0);
			freeExtraPromotion.setTag(freeExtrasForHardware.getTag());
			freeExtraPromotion.setDescription(freeExtrasForHardware.getDescription());
			freeExtraPromotion.setLabel(freeExtrasForHardware.getLabel());
			freeExtraPromotion.setMpType(freeExtrasForHardware.getType());
			freeExtraPromotion.setPackageType(freeExtrasForHardware.getPackageType());
			freeExtraPromotion.setFootNotes(freeExtrasForHardware.getFootNotes());
			if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
				freeExtraPromotion.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
			}
			if (StringUtils.isNotBlank(freeExtrasForHardware.getPromotionMedia())) {
				freeExtraPromotion.setPromotionMedia(freeExtrasForHardware.getPromotionMedia());
			}
			hardwarePromotions.setFreeExtraPromotion(freeExtraPromotion);
		}
		promotionsPackage.setPlanId(promotions.getBundleId());
		promotionsPackage.setHardwareId(promotions.getHardwareId());
		promotionsPackage.setHardwarePromotions(hardwarePromotions);
		promotionsPackage.setBundlePromotions(bundlePromotions);

		return promotionsPackage;

	}

}