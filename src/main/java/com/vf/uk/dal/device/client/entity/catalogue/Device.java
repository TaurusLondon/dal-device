package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.List;

import com.vf.uk.dal.device.client.entity.price.Duration;

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
	
	/** non Upgrade Lead Plan Id Details of Consumer*/
	private LeadPlanDetails nonUpgradeLeadPlanDetailsConsumer;
		
	/** Upgrade Lead Plan Id Details of Consumer*/
	private LeadPlanDetails upgradeLeadPlanDetailsConsumer; 
	
	/** non Upgrade Lead Plan Id Details of Business*/
	private LeadPlanDetails nonUpgradeLeadPlanDetailsBusiness;
		
	/** Upgrade Lead Plan Id Details of Business*/
	private LeadPlanDetails upgradeLeadPlanDetailsBusiness; 
	
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
	
	/** Meta data details */
	private MetaData metaData;
	
	/** Duration details **/
	private Duration duration;
	
	/** Discount details **/
	private Discount discount;
	
	/** PriceDetail **/
	private PriceDetail priceDetail;
	
	/** ProductAllowances **/
	private List<ProductAllowance> productAllowance;
	
	/** BoxPrice **/
	private BoxPrice boxPrice;
	
	/** ImageURLs **/
	private List<ImageURL> listOfimageURLs;

	/** MediaUrls **/
	private List<MediaURL> listOfMediaURLs;
	
	/** Misc **/
	private Misc misc;
	
	/** Groups **/
	private List<Group> specificationGroups;
	
	/** Set ProductGroup */
	private ProductGroups productGroups;
	
}


