package com.vf.uk.dal.device.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.catalogue.DeviceOnlineModel;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.Colour;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.Member;
import com.vf.uk.dal.device.model.productgroups.ProductGroupModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * DeviceServiceImplUtility
 *
 */
@Slf4j
@Component
public class DeviceServiceImplUtility {
	private static final String ERROR_CODE_SELECT_DEVICE_DETAIL = "error_device_detail_failed";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";
	public static final String SORT_HYPEN = "-";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String STRING_HANDSET = "Handset";
	public static final String PAYG_DEVICE = "Mobile Phones, Data Devices";
	public static final String LIMITED_TIME_DISCOUNT = "limited_time";
	public static final String FULL_DURATION_DISCOUNT = "full_duration";
	public static final String STRING_DATA_DEVICE = "Data Device";
	public static final String STRING_DATADEVICE_PAYM = "DATA_DEVICE_PAYM";
	public static final String STRING_DATADEVICE_PAYG = "DATA_DEVICE_PAYG";
	public static final String SORT_OPTION_DESC = "desc";
	public static final String SORT_OPTION_ASC = "asc";
	public static final String STRING_DEVICE_NEARLY_NEW = "DEVICE_NEARLY_NEW";
	public static final String STRING_TRUE = "true";

	@Autowired
	CommonUtility commonUtility;

	@Autowired
	DeviceDetailsMakeAndModelVaiantDaoUtils deviceDetailsMakeAndModelVaiantDaoUtils;
	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;

	@Value("${cdn.domain.host}")
	private String cdnDomain;

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public String getJourney(String journeyType) {
		boolean nonValidJourney = !JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
				&& !JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyType)
				&& !JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyType);
		String journeytype = null;
		if (StringUtils.isBlank(journeyType) || StringUtils.equalsIgnoreCase(JOURNEY_TYPE_ACQUISITION, journeyType)
				|| nonValidJourney) {
			journeytype = JOURNEY_TYPE_ACQUISITION;
		} else if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(JOURNEY_TYPE_UPGRADE, journeyType)) {
			journeytype = JOURNEY_TYPE_UPGRADE;
		} else if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(JOURNEY_TYPE_SECONDLINE, journeyType)) {
			journeytype = JOURNEY_TYPE_SECONDLINE;
		}
		return journeytype;
	}

	/**
	 * 
	 * @param promotionMap
	 * @param device
	 */
	public void getPromotionForDeviceList(
			Map<String, com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion> promotionMap, Device device) {
		if (device.getPromotionsPackage().getBundlePromotions() != null
				&& device.getPromotionsPackage().getBundlePromotions().getPricePromotion() != null && promotionMap.get(
						device.getPromotionsPackage().getBundlePromotions().getPricePromotion().getTag()) != null) {
			device.getPromotionsPackage().getBundlePromotions().setPricePromotion(
					promotionMap.get(device.getPromotionsPackage().getBundlePromotions().getPricePromotion().getTag()));

		}
		if (device.getPromotionsPackage().getHardwarePromotions() != null
				&& device.getPromotionsPackage().getHardwarePromotions().getPricePromotion() != null
				&& promotionMap.get(
						device.getPromotionsPackage().getHardwarePromotions().getPricePromotion().getTag()) != null) {

			device.getPromotionsPackage().getHardwarePromotions().setPricePromotion(promotionMap
					.get(device.getPromotionsPackage().getHardwarePromotions().getPricePromotion().getTag()));

		}
	}

	/**
	 * 
	 * @param deviceList
	 * @param promoteAsTags
	 */
	public void getPromoteAsForDevice(List<Device> deviceList, List<String> promoteAsTags) {
		deviceList.forEach(device -> {
			if (device.getPromotionsPackage() != null) {
				if (device.getPromotionsPackage().getBundlePromotions() != null
						&& device.getPromotionsPackage().getBundlePromotions().getPricePromotion() != null) {
					promoteAsTags.add(device.getPromotionsPackage().getBundlePromotions().getPricePromotion().getTag());
				}
				if (device.getPromotionsPackage().getHardwarePromotions() != null
						&& device.getPromotionsPackage().getHardwarePromotions().getPricePromotion() != null) {
					promoteAsTags
							.add(device.getPromotionsPackage().getHardwarePromotions().getPricePromotion().getTag());
				}
			}
		});
	}

	/**
	 * 
	 * @param sortCriteria
	 * @return SortCriteria
	 */
	public String getSortCriteria(String sortCriteria) {
		String sortCriteriaLocal;
		if (StringUtils.isNotBlank(sortCriteria) && sortCriteria.startsWith(SORT_HYPEN)) {
			sortCriteriaLocal = sortCriteria.substring(1);
		} else {
			sortCriteriaLocal = sortCriteria;
		}
		return sortCriteriaLocal;
	}

	/**
	 * 
	 * @param productGroupModel
	 * @param productGroupdetailsMap
	 * @param deviceId
	 * @param journeyType
	 */
	public void getProductGroupdetailsMap(ProductGroupModel productGroupModel,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, String deviceId, String journeyType) {
		ProductGroupDetailsForDeviceList groupDetails = new ProductGroupDetailsForDeviceList();
		List<String> colourHex = new ArrayList<>();
		List<String> size = new ArrayList<>();
		log.info("Product group ID {}", productGroupModel.getId());
		if (StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
			if (productGroupModel.getUpgradeColor() != null
					&& CollectionUtils.isNotEmpty(productGroupModel.getUpgradeColor())) {
				colourHex.addAll(productGroupModel.getUpgradeColor());
			}
			if (productGroupModel.getUpgradeCapacity() != null
					&& CollectionUtils.isNotEmpty(productGroupModel.getUpgradeCapacity())) {
				size.addAll(productGroupModel.getUpgradeCapacity());
			}
		} else {
			if (productGroupModel.getAcqColor() != null
					&& CollectionUtils.isNotEmpty(productGroupModel.getAcqColor())) {
				colourHex.addAll(productGroupModel.getAcqColor());
			}
			if (productGroupModel.getAcqCapacity() != null
					&& CollectionUtils.isNotEmpty(productGroupModel.getAcqCapacity())) {
				size.addAll(productGroupModel.getAcqCapacity());
			}
		}
		List<Colour> colours = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(colourHex)) {
			colourHex.forEach(colour -> {
				String[] colorHex = colour.split("\\|");
				if (colorHex.length == 2) {
					Colour color = new Colour();
					color.setColorName(colorHex[0]);
					color.setColorHex(colorHex[1]);
					colours.add(color);
				}
			});
		}
		groupDetails.setGroupName(productGroupModel.getName());
		groupDetails.setGroupId(productGroupModel.getId());
		groupDetails.setColor(colours);
		groupDetails.setSize(size);
		productGroupdetailsMap.put(deviceId, groupDetails);

	}

	/**
	 * 
	 * @param productGroupModel
	 * @param productGroupdetailsMap
	 * @param deviceId
	 * @param journeyType
	 */
	public void getProductGroupdetailsMapForHandsetOnlineModel(DeviceOnlineModel productGroupModel,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, String deviceId, String journeyType) {
		ProductGroupDetailsForDeviceList groupDetails = new ProductGroupDetailsForDeviceList();
		List<Colour> colours = new ArrayList<>();
		if (StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)) {
			if (productGroupModel.getColorNameAndHexUpgrade() != null) {
				productGroupModel.getColorNameAndHexUpgrade().stream().forEach(colourHex -> {
					Colour color = new Colour();
					color.setColorName(colourHex.getColorName());
					color.setColorHex(colourHex.getColorHex());
					colours.add(color);
				});
			}
			groupDetails.setSize(productGroupModel.getSizeUpgrade());
		} else {
			if (productGroupModel.getColorNameAndHex() != null) {
				productGroupModel.getColorNameAndHex().stream().forEach(colourHex -> {
					Colour color = new Colour();
					color.setColorName(colourHex.getColorName());
					color.setColorHex(colourHex.getColorHex());
					colours.add(color);
				});
			}
			groupDetails.setSize(productGroupModel.getSize());
		}
		groupDetails.setGroupName(productGroupModel.getProductGroupName());
		groupDetails.setGroupId(productGroupModel.getProductGroupId().toString());
		groupDetails.setColor(colours);
		productGroupdetailsMap.put(deviceId, groupDetails);

	}

	/**
	 * 
	 * @param variantsList
	 * @return ListOfEntityMembers
	 */
	public List<com.vf.uk.dal.device.model.Member> getListOfMembers(List<String> variantsList) {
		com.vf.uk.dal.device.model.Member member;
		List<com.vf.uk.dal.device.model.Member> listOfMember = new ArrayList<>();
		for (String variants : variantsList) {
			member = new com.vf.uk.dal.device.model.Member();
			String[] variantIdPriority = variants.split("\\|");
			member.setId(variantIdPriority[0]);
			member.setPriority(variantIdPriority[1]);
			listOfMember.add(member);
		}
		return listOfMember;
	}

	/**
	 * 
	 * @param groupType
	 * @param journeyType
	 * @param bundleHardwareTupleList
	 * @param productModel
	 */
	public void getBundleAndHardwareList(String groupType, String journeyType,
			List<BundleAndHardwareTuple> bundleHardwareTupleList, ProductModel productModel) {
		if (StringUtils.isNotBlank(journeyType) && StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE)
				&& StringUtils.isNotBlank(productModel.getUpgradeLeadPlanId())
				&& productModel.getUpgradeLeadPlanId().length() == 6) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(productModel.getUpgradeLeadPlanId());
			bundleAndHardwareTuple.setHardwareId(productModel.getProductId());
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		} else if (getJourneyTypevalidationForDeviceList(journeyType)
				&& StringUtils.isNotBlank(productModel.getNonUpgradeLeadPlanId())
				&& productModel.getNonUpgradeLeadPlanId().length() == 6) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(productModel.getNonUpgradeLeadPlanId());
			bundleAndHardwareTuple.setHardwareId(productModel.getProductId());
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		} else if (groupType.equalsIgnoreCase(STRING_DEVICE_PAYG)
				&& getJourneyTypevalidationForDeviceList(journeyType)) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(null);
			bundleAndHardwareTuple.setHardwareId(productModel.getProductId());
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		}
	}

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public boolean getJourneyTypevalidationForDeviceList(String journeyType) {
		return isNonUpgrade(journeyType);
	}

	/**
	 * 
	 * @param startDateTime
	 * @param productModel2
	 * @return StartdateFromProductModel
	 */
	public Date getStartdateFromProductModel(ProductModel productModel2) {
		Date startDateTime = null;
		if (productModel2.getProductStartDate() != null) {
			try {
				startDateTime = new SimpleDateFormat(DATE_FORMAT).parse(productModel2.getProductStartDate());
			} catch (ParseException e) {
				log.error("Parse Exception: " + e);
			}
		}
		return startDateTime;
	}

	/**
	 * 
	 * @param endDateTime
	 * @param productModel2
	 * @return EndDateFromProductModel
	 */
	public Date getEndDateFromProductModel(ProductModel productModel2) {
		Date endDateTime = null;
		if (productModel2.getProductEndDate() != null) {
			try {
				endDateTime = new SimpleDateFormat(DATE_FORMAT).parse(productModel2.getProductEndDate());
			} catch (ParseException ex) {
				log.error("Parse Exception: " + ex);
			}
		}
		return endDateTime;
	}

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param preOrderableFlag
	 * @return flag
	 */
	public Boolean dateValidation(Date startDateTime, Date endDateTime, boolean preOrderableFlag) {
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
	 * @param journeyType
	 * @param memberFlag
	 * @param startDateTime
	 * @param endDateTime
	 * @param productModel2
	 * @param preOrderableFlag
	 * @return
	 */
	public Boolean isMember(String journeyType, Date startDateTime, Date endDateTime, ProductModel productModel2,
			boolean preOrderableFlag) {
		Boolean memberFlag = false;
		if (isNonUpgradeCheck(journeyType, startDateTime, endDateTime, productModel2, preOrderableFlag)
				|| isUpgradeCheck(journeyType, startDateTime, endDateTime, productModel2, preOrderableFlag)) {
			memberFlag = true;
		}
		return memberFlag;
	}

	/**
	 * 
	 * @param journeyType
	 * @param startDateTime
	 * @param endDateTime
	 * @param productModel2
	 * @param preOrderableFlag
	 * @return
	 */
	public boolean isUpgradeCheck(String journeyType, Date startDateTime, Date endDateTime, ProductModel productModel2,
			boolean preOrderableFlag) {
		return isUpgrade(journeyType) && productModel2.getProductClass().equalsIgnoreCase(STRING_HANDSET)
				&& dateValidation(startDateTime, endDateTime, preOrderableFlag) && isRet(productModel2);
	}

	/**
	 * 
	 * @param journeyType
	 * @param startDateTime
	 * @param endDateTime
	 * @param productModel2
	 * @param preOrderableFlag
	 * @return
	 */
	public boolean isNonUpgradeCheck(String journeyType, Date startDateTime, Date endDateTime,
			ProductModel productModel2, boolean preOrderableFlag) {
		return isNonUpgrade(journeyType) && productModel2.getProductClass().equalsIgnoreCase(STRING_HANDSET)
				&& dateValidation(startDateTime, endDateTime, preOrderableFlag) && isAcq(productModel2);
	}

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public boolean isUpgrade(String journeyType) {
		return StringUtils.isNotBlank(journeyType) && StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE);
	}

	/**
	 * 
	 * @param productModel2
	 * @return
	 */
	public boolean isRet(ProductModel productModel2) {
		return STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableRet())
				&& STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableRet());
	}

	/**
	 * 
	 * @param productModel2
	 * @return
	 */
	public boolean isAcq(ProductModel productModel2) {
		return STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableAcq())
				&& STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableAcq());
	}

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public boolean isNonUpgrade(String journeyType) {
		return StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
				&& !StringUtils.equalsIgnoreCase(journeyType, JOURNEY_TYPE_UPGRADE));
	}

	/**
	 * 
	 * @param sortCriteria
	 * @return
	 */
	public List<String> getSortCriteriaForList(String sortCriteria) {
		String sortBy = null;
		String sortOption = null;
		List<String> criteria = new ArrayList<>();
		if (sortCriteria != null) {
			if (sortCriteria.startsWith(SORT_HYPEN)) {
				sortOption = SORT_OPTION_DESC;
				sortBy = sortCriteria.substring(1, sortCriteria.length());
			} else if (!sortCriteria.startsWith(SORT_HYPEN)) {
				sortOption = SORT_OPTION_ASC;
				sortBy = sortCriteria;
			}
			criteria.add(sortOption);
			criteria.add(sortBy);
		}
		return criteria;
	}

	/**
	 * @param groupType
	 * @return
	 */
	public boolean validateGroupType(String groupType) {
		boolean result = groupType.equalsIgnoreCase(STRING_DEVICE_PAYM)
				|| groupType.equalsIgnoreCase(STRING_DEVICE_PAYG);
		boolean result1 = groupType.equalsIgnoreCase(STRING_DEVICE_NEARLY_NEW)
				|| groupType.equalsIgnoreCase(STRING_DATADEVICE_PAYM)
				|| groupType.equalsIgnoreCase(STRING_DATADEVICE_PAYG);
		return result || result1;
	}

	/**
	 * @param journeyTypeInput
	 * @return
	 */
	public boolean getJourneyForMakeAndModel(String journeyTypeInput) {
		return StringUtils.isBlank(journeyTypeInput) || (!JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
				&& !JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
				&& !JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput));
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public boolean getProductclassValidation(CommercialProduct comProduct) {
		return comProduct.getProductClass().equalsIgnoreCase(STRING_HANDSET)
				|| comProduct.getProductClass().equalsIgnoreCase(STRING_DATA_DEVICE);
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public boolean isNonUpgradeCommercialProduct(CommercialProduct comProduct) {
		return comProduct.getProductControl() != null && comProduct.getProductControl().isDisplayableAcq()
				&& comProduct.getProductControl().isSellableAcq();
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public boolean isUpgradeFromCommercialProduct(CommercialProduct comProduct) {
		return comProduct.getProductControl() != null && comProduct.getProductControl().isSellableRet()
				&& comProduct.getProductControl().isDisplayableRet();
	}

	/**
	 * @param journeyTypeInput
	 * @return JourneyForVariant
	 */
	public String getJourneyForVariant(String journeyTypeInput) {
		String journeyType;
		if (StringUtils.isBlank(journeyTypeInput) || (!JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
				&& !JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
				&& !JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput))) {
			journeyType = JOURNEY_TYPE_ACQUISITION;
		} else {
			journeyType = journeyTypeInput;
		}
		return journeyType;
	}

	/**
	 * 
	 * @param id
	 * @param deviceTile
	 * @param listOfProductGroup
	 * @return
	 */
	public Long getDevicevariantMemberPriority(String id, DeviceTile deviceTile, List<Group> listOfProductGroup) {
		Long memberPriority = null;
		for (Group productGroup : listOfProductGroup) {
			if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
				for (Member member : productGroup.getMembers()) {
					if (member.getId().equalsIgnoreCase(id)) {
						deviceTile.setGroupName(productGroup.getName());
						deviceTile.setGroupType(productGroup.getGroupType());
						memberPriority = member.getPriority();
					}
				}
			}

		}
		return memberPriority;
	}

	/**
	 * @param creditLimit
	 * @param iterator
	 * @param priceForBundleAndHardware
	 */
	public void calculateDiscount(Double creditLimit, Iterator<PriceForBundleAndHardware> iterator,
			PriceForBundleAndHardware priceForBundleAndHardware) {
		String discountType = deviceTilesDaoUtils
				.isPartialOrFullTenureDiscount(priceForBundleAndHardware.getBundlePrice());

		if ((null != discountType && discountType.equals(FULL_DURATION_DISCOUNT))
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice()
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross()) {
			Double grossPrice = Double
					.parseDouble(priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross());
			if (grossPrice > creditLimit) {
				iterator.remove();
			}
		} else if ((null == discountType || (discountType.equals(LIMITED_TIME_DISCOUNT)))
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyPrice()
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross()) {
			Double grossPrice = new Double(priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross());
			if (grossPrice > creditLimit) {
				iterator.remove();
			}
		}
	}

	/**
	 * 
	 * @param deviceSummary
	 * @param comBundle
	 * @param creditLimit
	 * @param isConditionalAcceptJourney
	 */
	public void isPlanAffordableImplementation(DeviceSummary deviceSummary, CommercialBundle comBundle,
			Double creditLimit, boolean isConditionalAcceptJourney) {
		if (null == comBundle) {
			deviceSummary.setIsAffordable(false);
		} else if (isConditionalAcceptJourney && null != deviceSummary.getPriceInfo()
				&& null != deviceSummary.getPriceInfo().getBundlePrice()) {
			String discountType = deviceTilesDaoUtils
					.isPartialOrFullTenureDiscount(deviceSummary.getPriceInfo().getBundlePrice());
			Double monthlyPrice = getBundlePriceBasedOnDiscountDurationImplementation(deviceSummary, discountType);

			if (null != monthlyPrice && monthlyPrice > creditLimit) {
				deviceSummary.setIsAffordable(false);
			} else {
				deviceSummary.setIsAffordable(true);
				deviceSummary.setLeadPlanId(deviceSummary.getPriceInfo().getBundlePrice().getBundleId());
				deviceSummary.setBundleType(comBundle.getDisplayGroup());
				deviceSummary.setLeadPlanDisplayName(comBundle.getDisplayName());
			}
		}
	}

	/**
	 * 
	 * @param creditLimit
	 * @param listOfPriceForBundleAndHardware
	 * @param bundleId
	 * @return
	 */
	public boolean isPlanPriceWithinCreditLimitImplementation(Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String bundleId) {
		if (CollectionUtils.isNotEmpty(listOfPriceForBundleAndHardware)) {
			for (PriceForBundleAndHardware priceForBundleAndHardware : listOfPriceForBundleAndHardware) {
				if (null != priceForBundleAndHardware.getBundlePrice()
						&& getDiscountTypeAndComparePriceImplementation(creditLimit,
								priceForBundleAndHardware.getBundlePrice())
						&& bundleId.equals(priceForBundleAndHardware.getBundlePrice().getBundleId())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * @param creditLimit
	 * @param bundlePrice
	 * @return
	 */
	public boolean getDiscountTypeAndComparePriceImplementation(Double creditLimit,
			com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice) {
		String discountType = deviceTilesDaoUtils.isPartialOrFullTenureDiscount(bundlePrice);
		Double grossPrice = null;
		if (null != discountType && discountType.equals(FULL_DURATION_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyDiscountPrice()
					&& null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
				grossPrice = Double.parseDouble(bundlePrice.getMonthlyDiscountPrice().getGross());
			}
		} else if ((null == discountType || (discountType.equals(LIMITED_TIME_DISCOUNT)))
				&& null != bundlePrice.getMonthlyPrice() && null != bundlePrice.getMonthlyPrice().getGross()) {
			grossPrice = new Double(bundlePrice.getMonthlyPrice().getGross());
		}
		return (null != grossPrice && grossPrice <= creditLimit);
	}

	/**
	 * 
	 * @param deviceSummary
	 * @param discountType
	 * @return
	 */
	public Double getBundlePriceBasedOnDiscountDurationImplementation(DeviceSummary deviceSummary,
			String discountType) {
		Double monthlyPrice = null;
		if ((null != discountType && discountType.equals(FULL_DURATION_DISCOUNT))
				&& null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
				&& null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross()) {
			monthlyPrice = Double
					.parseDouble(deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross());
		} else if ((null == discountType || discountType.equals(LIMITED_TIME_DISCOUNT))
				&& null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyPrice()
				&& StringUtils.isNotBlank(deviceSummary.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross())) {
			monthlyPrice = Double
					.parseDouble(deviceSummary.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross());
		}
		return monthlyPrice;
	}

	/**
	 * 
	 * @param listOfCommercialProducts
	 * @param make
	 * @param model
	 * @param journeyType
	 * @param commerProdMemMap
	 * @return listofLeadBundleId
	 */
	public Set<String> getlistofLeadBundleId(List<CommercialProduct> listOfCommercialProducts, String make,
			String model, String journeyType, Map<String, CommercialProduct> commerProdMemMap) {
		Set<String> listofLeadBundleId = new HashSet<>();
		listOfCommercialProducts.forEach(commercialProduct -> {
			if (isDataDevice(commercialProduct, make, model)) {
				String leadPlanFromCommercialProduct = commercialProduct.getLeadPlanId();
				List<String> compatiblePlans = commercialProduct.getListOfCompatiblePlanIds() == null
						? Collections.emptyList() : commercialProduct.getListOfCompatiblePlanIds();
				if (isUpgrade(journeyType) && isUpgradeFromCommercialProduct(commercialProduct)) {
					commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
					listofLeadBundleId.addAll(compatiblePlans);
				} else if (isNonUpgrade(journeyType) && isNonUpgradeCommercialProduct(commercialProduct)) {
					commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
					if (StringUtils.isNotBlank(leadPlanFromCommercialProduct)) {
						listofLeadBundleId.add(leadPlanFromCommercialProduct);
					}
					listofLeadBundleId.addAll(compatiblePlans);

				}
			}
		});
		return listofLeadBundleId;

	}

	/**
	 * 
	 * @param commercialProduct
	 * @param make
	 * @param model
	 * @return
	 */
	public boolean isDataDevice(CommercialProduct commercialProduct, String make, String model) {
		boolean result = false;
		if (getProductclassValidation(commercialProduct)
				&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
				&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)
				&& commercialProduct.getProductControl() != null) {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * @param commercialBundle
	 * @return
	 */
	public boolean isNonUpgradeCommercialBundle(CommercialBundle commercialBundle) {
		return commercialBundle.getBundleControl() != null && commercialBundle.getBundleControl().isSellableAcq()
				&& commercialBundle.getBundleControl().isDisplayableAcq();
	}

	/**
	 * 
	 * @param commercialBundle
	 * @return
	 */
	public boolean isUpgradeFromCommercialBundle(CommercialBundle commercialBundle) {
		return commercialBundle.getBundleControl() != null && commercialBundle.getBundleControl().isSellableRet()
				&& commercialBundle.getBundleControl().isDisplayableRet();
	}

	/**
	 * 
	 * @author manoj.bera
	 * @param leadPlanFromCommercialProduct
	 * @param compatiblePlans
	 * @param deviceId
	 * @return BundleAndHardwareTuple
	 */
	public Set<BundleAndHardwareTuple> getBundleHardwarePriceMap(String leadPlanFromCommercialProduct,
			List<String> compatiblePlans, String deviceId) {
		Set<BundleAndHardwareTuple> setOfBundleAndHardwareTuple = new HashSet<>();
		if (StringUtils.isNotBlank(leadPlanFromCommercialProduct)) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(leadPlanFromCommercialProduct);
			bundleAndHardwareTuple.setHardwareId(deviceId);
			setOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
		} else if (CollectionUtils.isNotEmpty(compatiblePlans)) {
			compatiblePlans.forEach(plan -> {
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(plan);
				bundleAndHardwareTuple.setHardwareId(deviceId);
				setOfBundleAndHardwareTuple.add(bundleAndHardwareTuple);
			});
		}
		return setOfBundleAndHardwareTuple;
	}

	/**
	 * @param bundleAndHardwareTupleList
	 * @param fromPricingMap
	 * @param leadPlanIdMap
	 * @param listofLeadPlan
	 * @param commercialProduct
	 */
	public void getPricingMap(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> fromPricingMap, Map<String, String> leadPlanIdMap, List<String> listofLeadPlan,
			CommercialProduct commercialProduct) {
		Set<BundleAndHardwareTuple> setOfBundleAndHardwareTuple = getBundleHardwarePriceMap(
				commercialProduct.getLeadPlanId(), null, commercialProduct.getId());
		bundleAndHardwareTupleList.addAll(setOfBundleAndHardwareTuple);
		leadPlanIdMap.put(commercialProduct.getId(), commercialProduct.getLeadPlanId());
		listofLeadPlan.add(commercialProduct.getLeadPlanId());
		fromPricingMap.put(commercialProduct.getId(), false);
	}

	/**
	 * @param bundleAndHardwareTupleList
	 * @param fromPricingMap
	 * @param commercialProduct
	 */
	public void getBundleAndHardwareTupleList(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			Map<String, Boolean> fromPricingMap, CommercialProduct commercialProduct) {
		Set<BundleAndHardwareTuple> setOfBundleAndHardwareTuple = getBundleHardwarePriceMap(null,
				commercialProduct.getListOfCompatiblePlanIds(), commercialProduct.getId());
		fromPricingMap.put(commercialProduct.getId(), true);
		bundleAndHardwareTupleList.addAll(setOfBundleAndHardwareTuple);
	}

	/**
	 * 
	 * @param bundleHeaderForDevice
	 * @return
	 */
	public boolean getoneOffPrice(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice) {
		return bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
				|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice() != null;
	}

	/**
	 * @param journeyType
	 * @param commercialBundle
	 * @param sellableCheck
	 * @return
	 */
	public boolean isSellable(String journeyType, CommercialBundle commercialBundle) {
		boolean sellableCheck = false;
		if (commercialBundle != null) {
			if (JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyType) && isUpgradeFromCommercialBundle(commercialBundle)
					&& !commercialBundle.getAvailability().getSalesExpired()) {
				sellableCheck = true;
			}
			if (!JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyType) && commercialBundle.getBundleControl() != null
					&& isNonUpgradeCommercialBundle(commercialBundle)
					&& !commercialBundle.getAvailability().getSalesExpired()) {
				sellableCheck = true;
			}
		}
		return sellableCheck;
	}

	/**
	 * 
	 * @param commercialProduct
	 * @param bundleAndHardwareTupleList
	 */
	public void getBundleHardwareTrupleList(CommercialProduct commercialProduct,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		BundleAndHardwareTuple bundleAndHardwareTuple;
		bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
		bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
		bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
	}

	/**
	 * 
	 * @param productIdOrName
	 * @return
	 */
	public List<String> getListOfProductIdsOrNames(String productIdOrName) {
		List<String> listOfProdIdsOrNames = new ArrayList<>();
		if (productIdOrName.contains(",")) {
			String[] prodIdsOrNames = productIdOrName.split(",");
			listOfProdIdsOrNames = Arrays.asList(prodIdsOrNames);
		} else {
			listOfProdIdsOrNames.add(productIdOrName);
		}
		return listOfProdIdsOrNames;
	}

	/**
	 * @param journeyTypeInput
	 * @return
	 */
	public String getJourneyTypeForVariantAndList(String journeyTypeInput) {
		String journeyType;
		if (getJourneyForMakeAndModel(journeyTypeInput)) {
			journeyType = JOURNEY_TYPE_ACQUISITION;
		} else {
			journeyType = journeyTypeInput;
		}
		return journeyType;
	}

	/**
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param strDateFormat
	 * @return
	 */
	public Boolean dateValidationForOffersImplementation(String startDateTime, String endDateTime,
			String strDateFormat) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();

		String currentDateStr = dateFormat.format(currentDate);

		try {
			currentDate = dateFormat.parse(currentDateStr);

		} catch (ParseException | DateTimeParseException e) {
			log.error(" ParseException: " + e);
		}

		Date startDate = null;
		Date endDate = null;

		try {
			if (startDateTime != null) {
				startDate = dateFormat.parse(startDateTime);
				log.info("::::: startDate " + startDate + " ::::::");
			}

		} catch (ParseException | DateTimeParseException e) {
			log.error("ParseException: " + e);
		}

		try {
			if (endDateTime != null) {
				endDate = dateFormat.parse(endDateTime);
				log.info("::::: EndDate " + endDate + " :::::::");
			}
		} catch (ParseException | DateTimeParseException e) {
			log.error("ParseException: " + e);
		}

		if (startDate != null && endDate != null && getDateForValidation(currentDate, startDate, endDate)) {
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

	/**
	 * 
	 * @param currentDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean getDateForValidation(Date currentDate, Date startDate, Date endDate) {
		return (currentDate.after(startDate) || currentDate.equals(startDate))
				&& (currentDate.before(endDate) || currentDate.equals(endDate));
	}

	/**
	 * 
	 * @param offerCode
	 * @param journeyTypeLocal
	 * @param registryclnt
	 * @param bundleAndHardwareTupleList
	 * @return
	 */
	public List<PriceForBundleAndHardware> getListOfBundleAndHardwareTuple(String offerCode, String journeyTypeLocal,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
		if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			listOfPriceForBundleAndHardware = commonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
					journeyTypeLocal, null);
		}
		return listOfPriceForBundleAndHardware;
	}

	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @return LeadPlanId
	 */
	public String getLeadPlanId(List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		String leadPlanId = null;
		if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
			log.info("::::: LeadPlanId " + leadPlanId + " :::::");
		}
		return leadPlanId;
	}

	/**
	 * 
	 * @param deviceId
	 * @param commercialProduct
	 * @param commercialBundle
	 * @return List<BundleAndHardwareTuple>
	 */
	public List<BundleAndHardwareTuple> getBundleAndHardwareTuple(String deviceId, CommercialProduct commercialProduct,
			CommercialBundle commercialBundle) {
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		if (commercialBundle != null) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(commercialBundle.getId());
			bundleAndHardwareTuple.setHardwareId(deviceId);
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		} else if (commercialProduct.getProductLines() != null && !commercialProduct.getProductLines().isEmpty()
				&& commercialProduct.getProductLines().contains(PAYG_DEVICE)) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(null);
			bundleAndHardwareTuple.setHardwareId(deviceId);
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		}
		return bundleHardwareTupleList;
	}

	/**
	 * 
	 * @param deviceId
	 * @param registryclnt
	 * @param journeyTypeLocal
	 * @param commercialProduct
	 * @param listOfPriceForBundleAndHardware
	 * @param bundleHardwareTupleList
	 * @return DeviceDetails
	 */
	public DeviceDetails getDeviceDetailsFinal(String deviceId, String journeyTypeLocal,
			CommercialProduct commercialProduct, List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			List<BundleAndHardwareTuple> bundleHardwareTupleList) {
		List<BundleAndHardwarePromotions> promotions = null;
		DeviceDetails deviceDetails;
		if (!bundleHardwareTupleList.isEmpty()) {
			promotions = commonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
					journeyTypeLocal);
		}
		if ((isUpgrade(journeyTypeLocal) && isUpgradeFromCommercialProduct(commercialProduct))
				|| (isNonUpgrade(journeyTypeLocal) && isNonUpgradeCommercialProduct(commercialProduct))) {
			deviceDetails = deviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceDetails(
					commercialProduct, listOfPriceForBundleAndHardware, promotions, cdnDomain);
		} else {
			log.error("No data found for given journeyType :" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE_DETAIL,
					ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA, "404");
		}
		return deviceDetails;
	}
}