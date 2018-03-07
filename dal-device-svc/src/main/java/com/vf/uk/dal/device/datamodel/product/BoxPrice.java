
package com.vf.uk.dal.device.datamodel.product;


public class BoxPrice {

	
    private Double priceNet;
	
    private Double priceGross;

    private Double priceVAT;

	
    public BoxPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(Double value) {
        this.priceNet = value;
    }

    public Double getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(Double value) {
        this.priceGross = value;
    }

    public Double getPriceVAT() {
        return priceVAT;
    }

    public void setPriceVAT(Double value) {
        this.priceVAT = value;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priceGross == null) ? 0 : priceGross.hashCode());
		result = prime * result + ((priceNet == null) ? 0 : priceNet.hashCode());
		result = prime * result + ((priceVAT == null) ? 0 : priceVAT.hashCode());
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
		BoxPrice other = (BoxPrice) obj;
		if (priceGross == null) {
			if (other.priceGross != null)
				return false;
		} else if (!priceGross.equals(other.priceGross))
			return false;
		if (priceNet == null) {
			if (other.priceNet != null)
				return false;
		} else if (!priceNet.equals(other.priceNet))
			return false;
		if (priceVAT == null) {
			if (other.priceVAT != null)
				return false;
		} else if (!priceVAT.equals(other.priceVAT))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoxPrice [priceNet=" + priceNet + ", priceGross=" + priceGross + ", priceVAT=" + priceVAT + "]";
	}
    
}
