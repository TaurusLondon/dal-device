package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleComposition
 * @author manoj.bera
 *
 */
@Data
public class BundleComposition {

	@JsonProperty("relationship")
	private List<Relationship> relationship;

	@JsonProperty("primaryProduct")
	private String primaryProduct;
}
