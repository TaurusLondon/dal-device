package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * MetaData
 */
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class MetaData {
	@JsonProperty("seoCanonical")
	private String seoCanonical = null;

	@JsonProperty("seoDescription")
	private String seoDescription = null;

	@JsonProperty("seoKeyWords")
	private String seoKeyWords = null;

	@JsonProperty("seoIndex")
	private String seoIndex = null;

	@JsonProperty("seoRobots")
	private List<UUID> seoRobots = null;
}
