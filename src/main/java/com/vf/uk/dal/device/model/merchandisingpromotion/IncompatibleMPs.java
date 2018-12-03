package com.vf.uk.dal.device.model.merchandisingpromotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * IncompatibleMPs
 * @author manoj.bera
 *
 */
@Data
public class IncompatibleMPs {
	@JsonProperty("tag")
	private List<String> tag;

	
}
