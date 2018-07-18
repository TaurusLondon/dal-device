package com.vf.uk.dal.device.entity;

import java.util.List;

/**
 * 
 * OfferPacks
 *
 */
public class OfferPacks {

	private String bundleId;
	private List<MediaLink> mediaLinkList;

	/**
	 * 
	 * @return
	 */
	public String getBundleId() {
		return bundleId;
	}

	/**
	 * 
	 * @param bundleId
	 */
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	/**
	 * 
	 * @return
	 */
	public List<MediaLink> getMediaLinkList() {
		return mediaLinkList;
	}

	/**
	 * 
	 * @param mediaLinkList
	 */
	public void setMediaLinkList(List<MediaLink> mediaLinkList) {
		this.mediaLinkList = mediaLinkList;
	}
}
