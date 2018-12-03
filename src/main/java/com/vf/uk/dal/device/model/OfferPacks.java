package com.vf.uk.dal.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * OfferPacks
 *
 */
@Data
public class OfferPacks {

	@JsonProperty("bundleId")
	private String bundleId;

	@JsonProperty("mediaLinkList")
	private List<MediaLink> mediaLinkList;
}
