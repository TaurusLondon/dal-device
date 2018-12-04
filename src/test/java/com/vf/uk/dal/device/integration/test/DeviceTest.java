package com.vf.uk.dal.device.integration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.bundle.BundleHeader;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.client.entity.customer.SourcePackageSummary;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundleDeviceAndProductsList;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.client.entity.price.RequestForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwareRequest;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.model.Insurances;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.ProductGroup;
import com.vf.uk.dal.device.model.ProductGroupDetailsForDeviceList;
import com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion;
import com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.solr.DevicePreCalculatedData;
import com.vf.uk.dal.device.service.CacheDeviceService;
import com.vf.uk.dal.device.service.DeviceRecommendationService;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.AccessoriesAndInsurancedaoUtils;
import com.vf.uk.dal.device.utils.CacheDeviceDaoUtils;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ListOfDeviceDetailsDaoUtils;
import com.vf.uk.dal.device.utils.Validator;

/**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)

public class DeviceTest {

	public static final String LIMITED_TIME_DISCOUNT = "limited_time";
	public static final String FULL_DURATION_DISCOUNT = "full_duration";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";

	@Autowired
	DeviceESHelper deviceESHelper;

	@MockBean
	DeviceDao deviceDAOMock;

	@Autowired
	DeviceDetailsController deviceDetailsController;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	AccessoryInsuranceController accessoryInsuranceController;

	@MockBean
	ResponseMappingHelper response;

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

	@Value("${cdn.domain.host}")
	private String cdnDomain;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "093353" + "&journeyType=" + null, BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(CommonMethods.getMemPro());
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel().get(0));
		given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialBundleFromCommercialBundleRepository());
		given(response.getListOfMerchandisingPromotionFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerchandisingPromotion_One());
		String jsonString1 = new String(CommonMethods.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",
				CommonMethods.getRecommendedDeviceListRequest("7741655541", "109381"),
				RecommendedProductListResponse.class)).willReturn(obj1);
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());

	}

	@Test
	public void notNullTestForGetDeviceDetailsWithJourneyType() {
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceDetailsController.getDeviceDetails("083921", "", "");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals(deviceDetails.getDeviceId(), "092572");
		Assert.assertEquals(deviceDetails.getLeadPlanId(), "110104");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getMake(), "apple");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getModel(), "iphone-7-plus");
		Assert.assertEquals(deviceDetails.getName(), "Apple iPhone 7 Plus 128GB silver");
	}

	@Test
	public void invalidInputTestForGetDeviceDetails() throws Exception {
		DeviceDetails deviceDetails = new DeviceDetails();
		try {
			deviceDetails = deviceDetailsController.getDeviceDetails(null, null, null);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Invalid input request received. Missing Device Id");
		}
		Assert.assertNull(deviceDetails.getDeviceId());
	}

	@Test
	public void invalidInputTestForGetDeviceDetailsInvalidDevice() throws Exception {
		try {
			deviceDetailsController.getDeviceDetails("1234", null, null);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Invalid Device Id Sent In Request");
		}
	}

	@Test
	public void notNullTestForGetDeviceDetails() {
		DeviceDetails deviceDetails = new DeviceDetails();

		deviceDetails = deviceDetailsController.getDeviceDetails("093353", "", "");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals(deviceDetails.getDeviceId(), "092572");
		Assert.assertEquals(deviceDetails.getLeadPlanId(), "110104");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getMake(), "apple");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getModel(), "iphone-7-plus");
		Assert.assertEquals(deviceDetails.getName(), "Apple iPhone 7 Plus 128GB silver");
	}

	@Test
	public void notNullTestForGetDeviceDetailsNullJourney() {
		DeviceDetails deviceDetails = new DeviceDetails();
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=093353",
				BundleDetailsForAppSrv.class)).willReturn(CommonMethods.getCoupledBundleListForDevice());
		deviceDetails = deviceDetailsController.getDeviceDetails("093353", null, null);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals(deviceDetails.getDeviceId(), "092572");
		Assert.assertEquals(deviceDetails.getLeadPlanId(), "110104");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getMake(), "apple");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getModel(), "iphone-7-plus");
		Assert.assertEquals(deviceDetails.getName(), "Apple iPhone 7 Plus 128GB silver");
	}

	@Test
	public void invalidTestForGetDeviceDetailsWithJourneyTypePAYG() {

		DeviceDetails deviceDetails = deviceDetailsController.getDeviceDetails("088417", "Upgrade", null);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals(deviceDetails.getDeviceId(), "092572");
		Assert.assertEquals(deviceDetails.getLeadPlanId(), "110104");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getMake(), "apple");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getModel(), "iphone-7-plus");
		Assert.assertEquals(deviceDetails.getName(), "Apple iPhone 7 Plus 128GB silver");
	}

	@Test
	public void invalidTestForGetDeviceDetailsWithOfferCodePAYG() {
		DeviceDetails deviceDetails = deviceDetailsController.getDeviceDetails("088417", "abcd", "W_HH_OC_01");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals(deviceDetails.getDeviceId(), "092572");
		Assert.assertEquals(deviceDetails.getLeadPlanId(), "110104");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getMake(), "apple");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getModel(), "iphone-7-plus");
		Assert.assertEquals(deviceDetails.getName(), "Apple iPhone 7 Plus 128GB silver");
	}

	@Test
	public void notNullTestForGetDeviceDetailsPAYG() {

		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceDetailsController.getDeviceDetails("088417", "abcd", null);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals(deviceDetails.getDeviceId(), "092572");
		Assert.assertEquals(deviceDetails.getLeadPlanId(), "110104");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getMake(), "apple");
		Assert.assertEquals(deviceDetails.getEquipmentDetail().getModel(), "iphone-7-plus");
		Assert.assertEquals(deviceDetails.getName(), "Apple iPhone 7 Plus 128GB silver");
	}

	@Test
	public void notNullTestForGetAccessoriesOfDevice() {
		try {
			List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
			accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("093353", "Upgrade",
					"W_HH_PAYM_OC_02");
			Assert.assertNotNull(accessoryDetails);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "No Compatible Accessories found for given device Id");
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
	public void notNullTestForGetAccessoriesOfDeviceWithNullOfferCode() {
		BundleDeviceAndProductsList deviceAndProductsList = new BundleDeviceAndProductsList();
		List<String> accessory = new ArrayList<>();
		accessory.add("093329");
		accessory.add("085145");
		deviceAndProductsList.setAccessoryList(accessory);
		deviceAndProductsList.setDeviceId("093353");
		deviceAndProductsList.setExtraList(new ArrayList<>());
		deviceAndProductsList.setOfferCode(null);
		deviceAndProductsList.setPackageType(null);
		given(restTemplate.postForObject("http://PRICE-V1/price/product", deviceAndProductsList, PriceForProduct.class))
				.willReturn(CommonMethods.getPriceForProduct_For_GetAccessories());

		given(response.getListOfGroupFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupForAccessories());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfCommercialProductsForAccessory());
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductByDeviceIdForAccessory());
		List<AccessoryTileGroup> accessoryGroup = accessoryInsuranceController.getAccessoriesOfDevice("093353", null,
				null);
		assertNotNull(accessoryGroup);
		assertEquals(accessoryGroup.get(0).getGroupName(), "Zagg Glass iPhone 8/7/6");
		assertEquals(accessoryGroup.get(0).getAccessories().get(0).getSkuId(), "085145");
		assertEquals(accessoryGroup.get(0).getAccessories().get(0).getColour(), "Clear");
		assertEquals(accessoryGroup.get(0).getAccessories().get(0).getName(),
				"ZAGG InvisibleShield Glass for iPhone 7");
	}

	@Test
	public void notNullTestForGetInsuranceById() {

		try {
			Insurances insurance = null;
			insurance = accessoryInsuranceController.getInsuranceById("093353", null);
			Assert.assertNotNull(insurance);
			assertEquals(insurance.getMinCost(), "");
			assertEquals(insurance.getInsuranceList().get(0).getId(), "");
			assertEquals(insurance.getInsuranceList().get(0).getName(), "");
			assertEquals(insurance.getInsuranceList().get(0).getPrice().getGross(), "");
			insurance = accessoryInsuranceController.getInsuranceById("93353", null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Compatible Insurances found for given device Id");
		}
	}

	@Test
	public void notNullTestForGetInsuranceByIdWithJourneyType() {
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
			assertEquals(insurance.getMinCost(), "");
			assertEquals(insurance.getInsuranceList().get(0).getId(), "");
			assertEquals(insurance.getInsuranceList().get(0).getName(), "");
			assertEquals(insurance.getInsuranceList().get(0).getPrice().getGross(), "");
			Assert.assertNotNull(insurance);
			insurance = accessoryInsuranceController.getInsuranceById("093353", "test");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Compatible Insurances found for given device Id");
		}
	}

	@Test
	public void nullTestForGetInsuranceById() {
		Insurances insurance = null;
		try {
			insurance = accessoryInsuranceController.getInsuranceById(null, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid input request received. Missing Device Id");
		}
		Assert.assertNull(insurance);
	}

	@Test
	public void notNullTestForGetDeviceList() {
		FacetedDevice deviceDetailsList = null;
		try {
			given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getListOfProductGroupMode());
			given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
			given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getModel());
			given(response.getListOfOfferAppliedPriceModel(ArgumentMatchers.any()))
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
			String jsonString = new String(CommonMethods.readFile("\\BundleandhardwarePromotuions.json"));
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
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
			given(response.getListOfBundleModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getBundleModelListForBundleList());

			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, "2");

			Assert.assertNotNull(deviceDetailsList);
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655541", "Great Camera", "SecondLine", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
			url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655542/sourcePackageSummary";
			given(restTemplate.getForObject(url, SourcePackageSummary.class))
					.willReturn(CommonMethods.getSourcePackageSummary());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "SecondLine", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "Acquisition", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals(deviceDetailsList.getDevice().get(0).getDeviceId(), "093353");
			assertEquals(deviceDetailsList.getDevice().get(0).getProductClass(), "HANDSET");
			assertEquals(deviceDetailsList.getDevice().get(0).getRating(), "na");
		} catch (Exception e) {
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "JourneyType is not compatible for given GroupType");
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "JourneyType is not compatible for given GroupType");
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", null, null, "W_HH_PAYM_01", null);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "offerCode is not compatible for given GroupType");
		}
	}

	@Test
	public void nullOfferCodeforDeviceList() {
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, "W_HH_OC_02");

		} catch (Exception e) {
			assertEquals(e.getMessage(), "Please enter valid credit limit.");
		}

	}

	@Test
	public void nullTestForGetDeviceListWrongCredit() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.any()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList("Handset1", "apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {
		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceListWithoutMake() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.any()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList("Handset", null, "iPhone 7", "DEVICE_PAYM", "Priority", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {
		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceListWithoutDeviceType() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.any()))
					.thenReturn(deviceLists);

			deviceLists = deviceService.getDeviceList(null, "apple", "iphone 7", "DEVICE_PAYM", "Priority", 1, 2,
					"32 GB", "White", "iOS", "Great Camera", "upgrade", 34543.0f, null, "447582367723", true);
		} catch (Exception e) {
		}
		Assert.assertNull(deviceLists);
	}

	@Test
	public void nullTestForGetDeviceListUpgrade() {
		FacetedDevice deviceLists = null;
		try {
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.any()))
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
			assertEquals(e.getMessage(), "Invalid input request received. Missing groupType in the filter criteria");
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
			assertEquals(e.getMessage(), "Invalid input request received. Missing Sort in the filter criteria");
			try {
				deviceService.getDeviceList(null, "listOfMake", "model", "groupType", "Sort", 2, 2, "capacity",
						"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
						"msisdn", false);
			} catch (Exception ew) {
				assertEquals(ew.getMessage(), "Received sortCriteria is invalid.");
				try {
					deviceService.getDeviceList("PC", "listOfMake", "model", "groupType", "Sort", 2, 2, "capacity",
							"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
							"msisdn", false);
				} catch (Exception ed) {
					assertEquals(ed.getMessage(), "Received sortCriteria is invalid.");
					try {
						deviceService.getDeviceList("HANDSET", "listOfMake", "model", "ksjdbhf", "Sort", 2, 2,
								"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
								(float) 14.0, "offerCode", "msisdn", false);
					} catch (Exception er) {
						assertEquals(er.getMessage(), "Received sortCriteria is invalid.");
						try {
							deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort", 2, 2,
									"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
									(float) 14.0, "offerCode", "msisdn", false);
						} catch (Exception eq) {
							assertEquals(eq.getMessage(), "Received sortCriteria is invalid.");
							try {
								deviceService.getDeviceList("HANDSET", "listOfMake", "model", null, "Sort", 2, 2,
										"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
										(float) 14.0, "offerCode", "msisdn", false);
							} catch (Exception ek) {
								assertEquals(ek.getMessage(), "Received sortCriteria is invalid.");
								try {
									deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort",
											2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures",
											"journeyType", (float) 14.0, "offerCode", "", true);
								} catch (Exception lkj) {
									assertEquals(lkj.getMessage(), "Received sortCriteria is invalid.");
									try {
										deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM",
												"Sort", 2, 2, "capacity", "colour", "operatingSystem",
												"mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
												"msisdn", true);
									} catch (Exception edfg) {
										assertEquals(edfg.getMessage(), "Received sortCriteria is invalid.");
										try {
											deviceService.getDeviceList(null, "listOfMake", "model", "DEVICE_PAYM",
													"Rating", 2, 2, "capacity", "colour", "operatingSystem",
													"mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
													"msisdn", true);
										} catch (Exception ex) {
											assertEquals(ex.getMessage(),
													"Invalid input request received. Missing Product Class in the filter criteria");
											try {
												deviceService.getDeviceList("HandsetHandset", "listOfMake", "model",
														"DEVICE_PAYM", "Rating", 2, 2, "capacity", "colour",
														"operatingSystem", "mustHaveFeatures", "conditionalaccept",
														(float) 14.0, "offerCode", "msisdn", true);
											} catch (Exception ex1) {
												assertEquals(ex1.getMessage(),
														"Invalid Product Class sent in the request");
												try {
													deviceService.getDeviceList("Handset", "listOfMake", "model",
															"DEVICE_PAYMDEVICE_PAYM", "Rating", 2, 2, "capacity",
															"colour", "operatingSystem", "mustHaveFeatures",
															"conditionalaccept", (float) 14.0, "offerCode", "msisdn",
															true);
												} catch (Exception ex2) {
													assertEquals(ex2.getMessage(),
															"Invalid Group Type sent in the request");
													try {
														deviceService.getDeviceList("Handset", "listOfMake", null,
																"DEVICE_PAYM", "Rating", 2, 2, "capacity", "colour",
																"operatingSystem", "mustHaveFeatures",
																"conditionalaccept", (float) 14.0, "offerCode",
																"msisdn", true);
													} catch (Exception ex3) {
														assertEquals(ex3.getMessage(),
																"No Devices Found for the given input search criteria");
														try {
															deviceService.getDeviceList("Handset", "listOfMake",
																	"model", "DEVICE_PAYM", "Rating", 2, 2, "capacity",
																	"colour", "operatingSystem", "mustHaveFeatures",
																	null, (float) 14.0, "offerCode", "msisdn", true);
														} catch (Exception ex4) {
															assertEquals(ex4.getMessage(),
																	"No Devices Found for the given input search criteria");
															try {
																deviceService.getDeviceList("Handset", "listOfMake",
																		"model", "DEVICE_PAYM", "Rating", 2, 2,
																		"capacity", "colour", "operatingSystem",
																		"mustHaveFeatures", "conditionalaccept",
																		(float) 14.0, "offerCode", null, true);
															} catch (Exception ex5) {
																assertEquals(ex5.getMessage(),
																		"Invalid input parameters, MSISDN is mandatory when recommendations requested.");
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
		given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupMode());
		given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
		given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());

		FacetedDevice deviceLists = null;
		deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9,
				"32 GB", "White", "iOS", "Great Camera", null, null, null, "447582367723", true);
		Assert.assertNotNull(deviceLists);
		assertEquals(deviceLists.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceLists.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceLists.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptanceForException() {
		try {
			given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getListOfProductGroupModeForNullLeadPlan());
			given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
			deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
					"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Empty Lead DeviceId List Coming From Solr");
		}
		try {
			given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getListOfProductGroupMode());
			given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(Collections.emptyList());
			deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
					"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Data Found for the given product list");
		}
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptanceUpgrade() {
		given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupMode());
		given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
		given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
		given(response.getListOfOfferAppliedPriceModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getOfferAppliedPriceModel());
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerChandisingPromotion_One());

		FacetedDevice deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM",
				"Priority", 0, 9, "32 GB", "White", "iOS", "Great Camera", "Upgrade", null, "W_HH_PAYM_OC_01",
				"447582367723", true);
		Assert.assertNotNull(deviceLists);
		assertEquals(deviceLists.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceLists.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceLists.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void nullTestForGetDeviceListForException() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		thrown.expect(Exception.class);
		thrown.expectMessage("No Devices Found for the given input search criteria");
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "JourneyType", null, null, "447582367723", true);

	}

	@Test
	public void nullTestForGetDeviceListForExcptionUpgrade() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		thrown.expect(Exception.class);
		thrown.expectMessage("No Devices Found for the given input search criteria");
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "Upgrade", null, "offerCode", "447582367723", true);

	}

	@Test
	public void nullTestForGetDeviceListForExcptionWithMSISDN() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		thrown.expect(Exception.class);
		thrown.expectMessage("No Devices Found for the given input search criteria");
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "SecondLine", null, "offerCode", "447582367723", true);

	}

	@Test
	public void nullTestForGetDeviceListForExcptionWithoutMSISDN() {
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerChandisingPromotion());
		thrown.expect(Exception.class);
		thrown.expectMessage("Invalid input parameters, MSISDN is mandatory when recommendations requested.");
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", "SecondLine", null, "W_HH_PAYM_02", null, true);

	}

	@Test
	public void notNullTestForGetGroupNameWithListOfProduct() {
		given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
		List<String> variantsList = new ArrayList<String>();
		variantsList.add("093353|1");
		variantsList.add("092660|5");
		deviceService.getGroupNameWithListOfProduct("SecondLine", variantsList, new HashMap<String, String>(),
				CommonMethods.getProductGroupModel().get(0), new ArrayList<String>());

	}

	@Test
	public void notNullTestForGetDeviceTileById() {
		try {
			deviceController.getDeviceTileById("093329", "Upgrade", "W_HH_SIMONLY");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No details found for given criteria");
		}
	}

	@Test
	public void nullTestForGetDeviceTileById() {
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "092572" + "&journeyType=" + "Upgrade", BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfVariant().get(0));
		try {
			deviceController.getDeviceTileById("093329", "Upgrade", "W_HH_SIMONLY");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No details found for given criteria");
		}

	}

	@Test
	public void notNullconvertCoherenceDeviceToDeviceTile() {
		DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile((long) 2.0,
				CommonMethods.getCommercialProduct(), CommonMethods.getCommercialBundle(),
				CommonMethods.getPriceForBundleAndHardware().get(0), null, "DEVICE_PAYM", true, null, cdnDomain);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTile() throws JsonParseException, JsonMappingException, IOException {
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		List<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct_Five());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any())).willReturn(a);
		List<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(ArgumentMatchers.any())).willReturn(listOfCommerCualBundle);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
		PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		try {
			given(deviceDAOMock.getBazaarVoice(ArgumentMatchers.anyString()))
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
		given(restTemplate.postForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.willReturn(obj);

		ResponseEntity<CacheDeviceTileResponse> cacheResponse = cacheDeviceAndReviewController
				.cacheDeviceTile("DEVICE_PAYM");
		assertNotNull(cacheResponse);
		assertEquals(cacheResponse.getStatusCodeValue(), 201);
		assertEquals(cacheResponse.getBody().getJobId(), "1234");
		assertEquals(cacheResponse.getBody().getJobStatus(), "Success");
	}

	@Test
	public void notNullTestForcacheDeviceTileWithoutLeadMember() throws IOException {
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		CommercialProduct com = CommonMethods.getCommercialProductForCacheDeviceTile();
		com.setLeadPlanId(null);
		CommercialProduct com1 = CommonMethods.getCommercialProductForCacheDeviceTile_One();
		List<CommercialProduct> a = new ArrayList<>();
		a.add(com);
		a.add(com1);
		given(response.getCommercialProductFromJson(ArgumentMatchers.any())).willReturn(a);
		List<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(ArgumentMatchers.any())).willReturn(listOfCommerCualBundle);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString1 = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
		PriceForBundleAndHardware[] obj1 = mapper.readValue(jsonString1, PriceForBundleAndHardware[].class);
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
		String jsonString = new String(CommonMethods.readFile("\\rest-mock\\BUNDLES-V1.json"));
		ObjectMapper mapper1 = new ObjectMapper();
		mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BundleDetailsForAppSrv obj = mapper1.readValue(jsonString, BundleDetailsForAppSrv.class);
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123",
				BundleDetailsForAppSrv.class)).willReturn(obj);
		ResponseEntity<CacheDeviceTileResponse> cacheResponse = cacheDeviceAndReviewController
				.cacheDeviceTile("DEVICE_PAYM");
		assertNotNull(cacheResponse);
		assertEquals(cacheResponse.getStatusCodeValue(), 201);
		assertEquals(cacheResponse.getBody().getJobId(), "1234");
		assertEquals(cacheResponse.getBody().getJobStatus(), "Success");
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTileWithoutOfferCodeForPAYG()
			throws JsonParseException, JsonMappingException, IOException {
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		Collection<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct_Five());
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getModel());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(CommonMethods.readFile("\\TEST-MOCK\\PRICE_FOR_PAYG.json"));
		PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		given(deviceDAOMock.getBazaarVoice(ArgumentMatchers.anyString()))
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
		ResponseEntity<CacheDeviceTileResponse> cacheResponse = cacheDeviceAndReviewController
				.cacheDeviceTile("DEVICE_PAYG");
		assertNotNull(cacheResponse);
		assertEquals(cacheResponse.getStatusCodeValue(), 201);
		assertEquals(cacheResponse.getBody().getJobId(), "1234");
		assertEquals(cacheResponse.getBody().getJobStatus(), "Success");
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void notNullTestForcacheDeviceTileWithoutOfferCode()
			throws JsonParseException, JsonMappingException, IOException {
		given(deviceTileCache.insertCacheDeviceToDb()).willReturn(CommonMethods.getCacheDeviceTileResponse());
		List<CommercialProduct> a = new ArrayList<>();
		a.add(CommonMethods.getCommercialProduct_Five());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any())).willReturn(a);
		List<CommercialBundle> listOfCommerCualBundle = new ArrayList<>();
		listOfCommerCualBundle.add(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(ArgumentMatchers.any())).willReturn(listOfCommerCualBundle);
		given(this.response.getListOfMerchandisingPromotionModelFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getModel());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
		PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		try {
			given(deviceDAOMock.getBazaarVoice(ArgumentMatchers.anyString()))
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
		ResponseEntity<CacheDeviceTileResponse> cacheResponse = cacheDeviceAndReviewController
				.cacheDeviceTile("DEVICE_PAYM");
		assertNotNull(cacheResponse);
		assertEquals(cacheResponse.getStatusCodeValue(), 201);
		assertEquals(cacheResponse.getBody().getJobId(), "1234");
		assertEquals(cacheResponse.getBody().getJobStatus(), "Success");
	}

	@Test
	public void nullTestForCacheDeviceTile() {
		try {
			cacheDeviceAndReviewController.cacheDeviceTile("");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"Group Type is null or Empty String.");
			try {
				cacheDeviceAndReviewController.cacheDeviceTile("INVALID_GT");
			} catch (Exception ex) {
				assertEquals(ex.getMessage(),"Invalid Group Type sent in the request");
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

			String jsonString = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			PriceForBundleAndHardware[] obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
			given(restTemplate.postForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(),
					ArgumentMatchers.any())).willReturn(obj);
			CacheDeviceService.getIlsPriceWithOfferCodeAndJourney(listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
					bundleHardwareTroupleMap, new HashMap<>(), "DEVICE_PAYM");
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
		String leadId = CacheDeviceService.getNonUpgradeLeadPlanIdForPaymCacheDevice(nonLeadPlanIdPriceMap, new HashMap<>(),
				commercialBundleMap, new ArrayList<>(), "093353", "Apple-Iphone");
		assertNotNull(leadId);
		assertEquals(leadId,"110163");
	}

	@Test
	public void testForGetUpgradeLeadPlanIdForCacheDevice() {
		Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
		nonLeadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		commercialBundleMap.put("110163", CommonMethods.getCommercialBundleForcacheDevice());
		String leadId = CacheDeviceService.getUpgradeLeadPlanIdForCacheDevice(nonLeadPlanIdPriceMap, commercialBundleMap, "093353");
		assertNotNull(leadId);
		assertEquals(leadId,"110163");
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
						CommonMethods.getleadMemberMap(), null, STRING_DEVICE_PAYG);

		Assert.assertNotNull(productGroupForDeviceListing);
		assertEquals(productGroupForDeviceListing.getDeviceId(),"093353");
		assertEquals(productGroupForDeviceListing.getProductGroupName(),"groupname");
		assertEquals(productGroupForDeviceListing.getProductGroupId(),"groupId");
		assertEquals(productGroupForDeviceListing.getNonUpgradeLeadDeviceId(),"093353");
		assertEquals(productGroupForDeviceListing.getUpgradeLeadDeviceId(),"093353");
	}

	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListing() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DevicePreCalculatedData productGroupForDeviceListing = CacheDeviceDaoUtils
				.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353", "leadPlanId", "groupname",
						"groupId", CommonMethods.getPrice(), CommonMethods.getleadMemberMap(), iLSPriceMap,
						CommonMethods.getleadMemberMap(), "upgradeLeadPlanId", STRING_DEVICE_PAYM);
		Assert.assertNotNull(productGroupForDeviceListing);
		assertEquals(productGroupForDeviceListing.getDeviceId(),"093353");
		assertEquals(productGroupForDeviceListing.getProductGroupName(),"groupname");
		assertEquals(productGroupForDeviceListing.getProductGroupId(),"groupId");
		assertEquals(productGroupForDeviceListing.getNonUpgradeLeadDeviceId(),"093353");
		assertEquals(productGroupForDeviceListing.getUpgradeLeadDeviceId(),"093353");
	}

	@Test
	public void testForGetListOfOfferAppliedPriceDetails() {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> listOfferAppliedPriceDetails = CacheDeviceDaoUtils
				.getListOfOfferAppliedPriceDetails(CommonMethods.getOfferAppliedPriceDetails());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
		assertEquals(listOfferAppliedPriceDetails.get(0).getDeviceId(),"093353");
		assertEquals(listOfferAppliedPriceDetails.get(0).getJourneyType(),"Upgrade");
		assertEquals(listOfferAppliedPriceDetails.get(0).getOfferCode(),"W_HH_OC_01");
	}

	@Test
	public void testForGetPriceForSolr() {
		com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo listOfferAppliedPriceDetails = CacheDeviceDaoUtils
				.getPriceForSolr(CommonMethods.getPriceinforForSorl());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
		assertEquals(listOfferAppliedPriceDetails.getBundlePrice().getBundleId(),"110154");
		assertEquals(listOfferAppliedPriceDetails.getBundlePrice().getMonthlyPrice().getGross(),10.300000190734863,0);
		assertEquals(listOfferAppliedPriceDetails.getHardwarePrice().getOneOffPrice().getGross(),9.0,0);
		assertEquals(listOfferAppliedPriceDetails.getHardwarePrice().getHardwareId(),"092660");
	}

	@Test
	public void testForGetListOfSolrMedia() {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.Media> listOfferAppliedPriceDetails = CacheDeviceDaoUtils
				.getListOfSolrMedia(CommonMethods.getmediaForSorl());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
		assertEquals(listOfferAppliedPriceDetails.get(0).getDiscountId(),"discountId");
		assertEquals(listOfferAppliedPriceDetails.get(0).getId(),"id");
		assertEquals(listOfferAppliedPriceDetails.get(0).getOfferCode(),"offerCode");
		assertEquals(listOfferAppliedPriceDetails.get(0).getType(),"type");
	}

	@Test
	public void testForGetListOfIlsPriceWithoutOfferCode() {
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("SecondLine", iLSPriceMap);
		Map<String,Object> cachePrice =CacheDeviceDaoUtils.getListOfIlsPriceWithoutOfferCode("093353", ilsPriceForBundleAndHardwareMap);
		assertNotNull(cachePrice);
	}

	@Test
	public void testForGetListOfOfferAppliedPrice() {
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> listOfeerCode = new HashMap<>();
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("W_HH_Paym_02", iLSPriceMap);
		listOfeerCode.put("SecondLine", ilsPriceForBundleAndHardwareMap);
		Map<String,Object> cachePrice =CacheDeviceDaoUtils.getListOfOfferAppliedPrice("093353", listOfeerCode);
		Assert.assertNotNull(cachePrice);
	}

	@Test
	public void testForGetPriceInfoForSolr() {

		Map<String,Object> cachePrice =CacheDeviceDaoUtils.getPriceInfoForSolr(CommonMethods.getOfferAppliedPrice().get(0), new HashMap<>());
		Assert.assertNotNull(cachePrice);
	}

	@Test
	public void testForGetMerchandising() {
		List<String> promoteAs = new ArrayList<>();
		promoteAs.add("handset-promotion");
		List<MerchandisingPromotion> mpList = deviceESHelper.getMerchandising(promoteAs);
		assertNotNull(mpList);
		assertEquals(mpList.get(0).getTag(),"AllPhone.full.2017");
		assertEquals(mpList.get(0).getLabel(),"Label");
		assertEquals(mpList.get(0).getDescription(),"description");
	}

	@Test
	public void testForCalculateDiscount() {
		Iterator<PriceForBundleAndHardware> iterator = CommonMethods.getOfferAppliedPrice().iterator();
		iterator.next();
		DeviceServiceImplUtility.calculateDiscount(2.0, iterator, CommonMethods.getOfferAppliedPrice().get(0));

	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceList() {
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
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceListPaymWithoutOffer() {
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
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceListWithoutOffer() {
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
				listOfOfferAppliedPrice1, null, productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceListPAYM() {
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
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceListUpgrade() {
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
				listOfOfferAppliedPrice1, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceListPayG() {
		Map<String, ProductGroupDetailsForDeviceList> productGroupdetailsMap = new HashMap<>();
		productGroupdetailsMap.put("093353", CommonMethods.getGroupDetails());
		productGroupdetailsMap.put("092660", CommonMethods.getGroupDetails());
		Map<String, String> groupNameWithProdId = new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung", "7630");
		Map<String, Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DeviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYG", null, null, null,
				null, groupNameWithProdId, null, null, isLeadMemberFromSolr, null, JOURNEY_TYPE_ACQUISITION,
				productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Test
	public void notNullTestForDaoUtilsconvertProductModelListToDeviceListWithOffer() {
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
				listOfOfferAppliedPrice1, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals(deviceList.getDevice().get(0).getDeviceId(), "093353");
		assertEquals(deviceList.getDevice().get(0).getProductClass(), "HANDSET");
		assertEquals(deviceList.getDevice().get(0).getRating(), "na");
	}

	@Deprecated
	@Test
	public void notNullTestForDateValidation() {
		Date startDateTime = new Date("15/05/2017");
		Date endDateTime = new Date("15/05/2071");
		assertTrue(DeviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, true));
	}

	@Test
	public void notNullTestForGetBundlePriceBasedOnDiscountDuration() {
		Double priceFull = DeviceTilesDaoUtils.getBundlePriceBasedOnDiscountDuration(
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice(), FULL_DURATION_DISCOUNT);
		Double price = DeviceTilesDaoUtils.getBundlePriceBasedOnDiscountDuration(
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice(), LIMITED_TIME_DISCOUNT);
		assertNotNull(price);
		assertEquals(price,13.64,0);
		assertNotNull(priceFull);
		assertEquals(priceFull,10.11,0);
	}

	@Test
	public void notNullTestForPopulateMerchandisingPromotions() {
		PriceForBundleAndHardware bundlePrice = CommonMethods.getPriceForBundleAndHardware().get(0);
		bundlePrice.getBundlePrice().setMerchandisingPromotions(null);
		DeviceTilesDaoUtils.populateMerchandisingPromotions(bundlePrice,
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice());
	}

	@Test
	public void notNullTestForGetBundleAndHardwarePriceFromSolrWithoutOfferCode() {
		PriceForBundleAndHardware priceForBundle = DeviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(null,
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(0), "110154");
		PriceForBundleAndHardware price = DeviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(null,
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(1), "110154");
		assertNotNull(priceForBundle);
		assertNotNull(price);
		assertEquals(priceForBundle.getBundlePrice().getBundleId(),"109373");
		assertEquals(priceForBundle.getBundlePrice().getMonthlyPrice().getGross(),"10");
		assertEquals(price.getBundlePrice().getBundleId(),"109372");
		assertEquals(price.getBundlePrice().getMonthlyPrice().getGross(),"10");
	}

	@Test
	public void notNullTestForGetDeviceIlsJourneyAwarePriceMap() {
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
		String monthlyPrice = DeviceUtils.getmonthlyPriceFormPriceForPayG(price);
		assertNotNull(monthlyPrice);
		assertEquals(monthlyPrice,"20");
	}

	@Test
	public void testForGetUpdateDeviceratingForCacheDevice() {
		Map<String, String> ratingsReviewMap = new HashMap<>();
		ratingsReviewMap.put("sku93353", "3.275");

		DeviceUtils.updateDeviceratingForCacheDevice(ratingsReviewMap, CommonMethods.getDevicePreCal().get(0));
	}

	@Test
	public void testForLeastMonthlyPriceForpayG() {
		String leastPrice = DeviceUtils.leastMonthlyPriceForpayG(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		assertNotNull(leastPrice);
		assertEquals(leastPrice,"0");
	}

	@Test
	public void notNullTestForDaoUtilsconvertCoherenceDeviceToDeviceDetails() {
		DeviceDetails deviceDetails = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceDetails(
				CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware(),
				CommonMethods.getListOfBundleAndHardwarePromotions(), cdnDomain);
		Assert.assertNotNull(deviceDetails);
		assertEquals(deviceDetails.getDeviceId(),"093353");
		assertEquals(deviceDetails.getProductClass(),"Handset");
	}

	@Test
	public void notNullTestForSetPriceMerchandisingPromotion() {
		List<MediaLink> mediaLink = AccessoriesAndInsurancedaoUtils.setPriceMerchandisingPromotion(
				CommonMethods.getForUtilityPriceForBundleAndHardware().get(0).getHardwarePrice());
		assertNotNull(mediaLink);
		assertEquals(mediaLink.get(0).getId(),"limited_discount.merchandisingPromotions.merchandisingPromotion.label");
		assertEquals(mediaLink.get(0).getType(),"TEXT");
		assertEquals(mediaLink.get(0).getValue(),"20% off with any handset");
	}

	@Test
	public void notNullTestForConvertGroupProductToProductGroupDetails() {
		List<ProductGroup> productGroup=ListOfDeviceDetailsDaoUtils.convertGroupProductToProductGroupDetails(CommonMethods.getProductGroupModel());
		assertNotNull(productGroup);
		assertEquals(productGroup.get(0).getGroupName(),"Apple iPhone091221|5-7");
		assertEquals(productGroup.get(0).getGroupPriority(),"1");
		assertEquals(productGroup.get(0).getGroupType(),"DEVICE");
	}

	@Test
	public void notNullTestForGetAscendingOrderForBundlePriceAscendingOrder() {
		ListOfDeviceDetailsDaoUtils listOfDeviceDetailsDaoUtils = new ListOfDeviceDetailsDaoUtils();
		List<BundleHeader> bundleHeader = listOfDeviceDetailsDaoUtils.getAscendingOrderForBundlePrice1(CommonMethods.getBundleHeaderList("SIMO"));
		assertNotNull(bundleHeader);
		assertEquals(bundleHeader.get(0).getBundleClass(),"SIMO");
		assertEquals(bundleHeader.get(0).getBundleType(),"SIMO");
		assertEquals(bundleHeader.get(0).getSkuId(),"1");
		assertEquals(bundleHeader.get(0).getDescription(),"SIMO plans only");
	}

	@Test
	public void notNullTestForGetAscendingOrderForBundleHeaderPrice() {
		List<BundleHeader> bundleHeader = deviceServiceCommonUtility.getAscendingOrderForBundleHeaderPrice(CommonMethods.getBundleHeaderList("SIMO"));
		assertNotNull(bundleHeader);
		assertEquals(bundleHeader.get(0).getBundleClass(),"SIMO");
		assertEquals(bundleHeader.get(0).getBundleType(),"SIMO");
		assertEquals(bundleHeader.get(0).getSkuId(),"1");
		assertEquals(bundleHeader.get(0).getDescription(),"SIMO plans only");
	}

	@Test
	public void notNullTestForGetAscendingOrderForBundlePrice() {
		List<PriceForBundleAndHardware> price =deviceServiceCommonUtility.getAscendingOrderForBundlePrice(CommonMethods.getPriceForBundleAndHardwareSorting());
		assertNotNull(price);
		assertEquals(price.get(0).getBundlePrice().getBundleId(),"183099");
		assertEquals(price.get(0).getMonthlyPrice().getGross(),"13.64");
		assertEquals(price.get(0).getOneOffPrice().getGross(),"5.11");
		assertEquals(price.get(0).getHardwarePrice().getHardwareId(),"093353");
	}

	@Test
	public void notNullTestForGetAscendingOrderForOneoffPrice() {
		List<PriceForBundleAndHardware> price =deviceServiceCommonUtility.getAscendingOrderForOneoffPrice(CommonMethods.getPriceForBundleAndHardwareSorting());
		assertNotNull(price);
		assertEquals(price.get(0).getBundlePrice().getBundleId(),"183099");
		assertEquals(price.get(0).getMonthlyPrice().getGross(),"12.64");
		assertEquals(price.get(0).getOneOffPrice().getGross(),"15.11");
		assertEquals(price.get(0).getHardwarePrice().getHardwareId(),"093353");
	}

	@Test
	public void notNullTestForvalidateAllParameters() {
		try {
			Validator.validateAllParameters(null, "model", "groupType", "journeyType");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"Invalid input request received. Missing make in the filter criteria");
		}
		try {
			Validator.validateAllParameters("make", null, "groupType", "journeyType");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"Invalid input request received. Missing model in the filter criteria");
		}
		try {
			Validator.validateAllParameters("make", "model", null, "journeyType");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"Invalid input request received. Missing groupType in the filter criteria");
		}
		try {
			Validator.validateAllParameters("make", "model", "grouptype", "journeyType");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"Invalid Group Type sent in the request");
		}
		try {
			Validator.validateAllParameters("make", "model", "Device_PAYG", "Upgrade");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"JourneyType is not compatible for given GroupType");
		}
		try {
			Validator.validateAllParameters("make", "model", "Device_PAYG", "SecondLine");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"JourneyType is not compatible for given GroupType");
		}
		try {
			Validator.validateAllParameters("make", "model", "Device_PAYG", "");
		} catch (Exception e) {
			assertEquals(e.getMessage(),"JourneyType is not compatible for given GroupType");
		}
	}

	@Test
	public void notNullTestForvalidateCreditLimitValue() {
		assertFalse(Validator.validateCreditLimitValue("abcd"));
		assertTrue(Validator.validateCreditLimitValue("4"));
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("productId", "093353");
		queryParams.put("deviceId", "093353");
		assertFalse(Validator.validateGetDeviceList(queryParams));
		assertFalse(Validator.validateGetListOfDeviceTile(queryParams));
		assertTrue(Validator.validateJourneyType("upgrade"));
		assertFalse(Validator.validateJourneyType("journeyType"));
		assertFalse(Validator.validateProduct(queryParams));
		assertFalse(Validator.validateProductGroup(queryParams));
		try {
			Validator.validatePageSize(-1, 2);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"Page Size Value cannot be negative");
		}
	}
}
