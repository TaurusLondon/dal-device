package com.vf.uk.dal.device.datamodel.product;

public class ItemAttribute {

	private String key;

	private String value;

	private String type;

	private String valueUOM;

	public ItemAttribute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValueUOM() {
		return valueUOM;
	}

	public void setValueUOM(String valueUOM) {
		this.valueUOM = valueUOM;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((valueUOM == null) ? 0 : valueUOM.hashCode());
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
		ItemAttribute other = (ItemAttribute) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (valueUOM == null) {
			if (other.valueUOM != null)
				return false;
		} else if (!valueUOM.equals(other.valueUOM))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemAttribute [key=" + key + ", value=" + value + ", type=" + type + ", valueUOM=" + valueUOM + "]";
	}

}
