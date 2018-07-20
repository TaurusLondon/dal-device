package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

import lombok.Data;

@Data
public class Relationship {

	private String name;

	private String type;

	private String maxCardinality;

	private String minCardinality;

	private String defaultCardinality;

	private String level;

	private String id;

	private String parentRelationshipID;

	private String productID;

	private String productClass;

	private String productLine;

	private Long sequence;

	private String relationshipPath;

	private List<AddonIncompatibleProducts> incompatibleAddons;
}
