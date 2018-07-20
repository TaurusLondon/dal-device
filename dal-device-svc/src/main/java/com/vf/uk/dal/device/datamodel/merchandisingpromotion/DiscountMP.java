package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import lombok.Data;

@Data
public class DiscountMP {

	protected String type;

	protected float value;

	protected float qualifyingRecurringCost;

	protected Long priority;

	
}
