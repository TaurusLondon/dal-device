package com.vf.uk.dal.device.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.entity.Asset;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.entity.Subscription;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;
import com.vodafone.product.pojo.CommercialProduct;
/**
 * 
 * common methods used across the services.
 *
 */

public  class CommonUtility {
	/**
	 * Generates JWT token for security
	 * 
	 * **/
	/*public static String generateJWT() 
	{
		return ServiceContext.getJWTToken();
	}*/
	/**
	 * Round off price to two decimal points
	 * @return DecimalFormat
	 */
		
	public static DecimalFormat getDecimalFormat(){
		return new DecimalFormat("#0.00");
	}
	/**
	 * Date to String conversion.
	 * @param date
	 * @param strDateFormat
	 * @return dateToStr
	 */
	
	public static String getDateToString(Date date, String strDateFormat) {
		String formatdate=null;
		if(date!=null)
		{
			SimpleDateFormat format = new SimpleDateFormat(strDateFormat);
			formatdate=format.format(date);
		}
		return formatdate;
	}
	
	public static List<PriceForBundleAndHardware> getPriceDetails(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,String offerCode,RegistryClient registryClient,String journeyType) {
		RestTemplate restTemplate =registryClient.getRestTemplate();
		RequestForBundleAndHardware requestForBundleAndHardware=new RequestForBundleAndHardware();
		requestForBundleAndHardware.setBundleAndHardwareList(bundleAndHardwareTupleList);
		requestForBundleAndHardware.setOfferCode(offerCode);
		requestForBundleAndHardware.setPackageType(journeyType);
		PriceForBundleAndHardware[] client=restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware" ,requestForBundleAndHardware,PriceForBundleAndHardware[].class);
 		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<List<PriceForBundleAndHardware>>(){});
		
	}
	
	/*public static List<StockInfo> getStockAvailabilityForDevice(String deviceIds, RegistryClient registryClient) {
		String stockId=ConfigHelper.getString(Constants.STRING_WAREHOUSE_ID, Constants.STRING_DEFAULT_STOCKID);
		RestTemplate restTemplate =registryClient.getRestTemplate();
		StockInfo[] client= restTemplate.getForObject("http://UTILITY-V1/utility/?filter[skuId]="+deviceIds+"&filter[sourceId]="+stockId , StockInfo[].class  );
 		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<List<StockInfo>>(){});
		
	}*/
	public static CurrentJourney getCurrentJourney(String journeyId,RegistryClient registryClient) {
		RestTemplate restTemplate =registryClient.getRestTemplate();
		return restTemplate.getForObject("http://COMMON-V1/common/journey/"+journeyId+"/queries/currentJourney" ,CurrentJourney.class);
	}
	
	public static RecommendedProductListResponse getRecommendedProductList(RecommendedProductListRequest recomProductList,RegistryClient registryClient) {
		RestTemplate restTemplate =registryClient.getRestTemplate();
		return restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",recomProductList,RecommendedProductListResponse.class);
	}
	
	public static BundleDetailsForAppSrv getPriceDetailsForCompatibaleBundle(String deviceId,RegistryClient registryClient) {
		try {
			RestTemplate restTemplate =registryClient.getRestTemplate();
			return restTemplate.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=" +deviceId, BundleDetailsForAppSrv.class );
		} catch (Exception e) {
			LogHelper.error(CommonUtility.class, ""+e);
			return null;
		}}
	/**
	 * Gets the bundle details from complans listing API.
	 *
	 * @param deviceId
	 *            the device id
	 * @param registryClient
	 *            the registry client
	 * @return the bundle details from complans listing API
	 */
	public static BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria,
			RegistryClient registryClient) {
		RestTemplate restTemplate = registryClient.getRestTemplate();
		String URL = "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byDeviceId/" + deviceId + "/";
		if (sortCriteria != null && StringUtils.isNotBlank(sortCriteria)) {
			URL += "/?sort=" + sortCriteria;
		}
		BundleDetails client=new BundleDetails();
		try{
		 client = restTemplate.getForObject(URL, BundleDetails.class);
		} 
		catch(Exception e){
			LogHelper.error(CommonUtility.class, "getBundleDetailsFromGetCompatibleListAPI API Exception---------------"+e);
			throw new ApplicationException(e.getLocalizedMessage());
	}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<BundleDetails>() {
		});
	}
	public static JSONObject getJSONFromString(String strTobeConverted) {		
    	   JSONParser parser = new JSONParser();
    	   JSONObject jsonObject= null;
             try {
            	 jsonObject = (JSONObject) parser.parse(strTobeConverted);
			} catch (org.json.simple.parser.ParseException exception) {				
				LogHelper.error(CommonUtility.class, "Error while parsing string to JSONObject "+exception);
				throw new ApplicationException(ExceptionMessages.ERROR_STRING_TO_JSONOBJECT);
			}
		return jsonObject;
	}
	
	public static String trimLeadingZeros(String source) {
		int length = source.length();

		if (length < 2)
			return source;

		int i;
		for (i = 0; i < length - 1; i++) {
			char c = source.charAt(i);
			if (c != '0')
				break;
		}
		if (i == 0)
			return source;
		return source.substring(i);
	}
	
	public static String appendPrefixString(String deviceId)
	{
		StringBuilder target= new StringBuilder(Constants.PREFIX_SKU);
		target.append(deviceId.substring(1, deviceId.length()));
		return target.toString();
	}
	public static PriceForProduct getAccessoryPriceDetails(BundleDeviceAndProductsList bundleDeviceAndProductsList,RegistryClient registryClient) {
		RestTemplate restTemplate =registryClient.getRestTemplate();
		PriceForProduct client;
		try{
		 client=restTemplate.postForObject("http://PRICE-V1/price/product" ,bundleDeviceAndProductsList,PriceForProduct.class);
		}catch(Exception e){
			LogHelper.error(CommonUtility.class, "getAccessoryPriceDetails API Exception---------------"+e);
			throw new ApplicationException(ExceptionMessages.PRICING_API_EXCEPTION);
		}
 		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<PriceForProduct>(){});
		
	}
	public static List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> getPriceDetailsUsingBundleHarwareTrouple(List<BundleAndHardwareTuple> bundleAndHardwareTupleList,String offerCode,String journeyType,RegistryClient registryClient) {
		List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> priceList=null;
		try {
			RestTemplate restTemplate =registryClient.getRestTemplate();
			RequestForBundleAndHardware requestForBundleAndHardware=new RequestForBundleAndHardware();
			requestForBundleAndHardware.setBundleAndHardwareList(bundleAndHardwareTupleList);
			requestForBundleAndHardware.setOfferCode(offerCode);
			requestForBundleAndHardware.setPackageType(journeyType);
			com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] client=restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware" ,requestForBundleAndHardware,com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
			ObjectMapper mapper = new ObjectMapper();
			priceList= mapper.convertValue(client, new TypeReference<List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>(){});
		} catch (Exception e) {
			LogHelper.error(CommonUtility.class, ""+e);
			throw new ApplicationException(ExceptionMessages.PRICING_API_EXCEPTION);
		}
		return priceList;
	}
	
	/**
	 * Returns bundle Id from customer subscription.
	 * @param subscriptionId
	 * @param subscriptionType
	 * @param registryClient
	 * @return
	 */
	public static String getSubscriptionBundleId(String subscriptionId, String subscriptionType, RegistryClient registryClient) {
		
		String bundleId = null;

		try {
			String url = "http://CUSTOMER-V1/customer/subscription/" + subscriptionType + ":" + subscriptionId;
			RestTemplate restTemplate = registryClient.getRestTemplate();
			Subscription subAsset = restTemplate.getForObject(url, Subscription.class);
			
			if (null != subAsset) {
			
				List<Asset> assetList = subAsset.getAsset(); 
		
				if (assetList != null && !assetList.isEmpty()) {
					LogHelper.info(CommonUtility.class, "Subscriptions found for msisdn " + subscriptionId  + " : " + assetList.size());
					
					if (ConfigHelper.getString("ROOT_INSTALLED_PRODUCT_IDS_MPS", "100000, 110286").contains(subAsset.getProductId())) {
					
						for (Asset asset : assetList) {
							if (null != asset && null != asset.getProductId()) {
								String privateInstProdId = asset.getPrivateInstalledProductId();
								String privateRootInstProdId = asset.getPrivateRootInstalledProductId();
								String privateParentInstProdId = asset.getPrivateParentInstalledProductId();
								String promotionId = asset.getPromotionId();
								String serialNum = asset.getSerialNumber();
								
								if (null != privateInstProdId && null != privateRootInstProdId && null == privateParentInstProdId
										&& privateInstProdId.equals(privateRootInstProdId)
										&& Constants.SUBSCRIPTION_TYPE_MSISDN.equalsIgnoreCase(subscriptionType)
										&& null != promotionId && null == serialNum) {
									
										bundleId = asset.getProductId();
										LogHelper.info(CommonUtility.class, "BundleId found for subscription " + subscriptionId  + " : " + bundleId);
										return bundleId;
								}
							}
						}
					} else {
						LogHelper.info(CommonUtility.class, "Unable to find mobile subscription for subscriptionId" + subscriptionId);
					}
				} else {
					LogHelper.info(CommonUtility.class, "Unable to get Assets from subscription for subscriptionId" + subscriptionId);
				}
			} else {
				LogHelper.info(CommonUtility.class, "Unable to get Subscriptions from GetSubscriptionbyMSISDN servcie for subscriptionId" + subscriptionId);
			}
		
		}catch(Exception e){
			LogHelper.error(CommonUtility.class, "getBundleId API failed to get bundle from customer subscription : "+e);
		}
		LogHelper.warn(CommonUtility.class, "BundleId not found for subscription " + subscriptionId);
		return null;

	}
	
	
	public static String getpriceFormat(Float price) {
		String formatedPrice = null;
		String decimalFormat = "#.00";
		if (price != null) {
			DecimalFormat deciFormat = new DecimalFormat(decimalFormat);
			Double tmpPrice = price - Math.floor(price);
			if (tmpPrice.toString().equals("0.0")) {
				formatedPrice = String.valueOf(price.intValue());
			} else {
				formatedPrice = deciFormat.format(price);
			}
		}
		return formatedPrice;
	}
	/**
	 * Date validation
	 * @author manoj.bera
	 * @param startDateTime
	 * @param endDateTime
	 * @return flag 
	 */
	public static Boolean dateValidationForOffers(String startDateTime, String endDateTime, String strDateFormat) {
		
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		Date currentDate = new Date();
		
		String currentDateStr = dateFormat.format(currentDate);		
		
		try {
			currentDate = dateFormat.parse(currentDateStr);
			
		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(CommonUtility.class, "ParseException: " + e);
		}	
		
		Date startDate = null;
		Date endDate = null;

		try {
			if (startDateTime != null) {
				startDate = dateFormat.parse(startDateTime);
				LogHelper.info(CommonUtility.class, "::::: StartDate " + startDate + " :::::");
			}
			
		} catch (ParseException | DateTimeParseException e) {
			LogHelper.error(CommonUtility.class, "ParseException: " + e);
		}	
		
		try{
			if (endDateTime != null) {
				endDate = dateFormat.parse(endDateTime);
				LogHelper.info(CommonUtility.class, "::::: EndDate " + endDate + " :::::");
			}
		}catch (ParseException | DateTimeParseException e) {
			LogHelper.error(CommonUtility.class, "ParseException: " + e);
		}

		if (startDate != null && endDate != null && ((currentDate.after(startDate) || currentDate.equals(startDate))
				&& (currentDate.before(endDate) || currentDate.equals(endDate)))) {			
				flag = true;			
		}
		if (startDate == null && endDate != null && currentDate.before(endDate)) {
			flag = true;
		}
		if (startDate != null && endDate == null && currentDate.after(startDate)) {
			flag = true;
		}
		if (startDate == null && endDate == null) {
			flag = true;
		}

		return flag;
	}
	public static boolean isProductNotExpired(CommercialProduct commercialProduct) {
		boolean isProductExpired = false;
		String startDateTime = null;
		String endDateTime = null;
		if (commercialProduct.getProductAvailability().getStart() != null) {
			startDateTime = getDateToString(commercialProduct
					.getProductAvailability().getStart(),
					Constants.DATE_FORMAT_COHERENCE);
		}
		if (commercialProduct.getProductAvailability().getEnd() != null) {
			endDateTime = getDateToString(commercialProduct
					.getProductAvailability().getEnd(),
					Constants.DATE_FORMAT_COHERENCE);
		}
		if (!commercialProduct.getProductAvailability().isSalesExpired()) {
			
			isProductExpired = dateValidationForOffers(startDateTime,
					endDateTime, Constants.DATE_FORMAT_COHERENCE);

		}
		return isProductExpired;

	}

	public static boolean isProductJourneySpecific(
			CommercialProduct commercialProduct, String journeyType) {
		boolean isProductJourneySpecific = false;
		if (StringUtils.isNotBlank(journeyType)
				&& Constants.JOURNEYTYPE_UPGRADE.equalsIgnoreCase(journeyType)) {
			if (commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl().isIsSellableRet()
					&& commercialProduct.getProductControl()
							.isIsDisplayableRet()) {

				isProductJourneySpecific = true;
			}
		} else {
			if (commercialProduct.getProductControl() != null
					&& commercialProduct.getProductControl()
							.isIsDisplayableAcq()
					&& commercialProduct.getProductControl().isIsSellableAcq()) {

				isProductJourneySpecific = true;

			}
		}
		return isProductJourneySpecific;

	}
}
