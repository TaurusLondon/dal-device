package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.List;

import com.vf.uk.dal.device.model.productgroups.FacetField;

import lombok.Data;
/**
 * 
 * @author sahil.monga
 *
 */
@Data
public class DeviceEntityModel {

	/** deviceList */
	private List<DeviceOnlineModel> deviceList;
	
	/** facet Field */
	private List<FacetField> facetField;
	
	private long totalPageSize;
}
