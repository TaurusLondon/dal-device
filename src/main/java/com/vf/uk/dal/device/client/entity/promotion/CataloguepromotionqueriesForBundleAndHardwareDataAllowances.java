package com.vf.uk.dal.device.client.entity.promotion;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * CataloguepromotionqueriesForBundleAndHardwareDataAllowances.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-05T11:10:07.841Z")
@Data
public class CataloguepromotionqueriesForBundleAndHardwareDataAllowances {

	/** The tag. */
	@JsonProperty("tag")
	private String tag = null;

	/** The label. */
	@JsonProperty("label")
	private String label = null;

	/** The type. */
	@JsonProperty("type")
	private String type = null;

	/** The priority. */
	@JsonProperty("priority")
	private String priority = null;

	/** The description. */
	@JsonProperty("description")
	private String description = null;

	/** The free data. */
	@JsonProperty("freeData")
	private CataloguepromotionqueriesForBundleAndHardwareFreeData freeData = null;

	/** The package type. */
	@JsonProperty("packageType")
	private List<String> packageType = new ArrayList<>();

	private List<String> footNotes = new ArrayList<>();

	/** The promotion media. */
	@JsonProperty("promotionMedia")
	private String promotionMedia = null;
}
