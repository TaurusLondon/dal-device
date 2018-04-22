package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MerchandisingPromotion {

	
	private String tag;
	
	private String label;
	
	private String description;
	
	private String type;
	
	private String platform;
	
	private String promotionMedia;
	
	private Long priority;
	
	private IncompatibleMPs incompatibleMPs;
	
	private String actionOnRemove;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Timestamp startDateTime;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Timestamp endDateTime;
	
	private boolean confirmRequired;
	
	private String version;
	
	
	@JsonProperty("condition1")
	private Condition condition;
	
	private List<MerchandisingProduct> products;
	
	private boolean belowTheLine;
	
	private String priceEstablishedLabel;
	
	private String footNoteKey;

	
	
	public MerchandisingPromotion() {
		super();
	}
	
	

	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		this.tag = tag;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getPlatform() {
		return platform;
	}



	public void setPlatform(String platform) {
		this.platform = platform;
	}



	public String getPromotionMedia() {
		return promotionMedia;
	}



	public void setPromotionMedia(String promotionMedia) {
		this.promotionMedia = promotionMedia;
	}



	public Long getPriority() {
		return priority;
	}



	public void setPriority(Long priority) {
		this.priority = priority;
	}



	public IncompatibleMPs getIncompatibleMPs() {
		return incompatibleMPs;
	}



	public void setIncompatibleMPs(IncompatibleMPs incompatibleMPs) {
		this.incompatibleMPs = incompatibleMPs;
	}



	public String getActionOnRemove() {
		return actionOnRemove;
	}



	public void setActionOnRemove(String actionOnRemove) {
		this.actionOnRemove = actionOnRemove;
	}



	public Timestamp getStartDateTime() {
		return startDateTime;
	}



	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}



	public Timestamp getEndDateTime() {
		return endDateTime;
	}



	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}



	public boolean isConfirmRequired() {
		return confirmRequired;
	}



	public void setConfirmRequired(boolean confirmRequired) {
		this.confirmRequired = confirmRequired;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}


	//@JsonGetter{"condition"}
	public Condition getCondition() {
		return condition;
	}


	//@JsonSetter("condition1")
	public void setCondition(Condition condition) {
		this.condition = condition;
	}



	public List<MerchandisingProduct> getProducts() {
		return products;
	}



	public void setProducts(List<MerchandisingProduct> products) {
		this.products = products;
	}



	public boolean isBelowTheLine() {
		return belowTheLine;
	}



	public void setBelowTheLine(boolean belowTheLine) {
		this.belowTheLine = belowTheLine;
	}
	
	


	public String getPriceEstablishedLabel() {
		return priceEstablishedLabel;
	}



	public void setPriceEstablishedLabel(String priceEstablishedLabel) {
		this.priceEstablishedLabel = priceEstablishedLabel;
	}



	public String getFootNoteKey() {
		return footNoteKey;
	}



	public void setFootNoteKey(String footNoteKey) {
		this.footNoteKey = footNoteKey;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionOnRemove == null) ? 0 : actionOnRemove.hashCode());
		result = prime * result + (belowTheLine ? 1231 : 1237);
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + (confirmRequired ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((footNoteKey == null) ? 0 : footNoteKey.hashCode());
		result = prime * result + ((incompatibleMPs == null) ? 0 : incompatibleMPs.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + ((priceEstablishedLabel == null) ? 0 : priceEstablishedLabel.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((promotionMedia == null) ? 0 : promotionMedia.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		MerchandisingPromotion other = (MerchandisingPromotion) obj;
		if (actionOnRemove == null) {
			if (other.actionOnRemove != null)
				return false;
		} else if (!actionOnRemove.equals(other.actionOnRemove))
			return false;
		if (belowTheLine != other.belowTheLine)
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (confirmRequired != other.confirmRequired)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (footNoteKey == null) {
			if (other.footNoteKey != null)
				return false;
		} else if (!footNoteKey.equals(other.footNoteKey))
			return false;
		if (incompatibleMPs == null) {
			if (other.incompatibleMPs != null)
				return false;
		} else if (!incompatibleMPs.equals(other.incompatibleMPs))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (priceEstablishedLabel == null) {
			if (other.priceEstablishedLabel != null)
				return false;
		} else if (!priceEstablishedLabel.equals(other.priceEstablishedLabel))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (promotionMedia == null) {
			if (other.promotionMedia != null)
				return false;
		} else if (!promotionMedia.equals(other.promotionMedia))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "MerchandisingPromotion [tag=" + tag + ", label=" + label + ", description=" + description + ", type="
				+ type + ", platform=" + platform + ", promotionMedia=" + promotionMedia + ", priority=" + priority
				+ ", incompatibleMPs=" + incompatibleMPs + ", actionOnRemove=" + actionOnRemove + ", startDateTime="
				+ startDateTime + ", endDateTime=" + endDateTime + ", confirmRequired=" + confirmRequired + ", version="
				+ version + ", condition=" + condition + ", products=" + products + ", belowTheLine=" + belowTheLine
				+ ", priceEstablishedLabel=" + priceEstablishedLabel + ", footNoteKey=" + footNoteKey + "]";
	}



	
	public String getId() {
		// TODO Auto-generated method stub
		return tag;
	}

	/*@Override
	public int getImplVersion() {
		// TODO Auto-generated method stub
		return VERSION;
	}*/

}
