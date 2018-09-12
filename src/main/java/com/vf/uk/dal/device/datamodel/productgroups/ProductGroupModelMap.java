package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ProductGroupModelMap
 * @author manoj.bera
 *
 */
@Data
@ApiModel(value="productGroupMap" , description="Map of Product Group Model")
public class ProductGroupModelMap {

	@ApiModelProperty(value = "Map of Product Group", reference = "Map")
	@JsonProperty("productgroupMap")
	private Map<String, List<ProductGroupModel>> productgroupMap;
}
