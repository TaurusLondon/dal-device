package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;

import lombok.Data;

/**
 * 
 * CommercialBundle
 *
 */
@Data
public class CommercialBundle {

	private String id;

	private String name;

	private String desc;

	private String paymentType;

	private Availability availability;

	private Commitment commitment;

	private List<String> productLines;

	private List<DevicePrice> deviceSpecificPricing;

	private List<ServiceProduct> serviceProducts;

	private List<Allowance> allowances;

	private Float recurringCharge;

	private String displayName;

	private List<ImageURL> listOfimageURLs;

	private List<Group> specificationGroups;

	private BundleControl bundleControl;

	private PromoteAs promoteAs;

	private String displayGroup;

	/**
	 * CommercialBundle Constructor
	 */
	public CommercialBundle() {
		super();
	}

	

}
