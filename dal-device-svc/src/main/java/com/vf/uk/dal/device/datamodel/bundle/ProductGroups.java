package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *  ProductGroups
 * @author manoj.bera
 *
 */
@Data
public class ProductGroups {

	@JsonProperty("productGroup")
	private List<ProductGroup> productGroup;
}
