package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-05T11:10:07.841Z")
@Data
public class CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions {

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

	/** The plancouple id. */
	@JsonProperty("plancoupleId")
	private String plancoupleId = null;

	/** The package type. */
	@JsonProperty("packageType")
	private List<String> packageType = new ArrayList<>();

	/** The foot notes. */
	private List<String> footNotes = new ArrayList<>();

	/** The promotion media. */
	@JsonProperty("promotionMedia")
	private String promotionMedia = null;
}
