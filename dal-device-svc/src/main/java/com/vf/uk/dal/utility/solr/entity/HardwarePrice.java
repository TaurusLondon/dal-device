package com.vf.uk.dal.utility.solr.entity;

import java.util.List;

import lombok.Data;

/**
 * 
 * HardwarePrice
 *
 */
@Data
public class HardwarePrice {
	private String hardwareId;

	private OneOffPrice oneOffPrice;

	private OneOffDiscountPrice oneOffDiscountPrice;

	private List<DeviceFinancingOption> financingOptions = null;
}
