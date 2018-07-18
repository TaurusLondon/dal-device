package com.vf.uk.dal.device.entity;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * KeepDeviceChangePlanRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-08-21T05:02:16.131Z")

public class KeepDeviceChangePlanRequest {
	@JsonProperty("deviceId")
	private String deviceId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

	@JsonProperty("allowedRecurringPriceLimit")
	private String allowedRecurringPriceLimit = null;

	@JsonProperty("plansLimit")
	private String plansLimit = null;

	/**
	 * 
	 * @param deviceId
	 * @return
	 */
	public KeepDeviceChangePlanRequest deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	/**
	 * Device Id to get compatible plans
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
	public KeepDeviceChangePlanRequest bundleId(String bundleId) {
		this.bundleId = bundleId;
		return this;
	}

	/**
	 * Bundle Id to identify the plan which user selected initially
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
	 * @param allowedRecurringPriceLimit
	 * @return
	 */
	public KeepDeviceChangePlanRequest allowedRecurringPriceLimit(String allowedRecurringPriceLimit) {
		this.allowedRecurringPriceLimit = allowedRecurringPriceLimit;
		return this;
	}

	/**
	 * Allowed monthly price limit
	 * 
	 * @return allowedRecurringPriceLimit
	 **/
	public String getAllowedRecurringPriceLimit() {
		return allowedRecurringPriceLimit;
	}

	/**
	 * 
	 * @param allowedRecurringPriceLimit
	 */
	public void setAllowedRecurringPriceLimit(String allowedRecurringPriceLimit) {
		this.allowedRecurringPriceLimit = allowedRecurringPriceLimit;
	}

	/**
	 * 
	 * @param plansLimit
	 * @return
	 */
	public KeepDeviceChangePlanRequest plansLimit(String plansLimit) {
		this.plansLimit = plansLimit;
		return this;
	}

	/**
	 * Expected no. of plans in response
	 * 
	 * @return plansLimit
	 **/
	public String getPlansLimit() {
		return plansLimit;
	}

	/**
	 * 
	 * @param plansLimit
	 */
	public void setPlansLimit(String plansLimit) {
		this.plansLimit = plansLimit;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = (KeepDeviceChangePlanRequest) o;
		return Objects.equals(this.deviceId, keepDeviceChangePlanRequest.deviceId)
				&& Objects.equals(this.bundleId, keepDeviceChangePlanRequest.bundleId)
				&& Objects.equals(this.allowedRecurringPriceLimit,
						keepDeviceChangePlanRequest.allowedRecurringPriceLimit)
				&& Objects.equals(this.plansLimit, keepDeviceChangePlanRequest.plansLimit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceId, bundleId, allowedRecurringPriceLimit, plansLimit);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class KeepDeviceChangePlanRequest {\n");

		sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
		sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
		sb.append("    allowedRecurringPriceLimit: ").append(toIndentedString(allowedRecurringPriceLimit)).append("\n");
		sb.append("    plansLimit: ").append(toIndentedString(plansLimit)).append("\n");
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
