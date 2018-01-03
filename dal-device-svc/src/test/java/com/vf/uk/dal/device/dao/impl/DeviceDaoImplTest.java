/*package com.vf.uk.dal.device.dao.impl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import com.vf.uk.dal.common.context.ServiceContext;
import com.vf.uk.dal.common.context.URLParamContext;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.common.registry.client.Utility;
import com.vf.uk.dal.common.urlparams.FilterCriteria;
import com.vf.uk.dal.common.urlparams.FilterOperator;
import com.vf.uk.dal.common.urlparams.PaginationCriteria;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.KeepDeviceChangePlanRequest;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.DeviceTileCacheDAO;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.product.pojo.CommercialProduct;

*//**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)

public class DeviceDaoImplTest {
	
	 * @MockBean DeviceDao deviceDAOMock;
	 

	@Autowired
	DeviceDaoImpl deviceDaoImpl;

	@MockBean
	EurekaClient eureka;

	@MockBean
	RegistryClient registry;

	@MockBean
	RestTemplate restTemplate;

	@MockBean
	JdbcTemplate jdbcTemplate;

	@MockBean
	DataSource datasource;

	@Before
	public void setupMockBehaviour() throws Exception {
		String jsonString = new String(Utility.readFile("\\rest-mock\\COMMON-V1.json"));
		CurrentJourney obj = new ObjectMapper().readValue(jsonString, CurrentJourney.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.getForObject(
				"http://COMMON-V1/common/journey/" + "c1a42269-6562-4c96-b3be-1ca2a6681d57" + "/queries/currentJourney",
				CurrentJourney.class)).willReturn(obj);

		List<String> listOfRecommendedProductTypes;
		RecommendedProductListRequest recomProductListReq = new RecommendedProductListRequest();
		listOfRecommendedProductTypes = new ArrayList<>();
		listOfRecommendedProductTypes.add(Constants.STRING_DEVICE);
		recomProductListReq.setSerialNumber("07003145001");
		recomProductListReq.setRecommendedProductTypes(listOfRecommendedProductTypes);
		String jsonString1 = new String(Utility.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/", recomProductListReq,
				RecommendedProductListResponse.class)).willReturn(obj1);
	}

	private DeviceSummary getDeviceSummary() {
		DeviceSummary deviceSummary = new DeviceSummary();

		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		BundlePrice bundlePrice = new BundlePrice();
		Price price = new Price();
		price.setGross("100");
		price.setNet("80");
		price.setVat("20");
		bundlePrice.setMonthlyPrice(price);
		priceForBundleAndHardware.setBundlePrice(bundlePrice);

		deviceSummary.setPriceInfo(priceForBundleAndHardware);

		return deviceSummary;
	}

	@Test
	public void testIsPlanAffordableComBundleNull() {
		DeviceSummary deviceSummary = getDeviceSummary();
		deviceDaoImpl.isPlanAffordable(deviceSummary, null, new Double(60.00), true);
		Assert.assertFalse(deviceSummary.getIsAffordable());
	}
	
	@Test
	public void testIsPlanAffordableComBundleNotNullNotAffordable() {
		DeviceSummary deviceSummary = getDeviceSummary();
		deviceDaoImpl.isPlanAffordable(deviceSummary, new CommercialBundle(), new Double(60.00), true);
		Assert.assertFalse(deviceSummary.getIsAffordable());
	}
	
	@Test
	public void testIsPlanAffordableComBundleNotNullAffordable() {
		DeviceSummary deviceSummary = getDeviceSummary();
		deviceDaoImpl.isPlanAffordable(deviceSummary, new CommercialBundle(), new Double(110.00), true);
		Assert.assertTrue(deviceSummary.getIsAffordable());
	}
	
}
*/