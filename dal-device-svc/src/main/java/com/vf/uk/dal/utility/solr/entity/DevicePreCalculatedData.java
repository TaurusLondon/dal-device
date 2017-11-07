package com.vf.uk.dal.utility.solr.entity;

import java.util.List;
import java.util.Objects;

public class DevicePreCalculatedData {
	private String deviceId;

	private Float rating;

	private String leadPlanId;

	private String productGroupName;

	private String productGroupId;

	private List<Media> media;

	private PriceInfo priceInfo;
	
	private String isLeadMember;
	
	private String minimumCost;
	
	public DevicePreCalculatedData isLeadMember(String isLeadMember) {
		this.isLeadMember = isLeadMember;
		return this;
	}
	
	public DevicePreCalculatedData minimumCost(String minimumCost) {
		this.minimumCost = minimumCost;
		return this;
	}
	public String getIsLeadMember() {
		return isLeadMember;
	}

	public void setIsLeadMember(String isLeadMember) {
		this.isLeadMember = isLeadMember;
	}

	public String getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(String minimumCost) {
		this.minimumCost = minimumCost;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	public List<Media> getMedia() {
		return this.media;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}

	public PriceInfo getPriceInfo() {
		return this.priceInfo;
	}

	public String getLeadPlanId() {
		return leadPlanId;
	}

	public void setLeadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}
	
	public DevicePreCalculatedData deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	public DevicePreCalculatedData rating(Float rating) {
		this.rating = rating;
		return this;
	}

	public DevicePreCalculatedData leadPlanId(String leadPlanId) {
		this.leadPlanId = leadPlanId;
		return this;
	}

	public DevicePreCalculatedData productGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
		return this;
	}

	public DevicePreCalculatedData productGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
		return this;
	}

	public DevicePreCalculatedData media(List<Media> media) {
		this.media = media;
		return this;
	}

	public DevicePreCalculatedData priceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
		return this;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DevicePreCalculatedData devicePreCalculatedData = (DevicePreCalculatedData) o;
		return Objects.equals(this.deviceId, devicePreCalculatedData.deviceId)
				&& Objects.equals(this.rating, devicePreCalculatedData.rating)
				&& Objects.equals(this.leadPlanId, devicePreCalculatedData.leadPlanId)
				&& Objects.equals(this.productGroupName, devicePreCalculatedData.productGroupName)
				&& Objects.equals(this.productGroupId, devicePreCalculatedData.productGroupId)
				&& Objects.equals(this.media, devicePreCalculatedData.media)
				&& Objects.equals(this.priceInfo, devicePreCalculatedData.priceInfo)
				&& Objects.equals(this.isLeadMember, devicePreCalculatedData.isLeadMember)
				&& Objects.equals(this.minimumCost, devicePreCalculatedData.minimumCost);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceId, rating, leadPlanId, productGroupName,
				productGroupId, media, priceInfo, isLeadMember, minimumCost);
	}

	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		sb.append("class DevicePreCalculatedData {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
		sb.append("    leadPlanId: ").append(toIndentedString(leadPlanId)).append("\n");
		sb.append("    productGroupName: ").append(toIndentedString(productGroupName)).append("\n");
		sb.append("    productGroupId: ").append(toIndentedString(productGroupId)).append("\n");
		sb.append("    media: ").append(toIndentedString(media)).append("\n");
		sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
		sb.append("    isLeadMember: ").append(toIndentedString(isLeadMember)).append("\n");
		sb.append("    minimumCost: ").append(toIndentedString(minimumCost)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
