package com.vf.uk.dal.device.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CacheDeviceTileResponse.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-17T15:39:15.544Z")

public class CacheDeviceTileResponse {
	@JsonProperty("jobId")
	private String jobId = null;

	@JsonProperty("jobStatus")
	private String jobStatus = null;

	public CacheDeviceTileResponse jobId(String jobId) {
		this.jobId = jobId;
		return this;
	}

	/**
	 * job identifier
	 * 
	 * @return jobId
	 **/
	@ApiModelProperty(value = "job identifier")

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public CacheDeviceTileResponse jobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
		return this;
	}

	/**
	 * job status
	 * 
	 * @return jobStatus
	 **/
	@ApiModelProperty(value = "job status")

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CacheDeviceTileResponse cacheDeviceTileResponse = (CacheDeviceTileResponse) o;
		return Objects.equals(this.jobId, cacheDeviceTileResponse.jobId)
				&& Objects.equals(this.jobStatus, cacheDeviceTileResponse.jobStatus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobId, jobStatus);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CacheDeviceTileResponse {\n");

		sb.append("    jobId: ").append(toIndentedString(jobId)).append("\n");
		sb.append("    jobStatus: ").append(toIndentedString(jobStatus)).append("\n");
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
