package com.vf.uk.dal.device.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
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

/**
 * DeviceServiceCommonUtility
 * @author manoj.bera
 *
 */
@Component
public class DeviceServiceCommonUtility {

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	RegistryClient registryclnt;

	/**
	 * 
	 * @param leadMemberId
	 * @return DeviceTileRating
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
	 * @return DeviceReviewRating_Implementation
	 */
	public Map<String, String> getDeviceReviewRating_Implementation(List<String> listMemberIds) {

		List<BazaarVoice> response = getReviewRatingList_Implementation(listMemberIds);
		HashMap<String, String> bvReviewAndRateMap = new HashMap<>();
		try {
			for (BazaarVoice bazaarVoice : response) {
				if (bazaarVoice != null) {
					if (!bazaarVoice.getJsonsource().isEmpty()) {
						org.json.JSONObject jSONObject = new org.json.JSONObject(bazaarVoice.getJsonsource());
						setbvReviewAndRate(bvReviewAndRateMap, bazaarVoice, jSONObject);
					} else {
						bvReviewAndRateMap.put(bazaarVoice.getSkuId(), "na");
					}
				}
			}
		} catch (Exception e) {
			LogHelper.error(this, "Failed to get device review ratings, Exception: " + e);
			throw new ApplicationException(ExceptionMessages.BAZAARVOICE_RESPONSE_EXCEPTION);
		}
		return bvReviewAndRateMap;
	}

	private void setbvReviewAndRate(HashMap<String, String> bvReviewAndRateMap, BazaarVoice bazaarVoice,
			org.json.JSONObject jSONObject) throws JSONException {
		if (!StringUtils.equals("{}", jSONObject.get("Includes").toString())) {
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
				setbvReviewAndRateMap(bvReviewAndRateMap, key, averageOverallRating);
			}
		} else {
			bvReviewAndRateMap.put(bazaarVoice.getSkuId(), "na");
		}
	}

	private void setbvReviewAndRateMap(HashMap<String, String> bvReviewAndRateMap, String key,
			Double averageOverallRating) {
		if (!bvReviewAndRateMap.keySet().contains(key)) {
			String overallRating = (null != averageOverallRating)
					? averageOverallRating.toString() : "na";
			bvReviewAndRateMap.put(key, overallRating);
		}
	}

	/**
	 * 
	 * @param listMemberIds
	 * @return List<BazaarVoice>
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
	 * @param journeyType
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
	 * @param journeyType
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
	 * 
	 * @param listOfPriceForBundleHeaderLocal
	 * @param commercialbundleMap
	 * @param journeyType
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
				gross = setGrossPriceForOneOffPriceSorted(listOfOneOffPriceSorted);

				List<PriceForBundleAndHardware> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
				setListOfPriceForBundleAndHardware(gross, listOfOneOffPriceSorted,
						listOfEqualOneOffPriceForBundleHeader);
				List<PriceForBundleAndHardware> listOfBundelMonthlyPriceForBundleHeader;
				if (!listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
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

	private void setListOfPriceForBundleAndHardware(String gross,
			List<PriceForBundleAndHardware> listOfOneOffPriceSorted,
			List<PriceForBundleAndHardware> listOfEqualOneOffPriceForBundleHeader) {
		for (PriceForBundleAndHardware bundleAndHardwarePrice : listOfOneOffPriceSorted) {
			if (bundleAndHardwarePrice.getHardwarePrice() != null
					&& (bundleAndHardwarePrice.getHardwarePrice().getOneOffDiscountPrice() != null
							|| bundleAndHardwarePrice.getHardwarePrice().getOneOffPrice() != null)
					&& gross != null) {
				setListOfEqualOneOFFpIrceForBUnde(gross, listOfEqualOneOffPriceForBundleHeader,
						bundleAndHardwarePrice);
			}
		}
	}

	private void setListOfEqualOneOFFpIrceForBUnde(String gross,
			List<PriceForBundleAndHardware> listOfEqualOneOffPriceForBundleHeader,
			PriceForBundleAndHardware bundleAndHardwarePrice) {
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

	private String setGrossPriceForOneOffPriceSorted(List<PriceForBundleAndHardware> listOfOneOffPriceSorted) {
		String gross;
		if (listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
			gross = listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffDiscountPrice().getGross();
		} else {
			gross = listOfOneOffPriceSorted.get(0).getHardwarePrice().getOneOffPrice().getGross();
		}
		return gross;
	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return List<PriceForBundleAndHardware>
	 */
	public List<PriceForBundleAndHardware> getAscendingOrderForOneoffPrice(
			List<PriceForBundleAndHardware> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted,(PriceForBundleAndHardware priceForBundleAndHard,
				PriceForBundleAndHardware priceForBundleAndHard1)->{

					String gross;
					String gross1;
					if (priceForBundleAndHard.getHardwarePrice() != null && priceForBundleAndHard1.getHardwarePrice() != null) {
						gross = setHardwareOneOfPriceForComparing(priceForBundleAndHard);
						gross1 = setHardwareOneOfPriceForComparing(priceForBundleAndHard1);

						if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
							return -1;
						} else if (Double.compare(Double.parseDouble(gross), Double.parseDouble(gross1)) == 0) {
							return 0;
						} else{
							return 1;
						}
					}

					else{
						return -1;
					}
				
				});

		return bundleHeaderForDeviceSorted;
	}

	private String setHardwareOneOfPriceForComparing(PriceForBundleAndHardware priceForBundleAndHard) {
		String gross;
		if (priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice() != null
				&& priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
			gross = priceForBundleAndHard.getHardwarePrice().getOneOffDiscountPrice().getGross();
		} else {
			gross = priceForBundleAndHard.getHardwarePrice().getOneOffPrice().getGross();
		}
		return gross;
	}

	/**
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return List<PriceForBundleAndHardware>
	 */
	public List<PriceForBundleAndHardware> getAscendingOrderForBundlePrice(
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware,(PriceForBundleAndHardware priceForBundleAndHardware,
				PriceForBundleAndHardware priceForBundleAndHardware1)->{

					String gross;
					String gross1;
					if (priceForBundleAndHardware.getBundlePrice() != null
							&& priceForBundleAndHardware1.getBundlePrice() != null) {
						gross = setMonthlyPriceForBundleForCOmpare(priceForBundleAndHardware);
						gross1 = setMonthlyPriceForBundleForCOmpare(priceForBundleAndHardware1);
						if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
							return -1;
						} else{
							return 1;
						}
					}

					else{
						return -1;
					}
				
				});

		return listOfPriceForBundleAndHardware;
	}

	private String setMonthlyPriceForBundleForCOmpare(PriceForBundleAndHardware priceForBundleAndHardware) {
		String gross;
		if (priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
				&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
			gross = priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross();
		} else {
			gross = priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross();
		}
		return gross;
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
		listOfCoupleRelationForMcs
				.forEach(coupleRelationMcs -> listOfBundleHeaderForDevice.addAll(coupleRelationMcs.getPlanList())

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
			gross = setGrossPriceForHardware(listOfOneOffPriceForBundleHeader);

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

	private String setGrossPriceForHardware(
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfOneOffPriceForBundleHeader) {
		String gross;
		if (listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
				.getGross() != null) {
			gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
					.getOneOffDiscountPrice().getGross();
		} else {
			gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice().getOneOffPrice()
					.getGross();
		}
		return gross;
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
				&& DeviceServiceImplUtility.getoneOffPrice(bundleHeaderForDevice) && gross != null) {
			getListOfEqualOneOffPRiceForOneOffPrice(gross, listOfEqualOneOffPriceForBundleHeader, bundleHeaderForDevice);
		}
	}

	private void getListOfEqualOneOffPRiceForOneOffPrice(String gross,
			List<com.vf.uk.dal.utility.entity.BundleHeader> listOfEqualOneOffPriceForBundleHeader,
			com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderForDevice) {
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

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return List<com.vf.uk.dal.utility.entity.BundleHeader>
	 */
	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundleHeaderOneoffPrice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted,(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList,
				com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList1)->{
					String gross = null;
					String gross1 = null;
					if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
							&& bundleHeaderList.getPriceInfo().getHardwarePrice() != null
							&& bundleHeaderList1.getPriceInfo().getHardwarePrice() != null) {
						gross = setGrossForComparator(bundleHeaderList);
						gross1 = setGrossForComparator(bundleHeaderList1);

						if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
							return -1;
						} else if (Double.compare(Double.parseDouble(gross), Double.parseDouble(gross1)) == 0) {
							return 0;
						} else{
							return 1;
						}

					}

					else{
						return -1;
					}
				
					
				});

		return bundleHeaderForDeviceSorted;
	}

	private String setGrossForComparator(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList) {
		String gross;
		if (bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
				&& bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
						.getGross() != null) {
			gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
		} else {
			gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
		}
		return gross;
	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return List<com.vf.uk.dal.utility.entity.BundleHeader>
	 */
	public List<com.vf.uk.dal.utility.entity.BundleHeader> getAscendingOrderForBundleHeaderPrice(
			List<com.vf.uk.dal.utility.entity.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted,(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList,
				com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList1)->{
					

					String gross = null;
					String gross1 = null;
					if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
							&& bundleHeaderList.getPriceInfo().getBundlePrice() != null
							&& bundleHeaderList1.getPriceInfo().getBundlePrice() != null) {
						gross = setBundlePriceForComparing(bundleHeaderList);
						gross1 = setBundlePriceForComparing(bundleHeaderList1);
						if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
							return -1;
						} else{
							return 1;
						}
					}

					else{
						return -1;
					}
				
				});

		return bundleHeaderForDeviceSorted;
	}

	private String setBundlePriceForComparing(com.vf.uk.dal.utility.entity.BundleHeader bundleHeaderList) {
		String gross;
		if (bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
				&& bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
						.getGross() != null) {
			gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
		} else {
			gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
		}
		return gross;
	}

}
