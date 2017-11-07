package com.vf.uk.dal.utility.solr.entity;
public class HardwarePrice
{
    private String hardwareId;

    private OneOffPrice oneOffPrice;

    private OneOffDiscountPrice oneOffDiscountPrice;

    public void setHardwareId(String hardwareId){
        this.hardwareId = hardwareId;
    }
    public String getHardwareId(){
        return this.hardwareId;
    }
    public void setOneOffPrice(OneOffPrice oneOffPrice){
        this.oneOffPrice = oneOffPrice;
    }
    public OneOffPrice getOneOffPrice(){
        return this.oneOffPrice;
    }
    public void setOneOffDiscountPrice(OneOffDiscountPrice oneOffDiscountPrice){
        this.oneOffDiscountPrice = oneOffDiscountPrice;
    }
    public OneOffDiscountPrice getOneOffDiscountPrice(){
        return this.oneOffDiscountPrice;
    }
}
