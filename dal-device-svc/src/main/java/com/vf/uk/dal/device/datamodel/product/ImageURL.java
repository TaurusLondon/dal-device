package com.vf.uk.dal.device.datamodel.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImageURL {

	@JsonProperty("imageName")
	private String imageName;

	@JsonProperty("imageURL")
	private String imageURL;

}
