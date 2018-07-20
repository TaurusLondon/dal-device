package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.device.datamodel.bundle.Allowance;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.Equipment;
import com.vf.uk.dal.device.entity.HardwarePrice;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.MerchandisingControl;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.MerchandisingPromotions;
import com.vf.uk.dal.device.entity.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.entity.MerchandisingPromotionsWrapper;
import com.vf.uk.dal.device.entity.MetaData;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductAvailability1;
import com.vf.uk.dal.device.entity.Specification;
import com.vf.uk.dal.device.entity.SpecificationGroup;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareAccessory;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareDataAllowances;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareExtras;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSash;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSecureNet;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForHardwareSash;

public class DeviceDetailsMakeAndModelVaiantDaoUtils {

	private DeviceDetailsMakeAndModelVaiantDaoUtils() {
	};

	/**
	 * 
	 * @param cohProduct
	 * @param listOfPriceForBundleAndHardware
	 * @param listOfOfferPacks
	 * @return DeviceDetails
	 */
	public static DeviceDetails convertCoherenceDeviceToDeviceDetails(CommercialProduct cohProduct,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			List<BundleAndHardwarePromotions> listOfOfferPacks) {
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
		String dateFormat = Constants.DATE_FORMAT_COHERENCE;
		if (cohProduct.getProductControl().getAvailableFrom() != null) {
			merchandisingControl.setAvailableFrom(
					CommonUtility.getDateToString(cohProduct.getProductControl().getAvailableFrom(), dateFormat));
		}
		merchandisingControl.setBackorderable(cohProduct.getProductControl().isBackOrderable());
		deviceDetails.setMerchandisingControl(merchandisingControl);

		List<MerchandisingPromotions> listOfMerchandisingPromotion = new ArrayList<>();
		if (cohProduct.getPromoteAs() != null && (cohProduct.getPromoteAs().getPromotionName() != null
				&& !cohProduct.getPromoteAs().getPromotionName().isEmpty())) {
			for (String singlePromotion : cohProduct.getPromoteAs().getPromotionName()) {
				MerchandisingPromotions merchandisingPromotion = new MerchandisingPromotions();
				merchandisingPromotion.setPromotionName(singlePromotion);
				listOfMerchandisingPromotion.add(merchandisingPromotion);
			}
		}
		deviceDetails.setMerchandisingPromotion(listOfMerchandisingPromotion);

		Equipment equipment = new Equipment();
		equipment.setMake(cohProduct.getEquipment().getMake());
		equipment.setModel(cohProduct.getEquipment().getModel());
		deviceDetails.setEquipmentDetail(equipment);

		if (cohProduct.getProductLines() != null && !cohProduct.getProductLines().isEmpty()
				&& !cohProduct.getProductLines().contains(Constants.PAYG_DEVICE)) {
			deviceDetails.setLeadPlanId(cohProduct.getLeadPlanId());
		} else {
			deviceDetails.setLeadPlanId(null);
		}

		if (cohProduct.getProductAvailability().getEnd() != null) {
			productAvailability.setEndDate(
					CommonUtility.getDateToString(cohProduct.getProductAvailability().getEnd(), dateFormat));
		}
		if (cohProduct.getProductAvailability().getStart() != null) {
			productAvailability.setStartDate(
					CommonUtility.getDateToString(cohProduct.getProductAvailability().getStart(), dateFormat));
		}

		productAvailability.setSalesExpired(cohProduct.getProductAvailability().isSalesExpired());
		deviceDetails.setProductAvailability(productAvailability);

		MediaLink mediaLink;
		if (cohProduct.getListOfimageURLs() != null) {
			for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : cohProduct.getListOfimageURLs()) {
				if (StringUtils.isNotBlank(imageURL.getImageURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(imageURL.getImageURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (cohProduct.getListOfmediaURLs() != null) {
			for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : cohProduct.getListOfmediaURLs()) {
				if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(mediaURL.getMediaName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(mediaURL.getMediaURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}

		/**
		 * @author manoj.bera I called promotion API
		 */
		if (listOfOfferPacks != null && !listOfOfferPacks.isEmpty()) {

			BundleAndHardwarePromotions promotions = listOfOfferPacks.get(0);
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = promotions
					.getEntertainmentPacks();
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = promotions
					.getDataAllowances();
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = promotions
					.getPlanCouplingPromotions();
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash = promotions.getSashBannerForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = promotions.getSecureNet();
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = promotions
					.getSashBannerForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = promotions.getFreeExtras();
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
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBundleConditional = listOfOfferPacks.get(0)
					.getConditionalSashBanner();
			merchandisingMedia.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances,
					planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories,
					freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares,
					sashBundleConditional));
		}

		List<com.vf.uk.dal.device.datamodel.product.Group> listOfSpecificationGroups = cohProduct
				.getSpecificationGroups();
		List<Specification> listOfSpecification;
		List<SpecificationGroup> listOfSpecificationGroup = new ArrayList<>();
		SpecificationGroup specificationGroups;
		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.product.Group specificationGroup : listOfSpecificationGroups) {
				specificationGroups = new SpecificationGroup();
				specificationGroups.setGroupName(specificationGroup.getGroupName());
				specificationGroups.setPriority(specificationGroup.getPriority().intValue());
				specificationGroups.setComparable(specificationGroup.isComparable());
				listOfSpecification = new ArrayList<>();
				List<com.vf.uk.dal.device.datamodel.product.Specification> listOfSpec = specificationGroup
						.getSpecifications();
				Specification specification;
				if (listOfSpec != null && !listOfSpec.isEmpty()) {
					for (com.vf.uk.dal.device.datamodel.product.Specification spec : listOfSpec) {
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
				listOfSpecificationGroup.add(specificationGroups);
			}
		}
		deviceDetails.setSpecificationsGroups(listOfSpecificationGroup);

		// price calculation
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			PriceForBundleAndHardware priceForBundleAndHardware;
			priceForBundleAndHardware = listOfPriceForBundleAndHardware.get(0);
			if (cohProduct.getId().equalsIgnoreCase(priceForBundleAndHardware.getHardwarePrice().getHardwareId())) {
				if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null
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
				if (priceForBundleAndHardware != null && priceForBundleAndHardware.getBundlePrice() != null
						&& getMonthlyPriceCheckForBundleAndHardware(priceForBundleAndHardware)
						&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross()
								.equalsIgnoreCase(
										priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross())) {
					priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setGross(null);
					priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setVat(null);
					priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setNet(null);
					priceForBundleAndHardware.getMonthlyDiscountPrice().setGross(null);
					priceForBundleAndHardware.getMonthlyDiscountPrice().setVat(null);
					priceForBundleAndHardware.getMonthlyDiscountPrice().setNet(null);
				}

				deviceDetails.setPriceInfo(priceForBundleAndHardware);

				/**
				 * pricing Merchandising population
				 */
				MerchandisingPromotion merchPromoForHardware = priceForBundleAndHardware.getHardwarePrice()
						.getMerchandisingPromotions();
				// Label
				MediaLink priceMediaLinkLabel = new MediaLink();
				if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
					if (StringUtils.isNotBlank(merchPromoForHardware.getLabel())) {
						priceMediaLinkLabel
								.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
						priceMediaLinkLabel.setType("TEXT");
						priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
						priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceMediaLinkLabel);
					}
					// Description
					if (StringUtils.isNotBlank(merchPromoForHardware.getDescription())) {
						MediaLink priceMediaLinkDescription = new MediaLink();
						priceMediaLinkDescription
								.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
						priceMediaLinkDescription.setType("TEXT");
						priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
						priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceMediaLinkDescription);
					}
					// PriceEstablished
					if (StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel())) {
						MediaLink priceEstablishedMediaLink = new MediaLink();
						priceEstablishedMediaLink.setId(
								merchPromoForHardware.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
						priceEstablishedMediaLink.setType("TEXT");
						priceEstablishedMediaLink.setValue(merchPromoForHardware.getPriceEstablishedLabel());
						priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceEstablishedMediaLink);
					}
				}

				// Bundle Promotion
				MerchandisingPromotion merchPromoForBundle = priceForBundleAndHardware.getBundlePrice()
						.getMerchandisingPromotions();
				// Label
				if (merchPromoForBundle != null && merchPromoForBundle.getMpType() != null) {
					if (StringUtils.isNotBlank(merchPromoForBundle.getLabel())) {
						MediaLink priceMediaLinkLabelForBundle = new MediaLink();
						priceMediaLinkLabelForBundle
								.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
						priceMediaLinkLabelForBundle.setType("TEXT");
						priceMediaLinkLabelForBundle.setValue(merchPromoForBundle.getLabel());
						priceMediaLinkLabelForBundle.setPriority(merchPromoForBundle.getPriority());
						merchandisingMedia.add(priceMediaLinkLabelForBundle);
					}
					// Description
					if (StringUtils.isNotBlank(merchPromoForBundle.getDescription())) {
						MediaLink priceMediaLinkDescriptionForBundle = new MediaLink();
						priceMediaLinkDescriptionForBundle
								.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
						priceMediaLinkDescriptionForBundle.setType("TEXT");
						priceMediaLinkDescriptionForBundle.setValue(merchPromoForBundle.getDescription());
						priceMediaLinkDescriptionForBundle.setPriority(merchPromoForBundle.getPriority());
						merchandisingMedia.add(priceMediaLinkDescriptionForBundle);
					}
					// PriceEstablished
					if (StringUtils.isNotBlank(merchPromoForBundle.getPriceEstablishedLabel())) {
						MediaLink priceEstablishedMediaLinkForBundle = new MediaLink();
						priceEstablishedMediaLinkForBundle.setId(
								merchPromoForBundle.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
						priceEstablishedMediaLinkForBundle.setType("TEXT");
						priceEstablishedMediaLinkForBundle.setValue(merchPromoForBundle.getPriceEstablishedLabel());
						priceEstablishedMediaLinkForBundle.setPriority(merchPromoForBundle.getPriority());
						merchandisingMedia.add(priceEstablishedMediaLinkForBundle);
					}
				}
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

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static boolean getMonthlyPriceCheckForBundleAndHardware(
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
	public static boolean getOnePriceCheckForPriceForBundleAndHarware(
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
	public static DeviceSummary convertCoherenceDeviceToDeviceTile(Long memberPriority,
			CommercialProduct commercialProduct, CommercialBundle comBundle,
			PriceForBundleAndHardware priceforBundleAndHardware, List<BundleAndHardwarePromotions> listOfOfferPacks,
			String groupType, boolean isConditionalAcceptJourney, Map<String, Boolean> fromPricingMap) {

		DeviceSummary deviceSummary;
		deviceSummary = new DeviceSummary();

		HardwarePrice hardwarePrice;
		Price price;
		PriceForBundleAndHardware priceInfo;
		deviceSummary.setDeviceId(commercialProduct.getId());
		deviceSummary.setDisplayName(commercialProduct.getDisplayName());
		if (commercialProduct.getProductControl() != null && commercialProduct.getProductControl().isPreOrderable()) {
			String startDateTime;
			startDateTime = CommonUtility.getDateToString(commercialProduct.getProductControl().getAvailableFrom(),
					Constants.DATE_FORMAT_COHERENCE);
			if (startDateTime != null
					&& CommonUtility.dateValidationForProduct(startDateTime, Constants.DATE_FORMAT_COHERENCE)) {
				deviceSummary.setPreOrderable(true);
				deviceSummary.setAvailableFrom(startDateTime);
			} else {
				deviceSummary.setPreOrderable(false);
			}
		} else {
			deviceSummary.setPreOrderable(false);
		}

		if (memberPriority != null) {
			deviceSummary.setPriority(String.valueOf(memberPriority));
		}
		if (fromPricingMap != null) {
			deviceSummary.setFromPricing(fromPricingMap.get(commercialProduct.getId()));
		}
		deviceSummary.setDisplayDescription(commercialProduct.getPreDesc());

		List<com.vf.uk.dal.device.datamodel.product.Group> listOfSpecificationGroups = commercialProduct
				.getSpecificationGroups();

		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.product.Group specificationGroup : listOfSpecificationGroups) {
				if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
					List<com.vf.uk.dal.device.datamodel.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vf.uk.dal.device.datamodel.product.Specification spec : listOfSpec) {
							if (spec.getName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
								deviceSummary.setColourName(spec.getValue());
							}
							if (spec.getName().equalsIgnoreCase(Constants.STRING_HEXVALUE)) {
								deviceSummary.setColourHex(spec.getValue());
							}
						}
					}
				}
				if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_CAPACITY)) {
					List<com.vf.uk.dal.device.datamodel.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vf.uk.dal.device.datamodel.product.Specification spec : listOfSpec) {
							if (spec.getName().equalsIgnoreCase(Constants.STRING_CAPACITY)) {
								deviceSummary.setMemory(spec.getValue() + spec.getValueUOM());
							}
						}
					}
				}

			}
		}
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		MediaLink mediaLink;
		if (commercialProduct.getListOfimageURLs() != null) {
			for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : commercialProduct.getListOfimageURLs()) {
				if (StringUtils.isNotBlank(imageURL.getImageURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(imageURL.getImageURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (commercialProduct.getListOfmediaURLs() != null) {
			for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : commercialProduct.getListOfmediaURLs()) {
				if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(mediaURL.getMediaName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(mediaURL.getMediaURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}

		/**
		 * @author manoj.bera
		 * @Sprint 6.4
		 */
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
			merchandisingMedia.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances,
					planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories,
					freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares,
					sashBundleConditional));
		}

		// MediaLink for PricePromotions
		if (priceforBundleAndHardware != null) {

			// Hardware Promotion
			MerchandisingPromotion merchPromoForHardware = priceforBundleAndHardware.getHardwarePrice()
					.getMerchandisingPromotions();
			// Label
			MediaLink priceMediaLinkLabel = new MediaLink();
			if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
				if (StringUtils.isNotBlank(merchPromoForHardware.getLabel())) {
					priceMediaLinkLabel.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
					priceMediaLinkLabel.setType("TEXT");
					priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
					priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
					merchandisingMedia.add(priceMediaLinkLabel);
				}
				// Description
				if (StringUtils.isNotBlank(merchPromoForHardware.getDescription())) {
					MediaLink priceMediaLinkDescription = new MediaLink();
					priceMediaLinkDescription
							.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					priceMediaLinkDescription.setType("TEXT");
					priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
					priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
					merchandisingMedia.add(priceMediaLinkDescription);
				}
				// PriceEstablished
				if (StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel())) {
					MediaLink priceEstablishedMediaLink = new MediaLink();
					priceEstablishedMediaLink
							.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
					priceEstablishedMediaLink.setType("TEXT");
					priceEstablishedMediaLink.setValue(merchPromoForHardware.getPriceEstablishedLabel());
					priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
					merchandisingMedia.add(priceEstablishedMediaLink);
				}
				if (StringUtils.isNotBlank(merchPromoForHardware.getPromotionMedia())) {
					MediaLink priceEstablishedMediaLink = new MediaLink();
					priceEstablishedMediaLink
							.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_PROMOTION_MEDIA);
					priceEstablishedMediaLink.setType("URL");
					priceEstablishedMediaLink.setValue(merchPromoForHardware.getPromotionMedia());
					priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
					merchandisingMedia.add(priceEstablishedMediaLink);
				}
			}

			// Bundle Promotion
			MerchandisingPromotion merchPromoForBundle = priceforBundleAndHardware.getBundlePrice()
					.getMerchandisingPromotions();
			// Label
			if (merchPromoForBundle != null && merchPromoForBundle.getMpType() != null) {
				if (StringUtils.isNotBlank(merchPromoForBundle.getLabel())) {
					MediaLink priceMediaLinkLabelForBundle = new MediaLink();
					priceMediaLinkLabelForBundle
							.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
					priceMediaLinkLabelForBundle.setType("TEXT");
					priceMediaLinkLabelForBundle.setValue(merchPromoForBundle.getLabel());
					priceMediaLinkLabelForBundle.setPriority(merchPromoForBundle.getPriority());
					merchandisingMedia.add(priceMediaLinkLabelForBundle);
				}
				// Description
				if (StringUtils.isNotBlank(merchPromoForBundle.getDescription())) {
					MediaLink priceMediaLinkDescriptionForBundle = new MediaLink();
					priceMediaLinkDescriptionForBundle
							.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					priceMediaLinkDescriptionForBundle.setType("TEXT");
					priceMediaLinkDescriptionForBundle.setValue(merchPromoForBundle.getDescription());
					priceMediaLinkDescriptionForBundle.setPriority(merchPromoForBundle.getPriority());
					merchandisingMedia.add(priceMediaLinkDescriptionForBundle);
				}
				// PriceEstablished
				if (StringUtils.isNotBlank(merchPromoForBundle.getPriceEstablishedLabel())) {
					MediaLink priceEstablishedMediaLinkForBundle = new MediaLink();
					priceEstablishedMediaLinkForBundle
							.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
					priceEstablishedMediaLinkForBundle.setType("TEXT");
					priceEstablishedMediaLinkForBundle.setValue(merchPromoForBundle.getPriceEstablishedLabel());
					priceEstablishedMediaLinkForBundle.setPriority(merchPromoForBundle.getPriority());
					merchandisingMedia.add(priceEstablishedMediaLinkForBundle);
				}
				if (StringUtils.isNotBlank(merchPromoForBundle.getPromotionMedia())) {
					MediaLink priceEstablishedMediaLink = new MediaLink();
					priceEstablishedMediaLink
							.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_PROMOTION_MEDIA);
					priceEstablishedMediaLink.setType("URL");
					priceEstablishedMediaLink.setValue(merchPromoForBundle.getPromotionMedia());
					priceEstablishedMediaLink.setPriority(merchPromoForBundle.getPriority());
					merchandisingMedia.add(priceEstablishedMediaLink);
				}
			}
			// }
			// }
		}
		deviceSummary.setMerchandisingMedia(merchandisingMedia);
		// US13782 : Assembly of promotion Nodes
		if (CollectionUtils.isNotEmpty(listOfOfferPacks) && priceforBundleAndHardware != null) {
			deviceSummary.setPromotionsPackage(
					assembleMechandisingPromotionsPackageGeneric(listOfOfferPacks.get(0), priceforBundleAndHardware));
		}

		if (comBundle != null) {
			deviceSummary.setLeadPlanId(comBundle.getId());
			// Bundle Type Mapping
			deviceSummary.setBundleType(comBundle.getDisplayGroup());
			deviceSummary.setLeadPlanDisplayName(comBundle.getDisplayName());
			if (comBundle.getAllowances() != null && !comBundle.getAllowances().isEmpty()) {
				for (Allowance bundleAllowance : comBundle.getAllowances()) {
					if (StringUtils.containsIgnoreCase(bundleAllowance.getType(), Constants.STRING_DATA_AlLOWANCE)) {
						deviceSummary.setUom(bundleAllowance.getUom());
						deviceSummary.setUomValue(bundleAllowance.getValue());
					}
				}

			}
		}

		deviceSummary.setProductGroupURI(
				commercialProduct.getEquipment().getMake() + "/" + commercialProduct.getEquipment().getModel());

		// Price calculation

		if (groupType != null && groupType.equals(Constants.STRING_DEVICE_PAYG)) {
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
			PriceForBundleAndHardware priceForBundleAndHardware = DeviceTilesDaoUtils.getBundleAndHardwarePrice(
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

	/**
	 * @author krishna.reddy @sprint-7.1
	 * @param memberPriority
	 * @param commercialProduct
	 * @param priceforBundleAndHardware
	 * @param groupType
	 * @return DeviceSummary
	 */
	public static DeviceSummary convertCoherenceDeviceToDeviceTile_PAYG(Long memberPriority,
			CommercialProduct commercialProduct, PriceForBundleAndHardware priceforBundleAndHardware, String groupType,
			BundleAndHardwarePromotions promotions) {

		DeviceSummary deviceSummary;
		deviceSummary = new DeviceSummary();

		deviceSummary.setDeviceId(commercialProduct.getId());
		deviceSummary.setDisplayName(commercialProduct.getDisplayName());
		if (commercialProduct.getProductControl() != null && commercialProduct.getProductControl().isPreOrderable()) {
			String startDateTime;
			startDateTime = CommonUtility.getDateToString(commercialProduct.getProductControl().getAvailableFrom(),
					Constants.DATE_FORMAT_COHERENCE);
			if (startDateTime != null
					&& CommonUtility.dateValidationForProduct(startDateTime, Constants.DATE_FORMAT_COHERENCE)) {
				deviceSummary.setPreOrderable(true);
				deviceSummary.setAvailableFrom(startDateTime);
			} else {
				deviceSummary.setPreOrderable(false);
			}
		} else {
			deviceSummary.setPreOrderable(false);
		}

		if (memberPriority != null) {
			deviceSummary.setPriority(String.valueOf(memberPriority));
		}
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		if (promotions != null) {

			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = promotions
					.getEntertainmentPacks();
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = promotions
					.getDataAllowances();
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = promotions
					.getPlanCouplingPromotions();
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash = promotions.getSashBannerForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = promotions.getSecureNet();
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = promotions
					.getSashBannerForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = promotions.getFreeExtras();
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
			merchandisingMedia.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances,
					planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories,
					freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares,
					sashBundleConditional));
		}

		deviceSummary.setFromPricing(null);

		deviceSummary.setDisplayDescription(commercialProduct.getPreDesc());

		List<com.vf.uk.dal.device.datamodel.product.Group> listOfSpecificationGroups = commercialProduct
				.getSpecificationGroups();

		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.product.Group specificationGroup : listOfSpecificationGroups) {
				if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
					List<com.vf.uk.dal.device.datamodel.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vf.uk.dal.device.datamodel.product.Specification spec : listOfSpec) {
							if (spec.getName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
								deviceSummary.setColourName(spec.getValue());
							}
							if (spec.getName().equalsIgnoreCase(Constants.STRING_HEXVALUE)) {
								deviceSummary.setColourHex(spec.getValue());
							}
						}
					}
				}
				if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_CAPACITY)) {
					List<com.vf.uk.dal.device.datamodel.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vf.uk.dal.device.datamodel.product.Specification spec : listOfSpec) {
							if (spec.getName().equalsIgnoreCase(Constants.STRING_CAPACITY)) {
								deviceSummary.setMemory(spec.getValue() + spec.getValueUOM());
							}
						}
					}
				}

			}
		}

		MediaLink mediaLink;
		if (commercialProduct.getListOfimageURLs() != null) {
			for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : commercialProduct.getListOfimageURLs()) {
				if (StringUtils.isNotBlank(imageURL.getImageURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(imageURL.getImageURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (commercialProduct.getListOfmediaURLs() != null) {
			for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : commercialProduct.getListOfmediaURLs()) {
				if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
					mediaLink = new MediaLink();
					mediaLink.setId(mediaURL.getMediaName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(mediaURL.getMediaURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}

		// MediaLink for PricePromotions
		if (priceforBundleAndHardware != null) {
			deviceSummary.setPriceInfo(DeviceTilesDaoUtils.getCalculatedPrice(priceforBundleAndHardware));
			// Hardware Promotion
			if (priceforBundleAndHardware.getHardwarePrice() != null) {

				MerchandisingPromotion merchPromoForHardware = priceforBundleAndHardware.getHardwarePrice()
						.getMerchandisingPromotions();
				// Label
				MediaLink priceMediaLinkLabel = new MediaLink();
				if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
					if (StringUtils.isNotBlank(merchPromoForHardware.getLabel())) {
						priceMediaLinkLabel
								.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
						priceMediaLinkLabel.setType("TEXT");
						priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
						priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceMediaLinkLabel);
					}
					// Description
					if (StringUtils.isNotBlank(merchPromoForHardware.getDescription())) {
						MediaLink priceMediaLinkDescription = new MediaLink();
						priceMediaLinkDescription
								.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
						priceMediaLinkDescription.setType("TEXT");
						priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
						priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceMediaLinkDescription);
					}
					// PriceEstablished
					if (StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel())) {
						MediaLink priceEstablishedMediaLink = new MediaLink();
						priceEstablishedMediaLink.setId(
								merchPromoForHardware.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
						priceEstablishedMediaLink.setType("TEXT");
						priceEstablishedMediaLink.setValue(merchPromoForHardware.getPriceEstablishedLabel());
						priceEstablishedMediaLink.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceEstablishedMediaLink);
					}
				}
			}
		}

		deviceSummary.setMerchandisingMedia(merchandisingMedia);

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
	public static MerchandisingPromotionsPackage assembleMechandisingPromotionsPackageGeneric(
			com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions bundleAndHardwarePromotion,
			PriceForBundleAndHardware priceForBundleAndHardware) {
		MerchandisingPromotionsPackage promotionsPackage = null;
		promotionsPackage = new MerchandisingPromotionsPackage();
		MerchandisingPromotionsWrapper bundlePromotions = new MerchandisingPromotionsWrapper();
		MerchandisingPromotionsWrapper hardwarePromotions = new MerchandisingPromotionsWrapper();
		if (bundleAndHardwarePromotion != null) {

			if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getDataAllowances())) {

				/* Assembly of dataPromotions */
				MerchandisingPromotion dataPromotion = new MerchandisingPromotion();
				CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance = bundleAndHardwarePromotion
						.getDataAllowances().get(0);
				dataPromotion.setTag(dataAllowance.getTag());
				dataPromotion.setDescription(dataAllowance.getDescription());
				// dataPromotion.setDiscountId(dataAllowance.get);
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

			if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getSecureNet())) {
				/* Assembly of secureNetPromotion */
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

			if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getSashBannerForPlan())) {
				/* Assembly of sashBannerPromotion */
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
			if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getConditionalSashBanner())) {
				/* Assembly of sashBannerPromotion */
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

			if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getEntertainmentPacks())) {
				/* Assembly of entertainmentPackPromotion */
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

			if (CollectionUtils.isNotEmpty(bundleAndHardwarePromotion.getFreeExtrasForPlan())) {
				/* Assembly of FreeExtrasForPlan */
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
}
