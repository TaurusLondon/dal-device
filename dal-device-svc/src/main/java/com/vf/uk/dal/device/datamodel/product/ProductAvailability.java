package com.vf.uk.dal.device.datamodel.product;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductAvailability {

	@JsonProperty("start")
	private Date start;

	@JsonProperty("end")
	private Date end;

	@JsonProperty("salesExpired")
	private boolean salesExpired;

}
