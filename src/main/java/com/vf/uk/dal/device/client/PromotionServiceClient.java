package com.vf.uk.dal.device.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwareRequest;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PromotionServiceClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	public List<BundleAndHardwarePromotions> getPromotionsForBundleAndHardWarePromotions(BundleAndHardwareRequest request)
	{
		BundleAndHardwarePromotions[] response = null;
		try {
			log.info("http://PROMOTION-V1/promotion/queries/ForBundleAndHardware------POST URL\n"
					+ "PayLoad\n Start calling");
			response = restTemplate.postForObject("http://PROMOTION-V1/promotion/queries/ForBundleAndHardware", request,
					BundleAndHardwarePromotions[].class);
		} catch (RestClientException e) {
			// Stanley - Added error logging
			log.error(e + "");
			throw new ApplicationException(ExceptionMessages.PROMOTION_API_EXCEPTION);
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(response, new TypeReference<List<BundleAndHardwarePromotions>>() {
		});
	}
}
