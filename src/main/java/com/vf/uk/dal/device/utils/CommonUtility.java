package com.vf.uk.dal.device.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.client.BundleServiceClient;
import com.vf.uk.dal.device.client.CustomerServiceClient;
import com.vf.uk.dal.device.client.PriceServiceClient;
import com.vf.uk.dal.device.client.PromotionServiceClient;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListRequest;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundleDeviceAndProductsList;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.client.entity.price.RequestForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwareRequest;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareAccessory;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareDataAllowances;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareExtras;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareSash;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForBundleAndHardwareSecureNet;
import com.vf.uk.dal.device.client.entity.promotion.CataloguepromotionqueriesForHardwareSash;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.model.product.CommercialProduct;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * common methods used across the services.
 *
 */
@Slf4j
@Component
public class CommonUtility {
	public static final String PREFIX_SKU = "sku";
	public static final String ZERO = "0";
	public static final String STRING_TEXT_ALLOWANCE = "TEXT";
	public static final String DATE_FORMAT_COHERENCE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String JOURNEYTYPE_UPGRADE = "UPGRADE";
	public static final String STRING_PROMOTION_MEDIA = "merchandisingPromotions.merchandisingPromotion.PromotionMedia";
	public static final String STRING_OFFERS_LABEL = "merchandisingPromotions.merchandisingPromotion.label";
	public static final String STRING_OFFERS_DESCRIPTION = "merchandisingPromotions.merchandisingPromotion.description";
	public static final String STRING_MEDIA_PRICEESTABLISH = "priceEstablishedLabel";
	public static final String STRING_MEDIA_DESCRIPTION = "description";
	public static final String STRING_MEDIA_LABEL = "label";
	public static final String STRING_URL_ALLOWANCE = "URL";

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	BundleServiceClient bundleServiceClient;

	@Autowired
	PriceServiceClient priceServiceClient;

	@Autowired
	PromotionServiceClient promotionServiceClient;

	@Autowired
	CustomerServiceClient customerServiceClient;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;
	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;
	
	/**
	 * Round off price to two decimal points
	 * 
	 * @return DecimalFormat
	 */

	public DecimalFormat getDecimalFormat() {
		return new DecimalFormat("#0.00");
	}

	/**
	 * Date to String conversion.
	 * 
	 * @param date
	 * @param strDateFormat
	 * @return dateToStr
	 */

	public String getDateToString(Date date, String strDateFormat) {
		String formatdate = null;
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(strDateFormat);
			formatdate = format.format(date);
		}
		return formatdate;
	}

	/**
	 * Gets the bundle details from complans listing API.
	 *
	 * @param deviceId
	 *            the device id
	 * @param registryClient
	 *            the registry client
	 * @return the bundle details from complans listing API
	 */
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria) {
		return bundleServiceClient.getBundleDetailsFromComplansListingAPI(deviceId, sortCriteria);
	}

	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @param offerCode
	 * @param registryClient
	 * @param journeyType
	 * @return List<PriceForBundleAndHardware>
	 */
	public List<PriceForBundleAndHardware> getPriceDetails(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			String offerCode, String journeyType, String groupType) {
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		String billingType = getBillingType(groupType);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleAndHardwareTupleList);
		requestForBundleAndHardware.setBillingType(billingType);
		requestForBundleAndHardware.setOfferCode(offerCode);
		requestForBundleAndHardware.setPackageType(journeyType);
		return priceServiceClient.getPriceDetails(requestForBundleAndHardware);
	}

	/**
	 * 
	 * @param recomProductList
	 * @param registryClient
	 * @return RecommendedProductListResponse
	 */
	public RecommendedProductListResponse getRecommendedProductList(RecommendedProductListRequest recomProductList) {
		return customerServiceClient.getRecommendedProductList(recomProductList);
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param registryClient
	 * @return BundleDetailsForAppSrv
	 */
	public BundleDetailsForAppSrv getPriceDetailsForCompatibaleBundle(String deviceId, String journeyType) {
		return bundleServiceClient.getPriceDetailsForCompatibaleBundle(deviceId, journeyType);
	}

	/**
	 * 
	 * @param strTobeConverted
	 * @return JSONObject
	 */
	public JSONObject getJSONFromString(String strTobeConverted) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(strTobeConverted);
		} catch (org.json.simple.parser.ParseException exception) {
			log.error("Error while parsing string to JSONObject " + exception);
			throw new ApplicationException(ExceptionMessages.ERROR_STRING_TO_JSONOBJECT);
		}
		return jsonObject;
	}

	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public String appendPrefixString(String deviceId) {
		StringBuilder target = new StringBuilder(PREFIX_SKU);
		String leadingZero = deviceId.substring(0, 1);
		if (leadingZero.equals(ZERO)) {
			target.append(deviceId.substring(1, deviceId.length()));
		} else {
			target.append(deviceId);
		}
		return target.toString();
	}

	/**
	 * 
	 * @param bundleDeviceAndProductsList
	 * @param registryClient
	 * @return PriceForProduct
	 */
	public PriceForProduct getAccessoryPriceDetails(BundleDeviceAndProductsList bundleDeviceAndProductsList) {
		return priceServiceClient.getAccessoryPriceDetails(bundleDeviceAndProductsList);
	}

	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @param offerCode
	 * @param journeyType
	 * @param registryClient
	 * @return List<PriceForBundleAndHardware>
	 */
	public List<PriceForBundleAndHardware> getPriceDetailsUsingBundleHarwareTrouple(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType,
			String groupType) {
		String billingType = getBillingType(groupType);
		return priceServiceClient.getPriceDetailsUsingBundleHarwareTrouple(bundleAndHardwareTupleList, offerCode,
				journeyType, billingType);
	}

	/**
	 * Returns bundle Id from customer subscription.
	 * 
	 * @param subscriptionId
	 * @param subscriptionType
	 * @param registryClient
	 * @return SubscriptionBundleId
	 */
	public String getSubscriptionBundleId(String subscriptionId, String subscriptionType) {
		return customerServiceClient.getSubscriptionBundleId(subscriptionId, subscriptionType);
	}

	/**
	 * 
	 * @param price
	 * @return getpriceFormat
	 */
	public String getpriceFormat(Float price) {
		String formatedPrice = null;
		String decimalFormat = "#.00";
		if (price != null) {
			DecimalFormat deciFormat = new DecimalFormat(decimalFormat);
			Double tmpPrice = price - Math.floor(price);
			if ("0.0".equals(tmpPrice.toString())) {
				formatedPrice = String.valueOf(price.intValue());
			} else {
				formatedPrice = deciFormat.format(price);
			}
		}
		return formatedPrice;
	}

	/**
	 * Date validation
	 * 
	 * @author manoj.bera
	 * @param startDateTime
	 * @param endDateTime
	 * @return flag
	 */
	public Boolean dateValidationForOffers(String startDateTime, String endDateTime, String strDateFormat) {

		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();

		String currentDateStr = dateFormat.format(currentDate);

		try {
			currentDate = dateFormat.parse(currentDateStr);

		} catch (ParseException | DateTimeParseException e) {
			log.error("ParseException : " + e);
		}

		Date startDate = null;
		Date endDate = null;

		try {
			if (startDateTime != null) {
				startDate = dateFormat.parse(startDateTime);
				log.info("::::: StartDate " + startDate + " :::::");
			}

		} catch (ParseException | DateTimeParseException e) {
			log.error(" ParseException: " + e);
		}

		try {
			if (endDateTime != null) {
				endDate = dateFormat.parse(endDateTime);
				log.info("::::: EndDate " + endDate + " :::::");
			}
		} catch (ParseException | DateTimeParseException e) {
			log.error("ParseException: " + e);
		}

		if (startDate != null && endDate != null
				&& checkForDateWhenStartAndEndDateNotNull(currentDate, startDate, endDate)) {
			flag = true;
		}
		if (startDate == null && endDate != null && currentDate.before(endDate)) {
			flag = true;
		}
		if (startDate != null && endDate == null && currentDate.after(startDate)) {
			flag = true;
		}
		if (startDate == null && endDate == null) {
			flag = true;
		}

		return flag;
	}

	private boolean checkForDateWhenStartAndEndDateNotNull(Date currentDate, Date startDate, Date endDate) {
		return (currentDate.after(startDate) || currentDate.equals(startDate))
				&& (currentDate.before(endDate) || currentDate.equals(endDate));
	}

	/**
	 * 
	 * @param commercialProduct
	 * @return isProductExpired
	 */
	public boolean isProductNotExpired(CommercialProduct commercialProduct) {
		boolean isProductExpired = false;
		String startDateTime = null;
		String endDateTime = null;
		if (commercialProduct.getProductAvailability().getStart() != null) {
			startDateTime = getDateToString(commercialProduct.getProductAvailability().getStart(),
					DATE_FORMAT_COHERENCE);
		}
		if (commercialProduct.getProductAvailability().getEnd() != null) {
			endDateTime = getDateToString(commercialProduct.getProductAvailability().getEnd(), DATE_FORMAT_COHERENCE);
		}
		if (!commercialProduct.getProductAvailability().isSalesExpired()) {

			isProductExpired = dateValidationForOffers(startDateTime, endDateTime, DATE_FORMAT_COHERENCE);

		}
		return isProductExpired;

	}

	/**
	 * 
	 * @param commercialProduct
	 * @param journeyType
	 * @return isProductJourneySpecific
	 */
	public boolean isProductJourneySpecific(CommercialProduct commercialProduct, String journeyType) {
		boolean isProductJourneySpecific = false;
		if (StringUtils.isNotBlank(journeyType) && JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)) {
			if (commercialProduct.getProductControl() != null && commercialProduct.getProductControl().isSellableRet()
					&& commercialProduct.getProductControl().isDisplayableRet()) {

				isProductJourneySpecific = true;
			}
		} else {
			if (commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isDisplayableAcq()
					&& commercialProduct.getProductControl().isSellableAcq()) {

				isProductJourneySpecific = true;

			}
		}
		return isProductJourneySpecific;

	}

	/**
	 * Gets the promotions for bundle and hardware.
	 *
	 * @param bundleHardwareTupleList
	 *            the bundle hardwaretuple list
	 * @param registryClient
	 *            the registry client
	 * @return the promotions for bundle and hardware
	 * @author manoj.bera
	 * @SPRINT 6.4
	 */
	public List<BundleAndHardwarePromotions> getPromotionsForBundleAndHardWarePromotions(
			List<BundleAndHardwareTuple> bundleHardwareTupleList, String journeyType) {
		BundleAndHardwareRequest request = new BundleAndHardwareRequest();
		request.setBundleAndHardwareList(bundleHardwareTupleList);
		request.setJourneyType(journeyType);
		return promotionServiceClient.getPromotionsForBundleAndHardWarePromotions(request);
	}

	/**
	 * 
	 * @param entertainmentPacks
	 * @param dataAllowances
	 * @param planCouplingPromotions
	 * @param sash
	 * @param secureNet
	 * @return List<MediaLink>
	 * @author manoj.bera
	 * @SPRINT 6.4
	 */
	public List<MediaLink> getMediaListForBundleAndHardware(
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks,
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances,
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash,
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet,
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional) {
		List<MediaLink> mediaList = new ArrayList<>();
		setConditionalSashBanner(sashBundleConditional, mediaList);
		setMediaListForFreeAccForHardware(freeAccForHardwares, mediaList);
		setMediaListForFreeExtrasForHardware(freeExtrasForHardwares, mediaList);
		setMediaListForFreeAccForHardware(freeAccForPlans, mediaList);
		setMediaListForFreeExtrasForHardware(freeExtrasForPlans, mediaList);
		setMediaListForFreeAccForHardware(freeAccessories, mediaList);
		setMediaListForFreeExtrasForHardware(freeExtras, mediaList);
		setMediaListForSashBannerForHardware(sashBannerForHardware, mediaList);
		setMediaListForEntertainmentPacks(entertainmentPacks, mediaList);
		setMediaListForDataAccessory(dataAllowances, mediaList);
		setPlanCoupolingPromotion(planCouplingPromotions, mediaList);
		setConditionalSashBanner(sash, mediaList);
		setMediaListForSecure(secureNet, mediaList);

		return mediaList;
	}

	private void setMediaListForSecure(List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet,
			List<MediaLink> mediaList) {
		if (secureNet != null && !secureNet.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareSecureNet secureNetPromotion : secureNet) {
				setMediaListForLabel(mediaList, secureNetPromotion);
				setMediaListForDescriptionSash(mediaList, secureNetPromotion);
				if (StringUtils.isNotBlank(secureNetPromotion.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(secureNetPromotion.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(secureNetPromotion.getPromotionMedia());
					if (StringUtils.isNotBlank(secureNetPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(secureNetPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setMediaListForDescriptionSash(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareSecureNet secureNetPromotion) {
		if (StringUtils.isNotBlank(secureNetPromotion.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(secureNetPromotion.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(secureNetPromotion.getDescription());
			if (StringUtils.isNotBlank(secureNetPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(secureNetPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareSecureNet secureNetPromotion) {
		if (StringUtils.isNotBlank(secureNetPromotion.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(secureNetPromotion.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(secureNetPromotion.getLabel());
			if (StringUtils.isNotBlank(secureNetPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(secureNetPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setPlanCoupolingPromotion(
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions,
			List<MediaLink> mediaList) {
		if (planCouplingPromotions != null && !planCouplingPromotions.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions planCouplingPromotion : planCouplingPromotions) {
				setMediaListForPlanCouplingLabel(mediaList, planCouplingPromotion);
				setPlanCouplingMediaListForDescription(mediaList, planCouplingPromotion);
				if (StringUtils.isNotBlank(planCouplingPromotion.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(planCouplingPromotion.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(planCouplingPromotion.getPromotionMedia());
					if (StringUtils.isNotBlank(planCouplingPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(planCouplingPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setPlanCouplingMediaListForDescription(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions planCouplingPromotion) {
		if (StringUtils.isNotBlank(planCouplingPromotion.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(planCouplingPromotion.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(planCouplingPromotion.getDescription());
			if (StringUtils.isNotBlank(planCouplingPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(planCouplingPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForPlanCouplingLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions planCouplingPromotion) {
		if (StringUtils.isNotBlank(planCouplingPromotion.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(planCouplingPromotion.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(planCouplingPromotion.getLabel());
			if (StringUtils.isNotBlank(planCouplingPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(planCouplingPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForDataAccessory(
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances,
			List<MediaLink> mediaList) {
		if (dataAllowances != null && !dataAllowances.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance : dataAllowances) {
				setDataAllowanceMediaListLabel(mediaList, dataAllowance);
				setDataAllowanceForDescription(mediaList, dataAllowance);
				if (StringUtils.isNotBlank(dataAllowance.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(dataAllowance.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(dataAllowance.getPromotionMedia());
					if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(dataAllowance.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setDataAllowanceForDescription(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance) {
		if (StringUtils.isNotBlank(dataAllowance.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(dataAllowance.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(dataAllowance.getDescription());
			if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(dataAllowance.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setDataAllowanceMediaListLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance) {
		if (StringUtils.isNotBlank(dataAllowance.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(dataAllowance.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(dataAllowance.getLabel());
			if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(dataAllowance.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForEntertainmentPacks(
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks,
			List<MediaLink> mediaList) {
		if (entertainmentPacks != null && !entertainmentPacks.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainment : entertainmentPacks) {
				setEntertainmentLabel(mediaList, entertainment);
				setEnertainmentDescription(mediaList, entertainment);
				if (StringUtils.isNotBlank(entertainment.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(entertainment.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(entertainment.getPromotionMedia());
					if (StringUtils.isNotBlank(entertainment.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(entertainment.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setEnertainmentDescription(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainment) {
		if (StringUtils.isNotBlank(entertainment.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(entertainment.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(entertainment.getDescription());
			if (StringUtils.isNotBlank(entertainment.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(entertainment.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setEntertainmentLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainment) {
		if (StringUtils.isNotBlank(entertainment.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(entertainment.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(entertainment.getLabel());
			if (StringUtils.isNotBlank(entertainment.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(entertainment.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForSashBannerForHardware(
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware, List<MediaLink> mediaList) {
		if (sashBannerForHardware != null && !sashBannerForHardware.isEmpty()) {
			for (CataloguepromotionqueriesForHardwareSash sashBannerHardware : sashBannerForHardware) {
				setSashBannerLabel(mediaList, sashBannerHardware);
				setSashBannerDescription(mediaList, sashBannerHardware);
				if (StringUtils.isNotBlank(sashBannerHardware.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(sashBannerHardware.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(sashBannerHardware.getPromotionMedia());
					if (StringUtils.isNotBlank(sashBannerHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(sashBannerHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setSashBannerDescription(List<MediaLink> mediaList,
			CataloguepromotionqueriesForHardwareSash sashBannerHardware) {
		if (StringUtils.isNotBlank(sashBannerHardware.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(sashBannerHardware.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(sashBannerHardware.getDescription());
			if (StringUtils.isNotBlank(sashBannerHardware.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(sashBannerHardware.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setSashBannerLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForHardwareSash sashBannerHardware) {
		if (StringUtils.isNotBlank(sashBannerHardware.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(sashBannerHardware.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(sashBannerHardware.getLabel());
			if (StringUtils.isNotBlank(sashBannerHardware.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(sashBannerHardware.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForFreeExtrasForHardware(
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares,
			List<MediaLink> mediaList) {
		if (freeExtrasForHardwares != null && !freeExtrasForHardwares.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForHardware : freeExtrasForHardwares) {
				setFreeExtraForHardwareLabel(mediaList, freeExtrasForHardware);
				setFreeExtraForHardwareDescription(mediaList, freeExtrasForHardware);
				if (StringUtils.isNotBlank(freeExtrasForHardware.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtrasForHardware.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(freeExtrasForHardware.getPromotionMedia());
					if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setFreeExtraForHardwareDescription(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForHardware) {
		if (StringUtils.isNotBlank(freeExtrasForHardware.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(freeExtrasForHardware.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(freeExtrasForHardware.getDescription());
			if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setFreeExtraForHardwareLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForHardware) {
		if (StringUtils.isNotBlank(freeExtrasForHardware.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(freeExtrasForHardware.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(freeExtrasForHardware.getLabel());
			if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForFreeAccForHardware(
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares,
			List<MediaLink> mediaList) {
		if (freeAccForHardwares != null && !freeAccForHardwares.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForHardware : freeAccForHardwares) {
				setMediaListForLabel(mediaList, freeAccForHardware);
				setMediaListForDescription(mediaList, freeAccForHardware);
				if (StringUtils.isNotBlank(freeAccForHardware.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccForHardware.getType() + "." + STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(freeAccForHardware.getPromotionMedia());
					if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
	}

	private void setMediaListForDescription(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForHardware) {
		if (StringUtils.isNotBlank(freeAccForHardware.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(freeAccForHardware.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(freeAccForHardware.getDescription());
			if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setMediaListForLabel(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForHardware) {
		if (StringUtils.isNotBlank(freeAccForHardware.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(freeAccForHardware.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(freeAccForHardware.getLabel());
			if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	private void setConditionalSashBanner(List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional,
			List<MediaLink> mediaList) {
		if (sashBundleConditional != null && !sashBundleConditional.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareSash sashPromotion : sashBundleConditional) {
				getConditionalSashBanner(mediaList, sashPromotion);
			}
		}
	}

	private void getConditionalSashBanner(List<MediaLink> mediaList,
			CataloguepromotionqueriesForBundleAndHardwareSash sashPromotion) {
		if (StringUtils.isNotBlank(sashPromotion.getLabel())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(sashPromotion.getType() + "." + STRING_OFFERS_LABEL);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(sashPromotion.getLabel());
			if (StringUtils.isNotBlank(sashPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(sashPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
		if (StringUtils.isNotBlank(sashPromotion.getDescription())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(sashPromotion.getType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaOfferLink.setType(STRING_TEXT_ALLOWANCE);
			mediaOfferLink.setValue(sashPromotion.getDescription());
			if (StringUtils.isNotBlank(sashPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(sashPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
		if (StringUtils.isNotBlank(sashPromotion.getPromotionMedia())) {
			MediaLink mediaOfferLink = new MediaLink();
			mediaOfferLink.setId(sashPromotion.getType() + "." + STRING_PROMOTION_MEDIA);
			mediaOfferLink.setType(STRING_URL_ALLOWANCE);
			mediaOfferLink.setValue(sashPromotion.getPromotionMedia());
			if (StringUtils.isNotBlank(sashPromotion.getPriority())) {
				mediaOfferLink.setPriority(Integer.valueOf(sashPromotion.getPriority()));
			}
			mediaList.add(mediaOfferLink);
		}
	}

	/**
	 * 
	 * @param availableFromDate
	 * @param strDateFormat
	 * @return dateValidationForProduct
	 */
	public Boolean dateValidationForProduct(String availableFromDate, String strDateFormat) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();
		Date startDate = null;

		String currentDateStr = dateFormat.format(currentDate);

		try {
			currentDate = dateFormat.parse(currentDateStr);
			startDate = dateFormat.parse(availableFromDate);

		} catch (ParseException | DateTimeParseException e) {
			log.error("ParseException: " + e);
		}

		if (startDate != null && currentDate.before(startDate)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param price
	 * @param commercialBundleMap
	 * @param productLinesList
	 * @param journeyType
	 * @return isValidJourneySpecificBundle
	 */
	public boolean isValidJourneySpecificBundle(PriceForBundleAndHardware price,
			Map<String, CommercialBundle> commercialBundleMap, List<String> productLinesList, String journeyType) {
		boolean flag = false;
		String bundleId = price.getBundlePrice().getBundleId();
		if (commercialBundleMap.containsKey(bundleId)) {
			CommercialBundle commercialBundle = commercialBundleMap.get(bundleId);
			String startDateTime = null;
			String endDateTime = null;
			if (commercialBundle.getAvailability().getStart() != null) {
				startDateTime = getDateToString(commercialBundle.getAvailability().getStart(), DATE_FORMAT_COHERENCE);
			}
			if (commercialBundle.getAvailability().getEnd() != null) {
				endDateTime = getDateToString(commercialBundle.getAvailability().getEnd(), DATE_FORMAT_COHERENCE);
			}
			boolean isCompatible = commercialBundle.getProductLines().stream()
					.anyMatch(productLinesList.get(0)::equalsIgnoreCase) ? true
							: checkproductLines(commercialBundle, productLinesList);
			boolean isSalesExpire = dateValidationForOffers(startDateTime, endDateTime, DATE_FORMAT_COHERENCE)
					&& !commercialBundle.getAvailability().getSalesExpired();
			flag = setFlag(journeyType, commercialBundle, isCompatible, isSalesExpire);
		}
		return flag;
	}

	private boolean checkproductLines(CommercialBundle commercialBundle, List<String> productLinesList) {
		return commercialBundle.getProductLines().stream().anyMatch(productLinesList.get(1)::equalsIgnoreCase) ? true
				: false;
	}

	private boolean setFlag(String journeyType, CommercialBundle commercialBundle, boolean isCompatible,
			boolean isSalesExpire) {
		boolean flag = false;
		if (checkForNonUpgrade(journeyType, commercialBundle, isCompatible, isSalesExpire)
				|| checkForUpgrade(journeyType, commercialBundle, isCompatible, isSalesExpire)) {
			flag = true;
		}
		return flag;
	}

	private boolean checkForNonUpgrade(String journeyType, CommercialBundle commercialBundle, boolean isCompatible,
			boolean isSalesExpire) {
		return deviceServiceImplUtility.isNonUpgrade(journeyType) && isCompatible && isSalesExpire
				&& deviceServiceImplUtility.isNonUpgradeCommercialBundle(commercialBundle);
	}

	private boolean checkForUpgrade(String journeyType, CommercialBundle commercialBundle, boolean isCompatible,
			boolean isSalesExpire) {
		return deviceServiceImplUtility.isUpgrade(journeyType) && isCompatible && isSalesExpire
				&& deviceServiceImplUtility.isUpgradeFromCommercialBundle(commercialBundle);
	}

	/**
	 * 
	 * @param image
	 * @return
	 */
	public String getImageMediaUrl(String cdnDomain, String image) {
		return StringUtils.isNotBlank(cdnDomain) ? cdnDomain + image : image;
	}

	/**
	 * 
	 * @param listOfOfferPacks
	 * @param merchandisingMedia
	 */
	public MerchandisingPromotionsPackage getNonPricingPromotions(BundleAndHardwarePromotions promotions,
			List<MediaLink> merchandisingMedia) {
		List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = promotions
				.getEntertainmentPacks();
		List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = promotions
				.getDataAllowances();
		List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = promotions
				.getPlanCouplingPromotions();
		List<CataloguepromotionqueriesForBundleAndHardwareSash> sash = promotions.getSashBannerForPlan();
		List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = promotions.getSecureNet();
		List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = promotions.getSashBannerForHardware();
		List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = promotions.getFreeExtras();
		List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories = promotions.getFreeAccessory();
		List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans = promotions
				.getFreeExtrasForPlan();
		List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans = promotions.getFreeAccForPlan();
		List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares = promotions
				.getFreeExtrasForHardware();
		List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares = promotions
				.getFreeAccForHardware();
		List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional = promotions
				.getConditionalSashBanner();
		merchandisingMedia
				.addAll(getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances, planCouplingPromotions,
						sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories, freeExtrasForPlans,
						freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares, sashBundleConditional));
		return deviceTilesDaoUtils.assembleMerchandisingPromotion(promotions, entertainmentPacks, dataAllowances, sash,
				secureNet, sashBannerForHardware, freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares,
				freeAccForHardwares, sashBundleConditional);
	}

	/**
	 * 
	 * @param commercialProduct
	 * @param merchandisingMedia
	 */
	public void getImageMediaLink(CommercialProduct commercialProduct, List<MediaLink> merchandisingMedia,
			String cdnDomain) {
		MediaLink mediaLink;
		if (commercialProduct.getListOfimageURLs() != null) {
			for (com.vf.uk.dal.device.model.product.ImageURL imageURL : commercialProduct.getListOfimageURLs()) {
				if (StringUtils.isNotBlank(imageURL.getImageURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(STRING_URL_ALLOWANCE);
					mediaLink.setValue(getImageMediaUrl(cdnDomain, imageURL.getImageURL()));
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (commercialProduct.getListOfmediaURLs() != null) {
			for (com.vf.uk.dal.device.model.product.MediaURL mediaURL : commercialProduct.getListOfmediaURLs()) {
				if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(mediaURL.getMediaName());
					mediaLink.setType(STRING_URL_ALLOWANCE);
					mediaLink.setValue(getImageMediaUrl(cdnDomain, mediaURL.getMediaURL()));
					merchandisingMedia.add(mediaLink);
				}
			}
		}
	}

	private String getBillingType(String groupType) {
		if (StringUtils.equalsIgnoreCase(groupType, "DEVICE_PAYG")) {
			return "payg";
		} else {
			return "paym";
		}
	}
}
