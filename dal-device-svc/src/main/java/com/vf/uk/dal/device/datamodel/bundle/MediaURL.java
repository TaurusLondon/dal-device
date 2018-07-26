package com.vf.uk.dal.device.datamodel.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MediaURL {

	@JsonProperty("mediaName")
	private String mediaName;

	@JsonProperty("mediaURL")
	private String mediaURL;

	public MediaURL() {
		super();
	}
}
