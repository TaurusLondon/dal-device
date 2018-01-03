package com.vf.uk.dal.device.controller.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.KeepDeviceChangePlanRequest;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.DeviceTileCacheDAO;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleAndHardwareRequest;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.product.pojo.CommercialProduct;

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
	RegistryClient registry;

	@MockBean
	RestTemplate restTemplate;

	@MockBean
	DeviceTileCacheDAO deviceTileCache;

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
		/*given(this.deviceDAOMock.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", null, null, null, null, null))
				.willReturn(CommonMethods.getDeviceTile("", "", ""));
		given(this.deviceDAOMock.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM", "091210", "ConditionalAccept",
				60.00, "W_HH_SIMONLY", null)).willReturn(CommonMethods.getDeviceTile("", "", ""));
		given(this.deviceDAOMock.getListOfDeviceTile(null, "iPhone-7", "DEVICE_PAYM", null, null, null, null, null))
				.willReturn(null);*/
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE", "Apple iPhone 7"))
				.willReturn(CommonMethods.getProductGroupByGroupTypeGroupName("", ""));
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("ValidData", null)).willReturn(null);
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVgikyj", "Apple iPhone 7")).willReturn(null);
		given(this.deviceDAOMock.getDeviceTileById("83921", null,null)).willReturn(CommonMethods.getDeviceTileById("83921"));
		given(this.deviceDAOMock.getDeviceTileById("83987", null,null)).willReturn(null);
		given(this.deviceDAOMock.getDeviceTileById(null, null,null)).willReturn(null);
		given(this.deviceDAOMock.getDeviceDetails("83921", "upgrade", "34543"))
				.willReturn(CommonMethods.getDevice("83921"));

		given(this.deviceDAOMock.getDeviceDetails("83921", "Upgrade", "W_HH_PAYM_OC_02"))
				.willReturn(CommonMethods.getDevice("83921"));
		given(this.deviceDAOMock.getDeviceDetails("83929", "upgrade", "34543")).willReturn(null);
		given(this.deviceDAOMock.getDeviceDetails(null, null, null)).willReturn(null);
		given(this.deviceDAOMock.getAccessoriesOfDevice("93353","Upgrade","W_HH_PAYM_OC_02"))
				.willReturn(CommonMethods.getAccessoriesTileGroup("93353"));
		given(this.deviceDAOMock.getAccessoriesOfDevice("93354",null,null)).willReturn(null);
		given(this.deviceDAOMock.getAccessoriesOfDevice(null,null,null)).willReturn(null);
		// given(this.deviceDAOMock.getDeviceList("HANDSET","Apple", "iPhone-7",
		// "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS","Great
		// Camera")).willReturn(CommonMethods.getFacetedDevice("HANDSET","Apple",
		// "iPhone-7", "DEVICE_PAYM", "", 0, 9,"123"));
		//given(this.deviceDAOMock.getInsuranceById("93353")).willReturn(CommonMethods.getInsurances("93353"));
		// given(this.deviceDAOMock.getStockAvailability("SIMO")).willReturn(CommonMethods.getStockInfo());
		// given(this.deviceDAOMock.getStockAvailability("ValidData")).willReturn(CommonMethods.getListOfStockInfo());
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
		given(deviceDAOMock.getMerchandisingPromotionByPromotionName("handset.limited.GBP"))
				.willReturn(CommonMethods.getMemPro());
		given(deviceDAOMock.getMerchandisingPromotionByPromotionName("handset.full.percentage"))
				.willReturn(CommonMethods.getMemPro());
		// given(this.deviceDAOMock.getDeviceListFromPricing("DEVICE_PAYM")).willReturn(CommonMethods.getDevicePreCalculatedData());
		given(this.deviceTileCache.saveDeviceListPreCalcData(CommonMethods.getDevicePreCalculatedData())).willReturn(1);
		given(this.deviceTileCache.saveDeviceMediaData(CommonMethods.getDevicePreCalculatedData().get(0).getMedia(),
				"093353")).willReturn(1);
		// given(deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.anyString())).willReturn(value)
		given(this.deviceDAOMock.getCommercialProductByProductId("093353"))
				.willReturn(CommonMethods.getCommercialProduct2());
		given(this.deviceDAOMock.getCommercialProductByProductId("093427"))
				.willReturn(CommonMethods.getCommercialProduct2());
		given(this.deviceDAOMock.getCommercialBundleByBundleId("110154"))
				.willReturn(CommonMethods.getCommercialBundle());
		given(deviceDAOMock.getMerchandisingPromotionByPromotionName("EXTRA.1GB.DATA"))
				.willReturn(CommonMethods.getMerchPromotion());
		
		given(deviceDAOMock.getListOfCommercialProductsFromCommercialProductRepository("Apple", "iPhone-7")).willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		
		given(deviceDAOMock.getListOfProductGroupFromProductGroupRepository("DEVICE_PAYM")).willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(deviceDAOMock.getCommercialProductFromCommercialProductRepository("088417")).willReturn(CommonMethods.getCommercialProductByDeviceId());
		given(deviceDAOMock.getBazaarVoice(Matchers.anyString())).willReturn(CommonMethods.getBazaarVoice());
	}

	@Test
	public void notNullTestForGetDeviceDetailsTile() {
		List<DeviceTile> deviceDetails = null;
		try {
			deviceDetails = deviceController
					.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",null,null,null,null,null);
			Assert.assertNotNull(deviceDetails);
			deviceDetails = deviceController
					.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",null,null,null,null,null);
		} catch (Exception e) {

		}

	}

	@Test
	public void notNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","ConditionalAccept",null,null,"091210","60.00");
			Assert.assertNotNull(deviceDetails);
		} catch (Exception e) {

		}

	}

	@Test
	public void creditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","ConditionalAccept",null,null,"091210",null);

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid credit limit.",
					e.toString());
		}

	}

	@Test
	public void invalidCreditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","ConditionalAccept",null,null,"091210","test");

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid credit limit.",
					e.toString());
		}

	}

	@Test
	public void nullCreditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			Map<String, String> queryparams = new HashMap<String, String>();
			queryparams.put("make", "apple");
			queryparams.put("model", "iPhone-7");
			queryparams.put("groupType", "DEVICE_PAYM");
			queryparams.put("deviceId", "091210");
			queryparams.put("creditLimit", "");
			queryparams.put("journeyType", "ConditionalAccept");
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","ConditionalAccept",null,null,"091210","");

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid credit limit.",
					e.toString());
		}
	}

	@Test
	public void invalidBundleIdTestforGetDeviceDetails() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","ConditionalAccept",null,"abcd","091210","60.0");

		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Invalid Bundle Id Sent In Request",
					e.toString());
		}
	}

	@Test
	public void noCreditLimitNullTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
		
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",null,null,null,"091210",null);

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid credit limit.",
					e.toString());
		}

	}

	@Test
	public void invalidContextNameTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","Upgrade",null,null,"091210","123");

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid context name.",
					e.toString());
		}

	}

	@Test
	public void nullContextNameTestForGetDeviceDetailsTileConditionalAccept() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",null,null,null,"091210","123");

		} catch (Exception e) {
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Please enter valid context name.",
					e.toString());
		}

	}

	@Test
	public void notNullTestForGetDeviceList() {
		FacetedDevice deviceDetailsList = null;
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);

			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			given(deviceDAOMock.getProductGroupsWithFacets(Matchers.anyObject(), Matchers.anyObject(),
					Matchers.anyObject(), Matchers.anyObject(), Matchers.anyObject(), Matchers.anyObject(),Matchers.anyString()))
							.willReturn(CommonMethods.getProductGroupFacetModel1());
			given(deviceDAOMock.getProductGroupsWithFacets(Matchers.anyObject(),Matchers.anyString()))
					.willReturn(CommonMethods.getProductGroupFacetModel1());
			given(deviceDAOMock.getProductModel(Matchers.anyList())).willReturn(CommonMethods.getProductModel());
			given(deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.anyString())).willReturn(CommonMethods.getCommercialProduct());
			given(deviceDAOMock.getJourneyTypeCompatibleOfferCodes(Matchers.anyString())).willReturn(CommonMethods.getModel());
			given(deviceDAOMock.getBundleAndHardwarePriceFromSolr(Matchers.anyList(),Matchers.anyString(),Matchers.anyString())).willReturn(CommonMethods.getOfferAppliedPriceModel());
			List<BundleAndHardwareTuple> bundleHardwareTupleList=new ArrayList<>();
			BundleAndHardwareTuple bundleAndHardwareTuple1= new BundleAndHardwareTuple();
			bundleAndHardwareTuple1.setBundleId("110154");
			bundleAndHardwareTuple1.setHardwareId("093353");
			BundleAndHardwareTuple bundleAndHardwareTuple2= new BundleAndHardwareTuple();
			bundleAndHardwareTuple2.setBundleId("110154");
			bundleAndHardwareTuple2.setHardwareId("092660");
			bundleHardwareTupleList.add(bundleAndHardwareTuple1);
			bundleHardwareTupleList.add(bundleAndHardwareTuple2);
			BundleAndHardwareRequest request =new BundleAndHardwareRequest();
			request.setBundleAndHardwareList(bundleHardwareTupleList);
			String jsonString = new String(Utility.readFile("\\BundleandhardwarePromotuions.json"));
			 BundleAndHardwarePromotions[] obj = new ObjectMapper().readValue(jsonString,  BundleAndHardwarePromotions[].class);
			given(restTemplate.postForObject("http://PROMOTION-V1/promotion/queries/ForBundleAndHardware",
					request, BundleAndHardwarePromotions[].class))
							.willReturn(obj);
			deviceDetailsList = deviceController
					.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
							"32 GB",null, "Great Camera",null,null, null, null);
			given(deviceDAOMock.getBundleDetails(Matchers.anyList()))
					.willReturn(CommonMethods.getBundleModelListForBundleList());
			
			deviceDetailsList = deviceController
					.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
							"32 GB",null, "Great Camera","Upgrade",null, null, "W_HH_OC_02");

			Assert.assertNotNull(deviceDetailsList);
			deviceDetailsList = deviceController
					.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
							"32 GB",null, "Great Camera","SecondLine",null, null, null);

		} catch (Exception e) {
			
		}
		ServiceContext.urlParamContext.remove();

	}

	@Test
	public void nullTestForGetDeviceList() {
		FacetedDevice deviceDetailsList = null;
		Map<String, String> queryparams = new HashMap<String, String>();
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);

			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceDetailsList = deviceController.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
					"32 GB",null, "Great Camera","Upgrade",null, null, "W_HH_OC_02");
		} catch (Exception e) {
			try {
				queryparams.put("creditLimit", null);
				deviceDetailsList = deviceController.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
						"32 GB",null, "Great Camera","Upgrade",null, null, "W_HH_OC_02");
			} catch (Exception ex) {
				try {
					queryparams.put("creditLimit", "abc");
					deviceDetailsList = deviceController.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
							"32 GB",null, "Great Camera","Upgrade",null, null, "W_HH_OC_02");
				} catch (Exception exc) {
				}
			}
		}

	}

	@Test
	public void notNullTestForconvertDevicePreCalDataToSolrData() {
		DaoUtils.convertDevicePreCalDataToSolrData(CommonMethods.getDevicePreCal());
	}

	@Test
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
	}

	@Test
	public void sizeTestForGetDeviceDetailsTile() {
		List<DeviceTile> deviceDetails = null;
		try {
			List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
			fcList.add(new FilterCriteria("make", FilterOperator.EQUALTO, null));
			fcList.add(new FilterCriteria("model", FilterOperator.EQUALTO, "iPhone-7"));
			fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE"));
			ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
			deviceDetails = deviceController
					.getListOfDeviceTile(null, "iPhone-7", "DEVICE_PAYM",null,null,null,null,null);
			ServiceContext.urlParamContext.remove();
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails);
	}

	@Test
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
	}

	@Test
	public void notNullTestgetDeviceTileById() {
		try {
			List<DeviceTile> deviceTileList = new ArrayList<DeviceTile>();
			deviceTileList = deviceController.getDeviceTileById(CommonMethods.getQueryParamsMap("83921"));
			Assert.assertNotNull(deviceTileList);
			deviceTileList = deviceController.getDeviceTileById(CommonMethods.getInvalidQueryParamsMap("83921"));
		} catch (Exception e) {

		}

	}

	@Test
	public void nullTestgetDeviceTileById() {
		try{
		List<DeviceTile> deviceTileList = new ArrayList<DeviceTile>();
		deviceTileList = deviceController.getDeviceTileById(CommonMethods.getQueryParamsMap("83987"));
		}
		catch(Exception e){
			Assert.assertEquals("com.vf.uk.dal.common.exception.ApplicationException: Invalid Device Id Sent In Request", e.toString());
		}
	}
	
	public void nullTestgetDeviceTileByIdForException() {
		Map<String, String> queryparams=new HashMap<>();
		queryparams.put("journeyType", null);
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		 deviceController.getDeviceTileById(queryparams);
	}
	
	public void nullTestgetDeviceTileByIdForException1() {
		Map<String, String> queryparams=new HashMap<>();
		queryparams.put("journeyType", "journeyType");
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		 deviceController.getDeviceTileById(queryparams);
	}
	@Test
	public void invalidInputTestgetDeviceTileById() throws Exception {
		List<DeviceTile> deviceTileList = new ArrayList<DeviceTile>();
		try {
			Map<String, String> queryparams = new HashMap<String, String>();
			queryparams.put("offerCode", "1234");
			deviceTileList = deviceController.getDeviceTileById(queryparams);
		} catch (Exception e) {

		}
		Assert.assertEquals(0, deviceTileList.size());
	}

	@Test
	public void notNullTestForgetDeviceDetails() {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceController.getDeviceDetails("083921", "Upgrade", "W_HH_PAYM_OC_02");
		Assert.assertNull(deviceDetails);
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
	public void nullTestForgetDeviceDetails() {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceController.getDeviceDetails("083929", "Upgrade", "W_HH_PAYM_OC_02");
		Assert.assertNull(deviceDetails);
	}

	@Test
	public void notNullTestForGetAccessoriesOfDevice() {
		try {
			List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
			accessoryDetails = deviceController.getAccessoriesOfDevice("093353",null,null);
			Assert.assertNotNull(accessoryDetails);
			accessoryDetails = deviceController.getAccessoriesOfDevice("093353",null,null);
		} catch (Exception e) {

		}
	}

	@Test
	public void nullTestForGetAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
		
		accessoryDetails = deviceController.getAccessoriesOfDevice("093354",null,null);
		Assert.assertNotNull(accessoryDetails);
	}

	@Test
	public void nullValueTestForGetAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
		try {
			
			accessoryDetails = deviceController.getAccessoriesOfDevice(null,null,null);
		} catch (Exception e) {

		}
		Assert.assertEquals(0, accessoryDetails.size());
	}

	@Test
	public void nullTestForGetInsuranceById() {
		Insurances insurance = null;
		try {
			insurance = deviceController.getInsuranceById(null,null);
		} catch (Exception e) {

		}
		Assert.assertNull(insurance);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTile() throws JsonParseException, JsonMappingException, IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceDAOMock.getProductGroupsByType("DEVICE_PAYM")).willReturn(CommonMethods.getGroup());
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		Collection<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct5());
		given(deviceDAOMock.getListCommercialProductRepositoryByLeadMemberId(Matchers.anyList())).willReturn(a);
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
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
						.willReturn(obj);

		deviceController.cacheDeviceTile();

	}

	@Test
	public void notNullTestForcacheDeviceTileWithoutLeadMember() throws IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceDAOMock.getProductGroupsByType("DEVICE_PAYM")).willReturn(CommonMethods.getGroup());
		CommercialProduct commercialProduct = CommonMethods.getCommercialProduct5();
		given(this.deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.any()))
				.willReturn(commercialProduct);
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		CommercialProduct com = CommonMethods.getCommercialProductForCacheDeviceTile();
		com.setLeadPlanId(null);
		CommercialProduct com1 = CommonMethods.getCommercialProductForCacheDeviceTile1();
		Collection<CommercialProduct> a = new ArrayList<>();
		a.add(com);
		a.add(com1);
		given(deviceDAOMock.getListCommercialProductRepositoryByLeadMemberId(Matchers.anyList())).willReturn(a);
		Collection<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(deviceDAOMock.getListCommercialBundleRepositoryByCompatiblePlanList(Matchers.anyList()))
				.willReturn(listOfCommerCualBundle);
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
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
						.willReturn(obj1);

		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());

		String jsonString = new String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		ObjectMapper mapper1=new ObjectMapper();
		mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		BundleDetailsForAppSrv obj = mapper1.readValue(jsonString, BundleDetailsForAppSrv.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123",
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
	public void testGetListOfDeviceTileForNullMake() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController
					.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",null,null,null,null,null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Invalid input request received. Missing make and model in the filter criteria",
					e.toString());
		}
	}

	@Test
	public void testGetListOfDeviceTileForNullModel() {
		List<DeviceTile> listOfDeviceTile = null;
		try {
			listOfDeviceTile = deviceController
					.getListOfDeviceTile("Apple", null, null,null,null,null,null,null);
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
				CommonMethods.getListOfBundleAndHardwarePromotions(), "DEVICE_PAYM", false,null);
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
		given(deviceDAOMock.getCommercialProductByProductId(Matchers.anyString()))
				.willReturn(CommonMethods.getCommercialProductForInsurance());
		given(deviceDAOMock.getGroupByProdGroupName(Matchers.anyString(),Matchers.anyString()))
				.willReturn(CommonMethods.getGropuFromProductGroups());
		given(deviceDAOMock.getCommercialProductsList(Matchers.anyList()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialProductForInsurance()));

		try {
			Insurances insurance = null;
			insurance = deviceController.getInsuranceById("093353",null);
			Assert.assertNotNull(insurance);
			insurance = deviceController.getInsuranceById("93353",null);
		} catch (Exception e) {
		}
	}
	@Test
	public void notNullTestForGetInsuranceByIdWithJourneyType() {
		given(deviceDAOMock.getCommercialProductByProductId(Matchers.anyString()))
				.willReturn(CommonMethods.getCommercialProductForInsurance());
		given(deviceDAOMock.getGroupByProdGroupName(Matchers.anyString(),Matchers.anyString()))
				.willReturn(CommonMethods.getGropuFromProductGroups());
		given(deviceDAOMock.getCommercialProductsList(Matchers.anyList()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialProductForInsurance()));

		try {
			Insurances insurance = null;
			Map<String, String> queryparams = new HashMap<String, String>();
			queryparams.put("deviceId", "093353");
			queryparams.put("journeyType","upgrade");
			Map<String, String> queryparamsInvalid = new HashMap<String, String>();
			queryparamsInvalid.put("deviceId", "093353");
			queryparamsInvalid.put("journeyType","test");
			insurance = deviceController.getInsuranceById("093353","upgrade");
			Assert.assertNotNull(insurance);
			insurance = deviceController.getInsuranceById("093353","test");
		} catch (Exception e) {
		}
	}


	@Test
	public void nullOfferCodeforMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM",null,"W_HH_OC_02",null,"091210","123");

		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: Required JourneyType with Offercode.",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","Upgrade","W_HH_OC_02",null,"091210","123");

		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: OfferCode is not compatible with JourneyType",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforsimoMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","SecondLine","W_HH_OC_02",null,"091210","123");

		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: OfferCode is not compatible with JourneyType",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeMakeModel() {
		List<DeviceTile> deviceDetails = null;
		try {
			
			// queryparams.put("offerCode", "W_HH_OC_02_1");
			deviceDetails = deviceController.getListOfDeviceTile("Apple", "iPhone-7", "DEVICE_PAYM","test",null,null,"091210","123");

		} catch (Exception e) {
			Assert.assertEquals(
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
					"com.vf.uk.dal.common.exception.ApplicationException: OfferCode is not compatible with JourneyType",
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
					"com.vf.uk.dal.common.exception.ApplicationException: Required JourneyType with Offercode.",
					e.toString());
		}
	}

	@Test
	public void invalidJourneyTypeforDeviceDetails() {
		try {
			deviceController.getDeviceDetails("083921", "Upgrade", "W_HH_SIMONLY_02");
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: OfferCode is not compatible with JourneyType",
					e.toString());
		}
	}

	@Test
	public void nullOfferCodeforDeviceList() {
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
					"32 GB",null, "Great Camera","Upgrade",null, null, "W_HH_OC_02");

		} catch (Exception e) {
			
		}

	}

	@Test
	public void invalidJourneyTypeDeviceList() {
		try {
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);
			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			deviceController.getDeviceList("HANDSET","DEVICE_PAYM","Priority",1,9,"Apple", "iPhone-7","White","iOS 9",
					"32 GB",null, "Great Camera",null,null, null, "W_HH_OC_02");

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
		Map<String, String> queryparams=new HashMap<>();
		queryparams.put("journeyType", null);
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		 deviceController.getListOfDeviceDetails(queryparams);
	}
	
	public void nullTestgetListOfDeviceDetailsForException1() {
		Map<String, String> queryparams=new HashMap<>();
		queryparams.put("journeyType", "journeyType");
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		 deviceController.getListOfDeviceDetails(queryparams);
	}
	@Test
	public void notNullTestForGetAccessoriesOfDeviceDao() {
		try {
			 DaoUtils.convertCoherenceAccesoryToAccessory(CommonMethods.getCommercialProduct2(), CommonMethods.getPriceForAccessory());
		} catch (Exception e) {

		}
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTileWithoutOfferCode() throws JsonParseException, JsonMappingException, IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceDAOMock.getProductGroupsByType("DEVICE_PAYM")).willReturn(CommonMethods.getGroup());
		given(deviceDAOMock.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		Collection<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct5());
		given(deviceDAOMock.getListCommercialProductRepositoryByLeadMemberId(Matchers.anyList())).willReturn(a);
		given(deviceDAOMock.getJourneyTypeCompatibleOfferCodes(Matchers.anyString())).willReturn(CommonMethods.getModel());
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
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class))
						.willReturn(obj);

		deviceController.cacheDeviceTile();

	}
	
}
