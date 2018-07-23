package com.vf.uk.dal.utility.entity;

import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.entity.BundlePrice;

import lombok.Data;

@Data
public class BundleModelAndPrice {

	private BundleModel bundleModel;

	private BundlePrice bundlePrice;

	
}
