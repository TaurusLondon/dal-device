package com.vf.uk.dal.device.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.BazaarVoice;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.CoupleRelation;

@Component
public class DeviceServiceCommonUtility {

	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	DeviceESHelper deviceEs;
	
	@Autowired
	RegistryClient registryclnt;
	/**
	 * @param deviceTile
	 * @param leadMemberId
	 */
	public String getDeviceTileRating(String leadMemberId) {
		Map<String, String> rating = getDeviceReviewRating_Implementation(new ArrayList<>(Arrays.asList(leadMemberId)));
		String avarageOverallRating = rating.containsKey(CommonUtility.appendPrefixString(leadMemberId))
				? rating.get(CommonUtility.appendPrefixString(leadMemberId)) : "na";
		LogHelper.info(this, "AvarageOverallRating for deviceId: " + leadMemberId + " Rating: " + avarageOverallRating);
		return avarageOverallRating;
	}
	/**
	 * 
	 * @param listMemberIds
	 * @return
	 */
	public Map<String, String> getDeviceReviewRating_Implementation(List<String> listMemberIds) {

		List<BazaarVoice> response = getReviewRatingList_Implementation(listMemberIds);
		HashMap<String, String> bvReviewAndRateMap = new HashMap<>();
		try {
			for (BazaarVoice bazaarVoice : response) {
				if (bazaarVoice != null) {
					if (!bazaarVoice.getJsonsource().isEmpty()) {
						org.json.JSONObject jSONObject = new org.json.JSONObject(bazaarVoice.getJsonsource());
						if (!jSONObject.get("Includes").toString().equals("{}")) {
							org.json.JSONObject includes = jSONObject.getJSONObject("Includes");
							org.json.JSONObject products = includes.getJSONObject("Products");
							Iterator<?> level = products.keys();
							while (level.hasNext()) {
								String key = (String) level.next();
								org.json.JSONObject skuId = products.getJSONObject(key);
								org.json.JSONObject reviewStatistics = (null != skuId)
										? skuId.getJSONObject("ReviewStatistics") : null;
								Double averageOverallRating = (null != reviewStatistics)
										? (Double) reviewStatistics.get("AverageOverallRating") : null;
								if (!bvReviewAndRateMap.keySet().contains(key)) {
									String overallRating = (null != averageOverallRating)
											? averageOverallRating.toString() : "na";
									bvReviewAndRateMap.put(key, overallRating);
								}
							}
						} else {
							bvReviewAndRateMap.put(bazaarVoice.getId(), "na");
						}
					} else {
						bvReviewAndRateMap.put(bazaarVoice.getId(), "na");
					}
				}
			}
		} catch (Exception e) {
			LogHelper.error(this, "Failed to get device review ratings, Exception: " + e);
			throw new ApplicationException(ExceptionMessages.BAZAARVOICE_RESPONSE_EXCEPTION);
		}
		return bvReviewAndRateMap;
	}
	
	/**
	 * 
	 * @param listMemberIds
	 * @return
	 */
	public List<BazaarVoice> getReviewRatingList_Implementation(List<String> listMemberIds) {

		try {
			LogHelper.info(this, "Start -->  calling  BazaarReviewRepository.get");
			List<BazaarVoice> response = new ArrayList<>();
			for (String skuId : listMemberIds) {
				response.add(deviceDao.getBazaarVoice(skuId));
			}
			LogHelper.info(this, "End --> After calling  BazaarReviewRepository.get");
			return response;
		} catch (Exception e) {
			LogHelper.error(this, "Bazar Voice Exception: " + e);
			throw new ApplicationException(ExceptionMessages.BAZARVOICE_SERVICE_EXCEPTION);
		}
	}
	/**
	 * Identifies members based on the validation rules.
	 * 
	 * @param listOfDeviceGroupMembers
	 * @return leadDeviceSkuId
	 */
	public String getMemeberBasedOnRules_Implementation(
			List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember, String journeyType) {
		String leadDeviceSkuId = null;
		DeviceTilesDaoUtils daoUtils = new DeviceTilesDaoUtils();
		List<com.vf.uk.dal.device.entity.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.entity.Member member : listOfSortedMember) {
			if (validateMemeber_Implementation(member.getId(), journeyType)) {
				leadDeviceSkuId = member.getId();
				break;
			}
		}
		return leadDeviceSkuId;
	}
	/**
	 * validates the member based on the memberId.
	 * 
	 * @param memberId
	 * @return memberFlag
	 */
	public Boolean validateMemeber_Implementation(String memberId, String journeyType) {
		Boolean memberFlag = false;

		LogHelper.info(this, " Start -->  calling  CommercialProductRepository.get");
		CommercialProduct comProduct = deviceEs.getCommercialProduct(memberId);
		LogHelper.info(this, " End -->  After calling  CommercialProductRepository.get");

		Date startDateTime = comProduct.getProductAvailability().getStart();
		Date endDateTime = comProduct.getProductAvailability().getEnd();
		boolean preOrderableFlag = comProduct.getProductControl().isPreOrderable();
		boolean isUpgrade = DeviceServiceImplUtility.isUpgrade(journeyType)
				&& DeviceServiceImplUtility.getProductclassValidation(comProduct)
				&& DeviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& DeviceServiceImplUtility.isUpgradeFromCommercialProduct(comProduct);
		if (isUpgrade || (DeviceServiceImplUtility.getProductclassValidation(comProduct)
				&& DeviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& DeviceServiceImplUtility.isNonUpgradeCommercialProduct(comProduct))) {
			memberFlag = true;
		}

		return memberFlag;
	}
	/**
	 * @author manoj.bera
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public PriceForBundleAndHardware identifyLowestPriceOfPlanForDevice(
			List<PriceForBundleAndHardware> listOfPriceForBundleHeaderLocal,
			Map<String, CommercialBundle> commercialbundleMap, String journeyType) {

		String gross;
		List<String> productLinesList = new ArrayList<>();
		productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		productLinesList.add(Constants.STRING_MBB_SELLABLE);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = listOfPriceForBundleHeaderLocal.stream()
				.filter(price -> CommonUtility.isValidJourneySpecificBundle(price, commercialbundleMap,
						productLinesList, journeyType))
				.collect(Collectors.toList());
		if (listOfPriceForBundleAndHardware != null && !listOfPriceForBundleAndHardware.isEmpty()) {
			List<PriceForBundleAndHardware> listOfOneOffPriceSorted = getAscendingOrderForOneoffPrice(
					listOfPriceForBundleAndHardware);
			if (listOfOneOffPriceSorted != null && !listOfOneOffPriceSorted.isEmpty()) {
				if (listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross = listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffPrice().getGross();
				}

				List<PriceForBundleAndHardware> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
				for (PriceForBundleAndHardware bundleAndHardwarePrice : listOfOneOffPriceSorted) {
					if (bundleAndHardwarePrice.getHardwarePrice() != null
							&& (bundleAndHardwarePrice.getHardwarePrice().getOneOffDiscountPrice() != null
									|| bundleAndHardwarePrice.getHardwarePrice().getOneOffPrice() != null)
							&& gross != null) {
						if ((bundleAndHardwarePrice.getHardwarePrice().getOneOffDiscountPrice().getGross() != null
								|| bundleAndHardwarePrice.getHardwarePrice().getOneOffPrice().getGross() != null)
								&& (gross
										.equalsIgnoreCase(bundleAndHardwarePrice.getHardwarePrice()
												.getOneOffDiscountPrice().getGross())
										|| gross.equalsIgnoreCase(bundleAndHardwarePrice.getHardwarePrice()
												.getOneOffPrice().getGross()))) {
							listOfEqualOneOffPriceForBundleHeader.add(bundleAndHardwarePrice);
						}
					}
				}
				List<PriceForBundleAndHardware> listOfBundelMonthlyPriceForBundleHeader;
				if (listOfEqualOneOffPriceForBundleHeader != null && !listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
					listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePrice(
							listOfEqualOneOffPriceForBundleHeader);
					if (listOfBundelMonthlyPriceForBundleHeader != null
							&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
						return listOfBundelMonthlyPriceForBundleHeader.get(0);
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return
	 */
	public List<PriceForBundleAndHardware> getAscendingOrderForOneoffPrice(
			List<PriceForBundleAndHardware> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceList1());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceList1 implements Comparator<PriceForBundleAndHardware> {

		@Override
		public int compare(PriceForBundleAndHardware priceForBundleAndHard,
				PriceForBundleAndHardware priceForBundleAndHard1) {
			String gross = null;
			String gross1 = null;
			if (priceForBundleAndHard.getHardwarePrice() != null && priceForBundleAndHard1.getHardwarePrice() != null) {
				if (priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice() != null
						&& priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross = priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = priceForBundleAndHard.getHardwarePrice().getOneOffPrice().getGross();
				}
				if (priceForBundleAndHard1.getHardwarePrice().getOneOffDiscountPrice() != null
						&& priceForBundleAndHard1.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
					gross1 = priceForBundleAndHard1.getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross1 = priceForBundleAndHard1.getHardwarePrice().getOneOffPrice().getGross();
				}

				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else if (Double.compare(Double.parseDouble(gross), Double.parseDouble(gross1)) == 0) {
					return 0;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public List<PriceForBundleAndHardware> getAscendingOrderForBundlePrice(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware, new SortedBundlePriceList1());

		return listOfPriceForBundleAndHardware;
	}

	class SortedBundlePriceList1 implements Comparator<PriceForBundleAndHardware> {

		@Override
		public int compare(PriceForBundleAndHardware priceForBundleAndHardware,
				PriceForBundleAndHardware priceForBundleAndHardware1) {
			String gross = null;
			String gross1 = null;
			if (priceForBundleAndHardware.getBundlePrice() != null
					&& priceForBundleAndHardware1.getBundlePrice() != null) {
				if (priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
						&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
					gross = priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross = priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross();
				}
				if (priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice() != null
						&& priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
					gross1 = priceForBundleAndHardware1.getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross1 = priceForBundleAndHardware1.getBundlePrice().getMonthlyPrice().getGross();
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
	 * @param commercialProduct
	 * @param journeyType
	 * @param bundleAndHardwareTupleList
	 * @param listOfBundleHeaderForDevice
	 */
	public void getTupleList(CommercialProduct commercialProduct, String journeyType,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice) {
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundles;
		List<CoupleRelation> listOfCoupleRelationForMcs;
		bundleDetailsForDevice = CommonUtility.getPriceDetailsForCompatibaleBundle(commercialProduct.getId(),
				journeyType, registryclnt);
		listOfBundles = bundleDetailsForDevice.getStandalonePlansList();
		listOfCoupleRelationForMcs = bundleDetailsForDevice.getCouplePlansList();
		listOfBundleHeaderForDevice.addAll(listOfBundles);
		listOfCoupleRelationForMcs.forEach(coupleRelationMcs -> 
			listOfBundleHeaderForDevice.addAll(coupleRelationMcs.getPlanList())

		);
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
		if (!listOfBundleHeaderForDevice.isEmpty()) {
			validationsonTupleList(commercialProduct, bundleAndHardwareTupleList, listOfBundleHeaderForDevice);
		}
	}

	/**
	 * @param commercialProduct
	 * @param bundleAndHardwareTupleList
	 * @param listOfBundleHeaderForDevice
	 */
	public void validationsonTupleList(CommercialProduct commercialProduct,
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList,
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundleHeaderForDevice) {
		BundleAndHardwareTuple bundleAndHardwareTuple;
		String gross;
		List<com.vf.uk.dal.utility.entity.BundleHeader> listOfOneOffPriceForBundleHeader = getAscendingOrderForBundleHeaderOneoffPrice(
				listOfBundleHeaderForDevice);
		if (listOfOneOffPriceForBundleHeader != null && !listOfOneOffPriceForBundleHeader.isEmpty()) {
			if (listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
					.getGross() != null) {
				gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
						.getOneOffDiscountPrice().getGross();
			} else {
				gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice().getOneOffPrice()
						.getGross();
			}

			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
			for (com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice : listOfOneOffPriceForBundleHeader) {
				getListOfEqualOneOffPrice(gross, listOfEqualOneOffPriceForBundleHeader, bundleHeaderForDevice);
			}
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfBundelMonthlyPriceForBundleHeader;
			String bundleId = null;
			if (!listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
				listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundleHeaderPrice(
						listOfEqualOneOffPriceForBundleHeader);
				if (listOfBundelMonthlyPriceForBundleHeader != null
						&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
					bundleId = listOfBundelMonthlyPriceForBundleHeader.get(0).getSkuId();
				}
			}
			LogHelper.info(this, "Compatible Id:" + bundleId);
			if (bundleId != null && !bundleId.isEmpty()) {
				bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(bundleId);
				bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
				bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
			}
			LogHelper.info(this, "List Of Bundle and Hardware Tuple:Inside compatible " + bundleAndHardwareTupleList);
		}
	}
	/**
	 * @param gross
	 * @param listOfEqualOneOffPriceForBundleHeader
	 * @param bundleHeaderForDevice
	 */
	public void getListOfEqualOneOffPrice(String gross,
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfEqualOneOffPriceForBundleHeader,
			com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice) {
		if (bundleHeaderForDevice.getPriceInfo() != null
				&& bundleHeaderForDevice.getPriceInfo().getHardwarePrice() != null
				&& DeviceServiceImplUtility.getoneOffPrice(bundleHeaderForDevice)
				&& gross != null) {
			if ((bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross() != null
					|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross() != null)
					&& (gross
							.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
									.getOneOffDiscountPrice().getGross())
							|| gross.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
									.getOneOffPrice().getGross()))) {
				listOfEqualOneOffPriceForBundleHeader.add(bundleHeaderForDevice);
			}
		}
	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return
	 */
	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundleHeaderOneoffPrice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceList());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceList implements Comparator<com.vf.uk.dal.utility.entity.BundleHeader> {

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
				} else if (Double.compare(Double.parseDouble(gross), Double.parseDouble(gross1)) == 0) {
					return 0;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return
	 */
	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundleHeaderPrice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedBundlePriceList());

		return bundleHeaderForDeviceSorted;
	}

	class SortedBundlePriceList implements Comparator<com.vf.uk.dal.utility.entity.BundleHeader> {

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
}
