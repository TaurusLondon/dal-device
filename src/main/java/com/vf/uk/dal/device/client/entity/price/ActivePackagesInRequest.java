package com.vf.uk.dal.device.client.entity.price;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Items present in customer account are sent in active Packages 
 *
 */
@Data
public class ActivePackagesInRequest {
	@JsonProperty("bundleId")
	private String bundleId;
	@JsonProperty("discounts")
	private List<ExistingDiscount> discounts;
	@JsonProperty("identifier")
	private Identifier identifier;

}
