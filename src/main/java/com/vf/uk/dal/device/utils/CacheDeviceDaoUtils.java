package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.DeviceFinancingOption;
import com.vf.uk.dal.device.entity.HardwarePrice;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.Media;
import com.vf.uk.dal.utility.solr.entity.MonthlyDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.MonthlyPrice;
import com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails;
import com.vf.uk.dal.utility.solr.entity.OneOffDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.OneOffPrice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheDeviceDaoUtils {

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
	public static Map<String, List<PriceForBundleAndHardware>> getILSPriceWithoutOfferCode(
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
	public static DevicePreCalculatedData convertBundleHeaderForDeviceToProductGroupForDeviceListing(String deviceId,
			String leadPlanId, String groupname, String groupId,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, Map<String, String> leadMemberMap,
			Map<String, List<PriceForBundleAndHardware>> listOfPriceForBundleAndHardwareWithOfferCode,
			Map<String, String> leadMemberMapForUpgrade, String upgradeLeadPlanId, String groupType) {
		DevicePreCalculatedData productGroupForDeviceListing = null;
		productGroupForDeviceListing = new DevicePreCalculatedData();
		productGroupForDeviceListing.setDeviceId(deviceId);
		productGroupForDeviceListing.setProductGroupName(groupname);
		productGroupForDeviceListing.setProductGroupId(groupId);
		productGroupForDeviceListing.setUpgradeLeadPlanId(upgradeLeadPlanId);
		// isLeadMember not removed need to revisit
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
		if ((StringUtils.isNotBlank(leadPlanId)
				&& StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYM))
				|| StringUtils.equalsIgnoreCase(groupType, STRING_DEVICE_PAYG)) {
			PriceForBundleAndHardware priceForBundleAndHardware1 = listOfPriceForBundleAndHardware.get(0);
			productGroupForDeviceListing.setNonUpgradeLeadPlanId(leadPlanId);
			productGroupForDeviceListing.setLeadPlanId(leadPlanId);
			Map<String, Object> priceMediaMap = getPriceInfoForSolr(priceForBundleAndHardware1,
					listOfPriceForBundleAndHardwareWithOfferCode);
			com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfo = (com.vf.uk.dal.utility.solr.entity.PriceInfo) priceMediaMap
					.get("price");
			productGroupForDeviceListing.setPriceInfo(priceInfo);
			@SuppressWarnings("unchecked")
			List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = (List<com.vf.uk.dal.utility.solr.entity.Media>) priceMediaMap
					.get("media");
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
	public static Map<String, Object> getPriceInfoForSolr(PriceForBundleAndHardware priceForBundleAndHardware,
			Map<String, List<PriceForBundleAndHardware>> listOfPriceForBundleAndHardwareWithOfferCode) {

		Map<String, Object> result = new ConcurrentHashMap<>();
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = new ArrayList<>();
		BundlePrice bundlePrice = priceForBundleAndHardware.getBundlePrice();
		Price monthlyPrice = null;
		Price monthlyDiscountPrice = null;
		String bundleId = DATA_NOT_FOUND;
		if (bundlePrice != null && bundlePrice.getMonthlyPrice() != null
				&& bundlePrice.getMonthlyPrice().getGross() != null) {
			bundleId = bundlePrice.getBundleId();
			if (bundlePrice.getMerchandisingPromotions() != null) {
				com.vf.uk.dal.utility.solr.entity.Media mediaLink = new com.vf.uk.dal.utility.solr.entity.Media();
				mediaLink.setId(
						bundlePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
				String type = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
						+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
						+ bundlePrice.getMerchandisingPromotions().getTag();
				mediaLink.setType(type);
				mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
				mediaLink.setDescription(DATA_NOT_FOUND);
				mediaLink.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLink.setOfferCode(DATA_NOT_FOUND);
				if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
					mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
				} else {
					mediaLink.setDiscountId(DATA_NOT_FOUND);
				}

				String description = null;
				if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
					description = bundlePrice.getMerchandisingPromotions().getDescription();
					com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription = new com.vf.uk.dal.utility.solr.entity.Media();
					mediaLinkForDescription.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
							+ STRING_OFFERS_DESCRIPTION);
					String type1 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
							+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
							+ bundlePrice.getMerchandisingPromotions().getTag();
					mediaLinkForDescription.setType(type1);
					mediaLinkForDescription.setValue(description);
					mediaLinkForDescription.setDescription(description);
					mediaLinkForDescription.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
					mediaLinkForDescription.setOfferCode(DATA_NOT_FOUND);
					if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
						mediaLinkForDescription.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
					} else {
						mediaLinkForDescription.setDiscountId(DATA_NOT_FOUND);
					}
					listOfMedia.add(mediaLinkForDescription);
				}
				listOfMedia.add(mediaLink);
				if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
					// PriceEstablishedLabel
					com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.utility.solr.entity.Media();
					mediaLinkForPriceEstablished.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
							+ STRING_PRICE_ESTABLISHED_LABEL);
					String type3 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
							+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
							+ bundlePrice.getMerchandisingPromotions().getTag();
					mediaLinkForPriceEstablished.setType(type3);
					mediaLinkForPriceEstablished
							.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
					mediaLinkForPriceEstablished.setDescription(DATA_NOT_FOUND);
					if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
						mediaLinkForPriceEstablished
								.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
					} else {
						mediaLinkForPriceEstablished.setDiscountId(DATA_NOT_FOUND);
					}
					mediaLinkForPriceEstablished.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
					mediaLinkForPriceEstablished.setOfferCode(DATA_NOT_FOUND);
					listOfMedia.add(mediaLinkForPriceEstablished);
				}
				if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPromotionMedia())) {
					// PromotionMedia
					com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.utility.solr.entity.Media();
					mediaLinkForPromotionMedia.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
							+ STRING_PRICE_PROMOTION_MEDIA);
					String type4 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&"
							+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
							+ bundlePrice.getMerchandisingPromotions().getTag();
					mediaLinkForPromotionMedia.setType(type4);
					mediaLinkForPromotionMedia.setValue(bundlePrice.getMerchandisingPromotions().getPromotionMedia());
					mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
					if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
						mediaLinkForPromotionMedia
								.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
					} else {
						mediaLinkForPromotionMedia.setDiscountId(DATA_NOT_FOUND);
					}
					mediaLinkForPromotionMedia.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
					mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
					listOfMedia.add(mediaLinkForPromotionMedia);
				}

			}

			monthlyPrice = bundlePrice.getMonthlyPrice();
			monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
		}
		HardwarePrice hardwarePrice = priceForBundleAndHardware.getHardwarePrice();
		String hardwareId = hardwarePrice.getHardwareId();

		if (hardwarePrice.getMerchandisingPromotions() != null) {
			com.vf.uk.dal.utility.solr.entity.Media mediaLink1 = new com.vf.uk.dal.utility.solr.entity.Media();
			mediaLink1.setId(
					hardwarePrice.getMerchandisingPromotions().getMpType() + "." + STRING_OFFERS_LABEL);
			String type2 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
					+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLink1.setType(type2);
			mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
			mediaLink1.setDescription(DATA_NOT_FOUND);
			mediaLink1.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLink1.setOfferCode(DATA_NOT_FOUND);
			if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
				mediaLink1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
			} else {
				mediaLink1.setDiscountId(DATA_NOT_FOUND);
			}
			String description = null;
			if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
				description = hardwarePrice.getMerchandisingPromotions().getDescription();
				com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription1 = new com.vf.uk.dal.utility.solr.entity.Media();
				mediaLinkForDescription1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
						+ STRING_OFFERS_DESCRIPTION);
				String type3 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
						+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
						+ hardwarePrice.getMerchandisingPromotions().getTag();
				mediaLinkForDescription1.setType(type3);
				mediaLinkForDescription1.setValue(description);
				mediaLinkForDescription1.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLinkForDescription1.setOfferCode(DATA_NOT_FOUND);
				mediaLinkForDescription1.setDescription(hardwarePrice.getMerchandisingPromotions().getDescription());
				if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
					mediaLinkForDescription1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
				} else {
					mediaLinkForDescription1.setDiscountId(DATA_NOT_FOUND);
				}
				listOfMedia.add(mediaLinkForDescription1);
			}
			listOfMedia.add(mediaLink1);
			if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
				// PriceEstablishedLabel
				com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceLabel = new com.vf.uk.dal.utility.solr.entity.Media();
				mediaLinkForPriceLabel.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
						+ STRING_PRICE_ESTABLISHED_LABEL);
				String type3 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
						+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
						+ hardwarePrice.getMerchandisingPromotions().getTag();
				mediaLinkForPriceLabel.setType(type3);
				mediaLinkForPriceLabel.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
				mediaLinkForPriceLabel.setDescription(DATA_NOT_FOUND);
				if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
					mediaLinkForPriceLabel.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
				} else {
					mediaLinkForPriceLabel.setDiscountId(DATA_NOT_FOUND);
				}
				mediaLinkForPriceLabel.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLinkForPriceLabel.setOfferCode(DATA_NOT_FOUND);
				listOfMedia.add(mediaLinkForPriceLabel);
			}
			if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPromotionMedia())) {
				// PromotionMedia
				com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.utility.solr.entity.Media();
				mediaLinkForPromotionMedia.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
						+ STRING_PRICE_PROMOTION_MEDIA);
				String type4 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&"
						+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
						+ hardwarePrice.getMerchandisingPromotions().getTag();
				mediaLinkForPromotionMedia.setType(type4);
				mediaLinkForPromotionMedia.setValue(hardwarePrice.getMerchandisingPromotions().getPromotionMedia());
				mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
				if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
					mediaLinkForPromotionMedia
							.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
				} else {
					mediaLinkForPromotionMedia.setDiscountId(DATA_NOT_FOUND);
				}
				mediaLinkForPromotionMedia.setPromoCategory(PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
				listOfMedia.add(mediaLinkForPromotionMedia);
			}

		}
		Price oneoffPrice = hardwarePrice.getOneOffPrice();
		Price oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
		List<DeviceFinancingOption> deviceFinancingOption = hardwarePrice.getFinancingOptions();
		List<com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption> financeOptions = null;
		if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
			financeOptions = new ArrayList<>();
			for (DeviceFinancingOption financsOption : deviceFinancingOption) {
				com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption finance = new com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption();
				finance.setApr(financsOption.getApr());
				finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
				finance.setFinanceProvider(financsOption.getFinanceProvider());
				finance.setFinanceTerm(financsOption.getFinanceTerm());
				com.vf.uk.dal.device.entity.Price monthly = financsOption.getMonthlyPrice();
				com.vf.uk.dal.utility.solr.entity.Price deviceMonthlyPrice = new com.vf.uk.dal.utility.solr.entity.Price();
				deviceMonthlyPrice.setGross(monthly.getGross());
				deviceMonthlyPrice.setNet(monthly.getNet());
				deviceMonthlyPrice.setVat(monthly.getVat());
				finance.setMonthlyPrice(deviceMonthlyPrice);
				com.vf.uk.dal.device.entity.Price totalInterest = financsOption.getTotalPriceWithInterest();
				com.vf.uk.dal.utility.solr.entity.Price totalPriceWithInterest = new com.vf.uk.dal.utility.solr.entity.Price();
				totalPriceWithInterest.setGross(totalInterest.getGross());
				totalPriceWithInterest.setNet(totalInterest.getNet());
				totalPriceWithInterest.setVat(totalInterest.getVat());
				finance.setTotalPriceWithInterest(totalPriceWithInterest);
				financeOptions.add(finance);
			}
		}
		MonthlyPrice mnthlyPrice = null;
		if (monthlyPrice != null) {
			mnthlyPrice = new MonthlyPrice();
			mnthlyPrice.setGross(monthlyPrice.getGross());
			mnthlyPrice.setNet(monthlyPrice.getNet());
			mnthlyPrice.setVat(monthlyPrice.getVat());
		}
		MonthlyDiscountPrice mnthlyDiscPrice = null;
		if (monthlyDiscountPrice != null) {
			mnthlyDiscPrice = new MonthlyDiscountPrice();
			mnthlyDiscPrice.setGross(monthlyDiscountPrice.getGross());
			mnthlyDiscPrice.setNet(monthlyDiscountPrice.getNet());
			mnthlyDiscPrice.setVat(monthlyDiscountPrice.getVat());
		}
		com.vf.uk.dal.utility.solr.entity.BundlePrice bp = new com.vf.uk.dal.utility.solr.entity.BundlePrice();
		bp.setBundleId(DATA_NOT_FOUND.equalsIgnoreCase(bundleId) ? null : bundleId);
		bp.setMonthlyPrice(mnthlyPrice);
		bp.setMonthlyDiscountPrice(mnthlyDiscPrice);
		OneOffPrice onffPrice = null;
		if (oneoffPrice != null) {
			onffPrice = new OneOffPrice();
			onffPrice.setGross(oneoffPrice.getGross());
			onffPrice.setNet(oneoffPrice.getNet());
			onffPrice.setVat(oneoffPrice.getVat());
			log.info( hardwareId+"One Off Price Gross"+onffPrice.getGross());
		}
		OneOffDiscountPrice onffDiscPrice = null;
		if (oneOffDisPrice != null) {
			onffDiscPrice = new OneOffDiscountPrice();
			onffDiscPrice.setGross(oneOffDisPrice.getGross());
			onffDiscPrice.setNet(oneOffDisPrice.getNet());
			onffDiscPrice.setVat(oneOffDisPrice.getVat());
			log.info( hardwareId+"One Off Disc Price Gross"+oneOffDisPrice.getGross());
		}
		com.vf.uk.dal.utility.solr.entity.HardwarePrice hw = new com.vf.uk.dal.utility.solr.entity.HardwarePrice();
		hw.setHardwareId(hardwareId);
		hw.setOneOffPrice(onffPrice);
		hw.setOneOffDiscountPrice(onffDiscPrice);
		hw.setFinancingOptions(financeOptions);
		com.vf.uk.dal.utility.solr.entity.PriceInfo priceinfo = new com.vf.uk.dal.utility.solr.entity.PriceInfo();
		priceinfo.setBundlePrice(bp);
		priceinfo.setHardwarePrice(hw);

		result.put("price", priceinfo);
		result.put("media", listOfMedia);
		return result;

	}

	/**
	 * @author manoj.bera
	 * @param deviceId
	 * @param listOfPriceForBundleAndHardwareMap
	 * @return ListOfOfferAppliedPrice
	 */
	public static Map<String, Object> getListOfOfferAppliedPrice(String deviceId,
			Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsPriceForBundleAndHardwareMap) {

		Map<String, Object> result = new HashMap<>();
		List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = new ArrayList<>();
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = new ArrayList<>();
		for (Entry<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsJourneyEntry : ilsPriceForBundleAndHardwareMap
				.entrySet()) {
			String journeyType = ilsJourneyEntry.getKey();
			Map<String, Map<String, List<PriceForBundleAndHardware>>> listOfPriceForBundleAndHardwareMap = ilsJourneyEntry
					.getValue();
			for (Entry<String, Map<String, List<PriceForBundleAndHardware>>> entry : listOfPriceForBundleAndHardwareMap
					.entrySet()) {

				Map<String, List<PriceForBundleAndHardware>> offeredPriceMap = entry.getValue();

				String offerCode = entry.getKey();
				List<PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = null;
				if (offeredPriceMap.containsKey(deviceId)) {
					priceForBundleAndHardwareWithOfferCodeList = offeredPriceMap.get(deviceId);
				}
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
							if (bundlePrice.getMerchandisingPromotions() != null) {
								com.vf.uk.dal.utility.solr.entity.Media mediaLink = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
										+ STRING_OFFERS_LABEL);
								String type4 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
										+ bundlePrice.getMerchandisingPromotions().getTag();
								mediaLink.setType(type4);
								mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
								mediaLink.setDescription(DATA_NOT_FOUND);
								mediaLink.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
								mediaLink.setOfferCode(offerCode);
								if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLink.setDiscountId(DATA_NOT_FOUND);
								}
								String description = null;
								if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
									description = bundlePrice.getMerchandisingPromotions().getDescription();
									com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription = new com.vf.uk.dal.utility.solr.entity.Media();
									mediaLinkForDescription.setId(bundlePrice.getMerchandisingPromotions().getMpType()
											+ "." + STRING_OFFERS_DESCRIPTION);
									String type5 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
											+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
											+ bundlePrice.getMerchandisingPromotions().getTag();
									mediaLinkForDescription.setType(type5);
									mediaLinkForDescription.setValue(description);
									mediaLinkForDescription.setDescription(description);
									mediaLinkForDescription.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
									mediaLinkForDescription.setOfferCode(offerCode);
									if (StringUtils
											.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
										mediaLinkForDescription.setDiscountId(
												bundlePrice.getMerchandisingPromotions().getDiscountId());
									} else {
										mediaLinkForDescription.setDiscountId(DATA_NOT_FOUND);
									}
									listOfMedia.add(mediaLinkForDescription);
								}
								listOfMedia.add(mediaLink);
								if (StringUtils.isNotBlank(
										bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
									// PriceEstablished Label
									com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablishedLabel = new com.vf.uk.dal.utility.solr.entity.Media();
									mediaLinkForPriceEstablishedLabel
											.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
													+ STRING_PRICE_ESTABLISHED_LABEL);
									String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
											+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
											+ bundlePrice.getMerchandisingPromotions().getTag();
									mediaLinkForPriceEstablishedLabel.setType(type6);
									mediaLinkForPriceEstablishedLabel.setValue(
											bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
									mediaLinkForPriceEstablishedLabel.setDescription(DATA_NOT_FOUND);
									if (StringUtils
											.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
										mediaLinkForPriceEstablishedLabel.setDiscountId(
												bundlePrice.getMerchandisingPromotions().getDiscountId());
									} else {
										mediaLinkForPriceEstablishedLabel.setDiscountId(DATA_NOT_FOUND);
									}
									mediaLinkForPriceEstablishedLabel
											.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
									mediaLinkForPriceEstablishedLabel.setOfferCode(offerCode);
									listOfMedia.add(mediaLinkForPriceEstablishedLabel);
								}
								if (StringUtils
										.isNotBlank(bundlePrice.getMerchandisingPromotions().getPromotionMedia())) {
									// PromotionMedia Label
									com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.utility.solr.entity.Media();
									mediaLinkForPromotionMedia
											.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
													+ STRING_PRICE_PROMOTION_MEDIA);
									String type7 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&"
											+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
											+ bundlePrice.getMerchandisingPromotions().getTag();
									mediaLinkForPromotionMedia.setType(type7);
									mediaLinkForPromotionMedia
											.setValue(bundlePrice.getMerchandisingPromotions().getPromotionMedia());
									mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
									if (StringUtils
											.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
										mediaLinkForPromotionMedia.setDiscountId(
												bundlePrice.getMerchandisingPromotions().getDiscountId());
									} else {
										mediaLinkForPromotionMedia.setDiscountId(DATA_NOT_FOUND);
									}
									mediaLinkForPromotionMedia
											.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
									mediaLinkForPromotionMedia.setOfferCode(offerCode);
									listOfMedia.add(mediaLinkForPromotionMedia);
								}
							}
						}
						HardwarePrice hardwarePrice = priceForBundleAndHardwareWithOfferCode.getHardwarePrice();
						List<com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption> financeOptions = null;
						if (hardwarePrice != null) {
							hardwareId = hardwarePrice.getHardwareId();
							oneoffPrice = hardwarePrice.getOneOffPrice();
							oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
							List<DeviceFinancingOption> deviceFinancingOption = hardwarePrice.getFinancingOptions();
							if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
								financeOptions = new ArrayList<>();
								for (DeviceFinancingOption financsOption : deviceFinancingOption) {
									com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption finance = new com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption();
									finance.setApr(financsOption.getApr());
									finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
									finance.setFinanceProvider(financsOption.getFinanceProvider());
									finance.setFinanceTerm(financsOption.getFinanceTerm());
									com.vf.uk.dal.device.entity.Price monthly = financsOption.getMonthlyPrice();
									com.vf.uk.dal.utility.solr.entity.Price deviceMonthlyPrice = new com.vf.uk.dal.utility.solr.entity.Price();
									deviceMonthlyPrice.setGross(monthly.getGross());
									deviceMonthlyPrice.setNet(monthly.getNet());
									deviceMonthlyPrice.setVat(monthly.getVat());
									finance.setMonthlyPrice(deviceMonthlyPrice);
									com.vf.uk.dal.device.entity.Price totalInterest = financsOption
											.getTotalPriceWithInterest();
									com.vf.uk.dal.utility.solr.entity.Price totalPriceWithInterest = new com.vf.uk.dal.utility.solr.entity.Price();
									totalPriceWithInterest.setGross(totalInterest.getGross());
									totalPriceWithInterest.setNet(totalInterest.getNet());
									totalPriceWithInterest.setVat(totalInterest.getVat());
									finance.setTotalPriceWithInterest(totalPriceWithInterest);
									financeOptions.add(finance);
								}
							}
							if (hardwarePrice.getMerchandisingPromotions() != null) {
								com.vf.uk.dal.utility.solr.entity.Media mediaLink1 = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLink1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
										+ STRING_OFFERS_LABEL);
								String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
										+ hardwarePrice.getMerchandisingPromotions().getTag();
								mediaLink1.setType(type6);
								mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
								mediaLink1.setDescription(DATA_NOT_FOUND);
								mediaLink1.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
								mediaLink1.setOfferCode(offerCode);
								if (StringUtils
										.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLink1
											.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLink1.setDiscountId(DATA_NOT_FOUND);
								}

								String description = null;
								if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
									description = hardwarePrice.getMerchandisingPromotions().getDescription();
									com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription1 = new com.vf.uk.dal.utility.solr.entity.Media();
									mediaLinkForDescription1
											.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
													+ STRING_OFFERS_DESCRIPTION);
									String type7 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
											+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
											+ hardwarePrice.getMerchandisingPromotions().getTag();
									mediaLinkForDescription1.setType(type7);
									mediaLinkForDescription1.setValue(description);
									mediaLinkForDescription1.setDescription(description);
									mediaLinkForDescription1
											.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
									mediaLinkForDescription1.setOfferCode(offerCode);
									if (StringUtils
											.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
										mediaLinkForDescription1.setDiscountId(
												hardwarePrice.getMerchandisingPromotions().getDiscountId());
									} else {
										mediaLinkForDescription1.setDiscountId(DATA_NOT_FOUND);
									}
									listOfMedia.add(mediaLinkForDescription1);
								}
								listOfMedia.add(mediaLink1);
								if (StringUtils.isNotBlank(
										hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
									// PriceEstablished Label
									com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.utility.solr.entity.Media();
									mediaLinkForPriceEstablished
											.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
													+ STRING_PRICE_ESTABLISHED_LABEL);
									String type8 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
											+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
											+ hardwarePrice.getMerchandisingPromotions().getTag();
									mediaLinkForPriceEstablished.setType(type8);
									mediaLinkForPriceEstablished.setValue(
											hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
									mediaLinkForPriceEstablished.setDescription(DATA_NOT_FOUND);
									if (StringUtils
											.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
										mediaLinkForPriceEstablished.setDiscountId(
												hardwarePrice.getMerchandisingPromotions().getDiscountId());
									} else {
										mediaLinkForPriceEstablished.setDiscountId(DATA_NOT_FOUND);
									}
									mediaLinkForPriceEstablished
											.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
									mediaLinkForPriceEstablished.setOfferCode(offerCode);
									listOfMedia.add(mediaLinkForPriceEstablished);
								}
								if (StringUtils
										.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPromotionMedia())) {
									// PromotionMedia Label
									com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.utility.solr.entity.Media();
									mediaLinkForPromotionMedia
											.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
													+ STRING_PRICE_PROMOTION_MEDIA);
									String type9 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&"
											+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
											+ hardwarePrice.getMerchandisingPromotions().getTag();
									mediaLinkForPromotionMedia.setType(type9);
									mediaLinkForPromotionMedia
											.setValue(hardwarePrice.getMerchandisingPromotions().getPromotionMedia());
									mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
									if (StringUtils
											.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
										mediaLinkForPromotionMedia.setDiscountId(
												hardwarePrice.getMerchandisingPromotions().getDiscountId());
									} else {
										mediaLinkForPromotionMedia.setDiscountId(DATA_NOT_FOUND);
									}
									mediaLinkForPromotionMedia
											.setPromoCategory(PROMO_CATEGORY_PRICING_DISCOUNT);
									mediaLinkForPromotionMedia.setOfferCode(offerCode);
									listOfMedia.add(mediaLinkForPromotionMedia);
								}
							}
						}
						MonthlyPrice mnthlyPrice = null;
						if (monthlyPrice != null) {
							mnthlyPrice = new MonthlyPrice();
							mnthlyPrice.setGross(monthlyPrice.getGross());
							mnthlyPrice.setNet(monthlyPrice.getNet());
							mnthlyPrice.setVat(monthlyPrice.getVat());
						}
						MonthlyDiscountPrice mnthlyDiscPrice = null;
						if (monthlyDiscountPrice != null) {
							mnthlyDiscPrice = new MonthlyDiscountPrice();
							mnthlyDiscPrice.setGross(monthlyDiscountPrice.getGross());
							mnthlyDiscPrice.setNet(monthlyDiscountPrice.getNet());
							mnthlyDiscPrice.setVat(monthlyDiscountPrice.getVat());
						}
						com.vf.uk.dal.utility.solr.entity.BundlePrice bp = new com.vf.uk.dal.utility.solr.entity.BundlePrice();
						bp.setBundleId(bundleId);
						bp.setMonthlyPrice(mnthlyPrice);
						bp.setMonthlyDiscountPrice(mnthlyDiscPrice);
						OneOffPrice onffPrice = null;
						if (oneoffPrice != null) {
							onffPrice = new OneOffPrice();
							onffPrice.setGross(oneoffPrice.getGross());
							onffPrice.setNet(oneoffPrice.getNet());
							onffPrice.setVat(oneoffPrice.getVat());
						}
						OneOffDiscountPrice onffDiscPrice = null;
						if (oneOffDisPrice != null) {
							onffDiscPrice = new OneOffDiscountPrice();
							onffDiscPrice.setGross(oneOffDisPrice.getGross());
							onffDiscPrice.setNet(oneOffDisPrice.getNet());
							onffDiscPrice.setVat(oneOffDisPrice.getVat());
						}
						com.vf.uk.dal.utility.solr.entity.HardwarePrice hw = new com.vf.uk.dal.utility.solr.entity.HardwarePrice();
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
		result.put("offeredPrice", listOfOfferAppliedPriceDetails);
		result.put("media", listOfMedia);
		return result;

	}

	/**
	 * @author krishna.reddy Sprint-6.6
	 * @param deviceId
	 * @param ilsPriceForBundleAndHardwareMap
	 * @return ListOfIlsPriceWithoutOfferCode
	 */
	public static Map<String, Object> getListOfIlsPriceWithoutOfferCode(String deviceId,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap) {
		Map<String, Object> result = new HashMap<>();
		List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = new ArrayList<>();
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = new ArrayList<>();

		for (Entry<String, Map<String, List<PriceForBundleAndHardware>>> entry : ilsPriceForBundleAndHardwareMap
				.entrySet()) {
			String journeyType = entry.getKey();
			String promoCatagoery = null;
			if (StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
				promoCatagoery = PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT;
			}
			if (StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_SECONDLINE)) {
				promoCatagoery = PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT;
			}
			Map<String, List<PriceForBundleAndHardware>> offeredPriceMap = entry.getValue();

			List<PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = null;
			if (offeredPriceMap.containsKey(deviceId)) {
				priceForBundleAndHardwareWithOfferCodeList = offeredPriceMap.get(deviceId);
			}
			if (priceForBundleAndHardwareWithOfferCodeList != null
					&& !priceForBundleAndHardwareWithOfferCodeList.isEmpty()) {
				for (PriceForBundleAndHardware priceForBundleAndHardwareWithOfferCode : priceForBundleAndHardwareWithOfferCodeList) {
					/*
					 * priceForBundleAndHardwareWithOfferCodeList.forEach(
					 * priceForBundleAndHardwareWithOfferCode -> {
					 */
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
							com.vf.uk.dal.utility.solr.entity.Media mediaLink = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
									+ STRING_OFFERS_LABEL);
							String type4 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
									+ bundlePrice.getMerchandisingPromotions().getTag();
							mediaLink.setType(type4);
							mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
							mediaLink.setDescription(DATA_NOT_FOUND);
							mediaLink.setPromoCategory(promoCatagoery);
							mediaLink.setOfferCode(DATA_NOT_FOUND);
							if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLink.setDiscountId(DATA_NOT_FOUND);
							}
							String description = null;
							if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
								description = bundlePrice.getMerchandisingPromotions().getDescription();
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForDescription.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
										+ STRING_OFFERS_DESCRIPTION);
								String type5 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
										+ bundlePrice.getMerchandisingPromotions().getTag();
								mediaLinkForDescription.setType(type5);
								mediaLinkForDescription.setValue(description);
								mediaLinkForDescription.setDescription(description);
								mediaLinkForDescription.setPromoCategory(promoCatagoery);
								mediaLinkForDescription.setOfferCode(DATA_NOT_FOUND);
								if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForDescription
											.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForDescription.setDiscountId(DATA_NOT_FOUND);
								}
								listOfMedia.add(mediaLinkForDescription);
							}
							listOfMedia.add(mediaLink);
							if (StringUtils
									.isNotBlank(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
								// PriceEstablished Label
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablishedLabel = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForPriceEstablishedLabel
										.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
												+ STRING_PRICE_ESTABLISHED_LABEL);
								String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
										+ bundlePrice.getMerchandisingPromotions().getTag();
								mediaLinkForPriceEstablishedLabel.setType(type6);
								mediaLinkForPriceEstablishedLabel
										.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
								mediaLinkForPriceEstablishedLabel.setDescription(DATA_NOT_FOUND);
								if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForPriceEstablishedLabel
											.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForPriceEstablishedLabel.setDiscountId(DATA_NOT_FOUND);
								}
								mediaLinkForPriceEstablishedLabel.setPromoCategory(promoCatagoery);
								mediaLinkForPriceEstablishedLabel.setOfferCode(DATA_NOT_FOUND);
								listOfMedia.add(mediaLinkForPriceEstablishedLabel);
							}
							if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getPromotionMedia())) {
								// PromotionMedia
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForPromotionMedia.setId(bundlePrice.getMerchandisingPromotions().getMpType()
										+ "." + STRING_PRICE_PROMOTION_MEDIA);
								String type7 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_BUNDLEPROMOTION + "&&"
										+ bundlePrice.getMerchandisingPromotions().getTag();
								mediaLinkForPromotionMedia.setType(type7);
								mediaLinkForPromotionMedia
										.setValue(bundlePrice.getMerchandisingPromotions().getPromotionMedia());
								mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
								if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForPromotionMedia
											.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForPromotionMedia.setDiscountId(DATA_NOT_FOUND);
								}
								mediaLinkForPromotionMedia.setPromoCategory(promoCatagoery);
								mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
								listOfMedia.add(mediaLinkForPromotionMedia);
							}
						}
					}
					HardwarePrice hardwarePrice = priceForBundleAndHardwareWithOfferCode.getHardwarePrice();
					List<com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption> financeOptions = null;
					if (hardwarePrice != null) {
						hardwareId = hardwarePrice.getHardwareId();
						oneoffPrice = hardwarePrice.getOneOffPrice();
						oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
						List<DeviceFinancingOption> deviceFinancingOption = hardwarePrice.getFinancingOptions();
						if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
							financeOptions = new ArrayList<>();
							for (DeviceFinancingOption financsOption : deviceFinancingOption) {
								com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption finance = new com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption();
								finance.setApr(financsOption.getApr());
								finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
								finance.setFinanceProvider(financsOption.getFinanceProvider());
								finance.setFinanceTerm(financsOption.getFinanceTerm());
								com.vf.uk.dal.device.entity.Price monthly = financsOption.getMonthlyPrice();
								com.vf.uk.dal.utility.solr.entity.Price deviceMonthlyPrice = new com.vf.uk.dal.utility.solr.entity.Price();
								deviceMonthlyPrice.setGross(monthly.getGross());
								deviceMonthlyPrice.setNet(monthly.getNet());
								deviceMonthlyPrice.setVat(monthly.getVat());
								finance.setMonthlyPrice(deviceMonthlyPrice);
								com.vf.uk.dal.device.entity.Price totalInterest = financsOption
										.getTotalPriceWithInterest();
								com.vf.uk.dal.utility.solr.entity.Price totalPriceWithInterest = new com.vf.uk.dal.utility.solr.entity.Price();
								totalPriceWithInterest.setGross(totalInterest.getGross());
								totalPriceWithInterest.setNet(totalInterest.getNet());
								totalPriceWithInterest.setVat(totalInterest.getVat());
								finance.setTotalPriceWithInterest(totalPriceWithInterest);
								financeOptions.add(finance);
							}
						}

						if (hardwarePrice.getMerchandisingPromotions() != null) {
							com.vf.uk.dal.utility.solr.entity.Media mediaLink1 = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLink1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
									+ STRING_OFFERS_LABEL);
							String type6 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
									+ hardwarePrice.getMerchandisingPromotions().getTag();
							mediaLink1.setType(type6);
							mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
							mediaLink1.setDescription(DATA_NOT_FOUND);
							mediaLink1.setPromoCategory(promoCatagoery);
							mediaLink1.setOfferCode(DATA_NOT_FOUND);
							if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLink1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLink1.setDiscountId(DATA_NOT_FOUND);
							}

							String description = null;
							if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
								description = hardwarePrice.getMerchandisingPromotions().getDescription();
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription1 = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForDescription1.setId(hardwarePrice.getMerchandisingPromotions().getMpType()
										+ "." + STRING_OFFERS_DESCRIPTION);
								String type7 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
										+ hardwarePrice.getMerchandisingPromotions().getTag();
								mediaLinkForDescription1.setType(type7);
								mediaLinkForDescription1.setValue(description);
								mediaLinkForDescription1.setDescription(description);
								mediaLinkForDescription1.setPromoCategory(promoCatagoery);
								mediaLinkForDescription1.setOfferCode(DATA_NOT_FOUND);
								if (StringUtils
										.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForDescription1
											.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForDescription1.setDiscountId(DATA_NOT_FOUND);
								}
								listOfMedia.add(mediaLinkForDescription1);
							}
							listOfMedia.add(mediaLink1);
							if (StringUtils.isNotBlank(
									hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel())) {
								// PriceEstablished Label
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForPriceEstablished
										.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
												+ STRING_PRICE_ESTABLISHED_LABEL);
								String type8 = STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
										+ hardwarePrice.getMerchandisingPromotions().getTag();
								mediaLinkForPriceEstablished.setType(type8);
								mediaLinkForPriceEstablished.setValue(
										hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
								mediaLinkForPriceEstablished.setDescription(DATA_NOT_FOUND);
								if (StringUtils
										.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForPriceEstablished
											.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForPriceEstablished.setDiscountId(DATA_NOT_FOUND);
								}
								mediaLinkForPriceEstablished.setPromoCategory(promoCatagoery);
								mediaLinkForPriceEstablished.setOfferCode(DATA_NOT_FOUND);
								listOfMedia.add(mediaLinkForPriceEstablished);
							}
							if (StringUtils
									.isNotBlank(hardwarePrice.getMerchandisingPromotions().getPromotionMedia())) {
								// PromotionMedia
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPromotionMedia = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForPromotionMedia.setId(hardwarePrice.getMerchandisingPromotions().getMpType()
										+ "." + STRING_PRICE_PROMOTION_MEDIA);
								String type9 = STRING_URL_ALLOWANCE + "&&" + bundleId + "&&"
										+ PROMO_TYPE_HARDWAREPROMOTION + "&&"
										+ hardwarePrice.getMerchandisingPromotions().getTag();
								mediaLinkForPromotionMedia.setType(type9);
								mediaLinkForPromotionMedia
										.setValue(hardwarePrice.getMerchandisingPromotions().getPromotionMedia());
								mediaLinkForPromotionMedia.setDescription(DATA_NOT_FOUND);
								if (StringUtils
										.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForPromotionMedia
											.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForPromotionMedia.setDiscountId(DATA_NOT_FOUND);
								}
								mediaLinkForPromotionMedia.setPromoCategory(promoCatagoery);
								mediaLinkForPromotionMedia.setOfferCode(DATA_NOT_FOUND);
								listOfMedia.add(mediaLinkForPromotionMedia);
							}
						}
					}
					MonthlyPrice mnthlyPrice = null;
					if (monthlyPrice != null) {
						mnthlyPrice = new MonthlyPrice();
						mnthlyPrice.setGross(monthlyPrice.getGross());
						mnthlyPrice.setNet(monthlyPrice.getNet());
						mnthlyPrice.setVat(monthlyPrice.getVat());
					}
					MonthlyDiscountPrice mnthlyDiscPrice = null;
					if (monthlyDiscountPrice != null) {
						mnthlyDiscPrice = new MonthlyDiscountPrice();
						mnthlyDiscPrice.setGross(monthlyDiscountPrice.getGross());
						mnthlyDiscPrice.setNet(monthlyDiscountPrice.getNet());
						mnthlyDiscPrice.setVat(monthlyDiscountPrice.getVat());
					}
					com.vf.uk.dal.utility.solr.entity.BundlePrice bp = new com.vf.uk.dal.utility.solr.entity.BundlePrice();
					bp.setBundleId(bundleId);
					bp.setMonthlyPrice(mnthlyPrice);
					bp.setMonthlyDiscountPrice(mnthlyDiscPrice);
					OneOffPrice onffPrice = null;
					if (oneoffPrice != null) {
						onffPrice = new OneOffPrice();
						onffPrice.setGross(oneoffPrice.getGross());
						onffPrice.setNet(oneoffPrice.getNet());
						onffPrice.setVat(oneoffPrice.getVat());
					}
					OneOffDiscountPrice onffDiscPrice = null;
					if (oneOffDisPrice != null) {
						onffDiscPrice = new OneOffDiscountPrice();
						onffDiscPrice.setGross(oneOffDisPrice.getGross());
						onffDiscPrice.setNet(oneOffDisPrice.getNet());
						onffDiscPrice.setVat(oneOffDisPrice.getVat());
					}
					com.vf.uk.dal.utility.solr.entity.HardwarePrice hw = new com.vf.uk.dal.utility.solr.entity.HardwarePrice();
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
		}

		result.put("offeredPrice", listOfOfferAppliedPriceDetails);
		result.put("media", listOfMedia);
		return result;
	}

	/**
	 * 
	 * @param preCalcPlanList
	 * @return DevicePreCalculatedData
	 */
	public static List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> convertDevicePreCalDataToSolrData(
			List<DevicePreCalculatedData> preCalcPlanList) {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData> deviceListObjectList = new ArrayList<>();

		preCalcPlanList.forEach(preCalList -> {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData deviceListObject = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.DevicePreCalculatedData();

			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media> mediaList = null;
			List<Media> listOfMedia = preCalList.getMedia();
			if (listOfMedia != null && !listOfMedia.isEmpty()) {
				mediaList = getListOfSolrMedia(listOfMedia);

			}

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.PriceInfo priceInfoObject = null;
			if (preCalList.getPriceInfo() != null) {
				priceInfoObject = getPriceForSolr(preCalList.getPriceInfo());
			}
			// Need to revist after solr changes done
			deviceListObject.setPriceInfo(priceInfoObject);
			deviceListObject.setDeviceId(preCalList.getDeviceId());
			deviceListObject.setRating(preCalList.getRating());
			deviceListObject.setLeadPlanId(preCalList.getLeadPlanId());
			deviceListObject.setNonUpgradeLeadPlanId(preCalList.getNonUpgradeLeadPlanId());
			deviceListObject.setUpgradeLeadPlanId(preCalList.getUpgradeLeadPlanId());
			deviceListObject.setProductGroupName(preCalList.getProductGroupName());
			deviceListObject.setProductGroupId(preCalList.getProductGroupId());
			deviceListObject.setMedia(mediaList);
			deviceListObject.setNonUpgradeLeadDeviceId(preCalList.getNonUpgradeLeadDeviceId());
			deviceListObject.setUpgradeLeadDeviceId(preCalList.getUpgradeLeadDeviceId());
			deviceListObject.setIsLeadMember(preCalList.getIsLeadMember());// preCalList.getIsLeadMember()
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
	public static List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media> getListOfSolrMedia(
			List<Media> listOfMedia) {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media> mediaList = new ArrayList<>();
		listOfMedia.forEach(media -> {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media mediaObject = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media();

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
	public static com.vf.uk.dal.device.datamodel.merchandisingpromotion.PriceInfo getPriceForSolr(
			com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfo) {
		com.vf.uk.dal.device.datamodel.merchandisingpromotion.PriceInfo priceInfoObject = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.PriceInfo();

		List<com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = priceInfo
				.getOfferAppliedPrices();
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails> listOfOfferAppliedPriceDetailsForSolr = null;
		if (listOfOfferAppliedPriceDetails != null && !listOfOfferAppliedPriceDetails.isEmpty()) {
			listOfOfferAppliedPriceDetailsForSolr = getListOfOfferAppliedPriceDetails(listOfOfferAppliedPriceDetails);
		}

		com.vf.uk.dal.utility.solr.entity.BundlePrice bundlePrice1 = priceInfo.getBundlePrice();
		com.vf.uk.dal.device.datamodel.merchandisingpromotion.BundlePrice bundlePrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.BundlePrice();
		if (bundlePrice1 != null) {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyPrice();

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyDiscountPrice();

			MonthlyPrice mnthlyPrice = bundlePrice1.getMonthlyPrice();

			if (mnthlyPrice != null && mnthlyPrice.getGross() != null) {
				monthlyPrice.setGross(Float.valueOf(mnthlyPrice.getGross()));

				monthlyPrice.setNet(Float.valueOf(mnthlyPrice.getNet()));

				monthlyPrice.setVat(Float.valueOf(mnthlyPrice.getVat()));
			}
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
		com.vf.uk.dal.device.datamodel.merchandisingpromotion.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.HardwarePrice();

		com.vf.uk.dal.utility.solr.entity.HardwarePrice hardwarePrice1 = priceInfo.getHardwarePrice();
		if (hardwarePrice1 != null) {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffPrice();

			OneOffPrice oneOffPrice1 = hardwarePrice1.getOneOffPrice();

			if (oneOffPrice1 != null && oneOffPrice1.getGross() != null) {
				oneOffPrice.setGross(Float.valueOf(oneOffPrice1.getGross()));

				oneOffPrice.setNet(Float.valueOf(oneOffPrice1.getNet()));

				oneOffPrice.setVat(Float.valueOf(oneOffPrice1.getVat()));
			}
			hardwarePrice.setOneOffPrice(oneOffPrice);

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffDiscountPrice();

			OneOffDiscountPrice OneOffDiscountPrice1 = hardwarePrice1.getOneOffDiscountPrice();

			if (OneOffDiscountPrice1 != null && OneOffDiscountPrice1.getGross() != null) {
				oneOffDiscountPrice.setGross(Float.valueOf(OneOffDiscountPrice1.getGross()));

				oneOffDiscountPrice.setNet(Float.valueOf(OneOffDiscountPrice1.getNet()));

				oneOffDiscountPrice.setVat(Float.valueOf(OneOffDiscountPrice1.getVat()));
			}

			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);

			List<com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption> deviceFinancingOption = hardwarePrice1
					.getFinancingOptions();
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption> financeOptions = null;
			if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
				financeOptions = new ArrayList<>();
				for (com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption financsOption : deviceFinancingOption) {
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption finance = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption();
					finance.setApr(financsOption.getApr());
					finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
					finance.setFinanceProvider(financsOption.getFinanceProvider());
					finance.setFinanceTerm(financsOption.getFinanceTerm());
					com.vf.uk.dal.utility.solr.entity.Price monthly = financsOption.getMonthlyPrice();
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price deviceMonthlyPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price();
					deviceMonthlyPrice.setGross(monthly.getGross());
					deviceMonthlyPrice.setNet(monthly.getNet());
					deviceMonthlyPrice.setVat(monthly.getVat());
					finance.setMonthlyPrice(deviceMonthlyPrice);
					com.vf.uk.dal.utility.solr.entity.Price totalInterest = financsOption.getTotalPriceWithInterest();
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price totalPriceWithInterest = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price();
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

	/**
	 * 
	 * @param offerAppliedPriceList
	 * @return List<OfferAppliedPriceDetails>
	 */
	public static List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails> getListOfOfferAppliedPriceDetails(
			List<OfferAppliedPriceDetails> offerAppliedPriceList) {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails> OfferAppliedListForSolr = new ArrayList<>();
		for (OfferAppliedPriceDetails offerAppliedPrice : offerAppliedPriceList) {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails OfferAppliedPriceDetails = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails();
			com.vf.uk.dal.utility.solr.entity.BundlePrice bundlePrice1 = offerAppliedPrice.getBundlePrice();
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.BundlePrice bundlePrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.BundlePrice();

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyPrice monthlyPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyPrice();

			MonthlyPrice mnthlyPrice = bundlePrice1.getMonthlyPrice();

			if (mnthlyPrice != null && mnthlyPrice.getGross() != null) {
				monthlyPrice.setGross(Float.valueOf(mnthlyPrice.getGross()));

				monthlyPrice.setNet(Float.valueOf(mnthlyPrice.getNet()));

				monthlyPrice.setVat(Float.valueOf(mnthlyPrice.getVat()));
			}
			bundlePrice.setMonthlyPrice(monthlyPrice);
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyDiscountPrice monthlyDiscountPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.MonthlyDiscountPrice();

			MonthlyDiscountPrice mnthlydiscPrice = bundlePrice1.getMonthlyDiscountPrice();
			if (mnthlydiscPrice != null && mnthlydiscPrice.getGross() != null) {
				monthlyDiscountPrice.setGross(Float.valueOf(mnthlydiscPrice.getGross()));

				monthlyDiscountPrice.setNet(Float.valueOf(mnthlydiscPrice.getNet()));

				monthlyDiscountPrice.setVat(Float.valueOf(mnthlydiscPrice.getVat()));

				bundlePrice.setBundleId(bundlePrice1.getBundleId());
			}
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.HardwarePrice hardwarePrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.HardwarePrice();

			com.vf.uk.dal.utility.solr.entity.HardwarePrice hardwarePrice1 = offerAppliedPrice.getHardwarePrice();

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffPrice oneOffPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffPrice();

			OneOffPrice oneOffPrice1 = hardwarePrice1.getOneOffPrice();

			if (oneOffPrice1 != null && oneOffPrice1.getGross() != null) {
				oneOffPrice.setGross(Float.valueOf(oneOffPrice1.getGross()));

				oneOffPrice.setNet(Float.valueOf(oneOffPrice1.getNet()));

				oneOffPrice.setVat(Float.valueOf(oneOffPrice1.getVat()));
			}
			hardwarePrice.setOneOffPrice(oneOffPrice);

			com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffDiscountPrice oneOffDiscountPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.OneOffDiscountPrice();

			OneOffDiscountPrice OneOffDiscountPrice1 = hardwarePrice1.getOneOffDiscountPrice();

			if (OneOffDiscountPrice1 != null && OneOffDiscountPrice1.getGross() != null) {
				oneOffDiscountPrice.setGross(Float.valueOf(OneOffDiscountPrice1.getGross()));

				oneOffDiscountPrice.setNet(Float.valueOf(OneOffDiscountPrice1.getNet()));

				oneOffDiscountPrice.setVat(Float.valueOf(OneOffDiscountPrice1.getVat()));
			}

			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			List<com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption> deviceFinancingOption = hardwarePrice1
					.getFinancingOptions();
			List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption> financeOptions = null;
			if (deviceFinancingOption != null && !deviceFinancingOption.isEmpty()) {
				financeOptions = new ArrayList<>();
				for (com.vf.uk.dal.utility.solr.entity.DeviceFinancingOption financsOption : deviceFinancingOption) {
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption finance = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.DeviceFinancingOption();
					finance.setApr(financsOption.getApr());
					finance.setDeviceFinancingId(financsOption.getDeviceFinancingId());
					finance.setFinanceProvider(financsOption.getFinanceProvider());
					finance.setFinanceTerm(financsOption.getFinanceTerm());
					com.vf.uk.dal.utility.solr.entity.Price monthly = financsOption.getMonthlyPrice();
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price deviceMonthlyPrice = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price();
					deviceMonthlyPrice.setGross(monthly.getGross());
					deviceMonthlyPrice.setNet(monthly.getNet());
					deviceMonthlyPrice.setVat(monthly.getVat());
					finance.setMonthlyPrice(deviceMonthlyPrice);
					com.vf.uk.dal.utility.solr.entity.Price totalInterest = financsOption.getTotalPriceWithInterest();
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price totalPriceWithInterest = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.Price();
					totalPriceWithInterest.setGross(totalInterest.getGross());
					totalPriceWithInterest.setNet(totalInterest.getNet());
					totalPriceWithInterest.setVat(totalInterest.getVat());
					finance.setTotalPriceWithInterest(totalPriceWithInterest);
					financeOptions.add(finance);
				}
			}
			hardwarePrice.setFinancingOptions(financeOptions);
			hardwarePrice.setHardwareId(hardwarePrice1.getHardwareId());
			OfferAppliedPriceDetails.setBundlePrice(bundlePrice);
			OfferAppliedPriceDetails.setHardwarePrice(hardwarePrice);
			OfferAppliedPriceDetails.setDeviceId(offerAppliedPrice.getDeviceId());
			OfferAppliedPriceDetails.setOfferCode(offerAppliedPrice.getOfferCode());
			OfferAppliedPriceDetails.setJourneyType(offerAppliedPrice.getJourneyType());
			OfferAppliedListForSolr.add(OfferAppliedPriceDetails);
		}
		return OfferAppliedListForSolr;
	}

}
