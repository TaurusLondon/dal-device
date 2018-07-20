package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * CoupleRelation
 */
@Data
public class CoupleRelation {
	private List<BundleHeader> planList = new ArrayList<BundleHeader>();

}
