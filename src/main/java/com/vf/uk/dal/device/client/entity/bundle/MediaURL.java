package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MediaURL
 * @author manoj.bera
 *
 */
@Data
public class MediaURL {

	@JsonProperty("mediaName")
	private String mediaName;

	@JsonProperty("mediaURL")
	private String mediaURL;

}
