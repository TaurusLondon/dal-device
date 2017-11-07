package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * StatusInfo
 */
public class StatusInfo {
	private String status = null;

	private List<String> errorCodes = new ArrayList<String>();

	public StatusInfo status(String status) {
		this.status = status;
		return this;
	}

	/**
	 * The status of ESM operation.
	 * 
	 * @return status
	 **/
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StatusInfo errorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
		return this;
	}

	public StatusInfo addErrorCodesItem(String errorCodesItem) {
		this.errorCodes.add(errorCodesItem);
		return this;
	}

	/**
	 * List of Error Codes while invoking an ESM Operation.
	 * 
	 * @return errorCodes
	 **/
	public List<String> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StatusInfo statusInfo = (StatusInfo) o;
		return Objects.equals(this.status, statusInfo.status) && Objects.equals(this.errorCodes, statusInfo.errorCodes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, errorCodes);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class StatusInfo {\n");

		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    errorCodes: ").append(toIndentedString(errorCodes)).append("\n");
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
