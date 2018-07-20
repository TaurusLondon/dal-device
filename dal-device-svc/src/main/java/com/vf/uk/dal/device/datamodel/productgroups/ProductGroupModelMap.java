package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;
import java.util.Map;

import lombok.Data;
@Data
public class ProductGroupModelMap {

	private Map<String, List<ProductGroupModel>> productgroupMap;
}
