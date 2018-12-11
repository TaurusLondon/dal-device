package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.client.entity.bundle.Allowance;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
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
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.Equipment;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.MerchandisingControl;
import com.vf.uk.dal.device.model.MerchandisingPromotions;
import com.vf.uk.dal.device.model.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.model.MerchandisingPromotionsWrapper;
import com.vf.uk.dal.device.model.MetaData;
import com.vf.uk.dal.device.model.ProductAvailability1;
import com.vf.uk.dal.device.model.Specification;
import com.vf.uk.dal.device.model.SpecificationGroup;
import com.vf.uk.dal.device.model.product.CommercialProduct;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceDetailsMakeAndModelVaiantDaoUtils {

	public static final String STRING_PRICE_ESTABLISHED_LABEL = "merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel";
	public static final String STRING_PRICE_PROMOTION_MEDIA = "merchandisingPromotions.merchandisingPromotion.promotionMedia";
	public static final String DATE_FORMAT_COHERENCE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String PAYG_DEVICE = "Mobile Phones, Data Devices";
	public static final String STRING_PROMOTION_MEDIA = "merchandisingPromotions.merchandisingPromotion.PromotionMedia";
	public static final String STRING_OFFERS_LABEL = "merchandisingPromotions.merchandisingPromotion.label";
	public static final String STRING_OFFERS_DESCRIPTION = "merchandisingPromotions.merchandisingPromotion.description";
	public static final String STRING_FOR_MEDIA_TYPE = "URL";
	public static final String STRING_COLOUR = "Colour";
	public static final String STRING_HEXVALUE = "HexValue";
	public static final String STRING_CAPACITY = "Capacity";
	public static final String STRING_DATA_AlLOWANCE = "DATA";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";

	public DeviceDetailsMakeAndModelVaiantDaoUtils() {
		/**
		 * constructor
		 */
	};

	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;
	
	/**
	 * 
	 * @param cohProduct
	 * @param listOfPriceForBundleAndHardware
	 * @param listOfOfferPacks
	 * @return DeviceDetails
	 */
	public DeviceDetails convertCoherenceDeviceToDeviceDetails(CommercialProduct cohProduct,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			List<BundleAndHardwarePromotions> listOfOfferPacks, String cdnDomain) {
		ProductAvailability1 productAvailability = new ProductAvailability1();

		List<MediaLink> merchandisingMedia = new ArrayList<>();

		List<String> productLines;
		productLines = cohProduct.getProductLines();

		DeviceDetails deviceDetails;

		deviceDetails = new DeviceDetails();
		deviceDetails.setDeviceId(cohProduct.getId());
		deviceDetails.setName(cohProduct.getName());
		deviceDetails.setDescription(cohProduct.getPreDesc());
		deviceDetails.setProductClass(cohProduct.getProductClass());
		deviceDetails.setProductLines(productLines);

		MerchandisingControl merchandisingControl = new MerchandisingControl();
		merchandisingControl.setIsDisplayableECare(cohProduct.getProductControl().isDisplayableinLife());
		merchandisingControl.setIsSellableECare(cohProduct.getProductControl().isSellableinLife());
		merchandisingControl.setIsDisplayableAcq(cohProduct.getProductControl().isDisplayableAcq());
		merchandisingControl.setIsSellableRet(cohProduct.getProductControl().isSellableRet());
		merchandisingControl.setIsDisplayableRet(cohProduct.getProductControl().isDisplayableRet());
		merchandisingControl.setIsSellableAcq(cohProduct.getProductControl().isSellableAcq());
		merchandisingControl.setIsDisplayableSavedBasket(cohProduct.getProductControl().isDisplayableSavedBasket());
		merchandisingControl.setOrder(cohProduct.getOrder().intValue());
		merchandisingControl.setPreorderable(cohProduct.getProductControl().isPreOrderable());
		String dateFormat = DATE_FORMAT_COHERENCE;
		setAvailableFrom(cohProduct, merchandisingControl, dateFormat);
		merchandisingControl.setBackorderable(cohProduct.getProductControl().isBackOrderable());
		deviceDetails.setMerchandisingControl(merchandisingControl);

		List<MerchandisingPromotions> listOfMerchandisingPromotion = new ArrayList<>();
		setListOfMerchandisingPromotion(cohProduct, listOfMerchandisingPromotion);
		deviceDetails.setMerchandisingPromotion(listOfMerchandisingPromotion);

		Equipment equipment = new Equipment();
		equipment.setMake(cohProduct.getEquipment().getMake());
		equipment.setModel(cohProduct.getEquipment().getModel());
		deviceDetails.setEquipmentDetail(equipment);
		setLeadPlanid(cohProduct, deviceDetails);
		setStartEndDate(cohProduct, productAvailability, dateFormat);

		productAvailability.setSalesExpired(cohProduct.getProductAvailability().isSalesExpired());
		deviceDetails.setProductAvailability(productAvailability);
		setMerchandisingMedia(cohProduct, cdnDomain, merchandisingMedia);

		/**
		 * @author manoj.bera I called promotion API
		 */
		if (listOfOfferPacks != null && !listOfOfferPacks.isEmpty()) {

			BundleAndHardwarePromotions promotions = listOfOfferPacks.get(0);
			commonUtility.getNonPricingPromotions(promotions, merchandisingMedia);
		}

		List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups = cohProduct.getSpecificationGroups();
		List<SpecificationGroup> listOfSpecificationGroup = setListOfSpecifications(listOfSpecificationGroups);
		deviceDetails.setSpecificationsGroups(listOfSpecificationGroup);

		// price calculation
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			PriceForBundleAndHardware priceForBundleAndHardware;
			priceForBundleAndHardware = listOfPriceForBundleAndHardware.get(0);
			if (priceForBundleAndHardware.getHardwarePrice() == null) {
				log.error("PRICE API of PriceForBundleAndHardware Exception---------------");
				throw new ApplicationException(ExceptionMessages.PRICING_API_EXCEPTION);
			}
			if (cohProduct.getId().equalsIgnoreCase(priceForBundleAndHardware.getHardwarePrice().getHardwareId())) {
				if (priceForBundleAndHardware.getHardwarePrice() != null
						&& getOnePriceCheckForPriceForBundleAndHarware(priceForBundleAndHardware)
						&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
								priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross())) {

					priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().setGross(null);
					priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().setVat(null);
					priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().setNet(null);
					priceForBundleAndHardware.getOneOffDiscountPrice().setGross(null);
					priceForBundleAndHardware.getOneOffDiscountPrice().setVat(null);
					priceForBundleAndHardware.getOneOffDiscountPrice().setNet(null);
				}
				setMonthlyDiscountPrice(priceForBundleAndHardware);

				deviceDetails.setPriceInfo(priceForBundleAndHardware);

				/**
				 * pricing Merchandising population
				 */
				MerchandisingPromotion merchPromoForHardware = priceForBundleAndHardware.getHardwarePrice()
						.getMerchandisingPromotions();
				// Label
				setMerchandisingMediaForHardware(merchandisingMedia, merchPromoForHardware);

				// Bundle Promotion
				MerchandisingPromotion merchPromoForBundle = priceForBundleAndHardware.getBundlePrice()
						.getMerchandisingPromotions();
				// Label
				setMerchandisingMediaForBundle(merchandisingMedia, merchPromoForBundle);
			}

		}
		deviceDetails.setMedia(merchandisingMedia);
		MetaData metaData;
		metaData = new MetaData();
		metaData.setSeoCanonical(cohProduct.getSeoCanonical());
		metaData.setSeoDescription(cohProduct.getSeoDescription());
		metaData.setSeoIndex(cohProduct.getSeoIndex());
		metaData.setSeoKeyWords(cohProduct.getSeoKeywords());
		deviceDetails.setMetaData(metaData);

		return deviceDetails;
	}

	private void setMonthlyDiscountPrice(PriceForBundleAndHardware priceForBundleAndHardware) {
		if (priceForBundleAndHardware.getBundlePrice() != null
				&& getMonthlyPriceCheckForBundleAndHardware(priceForBundleAndHardware)
				&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross()
						.equalsIgnoreCase(priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross())) {
			priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setGross(null);
			priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setVat(null);
			priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setNet(null);
			priceForBundleAndHardware.getMonthlyDiscountPrice().setGross(null);
			priceForBundleAndHardware.getMonthlyDiscountPrice().setVat(null);
			priceForBundleAndHardware.getMonthlyDiscountPrice().setNet(null);
		}
	}

	private void setMerchandisingMediaForHardware(List<MediaLink> merchandisingMedia,
			MerchandisingPromotion merchPromoForHardware) {
		MediaLink priceMediaLinkLabel = new MediaLink();
		if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
			if (StringUtils.isNotBlank(merchPromoForHardware.getLabel())) {
				priceMediaLinkLabel.setId(merchPromoForHardware.getMpType() + "." + STRING_OFFERS_LABEL);
				priceMediaLinkLabel.setType("TEXT");
				priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
				priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceMediaLinkLabel);
			}
			// Description
			if (StringUtils.isNotBlank(merchPromoForHardware.getDescription())) {
				MediaLink priceMediaLinkDescription = new MediaLink();
				priceMediaLinkDescription.setId(merchPromoForHardware.getMpType() + "." + STRING_OFFERS_DESCRIPTION);
				priceMediaLinkDescription.setType("TEXT");
				priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
				priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceMediaLinkDescription);
			}
			// PriceEstablished
			if (StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel())) {
				MediaLink priceEstablishedMediaLink = new MediaLink();
				priceEstablishedMediaLink
						.setId(merchPromoForHardware.getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
				priceEstablishedMediaLink.setType("TEXT");
				priceEstablishedMediaLink.setValue(merchPromoForHardware.getPriceEstablishedLabel());
				priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceEstablishedMediaLink);
			}
		}
	}

	private void setMerchandisingMediaForBundle(List<MediaLink> merchandisingMedia,
			MerchandisingPromotion merchPromoForBundle) {
		if (merchPromoForBundle != null && merchPromoForBundle.getMpType() != null) {
			if (StringUtils.isNotBlank(merchPromoForBundle.getLabel())) {
				MediaLink priceMediaLinkLabelForBundle = new MediaLink();
				priceMediaLinkLabelForBundle.setId(merchPromoForBundle.getMpType() + "." + STRING_OFFERS_LABEL);
				priceMediaLinkLabelForBundle.setType("TEXT");
				priceMediaLinkLabelForBundle.setValue(merchPromoForBundle.getLabel());
				priceMediaLinkLabelForBundle.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceMediaLinkLabelForBundle);
			}
			// Description
			if (StringUtils.isNotBlank(merchPromoForBundle.getDescription())) {
				MediaLink priceMediaLinkDescriptionForBundle = new MediaLink();
				priceMediaLinkDescriptionForBundle
						.setId(merchPromoForBundle.getMpType() + "." + STRING_OFFERS_DESCRIPTION);
				priceMediaLinkDescriptionForBundle.setType("TEXT");
				priceMediaLinkDescriptionForBundle.setValue(merchPromoForBundle.getDescription());
				priceMediaLinkDescriptionForBundle.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceMediaLinkDescriptionForBundle);
			}
			// PriceEstablished
			if (StringUtils.isNotBlank(merchPromoForBundle.getPriceEstablishedLabel())) {
				MediaLink priceEstablishedMediaLinkForBundle = new MediaLink();
				priceEstablishedMediaLinkForBundle
						.setId(merchPromoForBundle.getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
				priceEstablishedMediaLinkForBundle.setType("TEXT");
				priceEstablishedMediaLinkForBundle.setValue(merchPromoForBundle.getPriceEstablishedLabel());
				priceEstablishedMediaLinkForBundle.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceEstablishedMediaLinkForBundle);
			}
		}
	}

	private List<SpecificationGroup> setListOfSpecifications(
			List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups) {
		List<Specification> listOfSpecification;
		List<SpecificationGroup> listOfSpecificationGroup = new ArrayList<>();
		SpecificationGroup specificationGroups;
		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Group specificationGroup : listOfSpecificationGroups) {
				specificationGroups = new SpecificationGroup();
				specificationGroups.setGroupName(specificationGroup.getGroupName());
				specificationGroups.setPriority(specificationGroup.getPriority().intValue());
				specificationGroups.setComparable(specificationGroup.isComparable());
				listOfSpecification = new ArrayList<>();
				List<com.vf.uk.dal.device.model.product.Specification> listOfSpec = specificationGroup
						.getSpecifications();
				setSpecificationGroups(listOfSpecification, specificationGroups, listOfSpec);
				listOfSpecificationGroup.add(specificationGroups);
			}
		}
		return listOfSpecificationGroup;
	}

	private void setSpecificationGroups(List<Specification> listOfSpecification,
			SpecificationGroup specificationGroups, List<com.vf.uk.dal.device.model.product.Specification> listOfSpec) {
		Specification specification;
		if (listOfSpec != null && !listOfSpec.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Specification spec : listOfSpec) {
				specification = new Specification();
				specification.setName(spec.getName());
				specification.setValue(spec.getValue());
				if (spec.getPriority() == null) {
					specification.setPriority(null);
				} else {
					specification.setPriority(spec.getPriority().intValue());
				}
				specification.setComparable(spec.getComparable());
				specification.setIsKey(spec.getIsKey());
				specification.setValueType(spec.getValueType());
				specification.setValueUOM(spec.getValueUOM());
				specification.setDescription(spec.getDescription());
				specification.setFootNote(spec.getFootNote());
				specification.setHideInList(spec.getHideInList());
				listOfSpecification.add(specification);
			}
			specificationGroups.setSpecifications(listOfSpecification);
		}
	}

	private void setMerchandisingMedia(CommercialProduct cohProduct, String cdnDomain,
			List<MediaLink> merchandisingMedia) {
		MediaLink mediaLink;
		if (cohProduct.getListOfimageURLs() != null) {
			for (com.vf.uk.dal.device.model.product.ImageURL imageURL : cohProduct.getListOfimageURLs()) {
				if (StringUtils.isNotBlank(imageURL.getImageURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(commonUtility.getImageMediaUrl(cdnDomain, imageURL.getImageURL()));
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (cohProduct.getListOfmediaURLs() != null) {
			for (com.vf.uk.dal.device.model.product.MediaURL mediaURL : cohProduct.getListOfmediaURLs()) {
				if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(mediaURL.getMediaName());
					mediaLink.setType(STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(commonUtility.getImageMediaUrl(cdnDomain, mediaURL.getMediaURL()));
					merchandisingMedia.add(mediaLink);
				}
			}
		}
	}

	private void setStartEndDate(CommercialProduct cohProduct, ProductAvailability1 productAvailability,
			String dateFormat) {
		if (cohProduct.getProductAvailability().getEnd() != null) {
			productAvailability.setEndDate(
					commonUtility.getDateToString(cohProduct.getProductAvailability().getEnd(), dateFormat));
		}
		if (cohProduct.getProductAvailability().getStart() != null) {
			productAvailability.setStartDate(
					commonUtility.getDateToString(cohProduct.getProductAvailability().getStart(), dateFormat));
		}
	}

	private void setLeadPlanid(CommercialProduct cohProduct, DeviceDetails deviceDetails) {
		if (cohProduct.getProductLines() != null && !cohProduct.getProductLines().isEmpty()
				&& !cohProduct.getProductLines().contains(PAYG_DEVICE)) {
			deviceDetails.setLeadPlanId(cohProduct.getLeadPlanId());
		} else {
			deviceDetails.setLeadPlanId(null);
		}
	}

	private void setListOfMerchandisingPromotion(CommercialProduct cohProduct,
			List<MerchandisingPromotions> listOfMerchandisingPromotion) {
		if (cohProduct.getPromoteAs() != null && (cohProduct.getPromoteAs().getPromotionName() != null
				&& !cohProduct.getPromoteAs().getPromotionName().isEmpty())) {
			for (String singlePromotion : cohProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotions merchandisingPromotion = new MerchandisingPromotions();
				merchandisingPromotion.setPromotionName(singlePromotion);
				listOfMerchandisingPromotion.add(merchandisingPromotion);
			}
		}
	}

	private void setAvailableFrom(CommercialProduct cohProduct, MerchandisingControl merchandisingControl,
			String dateFormat) {
		if (cohProduct.getProductControl().getAvailableFrom() != null) {
			merchandisingControl.setAvailableFrom(
					commonUtility.getDateToString(cohProduct.getProductControl().getAvailableFrom(), dateFormat));
		}
	}

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public boolean getMonthlyPriceCheckForBundleAndHardware(
			PriceForBundleAndHardware priceForBundleAndHardware) {
		return priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
				&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null
				&& priceForBundleAndHardware.getMonthlyPrice() != null
				&& priceForBundleAndHardware.getMonthlyPrice().getGross() != null;
	}

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public boolean getOnePriceCheckForPriceForBundleAndHarware(
			PriceForBundleAndHardware priceForBundleAndHardware) {
		boolean result = priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
				&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null
				&& priceForBundleAndHardware.getOneOffDiscountPrice() != null;
		return result && priceForBundleAndHardware.getOneOffDiscountPrice().getGross() != null
				&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross() != null
				&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross() != null;
	}

	/**
	 * 
	 * @param memberPriority
	 * @param commercialProduct
	 * @param comBundle
	 * @param priceforBundleAndHardware
	 * @param listOfOfferPacks
	 * @param groupType
	 * @param isConditionalAcceptJourney
	 * @param fromPricingMap
	 * @return DeviceSummary
	 */
	public DeviceSummary convertCoherenceDeviceToDeviceTile(Long memberPriority,
			CommercialProduct commercialProduct, CommercialBundle comBundle,
			PriceForBundleAndHardware priceforBundleAndHardware, List<BundleAndHardwarePromotions> listOfOfferPacks,
			String groupType, boolean isConditionalAcceptJourney, Map<String, Boolean> fromPricingMap,
			String cdnDomain) {

		DeviceSummary deviceSummary;
		deviceSummary = new DeviceSummary();

		HardwarePrice hardwarePrice;
		Price price;
		PriceForBundleAndHardware priceInfo;
		deviceSummary.setDeviceId(commercialProduct.getId());
		deviceSummary.setDisplayName(commercialProduct.getDisplayName());
		setPreorderableAndAvailableFrom(commercialProduct, deviceSummary);
		setPriorityAndFromPricing(memberPriority, commercialProduct, fromPricingMap, deviceSummary);
		deviceSummary.setDisplayDescription(commercialProduct.getPreDesc());

		List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups = commercialProduct
				.getSpecificationGroups();
		setMemoryAndColourHex(deviceSummary, listOfSpecificationGroups);
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		commonUtility.getImageMediaLink(commercialProduct, merchandisingMedia, cdnDomain);

		/**
		 * @author manoj.bera
		 * @Sprint 6.4
		 */
		setMerchandisingMediaForPromotions(listOfOfferPacks, merchandisingMedia);

		// MediaLink for PricePromotions
		if (priceforBundleAndHardware != null) {

			// Hardware Promotion
			MerchandisingPromotion merchPromoForHardware = priceforBundleAndHardware.getHardwarePrice()
					.getMerchandisingPromotions();
			// Label
			setMerchandisingMediaForPromoForHardware(merchandisingMedia, merchPromoForHardware);

			// Bundle Promotion
			MerchandisingPromotion merchPromoForBundle = priceforBundleAndHardware.getBundlePrice()
					.getMerchandisingPromotions();
			// Label
			setMerchansidingMediaForPromoBundel(merchandisingMedia, merchPromoForBundle);
		}
		deviceSummary.setMerchandisingMedia(merchandisingMedia);
		com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion = null;
		if (listOfOfferPacks != null) {
			bundleAndHardwarePromotion = CollectionUtils.isNotEmpty(listOfOfferPacks) ? listOfOfferPacks.get(0) : null;
		}
		deviceSummary.setPromotionsPackage(
				assembleMechandisingPromotionsPackageGeneric(bundleAndHardwarePromotion, priceforBundleAndHardware));

		setUomAndValue(comBundle, deviceSummary);

		deviceSummary.setProductGroupURI(
				commercialProduct.getEquipment().getMake() + "/" + commercialProduct.getEquipment().getModel());

		// Price calculation

		if (groupType != null && groupType.equals(STRING_DEVICE_PAYG)) {
			price = new Price();
			price.setGross(commercialProduct.getPriceDetail().getPriceGross().toString());
			price.setNet(commercialProduct.getPriceDetail().getPriceNet().toString());
			price.setVat(commercialProduct.getPriceDetail().getPriceVAT().toString());
			hardwarePrice = new HardwarePrice();
			hardwarePrice.setOneOffPrice(price);
			hardwarePrice.setHardwareId(deviceSummary.getDeviceId());
			priceInfo = new PriceForBundleAndHardware();
			priceInfo.setHardwarePrice(hardwarePrice);
			deviceSummary.setPriceInfo(priceInfo);
		} else {
			PriceForBundleAndHardware priceForBundleAndHardware = deviceTilesDaoUtils.getBundleAndHardwarePrice(
					priceforBundleAndHardware, commercialProduct.getId(), isConditionalAcceptJourney, comBundle);
			if (null == priceForBundleAndHardware || null == priceForBundleAndHardware.getBundlePrice()
					|| null == priceForBundleAndHardware.getBundlePrice().getMonthlyPrice()) {
				return null;
			} else {
				deviceSummary.setPriceInfo(priceForBundleAndHardware);
			}

		}
		return deviceSummary;
	}

	private void setUomAndValue(CommercialBundle comBundle, DeviceSummary deviceSummary) {
		if (comBundle != null) {
			deviceSummary.setLeadPlanId(comBundle.getId());
			deviceSummary.setBundleType(comBundle.getDisplayGroup());
			deviceSummary.setLeadPlanDisplayName(comBundle.getDisplayName());
			if (comBundle.getAllowances() != null && !comBundle.getAllowances().isEmpty()) {
				for (Allowance bundleAllowance : comBundle.getAllowances()) {
					if (StringUtils.containsIgnoreCase(bundleAllowance.getType(), STRING_DATA_AlLOWANCE)) {
						deviceSummary.setUom(bundleAllowance.getUom());
						deviceSummary.setUomValue(bundleAllowance.getValue());
					}
				}

			}
		}
	}

	private void setMerchansidingMediaForPromoBundel(List<MediaLink> merchandisingMedia,
			MerchandisingPromotion merchPromoForBundle) {
		if (merchPromoForBundle != null && merchPromoForBundle.getMpType() != null) {
			if (StringUtils.isNotBlank(merchPromoForBundle.getLabel())) {
				MediaLink priceMediaLinkLabelForBundle = new MediaLink();
				priceMediaLinkLabelForBundle.setId(merchPromoForBundle.getMpType() + "." + STRING_OFFERS_LABEL);
				priceMediaLinkLabelForBundle.setType("TEXT");
				priceMediaLinkLabelForBundle.setValue(merchPromoForBundle.getLabel());
				priceMediaLinkLabelForBundle.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceMediaLinkLabelForBundle);
			}
			// Description
			if (StringUtils.isNotBlank(merchPromoForBundle.getDescription())) {
				MediaLink priceMediaLinkDescriptionForBundle = new MediaLink();
				priceMediaLinkDescriptionForBundle
						.setId(merchPromoForBundle.getMpType() + "." + STRING_OFFERS_DESCRIPTION);
				priceMediaLinkDescriptionForBundle.setType("TEXT");
				priceMediaLinkDescriptionForBundle.setValue(merchPromoForBundle.getDescription());
				priceMediaLinkDescriptionForBundle.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceMediaLinkDescriptionForBundle);
			}
			// PriceEstablished
			if (StringUtils.isNotBlank(merchPromoForBundle.getPriceEstablishedLabel())) {
				MediaLink priceEstablishedMediaLinkForBundle = new MediaLink();
				priceEstablishedMediaLinkForBundle
						.setId(merchPromoForBundle.getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
				priceEstablishedMediaLinkForBundle.setType("TEXT");
				priceEstablishedMediaLinkForBundle.setValue(merchPromoForBundle.getPriceEstablishedLabel());
				priceEstablishedMediaLinkForBundle.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceEstablishedMediaLinkForBundle);
			}
			if (StringUtils.isNotBlank(merchPromoForBundle.getPromotionMedia())) {
				MediaLink priceEstablishedMediaLink = new MediaLink();
				priceEstablishedMediaLink.setId(merchPromoForBundle.getMpType() + "." + STRING_PROMOTION_MEDIA);
				priceEstablishedMediaLink.setType("URL");
				priceEstablishedMediaLink.setValue(merchPromoForBundle.getPromotionMedia());
				priceEstablishedMediaLink.setPriority(merchPromoForBundle.getPriority());
				merchandisingMedia.add(priceEstablishedMediaLink);
			}
		}
	}

	private void setMerchandisingMediaForPromoForHardware(List<MediaLink> merchandisingMedia,
			MerchandisingPromotion merchPromoForHardware) {
		MediaLink priceMediaLinkLabel = new MediaLink();
		if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
			if (StringUtils.isNotBlank(merchPromoForHardware.getLabel())) {
				priceMediaLinkLabel.setId(merchPromoForHardware.getMpType() + "." + STRING_OFFERS_LABEL);
				priceMediaLinkLabel.setType("TEXT");
				priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
				priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceMediaLinkLabel);
			}
			// Description
			if (StringUtils.isNotBlank(merchPromoForHardware.getDescription())) {
				MediaLink priceMediaLinkDescription = new MediaLink();
				priceMediaLinkDescription.setId(merchPromoForHardware.getMpType() + "." + STRING_OFFERS_DESCRIPTION);
				priceMediaLinkDescription.setType("TEXT");
				priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
				priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceMediaLinkDescription);
			}
			// PriceEstablished
			if (StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel())) {
				MediaLink priceEstablishedMediaLink = new MediaLink();
				priceEstablishedMediaLink
						.setId(merchPromoForHardware.getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
				priceEstablishedMediaLink.setType("TEXT");
				priceEstablishedMediaLink.setValue(merchPromoForHardware.getPriceEstablishedLabel());
				priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceEstablishedMediaLink);
			}
			if (StringUtils.isNotBlank(merchPromoForHardware.getPromotionMedia())) {
				MediaLink priceEstablishedMediaLink = new MediaLink();
				priceEstablishedMediaLink.setId(merchPromoForHardware.getMpType() + "." + STRING_PROMOTION_MEDIA);
				priceEstablishedMediaLink.setType("URL");
				priceEstablishedMediaLink.setValue(merchPromoForHardware.getPromotionMedia());
				priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
				merchandisingMedia.add(priceEstablishedMediaLink);
			}
		}
	}

	private void setMerchandisingMediaForPromotions(List<BundleAndHardwarePromotions> listOfOfferPacks,
			List<MediaLink> merchandisingMedia) {
		if (listOfOfferPacks != null && !listOfOfferPacks.isEmpty()) {

			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = listOfOfferPacks
					.get(0).getEntertainmentPacks();
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = listOfOfferPacks.get(0)
					.getDataAllowances();
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = listOfOfferPacks
					.get(0).getPlanCouplingPromotions();
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash = listOfOfferPacks.get(0)
					.getSashBannerForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = listOfOfferPacks.get(0)
					.getSecureNet();
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = listOfOfferPacks.get(0)
					.getSashBannerForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = listOfOfferPacks.get(0)
					.getFreeExtras();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories = listOfOfferPacks.get(0)
					.getFreeAccessory();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans = listOfOfferPacks.get(0)
					.getFreeExtrasForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans = listOfOfferPacks.get(0)
					.getFreeAccForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares = listOfOfferPacks.get(0)
					.getFreeExtrasForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares = listOfOfferPacks.get(0)
					.getFreeAccForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional = listOfOfferPacks.get(0)
					.getConditionalSashBanner();
			merchandisingMedia.addAll(commonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances,
					planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories,
					freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares,
					sashBundleConditional));
		}
	}

	private void setMemoryAndColourHex(DeviceSummary deviceSummary,
			List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups) {
		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Group specificationGroup : listOfSpecificationGroups) {
				if (specificationGroup.getGroupName().equalsIgnoreCase(STRING_COLOUR)) {
					List<com.vf.uk.dal.device.model.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					setColourNameAndHex(deviceSummary, listOfSpec);
				}
				if (specificationGroup.getGroupName().equalsIgnoreCase(STRING_CAPACITY)) {
					List<com.vf.uk.dal.device.model.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					setMemory(deviceSummary, listOfSpec);
				}

			}
		}
	}

	private void setMemory(DeviceSummary deviceSummary,
			List<com.vf.uk.dal.device.model.product.Specification> listOfSpec) {
		if (listOfSpec != null && !listOfSpec.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Specification spec : listOfSpec) {
				if (spec.getName().equalsIgnoreCase(STRING_CAPACITY)) {
					deviceSummary.setMemory(spec.getValue() + spec.getValueUOM());
				}
			}
		}
	}

	private void setColourNameAndHex(DeviceSummary deviceSummary,
			List<com.vf.uk.dal.device.model.product.Specification> listOfSpec) {
		if (listOfSpec != null && !listOfSpec.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Specification spec : listOfSpec) {
				if (spec.getName().equalsIgnoreCase(STRING_COLOUR)) {
					deviceSummary.setColourName(spec.getValue());
				}
				if (spec.getName().equalsIgnoreCase(STRING_HEXVALUE)) {
					deviceSummary.setColourHex(spec.getValue());
				}
			}
		}
	}

	private void setPriorityAndFromPricing(Long memberPriority, CommercialProduct commercialProduct,
			Map<String, Boolean> fromPricingMap, DeviceSummary deviceSummary) {
		if (memberPriority != null) {
			deviceSummary.setPriority(String.valueOf(memberPriority));
		}
		if (fromPricingMap != null) {
			deviceSummary.setFromPricing(fromPricingMap.get(commercialProduct.getId()));
		}
	}

	private void setPreorderableAndAvailableFrom(CommercialProduct commercialProduct,
			DeviceSummary deviceSummary) {
		if (commercialProduct.getProductControl() != null && commercialProduct.getProductControl().isPreOrderable()) {
			String startDateTime;
			startDateTime = commonUtility.getDateToString(commercialProduct.getProductControl().getAvailableFrom(),
					DATE_FORMAT_COHERENCE);
			if (startDateTime != null && commonUtility.dateValidationForProduct(startDateTime, DATE_FORMAT_COHERENCE)) {
				deviceSummary.setPreOrderable(true);
				deviceSummary.setAvailableFrom(startDateTime);
			} else {
				deviceSummary.setPreOrderable(false);
			}
		} else {
			deviceSummary.setPreOrderable(false);
		}
	}

	/**
	 * @author krishna.reddy @sprint-7.1
	 * @param memberPriority
	 * @param commercialProduct
	 * @param priceforBundleAndHardware
	 * @param groupType
	 * @return DeviceSummary
	 */
	public DeviceSummary convertCoherenceDeviceToDeviceTilePAYG(Long memberPriority,
			CommercialProduct commercialProduct, PriceForBundleAndHardware priceforBundleAndHardware,
			BundleAndHardwarePromotions promotions, String cdnDomain) {

		DeviceSummary deviceSummary;
		deviceSummary = new DeviceSummary();

		deviceSummary.setDeviceId(commercialProduct.getId());
		deviceSummary.setDisplayName(commercialProduct.getDisplayName());
		setPreorderableAndAvailableFrom(commercialProduct, deviceSummary);

		if (memberPriority != null) {
			deviceSummary.setPriority(String.valueOf(memberPriority));
		}
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		if (promotions != null) {
			commonUtility.getNonPricingPromotions(promotions, merchandisingMedia);
		}

		deviceSummary.setFromPricing(null);

		deviceSummary.setDisplayDescription(commercialProduct.getPreDesc());

		List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups = commercialProduct
				.getSpecificationGroups();

		setMemoryAndColourHex(deviceSummary, listOfSpecificationGroups);

		commonUtility.getImageMediaLink(commercialProduct, merchandisingMedia, cdnDomain);

		// MediaLink for PricePromotions
		if (priceforBundleAndHardware != null) {
			deviceSummary.setPriceInfo(deviceTilesDaoUtils.getCalculatedPrice(priceforBundleAndHardware));
			// Hardware Promotion
			if (priceforBundleAndHardware.getHardwarePrice() != null) {

				MerchandisingPromotion merchPromoForHardware = priceforBundleAndHardware.getHardwarePrice()
						.getMerchandisingPromotions();
				setMerchandisingMediaForHardware(merchandisingMedia, merchPromoForHardware);
			}
		}

		deviceSummary.setMerchandisingMedia(merchandisingMedia);

		deviceSummary.setPromotionsPackage(
				assembleMechandisingPromotionsPackageGeneric(promotions, priceforBundleAndHardware));
		deviceSummary.setLeadPlanId(null);
		// Bundle Type Mapping
		deviceSummary.setBundleType(null);
		deviceSummary.setLeadPlanDisplayName(null);

		deviceSummary.setUom(null);
		deviceSummary.setUomValue(null);

		deviceSummary.setProductGroupURI(
				commercialProduct.getEquipment().getMake() + "/" + commercialProduct.getEquipment().getModel());
		deviceSummary.setIsCompatible(null);

		return deviceSummary;
	}

	/**
	 * Assemble mechandising promotions package generic.
	 *
	 * @param bundleAndHardwarePromotion
	 *            the bundle and hardware promotion
	 * @param priceForBundleAndHardware
	 *            the price for bundle and hardware
	 * @return the merchandising promotions package
	 */
	public MerchandisingPromotionsPackage assembleMechandisingPromotionsPackageGeneric(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			PriceForBundleAndHardware priceForBundleAndHardware) {
		MerchandisingPromotionsPackage promotionsPackage = null;
		promotionsPackage = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper bundlePromotions = new MerchandisingPromotionsWrapper();
		MerchandisingPromotionsWrapper hardwarePromotions = new MerchandisingPromotionsWrapper();
		if (bundleAndHardwarePromotion != null) {

			setDataPromotion(bundleAndHardwarePromotion, bundlePromotions);
			setSecureNetPromotion(bundleAndHardwarePromotion, bundlePromotions);
			setSashBannerPromotion(bundleAndHardwarePromotion, bundlePromotions);
			setConditionalSashBannerPromotion(bundleAndHardwarePromotion, bundlePromotions);
			setEntertainmentPackPromotion(bundleAndHardwarePromotion, bundlePromotions);

			setFreeExtraPromotion(bundleAndHardwarePromotion, bundlePromotions);

			setFreeAccessoryPromotion(bundleAndHardwarePromotion, bundlePromotions);

			setSashBannerForHardware(bundleAndHardwarePromotion, hardwarePromotions);

			setFreeAccessoryPromotionForHardware(bundleAndHardwarePromotion, hardwarePromotions);

			setFreeExtraPromotionForHardware(bundleAndHardwarePromotion, hardwarePromotions);
			promotionsPackage.setPlanId(bundleAndHardwarePromotion.getBundleId());
			promotionsPackage.setHardwareId(bundleAndHardwarePromotion.getHardwareId());
		}
		if (priceForBundleAndHardware != null && priceForBundleAndHardware.getBundlePrice() != null
				&& priceForBundleAndHardware.getBundlePrice().getMerchandisingPromotions() != null) {
			bundlePromotions.setPricePromotion(priceForBundleAndHardware.getBundlePrice().getMerchandisingPromotions());
		}

		if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null
				&& priceForBundleAndHardware.getHardwarePrice().getMerchandisingPromotions() != null) {
			hardwarePromotions
					.setPricePromotion(priceForBundleAndHardware.getHardwarePrice().getMerchandisingPromotions());
		}
		promotionsPackage.setBundlePromotions(bundlePromotions);
		promotionsPackage.setHardwarePromotions(hardwarePromotions);
		return promotionsPackage;
	}

	private void setFreeExtraPromotionForHardware(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper hardwarePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getFreeExtrasForHardware())) {
			/* Assembly of freeExtrasForHardware */
			MerchandisingPromotion freeExtraPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForHardware = bundleAndHardwarePromotion
					.getFreeExtrasForHardware().get(0);
			freeExtraPromotion.setTag(freeExtrasForHardware.getTag());
			freeExtraPromotion.setDescription(freeExtrasForHardware.getDescription());
			freeExtraPromotion.setLabel(freeExtrasForHardware.getLabel());
			freeExtraPromotion.setMpType(freeExtrasForHardware.getType());
			freeExtraPromotion.setPackageType(freeExtrasForHardware.getPackageType());
			if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
				freeExtraPromotion.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
			}
			if (StringUtils.isNotBlank(freeExtrasForHardware.getPromotionMedia())) {
				freeExtraPromotion.setPromotionMedia(freeExtrasForHardware.getPromotionMedia());
			}
			hardwarePromotions.setFreeExtraPromotion(freeExtraPromotion);
		}
	}

	private void setFreeAccessoryPromotionForHardware(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper hardwarePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getFreeAccForHardware())) {
			/* Assembly of freeAccForHardware */
			MerchandisingPromotion freeAccessoryPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForHardware = bundleAndHardwarePromotion
					.getFreeAccForHardware().get(0);
			freeAccessoryPromotion.setTag(freeAccForHardware.getTag());
			freeAccessoryPromotion.setDescription(freeAccForHardware.getDescription());
			freeAccessoryPromotion.setLabel(freeAccForHardware.getLabel());
			freeAccessoryPromotion.setMpType(freeAccForHardware.getType());
			freeAccessoryPromotion.setPackageType(freeAccForHardware.getPackageType());
			if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
				freeAccessoryPromotion.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
			}
			if (StringUtils.isNotBlank(freeAccForHardware.getPromotionMedia())) {
				freeAccessoryPromotion.setPromotionMedia(freeAccForHardware.getPromotionMedia());
			}
			hardwarePromotions.setFreeAccessoryPromotion(freeAccessoryPromotion);
		}
	}

	private void setSashBannerForHardware(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper hardwarePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getSashBannerForHardware())) {
			/* Assembly of sashBannerForHardware */
			MerchandisingPromotion sashBannerPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForHardwareSash sashBannerForHardware = bundleAndHardwarePromotion
					.getSashBannerForHardware().get(0);
			sashBannerPromotion.setTag(sashBannerForHardware.getTag());
			sashBannerPromotion.setDescription(sashBannerForHardware.getDescription());
			sashBannerPromotion.setLabel(sashBannerForHardware.getLabel());
			sashBannerPromotion.setMpType(sashBannerForHardware.getType());
			sashBannerPromotion.setPackageType(sashBannerForHardware.getPackageType());
			if (StringUtils.isNotBlank(sashBannerForHardware.getPriority())) {
				sashBannerPromotion.setPriority(Integer.valueOf(sashBannerForHardware.getPriority()));
			}
			if (StringUtils.isNotBlank(sashBannerForHardware.getPromotionMedia())) {
				sashBannerPromotion.setPromotionMedia(sashBannerForHardware.getPromotionMedia());
			}
			hardwarePromotions.setSashBannerPromotion(sashBannerPromotion);
		}
	}

	private void setFreeAccessoryPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getFreeAccForPlan())) {
			/* Assembly of freeAccessoryPromotion */
			MerchandisingPromotion freeAccessoryPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForPlan = bundleAndHardwarePromotion
					.getFreeAccForPlan().get(0);
			freeAccessoryPromotion.setTag(freeAccForPlan.getTag());
			freeAccessoryPromotion.setDescription(freeAccForPlan.getDescription());
			freeAccessoryPromotion.setLabel(freeAccForPlan.getLabel());
			freeAccessoryPromotion.setMpType(freeAccForPlan.getType());
			freeAccessoryPromotion.setPackageType(freeAccForPlan.getPackageType());
			if (StringUtils.isNotBlank(freeAccForPlan.getPriority())) {
				freeAccessoryPromotion.setPriority(Integer.valueOf(freeAccForPlan.getPriority()));
			}
			if (StringUtils.isNotBlank(freeAccForPlan.getPromotionMedia())) {
				freeAccessoryPromotion.setPromotionMedia(freeAccForPlan.getPromotionMedia());
			}
			bundlePromotions.setFreeAccessoryPromotion(freeAccessoryPromotion);
		}
	}

	private void setFreeExtraPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getFreeExtrasForPlan())) {
			/** Assembly of FreeExtrasForPlan */
			MerchandisingPromotion freeExtraPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForPlan = bundleAndHardwarePromotion
					.getFreeExtrasForPlan().get(0);
			freeExtraPromotion.setTag(freeExtrasForPlan.getTag());
			freeExtraPromotion.setDescription(freeExtrasForPlan.getDescription());
			freeExtraPromotion.setLabel(freeExtrasForPlan.getLabel());
			freeExtraPromotion.setMpType(freeExtrasForPlan.getType());
			freeExtraPromotion.setPackageType(freeExtrasForPlan.getPackageType());
			if (StringUtils.isNotBlank(freeExtrasForPlan.getPriority())) {
				freeExtraPromotion.setPriority(Integer.valueOf(freeExtrasForPlan.getPriority()));
			}
			if (StringUtils.isNotBlank(freeExtrasForPlan.getPromotionMedia())) {
				freeExtraPromotion.setPromotionMedia(freeExtrasForPlan.getPromotionMedia());
			}
			bundlePromotions.setFreeExtraPromotion(freeExtraPromotion);
		}
	}

	private void setEntertainmentPackPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getEntertainmentPacks())) {
			/** Assembly of entertainmentPackPromotion */
			MerchandisingPromotion entertainmentPackPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainmentPacks = bundleAndHardwarePromotion
					.getEntertainmentPacks().get(0);
			entertainmentPackPromotion.setTag(entertainmentPacks.getTag());
			entertainmentPackPromotion.setDescription(entertainmentPacks.getDescription());
			entertainmentPackPromotion.setLabel(entertainmentPacks.getLabel());
			entertainmentPackPromotion.setMpType(entertainmentPacks.getType());
			entertainmentPackPromotion.setPackageType(entertainmentPacks.getPackageType());
			entertainmentPackPromotion.setPromotionMedia(entertainmentPacks.getPromotionMedia());
			if (StringUtils.isNotBlank(entertainmentPacks.getPriority())) {
				entertainmentPackPromotion.setPriority(Integer.valueOf(entertainmentPacks.getPriority()));
			}
			bundlePromotions.setEntertainmentPackPromotion(entertainmentPackPromotion);
		}
	}

	private void setConditionalSashBannerPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getConditionalSashBanner())) {
			/** Assembly of sashBannerPromotion */
			MerchandisingPromotion sashBannerCondition = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareSash sashBannerForConditional = bundleAndHardwarePromotion
					.getConditionalSashBanner().get(0);
			sashBannerCondition.setTag(sashBannerForConditional.getTag());
			sashBannerCondition.setDescription(sashBannerForConditional.getDescription());
			sashBannerCondition.setLabel(sashBannerForConditional.getLabel());
			sashBannerCondition.setMpType(sashBannerForConditional.getType());
			sashBannerCondition.setPackageType(sashBannerForConditional.getPackageType());
			if (StringUtils.isNotBlank(sashBannerForConditional.getPriority())) {
				sashBannerCondition.setPriority(Integer.valueOf(sashBannerForConditional.getPriority()));
			}
			if (StringUtils.isNotBlank(sashBannerForConditional.getPromotionMedia())) {
				sashBannerCondition.setPromotionMedia(sashBannerForConditional.getPromotionMedia());
			}
			bundlePromotions.setConditionalSashBannerPromotion(sashBannerCondition);
		}
	}

	private void setSashBannerPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getSashBannerForPlan())) {
			/** Assembly of sashBannerPromotion */
			MerchandisingPromotion sashBannerPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareSash sashBannerForPlan = bundleAndHardwarePromotion
					.getSashBannerForPlan().get(0);
			sashBannerPromotion.setTag(sashBannerForPlan.getTag());
			sashBannerPromotion.setDescription(sashBannerForPlan.getDescription());
			sashBannerPromotion.setLabel(sashBannerForPlan.getLabel());
			sashBannerPromotion.setMpType(sashBannerForPlan.getType());
			sashBannerPromotion.setPackageType(sashBannerForPlan.getPackageType());
			if (StringUtils.isNotBlank(sashBannerForPlan.getPriority())) {
				sashBannerPromotion.setPriority(Integer.valueOf(sashBannerForPlan.getPriority()));
			}
			if (StringUtils.isNotBlank(sashBannerForPlan.getPromotionMedia())) {
				sashBannerPromotion.setPromotionMedia(sashBannerForPlan.getPromotionMedia());
			}
			bundlePromotions.setSashBannerPromotion(sashBannerPromotion);
		}
	}

	private void setSecureNetPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getSecureNet())) {
			/** Assembly of secureNetPromotion */
			MerchandisingPromotion secureNetPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareSecureNet secureNet = bundleAndHardwarePromotion
					.getSecureNet().get(0);
			secureNetPromotion.setTag(secureNet.getTag());
			secureNetPromotion.setDescription(secureNet.getDescription());
			secureNetPromotion.setLabel(secureNet.getLabel());
			secureNetPromotion.setMpType(secureNet.getType());
			secureNetPromotion.setPackageType(secureNet.getPackageType());
			if (StringUtils.isNotBlank(secureNet.getPriority())) {
				secureNetPromotion.setPriority(Integer.valueOf(secureNet.getPriority()));
			}
			if (StringUtils.isNotBlank(secureNet.getPromotionMedia())) {
				secureNetPromotion.setPromotionMedia(secureNet.getPromotionMedia());
			}
			bundlePromotions.setSecureNetPromotion(secureNetPromotion);
		}
	}

	private void setDataPromotion(
			com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			MerchandisingPromotionsWrapper bundlePromotions) {
		if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getDataAllowances())) {

			/** Assembly of dataPromotions */
			MerchandisingPromotion dataPromotion = new MerchandisingPromotion();
			CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance = bundleAndHardwarePromotion
					.getDataAllowances().get(0);
			dataPromotion.setTag(dataAllowance.getTag());
			dataPromotion.setDescription(dataAllowance.getDescription());
			dataPromotion.setLabel(dataAllowance.getLabel());
			dataPromotion.setMpType(dataAllowance.getType());
			dataPromotion.setPackageType(dataAllowance.getPackageType());
			if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
				dataPromotion.setPriority(Integer.valueOf(dataAllowance.getPriority()));
			}
			if (StringUtils.isNotBlank(dataAllowance.getPromotionMedia())) {
				dataPromotion.setPromotionMedia(dataAllowance.getPromotionMedia());
			}
			bundlePromotions.setDataPromotion(dataPromotion);
		}
	}
}
