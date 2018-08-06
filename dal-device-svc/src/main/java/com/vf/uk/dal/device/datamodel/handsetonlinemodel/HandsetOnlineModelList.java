package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HandsetOnlineModelList {

	@JsonProperty("handsetList")
	private List<HandsetOnlineModel> handsetList;
}
