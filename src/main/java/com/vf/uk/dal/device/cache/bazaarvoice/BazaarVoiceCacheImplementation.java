package com.vf.uk.dal.device.cache.bazaarvoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vf.uk.dal.device.model.product.BazaarVoice;
import com.vf.uk.dal.device.utils.BazaarVoiceCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BazaarVoiceCacheImplementation {

	private static final String PREFIX_SKU = "sku";
	private static final Object ZERO = "0";
	@Autowired
	BazaarVoiceCache bzrVoiceCache;

	/**
	 * 
	 * @param leadMemberId
	 * @return DeviceRating
	 */
	public String getDeviceRating(String leadMemberId) {
		HashMap<String, String> bvReviewAndRateMap = new HashMap<>();
		HashMap<String, String> bvTotalReviewRating = new HashMap<>();
		getDeviceReviewRatingImplementation(new ArrayList<>(Arrays.asList(leadMemberId)), bvReviewAndRateMap,
				bvTotalReviewRating);
		String avarageOverallRating = bvReviewAndRateMap.containsKey(appendPrefixString(leadMemberId))
				? bvReviewAndRateMap.get(appendPrefixString(leadMemberId)) : "na";
		String totalReviewRating = bvTotalReviewRating.containsKey(appendPrefixString(leadMemberId))
				? bvTotalReviewRating.get(appendPrefixString(leadMemberId)) : "0";
		log.info("AvarageOverallRating for deviceId: " + leadMemberId + " Rating: " + avarageOverallRating);
		return avarageOverallRating + "|" + totalReviewRating;
	}

	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public String appendPrefixString(String deviceId) {
		StringBuilder target = new StringBuilder(PREFIX_SKU);
		String leadingZero = deviceId.substring(0, 1);
		if (leadingZero.equals(ZERO)) {
			target.append(deviceId.substring(1, deviceId.length()));
		} else {
			target.append(deviceId);
		}
		return target.toString();
	}

	/**
	 * 
	 * @param listMemberIds
	 * @return DeviceReviewRating_Implementation
	 */
	public void getDeviceReviewRatingImplementation(List<String> listMemberIds, Map<String, String> bvReviewAndRateMap,
			Map<String, String> bvTotalReviewRating) {
		List<BazaarVoice> response = getReviewRatingList(listMemberIds);

		try {
			for (BazaarVoice bazaarVoice : response) {
				if (bazaarVoice != null) {
					if (!bazaarVoice.getJsonsource().isEmpty()) {
						org.json.JSONObject jSONObject = new org.json.JSONObject(bazaarVoice.getJsonsource());
						setbvReviewAndRate(bvReviewAndRateMap, bazaarVoice, jSONObject, bvTotalReviewRating);
					} else {
						bvReviewAndRateMap.put(bazaarVoice.getSkuId(), "na");
						bvTotalReviewRating.put(bazaarVoice.getSkuId(), "0");
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed to get device review ratings, Exception: " + e);
		}

	}

	private void setbvReviewAndRate(Map<String, String> bvReviewAndRateMap, BazaarVoice bazaarVoice,
			org.json.JSONObject jSONObject, Map<String, String> bvTotalReviewRating) throws JSONException {
		if (!StringUtils.equals("{}", jSONObject.get("Includes").toString())) {
			org.json.JSONObject includes = jSONObject.getJSONObject("Includes");
			org.json.JSONObject products = includes.getJSONObject("Products");
			Iterator<?> level = products.keys();
			while (level.hasNext()) {
				String key = (String) level.next();
				org.json.JSONObject skuId = products.getJSONObject(key);
				org.json.JSONObject reviewStatistics = (null != skuId) ? skuId.getJSONObject("ReviewStatistics") : null;
				Double averageOverallRating = (null != reviewStatistics)
						? (Double) reviewStatistics.get("AverageOverallRating") : null;
				Integer totalReviewCount = (null != reviewStatistics) ? (Integer) skuId.get("TotalReviewCount") : null;
				setbvReviewAndRateMap(bvReviewAndRateMap, key, averageOverallRating);
				setbvTotalReviewCount(bvTotalReviewRating, key, totalReviewCount);
			}
		} else {
			bvReviewAndRateMap.put(bazaarVoice.getSkuId(), "na");
		}
	}

	private void setbvTotalReviewCount(Map<String, String> bvTotalReviewRating, String key, Integer totalReviewCount) {
		if (!bvTotalReviewRating.keySet().contains(key)) {
			String overallRating = (null != totalReviewCount) ? totalReviewCount.toString() : "0";
			bvTotalReviewRating.put(key, overallRating);
		}
	}

	private void setbvReviewAndRateMap(Map<String, String> bvReviewAndRateMap, String key,
			Double averageOverallRating) {
		if (!bvReviewAndRateMap.keySet().contains(key)) {
			String overallRating = (null != averageOverallRating) ? averageOverallRating.toString() : "na";
			bvReviewAndRateMap.put(key, overallRating);
		}
	}

	/**
	 * 
	 * @param listMemberIds
	 * @return List<BazaarVoice>
	 */
	public List<BazaarVoice> getReviewRatingList(List<String> listMemberIds) {
		List<BazaarVoice> response = new ArrayList<>();
		try {
			log.info("Start -->  calling  BazaarReviewRepository.get");
			for (String skuId : listMemberIds) {
				response.add(getBazaarVoice(skuId));
			}
			log.info("End --> After calling  BazaarReviewRepository.get");
		} catch (Exception e) {
			log.error("Bazar Voice Exception: " + e);
		}
		return response;
	}

	/**
	 * 
	 * @param skuId
	 * @return
	 */
	public BazaarVoice getBazaarVoice(String skuId) {
		BazaarVoice bazaarVoice = new BazaarVoice();
		bazaarVoice.setSkuId(skuId);
		bazaarVoice.setJsonsource(getDeviceReviewDetails(appendPrefixString(skuId)));
		return bazaarVoice;
	}

	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public String getDeviceReviewDetails(String deviceId) {
		String jsonObject;
		log.info("Start -->  calling  BazaarReviewRepository.get");
		jsonObject = bzrVoiceCache.getBazaarVoiceReviews(deviceId);
		return jsonObject;
	}
}
