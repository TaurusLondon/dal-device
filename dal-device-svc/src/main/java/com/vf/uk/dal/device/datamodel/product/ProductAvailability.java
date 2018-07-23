package com.vf.uk.dal.device.datamodel.product;

import java.sql.Date;

import lombok.Data;

@Data
public class ProductAvailability {

	private Date start;

	private Date end;

	private boolean salesExpired;

}
