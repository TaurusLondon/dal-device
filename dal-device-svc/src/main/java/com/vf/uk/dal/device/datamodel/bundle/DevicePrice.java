
package com.vf.uk.dal.device.datamodel.bundle;

public class DevicePrice {

	private String deviceId;

	private float priceNet;

	private float priceGross;

	private float priceVAT;

	private String productLine;

	public DevicePrice() {
		super();

	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public float getPriceNet() {
		return priceNet;
	}

	public void setPriceNet(float priceNet) {
		this.priceNet = priceNet;
	}

	public float getPriceGross() {
		return priceGross;
	}

	public void setPriceGross(float priceGross) {
		this.priceGross = priceGross;
	}

	public float getPriceVAT() {
		return priceVAT;
	}

	public void setPriceVAT(float priceVAT) {
		this.priceVAT = priceVAT;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + Float.floatToIntBits(priceGross);
		result = prime * result + Float.floatToIntBits(priceNet);
		result = prime * result + Float.floatToIntBits(priceVAT);
		result = prime * result + ((productLine == null) ? 0 : productLine.hashCode());
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
		DevicePrice other = (DevicePrice) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (Float.floatToIntBits(priceGross) != Float.floatToIntBits(other.priceGross))
			return false;
		if (Float.floatToIntBits(priceNet) != Float.floatToIntBits(other.priceNet))
			return false;
		if (Float.floatToIntBits(priceVAT) != Float.floatToIntBits(other.priceVAT))
			return false;
		if (productLine == null) {
			if (other.productLine != null)
				return false;
		} else if (!productLine.equals(other.productLine))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DevicePrice [deviceId=" + deviceId + ", priceNet=" + priceNet + ", priceGross=" + priceGross
				+ ", priceVAT=" + priceVAT + ", productLine=" + productLine + "]";
	}

}
