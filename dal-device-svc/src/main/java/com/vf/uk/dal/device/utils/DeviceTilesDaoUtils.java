package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.DeviceFinancingOption;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.Count;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.Facet;
import com.vf.uk.dal.device.entity.FacetWithCount;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.GroupDetails;
import com.vf.uk.dal.device.entity.HardwarePrice;
import com.vf.uk.dal.device.entity.Make;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.MerchandisingControl;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.entity.MerchandisingPromotionsWrapper;
import com.vf.uk.dal.device.entity.NewFacet;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareAccessory;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareDataAllowances;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareExtras;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSash;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSecureNet;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForHardwareSash;

/**
 * Mapping of coherence and solr entities to Device entities.
 * 
 * @author
 **/

public class DeviceTilesDaoUtils {

	private static String leadMember = "leadMember";

	/**
	 * @author manoj.bera
	 * @param priceForBundleAndHardware
	 * @param deviceId
	 * @param isConditionalAcceptJourney
	 * @param comBundle
	 * @return
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
	 * @return
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
	 * @return
	 */
	public List<com.vf.uk.dal.device.entity.Member> getAscendingOrderForMembers(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember) {
		Collections.sort(listOfDeviceGroupMember, new SortedMemberPriorityList());

		return listOfDeviceGroupMember;
	}

	class SortedMemberPriorityList implements Comparator<com.vf.uk.dal.device.entity.Member> {

		@Override
		public int compare(com.vf.uk.dal.device.entity.Member member1, com.vf.uk.dal.device.entity.Member member2) {

			if (member1.getPriority() != null && member2.getPriority() != null) {
				if (Integer.valueOf(member1.getPriority()) < Integer.valueOf(member2.getPriority())) {
					return -1;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * Sort plans in ascending order based on monthly price.
	 * 
	 * @param plans
	 * @return
	 */
	public static List<PriceForBundleAndHardware> sortPlansBasedOnMonthlyPrice(List<PriceForBundleAndHardware> plans) {
		Collections.sort(plans, new Comparator<PriceForBundleAndHardware>() {
			@Override
			public int compare(PriceForBundleAndHardware plans1, PriceForBundleAndHardware plans2) {
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

				if (gross1 == gross2)
					return 0;
				else if (gross1 > gross2)
					return 1;
				else
					return -1;
			}
		});

		return plans;
	}

	/**
	 * 
	 * @param bundlePrice
	 * @param discountType
	 * @return
	 */
	public static Double getBundlePriceBasedOnDiscountDuration(com.vf.uk.dal.device.entity.BundlePrice bundlePrice,
			String discountType) {
		Double monthlyPrice = null;
		if (null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyDiscountPrice()
					&& null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
				monthlyPrice = Double.parseDouble(bundlePrice.getMonthlyDiscountPrice().getGross());
			}
		} else if (null == discountType || discountType.equals(Constants.LIMITED_TIME_DISCOUNT)) {
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
	public static String isPartialOrFullTenureDiscount(com.vf.uk.dal.device.entity.BundlePrice bundlePrice) {
		if (null != bundlePrice.getMerchandisingPromotions()
				&& null != bundlePrice.getMerchandisingPromotions().getMpType()) {
			if (bundlePrice.getMerchandisingPromotions().getMpType().equalsIgnoreCase(Constants.FULL_DURATION_DISCOUNT)
					|| bundlePrice.getMerchandisingPromotions().getMpType()
							.equalsIgnoreCase(Constants.CONDITIONAL_FULL_DISCOUNT)) {
				return Constants.FULL_DURATION_DISCOUNT;
			} else if (bundlePrice.getMerchandisingPromotions().getMpType()
					.equalsIgnoreCase(Constants.LIMITED_TIME_DISCOUNT)
					|| bundlePrice.getMerchandisingPromotions().getMpType()
							.equalsIgnoreCase(Constants.CONDITIONAL_LIMITED_DISCOUNT)) {
				return Constants.LIMITED_TIME_DISCOUNT;
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
	 * @return
	 */
	public static FacetedDevice convertProductModelListToDeviceList(List<ProductModel> listOfProductModel,
			List<String> listOfProducts, List<FacetField> facetFieldList, String groupType, List<CommercialProduct> ls,
			Map<String, BundleModel> bundleModelMap, Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice,
			String offerCode1, Map<String, String> groupNameWithProdId, Map<String, BundlePrice> bundleModelAndPriceMap,
			Map<String, BundleAndHardwarePromotions> promotionmap, Map<String, Boolean> isLeadMemberFromSolr,
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap, String journeyType,
			Map<String, GroupDetails> productGroupdetailsMap) {
		String offerCode = offerCode1;
		List<GroupDetails> listOfGroupDetails = new ArrayList<>();
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
				if (facetFields.getName().equalsIgnoreCase(Constants.STRING_FACET_COLOUR)
						|| facetFields.getName().equalsIgnoreCase(Constants.STRING_FACET_CAPACITY)
						|| facetFields.getName().equalsIgnoreCase(Constants.STRING_FACET_OPERATING_SYSYTEM)
						|| facetFields.getName().equalsIgnoreCase(Constants.STRING_MUST_HAVE_FEATURES)
						|| facetFields.getName().equalsIgnoreCase(Constants.STRING_EQUIPMENT_MAKE)) {
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
					if (productModel.getProductClass().equalsIgnoreCase(Constants.STRING_PRODUCT_MODEL)) {
						String leadPlanId = null;
						GroupDetails groupdeatils = productGroupdetailsMap.containsKey(productModel.getProductId())
								? productGroupdetailsMap.get(productModel.getProductId()) : null;
						if (groupdeatils != null) {
							listOfGroupDetails.add(groupdeatils);
						}
						if (StringUtils.isNotBlank(journeyType)
								&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)) {
							leadPlanId = productModel.getUpgradeLeadPlanId();
						} else {
							leadPlanId = productModel.getNonUpgradeLeadPlanId();
						}
						merchandisingPromotionsPackage = new MerchandisingPromotionsPackage();
						deviceDetails = new Device();
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
							deviceDetails.setRating(Constants.DEVICE_RATING_NA.toLowerCase());
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
							merchandisingControl.setOrder(Integer.valueOf(productModel.getOrder()));
						}
						if (getPreOrBackOderable(productModel.getPreOrderable())) {
							if (productModel.getAvailableFrom() != null && CommonUtility.dateValidationForProduct(
									productModel.getAvailableFrom(), Constants.DATE_FORMAT_SOLR)) {
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
						LogHelper.info(DeviceTilesDaoUtils.class, "IN Facet " + leadPlanId);
						LogHelper.info(DeviceTilesDaoUtils.class, "IN Facet for" + bundleModelMap);
						if (leadPlanId != null && bundleModelMap != null) {
							bundleModel = bundleModelMap.get(leadPlanId);
							if (null != bundleModelAndPriceMap && !bundleModelAndPriceMap.isEmpty()) {
								bundlePrice = bundleModelAndPriceMap.get(leadPlanId);
							}
							LogHelper.info(DeviceTilesDaoUtils.class, "IN Facet " + bundleModel);

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
						if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
								|| groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {

							if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM) && ((StringUtils
									.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType))
									|| (StringUtils.isBlank(offerCode) && (StringUtils.isNotBlank(journeyType)
											&& !StringUtils.equals(Constants.JOURNEY_TYPE_ACQUISITION, journeyType))))

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
								if (StringUtils.isNotBlank(journeyType)
										&& (deviceDetails.getPriceInfo() == null
												|| (deviceDetails.getPriceInfo() != null
														&& deviceDetails.getPriceInfo().getBundlePrice() == null
														&& deviceDetails.getPriceInfo().getHardwarePrice() == null))
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
											productModel, bundleModel, leadPlanId);
									offerFlag = false;
									deviceDetails.setPriceInfo(priceForWithOutOfferCode);
								}
							} else {
								PriceForBundleAndHardware priceForBundleAndHardware = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
										productModel, bundleModel, leadPlanId);
								if (priceForBundleAndHardware.getBundlePrice() != null) {
									if (bundlePrice != null) {
										populateMerchandisingPromotions(priceForBundleAndHardware, bundlePrice);
									}
									deviceDetails.setPriceInfo(priceForBundleAndHardware);
								}

							}
						}
						// Media Link
						List<MediaLink> mediaList = new ArrayList<>();
						com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchandising = null;
						com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchandising = null;
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
											&& !Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& Constants.PROMO_CATEGORY_PRICING_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])
											&& offerCode.equalsIgnoreCase(mediaStrList[i + 4]) && leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}

											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												promotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(promotionMedia))
												bundleMerchecdising.setPromotionMedia(promotionMedia);
											bundleMerchecdising.setTag(bundleTag);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
											hardwareMerchecdising.setDescription(hardwareDescription);
											hardwareMerchecdising.setDiscountId(hardwareDiscountId);
											hardwareMerchecdising.setLabel(hardwareLabel);
											hardwareMerchecdising.setMpType(hardwareMpType);
											hardwareMerchecdising
													.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
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
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])) {
										boolean nonOfferAppliedPriceFlag = false;
										if (StringUtils.isNotBlank(journeyType)
												&& StringUtils.equalsIgnoreCase(journeyType,
														Constants.JOURNEY_TYPE_UPGRADE)
												&& Constants.PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT
														.equalsIgnoreCase(mediaStrList[i + 3])) {
											nonOfferAppliedPriceFlag = true;
										}
										if (StringUtils.isNotBlank(journeyType)
												&& StringUtils.equalsIgnoreCase(journeyType,
														Constants.JOURNEY_TYPE_SECONDLINE)
												&& Constants.PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT
														.equalsIgnoreCase(mediaStrList[i + 3])) {
											nonOfferAppliedPriceFlag = true;
										}
										if (nonOfferAppliedPriceFlag) {
											MediaLink mediaLink = new MediaLink();
											mediaLink.setId(mediaStrList[i]);
											mediaLink.setValue(mediaStrList[i + 1]);
											mediaLink.setType(typeArray[0]);
											mediaList.add(mediaLink);

											if (Constants.PROMO_TYPE_BUNDLEPROMOTION
													.equalsIgnoreCase(typeArray[i + 2])) {
												bundleTag = typeArray[3];
												if (StringUtils.contains(
														mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
																mediaStrList[i].length()),
														Constants.STRING_MEDIA_LABEL)) {
													bundleLabel = mediaStrList[i + 1];
													bundleMpType = mediaStrList[i].substring(0,
															mediaStrList[i].indexOf('.'));
													bundleDiscountId = mediaStrList[i + 6];
												}
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														Constants.STRING_MEDIA_DESCRIPTION)) {
													bundleDescription = mediaStrList[i + 1];
												}
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														Constants.STRING_MEDIA_PRICEESTABLISH)
														&& StringUtils.isNotBlank(mediaStrList[i + 1])
														&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
													bundlePriceEstablishedLabel = mediaStrList[i + 1];
												}
												com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
												bundleMerchecdising.setDescription(bundleDescription);
												bundleMerchecdising.setDiscountId(bundleDiscountId);
												bundleMerchecdising.setLabel(bundleLabel);
												bundleMerchecdising.setMpType(bundleMpType);
												bundleMerchecdising
														.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
												bundleMerchecdising.setTag(bundleTag);
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														Constants.STRING_MEDIA_PROMOTION)
														&& StringUtils.isNotBlank(mediaStrList[i + 1])
														&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
													promotionMedia = mediaStrList[i + 1];
												}
												if (StringUtils.isNotBlank(promotionMedia))
													bundleMerchecdising.setPromotionMedia(promotionMedia);
												bundleMerchandising = bundleMerchecdising;
											}
											if (Constants.PROMO_TYPE_HARDWAREPROMOTION
													.equalsIgnoreCase(typeArray[i + 2])) {
												hardwareTag = typeArray[i + 3];
												if (StringUtils.contains(
														mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
																mediaStrList[i].length()),
														Constants.STRING_MEDIA_LABEL)) {
													hardwareLabel = mediaStrList[i + 1];
													hardwareMpType = mediaStrList[i].substring(0,
															mediaStrList[i].indexOf('.'));
													hardwareDiscountId = mediaStrList[i + 6];
												}
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														Constants.STRING_MEDIA_DESCRIPTION)) {
													hardwareDescription = mediaStrList[i + 1];
												}
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														Constants.STRING_MEDIA_PRICEESTABLISH)
														&& StringUtils.isNotBlank(mediaStrList[i + 1])
														&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
													hardwarePriceEstablishedLabel = mediaStrList[i + 1];
												}
												com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
												hardwareMerchecdising.setDescription(hardwareDescription);
												hardwareMerchecdising.setDiscountId(hardwareDiscountId);
												hardwareMerchecdising.setLabel(hardwareLabel);
												hardwareMerchecdising.setMpType(hardwareMpType);
												hardwareMerchecdising
														.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
												hardwareMerchecdising.setTag(hardwareTag);
												if (StringUtils.containsIgnoreCase(mediaStrList[i],
														Constants.STRING_MEDIA_PROMOTION)
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
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])
											&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												promotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(promotionMedia))
												bundleMerchecdising.setPromotionMedia(promotionMedia);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
											hardwareMerchecdising.setDescription(hardwareDescription);
											hardwareMerchecdising.setDiscountId(hardwareDiscountId);
											hardwareMerchecdising.setLabel(hardwareLabel);
											hardwareMerchecdising.setMpType(hardwareMpType);
											hardwareMerchecdising
													.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
											hardwareMerchecdising.setTag(hardwareTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePromotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(hardwarePromotionMedia))
												hardwareMerchecdising.setPromotionMedia(hardwarePromotionMedia);
											hardwareMerchandising = hardwareMerchecdising;
										}
									}

									if (StringUtils.equalsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYG)
											&& StringUtils.isBlank(offerCode) && StringUtils.isNotBlank(journeyType)
											&& StringUtils.equalsIgnoreCase(journeyType,
													Constants.JOURNEY_TYPE_ACQUISITION)
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& StringUtils.isNotBlank(mediaStrList[i + 3])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												promotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(promotionMedia))
												bundleMerchecdising.setPromotionMedia(promotionMedia);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
											hardwareMerchecdising.setDescription(hardwareDescription);
											hardwareMerchecdising.setDiscountId(hardwareDiscountId);
											hardwareMerchecdising.setLabel(hardwareLabel);
											hardwareMerchecdising.setMpType(hardwareMpType);
											hardwareMerchecdising
													.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
											hardwareMerchecdising.setTag(hardwareTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
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
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])
											&& StringUtils.isNotBlank(mediaStrList[i + 3])
											&& StringUtils.containsIgnoreCase(mediaStrList[i + 3], journeyType)) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												promotionMedia = mediaStrList[i + 1];
											}
											if (StringUtils.isNotBlank(promotionMedia))
												bundleMerchecdising.setPromotionMedia(promotionMedia);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
											hardwareMerchecdising.setDescription(hardwareDescription);
											hardwareMerchecdising.setDiscountId(hardwareDiscountId);
											hardwareMerchecdising.setLabel(hardwareLabel);
											hardwareMerchecdising.setMpType(hardwareMpType);
											hardwareMerchecdising
													.setPriceEstablishedLabel(hardwarePriceEstablishedLabel);
											hardwareMerchecdising.setTag(hardwareTag);
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
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
											&& StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_ACQUISITION,
													journeyType)
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])
											&& leadPlanId != null && leadPlanId.equalsIgnoreCase(typeArray[1])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}

											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												promotionMedia = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
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
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf('.') + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf('.'));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePriceEstablishedLabel = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PROMOTION)
													&& StringUtils.isNotBlank(mediaStrList[i + 1])
													&& !"null".equalsIgnoreCase(mediaStrList[i + 1])) {
												hardwarePromotionMedia = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchecdising = new MerchandisingPromotion();
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
							List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = promotions
									.getEntertainmentPacks();
							List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = promotions
									.getDataAllowances();
							List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = promotions
									.getPlanCouplingPromotions();
							List<CataloguepromotionqueriesForBundleAndHardwareSash> sash = promotions
									.getSashBannerForPlan();
							List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = promotions
									.getSecureNet();
							List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = promotions
									.getSashBannerForHardware();
							List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = promotions
									.getFreeExtras();
							List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories = promotions
									.getFreeAccessory();
							List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans = promotions
									.getFreeExtrasForPlan();
							List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans = promotions
									.getFreeAccForPlan();
							List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares = promotions
									.getFreeExtrasForHardware();
							List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares = promotions
									.getFreeAccForHardware();
							List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional = promotions
									.getConditionalSashBanner();
							mediaList.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks,
									dataAllowances, planCouplingPromotions, sash, secureNet, sashBannerForHardware,
									freeExtras, freeAccessories, freeExtrasForPlans, freeAccForPlans,
									freeExtrasForHardwares, freeAccForHardwares, sashBundleConditional));

							merchandisingPromotionsPackage = assembleMerchandisingPromotion(promotions,
									entertainmentPacks, dataAllowances, planCouplingPromotions, sash, secureNet,
									sashBannerForHardware, freeExtras, freeAccessories, freeExtrasForPlans,
									freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares,
									sashBundleConditional);

						}
						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsFront())) {
							MediaLink mediaThumbsFrontLink = new MediaLink();
							mediaThumbsFrontLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_FRONT);
							mediaThumbsFrontLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaThumbsFrontLink.setValue(productModel.getImageURLsThumbsFront());
							mediaList.add(mediaThumbsFrontLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsLeft())) {
							MediaLink mediaThumbsLeftLink = new MediaLink();
							mediaThumbsLeftLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_LEFT);
							mediaThumbsLeftLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaThumbsLeftLink.setValue(productModel.getImageURLsThumbsLeft());
							mediaList.add(mediaThumbsLeftLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsRight())) {
							MediaLink mediaThumbsRightLink = new MediaLink();
							mediaThumbsRightLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_RIGHT);
							mediaThumbsRightLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaThumbsRightLink.setValue(productModel.getImageURLsThumbsRight());
							mediaList.add(mediaThumbsRightLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsThumbsSide())) {
							MediaLink mediaThumbsSideLink = new MediaLink();
							mediaThumbsSideLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_SIDE);
							mediaThumbsSideLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaThumbsSideLink.setValue(productModel.getImageURLsThumbsSide());
							mediaList.add(mediaThumbsSideLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullLeft())) {
							MediaLink mediaFullLeftLink = new MediaLink();
							mediaFullLeftLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_LEFT);
							mediaFullLeftLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaFullLeftLink.setValue(productModel.getImageURLsFullLeft());
							mediaList.add(mediaFullLeftLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullRight())) {
							MediaLink mediaFullRightLink = new MediaLink();
							mediaFullRightLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_RIGHT);
							mediaFullRightLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaFullRightLink.setValue(productModel.getImageURLsFullRight());
							mediaList.add(mediaFullRightLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullSide())) {
							MediaLink mediaFullSideLink = new MediaLink();
							mediaFullSideLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_SIDE);
							mediaFullSideLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaFullSideLink.setValue(productModel.getImageURLsFullSide());
							mediaList.add(mediaFullSideLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsFullBack())) {
							MediaLink mediaFullBackLink = new MediaLink();
							mediaFullBackLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_BACK);
							mediaFullBackLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaFullBackLink.setValue(productModel.getImageURLsFullBack());
							mediaList.add(mediaFullBackLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsGrid())) {
							MediaLink mediaGridLink = new MediaLink();
							mediaGridLink.setId(MediaConstants.STRING_FOR_IMAGE_GRID);
							mediaGridLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaGridLink.setValue(productModel.getImageURLsGrid());
							mediaList.add(mediaGridLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsSmall())) {
							MediaLink mediaSmallLink = new MediaLink();
							mediaSmallLink.setId(MediaConstants.STRING_FOR_IMAGE_SMALL);
							mediaSmallLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaSmallLink.setValue(productModel.getImageURLsSmall());
							mediaList.add(mediaSmallLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsSticker())) {
							MediaLink mediaStickerLink = new MediaLink();
							mediaStickerLink.setId(MediaConstants.STRING_FOR_IMAGE_STICKER);
							mediaStickerLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaStickerLink.setValue(productModel.getImageURLsSticker());
							mediaList.add(mediaStickerLink);
						}

						if (StringUtils.isNotBlank(productModel.getImageURLsIcon())) {
							MediaLink mediaIconLink = new MediaLink();
							mediaIconLink.setId(MediaConstants.STRING_FOR_IMAGE_ICON);
							mediaIconLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaIconLink.setValue(productModel.getImageURLsIcon());
							mediaList.add(mediaIconLink);
						}

						if (StringUtils.isNotBlank(productModel.getThreeDSpin())) {
							MediaLink media3DSpinLink = new MediaLink();
							media3DSpinLink.setId(MediaConstants.STRING_FOR_IMAGE_3DSPIN);
							media3DSpinLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							media3DSpinLink.setValue(productModel.getThreeDSpin());
							mediaList.add(media3DSpinLink);
						}

						if (StringUtils.isNotBlank(productModel.getSupport())) {
							MediaLink mediaSupportLink = new MediaLink();
							mediaSupportLink.setId(MediaConstants.STRING_FOR_IMAGE_SUPPORT);
							mediaSupportLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaSupportLink.setValue(productModel.getSupport());
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
								&& groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {
							deviceDetails.setPromotionsPackage(merchandisingPromotionsPackage);
							deviceList.add(deviceDetails);
							count++;
						} else if (isLeadMemberFromSolr.get(leadMember)
								&& groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)) {
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
			LogHelper.info(DeviceTilesDaoUtils.class, "Products not provided while converting ProductModelListToDeviceList.");
		}
		facetedDevice.setDevice(deviceList);
		facetedDevice.setNoOfRecordsFound(count);
		facetedDevice.setProductGroupDetails(listOfGroupDetails);
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
			com.vf.uk.dal.device.entity.BundlePrice priceInfo = priceForBundleAndHardware.getBundlePrice();
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
	 * @return
	 */
	public static List<com.vf.uk.dal.device.entity.DeviceFinancingOption> getDeviceFinaceOptions (List<DeviceFinancingOption> deviceFinancingOption){
		List<com.vf.uk.dal.device.entity.DeviceFinancingOption> financeOptions = null;
		if(deviceFinancingOption!=null && !deviceFinancingOption.isEmpty())
		{
			financeOptions = new ArrayList<>();
				for(DeviceFinancingOption financsOption: deviceFinancingOption){
				com.vf.uk.dal.device.entity.DeviceFinancingOption finance= new com.vf.uk.dal.device.entity.DeviceFinancingOption();
				finance.setApr(financsOption.getApr());
				finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
				finance.setFinanceProvider(financsOption.getFinanceProvider());
				finance.setFinanceTerm(financsOption.getFinanceTerm());
				com.vf.uk.dal.device.datamodel.product.Price monthly= financsOption.getMonthlyPrice();
				com.vf.uk.dal.device.entity.Price deviceMonthlyPrice = new com.vf.uk.dal.device.entity.Price();
				deviceMonthlyPrice.setGross(monthly.getGross());
				deviceMonthlyPrice.setNet(monthly.getNet());
				deviceMonthlyPrice.setVat(monthly.getVat());
				finance.setMonthlyPrice(deviceMonthlyPrice);
				com.vf.uk.dal.device.datamodel.product.Price totalInterest= financsOption.getTotalPriceWithInterest();
				com.vf.uk.dal.device.entity.Price totalPriceWithInterest = new com.vf.uk.dal.device.entity.Price();
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
	 * @return
	 */
	public static PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrUtils(List<OfferAppliedPriceModel> offers,
			String leadPlanId) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.entity.BundlePrice bundlePrice = new com.vf.uk.dal.device.entity.BundlePrice();
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
						hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(offer.getFinancingOptions()));
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
						hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(offer.getFinancingOptions()));
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
					} else {
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

	public static PriceForBundleAndHardware getBundleAndHardwarePriceFromSolrWithoutOfferCode(ProductModel productModel,
			BundleModel bundleModel, String leadPlanId) {
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		HardwarePrice hardwarePrice = new HardwarePrice();
		com.vf.uk.dal.device.entity.BundlePrice bundlePrice = new com.vf.uk.dal.device.entity.BundlePrice();
		if (productModel != null) {
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
				hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(productModel.getFinancingOptions()));
				priceForBundleAndHardware.setOneOffDiscountPrice(oneOffDiscountPrice);
				priceForBundleAndHardware.setOneOffPrice(oneOffPrice);
				priceForBundleAndHardware.setHardwarePrice(hardwarePrice);
			} else if (productModel.getOneOffDiscountedGrossPrice() != null
					&& productModel.getOneOffGrossPrice() != null
					&& !productModel.getOneOffGrossPrice().equals(productModel.getOneOffDiscountedGrossPrice())) {
				Price oneOffDiscountPrice = new Price();
				oneOffDiscountPrice
						.setGross(CommonUtility.getpriceFormat(productModel.getOneOffDiscountedGrossPrice()));
				oneOffDiscountPrice.setNet(CommonUtility.getpriceFormat(productModel.getOneOffDiscountedNetPrice()));
				oneOffDiscountPrice.setVat(CommonUtility.getpriceFormat(productModel.getOneOffDiscountedVatPrice()));
				Price oneOffPrice = new Price();
				oneOffPrice.setGross(CommonUtility.getpriceFormat(productModel.getOneOffGrossPrice()));
				oneOffPrice.setNet(CommonUtility.getpriceFormat(productModel.getOneOffNetPrice()));
				oneOffPrice.setVat(CommonUtility.getpriceFormat(productModel.getOneOffVatPrice()));
				hardwarePrice.setOneOffPrice(oneOffPrice);
				hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
				hardwarePrice.setHardwareId(productModel.getProductId());
				hardwarePrice.setFinancingOptions(getDeviceFinaceOptions(productModel.getFinancingOptions()));
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
				monthlyDiscountPrice.setNet(CommonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceNet()));
				monthlyDiscountPrice.setVat(CommonUtility.getpriceFormat(productModel.getBundleMonthlyDiscPriceVat()));
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
		}
		if (bundleModel != null) {
			if (bundleModel.getMonthlyDiscountedGrossPrice() != null && bundleModel.getMonthlyGrossPrice() != null
					&& bundleModel.getMonthlyDiscountedGrossPrice().equals(bundleModel.getMonthlyGrossPrice())) {
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
				monthlyDiscountPrice.setNet(CommonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedNetPrice()));
				monthlyDiscountPrice.setVat(CommonUtility.getpriceFormat(bundleModel.getMonthlyDiscountedVatPrice()));
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
		return priceForBundleAndHardware;
	}

	public static Boolean getPreOrBackOderable(String preOrderable) {
		Boolean result;
		if (Constants.IS_PREORDERABLE_YES.equalsIgnoreCase(preOrderable)) {
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
	 * @return
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