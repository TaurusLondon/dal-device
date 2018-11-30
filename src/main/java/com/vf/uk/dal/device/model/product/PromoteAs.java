package com.vf.uk.dal.device.model.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * PromoteAs
 * @author manoj.bera
 *
 */
@Data
public class PromoteAs {

	@JsonProperty("promotionName")
	private List<String> promotionName;
}
