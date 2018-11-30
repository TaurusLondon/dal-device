package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.bundle.BundleModel;
import com.vf.uk.dal.device.client.entity.bundle.BundleModelAndPrice;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.model.product.ProductModel;

import lombok.extern.slf4j.Slf4j;

/**
 * DeviceConditionallHelper
 * @author manoj.bera
 *
 */
@Slf4j
@Component
public class DeviceConditionallHelper {

	public static final String FULL_DURATION = "full_duration,conditional_full_discount";
	@Autowired
	DeviceDao deviceDao;

	@Autowired
	DeviceESHelper deviceEs;

	/**
	 * 
	 * @param creditLimit
	 * @param listOfProductsNew
	 * @param listOfProductModelNew
	 * @return BundleModelAndPrice
	 */
	public BundleModelAndPrice calculatePlan(Float creditLimit, List<String> listOfProductsNew,
			List<ProductModel> listOfProductModelNew) {

		List<ProductModel> listOfProductModel = deviceEs.getListOfProductModel(listOfProductsNew);
		BundleModelAndPrice bundleModelAndPrice = new BundleModelAndPrice();

		BundleModel bundleModel = null;
		if (listOfProductModel != null && !listOfProductModel.isEmpty()) {

			log.info( "inside CP for lead device " + listOfProductsNew);
			List<String> listOfLeadPlanIdNew = new ArrayList<>();
			ProductModel prodModel = listOfProductModel.get(0);
			log.info( "LEad Plan id  " + prodModel.getLeadPlanId());
			listOfLeadPlanIdNew.add(prodModel.getLeadPlanId());

			List<BundleModel> listOfBundleDetails = deviceEs.getListOfBundleModel(listOfLeadPlanIdNew);
			log.info( "List of bundle details  " + listOfBundleDetails);
			if (listOfBundleDetails != null && !listOfBundleDetails.isEmpty()) {
				bundleModel = listOfBundleDetails.get(0);

				log.info( "LeadBundle detaisls gross " + bundleModel.getMonthlyGrossPrice());
				BundleDetails bundleDetails = deviceDao.getBundleDetailsFromComplansListingAPI(prodModel.getProductId(),
						null);
				BundlePrice bundlePrice = null;
				bundlePrice = setBundlePriceAndBreak(bundleModel, bundleDetails);
				if (null != bundlePrice) {
					Float monthlyPrice = checkPromotionAndGetPrice(bundlePrice);
					log.info( "Float Monthly price " + monthlyPrice);
					bundleModelAndPrice = setBundleModelAndPriceOnBAsisOfMonthlyPrice(creditLimit,bundleModel, prodModel, bundleDetails, bundlePrice, monthlyPrice);

				} else {
					log.info( "Bundle price was null  ");
					log.info( "Bundle Mode was   " + bundleModel);
				}

			}

			listOfProductModelNew.addAll(listOfProductModel);
		}

		return bundleModelAndPrice;
	}

	private BundleModelAndPrice setBundleModelAndPriceOnBAsisOfMonthlyPrice(Float creditLimit,
			BundleModel bundleModel, ProductModel prodModel,
			BundleDetails bundleDetails, BundlePrice bundlePrice, Float monthlyPrice) {
		BundleModelAndPrice bundleModelAndPrice = new BundleModelAndPrice();
		BundleModel bundleModelMethod = bundleModel;
		List<String> listOfLeadPlanIdNew;
		if (monthlyPrice > creditLimit) {
			listOfLeadPlanIdNew = prodModel.getListOfCompatibleBundles();
			List<BundleModel> listOfBundleDetails1 = deviceEs.getListOfBundleModel(listOfLeadPlanIdNew);
			bundleModelAndPrice = getLowestMontlyPrice(creditLimit, listOfBundleDetails1, bundleDetails,
					bundleModelAndPrice);
		} else {
			setBundleModelANdPrice(bundleModelAndPrice, bundleModelMethod, bundlePrice);
		}
		return bundleModelAndPrice;
	}

	private void setBundleModelANdPrice(BundleModelAndPrice bundleModelAndPrice, BundleModel bundleModel,
			BundlePrice bundlePrice) {
		if (bundlePrice.getMonthlyPrice() != null && bundlePrice.getMonthlyPrice().getGross() != null
					&& bundlePrice.getMonthlyPrice().getNet() != null
					&& bundlePrice.getMonthlyPrice().getVat() != null) {

				bundleModel.setMonthlyGrossPrice(new Float(bundlePrice.getMonthlyPrice().getGross()));
				bundleModel.setMonthlyNetPrice(new Float(bundlePrice.getMonthlyPrice().getNet()));
				bundleModel.setMonthlyVatPrice(new Float(bundlePrice.getMonthlyPrice().getVat()));

				bundleModelAndPrice.setBundlePrice(bundlePrice);
				bundleModelAndPrice.setBundleModel(bundleModel);
		}
	}

	private BundlePrice setBundlePriceAndBreak(BundleModel bundleModel, BundleDetails bundleDetails) {
		BundlePrice bundlePrice= null;
		if (null != bundleDetails && CollectionUtils.isNotEmpty(bundleDetails.getPlanList())) {
			for (com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeader : bundleDetails.getPlanList()) {
				log.info( "Leaddevice sku and bundle id  sku " + bundleHeader.getSkuId() + " id   "
						+ bundleModel.getMonthlyNetPrice());
				if (bundleHeader.getSkuId().equals(bundleModel.getBundleId())&& null != bundleHeader.getPriceInfo()
							&& null != bundleHeader.getPriceInfo().getBundlePrice()) {
						bundlePrice = bundleHeader.getPriceInfo().getBundlePrice();
						break;
				}
			}
		}
		return bundlePrice;
	}

	/**
	 * 
	 * @param bundlePrice
	 * @return checkPromotionAndGetPrice
	 */
	private Float checkPromotionAndGetPrice(BundlePrice bundlePrice) {
		com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion merchandisingPromotion = bundlePrice
				.getMerchandisingPromotions();
		Float monthlyPrice = null;
		if (null != merchandisingPromotion) {
			if (StringUtils.containsIgnoreCase(FULL_DURATION, merchandisingPromotion.getMpType())) {
				if (StringUtils.isNotBlank(bundlePrice.getMonthlyDiscountPrice().getGross())) {
					monthlyPrice = Float.parseFloat(bundlePrice.getMonthlyDiscountPrice().getGross());
				}
			} else {
				if (StringUtils.isNotBlank(bundlePrice.getMonthlyPrice().getGross())) {
					monthlyPrice = Float.parseFloat(bundlePrice.getMonthlyPrice().getGross());
				}
			}
		} else {
			if (StringUtils.isNotBlank(bundlePrice.getMonthlyPrice().getGross())) {
				monthlyPrice = Float.parseFloat(bundlePrice.getMonthlyPrice().getGross());
			}
		}
		return monthlyPrice;
	}

	/**
	 * 
	 * @param creditLimit
	 * @param listOfBundleDetails
	 * @param bundleDetails
	 * @param bundleModelAndPrice
	 * @return BundleModelAndPrice
	 */
	public BundleModelAndPrice getLowestMontlyPrice(Float creditLimit, List<BundleModel> listOfBundleDetails,
			BundleDetails bundleDetails, BundleModelAndPrice bundleModelAndPrice) {
		Float smallest = Float.MAX_VALUE;
		BundleModel bm = null;
		com.vf.uk.dal.device.client.entity.bundle.BundleHeader newBundleHeader = null;
		com.vf.uk.dal.device.client.entity.price.BundlePrice bPrice = null;
		for (BundleModel bundleModel : listOfBundleDetails) {
			for (com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeader : bundleDetails.getPlanList()) {
				if (bundleHeader.getSkuId().equals(bundleModel.getBundleId())&& null != bundleHeader.getPriceInfo() && null != bundleHeader.getPriceInfo().getBundlePrice()) {
						com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = bundleHeader.getPriceInfo()
								.getBundlePrice();
						Float monthlyGrossPrice = checkPromotionAndGetPrice(bundlePrice);
						if (monthlyGrossPrice != null && monthlyGrossPrice < smallest
								&& monthlyGrossPrice <= creditLimit) {
							smallest = monthlyGrossPrice;
							bm = bundleModel;
							newBundleHeader = bundleHeader;
							bPrice = bundlePrice;
					}
				}
			}

		}
		if ( bm!= null && newBundleHeader.getPriceInfo() != null && 
					newBundleHeader.getPriceInfo().getMonthlyPrice() != null 
					&& checkNullValuesForPriceInfo(newBundleHeader)) {

					bm.setMonthlyGrossPrice(new Float(newBundleHeader.getPriceInfo().getMonthlyPrice().getGross()));
					bm.setMonthlyNetPrice(new Float(newBundleHeader.getPriceInfo().getMonthlyPrice().getNet()));
					bm.setMonthlyVatPrice(new Float(newBundleHeader.getPriceInfo().getMonthlyPrice().getVat()));
					bundleModelAndPrice.setBundleModel(bm);
					bundleModelAndPrice.setBundlePrice(bPrice);

		}
		return bundleModelAndPrice;
	}

	private boolean checkNullValuesForPriceInfo(com.vf.uk.dal.device.client.entity.bundle.BundleHeader newBundleHeader) {
		return newBundleHeader.getPriceInfo().getMonthlyPrice().getGross() != null
				&& newBundleHeader.getPriceInfo().getMonthlyPrice().getNet() != null
				&& newBundleHeader.getPriceInfo().getMonthlyPrice().getVat() != null;
	}

	/**
	 * 
	 * @param listOfProductVariants
	 * @return LeadDeviceMap
	 */
	public Map<String, String> getLeadDeviceMap(List<String> listOfProductVariants) {
		Map<String, String> deviceMap = new HashMap<>();

		for (String id : listOfProductVariants) {
			if (id.indexOf('|') != -1) {
				deviceMap.put(id.substring(id.indexOf('|') + 1), id.substring(0, id.indexOf('|')));

			}
		}
		return deviceMap;

	}
}