package com.vf.uk.dal.device.client.entity.bundle;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleDetails
 */

@Data
public class BundleDetails {
	
	@JsonProperty("planList")
	private List<BundleHeader> planList = new ArrayList<>();

	
}
