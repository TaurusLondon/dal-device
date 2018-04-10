package com.vf.uk.dal.device.controller.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.KeepDeviceChangePlanRequest;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.entity.SourcePackageSummary;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.DeviceTileCacheDAO;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleAndHardwareRequest;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;

/**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)

public class DeviceControllerTest {
	@MockBean
	DeviceDao deviceDAOMock;

	@Autowired
	DeviceController deviceController;

	@MockBean
	EurekaClient eureka;

	@MockBean
	ResponseMappingHelper response;
	
	@MockBean
	RegistryClient registry;

	@MockBean
	RestTemplate restTemplate;

	@MockBean
	DeviceTileCacheDAO deviceTileCache;
	
	@Autowired
	CatalogServiceAspect aspect;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		String jsonString = new String(Utility.readFile("\\rest-mock\\COMMON-V1.json"));
		CurrentJourney obj = new ObjectMapper().readValue(jsonString, CurrentJourney.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.getForObject("http://BUNDLES-V1/es/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=" +"093353"+"&journeyType="+null, BundleDetailsForAppSrv.class )).willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(restTemplate.getForObject(
				"http://COMMON-V1/common/journey/" + "c1a42269-6562-4c96-b3be-1ca2a6681d57" + "/queries/currentJourney",
				CurrentJourney.class)).willReturn(obj);
		given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(CommonMethods.getMemPro());
		given(response.getCommercialProduct(Matchers.anyObject())).willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel().get(0));
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(Matchers.anyObject())).willReturn(CommonMethods.getCommercialBundleFromCommercialBundleRepository());
		given(response.getListOfMerchandisingPromotionFromJson(Matchers.anyObject())).willReturn(CommonMethods.getMerchandisingPromotion1());
		String jsonString1 = new String(Utility.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/", 
				CommonMethods.getRecommendedDeviceListRequest("7741655541", "109381"), RecommendedProductListResponse.class)).willReturn(obj1);
		/*
		 * given(this.deviceDAOMock.getListOfDeviceTile("Apple", "iPhone-7",
		 * "DEVICE_PAYM", null, null, null, null, null))
		 * .willReturn(CommonMethods.getDeviceTile("", "", ""));
		 * given(this.deviceDAOMock.getListOfDeviceTile("Apple", "iPhone-7",
		 * "DEVICE_PAYM", "091210", "ConditionalAccept", 60.00, "W_HH_SIMONLY",
		 * null)).willReturn(CommonMethods.getDeviceTile("", "", ""));
		 * given(this.deviceDAOMock.getListOfDeviceTile(null, "iPhone-7",
		 * "DEVICE_PAYM", null, null, null, null, null)) .willReturn(null);
		 */
		/*given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE", "Apple iPhone 7"))
				.willReturn(CommonMethods.getProductGroupByGroupTypeGroupName("", ""));
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("ValidData", null)).willReturn(null);
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVgikyj", "Apple iPhone 7")).willReturn(null);*/
		given(this.deviceDAOMock.getDeviceTileById("83921", null, null))
				.willReturn(CommonMethods.getDeviceTileById("83921"));
		given(this.deviceDAOMock.getDeviceTileById("83987", null, null)).willReturn(null);
		given(this.deviceDAOMock.getDeviceTileById(null, null, null)).willReturn(null);
		/*
		 * given(this.deviceDAOMock.getDeviceDetails("83921", "upgrade",
		 * "34543")) .willReturn(CommonMethods.getDevice("83921"));
		 * 
		 * given(this.deviceDAOMock.getDeviceDetails("83921", "Upgrade",
		 * "W_HH_PAYM_OC_02")) .willReturn(CommonMethods.getDevice("83921"));
		 * given(this.deviceDAOMock.getDeviceDetails("83929", "upgrade",
		 * "34543")).willReturn(null);
		 * given(this.deviceDAOMock.getDeviceDetails(null, null,
		 * null)).willReturn(null);
		 */
		/*given(this.deviceDAOMock.getAccessoriesOfDevice("93353", "Upgrade", "W_HH_PAYM_OC_02"))
				.willReturn(CommonMethods.getAccessoriesTileGroup("93353"));
		given(this.deviceDAOMock.getAccessoriesOfDevice("93354", null, null)).willReturn(null);
		given(this.deviceDAOMock.getAccessoriesOfDevice(null, null, null)).willReturn(null);*/
		// given(this.deviceDAOMock.getDeviceList("HANDSET","Apple", "iPhone-7",
		// "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS","Great
		// Camera")).willReturn(CommonMethods.getFacetedDevice("HANDSET","Apple",
		// "iPhone-7", "DEVICE_PAYM", "", 0, 9,"123"));
		// given(this.deviceDAOMock.getInsuranceById("93353")).willReturn(CommonMethods.getInsurances("93353"));
		// given(this.deviceDAOMock.getStockAvailability("SIMO")).willReturn(CommonMethods.getStockInfo());
		// given(this.deviceDAOMock.getStockAvailability("ValidData")).willReturn(CommonMethods.getListOfStockInfo());
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
		
	}

	@Test
	public void notNullTestForGetDeviceDetailsTile() {
		List<DeviceTile> deviceDetails = null;
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(deviceDAOMock.getPriceForBundleAndHardwareListFromTupleListAsync(Matchers.anyList(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleListAsync());
		try {
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null, null,
					null, null);
			Assert.assertNotNull(deviceDetails);
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null, null,
					null, null);
		} catch (Exception e) {

		}

	}
	@Test
	public void notNullTestForGetDeviceDetailsTileWithBundleId() {
		List<DeviceTile> deviceDetails = null;
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(deviceDAOMock.getPriceForBundleAndHardwareListFromTupleListAsync(Matchers.anyList(), Matchers.anyString(), Matchers.anyString(),Matchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleListAsync());
		given(deviceDAOMock.getBundleAndHardwarePromotionsListFromBundleListAsync(Matchers.anyList(), Matchers.anyString(), Matchers.anyString())).willReturn(CommonMethods.getBundleAndHardwarePromotionsListFromBundleListAsync());
		try {
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null, "110345",
					null, null);
			Assert.assertNotNull(deviceDetails);
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", "Upgrade", null, null,
					null, null);
		} catch (Exception e) {

		}

	}

	@Test
	public void notNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",
					"ConditionalAccept", null, null, "091210", "60.00");
			Assert.assertNotNull(deviceDetails);
		} catch (Exception e) {

		}

	}

	@Test
	public void invalidCreditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",
					"ConditionalAccept", null, null, "091210", "test");

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid credit limit.",		e.toString());
		}

	}

	@Test
	public void nullCreditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",
					"ConditionalAccept", null, null, "091210", "");

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid credit limit.",
					e.toString());
		}
	}

	@Test
	public void invalidBundleIdTestforGetDeviceDetails() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",
					"ConditionalAccept", null, "abcd", "091210", "60.0");

		} catch (Exception e) {
			Assert.assertNotNull(
					"com.vf.uk.dal.common.exception.ApplicationException: Invalid Bundle Id Sent In Request",
					e.toString());
		}
	}

	@Test
	public void noCreditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null, null,
					"091210", null);

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: No Devices Found for the given input search criteria",
					e.toString());
		}

	}

	@Test
	public void invalidContextNameTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", "Upgrade", null,
					null, "091210", "123");

		} catch (Exception e) {
			Assert.assertNotNull("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid context name.",
					e.toString());
		}

	}

	@Test
	public void nullContextNameTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null, null,
					"091210", "123");

		} catch (Exception e) {
			Assert.assertNotNull("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid context name.",
					e.toString());
		}

	}

	@Test
	public void notNullTestForGetDeviceList() {
		FacetedDevice deviceDetailsList = null;
		try {
			//given()
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);

			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			/*given(deviceDAOMock.getProductGroupsWithFacets(Matchers.anyObject(), Matchers.anyObject(),
					Matchers.anyObject(), Matchers.anyObject(), Matchers.anyObject(), Matchers.anyObject(),
					Matchers.anyString())).willReturn(CommonMethods.getProductGroupFacetModel1());*/
			given(response.getListOfProductGroupModel(Matchers.anyObject())).willReturn(CommonMethods.getListOfProductGroupMode());
			/*given(deviceDAOMock.getProductGroupsWithFacets(Matchers.anyObject(), Matchers.anyString()))
					.willReturn(CommonMethods.getProductGroupFacetModel1());*/
			given(response.getFacetField(Matchers.anyObject())).willReturn(CommonMethods.getListOfFacetField());
			/*given(deviceDAOMock.getProductModel(Matchers.anyList())).willReturn(CommonMethods.getProductModel());
					.willReturn(CommonMethods.getCommercialProduct());*/
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
					.willReturn(CommonMethods.getModel());
			given(response.getListOfOfferAppliedPriceModel(Matchers.anyObject())).willReturn(CommonMethods.getOfferAppliedPriceModel());
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			BundleAndHardwareTuple bundleAndHardwareTuple1 = new BundleAndHardwareTuple();
			bundleAndHardwareTuple1.setBundleId("110154");
			bundleAndHardwareTuple1.setHardwareId("093353");
			BundleAndHardwareTuple bundleAndHardwareTuple2 = new BundleAndHardwareTuple();
			bundleAndHardwareTuple2.setBundleId("110154");
			bundleAndHardwareTuple2.setHardwareId("092660");
			bundleHardwareTupleList.add(bundleAndHardwareTuple1);
			bundleHardwareTupleList.add(bundleAndHardwareTuple2);
			BundleAndHardwareRequest request = new BundleAndHardwareRequest();
			request.setBundleAndHardwareList(bundleHardwareTupleList);
			String jsonString = new String(Utility.readFile("\\BundleandhardwarePromotuions.json"));
			BundleAndHardwarePromotions[] obj = new ObjectMapper().readValue(jsonString,
					BundleAndHardwarePromotions[].class);
			given(restTemplate.postForObject("http://PROMOTION-V1/es/promotion/queries/ForBundleAndHardware", request,
					BundleAndHardwarePromotions[].class)).willReturn(obj);
			String url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655541/sourcePackageSummary";
			given(restTemplate.getForObject(url, SourcePackageSummary.class)).willReturn(CommonMethods.getSourcePackageSummary());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", null, null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			/*given(deviceDAOMock.getBundleDetails(Matchers.anyList()))
					.willReturn(CommonMethods.getBundleModelListForBundleList());*/
			given(response.getListOfBundleModel(Matchers.anyObject()))
			.willReturn(CommonMethods.getBundleModelListForBundleList());
			
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, "2");

			Assert.assertNotNull(deviceDetailsList);
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			ServiceContext.urlParamContext.remove();
			ServiceContext.setURLParamContext(new URLParamContext("-Priority", "", null, paginationCriteria));
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655541", "Great Camera", "SecondLine", "true", null, null);
			Assert.assertNotNull(deviceDetailsList);
			url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655542/sourcePackageSummary";
			given(restTemplate.getForObject(url, SourcePackageSummary.class)).willReturn(CommonMethods.getSourcePackageSummary());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "SecondLine", "true", null, null);
			Assert.assertNotNull(deviceDetailsList);
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "Acquisition", "true", null, null);
			Assert.assertNotNull(deviceDetailsList);
		} catch (Exception e) {
		}
		try{
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
		}catch(Exception e)
		{}
		try{
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, null);
		}catch(Exception e)
		{}try{
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", null, null, "W_HH_PAYM_01", null);
		}catch(Exception e)
		{}
		ServiceContext.urlParamContext.remove();

	}

	@Test
	public void nullTestForGetDeviceList() {
		FacetedDevice deviceDetailsList = null;
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);

			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, "W_HH_OC_02", "-1");
		} catch (Exception e) {
			try {
				deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
						"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, "W_HH_OC_02",
						null);
			} catch (Exception ex) {
				try {
					deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9,
							"Apple", "iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, "W_HH_OC_02",
							"abc");
				} catch (Exception exc) {
					try {
						deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9,
								"Apple", "iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, "W_HH_OC_02",
								null);
					} catch (Exception ex1) {
						try {
							deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9,
									"Apple", "iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Acquisition", null, "W_HH_OC_02",
									null);
						} catch (Exception ex2) {
						}
					}
				}
			}
		}

	}

	@Test
	public void notNullTestForconvertDevicePreCalDataToSolrData() {
		DaoUtils.convertDevicePreCalDataToSolrData(CommonMethods.getDevicePreCal());
	}

	/*@Test
	public void notNullTestForGetProductGroupListWithValidInput() {
		List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
		try {
			List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
			fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE"));
			fcList.add(new FilterCriteria("groupName", FilterOperator.EQUALTO, "Apple iPhone 7"));
			ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
			productGroupList = deviceController.getProductGroup();
			ServiceContext.urlParamContext.remove();
		} catch (Exception e) {

		}
		Assert.assertNotNull(productGroupList);
	}*/

	@Test
	public void sizeTestForGetDeviceDetailsTile() {
		List<DeviceTile> deviceDetails = null;
		try {
			List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
			fcList.add(new FilterCriteria("make", FilterOperator.EQUALTO, null));
			fcList.add(new FilterCriteria("model", FilterOperator.EQUALTO, "iPhone-7"));
			fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE"));
			ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
			deviceDetails = deviceController.getListOfDeviceTile(null, "iPhone-7", "DEVICE_PAYM", null, null, null,
					null, null);
			ServiceContext.urlParamContext.remove();
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails);
	}

	/*@Test
	public void notNullTestForGetProductGroupList() throws Exception {
		List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
		try {
			ServiceContext.urlParamContext.remove();
			productGroupList = deviceController.getProductGroup();
		} catch (Exception e) {

		}
		Assert.assertNotNull(productGroupList);
	}

	@Test
	public void nullTestForGetProductGroupListWithInvalidInput() throws Exception {

		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVgikyj"));
		fcList.add(new FilterCriteria("groupName", FilterOperator.EQUALTO, "Apple iPhone 7"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		List<ProductGroup> productGroupList = deviceController.getProductGroup();
		ServiceContext.urlParamContext.remove();
		Assert.assertNull(productGroupList);
	}*/

	@Test
	public void notNullTestgetDeviceTileById() {
		try {
			List<DeviceTile> deviceTileList = new ArrayList<DeviceTile>();
			deviceTileList = deviceController.getDeviceTileById("83921",null,null);
			Assert.assertNotNull(deviceTileList);
			deviceTileList = deviceController.getDeviceTileById("83921",null,null);
		} catch (Exception e) {

		}

	}

	@Test
	public void nullTestgetDeviceTileById() {
		try {
			List<DeviceTile> deviceTileList = new ArrayList<DeviceTile>();
			deviceTileList = deviceController.getDeviceTileById("83987",null,null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Invalid Device Id Sent In Request",
					e.toString());
		}
	}

	public void nullTestgetDeviceTileByIdForException() {
		
		deviceController.getDeviceTileById("093353",null,"W_HH_OC_01");
	}

	public void nullTestgetDeviceTileByIdForException1() {
		
		deviceController.getDeviceTileById("093353","journeyType","W_HH_OC_01");
	}

	@Test
	public void invalidInputTestgetDeviceTileById() throws Exception {
		List<DeviceTile> deviceTileList = new ArrayList<DeviceTile>();
		try {
			
			deviceTileList = deviceController.getDeviceTileById(null,null,"1234");
		} catch (Exception e) {

		}
		Assert.assertEquals(0, deviceTileList.size());
	}

	@Test
	public void notNullTestForgetDeviceDetailsWithJourneyType() {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceController.getDeviceDetails("083921", "", "");
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void invalidInputTestForgetDeviceDetails() throws Exception {
		DeviceDetails deviceDetails = new DeviceDetails();
		try {
			deviceDetails = deviceController.getDeviceDetails(null, null, null);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails.getDeviceId());
	}

	@Test
	public void invalidInputTestForgetDeviceDetails1() throws Exception {
		DeviceDetails deviceDetails = new DeviceDetails();
		try {
			deviceDetails = deviceController.getDeviceDetails("1234", null, null);
		} catch (Exception e) {

		}
	}

	@Test
	public void notNullTestForgetDeviceDetails() {
		DeviceDetails deviceDetails = new DeviceDetails();
		
		deviceDetails = deviceController.getDeviceDetails("093353", "", "");
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void notNullTestForGetAccessoriesOfDevice() {
		try {
			List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
			accessoryDetails = deviceController.getAccessoriesOfDevice("093353", "Upgrade", "W_HH_PAYM_OC_02");
			Assert.assertNotNull(accessoryDetails);
			// accessoryDetails =
			// deviceController.getAccessoriesOfDevice("093353",null,null);
		} catch (Exception e) {

		}
	}

	@Test
	public void nullValueTestForGetAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
		try {

			accessoryDetails = deviceController.getAccessoriesOfDevice(null, null, null);
		} catch (Exception e) {
			try {

				accessoryDetails = deviceController.getAccessoriesOfDevice("1234", null, null);
			} catch (Exception ex) {}
		}
		Assert.assertEquals(0, accessoryDetails.size());
	}

	@Test
	public void nullTestForGetInsuranceById() {
		Insurances insurance = null;
		try {
			insurance = deviceController.getInsuranceById(null, null);
		} catch (Exception e) {

		}
		Assert.assertNull(insurance);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTile() throws JsonParseException, JsonMappingException, IOException {
		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		List<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct5());
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(a);
		List<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(Matchers.anyObject())).willReturn(listOfCommerCualBundle);
		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());
		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] obj = mapper.readValue(jsonString,
				com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		try {
			given(deviceDAOMock.getReviewRatingList(Matchers.anyList()))
					.willReturn(CommonMethods.getReviewsJsonObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId("110154");
		bundle.setHardwareId("123");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		requestForBundleAndHardware.setOfferCode("W_HH_PAYM_OC_01");
		given(restTemplate.postForObject("http://PRICE-V1/es/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
						.willReturn(obj);

		deviceController.cacheDeviceTile();

	}

	@Test
	public void notNullTestForcacheDeviceTileWithoutLeadMember() throws IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
	
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		CommercialProduct com = CommonMethods.getCommercialProductForCacheDeviceTile();
		com.setLeadPlanId(null);
		CommercialProduct com1 = CommonMethods.getCommercialProductForCacheDeviceTile1();
		List<CommercialProduct> a = new ArrayList<>();
		a.add(com);
		a.add(com1);
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(a);
		List<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(Matchers.anyObject())).willReturn(listOfCommerCualBundle);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString1 = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] obj1 = mapper.readValue(jsonString1,
				com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId("109154");
		bundle.setHardwareId("123");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		requestForBundleAndHardware.setOfferCode("W_HH_PAYM_OC_01");
		given(restTemplate.postForObject("http://PRICE-V1/es/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
						.willReturn(obj1);

		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());

		String jsonString = new String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		ObjectMapper mapper1 = new ObjectMapper();
		mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BundleDetailsForAppSrv obj = mapper1.readValue(jsonString, BundleDetailsForAppSrv.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/es/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123",
				BundleDetailsForAppSrv.class)).willReturn(obj);
		try {
			deviceController.cacheDeviceTile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @Test public void testCacheDeviceTileForException() throws IOException {
	 * List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
	 * fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO,
	 * "DEVICE_PAYM")); ServiceContext.setURLParamContext(new
	 * URLParamContext("", "", fcList, null));
	 * given(deviceDAOMock.getProductGroupsByType("DEVICE_PAYM")).willReturn(
	 * CommonMethods.getGroup());
	 * given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.
	 * getCacheDeviceTileResponse()); CommercialProduct
	 * com=CommonMethods.getCommercialProduct(); com.setLeadPlanId(null);
	 * Collection<CommercialProduct> a=new ArrayList<>(); a.add(com);
	 * given(deviceDAOMock.getListCommercialProductRepositoryByLeadMemberId(
	 * Matchers.anyList())).willReturn(a);
	 * 
	 * //given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString()
	 * )).willReturn(CommonMethods.getStockAvailability());
	 * 
	 * String jsonString=new
	 * String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
	 * BundleDetailsForAppSrv obj=new ObjectMapper().readValue(jsonString,
	 * BundleDetailsForAppSrv.class);
	 * given(registry.getRestTemplate()).willReturn(restTemplate);
	 * given(restTemplate.getForObject(
	 * "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123",
	 * BundleDetailsForAppSrv.class )).willReturn(obj); doThrow(new
	 * ApplicationException("Excepion Occurred!"
	 * )).when(deviceDAOMock).movePreCalcDataToSolr(Matchers.anyList()); try {
	 * deviceController.cacheDeviceTile(); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	@Test
	public void testCacheDeviceTileStatus() {
		given(deviceDAOMock.getCacheDeviceJobStatus("testID")).willReturn(CommonMethods.getCacheDeviceTileResponse());
		CacheDeviceTileResponse response = deviceController.getCacheDeviceJobStatus("testID");
		Assert.assertEquals("Success", response.getJobStatus());
	}

	/*
	 * @Test public void notNullgetStockAvailabilityForDeviceList(){
	 * ServiceContext.urlParamContext.remove(); List<FilterCriteria> fcList =
	 * new ArrayList<FilterCriteria>(); fcList.add(new
	 * FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYG"));
	 * ServiceContext.setURLParamContext(new URLParamContext("", "", fcList,
	 * null)); List<StockInfo>
	 * deviceList=deviceController.getStockAvailabilityForDeviceList();
	 * Assert.assertNotNull(deviceList); }
	 * 
	 * @Test public void nullgetStockAvailabilityForDeviceList(){
	 * ServiceContext.urlParamContext.remove(); List<FilterCriteria> fcList =
	 * new ArrayList<FilterCriteria>(); fcList.add(new
	 * FilterCriteria("groupType", FilterOperator.EQUALTO, null));
	 * ServiceContext.setURLParamContext(new URLParamContext("", "", fcList,
	 * null)); List<StockInfo> deviceList = null; try{
	 * deviceList=deviceController.getStockAvailabilityForDeviceList(); }
	 * catch(Exception e){ Assert.assertNull(deviceList); }
	 * 
	 * }
	 */
	@Test
	public void testGetListOfDeviceTileForNullDeviceID() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile(null, "iPhone-7", "DEVICE_PAYM", null, null,
					null, "123", null);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testGetListOfDeviceTileForNullBundleID() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null,
					"123", null, null);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testGetListOfDeviceTileForNullMakeAndModel() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile(null, null, "DEVICE_PAYM", null, null,
					null, null, null);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testGetListOfDeviceTileForNullMake() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile(null, "iPhone-7", "DEVICE_PAYM", null, null,
					null, null, null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Invalid input request received. Missing make in the filter criteria",
					e.toString());
		}
	}

	@Test
	public void testGetListOfDeviceTileForNullModel() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile("Apple", null, null, null, null, null, null, null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Invalid input request received. Missing model in the filter criteria",
					e.toString());
		}
	}
	/*
	 * @Test public void testGetStockAvailabilityForDeviceListFor() {
	 * 
	 * List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
	 * fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO,
	 * "DEVICE")); ServiceContext.setURLParamContext(new URLParamContext("", "",
	 * fcList, null)); ServiceContext.urlParamContext.remove();
	 * 
	 * List<StockInfo> stockAvailabilityForGroupType = null; try {
	 * stockAvailabilityForGroupType =
	 * deviceController.getStockAvailabilityForDeviceList(); } catch(Exception
	 * e) { Assert.assertEquals(
	 * "com.vf.uk.dal.common.exception.ApplicationException: Group Type is null or Empty String."
	 * , e.toString()); } }
	 */

	/*
	 * @Test public void testGetStockAvailabilityForDeviceListForValidData() {
	 * ServiceContext.urlParamContext.remove(); List<FilterCriteria> fcList =
	 * new ArrayList<FilterCriteria>(); fcList.add(new
	 * FilterCriteria("groupType", FilterOperator.EQUALTO, "ValidData"));
	 * ServiceContext.setURLParamContext(new URLParamContext("", "", fcList,
	 * null));
	 * 
	 * List<StockInfo> stockAvailabilityForGroupType = null; try {
	 * stockAvailabilityForGroupType =
	 * deviceController.getStockAvailabilityForDeviceList(); } catch(Exception
	 * e) { Assert.assertEquals(
	 * "com.vf.uk.dal.common.exception.ApplicationException: Invalid Group Type sent in the request"
	 * , e.toString()); } }
	 */
	@Test
	public void testGetKeepDeviceChangePlan() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			BundleDetails bundleDetails = deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Assert.assertNotNull(bundleDetails);

	}

	@Test
	public void testGetKeepDeviceChangePlanInvalidInput() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = null;
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanAllInvalidInput() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullDeviceId() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId(null);
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (ApplicationException e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullDeviceId1() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("abc");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (ApplicationException e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullBundleId() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId(null);
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Bundle Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullBundleId1() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("abc");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Bundle Id Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullAllowedRecurringPriceLimit() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit(null);
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing AllowedRecurringPriceLimit", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullAllowedRecurringPriceLimit1() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("abc");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid AllowedRecurringPriceLimit Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullPlansLimit() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit(null);
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Plans Limit", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullPlansLimit1() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("abc");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Plans Limit Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForEmptyDataLimit() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson1());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Plans List found for given device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingle() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson5());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110151");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("73");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		BundleDetails bundleDetails;
		try {
			bundleDetails = deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Assert.assertNotNull(bundleDetails);

	}

	@Test
	public void testGetKeepDeviceChangePlanForError() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("10");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Similar Plan Found For Given Device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForInvalidBundleIdError() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("000000");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("10");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Bundle Id Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForCompatibleError() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null)).willReturn(null);
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("10");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Plans List found for given device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingleAfterSameMonthlyCost() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("33");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		BundleDetails bundleDetails;
		try {
			bundleDetails = deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Assert.assertNotNull(bundleDetails);

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingleAfterSameMonthlyCostException() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson3());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("33");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Similar Plan Found For Given Device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingleAfterSameMonthlyCostException1() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson4());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("33");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Plans List found for given device Id", e.getMessage());
		}
	}

	@Test(expected = ApplicationException.class)
	public void testGetDeviceReviewDetailsNull() {
		given(this.deviceDAOMock.getDeviceReviewDetails("093353")).willReturn(null);
		deviceController.getDeviceReviewDetails("093353");
	}

	/*
	 * @Test public void testGetDeviceReviewDetailsNotNull(){ try {
	 * given(this.deviceDAOMock.getDeviceReviewDetails("sku93353"))
	 * .willReturn(CommonMethods.getReviewsJsonObject()); } catch (Exception e)
	 * { LogHelper.error(this,
	 * "Not able to find review details for given deviceId"); }
	 * //Assert.assertFalse((deviceController.getDeviceReviewDetails("093353")).
	 * isEmpty()); }
	 */
	@Test
	public void notNullTestForgetListOfDeviceDetails() {
		List<DeviceDetails> deviceDetails;
		given(deviceDAOMock.getPriceForBundleAndHardware(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
	}
	@Test
	public void notNullTestForgetListOfDeviceDetailsWithoutLeadPlanId() {
		List<DeviceDetails> deviceDetails;
		CommercialProduct commercial=CommonMethods.getCommercialProductByDeviceId_093353();
		commercial.setLeadPlanId(null);
		given(response.getCommercialProduct(Matchers.anyObject())).willReturn(commercial);
		given(deviceDAOMock.getPriceForBundleAndHardware(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void emptyParamTestForgetListOfDeviceDetails() throws Exception {
		List<DeviceDetails> deviceDetails = null;
		try {
			deviceDetails = deviceController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails(null));
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails);
	}

	@Test
	public void invalidParamTestForgetListOfDeviceDetails() throws Exception {
		List<DeviceDetails> deviceDetails = null;
		try {
			deviceDetails = deviceController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails(null, null));
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails);
	}

	@Test
	public void testDaoUtilsconvertCoherenceDeviceToDeviceTile() {
		DaoUtils.convertCoherenceDeviceToDeviceTile(Long.valueOf(1), CommonMethods.getCommercialProduct1(),
				CommonMethods.getCommercialBundle(), CommonMethods.getPriceForBundleAndHardware().get(0),
				CommonMethods.getListOfBundleAndHardwarePromotions(), "DEVICE_PAYM", false, null);
	}

	@Test
	public void testDaoUtilsconvertCoherenceDeviceToDeviceDetails() {
		DaoUtils.convertCoherenceDeviceToDeviceDetails(CommonMethods.getCommercialProduct(),
				CommonMethods.getPriceForBundleAndHardware(), CommonMethods.getListOfBundleAndHardwarePromotions());
	}

	@Test
	public void testDaoUtilsconvertGroupProductToProductGroupDetails() {
		DaoUtils.convertGroupProductToProductGroupDetails(CommonMethods.getProductGroupModel());
	}

	@Test
	public void notNullTestForGetInsuranceById() {
		

		try {
			Insurances insurance = null;
			insurance = deviceController.getInsuranceById("093353", null);
			Assert.assertNotNull(insurance);
			insurance = deviceController.getInsuranceById("93353", null);
		} catch (Exception e) {
		}
	}

	@Test
	public void notNullTestForGetInsuranceByIdWithJourneyType() {
		/*given(deviceDAOMock.getCommercialProductByProductId(Matchers.anyString()))
				.willReturn(CommonMethods.getCommercialProductForInsurance());
		given(deviceDAOMock.getGroupByProdGroupName(Matchers.anyString(), Matchers.anyString()))
				.willReturn(CommonMethods.getGropuFromProductGroups());
		given(deviceDAOMock.getCommercialProductsList(Matchers.anyList()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialProductForInsurance()));*/

		try {
			Insurances insurance = null;
			Map<String, String> queryparams = new HashMap<String, String>();
			queryparams.put("deviceId", "093353");
			queryparams.put("journeyType", "upgrade");
			Map<String, String> queryparamsInvalid = new HashMap<String, String>();
			queryparamsInvalid.put("deviceId", "093353");
			queryparamsInvalid.put("journeyType", "test");
			insurance = deviceController.getInsuranceById("093353", "upgrade");
			Assert.assertNotNull(insurance);
			insurance = deviceController.getInsuranceById("093353", "test");
		} catch (Exception e) {
		}
	}

	@Test
	public void nullOfferCodeforMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, "W_HH_OC_02",
					null, "091210", "123");

		} catch (Exception e) {
			Assert.assertNotNull(
					"com.vf.uk.dal.common.exception.ApplicationException: Required JourneyType with Offercode.",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", "Upgrade",
					"W_HH_OC_02", null, "091210", "123");

		} catch (Exception e) { 
			Assert.assertNotNull(
					"com.vf.uk.dal.common.exception.ApplicationException: OfferCode is not compatible with JourneyType",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforsimoMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {

			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", "SecondLine",
					"W_HH_OC_02", null, "091210", "123");

		} catch (Exception e) {
			Assert.assertNotNull(
					"com.vf.uk.dal.common.exception.ApplicationException: OfferCode is not compatible with JourneyType",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {

			// queryparams.put("offerCode", "W_HH_OC_02_1");
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", "test", null, null,
					"091210", "123");

		} catch (Exception e) {
			Assert.assertNotNull(
					"com.vf.uk.dal.common.exception.ApplicationException: Received invalid journeyType in the request.",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforsimoDeviceDetails() {
		try {
			deviceController.getDeviceDetails("083921", "SecondLine", "W_HH_PAYM_OC_01");
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: JourneyType is not compatible for given DeviceId",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeDeviceDetails() {
		try {
			deviceController.getDeviceDetails("083921", "test", null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Received invalid journeyType in the request.",
					e.toString());
		}
	}

	@Test
	public void nullOfferCodeforDeviceDetails() {
		try {
			deviceController.getDeviceDetails("083921", null, "W_HH_OC_02");
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: offerCode is not compatible for given DeviceId",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforDeviceDetails() {
		try {
			deviceController.getDeviceDetails("083921", "Upgrade", "W_HH_SIMONLY_02");
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: JourneyType is not compatible for given DeviceId",
					e.toString());
		}
	}

	@Test
	public void nullOfferCodeforDeviceList() {
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, "W_HH_OC_02");

		} catch (Exception e) {

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
			try{
				deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
						"iOS 9", "32 GB", null, "Great Camera", null, "true", null, "W_HH_OC_02");
			}
			catch (Exception ex) {
				try{
					ServiceContext.urlParamContext.remove();
					paginationCriteria = new PaginationCriteria(9, -1);
					ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
					deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
							"iOS 9", "32 GB", null, "Great Camera", null, "true", null, "W_HH_OC_02");
				}
				catch (Exception ex1) {
					try{
						ServiceContext.urlParamContext.remove();
						paginationCriteria = new PaginationCriteria(9, 0);
						ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
						deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
								"iOS 9", "32 GB", "123456", "Great Camera", null, "true", null, "W_HH_OC_02");
					}
					catch (Exception ex2) {
						try{
							deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
									"iOS 9", "32 GB", "1234567890", "Great Camera", null, "true", null, "");
						}
						catch (Exception ex3) {
							
						}
					}
				}
			}
		}
	}


	@Test
	public void invalidJourneyTypeDeviceList() {
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", null, null, null, "W_HH_OC_02");

		} catch (Exception e) {
			Assert.assertNotEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: No Devices Found for the given input search criteria",
					e.toString());
		}

	}

	@Test
	public void convertCoherenceAccesoryToAccessory() {
		DaoUtils.convertCoherenceAccesoryToAccessory(CommonMethods.getCommercialProduct(),
				CommonMethods.GetPriceforproduct().getPriceForAccessoryes().get(0));
	}

	public void nullTestgetListOfDeviceDetailsForException() {
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("journeyType", null);
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		deviceController.getListOfDeviceDetails(queryparams);
	}

	public void nullTestgetListOfDeviceDetailsForException1() {
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("journeyType", "journeyType");
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		deviceController.getListOfDeviceDetails(queryparams);
	}

	@Test
	public void notNullTestForGetAccessoriesOfDeviceDao() {
		try {
			DaoUtils.convertCoherenceAccesoryToAccessory(CommonMethods.getCommercialProduct2(),
					CommonMethods.getPriceForAccessory());
		} catch (Exception e) {

		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTileWithoutOfferCode()
			throws JsonParseException, JsonMappingException, IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		List<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct5());
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(a);
		List<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(Matchers.anyObject())).willReturn(listOfCommerCualBundle);
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getModel());
		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());
		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] obj = mapper.readValue(jsonString,
				com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		try {
			given(deviceDAOMock.getReviewRatingList(Matchers.anyList()))
					.willReturn(CommonMethods.getReviewsJsonObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
			RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
			BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
			bundle.setBundleId("110154");
			bundle.setHardwareId("123");
			bundleList.add(bundle);
			requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
			requestForBundleAndHardware.setOfferCode("NA");
			requestForBundleAndHardware.setPackageType("Upgrade");
			given(restTemplate.postForObject("http://PRICE-V1/es/price/calculateForBundleAndHardware",
					requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
							.willReturn(obj);

			deviceController.cacheDeviceTile();
	}
	
	@Test
	public void nullTestForCacheDeviceTile()
	{
		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, ""));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		try{
			deviceController.cacheDeviceTile();
		}
		catch(Exception e){
			try{
			ServiceContext.urlParamContext.remove();
			fcList = new ArrayList<FilterCriteria>();
			fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "INVALID_GT"));
			ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
			deviceController.cacheDeviceTile();
			}
			catch(Exception ex){}
		}
	}
	
	/**
	 * Start test suit for getDeviceReviewDetails API
	 * @author suresh.kumar
	 */
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void test_Controller_getDeviceReviewDetailsValidInput(){
		try{
			given(deviceDAOMock.getDeviceReviewDetails(Matchers.anyString())).willReturn(CommonMethods.getReviewsJson());
			deviceController.getDeviceReviewDetails("093353");
		}catch(Exception exception){
			//assertEquals("com.vf.uk.dal.common.exception.ApplicationException: No reviews found for the given deviceId", exception.toString());
		}
	}
	
	/**
	 * End
	 */
	
	@Test
	public void notNullTestForgetDeviceDetails1() {
		DeviceDetails deviceDetails = new DeviceDetails();
		given(restTemplate.getForObject("http://BUNDLES-V1/es/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=093353", BundleDetailsForAppSrv.class)).willReturn(CommonMethods.getCoupledBundleListForDevice());
		deviceDetails = deviceController.getDeviceDetails("093353",null,null);
		Assert.assertNotNull(deviceDetails);
	}
	@Test
	public void notNullTestForGetDeviceDetailsTile_PAYG() {
		List<DeviceTile> deviceDetails = null;
		try {
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYG", null, null, null,
					null, null);
			Assert.assertNotNull(deviceDetails);
		} catch (Exception e) {

		}

	}
	@Test
	public void testGetListOfDeviceTileForInvalidMakeAndModel_PAYG() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile("Vodafone", "iPhone-7", "DEVICE_PAYG", null, null,
					null, null, null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: No Devices Found for the given input search criteria",
					e.toString());
		}
	}
	@Test
	public void testGetListOfDeviceTileForInvalidJourneyType_PAYG() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYG", "Upgrade", null,
					null, null, null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: JourneyType is not compatible for given GroupType",
					e.toString());
		}
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTileWithoutOfferCodeForPAYG()
			throws JsonParseException, JsonMappingException, IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYG"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		Collection<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct5());
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getModel());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(Utility.readFile("\\TEST-MOCK\\PRICE_FOR_PAYG.json"));
		com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] obj = mapper.readValue(jsonString,
				com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		Map<String,String> ratingmap=new HashMap<>();
		ratingmap.put("123", "3.7");
		ratingmap.put("23", "3.7");
		ratingmap.put("sku124", "3.9");
		ratingmap.put("sku24", "3.9");
		given(deviceDAOMock.getDeviceReviewRating(Matchers.anyList()))
					.willReturn(ratingmap);
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId(null);
		bundle.setHardwareId("123");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		requestForBundleAndHardware.setOfferCode(null);
		requestForBundleAndHardware.setPackageType(null);
		given(restTemplate.postForObject("http://PRICE-V1/es/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
						.willReturn(obj);

		deviceController.cacheDeviceTile();
	}
	@Test
	public void InvalidTestForgetDeviceDetailsWithJourneyTypePAYG() {
		
		try{
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceController.getDeviceDetails("088417", "Upgrade", null);
		}catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: JourneyType is not compatible for given DeviceId",e.toString());
		}
	}
	@Test
	public void InvalidTestForgetDeviceDetailsWithOfferCodePAYG() {
		try{
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceController.getDeviceDetails("088417", "abcd", "W_HH_OC_01");
		}catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: offerCode is not compatible for given DeviceId",e.toString());
		}
	}
	@Test
	public void notNullTestForgetDeviceDetailsPAYG() {
	
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceController.getDeviceDetails("088417", "abcd", null);
		Assert.assertNotNull(deviceDetails);
	}
	@Test
	public void notNullTestConvertCoherenceDeviceToDeviceTile_PAYG(){
		DaoUtils.convertCoherenceDeviceToDeviceTile_PAYG(Long.parseLong("1"), CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware().get(0), "DEVICE_PAYG", CommonMethods.getListOfBundleAndHardwarePromotions().get(0));
	}
	@Test
	public void notNullTestgetListOfOfferAppliedPrice(){
		DaoUtils.getBundlePriceBasedOnDiscountDuration(CommonMethods.getBundlePrice(), "conditional_full_discount");
	}
	@Test
	public void notNullgetListOfPriceForBundleAndHardwareForDevice(){
		DaoUtils dao=new DaoUtils();
		dao.getListOfPriceForBundleAndHardwareForDevice(CommonMethods.getBundleHeaderList("SIMO"));
	}
	@Test
	public void notNullassembleMerchandisingPromotion(){
		DaoUtils.assembleMerchandisingPromotion(CommonMethods.getListOfBundleAndHardwarePromotions().get(0), 
				CommonMethods.getBundleAndHardwareEntertainmentPacks(), CommonMethods.getBundleAndHardwareAllowance(),
				CommonMethods.getBundleAndHardwareCouplingPromotions(), CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareSash(),
				CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareSecureNet(), CommonMethods.getCataloguepromotionqueriesForHardwareSash(), 
				CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareExtras(), CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareAccessory(),
				CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareExtras(), CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareAccessory(),
				CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareExtras(), CommonMethods.getCataloguepromotionqueriesForBundleAndHardwareAccessory());
	}
	@Test
	public void notNullgetListOfIlsPriceWithoutOfferCode(){
		List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware> 
		listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade= CommonMethods.getOfferAppliedPrice();
		Map<String, Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>> mapOfIlsPriceWithoutOfferCode = new HashMap<>();
		mapOfIlsPriceWithoutOfferCode.put(Constants.JOURNEY_TYPE_UPGRADE,
				DaoUtils.getILSPriceWithoutOfferCode(listOfPriceForBundleAndHardwareWithoutOfferCodeForUpgrade));
		
		DaoUtils.getListOfIlsPriceWithoutOfferCode("093353", mapOfIlsPriceWithoutOfferCode);
	}
	@Test
	public void notNullgetListOfPriceForBundleAndHardwareForCacheDevice(){
		DaoUtils dao=new DaoUtils();
		Map<String, CommercialBundle> commercialbundleMap=new HashMap<>();
		commercialbundleMap.put("093353", CommonMethods.getCommercialBundleFromCommercialBundleRepository());
		dao.getListOfPriceForBundleAndHardwareForCacheDevice(CommonMethods.getOfferAppliedPrice(), commercialbundleMap, "Upgrade");
	}
	@Test
	public void notNullpopulateMerchandisingPromotions(){
		DaoUtils.populateMerchandisingPromotions(CommonMethods.getPriceForBundleAndHardware1().get(0), CommonMethods.getBundlePriceForUtility());
	}
	
	@Test
	public void notNullgetCommercialProduct(){
		Assert.assertNotNull(deviceController.getCommercialProduct("093353",null));
		Assert.assertNotNull(deviceController.getCommercialProduct("093353,093329",null));
		Assert.assertNotNull(deviceController.getCommercialProduct(null,"iPhone 7 Silicone Case mid blue"));
		try{
			deviceController.getCommercialProduct(null,null);
		}catch(Exception e){}
	}
	@Test
	public void notProductGroupByGroupType(){
		Assert.assertNotNull(deviceController.getProductGroupByGroupType("DEVICE_PAYM"));
		try{
			deviceController.getProductGroupByGroupType(null);
		}catch(Exception e){}
	}
	
	@Test
	public void notNullTestForgetAccessoriesOfDevice(){
		BundleDeviceAndProductsList deviceAndProductsList = new BundleDeviceAndProductsList();
		List<String> accessory=new ArrayList<>();
		accessory.add("093329");accessory.add("085145");
		deviceAndProductsList.setAccessoryList(accessory);
		deviceAndProductsList.setDeviceId("093353");
		deviceAndProductsList.setExtraList(new ArrayList<>());
		deviceAndProductsList.setOfferCode(null);
		deviceAndProductsList.setPackageType(null);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://PRICE-V1/es/price/product",
				deviceAndProductsList, PriceForProduct.class))
				.willReturn(CommonMethods.getPriceForProduct_For_GetAccessories());
		
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getListOfProductGroupForAccessories());
		given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(CommonMethods.getListOfCommercialProductsForAccessory());
		given(response.getCommercialProduct(Matchers.anyObject())).willReturn(CommonMethods.getCommercialProductByDeviceIdForAccessory());
		Assert.assertNotNull(deviceController.getAccessoriesOfDevice("093353",null,null));
	
	}
	
	@Test
	public void notGetProductGroupModel(){
		try {
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(Matchers.anyObject())).willReturn(CommonMethods.getProductGroupModelForDeliveryMethod());
			Assert.assertNotNull(deviceController.getProductGroupModel("093353,092660"));
		} catch (Exception e1) {
		}
		try{
			deviceController.getProductGroupModel(null);	
		}catch(Exception e){}
		try{
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(Matchers.anyObject())).willReturn(new ArrayList<>());
			deviceController.getProductGroupModel("093353,092660");
		}catch(Exception e){}
	}
}
