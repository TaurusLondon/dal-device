package com.vf.uk.dal.utility.solr.entity;
/**
 * 
 * HardwarePrice
 *
 */
public class HardwarePrice
{
    private String hardwareId;

    private OneOffPrice oneOffPrice;

    private OneOffDiscountPrice oneOffDiscountPrice;
    /**
     * 
     * @param hardwareId
     */
    public void setHardwareId(String hardwareId){
        this.hardwareId = hardwareId;
    }
    /**
     * 
     * 
     * @return
     */
    public String getHardwareId(){
        return this.hardwareId;
    }
    /**
     * 
     * @param oneOffPrice
     */
    public void setOneOffPrice(OneOffPrice oneOffPrice){
        this.oneOffPrice = oneOffPrice;
    }
    /**
     * 
     * @return
     */
    public OneOffPrice getOneOffPrice(){
        return this.oneOffPrice;
    }
    /**
     * 
     * @param oneOffDiscountPrice
     */
    public void setOneOffDiscountPrice(OneOffDiscountPrice oneOffDiscountPrice){
        this.oneOffDiscountPrice = oneOffDiscountPrice;
    }
    /**
     * 
     * @return
     */
    public OneOffDiscountPrice getOneOffDiscountPrice(){
        return this.oneOffDiscountPrice;
    }
}
