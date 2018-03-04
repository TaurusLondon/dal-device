package com.vf.uk.dal.device.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.productGroups.pojo.Group;

public class ResponseMappingHelper {
	
	public static List<CommercialProduct> getCommercialBundleFromJson(Response response) {

		List<CommercialProduct> bundleModelList = new ArrayList<>();
		JSONParser parser = new JSONParser();
		try {
			LogHelper.info(ResponseMappingHelper.class, "<---- parsing json object response ---->");
			JSONObject jsonObj = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
			JSONObject jsonObj1 = (JSONObject) jsonObj.get(Constants.STRING_HITS);
			JSONArray jsonObj2 = (JSONArray) jsonObj1.get(Constants.STRING_HITS);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			for (int i = 0; i < jsonObj2.size(); i++) {
				JSONObject jsonObj3 = (JSONObject) jsonObj2.get(i);
				CommercialProduct obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), CommercialProduct.class);
				bundleModelList.add(obj);
			}
			LogHelper.info(ResponseMappingHelper.class, "<---- bundle model list: "+ bundleModelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing bundlemodel from ES response:::::: " + e);
		}
		return bundleModelList;
	
	}
	public static List<Group> getListOfGroupFromJson(Response response) {

		List<Group> bundleModelList = new ArrayList<>();
		JSONParser parser = new JSONParser();
		try {
			LogHelper.info(ResponseMappingHelper.class, "<---- parsing json object response ---->");
			JSONObject jsonObj = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
			JSONObject jsonObj1 = (JSONObject) jsonObj.get(Constants.STRING_HITS);
			JSONArray jsonObj2 = (JSONArray) jsonObj1.get(Constants.STRING_HITS);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			for (int i = 0; i < jsonObj2.size(); i++) {
				JSONObject jsonObj3 = (JSONObject) jsonObj2.get(i);
				Group obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), Group.class);
				bundleModelList.add(obj);
			}
			LogHelper.info(ResponseMappingHelper.class, "<---- bundle model list: "+ bundleModelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing bundlemodel from ES response:::::: " + e);
		}
		return bundleModelList;
	
	}

}
