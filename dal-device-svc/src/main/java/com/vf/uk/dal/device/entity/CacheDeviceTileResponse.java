package com.vf.uk.dal.device.entity;

import java.util.Objects;

/**
 * The Class CacheDeviceTileResponse.
 */
public class CacheDeviceTileResponse {

	/** The job id. */
	private String jobId;

	/** The job status. */
	private String jobStatus;

	/**
	 * Gets the job id.
	 *
	 * @return the job id
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Sets the job id.
	 *
	 * @param jobId
	 *            the new job id
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the job status.
	 *
	 * @return the job status
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * Sets the job status.
	 *
	 * @param jobStatus
	 *            the new job status
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CacheDeviceTileResponse cacheBundlePriceResponse = (CacheDeviceTileResponse) o;
		return Objects.equals(this.jobId, cacheBundlePriceResponse.jobId)
				&& Objects.equals(this.jobStatus, cacheBundlePriceResponse.jobStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(jobId, jobStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CacheDeviceTile {\n");

		sb.append("    jobId: ").append(toIndentedString(jobId)).append("\n");
		sb.append("    jobStatus: ").append(toIndentedString(jobStatus)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 *
	 * @param o
	 *            the o
	 * @return the string
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}