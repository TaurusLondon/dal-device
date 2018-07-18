package com.vf.uk.dal.device.datamodel.product;

import java.util.List;

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

	public Relationship() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaxCardinality() {
		return maxCardinality;
	}

	public void setMaxCardinality(String maxCardinality) {
		this.maxCardinality = maxCardinality;
	}

	public String getMinCardinality() {
		return minCardinality;
	}

	public void setMinCardinality(String minCardinality) {
		this.minCardinality = minCardinality;
	}

	public String getDefaultCardinality() {
		return defaultCardinality;
	}

	public void setDefaultCardinality(String defaultCardinality) {
		this.defaultCardinality = defaultCardinality;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentRelationshipID() {
		return parentRelationshipID;
	}

	public void setParentRelationshipID(String parentRelationshipID) {
		this.parentRelationshipID = parentRelationshipID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductClass() {
		return productClass;
	}

	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getRelationshipPath() {
		return relationshipPath;
	}

	public void setRelationshipPath(String relationshipPath) {
		this.relationshipPath = relationshipPath;
	}

	public List<AddonIncompatibleProducts> getIncompatibleAddons() {
		return incompatibleAddons;
	}

	public void setIncompatibleAddons(List<AddonIncompatibleProducts> incompatibleAddons) {
		this.incompatibleAddons = incompatibleAddons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultCardinality == null) ? 0 : defaultCardinality.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((incompatibleAddons == null) ? 0 : incompatibleAddons.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((maxCardinality == null) ? 0 : maxCardinality.hashCode());
		result = prime * result + ((minCardinality == null) ? 0 : minCardinality.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentRelationshipID == null) ? 0 : parentRelationshipID.hashCode());
		result = prime * result + ((productClass == null) ? 0 : productClass.hashCode());
		result = prime * result + ((productID == null) ? 0 : productID.hashCode());
		result = prime * result + ((productLine == null) ? 0 : productLine.hashCode());
		result = prime * result + ((relationshipPath == null) ? 0 : relationshipPath.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relationship other = (Relationship) obj;
		if (defaultCardinality == null) {
			if (other.defaultCardinality != null)
				return false;
		} else if (!defaultCardinality.equals(other.defaultCardinality))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (incompatibleAddons == null) {
			if (other.incompatibleAddons != null)
				return false;
		} else if (!incompatibleAddons.equals(other.incompatibleAddons))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (maxCardinality == null) {
			if (other.maxCardinality != null)
				return false;
		} else if (!maxCardinality.equals(other.maxCardinality))
			return false;
		if (minCardinality == null) {
			if (other.minCardinality != null)
				return false;
		} else if (!minCardinality.equals(other.minCardinality))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentRelationshipID == null) {
			if (other.parentRelationshipID != null)
				return false;
		} else if (!parentRelationshipID.equals(other.parentRelationshipID))
			return false;
		if (productClass == null) {
			if (other.productClass != null)
				return false;
		} else if (!productClass.equals(other.productClass))
			return false;
		if (productID == null) {
			if (other.productID != null)
				return false;
		} else if (!productID.equals(other.productID))
			return false;
		if (productLine == null) {
			if (other.productLine != null)
				return false;
		} else if (!productLine.equals(other.productLine))
			return false;
		if (relationshipPath == null) {
			if (other.relationshipPath != null)
				return false;
		} else if (!relationshipPath.equals(other.relationshipPath))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Relationship [name=" + name + ", type=" + type + ", maxCardinality=" + maxCardinality
				+ ", minCardinality=" + minCardinality + ", defaultCardinality=" + defaultCardinality + ", level="
				+ level + ", id=" + id + ", parentRelationshipID=" + parentRelationshipID + ", productID=" + productID
				+ ", productClass=" + productClass + ", productLine=" + productLine + ", sequence=" + sequence
				+ ", relationshipPath=" + relationshipPath + ", incompatibleAddons=" + incompatibleAddons + "]";
	}

}
