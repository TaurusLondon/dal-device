package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * CoupleRelation
 */
@Data
public class CoupleRelation {
	
	@JsonProperty("planList")
	private List<BundleHeader> planList = new ArrayList<BundleHeader>();

}
