
package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class DevicePrice {

	private String deviceId;

	private float priceNet;

	private float priceGross;

	private float priceVAT;

	private String productLine;

	public DevicePrice() {
		super();

	}
}
