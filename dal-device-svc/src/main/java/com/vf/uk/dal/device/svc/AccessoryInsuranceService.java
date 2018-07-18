package com.vf.uk.dal.device.svc;

import java.util.List;

import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.Insurances;

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
