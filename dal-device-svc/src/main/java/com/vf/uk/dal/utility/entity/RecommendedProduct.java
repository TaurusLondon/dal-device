package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * RecommendedProduct
 *
 */
@Data
public class RecommendedProduct {
	private String typeCode = null;

	private String priorityCode = null;

	private String id = null;

	private String content = null;

	private List<Reason> recommendationReasons = new ArrayList<Reason>();

	private String anyTimeUpgradeAmount = null;

	private String description = null;

	private Boolean isCompatible = null;
}
