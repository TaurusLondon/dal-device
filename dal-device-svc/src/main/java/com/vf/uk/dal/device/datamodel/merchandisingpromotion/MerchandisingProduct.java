package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MerchandisingProduct
 * @author manoj.bera
 *
 */
@Data
public class MerchandisingProduct {
	@JsonProperty("promotionId")
	private String promotionId;
	@JsonProperty("productLine")
	private String productLine;
	@JsonProperty("productPath")
	private String productPath;
	@JsonProperty("action")
	private String action;
	@JsonProperty("discounts")
	private List<DiscountMP> discounts;

}
