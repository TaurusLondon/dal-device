package com.vf.uk.dal.device.client.entity.bundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ImageURL
 * @author manoj.bera
 *
 */
@Data
public class ImageURL {

	@JsonProperty("imageName")
	private String imageName;

	@JsonProperty("imageURL")
	private String imageURL;

}
