package com.vf.uk.dal.device.model.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * CommercialProduct
 * @author manoj.bera
 *
 */
@Data
public class CommercialProduct {

	@JsonProperty("id")
	private String id;

	@JsonProperty("order")
	private Long order;

	@JsonProperty("name")
	private String name;

	@JsonProperty("productClass")
	private String productClass;

	@JsonProperty("isServicesProduct")
	private Boolean isServicesProduct;

	@JsonProperty("duration")
	private Duration duration;

	@JsonProperty("discount")
	private Discount discount;

	@JsonProperty("warranty")
	private String warranty;

	@JsonProperty("condition")
	private String condition;

	@JsonProperty("categories")
	private List<String> categories;

	@JsonProperty("productLines")
	private List<String> productLines;

	@JsonProperty("priceDetail")
	private PriceDetail priceDetail;

	@JsonProperty("productAllowance")
	private List<ProductAllowance> productAllowance;

	@JsonProperty("boxPrice")
	private BoxPrice boxPrice;

	@JsonProperty("productAvailability")
	private com.vf.uk.dal.device.model.product.ProductAvailability productAvailability;

	@JsonProperty("productRelationshipList")
	private List<Relationship> productRelationshipList;

	@JsonProperty("isDeviceProduct")
	private Boolean isDeviceProduct;

	@JsonProperty("inclusiveEligibility")
	private String inclusiveEligibility;

	@JsonProperty("omniChannelDiscountEligible")
	private boolean omniChannelDiscountEligible;
	
	@JsonIgnore
	private String version;

	@JsonProperty("seoCanonical")
	private String seoCanonical;

	@JsonProperty("seoDescription")
	private String seoDescription;

	@JsonProperty("seoKeywords")
	private String seoKeywords;

	@JsonProperty("seoIndex")
	private String seoIndex;

	@JsonProperty("seoRobots")
	private List<String> seoRobots;

	@JsonProperty("productSubClass")
	private String productSubClass;

	@JsonProperty("equipment")
	private Equipment equipment;

	@JsonProperty("eligibilitySubflow")
	private String eligibilitySubflow;

	@JsonProperty("allowanceDisplay")
	private List<Allowance> allowanceDisplay;

	@JsonProperty("ageRestricted")
	private String ageRestricted;

	@JsonProperty("delivery")
	private Delivery delivery;

	@JsonProperty("isBattery")
	private boolean isBattery;
	
	@JsonIgnore
	private String metadataVersion;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("preDesc")
	private String preDesc;

	@JsonProperty("postDesc")
	private String postDesc;

	@JsonProperty("preDescMobile")
	private String preDescMobile;

	@JsonProperty("postDescMobile")
	private String postDescMobile;

	@JsonProperty("listOfimageURLs")
	private List<ImageURL> listOfimageURLs;

	@JsonProperty("listOfmediaURLs")
	private List<MediaURL> listOfmediaURLs;

	@JsonProperty("listOfhelpURLs")
	private List<HelpURL> listOfhelpURLs;
	
	@JsonProperty("specificationGroups")
	private List<Group> specificationGroups;

	@JsonProperty("inTheBox")
	private String inTheBox;

	@JsonProperty("contentVersion")
	private String contentVersion;

	@JsonProperty("productControl")
	private ProductControl productControl;

	@JsonProperty("productGroups")
	private ProductGroups productGroups;

	@JsonProperty("misc")
	private Misc misc;

	@JsonProperty("productPriceOverride")
	private ProductPriceOverride productPriceOverride;

	@JsonProperty("promoteAs")
	private PromoteAs promoteAs;
	
	@JsonIgnore
	private String merchandisingVersion;

	@JsonProperty("leadPlanId")
	private String leadPlanId;

	@JsonProperty("recommendataions")
	private List<Recommendations> recommendataions;

	@JsonProperty("listOfCompatiblePlanIds")
	private List<String> listOfCompatiblePlanIds;

	@JsonProperty("fastMoving")
	private Boolean fastMoving;

	@JsonProperty("stockThresholdLimit")
	private String stockThresholdLimit;

	@JsonProperty("listOfEligibleChannels")
	private List<EligibleChannel> listOfEligibleChannels;

	@JsonProperty("paymentType")
	private String paymentType;

}
