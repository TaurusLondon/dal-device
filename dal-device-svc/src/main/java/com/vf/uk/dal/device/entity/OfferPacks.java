package com.vf.uk.dal.device.entity;

import java.util.List;

public class OfferPacks {
	
	private String bundleId;
	private List<MediaLink> mediaLinkList;
	
	public String getBundleId() {
		return bundleId;
	}
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}
	public List<MediaLink> getMediaLinkList() {
		return mediaLinkList;
	}
	public void setMediaLinkList(List<MediaLink> mediaLinkList) {
		this.mediaLinkList = mediaLinkList;
	}
}
