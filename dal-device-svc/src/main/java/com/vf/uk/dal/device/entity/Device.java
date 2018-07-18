package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Device
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T12:25:09.565Z")

public class Device {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("make")
	private String make = null;

	@JsonProperty("model")
	private String model = null;

	@JsonProperty("groupType")
	private String groupType = null;

	@JsonProperty("rating")
	private String rating = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("productClass")
	private String productClass = null;

	@JsonProperty("merchandisingControl")
	private MerchandisingControl merchandisingControl = null;

	@JsonProperty("media")
	private List<MediaLink> media = null;

	@JsonProperty("priceInfo")
	private PriceForBundleAndHardware priceInfo = null;

	/** The promotions package. */
	@JsonProperty("promotionsPackage")
	private MerchandisingPromotionsPackage promotionsPackage = null;

	/**
	 * Promotions package.
	 *
	 * @param promotionsPackage
	 *            the promotions package
	 * @return the device summary
	 */
	public Device promotionsPackage(MerchandisingPromotionsPackage promotionsPackage) {
		this.promotionsPackage = promotionsPackage;
		return this;
	}

	/**
	 * Get promotionsPackage.
	 *
	 * @return promotionsPackage
	 */
	public MerchandisingPromotionsPackage getPromotionsPackage() {
		return promotionsPackage;
	}

	/**
	 * Sets the promotions package.
	 *
	 * @param promotionsPackage
	 *            the new promotions package
	 */
	public void setPromotionsPackage(MerchandisingPromotionsPackage promotionsPackage) {
		this.promotionsPackage = promotionsPackage;
	}

	/**
	 * 
	 * @return
	 */
	public Device deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	/**
	 * Product id of the requested device from the product catalogue
	 * 
	 * @return deviceId
	 **/
	@ApiModelProperty(value = "Product id of the requested device from the product catalogue")

	/**
	 * 
	 * @return
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * 
	 * @return
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 
	 * @return
	 */
	public Device name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Name of the product group as provided in the product catalogue
	 * 
	 * @return name
	 **/
	@ApiModelProperty(value = "Name of the product group as provided in the product catalogue")

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public Device make(String make) {
		this.make = make;
		return this;
	}

	/**
	 * Make of the product
	 * 
	 * @return make
	 **/
	@ApiModelProperty(value = "Make of the product")

	/**
	 * 
	 * @return
	 */
	public String getMake() {
		return make;
	}

	/**
	 * 
	 * @return
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * 
	 * @return
	 */
	public Device model(String model) {
		this.model = model;
		return this;
	}

	/**
	 * Model of the product
	 * 
	 * @return model
	 **/
	@ApiModelProperty(value = "Model of the product")

	/**
	 * 
	 * @return
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 
	 * @return
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 
	 * @return
	 */
	public Device groupType(String groupType) {
		this.groupType = groupType;
		return this;
	}

	/**
	 * Group type of product group DEVICE_PAYM, DEVICE_PAYG
	 * 
	 * @return groupType
	 **/
	@ApiModelProperty(value = "Group type of product group DEVICE_PAYM, DEVICE_PAYG")

	/**
	 * 
	 * @return
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * 
	 * @return
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * 
	 * @return
	 */
	public Device rating(String rating) {
		this.rating = rating;
		return this;
	}

	/**
	 * This will indicate the number of rating starts to be displayed on screen.
	 * 
	 * @return rating
	 **/
	@ApiModelProperty(value = "This will indicate the number of rating starts to be displayed on screen.")

	/**
	 * 
	 * @return
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * 
	 * @return
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	public Device description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Description of the product as provided in the product catalogue
	 * 
	 * @return description
	 **/
	@ApiModelProperty(value = "Description of the product as provided in the product catalogue")

	/**
	 * 
	 * @return
	 */
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
	public Device productClass(String productClass) {
		this.productClass = productClass;
		return this;
	}

	/**
	 * Catalogue product class identifies products - SIMO, HANDSET etc.
	 * 
	 * @return productClass
	 **/
	@ApiModelProperty(value = "Catalogue product class identifies products - SIMO, HANDSET etc.")

	/**
	 * 
	 * @return
	 */
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
	public Device merchandisingControl(MerchandisingControl merchandisingControl) {
		this.merchandisingControl = merchandisingControl;
		return this;
	}

	/**
	 * Get merchandisingControl
	 * 
	 * @return merchandisingControl
	 **/
	@ApiModelProperty(value = "")

	@Valid
	/**
	 * 
	 * @return
	 */
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
	public Device media(List<MediaLink> media) {
		this.media = media;
		return this;
	}

	/**
	 * 
	 * @param mediaItem
	 * @return
	 */
	public Device addMediaItem(MediaLink mediaItem) {
		if (this.media == null) {
			this.media = new ArrayList<>();
		}
		this.media.add(mediaItem);
		return this;
	}

	/**
	 * Get media
	 * 
	 * @return media
	 **/
	@ApiModelProperty(value = "")

	@Valid
	/**
	 * 
	 * @return
	 */
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
	public Device priceInfo(PriceForBundleAndHardware priceInfo) {
		this.priceInfo = priceInfo;
		return this;
	}

	/**
	 * Get priceInfo
	 * 
	 * @return priceInfo
	 **/
	@ApiModelProperty(value = "")

	@Valid
	/**
	 * 
	 * @return
	 */
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
		Device device = (Device) o;
		return Objects.equals(this.deviceId, device.deviceId) && Objects.equals(this.name, device.name)
				&& Objects.equals(this.make, device.make) && Objects.equals(this.model, device.model)
				&& Objects.equals(this.groupType, device.groupType) && Objects.equals(this.rating, device.rating)
				&& Objects.equals(this.description, device.description)
				&& Objects.equals(this.productClass, device.productClass)
				&& Objects.equals(this.merchandisingControl, device.merchandisingControl)
				&& Objects.equals(this.media, device.media) && Objects.equals(this.priceInfo, device.priceInfo)
				&& Objects.equals(this.promotionsPackage, device.promotionsPackage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceId, name, make, model, groupType, rating, description, productClass,
				merchandisingControl, media, priceInfo, promotionsPackage);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Device {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    make: ").append(toIndentedString(make)).append("\n");
		sb.append("    model: ").append(toIndentedString(model)).append("\n");
		sb.append("    groupType: ").append(toIndentedString(groupType)).append("\n");
		sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
		sb.append("    merchandisingControl: ").append(toIndentedString(merchandisingControl)).append("\n");
		sb.append("    media: ").append(toIndentedString(media)).append("\n");
		sb.append("    priceInfo: ").append(toIndentedString(priceInfo)).append("\n");
		sb.append("    promotionsPackage: ").append(toIndentedString(promotionsPackage)).append("\n");
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
