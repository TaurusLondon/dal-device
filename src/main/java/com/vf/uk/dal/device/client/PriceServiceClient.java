package com.vf.uk.dal.device.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundleDeviceAndProductsList;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.client.entity.price.RequestForBundleAndHardware;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PriceServiceClient {

	@Autowired
	RestTemplate restTemplate;
	private static final String ERROR_CODE_DEVICE = "error_device_failed";
	public List<PriceForBundleAndHardware> getPriceDetails(RequestForBundleAndHardware requestForBundleAndHardware) {
		PriceForBundleAndHardware[] client = new PriceForBundleAndHardware[7000];
		try {
			log.info("Start --> Calling  Price.calculateForBundleAndHardware");
			client = restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
					requestForBundleAndHardware, PriceForBundleAndHardware[].class);
			log.info("End --> Calling  Price.calculateForBundleAndHardware");
		} catch (Exception e) {
			log.error("PRICE API of PriceForBundleAndHardware Exception---------------" + e);
			throw new DeviceCustomException(ERROR_CODE_DEVICE,ExceptionMessages.PRICING_API_EXCEPTION,"404");
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<List<PriceForBundleAndHardware>>() {
		});
	}

	/**
	 * 
	 * @param bundleDeviceAndProductsList
	 * @param registryClient
	 * @return PriceForProduct
	 */
	public PriceForProduct getAccessoryPriceDetails(BundleDeviceAndProductsList bundleDeviceAndProductsList) {
		PriceForProduct client;
		try {
			log.info("Start -->  calling  Price.product");
			client = restTemplate.postForObject("http://PRICE-V1/price/product", bundleDeviceAndProductsList,
					PriceForProduct.class);
			log.info("End -->  calling  Price.product");
		} catch (Exception e) {
			log.error("getAccessoryPriceDetails API Exception---------------" + e);
			throw new DeviceCustomException(ERROR_CODE_DEVICE,ExceptionMessages.PRICING_API_EXCEPTION,"404");
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<PriceForProduct>() {
		});

	}

	/**
	 * 
	 * @param bundleAndHardwareTupleList
	 * @param offerCode
	 * @param journeyType
	 * @param registryClient
	 * @return List<PriceForBundleAndHardware>
	 */
	public List<PriceForBundleAndHardware> getPriceDetailsUsingBundleHarwareTrouple(
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList, String offerCode, String journeyType,
			String billingType) {
		List<PriceForBundleAndHardware> priceList = null;
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		try {
			RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
			requestForBundleAndHardware.setBillingType(billingType);
			requestForBundleAndHardware.setBundleAndHardwareList(bundleAndHardwareTupleList);
			requestForBundleAndHardware.setOfferCode(offerCode);
			requestForBundleAndHardware.setPackageType(journeyType);
			ObjectMapper mapper = new ObjectMapper();
			log.info("Start --> Calling  Price.calculateForBundleAndHardware journeyType " + journeyType + " OfferCode "
					+ offerCode + " Index Version " + CatalogServiceAspect.CATALOG_VERSION.get());
			/**
			 * Price API throwing timeout exception while calling PAYG price t
			 * handle that override timeout
			 */
			factory.setConnectTimeout(300000);
			restTemplate.setRequestFactory(factory);
			PriceForBundleAndHardware[] client = restTemplate.postForObject(
					"http://PRICE-V1/price/calculateForBundleAndHardware", requestForBundleAndHardware,
					PriceForBundleAndHardware[].class);
			log.info("End --> Calling  Price.calculateForBundleAndHardware");
			priceList = mapper.convertValue(client, new TypeReference<List<PriceForBundleAndHardware>>() {
			});
		} catch (Exception e) {
			log.error("" + e);
			throw new DeviceCustomException(ERROR_CODE_DEVICE,ExceptionMessages.PRICING_API_EXCEPTION,"404");
		} finally {
			factory.setConnectTimeout(61000);
			restTemplate.setRequestFactory(factory);
		}
		return priceList;
	}
}
