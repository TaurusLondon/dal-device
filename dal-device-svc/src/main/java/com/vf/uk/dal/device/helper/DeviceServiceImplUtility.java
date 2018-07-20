package com.vf.uk.dal.device.helper;

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

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;

public class DeviceServiceImplUtility {

	private DeviceServiceImplUtility() {
	};

	public static String getJourney(String journeyType) {
		boolean nonValidJourney = (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyType)
				&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyType)
				&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyType));
		String journeytype = null;
		if (StringUtils.isBlank(journeyType)
				|| StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_ACQUISITION, journeyType) || nonValidJourney) {
			journeytype = Constants.JOURNEY_TYPE_ACQUISITION;
		} else if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_UPGRADE, journeyType)) {
			journeytype = Constants.JOURNEY_TYPE_UPGRADE;
		} else if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(Constants.JOURNEY_TYPE_SECONDLINE, journeyType)) {
			journeytype = Constants.JOURNEY_TYPE_SECONDLINE;
		}
		return journeytype;
	}

	/**
	 * 
	 * @param promotionMap
	 * @param device
	 */
	public static void getPromotionForDeviceList(
			Map<String, com.vf.uk.dal.device.entity.MerchandisingPromotion> promotionMap, Device device) {
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
	public static void getPromoteAsForDevice(List<Device> deviceList, List<String> promoteAsTags) {
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
	public static String getSortCriteria(String sortCriteria) {
		String sortCriteriaLocal;
		if (StringUtils.isNotBlank(sortCriteria) && sortCriteria.startsWith(Constants.SORT_HYPEN)) {
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
	 */
	public static void getProductGroupdetailsMap(ProductGroupModel productGroupModel,
			Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap, String deviceId) {
		ProductGroupDetailsForDeviceList groupDetails = new ProductGroupDetailsForDeviceList();
		groupDetails.setGroupName(productGroupModel.getName());
		groupDetails.setGroupId(productGroupModel.getId());
		groupDetails.setColor(productGroupModel.getColour());
		groupDetails.setSize(productGroupModel.getCapacity());
		groupDetails.setColorHex(productGroupModel.getHexCode());
		productGroupdetailsMap.put(deviceId, groupDetails);

	}

	/**
	 * 
	 * @param variantsList
	 * @return ListOfEntityMembers
	 */
	public static List<com.vf.uk.dal.device.entity.Member> getListOfMembers(List<String> variantsList) {
		com.vf.uk.dal.device.entity.Member member;
		List<com.vf.uk.dal.device.entity.Member> listOfMember = new ArrayList<>();
		for (String variants : variantsList) {
			member = new com.vf.uk.dal.device.entity.Member();
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
	public static void getBundleAndHardwareList(String groupType, String journeyType,
			List<BundleAndHardwareTuple> bundleHardwareTupleList, ProductModel productModel) {
		if (StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE)
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
		} else if (groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG)
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
	public static boolean getJourneyTypevalidationForDeviceList(String journeyType) {
		return isNonUpgrade(journeyType);
	}

	/**
	 * 
	 * @param startDateTime
	 * @param productModel2
	 * @return StartdateFromProductModel
	 */
	public static Date getStartdateFromProductModel(ProductModel productModel2) {
		Date startDateTime = null;
		if (productModel2.getProductStartDate() != null) {
			try {
				startDateTime = new SimpleDateFormat(Constants.DATE_FORMAT).parse(productModel2.getProductStartDate());
			} catch (ParseException e) {
				LogHelper.error(DeviceServiceImplUtility.class, "Parse Exception: " + e);
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
	public static Date getEndDateFromProductModel(ProductModel productModel2) {
		Date endDateTime = null;
		if (productModel2.getProductEndDate() != null) {
			try {
				endDateTime = new SimpleDateFormat(Constants.DATE_FORMAT).parse(productModel2.getProductEndDate());
			} catch (ParseException ex) {
				LogHelper.error(DeviceServiceImplUtility.class, "Parse Exception: " + ex);
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
	 * @param journeyType
	 * @param memberFlag
	 * @param startDateTime
	 * @param endDateTime
	 * @param productModel2
	 * @param preOrderableFlag
	 * @return
	 */
	public static Boolean isMember(String journeyType, Date startDateTime, Date endDateTime, ProductModel productModel2,
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
	public static boolean isUpgradeCheck(String journeyType, Date startDateTime, Date endDateTime,
			ProductModel productModel2, boolean preOrderableFlag) {
		return isUpgrade(journeyType) && productModel2.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
				&& DeviceServiceImplUtility.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& isRet(productModel2);
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
	public static boolean isNonUpgradeCheck(String journeyType, Date startDateTime, Date endDateTime,
			ProductModel productModel2, boolean preOrderableFlag) {
		return isNonUpgrade(journeyType) && productModel2.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
				&& DeviceServiceImplUtility.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& isAcq(productModel2);
	}

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public static boolean isUpgrade(String journeyType) {
		return StringUtils.isNotBlank(journeyType)
				&& StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE);
	}

	/**
	 * 
	 * @param productModel2
	 * @return
	 */
	public static boolean isRet(ProductModel productModel2) {
		return Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableRet())
				&& Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableRet());
	}

	/**
	 * 
	 * @param productModel2
	 * @return
	 */
	public static boolean isAcq(ProductModel productModel2) {
		return Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsDisplayableAcq())
				&& Constants.STRING_TRUE.equalsIgnoreCase(productModel2.getIsSellableAcq());
	}

	/**
	 * 
	 * @param journeyType
	 * @return
	 */
	public static boolean isNonUpgrade(String journeyType) {
		return StringUtils.isBlank(journeyType) || (StringUtils.isNotBlank(journeyType)
				&& !StringUtils.equalsIgnoreCase(journeyType, Constants.JOURNEY_TYPE_UPGRADE));
	}

	/**
	 * 
	 * @param sortCriteria
	 * @return
	 */
	public static List<String> getSortCriteriaForList(String sortCriteria) {
		String sortBy = null;
		String sortOption = null;
		List<String> criteria = new ArrayList<>();
		if (sortCriteria != null) {
			if (sortCriteria.startsWith(Constants.SORT_HYPEN)) {
				sortOption = Constants.SORT_OPTION_DESC;
				sortBy = sortCriteria.substring(1, sortCriteria.length());
			} else if (!sortCriteria.startsWith(Constants.SORT_HYPEN)) {
				sortOption = Constants.SORT_OPTION_ASC;
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
	public static boolean validateGroupType(String groupType) {
		boolean result = groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYM)
				|| groupType.equalsIgnoreCase(Constants.STRING_DEVICE_PAYG);
		boolean result1 = groupType.equalsIgnoreCase(Constants.STRING_DEVICE_NEARLY_NEW)
				|| groupType.equalsIgnoreCase(Constants.STRING_DATADEVICE_PAYM)
				|| groupType.equalsIgnoreCase(Constants.STRING_DATADEVICE_PAYG);
		return result || result1;
	}

	/**
	 * @param journeyTypeInput
	 * @return
	 */
	public static boolean getJourneyForMakeAndModel(String journeyTypeInput) {
		return StringUtils.isBlank(journeyTypeInput)
				|| (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput));
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public static boolean getProductclassValidation(CommercialProduct comProduct) {
		return comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)
				|| comProduct.getProductClass().equalsIgnoreCase(Constants.STRING_DATA_DEVICE);
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public static boolean isNonUpgradeCommercialProduct(CommercialProduct comProduct) {
		return comProduct.getProductControl() != null && comProduct.getProductControl().isDisplayableAcq()
				&& comProduct.getProductControl().isSellableAcq();
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public static boolean isUpgradeFromCommercialProduct(CommercialProduct comProduct) {
		return comProduct.getProductControl() != null && comProduct.getProductControl().isSellableRet()
				&& comProduct.getProductControl().isDisplayableRet();
	}

	/**
	 * @param journeyTypeInput
	 * @return JourneyForVariant
	 */
	public static String getJourneyForVariant(String journeyTypeInput) {
		String journeyType;
		if (StringUtils.isBlank(journeyTypeInput)
				|| (!Constants.JOURNEY_TYPE_ACQUISITION.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_UPGRADE.equalsIgnoreCase(journeyTypeInput)
						&& !Constants.JOURNEY_TYPE_SECONDLINE.equalsIgnoreCase(journeyTypeInput))) {
			journeyType = Constants.JOURNEY_TYPE_ACQUISITION;
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
	public static Long getDevicevariantMemberPriority(String id, DeviceTile deviceTile,
			List<Group> listOfProductGroup) {
		Long memberPriority = null;
		for (Group productGroup : listOfProductGroup) {
			if (productGroup.getMembers() != null && !productGroup.getMembers().isEmpty()) {
				for (Member member : productGroup.getMembers()) {
					if (member.getId().equalsIgnoreCase(id)) {
						deviceTile.setGroupName(productGroup.getName());
						deviceTile.setGroupType(productGroup.getGroupType());
						memberPriority = Long.valueOf(member.getPriority());
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
	public static void calculateDiscount(Double creditLimit, Iterator<PriceForBundleAndHardware> iterator,
			PriceForBundleAndHardware priceForBundleAndHardware) {
		String discountType = DeviceTilesDaoUtils
				.isPartialOrFullTenureDiscount(priceForBundleAndHardware.getBundlePrice());

		if ((null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT))
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice()
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross()) {
			Double grossPrice = Double
					.parseDouble(priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross());
			if (grossPrice > creditLimit) {
				iterator.remove();
			}
		} else if ((null == discountType || (discountType.equals(Constants.LIMITED_TIME_DISCOUNT)))
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyPrice()
				&& null != priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross()) {
			Double grossPrice = new Double(priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross());
			if (grossPrice > creditLimit) {
				iterator.remove();
			}
		}
	}

	/**
	 * Check if plan is affordable as per credit limit and plan monthly price,
	 * and set flag.
	 * 
	 * @param deviceSummary
	 * @param comBundle
	 */
	public static void isPlanAffordable_Implementation(DeviceSummary deviceSummary, CommercialBundle comBundle,
			Double creditLimit, boolean isConditionalAcceptJourney) {
		if (null == comBundle) {
			deviceSummary.setIsAffordable(false);
		} else if (isConditionalAcceptJourney && null != deviceSummary.getPriceInfo()
				&& null != deviceSummary.getPriceInfo().getBundlePrice()) {
			String discountType = DeviceTilesDaoUtils
					.isPartialOrFullTenureDiscount(deviceSummary.getPriceInfo().getBundlePrice());
			Double monthlyPrice = getBundlePriceBasedOnDiscountDuration_Implementation(deviceSummary, discountType);

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
	 * @param creditDetails
	 * @param listOfPriceForBundleAndHardware
	 */
	public static boolean isPlanPriceWithinCreditLimit_Implementation(Double creditLimit,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String bundleId) {
		if (CollectionUtils.isNotEmpty(listOfPriceForBundleAndHardware)) {
			for (PriceForBundleAndHardware priceForBundleAndHardware : listOfPriceForBundleAndHardware) {
				if (null != priceForBundleAndHardware.getBundlePrice()
						&& getDiscountTypeAndComparePrice_Implementation(creditLimit,
								priceForBundleAndHardware.getBundlePrice())
						&& bundleId.equals(priceForBundleAndHardware.getBundlePrice().getBundleId())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Check if there is full or partial discount, depending on discount type
	 * get price and check if it is within credit limit.
	 * 
	 * @param creditLimit
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static boolean getDiscountTypeAndComparePrice_Implementation(Double creditLimit,
			com.vf.uk.dal.device.entity.BundlePrice bundlePrice) {
		String discountType = DeviceTilesDaoUtils.isPartialOrFullTenureDiscount(bundlePrice);
		Double grossPrice = null;
		if (null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT)) {
			if (null != bundlePrice.getMonthlyDiscountPrice()
					&& null != bundlePrice.getMonthlyDiscountPrice().getGross()) {
				grossPrice = Double.parseDouble(bundlePrice.getMonthlyDiscountPrice().getGross());
			}
		} else if ((null == discountType || (discountType.equals(Constants.LIMITED_TIME_DISCOUNT)))
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
	public static Double getBundlePriceBasedOnDiscountDuration_Implementation(DeviceSummary deviceSummary,
			String discountType) {
		Double monthlyPrice = null;
		if ((null != discountType && discountType.equals(Constants.FULL_DURATION_DISCOUNT))
				&& null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
				&& null != deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross()) {
			monthlyPrice = Double
					.parseDouble(deviceSummary.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross());
		} else if ((null == discountType || discountType.equals(Constants.LIMITED_TIME_DISCOUNT))
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
	public static Set<String> getlistofLeadBundleId(List<CommercialProduct> listOfCommercialProducts, String make,
			String model, String journeyType, Map<String, CommercialProduct> commerProdMemMap) {
		Set<String> listofLeadBundleId = new HashSet<>();
		listOfCommercialProducts.forEach(commercialProduct -> {
			if (isDataDevice(commercialProduct, make, model)) {
				String leadPlanFromCommercialProduct = commercialProduct.getLeadPlanId();
				List<String> compatiblePlans = commercialProduct.getListOfCompatiblePlanIds() == null
						? Collections.emptyList() : commercialProduct.getListOfCompatiblePlanIds();
				if (DeviceServiceImplUtility.isUpgrade(journeyType)
						&& DeviceServiceImplUtility.isUpgradeFromCommercialProduct(commercialProduct)) {
					commerProdMemMap.put(commercialProduct.getId(), commercialProduct);
					listofLeadBundleId.addAll(compatiblePlans);
				} else if (DeviceServiceImplUtility.isNonUpgrade(journeyType)
						&& DeviceServiceImplUtility.isNonUpgradeCommercialProduct(commercialProduct)) {
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
	public static boolean isDataDevice(CommercialProduct commercialProduct, String make, String model) {
		boolean result = false;
		if (DeviceServiceImplUtility.getProductclassValidation(commercialProduct)
				&& commercialProduct.getEquipment().getMake().equalsIgnoreCase(make)
				&& commercialProduct.getEquipment().getModel().equalsIgnoreCase(model)
				&& commercialProduct.getProductControl() != null) {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public static boolean isNonUpgradeCommercialBundle(CommercialBundle commercialBundle) {
		return commercialBundle.getBundleControl() != null && commercialBundle.getBundleControl().getIsSellableAcq()
				&& commercialBundle.getBundleControl().getIsDisplayableAcq();
	}

	/**
	 * 
	 * @param comProduct
	 * @return
	 */
	public static boolean isUpgradeFromCommercialBundle(CommercialBundle commercialBundle) {
		return commercialBundle.getBundleControl() != null && commercialBundle.getBundleControl().getIsSellableRet()
				&& commercialBundle.getBundleControl().getIsDisplayableRet();
	}

	/**
	 * 
	 * @author manoj.bera
	 * @param leadPlanFromCommercialProduct
	 * @param compatiblePlans
	 * @param deviceId
	 * @return BundleAndHardwareTuple
	 */
	public static Set<BundleAndHardwareTuple> getBundleHardwarePriceMap(String leadPlanFromCommercialProduct,
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
	public static void getPricingMap(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
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
	public static void getBundleAndHardwareTupleList(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
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
	public static boolean getoneOffPrice(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice) {
		return bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
				|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice() != null;
	}

	/**
	 * @param journeyType
	 * @param commercialBundle
	 * @param sellableCheck
	 * @return
	 */
	public static boolean isSellable(String journeyType, CommercialBundle commercialBundle) {
		boolean sellableCheck = false;
		if (commercialBundle != null) {
			if (Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& DeviceServiceImplUtility.isUpgradeFromCommercialBundle(commercialBundle)
					&& !commercialBundle.getAvailability().getSalesExpired()) {
				sellableCheck = true;
			}
			if (!Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)
					&& commercialBundle.getBundleControl() != null
					&& DeviceServiceImplUtility.isNonUpgradeCommercialBundle(commercialBundle)
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
	public static void getBundleHardwareTrupleList(CommercialProduct commercialProduct,
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
	public static List<String> getListOfProductIdsOrNames(String productIdOrName) {
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
	public static String getJourneyTypeForVariantAndList(String journeyTypeInput) {
		String journeyType;
		if (DeviceServiceImplUtility.getJourneyForMakeAndModel(journeyTypeInput)) {
			journeyType = Constants.JOURNEY_TYPE_ACQUISITION;
		} else {
			journeyType = journeyTypeInput;
		}
		return journeyType;
	}

	/**
	 * Date validation
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return flag
	 */
	public static Boolean dateValidationForOffers_Implementation(String startDateTime, String endDateTime,
			String strDateFormat) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();

		String currentDateStr = dateFormat.format(currentDate);

		try {
			currentDate = dateFormat.parse(currentDateStr);

		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(DeviceServiceImplUtility.class, " ParseException: " + e);
		}

		Date startDate = null;
		Date endDate = null;

		try {
			if (startDateTime != null) {
				startDate = dateFormat.parse(startDateTime);
				LogHelper.info(DeviceServiceImplUtility.class, "::::: startDate " + startDate + " :::::");
			}

		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(DeviceServiceImplUtility.class, "ParseException: " + e);
		}

		try {
			if (endDateTime != null) {
				endDate = dateFormat.parse(endDateTime);
				LogHelper.info(DeviceServiceImplUtility.class, "::::: EndDate " + endDate + " :::::");
			}
		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(DeviceServiceImplUtility.class, "ParseException: " + e);
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
	public static boolean getDateForValidation(Date currentDate, Date startDate, Date endDate) {
		return (currentDate.after(startDate) || currentDate.equals(startDate))
				&& (currentDate.before(endDate) || currentDate.equals(endDate));
	}

	public static List<PriceForBundleAndHardware> getListOfBundleAndHardwareTuple(String offerCode,
			String journeyTypeLocal, RegistryClient registryclnt,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = null;
		if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			listOfPriceForBundleAndHardware = CommonUtility.getPriceDetails(bundleAndHardwareTupleList, offerCode,
					registryclnt, journeyTypeLocal);
		}
		return listOfPriceForBundleAndHardware;
	}

	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @return LeadPlanId
	 */
	public static String getLeadPlanId(List<BundleAndHardwareTuple> bundleAndHardwareTupleList) {
		String leadPlanId = null;
		if (bundleAndHardwareTupleList != null && !bundleAndHardwareTupleList.isEmpty()) {
			leadPlanId = bundleAndHardwareTupleList.get(0).getBundleId();
			LogHelper.info(DeviceServiceImplUtility.class, "::::: LeadPlanId " + leadPlanId + " :::::");
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
	public static List<BundleAndHardwareTuple> getBundleAndHardwareTuple(String deviceId,
			CommercialProduct commercialProduct, CommercialBundle commercialBundle) {
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		if (commercialBundle != null) {
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId(commercialBundle.getId());
			bundleAndHardwareTuple.setHardwareId(deviceId);
			bundleHardwareTupleList.add(bundleAndHardwareTuple);
		} else if (commercialProduct.getProductLines() != null && !commercialProduct.getProductLines().isEmpty()
				&& commercialProduct.getProductLines().contains(Constants.PAYG_DEVICE)) {
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
	public static DeviceDetails getDeviceDetailsFinal(String deviceId, RegistryClient registryclnt,
			String journeyTypeLocal, CommercialProduct commercialProduct,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			List<BundleAndHardwareTuple> bundleHardwareTupleList) {
		List<BundleAndHardwarePromotions> promotions = null;
		DeviceDetails deviceDetails;
		if (!bundleHardwareTupleList.isEmpty()) {
			promotions = CommonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList,
					journeyTypeLocal, registryclnt);
		}
		if ((isUpgrade(journeyTypeLocal) && isUpgradeFromCommercialProduct(commercialProduct))
				|| (isNonUpgrade(journeyTypeLocal) && isNonUpgradeCommercialProduct(commercialProduct))) {
			deviceDetails = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceDetails(
					commercialProduct, listOfPriceForBundleAndHardware, promotions);
		} else {
			LogHelper.error(DeviceServiceImplUtility.class, "No data found for given journeyType :" + deviceId);
			throw new ApplicationException(ExceptionMessages.NO_DATA_FOR_GIVEN_SEARCH_CRITERIA);
		}
		return deviceDetails;
	}

}
