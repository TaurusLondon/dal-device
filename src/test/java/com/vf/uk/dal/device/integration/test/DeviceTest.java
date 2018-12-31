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
import com.vf.uk.dal.device.client.entity.bundle.BundleModel;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.catalogue.DeviceEntityModel;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.client.entity.customer.SourcePackageSummary;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundleDeviceAndProductsList;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.HardwarePrice;
import com.vf.uk.dal.device.client.entity.price.Price;
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
import com.vf.uk.dal.device.service.CacheDeviceServiceImpl;
import com.vf.uk.dal.device.service.DeviceRecommendationService;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.AccessoriesAndInsurancedaoUtils;
import com.vf.uk.dal.device.utils.CacheDeviceDaoUtils;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceConditionallHelper;
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
	CacheDeviceDaoUtils cacheDeviceDaoUtils;

	@Autowired
	AccessoriesAndInsurancedaoUtils accessoriesAndInsurancedaoUtils;

	@Autowired
	CommonUtility commonUtility;

	@Autowired
	ListOfDeviceDetailsDaoUtils listOfDeviceDetailsDaoUtils;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;

	@Autowired
	DeviceDetailsMakeAndModelVaiantDaoUtils deviceDetailsMakeAndModelVaiantDaoUtils;

	@Autowired
	Validator validator;

	@Autowired
	DeviceUtils deviceUtils;

	@Autowired
	AccessoriesAndInsurancedaoUtils AccessoriesAndInsurancedaoUtils;

	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;

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
	DeviceConditionallHelper deviceConditionallHelper;

	@MockBean
	DeviceRecommendationService deviceRecomServiceMock;

	@Autowired
	CacheDeviceAndReviewController cacheDeviceAndReviewController;

	@Autowired
	CacheDeviceService cacheDeviceService;

	@Autowired
	CacheDeviceServiceImpl cacheDeviceServiceImpl;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;

	@Value("${cdn.domain.host}")
	private String cdnDomain;

	@Value("${cacheDevice.handsetOnlineModel.enabled}")
	private boolean handsetOnlineModelEnabled;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "093353" + "&journeyType=" + null, BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(deviceESHelper.getPriceForBundleAndHardwareJourneySpecificMap(ArgumentMatchers.anyList()))
				.willReturn(CommonMethods.getPricePromoModel());
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
		Assert.assertEquals("092572", deviceDetails.getDeviceId());
		Assert.assertEquals("110104", deviceDetails.getLeadPlanId());
		Assert.assertEquals("apple", deviceDetails.getEquipmentDetail().getMake());
		Assert.assertEquals("iphone-7-plus", deviceDetails.getEquipmentDetail().getModel());
		Assert.assertEquals("Apple iPhone 7 Plus 128GB silver", deviceDetails.getName());
	}

	@Test
	public void invalidInputTestForGetDeviceDetails() throws Exception {
		DeviceDetails deviceDetails = new DeviceDetails();
		try {
			deviceDetails = deviceDetailsController.getDeviceDetails(null, null, null);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}
		Assert.assertNull(deviceDetails.getDeviceId());
	}

	@Test
	public void invalidInputTestForGetDeviceDetailsInvalidDevice() throws Exception {
		try {
			deviceDetailsController.getDeviceDetails("1234", null, null);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void notNullTestForGetDeviceDetails() {
		DeviceDetails deviceDetails = new DeviceDetails();

		deviceDetails = deviceDetailsController.getDeviceDetails("093353", "", "");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals("092572", deviceDetails.getDeviceId());
		Assert.assertEquals("110104", deviceDetails.getLeadPlanId());
		Assert.assertEquals("apple", deviceDetails.getEquipmentDetail().getMake());
		Assert.assertEquals("iphone-7-plus", deviceDetails.getEquipmentDetail().getModel());
		Assert.assertEquals("Apple iPhone 7 Plus 128GB silver", deviceDetails.getName());
	}

	@Test
	public void notNullTestForGetDeviceDetailsNullJourney() {
		DeviceDetails deviceDetails = new DeviceDetails();
		given(restTemplate.getForObject(
				"http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=093353",
				BundleDetailsForAppSrv.class)).willReturn(CommonMethods.getCoupledBundleListForDevice());
		deviceDetails = deviceDetailsController.getDeviceDetails("093353", null, null);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals("092572", deviceDetails.getDeviceId());
		Assert.assertEquals("110104", deviceDetails.getLeadPlanId());
		Assert.assertEquals("apple", deviceDetails.getEquipmentDetail().getMake());
		Assert.assertEquals("iphone-7-plus", deviceDetails.getEquipmentDetail().getModel());
		Assert.assertEquals("Apple iPhone 7 Plus 128GB silver", deviceDetails.getName());
	}

	@Test
	public void invalidTestForGetDeviceDetailsWithJourneyTypePAYG() {

		DeviceDetails deviceDetails = deviceDetailsController.getDeviceDetails("088417", "Upgrade", null);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals("092572", deviceDetails.getDeviceId());
		Assert.assertEquals("110104", deviceDetails.getLeadPlanId());
		Assert.assertEquals("apple", deviceDetails.getEquipmentDetail().getMake());
		Assert.assertEquals("iphone-7-plus", deviceDetails.getEquipmentDetail().getModel());
		Assert.assertEquals("Apple iPhone 7 Plus 128GB silver", deviceDetails.getName());
	}

	@Test
	public void invalidTestForGetDeviceDetailsWithOfferCodePAYG() {
		DeviceDetails deviceDetails = deviceDetailsController.getDeviceDetails("088417", "abcd", "W_HH_OC_01");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals("092572", deviceDetails.getDeviceId());
		Assert.assertEquals("110104", deviceDetails.getLeadPlanId());
		Assert.assertEquals("apple", deviceDetails.getEquipmentDetail().getMake());
		Assert.assertEquals("iphone-7-plus", deviceDetails.getEquipmentDetail().getModel());
		Assert.assertEquals("Apple iPhone 7 Plus 128GB silver", deviceDetails.getName());
	}

	@Test
	public void notNullTestForGetDeviceDetailsPAYG() {

		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails = deviceDetailsController.getDeviceDetails("088417", "abcd", null);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals("092572", deviceDetails.getDeviceId());
		Assert.assertEquals("110104", deviceDetails.getLeadPlanId());
		Assert.assertEquals("apple", deviceDetails.getEquipmentDetail().getMake());
		Assert.assertEquals("iphone-7-plus", deviceDetails.getEquipmentDetail().getModel());
		Assert.assertEquals("Apple iPhone 7 Plus 128GB silver", deviceDetails.getName());
	}

	@Test
	public void notNullTestForGetAccessoriesOfDevice() {
		try {
			List<AccessoryTileGroup> accessoryDetails = new ArrayList<>();
			accessoryDetails = accessoryInsuranceController.getAccessoriesOfDevice("093353", "Upgrade",
					"W_HH_PAYM_OC_02");
			Assert.assertNotNull(accessoryDetails);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Accessories found for given device Id", e.getMessage());
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
		assertEquals("Zagg Glass iPhone 8/7/6", accessoryGroup.get(0).getGroupName());
		assertEquals("085145", accessoryGroup.get(0).getAccessories().get(0).getSkuId());
		assertEquals("Clear", accessoryGroup.get(0).getAccessories().get(0).getColour());
		assertEquals("ZAGG InvisibleShield Glass for iPhone 7",
				accessoryGroup.get(0).getAccessories().get(0).getName());
	}

	@Test
	public void notNullTestForGetInsuranceById() {

		try {
			Insurances insurance = null;
			insurance = accessoryInsuranceController.getInsuranceById("093353", null);
			Assert.assertNotNull(insurance);
			assertEquals("", insurance.getMinCost());
			assertEquals("", insurance.getInsuranceList().get(0).getId());
			assertEquals("", insurance.getInsuranceList().get(0).getName());
			assertEquals("", insurance.getInsuranceList().get(0).getPrice().getGross());
			insurance = accessoryInsuranceController.getInsuranceById("93353", null);
		} catch (Exception e) {
			assertEquals("No Compatible Insurances found for given device Id", e.getMessage());
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
			assertEquals("", insurance.getMinCost());
			assertEquals("", insurance.getInsuranceList().get(0).getId());
			Assert.assertNotNull(insurance);
			insurance = accessoryInsuranceController.getInsuranceById("093353", "test");
		} catch (Exception e) {
			assertEquals("No Compatible Insurances found for given device Id", e.getMessage());
		}
	}

	@Test
	public void nullTestForGetInsuranceById() {
		Insurances insurance = null;
		try {
			insurance = accessoryInsuranceController.getInsuranceById(null, null);
		} catch (Exception e) {
			assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
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
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
			given(response.getListOfBundleModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getBundleModelListForBundleList());

			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, "2");

			Assert.assertNotNull(deviceDetailsList);
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655541", "Great Camera", "SecondLine", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
			url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655542/sourcePackageSummary";
			given(restTemplate.getForObject(url, SourcePackageSummary.class))
					.willReturn(CommonMethods.getSourcePackageSummary());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "SecondLine", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "-Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", "7741655542", "Great Camera", "Acquisition", "true", null,
					null);
			Assert.assertNotNull(deviceDetailsList);
			assertEquals("093353", deviceDetailsList.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceDetailsList.getDevice().get(0).getProductClass());
			assertEquals("na", deviceDetailsList.getDevice().get(0).getRating());
		} catch (Exception e) {
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "SecondLine", null, null, null);
		} catch (Exception e) {
			assertEquals("JourneyType is not compatible for given GroupType", e.getMessage());
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, null);
		} catch (Exception e) {
			assertEquals("JourneyType is not compatible for given GroupType", e.getMessage());
		}
		try {
			deviceDetailsList = deviceController.getDeviceList("HANDSET", "DEVICE_PAYG", "Priority", 1, 9, "Apple",
					"iPhone-7", "White", "iOS 9", "32 GB", null, "Great Camera", null, null, "W_HH_PAYM_01", null);
		} catch (Exception e) {
			assertEquals("offerCode is not compatible for given GroupType", e.getMessage());
		}
	}

	@Test
	public void nullOfferCodeforDeviceList() {
		try {
			deviceController.getDeviceList("HANDSET", "DEVICE_PAYM", "Priority", 1, 9, "Apple", "iPhone-7", "White",
					"iOS 9", "32 GB", null, "Great Camera", "Upgrade", null, null, "W_HH_OC_02");

		} catch (Exception e) {
			assertEquals("Please enter valid credit limit.", e.getMessage());
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
			assertEquals("Invalid input request received. Missing groupType in the filter criteria", e.getMessage());
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
			assertEquals("Invalid input request received. Missing Sort in the filter criteria", e.getMessage());
			try {
				deviceService.getDeviceList(null, "listOfMake", "model", "groupType", "Sort", 2, 2, "capacity",
						"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
						"msisdn", false);
			} catch (Exception ew) {
				assertEquals("Received sortCriteria is invalid.", ew.getMessage());
				try {
					deviceService.getDeviceList("PC", "listOfMake", "model", "groupType", "Sort", 2, 2, "capacity",
							"colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
							"msisdn", false);
				} catch (Exception ed) {
					assertEquals("Received sortCriteria is invalid.", ed.getMessage());
					try {
						deviceService.getDeviceList("HANDSET", "listOfMake", "model", "ksjdbhf", "Sort", 2, 2,
								"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
								(float) 14.0, "offerCode", "msisdn", false);
					} catch (Exception er) {
						assertEquals("Received sortCriteria is invalid.", er.getMessage());
						try {
							deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort", 2, 2,
									"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
									(float) 14.0, "offerCode", "msisdn", false);
						} catch (Exception eq) {
							assertEquals("Received sortCriteria is invalid.", eq.getMessage());
							try {
								deviceService.getDeviceList("HANDSET", "listOfMake", "model", null, "Sort", 2, 2,
										"capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType",
										(float) 14.0, "offerCode", "msisdn", false);
							} catch (Exception ek) {
								assertEquals("Received sortCriteria is invalid.", ek.getMessage());
								try {
									deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort",
											2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures",
											"journeyType", (float) 14.0, "offerCode", "", true);
								} catch (Exception lkj) {
									assertEquals("Received sortCriteria is invalid.", lkj.getMessage());
									try {
										deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM",
												"Sort", 2, 2, "capacity", "colour", "operatingSystem",
												"mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
												"msisdn", true);
									} catch (Exception edfg) {
										assertEquals("Received sortCriteria is invalid.", edfg.getMessage());
										try {
											deviceService.getDeviceList(null, "listOfMake", "model", "DEVICE_PAYM",
													"Rating", 2, 2, "capacity", "colour", "operatingSystem",
													"mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
													"msisdn", true);
										} catch (Exception ex) {
											assertEquals(
													"Invalid input request received. Missing Product Class in the filter criteria",
													ex.getMessage());
											try {
												deviceService.getDeviceList("HandsetHandset", "listOfMake", "model",
														"DEVICE_PAYM", "Rating", 2, 2, "capacity", "colour",
														"operatingSystem", "mustHaveFeatures", "conditionalaccept",
														(float) 14.0, "offerCode", "msisdn", true);
											} catch (Exception ex1) {
												assertEquals("Invalid Product Class sent in the request",
														ex1.getMessage());
												try {
													deviceService.getDeviceList("Handset", "listOfMake", "model",
															"DEVICE_PAYMDEVICE_PAYM", "Rating", 2, 2, "capacity",
															"colour", "operatingSystem", "mustHaveFeatures",
															"conditionalaccept", (float) 14.0, "offerCode", "msisdn",
															true);
												} catch (Exception ex2) {
													assertEquals("Invalid Group Type sent in the request",
															ex2.getMessage());
													try {
														deviceService.getDeviceList("Handset", "listOfMake", null,
																"DEVICE_PAYM", "Rating", 2, 2, "capacity", "colour",
																"operatingSystem", "mustHaveFeatures",
																"conditionalaccept", (float) 14.0, "offerCode",
																"msisdn", true);
													} catch (Exception ex3) {
														assertEquals(
																"No Devices Found for the given input search criteria",
																ex3.getMessage());
														try {
															deviceService.getDeviceList("Handset", "listOfMake",
																	"model", "DEVICE_PAYM", "Rating", 2, 2, "capacity",
																	"colour", "operatingSystem", "mustHaveFeatures",
																	null, (float) 14.0, "offerCode", "msisdn", true);
														} catch (Exception ex4) {
															assertEquals(
																	"No Devices Found for the given input search criteria",
																	ex4.getMessage());
															try {
																deviceService.getDeviceList("Handset", "listOfMake",
																		"model", "DEVICE_PAYM", "Rating", 2, 2,
																		"capacity", "colour", "operatingSystem",
																		"mustHaveFeatures", "conditionalaccept",
																		(float) 14.0, "offerCode", null, true);
															} catch (Exception ex5) {
																assertEquals(
																		"Invalid input parameters, MSISDN is mandatory when recommendations requested.",
																		ex5.getMessage());
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
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance() throws IOException {
		if (!handsetOnlineModelEnabled) {
			given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getListOfProductGroupMode());
			given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());

			FacetedDevice deviceLists = null;

			deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9,
					"32 GB", "White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYG", "Priority", 0, 9, "32 GB",
					"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			Assert.assertNotNull(deviceLists);
			assertEquals("093353", deviceLists.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceLists.getDevice().get(0).getProductClass());
			assertEquals("na", deviceLists.getDevice().get(0).getRating());
		} else {
			String jsonString = new String(CommonMethods.readFile("\\rest-mock\\DEVICEENTITY-V1.json"));
			DeviceEntityModel obj = new ObjectMapper().readValue(jsonString, DeviceEntityModel.class);
			given(restTemplate.getForObject(
					"http://CATALOGUE-V1/productCatalog/device?make=" + ArgumentMatchers.any() + "&model="
							+ ArgumentMatchers.any() + "&groupType=DEVICE_PAYM" + "&deviceId=" + null + "&packageType="
							+ null + "&sort=" + null + "&pageNumber=" + null + "&pageSize=" + null + "&color=" + null
							+ "&operatingSystem=" + null + "&capacity=" + null + "&mustHaveFeatures=" + null,
					DeviceEntityModel.class)).willReturn(obj);
			FacetedDevice deviceLists = null;

			deviceLists = deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9,
					"32 GB", "White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			Assert.assertNotNull(deviceLists);
			assertEquals("088410", deviceLists.getDevice().get(0).getDeviceId());
			assertEquals("Handset", deviceLists.getDevice().get(0).getProductClass());
		}
	}

	@Test
	public void nullTestForGetDeviceListForPaygGroupType() throws IOException {
		String jsonString = new String(CommonMethods.readFile("\\rest-mock\\DEVICEENTITY-V1.json"));
		DeviceEntityModel obj = new ObjectMapper().readValue(jsonString, DeviceEntityModel.class);
		given(restTemplate.getForObject(
				"http://CATALOGUE-V1/productCatalog/device?make=" + ArgumentMatchers.any() + "&model="
						+ ArgumentMatchers.any() + "&groupType=DEVICE_PAYG" + "&deviceId=" + null + "&packageType="
						+ null + "&sort=" + null + "&pageNumber=" + null + "&pageSize=" + null + "&color=" + null
						+ "&operatingSystem=" + null + "&capacity=" + null + "&mustHaveFeatures=" + null,
				DeviceEntityModel.class)).willReturn(obj);
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYG", "Priority", 0, 9, "32 GB", "White",
				"iOS", "Great Camera", null, null, null, "447582367723", true);
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptanceForException() {
		try {
			if (!handsetOnlineModelEnabled) {
				given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
						.willReturn(CommonMethods.getListOfProductGroupModeForNullLeadPlan());
				given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
				given(response.getListOfProductModel(ArgumentMatchers.any()))
						.willReturn(CommonMethods.getProductModel());

				deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
						"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			}else{
				String jsonString = new String(CommonMethods.readFile("\\rest-mock\\DEVICEENTITY-V1.json"));
				DeviceEntityModel obj = new ObjectMapper().readValue(jsonString, DeviceEntityModel.class);
				given(restTemplate.getForObject("http://CATALOGUE-V1/productCatalog/device?make="
						+ ArgumentMatchers.any() + "&model=" + ArgumentMatchers.any() + "&groupType=DEVICE_PAYM"
						+ "&deviceId=" + null + "&packageType=" + null + "&sort=" + null + "&pageNumber=" + null
						+ "&pageSize=" + null + "&color=" + null + "&operatingSystem=" + null + "&capacity=" + null
						+ "&mustHaveFeatures=" + null, DeviceEntityModel.class)).willReturn(obj);
				given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupModeForNullLeadPlan());
		given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
		given(response.getListOfProductModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getProductModel());

		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
				"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			}
		} catch (Exception e) {
			assertEquals("Empty Lead DeviceId List Coming From Solr", e.getMessage());
		}
		try {
			if (!handsetOnlineModelEnabled) {
				given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
						.willReturn(CommonMethods.getListOfProductGroupMode());
				given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
				given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(Collections.emptyList());
				deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
						"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			}else{
				String jsonString = new String(CommonMethods.readFile("\\rest-mock\\DEVICEENTITY-V1.json"));
				DeviceEntityModel obj = new ObjectMapper().readValue(jsonString, DeviceEntityModel.class);
				given(restTemplate.getForObject(
						"http://CATALOGUE-V1/productCatalog/device?make=" + ArgumentMatchers.any() + "&model="
								+ ArgumentMatchers.any() + "&groupType=DEVICE_PAYM" + "&deviceId=" + null + "&packageType="
								+ null + "&sort=" + null + "&pageNumber=" + null + "&pageSize=" + null + "&color=" + null
								+ "&operatingSystem=" + null + "&capacity=" + null + "&mustHaveFeatures=" + null,
						DeviceEntityModel.class)).willReturn(obj);
				given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupMode());
		given(response.getFacetField(ArgumentMatchers.any())).willReturn(CommonMethods.getListOfFacetField());
		given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(Collections.emptyList());
		deviceService.getDeviceList("Handset", "apple", "iPhone 7", "DEVICE_PAYM", "Priority", 0, 9, "32 GB",
				"White", "iOS", "Great Camera", null, null, null, "447582367723", true);
			}
		} catch (Exception e) {
			assertEquals("No Data Found for the given product list", e.getMessage());
		}
	}

	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptanceUpgrade() throws JsonParseException, JsonMappingException, IOException {
		if (!handsetOnlineModelEnabled) {
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
			assertEquals("093353", deviceLists.getDevice().get(0).getDeviceId());
			assertEquals("HANDSET", deviceLists.getDevice().get(0).getProductClass());
			assertEquals("na", deviceLists.getDevice().get(0).getRating());
		}else{
			String jsonString = new String(CommonMethods.readFile("\\rest-mock\\DEVICEENTITY-V1.json"));
			DeviceEntityModel obj = new ObjectMapper().readValue(jsonString, DeviceEntityModel.class);
			given(restTemplate.getForObject(
					"http://CATALOGUE-V1/productCatalog/device?make=" + ArgumentMatchers.any() + "&model="
							+ ArgumentMatchers.any() + "&groupType=DEVICE_PAYM" + "&deviceId=" + null + "&packageType="
							+ null + "&sort=" + null + "&pageNumber=" + null + "&pageSize=" + null + "&color=" + null
							+ "&operatingSystem=" + null + "&capacity=" + null + "&mustHaveFeatures=" + null,
					DeviceEntityModel.class)).willReturn(obj);
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
	assertEquals("088410", deviceLists.getDevice().get(0).getDeviceId());
	assertEquals("Handset", deviceLists.getDevice().get(0).getProductClass());
		}
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
			assertEquals("No details found for given criteria", e.getMessage());
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
			assertEquals("No details found for given criteria", e.getMessage());
		}

	}

	@Test
	public void notNullconvertCoherenceDeviceToDeviceTile() {
		deviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile((long) 2.0,
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
		assertEquals(201, cacheResponse.getStatusCodeValue());
		assertEquals("1234", cacheResponse.getBody().getJobId());
		assertEquals("Success", cacheResponse.getBody().getJobStatus());
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
		assertEquals(201, cacheResponse.getStatusCodeValue());
		assertEquals("1234", cacheResponse.getBody().getJobId());
		assertEquals("Success", cacheResponse.getBody().getJobStatus());
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
		assertEquals(201, cacheResponse.getStatusCodeValue());
		assertEquals("1234", cacheResponse.getBody().getJobId());
		assertEquals("Success", cacheResponse.getBody().getJobStatus());
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
		assertEquals(201, cacheResponse.getStatusCodeValue());
		assertEquals("1234", cacheResponse.getBody().getJobId());
		assertEquals("Success", cacheResponse.getBody().getJobStatus());
	}

	@Test
	public void nullTestForCacheDeviceTile() {
		try {
			cacheDeviceAndReviewController.cacheDeviceTile("");
		} catch (Exception e) {
			assertEquals("Group Type is null or Empty String.", e.getMessage());
			try {
				cacheDeviceAndReviewController.cacheDeviceTile("INVALID_GT");
			} catch (Exception ex) {
				assertEquals("Invalid Group Type sent in the request", ex.getMessage());
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
			cacheDeviceService.getIlsPriceWithOfferCodeAndJourney(listOfOfferCodesForUpgrade, listOfSecondLineOfferCode,
					bundleHardwareTroupleMap, new HashMap<>(), "DEVICE_PAYM");
		} catch (IOException e) {
		}

	}

	@Test
	public void testForgetMinimumPriceMap() throws IOException {
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
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> map = new HashMap<>();
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("SecondLine", iLSPriceMap);
		iLSPriceMap.put("Apple", CommonMethods.getOfferAppliedPrice());
		cacheDeviceServiceImpl.getMinimumPriceMap("DEVICE_PAYM", new HashMap<>(), 
				listOfOfferCodesForUpgrade, listOfSecondLineOfferCode, map, iLSPriceMap, bundleHardwareTroupleMap, ilsPriceForBundleAndHardwareMap);
		cacheDeviceServiceImpl.setLeadNonLeadPriceMap(null, null, null, null, null);
		List<BundleAndHardwareTuple> bundleAndHardwareTupleListForNonLeanPlanId = new ArrayList<>();
		cacheDeviceServiceImpl.setLeadNonLeadPriceMap(null, null, null, null, null);
		cacheDeviceServiceImpl.setLeadNonLeadPriceMap(null, null, null, null, bundleAndHardwareTupleListForNonLeanPlanId);
		cacheDeviceServiceImpl.setBundleAndHardwareTupleListJourneyAware(null, null, null, null, null,null,null);
		List<CommercialProduct> listOfCommercialProduct = new ArrayList<>();
		cacheDeviceServiceImpl.setBundleAndHardwareTupleListJourneyAware(null, null, null, null, null,null,listOfCommercialProduct);
		cacheDeviceServiceImpl.setListOfProductGroupRepository(null, null, null);
		Set<String> listOfOfferCodes = new HashSet<>();
		listOfOfferCodes.add("W_HH_OC_01");
		listOfOfferCodes.add("W_HH_OC_02");
		listOfOfferCodes.add("W_HH_OC_Paym_01");
		listOfOfferCodes.add("W_HH_OC_Paym_02");
		listOfOfferCodes.add("W_HH_PAYM_OC_01");
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
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
		List<String> l = new ArrayList<>();
		l.add("093353");
		Map<String, String> groupMap = new HashMap<>();
		groupMap.put("093353", "Apple-iphone&&2");
		Map<String, List<PriceForBundleAndHardware>> priceMap = new HashMap<>();
		priceMap.put("apple-iphone", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		deviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, priceMap, CommonMethods.getPrice(), "093353", "apple-iphone");
		priceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		cacheDeviceServiceImpl.getILSPriceMap(listOfOfferCodes, commercialBundleMap, listOfCimpatiblePlanMap, bundleHardwareTroupleMap, "093353", "110015");
		cacheDeviceServiceImpl.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, priceMap, listOfPriceForBundleAndHardware, "093353", "apple-iphone", "110015");
		cacheDeviceServiceImpl.getUpgradeLeadPlanIdForPaymDevice(priceMap, commercialBundleMap, "093353", "");
		cacheDeviceServiceImpl.getNonUpgradeLeadPlanIdForPaymDevice(priceMap,priceMap, commercialBundleMap,listOfPriceForBundleAndHardware, "093353","apple-iphone", "");
	}
	@Test
	public void testForDb() {
		cacheDeviceService.updateCacheDeviceToDb("12055", "457892");
		cacheDeviceServiceImpl.getCoomercialBundleMapForPaymCacheDevice(null, new HashSet<>());
	}

	@Test
	public void testForIndexPrecalData() {
		cacheDeviceService.indexPrecalData(CommonMethods.getDevicePreCalculatedDataFromSolr());
	}

	@Test
	public void testForGetNonUpgradeLeadPlanIdForPaymCacheDevice() {
		Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
		nonLeadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		commercialBundleMap.put("110163", CommonMethods.getCommercialBundleForcacheDevice());
		String leadId = cacheDeviceService.getNonUpgradeLeadPlanIdForPaymCacheDevice(nonLeadPlanIdPriceMap,
				new HashMap<>(), commercialBundleMap, new ArrayList<>(), "093353", "Apple-Iphone");
		nonLeadPlanIdPriceMap.put("Apple-Iphone", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		cacheDeviceService.getNonUpgradeLeadPlanIdForPaymCacheDevice(nonLeadPlanIdPriceMap,
				nonLeadPlanIdPriceMap, commercialBundleMap, new ArrayList<>(), "093353", "Apple-Iphone");
		assertNotNull(leadId);
		assertEquals("110163", leadId);
	}

	@Test
	public void testForGetUpgradeLeadPlanIdForCacheDevice() {
		Map<String, List<PriceForBundleAndHardware>> nonLeadPlanIdPriceMap = new HashMap<>();
		nonLeadPlanIdPriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		commercialBundleMap.put("110163", CommonMethods.getCommercialBundleForcacheDevice());
		String leadId = cacheDeviceService.getUpgradeLeadPlanIdForCacheDevice(nonLeadPlanIdPriceMap,
				commercialBundleMap, "093353");
		assertNotNull(leadId);
		assertEquals("110163", leadId);
	}

	@Test
	public void testForGetDevicePrecaldataForPaymCacheDeviceTile() {
		Set<String> listOfOfferCodes = new HashSet<>();
		listOfOfferCodes.add("W_HH_OC_01");
		listOfOfferCodes.add("W_HH_OC_02");
		listOfOfferCodes.add("W_HH_OC_Paym_01");
		listOfOfferCodes.add("W_HH_OC_Paym_02");
		listOfOfferCodes.add("W_HH_PAYM_OC_01");
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
		
		
		
		cacheDeviceService.getDevicePrecaldataForPaymCacheDeviceTile("DEVICE_PAYM", l, d, listOfOfferCodes, map, map,
				groupMap, leadPlanIdPriceMap, nonLeadPlanIdPriceMap, nonLeadPlanIdPriceMap, commercialBundleMap,
				listOfLeadPlanId, listOfCimpatiblePlanMap,
				CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile(), bundleHardwareTroupleMap,
				nonLeadPlanIdPriceMap, "093353",CommonMethods.getCommercialProduct());
		Map<String, List<PriceForBundleAndHardware>> priceMap = new HashMap<>();
		priceMap.put("apple-iphone", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		deviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, priceMap, CommonMethods.getPrice(), "093353", "apple-iphone");
		priceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile());
		deviceUtils.getLeadPlanGroupPriceMap(leadPlanIdPriceMap, priceMap, CommonMethods.getPrice(), "093353", "apple-iphone");
		deviceUtils.getIlsPriceMap(listOfOfferCodes, commercialBundleMap, listOfCimpatiblePlanMap, bundleHardwareTroupleMap, "093353");
		Map<String, String> minimumPriceMap = new HashMap<>();
		minimumPriceMap.put("093353", "10");
		Map<String, Map<String, List<PriceForBundleAndHardware>>> map1 = new HashMap<>();
		map1.put("offeredPrice", priceMap);
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> ilsOfferPriceWithJourneyAware = new HashMap<>();
		ilsOfferPriceWithJourneyAware.put("093353", map1);
		Map<String, Map<String, List<PriceForBundleAndHardware>>> mapOfIlsPriceWithoutOfferCode = new HashMap<>();
		mapOfIlsPriceWithoutOfferCode.put("093353", priceMap);
		DevicePreCalculatedData deviceDataRating = new DevicePreCalculatedData();
		deviceDataRating.setDeviceId("093353");
		deviceDataRating.setProductGroupName("093353");
		deviceUtils.updateDevicePrecaldataBasedOnIlsPriceAndRating(minimumPriceMap, ilsOfferPriceWithJourneyAware, mapOfIlsPriceWithoutOfferCode, groupMap, deviceDataRating);
		deviceDataRating.setPriceInfo(CommonMethods.getPriceinforForSorl());
		deviceDataRating.setMedia(CommonMethods.getmediaForSorl());
		deviceUtils.updateDevicePrecaldataBasedOnIlsPriceAndRating(minimumPriceMap, ilsOfferPriceWithJourneyAware, mapOfIlsPriceWithoutOfferCode, groupMap, deviceDataRating);
		listOfLeadPlanId.clear();
		cacheDeviceService.getDevicePrecaldataForPaymCacheDeviceTile("DEVICE_PAYM", l, d, listOfOfferCodes, map, map,
				groupMap, leadPlanIdPriceMap, nonLeadPlanIdPriceMap, nonLeadPlanIdPriceMap, commercialBundleMap,
				listOfLeadPlanId, listOfCimpatiblePlanMap,
				CommonMethods.getPriceForBundleAndHardwareForCacheDeviceTile(), bundleHardwareTroupleMap,
				nonLeadPlanIdPriceMap, "093353",CommonMethods.getCommercialProduct());
	}

	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListingNullLeadPlanId() {
		DevicePreCalculatedData productGroupForDeviceListing = cacheDeviceDaoUtils
				.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353", null, "groupname", "groupId",
						CommonMethods.getPrice(), CommonMethods.getleadMemberMap(), CommonMethods.getleadMemberMap(),
						null, STRING_DEVICE_PAYG,CommonMethods.getCommercialProduct());

		Assert.assertNotNull(productGroupForDeviceListing);
		assertEquals("093353", productGroupForDeviceListing.getDeviceId());
		assertEquals("groupname", productGroupForDeviceListing.getProductGroupName());
		assertEquals("groupId", productGroupForDeviceListing.getPaygProductGroupId());
		assertEquals("093353", productGroupForDeviceListing.getNonUpgradeLeadDeviceId());
		assertEquals("093353", productGroupForDeviceListing.getUpgradeLeadDeviceId());
	}

	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListing() {
		DevicePreCalculatedData productGroupForDeviceListing = cacheDeviceDaoUtils
				.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353", "leadPlanId", "groupname",
						"groupId", CommonMethods.getPrice(), CommonMethods.getleadMemberMap(),
						CommonMethods.getleadMemberMap(), "upgradeLeadPlanId", STRING_DEVICE_PAYM,CommonMethods.getCommercialProduct());
		Assert.assertNotNull(productGroupForDeviceListing);
		assertEquals("093353", productGroupForDeviceListing.getDeviceId());
		assertEquals("groupname", productGroupForDeviceListing.getProductGroupName());
		assertEquals("groupId", productGroupForDeviceListing.getPaymProductGroupId());
		assertEquals("093353", productGroupForDeviceListing.getNonUpgradeLeadDeviceId());
		assertEquals("093353", productGroupForDeviceListing.getUpgradeLeadDeviceId());
	}

	@Test
	public void testForGetListOfOfferAppliedPriceDetails() {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.OfferAppliedPriceDetails> listOfferAppliedPriceDetails = cacheDeviceDaoUtils
				.getListOfOfferAppliedPriceDetails(CommonMethods.getOfferAppliedPriceDetails());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
		assertEquals("093353", listOfferAppliedPriceDetails.get(0).getDeviceId());
		assertEquals("Upgrade", listOfferAppliedPriceDetails.get(0).getJourneyType());
		assertEquals("W_HH_OC_01", listOfferAppliedPriceDetails.get(0).getOfferCode());
	}

	@Test
	public void testForGetPriceForSolr() {
		com.vf.uk.dal.device.model.merchandisingpromotion.PriceInfo listOfferAppliedPriceDetails = cacheDeviceDaoUtils
				.getPriceForSolr(CommonMethods.getPriceinforForSorl());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
		assertEquals("110154", listOfferAppliedPriceDetails.getBundlePrice().getBundleId());
		assertEquals(10.300000190734863, listOfferAppliedPriceDetails.getBundlePrice().getMonthlyPrice().getGross(), 0);
		assertEquals(9.0, listOfferAppliedPriceDetails.getHardwarePrice().getOneOffPrice().getGross(), 0);
		assertEquals("092660", listOfferAppliedPriceDetails.getHardwarePrice().getHardwareId());
	}

	@Test
	public void testForGetListOfSolrMedia() {
		List<com.vf.uk.dal.device.model.merchandisingpromotion.Media> listOfferAppliedPriceDetails = cacheDeviceDaoUtils
				.getListOfSolrMedia(CommonMethods.getmediaForSorl());

		Assert.assertNotNull(listOfferAppliedPriceDetails);
		assertEquals("discountId", listOfferAppliedPriceDetails.get(0).getDiscountId());
		assertEquals("id", listOfferAppliedPriceDetails.get(0).getId());
		assertEquals("offerCode", listOfferAppliedPriceDetails.get(0).getOfferCode());
		assertEquals("type", listOfferAppliedPriceDetails.get(0).getType());
	}

	@Test
	public void testForGetListOfIlsPriceWithoutOfferCode() {
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("SecondLine", iLSPriceMap);
		Map<String, Object> cachePrice = cacheDeviceDaoUtils.getListOfIlsPriceWithoutOfferCode("093353",
				ilsPriceForBundleAndHardwareMap);
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
		Map<String, Object> cachePrice = cacheDeviceDaoUtils.getListOfOfferAppliedPrice("093353", listOfeerCode);
		Assert.assertNotNull(cachePrice);
	}

	@Test
	public void testForGetPriceInfoForSolr() {

		Map<String, Object> cachePrice = cacheDeviceDaoUtils
				.getPriceInfoForSolr(CommonMethods.getOfferAppliedPrice().get(0));
		Assert.assertNotNull(cachePrice);
	}

	@Test
	public void testForGetMerchandising() {
		List<String> promoteAs = new ArrayList<>();
		promoteAs.add("handset-promotion");
		List<MerchandisingPromotion> mpList = deviceESHelper.getMerchandising(promoteAs);
		assertNotNull(mpList);
		assertEquals("AllPhone.full.2017", mpList.get(0).getTag());
		assertEquals("Label", mpList.get(0).getLabel());
		assertEquals("description", mpList.get(0).getDescription());
	}

	@Test
	public void testForCalculateDiscount() {
		Iterator<PriceForBundleAndHardware> iterator = CommonMethods.getOfferAppliedPrice().iterator();
		iterator.next();
		deviceServiceImplUtility.calculateDiscount(2.0, iterator, CommonMethods.getOfferAppliedPrice().get(0));

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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null,
				listOfOfferAppliedPrice, "W_HH_OC_02", groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Map<String, BundleModel> bundleModelMap = new HashMap<>();
		bundleModelMap.put("110154", CommonMethods.getBundleModelListForBundleList().get(0));
		Map<String, BundlePrice> bundleModelAndPriceMap = new HashMap<>();
		bundleModelAndPriceMap.put("110154", CommonMethods.getBundlePrice());
		deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", bundleModelMap,
				listOfOfferAppliedPrice, "W_HH_OC_02", groupNameWithProdId, bundleModelAndPriceMap, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		bundleModelMap.clear();
		bundleModelAndPriceMap.put("121212", CommonMethods.getBundlePrice());
		bundleModelMap.put("121212", CommonMethods.getBundleModelListForBundleList().get(0));
		deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", bundleModelMap,
				listOfOfferAppliedPrice, "W_HH_OC_02", groupNameWithProdId, bundleModelAndPriceMap, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null,
				listOfOfferAppliedPrice1, null, groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null,
				listOfOfferAppliedPrice1, null, groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice1, null, productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null,
				listOfOfferAppliedPrice1, "W_HH_OC_02", groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null,
				listOfOfferAppliedPrice1, "W_HH_OC_02", groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice1, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYG", null, null, null,
				groupNameWithProdId, null, null, isLeadMemberFromSolr, null, JOURNEY_TYPE_ACQUISITION,
				productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
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
		FacetedDevice deviceList = deviceTilesDaoUtils.convertProductModelListToDeviceList(
				CommonMethods.getProductModel(), CommonMethods.getListOfProducts(),
				CommonMethods.getProductGroupFacetModel_One().getListOfFacetsFields(), "DEVICE_PAYM", null,
				listOfOfferAppliedPrice1, null, groupNameWithProdId, null, null, isLeadMemberFromSolr,
				listOfOfferAppliedPrice1, "Upgrade", productGroupdetailsMap, cdnDomain);
		Assert.assertNotNull(deviceList);
		assertEquals("093353", deviceList.getDevice().get(0).getDeviceId());
		assertEquals("HANDSET", deviceList.getDevice().get(0).getProductClass());
		assertEquals("na", deviceList.getDevice().get(0).getRating());
	}

	@Deprecated
	@Test
	public void notNullTestForDateValidation() {
		Date startDateTime = new Date("15/05/2017");
		Date endDateTime = new Date("15/05/2071");
		assertTrue(deviceTilesDaoUtils.dateValidation(startDateTime, endDateTime, true));
	}

	@Test
	public void notNullTestForGetBundlePriceBasedOnDiscountDuration() {
		Double priceFull = deviceTilesDaoUtils.getBundlePriceBasedOnDiscountDuration(
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice(), FULL_DURATION_DISCOUNT);
		Double price = deviceTilesDaoUtils.getBundlePriceBasedOnDiscountDuration(
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice(), LIMITED_TIME_DISCOUNT);
		assertNotNull(price);
		assertEquals(13.64, price, 0);
		assertNotNull(priceFull);
		assertEquals(10.11, priceFull, 0);
	}

	@Test
	public void notNullTestForPopulateMerchandisingPromotions() {
		PriceForBundleAndHardware bundlePrice = CommonMethods.getPriceForBundleAndHardware().get(0);
		bundlePrice.getBundlePrice().setMerchandisingPromotions(null);
		deviceTilesDaoUtils.populateMerchandisingPromotions(bundlePrice,
				CommonMethods.getPriceForBundleAndHardware().get(0).getBundlePrice());
	}

	@Test
	public void notNullTestForGetBundleAndHardwarePriceFromSolrWithoutOfferCode() {
		PriceForBundleAndHardware priceForBundle = deviceTilesDaoUtils
				.getBundleAndHardwarePriceFromSolrWithoutOfferCode(CommonMethods.getProductModel().get(0),
						CommonMethods.getBundleModelListForBundleListForDeviceList().get(0), "110154", "DEVICE_PAYM");
		PriceForBundleAndHardware price = deviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(
				CommonMethods.getProductModel().get(0),
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(1), "110154", "DEVICE_PAYM");
		deviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(
				CommonMethods.getProductModel().get(2),
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(1), "110154", "DEVICE_PAYG");
		deviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(
				CommonMethods.getProductModel().get(1),
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(1), "110154", "DEVICE_PAYG");
		deviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(null,
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(0), "110154", "DEVICE_PAYG");
		deviceTilesDaoUtils.getBundleAndHardwarePriceFromSolrWithoutOfferCode(null,
				CommonMethods.getBundleModelListForBundleListForDeviceList().get(1), "110154", "DEVICE_PAYG");
		assertNotNull(priceForBundle);
		assertNotNull(price);
		assertEquals("109373", priceForBundle.getBundlePrice().getBundleId());
		assertEquals("10", priceForBundle.getBundlePrice().getMonthlyPrice().getGross());
		assertEquals("109372", price.getBundlePrice().getBundleId());
		assertEquals("10", price.getBundlePrice().getMonthlyPrice().getGross());
	}

	@Test
	public void notNullTestForGetDeviceIlsJourneyAwarePriceMap() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		deviceUtils.getDeviceIlsJourneyAwarePriceMap(new HashMap<>(), CommonMethods.getOfferAppliedPrice().get(0));
		deviceUtils.getDeviceIlsJourneyAwarePriceMap(iLSPriceMap, CommonMethods.getOfferAppliedPrice().get(0));
	}

	@Test
	public void testForGetEntireIlsJourneyAwareMap() {
		Map<String, Map<String, Map<String, List<PriceForBundleAndHardware>>>> map = new HashMap<>();
		Map<String, Map<String, List<PriceForBundleAndHardware>>> ilsPriceForBundleAndHardwareMap = new HashMap<>();
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		ilsPriceForBundleAndHardwareMap.put("SecondLine", iLSPriceMap);
		deviceUtils.getEntireIlsJourneyAwareMap(map, ilsPriceForBundleAndHardwareMap);
	}

	@Test
	public void testForGetMinimumPriceMap() {
		Map<String, List<PriceForBundleAndHardware>> iLSPriceMap = new HashMap<>();
		iLSPriceMap.put("Apple", CommonMethods.getOfferAppliedPrice());
		deviceUtils.getMinimumPriceMap(new HashMap<>(), iLSPriceMap);
	}

	@Test
	public void testForGetmonthlyPriceFormPriceForPayG() {
		PriceForBundleAndHardware price = CommonMethods.getOfferAppliedPrice().get(0);
		deviceUtils.getmonthlyPriceFormPriceForPayG(price);
		price.getHardwarePrice().getOneOffPrice().setGross("20");
		price.getHardwarePrice().getOneOffDiscountPrice().setGross("20");
		String monthlyPrice = deviceUtils.getmonthlyPriceFormPriceForPayG(price);
		assertNotNull(monthlyPrice);
		assertEquals("20", monthlyPrice);
	}

	@Test
	public void testForGetUpdateDeviceratingForCacheDevice() {
		Map<String, String> ratingsReviewMap = new HashMap<>();
		ratingsReviewMap.put("sku93353", "3.275");

		deviceUtils.updateDeviceratingForCacheDevice(ratingsReviewMap, CommonMethods.getDevicePreCal().get(0));
	}

	@Test
	public void testForLeastMonthlyPriceForpayG() {
		String leastPrice = deviceUtils
				.leastMonthlyPriceForpayG(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		List<PriceForBundleAndHardware> priceList = CommonMethods.getPriceForBundleAndHardwareListFromTupleList();
		PriceForBundleAndHardware price = priceList.get(0);
		priceList.add(price);
		deviceUtils.leastMonthlyPriceForpayG(priceList);
		HardwarePrice bprice = price.getHardwarePrice();
		Price mdp = bprice.getOneOffDiscountPrice();
		mdp.setGross(null);
		bprice.setOneOffDiscountPrice(mdp);
		price.setHardwarePrice(bprice);
		priceList.add(0, price);
		deviceUtils.leastMonthlyPriceForpayG(priceList);
		assertNotNull(leastPrice);
		assertEquals("0", leastPrice);
	}

	@Test
	public void notNullTestForDaoUtilsconvertCoherenceDeviceToDeviceDetails() {
		DeviceDetails deviceDetails = deviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceDetails(
				CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware(),
				CommonMethods.getListOfBundleAndHardwarePromotions(), cdnDomain);
		Assert.assertNotNull(deviceDetails);
		assertEquals("093353", deviceDetails.getDeviceId());
		assertEquals("Handset", deviceDetails.getProductClass());
	}

	@Test
	public void notNullTestForSetPriceMerchandisingPromotion() {
		List<MediaLink> mediaLink = accessoriesAndInsurancedaoUtils.setPriceMerchandisingPromotion(
				CommonMethods.getForUtilityPriceForBundleAndHardware().get(0).getHardwarePrice());
		assertNotNull(mediaLink);
		assertEquals("limited_discount.merchandisingPromotions.merchandisingPromotion.label", mediaLink.get(0).getId());
		assertEquals("TEXT", mediaLink.get(0).getType());
		assertEquals("20% off with any handset", mediaLink.get(0).getValue());
	}

	@Test
	public void notNullTestForConvertGroupProductToProductGroupDetails() {
		List<ProductGroup> productGroup = listOfDeviceDetailsDaoUtils
				.convertGroupProductToProductGroupDetails(CommonMethods.getProductGroupModel());
		assertNotNull(productGroup);
		assertEquals("Apple iPhone091221|5-7", productGroup.get(0).getGroupName());
		assertEquals("1", productGroup.get(0).getGroupPriority());
		assertEquals("DEVICE", productGroup.get(0).getGroupType());
	}

	@Test
	public void notNullTestForGetAscendingOrderForBundlePriceAscendingOrder() {
		ListOfDeviceDetailsDaoUtils listOfDeviceDetailsDaoUtils = new ListOfDeviceDetailsDaoUtils();
		List<BundleHeader> bundleHeader = listOfDeviceDetailsDaoUtils
				.getAscendingOrderForBundlePrice1(CommonMethods.getBundleHeaderList("SIMO"));
		assertNotNull(bundleHeader);
		assertEquals("SIMO", bundleHeader.get(0).getBundleClass());
		assertEquals("SIMO", bundleHeader.get(0).getBundleType());
		assertEquals("1", bundleHeader.get(0).getSkuId());
		assertEquals("SIMO plans only", bundleHeader.get(0).getDescription());
	}

	@Test
	public void notNullTestForGetAscendingOrderForBundleHeaderPrice() {
		List<BundleHeader> bundleHeader = deviceServiceCommonUtility
				.getAscendingOrderForBundleHeaderPrice(CommonMethods.getBundleHeaderList("SIMO"));
		assertNotNull(bundleHeader);
		assertEquals("SIMO", bundleHeader.get(0).getBundleClass());
		assertEquals("SIMO", bundleHeader.get(0).getBundleType());
		assertEquals("1", bundleHeader.get(0).getSkuId());
		assertEquals("SIMO plans only", bundleHeader.get(0).getDescription());
	}

	@Test
	public void notNullTestForGetAscendingOrderForBundlePrice() {
		List<PriceForBundleAndHardware> price = deviceServiceCommonUtility
				.getAscendingOrderForBundlePrice(CommonMethods.getPriceForBundleAndHardwareSorting());
		assertNotNull(price);
		assertEquals("183099", price.get(0).getBundlePrice().getBundleId());
		assertEquals("13.64", price.get(0).getMonthlyPrice().getGross());
		assertEquals("5.11", price.get(0).getOneOffPrice().getGross());
		assertEquals("093353", price.get(0).getHardwarePrice().getHardwareId());
	}

	@Test
	public void notNullTestForGetAscendingOrderForOneoffPrice() {
		List<PriceForBundleAndHardware> price = deviceServiceCommonUtility
				.getAscendingOrderForOneoffPrice(CommonMethods.getPriceForBundleAndHardwareSorting());
		assertNotNull(price);
		assertEquals("183099", price.get(0).getBundlePrice().getBundleId());
		assertEquals("12.64", price.get(0).getMonthlyPrice().getGross());
		assertEquals("15.11", price.get(0).getOneOffPrice().getGross());
		assertEquals("093353", price.get(0).getHardwarePrice().getHardwareId());
	}

	@Test
	public void notNullTestForvalidateAllParameters() {
		try {
			validator.validateAllParameters(null, "model", "groupType", "journeyType");
		} catch (Exception e) {
			assertEquals("Invalid input request received. Missing make in the filter criteria", e.getMessage());
		}
		try {
			validator.validateAllParameters("make", null, "groupType", "journeyType");
		} catch (Exception e) {
			assertEquals("Invalid input request received. Missing model in the filter criteria", e.getMessage());
		}
		try {
			validator.validateAllParameters("make", "model", null, "journeyType");
		} catch (Exception e) {
			assertEquals("Invalid input request received. Missing groupType in the filter criteria", e.getMessage());
		}
		try {
			validator.validateAllParameters("make", "model", "grouptype", "journeyType");
		} catch (Exception e) {
			assertEquals("Invalid Group Type sent in the request", e.getMessage());
		}
		try {
			validator.validateAllParameters("make", "model", "Device_PAYG", "Upgrade");
		} catch (Exception e) {
			assertEquals("JourneyType is not compatible for given GroupType", e.getMessage());
		}
		try {
			validator.validateAllParameters("make", "model", "Device_PAYG", "SecondLine");
		} catch (Exception e) {
			assertEquals("JourneyType is not compatible for given GroupType", e.getMessage());
		}
		try {
			validator.validateAllParameters("make", "model", "Device_PAYG", "");
		} catch (Exception e) {
			assertEquals("JourneyType is not compatible for given GroupType", e.getMessage());
		}
	}

	@Test
	public void notNullTestForvalidateCreditLimitValue() {
		assertFalse(validator.validateCreditLimitValue("abcd"));
		assertTrue(validator.validateCreditLimitValue("4"));
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("productId", "093353");
		queryParams.put("deviceId", "093353");
		assertFalse(validator.validateGetDeviceList(queryParams));
		assertFalse(validator.validateGetListOfDeviceTile(queryParams));
		assertTrue(validator.validateJourneyType("upgrade"));
		assertFalse(validator.validateJourneyType("journeyType"));
		assertFalse(validator.validateProduct(queryParams));
		assertFalse(validator.validateProductGroup(queryParams));
		try {
			validator.validatePageSize(-1, 2);
		} catch (Exception e) {
			assertEquals("Page Size Value cannot be negative", e.getMessage());
		}
	}
}
