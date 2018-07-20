package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * RecommendedProductListResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-06-12T10:39:17.866Z")
@Data
public class RecommendedProductListResponse {

	private List<RecommendedProduct> recommendedProductList = new ArrayList<RecommendedProduct>();

	private StatusInfo statusInfo = null;
}
