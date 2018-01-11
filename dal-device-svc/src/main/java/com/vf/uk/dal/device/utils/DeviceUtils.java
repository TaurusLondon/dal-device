package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.utility.entity.BundleAllowance;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleHeader;
import com.vf.uk.dal.utility.entity.PriceForBundleAndHardware;

/**
 * Contains utility methods for bundle svc
 *
 */
public class DeviceUtils {
	static Double selectedPlanAlwnce = null;

	public static BundleDetails getSimilarPlanList(BundleDetails bndlDtls, String allowedRecurringPriceLimit,
			String bundleId, String plansLimit, BundleDetails bndlDtlsWithoutFullDuration) {
		BundleDetails bundleDeatils = new BundleDetails();
		List<BundleHeader> similarplan = new ArrayList<>();
		List<BundleHeader> allowblPlanLst = new ArrayList<>();
		List<BundleHeader> sameMonthlyPricePlanList = new ArrayList<>();

		// Listing all plans into descending order which are less than
		// allowedRecurringPriceLimit.
		for (BundleHeader bundleHeader : bndlDtlsWithoutFullDuration.getPlanList()) {
			if (StringUtils.equalsIgnoreCase(bundleId, bundleHeader.getSkuId())) {
				selectedPlanAlwnce = getBundleAllowance(bundleHeader, Constants.STRING_DATA_AlLOWANCE);
			}
		}
		for (BundleHeader bundleHeader : bndlDtls.getPlanList()) {
			if (compareMntlyPrcAlwableLmt(bundleHeader, allowedRecurringPriceLimit) <= 0) {
				allowblPlanLst.add(bundleHeader);
			}
		}

		if (selectedPlanAlwnce != null) {
			if (!allowblPlanLst.isEmpty()) {
				if (allowblPlanLst.size() > 1) {
					allowblPlanLst = sortedPlanUnderAllowedLimit(allowblPlanLst);
					String highestMonthlyPrice = getmonthlyPriceForBndl(allowblPlanLst.get(0));
					for (BundleHeader bundleHeader : allowblPlanLst) {
						String bundleMonthlyPrice = getmonthlyPriceForBndl(bundleHeader);
						if (bundleMonthlyPrice != null && bundleMonthlyPrice.equalsIgnoreCase(highestMonthlyPrice)) {
							sameMonthlyPricePlanList.add(bundleHeader);
						}
					}
					if (!sameMonthlyPricePlanList.isEmpty() && sameMonthlyPricePlanList.size() == 1) {
						similarplan.add(allowblPlanLst.get(0));
					} else if (!sameMonthlyPricePlanList.isEmpty() && sameMonthlyPricePlanList.size() > 1) {
						List<BundleHeader> allowblPlanLstMore = sortedPalnsUnderDataLimit(sameMonthlyPricePlanList);
						allowblPlanLstMore = removeObjectMoreDataLimit(allowblPlanLstMore, selectedPlanAlwnce);
						if (allowblPlanLstMore.isEmpty()) {
							LogHelper.error(DeviceUtils.class, ExceptionMessages.STRING_NO_SIMILAR_PLAN);
							throw new ApplicationException(ExceptionMessages.STRING_NO_SIMILAR_PLAN);
						} else {

							for (int i = 0; i < Integer.parseInt(plansLimit); i++) {
								if (allowblPlanLstMore.size() > i) {
									similarplan.add(allowblPlanLstMore.get(i));
								} else {
									break;
								}
							}
						}
					}
				} else {
					similarplan.add(allowblPlanLst.get(0));
				}
			} else {
				LogHelper.error(DeviceUtils.class, ExceptionMessages.STRING_NO_SIMILAR_PLAN);
				throw new ApplicationException(ExceptionMessages.STRING_NO_SIMILAR_PLAN);
			}
		} else {
			LogHelper.error(DeviceUtils.class, ExceptionMessages.STRING_NO_SIMILAR_PLAN);
			throw new ApplicationException(ExceptionMessages.INVALID_BUNDLE_ID);
		}
		selectedPlanAlwnce = null;
		bundleDeatils.setPlanList(similarplan);
		return bundleDeatils;
	}

	/**
	 * 
	 * @param allowblPlanLst
	 * @return
	 */
	public static List<BundleHeader> sortedPlanUnderAllowedLimit(List<BundleHeader> allowblPlanLst) {
		Collections.sort(allowblPlanLst, (BundleHeader bh1, BundleHeader bh2) -> {
			Double index1 = getDoubleFrmString(getmonthlyPriceForBndl(bh1));
			Double index2 = getDoubleFrmString(getmonthlyPriceForBndl(bh2));
			return -Double.compare(index1, index2);
		});
		return allowblPlanLst;
	}

	/**
	 * 
	 * @param allowblPlanLstMore
	 * @return
	 */
	public static List<BundleHeader> sortedPalnsUnderDataLimit(List<BundleHeader> allowblPlanLstMore) {
		Collections.sort(allowblPlanLstMore, (BundleHeader bh1, BundleHeader bh2) -> {
			Double index1 = getBundleAllowance(bh1, Constants.STRING_DATA_AlLOWANCE);
			Double index2 = getBundleAllowance(bh2, Constants.STRING_DATA_AlLOWANCE);
			return -Double.compare(index1, index2);
		});
		return allowblPlanLstMore;
	}

	/**
	 * 
	 * @param allowblPlanLst
	 * @param selectedPlanAlwnce
	 * @return
	 */
	public static List<BundleHeader> removeObjectMoreDataLimit(List<BundleHeader> allowblPlanLst,
			Double selectedPlanAlwnce) {
		Iterator<BundleHeader> it = allowblPlanLst.iterator();
		while (it.hasNext()) {
			BundleHeader bundleHeader = it.next();
			Double result = checkDataLimit(getBundleAllowance(bundleHeader, Constants.STRING_DATA_AlLOWANCE),
					selectedPlanAlwnce);
			if (result == null) {
				it.remove();
			}
		}
		return allowblPlanLst;
	}

	/**
	 * 
	 * @param allowance
	 * @param selectedPlanAlwnce
	 * @return
	 */
	public static Double checkDataLimit(Double allowance, Double selectedPlanAlwnce) {
		Double data = 0.0;
		String dataDifference = ConfigHelper.getString(Constants.STRING_DATA_DIFFERENCE,
				Constants.STRING_DEFAULT_DATA_DIFFERENCE);
		String dataDifferenceUom = ConfigHelper.getString(Constants.STRING_DATA_DIFFERENCE_UOM,
				Constants.STRING_DEFAULT_DATA_DIFFERENCE_UOM);
		if (allowance == null) {
			return null;
		} else if (allowance <= selectedPlanAlwnce) {
			if (StringUtils.isBlank(dataDifference) || StringUtils.isBlank(dataDifferenceUom)) {
				return allowance;
			} else {
				if (StringUtils.containsIgnoreCase(dataDifferenceUom, Constants.STRING_DATA_UOM_GB)) {
					data = dataConversion(dataDifference, Constants.STRING_DATA_UOM_GB);
				}
				data = selectedPlanAlwnce - data;
				if (allowance >= data) {
					return allowance;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param data
	 * @param type
	 * @return
	 */
	public static Double dataConversion(String data, String type) {
		if (StringUtils.isNotBlank(data)) {
			Double d = Double.parseDouble(data);
			if (Constants.STRING_DATA_UOM_GB.equalsIgnoreCase(type)) {
				d = d * 1024;
			}
			return d;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param bundleHeader
	 * @param allowanceType
	 * @return
	 */
	public static Double getBundleAllowance(BundleHeader bundleHeader, String allowanceType) {
		for (BundleAllowance allowance : bundleHeader.getAllowance()) {
			if (StringUtils.equalsIgnoreCase(allowance.getType(), allowanceType)) {
				return dataConversion(allowance.getValue(), allowance.getUom());
			}
		}
		return null;
	}

	/**
	 * 
	 * @param bundleHeader
	 * @param allowedRecurringPriceLimit
	 * @return
	 */
	public static int compareMntlyPrcAlwableLmt(BundleHeader bundleHeader, String allowedRecurringPriceLimit) {
		String monthlyPrice = getmonthlyPriceForBndl(bundleHeader);
		if (StringUtils.isNotBlank(monthlyPrice) && StringUtils.isNotBlank(allowedRecurringPriceLimit)) {
			return compareStringPrc(monthlyPrice, allowedRecurringPriceLimit);
		}
		return 0;
	}

	/**
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public static int compareStringPrc(String actual, String expected) {
		int result = 0;
		result = Double.compare(getDoubleFrmString(actual), getDoubleFrmString(expected));
		return result;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Double getDoubleFrmString(String value) {
		Double dValue = null;
		if (StringUtils.isNotBlank(value))
			dValue = Double.valueOf(value);
		return dValue;
	}

	/**
	 * 
	 * @param bundleHeader
	 * @return
	 */
	public static String getmonthlyPriceForBndl(BundleHeader bundleHeader) {
		String monthlyPrice = null;
		if (bundleHeader != null && bundleHeader.getPriceInfo().getBundlePrice() != null) {
			if (bundleHeader.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
					&& bundleHeader.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
				monthlyPrice = bundleHeader.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
			} else {
				monthlyPrice = bundleHeader.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
			}
		}
		return monthlyPrice;
	}
	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static String getmonthlyPriceFormPrice(PriceForBundleAndHardware priceForBundleAndHardware) {
		String monthlyPrice = null;
		if (priceForBundleAndHardware != null && priceForBundleAndHardware.getBundlePrice() != null) {
			if (priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice() != null
					&& priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross() != null) {
				monthlyPrice = priceForBundleAndHardware.getBundlePrice().getMonthlyDiscountPrice().getGross();
			} else {
				monthlyPrice = priceForBundleAndHardware.getBundlePrice().getMonthlyPrice().getGross();
			}
		}
		return monthlyPrice;
	}
	/**
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public static List<PriceForBundleAndHardware> sortedPriceForBundleAndHardware(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware, (PriceForBundleAndHardware bh1, PriceForBundleAndHardware bh2) -> {
			Double index1 = getDoubleFrmString(getmonthlyPriceFormPrice(bh1));
			Double index2 = getDoubleFrmString(getmonthlyPriceFormPrice(bh2));
			return Double.compare(index1, index2);
		});
		return listOfPriceForBundleAndHardware;
	}
	/**
	 * \
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public static String leastMonthlyPrice(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware)
	{
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareAfterSorted=sortedPriceForBundleAndHardware(listOfPriceForBundleAndHardware);
		return getmonthlyPriceFormPrice(listOfPriceForBundleAndHardwareAfterSorted.get(0));
	}
	/**
	 * \
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public static String leastMonthlyPriceForpayG(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware)
	{
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardwareAfterSorted=sortedPriceForBundleAndHardwareForPayG(listOfPriceForBundleAndHardware);
		return getmonthlyPriceFormPriceForPayG(listOfPriceForBundleAndHardwareAfterSorted.get(0));
	}
	/**
	 * 
	 * @param listOfPriceForBundleAndHardware
	 * @return
	 */
	public static List<PriceForBundleAndHardware> sortedPriceForBundleAndHardwareForPayG(List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware) {
		Collections.sort(listOfPriceForBundleAndHardware, (PriceForBundleAndHardware bh1, PriceForBundleAndHardware bh2) -> {
			Double index1 = getDoubleFrmString(getmonthlyPriceFormPriceForPayG(bh1));
			Double index2 = getDoubleFrmString(getmonthlyPriceFormPriceForPayG(bh2));
			return Double.compare(index1, index2);
		});
		return listOfPriceForBundleAndHardware;
	}
	/**
	 * 
	 * @param priceForBundleAndHardware
	 * @return
	 */
	public static String getmonthlyPriceFormPriceForPayG(PriceForBundleAndHardware priceForBundleAndHardware) {
		String oneOffPrice = null;
		if (priceForBundleAndHardware != null && priceForBundleAndHardware.getHardwarePrice() != null) {
			if (priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice() != null
					&& priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross() != null) {
				oneOffPrice = priceForBundleAndHardware.getHardwarePrice().getOneOffDiscountPrice().getGross();
			} else {
				oneOffPrice = priceForBundleAndHardware.getHardwarePrice().getOneOffPrice().getGross();
			}
		}
		return oneOffPrice;
	}
}
