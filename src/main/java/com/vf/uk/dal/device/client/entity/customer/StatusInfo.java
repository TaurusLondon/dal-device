package com.vf.uk.dal.device.client.entity.customer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * StatusInfo
 */
@Data
public class StatusInfo {
	
	@JsonProperty("status")
	private String status = null;

	@JsonProperty("errorCodes")
	private List<String> errorCodes = new ArrayList<>();
}
