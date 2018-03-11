package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Component;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;

@Component
public class ResponseMappingHelper {
	
	/**
	 * 
	 * @param response
	 * @return
	 */
	public  List<CommercialProduct> getCommercialProductFromJson(SearchResponse response) {

		List<CommercialProduct> commercialProductlList = new ArrayList<>();
		
		try {
			commercialProductlList=ElasticSearchUtils.getListOfObject(response, CommercialProduct.class);
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Product list: "+ commercialProductlList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Product list from ES response:::::: " + e);
		}
		return commercialProductlList;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public CommercialProduct getCommercialProduct(SearchResponse response) {

		CommercialProduct obj=null;
		try {
			obj=ElasticSearchUtils.getObject(response, CommercialProduct.class);
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
	public List<Group> getListOfGroupFromJson(SearchResponse response) {

		List<Group> bundleModelList = new ArrayList<>();
		try {
			bundleModelList=ElasticSearchUtils.getListOfObject(response, Group.class);
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
	public Group getSingleGroupFromJson(SearchResponse response) {

		Group obj =new Group();
		try {
			obj=ElasticSearchUtils.getObject(response, Group.class);
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
	public CommercialBundle getCommercialBundle(SearchResponse response) {

		CommercialBundle obj=new CommercialBundle();
		try {
			obj=ElasticSearchUtils.getObject(response, CommercialBundle.class);
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
	public List<CommercialBundle> getListOfCommercialBundleFromJson(SearchResponse response) {

		List<CommercialBundle> commercialBundlelList = new ArrayList<>();
		try {
			commercialBundlelList=ElasticSearchUtils.getListOfObject(response, CommercialBundle.class);
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Bundle list: "+ commercialBundlelList.size() + "---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing commercial Bundle list from ES response:::::: " + e);
		}
		return commercialBundlelList;
	
	}
	/**
	 * 
	 * @param response
	 * @return
	 */
	public List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> getListOfMerchandisingPromotionFromJson(SearchResponse response) {

		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> bundleModelList = new ArrayList<>();
		try {
			bundleModelList=ElasticSearchUtils.getListOfObject(response, com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion.class);
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
	public com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion getMerchandisingPromotion(SearchResponse response) {

		com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion obj=new 
				com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion();
		try {
			obj=ElasticSearchUtils.getObject(response, com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion.class);
			LogHelper.info(ResponseMappingHelper.class, "<---- Commercial Bundle list ---->");
		} catch (Exception e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Bundle from ES response:::::: " + e);
		}
		return obj;
	
	}

}
