package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * GroupDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-05-02T12:09:49.110Z")

public class ProductGroupDetailsForDeviceList {
	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("groupId")
	private String groupId = null;

	@JsonProperty("size")
	private List<String> size = null;

	@JsonProperty("color")
	private List<String> color = null;
	
	@JsonProperty("hexCode")
	private List<String> colorHex = null;

	public List<String> getColorHex() {
		return colorHex;
	}

	public void setColorHex(List<String> colorHex) {
		this.colorHex = colorHex;
	}

	public ProductGroupDetailsForDeviceList groupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	/**
	 * Get groupName
	 * 
	 * @return groupName
	 **/
	@ApiModelProperty(value = "")

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ProductGroupDetailsForDeviceList groupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	/**
	 * Get groupId
	 * 
	 * @return groupId
	 **/
	@ApiModelProperty(value = "")

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public ProductGroupDetailsForDeviceList size(List<String> size) {
		this.size = size;
		return this;
	}

	public ProductGroupDetailsForDeviceList addSizeItem(String sizeItem) {
		if (this.size == null) {
			this.size = new ArrayList<>();
		}
		this.size.add(sizeItem);
		return this;
	}

	/**
	 * Get size
	 * 
	 * @return size
	 **/
	@ApiModelProperty(value = "")

	public List<String> getSize() {
		return size;
	}

	public void setSize(List<String> size) {
		this.size = size;
	}

	public ProductGroupDetailsForDeviceList color(List<String> color) {
		this.color = color;
		return this;
	}

	public ProductGroupDetailsForDeviceList addColorItem(String colorItem) {
		if (this.color == null) {
			this.color = new ArrayList<>();
		}
		this.color.add(colorItem);
		return this;
	}

	/**
	 * Get color
	 * 
	 * @return color
	 **/
	@ApiModelProperty(value = "")

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	

	@Override
	public String toString() {
		return "ProductGroupDetailsForDeviceList [groupName=" + groupName + ", groupId=" + groupId + ", size=" + size
				+ ", color=" + color + ", colorHex=" + colorHex + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((colorHex == null) ? 0 : colorHex.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductGroupDetailsForDeviceList other = (ProductGroupDetailsForDeviceList) obj;
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
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
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
