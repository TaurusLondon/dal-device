package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import java.util.List;

import com.vf.uk.dal.device.datamodel.productgroups.FacetField;

import lombok.Data;
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class HandsetOnlineModelList {

	/** handset list */
	private List<HandsetOnlineModel> handsetList;
	
	/** facet field */
	private List<FacetField> facetField;
}
