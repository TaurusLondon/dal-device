package com.vf.uk.dal.device.client.entity.customer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * RecommendedProductListResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-06-12T10:39:17.866Z")
@Data
public class RecommendedProductListResponse {

	@JsonProperty("recommendedProductList")
	private List<RecommendedProduct> recommendedProductList = new ArrayList<RecommendedProduct>();

	@JsonProperty("statusInfo")
	private StatusInfo statusInfo = null;
}
