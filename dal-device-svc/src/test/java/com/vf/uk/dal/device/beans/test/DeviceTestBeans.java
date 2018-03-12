package com.vf.uk.dal.device.beans.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vf.uk.dal.common.beans.Environment;
import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.configuration.DataSourceInitializer;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.impl.DeviceDaoImpl;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.svc.impl.DeviceRecommendationServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceServiceImpl;
import com.vf.uk.dal.device.utils.DeviceServiceImplHelper;
import com.vf.uk.dal.device.utils.DeviceTileCacheDAO;
import com.vf.uk.dal.device.utils.ElasticSearchUtils;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;

@Configuration
public class DeviceTestBeans {
	//** Bean for the controller class is created here **//*
	@Bean
	public DeviceController getDeviceController() {
		return new DeviceController();
	}

	@Bean
	public DeviceService getDeviceServiceImpl() {
		return new DeviceServiceImpl();
	}
	
	@Bean
	public DeviceTileCacheDAO saveDataToCoherence() {
		return new DeviceTileCacheDAO();
	}
	
	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		return new DataSourceInitializer();
	}
	
	@Bean
	public RegistryClient getRegistryClient(){
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
	public DeviceServiceImplHelper getDeviceServiceImplHelper() {
		return new DeviceServiceImplHelper();
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
	public ResponseMappingHelper getResponseMappingHelper(){
		return new ResponseMappingHelper();
	}
	@Bean
	public ElasticSearchUtils getElasticSearchUtils(){
		return new ElasticSearchUtils();
	}
}