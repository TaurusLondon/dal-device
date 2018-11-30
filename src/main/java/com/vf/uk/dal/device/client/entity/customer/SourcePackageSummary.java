package com.vf.uk.dal.device.client.entity.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * SourcePackageSummary
 */
@Data
public class SourcePackageSummary {
	@JsonProperty("promotionId")
	private String promotionId = null;

	@JsonProperty("displayName")
	private String displayName = null;

	@JsonProperty("desc")
	private String desc = null;

	@JsonProperty("imageUrl")
	private String imageUrl = null;

	@JsonProperty("assetId")
	private String assetId = null;

	@JsonProperty("subscriptionId")
	private String subscriptionId = null;
}
