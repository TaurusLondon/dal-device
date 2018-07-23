package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class AddonIncompatibleProducts {

	private String incompatibleProductId;

	private String ruleScope;

	private String startDate;

	private String endDate;

}
