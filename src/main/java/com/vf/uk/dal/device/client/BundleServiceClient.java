package com.vf.uk.dal.device.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BundleServiceClient {

	@Autowired
	RestTemplate restTemplate;
	
	
	/**
	 * Gets the bundle details from complans listing API.
	 *
	 * @param deviceId
	 *            the device id
	 * @param registryClient
	 *            the registry client
	 * @return the bundle details from complans listing API
	 */
	public BundleDetails getBundleDetailsFromComplansListingAPI(String deviceId, String sortCriteria) {
		log.info("Start -->  calling  Bundle.GetCompatibleListAPI");
		String URL = "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byDeviceId/" + deviceId + "/";
		if (sortCriteria != null && StringUtils.isNotBlank(sortCriteria)) {
			URL += "/?sort=" + sortCriteria;
		}
		BundleDetails client = new BundleDetails();
		try {
			client = restTemplate.getForObject(URL, BundleDetails.class);
			log.info("End -->  calling  Bundle.GetCompatibleListAPI");
		} catch (Exception e) {
			log.error("getBundleDetailsFromGetCompatibleListAPI API Exception---------------" + e);
			throw new ApplicationException(ExceptionMessages.BUNDLECOMPATIBLELIST_API_EXCEPTION);
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(client, new TypeReference<BundleDetails>() {
		});
	}
	
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param registryClient
	 * @return BundleDetailsForAppSrv
	 */
	public BundleDetailsForAppSrv getPriceDetailsForCompatibaleBundle(String deviceId, String journeyType) {
		try {
			log.info("Start --> Calling  Bundle.getCoupledBundleList");
			return restTemplate
					.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
							+ deviceId + "&journeyType=" + journeyType, BundleDetailsForAppSrv.class);
		} catch (Exception e) {
			log.error("" + e);
			throw new ApplicationException(ExceptionMessages.COUPLEBUNDLELIST_API_EXCEPTION);
			// return null;
		}
	}
}
