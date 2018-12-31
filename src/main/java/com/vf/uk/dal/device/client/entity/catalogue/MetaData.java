package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.List;
import java.util.UUID;

import lombok.Data;

/**
 * MetaData
 */

@Data
public class MetaData {
	/** Seo Canonical */
	private String seoCanonical;

	/** Seo Description */
	private String seoDescription;

	/** Seo Key Words */
	private String seoKeyWords;

	/** Seo Index */
	private String seoIndex;

	/** Seo Robots */
	private List<UUID> seoRobots;
}
