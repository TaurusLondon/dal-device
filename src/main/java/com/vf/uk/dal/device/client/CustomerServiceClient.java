package com.vf.uk.dal.device.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListRequest;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.client.entity.customer.SourcePackageSummary;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerServiceClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * 
	 * @param recomProductList
	 * @param registryClient
	 * @return RecommendedProductListResponse
	 */
	public RecommendedProductListResponse getRecommendedProductList(RecommendedProductListRequest recomProductList) {
		return restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/", recomProductList,
				RecommendedProductListResponse.class);
	}
	
	/**
	 * Returns bundle Id from customer subscription.
	 * 
	 * @param subscriptionId
	 * @param subscriptionType
	 * @param registryClient
	 * @return SubscriptionBundleId
	 */
	public String getSubscriptionBundleId(String subscriptionId, String subscriptionType) {

		String bundleId = null;

		try {
			String url = "http://CUSTOMER-V1/customer/subscription/" + subscriptionType + ":" + subscriptionId
					+ "/sourcePackageSummary";
			SourcePackageSummary sourcePackageSummary = restTemplate.getForObject(url, SourcePackageSummary.class);

			if (null != sourcePackageSummary && null != sourcePackageSummary.getPromotionId()) {
				bundleId = sourcePackageSummary.getPromotionId();

				if (StringUtils.isBlank(bundleId))
					log.info("No bundleId retrived from getSubscriptionAPI for the given MSISDN");
			} else {
				log.info("Unable to get Subscriptions from GetSubscriptionbyMSISDN servcie for subscriptionId"
						+ subscriptionId);
			}

		} catch (Exception e) {
			log.error("getBundleId API failed to get bundle from customer subscription : " + e);
		}
		return bundleId;

	}

}
