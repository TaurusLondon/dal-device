package com.vf.uk.dal.device.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.entity.SourcePackageSummary;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleAndHardwareRequest;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareAccessory;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareDataAllowances;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareExtras;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSash;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForBundleAndHardwareSecureNet;
import com.vf.uk.dal.utility.entity.CataloguepromotionqueriesForHardwareSash;
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
		PriceForBundleAndHardware[] client = new PriceForBundleAndHardware[7000];;
		try {
			LogHelper.info(CommonUtility.class, "Start --> Calling  Price.calculateForBundleAndHardware");
			client = restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
					requestForBundleAndHardware, PriceForBundleAndHardware[].class);
			LogHelper.info(CommonUtility.class, "End --> Calling  Price.calculateForBundleAndHardware");
		} catch (Exception e) {
			LogHelper.error(CommonUtility.class, "PRICE API of PriceForBundleAndHardware Exception---------------"+e);
			throw new ApplicationException(ExceptionMessages.PRICING_API_EXCEPTION);
		}
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
	/*public static CurrentJourney getCurrentJourney(String journeyId,RegistryClient registryClient) {
		RestTemplate restTemplate =registryClient.getRestTemplate();
		return restTemplate.getForObject("http://COMMON-V1/common/journey/"+journeyId+"/queries/currentJourney" ,CurrentJourney.class);
	}*/
	
	public static RecommendedProductListResponse getRecommendedProductList(RecommendedProductListRequest recomProductList,RegistryClient registryClient) {
		RestTemplate restTemplate =registryClient.getRestTemplate();
		return restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",recomProductList,RecommendedProductListResponse.class);
	}
	
	public static BundleDetailsForAppSrv getPriceDetailsForCompatibaleBundle(String deviceId,RegistryClient registryClient) {
		try {
			LogHelper.info(CommonUtility.class, "Start --> Calling  Bundle.getCoupledBundleList");
			RestTemplate restTemplate =registryClient.getRestTemplate();
			return restTemplate.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=" +deviceId, BundleDetailsForAppSrv.class );
		} catch (Exception e) {
			LogHelper.error(CommonUtility.class, ""+e);
			throw new ApplicationException(ExceptionMessages.COUPLEBUNDLELIST_API_EXCEPTION);
			//return null;
		}
	}
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
		LogHelper.info(CommonUtility.class, "Start -->  calling  Bundle.GetCompatibleListAPI");
		String URL = "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byDeviceId/" + deviceId + "/";
		if (sortCriteria != null && StringUtils.isNotBlank(sortCriteria)) {
			URL += "/?sort=" + sortCriteria;
		}
		BundleDetails client=new BundleDetails();
		try{
		 client = restTemplate.getForObject(URL, BundleDetails.class);
		 LogHelper.info(CommonUtility.class, "End -->  calling  Bundle.GetCompatibleListAPI");
		} 
		catch(Exception e){
			LogHelper.error(CommonUtility.class, "getBundleDetailsFromGetCompatibleListAPI API Exception---------------"+e);
			throw new ApplicationException(ExceptionMessages.BUNDLECOMPATIBLELIST_API_EXCEPTION);
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
			LogHelper.info(CommonUtility.class, "Start -->  calling  Price.product");
		 client=restTemplate.postForObject("http://PRICE-V1/price/product" ,bundleDeviceAndProductsList,PriceForProduct.class);
		 LogHelper.info(CommonUtility.class, "End -->  calling  Price.product");
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
			LogHelper.info(CommonUtility.class, "Start --> Calling  Price.calculateForBundleAndHardware");
			com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] client=restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware" ,requestForBundleAndHardware,com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
			LogHelper.info(CommonUtility.class, "End --> Calling  Price.calculateForBundleAndHardware");
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
			String url = "http://CUSTOMER-V1/customer/subscription/" + subscriptionType + ":" + subscriptionId + "/sourcePackageSummary";
			RestTemplate restTemplate = registryClient.getRestTemplate();
			SourcePackageSummary sourcePackageSummary = restTemplate.getForObject(url, SourcePackageSummary.class);
			
			if (null != sourcePackageSummary && null != sourcePackageSummary.getPromotionId()){
				bundleId = sourcePackageSummary.getPromotionId();
			
				if (StringUtils.isBlank(bundleId))
					LogHelper.info(CommonUtility.class, "No bundleId retrived from getSubscriptionAPI for the given MSISDN");
			}
			 else {
				LogHelper.info(CommonUtility.class, "Unable to get Subscriptions from GetSubscriptionbyMSISDN servcie for subscriptionId" + subscriptionId);
			}
		
		}catch(Exception e){
			LogHelper.error(CommonUtility.class, "getBundleId API failed to get bundle from customer subscription : "+e);
		}
		return bundleId;

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
	/**
	 * Gets the promotions for bundle and hardware.
	 *
	 * @param bundleHardwareTupleList
	 *            the bundle hardwaretuple list
	 * @param registryClient
	 *            the registry client
	 * @return the promotions for bundle and hardware
	 * @author manoj.bera
	 * @SPRINT 6.4
	 */
	public static List<BundleAndHardwarePromotions> getPromotionsForBundleAndHardWarePromotions(List<BundleAndHardwareTuple> bundleHardwareTupleList , RegistryClient registryClient)
	{
		RestTemplate restTemplate = registryClient.getRestTemplate();
		BundleAndHardwareRequest request =new BundleAndHardwareRequest();
		request.setBundleAndHardwareList(bundleHardwareTupleList);
		BundleAndHardwarePromotions[] response = null;
		try {
			
			LogHelper.info(CommonUtility.class,"http://PROMOTION-V1/promotion/queries/ForBundleAndHardware------POST URL\n"+"PayLoad\n Start calling");
			response = restTemplate.postForObject("http://PROMOTION-V1/promotion/queries/ForBundleAndHardware",
					request, BundleAndHardwarePromotions[].class);
		} catch (RestClientException e) {
			// Stanley - Added error logging
			LogHelper.error(CommonUtility.class, e+"");
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(response,
				new TypeReference<List<BundleAndHardwarePromotions>>() {
				});
	}
	/**
	 * 
	 * @param entertainmentPacks
	 * @param dataAllowances
	 * @param planCouplingPromotions
	 * @param sash
	 * @param secureNet
	 * @return
	 * @author manoj.bera
	 * @SPRINT 6.4
	 */
	public static List<MediaLink> getMediaListForBundleAndHardware(
			List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks,
			List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances,
			List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions,
			List<CataloguepromotionqueriesForBundleAndHardwareSash> sash,
			List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet,
			List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware,
			List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras,
			 List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessories,
			 List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlans,
			 List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlans,
			 List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardwares,
			 List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardwares) {
		List<MediaLink> mediaList = new ArrayList<>();
		if (freeAccForHardwares != null && !freeAccForHardwares.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForHardware : freeAccForHardwares) 
			{
				if (StringUtils.isNotBlank(freeAccForHardware.getLabel()))
				{
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccForHardware.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeAccForHardware.getLabel());
					if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(freeAccForHardware.getDescription())) 
				{
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccForHardware.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeAccForHardware.getDescription());
					if (StringUtils.isNotBlank(freeAccForHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccForHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (freeExtrasForHardwares != null && !freeExtrasForHardwares.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForHardware : freeExtrasForHardwares) 
			{
				if (StringUtils.isNotBlank(freeExtrasForHardware.getLabel()))
				{
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtrasForHardware.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeExtrasForHardware.getLabel());
					if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(freeExtrasForHardware.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtrasForHardware.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeExtrasForHardware.getDescription());
					if (StringUtils.isNotBlank(freeExtrasForHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (freeAccForPlans != null && !freeAccForPlans.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccForPlan : freeAccForPlans) {
				if (StringUtils.isNotBlank(freeAccForPlan.getLabel())){
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccForPlan.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeAccForPlan.getLabel());
					if (StringUtils.isNotBlank(freeAccForPlan.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccForPlan.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(freeAccForPlan.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccForPlan.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeAccForPlan.getDescription());
					if (StringUtils.isNotBlank(freeAccForPlan.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccForPlan.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (freeExtrasForPlans != null && !freeExtrasForPlans.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasForPlan : freeExtrasForPlans) {
				if (StringUtils.isNotBlank(freeExtrasForPlan.getLabel())){
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtrasForPlan.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeExtrasForPlan.getLabel());
					if (StringUtils.isNotBlank(freeExtrasForPlan.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForPlan.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(freeExtrasForPlan.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtrasForPlan.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeExtrasForPlan.getDescription());
					if (StringUtils.isNotBlank(freeExtrasForPlan.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtrasForPlan.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (freeAccessories != null && !freeAccessories.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccessory : freeAccessories) {
				if (StringUtils.isNotBlank(freeAccessory.getLabel())){
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccessory.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeAccessory.getLabel());
					if (StringUtils.isNotBlank(freeAccessory.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccessory.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(freeAccessory.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeAccessory.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeAccessory.getDescription());
					if (StringUtils.isNotBlank(freeAccessory.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeAccessory.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (freeExtras != null && !freeExtras.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareExtras freeExtra : freeExtras) {
				if (StringUtils.isNotBlank(freeExtra.getLabel())){
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtra.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeExtra.getLabel());
					if (StringUtils.isNotBlank(freeExtra.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtra.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(freeExtra.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(freeExtra.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(freeExtra.getDescription());
					if (StringUtils.isNotBlank(freeExtra.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(freeExtra.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (sashBannerForHardware != null && !sashBannerForHardware.isEmpty()) {
			for (CataloguepromotionqueriesForHardwareSash sashBannerHardware : sashBannerForHardware) {
				if (StringUtils.isNotBlank(sashBannerHardware.getLabel())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(sashBannerHardware.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(sashBannerHardware.getLabel());
					if (StringUtils.isNotBlank(sashBannerHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(sashBannerHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(sashBannerHardware.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(sashBannerHardware.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(sashBannerHardware.getDescription());
					if (StringUtils.isNotBlank(sashBannerHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(sashBannerHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(sashBannerHardware.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(sashBannerHardware.getType() + "." + Constants.STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(Constants.STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(sashBannerHardware.getPromotionMedia());
					if (StringUtils.isNotBlank(sashBannerHardware.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(sashBannerHardware.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (entertainmentPacks != null && !entertainmentPacks.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainment : entertainmentPacks) {
				if (StringUtils.isNotBlank(entertainment.getLabel())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(entertainment.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(entertainment.getLabel());
					if (StringUtils.isNotBlank(entertainment.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(entertainment.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(entertainment.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(entertainment.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(entertainment.getDescription());
					if (StringUtils.isNotBlank(entertainment.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(entertainment.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(entertainment.getPromotionMedia())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(entertainment.getType() + "." + Constants.STRING_PROMOTION_MEDIA);
					mediaOfferLink.setType(Constants.STRING_URL_ALLOWANCE);
					mediaOfferLink.setValue(entertainment.getPromotionMedia());
					if (StringUtils.isNotBlank(entertainment.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(entertainment.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (dataAllowances != null && !dataAllowances.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowance : dataAllowances) {
				if (StringUtils.isNotBlank(dataAllowance.getLabel())){
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(dataAllowance.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(dataAllowance.getLabel());
					if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(dataAllowance.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(dataAllowance.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(dataAllowance.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(dataAllowance.getDescription());
					if (StringUtils.isNotBlank(dataAllowance.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(dataAllowance.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (planCouplingPromotions != null && !planCouplingPromotions.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions planCouplingPromotion : planCouplingPromotions) {
				if (StringUtils.isNotBlank(planCouplingPromotion.getLabel())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(planCouplingPromotion.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(planCouplingPromotion.getLabel());
					if (StringUtils.isNotBlank(planCouplingPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(planCouplingPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(planCouplingPromotion.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(planCouplingPromotion.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(planCouplingPromotion.getDescription());
					if (StringUtils.isNotBlank(planCouplingPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(planCouplingPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		if (sash != null && !sash.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareSash sashPromotion : sash) {
				if (StringUtils.isNotBlank(sashPromotion.getLabel())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(sashPromotion.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(sashPromotion.getLabel());
					if (StringUtils.isNotBlank(sashPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(sashPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(sashPromotion.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(sashPromotion.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(sashPromotion.getDescription());
					if (StringUtils.isNotBlank(sashPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(sashPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		
		if (secureNet != null && !secureNet.isEmpty()) {
			for (CataloguepromotionqueriesForBundleAndHardwareSecureNet secureNetPromotion : secureNet) {
				if (StringUtils.isNotBlank(secureNetPromotion.getLabel())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(secureNetPromotion.getType() + "." + Constants.STRING_OFFERS_LABEL);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(secureNetPromotion.getLabel());
					if (StringUtils.isNotBlank(secureNetPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(secureNetPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
				if (StringUtils.isNotBlank(secureNetPromotion.getDescription())) {
					MediaLink mediaOfferLink = new MediaLink();
					mediaOfferLink.setId(secureNetPromotion.getType() + "." + Constants.STRING_OFFERS_DESCRIPTION);
					mediaOfferLink.setType(Constants.STRING_TEXT_ALLOWANCE);
					mediaOfferLink.setValue(secureNetPromotion.getDescription());
					if (StringUtils.isNotBlank(secureNetPromotion.getPriority())) {
						mediaOfferLink.setPriority(Integer.valueOf(secureNetPromotion.getPriority()));
					}
					mediaList.add(mediaOfferLink);
				}
			}
		}
		
		return mediaList;
	}
	/**	
	 * @author manoj.bera
	 * @return
	 */
	public static ExecutorService getThreadPool() {
		int threadNum = 20;
		return Executors.newFixedThreadPool(threadNum);
	}
}
