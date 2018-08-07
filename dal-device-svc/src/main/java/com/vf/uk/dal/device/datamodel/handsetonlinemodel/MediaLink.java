package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MediaLink
 */

/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class MediaLink {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("value")
	private String value = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("priority")
	private Integer priority = null;

}
