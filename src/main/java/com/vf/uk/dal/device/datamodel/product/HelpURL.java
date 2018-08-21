package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * HelpURL
 * @author manoj.bera
 *
 */
@Data
public class HelpURL {

	@JsonProperty("url")
	private String url;

	@JsonProperty("text")
	private String text;

}
