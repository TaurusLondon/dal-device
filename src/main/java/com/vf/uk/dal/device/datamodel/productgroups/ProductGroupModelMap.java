package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ProductGroupModelMap
 * @author manoj.bera
 *
 */
@Data
public class ProductGroupModelMap {

	@JsonProperty("productgroupMap")
	private Map<String, List<ProductGroupModel>> productgroupMap;
}
