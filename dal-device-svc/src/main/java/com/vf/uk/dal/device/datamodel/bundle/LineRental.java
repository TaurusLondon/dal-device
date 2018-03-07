package com.vf.uk.dal.device.datamodel.bundle;



public class LineRental {

	
	private String lineRentalProductId;
	
    private Long lineRentalAmount;
	
	
	public LineRental() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLineRentalProductId() {
		return lineRentalProductId;
	}
	public void setLineRentalProductId(String lineRentalProductId) {
		this.lineRentalProductId = lineRentalProductId;
	}
	public Long getLineRentalAmount() {
		return lineRentalAmount;
	}
	public void setLineRentalAmount(Long lineRentalAmount) {
		this.lineRentalAmount = lineRentalAmount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lineRentalAmount == null) ? 0 : lineRentalAmount.hashCode());
		result = prime * result + ((lineRentalProductId == null) ? 0 : lineRentalProductId.hashCode());
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
		LineRental other = (LineRental) obj;
		if (lineRentalAmount == null) {
			if (other.lineRentalAmount != null)
				return false;
		} else if (!lineRentalAmount.equals(other.lineRentalAmount))
			return false;
		if (lineRentalProductId == null) {
			if (other.lineRentalProductId != null)
				return false;
		} else if (!lineRentalProductId.equals(other.lineRentalProductId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LineRental [lineRentalProductId=" + lineRentalProductId + ", lineRentalAmount=" + lineRentalAmount
				+ "]";
	}
    
   
    
	
}
