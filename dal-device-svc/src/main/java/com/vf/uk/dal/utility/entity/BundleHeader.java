package com.vf.uk.dal.utility.entity;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * BundleHeader.
 */

public class BundleHeader {

	/** The sku id. */
	private String skuId = null;

	/** The name. */
	private String name = null;

	/** The description. */
	private String description = null;

	/** The bundle name. */
	private String bundleName = null;

	/** The bundle description. */
	private String bundleDescription = null;

	/** The bundle class. */
	private String bundleClass = null;

	/** The product class. */
	private String productClass = null;

	/** The payment type. */
	private String paymentType = null;

	/** The bundle type. */
	private String bundleType = null;

	/** The plan couple id. */
	private String planCoupleId = null;

	/** The plan couple flag. */
	private Boolean planCoupleFlag = null;

	/** The plan couple lable. */
	private String planCoupleLable = null;

	/** The global roaming flag. */
	private Boolean globalRoamingFlag = null;

	/** The Secure Net */
	private Boolean secureNetFlag = null;

	/**
	 * 
	 * @param secureNetFlag
	 * @return
	 */
	public BundleHeader secureNetFlag(Boolean secureNetFlag) {
		this.secureNetFlag = secureNetFlag;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getSecureNetFlag() {
		return secureNetFlag;
	}

	/**
	 * 
	 * @param secureNetFlag
	 */
	public void setSecureNetFlag(Boolean secureNetFlag) {
		this.secureNetFlag = secureNetFlag;
	}

	/** The commitment period. */
	private String commitmentPeriod = null;

	/** The mobile line rental id. */
	private String mobileLineRentalId = null;

	/** The mobile service id. */
	private String mobileServiceId = null;

	/** The allowance. */
	private List<BundleAllowance> allowance = new ArrayList<>();

	/** The roaming allowance. */
	private List<BundleAllowance> roamingAllowance = new ArrayList<>();

	/** The merchandising media. */
	private List<MediaLink> merchandisingMedia = new ArrayList<>();

	/** The price info. */
	private PriceForBundleAndHardware priceInfo = null;

	/** The mcs. */
	private List<MonthlyCostSaver> mcs = new ArrayList<>();

	/**
	 * Sku id.
	 *
	 * @param skuId
	 *            the sku id
	 * @return the bundle header
	 */
	public BundleHeader skuId(String skuId) {
		this.skuId = skuId;
		return this;
	}

	/**
	 * Unique bundle id as available from the product catalogue.
	 *
	 * @return skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * Sets the sku id.
	 *
	 * @param skuId
	 *            the new sku id
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * Name.
	 *
	 * @param name
	 *            the name
	 * @return the bundle header
	 */
	public BundleHeader name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Name of the bundle as provided in the product catalogue.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Description.
	 *
	 * @param description
	 *            the description
	 * @return the bundle header
	 */
	public BundleHeader description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Description of the bundle as provided in the product catalogue.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Bundle name.
	 *
	 * @param bundleName
	 *            the bundle name
	 * @return the bundle header
	 */
	public BundleHeader bundleName(String bundleName) {
		this.bundleName = bundleName;
		return this;
	}

	/**
	 * Name of the bundle as provided in the merchandising file.
	 *
	 * @return bundleName
	 */
	public String getBundleName() {
		return bundleName;
	}

	/**
	 * Sets the bundle name.
	 *
	 * @param bundleName
	 *            the new bundle name
	 */
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	/**
	 * Bundle description.
	 *
	 * @param bundleDescription
	 *            the bundle description
	 * @return the bundle header
	 */
	public BundleHeader bundleDescription(String bundleDescription) {
		this.bundleDescription = bundleDescription;
		return this;
	}

	/**
	 * Description of the bundle as provided in the merchandising file.
	 *
	 * @return bundleDescription
	 */
	public String getBundleDescription() {
		return bundleDescription;
	}

	/**
	 * Sets the bundle description.
	 *
	 * @param bundleDescription
	 *            the new bundle description
	 */
	public void setBundleDescription(String bundleDescription) {
		this.bundleDescription = bundleDescription;
	}

	/**
	 * Bundle class.
	 *
	 * @param bundleClass
	 *            the bundle class
	 * @return the bundle header
	 */
	public BundleHeader bundleClass(String bundleClass) {
		this.bundleClass = bundleClass;
		return this;
	}

	/**
	 * Catalogue product class identifies products - SIMO, HANDSET etc.
	 * 
	 * @return bundleClass
	 **/
	public String getBundleClass() {
		return bundleClass;
	}

	/**
	 * Sets the bundle class.
	 *
	 * @param bundleClass
	 *            the new bundle class
	 */
	public void setBundleClass(String bundleClass) {
		this.bundleClass = bundleClass;
	}

	/**
	 * Product class.
	 *
	 * @param productClass
	 *            the product class
	 * @return the bundle header
	 */
	public BundleHeader productClass(String productClass) {
		this.productClass = productClass;
		return this;
	}

	/**
	 * Catalogue product class identifies products.
	 *
	 * @return productClass
	 */
	public String getProductClass() {
		return productClass;
	}

	/**
	 * Sets the product class.
	 *
	 * @param productClass
	 *            the new product class
	 */
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	/**
	 * Payment type.
	 *
	 * @param paymentType
	 *            the payment type
	 * @return the bundle header
	 */
	public BundleHeader paymentType(String paymentType) {
		this.paymentType = paymentType;
		return this;
	}

	/**
	 * Payment type of the bundle. For example, \"postpaid\", \"prepaid\" etc.
	 * 
	 * @return paymentType
	 **/
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType
	 *            the new payment type
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Bundle type.
	 *
	 * @param bundleType
	 *            the bundle type
	 * @return the bundle header
	 */
	public BundleHeader bundleType(String bundleType) {
		this.bundleType = bundleType;
		return this;
	}

	/**
	 * Type of the bundle. For example, Standard, Red, RedPlus etc.
	 * 
	 * @return bundleType
	 **/
	public String getBundleType() {
		return bundleType;
	}

	/**
	 * Sets the bundle type.
	 *
	 * @param bundleType
	 *            the new bundle type
	 */
	public void setBundleType(String bundleType) {
		this.bundleType = bundleType;
	}

	/**
	 * Plan couple id.
	 *
	 * @param planCoupleId
	 *            the plan couple id
	 * @return the bundle header
	 */
	public BundleHeader planCoupleId(String planCoupleId) {
		this.planCoupleId = planCoupleId;
		return this;
	}

	/**
	 * PlanID of the Couple Plan.
	 *
	 * @return planCoupleId
	 */
	public String getPlanCoupleId() {
		return planCoupleId;
	}

	/**
	 * Sets the plan couple id.
	 *
	 * @param planCoupleId
	 *            the new plan couple id
	 */
	public void setPlanCoupleId(String planCoupleId) {
		this.planCoupleId = planCoupleId;
	}

	/**
	 * Plan couple flag.
	 *
	 * @param planCoupleFlag
	 *            the plan couple flag
	 * @return the bundle header
	 */
	public BundleHeader planCoupleFlag(Boolean planCoupleFlag) {
		this.planCoupleFlag = planCoupleFlag;
		return this;
	}

	/**
	 * Indicate whether this plan is to be coupled or not True, False.
	 * 
	 * @return planCoupleFlag
	 **/
	public Boolean getPlanCoupleFlag() {
		return planCoupleFlag;
	}

	/**
	 * Sets the plan couple flag.
	 *
	 * @param planCoupleFlag
	 *            the new plan couple flag
	 */
	public void setPlanCoupleFlag(Boolean planCoupleFlag) {
		this.planCoupleFlag = planCoupleFlag;
	}

	/**
	 * Plan couple lable.
	 *
	 * @param planCoupleLable
	 *            the plan couple lable
	 * @return the bundle header
	 */
	public BundleHeader planCoupleLable(String planCoupleLable) {
		this.planCoupleLable = planCoupleLable;
		return this;
	}

	/**
	 * Message needs to be build based on data difference and cost logic.
	 * 
	 * @return planCoupleLable
	 **/
	public String getPlanCoupleLable() {
		return planCoupleLable;
	}

	/**
	 * Sets the plan couple lable.
	 *
	 * @param planCoupleLable
	 *            the new plan couple lable
	 */
	public void setPlanCoupleLable(String planCoupleLable) {
		this.planCoupleLable = planCoupleLable;
	}

	/**
	 * Global roaming flag.
	 *
	 * @param globalRoamingFlag
	 *            the global roaming flag
	 * @return the bundle header
	 */
	public BundleHeader globalRoamingFlag(Boolean globalRoamingFlag) {
		this.globalRoamingFlag = globalRoamingFlag;
		return this;
	}

	/**
	 * Indicate whether this plan having Global Roaming or not .
	 *
	 * @return globalRoamingFlag
	 */
	public Boolean getGlobalRoamingFlag() {
		return globalRoamingFlag;
	}

	/**
	 * Sets the global roaming flag.
	 *
	 * @param globalRoamingFlag
	 *            the new global roaming flag
	 */
	public void setGlobalRoamingFlag(Boolean globalRoamingFlag) {
		this.globalRoamingFlag = globalRoamingFlag;
	}

	/**
	 * Commitment period.
	 *
	 * @param commitmentPeriod
	 *            the commitment period
	 * @return the bundle header
	 */
	public BundleHeader commitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
		return this;
	}

	/**
	 * Commitment period of the bundle.
	 *
	 * @return commitmentPeriod
	 */
	public String getCommitmentPeriod() {
		return commitmentPeriod;
	}

	/**
	 * Sets the commitment period.
	 *
	 * @param commitmentPeriod
	 *            the new commitment period
	 */
	public void setCommitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

	/**
	 * Allowance.
	 *
	 * @param allowance
	 *            the allowance
	 * @return the bundle header
	 */
	public BundleHeader allowance(List<BundleAllowance> allowance) {
		this.allowance = allowance;
		return this;
	}

	/**
	 * Adds the allowance item.
	 *
	 * @param allowanceItem
	 *            the allowance item
	 * @return the bundle header
	 */
	public BundleHeader addAllowanceItem(BundleAllowance allowanceItem) {
		this.allowance.add(allowanceItem);
		return this;
	}

	/**
	 * Get allowance.
	 *
	 * @return allowance
	 */
	public List<BundleAllowance> getAllowance() {
		return allowance;
	}

	/**
	 * Sets the allowance.
	 *
	 * @param allowance
	 *            the new allowance
	 */
	public void setAllowance(List<BundleAllowance> allowance) {
		this.allowance = allowance;
	}

	/**
	 * Roaming allowance.
	 *
	 * @param roamingAllowance
	 *            the roaming allowance
	 * @return the bundle header
	 */
	public BundleHeader roamingAllowance(List<BundleAllowance> roamingAllowance) {
		this.roamingAllowance = roamingAllowance;
		return this;
	}

	/**
	 * Adds the roaming allowance item.
	 *
	 * @param roamingAllowanceItem
	 *            the roaming allowance item
	 * @return the bundle header
	 */
	public BundleHeader addRoamingAllowanceItem(BundleAllowance roamingAllowanceItem) {
		this.roamingAllowance.add(roamingAllowanceItem);
		return this;
	}

	/**
	 * Get roamingAllowance.
	 *
	 * @return roamingAllowance
	 */
	public List<BundleAllowance> getRoamingAllowance() {
		return roamingAllowance;
	}

	/**
	 * Sets the roaming allowance.
	 *
	 * @param roamingAllowance
	 *            the new roaming allowance
	 */
	public void setRoamingAllowance(List<BundleAllowance> roamingAllowance) {
		this.roamingAllowance = roamingAllowance;
	}

	/**
	 * Merchandising media.
	 *
	 * @param merchandisingMedia
	 *            the merchandising media
	 * @return the bundle header
	 */
	public BundleHeader merchandisingMedia(List<MediaLink> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
		return this;
	}

	/**
	 * Adds the merchandising media item.
	 *
	 * @param merchandisingMediaItem
	 *            the merchandising media item
	 * @return the bundle header
	 */
	public BundleHeader addMerchandisingMediaItem(MediaLink merchandisingMediaItem) {
		this.merchandisingMedia.add(merchandisingMediaItem);
		return this;
	}

	/**
	 * Get merchandisingMedia.
	 *
	 * @return merchandisingMedia
	 */
	public List<MediaLink> getMerchandisingMedia() {
		return merchandisingMedia;
	}

	/**
	 * Sets the merchandising media.
	 *
	 * @param merchandisingMedia
	 *            the new merchandising media
	 */
	public void setMerchandisingMedia(List<MediaLink> merchandisingMedia) {
		this.merchandisingMedia = merchandisingMedia;
	}

	/**
	 * Price info.
	 *
	 * @param priceInfo
	 *            the price info
	 * @return the bundle header
	 */
	public BundleHeader priceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
		return this;
	}

	/**
	 * Get priceInfo.
	 *
	 * @return priceInfo
	 */
	public PriceForBundleAndHardware getPriceInfo() {
		return priceInfo;
	}

	/**
	 * Sets the price info.
	 *
	 * @param priceInfo
	 *            the new price info
	 */
	public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
	}

	/**
	 * Mcs.
	 *
	 * @param mcs
	 *            the mcs
	 * @return the bundle header
	 */
	public BundleHeader mcs(List<MonthlyCostSaver> mcs) {
		this.mcs = mcs;
		return this;
	}

	/**
	 * Adds the mcs item.
	 *
	 * @param mcsItem
	 *            the mcs item
	 * @return the bundle header
	 */
	public BundleHeader addMcsItem(MonthlyCostSaver mcsItem) {
		this.mcs.add(mcsItem);
		return this;
	}

	/**
	 * Get mcs.
	 *
	 * @return mcs
	 */
	public List<MonthlyCostSaver> getMcs() {
		return mcs;
	}

	/**
	 * Sets the mcs.
	 *
	 * @param mcs
	 *            the new mcs
	 */
	public void setMcs(List<MonthlyCostSaver> mcs) {
		this.mcs = mcs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BundleHeader bundleHeader = (BundleHeader) o;
		return Objects.equals(this.skuId, bundleHeader.skuId) && Objects.equals(this.name, bundleHeader.name)
				&& Objects.equals(this.description, bundleHeader.description)
				&& Objects.equals(this.bundleName, bundleHeader.bundleName)
				&& Objects.equals(this.bundleDescription, bundleHeader.bundleDescription)
				&& Objects.equals(this.bundleClass, bundleHeader.bundleClass)
				&& Objects.equals(this.productClass, bundleHeader.productClass)
				&& Objects.equals(this.paymentType, bundleHeader.paymentType)
				&& Objects.equals(this.bundleType, bundleHeader.bundleType)
				&& Objects.equals(this.planCoupleId, bundleHeader.planCoupleId)
				&& Objects.equals(this.planCoupleFlag, bundleHeader.planCoupleFlag)
				&& Objects.equals(this.planCoupleLable, bundleHeader.planCoupleLable)
				&& Objects.equals(this.globalRoamingFlag, bundleHeader.globalRoamingFlag)
				&& Objects.equals(this.commitmentPeriod, bundleHeader.commitmentPeriod)
				&& Objects.equals(this.allowance, bundleHeader.allowance)
				&& Objects.equals(this.roamingAllowance, bundleHeader.roamingAllowance)
				&& Objects.equals(this.merchandisingMedia, bundleHeader.merchandisingMedia)
				&& Objects.equals(this.priceInfo, bundleHeader.priceInfo) && Objects.equals(this.mcs, bundleHeader.mcs)
				&& Objects.equals(this.secureNetFlag, bundleHeader.secureNetFlag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(skuId, name, description, bundleName, bundleDescription, bundleClass, productClass,
				paymentType, bundleType, planCoupleId, planCoupleFlag, planCoupleLable, globalRoamingFlag,
				commitmentPeriod, allowance, roamingAllowance, merchandisingMedia, priceInfo, mcs, secureNetFlag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BundleHeader {\n");

		sb.append("    skuId: ").append(toIndentedString(skuId)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    bundleName: ").append(toIndentedString(bundleName)).append("\n");
		sb.append("    bundleDescription: ").append(toIndentedString(bundleDescription)).append("\n");
		sb.append("    bundleClass: ").append(toIndentedString(bundleClass)).append("\n");
		sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
		sb.append("    paymentType: ").append(toIndentedString(paymentType)).append("\n");
		sb.append("    bundleType: ").append(toIndentedString(bundleType)).append("\n");
		sb.append("    planCoupleId: ").append(toIndentedString(planCoupleId)).append("\n");
		sb.append("    planCoupleFlag: ").append(toIndentedString(planCoupleFlag)).append("\n");
		sb.append("    planCoupleLable: ").append(toIndentedString(planCoupleLable)).append("\n");
		sb.append("    globalRoamingFlag: ").append(toIndentedString(globalRoamingFlag)).append("\n");
		sb.append("    commitmentPeriod: ").append(toIndentedString(commitmentPeriod)).append("\n");
		sb.append("    allowance: ").append(toIndentedString(allowance)).append("\n");
		sb.append("    roamingAllowance: ").append(toIndentedString(roamingAllowance)).append("\n");
		sb.append("    merchandisingMedia: ").append(toIndentedString(merchandisingMedia)).append("\n");
		sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
		sb.append("    mcs: ").append(toIndentedString(mcs)).append("\n");
		sb.append("    secureNetFlag: ").append(toIndentedString(secureNetFlag)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 *
	 * @param o
	 *            the o
	 * @return the string
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	/**
	 * Gets the mobile line rental id.
	 *
	 * @return the mobile line rental id
	 */
	public String getMobileLineRentalId() {
		return mobileLineRentalId;
	}

	/**
	 * Sets the mobile line rental id.
	 *
	 * @param mobileLineRentalId
	 *            the new mobile line rental id
	 */
	public void setMobileLineRentalId(String mobileLineRentalId) {
		this.mobileLineRentalId = mobileLineRentalId;
	}

	/**
	 * Gets the mobile service id.
	 *
	 * @return the mobile service id
	 */
	public String getMobileServiceId() {
		return mobileServiceId;
	}

	/**
	 * Sets the mobile service id.
	 *
	 * @param mobileServiceId
	 *            the new mobile service id
	 */
	public void setMobileServiceId(String mobileServiceId) {
		this.mobileServiceId = mobileServiceId;
	}

}
