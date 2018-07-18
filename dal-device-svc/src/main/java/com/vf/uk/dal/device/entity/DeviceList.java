package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DeviceList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-15T13:18:08.775Z")

public class DeviceList {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("rating")
	private Integer rating = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("productClass")
	private String productClass = null;

	@JsonProperty("merchandisingControl")
	private MerchandisingControl merchandisingControl = null;

	@JsonProperty("media")
	private List<MediaLink> media = new ArrayList<MediaLink>();

	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public DeviceList deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	/**
	 * Product id of the requested device from the product catalogue
	 * 
	 * @return deviceId
	 **/
	public String getDeviceId() {
		return deviceId;
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
	 * @param name
	 * @return
	 */
	public DeviceList name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Name of the product group as provided in the product catalogue
	 * 
	 * @return name
	 **/
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param rating
	 * @return
	 */
	public DeviceList rating(Integer rating) {
		this.rating = rating;
		return this;
	}

	/**
	 * This will indicate the number of rating starts to be displayed on screen.
	 * 
	 * @return rating
	 **/
	public Integer getRating() {
		return rating;
	}

	/**
	 * 
	 * @param rating
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	public DeviceList description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Description of the product as provided in the product catalogue
	 * 
	 * @return description
	 **/
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @param productClass
	 * @return
	 */
	public DeviceList productClass(String productClass) {
		this.productClass = productClass;
		return this;
	}

	/**
	 * Catalogue product class identifies products - SIMO, HANDSET etc.
	 * 
	 * @return productClass
	 **/
	public String getProductClass() {
		return productClass;
	}

	/**
	 * 
	 * @param productClass
	 */
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	/**
	 * 
	 * @param merchandisingControl
	 * @return
	 */
	public DeviceList merchandisingControl(MerchandisingControl merchandisingControl) {
		this.merchandisingControl = merchandisingControl;
		return this;
	}

	/**
	 * Get merchandisingControl
	 * 
	 * @return merchandisingControl
	 **/
	public MerchandisingControl getMerchandisingControl() {
		return merchandisingControl;
	}

	/**
	 * 
	 * @param merchandisingControl
	 */
	public void setMerchandisingControl(MerchandisingControl merchandisingControl) {
		this.merchandisingControl = merchandisingControl;
	}

	/**
	 * 
	 * @param media
	 * @return
	 */
	public DeviceList media(List<MediaLink> media) {
		this.media = media;
		return this;
	}

	/**
	 * 
	 * @param mediaItem
	 * @return
	 */
	public DeviceList addMediaItem(MediaLink mediaItem) {
		this.media.add(mediaItem);
		return this;
	}

	/**
	 * Get media
	 * 
	 * @return media
	 **/
	public List<MediaLink> getMedia() {
		return media;
	}

	/**
	 * 
	 * @param media
	 */
	public void setMedia(List<MediaLink> media) {
		this.media = media;
	}

	/**
	 * 
	 * @param priceInfo
	 * @return
	 */
	public DeviceList priceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
		return this;
	}

	/**
	 * Get priceInfo
	 * 
	 * @return priceInfo
	 **/
	public PriceForBundleAndHardware getPriceInfo() {
		return priceInfo;
	}

	/**
	 * 
	 * @param priceInfo
	 */
	public void setPriceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DeviceList deviceList = (DeviceList) o;
		return Objects.equals(this.deviceId, deviceList.deviceId) && Objects.equals(this.name, deviceList.name)
				&& Objects.equals(this.rating, deviceList.rating)
				&& Objects.equals(this.description, deviceList.description)
				&& Objects.equals(this.productClass, deviceList.productClass)
				&& Objects.equals(this.merchandisingControl, deviceList.merchandisingControl)
				&& Objects.equals(this.media, deviceList.media) && Objects.equals(this.priceInfo, deviceList.priceInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceId, name, rating, description, productClass, merchandisingControl, media, priceInfo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DeviceList {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
		sb.append("    merchandisingControl: ").append(toIndentedString(merchandisingControl)).append("\n");
		sb.append("    media: ").append(toIndentedString(media)).append("\n");
		sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
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
