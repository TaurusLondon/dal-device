package com.vf.uk.dal.device.datamodel.productgroups;

import java.util.List;
import java.util.Map;

public class ProductGroupModelMap {

	private Map<String, List<ProductGroupModel>> productgroupMap;

	public Map<String, List<ProductGroupModel>> getProductgroupMap() {
		return productgroupMap;
	}

	public void setProductgroupMap(Map<String, List<ProductGroupModel>> productgroupMap) {
		this.productgroupMap = productgroupMap;
	}
}
