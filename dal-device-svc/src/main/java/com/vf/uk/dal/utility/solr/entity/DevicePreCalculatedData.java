package com.vf.uk.dal.utility.solr.entity;

import java.util.List;
import java.util.Objects;
/**
 * 
 * DevicePreCalculatedData
 *
 */
public class DevicePreCalculatedData {
	private String deviceId;

	private Float rating;
	
	private String leadPlanId;

	private String nonUpgradeLeadPlanId;
	
	private String upgradeLeadPlanId;

	private String productGroupName;

	private String productGroupId;

	private List<Media> media;

	private PriceInfo priceInfo;
	
	private String minimumCost;
	
	private String isLeadMember;
	
	private String leadDeviceId;
	
	private String upgradeLeadDeviceId;
	
	private String nonUpgradeLeadDeviceId;
	
	
	/**
	 * 
	 * @param minimumCost
	 * @return
	 */
	public DevicePreCalculatedData minimumCost(String minimumCost) {
		this.minimumCost = minimumCost;
		return this;
	}
	/**
	 * 
	 * @return
	 */
	public String getMinimumCost() {
		return minimumCost;
	}
	/**
	 * 
	 * @param minimumCost
	 */
	public void setMinimumCost(String minimumCost) {
		this.minimumCost = minimumCost;
	}
	/**
	 * 
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 
	 * @return
	 */
	public String getDeviceId() {
		return this.deviceId;
	}
	/**
	 * 
	 * @param rating
	 */
	public void setRating(Float rating) {
		this.rating = rating;
	}
	/**
	 * 
	 * @return
	 */
	public Float getRating() {
		return this.rating;
	}
	/**
	 * 
	 * @param media
	 */
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	/**
	 * 
	 * @return
	 */
	public List<Media> getMedia() {
		return this.media;
	}
	/**
	 * 
	 * @param priceInfo
	 */
	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}
	/**
	 * 
	 * @return
	 */
	public PriceInfo getPriceInfo() {
		return this.priceInfo;
	}
	/**
	 * 
	 * @return
	 */
	public String getNonUpgradeLeadPlanId() {
		return nonUpgradeLeadPlanId;
	}
	/**
	 * 
	 * @param nonUpgradeLeadPlanId
	 */
	public void setNonUpgradeLeadPlanId(String nonUpgradeLeadPlanId) {
		this.nonUpgradeLeadPlanId = nonUpgradeLeadPlanId;
	}
	/**
	 * 
	 * @return
	 */
	public String getUpgradeLeadPlanId() {
		return upgradeLeadPlanId;
	}
	/**
	 * 
	 * @param upgradeLeadPlanId
	 */
	public void setUpgradeLeadPlanId(String upgradeLeadPlanId) {
		this.upgradeLeadPlanId = upgradeLeadPlanId;
	}
	/**
	 * 
	 * @return
	 */
	public String getProductGroupName() {
		return productGroupName;
	}
	/**
	 * 
	 * @param productGroupName
	 */
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	/**
	 * /**
	 * 
	 * @return
	 */
	public String getProductGroupId() {
		return productGroupId;
	}
	/**
	 * 
	 * @param productGroupId
	 */
	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}
	/**
	 * 
	 * @return
	 */
	public String getUpgradeLeadDeviceId()
	  {
	    return this.upgradeLeadDeviceId;
	  }
	/**
	 *   
	 * @param upgradeLeadDeviceId
	 */
	  public void setUpgradeLeadDeviceId(String upgradeLeadDeviceId)
	  {
	    this.upgradeLeadDeviceId = upgradeLeadDeviceId;
	  }
	  /**
	   *   
	   * @return
	   */
	  public String getNonUpgradeLeadDeviceId()
	  {
	    return this.nonUpgradeLeadDeviceId;
	  }
	  /**
	   * 	  
	   * @param nonUpgradeLeadDeviceId
	   */
	  public void setNonUpgradeLeadDeviceId(String nonUpgradeLeadDeviceId)
	  {
	    this.nonUpgradeLeadDeviceId = nonUpgradeLeadDeviceId;
	  }
	  /**
	   * 	  
	   * @return
	   */
	  	public String getIsLeadMember() {
			return isLeadMember;
		}

	  	/**
	  	 * 
	  	 * @param isLeadMember
	  	 */
		public void setIsLeadMember(String isLeadMember) {
			this.isLeadMember = isLeadMember;
		}
		/**
		 * 
		 * @return
		 */
		public String getLeadDeviceId() {
			return leadDeviceId;
		}
		/**
		 * 
		 * @param leadDeviceId
		 */
		public void setLeadDeviceId(String leadDeviceId) {
			this.leadDeviceId = leadDeviceId;
		}
		/**
		 * 		
		 * @return
		 */
		public String getLeadPlanId() {
			return leadPlanId;
		}
		/**
		 * 
		 * @param leadPlanId
		 */
		public void setLeadPlanId(String leadPlanId) {
			this.leadPlanId = leadPlanId;
		}

		/**
		 * 
		 * @param deviceId
		 * @return
		 */
	public DevicePreCalculatedData deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}
	/**
	 * 
	 * @param rating
	 * @return
	 */
	public DevicePreCalculatedData rating(Float rating) {
		this.rating = rating;
		return this;
	}
	/**
	 * /**
	 * @param nonUpgradeLeadPlanId
	 * @return
	 */
	public DevicePreCalculatedData nonUpgradeLeadPlanId(String nonUpgradeLeadPlanId) {
		this.nonUpgradeLeadPlanId = nonUpgradeLeadPlanId;
		return this;
	}

	/**
	 * 
	 * @param upgradeLeadPlanId
	 * @return
	 */
	public DevicePreCalculatedData upgradeLeadPlanId(String upgradeLeadPlanId) {
		this.upgradeLeadPlanId = upgradeLeadPlanId;
		return this;
	}
	/**
	 * 
	 * @param productGroupName
	 * @return
	 */

	public DevicePreCalculatedData productGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
		return this;
	}
	/**
	 * 
	 * @param productGroupId
	 * @return
	 */
	public DevicePreCalculatedData productGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
		return this;
	}
	/**
	 * 
	 * @param media
	 * @return
	 */
	public DevicePreCalculatedData media(List<Media> media) {
		this.media = media;
		return this;
	}
	/**
	 * 
	 * @param priceInfo
	 * @return
	 */
	public DevicePreCalculatedData priceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
		return this;
	}
	/**
	 * 	
	 * @param upgradeLeadDeviceId
	 * @return
	 */
	public DevicePreCalculatedData upgradeLeadDeviceId(String upgradeLeadDeviceId) {
		this.upgradeLeadDeviceId = upgradeLeadDeviceId;
		return this;
	}
	/**
	 * 
	 * @param nonUpgradeLeadDeviceId
	 * @return
	 */
	public DevicePreCalculatedData nonUpgradeLeadDeviceId(String nonUpgradeLeadDeviceId) {
		this.nonUpgradeLeadDeviceId = nonUpgradeLeadDeviceId;
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
				&& Objects.equals(this.nonUpgradeLeadPlanId, devicePreCalculatedData.nonUpgradeLeadPlanId)
				&& Objects.equals(this.upgradeLeadPlanId, devicePreCalculatedData.upgradeLeadPlanId)
				&& Objects.equals(this.productGroupName, devicePreCalculatedData.productGroupName)
				&& Objects.equals(this.productGroupId, devicePreCalculatedData.productGroupId)
				&& Objects.equals(this.media, devicePreCalculatedData.media)
				&& Objects.equals(this.priceInfo, devicePreCalculatedData.priceInfo)
				&& Objects.equals(this.minimumCost, devicePreCalculatedData.minimumCost)
				&& Objects.equals(this.upgradeLeadDeviceId, devicePreCalculatedData.upgradeLeadDeviceId)
				&& Objects.equals(this.nonUpgradeLeadDeviceId, devicePreCalculatedData.nonUpgradeLeadDeviceId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceId, rating, nonUpgradeLeadPlanId,upgradeLeadPlanId,productGroupName,
				productGroupId, media, priceInfo,minimumCost,upgradeLeadDeviceId,nonUpgradeLeadDeviceId);
	}

	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		sb.append("class DevicePreCalculatedData {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
		sb.append("    nonUpgradeLeadPlanId: ").append(toIndentedString(nonUpgradeLeadPlanId)).append("\n");
		sb.append("    upgradeLeadPlanId: ").append(toIndentedString(upgradeLeadPlanId)).append("\n");
		sb.append("    productGroupName: ").append(toIndentedString(productGroupName)).append("\n");
		sb.append("    productGroupId: ").append(toIndentedString(productGroupId)).append("\n");
		sb.append("    media: ").append(toIndentedString(media)).append("\n");
		sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
		sb.append("    minimumCost: ").append(toIndentedString(minimumCost)).append("\n");
		sb.append("    upgradeLeadDeviceId: ").append(toIndentedString(upgradeLeadDeviceId)).append("\n");
		sb.append("    nonUpgradeLeadDeviceId: ").append(toIndentedString(nonUpgradeLeadDeviceId)).append("\n");
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
