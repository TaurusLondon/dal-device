package com.vf.uk.dal.device.model.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Misc
 * @author manoj.bera
 *
 */
@Data
public class Misc {

	@JsonProperty("itemAttribute")
	private List<ItemAttribute> itemAttribute;

}
