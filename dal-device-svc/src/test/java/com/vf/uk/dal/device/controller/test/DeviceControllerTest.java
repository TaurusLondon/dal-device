package com.vf.uk.dal.device.controller.test;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;
import com.vf.uk.dal.common.context.ServiceContext;
import com.vf.uk.dal.common.context.URLParamContext;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.common.urlparams.FilterCriteria;
import com.vf.uk.dal.common.urlparams.FilterOperator;
import com.vf.uk.dal.common.urlparams.PaginationCriteria;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.controller.DeviceEntityController;
import com.vf.uk.dal.device.controller.DeviceMakeAndModelController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.svc.AccessoryInsuranceService;
import com.vf.uk.dal.device.svc.impl.CacheDeviceServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceMakeAndModelServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceServiceImpl;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;

/**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)

public class DeviceControllerTest {
	@MockBean
	DeviceDao deviceDAOMock;

	@MockBean
	DeviceTileCacheDAO cacheDao;

	@Autowired
	DeviceMakeAndModelController deviceMakeAndModelController;

	@Autowired
	DeviceController deviceController;

	@Autowired
	DeviceDetailsController deviceDetailsController;

	@Autowired
	CacheDeviceAndReviewController cacheDeviceAndReviewController;

	@MockBean
	EurekaClient eureka;

	@MockBean
	ResponseMappingHelper response;

	@MockBean
	RegistryClient registry;

	@MockBean
	RestTemplate restTemplate;

	@MockBean
	CacheDeviceServiceImpl deviceServiceCacheMock;

	@MockBean
	DeviceMakeAndModelServiceImpl deviceServiceMakeAndModelMock;

	@MockBean
	DeviceServiceImpl deviceServiceMock;

	@Autowired
	CatalogServiceAspect aspect;

	@Autowired
	DeviceEntityController deviceEntityController;

	@Autowired
	AccessoryInsuranceController accessoryInsuranceController;

	@MockBean
	AccessoryInsuranceService accessoryInsuranceService;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		given(deviceServiceMakeAndModelMock.getListOfDeviceTile(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyDouble(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString()))
						.willReturn(CommonMethods.getDeviceTile("apple", "iPhone-7", "DIVICE_PAYM"));
		given(deviceServiceMock.getDeviceTileById(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.willReturn(CommonMethods.getDeviceTile("apple", "iPhone-7", "DEVICE_PAYM"));
		given(deviceServiceCacheMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getOnlineHandsetModelFromJson(Matchers.anyObject()))
		.willReturn(CommonMethods.getOnlineHandsetModel());
		given(accessoryInsuranceService.getAccessoriesOfDevice(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).willReturn(CommonMethods.getAccessoriesTileGroup("093353"));
	}

	@Test
	public void getListOfDeviceTileNotNull() {
		Assert.assertNotNull(deviceMakeAndModelController.getListOfDeviceTile("apple", "iPhone-7", "DIVICE_PAYM",
				"UPGRADE", "W_HH_SIMONLY", "110154", "093353", "40"));
	}

	@Test
	public void getListOfDeviceTileNullMake() {
		try {
			deviceMakeAndModelController.getListOfDeviceTile(null, "iPhone-7", "DIVICE_PAYM", "UPGRADE", "W_HH_SIMONLY",
					"110154", "093353", "40");
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing make in the filter criteria", e.getMessage());
		}
	}

	@Test
	public void getListOfDeviceTileNullModel() {
		try {
			deviceMakeAndModelController.getListOfDeviceTile("apple", null, "DIVICE_PAYM", "UPGRADE", "W_HH_SIMONLY",
					"110154", "093353", "40");
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing model in the filter criteria", e.getMessage());
		}
	}

	@Test
	public void getListOfDeviceTileInavlidDeviceid() {
		try {
			deviceMakeAndModelController.getListOfDeviceTile("apple", "iPhone-7", "DIVICE_PAYM", "UPGRADE",
					"W_HH_SIMONLY", "110154", "093353945", "40");
		} catch (Exception e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void getListOfDeviceTileInavlidBundleid() {
		try {
			deviceMakeAndModelController.getListOfDeviceTile("apple", "iPhone-7", "DIVICE_PAYM", "UPGRADE",
					"W_HH_SIMONLY", "11015445", "093353", "40");
		} catch (Exception e) {
			Assert.assertEquals("Invalid Bundle Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void getDeviceTileById() {
		Assert.assertNotNull(deviceController.getDeviceTileById("093353", "Upgrade", "W_HH_SIMONLY"));

	}

	@Test
	public void getDeviceTileByIdInvalidDeviceId() {
		try {
			deviceController.getDeviceTileById("0933534958", "Upgrade", "W_HH_SIMONLY");
		} catch (Exception e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void notNullgetCommercialProduct() {
		Assert.assertNotNull(deviceEntityController.getCommercialProduct("093353", null));
		Assert.assertNotNull(deviceEntityController.getCommercialProduct("093353,093329", null));
		Assert.assertNotNull(deviceEntityController.getCommercialProduct(null, "iPhone 7 Silicone Case mid blue"));
		try {
			deviceEntityController.getCommercialProduct(null, null);
		} catch (Exception e) {
		}
	}

	@Test
	public void notProductGroupByGroupType() {
		Assert.assertNotNull(deviceEntityController.getProductGroupByGroupType("DEVICE_PAYM"));
		try {
			deviceEntityController.getProductGroupByGroupType(null);
		} catch (Exception e) {
		}
	}

	@Test
	public void notGetProductGroupModel() {
		try {
			deviceEntityController.getProductGroupModel("093353,092660");
		} catch (Exception e1) {
			Assert.assertEquals("Received Null Values for the given device id", e1.getMessage());
		}
		try {
			deviceEntityController.getProductGroupModel(null);
		} catch (Exception e) {
			Assert.assertEquals("Invalid query parameters", e.getMessage());
		}
		try {
			
			deviceEntityController.getProductGroupModel("093353,092660");
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
	}

	@Test
	public void errorhandleDeviceEntityControllerForMissingparam() {
		MissingServletRequestParameterException me = new MissingServletRequestParameterException("productId", "String");
		try {
			deviceEntityController.handleMissingParams(me);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceController.handleMissingParams(me);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			accessoryInsuranceController.handleMissingParams(me);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			cacheDeviceAndReviewController.handleMissingParams(me);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceDetailsController.handleMissingParams(me);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceMakeAndModelController.handleMissingParams(me);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
	}

	@Test
	public void invalidRequestDeviceList() {
		PaginationCriteria paginationCriteria;
		try {
			paginationCriteria = new PaginationCriteria(-9, -1);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", null, "invalid", null, "W_HH_OC_02");
		} catch (Exception e) {
			Assert.assertEquals("Invalid include recommendation value passed", e.getMessage());
		}
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", null, "true", null, "W_HH_OC_02");
		} catch (Exception ex) {
			Assert.assertEquals("Please enter valid credit limit.", ex.getMessage());
		}
		try {
			ServiceContext.urlParamContext.remove();
			paginationCriteria = new PaginationCriteria(9, -1);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", null, "true", null, "W_HH_OC_02");
		} catch (Exception ex1) {
			Assert.assertEquals("Page Number Value cannot be negative", ex1.getMessage());
		}
		try {
			ServiceContext.urlParamContext.remove();
			paginationCriteria = new PaginationCriteria(9, 0);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", "123456", "Great Camera", null, "true", null, "W_HH_OC_02");
		} catch (Exception ex2) {
			Assert.assertEquals("Invalid MSISDN passed in the request", ex2.getMessage());
		}
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", "1234567890", "Great Camera", null, "true", null, "");
		} catch (Exception ex3) {
			Assert.assertEquals("Please enter valid credit limit.", ex3.getMessage());
		}
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", "1234567890", "Great Camera", null, "true", null, "40");
		} catch (Exception ex3) {
			Assert.assertEquals("Please enter valid credit limit.", ex3.getMessage());
		}
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", "1234567890", "Great Camera", null, "true", null, "-40");
		} catch (Exception ex3) {
			Assert.assertEquals("Please enter valid credit limit.", ex3.getMessage());
		}
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", "1234567890", "Great Camera", null, "true", null, "abcd");
		} catch (Exception ex3) {
			Assert.assertEquals("Please enter valid credit limit.", ex3.getMessage());
		}
	}

	@Test
	public void nullTestForCacheDeviceTile() {
		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, ""));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		try {
			cacheDeviceAndReviewController.cacheDeviceTile();
		} catch (Exception e) {
			try {
				ServiceContext.urlParamContext.remove();
				fcList = new ArrayList<FilterCriteria>();
				fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "INVALID_GT"));
				ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
				cacheDeviceAndReviewController.cacheDeviceTile();
			} catch (Exception ex) {
				Assert.assertEquals("Invalid Group Type sent in the request", ex.getMessage());
			}
		}
	}

	@Test
	public void NotnullTestForCacheDeviceTile() {
		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		try {
			cacheDeviceAndReviewController.cacheDeviceTile();
		} catch (Exception e) {
			Assert.assertEquals("Invalid Group Type sent in the request", e.getMessage());
		}
	}
	// Accessory test cases START

	@Test
	public void notNullTestForAccessoriesOfDevice() {
		try {
			List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
			accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("093353", "Upgrade",
					"W_HH_PAYM_OC_02");
			Assert.assertNotNull(accessoryDetails);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Group Type sent in the request", e.getMessage());

		}
	}

	@Test
	public void nullValueTestForGetAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
		try {

			accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice(null, null, null);
		} catch (Exception e) {
			try {

				accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("1234", null, null);
			} catch (Exception ex) {
				Assert.assertEquals("Invalid Device Id Sent In Request", ex.getMessage());
			}
		}
		Assert.assertEquals(0, accessoryDetails.size());
	}
	// Insurance test cases Start

	@Test
	public void nullTestForGetInsuranceById() {
		Insurances insurance = null;
		try {
			insurance = accessoryInsuranceController.getInsuranceById(null, null);
		} catch (Exception e) {

		}
		Assert.assertNull(insurance);
	}

	@Test
	public void nullTestForGetInsuranceByInvalidDeviceId() {
		Insurances insurance = null;
		try {
			insurance = accessoryInsuranceController.getInsuranceById("0933as5", null);
		} catch (Exception e) {

		}
		Assert.assertNull(insurance);
	}

	@Test
	public void notNullTestForGetInsuranceById() {

		try {

			accessoryInsuranceController.getInsuranceById("093353", null);
			accessoryInsuranceController.getInsuranceById("93353", null);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void nullTestForDeviceDetails() {
		try {
			deviceDetailsController.getDeviceDetails(null, "abc", "abc");
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails("093353", "abc", "abc");
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails(null, null, null);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails("093353", "abc", "abc");
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails("093353", null, "abc");
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails("093353", null, null);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails("093353as", null, "abc");
		} catch (Exception e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
		try {
			deviceDetailsController.getDeviceDetails(null, null, "abc");
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}
	}
	@Test
	public void getHandsetOnlineModel() {
		Map<String,String> hom = new HashMap<>();
		hom.put("deviceId", "093353");
		hom.put("journeyType", "Acquisition");
		hom.put("make", "apple");
		hom.put("model", "iphone 7");
		hom.put("groupType", "PAYG");
		hom.put("sort", "2");
		hom.put("pageNumber", "2");
		hom.put("pageSize", "10");
		hom.put("color", "Black");
		hom.put("operatingSystem", "Black");
		hom.put("capacity", "Black");
		hom.put("mustHaveFeatures", "Black");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom));
		
		Map<String,String> hom1 = new HashMap<>();
		hom1.put("deviceId", "093353");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom1));
		
		Map<String,String> hom11 = new HashMap<>();
		hom11.put("deviceId", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom11));
		
		Map<String,String> hom2 = new HashMap<>();
		hom2.put("sort", "2");
		hom2.put("journeyType", "Acquisition");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom2));
		
		Map<String,String> hom21 = new HashMap<>();
		hom21.put("sort", "");
		hom21.put("journeyType", "Acquisition");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom21));
		
		Map<String,String> hom22 = new HashMap<>();
		hom22.put("sort", "2");
		hom22.put("journeyType", "upgrade");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom22));
		
		Map<String,String> hom23 = new HashMap<>();
		hom23.put("sort", "");
		hom23.put("journeyType", "upgrade");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom23));
		
		Map<String,String> hom24 = new HashMap<>();
		hom24.put("sort", "2");
		hom24.put("journeyType", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom24));
		
		Map<String,String> hom25 = new HashMap<>();
		hom25.put("sort", "2");
		hom25.put("journeyType", "secondline");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom25));
		
		Map<String,String> hom26 = new HashMap<>();
		hom26.put("sort", "2");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom26));
		
		Map<String,String> hom27 = new HashMap<>();
		hom27.put("journeyType", "secondline");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom27));
		
		Map<String,String> hom3 = new HashMap<>();
		hom3.put("make", "apple");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom3));
		
		Map<String,String> hom4 = new HashMap<>();
		hom4.put("model", "iphone 7");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom4));
		
		Map<String,String> hom31 = new HashMap<>();
		hom31.put("make", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom31));
		
		Map<String,String> hom41 = new HashMap<>();
		hom41.put("model", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom41));
		
		Map<String,String> hom5 = new HashMap<>();
		hom5.put("groupType", "PAYG");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom5));
		
		Map<String,String> hom51 = new HashMap<>();
		hom51.put("groupType", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom51));
		
		Map<String,String> hom52 = new HashMap<>();
		hom52.put("groupType", "\"\"");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom52));
		
		Map<String,String> hom6 = new HashMap<>();
		hom6.put("pageNumber", "2");
		hom6.put("pageSize", "10");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom6));
		
		Map<String,String> hom61 = new HashMap<>();
		hom61.put("pageNumber", "");
		hom61.put("pageSize", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom61));
		
		Map<String,String> hom62 = new HashMap<>();
		hom62.put("pageNumber", null);
		hom62.put("pageSize", null);
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom62));
		
		Map<String,String> hom63 = new HashMap<>();
		hom63.put("pageNumber", "0");
		hom63.put("pageSize", "0");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom63));
		
		Map<String,String> hom7 = new HashMap<>();
		hom7.put("pageNumber", "2");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom7));
		
		Map<String,String> hom8 = new HashMap<>();
		hom8.put("pageSize", "10");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom8));
		
		Map<String,String> hom9 = new HashMap<>();
		hom9.put("color", "Black");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom9));
		
		Map<String,String> hom91 = new HashMap<>();
		hom91.put("color", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom91));
		
		Map<String,String> hom92 = new HashMap<>();
		hom92.put("color", "\"\"");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom92));
		
		Map<String,String> hom10 = new HashMap<>();
		hom10.put("operatingSystem", "iOS");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom10));
		
		Map<String,String> hom101 = new HashMap<>();
		hom101.put("operatingSystem", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom101));
		
		Map<String,String> hom102 = new HashMap<>();
		hom102.put("operatingSystem", "\"\"");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom102));
		
		Map<String,String> hom111 = new HashMap<>();
		hom111.put("capacity", "32GB");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom111));
		
		Map<String,String> hom1111 = new HashMap<>();
		hom1111.put("capacity", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom1111));
		
		Map<String,String> hom1112 = new HashMap<>();
		hom1112.put("capacity", "\"\"");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom1112));
		
		Map<String,String> hom12 = new HashMap<>();
		hom12.put("mustHaveFeatures", "iOS");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom12));
		
		Map<String,String> hom122 = new HashMap<>();
		hom122.put("mustHaveFeatures", "");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom122));
		
		Map<String,String> hom121 = new HashMap<>();
		hom121.put("mustHaveFeatures", "\"\"");
		Assert.assertNotNull(deviceEntityController.getHandsetOnlineModel(hom121));
		
		
		
		Map<String, String > map = new HashMap<>();
		try {
			deviceEntityController.getHandsetOnlineModel(map);
		} catch (Exception e) {
			Assert.assertEquals("Invalid query parameters", e.getMessage());
		}
	}
	@Test
	public void getHandsetOnlineModelNullTest() {
		try {
			given(response.getOnlineHandsetModelFromJson(Matchers.anyObject()))
			.willReturn(null);
			Map<String,String> hom = new HashMap<>();
			hom.put("deviceId", "093353");
			hom.put("journeyType", "Acquisition");
			hom.put("make", "apple");
			hom.put("model", "iphone 7");
			hom.put("groupType", "PAYG");
			hom.put("sort", "2");
			hom.put("pageNumber", "2");
			hom.put("pageSize", "10");
			hom.put("color", "Black");
			hom.put("operatingSystem", "Black");
			hom.put("capacity", "Black");
			hom.put("mustHaveFeatures", "Black");
			deviceEntityController.getHandsetOnlineModel(hom);
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given handset online model", e.getMessage());
		}
	}
	
}
