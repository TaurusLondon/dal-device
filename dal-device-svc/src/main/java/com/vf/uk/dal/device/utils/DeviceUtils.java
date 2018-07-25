package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails;

/**
 * Contains utility methods for bundle svc
 *
 */
public class DeviceUtils {
	static Double selectedPlanAlwnce = null;

	/**
	 * 
	 * @param bndlDtls
	 * @param allowedRecurringPriceLimit
	 * @param bundleId
	 * @param plansLimit
	 * @param bndlDtlsWithoutFullDuration
	 * @return BundleDetails
	 */
	

	

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Double getDoubleFrmString(String value) {
		Double dValue = null;
		if (StringUtils.isNotBlank(value))
			dValue = Double.valueOf(value);
		return dValue;
	}

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static String getmonthlyPriceFormPrice(PriceForBundleAndHardware priceForBundleAndHardware) {
		String monthlyPrice = null;
		if (priceForBundleAndHardware != null && priceForBundleAndHardware.getBundlePrice() != null) {
			if (priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
					&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
				monthlyPrice = priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross();
			} else {
				monthlyPrice = priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross();
			}
		}
		return monthlyPrice;
	}

	/**
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return List<PriceForBundleAndHardware>
	 */
	public static List<PriceForBundleAndHardware> sortedPriceForBundleAndHardware(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware,
				(PriceForBundleAndHardware bh1, PriceForBundleAndHardware bh2) -> {
					Double index1 = getDoubleFrmString(getmonthlyPriceFormPrice(bh1));
					Double index2 = getDoubleFrmString(getmonthlyPriceFormPrice(bh2));
					return Double.compare(index1, index2);
				});
		return listOfPriceForBundleAndHardware;
	}

	/**
	 * \
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public static String leastMonthlyPrice(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareAfterSorted = sortedPriceForBundleAndHardware(
				listOfPriceForBundleAndHardware);
		return getmonthlyPriceFormPrice(listOfPriceForBundleAndHardwareAfterSorted.get(0));
	}

	/**
	 * \
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return leastMonthlyPriceForpayG
	 */
	public static String leastMonthlyPriceForpayG(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareAfterSorted = sortedPriceForBundleAndHardwareForPayG(
				listOfPriceForBundleAndHardware);
		return getmonthlyPriceFormPriceForPayG(listOfPriceForBundleAndHardwareAfterSorted.get(0));
	}

	/**
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return List<PriceForBundleAndHardware>
	 */
	public static List<PriceForBundleAndHardware> sortedPriceForBundleAndHardwareForPayG(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware,
				(PriceForBundleAndHardware bh1, PriceForBundleAndHardware bh2) -> {
					Double index1 = getDoubleFrmString(getmonthlyPriceFormPriceForPayG(bh1));
					Double index2 = getDoubleFrmString(getmonthlyPriceFormPriceForPayG(bh2));
					return Double.compare(index1, index2);
				});
		return listOfPriceForBundleAndHardware;
	}

	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static String getmonthlyPriceFormPriceForPayG(PriceForBundleAndHardware priceForBundleAndHardware) {
		String oneOffPrice = null;
		if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null) {
			if (priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
				oneOffPrice = priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross();
			} else {
				oneOffPrice = priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross();
			}
		}
		return oneOffPrice;
	}

	/**
	 * 
	 * @param objectsToOrder
	 * @param orderedObjects
	 * @return List<ProductModel>
	 */
	public List<ProductModel> sortListForProductModel(List<ProductModel> objectsToOrder, List<String> orderedObjects) {
		HashMap<String, Integer> indexMap = new HashMap<>();
		int index = 0;
		for (String object : orderedObjects) {
			indexMap.put(object, index);
			index++;
		}
		Collections.sort(objectsToOrder, (ProductModel left, ProductModel right) -> {
			Integer leftIndex = indexMap.get(left.getProductId());
			Integer rightIndex = indexMap.get(right.getProductId());
			if (leftIndex == null && rightIndex == null) {

				return 1;
			}
			if (leftIndex == null) {

				return 1;
			}
			if (rightIndex == null) {

				return -1;
			}
			return Integer.compare(leftIndex, rightIndex);

		});
		return objectsToOrder;
	}

	

	/**
	 * 
	 * @param listOfDeviceId
	 * @param groupIdAndNameMap
	 * @param productGroup
	 * @param listOfDeviceGroupMember
	 * @param groupId
	 * @param groupname
	 */
	public static void getMemberForCaceDevice(List<String> listOfDeviceId, Map<String, String> groupIdAndNameMap,
			Group productGroup, List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember, String groupId,
			String groupname) {
		com.vf.uk.dal.device.entity.Member entityMember;
		for (Member member : productGroup.getMembers()) {
			entityMember = new com.vf.uk.dal.device.entity.Member();
			entityMember.setId(member.getId());
			entityMember.setPriority(String.valueOf(member.getPriority()));
			listOfDeviceGroupMember.add(entityMember);
			listOfDeviceId.add(member.getId());
			groupIdAndNameMap.put(member.getId(), groupname + "&&" + groupId);
		}
	}

	/**
	 * 
	 * @param setOfCompatiblePlanIds
	 * @param bundleAndHardwareTupleList
	 * @param bundleAndHardwareTupleListForNonLeanPlanId
	 * @param bundleAndHardwareTupleListJourneyAware
	 * @param listOfLeadPlanId
	 * @param listOfCimpatiblePlanMap
	 * @param listOfCommercialProduct
	 */
	public static void getBundleharwareTrupleForPaymCacheDevice(Set<String> setOfCompatiblePlanIds,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId,
			Set<BundleAndHardwareTuple> bundleAndHardwareTupleListJourneyAware, Map<String, String> listOfLeadPlanId,
			Map<String, List<String>> listOfCimpatiblePlanMap, List<CommercialProduct> listOfCommercialProduct) {
		listOfCommercialProduct.forEach(commercialProduct -> {
			List<String> listOfCompatiblePlanIds = commercialProduct.getListOfCompatiblePlanIds() == null
					|| commercialProduct.getListOfCompatiblePlanIds().isEmpty() ? Collections.emptyList()
							: commercialProduct.getListOfCompatiblePlanIds();
			if (!listOfCompatiblePlanIds.isEmpty()) {
				List<String> compatibleBundleIds = commercialProduct.getListOfCompatiblePlanIds();
				listOfCimpatiblePlanMap.put(commercialProduct.getId(), compatibleBundleIds);
				setOfCompatiblePlanIds.addAll(compatibleBundleIds);
				compatibleBundleIds.forEach(compatiblePlanId -> {
					BundleAndHardwareTuple bundleAndHardwareTupleLocal = new BundleAndHardwareTuple();
					bundleAndHardwareTupleLocal.setBundleId(compatiblePlanId);
					bundleAndHardwareTupleLocal.setHardwareId(commercialProduct.getId());
					bundleAndHardwareTupleListForNonLeanPlanId.add(bundleAndHardwareTupleLocal);
					bundleAndHardwareTupleListJourneyAware.add(bundleAndHardwareTupleLocal);
				});
			}
			if (StringUtils.isNotBlank(commercialProduct.getLeadPlanId())
					&& listOfCompatiblePlanIds.contains(commercialProduct.getLeadPlanId())) {
				listOfLeadPlanId.put(commercialProduct.getId(), commercialProduct.getLeadPlanId());
				BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(commercialProduct.getLeadPlanId());
				bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
				bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
				bundleAndHardwareTupleListJourneyAware.add(bundleAndHardwareTuple);
			}
		});
	}

	/**
	 * 
	 * @param leadPlanIdPriceMap
	 * @param listOfPriceForBundleAndHardwareForLeadPlanIds
	 */
	public static void getLeadPlanMap(Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForLeadPlanIds) {
		listOfPriceForBundleAndHardwareForLeadPlanIds.forEach(priceForBundleAndHardware -> {

			if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null) {
				leadPlanIdPriceMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(),
						priceForBundleAndHardware);
			}
		});
	}

	/**
	 * 
	 * @param nonLeadPlanIdPriceMap
	 * @param listOfPriceForBundleAndHardwareForNonLeadPlanIds
	 */
	public static void getNonLeadPlanMap(Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForNonLeadPlanIds) {
		listOfPriceForBundleAndHardwareForNonLeadPlanIds.forEach(priceForBundleAndHardware -> {
			if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null) {
				List<PriceForBundleAndHardware> price = null;
				if (nonLeadPlanIdPriceMap.containsKey(priceForBundleAndHardware.getHardwarePrice().getHardwareId())) {
					price = nonLeadPlanIdPriceMap.get(priceForBundleAndHardware.getHardwarePrice().getHardwareId());
					price.add(priceForBundleAndHardware);
				} else {
					price = new ArrayList<>();
					price.add(priceForBundleAndHardware);
					nonLeadPlanIdPriceMap.put(priceForBundleAndHardware.getHardwarePrice().getHardwareId(), price);
				}
			}
		});
	}

	/**
	 * 
	 * @param groupNamePriceMap
	 * @param listOfPriceForBundleAndHardware
	 * @return nonUpgradeLeadPlanId
	 */
	public static String getGroupNamePriceMap(Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware,
			PriceForBundleAndHardware bundleHeaderForDevice, String groupname) {
		String nonUpgradeLeadPlanId;
		List<PriceForBundleAndHardware> listOfPriceForGroupName;
		listOfPriceForBundleAndHardware.add(bundleHeaderForDevice);
		nonUpgradeLeadPlanId = bundleHeaderForDevice.getBundlePrice().getBundleId();
		LogHelper.info(DeviceUtils.class,
				"Lead Plan Id Not Present " + bundleHeaderForDevice.getBundlePrice().getBundleId());
		if (groupNamePriceMap.containsKey(groupname)) {
			listOfPriceForGroupName = groupNamePriceMap.get(groupname);
			listOfPriceForGroupName.add(bundleHeaderForDevice);

		} else {
			listOfPriceForGroupName = new ArrayList<>();
			listOfPriceForGroupName.add(bundleHeaderForDevice);
			groupNamePriceMap.put(groupname, listOfPriceForGroupName);
		}
		return nonUpgradeLeadPlanId;
	}

	/**
	 * 
	 * @param leadPlanIdPriceMap
	 * @param groupNamePriceMap
	 * @param listOfPriceForBundleAndHardware
	 * @param deviceId
	 * @param groupname
	 */
	public static void getLeadPlanGroupPriceMap(Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware, String deviceId, String groupname) {
		List<PriceForBundleAndHardware> listOfPriceForGroupName;
		listOfPriceForBundleAndHardware.add(leadPlanIdPriceMap.get(deviceId));
		if (groupNamePriceMap.containsKey(groupname)) {
			listOfPriceForGroupName = groupNamePriceMap.get(groupname);
			listOfPriceForGroupName.add(leadPlanIdPriceMap.get(deviceId));
		} else {
			listOfPriceForGroupName = new ArrayList<>();
			listOfPriceForGroupName.add(leadPlanIdPriceMap.get(deviceId));
			groupNamePriceMap.put(groupname, listOfPriceForGroupName);
		}
	}

	/**
	 * 
	 * @param listOfOfferCodes
	 * @param commercialBundleMap
	 * @param listOfCimpatiblePlanMap
	 * @param bundleHardwareTroupleMap
	 * @param deviceId
	 */
	public static void getIlsPriceMap(Set<String> listOfOfferCodes, Map<String, CommercialBundle> commercialBundleMap,
			Map<String, List<String>> listOfCimpatiblePlanMap,
			Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap, String deviceId) {
		List<String> deviceSpecificCompatiblePlan = null;
		if (listOfCimpatiblePlanMap.containsKey(deviceId)) {
			deviceSpecificCompatiblePlan = listOfCimpatiblePlanMap.get(deviceId);
			if (deviceSpecificCompatiblePlan != null && !deviceSpecificCompatiblePlan.isEmpty()) {
				deviceSpecificCompatiblePlan.forEach(planId -> {
					CommercialBundle commercialBundle = commercialBundleMap.get(planId);
					String bundleId = commercialBundle.getId();
					List<String> listOfPromoteAs = commercialBundle.getPromoteAs() == null ? Collections.emptyList()
							: commercialBundle.getPromoteAs().getPromotionName();
					if (listOfPromoteAs != null && !listOfPromoteAs.isEmpty()) {
						getIlsBundleHardwarePriceMap(listOfOfferCodes, bundleHardwareTroupleMap, deviceId, bundleId,
								listOfPromoteAs);
					}
				});
			}
		}
	}

	/**
	 * 
	 * @param listOfOfferCodes
	 * @param bundleHardwareTroupleMap
	 * @param deviceId
	 * @param bundleId
	 * @param listOfPromoteAs
	 */
	public static void getIlsBundleHardwarePriceMap(Set<String> listOfOfferCodes,
			Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap, String deviceId, String bundleId,
			List<String> listOfPromoteAs) {
		listOfPromoteAs.forEach(promoteAs -> {
			if (listOfOfferCodes.contains(promoteAs)) {
				List<BundleAndHardwareTuple> bundleAndHardwareTupleListOffer = null;
				BundleAndHardwareTuple bundleAndHardwareTupleOffer = new BundleAndHardwareTuple();
				bundleAndHardwareTupleOffer.setBundleId(bundleId);
				bundleAndHardwareTupleOffer.setHardwareId(deviceId);
				if (bundleHardwareTroupleMap.containsKey(promoteAs)) {
					bundleAndHardwareTupleListOffer = bundleHardwareTroupleMap.get(promoteAs);
					bundleAndHardwareTupleListOffer.add(bundleAndHardwareTupleOffer);
				} else {
					bundleAndHardwareTupleListOffer = new ArrayList<>();
					bundleAndHardwareTupleListOffer.add(bundleAndHardwareTupleOffer);
					bundleHardwareTroupleMap.put(promoteAs, bundleAndHardwareTupleListOffer);
				}
			}
		});
	}

	/**
	 * 
	 * @param listOfOfferCodesForUpgrade
	 * @param listOfSecondLineOfferCode
	 * @param entry
	 * @return jouneyType
	 */
	public static String getJourneybasedOnOfferCode(List<String> listOfOfferCodesForUpgrade,
			List<String> listOfSecondLineOfferCode, Entry<String, List<BundleAndHardwareTuple>> entry) {
		String jouneyType = null;
		if ((listOfOfferCodesForUpgrade.contains(entry.getKey()) && listOfSecondLineOfferCode.contains(entry.getKey()))
				|| (listOfOfferCodesForUpgrade.contains(entry.getKey()))) {
			jouneyType = Constants.JOURNEY_TYPE_UPGRADE;
		} else if (listOfSecondLineOfferCode.contains(entry.getKey())) {
			jouneyType = Constants.JOURNEY_TYPE_SECONDLINE;
		}
		return jouneyType;
	}

	/**
	 * 
	 * @param ilsPriceForJourneyAwareOfferCodeMap
	 * @param jouneyType
	 * @param entry
	 * @param iLSPriceMapLocalMain
	 * @param listOfPriceForBundleAndHardwareForOffer
	 */
	public static void getIlsPriceForJourneyAwareOfferCodeMap(
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap,
			String jouneyType, Entry<String, List<BundleAndHardwareTuple>> entry,
			Map<String, List<PriceForBundleAndHardware>> iLSPriceMapLocalMain,
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareForOffer) {
		if (listOfPriceForBundleAndHardwareForOffer != null && !listOfPriceForBundleAndHardwareForOffer.isEmpty()) {
			iLSPriceMapLocalMain.put(entry.getKey(), listOfPriceForBundleAndHardwareForOffer);
			if (ilsPriceForJourneyAwareOfferCodeMap.containsKey(jouneyType)) {
				Map<String, List<PriceForBundleAndHardware>> iLSPriceMapLocal = ilsPriceForJourneyAwareOfferCodeMap
						.get(jouneyType);
				iLSPriceMapLocal.putAll(iLSPriceMapLocalMain);
				ilsPriceForJourneyAwareOfferCodeMap.put(jouneyType, iLSPriceMapLocal);
			} else {
				ilsPriceForJourneyAwareOfferCodeMap.put(jouneyType, iLSPriceMapLocalMain);
			}
		}
	}

	/**
	 * 
	 * @param ilsOfferPriceWithJourneyAware
	 * @param ilsPriceForJourneyAwareOfferCodeMap
	 */
	public static void getEntireIlsJourneyAwareMap(
			Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForJourneyAwareOfferCodeMap) {
		for (Entry<String, Map<String, List<PriceForBundleAndHardware>>> journeyEntry : ilsPriceForJourneyAwareOfferCodeMap
				.entrySet()) {

			Map<String, Map<String, List<PriceForBundleAndHardware>>> entireOfferedPriceMap = new HashMap<>();

			Map<String, List<PriceForBundleAndHardware>> ilsPriceJourney = journeyEntry.getValue();
			if (!ilsPriceJourney.isEmpty()) {
				for (Entry<String, List<PriceForBundleAndHardware>> entry : ilsPriceJourney.entrySet()) {
					Map<String, List<PriceForBundleAndHardware>> mapOfDevicePrice = new HashMap<>();
					if (entry.getValue() != null && !entry.getValue().isEmpty()) {
						List<PriceForBundleAndHardware> offerdPrice = entry.getValue();
						offerdPrice.forEach(price -> getDeviceIlsJourneyAwarePriceMap(mapOfDevicePrice, price));
					}
					entireOfferedPriceMap.put(entry.getKey(), mapOfDevicePrice);
				}
			}
			ilsOfferPriceWithJourneyAware.put(journeyEntry.getKey(), entireOfferedPriceMap);
		}
	}

	/**
	 * 
	 * @param mapOfDevicePrice
	 * @param price
	 */
	public static void getDeviceIlsJourneyAwarePriceMap(Map<String, List<PriceForBundleAndHardware>> mapOfDevicePrice,
			PriceForBundleAndHardware price) {
		List<PriceForBundleAndHardware> listOfDevicePrice = null;
		if (price != null && price.getHardwarePrice() != null) {
			if (mapOfDevicePrice.containsKey(price.getHardwarePrice().getHardwareId())) {
				listOfDevicePrice = mapOfDevicePrice.get(price.getHardwarePrice().getHardwareId());
				listOfDevicePrice.add(price);
			} else {
				listOfDevicePrice = new ArrayList<>();
				listOfDevicePrice.add(price);
				mapOfDevicePrice.put(price.getHardwarePrice().getHardwareId(), listOfDevicePrice);
			}
		}
	}

	/**
	 * 
	 * @param minimumPriceMap
	 * @param groupNamePriceMap
	 * @return
	 */
	public static void getMinimumPriceMap(Map<String, String> minimumPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap) {
		String minimumPrice = null;
		for (Entry<String, List<PriceForBundleAndHardware>> entry : groupNamePriceMap.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty()) {
				minimumPrice = DeviceUtils.leastMonthlyPrice(entry.getValue());
			}
			minimumPriceMap.put(entry.getKey(), minimumPrice);
		}
	}

	/**
	 * 
	 * @param minimumPriceMap
	 * @param groupNamePriceMap
	 * @return
	 */
	public static void getMinimumPriceMapForPayG(Map<String, String> minimumPriceMap,
			Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap) {
		String minimumPrice = null;
		for (Entry<String, List<PriceForBundleAndHardware>> entry : groupNamePriceMap.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty()) {
				minimumPrice = DeviceUtils.leastMonthlyPriceForpayG(entry.getValue());
			}
			minimumPriceMap.put(entry.getKey(), minimumPrice);
		}
	}

	/**
	 * 
	 * @param minimumPriceMap
	 * @param ilsOfferPriceWithJourneyAware
	 * @param mapOfIlsPriceWithoutOfferCode
	 * @param ratingsReviewMap
	 * @param deviceDataRating
	 */
	@SuppressWarnings("unchecked")
	public static void updateDevicePrecaldataBasedOnIlsPriceAndRating(Map<String, String> minimumPriceMap,
			Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware,
			Map<String, Map<String, List<PriceForBundleAndHardware>>> mapOfIlsPriceWithoutOfferCode,
			Map<String, String> ratingsReviewMap, DevicePreCalculatedData deviceDataRating) {
		com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfo = deviceDataRating.getPriceInfo();
		Map<String, Object> offeredPriceMediaMap = CacheDeviceDaoUtils
				.getListOfOfferAppliedPrice(deviceDataRating.getDeviceId(), ilsOfferPriceWithJourneyAware);
		List<OfferAppliedPriceDetails> iLSPrice = (List<OfferAppliedPriceDetails>) offeredPriceMediaMap
				.get("offeredPrice");
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfOfferdMedia = (List<com.vf.uk.dal.utility.solr.entity.Media>) offeredPriceMediaMap
				.get("media");
		Map<String, Object> withoutOfferedPriceMediaMap = CacheDeviceDaoUtils
				.getListOfIlsPriceWithoutOfferCode(deviceDataRating.getDeviceId(), mapOfIlsPriceWithoutOfferCode);
		List<OfferAppliedPriceDetails> iLSPriceWithoutOfferCode = (List<OfferAppliedPriceDetails>) withoutOfferedPriceMediaMap
				.get("offeredPrice");
		List<com.vf.uk.dal.utility.solr.entity.Media> listOfdMediaWithoutOfferCode = (List<com.vf.uk.dal.utility.solr.entity.Media>) withoutOfferedPriceMediaMap
				.get("media");
		if (iLSPriceWithoutOfferCode != null && !iLSPriceWithoutOfferCode.isEmpty()) {
			iLSPrice.addAll(iLSPriceWithoutOfferCode);
		}
		if (listOfdMediaWithoutOfferCode != null && !listOfdMediaWithoutOfferCode.isEmpty()) {
			listOfOfferdMedia.addAll(listOfdMediaWithoutOfferCode);
		}
		if (iLSPrice != null && !iLSPrice.isEmpty()) {
			if (priceInfo == null) {
				com.vf.uk.dal.utility.solr.entity.PriceInfo priceInfoLocal = new com.vf.uk.dal.utility.solr.entity.PriceInfo();
				priceInfoLocal.setOfferAppliedPrices(iLSPrice);
				deviceDataRating.setPriceInfo(priceInfoLocal);
			} else {
				priceInfo.setOfferAppliedPrices(iLSPrice);
			}

		}
		if (listOfOfferdMedia != null && !listOfOfferdMedia.isEmpty()) {
			List<com.vf.uk.dal.utility.solr.entity.Media> listOfMedia = deviceDataRating.getMedia();
			if (listOfMedia == null) {
				deviceDataRating.setMedia(listOfOfferdMedia);
			} else {
				listOfMedia.addAll(listOfOfferdMedia);
			}

		}
		if (minimumPriceMap.containsKey(deviceDataRating.getProductGroupName())) {
			deviceDataRating.setMinimumCost(minimumPriceMap.get(deviceDataRating.getProductGroupName()));
		}
		updateDeviceratingForCacheDevice(ratingsReviewMap, deviceDataRating);
	}

	/**
	 * 
	 * @param ratingsReviewMap
	 * @param deviceDataRating
	 */
	public static void updateDeviceratingForCacheDevice(Map<String, String> ratingsReviewMap,
			DevicePreCalculatedData deviceDataRating) {
		if (ratingsReviewMap.containsKey(CommonUtility.appendPrefixString(deviceDataRating.getDeviceId()))) {
			String avarageOverallRating = ratingsReviewMap
					.get(CommonUtility.appendPrefixString(deviceDataRating.getDeviceId()));
			if (avarageOverallRating != null && !Constants.DEVICE_RATING_NA.equalsIgnoreCase(avarageOverallRating)) {
				deviceDataRating.setRating(Float.parseFloat(avarageOverallRating));
			} else {
				deviceDataRating.setRating(null);
			}
		} else {
			deviceDataRating.setRating(null);
		}
	}

}
