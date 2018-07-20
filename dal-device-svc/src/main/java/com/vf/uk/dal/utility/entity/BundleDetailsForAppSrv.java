package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * BundleDetailsForAppSrv
 */
@Data
public class BundleDetailsForAppSrv {
	private List<CoupleRelation> couplePlansList = new ArrayList<CoupleRelation>();

	private List<BundleHeader> standalonePlansList = new ArrayList<BundleHeader>();
}
