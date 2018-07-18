package com.vf.uk.dal.device.datamodel.bundle;

import java.sql.Date;

/**
 * 
 * Availability
 *
 */
public class Availability {

	private Date start;
	private Date end;
	private Boolean salesExpired;

	/**
	 * Availability Constructor
	 */
	public Availability() {
		super();
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Boolean getSalesExpired() {
		return salesExpired;
	}

	public void setSalesExpired(Boolean salesExpired) {
		this.salesExpired = salesExpired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((salesExpired == null) ? 0 : salesExpired.hashCode());
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
		Availability other = (Availability) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (salesExpired == null) {
			if (other.salesExpired != null)
				return false;
		} else if (!salesExpired.equals(other.salesExpired))
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
		return "Availability [start=" + start + ", end=" + end + ", salesExpired=" + salesExpired + "]";
	}

}
