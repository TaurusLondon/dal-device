package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CommercialProduct {

	private String id;

	private Long order;

	private String name;

	private String productClass;

	private Boolean isServicesProduct;

	private Duration duration;

	private Discount discount;

	private String warranty;

	private String condition;

	private List<String> categories;

	private List<String> productLines;

	private PriceDetail priceDetail;

	private List<ProductAllowance> productAllowance;

	private BoxPrice boxPrice;

	private com.vf.uk.dal.device.datamodel.product.ProductAvailability productAvailability;

	private List<Relationship> productRelationshipList;

	private Boolean isDeviceProduct;

	private String inclusiveEligibility;

	private boolean omniChannelDiscountEligible;
	@JsonIgnore
	private String version;

	private String seoCanonical;

	private String seoDescription;

	private String seoKeywords;

	private String seoIndex;

	private List<String> seoRobots;

	private String productSubClass;

	private Equipment equipment;

	private String eligibilitySubflow;

	private List<Allowance> allowanceDisplay;

	private boolean ageRestricted;

	private Delivery delivery;

	private boolean isBattery;
	@JsonIgnore
	private String metadataVersion;

	private String displayName;

	private String preDesc;

	private String postDesc;

	private String preDescMobile;

	private String postDescMobile;

	private List<ImageURL> listOfimageURLs;

	private List<MediaURL> listOfmediaURLs;

	private List<HelpURL> listOfhelpURLs;

	private List<Group> specificationGroups;

	private String inTheBox;

	private String contentVersion;

	private ProductControl productControl;

	private ProductGroups productGroups;

	private Misc misc;

	private ProductPriceOverride productPriceOverride;

	private PromoteAs promoteAs;
	@JsonIgnore
	private String MerchandisingVersion;

	private String leadPlanId;

	private List<Recommendations> recommendataions;

	private List<String> listOfCompatiblePlanIds;

	private Boolean fastMoving;

	private String stockThresholdLimit;

	private List<EligibleChannel> listOfEligibleChannels;

	private String paymentType;

}
