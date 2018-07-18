package com.vf.uk.dal.device.datamodel.product;

public class MediaURL {

	private String mediaName;

	private String mediaURL;

	public MediaURL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mediaName == null) ? 0 : mediaName.hashCode());
		result = prime * result + ((mediaURL == null) ? 0 : mediaURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaURL other = (MediaURL) obj;
		if (mediaName == null) {
			if (other.mediaName != null)
				return false;
		} else if (!mediaName.equals(other.mediaName))
			return false;
		if (mediaURL == null) {
			if (other.mediaURL != null)
				return false;
		} else if (!mediaURL.equals(other.mediaURL))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MediaURL [mediaName=" + mediaName + ", mediaURL=" + mediaURL + "]";
	}

}
