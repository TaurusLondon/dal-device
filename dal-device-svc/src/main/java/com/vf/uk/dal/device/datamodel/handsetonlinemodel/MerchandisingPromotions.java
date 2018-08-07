package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MerchandisingPromotions
 */
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class MerchandisingPromotions {
	@JsonProperty("promotionName")
	private String promotionName = null;

}
