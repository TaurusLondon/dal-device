package com.vf.uk.dal.device.datamodel.product;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductAvailability
 * @author manoj.bera
 *
 */
@Data
public class ProductAvailability {

	@JsonProperty("start")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date start;

	@JsonProperty("end")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date end;

	@JsonProperty("salesExpired")
	private boolean salesExpired;

}
