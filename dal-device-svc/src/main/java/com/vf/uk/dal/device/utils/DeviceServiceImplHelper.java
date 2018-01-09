package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleModelAndPrice;
import com.vodafone.solrmodels.BundleModel;
import com.vodafone.solrmodels.ProductModel;

@Component
public class DeviceServiceImplHelper {
	
	@Autowired
	DeviceDao deviceDao;
	
	
	public String getFilterCriteria(String make, String capacity, String colour, String operatingSystem,
			String mustHaveFeatures) {
		String newCapacity;
		String newColour;
		String newOperatingSystem;
		String filterCriteria = "";
		String newMustHaveFeatures;
		String musthaveFeatureUpdated;
		if (StringUtils.isNotEmpty(make)) {
			String newMake = getFilterForDeviceList(make, Constants.STRING_EQUIPMENT_MAKE_COLON);
			filterCriteria = newMake;
		}

		if (capacity != null && !"\"\"".equals(capacity)) {
			newCapacity = getFilterForDeviceList(capacity, Constants.STRING_CAPACITY_COLON);
			if (StringUtils.isNotEmpty(filterCriteria)) {
				filterCriteria = filterCriteria + Constants.STRING_AND + newCapacity;
			} else {
				filterCriteria = newCapacity;
			}
		}
		if (colour != null && !"\"\"".equals(colour)) {
			newColour = getFilterForDeviceList(colour, Constants.STRING_COLOUR_COLON);
			if (StringUtils.isNotEmpty(filterCriteria)) {
				filterCriteria = filterCriteria + Constants.STRING_AND + newColour;
			} else {
				filterCriteria = newColour;
			}
		}
		if (operatingSystem != null && !"\"\"".equals(operatingSystem)) {
			newOperatingSystem = getFilterForDeviceList(operatingSystem, Constants.STRING_OPERATING_SYSTEM);
			if (StringUtils.isNotEmpty(filterCriteria)) {
				filterCriteria = filterCriteria + Constants.STRING_AND + newOperatingSystem;
			} else {
				filterCriteria = newOperatingSystem;
			}
		}
		if (mustHaveFeatures != null && !"\"\"".equals(mustHaveFeatures)) {
			newMustHaveFeatures = getFilterForDeviceList(mustHaveFeatures,
					Constants.STRING_MUST_HAVE_FEATURES_WITH_COLON);
			if (StringUtils.isNotEmpty(filterCriteria)) {
				filterCriteria = filterCriteria + Constants.STRING_AND + newMustHaveFeatures;
			} else {
				filterCriteria = newMustHaveFeatures;
			}
		}
		return filterCriteria;
	}
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
	
	public BundleModelAndPrice calculatePlan(Float creditLimit, List<String> listOfProductsNew,
			List<ProductModel> listOfProductModelNew) {
		// get the compatible plans for lead device id/ device id
		List<ProductModel> listOfProductModel = deviceDao.getProductModel(listOfProductsNew);
		
		BundleModelAndPrice bundleModelAndPrice = new BundleModelAndPrice();
		
		BundleModel bundleModel = null;
		// Map<String, BundleModel> bundleModelMapNew = new HashMap<>();
		if (listOfProductModel != null && !listOfProductModel.isEmpty()) {
			
			LogHelper.info(this, "inside CP for lead device "+listOfProductsNew);
			List<String> listOfLeadPlanIdNew = new ArrayList<>();
			ProductModel prodModel = listOfProductModel.get(0);
			// get the leadplan id
			LogHelper.info(this, "LEad Plan id  "+prodModel.getLeadPlanId());
			listOfLeadPlanIdNew.add(prodModel.getLeadPlanId());

			// get the bundle details for new plan
			List<BundleModel> listOfBundleDetails = deviceDao.getBundleDetails(listOfLeadPlanIdNew);
			
			LogHelper.info(this, "List of bundle details  "+listOfBundleDetails);
			if (listOfBundleDetails != null && !listOfBundleDetails.isEmpty()) {
				bundleModel = listOfBundleDetails.get(0);
				
				LogHelper.info(this, "LeadBundle detaisls gross "+bundleModel.getMonthlyGrossPrice());
				LogHelper.info(this, "LeadBundle detaisls net "+bundleModel.getMonthlyNetPrice());
				// check the monthly gros of bundle with credit limit
				// if (bundleModel.getMonthlyGrossPrice() <= creditLimit) {
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
						List<BundleModel> listOfBundleDetails1 = deviceDao.getBundleDetails(listOfLeadPlanIdNew);
						// now get the lowest monthly plan for the bundle list
						// and use this plan
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
			com.vodafone.merchandisingPromotion.pojo.MerchandisingPromotion merchanPromo = deviceDao
					.getMerchandisingPromotionByPromotionName(merchandisingPromotion.getTag());
			if (merchanPromo != null && StringUtils.containsIgnoreCase(Constants.FULL_DURATION, merchanPromo.getType())) {
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
		Map<String, String> deviceMap = new HashMap<String, String>();

		for (String id : listOfProductVariants) {
			if (id.indexOf("|") != -1) {
				deviceMap.put(id.substring(id.indexOf("|") + 1), id.substring(0, id.indexOf("|")));

			}
		}
		return deviceMap;

	}

}