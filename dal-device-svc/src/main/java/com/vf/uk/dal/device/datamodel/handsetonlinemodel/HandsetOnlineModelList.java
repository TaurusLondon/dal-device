package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class HandsetOnlineModelList {

	@JsonProperty("handsetList")
	private List<HandsetOnlineModel> handsetList;
}
