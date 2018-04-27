package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;


/**
 * 
 * CommercialBundle
 *
 */
public class CommercialBundle {
	
	
	private String id;
	
	private String name;
	
	private String desc;
	
	private String paymentType;
	
	private Availability availability;
	
	private Commitment commitment;
	
	private List<String> productLines;
	
	private List<DevicePrice> deviceSpecificPricing;
	
	private List<ServiceProduct> serviceProducts;
	
	private List<Allowance> allowances;
	
	private Float recurringCharge;
	
	private String displayName;
	
	private List<ImageURL> listOfimageURLs;
	
	private List<Group> specificationGroups;

	private BundleControl bundleControl;
	
	
	private PromoteAs promoteAs;
	
	private String displayGroup;
	
	/**
	 * CommercialBundle Constructor
	 */
	public CommercialBundle() {
		super();
	}


	
	public String getId() {
		return this.id;
	}
	public void setBundleID(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public Commitment getCommitment() {
		return commitment;
	}

	public void setCommitment(Commitment commitment) {
		this.commitment = commitment;
	}

	public List<String> getProductLines() {
		return productLines;
	}

	public void setProductLines(List<String> productLines) {
		this.productLines = productLines;
	}

	public List<DevicePrice> getDeviceSpecificPricing() {
		return deviceSpecificPricing;
	}

	public void setDeviceSpecificPricing(List<DevicePrice> deviceSpecificPricing) {
		this.deviceSpecificPricing = deviceSpecificPricing;
	}

	public List<ServiceProduct> getServiceProducts() {
		return serviceProducts;
	}

	public void setServiceProducts(List<ServiceProduct> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}

	public List<Allowance> getAllowances() {
		return allowances;
	}

	public void setAllowances(List<Allowance> allowances) {
		this.allowances = allowances;
	}

	public Float getRecurringCharge() {
		return recurringCharge;
	}

	public void setRecurringCharge(Float recurringCharge) {
		this.recurringCharge = recurringCharge;
	}


	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/*public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getFullDetails() {
		return fullDetails;
	}

	public void setFullDetails(String fullDetails) {
		this.fullDetails = fullDetails;
	}

	public String getDescMobile() {
		return descMobile;
	}

	public void setDescMobile(String descMobile) {
		this.descMobile = descMobile;
	}

	public String getFullDetailsMobile() {
		return fullDetailsMobile;
	}

	public void setFullDetailsMobile(String fullDetailsMobile) {
		this.fullDetailsMobile = fullDetailsMobile;
	}

	public String getContentVersion() {
		return contentVersion;
	}

	public void setContentVersion(String contentVersion) {
		this.contentVersion = contentVersion;
	}

	public List<MediaURL> getListOfmediaURLs() {
		return listOfmediaURLs;
	}

	public void setListOfmediaURLs(List<MediaURL> listOfmediaURLs) {
		this.listOfmediaURLs = listOfmediaURLs;
	}*/
	public List<ImageURL> getListOfimageURLs() {
		return listOfimageURLs;
	}

	public void setListOfimageURLs(List<ImageURL> listOfimageURLs) {
		this.listOfimageURLs = listOfimageURLs;
	}

	public List<Group> getSpecificationGroups() {
		return specificationGroups;
	}

	public void setSpecificationGroups(List<Group> specificationGroups) {
		this.specificationGroups = specificationGroups;
	}

	public BundleControl getBundleControl() {
		return bundleControl;
	}

	public void setBundleControl(BundleControl bundleControl) {
		this.bundleControl = bundleControl;
	}

	public PromoteAs getPromoteAs() {
		return promoteAs;
	}

	public void setPromoteAs(PromoteAs promoteAs) {
		this.promoteAs = promoteAs;
	}


		public String getDisplayGroup() {
		return displayGroup;
	}

	public void setDisplayGroup(String displayGroup) {
		this.displayGroup = displayGroup;
	}



	@Override
	public String toString() {
		return "CommercialBundle [id=" + id + ", name=" + name + ", desc=" + desc + ", paymentType=" + paymentType
				+ ", availability=" + availability + ", commitment=" + commitment + ", productLines=" + productLines
				+ ", deviceSpecificPricing=" + deviceSpecificPricing + ", serviceProducts=" + serviceProducts
				+ ", allowances=" + allowances + ", recurringCharge=" + recurringCharge + ", displayName=" + displayName
				+ ", listOfimageURLs=" + listOfimageURLs + ", specificationGroups=" + specificationGroups
				+ ", bundleControl=" + bundleControl + ", promoteAs=" + promoteAs + ", displayGroup=" + displayGroup
				+ "]";
	}

}
