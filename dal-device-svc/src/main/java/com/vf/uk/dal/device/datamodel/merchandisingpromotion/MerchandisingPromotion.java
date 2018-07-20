package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Timestamp startDateTime;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
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
}
