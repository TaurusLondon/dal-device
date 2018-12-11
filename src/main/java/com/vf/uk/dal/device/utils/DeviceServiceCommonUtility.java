package com.vf.uk.dal.device.utils;

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
import org.springframework.web.client.RestTemplate;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.bundle.CoupleRelation;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.model.product.BazaarVoice;
import com.vf.uk.dal.device.model.product.CommercialProduct;

import lombok.extern.slf4j.Slf4j;

/**
 * DeviceServiceCommonUtility
 * @author manoj.bera
 *
 */
@Slf4j
@Component
public class DeviceServiceCommonUtility {

	public static final String STRING_MOBILE_PHONE_SERVICE_SELLABLE = "Mobile Phone Service Sellable";
	public static final String STRING_MBB_SELLABLE = "MBB Sellable";
	
	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceUtils deviceUtils;
	
	
	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;
	
	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;
	
	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CommonUtility commonUtility;

	/**
	 * 
	 * @param leadMemberId
	 * @return DeviceTileRating
	 */
	public String getDeviceTileRating(String leadMemberId) {
		Map<String, String> rating = getDeviceReviewRatingImplementation(new ArrayList<>(Arrays.asList(leadMemberId)));
		String avarageOverallRating = rating.containsKey(commonUtility.appendPrefixString(leadMemberId))
				? rating.get(commonUtility.appendPrefixString(leadMemberId)) : "na";
		log.info( "AvarageOverallRating for deviceId: " + leadMemberId + " Rating: " + avarageOverallRating);
		return avarageOverallRating;
	}

	/**
	 * 
	 * @param listMemberIds
	 * @return DeviceReviewRating_Implementation
	 */
	public Map<String, String> getDeviceReviewRatingImplementation(List<String> listMemberIds) {

		List<BazaarVoice> response = getReviewRatingListImplementation(listMemberIds);
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
			log.error( "Failed to get device review ratings, Exception: " + e);
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
	public List<BazaarVoice> getReviewRatingListImplementation(List<String> listMemberIds) {

		try {
			log.info( "Start -->  calling  BazaarReviewRepository.get");
			List<BazaarVoice> response = new ArrayList<>();
			for (String skuId : listMemberIds) {
				response.add(deviceDao.getBazaarVoice(skuId));
			}
			log.info( "End --> After calling  BazaarReviewRepository.get");
			return response;
		} catch (Exception e) {
			log.error( "Bazar Voice Exception: " + e);
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
	public String getMemeberBasedOnRulesImplementation(
			List<com.vf.uk.dal.device.model.Member> listOfDeviceGroupMember, String journeyType) {
		String leadDeviceSkuId = null;
		DeviceTilesDaoUtils daoUtils = new DeviceTilesDaoUtils();
		List<com.vf.uk.dal.device.model.Member> listOfSortedMember = daoUtils
				.getAscendingOrderForMembers(listOfDeviceGroupMember);
		for (com.vf.uk.dal.device.model.Member member : listOfSortedMember) {
			if (validateMemeberImplementation(member.getId(), journeyType)) {
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
	public Boolean validateMemeberImplementation(String memberId, String journeyType) {
		Boolean memberFlag = false;
		boolean preOrderableFlag = false;
		log.info( " Start -->  calling  CommercialProductRepository.get");
		CommercialProduct comProduct = deviceEs.getCommercialProduct(memberId);
		log.info( " End -->  After calling  CommercialProductRepository.get");

		Date startDateTime = comProduct.getProductAvailability().getStart();
		Date endDateTime = comProduct.getProductAvailability().getEnd();
		preOrderableFlag = comProduct.getProductControl()==null?preOrderableFlag:comProduct.getProductControl().isPreOrderable();
		boolean isUpgrade = deviceServiceImplUtility.isUpgrade(journeyType)
				&& deviceServiceImplUtility.getProductclassValidation(comProduct)
				&& deviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& deviceServiceImplUtility.isUpgradeFromCommercialProduct(comProduct);
		if (isUpgrade || (deviceServiceImplUtility.getProductclassValidation(comProduct)
				&& deviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, preOrderableFlag)
				&& deviceServiceImplUtility.isNonUpgradeCommercialProduct(comProduct))) {
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
		productLinesList.add(STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		productLinesList.add(STRING_MBB_SELLABLE);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = listOfPriceForBundleHeaderLocal.stream()
				.filter(price -> commonUtility.isValidJourneySpecificBundle(price, commercialbundleMap,
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

					Double gross;
					Double compareGross;
					if (priceForBundleAndHard.getHardwarePrice() != null && priceForBundleAndHard1.getHardwarePrice() != null) {
						gross = deviceUtils.getDoubleFrmString(setHardwareOneOfPriceForComparing(priceForBundleAndHard));
						compareGross = deviceUtils.getDoubleFrmString(setHardwareOneOfPriceForComparing(priceForBundleAndHard1));
						return Double.compare(gross,compareGross);
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
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundleHeaderForDevice) {
		BundleDetailsForAppSrv bundleDetailsForDevice;
		List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundles;
		List<CoupleRelation> listOfCoupleRelationForMcs;
		bundleDetailsForDevice = commonUtility.getPriceDetailsForCompatibaleBundle(commercialProduct.getId(),
				journeyType);
		listOfBundles = bundleDetailsForDevice.getStandalonePlansList();
		listOfCoupleRelationForMcs = bundleDetailsForDevice.getCouplePlansList();
		listOfBundleHeaderForDevice.addAll(listOfBundles);
		listOfCoupleRelationForMcs
				.forEach(coupleRelationMcs -> listOfBundleHeaderForDevice.addAll(coupleRelationMcs.getPlanList())

		);
		Iterator<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> it = listOfBundleHeaderForDevice.iterator();
		while (it.hasNext()) {
			com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleheaderForDevice = it.next();
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
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundleHeaderForDevice) {
		BundleAndHardwareTuple bundleAndHardwareTuple;
		String gross;
		List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfOneOffPriceForBundleHeader = getAscendingOrderForBundleHeaderOneoffPrice(
				listOfBundleHeaderForDevice);
		if (listOfOneOffPriceForBundleHeader != null && !listOfOneOffPriceForBundleHeader.isEmpty()) {
			gross = setGrossPriceForHardware(listOfOneOffPriceForBundleHeader);

			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
			for (com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice : listOfOneOffPriceForBundleHeader) {
				getListOfEqualOneOffPrice(gross, listOfEqualOneOffPriceForBundleHeader, bundleHeaderForDevice);
			}
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundelMonthlyPriceForBundleHeader;
			String bundleId = null;
			if (!listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
				listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundleHeaderPrice(
						listOfEqualOneOffPriceForBundleHeader);
				if (listOfBundelMonthlyPriceForBundleHeader != null
						&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
					bundleId = listOfBundelMonthlyPriceForBundleHeader.get(0).getSkuId();
				}
			}
			log.info( "Compatible Id:" + bundleId);
			if (bundleId != null && !bundleId.isEmpty()) {
				bundleAndHardwareTuple = new BundleAndHardwareTuple();
				bundleAndHardwareTuple.setBundleId(bundleId);
				bundleAndHardwareTuple.setHardwareId(commercialProduct.getId());
				bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
			}
			log.info( "List Of Bundle and Hardware Tuple:Inside compatible " + bundleAndHardwareTupleList);
		}
	}

	private String setGrossPriceForHardware(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfOneOffPriceForBundleHeader) {
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
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfEqualOneOffPriceForBundleHeader,
			com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice) {
		if (bundleHeaderForDevice.getPriceInfo() != null
				&& bundleHeaderForDevice.getPriceInfo().getHardwarePrice() != null
				&& deviceServiceImplUtility.getoneOffPrice(bundleHeaderForDevice) && gross != null) {
			getListOfEqualOneOffPRiceForOneOffPrice(gross, listOfEqualOneOffPriceForBundleHeader, bundleHeaderForDevice);
		}
	}

	private void getListOfEqualOneOffPRiceForOneOffPrice(String gross,
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfEqualOneOffPriceForBundleHeader,
			com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice) {
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
	public List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> getAscendingOrderForBundleHeaderOneoffPrice(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted,(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList,
				com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList1)->{
					Double gross;
					Double compareGross;
					if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
							&& bundleHeaderList.getPriceInfo().getHardwarePrice() != null
							&& bundleHeaderList1.getPriceInfo().getHardwarePrice() != null) {
						gross = deviceUtils.getDoubleFrmString(setGrossForComparator(bundleHeaderList));
						compareGross = deviceUtils.getDoubleFrmString(setGrossForComparator(bundleHeaderList1));
						return Double.compare(gross,compareGross);

					}

					else{
						return -1;
					}
				
					
				});

		return bundleHeaderForDeviceSorted;
	}

	private String setGrossForComparator(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList) {
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
	public List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> getAscendingOrderForBundleHeaderPrice(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted,(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList,
				com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList1)->{
					

					Double gross;
					Double compareGross;
					if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
							&& bundleHeaderList.getPriceInfo().getBundlePrice() != null
							&& bundleHeaderList1.getPriceInfo().getBundlePrice() != null) {
						gross = deviceUtils.getDoubleFrmString(setBundlePriceForComparing(bundleHeaderList));
						compareGross = deviceUtils.getDoubleFrmString(setBundlePriceForComparing(bundleHeaderList1));
						return Double.compare(gross, compareGross);
					}

					else{
						return -1;
					}
				
				});

		return bundleHeaderForDeviceSorted;
	}

	private String setBundlePriceForComparing(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList) {
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
