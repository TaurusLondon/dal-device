package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.utility.entity.InstalledProduct;
import com.vf.uk.dal.utility.entity.Preferences;
import com.vf.uk.dal.utility.entity.RecommendedProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;

/**
 * Provides recommended devices for a logged in user.
 * 
 * @author rajendra.swarna
 */
@Component("deviceRecommendationService")
public class DeviceRecommendationServiceImpl implements DeviceRecommendationService {

	@Autowired
	RegistryClient registryclnt;
	@Autowired
	CommonUtility commonUtility;
	

	/**
	 * Returns recommended device list from chordiant.
	 * 
	 * @param journeyId
	 * @param facetedDevice
	 * @return FacetedDevice
	 */
	public FacetedDevice getRecommendedDeviceList(String msisdn, String deviceId, FacetedDevice facetedDevice) {

		RecommendedProductListResponse recommendedProductListResponse = null;
		FacetedDevice sortedFacetedDevice = new FacetedDevice();
		try {
			RecommendedProductListRequest recomProductListReq = this.getRecommendedDeviceListRequest(msisdn, deviceId);
			recommendedProductListResponse = commonUtility.getRecommendedProductList(recomProductListReq, registryclnt);

			if (recommendedProductListResponse != null) {
				sortedFacetedDevice = sortList(facetedDevice, recommendedProductListResponse);
			} else {
				LogHelper.error(this, "Unable to retrieve recommended device list from chordiant.");
			}

		} catch (Exception e) {
			LogHelper.error(this, "Failed to get recommended device list " + e);
			sortedFacetedDevice.setMessage("RECOMMENDATIONS_NOT_AVAILABLE_GRPL_SERVICE_FAILURE");
		}
		return sortedFacetedDevice;
	}

	/**
	 * Returns request object for getRecommendedDeviceList
	 * 
	 * @param msisdn
	 * @param deviceId
	 * @return RecommendedProductListRequest
	 */
	public RecommendedProductListRequest getRecommendedDeviceListRequest(String msisdn, String deviceId) {

		RecommendedProductListRequest recomProdListReq = new RecommendedProductListRequest();

		recomProdListReq.setSerialNumber(msisdn);
		recomProdListReq.setAccountCategory(Constants.ACCOUNT_CATEGORY_INDIVIDUAL);

		List<InstalledProduct> instProds = new ArrayList<>();
		InstalledProduct instProd = new InstalledProduct();
		instProd.setId(deviceId);
		instProd.setTypeCode(Constants.STRING_TARIFF);
		instProd.setAmount("220000.00");
		instProds.add(instProd);
		recomProdListReq.setInstalledProducts(instProds);

		List<Preferences> prefs = new ArrayList<>();
		Preferences handsetPref = new Preferences();
		handsetPref.setName(Constants.PREFERENCE_NAME_HANDSET);
		handsetPref.setDataTypeCode(Constants.PREFERENCE_DATATYPE_CODE_PREFERENCE);
		handsetPref.setValue("all");
		prefs.add(handsetPref);

		Preferences upgradePref = new Preferences();
		upgradePref.setName(Constants.PREFERENCE_NAME_UPGRADE);
		upgradePref.setDataTypeCode(Constants.PREFERENCE_DATATYPE_CODE_GENERAL);
		upgradePref.setValue("SIMOFLEX");
		prefs.add(upgradePref);

		Preferences recommitPref = new Preferences();
		recommitPref.setName(Constants.PREFERENCE_NAME_RECOMMIT);
		recommitPref.setDataTypeCode(Constants.PREFERENCE_DATATYPE_CODE_GENERAL);
		recommitPref.setValue("FALSE");
		prefs.add(recommitPref);

		Preferences segmentPref = new Preferences();
		segmentPref.setName(Constants.PREFERENCE_NAME_SEGMENT);
		segmentPref.setDataTypeCode(Constants.PREFERENCE_DATATYPE_ELIGIBILITY_CRITERIA);
		segmentPref.setValue("cbu");
		prefs.add(segmentPref);

		recomProdListReq.setPreferences(prefs);

		List<String> recomPrdTypes;
		recomPrdTypes = new ArrayList<>();
		recomPrdTypes.add(Constants.PREFERENCE_NAME_HANDSET);

		recomProdListReq.setBasketItems(null);
		recomProdListReq.setNoOfRecommendations("100");

		recomProdListReq.setRecommendedProductTypes(recomPrdTypes);

		return recomProdListReq;
	}

	/**
	 * @param objectsToOrder
	 * @param orderedObjects
	 * @return FacetedDevice
	 * 
	 */
	public FacetedDevice sortList(FacetedDevice objectsToOrder, RecommendedProductListResponse orderedObjects) {
		HashMap<String, Integer> indexMap = new HashMap<>();
		int index = 0;
		for (RecommendedProduct object : orderedObjects.getRecommendedProductList()) {
			indexMap.put(object.getId(), index);
			index++;
		}
		List<Device> listOfDevice = objectsToOrder.getDevice();
		Collections.sort(listOfDevice, new Comparator<Device>() {
			public int compare(Device left, Device right) {
				Integer leftIndex = indexMap.get(left.getDeviceId());
				Integer rightIndex = indexMap.get(right.getDeviceId());
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
			}
		});
		return objectsToOrder;
	}

}
