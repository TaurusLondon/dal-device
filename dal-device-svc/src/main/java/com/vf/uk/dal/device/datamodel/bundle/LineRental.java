package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class LineRental {

	private String lineRentalProductId;

	private Long lineRentalAmount;

	public LineRental() {
		super();
	}
}
