package com.vf.uk.dal.device.entity;

import java.util.List;

import lombok.Data;

/**
 * 
 * OfferPacks
 *
 */
@Data
public class OfferPacks {

	private String bundleId;
	private List<MediaLink> mediaLinkList;
}
