package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;

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

	@JsonProperty("productGroupName")
	private String productGroupName = null;

	@JsonProperty("productGroupId")
	private String productGroupId = null;
	
	@JsonProperty("size")
	private List<String> size = null;

	@JsonProperty("color")
	private List<String> color = null;
	
	@JsonProperty("colorHex")
	private List<String> colorHex = null;
	
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

	public List<String> getSize() {
		return size;
	}

	public void setSize(List<String> size) {
		this.size = size;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<String> getColorHex() {
		return colorHex;
	}

	public void setColorHex(List<String> colorHex) {
		this.colorHex = colorHex;
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((colorHex == null) ? 0 : colorHex.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((media == null) ? 0 : media.hashCode());
		result = prime * result + ((merchandisingControl == null) ? 0 : merchandisingControl.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((priceInfo == null) ? 0 : priceInfo.hashCode());
		result = prime * result + ((productClass == null) ? 0 : productClass.hashCode());
		result = prime * result + ((productGroupId == null) ? 0 : productGroupId.hashCode());
		result = prime * result + ((productGroupName == null) ? 0 : productGroupName.hashCode());
		result = prime * result + ((promotionsPackage == null) ? 0 : promotionsPackage.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", name=" + name + ", make=" + make + ", model=" + model
				+ ", groupType=" + groupType + ", rating=" + rating + ", description=" + description + ", productClass="
				+ productClass + ", merchandisingControl=" + merchandisingControl + ", media=" + media + ", priceInfo="
				+ priceInfo + ", promotionsPackage=" + promotionsPackage + ", productGroupName=" + productGroupName
				+ ", productGroupId=" + productGroupId + ", size=" + size + ", color=" + color + ", colorHex="
				+ colorHex + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (colorHex == null) {
			if (other.colorHex != null)
				return false;
		} else if (!colorHex.equals(other.colorHex))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (groupType == null) {
			if (other.groupType != null)
				return false;
		} else if (!groupType.equals(other.groupType))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (media == null) {
			if (other.media != null)
				return false;
		} else if (!media.equals(other.media))
			return false;
		if (merchandisingControl == null) {
			if (other.merchandisingControl != null)
				return false;
		} else if (!merchandisingControl.equals(other.merchandisingControl))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priceInfo == null) {
			if (other.priceInfo != null)
				return false;
		} else if (!priceInfo.equals(other.priceInfo))
			return false;
		if (productClass == null) {
			if (other.productClass != null)
				return false;
		} else if (!productClass.equals(other.productClass))
			return false;
		if (productGroupId == null) {
			if (other.productGroupId != null)
				return false;
		} else if (!productGroupId.equals(other.productGroupId))
			return false;
		if (productGroupName == null) {
			if (other.productGroupName != null)
				return false;
		} else if (!productGroupName.equals(other.productGroupName))
			return false;
		if (promotionsPackage == null) {
			if (other.promotionsPackage != null)
				return false;
		} else if (!promotionsPackage.equals(other.promotionsPackage))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
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
