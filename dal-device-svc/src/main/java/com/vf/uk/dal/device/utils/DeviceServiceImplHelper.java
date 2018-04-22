package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.BundleModel;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.querybuilder.DeviceQueryBuilderHelper;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleModelAndPrice;


@Component
public class DeviceServiceImplHelper {
	
	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	ResponseMappingHelper response;
	/**
	 * 
	 * @param filter
	 * @param parameter
	 * @return
	 */
	public String getFilterForDeviceList(String filter, String parameter) {
		String newFilter = "";
		String[] filterArray;
		List<String> filterList = new ArrayList<>();
		if(StringUtils.isNotEmpty(filter)){
			filterArray = filter.split(",");

			if (filterArray != null && filterArray.length > 0) {
				for (String filter1 : filterArray) {
					filterList.add(parameter + filter1);
				}
			}
			newFilter = convertMakeListToString(filterList);
		}
		return newFilter;
	}
	/**
	 * 
	 * @param makeList
	 * @return
	 */
	public String convertMakeListToString(List<String> makeList) {
		return String.join(" OR ", makeList);
	}
	
	/**
	 * 
	 * @param creditLimit
	 * @param listOfProductsNew
	 * @param listOfProductModelNew
	 * @return
	 */
	public BundleModelAndPrice calculatePlan(Float creditLimit, List<String> listOfProductsNew,
			List<ProductModel> listOfProductModelNew) {
		
		List<ProductModel> listOfProductModel = getListOfProductModel(listOfProductsNew);
		BundleModelAndPrice bundleModelAndPrice = new BundleModelAndPrice();
		
		BundleModel bundleModel = null;
		if (listOfProductModel != null && !listOfProductModel.isEmpty()) {
			
			LogHelper.info(this, "inside CP for lead device "+listOfProductsNew);
			List<String> listOfLeadPlanIdNew = new ArrayList<>();
			ProductModel prodModel = listOfProductModel.get(0);
			LogHelper.info(this, "LEad Plan id  "+prodModel.getLeadPlanId());
			listOfLeadPlanIdNew.add(prodModel.getLeadPlanId());

			List<BundleModel> listOfBundleDetails = getListOfBundleModel(listOfLeadPlanIdNew);
			LogHelper.info(this, "List of bundle details  "+listOfBundleDetails);
			if (listOfBundleDetails != null && !listOfBundleDetails.isEmpty()) {
				bundleModel = listOfBundleDetails.get(0);
				
				LogHelper.info(this, "LeadBundle detaisls gross "+bundleModel.getMonthlyGrossPrice());
				BundleDetails bundleDetails = deviceDao.getBundleDetailsFromComplansListingAPI(prodModel.getProductId(),
						null);
				com.vf.uk.dal.utility.entity.BundlePrice bundlePrice = null;
				if (null != bundleDetails && CollectionUtils.isNotEmpty(bundleDetails.getPlanList())) {
					for (com.vf.uk.dal.utility.entity.BundleHeader bundleHeader : bundleDetails.getPlanList()) {
						LogHelper.info(this, "Leaddevice sku and bundle id  sku "+  bundleHeader.getSkuId()  + " id   "+bundleModel.getMonthlyNetPrice());
						if (bundleHeader.getSkuId().equals(bundleModel.getBundleId())) {
							if (null != bundleHeader.getPriceInfo()
									&& null != bundleHeader.getPriceInfo().getBundlePrice()) {
								bundlePrice = bundleHeader.getPriceInfo().getBundlePrice();
								break;
							}
						}
					}
				}
				if (null != bundlePrice) {
					Float monthlyPrice = checkPromotionAndGetPrice(bundlePrice);
					LogHelper.info(this, "Float Monthly price "+ monthlyPrice); 
					if (null == monthlyPrice) {
						bundleModel = null;
					} else if (monthlyPrice > creditLimit) {

						bundleModel = null;
						listOfLeadPlanIdNew = new ArrayList<>();
						listOfLeadPlanIdNew = prodModel.getListOfCompatibleBundles();
						List<BundleModel> listOfBundleDetails1 = getListOfBundleModel(listOfLeadPlanIdNew);;
						bundleModelAndPrice = getLowestMontlyPrice(creditLimit, listOfBundleDetails1, bundleDetails, bundleModelAndPrice);
					} else {
						if (bundlePrice.getMonthlyPrice() != null) {
							if (bundlePrice.getMonthlyPrice().getGross() != null
									&& bundlePrice.getMonthlyPrice().getNet() != null
									&& bundlePrice.getMonthlyPrice().getVat() != null) {
								
								bundleModel.setMonthlyGrossPrice(new Float(bundlePrice.getMonthlyPrice().getGross()));
								bundleModel.setMonthlyNetPrice(new Float(bundlePrice.getMonthlyPrice().getNet()));
								bundleModel.setMonthlyVatPrice(new Float(bundlePrice.getMonthlyPrice().getVat()));
								
								bundleModelAndPrice.setBundlePrice(bundlePrice);
								bundleModelAndPrice.setBundleModel(bundleModel);
							}
						}						
					}

				} else {
					LogHelper.info(this, "Bundle price was null  "); 
					LogHelper.info(this, "Bundle Mode was   "+ bundleModel); 
					bundleModel = null;
				}

			}

			listOfProductModelNew.addAll(listOfProductModel);
		}
		
		return bundleModelAndPrice;
	}

	private Float checkPromotionAndGetPrice(com.vf.uk.dal.utility.entity.BundlePrice bundlePrice) {
		com.vf.uk.dal.utility.entity.MerchandisingPromotion merchandisingPromotion = bundlePrice
				.getMerchandisingPromotions();
		Float monthlyPrice = null;
		if (null != merchandisingPromotion) {
			if (StringUtils.containsIgnoreCase(Constants.FULL_DURATION, merchandisingPromotion.getMpType())) {
				if(StringUtils.isNotBlank(bundlePrice.getMonthlyDiscountPrice().getGross())){
					monthlyPrice = Float.parseFloat(bundlePrice.getMonthlyDiscountPrice().getGross());
				}
			} else {
				if(StringUtils.isNotBlank(bundlePrice.getMonthlyPrice().getGross())) {
					monthlyPrice = Float.parseFloat(bundlePrice.getMonthlyPrice().getGross());
				}
			}
		} else {
			if(StringUtils.isNotBlank(bundlePrice.getMonthlyPrice().getGross())) {
				monthlyPrice = Float.parseFloat(bundlePrice.getMonthlyPrice().getGross());
			}
		}
		return monthlyPrice;
	}

	public BundleModelAndPrice getLowestMontlyPrice(Float creditLimit, List<BundleModel> listOfBundleDetails,
			BundleDetails bundleDetails, BundleModelAndPrice bundleModelAndPrice) {
		Float smallest = Float.MAX_VALUE;
		BundleModel bm = null;
		com.vf.uk.dal.utility.entity.BundleHeader newBundleHeader = null;
		com.vf.uk.dal.utility.entity.BundlePrice bPrice = null;
		for (BundleModel bundleModel : listOfBundleDetails) {
			for (com.vf.uk.dal.utility.entity.BundleHeader bundleHeader : bundleDetails.getPlanList()) {
				if (bundleHeader.getSkuId().equals(bundleModel.getBundleId())) {
					if (null != bundleHeader.getPriceInfo() && null != bundleHeader.getPriceInfo().getBundlePrice()) {
						com.vf.uk.dal.utility.entity.BundlePrice bundlePrice = bundleHeader.getPriceInfo()
								.getBundlePrice();
						Float monthlyGrossPrice = checkPromotionAndGetPrice(bundlePrice);
						if (monthlyGrossPrice != null && monthlyGrossPrice < smallest
								&& monthlyGrossPrice <= creditLimit) {
							smallest = monthlyGrossPrice;
							// assign the smallest bundle
							bm = bundleModel;
							// assign the smallest corresponding bundleheader for prices
							newBundleHeader = bundleHeader;
							bPrice = bundlePrice;

						}
					}
				}
			}

		}
		if (bm != null) {
			if (newBundleHeader.getPriceInfo() != null && newBundleHeader.getPriceInfo().getMonthlyPrice() != null) {
				if (newBundleHeader.getPriceInfo().getMonthlyPrice().getGross() != null
						&& newBundleHeader.getPriceInfo().getMonthlyPrice().getNet() != null
						&& newBundleHeader.getPriceInfo().getMonthlyPrice().getVat() != null) {
					
					bm.setMonthlyGrossPrice(new Float(newBundleHeader.getPriceInfo().getMonthlyPrice().getGross()));
					bm.setMonthlyNetPrice(new Float(newBundleHeader.getPriceInfo().getMonthlyPrice().getNet()));
					bm.setMonthlyVatPrice(new Float(newBundleHeader.getPriceInfo().getMonthlyPrice().getVat()));
					
					bundleModelAndPrice.setBundleModel(bm);
					bundleModelAndPrice.setBundlePrice(bPrice);
				}
			}

		}
		return bundleModelAndPrice;
	}

	public Map<String, String> getLeadDeviceMap(List<String> listOfProductVariants) {
		Map<String, String> deviceMap = new HashMap<>();

		for (String id : listOfProductVariants) {
			if (id.indexOf('|') != -1) {
				deviceMap.put(id.substring(id.indexOf('|') + 1), id.substring(0, id.indexOf('|')));

			}
		}
		return deviceMap;

	}
	/**
	 * 
	 * @param bundleIds
	 * @return
	 */
	public List<BundleModel> getListOfBundleModel(List<String> bundleIds) 
	{
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForBundleModel(bundleIds);;
		SearchResponse bundleModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfBundleModel(bundleModelResponse);
		
	}
	/**
	 * 
	 * @param deviceIds
	 * @return
	 */
	public List<ProductModel> getListOfProductModel(List<String> deviceIds) 
	{
		SearchRequest queryContextMap = DeviceQueryBuilderHelper.searchQueryForProductModel(deviceIds);;
		SearchResponse productModelResponse = deviceDao.getResponseFromDataSource(queryContextMap);
		LogHelper.info(this, "converting elasticsearch response into standard json object response");
		return response.getListOfProductModel(productModelResponse);
		
	}
}