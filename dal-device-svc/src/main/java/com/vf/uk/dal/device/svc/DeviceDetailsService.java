package com.vf.uk.dal.device.svc;

import java.util.List;

import com.vf.uk.dal.device.entity.DeviceDetails;

public interface DeviceDetailsService {
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return
	 */
	public DeviceDetails getDeviceDetails(String deviceId, String journeyType, String offerCode);

	/**
	 * 
	 * @param deviceId
	 * @param offerCode
	 * @param journeyType
	 * @return
	 */
	public List<DeviceDetails> getListOfDeviceDetails(String deviceId, String offerCode, String journeyType);
}
