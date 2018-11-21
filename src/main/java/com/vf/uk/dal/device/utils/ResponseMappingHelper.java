package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotionModel;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResponseMappingHelper {

	@Autowired
	ElasticSearchUtils esUtils;

	/**
	 * 
	 * @param response
	 * @return List<CommercialProduct>
	 */
	public List<CommercialProduct> getCommercialProductFromJson(SearchResponse response) {

		List<CommercialProduct> commercialProductlList = new ArrayList<>();

		try {
			commercialProductlList = esUtils.getListOfObject(response, CommercialProduct.class);
			log.info("<---- Commercial Product list: " + commercialProductlList.size() + "------>");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing Commercial Product list from ES response:::::: " + e);
		}
		return commercialProductlList;

	}

	/**
	 * 
	 * @param response
	 * @return CommercialProduct
	 */
	public CommercialProduct getCommercialProduct(SearchResponse response) {

		CommercialProduct obj = null;
		try {
			obj = esUtils.getObject(response, CommercialProduct.class);
			log.info("<---- Commercial Product list: ---->");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing Commercial product from ES response:::::: " + e);
		}
		return obj;

	}

	/**
	 * 
	 * @param response
	 * @return List<Group>
	 */
	public List<Group> getListOfGroupFromJson(SearchResponse response) {

		List<Group> bundleModelList = new ArrayList<>();
		try {
			bundleModelList = esUtils.getListOfObject(response, Group.class);
			log.info("<---- Product group list:  " + bundleModelList.size() + "----->");
		} catch (Exception e) {
			log.error(":::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return bundleModelList;

	}

	/**
	 * 
	 * @param response
	 * @return Group
	 */
	public Group getSingleGroupFromJson(SearchResponse response) {

		Group obj = new Group();
		try {
			obj = esUtils.getObject(response, Group.class);
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing Product Group from ES response:::::: " + e);
		}
		return obj;

	}

	/**
	 * 
	 * @param response
	 * @return CommercialBundle
	 */
	public CommercialBundle getCommercialBundle(SearchResponse response) {

		CommercialBundle obj = new CommercialBundle();
		try {
			obj = esUtils.getObject(response, CommercialBundle.class);
			log.info("<---- Commercial Bundle list ---->");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing Commercial Bundle from ES response:::::: " + e);
		}
		return obj;

	}

	/**
	 * 
	 * @param response
	 * @return List<CommercialBundle>
	 */
	public List<CommercialBundle> getListOfCommercialBundleFromJson(SearchResponse response) {

		List<CommercialBundle> commercialBundlelList = new ArrayList<>();
		try {
			commercialBundlelList = esUtils.getListOfObject(response, CommercialBundle.class);
			log.info("<---- Commercial Bundle list: " + commercialBundlelList.size() + "----->");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing commercial Bundle list from ES response:::::: " + e);
		}
		return commercialBundlelList;

	}

	/**
	 * 
	 * @param response
	 * @return bundleModelList
	 */
	public List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> getListOfMerchandisingPromotionFromJson(
			SearchResponse response) {

		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion> bundleModelList = new ArrayList<>();
		try {
			bundleModelList = esUtils.getListOfObject(response,
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion.class);
			log.info("<---- Product group list: " + bundleModelList.size() + "---->");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing List of product Group from ES response::::::: " + e);
		}
		return bundleModelList;

	}

	/**
	 * 
	 * @param response
	 * @return MerchandisingPromotion
	 */
	public com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion getMerchandisingPromotion(
			SearchResponse response) {

		com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion obj = new com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion();
		try {
			obj = esUtils.getObject(response,
					com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion.class);
			log.info("<---- Commercial Bundle list ---->");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing Commercial Bundle from ES response:::::: " + e);
		}
		return obj;

	}

	/**
	 * 
	 * @param response
	 * @return List<MerchandisingPromotionModel>
	 */
	public List<MerchandisingPromotionModel> getListOfMerchandisingPromotionModelFromJson(SearchResponse response) {

		List<MerchandisingPromotionModel> merchandisingPromotionModelList = new ArrayList<>();
		try {
			merchandisingPromotionModelList = esUtils.getListOfObject(response, MerchandisingPromotionModel.class);
			log.info("<---- Product group list: " + merchandisingPromotionModelList.size() + "---->");
		} catch (Exception e) {
			log.error(":::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return merchandisingPromotionModelList;

	}

	/**
	 * 
	 * @param response
	 * @return List<ProductGroupModel>
	 */
	public List<ProductGroupModel> getListOfProductGroupModel(SearchResponse response) {

		List<ProductGroupModel> productGroupModel = null;
		try {
			productGroupModel = esUtils.getListOfObject(response, ProductGroupModel.class);
			log.info("<---- Product group Model--> ");
		} catch (Exception e) {
			log.error(":::::::Exception occurred preparing List of product Group from ES response::::::: " + e);
		}
		return productGroupModel;

	}

	/**
	 * 
	 * @param response
	 * @return List<ProductModel>
	 */
	public List<ProductModel> getListOfProductModel(SearchResponse response) {

		List<ProductModel> productModel = null;
		try {
			productModel = esUtils.getListOfObject(response, ProductModel.class);
			log.info("<---- Product group Model--> ");
		} catch (Exception e) {
			log.error(":::::: Exception occurred preparing List of product Group from ES response :::::: " + e);
		}
		return productModel;

	}

	/**
	 * 
	 * @param response
	 * @return List<BundleModel>
	 */
	public List<BundleModel> getListOfBundleModel(SearchResponse response) {

		List<BundleModel> bundleModel = null;
		try {
			bundleModel = esUtils.getListOfObject(response, BundleModel.class);
			log.info("<---- Product group Model---> ");
		} catch (Exception e) {
			log.error("::::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return bundleModel;

	}

	/**
	 * 
	 * @param response
	 * @return List<OfferAppliedPriceModel>
	 */
	public List<OfferAppliedPriceModel> getListOfOfferAppliedPriceModel(SearchResponse response) {

		List<OfferAppliedPriceModel> offerAppliedPriceModel = null;
		try {
			offerAppliedPriceModel = esUtils.getListOfObject(response, OfferAppliedPriceModel.class);
			log.info("<---- Product group Model ");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return offerAppliedPriceModel;

	}

	/**
	 * 
	 * @param response
	 * @return List<FacetField>
	 */
	public List<FacetField> getFacetField(SearchResponse response) {

		List<FacetField> facetField = null;
		try {
			facetField = esUtils.getListOfObjectForAggrs(response);
			log.info("<---- Product group Model ");
		} catch (Exception e) {
			log.error("::::::Exception occurred preparing List of product Group from ES response:::::: " + e);
		}
		return facetField;

	}
}
