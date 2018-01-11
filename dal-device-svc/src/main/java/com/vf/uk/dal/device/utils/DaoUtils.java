package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.Attributes;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.Equipment;
import com.vf.uk.dal.device.entity.Facet;
import com.vf.uk.dal.device.entity.FacetWithCount;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.HardwarePrice;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Make;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.Member;
import com.vf.uk.dal.device.entity.MerchandisingControl;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.MerchandisingPromotions;
import com.vf.uk.dal.device.entity.MetaData;
import com.vf.uk.dal.device.entity.NewFacet;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductAvailability;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.entity.Specification;
import com.vf.uk.dal.device.entity.SpecificationGroup;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundlePrice;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareAccessory;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareDataAllowances;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareExtras;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSash;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSecureNet;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForHardwareSash;
import com.vf.uk.dal.utility.entity.PriceForAccessory;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.Media;
import com.vf.uk.dal.utility.solr.entity.MonthlyDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.MonthlyPrice;
import com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails;
import com.vf.uk.dal.utility.solr.entity.OneOffDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.OneOffPrice;
import com.vodafone.dal.bundle.pojo.Allowance;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.product.pojo.ItemAttribute;
import com.vodafone.solrmodels.BundleModel;
import com.vodafone.solrmodels.OfferAppliedPriceModel;
import com.vodafone.solrmodels.ProductGroupModel;
import com.vodafone.solrmodels.ProductModel;

/**
 * Mapping of coherence and solr entities to Device entities.
 * 
 **/

public class DaoUtils {

	/**
	 * conversion of coherence Device to DeviceSummary.
	 * 
	 * @param member
	 * @param commercialProduct
	 * @param comBundle
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
			}else{
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

		List<com.vodafone.product.pojo.Group> listOfSpecificationGroups = commercialProduct.getSpecificationGroups();

		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vodafone.product.pojo.Group specificationGroup : listOfSpecificationGroups) {
				if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
					List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
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
					List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
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
			for (com.vodafone.product.pojo.ImageURL imageURL : commercialProduct.getListOfimageURLs()) {
				if(StringUtils.isNotBlank(imageURL.getImageURL()))
				{
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(imageURL.getImageURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (commercialProduct.getListOfmediaURLs() != null) {
			for (com.vodafone.product.pojo.MediaURL mediaURL : commercialProduct.getListOfmediaURLs()) {
				if(StringUtils.isNotBlank(mediaURL.getMediaURL()))
				{
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
		if(listOfOfferPacks!=null && !listOfOfferPacks.isEmpty())
		{
			
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks=listOfOfferPacks.get(0).getEntertainmentPacks();
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances=listOfOfferPacks.get(0).getDataAllowances();
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions=listOfOfferPacks.get(0).getPlanCouplingPromotions();
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash=listOfOfferPacks.get(0).getSashBannerForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet=listOfOfferPacks.get(0).getSecureNet();
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware=listOfOfferPacks.get(0).getSashBannerForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras=listOfOfferPacks.get(0).getFreeExtras();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories=listOfOfferPacks.get(0).getFreeAccessory();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans=listOfOfferPacks.get(0).getFreeExtrasForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans=listOfOfferPacks.get(0).getFreeAccForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares=listOfOfferPacks.get(0).getFreeExtrasForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares=listOfOfferPacks.get(0).getFreeAccForHardware();
			merchandisingMedia.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances, planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories, freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares));
		}

		// MediaLink for PricePromotions
		if (priceforBundleAndHardware != null ) {

					// Hardware Promotion
					MerchandisingPromotion merchPromoForHardware = priceforBundleAndHardware.getHardwarePrice()
							.getMerchandisingPromotions();
					// Label
					MediaLink priceMediaLinkLabel = new MediaLink();
					if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
						if(StringUtils.isNotBlank(merchPromoForHardware.getLabel()))
						{
							priceMediaLinkLabel
									.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
							priceMediaLinkLabel.setType("TEXT");
							priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
							priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
							merchandisingMedia.add(priceMediaLinkLabel);
						}
						// Description
						if(StringUtils.isNotBlank(merchPromoForHardware.getDescription()))
						{
							MediaLink priceMediaLinkDescription = new MediaLink();
							priceMediaLinkDescription
									.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
							priceMediaLinkDescription.setType("TEXT");
							priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
							priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
							merchandisingMedia.add(priceMediaLinkDescription);
						}
						// PriceEstablished
						if(StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel()))
						{
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
					MerchandisingPromotion merchPromoForBundle = priceforBundleAndHardware.getBundlePrice()
							.getMerchandisingPromotions();
					// Label
					if (merchPromoForBundle != null && merchPromoForBundle.getMpType() != null) {
						if(StringUtils.isNotBlank(merchPromoForBundle.getLabel()))
						{
							MediaLink priceMediaLinkLabelForBundle = new MediaLink();
							priceMediaLinkLabelForBundle
									.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
							priceMediaLinkLabelForBundle.setType("TEXT");
							priceMediaLinkLabelForBundle.setValue(merchPromoForBundle.getLabel());
							priceMediaLinkLabelForBundle.setPriority(merchPromoForBundle.getPriority());
							merchandisingMedia.add(priceMediaLinkLabelForBundle);
						}
						// Description
						if(StringUtils.isNotBlank(merchPromoForBundle.getDescription()))
						{
							MediaLink priceMediaLinkDescriptionForBundle = new MediaLink();
							priceMediaLinkDescriptionForBundle
									.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
							priceMediaLinkDescriptionForBundle.setType("TEXT");
							priceMediaLinkDescriptionForBundle.setValue(merchPromoForBundle.getDescription());
							priceMediaLinkDescriptionForBundle.setPriority(merchPromoForBundle.getPriority());
							merchandisingMedia.add(priceMediaLinkDescriptionForBundle);
						}
						// PriceEstablished
						if(StringUtils.isNotBlank(merchPromoForBundle.getPriceEstablishedLabel()))
						{
						MediaLink priceEstablishedMediaLinkForBundle = new MediaLink();
						priceEstablishedMediaLinkForBundle.setId(
								merchPromoForBundle.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
						priceEstablishedMediaLinkForBundle.setType("TEXT");
						priceEstablishedMediaLinkForBundle.setValue(merchPromoForBundle.getPriceEstablishedLabel());
						priceEstablishedMediaLinkForBundle.setPriority(merchPromoForBundle.getPriority());
						merchandisingMedia.add(priceEstablishedMediaLinkForBundle);
						}
					}
				//}
			//}
		}

		deviceSummary.setMerchandisingMedia(merchandisingMedia);

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
			PriceForBundleAndHardware priceForBundleAndHardware = getBundleAndHardwarePrice(
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
	 * conversion of coherence Device to DeviceDetails
	 * 
	 * @param cohProduct
	 * @return DeviceDetails
	 */
	public static DeviceDetails convertCoherenceDeviceToDeviceDetails(CommercialProduct cohProduct,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, List<BundleAndHardwarePromotions> listOfOfferPacks) {
		ProductAvailability productAvailability = new ProductAvailability();

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
		merchandisingControl.setIsDisplayableECare(cohProduct.getProductControl().isIsDisplayableinLife());
		merchandisingControl.setIsSellableECare(cohProduct.getProductControl().isIsSellableinLife());
		merchandisingControl.setIsDisplayableAcq(cohProduct.getProductControl().isIsDisplayableAcq());
		merchandisingControl.setIsSellableRet(cohProduct.getProductControl().isIsSellableRet());
		merchandisingControl.setIsDisplayableRet(cohProduct.getProductControl().isIsDisplayableRet());
		merchandisingControl.setIsSellableAcq(cohProduct.getProductControl().isIsSellableAcq());
		merchandisingControl.setIsDisplayableSavedBasket(cohProduct.getProductControl().isIsDisplayableSavedBasket());
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
		for (String singlePromotion : cohProduct.getPromoteAs().getPromotionName()) {
			MerchandisingPromotions merchandisingPromotion = new MerchandisingPromotions();
			merchandisingPromotion.setPromotionName(singlePromotion);
			listOfMerchandisingPromotion.add(merchandisingPromotion);
		}
		deviceDetails.setMerchandisingPromotion(listOfMerchandisingPromotion);

		Equipment equipment = new Equipment();
		equipment.setMake(cohProduct.getEquipment().getMake());
		equipment.setModel(cohProduct.getEquipment().getModel());
		deviceDetails.setEquipmentDetail(equipment);

		deviceDetails.setLeadPlanId(cohProduct.getLeadPlanId());

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
			for (com.vodafone.product.pojo.ImageURL imageURL : cohProduct.getListOfimageURLs()) {
				if(StringUtils.isNotBlank(imageURL.getImageURL()))
				{
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(imageURL.getImageURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (cohProduct.getListOfmediaURLs() != null) {
			for (com.vodafone.product.pojo.MediaURL mediaURL : cohProduct.getListOfmediaURLs()) {
				if(StringUtils.isNotBlank(mediaURL.getMediaURL()))
				{
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
		 * I called promotion API
		 */
		if(listOfOfferPacks!=null && !listOfOfferPacks.isEmpty())
		{
			
			BundleAndHardwarePromotions promotions=	listOfOfferPacks.get(0);
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks=promotions.getEntertainmentPacks();
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances=promotions.getDataAllowances();
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions=promotions.getPlanCouplingPromotions();
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash=promotions.getSashBannerForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet=promotions.getSecureNet();
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware=promotions.getSashBannerForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras=promotions.getFreeExtras();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories=promotions.getFreeAccessory();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans=promotions.getFreeExtrasForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans=promotions.getFreeAccForPlan();
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares=promotions.getFreeExtrasForHardware();
			List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares=promotions.getFreeAccForHardware();
			merchandisingMedia.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances, planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories, freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares));
		}

		List<com.vodafone.product.pojo.Group> listOfSpecificationGroups = cohProduct.getSpecificationGroups();
		List<Specification> listOfSpecification;
		List<SpecificationGroup> listOfSpecificationGroup = new ArrayList<>();
		SpecificationGroup specificationGroups;
		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vodafone.product.pojo.Group specificationGroup : listOfSpecificationGroups) {
				specificationGroups = new SpecificationGroup();
				specificationGroups.setGroupName(specificationGroup.getGroupName());
				specificationGroups.setPriority(specificationGroup.getPriority().intValue());
				specificationGroups.setComparable(specificationGroup.isComparable());
				listOfSpecification = new ArrayList<>();
				List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup.getSpecifications();
				Specification specification;
				if (listOfSpec != null && !listOfSpec.isEmpty()) {
					for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
						specification = new Specification();
						specification.setName(spec.getName());
						specification.setValue(spec.getValue());
						specification.setPriority(spec.getPriority().intValue());
						specification.setComparable(spec.isComparable());
						specification.setIsKey(spec.isKey());
						specification.setValueType(spec.getValueType());
						specification.setValueUOM(spec.getValueUOM());
						specification.setDescription(spec.getDescription());
						specification.setFootNote(spec.getFootNote());
						specification.setHideInList(spec.isHideInList());
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
						&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
						&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null
						&& priceForBundleAndHardware.getOneOffDiscountPrice() != null
						&& priceForBundleAndHardware.getOneOffDiscountPrice().getGross() != null
						&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross() != null
						&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {

					if (priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(
							priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
						priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().setGross(null);
						priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().setVat(null);
						priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().setNet(null);
						priceForBundleAndHardware.getOneOffDiscountPrice().setGross(null);
						priceForBundleAndHardware.getOneOffDiscountPrice().setVat(null);
						priceForBundleAndHardware.getOneOffDiscountPrice().setNet(null);
					}
				}
				if (priceForBundleAndHardware != null && priceForBundleAndHardware.getBundlePrice() != null
						&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
						&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null
						&& priceForBundleAndHardware.getMonthlyPrice() != null
						&& priceForBundleAndHardware.getMonthlyPrice().getGross() != null) {
					if (priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross()
							.equalsIgnoreCase(
									priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross())) {
						priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setGross(null);
						priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setVat(null);
						priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().setNet(null);
						priceForBundleAndHardware.getMonthlyDiscountPrice().setGross(null);
						priceForBundleAndHardware.getMonthlyDiscountPrice().setVat(null);
						priceForBundleAndHardware.getMonthlyDiscountPrice().setNet(null);
					}
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
					if(StringUtils.isNotBlank(merchPromoForHardware.getLabel()))
					{
						priceMediaLinkLabel
								.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
						priceMediaLinkLabel.setType("TEXT");
						priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
						priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceMediaLinkLabel);
					}
					// Description
					if(StringUtils.isNotBlank(merchPromoForHardware.getDescription()))
					{
						MediaLink priceMediaLinkDescription = new MediaLink();
						priceMediaLinkDescription
								.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
						priceMediaLinkDescription.setType("TEXT");
						priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
						priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
						merchandisingMedia.add(priceMediaLinkDescription);
					}
					// PriceEstablished
					if(StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel()))
					{
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
					if(StringUtils.isNotBlank(merchPromoForBundle.getLabel()))
					{
						MediaLink priceMediaLinkLabelForBundle = new MediaLink();
						priceMediaLinkLabelForBundle
								.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
						priceMediaLinkLabelForBundle.setType("TEXT");
						priceMediaLinkLabelForBundle.setValue(merchPromoForBundle.getLabel());
						priceMediaLinkLabelForBundle.setPriority(merchPromoForBundle.getPriority());
						merchandisingMedia.add(priceMediaLinkLabelForBundle);
					}
					// Description
					if(StringUtils.isNotBlank(merchPromoForBundle.getDescription()))
					{
						MediaLink priceMediaLinkDescriptionForBundle = new MediaLink();
						priceMediaLinkDescriptionForBundle
								.setId(merchPromoForBundle.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
						priceMediaLinkDescriptionForBundle.setType("TEXT");
						priceMediaLinkDescriptionForBundle.setValue(merchPromoForBundle.getDescription());
						priceMediaLinkDescriptionForBundle.setPriority(merchPromoForBundle.getPriority());
						merchandisingMedia.add(priceMediaLinkDescriptionForBundle);
					}
					// PriceEstablished
					if(StringUtils.isNotBlank(merchPromoForBundle.getPriceEstablishedLabel()))
					{
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
		metaData.seoKeyWords(cohProduct.getSeoKeywords());
		deviceDetails.setMetaData(metaData);

		return deviceDetails;
	}

	/**
	 * conversion of coherence ProductGroup to ProductGroupDetails.
	 * 
	 * @param cohProduct
	 * @return List<ProductGroup>
	 */
	public static List<ProductGroup> convertGroupProductToProductGroupDetails(
			List<ProductGroupModel> sohProductGroupList) {

		List<ProductGroup> productGroupList;
		productGroupList = new ArrayList<>();
		for (ProductGroupModel productGroupModel : sohProductGroupList) {
			ProductGroup productGroup;
			productGroup = new ProductGroup();
			productGroup.setId(null);
			productGroup.setGroupName(productGroupModel.getName());
			productGroup.setGroupPriority(String.valueOf(productGroupModel.getPriority()));
			productGroup.setGroupType(productGroupModel.getType());

			Member member;

			List<Member> listOfMember = new ArrayList<>();

			List<String> variantsList = productGroupModel.getListOfVariants();
			if (variantsList != null && !variantsList.isEmpty()) {
				for (String variants : variantsList) {
					member = new Member();
					String[] variantIdPriority = variants.split("\\|");
					if (variantIdPriority != null && variantIdPriority.length == 2) {
						member.setId(variantIdPriority[0]);
						member.setPriority(variantIdPriority[1]);
						listOfMember.add(member);
					}
				}
			}
			productGroup.setMembers(listOfMember);
			productGroupList.add(productGroup);
		}
		return productGroupList;
	}
	/**
	 * @author manoj.bera
	 * @param priceForAccessory
	 * @return
	 */
	public static PriceForAccessory getPriceForAccessory(PriceForAccessory priceForAccessory)
	{
		if(priceForAccessory!=null)
		{
			if(priceForAccessory.getHardwarePrice()!=null && priceForAccessory.getHardwarePrice().getOneOffPrice()!=null && priceForAccessory.getHardwarePrice().getOneOffDiscountPrice()!=null
				&& priceForAccessory.getHardwarePrice().getOneOffPrice().getGross()!=null && priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().getGross()!=null
				&& priceForAccessory.getHardwarePrice().getOneOffPrice().getGross().equalsIgnoreCase(priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().getGross()))
			{
				priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().setGross(null);
				priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().setNet(null);
				priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().setVat(null);
			}
		}
		return priceForAccessory;
	}
/**
 * 
 * @param commercialProduct
 * @param priceForAccessory
 * @return
 */
	public static Accessory convertCoherenceAccesoryToAccessory(CommercialProduct commercialProduct,
			PriceForAccessory priceForAccessory) {
		Accessory accessory = null;
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		if (commercialProduct != null && priceForAccessory != null) {

			com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = priceForAccessory.getHardwarePrice();
			if (hardwarePrice != null && hardwarePrice.getHardwareId().equalsIgnoreCase(commercialProduct.getId())) {
				List<MediaLink> mediList = setPriceMerchandisingPromotion(hardwarePrice);
				if (mediList != null && !mediList.isEmpty()) {
					merchandisingMedia.addAll(mediList);
				}

				if (Double.valueOf(hardwarePrice.getOneOffPrice().getGross()) > 0) {
					accessory = new Accessory();
					accessory.setDeviceCost(getPriceForAccessory(priceForAccessory));
					accessory.setSkuId(commercialProduct.getId());
					accessory.setName(commercialProduct.getDisplayName());
					accessory.setDescription(commercialProduct.getPreDesc());
				}
			}

			if (accessory != null) {
				List<com.vodafone.product.pojo.Group> listOfSpecificationGroups = commercialProduct
						.getSpecificationGroups();
				if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
					for (com.vodafone.product.pojo.Group specificationGroup : listOfSpecificationGroups) {
						if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
							List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup
									.getSpecifications();
							if (listOfSpec != null && !listOfSpec.isEmpty()) {
								for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
									if (spec.getName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
										accessory.setColour(spec.getValue());
									}
								}

							}
						}
					}
				}

				MediaLink mediaLink;
				if (commercialProduct.getListOfimageURLs() != null) {
					for (com.vodafone.product.pojo.ImageURL imageURL : commercialProduct.getListOfimageURLs()) {
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
					for (com.vodafone.product.pojo.MediaURL mediaURL : commercialProduct.getListOfmediaURLs()) {
						if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
							mediaLink = new MediaLink();
							mediaLink.setId(mediaURL.getMediaName());
							mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaLink.setValue(mediaURL.getMediaURL());
							merchandisingMedia.add(mediaLink);
						}
					}
				}

				/*
				 * Looping to check if any null values in Merchandising Media
				 * List
				 */
				/**
				 * @author manoj.bera below for loop not required Null check
				 *         done before
				 */
				
				accessory.setMerchandisingMedia(merchandisingMedia);

				List<Attributes> attList = new ArrayList<>();
				Attributes attribute;
				if (commercialProduct.getMisc() != null && commercialProduct.getMisc().getItemAttribute() != null
						&& !commercialProduct.getMisc().getItemAttribute().isEmpty()) {
					for (ItemAttribute item : commercialProduct.getMisc().getItemAttribute()) {
						attribute = new Attributes();
						attribute.setKey(item.getKey());
						attribute.setType(item.getType());
						attribute.setValue(item.getValue());
						attribute.setValueUOM(item.getValueUOM());
						attList.add(attribute);
					}
					accessory.setAttributes(attList);
				}
			}
		}
		return accessory;
	}

	public static Insurances convertCommercialProductToInsurance(List<CommercialProduct> insuranceProductList) {
		List<Double> minPrice = new ArrayList<>();
		List<Insurance> insuranceList = new ArrayList<>();
		Insurances insurances = new Insurances();

		Insurance insurance;
		for (CommercialProduct insuranceProduct : insuranceProductList) {
			insurance = new Insurance();
			insurance.setId(insuranceProduct.getId());
			insurance.setName(insuranceProduct.getDisplayName());
			Price price = new Price();
			if (insuranceProduct.getPriceDetail() != null
					&& insuranceProduct.getPriceDetail().getPriceGross() != null) {
				minPrice.add(insuranceProduct.getPriceDetail().getPriceGross());
			}
			price.setGross(String.valueOf(insuranceProduct.getPriceDetail().getPriceGross()));
			price.setVat(String.valueOf(insuranceProduct.getPriceDetail().getPriceVAT()));
			price.setNet(String.valueOf(insuranceProduct.getPriceDetail().getPriceNet()));
			insurance.setPrice(price);
			List<MediaLink> merchandisingMedia = new ArrayList<>();
			MediaLink mediaLink;
			if (insuranceProduct.getListOfimageURLs() != null) {
				for (com.vodafone.product.pojo.ImageURL imageURL : insuranceProduct.getListOfimageURLs()) {

					if (StringUtils.isNotBlank(imageURL.getImageURL())) {
						mediaLink = new MediaLink();

						mediaLink.setId(imageURL.getImageName());
						mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(imageURL.getImageURL());
						merchandisingMedia.add(mediaLink);
					}
				}
			}
			if (insuranceProduct.getListOfmediaURLs() != null) {
				for (com.vodafone.product.pojo.MediaURL mediaURL : insuranceProduct.getListOfmediaURLs()) {
					if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
						mediaLink = new MediaLink();
						mediaLink.setId(mediaURL.getMediaName());
						mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(mediaURL.getMediaURL());
						merchandisingMedia.add(mediaLink);
					}
				}
			}

			insurance.setMerchandisingMedia(merchandisingMedia);

			List<com.vodafone.product.pojo.Group> listOfSpecificationGroups = insuranceProduct.getSpecificationGroups();
			List<Specification> listOfSpecification;
			List<SpecificationGroup> listOfSpecificationGroup = new ArrayList<>();
			SpecificationGroup specificationGroups;
			if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
				for (com.vodafone.product.pojo.Group specificationGroup : listOfSpecificationGroups) {
					specificationGroups = new SpecificationGroup();
					specificationGroups.setGroupName(specificationGroup.getGroupName());
					specificationGroups.setPriority(specificationGroup.getPriority().intValue());
					specificationGroups.setComparable(specificationGroup.isComparable());
					listOfSpecification = new ArrayList<>();
					List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup.getSpecifications();
					Specification specification;
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
							specification = new Specification();
							specification.setName(spec.getName());
							specification.setValue(spec.getValue());
							specification.setPriority(spec.getPriority().intValue());
							specification.setComparable(spec.isComparable());
							specification.setIsKey(spec.isKey());
							specification.setValueType(spec.getValueType());
							specification.setValueUOM(spec.getValueUOM());
							specification.setDescription(spec.getDescription());
							specification.setFootNote(spec.getFootNote());
							listOfSpecification.add(specification);
						}
						specificationGroups.setSpecifications(listOfSpecification);
					}
					listOfSpecificationGroup.add(specificationGroups);
				}
				insurance.setSpecsGroup(listOfSpecificationGroup);

			}

			insuranceList.add(insurance);
		}
		insurances.setInsuranceList(insuranceList);
		insurances.setMinCost(String.valueOf(Collections.min(minPrice)));
		return insurances;
	}
/**
 * @author manoj.bera
 * @param priceForBundleAndHardware
 * @param deviceId
 * @param isConditionalAcceptJourney
 * @param comBundle
 * @return
 */
	public static PriceForBundleAndHardware getBundleAndHardwarePrice(
				PriceForBundleAndHardware priceForBundleAndHardware, String deviceId,
			boolean isConditionalAcceptJourney, CommercialBundle comBundle) {
		PriceForBundleAndHardware priceForBundleAndHardware1 = null;
		if (priceForBundleAndHardware != null) {
			// for (PriceForBundleAndHardware priceForBundleAndHardware :
			// listOfPriceForBundleAndHardware) {
			if (priceForBundleAndHardware.getHardwarePrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffPrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getHardwareId() != null) {
				// &&
				// priceForBundleAndHardware.getHardwarePrice().getHardwareId().equals(deviceId))
				// {
				Integer deviceID = new Integer(deviceId);
				Integer hardwareID = new Integer(priceForBundleAndHardware.getHardwarePrice().getHardwareId());
				if (isConditionalAcceptJourney) {
					if (null != priceForBundleAndHardware.getBundlePrice() && null != comBundle
							&& !comBundle.getId().equals(priceForBundleAndHardware.getBundlePrice().getBundleId())
							&& deviceID.equals(hardwareID)) {
						priceForBundleAndHardware1 = priceForBundleAndHardware;
						priceForBundleAndHardware1 = getCalculatedPrice(priceForBundleAndHardware1);
					}
				} else if (deviceID.equals(hardwareID)) {
					priceForBundleAndHardware1 = priceForBundleAndHardware;
					priceForBundleAndHardware1 = getCalculatedPrice(priceForBundleAndHardware1);
				}

			}
			// }
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
	 * @return flagTime
	 */
	public static boolean isCurrentDateBetweenStartAndEndDate(Date startDateTime, Date endDateTime) {
		boolean flagTime = false;

		Date currentDate = new Date();
		if (startDateTime != null && endDateTime != null) {
			Boolean x = currentDate.after(startDateTime);
			Boolean y = currentDate.equals(startDateTime);
			Boolean z = x || y;
			if (z && (currentDate.before(endDateTime) || currentDate.equals(endDateTime))) {
				flagTime = true;
			}
		}
		if (startDateTime == null && endDateTime != null && currentDate.before(endDateTime)) {
			flagTime = true;
		}
		if (startDateTime != null && endDateTime == null && currentDate.after(startDateTime)) {
			flagTime = true;
		}
		if (startDateTime == null && endDateTime == null) {
			flagTime = true;
		}
		return flagTime;
	}

	/**
	 * Price calculation
	 * 
	 * @param grossPrice
	 * @return Price
	 */
	public static Price getPriceFromGross(double grossPrice) {
		float vatPercentage = Float
				.parseFloat(ConfigHelper.getString(Constants.CONFIG_VAT_PERCENTAGE, Constants.DEFAULT_VAT_PERCENTAGE));
		Price price = new Price();
		double vat = (grossPrice / (100 + vatPercentage)) * vatPercentage;
		double net = grossPrice - vat;
		price.setGross(CommonUtility.getDecimalFormat().format(grossPrice));
		price.setNet(CommonUtility.getDecimalFormat().format(net));
		price.setVat(CommonUtility.getDecimalFormat().format(vat));
		return price;
	}

	public static String convertStringListToString(List<String> listOfMemberId) {
		return String.join(",", listOfMemberId);
	}

	/**
	 * converts the BundleHeaderForDevice to ProductGroupForDeviceListing
	 * 
	 * @param bundleHeaderForDevice
	 * @param groupId
	 * @param leadMemberId
	 * @param leadPlanId
	 * @return
	 */
	public static DevicePreCalculatedData convertBundleHeaderForDeviceToProductGroupForDeviceListing(String deviceId,
			String leadPlanId, String groupname, String groupId,
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			Map<String, String> leadMemberMap,
			Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> listOfPriceForBundleAndHardwareWithOfferCode,
			Map<String, String> leadMemberMapForUpgrade,String upgradeLeadPlanId,String groupType) {
		DevicePreCalculatedData productGroupForDeviceListing = null;
		productGroupForDeviceListing = new DevicePreCalculatedData();
		productGroupForDeviceListing.setDeviceId(deviceId);
		productGroupForDeviceListing.setProductGroupName(groupname);
		productGroupForDeviceListing.setProductGroupId(groupId);
		productGroupForDeviceListing.setUpgradeLeadPlanId(upgradeLeadPlanId);
		//isLeadMember not removed need to revisit
		if (leadMemberMap.containsKey(deviceId)) {
			productGroupForDeviceListing.setIsLeadMember(leadMemberMap.get(deviceId));
			productGroupForDeviceListing.setNonUpgradeLeadDeviceId(deviceId);
		} else {
			productGroupForDeviceListing.setIsLeadMember(Constants.IS_LEAD_MEMEBER_NO);
		}
		if (leadMemberMapForUpgrade!=null && !leadMemberMapForUpgrade.isEmpty() && leadMemberMapForUpgrade.containsKey(deviceId)) {
			productGroupForDeviceListing.setUpgradeLeadDeviceId(deviceId);
		} 
		if ((StringUtils.isNotBlank(leadPlanId) && StringUtils.equalsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYM)) || 
				StringUtils.equalsIgnoreCase(groupType, Constants.STRING_DEVICE_PAYG)) {
			com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceForBundleAndHardware1 = listOfPriceForBundleAndHardware
					.get(0);
			productGroupForDeviceListing.setNonUpgradeLeadPlanId(leadPlanId);
			productGroupForDeviceListing.setLeadPlanId(leadPlanId);
			Map<String, Object> priceMediaMap = getPriceInfoForSolr(priceForBundleAndHardware1,
					listOfPriceForBundleAndHardwareWithOfferCode);
			com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfo = (com.vf.uk.dal.utility.solr.entity.PriceInfo) priceMediaMap
					.get("price");
			productGroupForDeviceListing.setPriceInfo(priceInfo);
			List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = (List<com.vf.uk.dal.utility.solr.entity.Media>) priceMediaMap.get("media");
			productGroupForDeviceListing.setMedia(listOfMedia);
		}
		
		return productGroupForDeviceListing;
	}

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static Map<String, Object> getPriceInfoForSolr(
			com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceForBundleAndHardware,
			Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> listOfPriceForBundleAndHardwareWithOfferCode) {
		Map<String, Object> result = new HashMap<>();
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = new ArrayList<>();
		BundlePrice bundlePrice = priceForBundleAndHardware.getBundlePrice();
		com.vf.uk.dal.utility.entity.Price monthlyPrice = null;
		com.vf.uk.dal.utility.entity.Price monthlyDiscountPrice =null;
		String bundleId =Constants.DATA_NOT_FOUND;
		if(bundlePrice!=null && bundlePrice.getMonthlyPrice()!=null && bundlePrice.getMonthlyPrice().getGross()!=null)
		{
		 bundleId = bundlePrice.getBundleId();
		if (bundlePrice.getMerchandisingPromotions() != null) {
			com.vf.uk.dal.utility.solr.entity.Media mediaLink = new com.vf.uk.dal.utility.solr.entity.Media();
			mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "." + Constants.STRING_OFFERS_LABEL);
			String type = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
					+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&" + bundlePrice.getMerchandisingPromotions().getTag();
			mediaLink.setType(type);
			mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
			mediaLink.setDescription(Constants.DATA_NOT_FOUND);
			mediaLink.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLink.setOfferCode(Constants.DATA_NOT_FOUND);
			if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
				mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
			} else {
				mediaLink.setDiscountId(Constants.DATA_NOT_FOUND);
			}

			String description = null;
			if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
				description = bundlePrice.getMerchandisingPromotions().getDescription();
				com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription = new com.vf.uk.dal.utility.solr.entity.Media();
				mediaLinkForDescription.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
						+ Constants.STRING_OFFERS_DESCRIPTION);
				String type1 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
						+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
						+ bundlePrice.getMerchandisingPromotions().getTag();
				mediaLinkForDescription.setType(type1);
				mediaLinkForDescription.setValue(description);
				mediaLinkForDescription.setDescription(description);
				mediaLinkForDescription.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLinkForDescription.setOfferCode(Constants.DATA_NOT_FOUND);
				if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
					mediaLinkForDescription.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
				} else {
					mediaLinkForDescription.setDiscountId(Constants.DATA_NOT_FOUND);
				}
				listOfMedia.add(mediaLinkForDescription);
			}
			listOfMedia.add(mediaLink);
			// PriceEstablishedLabel
			com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.utility.solr.entity.Media();
			mediaLinkForPriceEstablished.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
					+ Constants.STRING_PRICE_ESTABLISHED_LABEL);
			String type3 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
					+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&" + bundlePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceEstablished.setType(type3);
			mediaLinkForPriceEstablished.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceEstablished.setDescription(Constants.DATA_NOT_FOUND);
			if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
				mediaLinkForPriceEstablished.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
			} else {
				mediaLinkForPriceEstablished.setDiscountId(Constants.DATA_NOT_FOUND);
			}
			mediaLinkForPriceEstablished.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForPriceEstablished.setOfferCode(Constants.DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPriceEstablished);

		}

		monthlyPrice = bundlePrice.getMonthlyPrice();
		monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
	}
		com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = priceForBundleAndHardware.getHardwarePrice();
		String hardwareId = hardwarePrice.getHardwareId();

		if (hardwarePrice.getMerchandisingPromotions() != null) {
			com.vf.uk.dal.utility.solr.entity.Media mediaLink1 = new com.vf.uk.dal.utility.solr.entity.Media();
			mediaLink1.setId(
					hardwarePrice.getMerchandisingPromotions().getMpType() + "." + Constants.STRING_OFFERS_LABEL);
			String type2 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
					+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLink1.setType(type2);
			mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
			mediaLink1.setDescription(Constants.DATA_NOT_FOUND);
			mediaLink1.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLink1.setOfferCode(Constants.DATA_NOT_FOUND);
			if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
				mediaLink1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
			} else {
				mediaLink1.setDiscountId(Constants.DATA_NOT_FOUND);
			}
			String description = null;
			if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
				description = hardwarePrice.getMerchandisingPromotions().getDescription();
				com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription1 = new com.vf.uk.dal.utility.solr.entity.Media();
				mediaLinkForDescription1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
						+ Constants.STRING_OFFERS_DESCRIPTION);
				String type3 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
						+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
						+ hardwarePrice.getMerchandisingPromotions().getTag();
				mediaLinkForDescription1.setType(type3);
				mediaLinkForDescription1.setValue(description);
				mediaLinkForDescription1.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
				mediaLinkForDescription1.setOfferCode(Constants.DATA_NOT_FOUND);
				mediaLinkForDescription1.setDescription(hardwarePrice.getMerchandisingPromotions().getDescription());
				if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
					mediaLinkForDescription1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
				} else {
					mediaLinkForDescription1.setDiscountId(Constants.DATA_NOT_FOUND);
				}
				listOfMedia.add(mediaLinkForDescription1);
			}
			listOfMedia.add(mediaLink1);
			// PriceEstablishedLabel
			com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceLabel = new com.vf.uk.dal.utility.solr.entity.Media();
			mediaLinkForPriceLabel.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
					+ Constants.STRING_PRICE_ESTABLISHED_LABEL);
			String type3 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
					+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
					+ hardwarePrice.getMerchandisingPromotions().getTag();
			mediaLinkForPriceLabel.setType(type3);
			mediaLinkForPriceLabel.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
			mediaLinkForPriceLabel.setDescription(Constants.DATA_NOT_FOUND);
			if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
				mediaLinkForPriceLabel.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
			} else {
				mediaLinkForPriceLabel.setDiscountId(Constants.DATA_NOT_FOUND);
			}
			mediaLinkForPriceLabel.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT);
			mediaLinkForPriceLabel.setOfferCode(Constants.DATA_NOT_FOUND);
			listOfMedia.add(mediaLinkForPriceLabel);

		}
		com.vf.uk.dal.utility.entity.Price oneoffPrice = hardwarePrice.getOneOffPrice();
		com.vf.uk.dal.utility.entity.Price oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
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
		bp.setBundleId(Constants.DATA_NOT_FOUND.equalsIgnoreCase(bundleId)?null:bundleId);
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
		com.vf.uk.dal.utility.solr.entity.PriceInfo priceinfo = new com.vf.uk.dal.utility.solr.entity.PriceInfo();
		priceinfo.setBundlePrice(bp);
		priceinfo.setHardwarePrice(hw);
		
		result.put("price", priceinfo);
		result.put("media", listOfMedia);
		return result;
	}
/**
 * @author krishna.reddy
 * @Sprint-6.6
 * @param ilsPricewithoutOfferCode
 * @return
 */
	public static Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> getILSPriceWithoutOfferCode(
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> ilsPricewithoutOfferCode) {
		Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> mapOfDevicePrice = new HashMap<>();
		ilsPricewithoutOfferCode
				.forEach(price -> {
					List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfDevicePrice = null;
					if (price != null && price.getHardwarePrice() != null) {
						if (mapOfDevicePrice.containsKey(price
								.getHardwarePrice().getHardwareId())) {
							listOfDevicePrice = mapOfDevicePrice.get(price
									.getHardwarePrice().getHardwareId());
							listOfDevicePrice.add(price);
						} else {
							listOfDevicePrice = new ArrayList<>();
							listOfDevicePrice.add(price);
							mapOfDevicePrice.put(price.getHardwarePrice()
									.getHardwareId(), listOfDevicePrice);
						}
					}
				});
		return mapOfDevicePrice;

	}
	/**
	 * @author manoj.bera
	 * @param deviceId
	 * @param listOfPriceForBundleAndHardwareMap
	 * @return
	 */
	public static Map<String, Object> getListOfOfferAppliedPrice(String deviceId,
			Map<String,Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>>> ilsPriceForBundleAndHardwareMap) {
		Map<String, Object> result = new HashMap<>();
		List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = new ArrayList<>();
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = new ArrayList<>();
		for(Entry<String,Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>>> ilsJourneyEntry : ilsPriceForBundleAndHardwareMap
				.entrySet())
		{
			String journeyType = ilsJourneyEntry.getKey();
			Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> listOfPriceForBundleAndHardwareMap = ilsJourneyEntry.getValue();
		for (Entry<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> entry : listOfPriceForBundleAndHardwareMap
				.entrySet()) {

			Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> offeredPriceMap = entry
					.getValue();

			String offerCode = entry.getKey();
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = null;
			if (offeredPriceMap.containsKey(deviceId)) {
				priceForBundleAndHardwareWithOfferCodeList = offeredPriceMap.get(deviceId);
			}
			if (priceForBundleAndHardwareWithOfferCodeList != null
					&& !priceForBundleAndHardwareWithOfferCodeList.isEmpty()) {
				priceForBundleAndHardwareWithOfferCodeList.forEach(priceForBundleAndHardwareWithOfferCode -> {
					OfferAppliedPriceDetails offerAppliedPriceDetails = new OfferAppliedPriceDetails();
					BundlePrice bundlePrice = priceForBundleAndHardwareWithOfferCode.getBundlePrice();
					com.vf.uk.dal.utility.entity.Price monthlyPrice = null;
					com.vf.uk.dal.utility.entity.Price monthlyDiscountPrice = null;
					String bundleId = null;
					String hardwareId = null;
					com.vf.uk.dal.utility.entity.Price oneoffPrice = null;
					com.vf.uk.dal.utility.entity.Price oneOffDisPrice = null;
					if (bundlePrice != null) {
						bundleId = bundlePrice.getBundleId();
						monthlyPrice = bundlePrice.getMonthlyPrice();
						monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
						if (bundlePrice.getMerchandisingPromotions() != null) {
							com.vf.uk.dal.utility.solr.entity.Media mediaLink = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
									+ Constants.STRING_OFFERS_LABEL);
							String type4 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
									+ bundlePrice.getMerchandisingPromotions().getTag();
							mediaLink.setType(type4);
							mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
							mediaLink.setDescription(Constants.DATA_NOT_FOUND);
							mediaLink.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_DISCOUNT);
							mediaLink.setOfferCode(offerCode);
							if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLink.setDiscountId(Constants.DATA_NOT_FOUND);
							}
							String description = null;
							if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
								description = bundlePrice.getMerchandisingPromotions().getDescription();
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForDescription.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
										+ Constants.STRING_OFFERS_DESCRIPTION);
								String type5 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
										+ bundlePrice.getMerchandisingPromotions().getTag();
								mediaLinkForDescription.setType(type5);
								mediaLinkForDescription.setValue(description);
								mediaLinkForDescription.setDescription(description);
								mediaLinkForDescription.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_DISCOUNT);
								mediaLinkForDescription.setOfferCode(offerCode);
								if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForDescription
											.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForDescription.setDiscountId(Constants.DATA_NOT_FOUND);
								}
								listOfMedia.add(mediaLinkForDescription);
							}
							listOfMedia.add(mediaLink);
							// PriceEstablished Label
							com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablishedLabel = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLinkForPriceEstablishedLabel.setId(bundlePrice.getMerchandisingPromotions().getMpType()
									+ "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
							String type6 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
									+ bundlePrice.getMerchandisingPromotions().getTag();
							mediaLinkForPriceEstablishedLabel.setType(type6);
							mediaLinkForPriceEstablishedLabel
									.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
							mediaLinkForPriceEstablishedLabel.setDescription(Constants.DATA_NOT_FOUND);
							if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLinkForPriceEstablishedLabel
										.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLinkForPriceEstablishedLabel.setDiscountId(Constants.DATA_NOT_FOUND);
							}
							mediaLinkForPriceEstablishedLabel
									.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_DISCOUNT);
							mediaLinkForPriceEstablishedLabel.setOfferCode(offerCode);
							listOfMedia.add(mediaLinkForPriceEstablishedLabel);
						}
					}
					com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = priceForBundleAndHardwareWithOfferCode
							.getHardwarePrice();
					if (hardwarePrice != null) {
						hardwareId = hardwarePrice.getHardwareId();
						oneoffPrice = hardwarePrice.getOneOffPrice();
						oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
						if (hardwarePrice.getMerchandisingPromotions() != null) {
							com.vf.uk.dal.utility.solr.entity.Media mediaLink1 = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLink1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
									+ Constants.STRING_OFFERS_LABEL);
							String type6 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
									+ hardwarePrice.getMerchandisingPromotions().getTag();
							mediaLink1.setType(type6);
							mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
							mediaLink1.setDescription(Constants.DATA_NOT_FOUND);
							mediaLink1.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_DISCOUNT);
							mediaLink1.setOfferCode(offerCode);
							if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLink1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLink1.setDiscountId(Constants.DATA_NOT_FOUND);
							}

							String description = null;
							if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
								description = hardwarePrice.getMerchandisingPromotions().getDescription();
								com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription1 = new com.vf.uk.dal.utility.solr.entity.Media();
								mediaLinkForDescription1.setId(hardwarePrice.getMerchandisingPromotions().getMpType()
										+ "." + Constants.STRING_OFFERS_DESCRIPTION);
								String type7 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
										+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
										+ hardwarePrice.getMerchandisingPromotions().getTag();
								mediaLinkForDescription1.setType(type7);
								mediaLinkForDescription1.setValue(description);
								mediaLinkForDescription1.setDescription(description);
								mediaLinkForDescription1.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_DISCOUNT);
								mediaLinkForDescription1.setOfferCode(offerCode);
								if (StringUtils
										.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
									mediaLinkForDescription1
											.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
								} else {
									mediaLinkForDescription1.setDiscountId(Constants.DATA_NOT_FOUND);
								}
								listOfMedia.add(mediaLinkForDescription1);
							}
							listOfMedia.add(mediaLink1);
							// PriceEstablished Label
							com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLinkForPriceEstablished.setId(hardwarePrice.getMerchandisingPromotions().getMpType()
									+ "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
							String type8 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
									+ hardwarePrice.getMerchandisingPromotions().getTag();
							mediaLinkForPriceEstablished.setType(type8);
							mediaLinkForPriceEstablished
									.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
							mediaLinkForPriceEstablished.setDescription(Constants.DATA_NOT_FOUND);
							if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLinkForPriceEstablished
										.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLinkForPriceEstablished.setDiscountId(Constants.DATA_NOT_FOUND);
							}
							mediaLinkForPriceEstablished.setPromoCategory(Constants.PROMO_CATEGORY_PRICING_DISCOUNT);
							mediaLinkForPriceEstablished.setOfferCode(offerCode);
							listOfMedia.add(mediaLinkForPriceEstablished);
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

	// For price API
	/**
	 * 
	 * @param listOfPriceForBundleHeader
	 * @return
	 */
	public com.vf.uk.dal.utility.entity.BundleHeader getListOfPriceForBundleAndHardwareForDevice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfPriceForBundleHeader) {
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundelMonthlyPriceForBundleHeader = null;
		com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice1 = null;
		String gross = null;

		try {

			if (listOfPriceForBundleHeader != null && !listOfPriceForBundleHeader.isEmpty()) {
				List<com.vf.uk.dal.utility.entity.BundleHeader> listOfOneOffPriceForBundleHeader = getAscendingOrderForOneoffPrice1(
						listOfPriceForBundleHeader);
				if (listOfOneOffPriceForBundleHeader != null && !listOfOneOffPriceForBundleHeader.isEmpty()) {
					if (listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
							.getOneOffDiscountPrice().getGross() != null) {
						gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
								.getOneOffDiscountPrice().getGross();
					} else {
						gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
								.getOneOffPrice().getGross();
					}
					List<com.vf.uk.dal.utility.entity.BundleHeader> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
					for (com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice : listOfOneOffPriceForBundleHeader) {
						if (bundleHeaderForDevice.getPriceInfo() != null
								&& bundleHeaderForDevice.getPriceInfo().getHardwarePrice() != null
								&& (bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
										.getOneOffDiscountPrice() != null
										|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
												.getOneOffPrice() != null)
								&& gross != null) {
							if ((bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
									.getGross() != null
									|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice()
											.getGross() != null)
									&& (gross
											.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
													.getOneOffDiscountPrice().getGross())
											|| gross.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo()
													.getHardwarePrice().getOneOffPrice().getGross()))) {
								listOfEqualOneOffPriceForBundleHeader.add(bundleHeaderForDevice);
							}
						}
					}
					String bundleId = null;
					if (listOfEqualOneOffPriceForBundleHeader != null
							&& !listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
						listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePrice1(
								listOfEqualOneOffPriceForBundleHeader);
						if (listOfBundelMonthlyPriceForBundleHeader != null
								&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
							bundleId = listOfBundelMonthlyPriceForBundleHeader.get(0).getSkuId();
						}
					}
					LogHelper.info(this, "Compatible Id:" + bundleId);
					if (bundleId != null && !bundleId.isEmpty()) {
						bundleHeaderForDevice1 = listOfBundelMonthlyPriceForBundleHeader.get(0);
					}
					
				}
			}
		} catch (Exception e) {
			LogHelper.error(this, "Exception occured when call happen to compatible bundles api: " + e);
		}

		return bundleHeaderForDevice1;

	}

	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundlePrice1(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedBundlePriceList1());

		return bundleHeaderForDeviceSorted;
	}

	class SortedBundlePriceList1 implements Comparator<com.vf.uk.dal.utility.entity.BundleHeader> {

		@Override
		public int compare(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList,
				com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
					&& bundleHeaderList.getPriceInfo().getBundlePrice() != null
					&& bundleHeaderList1.getPriceInfo().getBundlePrice() != null) {
				if (bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
								.getGross() != null) {
					gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
				}
				if (bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
								.getGross() != null) {
					gross1 = bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
				}
				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
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

	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForOneoffPrice1(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceList1());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceList1 implements Comparator<com.vf.uk.dal.utility.entity.BundleHeader> {

		@Override
		public int compare(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList,
				com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
					&& bundleHeaderList.getPriceInfo().getHardwarePrice() != null
					&& bundleHeaderList1.getPriceInfo().getHardwarePrice() != null) {
				if (bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() != null) {
					gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
				}
				if (bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() != null) {
					gross1 = bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
				}

				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else
					return 1;
			} else
				return -1;
		}
	}

	/**
	 * 
	 * @param listOfMedia
	 * @return
	 */
	public static List<com.vodafone.pojos.fromjson.device.Media> getListOfSolrMedia(List<Media> listOfMedia) {
		List<com.vodafone.pojos.fromjson.device.Media> mediaList = new ArrayList<>();
		listOfMedia.forEach(media -> {
			com.vodafone.pojos.fromjson.device.Media mediaObject = new com.vodafone.pojos.fromjson.device.Media();

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
	 * @param offerAppliedPriceList
	 * @return
	 */
	public static List<com.vodafone.pojos.fromjson.device.OfferAppliedPriceDetails> getListOfOfferAppliedPriceDetails(
			List<OfferAppliedPriceDetails> offerAppliedPriceList) {
		List<com.vodafone.pojos.fromjson.device.OfferAppliedPriceDetails> OfferAppliedListForSolr = new ArrayList<>();
		for (OfferAppliedPriceDetails offerAppliedPrice : offerAppliedPriceList) {
			com.vodafone.pojos.fromjson.device.OfferAppliedPriceDetails OfferAppliedPriceDetails = new com.vodafone.pojos.fromjson.device.OfferAppliedPriceDetails();
			com.vf.uk.dal.utility.solr.entity.BundlePrice bundlePrice1 = offerAppliedPrice.getBundlePrice();
			com.vodafone.pojos.fromjson.device.BundlePrice bundlePrice = new com.vodafone.pojos.fromjson.device.BundlePrice();

			com.vodafone.pojos.fromjson.device.MonthlyPrice monthlyPrice = new com.vodafone.pojos.fromjson.device.MonthlyPrice();

			MonthlyPrice mnthlyPrice = bundlePrice1.getMonthlyPrice();

			if (mnthlyPrice != null && mnthlyPrice.getGross() != null) {
				monthlyPrice.setGross(Float.valueOf(mnthlyPrice.getGross()));

				monthlyPrice.setNet(Float.valueOf(mnthlyPrice.getNet()));

				monthlyPrice.setVat(Float.valueOf(mnthlyPrice.getVat()));
			}
			bundlePrice.setMonthlyPrice(monthlyPrice);
			com.vodafone.pojos.fromjson.device.MonthlyDiscountPrice monthlyDiscountPrice = new com.vodafone.pojos.fromjson.device.MonthlyDiscountPrice();

			MonthlyDiscountPrice mnthlydiscPrice = bundlePrice1.getMonthlyDiscountPrice();
			if (mnthlydiscPrice != null && mnthlydiscPrice.getGross() != null) {
				monthlyDiscountPrice.setGross(Float.valueOf(mnthlydiscPrice.getGross()));

				monthlyDiscountPrice.setNet(Float.valueOf(mnthlydiscPrice.getNet()));

				monthlyDiscountPrice.setVat(Float.valueOf(mnthlydiscPrice.getVat()));

				bundlePrice.setBundleId(bundlePrice1.getBundleId());
			}
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);

			com.vodafone.pojos.fromjson.device.HardwarePrice hardwarePrice = new com.vodafone.pojos.fromjson.device.HardwarePrice();

			com.vf.uk.dal.utility.solr.entity.HardwarePrice hardwarePrice1 = offerAppliedPrice.getHardwarePrice();

			com.vodafone.pojos.fromjson.device.OneOffPrice oneOffPrice = new com.vodafone.pojos.fromjson.device.OneOffPrice();

			OneOffPrice oneOffPrice1 = hardwarePrice1.getOneOffPrice();

			if (oneOffPrice1 != null && oneOffPrice1.getGross() != null) {
				oneOffPrice.setGross(Float.valueOf(oneOffPrice1.getGross()));

				oneOffPrice.setNet(Float.valueOf(oneOffPrice1.getNet()));

				oneOffPrice.setVat(Float.valueOf(oneOffPrice1.getVat()));
			}
			hardwarePrice.setOneOffPrice(oneOffPrice);

			com.vodafone.pojos.fromjson.device.OneOffDiscountPrice oneOffDiscountPrice = new com.vodafone.pojos.fromjson.device.OneOffDiscountPrice();

			OneOffDiscountPrice OneOffDiscountPrice1 = hardwarePrice1.getOneOffDiscountPrice();

			if (OneOffDiscountPrice1 != null && OneOffDiscountPrice1.getGross() != null) {
				oneOffDiscountPrice.setGross(Float.valueOf(OneOffDiscountPrice1.getGross()));

				oneOffDiscountPrice.setNet(Float.valueOf(OneOffDiscountPrice1.getNet()));

				oneOffDiscountPrice.setVat(Float.valueOf(OneOffDiscountPrice1.getVat()));
			}

			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);

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

	/**
	 * 
	 * @param priceInfo
	 * @return
	 */
	public static com.vodafone.pojos.fromjson.device.PriceInfo getPriceForSolr(
			com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfo) {
		com.vodafone.pojos.fromjson.device.PriceInfo priceInfoObject = new com.vodafone.pojos.fromjson.device.PriceInfo();

		List<com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = priceInfo
				.getOfferAppliedPrices();
		List<com.vodafone.pojos.fromjson.device.OfferAppliedPriceDetails> listOfOfferAppliedPriceDetailsForSolr = null;
		if (listOfOfferAppliedPriceDetails != null
				&& !listOfOfferAppliedPriceDetails.isEmpty()) {
			listOfOfferAppliedPriceDetailsForSolr = getListOfOfferAppliedPriceDetails(listOfOfferAppliedPriceDetails);
		}

		com.vf.uk.dal.utility.solr.entity.BundlePrice bundlePrice1 = priceInfo
				.getBundlePrice();
		com.vodafone.pojos.fromjson.device.BundlePrice bundlePrice = new com.vodafone.pojos.fromjson.device.BundlePrice();
		if (bundlePrice1 != null) {
			com.vodafone.pojos.fromjson.device.MonthlyPrice monthlyPrice = new com.vodafone.pojos.fromjson.device.MonthlyPrice();

			com.vodafone.pojos.fromjson.device.MonthlyDiscountPrice monthlyDiscountPrice = new com.vodafone.pojos.fromjson.device.MonthlyDiscountPrice();

			MonthlyPrice mnthlyPrice = bundlePrice1.getMonthlyPrice();

			if (mnthlyPrice != null && mnthlyPrice.getGross() != null) {
				monthlyPrice.setGross(Float.valueOf(mnthlyPrice.getGross()));

				monthlyPrice.setNet(Float.valueOf(mnthlyPrice.getNet()));

				monthlyPrice.setVat(Float.valueOf(mnthlyPrice.getVat()));
			}
			bundlePrice.setMonthlyPrice(monthlyPrice);

			MonthlyDiscountPrice mnthlydiscPrice = bundlePrice1
					.getMonthlyDiscountPrice();
			if (mnthlydiscPrice != null && mnthlydiscPrice.getGross() != null) {
				monthlyDiscountPrice.setGross(Float.valueOf(mnthlydiscPrice
						.getGross()));

				monthlyDiscountPrice.setNet(Float.valueOf(mnthlydiscPrice
						.getNet()));

				monthlyDiscountPrice.setVat(Float.valueOf(mnthlydiscPrice
						.getVat()));
			}
			bundlePrice.setBundleId(bundlePrice1.getBundleId());
			bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		}
		com.vodafone.pojos.fromjson.device.HardwarePrice hardwarePrice = new com.vodafone.pojos.fromjson.device.HardwarePrice();

		com.vf.uk.dal.utility.solr.entity.HardwarePrice hardwarePrice1 = priceInfo
				.getHardwarePrice();
		if (hardwarePrice1 != null) {
			com.vodafone.pojos.fromjson.device.OneOffPrice oneOffPrice = new com.vodafone.pojos.fromjson.device.OneOffPrice();

			OneOffPrice oneOffPrice1 = hardwarePrice1.getOneOffPrice();

			if (oneOffPrice1 != null && oneOffPrice1.getGross() != null) {
				oneOffPrice.setGross(Float.valueOf(oneOffPrice1.getGross()));

				oneOffPrice.setNet(Float.valueOf(oneOffPrice1.getNet()));

				oneOffPrice.setVat(Float.valueOf(oneOffPrice1.getVat()));
			}
			hardwarePrice.setOneOffPrice(oneOffPrice);

			com.vodafone.pojos.fromjson.device.OneOffDiscountPrice oneOffDiscountPrice = new com.vodafone.pojos.fromjson.device.OneOffDiscountPrice();

			OneOffDiscountPrice OneOffDiscountPrice1 = hardwarePrice1
					.getOneOffDiscountPrice();

			if (OneOffDiscountPrice1 != null
					&& OneOffDiscountPrice1.getGross() != null) {
				oneOffDiscountPrice.setGross(Float.valueOf(OneOffDiscountPrice1
						.getGross()));

				oneOffDiscountPrice.setNet(Float.valueOf(OneOffDiscountPrice1
						.getNet()));

				oneOffDiscountPrice.setVat(Float.valueOf(OneOffDiscountPrice1
						.getVat()));
			}

			hardwarePrice.setOneOffDiscountPrice(oneOffDiscountPrice);
			hardwarePrice.setHardwareId(hardwarePrice1.getHardwareId());
		}
		
		priceInfoObject.setBundlePrice(bundlePrice);
		priceInfoObject.setHardwarePrice(hardwarePrice);
		priceInfoObject
				.setOfferAppliedPrices(listOfOfferAppliedPriceDetailsForSolr);
		return priceInfoObject;
	}

	/**
	 * 
	 * @param preCalcPlanList
	 * @return
	 */
	public static List<com.vodafone.pojos.fromjson.device.DevicePreCalculatedData> convertDevicePreCalDataToSolrData(
			List<DevicePreCalculatedData> preCalcPlanList) {
		List<com.vodafone.pojos.fromjson.device.DevicePreCalculatedData> deviceListObjectList = new ArrayList<>();

		preCalcPlanList.forEach(preCalList -> {
			com.vodafone.pojos.fromjson.device.DevicePreCalculatedData deviceListObject = new com.vodafone.pojos.fromjson.device.DevicePreCalculatedData();

			List<com.vodafone.pojos.fromjson.device.Media> mediaList = null;
			List<Media> listOfMedia = preCalList.getMedia();
			if (listOfMedia != null && !listOfMedia.isEmpty()) {
				mediaList = getListOfSolrMedia(listOfMedia);

			}

			com.vodafone.pojos.fromjson.device.PriceInfo priceInfoObject = null;
			if (preCalList.getPriceInfo() != null) {
				priceInfoObject = getPriceForSolr(preCalList.getPriceInfo());
			}
			//Need to revist after solr changes done
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
			deviceListObject.setIsLeadMember(preCalList.getIsLeadMember());//preCalList.getIsLeadMember()
			if (preCalList.getMinimumCost() != null) {
				deviceListObject.setMinimumCost(Float.valueOf(preCalList.getMinimumCost()));
			}
			deviceListObjectList.add(deviceListObject);
		});
		return deviceListObjectList;
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
			String offerCode, Map<String, String> groupNameWithProdId,
			Map<String, BundlePrice> bundleModelAndPriceMap ,
			Map<String,BundleAndHardwarePromotions> promotionmap,Map<String,Boolean> isLeadMemberFromSolr,
			Map<String, List<OfferAppliedPriceModel>> withoutOfferPriceMap,String journeyType) {
		HardwarePrice hardwarePrice;
		Price price;
		PriceForBundleAndHardware priceInfo;
		List<Device> deviceList = new ArrayList<>();
		FacetedDevice facetedDevice = new FacetedDevice();
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
		// facetedDevice.setFacet(facet);
		facetedDevice.setNewFacet(listOfNewFacet);

		int count = 0;
		Device deviceDetails;
		if (listOfProducts != null && !listOfProducts.isEmpty()) {
			for (ProductModel productModel : listOfProductModel) {
				if (listOfProducts.contains(productModel.getProductId())) {
					if (productModel.getProductClass().equalsIgnoreCase(Constants.STRING_PRODUCT_MODEL)) {
						String leadPlanId=null;
						if(StringUtils.isNotBlank(journeyType) && StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)){
							leadPlanId=productModel.getUpgradeLeadPlanId();
						}else{
							leadPlanId=productModel.getNonUpgradeLeadPlanId();
						}
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
							deviceDetails.setRating(Constants.DEVICE_RATING_NA);
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
							if (productModel.getAvailableFrom() != null
									&& CommonUtility.dateValidationForProduct(productModel.getAvailableFrom(), Constants.DATE_FORMAT_SOLR)) {
								merchandisingControl.setPreorderable(true);
								merchandisingControl.setAvailableFrom(productModel.getAvailableFrom());
							}else{
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
						LogHelper.info(DaoUtils.class, "IN Facet " + leadPlanId);
						LogHelper.info(DaoUtils.class, "IN Facet for" + bundleModelMap);
						if (leadPlanId != null && bundleModelMap != null) {
							bundleModel = bundleModelMap.get(leadPlanId);
							if (null != bundleModelAndPriceMap && !bundleModelAndPriceMap.isEmpty()) {
								bundlePrice = bundleModelAndPriceMap.get(leadPlanId);
							}
							LogHelper.info(DaoUtils.class, "IN Facet " + bundleModel);

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
						if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)) {

							if ((StringUtils.isNotBlank(offerCode) && StringUtils.isNotBlank(journeyType)) || 
									(StringUtils.isBlank(offerCode) && StringUtils.isNotBlank(journeyType))) {
								
								if (StringUtils.isNotBlank(offerCode) && listOfOfferAppliedPrice.containsKey(productModel.getProductId())) {
									PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtils(
											listOfOfferAppliedPrice.get(productModel.getProductId()),
											leadPlanId);
									if(priceForOfferCode.getBundlePrice()!=null && priceForOfferCode.getHardwarePrice()!=null)
									{
										offerFlag=true;
									deviceDetails.setPriceInfo(priceForOfferCode);
									}
								}if(StringUtils.isNotBlank(journeyType) && (deviceDetails.getPriceInfo()==null ||
										(deviceDetails.getPriceInfo()!=null
										&& deviceDetails.getPriceInfo().getBundlePrice()==null 
										&& deviceDetails.getPriceInfo().getHardwarePrice()==null)) && 
										withoutOfferPriceMap.containsKey(productModel.getProductId()))
								{
									PriceForBundleAndHardware priceForOfferCode = getBundleAndHardwarePriceFromSolrUtils(
											withoutOfferPriceMap.get(productModel.getProductId()),
											leadPlanId);
									if(priceForOfferCode.getBundlePrice()!=null && priceForOfferCode.getHardwarePrice()!=null)
									{
										offerFlag=true;
									deviceDetails.setPriceInfo(priceForOfferCode);
									}
								}if(deviceDetails.getPriceInfo()==null){
									PriceForBundleAndHardware priceForWithOutOfferCode = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
											productModel, bundleModel,leadPlanId);
									offerFlag=false;
									deviceDetails.setPriceInfo(priceForWithOutOfferCode);
								}
							} else {
								PriceForBundleAndHardware priceForBundleAndHardware = getBundleAndHardwarePriceFromSolrWithoutOfferCode(
										productModel, bundleModel,leadPlanId);
								if(priceForBundleAndHardware.getBundlePrice()!=null){
								if (bundlePrice != null) {
									populateMerchandisingPromotions(priceForBundleAndHardware, bundlePrice);
								}
								deviceDetails.setPriceInfo(priceForBundleAndHardware);
							}

							// deviceDetails.setPriceInfo(getBundleAndHardwarePriceFromSolr(productModel,
							// bundleModel));
						} 
							}else if (groupType.equals(Constants.STRING_DEVICE_PAYG)) {
							for (CommercialProduct commercialProduct : ls) {
								if (productModel.getProductId().equals(commercialProduct.getId())) {
									price = new Price();
									price.setGross(commercialProduct.getPriceDetail().getPriceGross().toString());
									price.setNet(commercialProduct.getPriceDetail().getPriceNet().toString());
									price.setVat(commercialProduct.getPriceDetail().getPriceVAT().toString());
									hardwarePrice = new HardwarePrice();
									hardwarePrice.setOneOffPrice(price);
									hardwarePrice.setHardwareId(productModel.getProductId());
									priceInfo = new PriceForBundleAndHardware();
									priceInfo.setHardwarePrice(hardwarePrice);
									deviceDetails.setPriceInfo(priceInfo);
								}
							}
						}
						// Media Link
						List<MediaLink> mediaList = new ArrayList<>();
						com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchandising = null;
						com.vf.uk.dal.device.entity.MerchandisingPromotion hardwareMerchandising = null;
						// Merchandising Media
						// List<MediaLink> mediaList = new ArrayList<>();
						String bundleTag = null;
						String bundleLabel = null;
						String bundleDescription = null;
						String bundleDiscountId = null;
						String bundleMpType = null;
						String bundlePriceEstablishedLabel = null;

						String hardwareTag = null;
						String hardwareLabel = null;
						String hardwareDescription = null;
						String hardwareDiscountId = null;
						String hardwareMpType = null;
						String hardwarePriceEstablishedLabel = null;
						for (String mediaStr : productModel.getMerchandisingMedia()) {

							String[] mediaStrList = mediaStr.split("\\|");
							if (mediaStrList.length == 7) {
								for (int i = 0; i < mediaStrList.length; i = i + 7) {

									String[] typeArray = mediaStrList[i + 2].split("&&");
									if (StringUtils.isNotBlank(offerCode) && offerFlag
											&& !Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& Constants.PROMO_CATEGORY_PRICING_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])
											&& offerCode.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
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
											hardwareMerchandising = hardwareMerchecdising;
										}
									}
									if (StringUtils.isNotBlank(offerCode) && offerFlag
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
										boolean nonOfferAppliedPriceFlag=false;
										if(StringUtils.isNotBlank(journeyType) && StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)
												&& Constants.PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT.equalsIgnoreCase(mediaStrList[i + 3]))
										{
											nonOfferAppliedPriceFlag = true;	
										}
										if(StringUtils.isNotBlank(journeyType) && StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_SECONDLINE)
												&& Constants.PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT.equalsIgnoreCase(mediaStrList[i + 3]))
										{
											nonOfferAppliedPriceFlag = true;
										}
										if(nonOfferAppliedPriceFlag){
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
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
											hardwareMerchandising = hardwareMerchecdising;
										}
									}
									}
									
									if (StringUtils.isNotBlank(offerCode) && !offerFlag
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])
											&& leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
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
											hardwareMerchandising = hardwareMerchecdising;
										}
									}
									if (StringUtils.isBlank(offerCode) && offerFlag  && StringUtils.isNotBlank(journeyType)
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])
											&& StringUtils.isNotBlank(mediaStrList[i + 3])
											&& StringUtils.containsIgnoreCase(mediaStrList[i + 3],journeyType)) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
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
											hardwareMerchandising = hardwareMerchecdising;
										}
									}
									
									if (StringUtils.isBlank(offerCode) && StringUtils.isBlank(journeyType)
											&& Constants.DATA_NOT_FOUND.equalsIgnoreCase(mediaStrList[i + 4])
											&& Constants.PROMO_CATEGORY_PRICING_AUTOMETIC_DISCOUNT
													.equalsIgnoreCase(mediaStrList[i + 3])
											&& leadPlanId != null
											&& leadPlanId.equalsIgnoreCase(typeArray[1])) {
										MediaLink mediaLink = new MediaLink();
										mediaLink.setId(mediaStrList[i]);
										mediaLink.setValue(mediaStrList[i + 1]);
										mediaLink.setType(typeArray[0]);
										mediaList.add(mediaLink);

										if (Constants.PROMO_TYPE_BUNDLEPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											bundleTag = typeArray[3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												bundleLabel = mediaStrList[i + 1];
												bundleMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												bundleDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												bundleDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
												bundlePriceEstablishedLabel = mediaStrList[i + 1];
											}
											com.vf.uk.dal.device.entity.MerchandisingPromotion bundleMerchecdising = new MerchandisingPromotion();
											bundleMerchecdising.setDescription(bundleDescription);
											bundleMerchecdising.setDiscountId(bundleDiscountId);
											bundleMerchecdising.setLabel(bundleLabel);
											bundleMerchecdising.setMpType(bundleMpType);
											bundleMerchecdising.setPriceEstablishedLabel(bundlePriceEstablishedLabel);
											bundleMerchecdising.setTag(bundleTag);
											bundleMerchandising = bundleMerchecdising;
										}
										if (Constants.PROMO_TYPE_HARDWAREPROMOTION.equalsIgnoreCase(typeArray[i + 2])) {
											hardwareTag = typeArray[i + 3];
											if (StringUtils.contains(
													mediaStrList[i].substring(mediaStrList[i].lastIndexOf(".") + 1,
															mediaStrList[i].length()),
													Constants.STRING_MEDIA_LABEL)) {
												hardwareLabel = mediaStrList[i + 1];
												hardwareMpType = mediaStrList[i].substring(0,
														mediaStrList[i].indexOf("."));
												hardwareDiscountId = mediaStrList[i + 6];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_DESCRIPTION)) {
												hardwareDescription = mediaStrList[i + 1];
											}
											if (StringUtils.containsIgnoreCase(mediaStrList[i],
													Constants.STRING_MEDIA_PRICEESTABLISH)) {
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
											hardwareMerchandising = hardwareMerchecdising;
										}
									}
								}
							}
						}
						/**
						 * Promotions from promotion API
						 * @author manoj.bera
						 */
						if(promotionmap!=null && !promotionmap.isEmpty() && promotionmap.containsKey(productModel.getProductId()))
						{
							
							BundleAndHardwarePromotions promotions=	promotionmap.get(productModel.getProductId());
							List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks=promotions.getEntertainmentPacks();
							List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances=promotions.getDataAllowances();
							List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions=promotions.getPlanCouplingPromotions();
							List<CataloguepromotionqueriesForBundleAndHardwareSash> sash=promotions.getSashBannerForPlan();
							List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet=promotions.getSecureNet();
							List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware=promotions.getSashBannerForHardware();
							List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras=promotions.getFreeExtras();
							List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories=promotions.getFreeAccessory();
							List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans=promotions.getFreeExtrasForPlan();
							List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans=promotions.getFreeAccForPlan();
							List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares=promotions.getFreeExtrasForHardware();
							List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares=promotions.getFreeAccForHardware();
							mediaList.addAll(CommonUtility.getMediaListForBundleAndHardware(entertainmentPacks, dataAllowances, planCouplingPromotions, sash, secureNet, sashBannerForHardware, freeExtras, freeAccessories, freeExtrasForPlans, freeAccForPlans, freeExtrasForHardwares, freeAccForHardwares));
						}
						if(StringUtils.isNotBlank(productModel.getImageURLsThumbsFront())){
						MediaLink mediaThumbsFrontLink = new MediaLink();
						mediaThumbsFrontLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_FRONT);
						mediaThumbsFrontLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaThumbsFrontLink.setValue(productModel.getImageURLsThumbsFront());
						mediaList.add(mediaThumbsFrontLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsThumbsLeft())){
						MediaLink mediaThumbsLeftLink = new MediaLink();
						mediaThumbsLeftLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_LEFT);
						mediaThumbsLeftLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaThumbsLeftLink.setValue(productModel.getImageURLsThumbsLeft());
						mediaList.add(mediaThumbsLeftLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsThumbsRight())){
						MediaLink mediaThumbsRightLink = new MediaLink();
						mediaThumbsRightLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_RIGHT);
						mediaThumbsRightLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaThumbsRightLink.setValue(productModel.getImageURLsThumbsRight());
						mediaList.add(mediaThumbsRightLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsThumbsSide())){
						MediaLink mediaThumbsSideLink = new MediaLink();
						mediaThumbsSideLink.setId(MediaConstants.STRING_FOR_IMAGE_THUMBS_SIDE);
						mediaThumbsSideLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaThumbsSideLink.setValue(productModel.getImageURLsThumbsSide());
						mediaList.add(mediaThumbsSideLink);
						}
						

						if(StringUtils.isNotBlank(productModel.getImageURLsFullLeft())){
						MediaLink mediaFullLeftLink = new MediaLink();
						mediaFullLeftLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_LEFT);
						mediaFullLeftLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaFullLeftLink.setValue(productModel.getImageURLsFullLeft());
						mediaList.add(mediaFullLeftLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsFullRight())){
						MediaLink mediaFullRightLink = new MediaLink();
						mediaFullRightLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_RIGHT);
						mediaFullRightLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaFullRightLink.setValue(productModel.getImageURLsFullRight());
						mediaList.add(mediaFullRightLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsFullSide())){
						MediaLink mediaFullSideLink = new MediaLink();
						mediaFullSideLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_SIDE);
						mediaFullSideLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaFullSideLink.setValue(productModel.getImageURLsFullSide());
						mediaList.add(mediaFullSideLink);
						}
					
						if(StringUtils.isNotBlank(productModel.getImageURLsFullBack())){
						MediaLink mediaFullBackLink = new MediaLink();
						mediaFullBackLink.setId(MediaConstants.STRING_FOR_IMAGE_FULL_BACK);
						mediaFullBackLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaFullBackLink.setValue(productModel.getImageURLsFullBack());
						mediaList.add(mediaFullBackLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsGrid())){
						MediaLink mediaGridLink = new MediaLink();
						mediaGridLink.setId(MediaConstants.STRING_FOR_IMAGE_GRID);
						mediaGridLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaGridLink.setValue(productModel.getImageURLsGrid());
						mediaList.add(mediaGridLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsSmall())){
						MediaLink mediaSmallLink = new MediaLink();
						mediaSmallLink.setId(MediaConstants.STRING_FOR_IMAGE_SMALL);
						mediaSmallLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaSmallLink.setValue(productModel.getImageURLsSmall());
						mediaList.add(mediaSmallLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsSticker())){
						MediaLink mediaStickerLink = new MediaLink();
						mediaStickerLink.setId(MediaConstants.STRING_FOR_IMAGE_STICKER);
						mediaStickerLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaStickerLink.setValue(productModel.getImageURLsSticker());
						mediaList.add(mediaStickerLink);
						}

						if(StringUtils.isNotBlank(productModel.getImageURLsIcon())){
						MediaLink mediaIconLink = new MediaLink();
						mediaIconLink.setId(MediaConstants.STRING_FOR_IMAGE_ICON);
						mediaIconLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaIconLink.setValue(productModel.getImageURLsIcon());
						mediaList.add(mediaIconLink);
						}
						
						if(StringUtils.isNotBlank(productModel.getThreeDSpin())){
						MediaLink media3DSpinLink = new MediaLink();
						media3DSpinLink.setId(MediaConstants.STRING_FOR_IMAGE_3DSPIN);
						media3DSpinLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						media3DSpinLink.setValue(productModel.getThreeDSpin());
						mediaList.add(media3DSpinLink);
						}

						if(StringUtils.isNotBlank(productModel.getSupport())){
						MediaLink mediaSupportLink = new MediaLink();
						mediaSupportLink.setId(MediaConstants.STRING_FOR_IMAGE_SUPPORT);
						mediaSupportLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaSupportLink.setValue(productModel.getSupport());
						mediaList.add(mediaSupportLink);
						}

						deviceDetails.setMedia(mediaList);
						PriceForBundleAndHardware priceForWithOutOfferCode = deviceDetails.getPriceInfo();
					if(priceForWithOutOfferCode!=null){
						if(priceForWithOutOfferCode.getBundlePrice()!=null){
						priceForWithOutOfferCode.getBundlePrice()
								.setMerchandisingPromotions(bundleMerchandising);
						}if(priceForWithOutOfferCode.getHardwarePrice()!=null){
						priceForWithOutOfferCode.getHardwarePrice()
								.setMerchandisingPromotions(hardwareMerchandising);
						}
					}
						if (isLeadMemberFromSolr.get("leadMember") && StringUtils.isNotBlank(leadPlanId)) {
							deviceList.add(deviceDetails);
							count++;
						}
						else if(!isLeadMemberFromSolr.get("leadMember")){
							deviceList.add(deviceDetails);
							count++;
						}
					}
				}
			}
			// }
		} else {
			LogHelper.info(DaoUtils.class, "Products not provided while converting ProductModelListToDeviceList.");
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
	private static void populateMerchandisingPromotions(PriceForBundleAndHardware priceForBundleAndHardware,
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
			BundleModel bundleModel,String leadPlanId) {
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
				bundlePrice.setBundleId(productModel.getLeadPlanIdNew());
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
				monthlyPrice.setNet(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceGross()));
				monthlyPrice.setVat(CommonUtility.getpriceFormat(productModel.getBundleMonthlyPriceGross()));
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
	 * @param listOfPriceForBundleHeader
	 * @return
	 */
	public com.vf.uk.dal.utility.entity.PriceForBundleAndHardware getListOfPriceForBundleAndHardwareForCacheDevice(
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleHeaderLocal,
			Map<String,CommercialBundle> commercialbundleMap,String journeyType) {
		List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfBundelMonthlyPriceForBundleHeader = null;
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderForDevice1 = null;
		String gross = null;
		try {

			if (listOfPriceForBundleHeaderLocal != null && !listOfPriceForBundleHeaderLocal.isEmpty()) {
				List<String> productLinesList = new ArrayList<>();
				productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
				productLinesList.add(Constants.STRING_MBB_SELLABLE);
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfPriceForBundleHeader=listOfPriceForBundleHeaderLocal.stream()
						.filter(price -> CommonUtility.isValidBundleForProduct(price,commercialbundleMap,productLinesList,journeyType))
						.collect(Collectors.toList());
				List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfOneOffPriceForBundleHeader = getAscendingOrderForOneoffPriceForCacheDeviceTile(
						listOfPriceForBundleHeader);
				if (listOfOneOffPriceForBundleHeader != null && !listOfOneOffPriceForBundleHeader.isEmpty()) {
					if (listOfOneOffPriceForBundleHeader.get(0).getHardwarePrice().getOneOffDiscountPrice()
							.getGross() != null) {
						gross = listOfOneOffPriceForBundleHeader.get(0).getHardwarePrice().getOneOffDiscountPrice()
								.getGross();
					} else {
						gross = listOfOneOffPriceForBundleHeader.get(0).getHardwarePrice().getOneOffPrice().getGross();
					}
					List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
					for (com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderForDevice : listOfOneOffPriceForBundleHeader) {
						if (bundleHeaderForDevice.getHardwarePrice() != null
								&& (bundleHeaderForDevice.getHardwarePrice().getOneOffDiscountPrice() != null
										|| bundleHeaderForDevice.getHardwarePrice().getOneOffPrice() != null)
								&& gross != null) {
							if ((bundleHeaderForDevice.getHardwarePrice().getOneOffDiscountPrice().getGross() != null
									|| bundleHeaderForDevice.getHardwarePrice().getOneOffPrice().getGross() != null)
									&& (gross
											.equalsIgnoreCase(bundleHeaderForDevice.getHardwarePrice()
													.getOneOffDiscountPrice().getGross())
											|| gross.equalsIgnoreCase(bundleHeaderForDevice.getHardwarePrice()
													.getOneOffPrice().getGross()))) {
								listOfEqualOneOffPriceForBundleHeader.add(bundleHeaderForDevice);
							}
						}
					}
					String bundleId = null;
					if (listOfEqualOneOffPriceForBundleHeader != null
							&& !listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
						listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePriceForCacheDeviceTile(
								listOfEqualOneOffPriceForBundleHeader);
						if (listOfBundelMonthlyPriceForBundleHeader != null
								&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
							bundleId = listOfBundelMonthlyPriceForBundleHeader.get(0).getBundlePrice().getBundleId();
						}
					}
					LogHelper.info(this, "Compatible Id:" + bundleId);
					if (bundleId != null && !bundleId.isEmpty()) {
						bundleHeaderForDevice1 = listOfBundelMonthlyPriceForBundleHeader.get(0);
					}
					
				}
			}
		} catch (Exception e) {
			LogHelper.error(this, "Exception occured when call happen to compatible bundles api: " + e);
		}

		return bundleHeaderForDevice1;

	}

	public List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> getAscendingOrderForBundlePriceForCacheDeviceTile(
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedBundlePriceListForcacheDeviceTile());

		return bundleHeaderForDeviceSorted;
	}

	class SortedBundlePriceListForcacheDeviceTile
			implements Comparator<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> {

		@Override
		public int compare(com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderList,
				com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getBundlePrice() != null && bundleHeaderList1.getBundlePrice() != null) {
				if (bundleHeaderList.getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
					gross = bundleHeaderList.getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getBundlePrice().getMonthlyPrice().getGross();
				}
				if (bundleHeaderList1.getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList1.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
					gross1 = bundleHeaderList1.getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getBundlePrice().getMonthlyPrice().getGross();
				}
				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	public List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> getAscendingOrderForOneoffPriceForCacheDeviceTile(
			List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceListForCacheDeviceTile());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceListForCacheDeviceTile
			implements Comparator<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> {

		@Override
		public int compare(com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderList,
				com.vf.uk.dal.utility.entity.PriceForBundleAndHardware bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getHardwarePrice() != null && bundleHeaderList1.getHardwarePrice() != null) {
				if (bundleHeaderList.getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross = bundleHeaderList.getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getHardwarePrice().getOneOffPrice().getGross();
				}
				if (bundleHeaderList1.getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList1.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross1 = bundleHeaderList1.getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getHardwarePrice().getOneOffPrice().getGross();
				}

				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else
					return 1;
			} else
				return -1;
		}
	}

	public static List<MediaLink> setPriceMerchandisingPromotion(
			com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice) {
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotions = hardwarePrice
				.getMerchandisingPromotions();
		if (merchandisingPromotions != null) {
			if (StringUtils.isNotBlank(merchandisingPromotions.getLabel())) {
				MediaLink mediaLinkLabel = new MediaLink();
				mediaLinkLabel.setId(merchandisingPromotions.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
				mediaLinkLabel.setType(Constants.STRING_TEXT);
				mediaLinkLabel.setValue(merchandisingPromotions.getLabel());
				merchandisingMedia.add(mediaLinkLabel);
			}
			if (StringUtils.isNotBlank(merchandisingPromotions.getDescription())) {
				MediaLink mediaLinkPriceEstablishedLabel = new MediaLink();
				mediaLinkPriceEstablishedLabel
						.setId(merchandisingPromotions.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
				mediaLinkPriceEstablishedLabel.setType(Constants.STRING_TEXT);
				mediaLinkPriceEstablishedLabel.setValue(merchandisingPromotions.getDescription());
				merchandisingMedia.add(mediaLinkPriceEstablishedLabel);
			}
			if (StringUtils.isNotBlank(merchandisingPromotions.getPriceEstablishedLabel())) {
				MediaLink mediaLinkPriceEstablishedLabel = new MediaLink();
				mediaLinkPriceEstablishedLabel
						.setId(merchandisingPromotions.getMpType() + "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
				mediaLinkPriceEstablishedLabel.setType(Constants.STRING_TEXT);
				mediaLinkPriceEstablishedLabel.setValue(merchandisingPromotions.getPriceEstablishedLabel());
				merchandisingMedia.add(mediaLinkPriceEstablishedLabel);
			}
		}

		return merchandisingMedia;
	}
/**
 * @author krishna.reddy
 * Sprint-6.6
 * @param deviceId
 * @param ilsPriceForBundleAndHardwareMap
 * @return
 */
	public static Map<String, Object> getListOfIlsPriceWithoutOfferCode(String deviceId,
			Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap) {
	Map<String, Object> result = new HashMap<>();
	List<OfferAppliedPriceDetails> listOfOfferAppliedPriceDetails = new ArrayList<>();
	List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = new ArrayList<>();
	
		
	for (Entry<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> entry : ilsPriceForBundleAndHardwareMap
			.entrySet()) {
		String journeyType = entry.getKey();
		String promoCatagoery =null;
		if(StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)){
			promoCatagoery=Constants.PROMO_CATEGORY_PRICING_UPGRADE_DISCOUNT;
		}
		if(StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_SECONDLINE)){
			promoCatagoery=Constants.PROMO_CATEGORY_PRICING_SECONDLINE_DISCOUNT;
		}
		Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> offeredPriceMap = entry
				.getValue();

		List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> priceForBundleAndHardwareWithOfferCodeList = null;
		if (offeredPriceMap.containsKey(deviceId)) {
			priceForBundleAndHardwareWithOfferCodeList = offeredPriceMap.get(deviceId);
		}
		if (priceForBundleAndHardwareWithOfferCodeList != null
				&& !priceForBundleAndHardwareWithOfferCodeList.isEmpty()) {
			for(com.vf.uk.dal.utility.entity.PriceForBundleAndHardware priceForBundleAndHardwareWithOfferCode : priceForBundleAndHardwareWithOfferCodeList )
			{
			/*priceForBundleAndHardwareWithOfferCodeList.forEach(priceForBundleAndHardwareWithOfferCode -> {*/
				OfferAppliedPriceDetails offerAppliedPriceDetails = new OfferAppliedPriceDetails();
				BundlePrice bundlePrice = priceForBundleAndHardwareWithOfferCode.getBundlePrice();
				com.vf.uk.dal.utility.entity.Price monthlyPrice = null;
				com.vf.uk.dal.utility.entity.Price monthlyDiscountPrice = null;
				String bundleId = null;
				String hardwareId = null;
				com.vf.uk.dal.utility.entity.Price oneoffPrice = null;
				com.vf.uk.dal.utility.entity.Price oneOffDisPrice = null;
				if (bundlePrice != null) {
					bundleId = bundlePrice.getBundleId();
					monthlyPrice = bundlePrice.getMonthlyPrice();
					monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
					if (bundlePrice.getMerchandisingPromotions() != null) {
						com.vf.uk.dal.utility.solr.entity.Media mediaLink = new com.vf.uk.dal.utility.solr.entity.Media();
						mediaLink.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
								+ Constants.STRING_OFFERS_LABEL);
						String type4 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
								+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
								+ bundlePrice.getMerchandisingPromotions().getTag();
						mediaLink.setType(type4);
						mediaLink.setValue(bundlePrice.getMerchandisingPromotions().getLabel());
						mediaLink.setDescription(Constants.DATA_NOT_FOUND);
						mediaLink.setPromoCategory(promoCatagoery);
						mediaLink.setOfferCode(Constants.DATA_NOT_FOUND);
						if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
							mediaLink.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
						} else {
							mediaLink.setDiscountId(Constants.DATA_NOT_FOUND);
						}
						String description = null;
						if (bundlePrice.getMerchandisingPromotions().getDescription() != null) {
							description = bundlePrice.getMerchandisingPromotions().getDescription();
							com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLinkForDescription.setId(bundlePrice.getMerchandisingPromotions().getMpType() + "."
									+ Constants.STRING_OFFERS_DESCRIPTION);
							String type5 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
									+ bundlePrice.getMerchandisingPromotions().getTag();
							mediaLinkForDescription.setType(type5);
							mediaLinkForDescription.setValue(description);
							mediaLinkForDescription.setDescription(description);
							mediaLinkForDescription.setPromoCategory(promoCatagoery);
							mediaLinkForDescription.setOfferCode(Constants.DATA_NOT_FOUND);
							if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLinkForDescription
										.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLinkForDescription.setDiscountId(Constants.DATA_NOT_FOUND);
							}
							listOfMedia.add(mediaLinkForDescription);
						}
						listOfMedia.add(mediaLink);
						// PriceEstablished Label
						com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablishedLabel = new com.vf.uk.dal.utility.solr.entity.Media();
						mediaLinkForPriceEstablishedLabel.setId(bundlePrice.getMerchandisingPromotions().getMpType()
								+ "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
						String type6 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
								+ Constants.PROMO_TYPE_BUNDLEPROMOTION + "&&"
								+ bundlePrice.getMerchandisingPromotions().getTag();
						mediaLinkForPriceEstablishedLabel.setType(type6);
						mediaLinkForPriceEstablishedLabel
								.setValue(bundlePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
						mediaLinkForPriceEstablishedLabel.setDescription(Constants.DATA_NOT_FOUND);
						if (StringUtils.isNotBlank(bundlePrice.getMerchandisingPromotions().getDiscountId())) {
							mediaLinkForPriceEstablishedLabel
									.setDiscountId(bundlePrice.getMerchandisingPromotions().getDiscountId());
						} else {
							mediaLinkForPriceEstablishedLabel.setDiscountId(Constants.DATA_NOT_FOUND);
						}
						mediaLinkForPriceEstablishedLabel
								.setPromoCategory(promoCatagoery);
						mediaLinkForPriceEstablishedLabel.setOfferCode(Constants.DATA_NOT_FOUND);
						listOfMedia.add(mediaLinkForPriceEstablishedLabel);
					}
				}
				com.vf.uk.dal.utility.entity.HardwarePrice hardwarePrice = priceForBundleAndHardwareWithOfferCode
						.getHardwarePrice();
				if (hardwarePrice != null) {
					hardwareId = hardwarePrice.getHardwareId();
					oneoffPrice = hardwarePrice.getOneOffPrice();
					oneOffDisPrice = hardwarePrice.getOneOffDiscountPrice();
					if (hardwarePrice.getMerchandisingPromotions() != null) {
						com.vf.uk.dal.utility.solr.entity.Media mediaLink1 = new com.vf.uk.dal.utility.solr.entity.Media();
						mediaLink1.setId(hardwarePrice.getMerchandisingPromotions().getMpType() + "."
								+ Constants.STRING_OFFERS_LABEL);
						String type6 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
								+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
								+ hardwarePrice.getMerchandisingPromotions().getTag();
						mediaLink1.setType(type6);
						mediaLink1.setValue(hardwarePrice.getMerchandisingPromotions().getLabel());
						mediaLink1.setDescription(Constants.DATA_NOT_FOUND);
						mediaLink1.setPromoCategory(promoCatagoery);
						mediaLink1.setOfferCode(Constants.DATA_NOT_FOUND);
						if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
							mediaLink1.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
						} else {
							mediaLink1.setDiscountId(Constants.DATA_NOT_FOUND);
						}

						String description = null;
						if (hardwarePrice.getMerchandisingPromotions().getDescription() != null) {
							description = hardwarePrice.getMerchandisingPromotions().getDescription();
							com.vf.uk.dal.utility.solr.entity.Media mediaLinkForDescription1 = new com.vf.uk.dal.utility.solr.entity.Media();
							mediaLinkForDescription1.setId(hardwarePrice.getMerchandisingPromotions().getMpType()
									+ "." + Constants.STRING_OFFERS_DESCRIPTION);
							String type7 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
									+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
									+ hardwarePrice.getMerchandisingPromotions().getTag();
							mediaLinkForDescription1.setType(type7);
							mediaLinkForDescription1.setValue(description);
							mediaLinkForDescription1.setDescription(description);
							mediaLinkForDescription1.setPromoCategory(promoCatagoery);
							mediaLinkForDescription1.setOfferCode(Constants.DATA_NOT_FOUND);
							if (StringUtils
									.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
								mediaLinkForDescription1
										.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
							} else {
								mediaLinkForDescription1.setDiscountId(Constants.DATA_NOT_FOUND);
							}
							listOfMedia.add(mediaLinkForDescription1);
						}
						listOfMedia.add(mediaLink1);
						// PriceEstablished Label
						com.vf.uk.dal.utility.solr.entity.Media mediaLinkForPriceEstablished = new com.vf.uk.dal.utility.solr.entity.Media();
						mediaLinkForPriceEstablished.setId(hardwarePrice.getMerchandisingPromotions().getMpType()
								+ "." + Constants.STRING_PRICE_ESTABLISHED_LABEL);
						String type8 = Constants.STRING_TEXT_ALLOWANCE + "&&" + bundleId + "&&"
								+ Constants.PROMO_TYPE_HARDWAREPROMOTION + "&&"
								+ hardwarePrice.getMerchandisingPromotions().getTag();
						mediaLinkForPriceEstablished.setType(type8);
						mediaLinkForPriceEstablished
								.setValue(hardwarePrice.getMerchandisingPromotions().getPriceEstablishedLabel());
						mediaLinkForPriceEstablished.setDescription(Constants.DATA_NOT_FOUND);
						if (StringUtils.isNotBlank(hardwarePrice.getMerchandisingPromotions().getDiscountId())) {
							mediaLinkForPriceEstablished
									.setDiscountId(hardwarePrice.getMerchandisingPromotions().getDiscountId());
						} else {
							mediaLinkForPriceEstablished.setDiscountId(Constants.DATA_NOT_FOUND);
						}
						mediaLinkForPriceEstablished.setPromoCategory(promoCatagoery);
						mediaLinkForPriceEstablished.setOfferCode(Constants.DATA_NOT_FOUND);
						listOfMedia.add(mediaLinkForPriceEstablished);
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
				offerAppliedPriceDetails.setDeviceId(hardwareId);
				offerAppliedPriceDetails.setOfferCode(Constants.DATA_NOT_FOUND);
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
	 * @author krishna.reddy
	 * @sprint-7.1
	 * @param memberPriority
	 * @param commercialProduct
	 * @param priceforBundleAndHardware
	 * @param groupType
	 * @return
	 */
	public static DeviceSummary convertCoherenceDeviceToDeviceTile_PAYG(Long memberPriority,
			CommercialProduct commercialProduct,PriceForBundleAndHardware priceforBundleAndHardware,
			String groupType) {

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
			}else{
				deviceSummary.setPreOrderable(false);
			}
		} else {
			deviceSummary.setPreOrderable(false);
		}
		
		if (memberPriority != null) {
			deviceSummary.setPriority(String.valueOf(memberPriority));
		}
		
			deviceSummary.setFromPricing(null);
		
		deviceSummary.setDisplayDescription(commercialProduct.getPreDesc());

		List<com.vodafone.product.pojo.Group> listOfSpecificationGroups = commercialProduct.getSpecificationGroups();

		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vodafone.product.pojo.Group specificationGroup : listOfSpecificationGroups) {
				if (specificationGroup.getGroupName().equalsIgnoreCase(Constants.STRING_COLOUR)) {
					List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
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
					List<com.vodafone.product.pojo.Specification> listOfSpec = specificationGroup.getSpecifications();
					if (listOfSpec != null && !listOfSpec.isEmpty()) {
						for (com.vodafone.product.pojo.Specification spec : listOfSpec) {
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
			for (com.vodafone.product.pojo.ImageURL imageURL : commercialProduct.getListOfimageURLs()) {
				if(StringUtils.isNotBlank(imageURL.getImageURL()))
				{
					mediaLink = new MediaLink();
					mediaLink.setId(imageURL.getImageName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(imageURL.getImageURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}
		if (commercialProduct.getListOfmediaURLs() != null) {
			for (com.vodafone.product.pojo.MediaURL mediaURL : commercialProduct.getListOfmediaURLs()) {
				if(StringUtils.isNotBlank(mediaURL.getMediaURL()))
				{
					mediaLink = new MediaLink();
					mediaLink.setId(mediaURL.getMediaName());
					mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
					mediaLink.setValue(mediaURL.getMediaURL());
					merchandisingMedia.add(mediaLink);
				}
			}
		}

		// MediaLink for PricePromotions
		if (priceforBundleAndHardware != null ) {
			deviceSummary.setPriceInfo(priceforBundleAndHardware);
					// Hardware Promotion
			if(priceforBundleAndHardware.getHardwarePrice()!=null){
				
					MerchandisingPromotion merchPromoForHardware = priceforBundleAndHardware.getHardwarePrice()
							.getMerchandisingPromotions();
					// Label
					MediaLink priceMediaLinkLabel = new MediaLink();
					if (merchPromoForHardware != null && StringUtils.isNotBlank(merchPromoForHardware.getMpType())) {
						if(StringUtils.isNotBlank(merchPromoForHardware.getLabel()))
						{
							priceMediaLinkLabel
									.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_LABEL);
							priceMediaLinkLabel.setType("TEXT");
							priceMediaLinkLabel.setValue(merchPromoForHardware.getLabel());
							priceMediaLinkLabel.setPriority(merchPromoForHardware.getPriority());
							merchandisingMedia.add(priceMediaLinkLabel);
						}
						// Description
						if(StringUtils.isNotBlank(merchPromoForHardware.getDescription()))
						{
							MediaLink priceMediaLinkDescription = new MediaLink();
							priceMediaLinkDescription
									.setId(merchPromoForHardware.getMpType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
							priceMediaLinkDescription.setType("TEXT");
							priceMediaLinkDescription.setValue(merchPromoForHardware.getDescription());
							priceMediaLinkDescription.setPriority(merchPromoForHardware.getPriority());
							merchandisingMedia.add(priceMediaLinkDescription);
						}
						// PriceEstablished
						if(StringUtils.isNotBlank(merchPromoForHardware.getPriceEstablishedLabel()))
						{
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

}