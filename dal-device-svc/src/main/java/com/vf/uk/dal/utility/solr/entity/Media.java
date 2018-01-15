package com.vf.uk.dal.utility.solr.entity;
/**
 * 
 * Media
 *
 */
public class Media
{
    private String id;

    private String value;

    private String type;

	private String promoCategory;
	
	private String offerCode;
	
    private String description;
    
	private String discountId;
	/**
	 * 
	 * @return
	 */
	public String getPromoCategory() {
		return promoCategory;
	}
	/**
	 * 
	 * @param promoCategory
	 */
	public void setPromoCategory(String promoCategory) {
		this.promoCategory = promoCategory;
	}
	/**
	 * 
	 * @return
	 */
	public String getOfferCode() {
		return offerCode;
	}
	/**
	 * 
	 * @param offerCode
	 */
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @return
	 */
	public String getDiscountId() {
		return discountId;
	}
	/**
	 * 
	 * @param discountId
	 */
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	/**
	 * 
	 * @param id
	 */
    public void setId(String id){
        this.id = id;
    }
    /**
     * 
     * @return
     */
    public String getId(){
        return this.id;
    }
    /**
     * 
     * @param value
     */
    public void setValue(String value){
        this.value = value;
    }
    /**
     * 
     * @return
     */
    public String getValue(){
        return this.value;
    }
    /**
     * 
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * 
     * @return
     */
    public String getType(){
        return this.type;
    }
}