package com.vf.uk.dal.device.svc;

import java.util.List;
import java.util.Map;

import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;

public interface DeviceMakeAndModelService {

	/**
	 * 
	 * @param make
	 * @param model
	 * @param groupType
	 * @param deviceId
	 * @param creditLimit
	 * @param journeyType
	 * @param offerCode
	 * @param bundleId
	 * @return List<DeviceTile>
	 */
	public List<DeviceTile> getListOfDeviceTile(String make, String model, String groupType, String deviceId,
			Double creditLimit, String journeyType, String offerCode, String bundleId);

	/**
	 * 
	 * @param journeyType
	 * @param commerBundleIdMap
	 * @param leadPlanIdMap
	 * @param listofLeadPlan
	 * @param bundleAndHardwarePromotionsMap
	 * @param priceMapForParticularDevice
	 * @param promotionsMap
	 * @param priceMap
	 * @param hardwareID
	 * @param flag
	 */
	public void getPriceAndPromotionMap(String journeyType, Map<String, CommercialBundle> commerBundleIdMap,
			Map<String, String> leadPlanIdMap, List<String> listofLeadPlan,
			Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap,
			Map<String, PriceForBundleAndHardware> priceMapForParticularDevice,
			Map<String, List<BundleAndHardwarePromotions>> promotionsMap,
			Map<String, List<PriceForBundleAndHardware>> priceMap, String hardwareID, Boolean flag);
}
