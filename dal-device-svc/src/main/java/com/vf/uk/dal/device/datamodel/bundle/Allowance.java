package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Allowance
{
    private String type;

    private String value;

    private String uom;

    @JsonProperty("displayUOM")
    private String displayUom;
    
    private String tilUOM;
    
	private String productId ;
    
    private Long conversionFactor;

    
    public String getTilUOM() {
		return tilUOM;
	}
	public void setTilUOM(String tilUOM) {
		this.tilUOM = tilUOM;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Long getConversionFactor() {
		return conversionFactor;
	}
	public void setConversionFactor(Long conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
    
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public void setUom(String uom){
        this.uom = uom;
    }
    public String getUom(){
        return this.uom;
    }
    public void setDisplayUom(String displayUom){
        this.displayUom = displayUom;
    }
    public String getDisplayUom(){
        return this.displayUom;
    }
	@Override
	public String toString() {
		return "Allowance [type=" + type + ", value=" + value + ", uom=" + uom + ", displayUom=" + displayUom + "tilUOM=" + tilUOM + "productId=" +productId + "conversionFactor=" + conversionFactor +"]";
	}
    
}