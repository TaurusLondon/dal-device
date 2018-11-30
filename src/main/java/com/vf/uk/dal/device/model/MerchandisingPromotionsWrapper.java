package com.vf.uk.dal.device.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion;

import lombok.Data;

/**
 * MerchandisingPromotionsWrapper.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-02T10:55:14.550Z")
@Data
public class MerchandisingPromotionsWrapper {

	/** The data promotion. */
	@JsonProperty("dataPromotion")
	private MerchandisingPromotion dataPromotion = null;

	/** The text promotion. */
	@JsonProperty("textPromotion")
	private MerchandisingPromotion textPromotion = null;

	/** The talk time promotion. */
	@JsonProperty("talkTimePromotion")
	private MerchandisingPromotion talkTimePromotion = null;

	/** The entertainment pack promotion. */
	@JsonProperty("entertainmentPackPromotion")
	private MerchandisingPromotion entertainmentPackPromotion = null;

	/** The secure net promotion. */
	@JsonProperty("secureNetPromotion")
	private MerchandisingPromotion secureNetPromotion = null;

	/** The sash banner promotion. */
	@JsonProperty("sashBannerPromotion")
	private MerchandisingPromotion sashBannerPromotion = null;

	/** The free extra promotion. */
	@JsonProperty("freeExtraPromotion")
	private MerchandisingPromotion freeExtraPromotion = null;

	/** The free accessory promotion. */
	@JsonProperty("freeAccessoryPromotion")
	private MerchandisingPromotion freeAccessoryPromotion = null;

	/** The price promotion. */
	@JsonProperty("pricePromotion")
	private MerchandisingPromotion pricePromotion = null;

	@JsonProperty("conditionalSashBannerPromotion")
	private MerchandisingPromotion conditionalSashBannerPromotion = null;
}
