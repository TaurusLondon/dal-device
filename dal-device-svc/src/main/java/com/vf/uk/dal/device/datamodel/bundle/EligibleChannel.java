package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class EligibleChannel {

	private String channelName;

	private String startDate;

	private String endDate;

	public EligibleChannel() {
	}
}
