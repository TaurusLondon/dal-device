package com.vf.uk.dal.device.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vf.uk.dal.device.client.entity.catalogue.DeviceEntityModel;
import com.vf.uk.dal.device.exception.DeviceCustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CatalogueServiceClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	public DeviceEntityModel getDeviceEntityService(String make, String model, String groupType, String deviceId,
			String packageType, String sort, Integer pageNumber, Integer pageSize, String color, String operatingSystem,
			String capacity, String mustHaveFeatures) {

		try {
			log.info("Start --> Calling Device Entity Service");
			DeviceEntityModel client;
			int pageNumberLocal = pageNumber == null ? 0 : pageNumber;
			int pageSizeLocal = pageSize == null ? 20 : pageSize;
			client = restTemplate.getForObject("http://CATALOGUE-V1/productCatalog/device?make=" + make + "&model="
					+ model + "&groupType=" + groupType + "&deviceId=" + deviceId + "&packageType=" + packageType
					+ "&sort=" + sort + "&pageNumber=" + pageNumberLocal + "&pageSize=" + pageSizeLocal + "&color="
					+ color + "&operatingSystem=" + operatingSystem + "&capacity=" + capacity + "&mustHaveFeatures="
					+ mustHaveFeatures, DeviceEntityModel.class);
			return client;
		} catch (Exception e) {
			log.error("Device Entity Service Exception--------------- {}", e);
			throw new DeviceCustomException("500", "No response from Device Entity Service", "500");
		}
	}
}
