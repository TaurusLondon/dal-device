package com.vf.uk.dal.device.client.entity.bundle;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Availability
 *
 */
@Data
public class Availability {

	@JsonProperty("start")
	private Date start;

	@JsonProperty("end")
	private Date end;

	@JsonProperty("salesExpired")
	private Boolean salesExpired;
}
