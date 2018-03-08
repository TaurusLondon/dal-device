package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.utils.Constants;

@Component
public class ResponseMappingHelper {
	
	/**
	 * 
	 * @param response
	 * @return
	 */
	public  List<CommercialProduct> getCommercialProductFromJson(Response response) {

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
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Product list: "+ bundleModelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Product list from ES response:::::: " + e);
		}
		return bundleModelList;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public CommercialProduct getCommercialProduct(Response response) {

		JSONParser parser = new JSONParser();
		CommercialProduct obj=null;
		try {
			LogHelper.info(ResponseMappingHelper.class, "<---- parsing json object response ---->");
			JSONObject jsonObj = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
			JSONObject jsonObj1 = (JSONObject) jsonObj.get(Constants.STRING_HITS);
			JSONArray jsonObj2 = (JSONArray) jsonObj1.get(Constants.STRING_HITS);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
				JSONObject jsonObj3 = (JSONObject) jsonObj2.get(0);
				 obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), CommercialProduct.class);
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Product list: ---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial product from ES response:::::: " + e);
		}
		return obj;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public List<Group> getListOfGroupFromJson(Response response) {

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
			LogHelper.info(ResponseMappingHelper.class, "<---- Product group list: "+ bundleModelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return bundleModelList;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public Group getSingleGroupFromJson(Response response) {

		JSONParser parser = new JSONParser();
		Group obj =new Group();
		try {
			LogHelper.info(ResponseMappingHelper.class, "<---- parsing json object response ---->");
			JSONObject jsonObj = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
			JSONObject jsonObj1 = (JSONObject) jsonObj.get(Constants.STRING_HITS);
			JSONArray jsonObj2 = (JSONArray) jsonObj1.get(Constants.STRING_HITS);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				JSONObject jsonObj3 = (JSONObject) jsonObj2.get(0);
				 obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), Group.class);
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Product Group from ES response:::::: " + e);
		}
		return obj;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public CommercialBundle getCommercialBundle(Response response) {

		JSONParser parser = new JSONParser();
		CommercialBundle obj=new CommercialBundle();
		try {
			LogHelper.info(ResponseMappingHelper.class, "<---- parsing json object response ---->");
			JSONObject jsonObj = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
			JSONObject jsonObj1 = (JSONObject) jsonObj.get(Constants.STRING_HITS);
			JSONArray jsonObj2 = (JSONArray) jsonObj1.get(Constants.STRING_HITS);
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
				JSONObject jsonObj3 = (JSONObject) jsonObj2.get(0);
				 obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), CommercialBundle.class);
				
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Bundle list ---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Bundle from ES response:::::: " + e);
		}
		return obj;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public List<CommercialBundle> getListOfCommercialBundleFromJson(Response response) {

		List<CommercialBundle> bundleModelList = new ArrayList<>();
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
				CommercialBundle obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), CommercialBundle.class);
				bundleModelList.add(obj);
			}
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Bundle list: "+ bundleModelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing commercial Bundle list from ES response:::::: " + e);
		}
		return bundleModelList;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> getListOfMerchandisingPromotionFromJson(Response response) {

		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> bundleModelList = new ArrayList<>();
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
				com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion.class);
				bundleModelList.add(obj);
			}
			LogHelper.info(ResponseMappingHelper.class, "<---- Product group list: "+ bundleModelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return bundleModelList;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion getMerchandisingPromotion(Response response) {

		JSONParser parser = new JSONParser();
		com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion obj=new 
				com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion();
		try {
			LogHelper.info(ResponseMappingHelper.class, "<---- parsing json object response ---->");
			JSONObject jsonObj = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
			JSONObject jsonObj1 = (JSONObject) jsonObj.get(Constants.STRING_HITS);
			JSONArray jsonObj2 = (JSONArray) jsonObj1.get(Constants.STRING_HITS);
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
				JSONObject jsonObj3 = (JSONObject) jsonObj2.get(0);
				 obj = mapper.readValue(jsonObj3.get(Constants.STRING_SOURCE).toString(), com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion.class);
				
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Bundle list ---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Bundle from ES response:::::: " + e);
		}
		return obj;
	
	}

}
