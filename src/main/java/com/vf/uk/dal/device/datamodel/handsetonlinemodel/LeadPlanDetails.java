package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import lombok.Data;

@Data
public class LeadPlanDetails {

	/** lead plan Id */
	private String leadPlanId;
	
	/** To identify that lead plan Id coming from mef or price */
	private Boolean isFromPricing;
	
	/** Display Name of lead Plan */
	private String leadPlanDisplayName;
	
	/** type of lead Plan */
	private String bundleType;
	
	/** Unit of measurement  */
	private String uom;
	
	/** Unit of measurement Value  */
	private String uomValue;
	
	/** promote As value  */
	private List<String> promotAs;
	
	/** bundle Hardware Tuple For */
	private List<BundleAndHardwareTuple> bundleHardwareTuple;

}
