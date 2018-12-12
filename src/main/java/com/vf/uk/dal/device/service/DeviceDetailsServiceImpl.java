package com.vf.uk.dal.device.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.bundle.BundleHeader;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.bundle.CoupleRelation;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.ListOfDeviceDetailsDaoUtils;
import com.vf.uk.dal.device.utils.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceDetailsServiceImpl implements DeviceDetailsService {
	private static final String ERROR_CODE_SELECT_DEVICE_DETAIL = "error_device_detail_failed";
	public static final String PAYG_DEVICE = "Mobile Phones, Data Devices";
	public static final String DATE_FORMAT_COHERENCE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String STRING_HANDSET = "Handset";
	public static final String STRING_FOR_MEDIA_TYPE = "URL";
	public static final String STRING_TEXT_ALLOWANCE = "TEXT";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String STRING_OFFERS_DESCRIPTION = "merchandisingPromotions.merchandisingPromotion.description";
	public static final String STRING_FOR_ENTERTAINMENT = "Entertainment";
	public static final String STRING_OFFERS_LABEL = "merchandisingPromotions.merchandisingPromotion.label";
	public static final String STRING_TEXT = "TEXT";
	public static final String STRING_PROMOTION_MEDIA = "merchandisingPromotions.merchandisingPromotion.PromotionMedia";

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	Validator validator;
	
	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	CommonUtility commonUtility;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param id
	 * @param journeyType
	 * @param offerCode
	 * @return DeviceDetails
	 */
	@Override
	public DeviceDetails getDeviceDetails(String deviceId, String journeyType, String offerCode) {
		DeviceDetails deviceDetails;
		deviceDetails = getDeviceDetailsImplementation(deviceId, journeyType, offerCode);
		return deviceDetails;
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyTypeInput
	 * @param offerCode
	 * @return DeviceDetails
	 */
	public DeviceDetails getDeviceDetailsImplementation(String deviceId, String journeyTypeInput, String offerCode) {
		log.info("Start -->  calling  CommercialProductRepository.get");
		String journeyType;
		journeyType = deviceServiceImplUtility.getJourneyForVariant(journeyTypeInput);
		CommercialProduct commercialProduct = deviceEs.getCommercialProduct(deviceId);
		log.info("End -->  After calling  CommercialProductRepository.get");
		DeviceDetails deviceDetails;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& deviceServiceImplUtility.getProductclassValidation(commercialProduct)) {
			deviceDetails = getDeviceDetailsResponse(deviceId, offerCode, journeyType, commercialProduct);
		} else {
			log.error("No data found for given Device Id :" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE_DETAIL,ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID,"404");
		}
		return deviceDetails;
	}

	/**
	 * 
	 * @param deviceId
	 * @param offerCode
	 * @param journeyType
	 * @param commercialProduct
	 * @param deviceDetails
	 * @return DeviceDetails
	 */
	public DeviceDetails getDeviceDetailsResponse(String deviceId, String offerCode, String journeyType,
			CommercialProduct commercialProduct) {
		DeviceDetails deviceDetails;
		String journeyTypeLocal = null;
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		if (commercialProduct.getProductLines() != null && !commercialProduct.getProductLines().isEmpty()
				&& commercialProduct.getProductLines().contains(PAYG_DEVICE)) {
			validator.getJourneyAndOfferCodeValidationForPAYG(offerCode, journeyType);
			journeyTypeLocal = JOURNEY_TYPE_ACQUISITION;
			bundleAndHardwareTupleList = new ArrayList<>();
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
			bundleAndHardwareTuple.setBundleId(null);
			bundleAndHardwareTupleList.add(bundleAndHardwareTuple);

		} else {
			journeyTypeLocal = journeyType;
			bundleAndHardwareTupleList = getListOfPriceForBundleAndHardwareImplementation(commercialProduct, null,
					journeyTypeLocal);
		}
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = deviceServiceImplUtility
				.getListOfBundleAndHardwareTuple(offerCode, journeyTypeLocal, bundleAndHardwareTupleList);
		String leadPlanId = deviceServiceImplUtility.getLeadPlanId(bundleAndHardwareTupleList);
		log.info("Start -->  calling  bundleRepository.get");
		CommercialBundle commercialBundle = null;
		if (StringUtils.isNotBlank(leadPlanId)) {
			commercialBundle = deviceEs.getCommercialBundle(leadPlanId);
			log.info("End -->  After calling  bundleRepository.get");

		}
		List<BundleAndHardwareTuple> bundleHardwareTupleList = deviceServiceImplUtility
				.getBundleAndHardwareTuple(deviceId, commercialProduct, commercialBundle);
		deviceDetails = deviceServiceImplUtility.getDeviceDetailsFinal(deviceId, journeyTypeLocal, commercialProduct,
				listOfPriceForBundleAndHardware, bundleHardwareTupleList);
		return deviceDetails;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @param commerBundleIdMap
	 * @param journeyType
	 * @return List<BundleAndHardwareTuple>
	 */
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardwareImplementation(
			CommercialProduct commercialProduct, Map<String, CommercialBundle> commerBundleIdMap, String journeyType) {

		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		CommercialBundle commercialBundle = null;
		if (commerBundleIdMap != null) {
			commercialBundle = commerBundleIdMap.get(commercialProduct.getLeadPlanId());
		} else if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())) {
			commercialBundle = deviceEs.getCommercialBundle(commercialProduct.getLeadPlanId());
		}
		boolean sellableCheck = deviceServiceImplUtility.isSellable(journeyType, commercialBundle);
		if (commercialProduct.getLeadPlanId() != null
				&& commercialProduct.getListOfCompatiblePlanIds().contains(commercialProduct.getLeadPlanId())
				&& sellableCheck) {
			deviceServiceImplUtility.getBundleHardwareTrupleList(commercialProduct, bundleAndHardwareTupleList);
		} else {
			try {
				deviceServiceCommonUtility.getTupleList(commercialProduct, journeyType, bundleAndHardwareTupleList,
						listOfBundleHeaderForDevice);
			} catch (Exception e) {
				log.error("Exception occured when call happen to compatible bundles api: " + e);
			}
		}

		return bundleAndHardwareTupleList;

	}

	/**
	 * 
	 * @param commercialProduct
	 * @param journeyType
	 * @param offerCode
	 * @return validOffer
	 */
	public boolean validateOfferValidForDeviceImplementation(CommercialProduct commercialProduct, String journeyType,
			String offerCode) {
		List<String> offerCodes = new ArrayList<>();
		boolean validOffer = false;

		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			log.info("Start -->  calling  MerchandisingPromotion.get");
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = deviceEs.getMerchandisingPromotion(promotionName);
				setOffreCodesList(journeyType, offerCodes, promotionName, merchandisingPromotion);
			}
			log.info("End -->  After calling  MerchandisingPromotion.get");
		}
		validOffer = offerCodes.contains(offerCode) ? offerCodes.contains(offerCode) : false;
		return validOffer;
	}

	private void setOffreCodesList(String journeyType, List<String> offerCodes, String promotionName,
			MerchandisingPromotion merchandisingPromotion) {
		if (merchandisingPromotion != null) {
			String startDateTime = commonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
					DATE_FORMAT_COHERENCE);
			String endDateTime = commonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
					DATE_FORMAT_COHERENCE);
			String promotionPackageType = merchandisingPromotion.getCondition().getPackageType();
			List<String> promotionPackagesList = new ArrayList<>();
			if (StringUtils.isNotEmpty(promotionPackageType)) {
				promotionPackagesList = Arrays.asList(promotionPackageType.toLowerCase().split(","));
			}

			log.info(":::::::: MERCHE_PROMOTION_TAG :::: " + merchandisingPromotion.getTag()
					+ "::::: START DATE :: " + startDateTime + ":::: END DATE ::: " + endDateTime + " :::: ");
			if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag())
					&& deviceServiceImplUtility.dateValidationForOffersImplementation(startDateTime,
							endDateTime, DATE_FORMAT_COHERENCE)
					&& promotionPackagesList.contains(journeyType.toLowerCase())) {
				offerCodes.add(promotionName);
			}
		}
	}

	/**
	 * 
	 * @param listOfDeviceIds
	 * @param offerCode
	 * @param journeyType
	 * @return List<DeviceDetails>
	 */
	public List<DeviceDetails> getDeviceDetailsList(List<String> listOfDeviceIds, String offerCode,
			String journeyType) {

		List<DeviceDetails> listOfDevices = new ArrayList<>();
		List<BundleAndHardwareTuple> listOfBundleAndHardwareTuple = new ArrayList<>();

		for (String deviceSkuId : listOfDeviceIds) {
			DeviceDetails deviceDetails;
			CommercialProduct commercialProduct = deviceEs.getCommercialProduct(deviceSkuId);
			if (commercialProduct != null && commercialProduct.getIsDeviceProduct()
					&& commercialProduct.getProductClass().equalsIgnoreCase(STRING_HANDSET)) {
				List<MediaLink> listOfmerchandisingMedia = new ArrayList<>();
				deviceDetails = new DeviceDetails();
				deviceDetails.setDeviceId(commercialProduct.getId());
				deviceDetails.setDeviceId(commercialProduct.getId());
				setListOfMerchandisingMediaForImageUrl(commercialProduct, listOfmerchandisingMedia);
				setListOfMerchandisingMediaForMediaUrl(commercialProduct, listOfmerchandisingMedia);
				CommercialBundle commercialBundle = deviceEs.getCommercialBundle(deviceDetails.getLeadPlanId());
				setListOfmerchandisingMedia(listOfmerchandisingMedia, commercialBundle);
				listOfmerchandisingMedia.addAll(getMediaListForDevice(commercialProduct));

				List<MediaLink> finalListOfMediaLink = setFinalListOfMediaLink(listOfmerchandisingMedia);

				deviceDetails.setMedia(finalListOfMediaLink);

				listOfDevices.add(deviceDetails);
			}

		}
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = deviceDao
				.getPriceForBundleAndHardware(listOfBundleAndHardwareTuple, offerCode, journeyType);

		log.info("Setting prices and its corresponding promotions");
		settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, listOfDevices);

		return listOfDevices;
	}

	private List<MediaLink> setFinalListOfMediaLink(List<MediaLink> listOfmerchandisingMedia) {
		List<MediaLink> finalListOfMediaLink = new ArrayList<>();
		if (listOfmerchandisingMedia != null && !listOfmerchandisingMedia.isEmpty()) {
			for (MediaLink merchandisingMediaLink : listOfmerchandisingMedia) {
				if (merchandisingMediaLink != null && merchandisingMediaLink.getValue() != null
						&& !"".equals(merchandisingMediaLink.getValue())) {
					finalListOfMediaLink.add(merchandisingMediaLink);
				}
			}
		}
		return finalListOfMediaLink;
	}

	private void setListOfmerchandisingMedia(List<MediaLink> listOfmerchandisingMedia,
			CommercialBundle commercialBundle) {
		if (commercialBundle != null) {
			listOfmerchandisingMedia.addAll(mediaListForBundle(commercialBundle));
		}
	}

	private void setListOfMerchandisingMediaForMediaUrl(CommercialProduct commercialProduct,
			List<MediaLink> listOfmerchandisingMedia) {
		MediaLink mediaLink;
		if (commercialProduct.getListOfmediaURLs() != null) {
			for (com.vf.uk.dal.device.model.product.MediaURL mediaURL : commercialProduct
					.getListOfmediaURLs()) {
				mediaLink = new MediaLink();
				mediaLink.setId(mediaURL.getMediaName());
				mediaLink.setType(STRING_FOR_MEDIA_TYPE);
				mediaLink.setValue(mediaURL.getMediaURL());
				listOfmerchandisingMedia.add(mediaLink);
			}
		}
	}

	private void setListOfMerchandisingMediaForImageUrl(CommercialProduct commercialProduct,
			List<MediaLink> listOfmerchandisingMedia) {
		MediaLink mediaLink;
		if (commercialProduct.getListOfimageURLs() != null) {
			for (com.vf.uk.dal.device.model.product.ImageURL imageURL : commercialProduct
					.getListOfimageURLs()) {
				mediaLink = new MediaLink();
				mediaLink.setId(imageURL.getImageName());
				mediaLink.setType(STRING_FOR_MEDIA_TYPE);
				mediaLink.setValue(imageURL.getImageURL());
				listOfmerchandisingMedia.add(mediaLink);
			}
		}
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @return leadPlanId
	 */
	public String getLeadPlanIdForDeviceId(String deviceId, String journeyType) {
		String leadPlanId = null;
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<CoupleRelation> listOfCoupleRelationForMcs;
		List<BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		List<BundleHeader> listOfBundles;
		try {
			CommercialProduct commercialProduct = deviceEs.getCommercialProduct(deviceId);
			if (commercialProduct != null) {
				leadPlanId = commercialProduct.getLeadPlanId();
			}

			if (leadPlanId == null || leadPlanId.isEmpty()) {

				bundleDetailsForDevice = commonUtility.getPriceDetailsForCompatibaleBundle(deviceId, journeyType);
				listOfBundles = bundleDetailsForDevice.getStandalonePlansList();
				listOfCoupleRelationForMcs = bundleDetailsForDevice.getCouplePlansList();
				listOfBundleHeaderForDevice.addAll(listOfBundles);
				listOfCoupleRelationForMcs.forEach(
						coupleRelationMcs -> listOfBundleHeaderForDevice.addAll(coupleRelationMcs.getPlanList()));

				leadPlanId = getLeadPlanId(listOfBundleHeaderForDevice, listOfBundles);
				return leadPlanId;
			}

		} catch (Exception e) {
			log.error(" Exception occured when call happen to compatible bundles api : " + e);
		}
		return leadPlanId;

	}

	private String getLeadPlanId(List<BundleHeader> listOfBundleHeaderForDevice,
			List<BundleHeader> listOfBundles) {
		String leadPlanId = null;
		BundleHeader bundleHeaderForDevice = null;
		if (listOfBundleHeaderForDevice.isEmpty()) {
			log.error("No Compatible Bundles found for given device Id from bundles api: " + listOfBundles);

		} else {
			Iterator<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> it = listOfBundleHeaderForDevice.iterator();
			while (it.hasNext()) {
				com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleheaderForDevice = it.next();
				if (bundleheaderForDevice.getPriceInfo() == null
						|| bundleheaderForDevice.getPriceInfo().getHardwarePrice() == null
						|| (bundleheaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() == null
								&& bundleheaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice()
										.getGross() == null)) {
					it.remove();
				}
			}

			ListOfDeviceDetailsDaoUtils daoutils = new ListOfDeviceDetailsDaoUtils();
			bundleHeaderForDevice = daoutils
					.getListOfPriceForBundleAndHardwareForDevice(listOfBundleHeaderForDevice);
			if (bundleHeaderForDevice != null) {
				leadPlanId = bundleHeaderForDevice.getSkuId();
			}
		}
		return leadPlanId;
	}

	/**
	 * 
	 * @param commercialBundle
	 * @return listOfMediaLink
	 */
	public List<MediaLink> mediaListForBundle(CommercialBundle commercialBundle) {
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		if (commercialBundle.getPromoteAs() != null && commercialBundle.getPromoteAs().getPromotionName() != null
				&& !commercialBundle.getPromoteAs().getPromotionName().isEmpty()) {
			for (String promotionName : commercialBundle.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = deviceEs.getMerchandisingPromotion(promotionName);
				if (merchandisingPromotion != null
						&& !"conditional_full_discount".equalsIgnoreCase(merchandisingPromotion.getType())
						&& checkMerchandisingPromotionsType(merchandisingPromotion)) {
					String startDateTime = commonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							DATE_FORMAT_COHERENCE);
					String endDateTime = commonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							DATE_FORMAT_COHERENCE);
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag()) && commonUtility
							.dateValidationForOffers(startDateTime, endDateTime, DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
		}
		return listOfMediaLink;
	}

	private boolean checkMerchandisingPromotionsType(MerchandisingPromotion merchandisingPromotion) {
		return !"conditional_limited_discount".equalsIgnoreCase(merchandisingPromotion.getType())
				&& !"full_duration".equalsIgnoreCase(merchandisingPromotion.getType())
				&& !"limited_time".equalsIgnoreCase(merchandisingPromotion.getType());
	}

	/**
	 * 
	 * @param merchandisingPromotion
	 * @return listOfMediaLink
	 */
	public List<MediaLink> listOfMediaLinkBasedOnMerchandising(MerchandisingPromotion merchandisingPromotion) {
		MediaLink mediaLinkForDescription;
		MediaLink mediaLinkForLabel;
		MediaLink mediaLinkForUrlGrid;
		List<MediaLink> listOfMediaLink = new ArrayList<>();

		mediaLinkForLabel = new MediaLink();
		mediaLinkForLabel.setId(merchandisingPromotion.getType() + "." + STRING_OFFERS_LABEL);
		mediaLinkForLabel.setType(STRING_TEXT_ALLOWANCE);
		mediaLinkForLabel.setValue(merchandisingPromotion.getLabel());
		listOfMediaLink.add(mediaLinkForLabel);

		mediaLinkForDescription = new MediaLink();
		mediaLinkForDescription.setId(merchandisingPromotion.getType() + "." + STRING_OFFERS_DESCRIPTION);
		mediaLinkForDescription.setType(STRING_TEXT_ALLOWANCE);
		mediaLinkForDescription.setValue(merchandisingPromotion.getDescription());
		listOfMediaLink.add(mediaLinkForDescription);
		if (merchandisingPromotion.getType() != null
				&& StringUtils.containsIgnoreCase(merchandisingPromotion.getType(), STRING_FOR_ENTERTAINMENT)) {
			mediaLinkForUrlGrid = new MediaLink();
			mediaLinkForUrlGrid.setId(merchandisingPromotion.getType() + "." + STRING_PROMOTION_MEDIA);
			mediaLinkForUrlGrid.setType(STRING_FOR_MEDIA_TYPE);
			mediaLinkForUrlGrid.setValue(merchandisingPromotion.getPromotionMedia());
			listOfMediaLink.add(mediaLinkForUrlGrid);
		}
		return listOfMediaLink;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @return listOfMediaLink
	 */
	public List<MediaLink> getMediaListForDevice(CommercialProduct commercialProduct) {
		List<MediaLink> listOfMediaLink = new ArrayList<>();
		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = deviceEs.getMerchandisingPromotion(promotionName);
				if (merchandisingPromotion != null && checkMerchandisingPromotionsType(merchandisingPromotion)) {
					String startDateTime = commonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							DATE_FORMAT_COHERENCE);
					String endDateTime = commonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							DATE_FORMAT_COHERENCE);
					if (promotionName.equals(merchandisingPromotion.getTag()) && commonUtility
							.dateValidationForOffers(startDateTime, endDateTime, DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
		}
		return listOfMediaLink;

	}

	public void settingPriceAndPromotionsToListOfDevices(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, List<DeviceDetails> listOfDeviceDetails) {
		for (DeviceDetails deviceDetails : listOfDeviceDetails) {
			if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
				for (PriceForBundleAndHardware priceForBundleAndHardware : listOfPriceForBundleAndHardware) {
					if (priceForBundleAndHardware.getHardwarePrice() != null && priceForBundleAndHardware
							.getHardwarePrice().getHardwareId().equalsIgnoreCase(deviceDetails.getDeviceId())) {
						List<MediaLink> listOfmerchandisingMedia = new ArrayList<>();
						setMonthlyDiscountPriceForPriceForBundleAndHardware(priceForBundleAndHardware);
						setOneOffDiscountPriceForPriceForBundleAndHardware(priceForBundleAndHardware);
						deviceDetails.setPriceInfo(priceForBundleAndHardware);
						listOfmerchandisingMedia.addAll(deviceDetails.getMedia());
						priceForBundleAndHardware.getBundlePrice().getMerchandisingPromotions();
						com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion listOfHardwareMerch = priceForBundleAndHardware
								.getHardwarePrice().getMerchandisingPromotions();
						setListOfmerchandisingMedia(listOfmerchandisingMedia, listOfHardwareMerch);
						com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion priceBundleMerch = priceForBundleAndHardware
								.getBundlePrice().getMerchandisingPromotions();
						setListOfmerchandisingMediaFromTag(listOfmerchandisingMedia, priceBundleMerch);
					}
				}
			}
		}
	}

	private void setListOfmerchandisingMediaFromTag(List<MediaLink> listOfmerchandisingMedia,
			com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion priceBundleMerch) {
		if (priceBundleMerch != null && priceBundleMerch.getTag() != null) {
			MerchandisingPromotion merchandisingPromotion = deviceEs
					.getMerchandisingPromotion(priceBundleMerch.getTag());
			if (merchandisingPromotion != null) {
				MediaLink mediaLinkForLabel = new MediaLink();
				mediaLinkForLabel.setId(merchandisingPromotion.getType() + "." + STRING_OFFERS_LABEL);
				mediaLinkForLabel.setType(STRING_TEXT);
				mediaLinkForLabel.setValue(merchandisingPromotion.getLabel());
				listOfmerchandisingMedia.add(mediaLinkForLabel);

				MediaLink mediaLinkForDescription = new MediaLink();
				mediaLinkForDescription
						.setId(merchandisingPromotion.getType() + "." + STRING_OFFERS_DESCRIPTION);
				mediaLinkForDescription.setType(STRING_TEXT);
				mediaLinkForDescription.setValue(merchandisingPromotion.getDescription());
				listOfmerchandisingMedia.add(mediaLinkForDescription);
			}
		}
	}

	private void setListOfmerchandisingMedia(List<MediaLink> listOfmerchandisingMedia,
			com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion listOfHardwareMerch) {
		if (listOfHardwareMerch != null) {
			MediaLink mediaLinkForLabel = new MediaLink();
			mediaLinkForLabel.setId(listOfHardwareMerch.getMpType() + "." + STRING_OFFERS_LABEL);
			mediaLinkForLabel.setType(STRING_TEXT);
			mediaLinkForLabel.setValue(listOfHardwareMerch.getLabel());
			listOfmerchandisingMedia.add(mediaLinkForLabel);

			MediaLink mediaLinkForDescription = new MediaLink();
			mediaLinkForDescription
					.setId(listOfHardwareMerch.getMpType() + "." + STRING_OFFERS_DESCRIPTION);
			mediaLinkForDescription.setType(STRING_TEXT);
			mediaLinkForDescription.setValue(listOfHardwareMerch.getDescription());
			listOfmerchandisingMedia.add(mediaLinkForDescription);
		}
	}

	private void setOneOffDiscountPriceForPriceForBundleAndHardware(
			PriceForBundleAndHardware priceForBundleAndHardware) {
		if (priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
				priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
			priceForBundleAndHardware.getHardwarePrice().setOneOffDiscountPrice(null);
			priceForBundleAndHardware.setOneOffDiscountPrice(null);
		}
	}

	private void setMonthlyDiscountPriceForPriceForBundleAndHardware(
			PriceForBundleAndHardware priceForBundleAndHardware) {
		if (priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross().equalsIgnoreCase(
				priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross())) {
			priceForBundleAndHardware.getBundlePrice().setMonthlyDiscountPrice(null);
			priceForBundleAndHardware.setMonthlyDiscountPrice(null);
		}
	}

	/**
	 * @param deviceId
	 * @param offerCode
	 * @param journeyType
	 * @return listOfDevices
	 */
	@Override
	public List<DeviceDetails> getListOfDeviceDetails(String deviceId, String offerCode, String journeyType) {
		List<DeviceDetails> listOfDevices;
		List<String> listOfDeviceIds;
		log.info("Get the list of device details of device id(s) " + deviceId);
		if (deviceId.contains(",")) {
			String[] deviceIds = deviceId.split(",");
			listOfDeviceIds = Arrays.asList(deviceIds);
			listOfDevices = getDeviceDetailsList(listOfDeviceIds, offerCode, journeyType);
		} else {
			listOfDeviceIds = new ArrayList<>();
			listOfDeviceIds.add(deviceId);
			listOfDevices = getDeviceDetailsList(listOfDeviceIds, offerCode, journeyType);
		}
		if (listOfDevices == null || listOfDevices.isEmpty()) {
			log.error("Invalid Device Id" + ExceptionMessages.INVALID_DEVICE_ID);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE_DETAIL,ExceptionMessages.INVALID_DEVICE_ID,"404");
		}
		return listOfDevices;
	}

}
