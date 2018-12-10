package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForAccessory;
import com.vf.uk.dal.device.model.Accessory;
import com.vf.uk.dal.device.model.Attributes;
import com.vf.uk.dal.device.model.Insurance;
import com.vf.uk.dal.device.model.Insurances;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.Specification;
import com.vf.uk.dal.device.model.SpecificationGroup;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ItemAttribute;

public class AccessoriesAndInsurancedaoUtils {

	public static final String STRING_PRICE_ESTABLISHED_LABEL = "merchandisingPromotions.merchandisingPromotion.priceEstablishedLabel";
	public static final String STRING_OFFERS_LABEL = "merchandisingPromotions.merchandisingPromotion.label";
	public static final String STRING_OFFERS_DESCRIPTION = "merchandisingPromotions.merchandisingPromotion.description";
	public static final String STRING_COLOUR = "Colour";
	public static final String STRING_TEXT = "TEXT";

	AccessoriesAndInsurancedaoUtils() {
	}

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

			com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice = priceForAccessory.getHardwarePrice();
			accessory = setMerchandisingMediaListAndAccessory(commercialProduct, priceForAccessory, merchandisingMedia,
					hardwarePrice);

			if (accessory != null) {
				List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups = commercialProduct
						.getSpecificationGroups();
				setColourForAccessory(accessory, listOfSpecificationGroups);

				CommonUtility.getImageMediaLink(commercialProduct, merchandisingMedia, cdnDomain);
				/**
				 * @author manoj.bera below for loop not required Null check
				 *         done before
				 */

				accessory.setMerchandisingMedia(merchandisingMedia);

				List<Attributes> attList = new ArrayList<>();
				setAttributesForAccessory(commercialProduct, accessory, attList);
			}
		}
		return accessory;
	}

	private static void setAttributesForAccessory(CommercialProduct commercialProduct, Accessory accessory,
			List<Attributes> attList) {
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

	private static void setColourForAccessory(Accessory accessory,
			List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups) {
		if (listOfSpecificationGroups != null && !listOfSpecificationGroups.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Group specificationGroup : listOfSpecificationGroups) {
				if (specificationGroup.getGroupName().equalsIgnoreCase(STRING_COLOUR)) {
					List<com.vf.uk.dal.device.model.product.Specification> listOfSpec = specificationGroup
							.getSpecifications();
					setColour(accessory, listOfSpec);
				}
			}
		}
	}

	private static void setColour(Accessory accessory,
			List<com.vf.uk.dal.device.model.product.Specification> listOfSpec) {
		if (listOfSpec != null && !listOfSpec.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Specification spec : listOfSpec) {
				if (spec.getName().equalsIgnoreCase(STRING_COLOUR)) {
					accessory.setColour(spec.getValue());
				}
			}

		}
	}

	private static Accessory setMerchandisingMediaListAndAccessory(CommercialProduct commercialProduct,
			PriceForAccessory priceForAccessory, List<MediaLink> merchandisingMedia,
			com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice) {
		Accessory accessory = null;
		if (hardwarePrice != null && hardwarePrice.getHardwareId().equalsIgnoreCase(commercialProduct.getId())) {
			List<MediaLink> mediList = setPriceMerchandisingPromotion(hardwarePrice);
			if (CollectionUtils.isNotEmpty(mediList) && !mediList.isEmpty()) {
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
		return accessory;
	}

	/**
	 * 
	 * @param hardwarePrice
	 * @return List<MediaLink>
	 */
	public static List<MediaLink> setPriceMerchandisingPromotion(
			com.vf.uk.dal.device.client.entity.price.HardwarePrice hardwarePrice) {
		List<MediaLink> merchandisingMedia = new ArrayList<>();
		com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion merchandisingPromotions = hardwarePrice
				.getMerchandisingPromotions();
		if (merchandisingPromotions != null) {
			if (StringUtils.isNotBlank(merchandisingPromotions.getLabel())) {
				MediaLink mediaLinkLabel = new MediaLink();
				mediaLinkLabel.setId(merchandisingPromotions.getMpType() + "." + STRING_OFFERS_LABEL);
				mediaLinkLabel.setType(STRING_TEXT);
				mediaLinkLabel.setValue(merchandisingPromotions.getLabel());
				merchandisingMedia.add(mediaLinkLabel);
			}
			if (StringUtils.isNotBlank(merchandisingPromotions.getDescription())) {
				MediaLink mediaLinkPriceEstablishedLabel = new MediaLink();
				mediaLinkPriceEstablishedLabel
						.setId(merchandisingPromotions.getMpType() + "." + STRING_OFFERS_DESCRIPTION);
				mediaLinkPriceEstablishedLabel.setType(STRING_TEXT);
				mediaLinkPriceEstablishedLabel.setValue(merchandisingPromotions.getDescription());
				merchandisingMedia.add(mediaLinkPriceEstablishedLabel);
			}
			if (StringUtils.isNotBlank(merchandisingPromotions.getPriceEstablishedLabel())) {
				MediaLink mediaLinkPriceEstablishedLabel = new MediaLink();
				mediaLinkPriceEstablishedLabel
						.setId(merchandisingPromotions.getMpType() + "." + STRING_PRICE_ESTABLISHED_LABEL);
				mediaLinkPriceEstablishedLabel.setType(STRING_TEXT);
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
	public static Insurances convertCommercialProductToInsurance(List<CommercialProduct> insuranceProductList,
			String cdnDomain) {
		List<Double> minPrice = new ArrayList<>();
		List<Insurance> insuranceList = new ArrayList<>();
		Insurances insurances = new Insurances();

		Insurance insurance;
		for (CommercialProduct insuranceProduct : insuranceProductList) {
			insurance = new Insurance();
			insurance.setId(insuranceProduct.getId());
			insurance.setName(insuranceProduct.getDisplayName());
			Price price = new Price();
			setMinPriceList(minPrice, insuranceProduct);
			price.setGross(String.valueOf(insuranceProduct.getPriceDetail().getPriceGross()));
			price.setVat(String.valueOf(insuranceProduct.getPriceDetail().getPriceVAT()));
			price.setNet(String.valueOf(insuranceProduct.getPriceDetail().getPriceNet()));
			insurance.setPrice(price);
			List<MediaLink> merchandisingMedia = new ArrayList<>();
			CommonUtility.getImageMediaLink(insuranceProduct, merchandisingMedia, cdnDomain);

			insurance.setMerchandisingMedia(merchandisingMedia);

			List<com.vf.uk.dal.device.model.product.Group> listOfSpecificationGroups = insuranceProduct
					.getSpecificationGroups();
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
					setSpecificationGroup(listOfSpecification, specificationGroups, listOfSpec);
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

	private static void setSpecificationGroup(List<Specification> listOfSpecification,
			SpecificationGroup specificationGroups, List<com.vf.uk.dal.device.model.product.Specification> listOfSpec) {
		Specification specification;
		if (listOfSpec != null && !listOfSpec.isEmpty()) {
			for (com.vf.uk.dal.device.model.product.Specification spec : listOfSpec) {
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
	}

	private static void setMinPriceList(List<Double> minPrice, CommercialProduct insuranceProduct) {
		if (insuranceProduct.getPriceDetail() != null && insuranceProduct.getPriceDetail().getPriceGross() != null) {
			minPrice.add(insuranceProduct.getPriceDetail().getPriceGross());
		}
	}

}
