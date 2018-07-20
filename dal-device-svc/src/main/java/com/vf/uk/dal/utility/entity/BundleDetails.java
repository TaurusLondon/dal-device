package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * BundleDetails
 */

@Data
public class BundleDetails {
	private List<BundleHeader> planList = new ArrayList<>();

	
}
