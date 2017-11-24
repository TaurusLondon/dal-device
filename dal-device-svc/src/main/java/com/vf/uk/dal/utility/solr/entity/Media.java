package com.vf.uk.dal.utility.solr.entity;
public class Media
{
    private String id;

    private String value;

    private String type;

	private String promoCategory;
	
	private String offerCode;
	
    private String description;
    
	private String discountId;

	public String getPromoCategory() {
		return promoCategory;
	}
	public void setPromoCategory(String promoCategory) {
		this.promoCategory = promoCategory;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
}