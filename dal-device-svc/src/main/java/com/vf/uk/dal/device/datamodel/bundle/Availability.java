package com.vf.uk.dal.device.datamodel.bundle;

import java.sql.Date;

import lombok.Data;

/**
 * 
 * Availability
 *
 */
@Data
public class Availability {

	private Date start;
	private Date end;
	private Boolean salesExpired;
}
