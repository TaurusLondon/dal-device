package com.vf.uk.dal.device.datamodel.bundle;


public class Recommendations {
	
	
    private String type;

    private String name;
	
     
     
	public Recommendations() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Recommendations other = (Recommendations) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "Recommendations [type=" + type + ", name=" + name + "]";
	}
	
     
     

}
