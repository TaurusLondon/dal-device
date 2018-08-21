package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleDetailsForAppSrv
 */
@Data
public class BundleDetailsForAppSrv {

	@JsonProperty("couplePlansList")
	private List<CoupleRelation> couplePlansList = new ArrayList<CoupleRelation>();

	@JsonProperty("standalonePlansList")
	private List<BundleHeader> standalonePlansList = new ArrayList<BundleHeader>();
}
