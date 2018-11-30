package com.vf.uk.dal.device.service;

import java.util.List;

import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.Insurances;
/**
 * 
 * @author sahil.monga
 *
 */
public interface AccessoryInsuranceService {

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return List<AccessoryTileGroup>
	 */
	public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId, String journeyType, String offerCode);

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @return
	 */
	Insurances getInsuranceByDeviceId(String deviceId, String journeyType);
}
