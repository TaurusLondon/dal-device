package com.vf.uk.dal.device.client.entity.bundle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Relationship
 * 
 * @author manoj.bera
 *
 */
@Data
public class Relationship {

	@JsonProperty("name")
	private String name;

	@JsonProperty("type")
	private String type;

	@JsonProperty("maxCardinality")
	private String maxCardinality;

	@JsonProperty("minCardinality")
	private String minCardinality;

	@JsonProperty("defaultCardinality")
	private String defaultCardinality;

	@JsonProperty("level")
	private String level;

	@JsonProperty("id")
	private String id;

	@JsonProperty("parentRelationshipID")
	private String parentRelationshipID;

	@JsonProperty("productID")
	private String productID;

	@JsonProperty("productClass")
	private String productClass;

	@JsonProperty("productLine")
	private String productLine;

	@JsonProperty("sequence")
	private Long sequence;

	@JsonProperty("relationshipPath")
	private String relationshipPath;

	@JsonProperty("incompatibleAddons")
	private List<AddonIncompatibleProducts> incompatibleAddons;
}
