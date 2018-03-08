package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

@Portable
public class DiscountMP {
	/*
	 * POF Index Numbers
	 */
	private static final int TYPE = 0;
	private static final int VALUE = 1;
	private static final int QUALIFYRECURRINGCOST = 2;
	private static final int PRIORITY = 3;
	
	@PortableProperty(TYPE)
    protected String type;
	@PortableProperty(VALUE)
    protected float value;
	@PortableProperty(QUALIFYRECURRINGCOST)
    protected float qualifyingRecurringCost;
	@PortableProperty(PRIORITY)
    protected Long priority;
	
	
	
	public DiscountMP() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public float getQualifyingRecurringCost() {
		return qualifyingRecurringCost;
	}
	public void setQualifyingRecurringCost(float qualifyingRecurringCost) {
		this.qualifyingRecurringCost = qualifyingRecurringCost;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + Float.floatToIntBits(qualifyingRecurringCost);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + Float.floatToIntBits(value);
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
		DiscountMP other = (DiscountMP) obj;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (Float.floatToIntBits(qualifyingRecurringCost) != Float.floatToIntBits(other.qualifyingRecurringCost))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Discount [type=" + type + ", value=" + value + ", qualifyingRecurringCost=" + qualifyingRecurringCost
				+ ", priority=" + priority + "]";
	}
    
    

}
