package com.vf.uk.dal.device.datamodel.product;


import java.sql.Date;

import io.swagger.annotations.ApiModelProperty;


public class ProductAvailability {

	
	@ApiModelProperty(hidden=true)
    private Date start;
	
	@ApiModelProperty(hidden=true)
    private Date end;
	
	@ApiModelProperty(hidden=true)
    private boolean salesExpired;

	
    public ProductAvailability() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getStart() {
        return start;
    }

    public void setStart(Date value) {
        this.start = value;
    }


    public Date getEnd() {
        return end;
    }

    public void setEnd(Date value) {
        this.end = value;
    }


    public boolean isSalesExpired() {
        return salesExpired;
    }

    public void setSalesExpired(boolean value) {
        this.salesExpired = value;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + (salesExpired ? 1231 : 1237);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		ProductAvailability other = (ProductAvailability) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (salesExpired != other.salesExpired)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductAvailability [start=" + start + ", end=" + end + ", salesExpired=" + salesExpired + "]";
	}

}
