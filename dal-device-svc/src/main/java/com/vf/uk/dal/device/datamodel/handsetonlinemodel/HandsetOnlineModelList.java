package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;

import lombok.Data;
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class HandsetOnlineModelList {

	@JsonProperty("handsetList")
	private List<HandsetOnlineModel> handsetList;
	
	@JsonProperty("facetField")
	private List<FacetField> facetField;
}
