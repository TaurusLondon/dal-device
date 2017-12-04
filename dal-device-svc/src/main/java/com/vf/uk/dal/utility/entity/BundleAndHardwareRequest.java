package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;

/**
 * RequestForBundleAndHardware
 */
public class BundleAndHardwareRequest {

	private List<BundleAndHardwareTuple> bundleAndHardwareList = null;

	public BundleAndHardwareRequest bundleAndHardwareList(List<BundleAndHardwareTuple> bundleAndHardwareList) {
		this.bundleAndHardwareList = bundleAndHardwareList;
		return this;
	}

	public BundleAndHardwareRequest addBundleAndHardwareListItem(BundleAndHardwareTuple bundleAndHardwareListItem) {
		if (this.bundleAndHardwareList == null) {
			this.bundleAndHardwareList = new ArrayList<BundleAndHardwareTuple>();
		}
		this.bundleAndHardwareList.add(bundleAndHardwareListItem);
		return this;
	}

	/**
	 * Get bundleAndHardwareList
	 * 
	 * @return bundleAndHardwareList
	 **/
	public List<BundleAndHardwareTuple> getBundleAndHardwareList() {
		return bundleAndHardwareList;
	}

	public void setBundleAndHardwareList(List<BundleAndHardwareTuple> bundleAndHardwareList) {
		this.bundleAndHardwareList = bundleAndHardwareList;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BundleAndHardwareRequest requestForBundleAndHardware = (BundleAndHardwareRequest) o;
		return Objects.equals(this.bundleAndHardwareList, requestForBundleAndHardware.bundleAndHardwareList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bundleAndHardwareList);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class RequestForBundleAndHardware {\n");

		sb.append("    bundleAndHardwareList: ").append(toIndentedString(bundleAndHardwareList)).append("\n");
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
