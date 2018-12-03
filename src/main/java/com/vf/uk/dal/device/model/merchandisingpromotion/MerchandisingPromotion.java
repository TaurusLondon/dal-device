package com.vf.uk.dal.device.model.merchandisingpromotion;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MerchandisingPromotion
 * @author manoj.bera
 *
 */
@Data
public class MerchandisingPromotion {
	@JsonProperty("tag")
	private String tag;
	@JsonProperty("label")
	private String label;
	@JsonProperty("description")
	private String description;
	@JsonProperty("type")
	private String type;
	@JsonProperty("platform")
	private String platform;
	@JsonProperty("promotionMedia")
	private String promotionMedia;
	@JsonProperty("priority")
	private Long priority;
	@JsonProperty("incompatibleMPs")
	private IncompatibleMPs incompatibleMPs;
	@JsonProperty("actionOnRemove")
	private String actionOnRemove;
	@JsonProperty("startDateTime")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp startDateTime;
	@JsonProperty("endDateTime")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp endDateTime;
	@JsonProperty("confirmRequired")
	private boolean confirmRequired;
	@JsonProperty("version")
	private String version;

	@JsonProperty("condition1")
	private Condition condition;
	@JsonProperty("products")
	private List<MerchandisingProduct> products;
	@JsonProperty("belowTheLine")
	private boolean belowTheLine;
	@JsonProperty("priceEstablishedLabel")
	private String priceEstablishedLabel;
	@JsonProperty("footNoteKey")
	private String footNoteKey;

}
