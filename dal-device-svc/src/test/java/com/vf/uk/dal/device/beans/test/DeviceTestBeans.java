package com.vf.uk.dal.device.beans.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.vf.uk.dal.common.beans.Environment;
import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.configuration.DataSourceInitializer;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.controller.DeviceEntityController;
import com.vf.uk.dal.device.controller.DeviceMakeAndModelController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.impl.DeviceDaoImpl;
import com.vf.uk.dal.device.dao.impl.DeviceTileCacheDAOImpl;
import com.vf.uk.dal.device.helper.DeviceConditionallHelper;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.impl.AccessoryInsuranceServiceImpl;
import com.vf.uk.dal.device.svc.impl.CacheDeviceServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceDetailsServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceEntityServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceMakeAndModelServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceRecommendationServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceServiceImpl;
import com.vf.uk.dal.device.utils.ElasticSearchUtils;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;

@TestConfiguration
public class DeviceTestBeans {
	// ** Bean for the controller class is created here **//*
	@Bean
	public DeviceController getDeviceController() {
		return new DeviceController();
	}

	@Bean
	public DeviceEntityController getDeviceEntityController() {
		return new DeviceEntityController();
	}

	@Bean
	public AccessoryInsuranceController getAccessoryInsuranceController() {
		return new AccessoryInsuranceController();
	}

	@Bean
	public DeviceServiceImpl getDeviceServiceImpl() {
		return new DeviceServiceImpl();
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		return new DataSourceInitializer();
	}

	@Bean
	public RegistryClient getRegistryClient() {
		return new RegistryClient();
	}

	@Bean
	public Environment getEnvironment() {
		return new Environment();
	}

	@Bean
	public DeviceRecommendationService getDeviceRecommendationServiceImpl() {
		return new DeviceRecommendationServiceImpl();
	}

	@Bean
	public DeviceDao getDeviceDaoImpl() {
		return new DeviceDaoImpl();
	}

	@Bean
	public ConfigHelper getConfigHelper() {
		return new ConfigHelper();
	}

	@Bean
	public ResponseMappingHelper getResponseMappingHelper() {
		return new ResponseMappingHelper();
	}

	@Bean
	public ElasticSearchUtils getElasticSearchUtils() {
		return new ElasticSearchUtils();
	}

	@Bean
	public CatalogServiceAspect getCatalogServiceAspect() {
		return new CatalogServiceAspect();
	}

	@Bean
	public DeviceTileCacheDAOImpl getDeviceTileCacheDAOImpl() {
		return new DeviceTileCacheDAOImpl();
	}

	@Bean
	public DeviceESHelper getDeviceESHelper() {
		return new DeviceESHelper();
	}

	@Bean
	public DeviceConditionallHelper getDeviceConditionallHelper() {
		return new DeviceConditionallHelper();
	}

	@Bean
	public CacheDeviceServiceImpl getCacheDeviceServiceImpl() {
		return new CacheDeviceServiceImpl();
	}

	@Bean
	public DeviceDetailsServiceImpl getDeviceDetailsServiceImpl() {
		return new DeviceDetailsServiceImpl();
	}

	@Bean
	public DeviceEntityServiceImpl getDeviceEntityServiceImpl() {
		return new DeviceEntityServiceImpl();
	}

	@Bean
	public DeviceMakeAndModelServiceImpl getDeviceMakeAndModelServiceImpl() {
		return new DeviceMakeAndModelServiceImpl();
	}

	@Bean
	public DeviceServiceCommonUtility getDeviceServiceCommonUtility() {
		return new DeviceServiceCommonUtility();
	}

	@Bean
	public AccessoryInsuranceServiceImpl getAccessoryInsuranceServiceImpl() {
		return new AccessoryInsuranceServiceImpl();
	}

	@Bean
	public DeviceMakeAndModelController getDeviceMakeAndModelController() {
		return new DeviceMakeAndModelController();
	}

	@Bean
	public DeviceDetailsController getDeviceDetailsController() {
		return new DeviceDetailsController();
	}

	@Bean
	public CacheDeviceAndReviewController getCacheDeviceAndReviewController() {
		return new CacheDeviceAndReviewController();
	}
}