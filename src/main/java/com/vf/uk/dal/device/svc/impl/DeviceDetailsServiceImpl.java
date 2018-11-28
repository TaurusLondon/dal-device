package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.Equipment;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.MerchandisingControl;
import com.vf.uk.dal.device.entity.MerchandisingPromotions;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.DeviceDetailsService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.ListOfDeviceDetailsDaoUtils;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleHeader;
import com.vf.uk.dal.utility.entity.CoupleRelation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceDetailsServiceImpl implements DeviceDetailsService {
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
		deviceDetails = getDeviceDetails_Implementation(deviceId, journeyType, offerCode);
		return deviceDetails;
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyTypeInput
	 * @param offerCode
	 * @return DeviceDetails
	 */
	public DeviceDetails getDeviceDetails_Implementation(String deviceId, String journeyTypeInput, String offerCode) {
		log.info("Start -->  calling  CommercialProductRepository.get");
		String journeyType;
		journeyType = DeviceServiceImplUtility.getJourneyForVariant(journeyTypeInput);
		CommercialProduct commercialProduct = deviceEs.getCommercialProduct(deviceId);
		log.info("End -->  After calling  CommercialProductRepository.get");
		DeviceDetails deviceDetails;
		if (commercialProduct != null && commercialProduct.getId() != null && commercialProduct.getIsDeviceProduct()
				&& DeviceServiceImplUtility.getProductclassValidation(commercialProduct)) {
			deviceDetails = getDeviceDetailsResponse(deviceId, offerCode, journeyType, commercialProduct);
		} else {
			log.error("No data found for given Device Id :" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
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
			Validator.getJourneyAndOfferCodeValidationForPAYG(offerCode, journeyType);
			journeyTypeLocal = JOURNEY_TYPE_ACQUISITION;
			bundleAndHardwareTupleList = new ArrayList<>();
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
			bundleAndHardwareTuple.setBundleId(null);
			bundleAndHardwareTupleList.add(bundleAndHardwareTuple);

		} else {
			journeyTypeLocal = journeyType;
			bundleAndHardwareTupleList = getListOfPriceForBundleAndHardware_Implementation(commercialProduct, null,
					journeyTypeLocal);
		}
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = deviceServiceImplUtility
				.getListOfBundleAndHardwareTuple(offerCode, journeyTypeLocal, bundleAndHardwareTupleList);
		String leadPlanId = DeviceServiceImplUtility.getLeadPlanId(bundleAndHardwareTupleList);
		log.info("Start -->  calling  bundleRepository.get");
		CommercialBundle commercialBundle = null;
		if (StringUtils.isNotBlank(leadPlanId)) {
			commercialBundle = deviceEs.getCommercialBundle(leadPlanId);
			log.info("End -->  After calling  bundleRepository.get");

		}
		List<BundleAndHardwareTuple> bundleHardwareTupleList = DeviceServiceImplUtility
				.getBundleAndHardwareTuple(deviceId, commercialProduct, commercialBundle);
		deviceDetails = deviceServiceImplUtility.getDeviceDetailsFinal(deviceId, journeyTypeLocal, commercialProduct,
				listOfPriceForBundleAndHardware, bundleHardwareTupleList);
		if (StringUtils.isNotEmpty(offerCode) && StringUtils.isNotEmpty(journeyTypeLocal)) {
			deviceDetails.setValidOffer(
					validateOfferValidForDevice_Implementation(commercialProduct, journeyTypeLocal, offerCode));
		}
		return deviceDetails;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @param commerBundleIdMap
	 * @param journeyType
	 * @return List<BundleAndHardwareTuple>
	 */
	public List<BundleAndHardwareTuple> getListOfPriceForBundleAndHardware_Implementation(
			CommercialProduct commercialProduct, Map<String, CommercialBundle> commerBundleIdMap, String journeyType) {

		List<BundleAndHardwareTuple> bundleAndHardwareTupleList;
		bundleAndHardwareTupleList = new ArrayList<>();
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice = new ArrayList<>();
		CommercialBundle commercialBundle = null;
		if (commerBundleIdMap != null) {
			commercialBundle = commerBundleIdMap.get(commercialProduct.getLeadPlanId());
		} else if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())) {
			commercialBundle = deviceEs.getCommercialBundle(commercialProduct.getLeadPlanId());
		}
		boolean sellableCheck = DeviceServiceImplUtility.isSellable(journeyType, commercialBundle);
		if (commercialProduct.getLeadPlanId() != null
				&& commercialProduct.getListOfCompatiblePlanIds().contains(commercialProduct.getLeadPlanId())
				&& sellableCheck) {
			DeviceServiceImplUtility.getBundleHardwareTrupleList(commercialProduct, bundleAndHardwareTupleList);
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
	public boolean validateOfferValidForDevice_Implementation(CommercialProduct commercialProduct, String journeyType,
			String offerCode) {
		List<String> offerCodes = new ArrayList<>();
		boolean validOffer = false;

		if (commercialProduct.getPromoteAs() != null && commercialProduct.getPromoteAs().getPromotionName() != null
				&& !commercialProduct.getPromoteAs().getPromotionName().isEmpty()) {
			log.info("Start -->  calling  MerchandisingPromotion.get");
			for (String promotionName : commercialProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotion merchandisingPromotion = deviceEs.getMerchandisingPromotion(promotionName);
				if (merchandisingPromotion != null) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							DATE_FORMAT_COHERENCE);
					String promotionPackageType = merchandisingPromotion.getCondition().getPackageType();
					List<String> promotionPackagesList = new ArrayList<>();
					if (StringUtils.isNotEmpty(promotionPackageType)) {
						promotionPackagesList = Arrays.asList(promotionPackageType.toLowerCase().split(","));
					}

					log.info(":::::::: MERCHE_PROMOTION_TAG :::: " + merchandisingPromotion.getTag()
							+ "::::: START DATE :: " + startDateTime + ":::: END DATE ::: " + endDateTime + " :::: ");
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag())
							&& DeviceServiceImplUtility.dateValidationForOffers_Implementation(startDateTime,
									endDateTime, DATE_FORMAT_COHERENCE)
							&& promotionPackagesList.contains(journeyType.toLowerCase())) {
						offerCodes.add(promotionName);
					}
				}
			}
			log.info("End -->  After calling  MerchandisingPromotion.get");
		}
		validOffer = offerCodes.contains(offerCode) ? true : false;
		return validOffer;
	}

	// serviceImpl
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
				deviceDetails.setName(commercialProduct.getDisplayName());
				deviceDetails.setDescription(commercialProduct.getPreDesc());
				deviceDetails.setDeviceId(commercialProduct.getId());
				deviceDetails.setName(commercialProduct.getName());
				deviceDetails.setDescription(commercialProduct.getPreDesc());
				deviceDetails.setProductClass(commercialProduct.getProductClass());
				deviceDetails.setProductLines(commercialProduct.getProductLines());

				MerchandisingControl merchandisingControl = new MerchandisingControl();
				merchandisingControl.setIsDisplayableECare(commercialProduct.getProductControl().isDisplayableinLife());
				merchandisingControl.setIsSellableECare(commercialProduct.getProductControl().isSellableinLife());
				merchandisingControl.setIsDisplayableAcq(commercialProduct.getProductControl().isDisplayableAcq());
				merchandisingControl.setIsSellableRet(commercialProduct.getProductControl().isSellableRet());
				merchandisingControl.setIsDisplayableRet(commercialProduct.getProductControl().isDisplayableRet());
				merchandisingControl.setIsSellableAcq(commercialProduct.getProductControl().isSellableAcq());
				merchandisingControl
						.setIsDisplayableSavedBasket(commercialProduct.getProductControl().isDisplayableSavedBasket());
				merchandisingControl.setOrder(commercialProduct.getOrder().intValue());
				merchandisingControl.setPreorderable(commercialProduct.getProductControl().isPreOrderable());
				String dateFormat = DATE_FORMAT_COHERENCE;
				if (commercialProduct.getProductControl().getAvailableFrom() != null) {
					merchandisingControl.setAvailableFrom(CommonUtility
							.getDateToString(commercialProduct.getProductControl().getAvailableFrom(), dateFormat));
				}
				merchandisingControl.setBackorderable(commercialProduct.getProductControl().isBackOrderable());
				deviceDetails.setMerchandisingControl(merchandisingControl);

				List<MerchandisingPromotions> listOfMerchandisingPromotion = new ArrayList<>();
				for (String singlePromotion : commercialProduct.getPromoteAs().getPromotionName()) {
					MerchandisingPromotions merchandisingPromotion = new MerchandisingPromotions();
					merchandisingPromotion.setPromotionName(singlePromotion);
					listOfMerchandisingPromotion.add(merchandisingPromotion);
				}
				deviceDetails.setMerchandisingPromotion(listOfMerchandisingPromotion);

				Equipment equipment = new Equipment();
				equipment.setMake(commercialProduct.getEquipment().getMake());
				equipment.setModel(commercialProduct.getEquipment().getModel());
				deviceDetails.setEquipmentDetail(equipment);

				if (commercialProduct.getEquipment() != null) {
					deviceDetails.setProductPageURI(commercialProduct.getEquipment().getMake() + "/"
							+ commercialProduct.getEquipment().getModel());
				}
				// Preparing list for Pricing API
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				if (commercialProduct.getLeadPlanId() != null) {
					bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
					bundleAndHardwareTuple.setHardwareId(deviceSkuId);
					deviceDetails.setLeadPlanId(commercialProduct.getLeadPlanId());
					listOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
				} else {
					String leadPlanId = getLeadPlanIdForDeviceId(deviceSkuId, journeyType);
					bundleAndHardwareTuple.setBundleId(leadPlanId);
					bundleAndHardwareTuple.setHardwareId(deviceSkuId);
					deviceDetails.setLeadPlanId(leadPlanId);
					listOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
				}

				MediaLink mediaLink;
				if (commercialProduct.getListOfimageURLs() != null) {
					for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : commercialProduct
							.getListOfimageURLs()) {
						mediaLink = new MediaLink();
						mediaLink.setId(imageURL.getImageName());
						mediaLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(imageURL.getImageURL());
						listOfmerchandisingMedia.add(mediaLink);
					}
				}
				if (commercialProduct.getListOfmediaURLs() != null) {
					for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : commercialProduct
							.getListOfmediaURLs()) {
						mediaLink = new MediaLink();
						mediaLink.setId(mediaURL.getMediaName());
						mediaLink.setType(STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(mediaURL.getMediaURL());
						listOfmerchandisingMedia.add(mediaLink);
					}
				}
				CommercialBundle commercialBundle = deviceEs.getCommercialBundle(deviceDetails.getLeadPlanId());
				if (commercialBundle != null) {
					listOfmerchandisingMedia.addAll(mediaListForBundle(commercialBundle));
				}
				listOfmerchandisingMedia.addAll(getMediaListForDevice(commercialProduct));

				List<MediaLink> finalListOfMediaLink = new ArrayList<>();
				if (listOfmerchandisingMedia != null && !listOfmerchandisingMedia.isEmpty()) {
					for (MediaLink merchandisingMediaLink : listOfmerchandisingMedia) {
						if (merchandisingMediaLink != null && merchandisingMediaLink.getValue() != null
								&& !"".equals(merchandisingMediaLink.getValue())) {
							finalListOfMediaLink.add(merchandisingMediaLink);
						}
					}
				}

				deviceDetails.setMedia(finalListOfMediaLink);

				listOfDevices.add(deviceDetails);
			}

		}
		// Calling pricing API
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = deviceDao
				.getPriceForBundleAndHardware(listOfBundleAndHardwareTuple, offerCode, journeyType);

		log.info("Setting prices and its corresponding promotions");
		settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, listOfDevices);

		return listOfDevices;
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
		BundleHeader bundleHeaderForDevice = null;
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

				if (listOfBundleHeaderForDevice.isEmpty()) {
					log.error("No Compatible Bundles found for given device Id from bundles api: " + listOfBundles);

				} else {
					Iterator<com.vf.uk.dal.utility.entity.BundleHeader> it = listOfBundleHeaderForDevice.iterator();
					while (it.hasNext()) {
						com.vf.uk.dal.utility.entity.BundleHeader bundleheaderForDevice = it.next();
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

		} catch (Exception e) {
			log.error(" Exception occured when call happen to compatible bundles api : " + e);
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
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_full_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_limited_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("full_duration")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("limited_time")) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							DATE_FORMAT_COHERENCE);
					if (promotionName != null && promotionName.equals(merchandisingPromotion.getTag()) && CommonUtility
							.dateValidationForOffers(startDateTime, endDateTime, DATE_FORMAT_COHERENCE)) {
						listOfMediaLink.addAll(listOfMediaLinkBasedOnMerchandising(merchandisingPromotion));
					}
				}
			}
		}
		return listOfMediaLink;
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
				if (merchandisingPromotion != null
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_full_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("conditional_limited_discount")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("full_duration")
						&& !merchandisingPromotion.getType().equalsIgnoreCase("limited_time")) {
					String startDateTime = CommonUtility.getDateToString(merchandisingPromotion.getStartDateTime(),
							DATE_FORMAT_COHERENCE);
					String endDateTime = CommonUtility.getDateToString(merchandisingPromotion.getEndDateTime(),
							DATE_FORMAT_COHERENCE);
					if (promotionName.equals(merchandisingPromotion.getTag()) && CommonUtility
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
						if (priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross().equalsIgnoreCase(
								priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross())) {
							priceForBundleAndHardware.getBundlePrice().setMonthlyDiscountPrice(null);
							priceForBundleAndHardware.setMonthlyDiscountPrice(null);
						}
						if (priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
								priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
							priceForBundleAndHardware.getHardwarePrice().setOneOffDiscountPrice(null);
							priceForBundleAndHardware.setOneOffDiscountPrice(null);
						}
						deviceDetails.setPriceInfo(priceForBundleAndHardware);
						listOfmerchandisingMedia.addAll(deviceDetails.getMedia());
						priceForBundleAndHardware.getBundlePrice().getMerchandisingPromotions();
						com.vf.uk.dal.device.entity.MerchandisingPromotion listOfHardwareMerch = priceForBundleAndHardware
								.getHardwarePrice().getMerchandisingPromotions();
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
						com.vf.uk.dal.device.entity.MerchandisingPromotion priceBundleMerch = priceForBundleAndHardware
								.getBundlePrice().getMerchandisingPromotions();
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
				}
			}
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
			throw new ApplicationException(ExceptionMessages.INVALID_DEVICE_ID);
		}
		return listOfDevices;
	}

}
