package com.vf.uk.dal.device.datamodel.merchandisingpromotion;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MonthlyPrice {
	@JsonProperty("gross")
	private Float gross;
	@JsonProperty("net")
	private Float net;
	@JsonProperty("vat")
	private Float vat;

	
}
