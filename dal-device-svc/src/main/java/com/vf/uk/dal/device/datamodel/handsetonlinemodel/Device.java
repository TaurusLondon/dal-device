package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import lombok.Data;

@Data
public class Device {
	
	/** device Id from Commercial Product */
	private String deviceId;
	
	/** make from Commercial Product */
	private String make;
	
	/** model from Commercial Product */
	private String model;
	
	/** non Upgrade Lead Plan Id Details*/
	private LeadPlanDetails nonUpgradeLeadPlanDetails;
	
	/** Upgrade Lead Plan Id Details*/
	private LeadPlanDetails upgradeLeadPlanDetails; 
	
	/** Device Display Name */
	private String displayName;
	
	/** Device Priority */
	private String priority;
	
	/** Device Display Description */
	private String displayDescription;
	
	/** Device Color */
	private String colourName;
	
	/** Device Color Hex Code */
	private String colourHex;
	
	/** Device Capacity */
	private String memory;
	
	/** Lead Plan Id Which is configure in MEF*/
	private String leadPlanIdForMEF;
	
	/**product Group URI */
	private String productGroupURI;
	
	/** Is It Eligible for preOrder or not */
	private Boolean preOrderable;
	
	/**  Product selling date  */
	private String availableFrom;
	
	/** product class */
	private String productClass;
	
	/**  Product  Lines */
	private List<String> productLines;
	
	/**  Merchandising Promotion */
	private List<MerchandisingPromotions> merchandisingPromotion;
	
	/**  Promote As For Hardware */
	private List<String> promotAsForHardware;

	/**  Product selling start date */
	private String startDate;

	/**  Product expiration date */
	private String endDate;

	/**  Product sales Expired Or Not */
	private Boolean salesExpired;
	
	/** Is Device Product Or Not */
	private Boolean isDeviceProduct;
	
	/** Is Service Product Or Not */
	private Boolean isServiceProduct;
	
	/** Merchandising Control details */
	private String isBattery;
	
	/** Merchandising Control details */
	private MerchandisingControl merchandisingControl;
	
	/** Compatible plans details */
	private List<String> compatiblePlans;
	
	/** Media Link details */
	private List<MediaLink> mediaLink;
	
	/** Meta data details */
	private MetaData metaData;
}
