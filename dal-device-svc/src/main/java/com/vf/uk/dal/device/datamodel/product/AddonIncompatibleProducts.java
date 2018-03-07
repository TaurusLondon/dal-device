package com.vf.uk.dal.device.datamodel.product;


public class AddonIncompatibleProducts {
	
	
	private String incompatibleProductId;
	
    private String ruleScope;
	
    private String startDate;
	
    private String endDate;
	
	
	public AddonIncompatibleProducts() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getIncompatibleProductId() {
		return incompatibleProductId;
	}


	public void setIncompatibleProductId(String incompatibleProductId) {
		this.incompatibleProductId = incompatibleProductId;
	}


	public String getRuleScope() {
		return ruleScope;
	}


	public void setRuleScope(String ruleScope) {
		this.ruleScope = ruleScope;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((incompatibleProductId == null) ? 0 : incompatibleProductId.hashCode());
		result = prime * result + ((ruleScope == null) ? 0 : ruleScope.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		AddonIncompatibleProducts other = (AddonIncompatibleProducts) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (incompatibleProductId == null) {
			if (other.incompatibleProductId != null)
				return false;
		} else if (!incompatibleProductId.equals(other.incompatibleProductId))
			return false;
		if (ruleScope == null) {
			if (other.ruleScope != null)
				return false;
		} else if (!ruleScope.equals(other.ruleScope))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "AddonIncompatibleProducts [incompatibleProductId=" + incompatibleProductId + ", ruleScope=" + ruleScope
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}


	    
	
}
