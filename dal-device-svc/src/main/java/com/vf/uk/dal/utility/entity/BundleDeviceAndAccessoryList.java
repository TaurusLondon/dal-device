package com.vf.uk.dal.utility.entity;

import java.util.List;
import java.util.Objects;

/**
 * BundleDeviceAndAccessoryList
 */

public class BundleDeviceAndAccessoryList {
	private String deviceId = null;

	private String bundleId = null;

	private List<String> accessoryList = null;

	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public BundleDeviceAndAccessoryList deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	/**
	 * Unique device id as available from the product catalogue
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
	 * @param bundleId
	 * @return
	 */
	public BundleDeviceAndAccessoryList bundleId(String bundleId) {
		this.bundleId = bundleId;
		return this;
	}

	/**
	 * Unique bundle id as available from the product catalogue
	 * 
	 * @return bundleId
	 **/
	public String getBundleId() {
		return bundleId;
	}

	/**
	 * 
	 * @param bundleId
	 */
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAccessoryList() {
		return accessoryList;
	}

	/**
	 * 
	 * @param accessoryList
	 */
	public void setAccessoryList(List<String> accessoryList) {
		this.accessoryList = accessoryList;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BundleDeviceAndAccessoryList bundleAndHardwareTuple = (BundleDeviceAndAccessoryList) o;
		return Objects.equals(this.deviceId, bundleAndHardwareTuple.deviceId)
				&& Objects.equals(this.bundleId, bundleAndHardwareTuple.bundleId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceId, bundleId);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BundleDeviceAndAccessoryList {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
		sb.append("    accessoryList: ").append(toIndentedString(accessoryList)).append("\n");
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
