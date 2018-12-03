package com.vf.uk.dal.device.beans.test;

import org.springframework.context.annotation.Bean;

import com.vf.uk.dal.common.beans.Environment;
import com.vf.uk.dal.common.configuration.DataSourceInitializer;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.client.BundleServiceClient;
import com.vf.uk.dal.device.client.CustomerServiceClient;
import com.vf.uk.dal.device.client.PriceServiceClient;
import com.vf.uk.dal.device.client.PromotionServiceClient;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.controller.DeviceEntityController;
import com.vf.uk.dal.device.controller.DeviceMakeAndModelController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceDaoImpl;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAOImpl;
import com.vf.uk.dal.device.service.AccessoryInsuranceServiceImpl;
import com.vf.uk.dal.device.service.CacheDeviceServiceImpl;
import com.vf.uk.dal.device.service.DeviceDetailsServiceImpl;
import com.vf.uk.dal.device.service.DeviceEntityServiceImpl;
import com.vf.uk.dal.device.service.DeviceMakeAndModelServiceImpl;
import com.vf.uk.dal.device.service.DeviceRecommendationService;
import com.vf.uk.dal.device.service.DeviceRecommendationServiceImpl;
import com.vf.uk.dal.device.service.DeviceServiceImpl;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceConditionallHelper;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ElasticSearchUtils;

 public class DeviceTestBeans {
	// ** Bean for the controller class is created here **//*
	
	@Bean public DeviceController getDeviceController() {
		return new DeviceController();
	}

	
	@Bean public DeviceEntityController getDeviceEntityController() {
		return new DeviceEntityController();
	}

	
	@Bean public AccessoryInsuranceController getAccessoryInsuranceController() {
		return new AccessoryInsuranceController();
	}

	
	@Bean public DeviceServiceImpl getDeviceServiceImpl() {
		return new DeviceServiceImpl();
	}

	
	@Bean public DataSourceInitializer dataSourceInitializer() {
		return new DataSourceInitializer();
	}
	
	@Bean public DeviceServiceImplUtility getDeviceServiceImplUtility() {
		return new DeviceServiceImplUtility();
	}
	
	@Bean public CommonUtility getCommonUtility() {
		return new CommonUtility();
	}
	
	@Bean public DeviceUtils getDeviceUtils(){
		return new DeviceUtils();
	}
	
	@Bean public Environment getEnvironment() {
		return new Environment();
	}

	
	@Bean public DeviceRecommendationService getDeviceRecommendationServiceImpl() {
		return new DeviceRecommendationServiceImpl();
	}

	
	@Bean public DeviceDao getDeviceDaoImpl() {
		return new DeviceDaoImpl();
	}
	
	@Bean public ResponseMappingHelper getResponseMappingHelper() {
		return new ResponseMappingHelper();
	}

	
	@Bean public ElasticSearchUtils getElasticSearchUtils() {
		return new ElasticSearchUtils();
	}

	
	@Bean public CatalogServiceAspect getCatalogServiceAspect() {
		return new CatalogServiceAspect();
	}

	
	@Bean public DeviceTileCacheDAOImpl getDeviceTileCacheDAOImpl() {
		return new DeviceTileCacheDAOImpl();
	}

	
	@Bean public DeviceESHelper getDeviceESHelper() {
		return new DeviceESHelper();
	}

	
	@Bean public DeviceConditionallHelper getDeviceConditionallHelper() {
		return new DeviceConditionallHelper();
	}

	
	@Bean public CacheDeviceServiceImpl getCacheDeviceServiceImpl() {
		return new CacheDeviceServiceImpl();
	}

	
	@Bean public DeviceDetailsServiceImpl getDeviceDetailsServiceImpl() {
		return new DeviceDetailsServiceImpl();
	}

	
	@Bean public DeviceEntityServiceImpl getDeviceEntityServiceImpl() {
		return new DeviceEntityServiceImpl();
	}

	
	@Bean public DeviceMakeAndModelServiceImpl getDeviceMakeAndModelServiceImpl() {
		return new DeviceMakeAndModelServiceImpl();
	}

	
	@Bean public DeviceServiceCommonUtility getDeviceServiceCommonUtility() {
		return new DeviceServiceCommonUtility();
	}

	
	@Bean public AccessoryInsuranceServiceImpl getAccessoryInsuranceServiceImpl() {
		return new AccessoryInsuranceServiceImpl();
	}

	
	@Bean public DeviceMakeAndModelController getDeviceMakeAndModelController() {
		return new DeviceMakeAndModelController();
	}

	
	@Bean public DeviceDetailsController getDeviceDetailsController() {
		return new DeviceDetailsController();
	}

	
	@Bean public CacheDeviceAndReviewController getCacheDeviceAndReviewController() {
		return new CacheDeviceAndReviewController();
	}
	
	@Bean public BundleServiceClient getBundleServiceClient()
	{
		return new BundleServiceClient();
	}
	
	@Bean public CustomerServiceClient getCustomerServiceClient()
	{
		return new CustomerServiceClient();
	}
	
	@Bean public PriceServiceClient getPriceServiceClient()
	{
		return new PriceServiceClient();
	}
	
	@Bean public PromotionServiceClient getPromotionServiceClient()
	{
		return new PromotionServiceClient();
	}
	
}