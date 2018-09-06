package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ItemAttribute;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.Attributes;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.Specification;
import com.vf.uk.dal.device.entity.SpecificationGroup;
import com.vf.uk.dal.utility.entity.PriceForAccessory;

public class AccessoriesAndInsurancedaoUtils {

	 AccessoriesAndInsurancedaoUtils() {}

	/**
	 * 
	 * @param commercialProduct
	 * @param priceForAccessory
	 * @return Accessory
	 */
	public static Accessory convertCoherenceAccesoryToAccessory(CommercialProduct commercialProduct,
			PriceForAccessory priceForAccessory, String cdnDomain) {
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
										accessory.setColour(spec.getValue());
									}
								}

							}
						}
					}
				}

				MediaLink mediaLink;
				if (commercialProduct.getListOfimageURLs() != null) {
					for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : commercialProduct
							.getListOfimageURLs()) {
						if (StringUtils.isNotBlank(imageURL.getImageURL())) {
							mediaLink = new MediaLink();
							mediaLink.setId(imageURL.getImageName());
							mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaLink.setValue(CommonUtility.getImageMediaUrl(cdnDomain,imageURL.getImageURL()));
							merchandisingMedia.add(mediaLink);
						}
					}
				}
				if (commercialProduct.getListOfmediaURLs() != null) {
					for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : commercialProduct
							.getListOfmediaURLs()) {
						if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
							mediaLink = new MediaLink();
							mediaLink.setId(mediaURL.getMediaName());
							mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
							mediaLink.setValue(CommonUtility.getImageMediaUrl(cdnDomain,mediaURL.getMediaURL()));
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
						if (StringUtils.isNotBlank(item.getKey())) {
							attribute = new Attributes();
							attribute.setKey(item.getKey());
							attribute.setType(item.getType());
							attribute.setValue(item.getValue());
							attribute.setValueUOM(item.getValueUOM());
							attList.add(attribute);
						}
					}
					accessory.setAttributes(attList.isEmpty() ? null : attList);
				}
			}
		}
		return accessory;
	}

	/**
	 * 
	 * @param hardwarePrice
	 * @return List<MediaLink>
	 */
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
	 * @author manoj.bera
	 * @param priceForAccessory
	 * @return PriceForAccessory
	 */
	public static PriceForAccessory getPriceForAccessory(PriceForAccessory priceForAccessory) {
		if (priceForAccessory != null && priceForAccessory.getHardwarePrice() != null
				&& getOneOffPriceCheck(priceForAccessory)
				&& priceForAccessory.getHardwarePrice().getOneOffPrice().getGross()
						.equalsIgnoreCase(priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().getGross())) {
			priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().setGross(null);
			priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().setNet(null);
			priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().setVat(null);
		}
		return priceForAccessory;
	}

	/**
	 * 
	 * @param priceForAccessory
	 * @return
	 */
	public static boolean getOneOffPriceCheck(PriceForAccessory priceForAccessory) {
		return priceForAccessory.getHardwarePrice().getOneOffPrice() != null
				&& priceForAccessory.getHardwarePrice().getOneOffDiscountPrice() != null
				&& priceForAccessory.getHardwarePrice().getOneOffPrice().getGross() != null
				&& priceForAccessory.getHardwarePrice().getOneOffDiscountPrice().getGross() != null;
	}

	/**
	 * 
	 * @param insuranceProductList
	 * @return Insurances
	 */
	public static Insurances convertCommercialProductToInsurance(List<CommercialProduct> insuranceProductList, String cdnDomain) {
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
				for (com.vf.uk.dal.device.datamodel.product.ImageURL imageURL : insuranceProduct.getListOfimageURLs()) {

					if (StringUtils.isNotBlank(imageURL.getImageURL())) {
						mediaLink = new MediaLink();

						mediaLink.setId(imageURL.getImageName());
						mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(CommonUtility.getImageMediaUrl(cdnDomain,imageURL.getImageURL()));
						merchandisingMedia.add(mediaLink);
					}
				}
			}
			if (insuranceProduct.getListOfmediaURLs() != null) {
				for (com.vf.uk.dal.device.datamodel.product.MediaURL mediaURL : insuranceProduct.getListOfmediaURLs()) {
					if (StringUtils.isNotBlank(mediaURL.getMediaURL())) {
						mediaLink = new MediaLink();
						mediaLink.setId(mediaURL.getMediaName());
						mediaLink.setType(MediaConstants.STRING_FOR_MEDIA_TYPE);
						mediaLink.setValue(CommonUtility.getImageMediaUrl(cdnDomain,mediaURL.getMediaURL()));
						merchandisingMedia.add(mediaLink);
					}
				}
			}

			insurance.setMerchandisingMedia(merchandisingMedia);

			List<com.vf.uk.dal.device.datamodel.product.Group> listOfSpecificationGroups = insuranceProduct
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
							specification.setPriority(spec.getPriority().intValue());
							specification.setComparable(spec.getComparable());
							specification.setIsKey(spec.getIsKey());
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

}
