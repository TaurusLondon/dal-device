package com.vf.uk.dal.device.integration.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
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
import com.vf.uk.dal.common.context.ServiceContext;
import com.vf.uk.dal.common.context.URLParamContext;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.common.registry.client.Utility;
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
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.entity.SourcePackageSummary;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.CacheDeviceService;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.AccessoriesAndInsurancedaoUtils;
import com.vf.uk.dal.device.utils.CacheDeviceDaoUtils;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ListOfDeviceDetailsDaoUtils;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleAndHardwareRequest;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;

/**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)

public class DeviceIntegrationTest {

	@Autowired
	DeviceESHelper deviceESHelper;

	@MockBean
	DeviceDao deviceDAOMock;

	@Autowired
	DeviceDetailsController deviceDetailsController;

	@Autowired
	AccessoryInsuranceController accessoryInsuranceController;

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

	@Autowired
	DeviceController deviceController;

	@Autowired
	DeviceService deviceService;

	@MockBean
	DeviceRecommendationService deviceRecomServiceMock;

	@Autowired
	CacheDeviceAndReviewController cacheDeviceAndReviewController;

	@Autowired
	CacheDeviceService CacheDeviceService;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		String jsonString = new String(Utility.readFile("\\rest-mock\\COMMON-V1.json"));
		CurrentJourney obj = new ObjectMapper().readValue(jsonString, CurrentJourney.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "093353" + "&journeyType=" + null, BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(restTemplate.getForObject(
				"http://COMMON-V1/common/journey/" + "c1a42269-6562-4c96-b3be-1ca2a6681d57" + "/queries/currentJourney",
				CurrentJourney.class)).willReturn(obj);
		given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(CommonMethods.getMemPro());
		given(response.getCommercialProduct(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel().get(0));
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialBundleFromCommercialBundleRepository());
		given(response.getListOfMerchandisingPromotionFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerchandisingPromotion_One());
		String jsonString1 = new String(Utility.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",
				CommonMethods.getRecommendedDeviceListRequest("7741655541", "109381"),
				RecommendedProductListResponse.class)).willReturn(obj1);
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());

	}

	@Test
	public void notNullTestForgetDeviceDetailsWithJourneyType() {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceDetailsController.getDeviceDetails("083921", "", "");
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void invalidInputTestForgetDeviceDetails() throws Exception {
		DeviceDetails deviceDetails = new DeviceDetails();
		try {
			deviceDetails = deviceDetailsController.getDeviceDetails(null, null, null);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails.getDeviceId());
	}

	@Test
	public void invalidInputTestForgetDeviceDetails_One() throws Exception {
		try {
			deviceDetailsController.getDeviceDetails("1234", null, null);
		} catch (Exception e) {

		}
	}

	@Test
	public void notNullTestForgetDeviceDetails() {
		DeviceDetails deviceDetails = new DeviceDetails();

		deviceDetails = deviceDetailsController.getDeviceDetails("093353", "", "");
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void notNullTestForgetDeviceDetails_One() {
		DeviceDetails deviceDetails = new DeviceDetails();
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=093353",
				BundleDetailsForAppSrv.class)).willReturn(CommonMethods.getCoupledBundleListForDevice());
		deviceDetails = deviceDetailsController.getDeviceDetails("093353", null, null);
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void InvalidTestForgetDeviceDetailsWithJourneyTypePAYG() {

		try {
			deviceDetailsController.getDeviceDetails("088417", "Upgrade", null);
		} catch (Exception e) {
			Assert.assertEquals(
					"com.vf.uk.dal.common.exception.ApplicationException: JourneyType is not compatible for given DeviceId",
					e.toString());
		}
	}

	@Test
	public void InvalidTestForgetDeviceDetailsWithOfferCodePAYG() {
		try {
			deviceDetailsController.getDeviceDetails("088417", "abcd", "W_HH_OC_01");
		} catch (Exception e) {
		}
	}

	@Test
	public void notNullTestForgetDeviceDetailsPAYG() {

		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceDetailsController.getDeviceDetails("088417", "abcd", null);
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void notNullTestForGetAccessoriesOfDevice() {
		try {
			List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
			accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("093353", "Upgrade",
					"W_HH_PAYM_OC_02");
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

			accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice(null, null, null);
		} catch (Exception e) {
			try {

				accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("1234", null, null);
			} catch (Exception ex) {
			}
		}
		Assert.assertEquals(0, accessoryDetails.size());
	}

	@Test
	public void notNullTestForgetAccessoriesOfDevice() {
		try {
			BundleDeviceAndProductsList deviceAndProductsList = new BundleDeviceAndProductsList();
			List<String> accessory = new ArrayList<>();
			accessory.add("093329");
			accessory.add("085145");
			deviceAndProductsList.setAccessoryList(accessory);
			deviceAndProductsList.setDeviceId("093353");
			deviceAndProductsList.setExtraList(new ArrayList<>());
			deviceAndProductsList.setOfferCode(null);
			deviceAndProductsList.setPackageType(null);
			given(registry.getRestTemplate()).willReturn(restTemplate);
			given(restTemplate.postForObject("http://PRICE-V1/price/product", deviceAndProductsList,
					PriceForProduct.class)).willReturn(CommonMethods.getPriceForProduct_For_GetAccessories());

			given(response.getListOfGroupFromJson(Matchers.anyObject()))
					.willReturn(CommonMethods.getListOfProductGroupForAccessories());
			given(response.getCommercialProductFromJson(Matchers.anyObject()))
					.willReturn(CommonMethods.getListOfCommercialProductsForAccessory());
			given(response.getCommercialProduct(Matchers.anyObject()))
					.willReturn(CommonMethods.getCommercialProductByDeviceIdForAccessory());
			Assert.assertNotNull(accessoryInsuranceController.getAccessoriesOfDevice("093353", null, null));
		} catch (Exception e) {

		}
	}

	@Test
	public void notNullTestForGetInsuranceById() {

		try {
			Insurances insurance = null;
			insurance = accessoryInsuranceController.getInsuranceById("093353", null);
			Assert.assertNotNull(insurance);
			insurance = accessoryInsuranceController.getInsuranceById("93353", null);
		} catch (Exception e) {
		}
	}

	@Test
	public void notNullTestForGetInsuranceByIdWithJourneyType() {
		/*
		 * given(deviceDAOMock.getCommercialProductByProductId(Matchers.
		 * anyString()))
		 * .willReturn(CommonMethods.getCommercialProductForInsurance());
		 * given(deviceDAOMock.getGroupByProdGroupName(Matchers.anyString(),
		 * Matchers.anyString()))
		 * .willReturn(CommonMethods.getGropuFromProductGroups());
		 * given(deviceDAOMock.getCommercialProductsList(Matchers.anyList()))
		 * .willReturn(Arrays.asList(CommonMethods.
		 * getCommercialProductForInsurance()));
		 */

		try {
			Insurances insurance = null;
			Map<String, String> queryparams = new HashMap<String, String>();
			queryparams.put("deviceId", "093353");
			queryparams.put("journeyType", "upgrade");
			Map<String, String> queryparamsInvalid = new HashMap<String, String>();
			queryparamsInvalid.put("deviceId", "093353");
			queryparamsInvalid.put("journeyType", "test");
			insurance = accessoryInsuranceController.getInsuranceById("093353", "upgrade");
			Assert.assertNotNull(insurance);
			insurance = accessoryInsuranceController.getInsuranceById("093353", "test");
		} catch (Exception e) {
		}
	}

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
	public void notNullTestForGetDeviceList() {
		FacetedDevice deviceDetailsList = null;
		try {
			// given()
			PaginationCriteria paginationCriteria = new PaginationCriteria(9, 0);

			ServiceContext.setURLParamContext(new URLParamContext("Priority", "", null, paginationCriteria));
			given(response.getListOfProductGroupModel(Matchers.anyObject()))
					.willReturn(CommonMethods.getListOfProductGroupMode());
			given(response.getFacetField(Matchers.anyObject())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
					.willReturn(CommonMethods.getModel());
			given(response.getListOfOfferAppliedPriceModel(Matchers.anyObject()))
					.willReturn(CommonMethods.getOfferAppliedPriceModel());
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
			given(restTemplate.postForObject("http://PROMOTION-V1/promotion/queries/ForBundleAndHardware", request,
					BundleAndHardwarePromotions[].class)).willReturn(obj);
			String url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655541/sourcePackageSummary";
			given(restTemplate.getForObject(url, SourcePackageSummary.class))
					.willReturn(CommonMethods.getSourcePackageSummary());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", null, null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			/*
			 * given(deviceDAOMock.getBundleDetails(Matchers.anyList()))
			 * .willReturn(CommonMethods.getBundleModelListForBundleList());
			 */
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
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655541", "Great Camera", "SecondLine", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655542/sourcePackageSummary";
			given(restTemplate.getForObject(url, SourcePackageSummary.class))
					.willReturn(CommonMethods.getSourcePackageSummary());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "SecondLine", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "Acquisition", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
		} catch (Exception e) {
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
		} catch (Exception e) {
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, null);
		} catch (Exception e) {
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", null, null, "W_HH_PAYM_01", null);
		} catch (Exception e) {
		}
		ServiceContext.urlParamContext.remove();

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
	public void nullTestForGetDeviceList_One() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList("Handset1", "apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceList_Two() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList("Handset", null, "iPhone 7", "DEVICE_PAYM", "Priority", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceList_Three() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList(null, "apple", "iphone 7", "DEVICE_PAYM", "Priority", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceList_Four() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE", "Priority", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceListForGroupType() {
		FacetedDevice deviceLists = null;
		try {
			deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", null, "Priority", 1, 2, "32 GB",
					"White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {

		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void testGetDeviceListForErrors() {

		try {
			deviceService.getDeviceList("productClass", "listOfMake", "model", "groupType", null, 2, 2, "capacity",
					"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn",
					false);
		} catch (Exception e) {
			try {
				deviceService.getDeviceList(null, "listOfMake", "model", "groupType", "Sort", 2, 2, "capacity",
						"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
						"msisdn", false);
			} catch (Exception ew) {
				try {
					deviceService.getDeviceList("PC", "listOfMake", "model", "groupType", "Sort", 2, 2, "capacity",
							"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
							"msisdn", false);
				} catch (Exception ed) {
					try {
						deviceService.getDeviceList("HANDSET", "listOfMake", "model", "ksjdbhf", "Sort", 2, 2,
								"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
								(float) 14.0, "offerCode", "msisdn", false);
					} catch (Exception er) {
						try {
							deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort", 2, 2,
									"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
									(float) 14.0, "offerCode", "msisdn", false);
						} catch (Exception eq) {
							try {
								deviceService.getDeviceList("HANDSET", "listOfMake", "model", null, "Sort", 2, 2,
										"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
										(float) 14.0, "offerCode", "msisdn", false);
							} catch (Exception ek) {
								try {
									deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort",
											2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures",
											"journeyType", (float) 14.0, "offerCode", "", true);
								} catch (Exception lkj) {
									try {
										deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM",
												"Sort", 2, 2, "capacity", "colour", "operatingSystem",
												"mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
												"msisdn", true);
									} catch (Exception edfg) {
										try {
											deviceService.getDeviceList(null, "listOfMake", "model", "DEVICE_PAYM",
													"Rating", 2, 2, "capacity", "colour", "operatingSystem",
													"mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
													"msisdn", true);
										} catch (Exception ex) {
											try {
												deviceService.getDeviceList("HandsetHandset", "listOfMake", "model",
														"DEVICE_PAYM", "Rating", 2, 2, "capacity", "colour",
														"operatingSystem", "mustHaveFeatures", "conditionalaccept",
														(float) 14.0, "offerCode", "msisdn", true);
											} catch (Exception ex1) {
												try {
													deviceService.getDeviceList("Handset", "listOfMake", "model",
															"DEVICE_PAYMDEVICE_PAYM", "Rating", 2, 2, "capacity",
															"colour", "operatingSystem", "mustHaveFeatures",
															"conditionalaccept", (float) 14.0, "offerCode", "msisdn",
															true);
												} catch (Exception ex2) {
													try {
														deviceService.getDeviceList("Handset", "listOfMake", null,
																"DEVICE_PAYM", "Rating", 2, 2, "capacity", "colour",
																"operatingSystem", "mustHaveFeatures",
																"conditionalaccept", (float) 14.0, "offerCode",
																"msisdn", true);
													} catch (Exception ex3) {
														try {
															deviceService.getDeviceList("Handset", "listOfMake",
																	"model", "DEVICE_PAYM", "Rating", 2, 2, "capacity",
																	"colour", "operatingSystem", "mustHaveFeatures",
																	null, (float) 14.0, "offerCode", "msisdn", true);
														} catch (Exception ex4) {
															try {
																deviceService.getDeviceList("Handset", "listOfMake",
																		"model", "DEVICE_PAYM", "Rating", 2, 2,
																		"capacity", "colour", "operatingSystem",
																		"mustHaveFeatures", "conditionalaccept",
																		(float) 14.0, "offerCode", null, true);
															} catch (Exception ex5) {

															}
														}
													}
												}
											}

										}
									}

								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance() {
		given(response.getListOfProductGroupModel(Matchers.anyObject()))
				.willReturn(CommonMethods.getListOfProductGroupMode());
		given(response.getFacetField(Matchers.anyObject())).willReturn(CommonMethods.getListOfFacetField());
		given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());

		FacetedDevice deviceLists = null;
		try {
			deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9,
					"32 GB", "White", "iOS", "Great Camera", null, null, null, "447582367723", true);
		} catch (Exception e) {

		}
		Assert.assertNotNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptanceForexception() {
		try {
			given(response.getListOfProductGroupModel(Matchers.anyObject()))
					.willReturn(CommonMethods.getListOfProductGroupModeForNullLeadPlan());
			given(response.getFacetField(Matchers.anyObject())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
					"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
		} catch (Exception e) {
		}
		try {
			given(response.getListOfProductGroupModel(Matchers.anyObject()))
					.willReturn(CommonMethods.getListOfProductGroupMode());
			given(response.getFacetField(Matchers.anyObject())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(Collections.emptyList());
			deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
					"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
		} catch (Exception e) {
		}
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance_One() {
		given(response.getListOfProductGroupModel(Matchers.anyObject()))
				.willReturn(CommonMethods.getListOfProductGroupMode());
		given(response.getFacetField(Matchers.anyObject())).willReturn(CommonMethods.getListOfFacetField());
		given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
		given(response.getListOfOfferAppliedPriceModel(Matchers.anyObject()))
				.willReturn(CommonMethods.getOfferAppliedPriceModel());
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerChandisingPromotion_One());

		try {
			deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9,
					"32 GB", "White", "iOS", "Great Camera", "Upgrade", null, "W_HH_PAYM_OC_01", "447582367723", true);
		} catch (Exception e) {

		}
		// Assert.assertNotNull(deviceLists);
	}

	@Test(expected = Exception.class)
	public void nullTestForGetDeviceListForExcption() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "JourneyType", null, null, "447582367723", true);

	}

	@Test(expected = Exception.class)
	public void nullTestForGetDeviceListForExcption_One() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerChandisingPromotion());

		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "Upgrade", null, "offerCode", "447582367723", true);

	}

	@Test(expected = Exception.class)
	public void nullTestForGetDeviceListForExcption_Three() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "SecondLine", null, "offerCode", "447582367723", true);

	}

	@Test(expected = Exception.class)
	public void nullTestForGetDeviceListForExcption_Four() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "SecondLine", null, "W_HH_PAYM_02", null, true);

	}

	@Test
	public void notNullTestForgetGroupNameWithListOfProduct() {
		given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
		List<String> variantsList = new ArrayList<String>();
		variantsList.add("093353|1");
		variantsList.add("092660|5");
		deviceService.getGroupNameWithListOfProduct("SecondLine", variantsList, new HashMap<String, String>(),
				CommonMethods.getProductGroupModel().get(0), new ArrayList<String>());

	}

	@Test
	public void notNullTestForgetDeviceTileById() {
		try {
			deviceController.getDeviceTileById("093329", "Upgrade", "W_HH_SIMONLY");
		} catch (Exception e) {

		}
	}

	@Test
	public void notNullTestForgetDeviceTileByIdForTry() {
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "092572" + "&journeyType=" + "Upgrade", BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(response.getCommercialProduct(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfVariant().get(0));
		try {
			deviceController.getDeviceTileById("093329", "Upgrade", "W_HH_SIMONLY");
		} catch (Exception e) {

		}

	}

	@Test
	public void notNullconvertCoherenceDeviceToDeviceTile() {
		DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile((long) 2.0,
				CommonMethods.getCommercialProduct(), CommonMethods.getCommercialBundle(),
				CommonMethods.getPriceForBundleAndHardware().get(0), null, "DEVICE_PAYM", true, null);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTile() throws JsonParseException, JsonMappingException, IOException {
		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		List<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct_Five());
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
		PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		try {
			given(deviceDAOMock.getBazaarVoice(Matchers.anyString()))
					.willReturn(CommonMethods.getReviewsJsonObject().get(0));
		} catch (Exception e) {
		}
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId("110154");
		bundle.setHardwareId("123");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		requestForBundleAndHardware.setOfferCode("W_HH_PAYM_OC_01");
		/*
		 * given(restTemplate.postForObject(
		 * "http://PRICE-V1/price/calculateForBundleAndHardware",
		 * requestForBundleAndHardware, PriceForBundleAndHardware[].class))
		 * .willReturn(obj);
		 */
		given(restTemplate.postForObject(Matchers.anyString(), Matchers.any(), Matchers.anyObject())).willReturn(obj);

		cacheDeviceAndReviewController.cacheDeviceTile();

	}

	@Test
	public void notNullTestForcacheDeviceTileWithoutLeadMember() throws IOException {
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));

		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		CommercialProduct com = CommonMethods.getCommercialProductForCacheDeviceTile();
		com.setLeadPlanId(null);
		CommercialProduct com1 = CommonMethods.getCommercialProductForCacheDeviceTile_One();
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
		PriceForBundleAndHardware[] obj1 = mapper.readValue(jsonString1, PriceForBundleAndHardware[].class);
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
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj1);
		/*
		 * given(restTemplate.postForObject(Matchers.anyString(),
		 * Matchers.any(), Matchers.anyObject())) .willReturn(obj);
		 */

		// given(deviceDAOMock.getStockAvailabilityByMemberId(Matchers.anyString())).willReturn(CommonMethods.getStockAvailability());

		String jsonString = new String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		ObjectMapper mapper1 = new ObjectMapper();
		mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BundleDetailsForAppSrv obj = mapper1.readValue(jsonString, BundleDetailsForAppSrv.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123",
				BundleDetailsForAppSrv.class)).willReturn(obj);
		try {
			cacheDeviceAndReviewController.cacheDeviceTile();
		} catch (Exception e) {
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
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		Collection<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct_Five());
		given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getModel());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(Utility.readFile("\\TEST-MOCK\\PRICE_FOR_PAYG.json"));
		PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		/*
		 * Map<String,String> ratingmap=new HashMap<>(); ratingmap.put("123",
		 * "3.7"); ratingmap.put("23", "3.7"); ratingmap.put("sku124", "3.9");
		 * ratingmap.put("sku24", "3.9");
		 */
		given(deviceDAOMock.getBazaarVoice(Matchers.anyString()))
				.willReturn(CommonMethods.getReviewsJsonObject().get(0));
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId(null);
		bundle.setHardwareId("123");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		requestForBundleAndHardware.setOfferCode(null);
		requestForBundleAndHardware.setPackageType(null);
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj);
		cacheDeviceAndReviewController.cacheDeviceTile();
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTileWithoutOfferCode()
			throws JsonParseException, JsonMappingException, IOException {
		ServiceContext.urlParamContext.remove();
		List<FilterCriteria> fcList = new ArrayList<FilterCriteria>();
		fcList.add(new FilterCriteria("groupType", FilterOperator.EQUALTO, "DEVICE_PAYM"));
		ServiceContext.setURLParamContext(new URLParamContext("", "", fcList, null));
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		List<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct_Five());
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
		PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		try {
			given(deviceDAOMock.getBazaarVoice(Matchers.anyString()))
					.willReturn(CommonMethods.getReviewsJsonObject().get(0));
		} catch (Exception e) {
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
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj);

		cacheDeviceAndReviewController.cacheDeviceTile();
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
			}
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void testForIlsPriceWithOfferCodeAndJourney() {
		try {
			List<String> listOfOfferCodesForUpgrade = new ArrayList<>();
			listOfOfferCodesForUpgrade.add("W_HH_OC_01");
			listOfOfferCodesForUpgrade.add("W_HH_OC_02");
			List<String> listOfSecondLineOfferCode = new ArrayList<>();
			listOfSecondLineOfferCode.add("W_HH_OC_Paym_01");
			listOfSecondLineOfferCode.add("W_HH_OC_Paym_02");
			Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap = new HashMap<>();
			List<BundleAndHardwareTuple> listOfBundleHardwareTruple = new ArrayList<>();
			BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
			bundleAndHardwareTuple.setBundleId("110154");
			bundleAndHardwareTuple.setHardwareId("093353");
			BundleAndHardwareTuple bundleAndHardwareTuple1 = new BundleAndHardwareTuple();
			bundleAndHardwareTuple1.setBundleId("110163");
			bundleAndHardwareTuple1.setHardwareId("095552");
			listOfBundleHardwareTruple.add(bundleAndHardwareTuple);
			listOfBundleHardwareTruple.add(bundleAndHardwareTuple1);
			bundleHardwareTroupleMap.put("W_HH_OC_01", listOfBundleHardwareTruple);
			bundleHardwareTroupleMap.put("W_HH_OC_02", listOfBundleHardwareTruple);
			bundleHardwareTroupleMap.put("W_HH_OC_Paym_01", listOfBundleHardwareTruple);
			bundleHardwareTroupleMap.put("W_HH_OC_Paym_02", listOfBundleHardwareTruple);

			String jsonString = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
			given(registry.getRestTemplate()).willReturn(restTemplate);
			given(restTemplate.postForObject(Matchers.anyString(), Matchers.anyObject(), Matchers.anyObject()))
					.willReturn(obj);
			CacheDeviceService.getIlsPriceWithOfferCodeAndJourney(listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
					bundleHardwareTroupleMap, new HashMap<>());
		} catch (IOException e) {
		}

	}

	@Test
	public void testForDb() {
		CacheDeviceService.updateCacheDeviceToDb("12055", "457892");
	}

	@Test
	public void testForIndexPrecalData() {
		CacheDeviceService.indexPrecalData(CommonMethods.getDevicePreCalculatedDataFromSolr());
	}

	@Test
	public void testForGetNonUpgradeLeadPlanIdForPaymCacheDevice() {
		Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
		nonLeadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		commercialBundleMap.put("110163", CommonMethods.getCommercialBundleForcacheDevice());
		CacheDeviceService.getNonUpgradeLeadPlanIdForPaymCacheDevice(nonLeadPlanIdPriceMap, new HashMap<>(),
				commercialBundleMap, new ArrayList<>(), "093353", "Apple-Iphone");
	}

	@Test
	public void testForGetUpgradeLeadPlanIdForCacheDevice() {
		Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
		nonLeadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		commercialBundleMap.put("110163", CommonMethods.getCommercialBundleForcacheDevice());
		CacheDeviceService.getUpgradeLeadPlanIdForCacheDevice(nonLeadPlanIdPriceMap, commercialBundleMap, "093353");
	}

	@Test
	public void testForGetDevicePrecaldataForPaymCacheDeviceTile() {
		Set<String> listOfOfferCodes = new HashSet<>();
		listOfOfferCodes.add("W_HH_OC_01");
		listOfOfferCodes.add("W_HH_OC_02");
		listOfOfferCodes.add("W_HH_OC_Paym_01");
		listOfOfferCodes.add("W_HH_OC_Paym_02");
		Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
		nonLeadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		Map<String, PriceForBundleAndHardware> leadPlanIdPriceMap = new HashMap<>();
		leadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile().get(0));
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		commercialBundleMap.put("110163", CommonMethods.getCommercialBundleForcacheDevice());
		Map<String, String> listOfLeadPlanId = new HashMap<>();
		listOfLeadPlanId.put("093353", "110163");
		Map<String, List<String>> listOfCimpatiblePlanMap = new HashMap<>();
		List<String> compatiblePlans = new ArrayList<>();
		compatiblePlans.add("110154");
		compatiblePlans.add("110163");
		listOfCimpatiblePlanMap.put("093353", compatiblePlans);
		Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap = new HashMap<>();
		List<BundleAndHardwareTuple> listOfBundleHardwareTruple = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId("110154");
		bundleAndHardwareTuple.setHardwareId("093353");
		BundleAndHardwareTuple bundleAndHardwareTuple1 = new BundleAndHardwareTuple();
		bundleAndHardwareTuple1.setBundleId("110163");
		bundleAndHardwareTuple1.setHardwareId("095552");
		listOfBundleHardwareTruple.add(bundleAndHardwareTuple);
		listOfBundleHardwareTruple.add(bundleAndHardwareTuple1);
		bundleHardwareTroupleMap.put("W_HH_OC_01", listOfBundleHardwareTruple);
		bundleHardwareTroupleMap.put("W_HH_OC_02", listOfBundleHardwareTruple);
		bundleHardwareTroupleMap.put("W_HH_OC_Paym_01", listOfBundleHardwareTruple);
		bundleHardwareTroupleMap.put("W_HH_OC_Paym_02", listOfBundleHardwareTruple);
		List<String> l = new ArrayList<>();
		l.add("093353");
		Map<String, String> map = new HashMap<>();
		map.put("093353", "Y");
		List<DevicePreCalculatedData> d = new ArrayList<>();
		Map<String, String> groupMap = new HashMap<>();
		groupMap.put("093353", "Apple-iphone&&2");
		CacheDeviceService.getDevicePrecaldataForPaymCacheDeviceTile("DEVICE_PAYM", l, d, listOfOfferCodes, map, map,
				groupMap, leadPlanIdPriceMap, nonLeadPlanIdPriceMap, nonLeadPlanIdPriceMap, commercialBundleMap,
				listOfLeadPlanId, listOfCimpatiblePlanMap,
				CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile(), bundleHardwareTroupleMap,
				nonLeadPlanIdPriceMap, "093353");
	}

	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListingNullLeadPlanId() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DevicePreCalculatedData productGroupForDeviceListing = CacheDeviceDaoUtils
				.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353", null, "groupname", "groupId",
						CommonMethods.getPrice(), CommonMethods.getleadMemberMap(), iLSPriceMap,
						CommonMethods.getleadMemberMap(), null, Constants.STRING_DEVICE_PAYG);

		Assert.assertNotNull(productGroupForDeviceListing);
	}

	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListing() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DevicePreCalculatedData productGroupForDeviceListing = CacheDeviceDaoUtils
				.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353", "leadPlanId", "groupname",
						"groupId", CommonMethods.getPrice(), CommonMethods.getleadMemberMap(), iLSPriceMap,
						CommonMethods.getleadMemberMap(), "upgradeLeadPlanId", Constants.STRING_DEVICE_PAYM);

		Assert.assertNotNull(productGroupForDeviceListing);
	}

	@Test
	public void testForGetListOfOfferAppliedPriceDetails() {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.OfferAppliedPriceDetails> listOfferAppliedPriceDetails = CacheDeviceDaoUtils
				.getListOfOfferAppliedPriceDetails(CommonMethods.getOfferAppliedPriceDetails());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
	}

	@Test
	public void testForGetPriceForSolr() {
		com.vf.uk.dal.device.datamodel.merchandisingpromotion.PriceInfo listOfferAppliedPriceDetails = CacheDeviceDaoUtils
				.getPriceForSolr(CommonMethods.getPriceinforForSorl());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
	}

	@Test
	public void testForGetListOfSolrMedia() {
		List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.Media> listOfferAppliedPriceDetails = CacheDeviceDaoUtils
				.getListOfSolrMedia(CommonMethods.getmediaForSorl());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
	}

	@Test
	public void testForGetListOfIlsPriceWithoutOfferCode() {
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("SecondLine", iLSPriceMap);
		CacheDeviceDaoUtils.getListOfIlsPriceWithoutOfferCode("093353", ilsPriceForBundleAndHardwareMap);

		// Assert.assertNotNull(listOfferAppliedPriceDetails);
	}

	@Test
	public void testForGetListOfOfferAppliedPrice() {
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> listOfeerCode = new HashMap<>();
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("W_HH_Paym_02", iLSPriceMap);
		listOfeerCode.put("SecondLine", ilsPriceForBundleAndHardwareMap);
		CacheDeviceDaoUtils.getListOfOfferAppliedPrice("093353", listOfeerCode);

		// Assert.assertNotNull(listOfferAppliedPriceDetails);
	}

	@Test
	public void testForGetPriceInfoForSolr() {

		CacheDeviceDaoUtils.getPriceInfoForSolr(CommonMethods.getOfferAppliedPrice().get(0), new HashMap<>());

	}

	@Test
	public void testForGetMerchandising() {
		List<String> promoteAs = new ArrayList<>();
		promoteAs.add("handset-promotion");
		deviceESHelper.getMerchandising(promoteAs);
	}

	@Test
	public void testForCalculateDiscount() {
		Iterator<PriceForBundleAndHardware> iterator = CommonMethods.getOfferAppliedPrice().iterator();
		iterator.next();
		DeviceServiceImplUtility.calculateDiscount(2.0, iterator, CommonMethods.getOfferAppliedPrice().get(0));

	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new HashMap<>();
		listOfOfferAppliedPrice.put("093353", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		listOfOfferAppliedPrice.put("092660", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null, null,
				listOfOfferAppliedPrice, "W_HH_OC_02", groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList_One() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new HashMap<>();
		listOfOfferAppliedPrice.put("093353", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		listOfOfferAppliedPrice.put("092660", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null, null,
				listOfOfferAppliedPrice1, null, groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList_Three() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null, null,
				listOfOfferAppliedPrice1, null, groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice1, null, productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList_Four() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new HashMap<>();
		listOfOfferAppliedPrice.put("093353", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		listOfOfferAppliedPrice.put("092660", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null, null,
				listOfOfferAppliedPrice1, "W_HH_OC_02", groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList_Five() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null, null,
				listOfOfferAppliedPrice1, "W_HH_OC_02", groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice1, "Upgrade", productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceListPayG() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		/*
		 * Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 =
		 * new HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>());
		 */
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYG", null, null, null,
				null, groupNameWithProdId, null, null, isLeadMemberFromSolr, null, Constants.JOURNEY_TYPE_ACQUISITION,
				productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList_Two() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null, null,
				listOfOfferAppliedPrice1, null, groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice1, "Upgrade", productGroupdetailsMap);
		Assert.assertNotNull(deviceList);
	}

	@Deprecated
	@Test
	public void NotNullTestForDateValidation() {
		Date startDateTime = new Date("15/05/2017");
		Date endDateTime = new Date("15/05/2071");
		DeviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, true);
	}

	@Test
	public void NotNullTestForGetBundlePriceBasedOnDiscountDuration() {
		DeviceTilesDaoUtils.getBundlePriceBasedOnDiscountDuration(
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice(), Constants.FULL_DURATION_DISCOUNT);
		DeviceTilesDaoUtils.getBundlePriceBasedOnDiscountDuration(
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice(), Constants.LIMITED_TIME_DISCOUNT);
	}

	@Test
	public void NotNullTestForPopulateMerchandisingPromotions() {
		PriceForBundleAndHardware bundlePrice = CommonMethods.getPriceForBundleAndHardware().get(0);
		bundlePrice.getBundlePrice().setMerchandisingPromotions(null);
		DeviceTilesDaoUtils.populateMerchandisingPromotions(bundlePrice,
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice());
	}

	@Test
	public void NotNullTestForGetBundleAndHardwarePriceFromSolrWithoutOfferCode() {
		DeviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(null,
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(0), "110154");
		DeviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(null,
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(1), "110154");
	}

	@Test
	public void NotNullTestForgetDeviceIlsJourneyAwarePriceMap() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DeviceUtils.getDeviceIlsJourneyAwarePriceMap(new HashMap<>(), CommonMethods.getOfferAppliedPrice().get(0));
		DeviceUtils.getDeviceIlsJourneyAwarePriceMap(iLSPriceMap, CommonMethods.getOfferAppliedPrice().get(0));
	}

	@Test
	public void testForGetEntireIlsJourneyAwareMap() {
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> map = new HashMap<>();
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("SecondLine", iLSPriceMap);
		DeviceUtils.getEntireIlsJourneyAwareMap(map, ilsPriceForBundleAndHardwareMap);
	}

	@Test
	public void testForGetMinimumPriceMap() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("Apple", CommonMethods.getOfferAppliedPrice());
		DeviceUtils.getMinimumPriceMap(new HashMap<>(), iLSPriceMap);
	}

	@Test
	public void testForGetmonthlyPriceFormPriceForPayG() {
		PriceForBundleAndHardware price = CommonMethods.getOfferAppliedPrice().get(0);
		DeviceUtils.getmonthlyPriceFormPriceForPayG(price);
		price.getHardwarePrice().getOneOffPrice().setGross("20");
		price.getHardwarePrice().getOneOffDiscountPrice().setGross("20");
		DeviceUtils.getmonthlyPriceFormPriceForPayG(price);
	}

	@Test
	public void testForGetUpdateDeviceratingForCacheDevice() {
		Map<String, String> ratingsReviewMap = new HashMap<>();
		ratingsReviewMap.put("sku93353", "3.275");

		DeviceUtils.updateDeviceratingForCacheDevice(ratingsReviewMap, CommonMethods.getDevicePreCal().get(0));
	}

	@Test
	public void testForLeastMonthlyPriceForpayG() {
		DeviceUtils.leastMonthlyPriceForpayG(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
	}

	@Test
	public void NotNullTestForDaoUtilsconvertCoherenceDeviceToDeviceDetails() {
		DeviceDetails deviceDetails = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceDetails(
				CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware(),
				CommonMethods.getListOfBundleAndHardwarePromotions());
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void NotNullTestForSetPriceMerchandisingPromotion() {
		AccessoriesAndInsurancedaoUtils.setPriceMerchandisingPromotion(
				CommonMethods.getForUtilityPriceForBundleAndHardware().get(0).getHardwarePrice());
	}

	@Test
	public void NotNullTestForConvertGroupProductToProductGroupDetails() {
		ListOfDeviceDetailsDaoUtils.convertGroupProductToProductGroupDetails(CommonMethods.getProductGroupModel());
	}

	@Test
	public void NotNullTestForGetAscendingOrderForBundlePrice_One() {
		ListOfDeviceDetailsDaoUtils l = new ListOfDeviceDetailsDaoUtils();
		l.getAscendingOrderForBundlePrice1(CommonMethods.getBundleHeaderList("SIMO"));
	}

	@Test
	public void NotNullTestForgetAscendingOrderForBundleHeaderPrice() {
		deviceServiceCommonUtility.getAscendingOrderForBundleHeaderPrice(CommonMethods.getBundleHeaderList("SIMO"));
	}

	@Test
	public void NotNullTestForGetAscendingOrderForBundlePrice() {
		deviceServiceCommonUtility.getAscendingOrderForBundlePrice(CommonMethods.getPriceForBundleAndHardwareSorting());
	}

	@Test
	public void NotNullTestForGetAscendingOrderForOneoffPrice() {
		deviceServiceCommonUtility.getAscendingOrderForOneoffPrice(CommonMethods.getPriceForBundleAndHardwareSorting());
	}

	@Test
	public void NotNullTestForvalidateAllParameters() {
		try {
			Validator.validateAllParameters(null, "model", "groupType", "journeyType");
		} catch (Exception e) {
		}
		try {
			Validator.validateAllParameters("make", null, "groupType", "journeyType");
		} catch (Exception e) {
		}
		try {
			Validator.validateAllParameters("make", "model", null, "journeyType");
		} catch (Exception e) {
		}
		try {
			Validator.validateAllParameters("make", "model", "grouptype", "journeyType");
		} catch (Exception e) {
		}
		try {
			Validator.validateAllParameters("make", "model", "Device_PAYG", "Upgrade");
		} catch (Exception e) {
		}
		try {
			Validator.validateAllParameters("make", "model", "Device_PAYG", "SecondLine");
		} catch (Exception e) {
		}
		try {
			Validator.validateAllParameters("make", "model", "Device_PAYG", "");
		} catch (Exception e) {
		}
	}

	@Test
	public void NotNullTestForvalidateCreditLimitValue() {
		Validator.validateCreditLimitValue("abcd");
		Validator.validateCreditLimitValue("4");
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("productId", "093353");
		queryParams.put("deviceId", "093353");
		Validator.validateGetDeviceList(queryParams);
		Validator.validateGetListOfDeviceTile(queryParams);
		Validator.validateJourneyType("upgrade");
		Validator.validateJourneyType("journeyType");
		Validator.validateProduct(queryParams);
		Validator.validateProductGroup(queryParams);
		try {
			Validator.validatePageSize(-1, 2);
		} catch (Exception e) {
		}
	}
}
