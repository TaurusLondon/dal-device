package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.DeviceFinancingOption;
import com.vf.uk.dal.device.client.entity.price.HardwarePrice;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.model.solr.DevicePreCalculatedData;
import com.vf.uk.dal.device.model.solr.Media;
import com.vf.uk.dal.device.model.solr.MonthlyDiscountPrice;
import com.vf.uk.dal.device.model.solr.MonthlyPrice;
import com.vf.uk.dal.device.model.solr.OfferAppliedPriceDetails;
import com.vf.uk.dal.device.model.solr.OneOffDiscountPrice;
import com.vf.uk.dal.device.model.solr.OneOffPrice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheDeviceDaoUtils {

	private static final String MEDIA = "media";

	public CacheDeviceDaoUtils() {
		/**
		 * constructor
		 */
	}

	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String DATA_NOT_FOUND = "NA";
	public static final String STRING_TEXT_ALLOWANCE = "TEXT";
	public static final String IS_LEAD_MEMEBER_NO = "N";
	public static final String STRING_PROMOTION_MEDIA = "merchandisingPromotions.merchandisingPromotion.PromotionMedia";
	public static final String STRING_OFFERS_LABEL = "merchandisingPromotions.merchandisingPromotion.label";
	public static final String STRING_OFFERS_DESCRIPTION = "merchandisingPromotions.merchandisingPromotion.description";
	public static final String PROMO_CATEGORY_PRICING_DISCOUNT = "Pricing_Discount";
	public static final String PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT = "Pricing_Automatic_Discount";
	public static final String PROMO_CATEGORY_NON_PRICING_DISCOUNT = "Non_Pricing_Discount";
	public static final String PROMO_TYPE_BUNDLEPROMOTION = "BP";
	public static final String PROMO_TYPE_HARDWAREPROMOTION = "HW";
	public static final String STRING_PRICE_ESTABLISHED_LABEL = "merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel";
	public static final String STRING_PRICE_PROMOTION_MEDIA = "merchandisingPromotions.merchandisingPromotion.promotionMedia";
	public static final String STRING_URL_ALLOWANCE = "URL";
	public static final String PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT = "Pricing_Upgrade_Discount";
	public static final String PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT = "Pricing_SecondLine_Discount";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";

	/**
	 * @author krishna.reddy @Sprint-6.6
	 * @param ilsPricewithoutOfferCode
	 * @return mapOfDevicePrice
	 */
	public Map<String, List<PriceForBundleAndHardware>> getILSPriceWithoutOfferCode(
			List<PriceForBundleAndHardware> ilsPricewithoutOfferCode) {
		Map<String, List<PriceForBundleAndHardware>> mapOfDevicePrice = new HashMap<>();
		ilsPricewithoutOfferCode.forEach(price -> {
			List<PriceForBundleAndHardware> listOfDevicePrice = null;
			if (price != null && price.getHardwarePrice() != null) {
				if (mapOfDevicePrice.containsKey(price.getHardwarePrice().getHardwareId())) {
					listOfDevicePrice = mapOfDevicePrice.get(price.getHardwarePrice().getHardwareId());
					listOfDevicePrice.add(price);
				} else {
					listOfDevicePrice = new ArrayList<>();
					listOfDevicePrice.add(price);
					mapOfDevicePrice.put(price.getHardwarePrice().getHardwareId(), listOfDevicePrice);
				}
			}
		});
		return mapOfDevicePrice;

	}

	/**
	 * converts the BundleHeaderForDevice to ProductGroupForDeviceListing
	 * 
	 * @param bundleHeaderForDevice
	 * @param groupId
	 * @param leadMemberId
	 * @param leadPlanId
	 * @return DevicePreCalculatedData
	 */
	public DevicePreCalculatedData convertBundleHeaderForDeviceToProductGroupForDeviceListing(String deviceId,
			String leadPlanId, String groupname, String groupId,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, Map<String, String> leadMemberMap,
			Map<String, String> leadMemberMapForUpgrade, String upgradeLeadPlanId, String groupType) {
		DevicePreCalculatedData productGroupForDeviceListing = null;
		productGroupForDeviceListing = new DevicePreCalculatedData();
		productGroupForDeviceListing.setDeviceId(deviceId);
		productGroupForDeviceListing.setGroupType(groupType);
		productGroupForDeviceListing.setProductGroupName(groupname);

		if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYM)) {
			productGroupForDeviceListing.setPaymProductGroupId(groupId);
		}
		if (StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
			productGroupForDeviceListing.setPaygProductGroupId(groupId);
		}
		productGroupForDeviceListing.setUpgradeLeadPlanId(upgradeLeadPlanId);
		if (leadMemberMap.containsKey(deviceId)) {
			productGroupForDeviceListing.setIsLeadMember(leadMemberMap.get(deviceId));
			productGroupForDeviceListing.setNonUpgradeLeadDeviceId(deviceId);
		} else {
			productGroupForDeviceListing.setIsLeadMember(IS_LEAD_MEMEBER_NO);
		}
		if (leadMemberMapForUpgrade != null && !leadMemberMapForUpgrade.isEmpty()
				&& leadMemberMapForUpgrade.containsKey(deviceId)) {
			productGroupForDeviceListing.setUpgradeLeadDeviceId(deviceId);
		}
		if ((StringUtils.isNotBlank(leadPlanId) && StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYM))
				|| StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
			PriceForBundleAndHardware priceForBundleAndHardware1 = listOfPriceForBundleAndHardware.get(0);
			productGroupForDeviceListing.setNonUpgradeLeadPlanId(leadPlanId);
			productGroupForDeviceListing.setLeadPlanId(leadPlanId);
			Map<String, Object> priceMediaMap = getPriceInfoForSolr(priceForBundleAndHardware1);
			com.vf.uk.dal.device.model.solr.PriceInfo priceInfo = (com.vf.uk.dal.device.model.solr.PriceInfo) priceMediaMap
					.get("price");
			productGroupForDeviceListing.setPriceInfo(priceInfo);
			@SuppressWarnings("unchecked")
			List<com.vf.uk.dal.device.model.solr.Media> listOfMedia = (List<com.vf.uk.dal.device.model.solr.Media>) priceMediaMap
					.get(MEDIA);
			productGroupForDeviceListing.setMedia(listOfMedia);
		}

		return productGroupForDeviceListing;
	}

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @param listOfPriceForBundleAndHardwareWithOfferCode
	 * @return PriceInfoForSolr
	 */
	public Map<String, Object> getPriceInfoForSolr(PriceForBundleAndHardware priceForBundleAndHardware) {

		Map<String, Object> result = new ConcurrentHashMap<>();
		List<com.vf.uk.dal.device.model.solr.Media> listOfMedia = new ArrayList<>();
		BundlePrice bundlePrice = priceForBundleAndHardware.getBundlePrice();
		Price monthlyPrice = null;
		Price monthlyDiscountPrice = null;
		String bundleId = DATA_NOT_FOUND;
		if (bundlePrice != null && bundlePrice.getMonthlyPrice() != null
				&& bundlePrice.getMonthlyPrice().getGross() != null) {
			bundleId = bundlePrice.getBundleId();
			if (bundlePrice.getMerchandisingPromotions() != null) {
				com.vf.uk.dal.device.model.solr.Media mediaLink = new com.vf.uk.dal.device.model.solr.Media();
				mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
				String type = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
						+ bundlePrice.getMerchandisingPromotions().getTag();
				mediaLink.setType(type);
				mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
				mediaLink.setDescription(DATA_NOT_FOUND);
				mediaLink.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLink.setOfferCode(DATA_NOT_FOUND);
				setMediaLinkDiscountId(bundlePrice, mediaLink);
				setListOfMedia(listOfMedia, bundlePrice, bundleId);
				listOfMedia.add(mediaLink);
				addMediaLinkForPriceEstablishedInListOfMedia(listOfMedia, bundlePrice, bundleId);
				addMediaLinkForPromotionMediaInListOfMedia(listOfMedia, bundlePrice, bundleId);

			}

			monthlyPrice = bundlePrice.getMonthlyPrice();
			monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
		}
		HardwarePrice hardwarePrice = priceForBundleAndHardware.getHardwarePrice();
		String hardwareId = hardwarePrice.getHardwareId();

		if (hardwarePrice.getMerchandisingPromotions() != null) {
			com.vf.uk.dal.device.model.solr.Media mediaLink1 = new com.vf.uk.dal.device.model.solr.Media();
			mediaLink1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
			String type2 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLink1.setType(type2);
			mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
			mediaLink1.setDescription(DATA_NOT_FOUND);
			mediaLink1.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLink1.setOfferCode(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLink1);
			setMediaLinkForDescriptionForHardware(listOfMedia, bundleId, hardwarePrice);
			listOfMedia.add(mediaLink1);
			setMediaLinkForPriceLabelForHardware(listOfMedia, bundleId, hardwarePrice);
			setMediaLinkForPromotionMediaForHardware(listOfMedia, bundleId, hardwarePrice);

		}
		Price oneoffPrice = hardwarePrice.getOneOffPrice();
		Price oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
		List<DeviceFinancingOption> deviceFinancingOption = hardwarePrice.getFinancingOptions();
		List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> financeOptions = null;
		financeOptions = setFinancingOptionForOfferPrice(deviceFinancingOption);
		MonthlyPrice mnthlyPrice = setMonthlyPriceForOfferAppliedPrice(monthlyPrice);
		MonthlyDiscountPrice mnthlyDiscPrice = setMonthlyDiscountPriceForOfferAppliedPrice(monthlyDiscountPrice);
		com.vf.uk.dal.device.model.solr.BundlePrice bp = new com.vf.uk.dal.device.model.solr.BundlePrice();
		bp.setBundleId(DATA_NOT_FOUND.equalsIgnoreCase(bundleId) ? null : bundleId);
		bp.setMonthlyPrice(mnthlyPrice);
		bp.setMonthlyDiscountPrice(mnthlyDiscPrice);
		OneOffPrice onffPrice = null;
		if (oneoffPrice != null) {
			onffPrice = new OneOffPrice();
			onffPrice.setGross(oneoffPrice.getGross());
			onffPrice.setNet(oneoffPrice.getNet());
			onffPrice.setVat(oneoffPrice.getVat());
			log.info(hardwareId + "One Off Price Gross" + onffPrice.getGross());
		}
		OneOffDiscountPrice onffDiscPrice = null;
		if (oneOffDisPrice != null) {
			onffDiscPrice = new OneOffDiscountPrice();
			onffDiscPrice.setGross(oneOffDisPrice.getGross());
			onffDiscPrice.setNet(oneOffDisPrice.getNet());
			onffDiscPrice.setVat(oneOffDisPrice.getVat());
			log.info(hardwareId + "One Off Disc Price Gross" + oneOffDisPrice.getGross());
		}
		com.vf.uk.dal.device.model.solr.HardwarePrice hw = new com.vf.uk.dal.device.model.solr.HardwarePrice();
		hw.setHardwareId(hardwareId);
		hw.setOneOffPrice(onffPrice);
		hw.setOneOffDiscountPrice(onffDiscPrice);
		hw.setFinancingOptions(financeOptions);
		com.vf.uk.dal.device.model.solr.PriceInfo priceinfo = new com.vf.uk.dal.device.model.solr.PriceInfo();
		priceinfo.setBundlePrice(bp);
		priceinfo.setHardwarePrice(hw);

		result.put("price", priceinfo);
		result.put(MEDIA, listOfMedia);
		return result;

	}

	private void setMediaLinkForPromotionMediaForHardware(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String bundleId, HardwarePrice hardwarePrice) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPromotionMedia())) {
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPromotionMedia
					.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_PROMOTION_MEDIA);
			String type4 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPromotionMedia.setType(type4);
			mediaLinkForPromotionMedia.setValue(hardwarePrice.getMerchandisingPromotions().getPromotionMedia());
			mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForPromotionMedia);
			mediaLinkForPromotionMedia.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPromotionMedia);
		}
	}

	private void setMediaLinkForPriceLabelForHardware(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String bundleId, HardwarePrice hardwarePrice) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
			// PriceEstablishedLabel
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPriceLabel = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPriceLabel.setId(
					hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
			String type3 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceLabel.setType(type3);
			mediaLinkForPriceLabel.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceLabel.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForPriceLabel);
			mediaLinkForPriceLabel.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForPriceLabel.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPriceLabel);
		}
	}

	private void setMediaLinkForDescriptionForHardware(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String bundleId, HardwarePrice hardwarePrice) {
		String description = null;
		if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
			description = hardwarePrice.getMerchandisingPromotions().getDescription();
			com.vf.uk.dal.device.model.solr.Media mediaLinkForDescription1 = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForDescription1
					.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_DESCRIPTION);
			String type3 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForDescription1.setType(type3);
			mediaLinkForDescription1.setValue(description);
			mediaLinkForDescription1.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForDescription1.setOfferCode(DATA_NOT_FOUND);
			mediaLinkForDescription1.setDescription(hardwarePrice.getMerchandisingPromotions().getDescription());
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForDescription1);
			listOfMedia.add(mediaLinkForDescription1);
		}
	}

	private void setMediaLinkDiscountIdForHardwarePrice(HardwarePrice hardwarePrice,
			com.vf.uk.dal.device.model.solr.Media mediaLink1) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
			mediaLink1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
		} else {
			mediaLink1.setDiscountId(DATA_NOT_FOUND);
		}
	}

	private void addMediaLinkForPromotionMediaInListOfMedia(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			BundlePrice bundlePrice, String bundleId) {
		if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPromotionMedia())) {
			// PromotionMedia
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPromotionMedia
					.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_PROMOTION_MEDIA);
			String type4 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPromotionMedia.setType(type4);
			mediaLinkForPromotionMedia.setValue(bundlePrice.getMerchandisingPromotions().getPromotionMedia());
			mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountId(bundlePrice, mediaLinkForPromotionMedia);
			mediaLinkForPromotionMedia.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPromotionMedia);
		}
	}

	private void addMediaLinkForPriceEstablishedInListOfMedia(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			BundlePrice bundlePrice, String bundleId) {
		if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPriceEstablished
					.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
			String type3 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceEstablished.setType(type3);
			mediaLinkForPriceEstablished.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceEstablished.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountId(bundlePrice, mediaLinkForPriceEstablished);
			mediaLinkForPriceEstablished.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForPriceEstablished.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPriceEstablished);
		}
	}

	private void setListOfMedia(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia, BundlePrice bundlePrice,
			String bundleId) {
		String description = null;
		if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
			description = bundlePrice.getMerchandisingPromotions().getDescription();
			com.vf.uk.dal.device.model.solr.Media mediaLinkForDescription = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForDescription
					.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_DESCRIPTION);
			String type1 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForDescription.setType(type1);
			mediaLinkForDescription.setValue(description);
			mediaLinkForDescription.setDescription(description);
			mediaLinkForDescription.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForDescription.setOfferCode(DATA_NOT_FOUND);
			setMediaLinkDiscountId(bundlePrice, mediaLinkForDescription);
			listOfMedia.add(mediaLinkForDescription);
		}
	}

	private void setMediaLinkDiscountId(BundlePrice bundlePrice, com.vf.uk.dal.device.model.solr.Media mediaLink) {
		if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
			mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
		} else {
			mediaLink.setDiscountId(DATA_NOT_FOUND);
		}
	}

	/**
	 * @author manoj.bera
	 * @param deviceId
	 * @param listOfPriceForBundleAndHardwareMap
	 * @return ListOfOfferAppliedPrice
	 */
	public Map<String, Object> getListOfOfferAppliedPrice(String deviceId,
			Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsPriceForBundleAndHardwareMap) {

		Map<String, Object> result = new HashMap<>();
		List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = new ArrayList<>();
		List<com.vf.uk.dal.device.model.solr.Media> listOfMedia = new ArrayList<>();
		for (Entry<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsJourneyEntry : ilsPriceForBundleAndHardwareMap
				.entrySet()) {
			String journeyType = ilsJourneyEntry.getKey();
			Map<String, Map<String, List<PriceForBundleAndHardware>>> listOfPriceForBundleAndHardwareMap = ilsJourneyEntry
					.getValue();
			setListOfOfferAppliedPriceDetails(deviceId, listOfOfferAppliedPriceDetails, listOfMedia, journeyType,
					listOfPriceForBundleAndHardwareMap);
		}
		result.put("offeredPrice", listOfOfferAppliedPriceDetails);
		result.put(MEDIA, listOfMedia);
		return result;

	}

	private void setListOfOfferAppliedPriceDetails(String deviceId,
			List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails,
			List<com.vf.uk.dal.device.model.solr.Media> listOfMedia, String journeyType,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> listOfPriceForBundleAndHardwareMap) {
		for (Entry<String, Map<String, List<PriceForBundleAndHardware>>> entry : listOfPriceForBundleAndHardwareMap
				.entrySet()) {

			Map<String, List<PriceForBundleAndHardware>> offeredPriceMap = entry.getValue();

			String offerCode = entry.getKey();
			List<PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = getPriceForBundleAndHardwareWithOfferCodeList(
					deviceId, offeredPriceMap);
			if (priceForBundleAndHardwareWithOfferCodeList != null
					&& !priceForBundleAndHardwareWithOfferCodeList.isEmpty()) {
				priceForBundleAndHardwareWithOfferCodeList.forEach(priceForBundleAndHardwareWithOfferCode -> {
					OfferAppliedPriceDetails offerAppliedPriceDetails = new OfferAppliedPriceDetails();
					BundlePrice bundlePrice = priceForBundleAndHardwareWithOfferCode.getBundlePrice();
					Price monthlyPrice = null;
					Price monthlyDiscountPrice = null;
					String bundleId = null;
					String hardwareId = null;
					Price oneoffPrice = null;
					Price oneOffDisPrice = null;
					if (bundlePrice != null) {
						bundleId = bundlePrice.getBundleId();
						monthlyPrice = bundlePrice.getMonthlyPrice();
						monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
						setListOfMediaForOfferPrice(listOfMedia, offerCode, bundlePrice, bundleId);
					}
					HardwarePrice hardwarePrice = priceForBundleAndHardwareWithOfferCode.getHardwarePrice();
					List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> financeOptions = null;
					if (hardwarePrice != null) {
						hardwareId = hardwarePrice.getHardwareId();
						oneoffPrice = hardwarePrice.getOneOffPrice();
						oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
						List<DeviceFinancingOption> deviceFinancingOption = hardwarePrice.getFinancingOptions();
						financeOptions = setFinancingOptionForOfferPrice(deviceFinancingOption);
						setMerchandisingMedia(listOfMedia, offerCode, bundleId, hardwarePrice);
					}
					MonthlyPrice mnthlyPrice = setMonthlyPriceForOfferAppliedPrice(monthlyPrice);
					MonthlyDiscountPrice mnthlyDiscPrice = setMonthlyDiscountPriceForOfferAppliedPrice(
							monthlyDiscountPrice);
					com.vf.uk.dal.device.model.solr.BundlePrice bp = new com.vf.uk.dal.device.model.solr.BundlePrice();
					bp.setBundleId(bundleId);
					bp.setMonthlyPrice(mnthlyPrice);
					bp.setMonthlyDiscountPrice(mnthlyDiscPrice);
					OneOffPrice onffPrice = setOneOffPriceForOfferAppliedPrice(oneoffPrice);
					OneOffDiscountPrice onffDiscPrice = setOneOffDiscountPrice(oneOffDisPrice);
					com.vf.uk.dal.device.model.solr.HardwarePrice hw = new com.vf.uk.dal.device.model.solr.HardwarePrice();
					hw.setHardwareId(hardwareId);
					hw.setOneOffPrice(onffPrice);
					hw.setOneOffDiscountPrice(onffDiscPrice);
					hw.setFinancingOptions(financeOptions);
					offerAppliedPriceDetails.setDeviceId(hardwareId);
					offerAppliedPriceDetails.setOfferCode(offerCode);
					offerAppliedPriceDetails.setBundlePrice(bp);
					offerAppliedPriceDetails.setHardwarePrice(hw);
					offerAppliedPriceDetails.setJourneyType(journeyType);
					listOfOfferAppliedPriceDetails.add(offerAppliedPriceDetails);
				});
			}
		}
	}

	private OneOffDiscountPrice setOneOffDiscountPrice(Price oneOffDisPrice) {
		OneOffDiscountPrice onffDiscPrice = null;
		if (oneOffDisPrice != null) {
			onffDiscPrice = new OneOffDiscountPrice();
			onffDiscPrice.setGross(oneOffDisPrice.getGross());
			onffDiscPrice.setNet(oneOffDisPrice.getNet());
			onffDiscPrice.setVat(oneOffDisPrice.getVat());
		}
		return onffDiscPrice;
	}

	private OneOffPrice setOneOffPriceForOfferAppliedPrice(Price oneoffPrice) {
		OneOffPrice onffPrice = null;
		if (oneoffPrice != null) {
			onffPrice = new OneOffPrice();
			onffPrice.setGross(oneoffPrice.getGross());
			onffPrice.setNet(oneoffPrice.getNet());
			onffPrice.setVat(oneoffPrice.getVat());
		}
		return onffPrice;
	}

	private MonthlyDiscountPrice setMonthlyDiscountPriceForOfferAppliedPrice(Price monthlyDiscountPrice) {
		MonthlyDiscountPrice mnthlyDiscPrice = null;
		if (monthlyDiscountPrice != null) {
			mnthlyDiscPrice = new MonthlyDiscountPrice();
			mnthlyDiscPrice.setGross(monthlyDiscountPrice.getGross());
			mnthlyDiscPrice.setNet(monthlyDiscountPrice.getNet());
			mnthlyDiscPrice.setVat(monthlyDiscountPrice.getVat());
		}
		return mnthlyDiscPrice;
	}

	private MonthlyPrice setMonthlyPriceForOfferAppliedPrice(Price monthlyPrice) {
		MonthlyPrice mnthlyPrice = null;
		if (monthlyPrice != null) {
			mnthlyPrice = new MonthlyPrice();
			mnthlyPrice.setGross(monthlyPrice.getGross());
			mnthlyPrice.setNet(monthlyPrice.getNet());
			mnthlyPrice.setVat(monthlyPrice.getVat());
		}
		return mnthlyPrice;
	}

	private void setMerchandisingMedia(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia, String offerCode,
			String bundleId, HardwarePrice hardwarePrice) {
		if (hardwarePrice.getMerchandisingPromotions() != null) {
			com.vf.uk.dal.device.model.solr.Media mediaLink1 = new com.vf.uk.dal.device.model.solr.Media();
			mediaLink1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
			String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLink1.setType(type6);
			mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
			mediaLink1.setDescription(DATA_NOT_FOUND);
			mediaLink1.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
			mediaLink1.setOfferCode(offerCode);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLink1);

			setMediaLinkForDescriptionForOffer(listOfMedia, offerCode, bundleId, hardwarePrice);
			listOfMedia.add(mediaLink1);
			setMediaLinkForPriceEstablishedOfferPrice(listOfMedia, offerCode, bundleId, hardwarePrice);
			setMediaLinkForPromotionMedia(listOfMedia, offerCode, bundleId, hardwarePrice);
		}
	}

	private void setMediaLinkForDescriptionForOffer(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String offerCode, String bundleId, HardwarePrice hardwarePrice) {
		String description = null;
		if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
			description = hardwarePrice.getMerchandisingPromotions().getDescription();
			com.vf.uk.dal.device.model.solr.Media mediaLinkForDescription1 = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForDescription1
					.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_DESCRIPTION);
			String type7 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForDescription1.setType(type7);
			mediaLinkForDescription1.setValue(description);
			mediaLinkForDescription1.setDescription(description);
			mediaLinkForDescription1.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
			mediaLinkForDescription1.setOfferCode(offerCode);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForDescription1);
			listOfMedia.add(mediaLinkForDescription1);
		}
	}

	private void setMediaLinkForPriceEstablishedOfferPrice(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String offerCode, String bundleId, HardwarePrice hardwarePrice) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
			// PriceEstablished Label
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPriceEstablished.setId(
					hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
			String type8 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceEstablished.setType(type8);
			mediaLinkForPriceEstablished
					.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceEstablished.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForPriceEstablished);
			mediaLinkForPriceEstablished.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
			mediaLinkForPriceEstablished.setOfferCode(offerCode);
			listOfMedia.add(mediaLinkForPriceEstablished);
		}
	}

	private void setMediaLinkForPromotionMedia(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String offerCode, String bundleId, HardwarePrice hardwarePrice) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPromotionMedia())) {
			// PromotionMedia Label
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPromotionMedia
					.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_PROMOTION_MEDIA);
			String type9 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPromotionMedia.setType(type9);
			mediaLinkForPromotionMedia.setValue(hardwarePrice.getMerchandisingPromotions().getPromotionMedia());
			mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForPromotionMedia);
			mediaLinkForPromotionMedia.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
			mediaLinkForPromotionMedia.setOfferCode(offerCode);
			listOfMedia.add(mediaLinkForPromotionMedia);
		}
	}

	private List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> setFinancingOptionForOfferPrice(
			List<DeviceFinancingOption> deviceFinancingOption) {
		List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> financeOptions = null;
		if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
			financeOptions = new ArrayList<>();
			for (DeviceFinancingOption financsOption : deviceFinancingOption) {
				com.vf.uk.dal.device.model.solr.DeviceFinancingOption finance = new com.vf.uk.dal.device.model.solr.DeviceFinancingOption();
				finance.setApr(financsOption.getApr());
				finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
				finance.setFinanceProvider(financsOption.getFinanceProvider());
				finance.setFinanceTerm(financsOption.getFinanceTerm());
				com.vf.uk.dal.device.client.entity.price.Price monthly = financsOption.getMonthlyPrice();
				com.vf.uk.dal.device.model.solr.Price deviceMonthlyPrice = new com.vf.uk.dal.device.model.solr.Price();
				deviceMonthlyPrice.setGross(monthly.getGross());
				deviceMonthlyPrice.setNet(monthly.getNet());
				deviceMonthlyPrice.setVat(monthly.getVat());
				finance.setMonthlyPrice(deviceMonthlyPrice);
				com.vf.uk.dal.device.client.entity.price.Price totalInterest = financsOption
						.getTotalPriceWithInterest();
				com.vf.uk.dal.device.model.solr.Price totalPriceWithInterest = new com.vf.uk.dal.device.model.solr.Price();
				totalPriceWithInterest.setGross(totalInterest.getGross());
				totalPriceWithInterest.setNet(totalInterest.getNet());
				totalPriceWithInterest.setVat(totalInterest.getVat());
				finance.setTotalPriceWithInterest(totalPriceWithInterest);
				financeOptions.add(finance);
			}
		}
		return financeOptions;
	}

	private void setListOfMediaForOfferPrice(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia, String offerCode,
			BundlePrice bundlePrice, String bundleId) {
		if (bundlePrice.getMerchandisingPromotions() != null) {
			com.vf.uk.dal.device.model.solr.Media mediaLink = new com.vf.uk.dal.device.model.solr.Media();
			mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
			String type4 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLink.setType(type4);
			mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
			mediaLink.setDescription(DATA_NOT_FOUND);
			mediaLink.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
			mediaLink.setOfferCode(offerCode);
			setMediaLinkDiscountId(bundlePrice, mediaLink);
			String description = null;
			if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
				description = bundlePrice.getMerchandisingPromotions().getDescription();
				com.vf.uk.dal.device.model.solr.Media mediaLinkForDescription = new com.vf.uk.dal.device.model.solr.Media();
				mediaLinkForDescription
						.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_DESCRIPTION);
				String type5 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
						+ bundlePrice.getMerchandisingPromotions().getTag();
				mediaLinkForDescription.setType(type5);
				mediaLinkForDescription.setValue(description);
				mediaLinkForDescription.setDescription(description);
				mediaLinkForDescription.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
				mediaLinkForDescription.setOfferCode(offerCode);
				setMediaLinkDiscountId(bundlePrice, mediaLinkForDescription);
				listOfMedia.add(mediaLinkForDescription);
			}
			listOfMedia.add(mediaLink);
			if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
				// PriceEstablished Label
				com.vf.uk.dal.device.model.solr.Media mediaLinkForPriceEstablishedLabel = new com.vf.uk.dal.device.model.solr.Media();
				mediaLinkForPriceEstablishedLabel.setId(
						bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
				String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
						+ bundlePrice.getMerchandisingPromotions().getTag();
				mediaLinkForPriceEstablishedLabel.setType(type6);
				mediaLinkForPriceEstablishedLabel
						.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
				mediaLinkForPriceEstablishedLabel.setDescription(DATA_NOT_FOUND);
				setMediaLinkDiscountId(bundlePrice, mediaLinkForPriceEstablishedLabel);
				mediaLinkForPriceEstablishedLabel.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
				mediaLinkForPriceEstablishedLabel.setOfferCode(offerCode);
				listOfMedia.add(mediaLinkForPriceEstablishedLabel);
			}
			if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPromotionMedia())) {
				// PromotionMedia Label
				com.vf.uk.dal.device.model.solr.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.device.model.solr.Media();
				mediaLinkForPromotionMedia.setId(
						bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_PROMOTION_MEDIA);
				String type7 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
						+ bundlePrice.getMerchandisingPromotions().getTag();
				mediaLinkForPromotionMedia.setType(type7);
				mediaLinkForPromotionMedia.setValue(bundlePrice.getMerchandisingPromotions().getPromotionMedia());
				mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
				setMediaLinkDiscountId(bundlePrice, mediaLinkForPromotionMedia);
				mediaLinkForPromotionMedia.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
				mediaLinkForPromotionMedia.setOfferCode(offerCode);
				listOfMedia.add(mediaLinkForPromotionMedia);
			}
		}
	}

	/**
	 * @author krishna.reddy Sprint-6.6
	 * @param deviceId
	 * @param ilsPriceForBundleAndHardwareMap
	 * @return ListOfIlsPriceWithoutOfferCode
	 */
	public Map<String, Object> getListOfIlsPriceWithoutOfferCode(String deviceId,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap) {
		Map<String, Object> result = new HashMap<>();
		List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = new ArrayList<>();
		List<com.vf.uk.dal.device.model.solr.Media> listOfMedia = new ArrayList<>();

		for (Entry<String, Map<String, List<PriceForBundleAndHardware>>> entry : ilsPriceForBundleAndHardwareMap
				.entrySet()) {
			String journeyType = entry.getKey();
			String promoCatagoery = getPromoCategory(journeyType);
			Map<String, List<PriceForBundleAndHardware>> offeredPriceMap = entry.getValue();

			List<PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = getPriceForBundleAndHardwareWithOfferCodeList(
					deviceId, offeredPriceMap);
			if (priceForBundleAndHardwareWithOfferCodeList != null
					&& !priceForBundleAndHardwareWithOfferCodeList.isEmpty()) {
				setListOfOfferApplyPriceDetails(listOfOfferAppliedPriceDetails, listOfMedia, journeyType,
						promoCatagoery, priceForBundleAndHardwareWithOfferCodeList);
			}
		}

		result.put("offeredPrice", listOfOfferAppliedPriceDetails);
		result.put(MEDIA, listOfMedia);
		return result;
	}

	private void setListOfOfferApplyPriceDetails(List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails,
			List<com.vf.uk.dal.device.model.solr.Media> listOfMedia, String journeyType, String promoCatagoery,
			List<PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList) {
		for (PriceForBundleAndHardware priceForBundleAndHardwareWithOfferCode : priceForBundleAndHardwareWithOfferCodeList) {
			OfferAppliedPriceDetails offerAppliedPriceDetails = new OfferAppliedPriceDetails();
			BundlePrice bundlePrice = priceForBundleAndHardwareWithOfferCode.getBundlePrice();
			Price monthlyPrice = null;
			Price monthlyDiscountPrice = null;
			String bundleId = null;
			String hardwareId = null;
			Price oneoffPrice = null;
			Price oneOffDisPrice = null;
			if (bundlePrice != null) {
				bundleId = bundlePrice.getBundleId();
				monthlyPrice = bundlePrice.getMonthlyPrice();
				monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
				if (bundlePrice.getMerchandisingPromotions() != null) {
					com.vf.uk.dal.device.model.solr.Media mediaLink = new com.vf.uk.dal.device.model.solr.Media();
					mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
					String type4 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
							+ bundlePrice.getMerchandisingPromotions().getTag();
					mediaLink.setType(type4);
					mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
					mediaLink.setDescription(DATA_NOT_FOUND);
					mediaLink.setPromoCategory(promoCatagoery);
					mediaLink.setOfferCode(DATA_NOT_FOUND);
					setMediaLinkDiscountId(bundlePrice, mediaLink);
					setMediaLinkForDescriptionForBundlePrice(listOfMedia, promoCatagoery, bundlePrice, bundleId);
					listOfMedia.add(mediaLink);
					setMediaLinkForPriceEstablishedLabelForBundle(listOfMedia, promoCatagoery, bundlePrice, bundleId);
					setmediaLinkForPromotionMediaForBundle(listOfMedia, promoCatagoery, bundlePrice, bundleId);
				}
			}
			HardwarePrice hardwarePrice = priceForBundleAndHardwareWithOfferCode.getHardwarePrice();
			List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> financeOptions = null;
			if (hardwarePrice != null) {
				hardwareId = hardwarePrice.getHardwareId();
				oneoffPrice = hardwarePrice.getOneOffPrice();
				oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
				List<DeviceFinancingOption> deviceFinancingOption = hardwarePrice.getFinancingOptions();
				financeOptions = setFinancingOptionForOfferPrice(deviceFinancingOption);

				if (hardwarePrice.getMerchandisingPromotions() != null) {
					com.vf.uk.dal.device.model.solr.Media mediaLink1 = new com.vf.uk.dal.device.model.solr.Media();
					mediaLink1
							.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
					String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
							+ hardwarePrice.getMerchandisingPromotions().getTag();
					mediaLink1.setType(type6);
					mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
					mediaLink1.setDescription(DATA_NOT_FOUND);
					mediaLink1.setPromoCategory(promoCatagoery);
					mediaLink1.setOfferCode(DATA_NOT_FOUND);
					setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLink1);

					String description = null;
					if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
						description = hardwarePrice.getMerchandisingPromotions().getDescription();
						com.vf.uk.dal.device.model.solr.Media mediaLinkForDescription1 = new com.vf.uk.dal.device.model.solr.Media();
						mediaLinkForDescription1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
								+ STRING_OFFERS_DESCRIPTION);
						String type7 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION
								+ "&&" + hardwarePrice.getMerchandisingPromotions().getTag();
						mediaLinkForDescription1.setType(type7);
						mediaLinkForDescription1.setValue(description);
						mediaLinkForDescription1.setDescription(description);
						mediaLinkForDescription1.setPromoCategory(promoCatagoery);
						mediaLinkForDescription1.setOfferCode(DATA_NOT_FOUND);
						setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForDescription1);
						listOfMedia.add(mediaLinkForDescription1);
					}
					listOfMedia.add(mediaLink1);
					setmediaLinkForPriceEstablishedForHardware(listOfMedia, promoCatagoery, bundleId, hardwarePrice);
					setMediaLinkForPromotionMediaForHardware(listOfMedia, promoCatagoery, bundleId, hardwarePrice);
				}
			}
			MonthlyPrice mnthlyPrice = setMonthlyPriceForOfferAppliedPrice(monthlyPrice);
			MonthlyDiscountPrice mnthlyDiscPrice = setMonthlyDiscountPriceForOfferAppliedPrice(monthlyDiscountPrice);
			com.vf.uk.dal.device.model.solr.BundlePrice bp = new com.vf.uk.dal.device.model.solr.BundlePrice();
			bp.setBundleId(bundleId);
			bp.setMonthlyPrice(mnthlyPrice);
			bp.setMonthlyDiscountPrice(mnthlyDiscPrice);
			OneOffPrice onffPrice = setOneOffPriceForOfferAppliedPrice(oneoffPrice);
			OneOffDiscountPrice onffDiscPrice = setOneOffDiscountPrice(oneOffDisPrice);
			com.vf.uk.dal.device.model.solr.HardwarePrice hw = new com.vf.uk.dal.device.model.solr.HardwarePrice();
			hw.setHardwareId(hardwareId);
			hw.setOneOffPrice(onffPrice);
			hw.setOneOffDiscountPrice(onffDiscPrice);
			hw.setFinancingOptions(financeOptions);
			offerAppliedPriceDetails.setDeviceId(hardwareId);
			offerAppliedPriceDetails.setOfferCode(DATA_NOT_FOUND);
			offerAppliedPriceDetails.setBundlePrice(bp);
			offerAppliedPriceDetails.setHardwarePrice(hw);
			offerAppliedPriceDetails.setJourneyType(journeyType);
			listOfOfferAppliedPriceDetails.add(offerAppliedPriceDetails);
		}
	}

	private void setMediaLinkForPromotionMediaForHardware(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String promoCatagoery, String bundleId, HardwarePrice hardwarePrice) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPromotionMedia())) {
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPromotionMedia
					.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_PROMOTION_MEDIA);
			String type9 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPromotionMedia.setType(type9);
			mediaLinkForPromotionMedia.setValue(hardwarePrice.getMerchandisingPromotions().getPromotionMedia());
			mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForPromotionMedia);
			mediaLinkForPromotionMedia.setPromoCategory(promoCatagoery);
			mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPromotionMedia);
		}
	}

	private void setmediaLinkForPriceEstablishedForHardware(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String promoCatagoery, String bundleId, HardwarePrice hardwarePrice) {
		if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPriceEstablished.setId(
					hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
			String type8 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceEstablished.setType(type8);
			mediaLinkForPriceEstablished
					.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceEstablished.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountIdForHardwarePrice(hardwarePrice, mediaLinkForPriceEstablished);
			mediaLinkForPriceEstablished.setPromoCategory(promoCatagoery);
			mediaLinkForPriceEstablished.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPriceEstablished);
		}
	}

	private void setmediaLinkForPromotionMediaForBundle(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String promoCatagoery, BundlePrice bundlePrice, String bundleId) {
		if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPromotionMedia())) {
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPromotionMedia
					.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_PROMOTION_MEDIA);
			String type7 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPromotionMedia.setType(type7);
			mediaLinkForPromotionMedia.setValue(bundlePrice.getMerchandisingPromotions().getPromotionMedia());
			mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountId(bundlePrice, mediaLinkForPromotionMedia);
			mediaLinkForPromotionMedia.setPromoCategory(promoCatagoery);
			mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPromotionMedia);
		}
	}

	private void setMediaLinkForPriceEstablishedLabelForBundle(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String promoCatagoery, BundlePrice bundlePrice, String bundleId) {
		if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
			com.vf.uk.dal.device.model.solr.Media mediaLinkForPriceEstablishedLabel = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForPriceEstablishedLabel
					.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
			String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceEstablishedLabel.setType(type6);
			mediaLinkForPriceEstablishedLabel
					.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceEstablishedLabel.setDescription(DATA_NOT_FOUND);
			setMediaLinkDiscountId(bundlePrice, mediaLinkForPriceEstablishedLabel);
			mediaLinkForPriceEstablishedLabel.setPromoCategory(promoCatagoery);
			mediaLinkForPriceEstablishedLabel.setOfferCode(DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPriceEstablishedLabel);
		}
	}

	private void setMediaLinkForDescriptionForBundlePrice(List<com.vf.uk.dal.device.model.solr.Media> listOfMedia,
			String promoCatagoery, BundlePrice bundlePrice, String bundleId) {
		String description = null;
		if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
			description = bundlePrice.getMerchandisingPromotions().getDescription();
			com.vf.uk.dal.device.model.solr.Media mediaLinkForDescription = new com.vf.uk.dal.device.model.solr.Media();
			mediaLinkForDescription
					.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_DESCRIPTION);
			String type5 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&" + PROMO_TYPE_BUNDLEPROMOTION + "&&"
					+ bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForDescription.setType(type5);
			mediaLinkForDescription.setValue(description);
			mediaLinkForDescription.setDescription(description);
			mediaLinkForDescription.setPromoCategory(promoCatagoery);
			mediaLinkForDescription.setOfferCode(DATA_NOT_FOUND);
			setMediaLinkDiscountId(bundlePrice, mediaLinkForDescription);
			listOfMedia.add(mediaLinkForDescription);
		}
	}

	private List<PriceForBundleAndHardware> getPriceForBundleAndHardwareWithOfferCodeList(String deviceId,
			Map<String, List<PriceForBundleAndHardware>> offeredPriceMap) {
		List<PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = null;
		if (offeredPriceMap.containsKey(deviceId)) {
			priceForBundleAndHardwareWithOfferCodeList = offeredPriceMap.get(deviceId);
		}
		return priceForBundleAndHardwareWithOfferCodeList;
	}

	private String getPromoCategory(String journeyType) {
		String promoCatagoery = null;
		if (StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
			promoCatagoery = PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT;
		}
		if (StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_SECONDLINE)) {
			promoCatagoery = PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT;
		}
		return promoCatagoery;
	}

	/**
	 * 
	 * @param preCalcPlanList
	 * @return DevicePreCalculatedData
	 */
	public List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> convertDevicePreCalDataToSolrData(
			List<DevicePreCalculatedData> preCalcPlanList) {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData> deviceListObjectList = new ArrayList<>();

		preCalcPlanList.forEach(preCalList -> {
			com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData deviceListObject = new com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData();

			List<com.vf.uk.dal.device.model.merchandisingpromotion.Media> mediaList = null;
			List<Media> listOfMedia = preCalList.getMedia();
			if (listOfMedia != null && !listOfMedia.isEmpty()) {
				mediaList = getListOfSolrMedia(listOfMedia);

			}

			com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo priceInfoObject = null;
			if (preCalList.getPriceInfo() != null) {
				priceInfoObject = getPriceForSolr(preCalList.getPriceInfo());
			}

			deviceListObject.setGroupType(preCalList.getGroupType());
			deviceListObject.setPriceInfo(priceInfoObject);
			deviceListObject.setDeviceId(preCalList.getDeviceId());
			deviceListObject.setRating(preCalList.getRating());
			deviceListObject.setLeadPlanId(preCalList.getLeadPlanId());
			deviceListObject.setNonUpgradeLeadPlanId(preCalList.getNonUpgradeLeadPlanId());
			deviceListObject.setUpgradeLeadPlanId(preCalList.getUpgradeLeadPlanId());
			deviceListObject.setProductGroupName(preCalList.getProductGroupName());
			if (preCalList.getPaymProductGroupId() != null
					&& StringUtils.equalsIgnoreCase(preCalList.getGroupType(), STRING_DEVICE_PAYM)) {
				deviceListObject.setPaymProductGroupId(preCalList.getPaymProductGroupId());
			}
			if (preCalList.getPaygProductGroupId() != null
					&& StringUtils.equalsIgnoreCase(preCalList.getGroupType(), STRING_DEVICE_PAYG)) {
				deviceListObject.setPaygProductGroupId(preCalList.getPaygProductGroupId());
			}
			deviceListObject.setMedia(mediaList);
			deviceListObject.setNonUpgradeLeadDeviceId(preCalList.getNonUpgradeLeadDeviceId());
			deviceListObject.setUpgradeLeadDeviceId(preCalList.getUpgradeLeadDeviceId());
			deviceListObject.setIsLeadMember(preCalList.getIsLeadMember());
			if (preCalList.getMinimumCost() != null) {
				deviceListObject.setMinimumCost(Float.valueOf(preCalList.getMinimumCost()));
			}
			deviceListObjectList.add(deviceListObject);
		});
		return deviceListObjectList;
	}

	/**
	 * 
	 * @param listOfMedia
	 * @return List<Media>
	 */
	public List<com.vf.uk.dal.device.model.merchandisingpromotion.Media> getListOfSolrMedia(List<Media> listOfMedia) {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.Media> mediaList = new ArrayList<>();
		listOfMedia.forEach(media -> {
			com.vf.uk.dal.device.model.merchandisingpromotion.Media mediaObject = new com.vf.uk.dal.device.model.merchandisingpromotion.Media();

			mediaObject.setId(media.getId());

			mediaObject.setValue(media.getValue());

			mediaObject.setType(media.getType());

			mediaObject.setDescription(media.getDescription());
			mediaObject.setDiscountId(media.getDiscountId());
			mediaObject.setPromoCategory(media.getPromoCategory());
			mediaObject.setOfferCode(media.getOfferCode());
			mediaList.add(mediaObject);
		});
		return mediaList;
	}

	/**
	 * 
	 * @param priceInfo
	 * @return
	 */
	public com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo getPriceForSolr(
			com.vf.uk.dal.device.model.solr.PriceInfo priceInfo) {
		com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo priceInfoObject = new com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo();

		List<com.vf.uk.dal.device.model.solr.OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = priceInfo
				.getOfferAppliedPrices();
		List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> listOfOfferAppliedPriceDetailsForSolr = null;
		if (listOfOfferAppliedPriceDetails != null && !listOfOfferAppliedPriceDetails.isEmpty()) {
			listOfOfferAppliedPriceDetailsForSolr = getListOfOfferAppliedPriceDetails(listOfOfferAppliedPriceDetails);
		}

		com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice bundlePrice = setBundlePrice(priceInfo);
		com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice();

		com.vf.uk.dal.device.model.solr.HardwarePrice hardwarePrice1 = priceInfo.getHardwarePrice();
		if (hardwarePrice1 != null) {
			com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice();

			OneOffPrice oneOffPrice1 = hardwarePrice1.getOneOffPrice();

			if (oneOffPrice1 != null && oneOffPrice1.getGross() != null) {
				oneOffPrice.setGross(Float.valueOf(oneOffPrice1.getGross()));

				oneOffPrice.setNet(Float.valueOf(oneOffPrice1.getNet()));

				oneOffPrice.setVat(Float.valueOf(oneOffPrice1.getVat()));
			}
			hardwarePrice.setOneOffPrice(oneOffPrice);

			com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice();

			OneOffDiscountPrice OneOffDiscountPrice1 = hardwarePrice1.getOneOffDiscountPrice();

			if (OneOffDiscountPrice1 != null && OneOffDiscountPrice1.getGross() != null) {
				oneOffDiscountPrice.setGross(Float.valueOf(OneOffDiscountPrice1.getGross()));

				oneOffDiscountPrice.setNet(Float.valueOf(OneOffDiscountPrice1.getNet()));

				oneOffDiscountPrice.setVat(Float.valueOf(OneOffDiscountPrice1.getVat()));
			}

			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);

			List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> deviceFinancingOption = hardwarePrice1
					.getFinancingOptions();
			List<com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption> financeOptions = null;
			if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
				financeOptions = new ArrayList<>();
				for (com.vf.uk.dal.device.model.solr.DeviceFinancingOption financsOption : deviceFinancingOption) {
					com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption finance = new com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption();
					finance.setApr(financsOption.getApr());
					finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
					finance.setFinanceProvider(financsOption.getFinanceProvider());
					finance.setFinanceTerm(financsOption.getFinanceTerm());
					com.vf.uk.dal.device.model.solr.Price monthly = financsOption.getMonthlyPrice();
					com.vf.uk.dal.device.model.merchandisingpromotion.Price deviceMonthlyPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.Price();
					deviceMonthlyPrice.setGross(monthly.getGross());
					deviceMonthlyPrice.setNet(monthly.getNet());
					deviceMonthlyPrice.setVat(monthly.getVat());
					finance.setMonthlyPrice(deviceMonthlyPrice);
					com.vf.uk.dal.device.model.solr.Price totalInterest = financsOption.getTotalPriceWithInterest();
					com.vf.uk.dal.device.model.merchandisingpromotion.Price totalPriceWithInterest = new com.vf.uk.dal.device.model.merchandisingpromotion.Price();
					totalPriceWithInterest.setGross(totalInterest.getGross());
					totalPriceWithInterest.setNet(totalInterest.getNet());
					totalPriceWithInterest.setVat(totalInterest.getVat());
					finance.setTotalPriceWithInterest(totalPriceWithInterest);
					financeOptions.add(finance);
				}
			}
			hardwarePrice.setFinancingOptions(financeOptions);
			hardwarePrice.setHardwareId(hardwarePrice1.getHardwareId());
		}

		priceInfoObject.setBundlePrice(bundlePrice);
		priceInfoObject.setHardwarePrice(hardwarePrice);
		priceInfoObject.setOfferAppliedPrices(listOfOfferAppliedPriceDetailsForSolr);
		return priceInfoObject;
	}

	private com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice setBundlePrice(
			com.vf.uk.dal.device.model.solr.PriceInfo priceInfo) {
		com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice1 = priceInfo.getBundlePrice();
		com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice bundlePrice = new com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice();
		if (bundlePrice1 != null) {
			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice();

			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice();

			setMonthlyPriceForOfferAppliedPrice(bundlePrice1, monthlyPrice);
			bundlePrice.setMonthlyPrice(monthlyPrice);

			MonthlyDiscountPrice mnthlydiscPrice = bundlePrice1.getMonthlyDiscountPrice();
			if (mnthlydiscPrice != null && mnthlydiscPrice.getGross() != null) {
				monthlyDiscountPrice.setGross(Float.valueOf(mnthlydiscPrice.getGross()));

				monthlyDiscountPrice.setNet(Float.valueOf(mnthlydiscPrice.getNet()));

				monthlyDiscountPrice.setVat(Float.valueOf(mnthlydiscPrice.getVat()));
			}
			bundlePrice.setBundleId(bundlePrice1.getBundleId());
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		}
		return bundlePrice;
	}

	/**
	 * 
	 * @param offerAppliedPriceList
	 * @return List<OfferAppliedPriceDetails>
	 */
	public List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> getListOfOfferAppliedPriceDetails(
			List<OfferAppliedPriceDetails> offerAppliedPriceList) {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> offerAppliedListForSolr = new ArrayList<>();
		for (OfferAppliedPriceDetails offerAppliedPrice : offerAppliedPriceList) {
			com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails offerAppliedPriceDetails = new com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails();
			com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice1 = offerAppliedPrice.getBundlePrice();
			com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice bundlePrice = new com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice();

			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice();

			setMonthlyPriceForOfferAppliedPrice(bundlePrice1, monthlyPrice);
			bundlePrice.setMonthlyPrice(monthlyPrice);
			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = setMonthlyDiscountPriceForAppliedPrice(
					bundlePrice1, bundlePrice);
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);

			com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.model.merchandisingpromotion.HardwarePrice();

			com.vf.uk.dal.device.model.solr.HardwarePrice hardwarePrice1 = offerAppliedPrice.getHardwarePrice();

			com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.OneOffPrice();

			OneOffPrice oneOffPrice1 = hardwarePrice1.getOneOffPrice();

			if (oneOffPrice1 != null && oneOffPrice1.getGross() != null) {
				oneOffPrice.setGross(Float.valueOf(oneOffPrice1.getGross()));

				oneOffPrice.setNet(Float.valueOf(oneOffPrice1.getNet()));

				oneOffPrice.setVat(Float.valueOf(oneOffPrice1.getVat()));
			}
			hardwarePrice.setOneOffPrice(oneOffPrice);

			com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.OneOffDiscountPrice();

			OneOffDiscountPrice oneOffDiscountPrice1 = hardwarePrice1.getOneOffDiscountPrice();

			if (oneOffDiscountPrice1 != null && oneOffDiscountPrice1.getGross() != null) {
				oneOffDiscountPrice.setGross(Float.valueOf(oneOffDiscountPrice1.getGross()));

				oneOffDiscountPrice.setNet(Float.valueOf(oneOffDiscountPrice1.getNet()));

				oneOffDiscountPrice.setVat(Float.valueOf(oneOffDiscountPrice1.getVat()));
			}

			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			List<com.vf.uk.dal.device.model.solr.DeviceFinancingOption> deviceFinancingOption = hardwarePrice1
					.getFinancingOptions();
			List<com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption> financeOptions = null;
			if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
				financeOptions = new ArrayList<>();
				for (com.vf.uk.dal.device.model.solr.DeviceFinancingOption financsOption : deviceFinancingOption) {
					com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption finance = new com.vf.uk.dal.device.model.merchandisingpromotion.DeviceFinancingOption();
					finance.setApr(financsOption.getApr());
					finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
					finance.setFinanceProvider(financsOption.getFinanceProvider());
					finance.setFinanceTerm(financsOption.getFinanceTerm());
					com.vf.uk.dal.device.model.solr.Price monthly = financsOption.getMonthlyPrice();
					com.vf.uk.dal.device.model.merchandisingpromotion.Price deviceMonthlyPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.Price();
					deviceMonthlyPrice.setGross(monthly.getGross());
					deviceMonthlyPrice.setNet(monthly.getNet());
					deviceMonthlyPrice.setVat(monthly.getVat());
					finance.setMonthlyPrice(deviceMonthlyPrice);
					com.vf.uk.dal.device.model.solr.Price totalInterest = financsOption.getTotalPriceWithInterest();
					com.vf.uk.dal.device.model.merchandisingpromotion.Price totalPriceWithInterest = new com.vf.uk.dal.device.model.merchandisingpromotion.Price();
					totalPriceWithInterest.setGross(totalInterest.getGross());
					totalPriceWithInterest.setNet(totalInterest.getNet());
					totalPriceWithInterest.setVat(totalInterest.getVat());
					finance.setTotalPriceWithInterest(totalPriceWithInterest);
					financeOptions.add(finance);
				}
			}
			hardwarePrice.setFinancingOptions(financeOptions);
			hardwarePrice.setHardwareId(hardwarePrice1.getHardwareId());
			offerAppliedPriceDetails.setBundlePrice(bundlePrice);
			offerAppliedPriceDetails.setHardwarePrice(hardwarePrice);
			offerAppliedPriceDetails.setDeviceId(offerAppliedPrice.getDeviceId());
			offerAppliedPriceDetails.setOfferCode(offerAppliedPrice.getOfferCode());
			offerAppliedPriceDetails.setJourneyType(offerAppliedPrice.getJourneyType());
			offerAppliedListForSolr.add(offerAppliedPriceDetails);
		}
		return offerAppliedListForSolr;
	}

	private com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice setMonthlyDiscountPriceForAppliedPrice(
			com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice1,
			com.vf.uk.dal.device.model.merchandisingpromotion.BundlePrice bundlePrice) {
		com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = new com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyDiscountPrice();

		MonthlyDiscountPrice mnthlydiscPrice = bundlePrice1.getMonthlyDiscountPrice();
		if (mnthlydiscPrice != null && mnthlydiscPrice.getGross() != null) {
			monthlyDiscountPrice.setGross(Float.valueOf(mnthlydiscPrice.getGross()));

			monthlyDiscountPrice.setNet(Float.valueOf(mnthlydiscPrice.getNet()));

			monthlyDiscountPrice.setVat(Float.valueOf(mnthlydiscPrice.getVat()));

			bundlePrice.setBundleId(bundlePrice1.getBundleId());
		}
		return monthlyDiscountPrice;
	}

	private void setMonthlyPriceForOfferAppliedPrice(com.vf.uk.dal.device.model.solr.BundlePrice bundlePrice1,
			com.vf.uk.dal.device.model.merchandisingpromotion.MonthlyPrice monthlyPrice) {
		MonthlyPrice mnthlyPrice = bundlePrice1.getMonthlyPrice();

		if (mnthlyPrice != null && mnthlyPrice.getGross() != null) {
			monthlyPrice.setGross(Float.valueOf(mnthlyPrice.getGross()));

			monthlyPrice.setNet(Float.valueOf(mnthlyPrice.getNet()));

			monthlyPrice.setVat(Float.valueOf(mnthlyPrice.getVat()));
		}
	}

}
