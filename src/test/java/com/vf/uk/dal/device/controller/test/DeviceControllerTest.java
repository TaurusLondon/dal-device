package com.vf.uk.dal.device.controller.test;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.RestTemplate;

import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.controller.DeviceEntityController;
import com.vf.uk.dal.device.controller.DeviceMakeAndModelController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.Insurances;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.service.AccessoryInsuranceService;
import com.vf.uk.dal.device.service.CacheDeviceServiceImpl;
import com.vf.uk.dal.device.service.DeviceMakeAndModelServiceImpl;
import com.vf.uk.dal.device.service.DeviceServiceImpl;

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
	ResponseMappingHelper response;

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
		given(deviceServiceMakeAndModelMock.getListOfDeviceTile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getDeviceTile("apple", "iPhone-7", "DIVICE_PAYM"));
		given(deviceServiceMock.getDeviceTileById(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
				.willReturn(CommonMethods.getDeviceTile("apple", "iPhone-7", "DEVICE_PAYM"));
		given(deviceServiceCacheMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getFacetField(ArgumentMatchers.any()))
		.willReturn(CommonMethods.getListOfFacetField());
		given(accessoryInsuranceService.getAccessoriesOfDevice(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).willReturn(CommonMethods.getAccessoriesTileGroup("093353"));
	}

	@Test
	public void getListOfDeviceTileNotNull() {
		List<DeviceTile> deviceTile = deviceMakeAndModelController.getListOfDeviceTile("apple", "iPhone-7",
				"DIVICE_PAYM", "UPGRADE", "W_HH_SIMONLY", "110154", "093353", "40");
		Assert.assertNotNull(deviceTile);
		Assert.assertEquals(deviceTile.get(0).getDeviceId(), "93353");
		Assert.assertEquals(deviceTile.get(0).getGroupName(), "Apple iPhone 6s");
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
		List<DeviceTile> deviceTile = deviceController.getDeviceTileById("093353", "Upgrade", "W_HH_SIMONLY");
		Assert.assertNotNull(deviceTile);
		Assert.assertEquals(deviceTile.get(0).getDeviceId(), "93353");
		Assert.assertEquals(deviceTile.get(0).getGroupName(), "Apple iPhone 6s");
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
		List<CommercialProduct> commercialProd = deviceEntityController.getCommercialProduct("093353", null);
		Assert.assertNotNull(commercialProd);
		Assert.assertNotNull(deviceEntityController.getCommercialProduct("093353,093329", null));
		Assert.assertNotNull(deviceEntityController.getCommercialProduct(null, "iPhone 7 Silicone Case mid blue"));
		Assert.assertEquals(commercialProd.get(0).getId(), "093329");
		Assert.assertEquals(commercialProd.get(0).getName(), "iPhone 7 Silicone Case mid blue");
		try {
			deviceEntityController.getCommercialProduct(null, null);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Invalid query parameters");
		}
	}

	@Test
	public void notProductGroupByGroupType() {
		List<Group> group = deviceEntityController.getProductGroupByGroupType("DEVICE_PAYM");
		Assert.assertNotNull(group);
		Assert.assertEquals(group.get(0).getName(), "Apple iPhone 6s");
		try {
			deviceEntityController.getProductGroupByGroupType(null);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Invalid query parameters");
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
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", -1, -9, "Apple", "iPhone-7", "White",
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
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", -1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", null, "true", null, "W_HH_OC_02");
		} catch (Exception ex1) {
			Assert.assertEquals("Page Number Value cannot be negative", ex1.getMessage());
		}
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 0, 9, "Apple", "iPhone-7", "White",
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
		try {
			cacheDeviceAndReviewController.cacheDeviceTile("");
		} catch (Exception e) {
			try {
				cacheDeviceAndReviewController.cacheDeviceTile("INVALID_GT");
			} catch (Exception ex) {
				Assert.assertEquals("Invalid Group Type sent in the request", ex.getMessage());
			}
		}
	}

	@Test
	public void NotnullTestForCacheDeviceTile() {
		ResponseEntity<CacheDeviceTileResponse> cacheDevice =cacheDeviceAndReviewController.cacheDeviceTile("DEVICE_PAYM");
		CacheDeviceTileResponse response =cacheDevice.getBody();
		Assert.assertNotNull(response);
		Assert.assertNotNull(cacheDevice);
		Assert.assertEquals(response.getJobId(), "1234");
		Assert.assertEquals(response.getJobStatus(), "Success");
	}
	// Accessory test cases START

	@Test
	public void notNullTestForAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("093353", "Upgrade", "W_HH_PAYM_OC_02");
		Assert.assertNotNull(accessoryDetails);
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
			Assert.assertEquals(e.getMessage(), "Invalid input request received. Missing Device Id");
		}
		Assert.assertNull(insurance);
	}

	@Test
	public void nullTestForGetInsuranceByInvalidDeviceId() {
		Insurances insurance = null;
		try {
			insurance = accessoryInsuranceController.getInsuranceById("0933as5", null);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Invalid Device Id Sent In Request");
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
}
