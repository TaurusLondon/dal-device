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
	
	public DeviceController getDeviceController() {
		return new DeviceController();
	}

	
	public DeviceEntityController getDeviceEntityController() {
		return new DeviceEntityController();
	}

	
	public AccessoryInsuranceController getAccessoryInsuranceController() {
		return new AccessoryInsuranceController();
	}

	
	public DeviceServiceImpl getDeviceServiceImpl() {
		return new DeviceServiceImpl();
	}

	
	public DataSourceInitializer dataSourceInitializer() {
		return new DataSourceInitializer();
	}

	
	public RegistryClient getRegistryClient() {
		return new RegistryClient();
	}

	
	public Environment getEnvironment() {
		return new Environment();
	}

	
	public DeviceRecommendationService getDeviceRecommendationServiceImpl() {
		return new DeviceRecommendationServiceImpl();
	}

	
	public DeviceDao getDeviceDaoImpl() {
		return new DeviceDaoImpl();
	}

	
	public ConfigHelper getConfigHelper() {
		return new ConfigHelper();
	}

	
	public ResponseMappingHelper getResponseMappingHelper() {
		return new ResponseMappingHelper();
	}

	
	public ElasticSearchUtils getElasticSearchUtils() {
		return new ElasticSearchUtils();
	}

	
	public CatalogServiceAspect getCatalogServiceAspect() {
		return new CatalogServiceAspect();
	}

	
	public DeviceTileCacheDAOImpl getDeviceTileCacheDAOImpl() {
		return new DeviceTileCacheDAOImpl();
	}

	
	public DeviceESHelper getDeviceESHelper() {
		return new DeviceESHelper();
	}

	
	public DeviceConditionallHelper getDeviceConditionallHelper() {
		return new DeviceConditionallHelper();
	}

	
	public CacheDeviceServiceImpl getCacheDeviceServiceImpl() {
		return new CacheDeviceServiceImpl();
	}

	
	public DeviceDetailsServiceImpl getDeviceDetailsServiceImpl() {
		return new DeviceDetailsServiceImpl();
	}

	
	public DeviceEntityServiceImpl getDeviceEntityServiceImpl() {
		return new DeviceEntityServiceImpl();
	}

	
	public DeviceMakeAndModelServiceImpl getDeviceMakeAndModelServiceImpl() {
		return new DeviceMakeAndModelServiceImpl();
	}

	
	public DeviceServiceCommonUtility getDeviceServiceCommonUtility() {
		return new DeviceServiceCommonUtility();
	}

	
	public AccessoryInsuranceServiceImpl getAccessoryInsuranceServiceImpl() {
		return new AccessoryInsuranceServiceImpl();
	}

	
	public DeviceMakeAndModelController getDeviceMakeAndModelController() {
		return new DeviceMakeAndModelController();
	}

	
	public DeviceDetailsController getDeviceDetailsController() {
		return new DeviceDetailsController();
	}

	
	public CacheDeviceAndReviewController getCacheDeviceAndReviewController() {
		return new CacheDeviceAndReviewController();
	}
}